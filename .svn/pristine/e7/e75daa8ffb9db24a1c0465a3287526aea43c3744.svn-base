package com.ztesoft.mobile.etl.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.etl.dao.TransConfigDAO;
import com.ztesoft.mobile.etl.dao.TransConfigDAOImpl;

public class QryMeterdataCatalogsExtAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		Long prodModelCataId = new Long(request.getParameter("node"));

		System.out.println("QryMeterdataCatalogsExtAction :" + prodModelCataId);

		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			List result = getDao().selCatalogTree(prodModelCataId.longValue());

			jsonStr = JsonUtil.getJsonByList(result);

			System.out.println("QryMeterdataCatalogsExtAction jsonStr:"
					+ jsonStr);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private TransConfigDAO getDao() {
		String daoName = TransConfigDAOImpl.class.getName();
		return (TransConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
