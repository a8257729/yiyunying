package com.ztesoft.mobile.common.xwork;

import java.util.Map;

import com.opensymphony.util.TextUtils;
import com.opensymphony.xwork.DefaultActionProxy;
/**
 * 特定的Action代理类
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
   * 调用之前
   */
	protected void prepare() throws Exception {
		invocation = DedicatedActionProxyFactory.getFactory()
				.createActionInvocation(this, extraContext);
		resolveMethod();
	}
   /**
    * 构建一个Action
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
   * 覆盖父类的方法，得到action中定义的执行方法名
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
