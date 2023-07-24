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
 * ʵ�������������ר������Jta������������������࣬ʵ�ֲ��ֹ��������� �����������ݼ̳��µ�ʵ�������ʵ�֡�
 * 
 * @author Dawn
 * 
 */
public abstract class JtaTransactionManager implements TransactionManager {
	/* ȱʡ�������� */
	private static final String DEFAULT_USER_TRANSACTION_NAME = "java:comp/UserTransaction";

	protected static UserTransaction userTransaction = null;

	protected TransactionManager transactionManager = null;

	protected static Context context = null;

	/**
	 * ���캯����������ͨ��ȱʡ������ʵ�������������
	 * 
	 * @throws TransactionSystemException
	 */
	public JtaTransactionManager() throws TransactionSystemException {
		transactionManager = lookupTransactionManager();
	}

	/**
	 * ģ�巽����������ʵ������е���
	 * 
	 * @return
	 * @throws TransactionSystemException
	 */
	protected abstract TransactionManager lookupTransactionManager()
			throws TransactionSystemException;

	/**
	 * Ĭ�ϻ�ȡUserTransaction�������̳���������������Ը���ʵ��������е��á�
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
	 * Ĭ�ϻ�ȡInitialContext�������̳���������������Ը���ʵ��������е��á�
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
	 * ����ʼ
	 */
	public void begin() throws NotSupportedException, SystemException {
		transactionManager.begin();
	}

	/**
	 * �����ύ
	 */
	public void commit() throws HeuristicMixedException, SecurityException,
			HeuristicRollbackException, IllegalStateException, SystemException,
			RollbackException {
		transactionManager.commit();
	}

	/**
	 * ��ȡ����״̬
	 */
	public int getStatus() throws SystemException {
		return transactionManager.getStatus();
	}

	/**
	 * ��ȡ����
	 */
	public Transaction getTransaction() throws SystemException {
		return transactionManager.getTransaction();
	}
    /**
     * ����ָ�
     */
	public void resume(Transaction transaction)
			throws InvalidTransactionException, IllegalStateException,
			SystemException {
		transactionManager.resume(transaction);
	}
   /**
    * ����ع�
    */
	public void rollback() throws SecurityException, IllegalStateException,
			SystemException {
		transactionManager.rollback();
	}
   /**
    * ��������ֻ���ڻع�
    */
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		transactionManager.setRollbackOnly();
	}
   /**
    * ��������ʱ��
    */
	public void setTransactionTimeout(int _int) throws SystemException {
		transactionManager.setTransactionTimeout(_int);
	}
    /**
     * �������
     */
	public Transaction suspend() throws SystemException {
		return transactionManager.suspend();

	}
   /**
    * ��ȡ�����������
    * @return
    */
	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

}
