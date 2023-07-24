package com.ztesoft.eoms.oaas.org.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.OrgPrivManager;

public class JobPrivGrantTreeAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		int _jobId = Integer.parseInt(request.getParameter("_jobId"));
		int specialJobId = Integer.parseInt(request.getParameter("specialJobId"));
		
		String methodName = request.getParameter("methodName");
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if("1".equals(methodName)){
				jsonStr = OrgPrivManager.findPrivsJobGrantJson(_jobId);
			}else if("2".equals(methodName)){
				jsonStr = OrgPrivManager.findPrivsJobGrantJson(jobId,_jobId);
			}else if("3".equals(methodName)){
				jsonStr = OrgPrivManager.findPrivsJobGrantJson(specialJobId,_jobId);
			}else if("4".equals(methodName)){
				jsonStr = OrgPrivManager.findPrivsJobGrantJson(jobId,specialJobId,_jobId);
			}else{
				logger.debug("methodName error...");
			}
			
			logger.debug("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			logger.debug(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
