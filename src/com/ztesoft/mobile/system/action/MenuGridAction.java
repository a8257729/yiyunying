package com.ztesoft.mobile.system.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.MenuDAO;
import com.ztesoft.mobile.system.dao.MenuDAOImpl;



public class MenuGridAction implements BaseAction { 
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int moduleId = Integer.parseInt((String)request.getParameter("moduleId"));
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map result = getMenuDAO().getMenuListByModule(moduleId) ;
			int totalSize = Integer.parseInt(result.get("totalSize")+"");
			
			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(result.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData((List)result.get("resultList"),totalRecords);
			}
			System.out.println("MenuGridAction jsonStr:"+jsonStr);
		
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private MenuDAO getMenuDAO() {
        String daoName = MenuDAOImpl.class.getName();
        return (MenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
