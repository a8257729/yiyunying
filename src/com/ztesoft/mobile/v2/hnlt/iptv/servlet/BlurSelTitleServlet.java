package com.ztesoft.mobile.v2.hnlt.iptv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryService;
import com.ztesoft.mobile.v2.hnlt.iptv.service.QuestionLibraryServiceImpl;



public class BlurSelTitleServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	QuestionLibraryService service	= new QuestionLibraryServiceImpl();
		req.setCharacterEncoding("UTF-8"); 
		String class_name = req.getParameter("class_name");
		String hot_question=req.getParameter("hot_question");
		try {
			List hotQuestion = service.selHotByTitle(hot_question, class_name);

			if(hotQuestion.size()>0){
				Gson gson = new Gson();
				String msg = gson.toJson(hotQuestion);
				resp.setCharacterEncoding("UTF-8");
				PrintWriter out = resp.getWriter();
				out.print(msg);
				out.close();
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
