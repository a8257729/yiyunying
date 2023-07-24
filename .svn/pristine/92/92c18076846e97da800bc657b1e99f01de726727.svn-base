package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.PostDAO;
import com.ztesoft.mobile.system.dao.PostDAOImpl;



public class PostCmoSelAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		int orgTmpId = Integer.parseInt(request.getParameter("orgTmpId"));
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			result = getPostDAO().findByOrgTmp(orgTmpId) ;
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("PostCmoSelAction jsonStr:" + jsonStr);
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

	private PostDAO getPostDAO() {
        String daoName = PostDAOImpl.class.getName();
        return (PostDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
