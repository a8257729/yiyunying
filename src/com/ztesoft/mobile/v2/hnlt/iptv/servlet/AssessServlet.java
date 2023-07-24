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

public class AssessServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		QuestionLibraryService service	= new QuestionLibraryServiceImpl();
		req.setCharacterEncoding("UTF-8"); 
		String title = req.getParameter("title");
		String id = req.getParameter("id");
		try {
			int i=0;
			if("zan_img".equals(id)){
				i = service.updateAssessZan(title);
			}
			if("buzan_img".equals(id)){
				i = service.updateAssessBZ(title);
			}
			
			if(i!=0){
				  List list = service.selCtxByTitle(title);
				  req.getSession().setAttribute("details", list);
				  System.out.println(i);
				
			}else{
				System.out.println("ÃÌº” ß∞‹");
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		doGet(req,resp);
		
	}

}
