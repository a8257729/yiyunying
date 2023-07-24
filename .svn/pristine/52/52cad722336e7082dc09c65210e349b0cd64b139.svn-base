package com.ztesoft.mobile.systemMobMgr.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieFildNodeDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieFildNodeDAOImpl;

public class InsertFiledNodeAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMoblieFildNodeDAO().insert(param);
		}else if(type.equals("mod")){
			getMoblieFildNodeDAO().update(param);
		}else if(type.equals("del")){
			getMoblieFildNodeDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	 private MoblieFildNodeDAO getMoblieFildNodeDAO() {
	        String daoName = MoblieFildNodeDAOImpl.class.getName();
	        return (MoblieFildNodeDAO) BaseDAOFactory.getImplDAO(daoName);
	    }
	
}
