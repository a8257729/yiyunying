package com.ztesoft.mobile.service.handler;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.service.CommonXmlToJson;

public class DirectInterfaceHandler extends AbstractHandler {
	
	protected void beforeHandle(Map paramMap) {
		super.beforeHandle(paramMap);
		
		/*
		System.out.println("�ӿڵ���֮ǰ");
		
		String jsondata = MapUtils.getString(paramMap, "params");
		System.out.println("��ȡ����JSON�ַ�����" + jsondata);
		//���ݹ�����ԭ�е�json�ַ���������Ƕ��һ��
		JSONObject rootObj = new JSONObject();
		rootObj.put("method", MapUtils.getString(paramMap, "intefaceMethod"));
		rootObj.put("content", jsondata);
		System.out.println("ת������JSON�ַ�����" + rootObj.toString());
		paramMap.put("requestXml", rootObj.toString());
		*/
	}

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		//Ȼ��������ָ���¼�beforeCallWS
		//this.beforeCallWS(paramMap);

		//������ýӿ�
		//String requestXml = MapUtils.getString(paramMap, "requestXml");
		String jsondata = MapUtils.getString(paramMap, "params");
		String responseXml = CommonXmlToJson.callWebService(paramMap, jsondata);
		
		//ע�⣬���ص�json�ַ��������ǵ�key������responseJson
		paramMap.put("responseXml", responseXml);
		
		//Ȼ��������ָ���¼�afterCallWS
		this.afterCallWS(paramMap);
	}
	
	protected void afterHandle(Map paramMap) {
		super.afterHandle(paramMap);
		
		System.out.println("�ӿڵ���֮��");
		String responseXml = MapUtils.getString(paramMap, "responseXml");

		paramMap.put("response", responseXml);
		
	}
	
}
