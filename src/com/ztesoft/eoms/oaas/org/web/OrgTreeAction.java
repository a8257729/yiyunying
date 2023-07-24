package com.ztesoft.eoms.oaas.org.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.org.dao.OrgDAO;
import com.ztesoft.eoms.oaas.org.impl.OrgDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class OrgTreeAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int orgId = Integer.parseInt(request.getParameter("node"));
		
		logger.debug("OrgTreeAction orgId :"+orgId);
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if(orgId ==-1){
				result = getOrgDAO().getTopOrg();
			}else{
				result = getOrgDAO().getSubOrgsById(orgId);
			}
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
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

	private OrgDAO getOrgDAO() {
        String daoName = OrgDAOImpl.class.getName();
        return (OrgDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
