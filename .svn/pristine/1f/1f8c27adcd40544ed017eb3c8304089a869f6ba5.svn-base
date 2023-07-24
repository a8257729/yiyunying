package com.ztesoft.eoms.common.transaction.jta;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import com.ztesoft.eoms.common.helper.ValidateHelper;


public class UserTransactionAdapter implements UserTransaction {

    private final TransactionManager transactionManager;


    /**
     * Create a new UserTransactionAdapter.
     * @param transactionManager the JTA TransactionManager
     */
    public UserTransactionAdapter(TransactionManager transactionManager) {
        ValidateHelper.notNull(transactionManager,
                               "TransactionManager is required");
        this.transactionManager = transactionManager;
    }

    /**
     * Return the JTA TransactionManager that this adapter delegates to.
     */
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }


    public void begin() throws NotSupportedException, SystemException {
        this.transactionManager.begin();
    }

    public void commit() throws RollbackException, HeuristicMixedException,
            HeuristicRollbackException,
            SecurityException, IllegalStateException, SystemException {
        this.transactionManager.commit();
    }

    public int getStatus() throws SystemException {
        return this.transactionManager.getStatus();
    }

    public void rollback() throws IllegalStateException, SecurityException,
            SystemException {
        this.transactionManager.rollback();
    }

    public void setRollbackOnly() throws IllegalStateException, SystemException {
        this.transactionManager.setRollbackOnly();
    }

    public void setTransactionTimeout(int timeout) throws SystemException {
        this.transactionManager.setTransactionTimeout(timeout);
    }

}
