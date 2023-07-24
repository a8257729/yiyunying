package com.ztesoft.eoms.oaas.org.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.OrgPrivManager;

public class JobPrivHoldSelAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		logger.debug("PrivHoldSelAction excute.............");
		logger.debug("jobId:"+jobId);
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			//jsonStr = OrgPrivManager.findPrivsJobHoldJson(jobId);
			
			logger.debug("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			logger.error("",e1);
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
