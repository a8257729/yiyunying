package com.ztesoft.mobile.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.StaffJobManager;



public class GetJobOrgAction implements BaseAction {
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		String orgPathCode = request.getParameter("orgPathCode");
		int orgId = Integer.parseInt(request.getParameter("orgId").toString());
		
		System.out.println("orgPathCode:"+orgPathCode);
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = StaffJobManager.findJobsNotHoldJson(orgPathCode,orgId);
			
			System.out.println("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
