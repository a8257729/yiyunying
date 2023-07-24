package com.ztesoft.mobile.outsystem.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAO;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAOImpl;

public class QryOuterSystemAction implements BaseAction{

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		//System.out.println("QryOuterSystemAction paramMap: "+paramMap);
		//处理起始条数
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		
		//System.out.println("QryOuterSystemAction limit:----->: " + limit);
		
		//System.out.println("QryOuterSystemAction start:----->: " + start);
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			Map retMap = getOutSystemDAO().queryOuterSystem(paramMap,start,limit);
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				jsonStr = JsonUtil.getExtGridJsonData((List)retMap.get("resultList"),Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";	
			}
			
			System.out.println("QryOuterSystemAction jsonStr:----->: " + jsonStr);
			
			response.getWriter().write(jsonStr);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
				
		return null;
	}
	
	public OuterSystemDAO getOutSystemDAO(){
		String daoName = OuterSystemDAOImpl.class.getName();
		
		return (OuterSystemDAO) BaseDAOFactory.getImplDAO(daoName);
	}


}
