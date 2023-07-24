package com.ztesoft.mobile.pn.xmpp.handler;

import java.util.HashMap;
import java.util.Map;

import gnu.inet.encoding.Stringprep;
import gnu.inet.encoding.StringprepException;

import com.ztesoft.mobile.pn.service.MobilePnUserService;
import com.ztesoft.mobile.pn.service.ServiceLocator;
import com.ztesoft.mobile.pn.xmpp.UnauthorizedException;
import com.ztesoft.mobile.pn.xmpp.session.ClientSession;
import com.ztesoft.mobile.pn.xmpp.session.Session;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;
import org.xmpp.packet.JID;
import org.xmpp.packet.PacketError;

/** 
 * This class is to handle the TYPE_IQ jabber:iq:register protocol.
 *
 */
public class IQRegisterHandler extends IQHandler {

    private static final String NAMESPACE = "jabber:iq:register";

    private MobilePnUserService userService;

    private Element probeResponse;

    /**
     * Constructor.
     */
    public IQRegisterHandler() {
        userService = ServiceLocator.getInstance().getMobilePnUserService();
        probeResponse = DocumentHelper.createElement(QName.get("query",
                NAMESPACE));
        probeResponse.addElement("username");
        probeResponse.addElement("password");
        //probeResponse.addElement("email");
        //probeResponse.addElement("name");
    }

    /**
     * Handles the received IQ packet.
     * 
     * @param packet the packet
     * @return the response to send back
     * @throws UnauthorizedException if the user is not authorized
     */
    public IQ handleIQ(IQ packet) throws UnauthorizedException {
        IQ reply = null;

        ClientSession session = sessionManager.getSession(packet.getFrom());
        if (session == null) {
            log.error("Session not found for key " + packet.getFrom());
            reply = IQ.createResultIQ(packet);
            reply.setChildElement(packet.getChildElement().createCopy());
            reply.setError(PacketError.Condition.internal_server_error);
            return reply;
        }

        if (IQ.Type.get.equals(packet.getType())) {
            reply = IQ.createResultIQ(packet);
            if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
                // TODO
            } else {
                reply.setTo((JID) null);
                reply.setChildElement(probeResponse.createCopy());
            }
        } else if (IQ.Type.set.equals(packet.getType())) {
            try {
                Element query = packet.getChildElement();
                if (query.element("remove") != null) {
                    if (session.getStatus() == Session.STATUS_AUTHENTICATED) {
                        // TODO
                    } else {
                        throw new UnauthorizedException();
                    }
                } else {
                    String username = query.elementText("username");
                    String password = query.elementText("password");
                    String pnStaffIdStr = query.elementText("staffId");
                    Long staffId= null;
                    if(StringUtils.isNotBlank(pnStaffIdStr)) {
                    	staffId = Long.valueOf(pnStaffIdStr);
                    }
                    String pnStaffName = query.elementText("staffName");
                    String pnImsi = query.elementText("imis");
                    String pnJobId = query.elementText("jobId");

                    if (username != null) {
                        Stringprep.nodeprep(username);
                    }

                    // Deny registration of users with no password
                    if (password == null || password.trim().length() == 0) {
                        reply = IQ.createResultIQ(packet);
                        reply.setChildElement(packet.getChildElement()
                                .createCopy());
                        reply.setError(PacketError.Condition.not_acceptable);
                        return reply;
                    }
                    
                    log.info("Begin to write  data into database...");

                    Map<Object, Object> userMap ;
                    if (session.getStatus() == Session.STATUS_AUTHENTICATED) {	//已验证，则更新记录
                    	Map<Object, Object> paramMap = new HashMap<Object, Object>();
                    	paramMap.put("pnUserNameEquals", session.getUsername());
                    	userMap = userService.getOneByCons(paramMap);
                    	userMap.put("pnOnline",  new Integer(1));		//设置为在线状态
                        userMap.put("pnStaffId", staffId);
                        userMap.put("pnStaffName", pnStaffName);
                        //userMap.put("pnImsi", pnImsi);
                    	userService.updatePnUser(paramMap);
                    	
                    } else {	//会话状态不是已验证，则新增用户

                    	userMap = new HashMap<Object, Object>();
                        userMap.put("pnUserName", username);
                        userMap.put("pnUserPassword", password);
                        userMap.put("pnOnline",  new Integer(1));		//设置为在线状态
                        userMap.put("pnStaffId", staffId);
                        userMap.put("pnStaffName", pnStaffName);
                        //userMap.put("pnImsi", pnImsi);
                        userService.savePnUser(userMap);
                    }

                    log.info("Finish writing data into database...");

                    reply = IQ.createResultIQ(packet);
                }
            } catch (Exception ex) {
                log.error(ex);
                reply = IQ.createResultIQ(packet);
                reply.setChildElement(packet.getChildElement().createCopy());
                if (ex instanceof StringprepException) {
                    reply.setError(PacketError.Condition.jid_malformed);
                } else if (ex instanceof IllegalArgumentException) {
                    reply.setError(PacketError.Condition.not_acceptable);
                } else {
                    reply.setError(PacketError.Condition.internal_server_error);
                }
            }
        }

        // Send the response directly to the session
        if (reply != null) {
            session.process(reply);
        }
        return null;
    }

    /**
     * Returns the namespace of the handler.
     * 
     * @return the namespace
     */
    public String getNamespace() {
        return NAMESPACE;
    }

}
