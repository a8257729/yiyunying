package com.ztesoft.eoms.common.ext.paging;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

public class ExtPagingServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionName = request.getParameter("actionName");
		System.out.println("actionName:"+actionName);

		String filePath = request.getSession().getServletContext().getRealPath("/")+ "/WEB-INF/ext.properties";

		InputStream in = new FileInputStream(filePath);
		Properties config = new Properties();
		config.load(in);

		BaseAction baseAction = null;
		try {
			System.out.println(config.getProperty(actionName).toString());
			baseAction = (BaseAction) (Class.forName(config.getProperty(actionName).toString()).newInstance());

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Object object = baseAction.doAction(request, response);

	}
}
