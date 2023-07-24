package com.ztesoft.mobile.common.xwork;

import java.util.Map;

import com.opensymphony.util.TextUtils;
import com.opensymphony.xwork.DefaultActionProxy;
/**
 * �ض���Action������
 * @author Dawn
 *
 */
public class DedicatedActionProxy extends DefaultActionProxy {

	private static final long serialVersionUID = 2749031552775871907L;

	protected DedicatedActionProxy(String namespace, String actionName,
			Map extraContext, boolean executeResult, boolean cleanupContext)
			throws Exception {

		super(namespace, actionName, extraContext, executeResult,
				cleanupContext);
	}
  /**
   * ����֮ǰ
   */
	protected void prepare() throws Exception {
		invocation = DedicatedActionProxyFactory.getFactory()
				.createActionInvocation(this, extraContext);
		resolveMethod();
	}
   /**
    * ����һ��Action
    * @return
    */
	public AbstractAction buildAction() {
		Object _obj = super.getAction();

		if (_obj instanceof AbstractAction) {

			return (AbstractAction) _obj;
		}
		throw new java.lang.IllegalAccessError(
				"The Request Services did not implements Action typing ");
	}
  /**
   * ���Ǹ���ķ������õ�action�ж����ִ�з�����
   *
   */
	private void resolveMethod() {
		// if the method is set to null, use the one from the configuration
		// if the one from the configuration is also null, use "execute"
		if (!TextUtils.stringSet(this.method)) {
			this.method = config.getMethodName();
			if (!TextUtils.stringSet(this.method)) {
				this.method = "execute";
			}
		}
	}
}
