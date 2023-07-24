package com.ztesoft.mobile.service.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.client.action.ResourcePhotoUploadAction;
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.XmlToJsonUtils;
import com.ztesoft.android.util.ZipUtil;

/**
 * 回单
 * @author heison.yee
 *
 */
public class ReturnWorkformHandler extends AbstractHandler {
	
	private static final String WS_NAMESPACE = "";
	private static final String WS_METHOD_OPERATION_NAME = "pfServicesForEBiz";
	
	@Override
	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");	

		//调用接口服务
		EBizToIomWebservice service = new EBizToIomWebservice();
		//返回对象
		//ResponseObject respObj = new ResponseObject();
		
		//request.setCharacterEncoding("UTF-8");	//处理乱码用
		//String jsonPara = reqObj.getParams();
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("调用【ReturnWorkformHandler】，传入参数：" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		System.out.println("调用【ReturnWorkformHandler】，生成的JSON数据：" + jsonObject.toString());
		
		//StringBuilder responseXmlStr = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		String responseXmlStr = "";
		String newstr = "";
		JSONObject jsonObj = new JSONObject();
		String jsondata = null; 
		String xmlStr = null;
		try{
			
			////////////////////////////
			// add by guo.jinjun at 2012年4月12日
			String photoName = ResourcePhotoUploadAction.uploadPic(jsonObject, (HttpServletRequest)MapUtils.getObject(paramMap, "httpRequest"));
			jsonObject.put("photo", photoName);
			
			//String wsRequestStr = jsonObject.getString("WSRequest");
			//wsRequestStr = URLDecoder.decode(wsRequestStr, "UTF-8");
			String methodStr = jsonObject.getString("QueryMethod");
			//String paramsStr = jsonObject.getString("Params");
			jsonObject.remove("QueryMethod");
			jsonObject.remove("actionName");
			//JSONObject paramsObject = JSONObject.fromObject(paramsStr);
			System.out.println("调用【ReturnWorkformHandler】，生成的Params的数据：" + jsonObject.toString());
			
			Map map = new HashMap();
			map.put("Params", jsonObject);
			map.put("QueryMethod", methodStr);
			
			JSONObject newObj = JSONObject.fromObject(map);
			
			XMLSerializer serl = new XMLSerializer();
			serl.setTypeHintsEnabled(false);
			serl.setObjectName("Data");
			//(1)弱智做法：String requestXmlStr = serl.write(newObj, "GBK").replace("encoding=\"GBK\"", "encoding=\"UTF-8\"");
			//别扭的处理编码问题，无语
			//String requestXmlStr = new String(serl.write(newObj, "UTF-8").getBytes("GBK"), "UTF-8");	
			StringBuffer requestXmlBuff = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			requestXmlBuff.append("<Data>")
											.append("<Params>")
													.append("<Comments>").append(jsonObject.optString("Comments","")).append("</Comments>")
													.append("<FinishTime>").append(jsonObject.optString("FinishTime","")).append("</FinishTime>")
													.append("<LoginPhoneNo>").append(jsonObject.optString("LoginPhoneNo","")).append("</LoginPhoneNo>")
													.append("<WorkOrderID>").append(jsonObject.optString("WorkOrderID","")).append("</WorkOrderID>")
													.append("<nextStateCode>").append(jsonObject.optString("nextStateCode","")).append("</nextStateCode>")
													.append("<UseName>").append(jsonObject.optString("UseName","")).append("</UseName>")
													.append("<photo>").append(jsonObject.optString("photo","")).append("</photo>")
													.append("<reasonClassCode>").append(jsonObject.optString("reasonClassCode","")).append("</reasonClassCode>")
													.append("<reasonClassName>").append(jsonObject.optString("reasonClassName","")).append("</reasonClassName>")
													.append("<reasonCode>").append(jsonObject.optString("reasonCode","")).append("</reasonCode>")
													.append("<reasonName>").append(jsonObject.optString("reasonName","")).append("</reasonName>")
													.append("<reasonResultCode>").append(jsonObject.optString("reasonResultCode","")).append("</reasonResultCode>")
													.append("<reasonResultName>").append(jsonObject.optString("reasonResultName","")).append("</reasonResultName>")
													.append("<resCode>").append(jsonObject.optString("resCode","")).append("</resCode>")
											.append("</Params>")
									.append("</Data>");
			
			String requestXmlStr = requestXmlBuff.toString();
			String[] paraName = {"infType","requestxml"};
			//
			System.out.println("调用【ReturnWorkformHandler】，XML字符串: " + requestXmlStr);		
			
			paramMap.put("requestXml", requestXmlStr);
			this.beforeCallWS(paramMap);	//调用接口之前			
		
			if("finishWorkOrderForEBiz".equals(methodStr)) {	//服务开通回单接口
			
				/*StringBuilder responseXmlStrx = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
					responseXmlStrx.append("<Data>")
									.append("<WorkOrderID>TEST001</WorkOrderID>")
										.append("<Return>")
											.append("<Result>000</Result>")
											.append("<ErrorDesc>回单成功</ErrorDesc>")
										.append("</Return>")
									.append("</Data>");
				responseXmlStr = responseXmlStrx.toString(); */
			
				try{
					responseXmlStr = service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE, WS_METHOD_OPERATION_NAME, paraName,  new String[]{methodStr, requestXmlStr});
				}catch(Exception ex){
					//获取接口调用异常
					System.out.println("接口调用异常：" + ex.getMessage());
					throw new Exception("WebService接口调用异常");
				}
				newstr = ZipUtil.compress(this.formatResponseJSONForFinish(responseXmlStr.toString()).toString());
			} 
			System.out.println("接口返回XML报文："+ responseXmlStr);
			
			paramMap.put("responseXml", responseXmlStr);
			this.afterCallWS(paramMap);		//调用接口之后
			
		}catch(Exception ex) {
			newstr = "{\"result\": \"001\"}";
		}finally{
			//repObj.setResponse(newstr);
			paramMap.put("response", newstr);
		}
	}
		
	private JSONObject formatResponseJSONForFinish(String xmlStr) {
		
		JSONObject responseJSON = new JSONObject();
		XMLSerializer serl = new XMLSerializer();
		try{
			//将返回的XML格式的数据转换成JSON格式
			JSONObject rawResponseObj = JSONObject.fromObject(serl.read(xmlStr.toString()).toString());
			JSONObject returnObj = rawResponseObj.getJSONObject("Return");
			responseJSON.put("result", returnObj.optString("Result","001"));
			responseJSON.put("errorDesc", returnObj.optString("ErrorDesc","回单失败"));
			
		}catch(Exception e){
			e.printStackTrace();
			responseJSON.put("result", "001");
		}
		
		System.out.println("Response的JSON报文是：" + responseJSON.toString());
		
		return responseJSON;
	}
}
