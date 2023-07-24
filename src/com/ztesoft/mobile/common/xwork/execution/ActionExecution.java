package com.ztesoft.mobile.common.xwork.execution;

import java.util.Map;
import java.util.HashMap;
import java.util.Enumeration;
import com.ztesoft.mobile.common.exception.RequiredException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.providers.XmlConfigurationProvider;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.ztesoft.mobile.common.helper.ConfigMgr;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.common.xwork.DedicatedActionProxy;
import com.ztesoft.mobile.common.xwork.DedicatedActionProxyFactory;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author: ossdev
 * @version 1.0
 */
public class ActionExecution {

	/** 日志接口 */
	private static final Logger _log = Logger.getLogger(ConfigMgr.class);

	private static final ActionExecution _instance = new ActionExecution();

	public static ActionExecution singleton() {
		return _instance;
	}

	/**
	 * 初始化action的配置
	 * 
	 * @throws Exception
	 */
	public void initActionMap() throws Exception {
		try {
			// ConfigurationManager.addConfigurationProvider(new
			// DedicatedXmlConfigurationProvider("cfg/root_service.xml"));
			ConfigurationManager
					.addConfigurationProvider(new XmlConfigurationProvider(
							"cfg/root_service.xml"));
			ConfigurationManager.getConfiguration().getRuntimeConfiguration();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			ex.printStackTrace(System.err);
			throw ex;
		}
	}

	/**
	 * 执行action
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return Object
	 * @throws Exception
	 */
	public Object excuteAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServletInputStream sis = null;

		try {
			String characterEncoding = request.getCharacterEncoding();
			sis = request.getInputStream();
			ActionModel actionModel = ExecuteHelp
					.getActionModelFromInputStream(sis, characterEncoding);
			//
			MonitorActionThreadLocal.putValue(actionModel.getActionName());
			DedicatedActionContext.setSession(getSessionFromRequest(request));

			return excuteAction(actionModel);
		} finally {
			if (sis != null) {
				sis.close();
			}
		}
	}

	/**
	 * 执行action
	 * 
	 * @param actionModel
	 *            ActionModel
	 * @return Object
	 * @throws Exception
	 */
	public Object excuteAction(ActionModel actionModel) throws Exception {

		Object returnObject = null;
		DedicatedActionContext.setParams(actionModel.getParameterMap());
		ActionProxy proxy = DedicatedActionProxyFactory.getFactory()
				.createActionProxy(actionModel.getActionSpace(),
						actionModel.getActionName(),
						ActionContext.getContext().getContextMap(), true, true);

		if (proxy != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("执行:"+proxy.getActionName());
				System.out.println("执行:"+proxy.getActionName());
			}
			proxy.execute();
			returnObject = DedicatedActionContext.getResult();
			DedicatedActionContext.setResult(0);
		} else {
			throw new RequiredException("--create ActionProxy by '"
					+ actionModel.getActionPath() + "' failed");
		}

		return returnObject;
	}

	/**
	 * 执行嵌套的action
	 * 
	 * @param actionSpace
	 *            String
	 * @param actionName
	 *            String
	 * @return Map
	 * @throws Exception
	 */
	public Object excuteNestedAction(String actionSpace, String actionName,
			Map nestedParaMap) throws Exception {
		DedicatedActionContext.setParams(nestedParaMap);
		DedicatedActionProxy proxy = (DedicatedActionProxy) DedicatedActionProxyFactory
				.getFactory().createActionProxy(actionSpace, actionName,
						ActionContext.getContext().getContextMap());

		if (proxy != null) {
			proxy.buildAction().execute();
			return DedicatedActionContext.getResult();
		} else {
			throw new RequiredException("--create ActionProxy by '"
					+ actionSpace + "/" + actionName + "' failed");
		}
	}

	/**
	 * 从HttpServletRequest里获取session信息
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return Map
	 */
	private Map getSessionFromRequest(HttpServletRequest request) {
		Map sessionMap = new HashMap();
		Enumeration enume = request.getSession().getAttributeNames();

		if (enume != null) {
			String attributeName = null;
			Object attributeValue = null;

			while (enume.hasMoreElements()) {
				attributeName = (String) enume.nextElement();
				attributeValue = request.getSession().getAttribute(
						attributeName);
				sessionMap.put((Object) attributeName, attributeValue);
				if (_log.isDebugEnabled()) {
					_log.debug("session attribute: name=" + attributeName
							+ ";value=" + attributeValue);
				}
			}
		}

		return sessionMap;
	}
}