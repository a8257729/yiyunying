package com.ztesoft.eoms.oaas.post.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.post.dao.PostDAO;
import com.ztesoft.eoms.oaas.post.impl.PostDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class PostCmoSelAction extends BaseActionAdapter {
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
			
			logger.debug("PostCmoSelAction jsonStr:" + jsonStr);
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

	private PostDAO getPostDAO() {
        String daoName = PostDAOImpl.class.getName();
        return (PostDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
