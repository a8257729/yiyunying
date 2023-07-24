package com.ztesoft.eoms.oaas.staff.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.oaas.staff.dao.StaffSelDAO;
import com.ztesoft.eoms.oaas.staff.impl.StaffSelDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class StaffPagingAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		int orgId = Integer.parseInt((String)request.getParameter("orgId")); 
		String qryType = request.getParameter("qryType");
		String orgPathCode = request.getParameter("orgPathCode");
		String staffName = request.getParameter("staffName");
		String userName = request.getParameter("userName");
		String officeTel = request.getParameter("officeTel");
		String mobileTel = request.getParameter("mobileTel");
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		
		Map result ;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if("qrySub".equals(qryType) || "qrySelf".equals(qryType)){
				result = getOrgStaffs().getOrgStaff(qryType,orgId,orgPathCode, staffName, userName, officeTel, mobileTel, start, limit);
			}else {
				result = getOrgStaffs().getNoJobStaff(staffName, userName, officeTel, mobileTel, start, limit);
			}
			
			int totalSize = Integer.parseInt(result.get("totalSize")+"");
			
			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(result.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData((List)result.get("resultList"),totalRecords);
			}
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

	private StaffSelDAO getOrgStaffs() {
        String daoName = StaffSelDAOImpl.class.getName();
        return (StaffSelDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
