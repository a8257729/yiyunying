package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.client.action.sqm.FixedNetworkSearchAction;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;

public class EbizToSqmHandler extends AbstractHandler {

	  protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("传入参数!!:" + jsonPara);
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String funcName=jsonObject.getString("function");
		System.out.println(">>>>>>>>>>>>>>> " + funcName);
		//后面根据funcName名做具体调用
		String outXml = "";

		try {
		    if ("GetBindInfo".equals(funcName)) {
		    	outXml = new FixedNetworkSearchAction().queryPortInfo(jsonObject);
		     }else if("GetCustomerInfo".equals(funcName)){
			    outXml = new FixedNetworkSearchAction().queryCustomerInfo(jsonObject);
		     }else if("LANModPwd".equals(funcName)){
				outXml = new FixedNetworkSearchAction().modAccPwd(jsonObject);
		     }else if("LANSetStatus".equals(funcName)){
				outXml = new FixedNetworkSearchAction().setAccState(jsonObject);
		     }else if("GetAccOnLineLog".equals(funcName)){
					outXml = new FixedNetworkSearchAction().getAccOnLineLog(jsonObject);
		     }else if("DelOnlineInfo".equals(funcName)){ 
					outXml = new FixedNetworkSearchAction().delOnlineInfo(jsonObject);
		    	 
		     }
		} catch (Exception e) {
//			jsondata = "{\"result\": \"001\",\"body\": {\"listdata\": []}}";
			e.printStackTrace();
		}    

		String jsondata = JsonUtils.getJsonStrFromXML(outXml);
		System.out.println("原字符串="+jsondata);   
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

}
