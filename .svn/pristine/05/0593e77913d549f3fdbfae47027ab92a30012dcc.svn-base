package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.MenuDAO;
import com.ztesoft.mobile.system.dao.MenuDAOImpl;
public class ModuleTreeAction implements BaseAction {
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		int moduleId = Integer.parseInt(request.getParameter("node"));
		
		System.out.println("OrgTreeAction orgId :" + moduleId);
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			result = getMenuDAO().getSubMenusById(moduleId);
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
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

	private MenuDAO getMenuDAO() {
        String daoName = MenuDAOImpl.class.getName();
        return (MenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
