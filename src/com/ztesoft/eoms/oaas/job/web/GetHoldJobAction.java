package com.ztesoft.eoms.oaas.job.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.job.manager.StaffJobManager;

public class GetHoldJobAction extends BaseActionAdapter {
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		String orgPathCode = request.getParameter("orgPathCode");
		int orgId = Integer.parseInt(request.getParameter("orgId").toString());
		int staffId = Integer.parseInt(request.getParameter("staffId").toString());
		
		logger.debug("orgPathCode:"+orgPathCode);
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = StaffJobManager.findHoldJobsJson(orgPathCode,orgId,staffId);
			
			logger.debug("findHoldJobsJson:"+jsonStr);
		} catch (Exception e1) {
			logger.debug(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			logger.debug(e.getMessage());
		}
		return null;
	}
}
