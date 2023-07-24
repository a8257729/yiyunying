package com.ztesoft.eoms.oaas.menu.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.menu.dao.MenuDAO;
import com.ztesoft.eoms.oaas.menu.impl.MenuDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class ModuleTreeAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		int moduleId = Integer.parseInt(request.getParameter("node"));
		
		logger.debug("OrgTreeAction orgId :" + moduleId);
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			result = getMenuDAO().getSubMenusById(moduleId);
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
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

	private MenuDAO getMenuDAO() {
        String daoName = MenuDAOImpl.class.getName();
        return (MenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
