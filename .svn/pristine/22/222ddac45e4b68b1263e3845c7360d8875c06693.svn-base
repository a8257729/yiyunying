package com.ztesoft.mobile.v2.action.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.jspsmart.upload.Request;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class OptAppFunHisAction extends AbstractAction {

	@Override
	public String execute() throws Exception {

		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		String menuType = MapUtils.getString(param, "menuType");
		String optfunmenu = MapUtils.getString(param, "optfunmenu"); // 用于判断此操作是对moblie_app
	
		param.put("state", "1");
		param.put("stateDate", new Date());
		param.put("createTime", new Date());
		param.put("buildTime", new Date());
		param.put("updateTime", new Date());
		
		if (optfunmenu != null && optfunmenu.equals("app")) {
			if (type.equals("add")) {
				param.put("updateTime", new Date());
                  List funcList=(List) param.get("funcArray");
				System.out.println("param  " +param);
				param.put("downloadCount",0);
				getMobileAppDAO().insert(param);
				if(funcList!=null && funcList.size()>0){
				for(int i=0;i<funcList.size();i++){
					Map funcObjMap=(Map)funcList.get(i);
					    funcObjMap.put("appId", param.get("appId"));
					   getMobileAppFunctionDAO().insert(funcObjMap);
			    	}
			 	}
				
			} else if (type.equals("mod")) {
				param.put("updateTime", new Date());
				System.out.println(" mod "+param);
				getMobileAppDAO().update(param);

			} else if (type.equals("del")) {
				//getMobileAppHisDAO().deleteFun(param);
                	
                 getMobileAppFunctionDAO().deleteAppFuncRelaConfig(param);//删除应用的时候，级联删除appFun 和 MOBILE_FUNC_MENU_RELA 还有MOBILE_MENU_CONFIG
                 getMobileAppDAO().delete(param);
			} else if (type.equals("dti")) {
				

			} else if(type.equals("download")){//下载时修改下载次数
				param.put("downloadCount",MapUtils.getInteger(param, "downloadCount")+1 );
				getMobileAppHisDAO().updateDownCount(param);
			}

		} else {
			if (type.equals("add")) {
				System.out.println(param);
                 getMobileAppFunctionDAO().insert(param);
			} else if (type.equals("mod")) {
				System.out.println(" param sss"+param);
                   getMobileAppFunctionDAO().update(param);
			} else if (type.equals("del")) {
				 getMobileAppFunctionDAO().delete(param); //功能删除的时候
			} else if (type.equals("dti")) {

			}

		}

		return null;
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
	private MobileAppDAO getMobileAppDAO() {
		String daoName = MobileAppDAOImpl.class.getName();
		return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileAppHisDAO getMobileAppHisDAO() {
		String daoName = MobileAppHisDAOImpl.class.getName();
		return (MobileAppHisDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
