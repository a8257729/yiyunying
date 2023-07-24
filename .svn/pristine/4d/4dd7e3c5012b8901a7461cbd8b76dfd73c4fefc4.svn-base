package com.ztesoft.mobile.systemMobMgr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.collections.MapUtils;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.vo.JobPrivilege;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.bean.Privilege;
import com.ztesoft.mobile.systemMobMgr.bean.PrivilegeClass;


public class StaffPrivJsonUtil {

	public static String getJsonByList(List dList) {
		String data = "";
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}
	//将权限类别对象转换成list并加上所属权限
	public static List addPrivsToPirvClass(Map privClassMap, Privilege[] privs){
		System.out.println("privClassList-----2222222222222222222222222222-------->>>  "+privClassMap.toString());
		List privClassList = new ArrayList();
		if(privClassMap!=null){
			Set pirvClassKey = privClassMap.keySet();
		 	Iterator it = pirvClassKey.iterator();
		 	while(it.hasNext()){
		 		PrivilegeClass p = (PrivilegeClass)privClassMap.get(it.next());
		 		System.out.println("privClassList-----2222222222222222222233333333322222222--33333333333------>>>  "+p.getMuneId());
		 		System.out.println("privClassList-----2222222222222222222233333333322222222--444444444------>>>  "+p.getMuneName());
		 		System.out.println("privClassList-----2222222222222222222233333333322222222--444444444--44---->>>  "+privs.length);
		 		Map privClass = new HashMap();
		 		privClass.put("id", p.getMuneId());
		 		privClass.put("text", p.getMuneName());
		 		privClass.put("parentId", p.getParentId());
		 		privClass.put("leaf", false);
		 		//privClass.put("checked", false);
		 		privClass.put("children", new ArrayList());
		 		for(int i =0;i<privs.length;i++){
		 			System.out.println("privClassList-----2222222222222222222233333333322222222--55555------>>>  "+privs[i].getPrivClassId());
			 		System.out.println("privClassList-----2222222222222222222233333333322222222--6666------>>>  "+p.getParentId());
		 			if(privs[i].getPrivClassId().equals(p.getMuneId())){
		 				System.out.println("privClassList-----2222222222222222222233333333322222222--6666--1111111111111---->>>  "+privs[i].getPrivName());
						Map privMap = new HashMap();
						privMap.put("id", privs[i].getPrivClassId());
						privMap.put("text", privs[i].getPrivName());
						privMap.put("leaf", true);
						
						//privMap.put("checked", false);
						
						((List)privClass.get("children")).add(privMap);
					}
		 		}
		 		privClassList.add(privClass);
		 		System.out.println("privClass------------->>>  "+privClass.toString());
		 	}
		 	
			
		}
		System.out.println("privClassList------------->>>  "+privClassList.toString());
		return privClassList;
	} 
	//将权限类别对象转换成list并加上职位权限
	public static List addJobPrivsToPirvClass(Map privClassMap, JobPrivilege[] privs){
		System.out.println("privClassList----5555555555--------->>>  "+privClassMap.toString());
		List privClassList = new ArrayList();
		Set pirvClassKey = privClassMap.keySet();
	 	Iterator it = pirvClassKey.iterator();
	 	while(it.hasNext()){
	 		PrivilegeClass p = (PrivilegeClass)privClassMap.get(it.next());
	 		System.out.println("privClassList-----2222222222222222222233333333322222222--66666666------>>>  "+p.getMuneId());
	 		System.out.println("privClassList-----2222222222222222222233333333322222222--77777777------>>>  "+p.getMuneName());
	 		Map privClass = new HashMap();
	 		privClass.put("id", p.getMuneId());
	 		privClass.put("text", p.getMuneName());
	 		privClass.put("name", p.getMuneName());
	 		privClass.put("parentId", p.getParentId());
	 		privClass.put("leaf", false);
	 		privClass.put("checked", false);
	 		privClass.put("children", new ArrayList());
	 		for(int i =0;i<privs.length;i++){
	 			System.out.println("privClassList-----2222222222222222222233333333322222222--88888------>>>  "+privs[i].getPrivClassId());
		 		System.out.println("privClassList-----2222222222222222222233333333322222222--999999------>>>  "+p.getMuneId());
	 			if(privs[i].getPrivClassId() == p.getMuneId()){
					Map privMap = new HashMap();
					privMap.put("id", privs[i].getPrivClassId());
					privMap.put("text", privs[i].getPrivName());
					privMap.put("name", privs[i].getPrivName());
					if("1".equals(privs[i].getCanGrant())){
						privMap.put("canGrant", "可");
					}else if("0".equals(privs[i].getCanGrant())){
						privMap.put("canGrant", "否");
					}
					privMap.put("leaf", true);
					privMap.put("checked", false);
					
					((List)privClass.get("children")).add(privMap);
				}
	 		}
	 		privClassList.add(privClass);
	 		
	 	}
	 	System.out.println("privClassList----222222222222--------->>>  "+privClassList.toString());
		return privClassList;
	} 
	//处理权限类别关联关系
	public static void addPirvClassToParent(List privClassList,Map parentClassMap){
		for(int i =0;i<privClassList.size();i++){
			Map privClass = (Map)privClassList.get(i);
			if(MapUtils.getString(parentClassMap,"id").equals(MapUtils.getString(privClass,"parentId"))){
				((List)parentClassMap.get("children")).add(privClass);
				
				addPirvClassToParent(privClassList,privClass);
			}
			
		}
		
	}
	//判断此节点的所有层级子节点是否含有叶子节点
	public static boolean hasLeafNode(Map privClass,List removeClassIdList){
		boolean flag = true;
		if(privClass.get("children")!=null){
			List children = (List)privClass.get("children");
			
			if(children.size()!=0){//有子节点，判断是否有叶子节点
				boolean tmp = false;
				for(int i =0;i<children.size();i++){
					Map map = (Map)children.get(i);
					if((Boolean)map.get("leaf")){//有叶子节点
						tmp = true;
					}else{
						if(hasLeafNode(map,removeClassIdList)){//有子节点,且子节点有叶子节点
							tmp = true;
						}else{
							removeClassIdList.add(map.get("id").toString());
							//System.out.println("map -- :"+map.get("text"));
						}			
					}
				}
				if(!tmp){//没有叶子节点
					flag =false;
				}
			}else{
				//没有叶子节点的空节点
				flag = false;
			}
		}else{
			//空节点
			flag = false;
		}
		return flag;
	}
	
	public static String getPrivSelJson(Map privClassMap, Privilege[] privs,String treeTitle){
		List privClassList = addPrivsToPirvClass(privClassMap,privs);
		List tmpList = addPrivsToPirvClass(privClassMap,privs);
		List removeClassIdList = new ArrayList();
		System.out.println("privClassList:kkkkkkkkkkkkkk "+privClassList.toString());
		System.out.println("privClassList:ffffffffffffff "+tmpList.toString());
        List parentList = new ArrayList();
        
        try {
	 		Map privClass = new HashMap();
	 		privClass.put("id", "0");
	 		privClass.put("text", treeTitle);
	 		privClass.put("name", treeTitle);
	 		
	 		privClass.put("leaf", false);
	 		//privClass.put("checked",false);
	 		privClass.put("children", new ArrayList());
    	 		
	 		addPirvClassToParent(privClassList,privClass);
	 		
	 		hasLeafNode(privClass,removeClassIdList);
	 		for(int i=0;i<removeClassIdList.size();i++){
	 			for(int j=0;j<tmpList.size();j++){
	 				if(((Map)tmpList.get(j)).get("id").toString().equals(removeClassIdList.get(i).toString())){
	 					tmpList.remove(tmpList.get(j));
	 					break;
	 				}
	 			}
	 		}
	 		System.out.println("tmpList 2:"+tmpList.size());
	 		privClass.put("children", new ArrayList());
	 		addPirvClassToParent(tmpList,privClass);
	 		parentList.add(privClass);
        	
            return JsonUtil.getJsonByList(parentList);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            OAASError error = new OAASError(OAASError.XML_COMPOSITTE_ERROR);
            //throw new OAASException(error, ex);
            return ex.getMessage();
        }
        finally {
        	parentList.clear();
        	parentList = null;
        }
	}
	
	public static String getJobPrivSelJson(Map privClassMap, JobPrivilege[] privs,String treeTitle){
		List privClassList = addJobPrivsToPirvClass(privClassMap,privs);
		List tmpList = addJobPrivsToPirvClass(privClassMap,privs);
		List removeClassIdList = new ArrayList();
		System.out.println("privClassList: "+privClassList);
        List parentList = new ArrayList();
        try {
	 		Map privClass = new HashMap();
	 		privClass.put("id", "0");
	 		privClass.put("text", treeTitle);
	 		privClass.put("name", treeTitle);
	 		privClass.put("leaf", false);
	 		privClass.put("checked",false);
	 		privClass.put("children", new ArrayList());
	 		
	 		addPirvClassToParent(privClassList,privClass);
	 		
	 		hasLeafNode(privClass,removeClassIdList);
	 		System.out.println("removeClassIdList :"+removeClassIdList.size());
	 		System.out.println("tmpList 1:"+tmpList.size());
	 		for(int i=0;i<removeClassIdList.size();i++){
	 			for(int j=0;j<tmpList.size();j++){
	 				if(((Map)tmpList.get(j)).get("id").toString().equals(removeClassIdList.get(i).toString())){
	 					tmpList.remove(tmpList.get(j));
	 					break;
	 				}
	 			}
	 		}
	 		System.out.println("tmpList 2:"+tmpList.size());
	 		privClass.put("children", new ArrayList());
	 		addPirvClassToParent(tmpList,privClass);
	 		parentList.add(privClass);
	 		
	 		System.out.println("getJobPrivSelJson" + JsonUtil.getJsonByList(parentList));
        	
            return JsonUtil.getJsonByList(parentList);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            OAASError error = new OAASError(OAASError.XML_COMPOSITTE_ERROR);
            //throw new OAASException(error, ex);
            return ex.getMessage();
        }
        finally {
        	parentList.clear();
        	parentList = null;
        }
	}
}