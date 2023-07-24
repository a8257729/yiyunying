package com.ztesoft.mobile.service.handler;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.CommonXmlToJson;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;

public class InterfaceHandler extends AbstractHandler {
	
	protected void beforeHandle(Map paramMap) {
		super.beforeHandle(paramMap);
		
		System.out.println("接口调用之前");
		
		//在这里进行JSON转换为XML的操作
		Map ext = MapUtils.getMap(paramMap, "ext");
		String jsondata = MapUtils.getString(ext, "params");
		//paramMap.remove(ext);
		System.out.println("获取到的JSON字符串：" + jsondata);
		//将客户端的json转换成接口报文所需要的xml格式串
		String requestXml = CommonXmlToJson.json2IntefXml(paramMap, 1, jsondata);
		System.out.println("转换后的requestXml字符串：" + requestXml);
		//注意，XML必须是的key必须是requestXml
		paramMap.put("requestXml", requestXml);
	}

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		//然后必须调用指定事件beforeCallWS
		this.beforeCallWS(paramMap);

		//这里调用接口
		String requestXml = MapUtils.getString(paramMap, "requestXml");
		String responseXml = CommonXmlToJson.callWebService(paramMap, requestXml);
		
		//注意，返回的XML必须是的key必须是responseXml
		paramMap.put("responseXml", responseXml);
		
		//然后必须调用指定事件afterCallWS
		this.afterCallWS(paramMap);
	}
	
	protected void afterHandle(Map paramMap) {
		super.afterHandle(paramMap);
		
		System.out.println("接口调用之后");
		//在这里进行XML转换为JSON的操作
		String responseXml = MapUtils.getString(paramMap, "responseXml");
		String json = CommonXmlToJson.InterfXml2Json(paramMap, responseXml);
		//TODO 
		paramMap.put("response", json);
		
	}
	
}
