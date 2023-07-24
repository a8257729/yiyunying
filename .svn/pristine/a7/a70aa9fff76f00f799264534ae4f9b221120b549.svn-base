package com.ztesoft.eoms.oaas.org.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.dao.PrivDAO;
import com.ztesoft.eoms.oaas.privilege.impl.PrivDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class JobPrivTreeGridAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int parentId = Integer.parseInt((String)request.getParameter("node"));
		int jobId = Integer.parseInt((String)request.getParameter("jobId"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List result = getPrivDAO().findJobPrivTreeList(parentId,jobId);
			
			jsonStr = JsonUtil.getJsonByList(result);
			
			logger.debug("jsonStr:"+jsonStr);
		
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private PrivDAO getPrivDAO() {
        String daoName = PrivDAOImpl.class.getName();
        return (PrivDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
