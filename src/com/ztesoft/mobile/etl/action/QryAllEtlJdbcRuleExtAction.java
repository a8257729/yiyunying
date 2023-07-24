package com.ztesoft.mobile.etl.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.etl.dao.EtlJdbcRuleDAO;
import com.ztesoft.mobile.etl.dao.EtlJdbcRuleDAOImpl;

public class QryAllEtlJdbcRuleExtAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		System.out.println("QryAllEtlJdbcRuleExtAction paramMap: " + paramMap);

		int limit = Integer.parseInt((String) request.getParameter("limit"));
		int start = Integer.parseInt((String) request.getParameter("start"))
				/ limit + 1;

		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map retMap = getEtlRuleDAO()
					.queryAllEtlRule(paramMap, start, limit);
			if (Integer.parseInt(retMap.get("totalSize").toString()) != 0) {
				List resList = (List) retMap.get("resultList");
				jsonStr = JsonUtil.getExtGridJsonData(resList, Integer
						.parseInt(retMap.get("totalRecords").toString()));
			} else {
				jsonStr = "{totalCount:0,Body:[]}";
			}

			System.out.println("QryAllEtlJdbcRuleExtAction jsonStr:----->: "
					+ jsonStr);

			response.getWriter().write(jsonStr);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public EtlJdbcRuleDAO getEtlRuleDAO() {
		String daoName = EtlJdbcRuleDAOImpl.class.getName();

		return (EtlJdbcRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}