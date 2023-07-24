package com.ztesoft.eoms.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	private static String top = "{";

	private static String top_ = "}";

	public static String getJsonByList(List dList) {
		String data = "";
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}

	/***************************************************************************
	 * 具有对象标识json数据
	 * 
	 * @param params
	 * @return
	 */
	public static String getObjectJsonData(HashMap params) {
		StringBuffer data = new StringBuffer();
		int i = 0;
		if (params != null) {
			data.append(top);
			Iterator it = params.entrySet().iterator();
			while (it.hasNext()) {
				Entry element = (Entry) it.next();
				String key = (String) element.getKey();
				List dList = (ArrayList) element.getValue();
				if (i > 0)
					data.append(",");
				data.append("\"" + key + "\"" + ":");
				data.append(getJsonByList(dList));
				i++;
			}
			data.append(top_);
		}
		return data.toString();
	}

	/***************************************************************************
	 * 针对ext表json格式数据
	 * 
	 * @param dList
	 * @return
	 */
	public static String getExtGridJsonData(List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append("{\"totalCount\":" + dList.size() + ", Body:");
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}
	/***************************************************************************
	 * 针对ext表json格式数据
	 * 
	 * @param dList		 bean
	 * @param TotalCount 记录总数
	 * @return
	 */
	public static String getExtGridJsonData(List dList,int TotalCount) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			data.append("{\"totalCount\":" + TotalCount + ", Body:");
			data.append(getJsonByList(dList));
			data.append("}");
		}
		return data.toString();
	}

	/***************************************************************************
	 * 基础json数据
	 * 
	 * @param dList
	 * @return
	 */
	public static String getBasetJsonData(List dList) {
		StringBuffer data = new StringBuffer();
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data.append(ja.toString());
		}
		return data.toString();
	}
	/***************************************************************************
	 * 基础json数据
	 * 
	 * @param dList
	 * @return
	 */
	public static String getJsonData(Object o) {
		StringBuffer data = new StringBuffer();
			JSONArray ja = JSONArray.fromObject(o);
			data.append(ja.toString());
		   return data.toString();
	}
	/**
	 * @param rs
	 * @return 通过ResultSet 生成json的字符串
	 * @throws SQLException
	 */
	public static String ResultSet2Json(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			JSONArray jr = new JSONArray();
			int iColCount = rsmd.getColumnCount();
			System.out.println("iColCount  "+iColCount);
			while (rs.next()) {
				JSONObject j = new JSONObject();
				for (int i = 1; i <= iColCount; i++) {
					System.out.println("rs.getString(i)  "+rs.getString(i));
					j.put(rsmd.getColumnName(i), rs.getString(i)); // 插入数据
				}
				jr.add(j);
			}
			return jr.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "";
	}
	/**
	 * @param rs
	 * @return 通过ResultSet 生成json的字符串
	 * @throws SQLException
	 */
	public static Object json2Bean(String json,String className) {
		Object retObj =null;
		try {
			Class cla= Class.forName(className);
			JSONObject obj=JSONObject.fromObject(json);
			retObj = JSONObject.toBean(obj, cla);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return retObj;
	}
	
}
