package com.ztesoft.eoms.common.transaction.jta;

import java.lang.reflect.Method;

import javax.transaction.TransactionManager;

import com.ztesoft.eoms.exception.TransactionSystemException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 直接从服务器类加载器中获取TransactionManager,适用于WebLogic
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author  dawn
 * @version 1.0
 */
public class WebLogicTransactionManager extends JtaTransactionManager {
   
    private static final String WEBLOGIC7_TX_HELPER_CLASS_NAME =
            "weblogic.transaction.TxHelper";
    private static final String WEBLOGIC8_TX_HELPER_CLASS_NAME =
            "weblogic.transactiom.TransactionHelper";


    private static final Log logger = LogFactory.getLog(WebLogicTransactionManager.class);
 /**
  * 构造函数
  * @throws TransactionSystemException
  */
    public WebLogicTransactionManager() throws TransactionSystemException{
        super();
    }

    /**
     * lookupTransactionManager
     *
     * @return TransactionManager
     * @throws TransactionSystemException
     * @todo Implement this
     *   com.ztesoft.eoms.common.transaction.jta.JtaTransactionManager method
     */
    protected TransactionManager lookupTransactionManager() throws
            TransactionSystemException {
        Class helperClass = null;
        TransactionManager _temp = null;
        try {
            logger.debug("Looking for WebLogic TxHelper: " +
                         WEBLOGIC7_TX_HELPER_CLASS_NAME);
            helperClass = Class.forName(WEBLOGIC7_TX_HELPER_CLASS_NAME);
            if (logger.isDebugEnabled()) {
                logger.debug("Found WebLogic TxHelper: " + helperClass.getName());
            }

        } catch (ClassNotFoundException ex) {
            try {
                logger.debug("Looking for WebLogic TxHelper: " +
                             WEBLOGIC8_TX_HELPER_CLASS_NAME);
                helperClass = Class.forName(WEBLOGIC8_TX_HELPER_CLASS_NAME);
                if (logger.isDebugEnabled()) {
                    logger.debug("Found WebLogic TxHelper: " +
                                 helperClass.getName());
                }

            } catch (ClassNotFoundException ex1) {
                throw new TransactionSystemException(
                        "Could not find WebLogic's TxHelper class", ex);

            }

        }
        try {
            Method method = helperClass.getMethod("getTransactionManager",
                                                  (Class[])null);
            _temp = (TransactionManager) method.invoke(null,
                    (Object[])null);
        } catch (Exception e) {
            throw new TransactionSystemException(
                    "Could not access WebLogic's TxHelper.getTransactionManager method",
                    e);

        }
        return _temp;
    }
}
