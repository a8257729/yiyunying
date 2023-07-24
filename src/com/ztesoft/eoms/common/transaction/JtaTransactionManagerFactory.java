package com.ztesoft.eoms.common.transaction;

import javax.transaction.TransactionManager;

import com.ztesoft.eoms.common.transaction.jta.JNDITransactionManager;
import com.ztesoft.eoms.common.transaction.jta.WebLogicTransactionManager;
import com.ztesoft.eoms.common.transaction.jta.WebSphereTransactionManager;
import com.ztesoft.eoms.exception.TransactionSystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public class JtaTransactionManagerFactory {
    protected final Log logger = LogFactory.getLog(getClass());

    private static JtaTransactionManagerFactory _instance = new
            JtaTransactionManagerFactory();
    private TransactionManager getJNDITransactionManager() {

        try {

            return new
                    JNDITransactionManager().getTransactionManager();
        } catch (TransactionSystemException tse) {
            logger.debug("Could not find JNDI TransactionManager ");
            return null;
        }
    }

    private TransactionManager getWebLogicTransactionManager() {

        try {

            return new
                    WebLogicTransactionManager().getTransactionManager();
        } catch (TransactionSystemException tse) {
            logger.debug("Could not find WebLogic TransactionManager ");
            return null;
        }
    }

    private TransactionManager getWebSphereTransactionManager() {

        try {

            return new WebSphereTransactionManager().getTransactionManager();
        } catch (TransactionSystemException tse) {
            logger.debug("Could not find WebSphere TransactionManager ");
            return null;
        }

    }


    public TransactionManager getTransactionManager() {
        TransactionManager transactionManager = null;

        if (transactionManager == null) {
            transactionManager = getJNDITransactionManager();
        }
        if (transactionManager == null) {
            transactionManager = getWebLogicTransactionManager();
        }
        if (transactionManager == null) {
            transactionManager = getWebSphereTransactionManager();
        }

        return transactionManager;
    }

    public static JtaTransactionManagerFactory instance() {
        return _instance;
    }

}
