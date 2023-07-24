
package com.ztesoft.mobile.pn.xmpp.presence;

import java.util.Map;

import com.ztesoft.mobile.pn.xmpp.session.ClientSession;
import com.ztesoft.mobile.pn.xmpp.session.SessionManager;

import org.apache.commons.collections.MapUtils;
import org.xmpp.packet.Presence;

/** 
 * This class is to manage the presences of users. 
 *
 */
public class PresenceManager {

    private SessionManager sessionManager;

    /**
     * Constructor.
     */
    public PresenceManager() {
        sessionManager = SessionManager.getInstance();
    }

    /**
     * Returns the availability of the user.
     * 
     * @param user the user
     * @return true if the user is available
     */
    public boolean isAvailable(Map userMap) {
        return sessionManager.getSession(MapUtils.getString(userMap, "pnUserName")) != null;
    }

    /**
     * Returns the current presence of the user.
     * 
     * @param user the user
     * @return the current presence of the user.
     */
    public Presence getPresence(Map userMap) {
        if (userMap == null || userMap.isEmpty()) {
            return null;
        }
        Presence presence = null;
        ClientSession session = sessionManager.getSession(MapUtils.getString(userMap, "pnUserName"));
        if (session != null) {
            presence = session.getPresence();
        }
        return presence;
    }

}
