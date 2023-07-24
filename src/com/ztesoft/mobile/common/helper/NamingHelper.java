/**
 * <p>Title: UOS平台</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 中兴通讯运营支撑开发部</p>
 * @author not attributable
 * @version 1.0
 */
package com.ztesoft.mobile.common.helper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Provides access to JNDI properties for lookups. The properties
 * are stored in two resource files that are read when this class is
 * instantiated. The files contain the following:
 * <UL><LI>JNDI_LOOKUP_PROPERTIES:<BR>
 *     <UL><LI>The name of the InitialContextFactory class to use</LI>
 *         <LI>The IIOP URL for the Persistant name server</LI></UL></LI>
 *     <LI>JNDI_NAMES:<BR>
 *	   <UL><LI>The JNDI names of any resources.</LI></UL></LI></UL>
 *
 * This class is implemented as a singleton.
 *
 * @author: guojun
 */
public class NamingHelper {
	private static final Logger _LOG = Logger.getLogger(NamingHelper.class);
    /**采用特殊符号，防止和用户请求的名称出现重复*/
    private static final String DEFAULT_CTX_NAME =
            "$EOMSPROJ-DEFAULTCONTEXT";
    /**singleton instance variable*/
    private static NamingHelper instance = null;

    /**there is only a instance in a JVM*/
    private static final Map ctxMap = Collections.synchronizedMap(new HashMap());

    /**
     * NamingHelper default constructor.
     */
    private NamingHelper() {
        super();
    }

    /**
     * Retrieves the InitialContext which can then
     * be used for DataSource, EJB Home and JMS administered object
     * lookup.
     *
     * @return The InitialContext object
     *
     * @exception javax.naming.NamingException
     */
    public InitialContext getInitialContext() throws NamingException {
    	
        InitialContext ctx = (InitialContext) ctxMap.get(DEFAULT_CTX_NAME);
        if (ctx == null) {
            ctx = new javax.naming.InitialContext();
            ctxMap.put(DEFAULT_CTX_NAME, ctx);
        }
        if(_LOG.isDebugEnabled()){
    		_LOG.debug("ctxMap="+ctxMap);
    	}
        return ctx;
    }

    /**
     * Retrieves the InitialContext which can then
     * be used for DataSource, EJB Home and JMS administered object
     * lookup.
     * it is used by client.
     *
     * @return The InitialContext object
     *
     * @exception javax.naming.NamingException
     */
    public InitialContext getInitialContext(String contextFactory,
                                            String providerUrl,
                                            String pkgPrefixes,
                                            String username,
                                            String credentials) throws
            NamingException {
        StringBuffer ctxName = new StringBuffer();
        Properties props = new Properties();
        //设置工厂类
        if (contextFactory != null) {
            props.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                      contextFactory);
            ctxName.append(contextFactory);
        }
        //设置URL链接
        if (providerUrl != null) {
            props.put(javax.naming.Context.PROVIDER_URL, providerUrl);
            ctxName.append(providerUrl);
        }
        //设置URL连接的包前置名称
        if (pkgPrefixes != null) {
            props.put(javax.naming.Context.URL_PKG_PREFIXES, pkgPrefixes);
            ctxName.append(pkgPrefixes);
        }
        //设置用户名和密码
        if (username != null) {
            props.put(Context.SECURITY_PRINCIPAL, username);
            ctxName.append(username);

            if (credentials != null) {
                props.put(Context.SECURITY_CREDENTIALS, credentials);
                ctxName.append(credentials);
            }
        }
        InitialContext ctx = (InitialContext) ctxMap.get(ctxName.toString());
        if (ctx == null) {
            ctx = new javax.naming.InitialContext(props);
            ctxMap.put(ctxName.toString(), ctx);
        }
        return ctx;
    }

    /**
     * Singleton accessor method.
     *
     * @return Singleton instance of NamingHelper
     */
    public static NamingHelper singleton() {
        if (instance == null) {
            instance = new NamingHelper();
        }
        return instance;
    }

}
