package com.ztesoft.android.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;
import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.JSONObject;


public class AndrServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String actionName = jsonObject.getString("actionName");   //获取跳转类名
		//String actionName = request.getParameter("actionName");
		System.out.println("actionName:"+actionName);
		String filePath = request.getSession().getServletContext().getRealPath("/")+ "/WEB-INF/extservice/andrsystem.properties";
		InputStream in = new FileInputStream(filePath);
		Properties config = new Properties();
		config.load(in);
		AndrBaseAction baseAction = null;
		try {
			System.out.println(config.getProperty(actionName).toString()+" invoke..");
			baseAction = (AndrBaseAction) (Class.forName(config.getProperty(actionName).toString()).newInstance());

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
