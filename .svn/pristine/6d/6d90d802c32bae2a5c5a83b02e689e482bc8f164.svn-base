package com.ztesoft.eoms.oaas.menu.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.menu.dao.MenuDAO;
import com.ztesoft.eoms.oaas.menu.impl.MenuDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class MenuGridAction extends BaseActionAdapter { 
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
			logger.debug("MenuGridAction jsonStr:"+jsonStr);
		
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
