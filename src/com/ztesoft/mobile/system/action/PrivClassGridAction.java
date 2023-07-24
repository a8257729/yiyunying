package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.PrivDAO;
import com.ztesoft.mobile.system.dao.PrivDAOImpl;



public class PrivClassGridAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int privClassId ;
		
		privClassId = Integer.parseInt((String)request.getParameter("node"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			System.out.println("privClassId:" + privClassId);
			List result = getPrivDAO().findPrivClassesList(privClassId);
			
			jsonStr = JsonUtil.getJsonByList(result);
			
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

	private PrivDAO getPrivDAO() {
        String daoName = PrivDAOImpl.class.getName();
        return (PrivDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
