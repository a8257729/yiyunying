package com.ztesoft.eoms.oaas.role.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.RolePrivManager;

public class RolePrivGrantTreeAction extends BaseActionAdapter {
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
