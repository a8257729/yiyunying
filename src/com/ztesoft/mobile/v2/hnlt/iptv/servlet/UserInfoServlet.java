package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;

/**
 * Servlet implementation class UserInfoServlet
 */
public class UserInfoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IptvInterface iptvInterface =new IptvInterface();
		
		request.setCharacterEncoding("UTF-8");
		String loginAccount = request.getParameter("loginAccount");	//IPTV账号
		String mac = request.getParameter("mac");					//MAC账号
		String command = request.getParameter("command");			//指令动作
		String epggroupnmb = request.getParameter("HD");			//高清标识
		Map map = new HashMap<String,Object>();						//用来存放返回的结果
		Map<String,Object> ParamsMap = new HashMap<String,Object>();//用来存请求的参数
		
		if("QA".equals(command)){//根据业务账号查询
			map = iptvInterface.queryAccount(loginAccount);
			if(map.size()>0){
				Gson gson = new Gson();
				String msg = gson.toJson(map);
				PrintWriter out = response.getWriter();
				out.print(msg);
				out.close();
			}
		}else if("QM".equals(command)){//根据Mac查询
			map = iptvInterface.queryMAC(mac);
			if(map.size()>0){
				Gson gson = new Gson();
				String msg = gson.toJson(map);
				PrintWriter out = response.getWriter();
				out.print(msg);
				out.close();
			}
		}else if("M".equals(command)){//根据账号修改高清标识
			ParamsMap.put("loginAccount", loginAccount);
			ParamsMap.put("epggroupnmb", epggroupnmb);
			Map<String,Object> resultMap = iptvInterface.modifyHDTypeByAccount(ParamsMap);
			Gson gson = new Gson();
			String msg = gson.toJson(resultMap);
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.close();
		}else if("R".equals(command)){//根据账号复机
			ParamsMap.put("loginAccount", loginAccount);
			ParamsMap.put("epggroupnmb", epggroupnmb);
			Map<String,Object> resultMap = iptvInterface.resetByAccount(ParamsMap);
			Gson gson = new Gson();
			String msg = gson.toJson(resultMap);
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.close();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request,response);
	}

}
