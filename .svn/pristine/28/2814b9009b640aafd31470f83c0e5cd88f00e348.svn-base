package com.ztesoft.mobile.outsystem.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAO;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAOImpl;

public class QryAllSysRegionAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//处理起始条数
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			List outSystemList = getOutSystemDAO().selAllSysOrgion(null);
			addTempSysOrgion(outSystemList);
			if(outSystemList.size()!=0){
				jsonStr = JsonUtil.getBasetJsonData(outSystemList);
			}else{
				jsonStr = "[]";
				
			}
//			System.out.println("jsonStr:----->: " + jsonStr);
			response.getWriter().write(jsonStr);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public OuterSystemDAO getOutSystemDAO(){
		String daoName = OuterSystemDAOImpl.class.getName();
		
		return (OuterSystemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	/**
	 * 添加一个空的域
	 * @param outSystemList
	 */
	private void addTempSysOrgion(List outSystemList){
		Map orgMap = new HashMap();
		orgMap.put("sysRegionId", "");
		orgMap.put("sysRegionName", "-----");
		
		if(outSystemList == null) outSystemList = new ArrayList();
		
		outSystemList.add(orgMap);
	}

}
