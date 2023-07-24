package com.ztesoft.mobile.service.handler;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.SystemInfoDAO;
import com.ztesoft.android.dao.SystemInfoDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class UpdateHandler extends AbstractHandler {
	
	private static String LOGON_SUCCESS = "000";// 用户名???密码校验成??
	private static String USERNAME_NOT_EXIST = "005";// 用户名不存在
	private static String JSON_PARA_ERROR = "009";// 传入的JSON数据格式不正??
	private static String CONNECTION_ERROR = "010";//数据库连接异??
	

	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
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
		
		//ResponseObject resObj = new ResponseObject();
		//resObj.setResponse(resultValue);
		
		//return resObj;
		paramMap.put("response", resultValue);

	}
	
	private SystemInfoDAO getSystemInfoDAO() {
		String daoName = SystemInfoDAOImpl.class.getName();
		return (SystemInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
