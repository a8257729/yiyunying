package com.ztesoft.eoms.common.transaction.jta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.transaction.TransactionManager;

import com.ztesoft.eoms.exception.TransactionSystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 直接从服务器类加载器中获取TransactionManager,适用于WebSphere
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class WebSphereTransactionManager extends JtaTransactionManager {

    private static final String FACTORY_CLASS_5_1 =
            "com.ibm.ws.Transaction.TransactionManagerFactory";

    private static final String FACTORY_CLASS_5_0 =
            "com.ibm.ejs.jts.jta.TransactionManagerFactory";

    private static final String FACTORY_CLASS_4 = "com.ibm.ejs.jts.jta.JTSXA";


    private static final Log logger = LogFactory.getLog(WebSphereTransactionManager.class);


    public WebSphereTransactionManager() throws TransactionSystemException {
        super();
    }

    protected TransactionManager lookupTransactionManager() throws
            TransactionSystemException {
        Class clazz;
        TransactionManager _temp = null;
        try {
            logger.debug("Trying WebSphere 5.1+: " + FACTORY_CLASS_5_1);
            clazz = Class.forName(FACTORY_CLASS_5_1);
            logger.info("Found WebSphere 5.1+: " + FACTORY_CLASS_5_1);
        } catch (ClassNotFoundException ex) {
            logger.debug(
                    "Could not find WebSphere 5.1/6.0 TransactionManager factory class",
                    ex);
            try {
                logger.debug("Trying WebSphere 5.0: " + FACTORY_CLASS_5_0);
                clazz = Class.forName(FACTORY_CLASS_5_0);
                logger.info("Found WebSphere 5.0: " + FACTORY_CLASS_5_0);
            } catch (ClassNotFoundException ex2) {
                logger.debug(
                        "Could not find WebSphere 5.0 TransactionManager factory class",
                        ex2);
                try {
                    logger.debug("Trying WebSphere 4: " + FACTORY_CLASS_4);
                    clazz = Class.forName(FACTORY_CLASS_4);
                    logger.info("Found WebSphere 4: " + FACTORY_CLASS_4);
                } catch (ClassNotFoundException ex3) {
                    logger.debug(
                            "Could not find WebSphere 4 TransactionManager factory class",
                            ex3);
                    throw new TransactionSystemException(
                            "Could not find any WebSphere  TransactionManager factory class, " +
                            "neither for WebSphere version 5.1+ nor 5.0 nor 4");
                }
            }
        }

        try {
            Method method = clazz.getMethod("getTransactionManager", (Class[])null);
            _temp = (TransactionManager) method.invoke(null, (Object[])null);
        } catch (InvocationTargetException ex) {
            throw new TransactionSystemException(
                    "WebSphere's TransactionManagerFactory.getTransactionManager method failed",
                    ex.getTargetException());
        } catch (Exception ex) {
            throw new TransactionSystemException(
                    "Could not access WebSphere's TransactionManagerFactory.getTransactionManager method",
                    ex);
        }
        return _temp;
    }

}
