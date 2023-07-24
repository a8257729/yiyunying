package com.ztesoft.android.util;

import java.text.SimpleDateFormat;  
import java.util.ArrayList;  
import java.util.Collection;  
import java.util.Date;  
import java.util.HashMap;  
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
  
import net.sf.ezmorph.MorpherRegistry;  
import net.sf.ezmorph.object.DateMorpher;  
import net.sf.json.JSONArray;  
import net.sf.json.JSONObject;  
import net.sf.json.JsonConfig;  
import net.sf.json.processors.JsonValueProcessor;  
import net.sf.json.util.JSONUtils;  
import net.sf.json.xml.XMLSerializer;  
  
public class JsonUtils {  
  
    /** 
     * ��������ת����ʽ 
     */  
    static {  
        //ע����  
//        MorpherRegistry mr = JSONUtils.getMorpherRegistry();  
//  
//        //��ת�������ڸ�ʽ����Json���п��Գ������¸�ʽ��������ʱ��  
//        DateMorpher dm = new DateMorpher(new String[] { Util.YYYY_MM_DD,  
//                Util.YYYY_MM_DD_HH_MM_ss, Util.HH_MM_ss, Util.YYYYMMDD,  
//                Util.YYYYMMDDHHMMSS, Util.HHMMss });  
//        mr.registerMorpher(dm);  
    }  
  
    /** 
    * ��json��ת����ʵ����� 
    * @param jsonObjStr e.g. {'name':'get','dateAttr':'2009-11-12'} 
    * @param clazz Person.class 
    * @return 
    */  
    public static Object getDtoFromJsonObjStr(String jsonObjStr, Class clazz) {  
        return JSONObject.toBean(JSONObject.fromObject(jsonObjStr), clazz);  
    }  
  
    /** 
    * ��json��ת����ʵ����󣬲���ʵ�弯�����Դ�������ʵ��Bean 
    * @param jsonObjStr e.g. {'data':[{'name':'get'},{'name':'set'}]} 
    * @param clazz e.g. MyBean.class 
    * @param classMap e.g. classMap.put("data", Person.class) 
    * @return Object 
    */  
    public static Object getDtoFromJsonObjStr(String jsonObjStr, Class clazz, Map classMap) {  
        return JSONObject.toBean(JSONObject.fromObject(jsonObjStr), clazz, classMap);  
    }  
  
    /** 
    * ��һ��json���鴮ת������ͨ���� 
    * @param jsonArrStr  e.g. ['get',1,true,null] 
    * @return Object[] 
    */  
    public static Object[] getArrFromJsonArrStr(String jsonArrStr) {  
        return JSONArray.fromObject(jsonArrStr).toArray();  
    }  
  
    /** 
    * ��һ��json���鴮ת����ʵ������ 
    * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}] 
    * @param clazz e.g. Person.class 
    * @return Object[] 
    */  
    public static Object[] getDtoArrFromJsonArrStr(String jsonArrStr, Class clazz) {  
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
        Object[] objArr = new Object[jsonArr.size()];  
        for (int i = 0; i < jsonArr.size(); i++) {  
            objArr[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz);  
        }  
        return objArr;  
    }  
  
    /** 
    * ��һ��json���鴮ת����ʵ�����飬������Ԫ�ص����Ժ�������ʵ��Bean 
    * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}] 
    * @param clazz e.g. MyBean.class 
    * @param classMap e.g. classMap.put("data", Person.class) 
    * @return Object[] 
    */  
    public static Object[] getDtoArrFromJsonArrStr(String jsonArrStr, Class clazz,  
            Map classMap) {  
        JSONArray array = JSONArray.fromObject(jsonArrStr);  
        Object[] obj = new Object[array.size()];  
        for (int i = 0; i < array.size(); i++) {  
            JSONObject jsonObject = array.getJSONObject(i);  
            obj[i] = JSONObject.toBean(jsonObject, clazz, classMap);  
        }  
        return obj;  
    }  
  
    /** 
    * ��һ��json���鴮ת���ɴ����ͨ����Ԫ�صļ��� 
    * @param jsonArrStr  e.g. ['get',1,true,null] 
    * @return List 
    */  
    public static List getListFromJsonArrStr(String jsonArrStr) {  
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
        List list = new ArrayList();  
        for (int i = 0; i < jsonArr.size(); i++) {  
            list.add(jsonArr.get(i));  
        }  
        return list;  
    }  
  
    /** 
    * ��һ��json���鴮ת���ɼ��ϣ��Ҽ������ŵ�Ϊʵ��Bean 
    * @param jsonArrStr e.g. [{'name':'get'},{'name':'set'}] 
    * @param clazz 
    * @return List 
    */  
    public static List getListFromJsonArrStr(String jsonArrStr, Class clazz) {  
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
        List list = new ArrayList();  
        for (int i = 0; i < jsonArr.size(); i++) {  
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));  
        }  
        return list;  
    }  
  
    /** 
    * ��һ��json���鴮ת���ɼ��ϣ��Ҽ�����Ķ�������Ժ�������ʵ��Bean 
    * @param jsonArrStr e.g. [{'data':[{'name':'get'}]},{'data':[{'name':'set'}]}] 
    * @param clazz e.g. MyBean.class 
    * @param classMap e.g. classMap.put("data", Person.class) 
    * @return List 
    */  
    public static List getListFromJsonArrStr(String jsonArrStr, Class clazz, Map classMap) {  
        JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);  
        List list = new ArrayList();  
        for (int i = 0; i < jsonArr.size(); i++) {  
            list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz, classMap));  
        }  
        return list;  
    }  
  
    /** 
    * ��json����ת����map���� 
    * @param jsonObjStr e.g. {'name':'get','int':1,'double',1.1,'null':null} 
    * @return Map 
    */  
    public static Map getMapFromJsonObjStr(String jsonObjStr) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);  
  
        Map map = new HashMap();  
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {  
            String key = (String) iter.next();  
            map.put(key, jsonObject.get(key));  
        }  
        return map;  
    }  
  
    /** 
    * ��json����ת����map������map�������ŵ�Ϊ����ʵ��Bean 
    * @param jsonObjStr e.g. {'data1':{'name':'get'},'data2':{'name':'set'}} 
    * @param clazz e.g. Person.class 
    * @return Map 
    */  
    public static Map getMapFromJsonObjStr(String jsonObjStr, Class clazz) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);  
  
        Map map = new HashMap();  
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {  
            String key = (String) iter.next();  
            map.put(key, JSONObject.toBean(jsonObject.getJSONObject(key), clazz));  
        }  
        return map;  
    }  
  
    /** 
     * ��json����ת����map������map�������ŵ�����ʵ��Bean����������ʵ��Bean 
     * @param jsonObjStr e.g. {'mybean':{'data':[{'name':'get'}]}} 
     * @param clazz e.g. MyBean.class 
     * @param classMap  e.g. classMap.put("data", Person.class) 
     * @return Map 
     */  
    public static Map getMapFromJsonObjStr(String jsonObjStr, Class clazz, Map classMap) {  
        JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);  
  
        Map map = new HashMap();  
        for (Iterator iter = jsonObject.keys(); iter.hasNext();) {  
            String key = (String) iter.next();  
            map.put(key, JSONObject  
                    .toBean(jsonObject.getJSONObject(key), clazz, classMap));  
        }  
        return map;  
    }  
  
    /** 
     * ��ʵ��Bean��Map�������顢�б���ת����Json�� 
     * @param obj  
     * @return 
     * @throws Exception String 
     */  
    public static String getJsonStr(Object obj) {  
        String jsonStr = null;  
        //Json����      
        JsonConfig jsonCfg = new JsonConfig();  
  
        //ע�����ڴ�����  
        jsonCfg.registerJsonValueProcessor(java.util.Date.class,  
                new JsonDateValueProcessor("yyyy-MM-dd hh:mm:ss"));  
        if (obj == null) {  
            return "{}";  
        }  
  
        if (obj instanceof Collection || obj instanceof Object[]) {  
            jsonStr = JSONArray.fromObject(obj, jsonCfg).toString();  
        } else {  
            jsonStr = JSONObject.fromObject(obj, jsonCfg).toString();  
        }  
  
        return jsonStr;  
    }  
  
    /** 
     * ��json�������顢����(collection map)��ʵ��Beanת����XML 
     * XMLSerializer API�� 
     * http://json-lib.sourceforge.net/apidocs/net/sf/json/xml/XMLSerializer.html 
     * ����ʵ����ο��� 
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html 
     * http://json-lib.sourceforge.net/xref-test/net/sf/json/xml/TestXMLSerializer_writes.html 
     * @param obj  
     * @return 
     * @throws Exception String 
     */  
    public static String getXMLFromObj(Object obj) {  
        XMLSerializer xmlSerial = new XMLSerializer();  
  
        //Json����      
        JsonConfig jsonCfg = new JsonConfig();  
  
        //ע�����ڴ�����  
        jsonCfg.registerJsonValueProcessor(java.util.Date.class,  
                new JsonDateValueProcessor("yyyy-MM-dd hh:mm:ss"));  
  
        if ((String.class.isInstance(obj) && String.valueOf(obj).startsWith("["))  
                || obj.getClass().isArray() || Collection.class.isInstance(obj)) {  
            JSONArray jsonArr = JSONArray.fromObject(obj, jsonCfg);  
            return xmlSerial.write(jsonArr,"utf-8");  
        } else {  
            JSONObject jsonObj = JSONObject.fromObject(obj, jsonCfg);  
            return xmlSerial.write(jsonObj,"utf-8");  
        }  
    }  
  
    /** 
     * ��XMLתjson�� 
     * @param xml 
     * @return String 
     */  
    public static String getJsonStrFromXML(String xml) {  
        XMLSerializer xmlSerial = new XMLSerializer();  
        return String.valueOf(xmlSerial.read(xml));  
    }  
  
}  
  
/** 
 * json����ֵ������ʵ��   
 * (C) 2009-9-11, jzj 
 */  
class JsonDateValueProcessor implements JsonValueProcessor {  
  
    private String format = "yyyy-MM-dd hh:mm:ss";  
  
    public JsonDateValueProcessor() {  
  
    }  
  
    public JsonDateValueProcessor(String format) {  
        this.format = format;  
    }  
  
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {  
        return process(value, jsonConfig);  
    }  
  
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
        return process(value, jsonConfig);  
    }  
  
    private Object process(Object value, JsonConfig jsonConfig) {  
        if (value instanceof Date) {  
            String str = new SimpleDateFormat(format).format((Date) value);  
            return str;  
        }  
        return value == null ? null : value.toString();  
    }  
  
    public String getFormat() {  
        return format;  
    }  
  
    public void setFormat(String format) {  
        this.format = format;  
    }  
  
}  