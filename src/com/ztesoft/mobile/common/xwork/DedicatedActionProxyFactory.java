package com.ztesoft.mobile.common.xwork;

import java.util.Map;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
/**
 * 获取xwork各个功能各个接口的工厂类
 * @author Dawn
 *
 */
public class DedicatedActionProxyFactory  extends ActionProxyFactory {
	public DedicatedActionProxyFactory () {
		super();
	}
 
	public ActionInvocation createActionInvocation(ActionProxy actionProxy)
			throws Exception {
		return new DedicatedActionInvocation(actionProxy);
	}

	public ActionInvocation createActionInvocation(ActionProxy actionProxy,
			Map extraContext) throws Exception {
		return new DedicatedActionInvocation(actionProxy, extraContext);
	}

	public ActionInvocation createActionInvocation(ActionProxy actionProxy,
			Map extraContext, boolean pushAction) throws Exception {
		return new DedicatedActionInvocation(actionProxy, extraContext, pushAction);
	}

	/**
	 * Use this method to build an DefaultActionProxy instance.
	 */
	public ActionProxy createActionProxy(String namespace, String actionName,
			Map extraContext) throws Exception {
		return new DedicatedActionProxy(namespace, actionName, extraContext, true,
				true);
	}

	/**
	 * Use this method to build an DefaultActionProxy instance.
	 */
	public ActionProxy createActionProxy(String namespace, String actionName,
			Map extraContext, boolean executeResult, boolean cleanupContext)
			throws Exception {
		return new DedicatedActionProxy(namespace, actionName, extraContext,
				executeResult, cleanupContext);
	}

	private static ActionProxyFactory instance = null;

	public synchronized static ActionProxyFactory  getFactory() {
		if (instance == null) {
			instance = new DedicatedActionProxyFactory();

		}
		return instance;
	}
}
