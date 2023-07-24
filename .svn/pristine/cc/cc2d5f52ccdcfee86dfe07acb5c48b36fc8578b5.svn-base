package com.ztesoft.eoms.oaas.privilege.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.dao.PrivDAO;
import com.ztesoft.eoms.oaas.privilege.impl.PrivDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class PrivClassGridAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int privClassId ;
		
		privClassId = Integer.parseInt((String)request.getParameter("node"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			logger.debug("privClassId:" + privClassId);
			List result = getPrivDAO().findPrivClassesList(privClassId);
			
			jsonStr = JsonUtil.getJsonByList(result);
			
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

	private PrivDAO getPrivDAO() {
        String daoName = PrivDAOImpl.class.getName();
        return (PrivDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
