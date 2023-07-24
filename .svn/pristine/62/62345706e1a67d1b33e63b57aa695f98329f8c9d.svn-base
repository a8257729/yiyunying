package com.ztesoft.mobile.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.StaffPrivManager;



public class PrivConfSelAction implements BaseAction {
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
			System.out.println("PrivConfSelAction excute........");
			System.out.println("jobId:"+jobId);
			System.out.println("specialJobId:"+specialJobId);
			System.out.println("_jobId:"+_jobId);
			System.out.println("_defaultJobId:"+_defaultJobId);
			System.out.println("methodName:"+methodName);
			
			if("1".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(_jobId, _defaultJobId);
			}else if("2".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(jobId, _jobId, _defaultJobId,false );
			}else if("3".equals(methodName.trim())){
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(specialJobId, _jobId, _defaultJobId,false );
			}else{
				jsonStr = StaffPrivManager.findPrivsJobNotHoldJson(jobId, specialJobId, _jobId, _defaultJobId,false );
			}
			System.out.println("PrivConfSelAction jsonStr:"+jsonStr);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
