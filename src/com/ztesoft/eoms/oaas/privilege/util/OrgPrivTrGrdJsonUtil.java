package com.ztesoft.eoms.oaas.privilege.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.vo.JobPrivilege;
import com.zterc.uos.oaas.vo.Privilege;
import com.zterc.uos.oaas.vo.PrivilegeClass;
import com.ztesoft.eoms.util.JsonUtil;

public class OrgPrivTrGrdJsonUtil {

	private final static  Logger logger = Logger.getLogger(OrgPrivTrGrdJsonUtil.class);

	public static String getJsonByList(List dList) {
		String data = "";
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}
	//��Ȩ��������ת����list����������Ȩ��
	public static List addPrivsToPirvClass(Map privClassMap, Privilege[] privs){
		List privClassList = new ArrayList();
		Set pirvClassKey = privClassMap.keySet();
	 	Iterator it = pirvClassKey.iterator();
	 	while(it.hasNext()){
	 		PrivilegeClass p = (PrivilegeClass)privClassMap.get(it.next());
	 		Map privClass = new HashMap();
	 		privClass.put("id", p.getPrivClassId());
	 		privClass.put("text", p.getPrivClassName());
	 		privClass.put("parentId", p.getParentId());
	 		privClass.put("leaf", false);
	 		//privClass.put("checked", false);
	 		privClass.put("children", new ArrayList());
	 		for(int i =0;i<privs.length;i++){
	 			if(privs[i].getPrivClassId() == p.getPrivClassId()){
					Map privMap = new HashMap();
					privMap.put("id", privs[i].getPrivCode());
					privMap.put("text", privs[i].getPrivName());
					privMap.put("leaf", true);
					
					//privMap.put("checked", false);
					
					((List)privClass.get("children")).add(privMap);
				}
	 		}
	 		privClassList.add(privClass);
	 		
	 	}
		return privClassList;
	} 
	//��Ȩ��������ת����list������ְλȨ��
	public static List addJobPrivsToPirvClass(Map privClassMap, JobPrivilege[] privs){
		List privClassList = new ArrayList();
		Set pirvClassKey = privClassMap.keySet();
	 	Iterator it = pirvClassKey.iterator();
	 	while(it.hasNext()){
	 		PrivilegeClass p = (PrivilegeClass)privClassMap.get(it.next());
	 		Map privClass = new HashMap();
	 		privClass.put("id", p.getPrivClassId());
	 		privClass.put("text", p.getPrivClassName());
	 		privClass.put("parentId", p.getParentId());
	 		privClass.put("leaf", false);
	 		privClass.put("checked", false);
	 		privClass.put("children", new ArrayList());
	 		for(int i =0;i<privs.length;i++){
	 			if(privs[i].getPrivClassId() == p.getPrivClassId()){
					Map privMap = new HashMap();
					privMap.put("id", privs[i].getPrivCode());
					privMap.put("text", privs[i].getPrivName());
					privMap.put("leaf", true);
					privMap.put("checked", false);
					
					((List)privClass.get("children")).add(privMap);
				}
	 		}
	 		privClassList.add(privClass);
	 		
	 	}
		return privClassList;
	} 
	//����Ȩ����������ϵ
	public static void addPirvClassToParent(List privClassList,Map parentClassMap){
		for(int i =0;i<privClassList.size();i++){
			Map privClass = (Map)privClassList.get(i);
			if(MapUtils.getString(parentClassMap,"id").equals(MapUtils.getString(privClass,"parentId"))){
				((List)parentClassMap.get("children")).add(privClass);
				
				addPirvClassToParent(privClassList,privClass);
			}
		}
	}
	//�жϴ˽ڵ�����в㼶�ӽڵ��Ƿ���Ҷ�ӽڵ�
	public static boolean hasLeafNode(Map privClass,List removeClassIdList){
		boolean flag = true;
		if(privClass.get("children")!=null){
			List children = (List)privClass.get("children");
			
			if(children.size()!=0){//���ӽڵ㣬�ж��Ƿ���Ҷ�ӽڵ�
				boolean tmp = false;
				for(int i =0;i<children.size();i++){
					Map map = (Map)children.get(i);
					if((Boolean)map.get("leaf")){//��Ҷ�ӽڵ�
						tmp = true;
					}else{
						if(hasLeafNode(map,removeClassIdList)){//���ӽڵ�,���ӽڵ���Ҷ�ӽڵ�
							tmp = true;
						}else{
							removeClassIdList.add(map.get("id").toString());
							//logger.debug("map -- :"+map.get("text"));
						}			
					}
				}
				if(!tmp){//û��Ҷ�ӽڵ�
					flag =false;
				}
			}else{
				//û��Ҷ�ӽڵ�Ŀսڵ�
				flag = false;
			}
		}else{
			//�սڵ�
			flag = false;
		}
		
		return flag;
	}
	
	
	public static String getPrivSelJson(Map privClassMap, Privilege[] privs,String treeTitle){
		
		List privClassList = addPrivsToPirvClass(privClassMap,privs);
		List tmpList = addPrivsToPirvClass(privClassMap,privs);
		List removeClassIdList = new ArrayList();
		logger.debug("privClassList: "+privClassList);
        List parentList = new ArrayList();
        
        try {
	 		Map privClass = new HashMap();
	 		privClass.put("id", "0");
	 		privClass.put("text", treeTitle);
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
	 		logger.debug("tmpList 2:"+tmpList.size());
	 		privClass.put("children", new ArrayList());
	 		addPirvClassToParent(tmpList,privClass);
	 		parentList.add(privClass);
        	
            return JsonUtil.getJsonByList(parentList);
        }
        catch (Exception ex) {
            logger.debug(ex.getMessage());
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
		logger.debug("privClassList: "+privClassList);
        List parentList = new ArrayList();
        try {
	 		Map privClass = new HashMap();
	 		privClass.put("id", "0");
	 		privClass.put("text", treeTitle);
	 		privClass.put("leaf", false);
	 		privClass.put("checked",false);
	 		privClass.put("children", new ArrayList());
	 		
	 		addPirvClassToParent(privClassList,privClass);
	 		
	 		hasLeafNode(privClass,removeClassIdList);
	 		logger.debug("removeClassIdList :"+removeClassIdList.size());
	 		logger.debug("tmpList 1:"+tmpList.size());
	 		for(int i=0;i<removeClassIdList.size();i++){
	 			for(int j=0;j<tmpList.size();j++){
	 				if(((Map)tmpList.get(j)).get("id").toString().equals(removeClassIdList.get(i).toString())){
	 					tmpList.remove(tmpList.get(j));
	 					break;
	 				}
	 			}
	 		}
	 		logger.debug("tmpList 2:"+tmpList.size());
	 		privClass.put("children", new ArrayList());
	 		addPirvClassToParent(tmpList,privClass);
	 		parentList.add(privClass);
	 		
	 		logger.debug("getJobPrivSelJson"+JsonUtil.getJsonByList(parentList));
        	
            return JsonUtil.getJsonByList(parentList);
        }
        catch (Exception ex) {
            logger.debug(ex.getMessage());
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
