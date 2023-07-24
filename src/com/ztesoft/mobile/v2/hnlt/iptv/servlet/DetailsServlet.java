package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryService;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryServiceImpl;



public class DetailsServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	QuestionLibraryService service	= new QuestionLibraryServiceImpl();
		req.setCharacterEncoding("UTF-8"); 
		String search_value = req.getParameter("search_value");
		try {
			List list = service.selCtxByTitle(search_value);
			
			if(list.size()>0){
				req.getSession().setAttribute("details", list);
				req.getRequestDispatcher("report/map/details.jsp").forward(req, resp);
			}else{
				System.out.println("没有查到数据");
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}   

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req,resp);
	}

	
}
