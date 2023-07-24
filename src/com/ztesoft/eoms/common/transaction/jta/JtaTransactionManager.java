package com.ztesoft.eoms.common.transaction.jta;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.Transaction;
import javax.transaction.HeuristicMixedException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import com.ztesoft.eoms.exception.TransactionSystemException;

/**
 * 实现事务管理器的专门用于Jta的事务管理器，抽象类，实现部分公共方法， 其他方法根据继承下的实现类进行实现。
 * 
 * @author Dawn
 * 
 */
public abstract class JtaTransactionManager implements TransactionManager {
	/* 缺省的事务名 */
	private static final String DEFAULT_USER_TRANSACTION_NAME = "java:comp/UserTransaction";

	protected static UserTransaction userTransaction = null;

	protected TransactionManager transactionManager = null;

	protected static Context context = null;

	/**
	 * 构造函数，，首先通过缺省事务名实例化事务管理器
	 * 
	 * @throws TransactionSystemException
	 */
	public JtaTransactionManager() throws TransactionSystemException {
		transactionManager = lookupTransactionManager();
	}

	/**
	 * 模板方法，供后面实现类进行调用
	 * 
	 * @return
	 * @throws TransactionSystemException
	 */
	protected abstract TransactionManager lookupTransactionManager()
			throws TransactionSystemException;

	/**
	 * 默认获取UserTransaction方法，继承这个类的其他类可以根据实际情况进行调用。
	 * 
	 * @return
	 * @throws NamingException
	 */
	public synchronized UserTransaction getUserTransaction()
			throws NamingException {
		if (userTransaction == null) {
			userTransaction = (UserTransaction) getInitialContext().lookup(
					DEFAULT_USER_TRANSACTION_NAME);
		}
		return userTransaction;
	}

	/**
	 * 默认获取InitialContext方法，继承这个类的其他类可以根据实际情况进行调用。
	 * 
	 * @return
	 * @throws NamingException
	 */
	public synchronized Context getInitialContext() throws NamingException {
		if (context == null) {
			context = new InitialContext();
		}
		return context;
	}

	/**
	 * 事务开始
	 */
	public void begin() throws NotSupportedException, SystemException {
		transactionManager.begin();
	}

	/**
	 * 事务提交
	 */
	public void commit() throws HeuristicMixedException, SecurityException,
			HeuristicRollbackException, IllegalStateException, SystemException,
			RollbackException {
		transactionManager.commit();
	}

	/**
	 * 获取事务状态
	 */
	public int getStatus() throws SystemException {
		return transactionManager.getStatus();
	}

	/**
	 * 获取事务
	 */
	public Transaction getTransaction() throws SystemException {
		return transactionManager.getTransaction();
	}
    /**
     * 事务恢复
     */
	public void resume(Transaction transaction)
			throws InvalidTransactionException, IllegalStateException,
			SystemException {
		transactionManager.resume(transaction);
	}
   /**
    * 事务回滚
    */
	public void rollback() throws SecurityException, IllegalStateException,
			SystemException {
		transactionManager.rollback();
	}
   /**
    * 设置事务只用于回滚
    */
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		transactionManager.setRollbackOnly();
	}
   /**
    * 设置事务时长
    */
	public void setTransactionTimeout(int _int) throws SystemException {
		transactionManager.setTransactionTimeout(_int);
	}
    /**
     * 事务挂起
     */
	public Transaction suspend() throws SystemException {
		return transactionManager.suspend();

	}
   /**
    * 获取事务管理器，
    * @return
    */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

}
