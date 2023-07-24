package com.ztesoft.eoms.common.transaction;


public interface TransactionSynchronization {

    /** Completion status in case of proper commit */
    int STATUS_COMMITTED = 0;

    /** Completion status in case of proper rollback */
    int STATUS_ROLLED_BACK = 1;

    /** Completion status in case of heuristic mixed completion or system errors */
    int STATUS_UNKNOWN = 2;


    /**
     * Suspend this synchronization. Supposed to unbind resources
     * from TransactionSynchronizationManager if managing any.
     * @see TransactionSynchronizationManager#unbindResource
     */
    void suspend();

    /**
     * Resume this synchronization. Supposed to rebind resources
     * to TransactionSynchronizationManager if managing any.
     * @see TransactionSynchronizationManager#bindResource
     */
    void resume();

    /**
     * Invoked before transaction commit (before "beforeCompletion").
     * Can e.g. flush transactional O/R Mapping sessions to the database.
     * <p>This callback does <i>not</i> mean that the transaction will actually
     * be committed. A rollback decision can still occur after this method has
     * been called. This callback is rather meant to perform work that's only
     * relevant if a commit still has a chance to happen, such as flushing SQL
     * statements to the database.
     * <p>Note that exceptions will get propagated to the commit caller
     * and cause a rollback of the transaction.
     * @param readOnly if the transaction is defined as read-only transaction
     * @throws RuntimeException in case of errors; will be propagated to caller
     * (note: do not throw TransactionException subclasses here!)
     * @see #beforeCompletion
     */
    void beforeCommit(boolean readOnly);

    /**
     * Invoked before transaction commit/rollback.
     * Can e.g. perform resource cleanup, <i>before</i> transaction completion.
     * <p>This method will be invoked after <code>beforeCommit</code>, even
     * if <code>beforeCommit</code> threw an exception). This callback allows
     * for closing resources before transaction completion, for any outcome.
     * @throws RuntimeException in case of errors; will be logged
     * (note: do not throw TransactionException subclasses here!)
     * @see #beforeCommit
     * @see #afterCompletion
     */
    void beforeCompletion();

    /**
     * Invoked after transaction commit/rollback.
     * Can e.g. perform resource cleanup, <i>after</i> transaction completion.
     * @param status completion status according to the STATUS_* constants
     * @throws RuntimeException in case of errors; will be logged
     * (note: do not throw TransactionException subclasses here!)
     * @see #STATUS_COMMITTED
     * @see #STATUS_ROLLED_BACK
     * @see #STATUS_UNKNOWN
     * @see #beforeCompletion
     */
    void afterCompletion(int status);

}
