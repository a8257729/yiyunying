package com.ztesoft.mobile.common.xwork.interceptor;

import javax.transaction.UserTransaction;
import org.apache.log4j.Logger;
import com.opensymphony.xwork.ActionInvocation;
import com.ztesoft.mobile.common.helper.ConfigMgr;
import com.ztesoft.mobile.common.helper.ValidateHelper;

public class TransactionControllerInterceptor extends AbstractActonInterceptor {
	private static final long serialVersionUID = 190l;

	private UserTransaction userTransaction = null;
	

	private static String JNDITransactionName = null;

	private static final Logger _log = Logger
			.getLogger(TransactionControllerInterceptor.class);

	static {
		if (!ValidateHelper.validateNotNull(JNDITransactionName)) {
			JNDITransactionName = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.transaction.jndiName");

		}
		if (_log.isDebugEnabled()) {
			_log.debug("TRANSACTION JNDI NAME =" + JNDITransactionName);
		}

	}

	protected void before(ActionInvocation invocation) throws Exception {

		if(_log.isDebugEnabled()){
			_log.debug("Intercepter_before!");
		}
//		Context context = NamingHelper.singleton().getInitialContext();
//		userTransaction = (UserTransaction) context.lookup(JNDITransactionName);
//		if (userTransaction != null) {
//			userTransaction.begin();
//		} else {
//		}
	}

	protected void after(ActionInvocation invocation, String result)
			throws Exception {
		if(_log.isDebugEnabled()){
			_log.debug("Intercepter_after!");
		}
//		try {
//			if (userTransaction != null) {
//
//				userTransaction.commit();
//			}
//		} catch (Exception e) {
//
//		} finally {
//			userTransaction = null;
//		}
	}

	protected void handleException(Exception ex) throws Exception {

		if (_log.isDebugEnabled()) {

			_log.debug("########Transaction ROLLBACK########");
		}

		try {
			if (userTransaction != null) {
				userTransaction.rollback();
			}
		} finally {
			userTransaction = null;
		}
		throw ex;
	}

	public void init() {

	}

	protected void destory() {

		if (userTransaction != null) {
			userTransaction = null;
		}
	}

}
