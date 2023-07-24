package com.ztesoft.mobile.etl.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.etl.dao.TransItemDAO;
import com.ztesoft.mobile.etl.dao.TransItemDAOImpl;

public class QryTransRulesCompsListExtAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);

		System.out.println("QryTransRulesCompsListExtAction param:" + paramMap);
		// pagination info
		int limit = Integer.parseInt((String) request.getParameter("limit"));
		int start = Integer.parseInt((String) request.getParameter("start"))
				/ limit + 1;

		String jsonStr = "";

		try {
			Map retMap = getDAO().selItemsCompAsPage(paramMap, start, limit);

			if (Integer.parseInt(retMap.get("totalSize").toString()) != 0) {
				jsonStr = JsonUtil.getExtGridJsonData((List) retMap
						.get("resultList"), Integer.parseInt(retMap.get(
						"totalRecords").toString()));
			} else {
				jsonStr = "{totalCount:0,Body:[]}";
			}

			System.out.println("QryTransRulesCompsListExtAction -jsonStr:"
					+ jsonStr);
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(jsonStr);

		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private TransItemDAO getDAO() {
		String daoName = TransItemDAOImpl.class.getName();
		return (TransItemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
