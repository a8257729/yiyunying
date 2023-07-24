package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.client.dao.CommonDAO;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.DoServiceDaoImpl;
import com.ztesoft.android.util.TimeUtil;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAO;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;

public class CommonHandler extends AbstractHandler {

	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数??" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);

		String InterfaceCode = TimeUtil.strObj(jsonObject.get("InterfaceCode"));   //这个为当前要请求的环节
		String keyId = TimeUtil.strObj(jsonObject.get("id"));               //主键ID
        String returnData = TimeUtil.strObj(jsonObject.get("returnData"));
        String orgId = TimeUtil.strObj(jsonObject.get("OrgId"));             //部门ID
        String jobId = TimeUtil.strObj(jsonObject.get("JobId"));             //当前职位
        String defaultJobId = TimeUtil.strObj(jsonObject.get("DefaultJobId")); //默认职位
        String userName = TimeUtil.strObj(jsonObject.get("UseName"));   //登录账号
        String funcName = TimeUtil.strObj(jsonObject.get("funcName"));//动作
        String body = TimeUtil.strObj(jsonObject.get("body"));   //这个里面的数据直接输入到业务系统
		
		String jsondata = "";
		System.out.println("keyId-------------->>  "+keyId);
		String json="";
		if(funcName.equals("downloadFinish")){
			String apkCode = TimeUtil.strObj(jsonObject.get("apkCode"));             //APK编码
	        String apkVer = TimeUtil.strObj(jsonObject.get("apkVer"));             //APK版本号
	        Map paramsMap=new HashMap();
	        paramsMap.put("apkCode", apkCode);
	        paramsMap.put("apkVersionNo", apkVer);
	        getApkVerManagerDAO().addDownlaodTimes(paramsMap);
		}else{
			Map m = getServiceDAO().getIntefaceInfoByMappingCode(InterfaceCode);
			json = DoServiceDaoImpl.CommonDoService("http://192.168.43.22:8080/DispatchWebService/services/DispatchWebService?wsdl", "getFaultRequstData", "232");
		}
		//jsondata = "{\"result\": \"000\"}";
		System.out.println("原字符串="+json);   
	    System.out.println("原长="+jsondata.length());   
	    String newstr = ZipUtil.compress(jsondata);   
	    System.out.println("压缩后的字符串="+newstr);   
	    System.out.println("压缩后的长="+newstr.length());  
	      
	    //response.setContentType("text/plain;charset=ISO-8859-1");
	    //response.getWriter().write(newstr);
	    //ResponseObject respObj = new ResponseObject();
	    //respObj.setResponse(newstr);
	    //return respObj;
	    paramMap.put("response", newstr);
	    
	}

	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private CommonDAO getCommonDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (CommonDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	private ApkVerManagerDAO getApkVerManagerDAO() {
		String daoName = ApkVerManagerDAOImpl.class.getName();
		return (ApkVerManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}


}
