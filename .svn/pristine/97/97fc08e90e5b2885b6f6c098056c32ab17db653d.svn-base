package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;

public class QryGroupStaffListByIdAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		System.out.println("paramMap: "+paramMap);
		//处理起始条数
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		paramMap.put("start", new Integer(start));
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			
			Map retMap = getImStaffBiggroupRealDAO().selGroupStaffsById(paramMap);
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				jsonStr = JsonUtil.getExtGridJsonData((List)retMap.get("resultList"),Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";
				
			}
			
			//输出返回值
			response.getWriter().write(jsonStr);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//数据访问类定义
	private ImStaffBiggroupRealDAO getImStaffBiggroupRealDAO() {
		String daoName = ImStaffBiggroupRealDAOImpl.class.getName();
		return (ImStaffBiggroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}

