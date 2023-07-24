package com.ztesoft.mobile.outsystem.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAO;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkVersionInfoDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkVersionInfoDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;

public class ApkRegManageAction extends AbstractAction{
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		//System.out.println("========"+param.toString());

		if(type.equals("add")||type.equals("devAdd")){
			//if (type.equals("add")){
			String apkFlag = "";
			if (MapUtils.getString(param, "apkFlag") != null ){
				apkFlag = MapUtils.getString(param, "apkFlag");
			}
			if (apkFlag.equals("insert") || type.equals("devAdd")){
				Map _map = getOtherApkManagerDAO().insert(param);
				param.put("apkId", MapUtils.getLong(_map, "apkId"));
			}
			//}
			//插入菜单表和权限表
			Map menuMap = getAppRegMgrDAO().insert(param);
		
			//当apkFlag 为 insert时代表首次注册，需要填写所有开放的功能列表
			  if (type.equals("add")){			  
				  List funcList = (List) MapUtils.getObject(param, "funList");
				  if (funcList != null && funcList.size()>0){
					  for ( int i = 0; i < funcList.size(); i++){
						 Map _map1 = (Map) funcList.get(i);		

						 //isSelect = 1 代表 该功能为首次注册时选择的功能，因为首次注册可能开放很多功能，但只能选中一个
						 if (MapUtils.getString(_map1, "isSelect")!= null && MapUtils.getString(_map1, "isSelect").equals("1")){
							 if (apkFlag.equals("insert")){
							     _map1.put("muneId", MapUtils.getLong(menuMap, "muneId"));
							 }

							 if (apkFlag.equals("notOper")){
								 //如果不是首次注册，则需要先把选择的功能对应的菜单清空，然后再更新为新的应用菜单
								 getMobileApkFunctionDAO().updateNull(_map1);
								 _map1.put("muneId", MapUtils.getLong(menuMap, "muneId"));

								 getMobileApkFunctionDAO().updateMenuId(_map1); 
							 }
						 }
						 if (apkFlag.equals("insert")){
				             getMobileApkFunctionDAO().insert(_map1);
						 } 
					  }
			      }
				  if (apkFlag.equals("insert")){
					  param.put("createDate", new Date());
					  param.put("apkState", "1");
					  getOtherApkVersionInfoDAO().insert(param);
				  }
			}
		}else if(type.equals("mod") || type.equals("devMod") || type.equals("update")){
			
			//mod代表应用修改，devMod代表配置开发修改，update代表应用更新
			getAppRegMgrDAO().update(param);
			if (type.equals("mod")){
			   getOtherApkManagerDAO().updateName(param);
			}else if (type.equals("update")|| type.equals("devMod")){
			   getOtherApkManagerDAO().update(param);				
			}
			if (type.equals("mod") || type.equals("update")){
				List funcList = (List) MapUtils.getObject(param, "funList");
				  if (funcList != null && funcList.size()>0){
					  for ( int i = 0; i < funcList.size(); i++){
						 Map _map1 = (Map) funcList.get(i);		
						 //isSelect = 1 代表 选择的功能
						 if (MapUtils.getString(_map1, "isSelect")!= null && MapUtils.getString(_map1, "isSelect").equals("1")){
                             //因为一个功能只能对应一个菜单，假设原来的菜单已经对应了功能故要清空原来的菜单
							 getMobileApkFunctionDAO().updateNull(_map1);
							 getMobileApkFunctionDAO().updateMenuId(_map1);
						 }
				        
					  }
				  }
			}
			if (type.equals("update")){				
				param.put("createDate", new Date());
				param.put("apkState", "1");
				getOtherApkVersionInfoDAO().insert(param);
			}
		}else if(type.equals("del") || type.equals("devDel")){
            getMobMunePrDAO().delete(param);			
			Map mappram = new HashMap();
			mappram.put("privClassId", MapUtils.getString(param, "muneId"));
			mappram.put("privType", "1");           //1为菜单
			getMobPrivDAO().delete2(mappram);          //删除与其相关的权限
	//		getOtherApkManagerDAO().delete(param);
            if (type.equals("del")){
				if (MapUtils.getString(param, "apkCode")!= null && !MapUtils.getString(param, "apkCode").equals("")){
			//	  getMobileApkFunctionDAO().deleteByApkCode(param);
				}
            }
		}else if (type.equals("funcAdd")){
			getMobileApkFunctionDAO().insert(param);
		}else if (type.equals("funcMod")){
			getMobileApkFunctionDAO().update(param);
		}else if (type.equals("funcDel")){
			getMobileApkFunctionDAO().delete(param);
		}else if (type.equals("sysAdd")){
			getAppRegMgrDAO().osInsert(param);
		}else if (type.equals("sysMod")){
			getAppRegMgrDAO().osUpdate(param);
		}else if (type.equals("sysDel")){
			getAppRegMgrDAO().osDelete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
	private MobileApkFunctionDAO getMobileApkFunctionDAO() {
		String daoName = MobileApkFunctionDAOImpl.class.getName();
		return (MobileApkFunctionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobMunePrDAO getMobMunePrDAO() {
        String daoName = MobMunePrDAOImpl.class.getName();
        return (MobMunePrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private OtherApkManagerDAO getOtherApkManagerDAO() {
		String daoName = OtherApkManagerDAOImpl.class.getName();
		return (OtherApkManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private OtherApkVersionInfoDAO getOtherApkVersionInfoDAO() {
		String daoName = OtherApkVersionInfoDAOImpl.class.getName();
		return (OtherApkVersionInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private AppRegMgrDAO getAppRegMgrDAO() {
		String daoName = AppRegMgrDAOImpl.class.getName();
		return (AppRegMgrDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
