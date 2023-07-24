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
import com.ztesoft.mobile.common.extservice.BaseAction;

public class OrgTmpCmoSelAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		int orgTmpId = Integer.parseInt(request.getParameter("orgTmpId"));
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if(orgTmpId == 0){
				result = getOrgDAO().getTopOrgTmpTree() ;
			}else{
				result = getOrgDAO().getSubOrgTmpTreeById(orgTmpId) ;
			}
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("AreaCmoSelAction jsonStr:"+jsonStr);
		} catch (Exception e1) {
			//logger.debug(e1.getMessage());
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
