package com.ztesoft.mobile.v2.ws.parser;

/**
 * 
 * @author heisonyee
 */
public interface XmlParser {
	
	/** ��������XML�ַ��� */
	public String parseRequestXml(String requestXml);
	
	/** ������ӦXML�ַ��� */
	public String parseResponseXml(String responseXml);

}
