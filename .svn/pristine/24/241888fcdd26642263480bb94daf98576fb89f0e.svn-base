package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.MobLoginLogDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobLoginLogDAOImpl;




public class MobLoginLogAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		System.out.println("111111111111");
		//获取参数
		int limit = request.getParameter("limit")==null? -1:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null? -1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		System.out.println("22222222");
		Map result ;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			//查询自己的联系人
			Map qryMap = new HashMap();
			qryMap.put("staffId", request.getParameter("staffId"));

			result = getMobLoginLogDAO().selMobileLog(qryMap, start, limit);
			
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
	
	
	private MobLoginLogDAO getMobLoginLogDAO() {
		String daoName = MobLoginLogDAOImpl.class.getName();
		return (MobLoginLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
