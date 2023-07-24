package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.OrgDAO;
import com.ztesoft.mobile.system.dao.OrgDAOImpl;



public class OrgTreeActionCheck implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int orgId = Integer.parseInt(request.getParameter("node"));
		
		System.out.println("OrgTreeAction orgId :"+orgId);
		Map result ;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if(orgId ==-1){
				result = getOrgDAO().getTopOrgCheck();
			}else{
				result = getOrgDAO().getSubOrgsByIdCheck(orgId);
			}
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("jsonStr:"+jsonStr);
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

	private OrgDAO getOrgDAO() {
        String daoName = OrgDAOImpl.class.getName();
        return (OrgDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
