package com.ztesoft.mobile.v2.entity.interf;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig.Feature;

public class MobileRequestData implements Serializable{
	public String type;
	/** 指定响应格式为JSON， 默认为JSON */
	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static final String JSON_FORMAT = "json";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public static <T extends Object> T parse(String str, Class<T> t) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(str, t);
	}
	
	

}
