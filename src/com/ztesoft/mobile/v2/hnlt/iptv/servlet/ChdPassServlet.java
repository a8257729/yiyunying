package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;


public class ChdPassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Map<String, Object> map = new HashMap();
		    request.setCharacterEncoding("UTF-8");
		    String logicdevno = request.getParameter("logicdevno");
		    String chd_pass = request.getParameter("chd_pass");
		    String old_chd_pass = request.getParameter("old_chd_pass");
		    String command = request.getParameter("command");
		    map.put("logicdevno", logicdevno);
		    map.put("chd_pass", chd_pass);
		    map.put("old_chd_pass", old_chd_pass);
		    map.put("command", command);
		    IptvInterface iptvInterface = new IptvInterface();
		    Map<String,Object> result = iptvInterface.ChdPass(map);
		    response.setCharacterEncoding("UTF-8");
		    if (result.size() > 0)
		    {
		      Gson gson = new Gson();
		      String resultInfo = gson.toJson(result);
		      PrintWriter out = response.getWriter();
		      out.print(resultInfo);
		      out.flush();
		      out.close();
		    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
