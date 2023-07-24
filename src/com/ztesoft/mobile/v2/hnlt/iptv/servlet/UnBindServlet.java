package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import com.google.gson.Gson;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UnBindServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(UnBindServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String mac = req.getParameter("mac");
		String cityId = req.getParameter("areano");
		String servaccount = req.getParameter("loginAccount");
		String orgId = req.getParameter("orgId");
		String userName = req.getParameter("userName");
		
		IptvInterface iptvInterface  = new IptvInterface();
		Map inMap = new HashMap();
		inMap.put("mac", mac);
		inMap.put("cityId", cityId);
		inMap.put("servaccount", servaccount);

		//添加网格id和操作员id
		inMap.put("grid", orgId);
		inMap.put("opertor", userName);

		try {
			Map UnbindMap = iptvInterface.unbandIPTVSTB(inMap);
			if(UnbindMap.size()>0){
				Gson gson = new Gson();
				String msg = gson.toJson(UnbindMap);
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.print(msg);
				out.close();
			/*req.getSession().removeAttribute("bindMap");
			req.getSession().setAttribute("UnbindMap", UnbindMap);*/
			}	
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		doGet(req, resp);
	}
	
}
