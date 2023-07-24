package com.ztesoft.eoms.common.transaction;

/**
 *定义默认的事务管理器状态
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

public class DefaultTransactionStatus {

    private final Object transaction;

    private final boolean newTransaction;

    private final boolean newSynchronization;

    private final boolean readOnly;

    private final boolean debug;

    private final Object suspendedResources;


    public DefaultTransactionStatus(
            Object transaction, boolean newTransaction,
            boolean newSynchronization,
            boolean readOnly, boolean debug, Object suspendedResources) {

        this.transaction = transaction;
        this.newTransaction = newTransaction;
        this.newSynchronization = newSynchronization;
        this.readOnly = readOnly;
        this.debug = debug;
        this.suspendedResources = suspendedResources;
    }

    /**
     * Return the underlying transaction object.
     */
    public Object getTransaction() {
        return transaction;
    }

    /**
     * Return whether there is an actual transaction active.
     */
    public boolean hasTransaction() {
        return (this.transaction != null);
    }

    public boolean isNewTransaction() {
        return (hasTransaction() && this.newTransaction);
    }

    /**
     * Return if a new transaction synchronization has been opened
     * for this transaction.
     */
    public boolean isNewSynchronization() {
        return newSynchronization;
    }

    /**
     * Return if this transaction is defined as read-only transaction.
     */
    public boolean isReadOnly() {
        return readOnly;
    }

    /**
     * Return whether the progress of this transaction is debugged. This is used
     * by AbstractPlatformTransactionManager as an optimization, to prevent repeated
     * calls to logger.isDebug(). Not really intended for client code.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Return the holder for resources that have been suspended for this transaction,
     * if any.
     */
    public Object getSuspendedResources() {
        return suspendedResources;
    }
}
