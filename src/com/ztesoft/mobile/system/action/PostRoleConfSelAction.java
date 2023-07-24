package com.ztesoft.mobile.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.RoleDAO;
import com.ztesoft.mobile.system.dao.RoleDAOImpl;


public class PostRoleConfSelAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		int areaId = Integer.parseInt(request.getParameter("areaId"));
		Map result ;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			System.out.println("postId:"+postId);
			System.out.println("areaId:"+areaId);
			
			result = getRoleDAO().getPostRolesInArea(postId, areaId);
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("jsonStr:"+jsonStr);
		
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private RoleDAO getRoleDAO() {
        String daoName = RoleDAOImpl.class.getName();
        return (RoleDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
