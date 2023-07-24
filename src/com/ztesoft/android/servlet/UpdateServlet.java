/*
 * @(#)com.ztesoft.sign.SignServlet.java
 * 南京中兴软创科技有限责任公司
 * Date:  05-Jun-2011
 */

package com.ztesoft.android.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.SystemInfoDAO;
import com.ztesoft.android.dao.SystemInfoDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class UpdateServlet extends HttpServlet
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
		req.setCharacterEncoding("UTF-8");
		String jsonPara = req.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String updateType = jsonObject.get("updateType")==null?"-1":jsonObject.getString("updateType");
		String resultCode = LOGON_SUCCESS;
        if(updateType.equals("1")){
        	try {
				getSystemInfoDAO().updateStaffInfo(jsonPara);
			} catch (NumberFormatException e) {
				resultCode = CONNECTION_ERROR;
				e.printStackTrace();
			} catch (DataAccessException e) {
				resultCode = CONNECTION_ERROR;
				e.printStackTrace();
			}
        }
		
		String resultValue = "{\"result\": \""+resultCode+"\"}";
		System.out.println("resultValue---> "+resultValue);
		byte[] b = resultValue.getBytes("utf-8");
		resp.setContentType("text/plain;charset=gbk");
		OutputStream out = resp.getOutputStream();
		out.write(b);
		out.flush();
		out.close();
	}
	
	private SystemInfoDAO getSystemInfoDAO() {
		String daoName = SystemInfoDAOImpl.class.getName();
		return (SystemInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
