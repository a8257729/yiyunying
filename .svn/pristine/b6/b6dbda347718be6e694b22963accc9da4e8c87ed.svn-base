package com.ztesoft.mobile.v2.action.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;
import com.ztesoft.mobile.v2.dao.app.FuncMenuRelaDAO;
import com.ztesoft.mobile.v2.dao.app.FuncMenuRelaDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class OptAppFunctionAction extends AbstractAction {
	public String execute() throws Exception {

		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		String menuType = MapUtils.getString(param, "menuType");
		param.put("state", "1");
		param.put("stateDate", new Date());
		param.put("createTime", new Date());
		param.put("buildTime", new Date());
       
		if (type.equals("add")) {
			if (menuType.equals("2")) {
				System.out.println("xxxxxxxxxxxxxx=" + param);
			//	 param.put("accessClass",  getPakageForClass(param.get("accessClass").toString()));
				
				//getMobileAppFunctionDAO().insert(param);
				Map reMap = getMobileMenuDAO().insert(param);
				getMobileMenuConfigDAO().insert(param);
				Map priv = new HashMap();
				priv.put("privCode", MapUtils.getString(reMap, "privCode"));
				priv.put("privName", MapUtils.getString(param, "menuName"));
				priv.put("privClassId",MapUtils.getLong(reMap, "menuId"));
				priv.put("comments", MapUtils.getString(param, "menuName"));
				priv.put("menuType", MapUtils.getString(param, "menuType"));
				priv.put("privType", MapUtils.getString(param, "privType"));
				priv.put("state", "10A");
				getMobPrivDAO().insert(priv);
				Map map=new HashMap();
				map.put("appFuncId", MapUtils.getString(param, "appFuncId"));
				map.put("menuConfigId", MapUtils.getString(param, "menuConfigId"));
				map.put("buildTime", new Date());
				map.put("state", MapUtils.getString(param, "state"));
				map.put("stateDate", new Date());
				getMobileFuncMenuRelaDAO().insert(map);
			
			} else {
				Map priv = new HashMap();
				Map reMap = getMobileMenuDAO().insert(param);
				getMobileMenuConfigDAO().insert(param);
				priv.put("privCode", MapUtils.getString(reMap, "privCode"));
				priv.put("privName", MapUtils.getString(param, "menuName"));
				priv.put("privClassId", MapUtils.getLong(reMap, "menuId"));
				priv.put("comments", MapUtils.getString(param, "menuName"));
				priv.put("menuType", MapUtils.getString(param, "menuType"));
				priv.put("privType", MapUtils.getString(param, "privType"));
				
				priv.put("state", "10A");
				getMobPrivDAO().insert(priv);
			}
		} else if (type.equals("mod")) {
		//	getMobileAppFunctionDAO().update(param);
			getMobileMenuDAO().update(param);
			System.out.println("update "+param);
			if(menuType.equals("2")){
		      	getMobileFuncMenuRelaDAO().updateFuncMenuRela(param);
			  }
			getMobileMenuConfigDAO().update(param);
		} else if (type.equals("del")) {
			getMobileFuncMenuRelaDAO().delete(param);
			getMobileFuncMenuRelaDAO().deleteFuncMenuRela(param);
			getMobileMenuConfigDAO().delete(param);
			getMobileMenuDAO().delete(param);
			getMobPrivDAO().delete(param);
		} else if (type.equals("menuAdd")) {
			Map reMap = getMobileMenuDAO().insert(param);
			Map priv = new HashMap();
			priv.put("privCode", MapUtils.getString(reMap, "privCode"));
			priv.put("privName", MapUtils.getString(param, "menuName"));
			priv.put("privClassId", MapUtils.getLong(reMap, "menuId"));
			priv.put("comments", MapUtils.getString(param, "menuName"));
			priv.put("state", "10A");
			getMobPrivDAO().insert(priv);
		} else if (type.equals("menuMod")) {
			getMobileMenuDAO().update(param);
		} else if (type.equals("menuDel")) {
			getMobileMenuDAO().delete(param);
			
			getMobPrivDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
// 从类路径中截取包路径
	public String getPakageForClass(String classpath){
		String pakagePath=null;
		 int last= classpath.lastIndexOf(".");
		pakagePath=classpath.substring(0,last+1);
		return pakagePath;
	}
	
	private MobileAppFuncDAO getMobileAppFunctionDAO() {
		String daoName = MobileAppFuncDAOImpl.class.getName();
		return (MobileAppFuncDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileMenuDAO getMobileMenuDAO() {
		String daoName = MobileMenuDAOImpl.class.getName();
		return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileMenuConfigDAO getMobileMenuConfigDAO() {
		String daoName = MobileMenuConfigDAOImpl.class.getName();
		return (MobileMenuConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private FuncMenuRelaDAO getMobileFuncMenuRelaDAO() {
		String daoName = FuncMenuRelaDAOImpl.class.getName();
		return (FuncMenuRelaDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
