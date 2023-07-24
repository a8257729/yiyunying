/*
 * @(#)com.ztesoft.sign.SignServlet.java
 * 南京中兴软创科技有限责任公司
 * Date:  05-Jun-2011
 */

package com.ztesoft.android.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.Base64It;

public class SelJobServlet extends HttpServlet
{
	private static String SET_SUCCESS = "000";
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
		String resultCode = SET_SUCCESS;
		String staffId = null;
		String jsonjob = null;
		try
		{
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
			staffId = jsonObject.getString("staffId");
			
			System.out.println("staffId "+staffId);
		} catch (JSONException e)
		{
			resultCode = JSON_PARA_ERROR;
			e.printStackTrace();
		}
		
		  try {
			jsonjob = getServiceDAO().getJobData(staffId);
		} catch (DataAccessException e) {
			resultCode = CONNECTION_ERROR;
			e.printStackTrace();
		}
					
		
		
		
		String resultValue = "{\"result\": \""+resultCode+"\", \"body\": {\"jobList\": "+jsonjob+"}}";
		System.out.println("resultValue---> "+resultValue);
		byte[] b = resultValue.getBytes("utf-8");
		resp.setContentType("text/plain;charset=gbk");
		OutputStream out = resp.getOutputStream();
		out.write(b);
		out.flush();
		out.close();
	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
