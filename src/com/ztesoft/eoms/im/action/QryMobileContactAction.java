package com.ztesoft.eoms.im.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.im.dao.ImMobileContactDAO;
import com.ztesoft.eoms.im.dao.ImMobileContactDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class QryMobileContactAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List retlist = new ArrayList();//Êý¾ÝÆ´×°
			response.getWriter().write(JsonUtil.getJsonByList(retlist));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private ImMobileContactDAO getMobileContactDAO() {
		String daoName = ImMobileContactDAOImpl.class.getName();
		return (ImMobileContactDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
