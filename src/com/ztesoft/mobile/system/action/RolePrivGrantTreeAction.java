package com.ztesoft.mobile.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.RolePrivManager;

public class RolePrivGrantTreeAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		int specialJobId = Integer.parseInt(request.getParameter("specialJobId"));
		
		String methodName = request.getParameter("methodName");
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if("1".equals(methodName)){
				jsonStr = RolePrivManager.findPrivsRoleNotHoldJson(roleId);
			}else if("2".equals(methodName)){
				jsonStr = RolePrivManager.findPrivsRoleNotHoldJson(jobId,roleId);
			}else if("3".equals(methodName)){
				jsonStr = RolePrivManager.findPrivsRoleNotHoldJson(specialJobId,roleId);
			}else if("4".equals(methodName)){
				jsonStr = RolePrivManager.findPrivsRoleNotHoldJson(jobId,specialJobId,roleId);
			}else{
				System.out.println("methodName error...");
			}
			
			System.out.println("jsonStr:"+jsonStr);
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
