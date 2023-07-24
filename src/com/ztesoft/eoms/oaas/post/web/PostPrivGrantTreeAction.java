package com.ztesoft.eoms.oaas.post.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.privilege.manager.PostPrivManager;

public class PostPrivGrantTreeAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int postId = Integer.parseInt(request.getParameter("postId"));
		
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			jsonStr = PostPrivManager.findPrivsPostGrantJson(postId);
			
			logger.debug("PostPrivGrantTreeAction jsonStr:"+jsonStr);
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
