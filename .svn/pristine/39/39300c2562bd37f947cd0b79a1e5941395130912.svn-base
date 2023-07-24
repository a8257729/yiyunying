package com.ztesoft.mobile.v2.util;

import com.ztesoft.mobile.v2.core.Entity;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static  String toJSONString(Object object) throws JSONException {
		String jsonStr = null;
		try {
			if (object instanceof Map) { // Map
				jsonStr = mapper.writeValueAsString((Map) object);

			} else if (object instanceof JSONObject) {	//JSONObject
				jsonStr = ((JSONObject) object).toString();
				
			} else if(object instanceof JSONArray) {	//JSONArray
				jsonStr = ((JSONArray) object).toString();
				
			} else if(object instanceof String) {	//String
				jsonStr = (String) object;
				
			} else if (object instanceof Entity) {	//Entity
				jsonStr = mapper.writeValueAsString(object);
				
			} else if (object instanceof List) {		//List
				jsonStr = mapper.writeValueAsString((List) object);
				
			}	else if (object instanceof Set) {		//Set
				jsonStr = mapper.writeValueAsString((Set) object);
				
			} else {
				throw new IllegalArgumentException("参数不正确，只支持：String、Map、List、Set、JSONObject、JSONArray和Entity的子类");
			}
		} catch(Exception ex0) {
			throw new JSONException("JSON格式转换出错");
		}
		return jsonStr;
	}
}
