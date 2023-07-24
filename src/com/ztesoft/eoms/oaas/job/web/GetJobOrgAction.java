package com.ztesoft.eoms.oaas.job.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.job.manager.StaffJobManager;

public class GetJobOrgAction extends BaseActionAdapter {
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//��ȡ����
		String orgPathCode = request.getParameter("orgPathCode");
		int orgId = Integer.parseInt(request.getParameter("orgId").toString());
		
		logger.debug("orgPathCode:"+orgPathCode);
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = StaffJobManager.findJobsNotHoldJson(orgPathCode,orgId);
			
			logger.debug("jsonStr:"+jsonStr);
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
