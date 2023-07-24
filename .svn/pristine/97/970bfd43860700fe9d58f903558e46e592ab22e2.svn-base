package com.ztesoft.mobile.service.handler;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class TestHandler extends AbstractHandler {


	public static void main(String args[]) throws Exception {
	
		Map paramMap = new HashMap();
		paramMap.put("path", "logonmob");
		paramMap.put("code", "logonmob");
		
		Handler handler = new  HandlerFactory().createHandler(paramMap);
		
		Map dataMap = handler.getResultMap();
		
		TestHandler th = new TestHandler();
		
		System.out.println(th.getPath());
	}

	@Override
	protected void processHandle(Map paramMap)
	{
		//super.processHandle(paramMap);
		paramMap.put("response", "{\"txt\":\"Œ“ «∑µªÿ\"}");
	}

	
}
