package com.ztesoft.mobile.outsystem.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.outsystem.dao.SysCompanyInfoDAO;
import com.ztesoft.mobile.outsystem.dao.SysCompanyInfoDAOImpl;

public class QryAllSysCompanyAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//处理起始条数
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			List outSystemList = getSysCompanyDAO().selAll(null);
			
			if(outSystemList.size()!=0){
				jsonStr = JsonUtil.getBasetJsonData(outSystemList);
			}else{
				jsonStr = "[]";
				
			}
			System.out.println("jsonStr:----->: " + jsonStr);
			response.getWriter().write(jsonStr);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SysCompanyInfoDAO getSysCompanyDAO(){
		String daoName = SysCompanyInfoDAOImpl.class.getName();
		
		return (SysCompanyInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
