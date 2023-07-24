package com.ztesoft.eoms.oaas.role.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.role.dao.RoleDAO;
import com.ztesoft.eoms.oaas.role.impl.RoleDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class PostRoleHoldSelAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		logger.debug("PostRoleHoldSelAction postId:"+postId);
		
		Map result ;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			result = getRoleDAO().findRolesByPost(postId);
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

	private RoleDAO getRoleDAO() {
        String daoName = RoleDAOImpl.class.getName();
        return (RoleDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
