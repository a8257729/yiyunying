package com.ztesoft.mobile.common.exception;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company:������쳣,���ڱ����������쳣������м̳� </p>
 *
 * @author dawn
 * @version 1.0
 */
public abstract class TransactionException extends RuntimeException {
    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
