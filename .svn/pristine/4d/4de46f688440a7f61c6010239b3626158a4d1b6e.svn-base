package com.ztesoft.mobile.v2.core;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.ztesoft.mobile.v2.util.CryptUtils;


public class RequestEntity {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public RequestEntity(String params) {
		super();
		this.params = CryptUtils.encryptString(params);;
	}
	
	public RequestEntity() {
		super();
	}
	
	/** 指定响应格式为JSON， 默认为JSON */
	public static final String JSON_FORMAT = "json";
	/** 指定响应格式为XML */
	//public static final String XML_FORMAT = "xml";

	/** API接口名称 */
	private String method;
	/** */
	private String session;
	/** 时间戳，格式为yyyy-MM-dd HH:mm:ss，例如：2008-01-25 20:23:30。*/
	private long timestamp;
	
	private String appKey;
	
	private String appToken;
	
	private String format;
	/** API协议版本 */
	private String version;
	
	private String params;
	
	private String accessToken;
	
	private String signMethod;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getAppKey() {
		return appKey;
	}
	
	public String getAppToken() {
		return appToken;
	}

	public void setAppToken(String appToken) {
		this.appToken = appToken;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getParams() {
		return CryptUtils.decryptString(params);
	}

	public void setParams(String params) {
		this.params = CryptUtils.encryptString(params);
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	@Override
	public String toString() {
		String str = null;
		try {
			return mapper.writeValueAsString(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public static <T extends Object> T parse(String str, Class<T> t) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(str, t);
	}
	
	public <T extends Object> T parse(Class<T> t) throws JsonParseException, JsonMappingException, IOException {
		return (T) mapper.readValue(this.getParams(), t);
	}
	
}
