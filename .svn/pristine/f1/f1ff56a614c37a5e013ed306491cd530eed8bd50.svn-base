package com.ztesoft.mobile.service.handler;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.service.CommonXmlToJson;

public class DirectInterfaceHandler extends AbstractHandler {
	
	protected void beforeHandle(Map paramMap) {
		super.beforeHandle(paramMap);
		
		/*
		System.out.println("接口调用之前");
		
		String jsondata = MapUtils.getString(paramMap, "params");
		System.out.println("获取到的JSON字符串：" + jsondata);
		//根据规则，在原有的json字符串外面再嵌套一层
		JSONObject rootObj = new JSONObject();
		rootObj.put("method", MapUtils.getString(paramMap, "intefaceMethod"));
		rootObj.put("content", jsondata);
		System.out.println("转换后后的JSON字符串：" + rootObj.toString());
		paramMap.put("requestXml", rootObj.toString());
		*/
	}

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		//然后必须调用指定事件beforeCallWS
		//this.beforeCallWS(paramMap);

		//这里调用接口
		//String requestXml = MapUtils.getString(paramMap, "requestXml");
		String jsondata = MapUtils.getString(paramMap, "params");
		String responseXml = CommonXmlToJson.callWebService(paramMap, jsondata);
		
		//注意，返回的json字符串必须是的key必须是responseJson
		paramMap.put("responseXml", responseXml);
		
		//然后必须调用指定事件afterCallWS
		this.afterCallWS(paramMap);
	}
	
	protected void afterHandle(Map paramMap) {
		super.afterHandle(paramMap);
		
		System.out.println("接口调用之后");
		String responseXml = MapUtils.getString(paramMap, "responseXml");

		paramMap.put("response", responseXml);
		
	}
	
}
