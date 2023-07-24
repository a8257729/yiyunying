package com.ztesoft.mobile.common.helper;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ParamUtils {
	public Map getMapByRequest(HttpServletRequest request){
		Enumeration names = request.getParameterNames();
		Map retMap = new HashMap();
		while(names.hasMoreElements()){
			String name = names.nextElement().toString();
			retMap.put(name, request.getParameter(name));
		}
		
		return retMap ;
	}
}
