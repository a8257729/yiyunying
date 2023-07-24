package com.ztesoft.mobile.etl.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.etl.dao.CleanRuleConfigDAO;
import com.ztesoft.mobile.etl.dao.CleanRuleConfigDAOImpl;

/**
 * description : 规则列别组合框查询
 * @author FL
 *
 */
public class RoleTypeSelAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map result = getCleanRuleConfigDAO().qryRuleTypeList();
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("RoleTypeSelAction:"+jsonStr);
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

	private CleanRuleConfigDAO getCleanRuleConfigDAO() {
        String daoName = CleanRuleConfigDAOImpl.class.getName();
        return (CleanRuleConfigDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
