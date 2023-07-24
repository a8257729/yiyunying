package com.ztesoft.mobile.service.bo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 请求数据的封装对象
 * @author heison.yee
 *
 */
//@XmlRootElement(name = "Request") 
public class RequestObject implements Serializable {
	
	private HttpServletRequest request;
	private String encoding;
	private String body;
	private String params;	//请求字符串
	
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	//可以继续扩展
	
}
