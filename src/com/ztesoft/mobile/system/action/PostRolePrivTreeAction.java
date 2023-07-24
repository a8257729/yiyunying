package com.ztesoft.mobile.system.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.system.service.PostPrivManager;



public class PostRolePrivTreeAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = PostPrivManager.findAllRolePrivsByPostJson(postId);
			
			System.out.println("PostRolePrivTreeAction jsonStr:"+jsonStr);
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
