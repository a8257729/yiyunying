package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.StaffSelDAO;
import com.ztesoft.mobile.system.dao.StaffSelDAOImpl;



public class StaffPagingForPushMsgAction implements BaseAction {
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
				result = getOrgStaffs().getOrgForTree(qryType,orgId,orgPathCode, staffName, userName, officeTel, mobileTel, start, limit);
			} else{
				result = getOrgStaffs().getOrgStaffForTree(qryType,orgId,orgPathCode, staffName, userName, officeTel, mobileTel, start, limit);
			}
			
			List dlist = (List)result.get("resultList");
			List nlist = new ArrayList();
			if(dlist.size()>0){
				Map mapFunc = new HashMap();
				mapFunc.put("id", -10);
				mapFunc.put("text", "qryStaff".equals(qryType)?"人员列表":"组织列表");
				mapFunc.put("leaf", false);
				mapFunc.put("checked",false);
				mapFunc.put("children", (List)result.get("resultList"));
				nlist.add(mapFunc);
			}
			jsonStr = JsonUtil.getJsonByList(nlist);
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
