package com.ztesoft.mobile.etl.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.etl.dao.DataBaseMngDAO;
import com.ztesoft.mobile.etl.dao.DataBaseMngDAOImpl;

public class DataBaseTypeExtAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List resultList = getDataBaseMngDAO().queryDataBaseTypes();
			jsonStr = JsonUtil.getJsonByList(resultList);
			System.out.println("DataBaseTypeExtAction jsonStr:" + jsonStr);
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

	public DataBaseMngDAO getDataBaseMngDAO() {
		String daoName = DataBaseMngDAOImpl.class.getName();
		return (DataBaseMngDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}