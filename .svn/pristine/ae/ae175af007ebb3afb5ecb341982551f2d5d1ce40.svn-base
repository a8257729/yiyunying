package com.ztesoft.mobile.common.helper;

public class ServerUtil {

    public static final String GERONIMO_CLASS =
        "/org/apache/geronimo/system/main/Daemon.class";

    public static final String JBOSS_CLASS = "/org/jboss/Main.class";

    public static final String JETTY_CLASS = "/org/mortbay/jetty/Server.class";

    public static final String JONAS_CLASS =
        "/org/objectweb/jonas/server/Server.class";

    public static final String OC4J_CLASS =
        "/oracle/jsp/oc4jutil/Oc4jUtil.class";

    public static final String ORION_CLASS =
        "/com/evermind/server/ApplicationServer.class";

    public static final String PRAMATI_CLASS = "/com/pramati/Server.class";

    public static final String RESIN_CLASS =
        "/com/caucho/server/resin/Resin.class";

    public static final String REXIP_CLASS = "/com/tcc/Main.class";

    public static final String SUN7_CLASS =
        "/com/iplanet/ias/tools/cli/IasAdminMain.class";

    public static final String SUN8_CLASS =
        "/com/sun/enterprise/cli/framework/CLIMain.class";

    public static final String TOMCAT_CLASS =
        "/org/apache/catalina/startup/Bootstrap.class";

    public static final String WEBLOGIC_CLASS = "/weblogic/Server.class";

    public static final String WEBSPHERE_CLASS =
        "/com/ibm/websphere/product/VersionInfo.class";

    public static String getServerId() {
    	ServerUtil sd = _instance;

        if (sd._serverId == null) {
        	if (ServerUtil.isJBoss()) {
                sd._serverId = "jboss";
            }else if (ServerUtil.isWebSphere()) {
                sd._serverId = "websphere";
            }            
            else if (ServerUtil.isWebLogic()) {
                sd._serverId = "weblogic";
            }
            

                     

            if (sd._serverId == null) {
                throw new RuntimeException("Server is not supported");
            }
        }

        return sd._serverId;
    }


    public static boolean isJBoss() {
    	ServerUtil sd = _instance;

        if (sd._jBoss == null) {
            Class c = sd.getClass();

            if (c.getResource(JBOSS_CLASS) != null) {
                sd._jBoss = Boolean.TRUE;
            }
            else {
                sd._jBoss = Boolean.FALSE;
            }
        }

        return sd._jBoss.booleanValue();
    }
public static boolean isWebLogic() {
	ServerUtil sd = _instance;
		if (sd._webLogic == null) {
            Class c = sd.getClass();

            if (c.getResource(WEBLOGIC_CLASS) != null) {
                sd._webLogic = Boolean.TRUE;
            }
            else {
                sd._webLogic = Boolean.FALSE;
            }
        }

        return sd._webLogic.booleanValue();
}
public static boolean isWebSphere() {
	ServerUtil sd = _instance;

        if (sd._webSphere == null) {
            Class c = sd.getClass();

            if (c.getResource(WEBSPHERE_CLASS) != null) {
                sd._webSphere = Boolean.TRUE;
            }
            else {
                sd._webSphere = Boolean.FALSE;
            }
        }

        return sd._webSphere.booleanValue();
    }

    private ServerUtil() {
    }


    private static ServerUtil _instance = new ServerUtil();

    private String _serverId;
    private Boolean _geronimo;
    private Boolean _jBoss;
    private Boolean _jetty;
    private Boolean _jonas;
    private Boolean _oc4j;
    private Boolean _orion;
    private Boolean _pramati;
    private Boolean _resin;
    private Boolean _rexIP;
    private Boolean _sun7;
    private Boolean _sun8;
    private Boolean _tomcat;
    private Boolean _webLogic;
    private Boolean _webSphere;

} 