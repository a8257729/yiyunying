package com.ztesoft.mobile.v2.action.app;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAOImpl;

public class OptAppAction extends AbstractAction{

	public String execute() throws Exception {


		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");

		
		if(type.equals("add")){
			param.put("buildTime", new Date());
			param.put("updateTime", new Date());
			param.put("versionCode", "1");
			param.put("state", "1");
			param.put("downloadCount", "0");
						
			Map _Map = getMobileAppDAO().insert(param);			
			getMobileAppHisDAO().insert(_Map);
		}else if(type.equals("mod")){
			getMobileAppDAO().update(param);
			//插应用版本历史表
			Map mapHis = getMobileAppDAO().selById(param);
			//更新时取出历史表里最大id，然后更新历史表信息
			Map lastmap = getMobileAppHisDAO().selMaxIdByConn(param);
			mapHis.put("appHisId", MapUtils.getLong(lastmap, "appHisId"));
			getMobileAppHisDAO().update(mapHis);
		}else if(type.equals("del")){
			getMobileAppDAO().delete(param);
			getMobileAppHisDAO().delete(param);
		}else if(type.equals("upgrade")){
			Map _Map =getMobileAppDAO().selVisionCodeByConn(param);
			param.put("versionCode", MapUtils.getLong(_Map, "icount"));
			param.put("updateTime", new Date());
			getMobileAppDAO().update(param);	
			//更新应用状态
			param.put("appStatus", "2");
			getMobileAppDAO().updateAppStatus(param);
			//插应用版本历史表
			Map mapHis = getMobileAppDAO().selById(param);
			getMobileAppHisDAO().insert(mapHis);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

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