package com.ztesoft.mobile.common.extservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;


public class ExtServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionName = request.getParameter("actionName");
		System.out.println("actionName:"+actionName);
		String[] actionNames = actionName.split("/");
		String packageName = actionNames[0];
		String action = actionNames[1];
		String filePath = request.getSession().getServletContext().getRealPath("/")+ "/WEB-INF/extservice/"+packageName+".properties";
		InputStream in = new FileInputStream(filePath);
		Properties config = new Properties();
		config.load(in);
		BaseAction baseAction = null;
		try {
			System.out.println(config.getProperty(action).toString()+" invoke..");
			baseAction = (BaseAction) (Class.forName(config.getProperty(action).toString()).newInstance());

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			Object object = baseAction.doAction(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
