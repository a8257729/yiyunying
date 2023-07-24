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
import com.ztesoft.android.dao.SystemInfoDAO;
import com.ztesoft.android.dao.SystemInfoDAOImpl;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.Base64It;

public class LogonServlet extends HttpServlet
{

	private static String LOGON_SUCCESS = "000";// 用户名???密码校验成??
	private static String USERNAME_NOT_EXIST = "005";// 用户名不存在
	private static String PASSWORD_ERROR = "006";// 密码错误
	private static String USERNAME_NULL = "007";// 用户名为??
	private static String PASSWORD_NULL = "008";// 密码为空
	private static String JSON_PARA_ERROR = "009";// 传入的JSON数据格式不正??

	private static String CONNECTION_ERROR = "010";//数据库连接异??
	private static String PNONENUMBER_ERROR = "012";//手机号码错误
	JSONObject device = new JSONObject();
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
		String resultCode = null;
		String para_username = null;
		String para_password = null;
		String phoneNumber = null;
		boolean isPhoneAutoLogin = false;
		String jsonjob = null;      //职位信息
		String versionjson = null;  //本程序apk信息
		String staffMapping = null; //人员映射信息
		String othApkInfo = null;  //业务系统的apk信息
		String othApkMethod = null;  //业务系统的apk方法信息
		String muneTabs = null; //首页菜单tab页
		String staffId=null;
		try
		{
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
			para_username = jsonObject.get("username")==null?"":jsonObject.getString("username");
			para_password = jsonObject.get("password")==null?"":jsonObject.getString("password");
			phoneNumber = jsonObject.get("serialImis")==null?"":jsonObject.getString("serialImis");
			isPhoneAutoLogin = jsonObject.get("isPhoneAutoLogin")==null?false:jsonObject.getBoolean("isPhoneAutoLogin");
			device = jsonObject.getJSONObject("device");
			
			System.out.println("para_userId "+device.toString());
		} catch (JSONException e)
		{
			resultCode = JSON_PARA_ERROR;
			e.printStackTrace();
		}
		if(isPhoneAutoLogin){
			System.out.println("isPhoneAutoLogin-->> "+isPhoneAutoLogin);
			Map map = null;
			try {
				if(phoneNumber.equals("") || phoneNumber == null){
					resultCode=PNONENUMBER_ERROR;
				}else {
					map = getServiceDAO().getPasswordByPhoneNO(phoneNumber);  //手机号自动登录
					staffId = map==null?"-1":map.get("staffId").toString();
					if(staffId.equals("-1")){
						resultCode=PNONENUMBER_ERROR;
					}else {
						device.put("staffId", staffId);
						resultCode = LOGON_SUCCESS;// 登录成功
						jsonjob = getServiceDAO().getJobData(staffId);
						staffMapping = getServiceDAO().getMappingSession(staffId);
						versionjson = getServiceDAO().getVersionNo();
						othApkInfo = getServiceDAO().getOtherApkInfo();
						othApkMethod = getServiceDAO().getOtherApkMethodInfo();
						muneTabs = getServiceDAO().getMuneTabs(staffId, new Integer(0));
					}
				}
			} catch (DataAccessException e1) {
				resultCode=CONNECTION_ERROR;
				e1.printStackTrace();
			}
		}else {
			if (para_username == null || "".equals(para_username.trim()))
			{// 用户名为??
				resultCode = USERNAME_NULL;
			} else
			{// 用户名不为空
				if (para_password == null || "".equals(para_username))
				{// 密码为空
					resultCode = PASSWORD_NULL;
				} else
				{// 密码不为??
					String password = null;
					Map map = null;
					try {
						map = getServiceDAO().getPasswordByUserName(para_username);
						password = map==null?"":map.get("password")+"";

					} catch (DataAccessException e1) {
						resultCode=CONNECTION_ERROR;
						password = null;
						e1.printStackTrace();
					}
					System.out.println("password  "+password);
					if (password == null || "".equals(password.trim()))
					{// 不存在该用户
						resultCode = USERNAME_NOT_EXIST;
					} else
					{// 存在该用户，然后校验密码
						byte[] bytes = para_password.getBytes(); 
						String oldBPassWord = "{SHA}"+new String(new Base64It().encode(bytes));
						if (password.equals(para_password)||password.equals(oldBPassWord))
						{
							staffId = map.get("staffId")+"";
							device.put("staffId", staffId);
							resultCode = LOGON_SUCCESS;// 登录成功
							try {
								jsonjob = getServiceDAO().getJobData(staffId);
								staffMapping = getServiceDAO().getMappingSession(staffId);
								versionjson = getServiceDAO().getVersionNo();
								othApkInfo = getServiceDAO().getOtherApkInfo();
								othApkMethod = getServiceDAO().getOtherApkMethodInfo();
								muneTabs = getServiceDAO().getMuneTabs(staffId, new Integer(0));
							} catch (DataAccessException e) {
								resultCode=CONNECTION_ERROR;
								e.printStackTrace();
							}
						} else
						{
							resultCode = PASSWORD_ERROR;// 密码错误
						}
					}
				}
			}
		}
		
		final String iStaffId =staffId;
		final String imsi = phoneNumber;
		if(resultCode.equals(LOGON_SUCCESS)){
/*			Thread thread = new Thread(new Runnable()
			{
				public void run()
				{
					//getSystemInfoDAO().updateStaffImsi(iStaffId, imsi);  //第一次登录，记录此人手机的imsi号到address2字段，用于下次自动登录
					//getSystemInfoDAO().insert(device);
				}

			});
			thread.start();*/
		}
		
		//String versionjson = "\"new_version\": {\"force_update\": \"0\",\"url\": \"http: //202.102.116.111: 8080/upload/XT800/IResource_XT800_2011_09_24_V1.0.1.apk\",\"engine_version\": \"1.0.2\",\"comments\": \"1.优化日程管理\n2.改登录密码\r\n3.设备查询与光路资源之间的互查\"}";
		
		JSONObject resultObject = new JSONObject();
		JSONObject bodyObject = new JSONObject();
		bodyObject.put("jobList", jsonjob);
		if(versionjson !=null){
		   bodyObject.put("new_version", versionjson);  
		}
		if(staffMapping !=null){
			bodyObject.put("staffMapping", staffMapping);			
		}
		if(othApkInfo !=null){
			bodyObject.put("othApkInfo", othApkInfo);			
		}
		if(othApkMethod !=null){
			bodyObject.put("othApkMethod", othApkMethod);			
		}
		if(muneTabs !=null){
			bodyObject.put("muneTabs", muneTabs);			
		}else{
			resultCode="1004";
		}
		
		resultObject.put("result", resultCode); 
		resultObject.put("body", bodyObject);
		
		System.out.println("原字符串="+resultObject.toString());   
	    System.out.println("原长="+resultObject.toString().length());   
	    String newstr = ZipUtil.compress(resultObject.toString());   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
		resp.setContentType("text/plain;charset=ISO-8859-1");
		resp.getWriter().write(newstr);
		
	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private SystemInfoDAO getSystemInfoDAO() {
		String daoName = SystemInfoDAOImpl.class.getName();
		return (SystemInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
