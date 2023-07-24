package com.ztesoft.mobile.v2.hnlt.iptv.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryService;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryServiceImpl;


public class ClassNameServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		QuestionLibraryService service	= new QuestionLibraryServiceImpl();
		try {
			List list = service.selClassName();
			if(list.size()>0){
				request.getSession().setAttribute("className", list);
				request.getRequestDispatcher("report/map/problem_library.jsp").forward(request, response);
			}else{
				System.out.println("没有查到数据");
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
