package com.ztesoft.mobile.common.xwork;

import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.DefaultActionInvocation;
import com.opensymphony.xwork.config.entities.InterceptorMapping;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.ztesoft.mobile.common.helper.ConfigMgr;
import com.ztesoft.mobile.common.helper.NamingHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;

/**
 * xwork���ò�����
 * 
 * @author Dawn
 * 
 */
public class DedicatedActionInvocation extends DefaultActionInvocation {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 5954745784159098543L;

	private static String JNDITransactionName = null;

	private static final Logger LOG = Logger
			.getLogger(DedicatedActionInvocation.class);
	static {
		if (!ValidateHelper.validateNotNull(JNDITransactionName)) {
			JNDITransactionName = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.transaction.jndiName");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("TRANSACTION JNDI NAME =" + JNDITransactionName);
		}

	}

	/**
	 * ���캯��
	 * 
	 * @param proxy
	 *            ����Action������
	 * @param extraContext
	 *            �������
	 * @throws Exception
	 */
	protected DedicatedActionInvocation(ActionProxy proxy, Map extraContext)
			throws Exception {
		this(proxy, extraContext, true);
	}

	/**
	 * ���캯��
	 * 
	 * @param proxy
	 *            ����action������
	 * @throws Exception
	 */
	protected DedicatedActionInvocation(ActionProxy proxy) throws Exception {
		this(proxy, null);
	}

	/**
	 * ���캯�������ڸ��Ǹ��෽����ͬDedicatedActionInvocation(ActionProxy proxy, Map
	 * extraContext)
	 * 
	 * @param proxy
	 * @param extraContext
	 * @param pushAction
	 * @throws Exception
	 */
	public DedicatedActionInvocation(ActionProxy proxy, Map extraContext,
			boolean pushAction) throws Exception {
		super(proxy, extraContext, true);

	}

	/**
	 * invoke��������Action����һ�ε���
	 */
	public String invoke() throws Exception {
		if (interceptors.hasNext()) {
			InterceptorMapping interceptor = (InterceptorMapping) interceptors
					.next();
			resultCode = interceptor.getInterceptor().intercept(this);
		} else {// �����������
			String actionName = getProxy().getActionName();
			UserTransaction userTransaction = null;
			if (actionName != null
					&& !(actionName.startsWith("Sel")
							|| actionName.startsWith("sel")
							|| actionName.startsWith("Qry")
							|| actionName.startsWith("Query")
							|| actionName.startsWith("Get")
							|| actionName.startsWith("Judge")
							|| actionName.startsWith("Ico")
							|| actionName.startsWith("Cal")
							|| actionName.indexOf("query") > 0 || actionName
							.indexOf("Qry") > 0)) {

				Context context = NamingHelper.singleton().getInitialContext();
				if (LOG.isDebugEnabled()) {
					LOG.debug("Raise transaction for action=" + actionName);
				}
				userTransaction = (UserTransaction) context
						.lookup(JNDITransactionName);
			}
			if (userTransaction != null) {
				userTransaction.begin();
			}
			resultCode = invokeActionOnly();
			if (userTransaction != null) {
				try {
					userTransaction.commit();
					if (LOG.isDebugEnabled()) {
						LOG
								.debug("Commit transaction for action="
										+ actionName);
					}
				} catch (Exception e) {
					throw new Exception("transaction_COMMIT_Exception:"
							+ e.getMessage());
				} finally {
					userTransaction = null;
				}
			}
			actionName = null;
		}

		// this is needed because the result will be executed, then control will
		// return to the Interceptor, which will
		// return above and flow through again
		if (!executed) {

			if (preResultListeners != null) {
				for (Iterator iterator = preResultListeners.iterator(); iterator
						.hasNext();) {
					PreResultListener listener = (PreResultListener) iterator
							.next();
					listener.beforeResult(this, resultCode);
				}
			}

			// now execute the result, if we're supposed to
			if (proxy.getExecuteResult()) {
				executeResult();
			}

			executed = true;
		}

		return resultCode;
	}

	/**
	 * Uses getResult to get the final Result and executes it
	 */
	private void executeResult() throws Exception {
		result = createResult();

		if (result != null) {
			result.execute(this);
		} else if (!Action.NONE.equals(resultCode)) {
			LOG.warn("No result defined for action "
					+ getAction().getClass().getName() + " and result "
					+ getResultCode());
		}
	}
}
