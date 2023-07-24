package com.ztesoft.mobile.common.initialization;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.StringHelper;
import com.ztesoft.mobile.common.xwork.execution.ActionExecution;


public class StartupOpe {
	private static Log logger = LogFactory.getLog(StartupOpe.class);
 
	private ServletContext context;

	private ServletConfig config;

	public StartupOpe(ServletContext context, ServletConfig config) {
		this.context = context;
		this.config = config;

	}
	protected void initEnVariable() {
		String path = StringHelper.replaceNull(context.getRealPath("/"));
		String contextHome = StringHelper.replaceNull(context
				.getServletContextName());
		System.setProperty("start-scheduler-on-load", "false");
		setSysProp("PROJ_HOME", path);
		setSysProp("CONTEXT_HOME", contextHome);
		
	}

	private void setSysProp(String key, String value) {
		System.setProperty(key, value);

	}

	private String getSysProp(String key) {
		return System.getProperty(key);
	}
    
	protected void initXWorkActionMap() throws Exception {
		ActionExecution.singleton().initActionMap();
	}

	
	protected static void cleanDAOMap() {
		BaseDAOFactory.cleanMap();
	}
}
