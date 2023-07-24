package com.ztesoft.eoms.oaas.orgTmp.web;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.orgTmp.dao.OrgTmpDAO;
import com.ztesoft.eoms.oaas.orgTmp.impl.OrgTmpDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class JobTmpLevelSelAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map result = getOrgTmpDAO().findJobTmpLevelList();
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			logger.debug("jsonStr:"+jsonStr);
		
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
