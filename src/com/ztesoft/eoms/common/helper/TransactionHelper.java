package com.ztesoft.eoms.common.helper;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

public class TransactionHelper {
	public static UserTransaction getTransaction() throws NamingException {
		Context context = NamingHelper.singleton().getInitialContext();
		UserTransaction userTransaction = null;
		try {
			userTransaction = (UserTransaction) context
					.lookup("java:comp/UserTransaction");
		} catch (NamingException ex) {
			System.out.println("getTransaction(java:comp/UserTransaction)´íÎó:"
					+ ex.getMessage());
			try {
				userTransaction = (UserTransaction) context
						.lookup("jta/usertransaction");
			} catch (NamingException ex2) {
				System.out.println("getTransaction(jta/usertransaction)´íÎó:"
						+ ex2.getMessage());
				throw ex2;
			}
		}
		return userTransaction;
	}
	
}
