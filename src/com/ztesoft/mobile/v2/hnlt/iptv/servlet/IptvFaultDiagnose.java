package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import com.google.gson.Gson;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IptvFaultDiagnose extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap();
		request.setCharacterEncoding("UTF-8");
		String ukType = request.getParameter("ukType");
		String ukValue = request.getParameter("ukValue");
		String flag = request.getParameter("flag");
		map.put("ukType", ukType);
		map.put("ukValue", ukValue);
		map.put("flag", flag);
		System.out.println("ukType=" + ukType + ",ukValue=" + ukValue + ",flag=" + flag);
		IptvInterface iptvInterface = new IptvInterface();
		List<Object> result = iptvInterface.queryDiagnose(map);
		response.setCharacterEncoding("UTF-8");
		if (result.size() > 0) {
			Gson gson = new Gson();
			String msg = gson.toJson(result);
			PrintWriter out = response.getWriter();
			out.print(msg);
			out.flush();
			out.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
