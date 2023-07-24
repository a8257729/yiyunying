package com.ztesoft.mobile.common.extservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("==================================DownServler.doPost====================================");
		
		String fileName = req.getParameter("fileName");
		String type = req.getParameter("type");		
		String fullPath = "";
		File file = null;
		
		
		fullPath = getServletContext().getRealPath("/") + "/version/" + fileName + "." + type;				
		System.out.println("fullPath------------------------------>>  " + fullPath);
		
		file = new File(fullPath);
		if(file.exists()){
			resp.reset();
			if(type.equals("apk")){//��������Ϊapk
				resp.setContentType("application/vnd.android.package-archive");
			}
			//���ñ�����ļ���
			resp.addHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(file.getName(), "utf-8") + "\"");
			//��ȡ�ļ���С
			int fileLength = (int) file.length();
            resp.setContentLength(fileLength);
            /*����ļ����ȴ���0*/
            if (fileLength != 0) {
                /*����������*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*���������*/
                ServletOutputStream servletOS = resp.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
                inStream.close();
                servletOS.flush();
                servletOS.close();
            }
		}
		
		System.out.println("fileName----------------> " + fileName);
		System.out.println("type--------------------> " + type);
	
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("==================================DownServler====================================");
		String isDown = req.getParameter("fileName");
		if(isDown == null || isDown.equals("")){
			System.out.println("===========================down.jsp===============================");
			req.getRequestDispatcher("down.jsp").forward(req, resp);
		}else{
			System.out.println("===========================downAPK===============================");
			doPost(req, resp);
		}
	}
	
}
