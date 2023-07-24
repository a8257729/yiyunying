package com.ztesoft.mobile.service.handler;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.json.JSONException;

import net.sf.json.JSONObject;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.util.SecurityUtil;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.eoms.exintf.util.dao.UosConfigDAO;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.Base64It;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class ChangePasswordHandler extends AbstractHandler{
	
	public static final String CHANGE_SUCCESS = "000";
	public static final String OLDPASSWORD_ERR = "001";
	public static final String CHANGE_FAILED = "002";

//	public ResponseObject handle(RequestObject reqObj) throws Exception {
//		// TODO Auto-generated method stub
//		String params = reqObj.getParams();
//		JSONObject jsonObject = JSONObject.fromObject(params);
//		String oldpwd = jsonObject.getString("oldpassword");
//		String newpwd = jsonObject.getString("newpassword");
//		String username = jsonObject.getString("username");
//		String result = CHANGE_SUCCESS;
//		
//		ServiceDAO serviceDAO = getServiceDAO();
//		Map map = serviceDAO.getPasswordByUserName(username);
//		String password = (String)map.get("password");
//		if(password.startsWith("{SHA}")){
//			oldpwd = "{SHA}" + new String(Base64It.encode(oldpwd.getBytes()));
//			newpwd = "{SHA}" + new String(Base64It.encode(newpwd.getBytes()));
//		}
//		if(!oldpwd.equals(password)){
//			result = OLDPASSWORD_ERR;
//		}else{
//			//serviceDAO.updateUserPasswordByName(username, newpwd);
//		}
//		
//		ResponseObject repObj = new ResponseObject();
//		JSONObject jsonRet = new JSONObject();
//		jsonRet.put("result", result);
//		System.out.println(jsonRet.toString());
//		repObj.setResponse(ZipUtil.compress(jsonRet.toString()));
//		return repObj;
//	}
	
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}


	@Override
	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		System.out.println("传入参数??" + jsonPara);
		JSONObject jsonObject = JSONObject.fromObject(jsonPara);
		String oldpwd = jsonObject.getString("oldpassword");
		String newpwd = jsonObject.getString("newpassword");
		String username = jsonObject.getString("username");
		String result = CHANGE_SUCCESS;
		String errorDesc="";
		
		ServiceDAO serviceDAO = getServiceDAO();
		Map map = serviceDAO.getPasswordByUserName(username);
		String password = map==null?"":map.get("password")+"";
		String staffId = map==null?"-1":map.get("staffId").toString();
		String paramValue = UosConfigDAO.getInstance().getValue("PASSWORD_ENCODE");
		if("BASE64Encoder".equals(paramValue)){
			oldpwd = SecurityUtil.encrypt(staffId + oldpwd);
			newpwd = SecurityUtil.encrypt(staffId + newpwd);
		}else if(password.startsWith("{SHA}")){
			oldpwd = "{SHA}" + new String(Base64It.encode(oldpwd.getBytes()));
			newpwd = "{SHA}" + new String(Base64It.encode(newpwd.getBytes()));
		}
		if(oldpwd.equals(password)||password.equals(jsonObject.getString("oldpassword"))){
			try{
				serviceDAO.updateUserPasswordByName(username, newpwd);
			}catch(Exception e ){
				result = OLDPASSWORD_ERR;
				errorDesc="密码更新失败！";
			}
		}else{
			result = OLDPASSWORD_ERR;
			errorDesc="旧密码错误！";
		}
		
		ResponseObject repObj = new ResponseObject();
		JSONObject jsonRet = new JSONObject();
		jsonRet.put("Result", result);
		jsonRet.put("errorDesc", errorDesc);
		System.out.println(jsonRet.toString());
		result=jsonRet.toString();
		String newstr = ZipUtil.compress(result);   
		/*System.out.println("看看可以实时更新不？");   
		System.out.println("压缩后的字符串="+newstr);   
		System.out.println("压缩后的长="+newstr.length());  */
		     
		//response.setContentType("text/plain;charset=ISO-8859-1");
		//response.getWriter().write(newstr);
		paramMap.put("response", newstr);
	}
}
