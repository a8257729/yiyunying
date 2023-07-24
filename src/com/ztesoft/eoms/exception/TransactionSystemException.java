package com.ztesoft.eoms.exception;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description:事务系统级异常子类,继承事务异常,用于被实例化一个系统级异常 </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public class TransactionSystemException extends TransactionException {
    public TransactionSystemException(String msg) {
        super(msg);
    }


    public TransactionSystemException(String msg, Throwable ex) {
        super(msg, ex);
    }


}
