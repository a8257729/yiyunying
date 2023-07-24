package com.ztesoft.mobile.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.OrgPrivManager;



public class JobPrivHoldTreeAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int _jobId = Integer.parseInt(request.getParameter("_jobId"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if(_jobId == 0){
				jsonStr = "[]" ;
			}else{
				jsonStr = OrgPrivManager.findPrivsByJob(_jobId);
			}
			
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
}
