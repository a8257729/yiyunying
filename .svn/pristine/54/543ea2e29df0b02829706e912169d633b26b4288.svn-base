package com.ztesoft.mobile.system.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.vo.Job;
import com.zterc.uos.oaas.vo.Organization;
import com.ztesoft.mobile.common.helper.JsonUtil;


public class JobJsonUtil {

	public static String getJsonByList(List dList) {
		String data = "";
		if (dList != null) {
			JSONArray ja = JSONArray.fromObject(dList);
			data = ja.toString();
		}
		return data;
	}
	
	public static String getOrgSelJson(Map jobOrgs, Job[] jobs, int orgId,String topText){
		
        List parentList = new ArrayList();
        Map jobOrg = new HashMap();
        try {
        	if(orgId == 0){
        		
    	 		jobOrg.put("id", 0);
    	 		jobOrg.put("text", topText);
    	 		jobOrg.put("leaf", false);
    	 		jobOrg.put("children", new ArrayList());
    	 		getOrgTree(jobOrgs,jobs,jobOrg,0);
        	}else{
        		Organization org = (Organization)jobOrgs.get(orgId);
        		jobOrg.put("id", org.getOrgId());
    	 		jobOrg.put("text", org.getOrgName());
    	 		jobOrg.put("leaf", false);
    	 		jobOrg.put("children", new ArrayList());
    	 		getOrgTree(jobOrgs,jobs,jobOrg,orgId);
        	}
	 		
	 		parentList.add(jobOrg);
	 		
	 		System.out.println("getPrivSelJson"+JsonUtil.getJsonByList(parentList));
        	
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
	
	/**
	 * 组装权限类别树递归算法
	 */
	private static void getOrgTree(Map jobOrg,Job[] jobs,Map currJobOrg,int orgId){
		try {
			int currOrgId = orgId ;
			
			List subPrivClasList = new ArrayList();
		 	Set pirvClassKey = jobOrg.keySet();
		 	Iterator it = pirvClassKey.iterator();
		 	
		 	while(it.hasNext()){
		 		Organization p = (Organization)jobOrg.get(it.next());
		 		
		 		if(currOrgId == p.getParentId()){
		 			//子权限类别，将其添加为子节点
		 			Map subPrivClass = new HashMap();
		 			subPrivClass.put("id", p.getOrgId());
		 			subPrivClass.put("text", p.getOrgName());
		 			subPrivClass.put("leaf", false);
		 			
			 		subPrivClasList.add(subPrivClass);
			 		getOrgTree(jobOrg,jobs,subPrivClass,p.getOrgId());
		 		}
		 	}
		 	
		 	for (int i = 0; i < jobs.length; i++) {
				if(jobs[i].getOrgId() == currOrgId){
					
					Map subPriv = new HashMap();
					subPriv.put("id", jobs[i].getJobId());
					subPriv.put("text", jobs[i].getJobName());
					subPriv.put("leaf", true);
					
					if("1".equals(jobs[i].getIsBasic().trim())){
						subPriv.put("checked", true);
					}else{
						subPriv.put("checked", false);
					}
					
					subPrivClasList.add(subPriv);
				}
			}
		 	currJobOrg.put("children", subPrivClasList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
