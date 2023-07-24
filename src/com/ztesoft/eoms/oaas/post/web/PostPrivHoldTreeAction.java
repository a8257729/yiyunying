package com.ztesoft.eoms.oaas.post.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.PostPrivManager;

public class PostPrivHoldTreeAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			if(postId == 0){
				jsonStr = "[]" ;
			}else{
				jsonStr = PostPrivManager.findPrivsByPostJson(postId);
			}
			
			logger.debug("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			logger.debug(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
