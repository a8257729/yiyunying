package com.ztesoft.eoms.oaas.role.web;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.RolePrivManager;

public class RolePrivListAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		logger.debug("RolePrivListAction excute.............");
		logger.debug("areaId:"+roleId);
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = RolePrivManager.findPrivsByRoleJson(roleId);
			
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
