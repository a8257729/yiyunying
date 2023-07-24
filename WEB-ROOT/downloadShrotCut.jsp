<%@ page contentType="text/html; charset=utf-8"
import="java.io.File" 
import="java.io.FileInputStream" 
import="java.io.IOException"  
import="java.io.OutputStream"  
import="javax.servlet.ServletException"  
import="javax.servlet.ServletOutputStream"
import="javax.servlet.http.HttpServletRequest"  
import="javax.servlet.http.HttpServletResponse" 
%>

<%
			String ret="";
			try {
				//String filename = request.getParameter("fileName"); 
				
				/*String path = request.getContextPath(); 
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
				
				out.println(basePath);   */ 
				String path=application.getRealPath(request.getRequestURI());
			
				if (path.indexOf("MOBILE") > 0) {    
					path = path.substring(0, path.indexOf("MOBILE\\")+6)+"\\shortcut\\移动服务平台.url";    
				} 
				
				String svrFilePath=path;
				File file = new File(svrFilePath);
				//out.print(file.getName());
				response.setContentType("application/force-download");   
				response.setHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode(file.getName(), "UTF-8"));   
				response.setHeader("Pragma", "No-cache");    
				response.setHeader("Cache-Control", "no-cache");    
				OutputStream outp = response.getOutputStream();
				FileInputStream in = new FileInputStream(file);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				//		
				//in.close();
				outp.flush();
				outp.close();
				// 要加以下两句话，否则会报错
				// java.lang.IllegalStateException: getOutputStream() has
				// already been called for //this response
				//outp.clear();
				//outp = pageContext.pushBody();
				ret="true";
				
			} catch (Exception e) {
				System.out.println("Error!");
				ret="false";
				e.printStackTrace();
			} 
%>
	