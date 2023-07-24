package com.ztesoft.android.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.CeshiDataDAO;
import com.ztesoft.android.dao.CeshiDataDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class CeshiDataServlet extends HttpServlet
{

	private static String LOGON_SUCCESS = "000";// 用户名???密码校验成??
	private static String USERNAME_NOT_EXIST = "005";// 用户名不存在
	private static String JSON_PARA_ERROR = "009";// 传入的JSON数据格式不正??
	private static String CONNECTION_ERROR = "010";//数据库连接异??

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
//		req.setCharacterEncoding("UTF-8");
		String jsonPara = req.getParameter("params");
//		//jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		//JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		//String type = jsonObject.get("type")==null?"-1":jsonObject.getString("type");
		String resultCode = "000";
		String returnData = "";
       // if(type.equals("1")){
        	try {
        		returnData = getCeshiDataDAO().getData(jsonPara);
			} catch (NumberFormatException e) {
				resultCode="001";
				e.printStackTrace();
			} catch (DataAccessException e) {
				e.printStackTrace();
			} 
       // }
		
		String resultValue = "{\"result\": \""+resultCode+"\",listdata:"+returnData+"}";
		System.out.println("resultValue---> "+resultValue);
		byte[] b = resultValue.getBytes("utf-8");
		resp.setContentType("text/plain;charset=gbk");
		OutputStream out = resp.getOutputStream();
		out.write(b);
		out.flush();
		out.close();
	}
	
	private CeshiDataDAO getCeshiDataDAO() {
		String daoName = CeshiDataDAOImpl.class.getName();
		return (CeshiDataDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
