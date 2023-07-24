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

public class QryAllSystemsAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		System.out.println("paramMap: "+paramMap); 
		//处理起始条数
	
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List retList = getOssmhMappingDAO().selAllSystems(paramMap);
			if(retList.size()!=0){
				jsonStr = JsonUtil.getBasetJsonData(retList);
			}else{
				jsonStr = "[]";
				
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
