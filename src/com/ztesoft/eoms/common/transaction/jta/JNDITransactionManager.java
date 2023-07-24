package com.ztesoft.eoms.common.transaction.jta;

import javax.transaction.TransactionManager;

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
 * @author not attributable
 * @version 1.0
 * 覆盖Jta事务管理器，实现从jndi中获取事务管理器，支持所有的数据库。
 */
public class JNDITransactionManager extends JtaTransactionManager {

    private static final Log _log = LogFactory.getLog(JNDITransactionManager.class);
    /**
     * Fallback JNDI locations for the JTA TransactionManager. Applied if
     * the JTA UserTransaction does not implement the JTA TransactionManager
     * interface, provided that the "autodetectTransactionManager" flag is "true".
     * @see #setTransactionManagerName
     * @see #setAutodetectTransactionManager
     */
    public static final String[] FALLBACK_TRANSACTION_MANAGER_NAMES =
            new String[] {"java:comp/TransactionManager",
            "java:pm/TransactionManager", "java:/TransactionManager",
            "javax.transaction.TransactionManager"};


    public JNDITransactionManager() throws TransactionSystemException {
        super();

    }


    protected TransactionManager lookupTransactionManager() throws
            TransactionSystemException {

        try {
            userTransaction = getUserTransaction();

        } catch (javax.naming.NamingException ne1) {
            throw new TransactionSystemException(ne1.getMessage());
        }
        if (userTransaction instanceof TransactionManager) {
            return (TransactionManager) userTransaction;
        }

        TransactionManager transactionManager = null;

        for (int i = 0; i < FALLBACK_TRANSACTION_MANAGER_NAMES.length; i++) {
            try {
                transactionManager = (TransactionManager)
                                     getInitialContext().
                                     lookup(
                                             FALLBACK_TRANSACTION_MANAGER_NAMES[
                                             i]);
                if (transactionManager != null) {
                    return transactionManager;
                }
            } catch (javax.naming.NameNotFoundException ne) {
                _log.debug(
                        "JTA TransactionManager is not available at JNDI location [" +
                        FALLBACK_TRANSACTION_MANAGER_NAMES[i] + "]");
            } catch (javax.naming.NamingException ne1) {
                throw new TransactionSystemException(ne1.getMessage());
            }

        }

        return null;
    }


}
