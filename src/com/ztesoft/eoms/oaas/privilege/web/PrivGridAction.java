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

public class PrivGridAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		int privClassId = Integer.parseInt((String)request.getParameter("privClassId"));
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List result = getPrivDAO().findPrivByClass(privClassId);
			logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"+result);
			jsonStr = JsonUtil.getJsonByList(result);
			
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
