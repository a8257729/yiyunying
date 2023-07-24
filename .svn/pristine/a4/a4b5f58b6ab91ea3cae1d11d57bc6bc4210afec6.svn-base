package com.ztesoft.mobile.v2.aop;

import java.util.List;

import com.ztesoft.mobile.v2.entity.interf.MobileRestService;

public class BaseRestServInterceptor {
	
	/** Æ´½ÓURLs×Ö·û´® */
	protected String urlStrings(String[] urls) {
		if(null == urls || 0 == urls.length) return "";
		
		String str = null;
		if(urls.length > 1) {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<urls.length; i++) {
				sb.append(urls[i]).append(",");
			}
			str = sb.substring(0, sb.length()-1);
		} else {
			str = urls[0];
		}
		return str;
	}
	
	protected MobileRestService matchResServ(String[] urls, List<MobileRestService> list) {
		MobileRestService serv = null;
		for(int j=0; j<urls.length; j++) {
			MobileRestService item = this.matchRestServ(urls[j], list);
			if(null != item) {
				serv = item;
				break;
			} 
		}
		return serv;
	}
	
	protected MobileRestService matchRestServ(String url, List<MobileRestService> list) {
		
		MobileRestService serv = null;
		for(int i=0; i<list.size(); i++) {
			MobileRestService item = list.get(i);
			if(url.equalsIgnoreCase(item.getServAddr())) {
				serv = item;
				break;
			}
		}
		return serv;
	}
}
