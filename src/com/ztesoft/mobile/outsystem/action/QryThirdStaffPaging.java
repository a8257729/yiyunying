package com.ztesoft.mobile.outsystem.action;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAO;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAOImpl;

public class QryThirdStaffPaging implements BaseAction {

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
			Map retMap = getOssmhMappingDAO().selThirdUserByStaffJobIdPaging(paramMap);
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				jsonStr = JsonUtil.getExtGridJsonData((List)retMap.get("resultList"),Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";
				
			}
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
	private OssmhMappingDAO getOssmhMappingDAO() {
        String daoName = OssmhMappingDAOImpl.class.getName();
        return (OssmhMappingDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
