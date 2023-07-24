package com.ztesoft.android.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.helper.JsonUtil;

public class ParserToList {

	public static String JsonToJsonList(String json,String keyName,String formName,String nowpage,String displayType,
			List<Map> fileList, String butPriJson,List<Map> fileNodeList){

		String jsonSearchFileds = JsonUtil.getJsonByList(fileList);
		String fileNodeStr = JsonUtil.getJsonByList(fileNodeList);
	
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
        String result = holder.getString("result");  
        String bodyresult = "000";  //业务系统结果码
        String errordesc = "";  //业务系统查询异常信息
        JSONObject jsondata = new JSONObject();
        if(body.has("result")){
        	bodyresult = body.getString("result");  //业务系统结果码
        }
        if (body.has("errordesc")){
        	errordesc = body.getString("errordesc");  //业务系统查询异常信息
        }
        //根据指定节点，把业务数据节点重新封装	 如listdata,listdata1,listdata2......
        for(Map nodeMap : fileNodeList){
        	String nodeName = nodeMap.get("nodeName").toString();
        	if (body.has(nodeName))
        	{
        		jsondata.put(nodeName, bulitList(body,fileList,keyName,nodeName));
        	}
        }

		jsondata.put("bodyresult", bodyresult);             //业务系统返回结果码
		jsondata.put("errordesc", errordesc);
		
		if(body.has("totalpage")){
	        	jsondata.put("totalpage",  body.getString("totalpage"));
	    }
		if(body.has("totalnum")){
        	jsondata.put("totalnum",  body.getString("totalnum"));
        }
		if(body.has("pagesize")){
        	jsondata.put("pagesize",  body.getString("pagesize"));
        }
		if(body.has("pagenum")){
        	jsondata.put("pagenum",  body.getString("pagenum"));
        }
	
		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);             //平台返回结果码 
		objresult.put("formName", formName);         //页面显示名称
		objresult.put("nowpage", nowpage);           //请求页面
		objresult.put("displayType", displayType);  //页面显示类型
		objresult.put("buttons", butObject);        //按钮权限 
		objresult.put("body", jsondata);           //主内容
	
		objresult.put("keyName", keyName);    //主键名称
		objresult.put("searchFileds", jsonSearchFileds);  //查询条件字段
		objresult.put("nodes", fileNodeStr);      //节点集合
	
		return objresult.toString();
	}
	public static String JsonToJsonDetail(String json,String keyName,String formName,String nowpage,
			String displayType,List<Map> fileList, String butPriJson,String fileNodeList,String tabs){

		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
        String result = holder.getString("result");  
        String bodyresult = "000";  //业务系统结果码
        String errordesc = "";  //业务系统查询异常信息
        if(body.has("result")){
        	bodyresult = body.getString("result");  //业务系统结果码
        }
        if (body.has("errordesc")){
        	errordesc = body.getString("errordesc");  //业务系统查询异常信息
        }
        
        JSONArray njsonArray = new JSONArray();
		JSONArray njsonArray1 = new JSONArray();
		JSONArray njsonArray2 = new JSONArray();
		JSONArray njsonArray3 = new JSONArray();
		JSONArray njsonArray4 = new JSONArray();
		JSONArray njsonArray5 = new JSONArray();
		JSONArray njsonArray6 = new JSONArray();
		JSONArray njsonArray7 = new JSONArray();
		
		JSONObject jsondata = new JSONObject();
		if (body.has("listdata"))
		{		
			njsonArray = bulitDetail(njsonArray,body,fileList,keyName,displayType,"listdata");//njsonArray返回对象		
		}
		if (body.has("listdata1"))
		{
			njsonArray1 = bulitDetail(njsonArray1,body,fileList,keyName,displayType,"listdata1");
		}
		if (body.has("listdata2"))
		{
			njsonArray2 = bulitDetail(njsonArray2,body,fileList,keyName,displayType,"listdata2");
		}
		if (body.has("listdata3"))
		{		
			njsonArray3 = bulitDetail(njsonArray3,body,fileList,keyName,displayType,"listdata3");
		}
		if (body.has("listdata4"))
		{		
			njsonArray4 = bulitDetail(njsonArray4,body,fileList,keyName,displayType,"listdata4");
		}
		if (body.has("listdata5"))
		{		
			njsonArray5 = bulitDetail(njsonArray5,body,fileList,keyName,displayType,"listdata5");
		}
		if (body.has("listdata6"))
		{		
			njsonArray6 = bulitDetail(njsonArray6,body,fileList,keyName,displayType,"listdata6");
		}
		if (body.has("listdata7"))
		{		
			njsonArray7 = bulitDetail(njsonArray7,body,fileList,keyName,displayType,"listdata7");
		}
		if(njsonArray.size()>0){
		  jsondata.put("listdata", njsonArray);
		}
		if(njsonArray1.size()>0){
			jsondata.put("listdata1", njsonArray1);
		}
		if(njsonArray2.size()>0){
			jsondata.put("listdata2", njsonArray2);		
		}
		if(njsonArray3.size()>0){
			jsondata.put("listdata3", njsonArray3);	
        }
		if(njsonArray4.size()>0){
			jsondata.put("listdata4", njsonArray4);	
        }
		if(njsonArray5.size()>0){
			jsondata.put("listdata5", njsonArray5);	
        }
		if(njsonArray6.size()>0){
			jsondata.put("listdata6", njsonArray6);	
        }
		if(njsonArray7.size()>0){
			jsondata.put("listdata7", njsonArray7);	
        }

		jsondata.put("bodyresult", bodyresult);             //业务系统返回结果码
		jsondata.put("errordesc", errordesc);
		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", jsondata);
		objresult.put("nodes", fileNodeList);
		
		
		return objresult.toString();//rtJsonDetailData(result,formName,nowpage,butPriJson,njsonArray.toString());
	}
	//获取节点下对应的字段值
	public static JSONArray  bulitList(JSONObject body,List<Map> fileList,String keyName,String nodeName){
		JSONArray njsonArray = new JSONArray();
		try {
			JSONArray jsonArray = body.getJSONArray(nodeName);
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject njsonObject = new JSONObject();
				njsonObject.put("id", jsonObject.get(keyName));
				for(Map m:fileList){
					if(m.containsValue(nodeName)){  //判断是否有此节点
						if(jsonObject.get(m.get("filedName"))==null){
							njsonObject.put(m.get("filedName"), "");
						}else {
							njsonObject.put(m.get("filedName"), jsonObject.get(m.get("filedName")));
						}
					}
				}
				njsonArray.add(njsonObject);
			}
		} catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
			try {
				JSONObject jsonObject = body.getJSONObject(nodeName);	
				JSONObject njsonObject = new JSONObject();
				njsonObject.put("id", jsonObject.get(keyName));
				for(Map m:fileList){
					if(m.containsValue(nodeName)){  //判断是否有此节点
						if(jsonObject.get(m.get("filedName"))==null){
							njsonObject.put(m.get("filedName"), "");
						}else {
							njsonObject.put(m.get("filedName"), jsonObject.get(m.get("filedName")));
						}
					}
				}
				njsonArray.add(njsonObject);
			} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
				System.out.println("非法的JSON字符串");
			}
		}
		return njsonArray;
	}
	//获取节点下对应的字段值，明细数据
	public static JSONArray  bulitDetail(JSONArray njsonArray,JSONObject body,List<Map> fileList,String keyName,String displayType,String nodeName){
		try {			
			
			JSONArray jsonArray = body.getJSONArray(nodeName);		
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				for(Map m: fileList){
					if(m.containsValue(nodeName)){  //判断是否有此节点
						JSONObject njsonObject = bulitDetailData(jsonObject,m,keyName,displayType);
						njsonArray.add(njsonObject);
					}
				}
			}
		} catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
			try {
				JSONObject jsonObject = body.getJSONObject(nodeName);	
				for(Map m: fileList){
					if(m.containsValue(nodeName)){
						JSONObject njsonObject = bulitDetailData(jsonObject,m,keyName,displayType);
						njsonArray.add(njsonObject);
					}
				}
			} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
				System.out.println("非法的JSON字符串");
			}
		}			
		return njsonArray;
	}
	public static JSONObject  bulitDetailData(JSONObject jsonObject,Map m,String keyName,String displayType){
		JSONObject njsonObject = new JSONObject();
		njsonObject.put("id", jsonObject.has(keyName)?jsonObject.get(keyName):"-1");	                 //主键
		njsonObject.put("name", m.get("filedName"));
		njsonObject.put("type", m.get("filedType"));                    //显示字段类型
		njsonObject.put("value", jsonObject.has(m.get("filedName")+"")?jsonObject.get(m.get("filedName")):"");   //字段名
		njsonObject.put("label", m.get("filedLable"));                  //中文名

		njsonObject.put("pass", m.get("isPassValue"));                  //是否传值
		njsonObject.put("display", m.get("isDisplay"));                 //是否显示    
		njsonObject.put("displayType", m.get("displayType")==null?"-1":m.get("displayType"));                    //事件的显示类型  
		
		njsonObject.put("position", m.get("position"));
		njsonObject.put("defaultValue", m.get("defaultValue")==null?"":m.get("defaultValue"));
		njsonObject.put("actionCode", m.get("actionCode")==null?"":m.get("actionCode"));
		njsonObject.put("showLable", m.get("showLable")==null?"1":m.get("showLable"));
		njsonObject.put("selectData", m.get("selectData")==null?"":m.get("selectData"));
		njsonObject.put("checkedData", m.get("checkedData")==null?"":m.get("checkedData"));
		njsonObject.put("attrCode", m.get("attrCode")==null?"":m.get("attrCode"));
		njsonObject.put("isSearchField", m.get("isSearchField")==null?"":m.get("isSearchField")); 
		njsonObject.put("actionEvent", m.get("actionEvent")==null?"":m.get("actionEvent")); 
		
		System.out.println("njsonObject "+njsonObject.toString());
		return njsonObject;
	}
	public static String JsonToJsonForm(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson){

		String jsonFileds = JsonUtil.getJsonByList(fileList);
		JSONObject jsondata = new JSONObject();
		jsondata.put("listdata", jsonFileds);
		
		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		
		JSONObject objresult = new JSONObject();
		objresult.put("result", "000");
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", jsondata);
		
		return objresult.toString();//rtJsonDetailData("000",formName,nowpage,butPriJson,njsonArray.toString());
	}
	public static String JsonToJsonListTree(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson,List<Map> fileNodeList){
	
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
		String result = holder.getString("result");  
		JSONArray njsonArray = new JSONArray();
		JSONArray subArray = new JSONArray();
		JSONObject ajsonObject = new JSONObject();
		JSONObject tJsonObject  = new JSONObject();
		int num = 0;
	
		for(int j=0;j<fileNodeList.size();j++){
			Map fMap= (Map)fileNodeList.get(j);
			if(fMap.get("isLeaf").equals("1")){
				ajsonObject.put(fMap.get("nodeName"), body.get(fMap.get("nodeName").toString()));
				ajsonObject.put("value"+j, fMap.get("nodeName"));
				
			}
			
		}
		subArray.add(ajsonObject);
		for(int j=0;j<fileNodeList.size();j++){
			Map fMap= (Map)fileNodeList.get(j);
			if (body.has(fMap.get("nodeName").toString()) && fMap.get("isLeaf").equals("2"))
			{
                 
				JSONArray jsonArray = body.getJSONArray(fMap.get("nodeName").toString());
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);

					JSONObject njsonObject = new JSONObject();
					num = fileList.size();
					for(int k=0;k<fileList.size();k++){
						Map m = (Map)fileList.get(k);
						System.out.println("比较--> "+jsonObject.get(m.get("filedName")) + " ---- " +m.get("filedName"));
						njsonObject.put(m.get("filedName"), jsonObject.get(m.get("filedName")));	
						njsonObject.put("value"+k, m.get("filedName"));	                 //主键
						tJsonObject.put("label"+k, m.get("filedLable")==null?"":m.get("filedLable"));                                  //作为标题

					}
					subArray.add(njsonObject);
					
				}
				
			}
			
		}
		
		
		tJsonObject.put("listdata", subArray);  //标题做为父节点

		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("colNum", num);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", tJsonObject);
		System.out.println("njsonArray--> "+objresult.toString());
		return objresult.toString();
	}
	
	//纯数据转输交互
	public static String JsonToJsonStream(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList){

		//System.out.println("纯数据转输交互----> "+json);
		LinkedList keyList = new LinkedList();
	
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
        String result = "000";  
        String bodyresult = "000";  //业务系统结果码
        String errordesc = "";  //业务系统查询异常信息
        if(body.has("result")){
        	bodyresult = body.getString("result");  //业务系统结果码
        }
        if (body.has("errordesc")){
        	errordesc = body.getString("errordesc");  //业务系统查询异常信息
        }
        
		JSONArray njsonArray = new JSONArray();
		for(Map m:fileList){
			keyList.add(m.get("filedName"));
		}
		if (body.has("listdata"))
		{
			try {
				JSONArray jsonArray = body.getJSONArray("listdata");
				for(int i=0;i<jsonArray.size();i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					JSONObject njsonObject = new JSONObject();
					//njsonObject.put("id", jsonObject.get(keyName));
					for(Map m:fileList){
						njsonObject.put(m.get("filedName"), jsonObject.get(m.get("filedName")));
					}
					njsonArray.add(njsonObject);
				}
			} catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
				try {
					JSONObject jsonObject = body.getJSONObject("listdata");	
					JSONObject njsonObject = new JSONObject();
					//njsonObject.put("id", jsonObject.get(keyName));
					for(Map m:fileList){
						njsonObject.put(m.get("filedName"), jsonObject.get(m.get("filedName")));
					}
					njsonArray.add(njsonObject);
				} catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
					System.out.println("非法的JSON字符串");
				}
			}
		}
		JSONObject jsondata = new JSONObject();
		if(njsonArray.size()>0){
		  jsondata.put("listdata", njsonArray);
		}
		
        //JSONObject jsondata = new JSONObject();
		jsondata.put("bodyresult", bodyresult);             //业务系统返回结果码
		jsondata.put("errordesc", errordesc);
	
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);             //平台返回结果码 
		objresult.put("formName", formName);         //页面显示名称
		objresult.put("nowpage", nowpage);           //请求页面
		objresult.put("displayType", displayType);  //页面显示类型
		objresult.put("searchFileds", JsonUtil.getJsonByList(keyList));  //关键字段
		objresult.put("body", jsondata);           //主内容
	
		return objresult.toString();
	}
	public static String JsonToJsonListTree2(String json,String type,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson,List<Map> fileNodeList){
		
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
		String result = holder.getString("result");  
		JSONArray njsonArray = new JSONArray();
		JSONArray subArray = new JSONArray();
		JSONObject ajsonObject = new JSONObject();
		JSONObject tJsonObject  = new JSONObject();
		int num = 0;
		System.out.println("fileList-->> "+fileList.toString());
		System.out.println("fileNodeList-->> "+fileNodeList.toString());
//		for(int j=0;j<fileNodeList.size();j++){
//			Map fMap= (Map)fileNodeList.get(j);
//			if(fMap.get("isLeaf").equals("1")){
//				ajsonObject.put(fMap.get("nodeName"), body.get(fMap.get("nodeName").toString()));
//				ajsonObject.put("value"+j, fMap.get("nodeName"));
//				
//			}
//			
//		}
//		subArray.add(ajsonObject);
//		for(int j=0;j<fileNodeList.size();j++){
//			Map fMap= (Map)fileNodeList.get(j);
//			if (body.has(fMap.get("nodeName").toString()) && fMap.get("isLeaf").equals("2"))
//			{
//                 
//				JSONArray jsonArray = body.getJSONArray(fMap.get("nodeName").toString());
//				for(int i=0;i<jsonArray.size();i++){
//					JSONObject jsonObject = jsonArray.getJSONObject(i);

					JSONObject njsonObject = new JSONObject();
					
					for(int k=0;k<fileList.size();k++){
						Map m = (Map)fileList.get(k);
						if(m.get("filedName").equals("TYPE")){
							continue;
						}
						
						System.out.println("比较--> "+body.get(m.get("filedName")) + " ---- " +m.get("filedName"));
						njsonObject.put(type+"_"+m.get("filedName"), body.get(m.get("filedName")));	
						njsonObject.put("value"+num, type+"_"+m.get("filedName"));	                 //主键
						tJsonObject.put("label"+num, m.get("filedLable")==null?"":m.get("filedLable"));                                  //作为标题
						num++;
					}
					subArray.add(njsonObject);
					
		//		}
//				
//			}
//			
//		}
		
		
		tJsonObject.put("listdata", subArray);  //标题做为父节点

		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("colNum", num);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", tJsonObject);
		System.out.println("njsonArray--> "+objresult.toString());
		return objresult.toString();
	}
	
public static String JsonToJsonListTree3(String json,String type,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson,List<Map> fileNodeList){
		
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
		String result = holder.getString("result");  
		JSONArray njsonArray = new JSONArray();
		JSONArray subArray = new JSONArray();
		JSONObject ajsonObject = new JSONObject();
		JSONObject tJsonObject  = new JSONObject();
		int num = 0;
		System.out.println("fileList-->> "+fileList.toString());
		System.out.println("fileNodeList-->> "+fileNodeList.toString());
		int backId = 100001; 
		int nowId = 0;
		for(int j=0;j<fileNodeList.size();j++){
			Map f= (Map)fileNodeList.get(j);
			
			if (f.get("isLeaf").equals("2"))
			{
				JSONObject jsonObject = body.getJSONObject(f.get("nodeName").toString()); 
					JSONObject njsonObject = new JSONObject();
					for(int k=0;k<fileList.size();k++){
						Map m = (Map)fileList.get(k);
						System.out.println("jsonObject.get(d[k] "+jsonObject.get(m.get("filedName")));
						njsonObject.put("value"+k, jsonObject.get(m.get("filedName")));	                 //主键
						njsonObject.put("label"+k, m.get("fileLable"));
						
					}
					nowId = backId+1;
					njsonObject.put("filedName", f.get("nodeName").toString());
					njsonObject.put("fileLable", f.get("nodeLabel").toString());
					njsonObject.put("nowId", nowId);                   
					njsonObject.put("backId", backId);
					njsonObject.put("nextId", nowId+1);
					subArray.add(njsonObject);
					backId = backId+2;                 //需要给出三个不一样的节点ID
			}else {
				//ajsonObject.put("value"+j, body.getString(f.get("filedName").toString()));
			}
			
		}
		
		
		tJsonObject.put("listdata", subArray);  //标题做为父节点

		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("colNum", num);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", tJsonObject);
		System.out.println("njsonArray--> "+objresult.toString());
		return objresult.toString();
	}

	public static String JsonMethodName(String nowpage,String formName,String displayType,String butPriJson,List<Map> fileList,String tabs){
		
		String jsonFileds = JsonUtil.getJsonByList(fileList);
		JSONObject jsondata = new JSONObject();
		jsondata.put("listdata", jsonFileds);
		jsondata.put("tabData", tabs);
		 
		 JSONObject butObject = new JSONObject();
			butObject.put("buttondata", butPriJson);
			JSONObject objresult = new JSONObject();
			objresult.put("result", "000");
			objresult.put("formName", formName);
			objresult.put("nowpage", nowpage);
			objresult.put("displayType", displayType);
			objresult.put("buttons", butObject);
			objresult.put("body", jsondata);
		return objresult.toString();
		
	}
	public static String JsonMethodName2(String nowpage,String formName,String displayType,String butPriJson){
		 JSONArray njsonArray = new JSONArray();
		 JSONObject njsonObject = new JSONObject();	
		 njsonObject.put("displayType", displayType);                    //整个表格的数据展显类型             
		 njsonArray.add(njsonObject);
		 
		 JSONObject jsondata = new JSONObject();
			jsondata.put("listdata", njsonArray);
		 
		 JSONObject butObject = new JSONObject();
			butObject.put("buttondata", butPriJson);
			JSONObject objresult = new JSONObject();
			objresult.put("result", "000");
			objresult.put("formName", formName);
			objresult.put("nowpage", nowpage);
			objresult.put("displayType", displayType);
			objresult.put("buttons", butObject);
			objresult.put("body", jsondata);
		return objresult.toString();//rtJsonFormData("000",nowpage,formName,butPriJson,njsonArray.toString());
		
	}
	public static String JsonStatData(String jsondata,String statFiledTemple,String transferTemple, String statNodeTemple){
		
		 JSONObject holder = JSONObject.fromObject(jsondata);
		 
		 JSONObject jobject = new JSONObject();		
		 jobject.put("body", statFiledTemple);
		 jobject.put("transfer", transferTemple);
		 jobject.put("position", statNodeTemple);
		 jobject.put("result", "000");
		 holder.put("templet", jobject);
		return holder.toString();
	}
//	public static String rtJsonData(String result,String total,String formName,String nowpage,String butPriJson,String jsondata){
//		String newJsonData = "{\"result\": \""+result+"\",\"total\": \""+total+"\",\"formName\": \""+formName+"\",\"nowpage\": \""+nowpage+"\",\"buttons\": {\"buttondata\": "+butPriJson+"}, \"body\": {\"listdata\": "+jsondata+"}}";
//		System.out.println("newJsonData--->  "+newJsonData);
//		return newJsonData;
//	}
//	public static String rtJsonDetailData(String result,String formName,String nowpage,String butPriJson,String jsondata){
//		String newJsonData = "{\"result\": \""+result+"\",\"formName\": \""+formName+"\",\"nowpage\": \""+nowpage+"\",\"buttons\": {\"buttondata\": "+butPriJson+"}, \"body\": {\"listdata\": "+jsondata+"}}";
//		System.out.println("newJsonData--->  "+newJsonData);
//		return newJsonData;
//	}
//	public static String rtJsonFormData(String result,String formName,String nowpage,String butPriJson,String jsondata){
//		String newJsonData = "{\"result\": \""+result+"\",\"formName\": \""+formName+"\",\"nowpage\": \""+nowpage+"\",\"buttons\": {\"buttondata\": "+butPriJson+"}, \"body\": {\"listdata\": "+jsondata+"}}";
//		System.out.println("newJsonData--->  "+newJsonData);
//		return newJsonData;
//	}
	
	public static void main(String[] args){
//		String[] fileds2={"jobPath","jobId","dateTime"};
//		String[] fileds3={"职位","职位ID","ee"};
		String json =  "{\"result\": \"000\", \"body\": {\"listdata\": [{\"jobPath\": \"省局长\", \"jobId\": \"82\"}]}}";
//	//	ParserToList.JsonToJsonForm("000", null,fileds2,fileds3);
 
		//String jsondata = "{\"result\": \"000\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"长白街322号三单元分管器01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\", \"SEQ\": 3}]}}";
		//String jsondata = "{\"result\":\"000\",\"body\":{\"LineConfig\":{\"state\":\"1\",\"time_con\":\"45\" },\"TermConfig\":{ \"state\":\"1\",\"time_con\":\"22\"},\"InNetActive\":{\"state\":\"1\",\"time_con\":\"245\"}, \"PortConfig\":{ \"state\":\"3\", \"time_con\":\"245\"},\"OutNetActive\":{\"state\":\"0\",\"time_con\":\"0\"},\"Construction\":{\"state\":\"0\",\"time_con\":\"0\"}}}";
		String jsondata = "{\"result\":\"000\",\"body\":{\"ftth_lv\":\"95.1%\",\"ftthb_auto_count\":\"253377/271128\",\"ftthb_lv\":\"93.45%\",\"ftth_auto_count\":\"190646/200476\",\"fttb_auto_count\":\"62731/70652\",\"fttb_lv\":\"88.79%\"}}";
		
		JSONObject holder = JSONObject.fromObject(jsondata);
		//JSONObject body = holder.getJSONObject("body"); 
       // String result = holder.getString("result");  
		
		
		 JSONObject butObject = new JSONObject();
		
		 JSONArray list = new JSONArray();
		 JSONObject m1 = new JSONObject();
		 m1.put("L0", "ftth_lv");
		 m1.put("L1", "FTTH成功率");
		 m1.put("L2", "详情");
		 m1.put("L3", "2");
		 
		 JSONObject m2 = new JSONObject();
		 m2.put("L0", "ftth_auto_count");
		 m2.put("L1", "成功数/工单总数");
		 m2.put("L2", "详情");
		 m2.put("L3", "2");
		 
		 JSONObject m3 = new JSONObject();
		 m3.put("L0", "fttb_lv");
		 m3.put("L1", "FTTB成功率");
		 m3.put("L2", "详情");
		 m3.put("L3", "3");
		 
		 JSONObject m4 = new JSONObject();
		 m4.put("L0", "fttb_auto_count");
		 m4.put("L1", "成功数/工单总数");
		 m4.put("L2", "详情");
		 m4.put("L3", "3");
		 
		 JSONObject m5 = new JSONObject();
		 m5.put("L0", "ftthb_lv");
		 m5.put("L1", "FTTB成功率");
		 m5.put("L2", "");
		 m5.put("L3", "4");
		 
		 JSONObject m6 = new JSONObject();
		 m6.put("L0", "ftthb_auto_count");
		 m6.put("L1", "成功数/工单总数");
		 m6.put("L2", "");
		 m6.put("L3", "4");
		 
		 list.add(m1);
		 list.add(m2);
		 list.add(m3);
		 list.add(m4);
		 list.add(m5);
		 list.add(m6);
		 JSONObject m7 = new JSONObject();
		 m7.put("body", list.toString());
		 m7.put("result", "000");
		 holder.put("templet", m7);
		System.out.println("njsonArray--> "+holder.toString());
		
		
	}
}