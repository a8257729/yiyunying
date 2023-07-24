package com.ztesoft.eoms.oaas.privilege.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.StaffPrivManager;

public class PrivConfSelAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		int specialJobId = Integer.parseInt(request.getParameter("specialJobId"));
		int _jobId = Integer.parseInt(request.getParameter("_jobId"));
		int _defaultJobId = Integer.parseInt(request.getParameter("_defaultJobId"));
		String methodName = request.getParameter("methodName");
		String jsonStr = "";
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			logger.debug("PrivConfSelAction excute........");
			logger.debug("jobId:"+jobId);
			logger.debug("specialJobId:"+specialJobId);
			logger.debug("_jobId:"+_jobId);
			logger.debug("_defaultJobId:"+_defaultJobId);
			logger.debug("methodName:"+methodName);
			
			if("1".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(_jobId, _defaultJobId);
			}else if("2".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(jobId, _jobId, _defaultJobId,false );
			}else if("3".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(specialJobId, _jobId, _defaultJobId,false );
			}else{
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(jobId, specialJobId, _jobId, _defaultJobId,false );
			}
			logger.debug("PrivConfSelAction jsonStr:"+jsonStr);
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
