package com.ztesoft.mobile.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.PostPrivManager;



public class PostPrivGrantTreeAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = PostPrivManager.findPrivsPostGrantJson(postId);
			
			System.out.println("PostPrivGrantTreeAction jsonStr:"+jsonStr);
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
