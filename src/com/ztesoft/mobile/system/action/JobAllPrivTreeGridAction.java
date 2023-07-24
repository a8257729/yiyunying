package com.ztesoft.mobile.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.dao.PrivDAO;
import com.ztesoft.mobile.system.dao.PrivDAOImpl;
import com.ztesoft.mobile.system.service.OrgPrivManager;



public class JobAllPrivTreeGridAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if(jobId == 0){
				jsonStr = "[]" ;
			}else{
				jsonStr = OrgPrivManager.findAllPrivsByJob(jobId);
			}
			
			System.out.println("jsonStr:"+jsonStr);
		
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private PrivDAO getPrivDAO() {
        String daoName = PrivDAOImpl.class.getName();
        return (PrivDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
