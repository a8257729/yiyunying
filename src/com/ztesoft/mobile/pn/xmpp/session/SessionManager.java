package com.ztesoft.mobile.pn.xmpp.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAO;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAOImpl;
import com.ztesoft.mobile.pn.xmpp.XmppServer;
import com.ztesoft.mobile.pn.xmpp.net.Connection;
import com.ztesoft.mobile.pn.xmpp.net.ConnectionCloseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xmpp.packet.JID;

/** 
 * This class manages the sessions connected to the server.
 *
 */
public class SessionManager {

    private static final Log log = LogFactory.getLog(SessionManager.class);

    private static final String RESOURCE_NAME = "ZTESoftMobileClient";

    private static SessionManager instance;

    private String serverName;

    private Map<String, ClientSession> preAuthSessions = new ConcurrentHashMap<String, ClientSession>();

    private Map<String, ClientSession> clientSessions = new ConcurrentHashMap<String, ClientSession>();

    private final AtomicInteger connectionsCounter = new AtomicInteger(0);

    private ClientSessionListener clientSessionListener = new ClientSessionListener();

    private SessionManager() {
        serverName = XmppServer.getInstance().getServerName();
    }

    /**
     * Returns the singleton instance of SessionManager.
     * 
     * @return the instance
     */
    public static SessionManager getInstance() {
    	//System.out.println("����Session������...");
        if (instance == null) {
            synchronized (SessionManager.class) {
                instance = new SessionManager();
            }
        }
        return instance;
    }

    /**
     * Creates a new ClientSession and returns it.
     *  
     * @param conn the connection
     * @return a newly created session
     */
    public ClientSession createClientSession(Connection conn) {
        if (serverName == null) {
            throw new IllegalStateException("Server not initialized");
        }

        Random random = new Random();
        String streamId = Integer.toHexString(random.nextInt());

        ClientSession session = new ClientSession(serverName, conn, streamId);
        conn.init(session);
        conn.registerCloseListener(clientSessionListener);

        // Add to pre-authenticated sessions
        preAuthSessions.put(session.getAddress().getResource(), session);

        // Increment the counter of user sessions
        connectionsCounter.incrementAndGet();

        log.debug("ClientSession created.");
        return session;
    }

    /**
     * Adds a new session that has been authenticated. 
     *  
     * @param session the session
     */
    public void addSession(ClientSession session) {
        preAuthSessions.remove(session.getStreamID().toString());
        ClientSession t = clientSessions.put(session.getAddress().toString(), session);
        String tokens = session.getAddress().getNode();
        //if(t != null) {
        System.out.println("д��Session...������״̬");
    	Map paramMap =new HashMap();
    	paramMap.put("pnOnline", new Integer(1));
    	paramMap.put("pnUserName", tokens);
    	try {
			this.getMobilePnUserDAO().updateOnline(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�û�����״̬����ʧ�ܣ�");
		}
        //}
        
        System.out.println("...д��Sesion....");
    }

    /**
     * Returns the session associated with the username.
     * 
     * @param username the username of the client address
     * @return the session associated with the username
     */
    public ClientSession getSession(String username) {
        // return getSession(new JID(username, serverName, null, true));
        return getSession(new JID(username, serverName, RESOURCE_NAME, true));
    }

    /**
     * Returns the session associated with the JID.
     * 
     * @param from the client address
     * @return the session associated with the JID
     */
    public ClientSession getSession(JID from) {
        if (from == null || serverName == null
                || !serverName.equals(from.getDomain())) {
            return null;
        }
        // Check pre-authenticated sessions
        if (from.getResource() != null) {
            ClientSession session = preAuthSessions.get(from.getResource());
            if (session != null) {
                return session;
            }
        }
        if (from.getResource() == null || from.getNode() == null) {
            return null;
        }
        return clientSessions.get(from.toString());
    }

    /**
     * Returns a list that contains all authenticated client sessions.
     * 
     * @return a list that contains all client sessions
     */
    public Collection<ClientSession> getSessions() {
    	System.out.println("һ���У�" + clientSessions.size() +"�Ự...");
        return clientSessions.values();
    }

    /**
     * Removes a client session.
     * 
     * @param session the session to be removed
     * @return true if the session was successfully removed 
     */
    public boolean removeSession(ClientSession session) {
    	boolean flag = false;
        if (session == null || serverName == null) {
            return flag;
        }
        JID fullJID = session.getAddress();
        String tokens = fullJID.getNode();
        // Remove the session from list
        boolean clientRemoved = clientSessions.remove(fullJID.toString()) != null;
        boolean preAuthRemoved = (preAuthSessions.remove(fullJID.getResource()) != null);

        // Decrement the counter of user sessions
        if (clientRemoved || preAuthRemoved) {
            connectionsCounter.decrementAndGet();
            flag =  true; 
        }
        
        if(flag) {
            System.out.println("���Session...������״̬");
        	Map paramMap =new HashMap();
        	paramMap.put("pnOnline", new Integer(0));
        	paramMap.put("pnUserName", tokens);
        	try {
				this.getMobilePnUserDAO().updateOnline(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("�û�����״̬����ʧ�ܣ�");
			}
        }

    
        return flag;
    }

    /**
     * Closes the all sessions. 
     */
    public void closeAllSessions() {
        try {
            // Send the close stream header to all connections
            Set<ClientSession> sessions = new HashSet<ClientSession>();
            sessions.addAll(preAuthSessions.values());
            sessions.addAll(clientSessions.values());

            for (ClientSession session : sessions) {
                try {
                    session.getConnection().systemShutdown();
                } catch (Throwable t) {
                }
            }
        } catch (Exception e) {
        }
        System.out.println("�ر�����Session...");
    }

    /**
     * A listner to handle a session that has been closed.
     */
    private class ClientSessionListener implements ConnectionCloseListener {

        public void onConnectionClose(Object handback) {
            try {
                ClientSession session = (ClientSession) handback;
                removeSession(session);
            } catch (Exception e) {
                log.error("Could not close socket", e);
            }
        }
    }

	private MobilePnUserDAO getMobilePnUserDAO() {
		String daoName = MobilePnUserDAOImpl.class.getName();
		return (MobilePnUserDAO) BaseDAOFactory.getImplDAO(daoName);
	}
    
}
