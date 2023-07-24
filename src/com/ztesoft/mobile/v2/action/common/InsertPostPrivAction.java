package com.ztesoft.mobile.v2.action.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobStaffJObRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobStaffJObRightDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobilePostPrivDAO;
import com.ztesoft.mobile.v2.dao.menu.MobilePostPrivDAOImpl;

public class InsertPostPrivAction extends AbstractAction{
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");

		String strpriv = MapUtils.getString(param, "strpriv");
		ArrayList grantPriv = (ArrayList)param.get("grantPriv");
		ArrayList priv = (ArrayList)param.get("priv");
		int postId = MapUtils.getIntValue(param, "postId");
		int _defaultJobId = MapUtils.getIntValue(param, "jobId");


		System.out.println("strpriv--->>  "+strpriv +"---type="+type+"postId ="+postId);
		if(type.equals("mobilePrivAdd")){ 
			List list = getMobilePostPrivDAO().getPrivCode(strpriv);  
	
			String[] privs = new String[list.size()];
			for(int i=0 ;i< list.size();i++){
					Map map = (Map)list.get(i);
					privs[i] = map.get("privCode")+"";
					System.out.println("privs: "+privs[i]);
			}
			String[] grantPrivs = new String[grantPriv.size()];
			for(int i=0 ;i< grantPriv.size();i++){
					grantPrivs[i] = grantPriv.get(i).toString();
					System.out.println("grantPrivs: "+grantPrivs[i]);
			}
		
			String[] str = {"-1"};
			getMobilePostPrivDAO().updateJobPrivs(str,postId,grantPrivs,privs);
		}else if(type.equals("postPrivAdd")){ 
			
			List _list = getMobilePostPrivDAO().selByPostId(param);
			if (_list != null && _list.size() >0){
				for(int i=0 ;i< _list.size();i++){
					Map _map = (Map) _list.get(i); 
					_map.put("jobId", _defaultJobId);
					_map.put("canGrant", "1");
					_map.put("updateDate", new Date());
					getMobStaffJObRightDAO().insert(_map);
				}				
			}			
		}else if(type.equals("postPrivDel")){ 
			
			getMobStaffJObRightDAO().deleteByJobId(param);		
		}else if(type.equals("postPrivMod")){ 
			//查询职位所有权限
			List _list1 = getMobStaffJObRightDAO().selByJobId(param);
			//查询岗位所有权限
			List _list = getMobilePostPrivDAO().selByPostId(param);
			if (_list != null && _list.size() >0){
				for(int i=0 ;i< _list.size();i++){
					if (_list1 != null && _list1.size()>0){
						for(int j=0 ;j< _list.size();j++){
							//如果该职位已经有了该权限则不插入
							Map _postmap = (Map) _list.get(i); 
							Map _map = (Map) _list.get(i); 
							if (MapUtils.getString(_postmap, "jobId")!= null && MapUtils.getString(_map, "jobId")!= null
								&& MapUtils.getString(_postmap, "jobId").equals(MapUtils.getString(_map, "jobId"))	){
								continue;
							}else{						
								_map.put("jobId", _defaultJobId);
								_map.put("canGrant", "1");
								_map.put("updateDate", new Date());
								getMobStaffJObRightDAO().insert(_map);
							}
						}
					}else {
						Map _map = (Map) _list.get(i); 
						_map.put("jobId", _defaultJobId);
						_map.put("canGrant", "1");
						_map.put("updateDate", new Date());
						getMobStaffJObRightDAO().insert(_map);
					}
				}				
			}
		}


		return SUCCESS;

	}
	

	private MobilePostPrivDAO getMobilePostPrivDAO() {
		String daoName = MobilePostPrivDAOImpl.class.getName();
		return (MobilePostPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobStaffJObRightDAO getMobStaffJObRightDAO() {
		String daoName = MobStaffJObRightDAOImpl.class.getName();
		return (MobStaffJObRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
