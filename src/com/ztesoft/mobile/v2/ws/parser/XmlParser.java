package com.ztesoft.mobile.v2.ws.parser;

/**
 * 
 * @author heisonyee
 */
public interface XmlParser {
	
	/** ½âÎöÇëÇóXML×Ö·û´® */
	public String parseRequestXml(String requestXml);
	
	/** ½âÎöÏìÓ¦XML×Ö·û´® */
	public String parseResponseXml(String responseXml);

}
