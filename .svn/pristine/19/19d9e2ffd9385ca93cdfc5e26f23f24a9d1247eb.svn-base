package com.ztesoft.mobile.service.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public class RequestErrorHandler extends AbstractHandler {

	public Map getResultMap() {
		Map map = super.getResultMap();
		if(map != null && !map.containsKey("response")) {
			String msg;
			try {
				msg = ZipUtil.compress("{result:002, body:{}}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				msg = "";
			}
			map.put("response", msg);
		}
		return map;
	}

	@Override
	protected void processHandle(Map paramMap) throws Exception {
		// TODO Auto-generated method stub
		//ø’ µœ÷£¨doNothing
	}

}
