package com.ztesoft.mobile.system.action;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.OrgTmpDAO;
import com.ztesoft.mobile.system.dao.OrgTmpDAOImpl;


public class JobTmpLevelSelAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map result = getOrgTmpDAO().findJobTmpLevelList();
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("jsonStr:"+jsonStr);
		
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private OrgTmpDAO getOrgTmpDAO() {
        String daoName = OrgTmpDAOImpl.class.getName();
        return (OrgTmpDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
