package com.ztesoft.mobile.common.exception;

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
 */
public class TransactionUsageException extends TransactionException {


    /**
     * Constructor for TransactionUsageException.
     * @param msg message
     */
    public TransactionUsageException(String msg) {
        super(msg);
    }

    /**
     * Constructor for TransactionUsageException.
     * @param msg message
     * @param ex root cause from transaction API in use
     */
    public TransactionUsageException(String msg, Throwable ex) {
        super(msg, ex);
    }


}
