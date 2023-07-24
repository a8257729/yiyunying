package com.ztesoft.mobile.pn.xmpp.push;

import java.util.Random;

import com.ztesoft.mobile.pn.Constants;
import com.ztesoft.mobile.pn.xmpp.session.ClientSession;
import com.ztesoft.mobile.pn.xmpp.session.SessionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.QName;
import org.xmpp.packet.IQ;

/** 
 * This class is to manage sending the notifcations to the users.  
 *
 */
public class NotificationManager {

    private static final String NOTIFICATION_NAMESPACE = "ztesoft_android_pn:iq:notification";

    private final Log log = LogFactory.getLog(getClass());

    private SessionManager sessionManager;

    /**
     * Constructor.
     */
    public NotificationManager() {
        sessionManager = SessionManager.getInstance();
    }

    /**
     * Broadcasts a newly created notification message to all connected users.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendBroadcast(String apiKey, String title, String message,
            String uri) {
        log.debug("sendBroadcast()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        for (ClientSession session : sessionManager.getSessions()) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
                
                //System.out.println(session.getAddress());	//9c96ab84510b4fc1a3ed43b6b2731b2d@127.0.0.1/ZTESoftMobileClient
                //System.out.println(notificationIQ.toXML());
            }
        }
    }

    /**
     * Sends a newly created notification message to the specific user.
     * 
     * @param apiKey the API key
     * @param title the title
     * @param message the message details
     * @param uri the uri
     */
    public void sendNotifcationToUser(String apiKey, String username,
            String title, String message, String uri) {
        log.debug("sendNotifcationToUser()...");
        IQ notificationIQ = createNotificationIQ(apiKey, title, message, uri);
        ClientSession session = sessionManager.getSession(username);
        if (session != null) {
            if (session.getPresence().isAvailable()) {
                notificationIQ.setTo(session.getAddress());
                session.deliver(notificationIQ);
            }
        }
    }

    /**
     * 给所有在线用户发送广播，username无用
     * @param username
     * @param title
     * @param message
     */
    public void sendNotifcationToUser(String username,
            String title, String message) {
        log.debug("sendNotifcationToUser()...");
        System.out.println("调用单用户推送接口...");
        this.sendBroadcast(Constants.PN_APIKEY_VALUE, title, message, Constants.PN_DEFAULT_URI);
    }
    
    /**
     * 给所有在线用户发送广播，username无用
     * @param username
     * @param title
     * @param message
     * @param uri
     */
    public void sendNotifcationToUser(String username,
            String title, String message, String uri) {
        log.debug("sendNotifcationToUser()...");
        System.out.println("调用单用户推送接口...");
        this.sendBroadcast(Constants.PN_APIKEY_VALUE, title, message, uri);
    }
    /**
     * Creates a new notification IQ and returns it.
     */
    private IQ createNotificationIQ(String apiKey, String title,
            String message, String uri) {
        Random random = new Random();
        String id = Integer.toHexString(random.nextInt());
        // String id = String.valueOf(System.currentTimeMillis());

        Element notification = DocumentHelper.createElement(QName.get(
                "notification", NOTIFICATION_NAMESPACE));
        notification.addElement("id").setText(id);
        notification.addElement("apiKey").setText(apiKey);
        notification.addElement("title").setText(title);
        notification.addElement("message").setText(message);
        notification.addElement("uri").setText(uri);

        IQ iq = new IQ();
        iq.setType(IQ.Type.set);
        iq.setChildElement(notification);

        return iq;
    }
    
    public static void main(String ags[]) {
    	NotificationManager nm = new NotificationManager();
    	nm.sendBroadcast("1234567890", "测试", "测试啊", "");
    }
}
