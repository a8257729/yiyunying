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
import com.ztesoft.mobile.v2.hnlt.iptv.vo.QuestionLibrary;



public class QuestionLibraryServlet extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	QuestionLibraryService service	= new QuestionLibraryServiceImpl();
		req.setCharacterEncoding("UTF-8");
		String  staffId = req.getParameter("staffId");
	
		try {
			
			//将用户信息保存到session
			List user = service.selInfoByStaffId(staffId);
			if(user.size()>0){
				req.getSession().setAttribute("user", user);
			}
			
			//将问题库信息保存到list跳转到问题库页面
			List list = service.selAll();
			if(list.size()>0){
				req.getSession().setAttribute("questionLibrary", list);
	
				req.getRequestDispatcher("report/map/question_index.jsp").forward(req, resp);
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
		// TODO Auto-generated method stub
		doGet(req,resp);
	}

	
}
