package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

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
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class LogonHandler extends AbstractHandler {

    public static final Map<String ,Integer> osMapping = new HashMap<String, Integer>();
    static {
        osMapping.put("android", new Integer(0));
        osMapping.put("ios", new Integer(1));
        osMapping.put("wp", new Integer(2));
    }
	
	private static String LOGON_SUCCESS = "000";// 用户名???密码校验成??
	private static String USERNAME_NOT_EXIST = "005";// 用户名不存在
	private static String PASSWORD_ERROR = "006";// 密码错误
	private static String USERNAME_NULL = "007";// 用户名为??
	private static String PASSWORD_NULL = "008";// 密码为空
	private static String JSON_PARA_ERROR = "009";// 传入的JSON数据格式不正??

	private static String CONNECTION_ERROR = "010";//数据库连接异??
	private static String PNONENUMBER_ERROR = "012";//手机号码错误
	private static String AUTOLOGIN_ERROR = "2003";//自动登录状态跟服务器状态不一致,请用账号登录
	private static String IMSI_ERROR = "2004";//IMSI码错误,请重新绑定
	
	JSONObject device = new JSONObject();
	
	  protected void processHandle(Map paramMap) throws Exception {
		//Map tmpMap = MapUtils.getMap(paramMap, "ext");R
		String params = MapUtils.getString(paramMap, "params");
		String os = MapUtils.getString(paramMap, "os", null);
		//
		System.out.println("客户端OS：" + os);
		String jsonPara = URLDecoder.decode(params, "UTF-8");
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
		boolean isAuto = false;
		try
		{
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
			para_username = jsonObject.optString("username", "");
			para_password = jsonObject.optString("password", "");
			//注: iOS平台上获取IMIS号的方法是私有的，不允许使用
			phoneNumber =  jsonObject.optString("serialImis","");
			isPhoneAutoLogin = jsonObject.optBoolean("isPhoneAutoLogin", false);
			device = jsonObject.optJSONObject("device");
			//
			//System.out.println("para_userId "+device.toString());
		} catch (JSONException e)
		{
			resultCode = JSON_PARA_ERROR;
			e.printStackTrace();
		}
		if(isPhoneAutoLogin){
		
			Map map = null;
			try {
				if(StringUtils.isBlank(phoneNumber)){
					resultCode=PNONENUMBER_ERROR;
				}else {
					map = getServiceDAO().getPasswordByPhoneNO(phoneNumber);  //手机号自动登录					
					if(map==null){
						resultCode=IMSI_ERROR;
					}else {
						staffId = map.get("staffId").toString();
						int agent = map.get("agent")==null?0:1;
						isAuto = agent==0?false:true;
						if(isPhoneAutoLogin == isAuto){  //如果客户端的自动登录状态跟服务端不一致，则不允许自动登录		
							device.put("staffId", staffId);
							resultCode = LOGON_SUCCESS;// 登录成功
							jsonjob = getServiceDAO().getJobData(staffId);
							staffMapping = getServiceDAO().getMappingSession(staffId);
							versionjson = getServiceDAO().getVersionNo();
							othApkInfo = getServiceDAO().getOtherApkInfo();
							othApkMethod = getServiceDAO().getOtherApkMethodInfo();
							muneTabs = getServiceDAO().getMuneTabs(staffId, osMapping.get(os));
						}else {
						  resultCode=AUTOLOGIN_ERROR;
						}
					}
				}
			} catch (DataAccessException e1) {
				resultCode=CONNECTION_ERROR;
				e1.printStackTrace();
			}
		}else {
			if (StringUtils.isBlank(para_username))
			{// 用户名为??
				resultCode = USERNAME_NULL;
			} else
			{// 用户名不为空
				if (StringUtils.isBlank(para_password)) {// 密码为空
					resultCode = PASSWORD_NULL;
				} else {// 密码不为??
					String password = null;
					Map map = null;
					try {
						map = getServiceDAO().getPasswordByUserName(para_username);
						password = MapUtils.getString(map, "password");

					} catch (DataAccessException e1) {
						resultCode=CONNECTION_ERROR;
						password = null;
						e1.printStackTrace();
					}
					System.out.println("password  "+password);
					if (StringUtils.isBlank(password)) {// 不存在该用户
						resultCode = USERNAME_NOT_EXIST;
					} else { // 存在该用户，然后校验密码
						byte[] bytes = para_password.getBytes(); 
						String oldBPassWord = "{SHA}"+new String(new Base64It().encode(bytes));
						if (password.equals(para_password)||password.equals(oldBPassWord)) {
							staffId = map.get("staffId")+"";
							if(!"ios".equalsIgnoreCase(os)) {
								device.put("staffId", staffId);
							}
							resultCode = LOGON_SUCCESS;
							// 登录成功
							try {
								jsonjob = getServiceDAO().getJobData(staffId);
								staffMapping = getServiceDAO().getMappingSession(staffId);
								versionjson = getServiceDAO().getVersionNo();
								othApkInfo = getServiceDAO().getOtherApkInfo();
								othApkMethod = getServiceDAO().getOtherApkMethodInfo();
								muneTabs = getServiceDAO().getMuneTabs(staffId, osMapping.get(os));
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

		JSONObject resultObject = new JSONObject();
		JSONObject bodyObject = new JSONObject();
		bodyObject.put("jobList", jsonjob);
		
		if(!"ios".equalsIgnoreCase(os) ){		//android
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

		} else  {	//ios
			//TODO 获取还有其他的
		}
		
		if(muneTabs !=null){
			bodyObject.put("muneTabs", muneTabs);			
		}	

		//else{
		//	resultCode="1004";
		//}
		
		resultObject.put("result", resultCode); 
		resultObject.put("body", bodyObject);
		
		System.out.println("原字符串="+resultObject.toString());   
	    String newstr = ZipUtil.compress(resultObject.toString());   
	    paramMap.put("response", newstr);
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
