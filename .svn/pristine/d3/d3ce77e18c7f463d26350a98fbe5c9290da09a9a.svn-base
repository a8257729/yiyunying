package com.ztesoft.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CopyOfParserToList {

	public static String JsonToJsonList(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson){
		//String[] fileds2={"jobPath","jobId","dateTime"};
		//String[] fileds3={"职位","职位ID","ee"};
		//json =  "{\"result\": \"000\",\"total\": \"10\", \"body\": {\"listdata\": [{\"jobPath\": \"省局长\", \"jobId\": \"82\"}]}}";

		String[] sfileds = new String[fileList.size()];
		String[] sfiledNames = new String[fileList.size()];
		System.out.println("fileList--->>  "+fileList.size());
		for(int k=0;k<fileList.size();k++){
			Map m = fileList.get(k);
			sfileds[k] = m.get("filedName")+"";
			sfiledNames[k] = m.get("filedLable")+"";
		}
		System.out.println("sfileds--->>  "+sfileds.length);
		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
        String result = holder.getString("result");  
        String total = holder.getString("total"); 
		JSONArray njsonArray = new JSONArray();
		if (body.has("listdata"))
		{
			JSONArray jsonArray = body.getJSONArray("listdata");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONObject njsonObject = new JSONObject();
				njsonObject.put("id", jsonObject.get(keyName));
				njsonObject.put("title", jsonObject.get(sfileds[0]));
				njsonObject.put("sndtitle", sfileds.length>=2?jsonObject.get(sfileds[1]):"");
				njsonObject.put("datetime", sfileds.length>=3?jsonObject.get(sfileds[2]):"");
				njsonObject.put("label", sfiledNames[0]);
				njsonObject.put("label2", sfiledNames.length>=2?sfiledNames[1]:"");
				njsonObject.put("label3", sfiledNames.length>=3?sfiledNames[2]:"");
				njsonObject.put("displayType", displayType);                    //整个表格的数据展显类型 
				njsonArray.add(njsonObject);
			}
		}else {
			JSONObject njsonObject = new JSONObject();
			njsonObject.put("id", "");
			njsonObject.put("title", "");
			njsonObject.put("sndtitle", "");
			njsonObject.put("datetime", "");
			njsonObject.put("label", "");
			njsonObject.put("label2", "");
			njsonObject.put("label3", "");
			njsonObject.put("displayType", "");
			njsonArray.add(njsonObject);
		}
		JSONObject jsondata = new JSONObject();
		jsondata.put("listdata", njsonArray);
		
		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("total", total);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", jsondata);
		
		return objresult.toString();//rtJsonData(result,total,formName,nowpage,butPriJson,njsonArray.toString());
	}
	public static String JsonToJsonDetail(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson){

		JSONObject holder = JSONObject.fromObject(json);
		JSONObject body = holder.getJSONObject("body"); 
        String result = holder.getString("result");  
		JSONArray njsonArray = new JSONArray();
		if (body.has("listdata"))
		{
			JSONArray jsonArray = body.getJSONArray("listdata");
			for(int i=0;i<jsonArray.size();i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				for(Map m: fileList){
					JSONObject njsonObject = new JSONObject();
					njsonObject.put("id", jsonObject.get(keyName));	                 //主键
					njsonObject.put("name", m.get("filedName"));
					njsonObject.put("type", m.get("filedType"));                    //显示字段类型
					njsonObject.put("value", jsonObject.get(m.get("filedName")));   //字段名
					njsonObject.put("label", m.get("filedLable"));                  //中文名
					
					njsonObject.put("pass", m.get("isPassValue"));                  //是否传值
					njsonObject.put("display", m.get("isDisplay"));                 //是否显示    
					njsonObject.put("displayType", displayType);                    //整个表格的数据展显类型  
					njsonArray.add(njsonObject);
				}
			
				
			}
		}else {
			JSONObject njsonObject = new JSONObject();
			njsonObject.put("id", "");
			njsonObject.put("name", "");
			njsonObject.put("value", "");
			njsonObject.put("label", "");
			njsonObject.put("type", "");
			njsonObject.put("pass", "");
			njsonObject.put("display", "");
			njsonObject.put("displayType", "");
			njsonArray.add(njsonObject);
		}
		JSONObject jsondata = new JSONObject();
		jsondata.put("listdata", njsonArray);
		
		JSONObject butObject = new JSONObject();
		butObject.put("buttondata", butPriJson);
		
		
		JSONObject objresult = new JSONObject();
		objresult.put("result", result);
		objresult.put("formName", formName);
		objresult.put("nowpage", nowpage);
		objresult.put("displayType", displayType);
		objresult.put("buttons", butObject);
		objresult.put("body", jsondata);
		
		return objresult.toString();//rtJsonDetailData(result,formName,nowpage,butPriJson,njsonArray.toString());
	}
	
	public static String JsonToJsonForm(String json,String keyName,String formName,String nowpage,String displayType,List<Map> fileList, String butPriJson){

		JSONArray njsonArray = new JSONArray();
		for(Map m: fileList){
			JSONObject njsonObject = new JSONObject();
			
			njsonObject.put("id", keyName);	                                 //主键
			njsonObject.put("name", m.get("filedName"));
			njsonObject.put("label", m.get("filedLable"));                  //中文名
			njsonObject.put("type", m.get("filedType"));                    //显示字段类型
			njsonObject.put("pass", m.get("isPassValue"));                  //是否传值
			njsonObject.put("display", m.get("isDisplay"));                 //是否显示    
			njsonObject.put("options", m.get("checkedData")==null?"-1":m.get("checkedData"));             //下拉数据
			njsonObject.put("treedata", m.get("selectData")==null?"-1":m.get("selectData"));             //选择树数据 
			njsonObject.put("required", m.get("isRequired")==null?"N":m.get("isRequired"));
			njsonObject.put("displayType", displayType);                    //整个表格的数据展显类型 
			njsonArray.add(njsonObject);
		}
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
		System.out.println("fileList-->> "+fileList.toString());
		System.out.println("fileNodeList-->> "+fileNodeList.toString());
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
	
	public static String JsonMethodName(String nowpage,String formName,String displayType,String butPriJson){
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
//		String json =  "{\"result\": \"000\", \"body\": {\"listdata\": [{\"jobPath\": \"省局长\", \"jobId\": \"82\"}]}}";
//	//	ParserToList.JsonToJsonForm("000", null,fileds2,fileds3);

		//String jsondata = "{\"result\": \"000\",\"body\": {\"OBD_CODE\": \"DT-POS-003510\",\"OBD_NAME\": \"长白街322号三单元分管器01\", \"NODES\": [ { \"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 1},{\"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\",\"SEQ\": 2},{\"NODE_CODE\": \"250TY.NH0001GJ046\",\"NODE_NAME\": \"南湖路34-3号门口GJ046\", \"SEQ\": 3}]}}";
		String jsondata = "{\"result\":\"000\",\"body\":{\"LineConfig\":{\"state\":\"1\",\"time_con\":\"45\" },\"TermConfig\":{ \"state\":\"1\",\"time_con\":\"22\"},\"InNetActive\":{\"state\":\"1\",\"time_con\":\"245\"}, \"PortConfig\":{ \"state\":\"3\", \"time_con\":\"245\"},\"OutNetActive\":{\"state\":\"0\",\"time_con\":\"0\"},\"Construction\":{\"state\":\"0\",\"time_con\":\"0\"}}}";
		
		List fileList4 = new ArrayList();
		Map fileMap4 = new HashMap();
		fileMap4.put("filedName", "LineConfig");
		fileMap4.put("fileLable", "光纤配置");
		fileMap4.put("fileType", "true");
		fileList4.add(fileMap4);
		
		Map fileMap5 = new HashMap();
		fileMap5.put("filedName", "TermConfig");
		fileMap5.put("fileLable", "终端配置");
		fileMap5.put("fileType", "true");
		fileList4.add(fileMap5);
		
		Map fileMap6 = new HashMap();
		fileMap6.put("filedName", "InNetActive");
		fileMap6.put("fileLable", "网络激活");
		fileMap6.put("fileType", "true");
		fileList4.add(fileMap6);
		JSONObject holder = JSONObject.fromObject(jsondata);
		JSONObject body = holder.getJSONObject("body"); 
        String result = holder.getString("result");  
		JSONArray njsonArray = new JSONArray();
		JSONArray subArray = new JSONArray();
		JSONObject ajsonObject = new JSONObject();
		int backId = 100001; 
		int nowId = 0;
		for(int j=0;j<fileList4.size();j++){
			Map f= (Map)fileList4.get(j);
			
			if (body.has(f.get("filedName").toString()) && f.get("fileType").equals("true"))
			{
				JSONObject jsonObject = body.getJSONObject(f.get("filedName").toString()); 
				//JSONArray jsonArray = body.getJSONArray(f.get("filedName").toString());
				//for(int i=0;i<jsonArray.size();i++){
					//JSONObject jsonObject = jsonArray.getJSONObject(i);

					JSONObject njsonObject = new JSONObject();

					List fileList = new ArrayList();
					Map fileMap = new HashMap();
					fileMap.put("filedName", "state");
					fileMap.put("fileLable", "状态");
					fileList.add(fileMap);

					Map fileMap2 = new HashMap();
					fileMap2.put("filedName", "time_con");
					fileMap2.put("fileLable", "时间");
					fileList.add(fileMap2);

					for(int k=0;k<fileList.size();k++){
						Map m = (Map)fileList.get(k);
						System.out.println("jsonObject.get(d[k] "+jsonObject.get(m.get("filedName")));
						njsonObject.put("value"+k, jsonObject.get(m.get("filedName")));	                 //主键
						njsonObject.put("label"+k, m.get("fileLable"));
						
					}
					nowId = backId+1;
					njsonObject.put("filedName", f.get("filedName").toString());
					njsonObject.put("fileLable", f.get("fileLable").toString());
					njsonObject.put("nowId", nowId);                   
					njsonObject.put("backId", backId);
					njsonObject.put("nextId", nowId+1);
					subArray.add(njsonObject);
					backId = backId+2;                 //需要给出三个不一样的节点ID
			
				//}
			}else {
				//if(body.has("OBD_CODE")){
				//String OBD_CODE = body.getString(f.get("filedName").toString());
				//String OBD_NAME = body.getString("OBD_NAME");			
				ajsonObject.put("value"+j, body.getString(f.get("filedName").toString()));

				//}
			}
			
		}
		ajsonObject.put("listdata", subArray);
		njsonArray.add(ajsonObject);
		String newJsonData = "{\"result\": \""+result+"\", \"body\": "+ajsonObject.toString()+"}";
		System.out.println("njsonArray--> "+newJsonData.toString());
	}
}
