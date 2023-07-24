package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.MobileActionTypeDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobileActionTypeDAOImpl;

public class SelMobileActionTypeAction implements BaseAction{

	public Object doAction(HttpServletRequest request, HttpServletResponse response) {
		//获取参数
		String typeName = request.getParameter("typeName")==null?"0":request.getParameter("typeName"); 
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();

			param.put("typeName", typeName);
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			
			Map paginResultMap = getMobileActionTypeDAO().selById(param);
			
			//列表数据
			List resultList = (List) paginResultMap.get("resultList");
			int totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");
			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
			}
			System.out.println("====================="+jsonStr+"===========================");
			response.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private MobileActionTypeDAO getMobileActionTypeDAO(){
		String daoName = MobileActionTypeDAOImpl.class.getName();
		return (MobileActionTypeDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	
}