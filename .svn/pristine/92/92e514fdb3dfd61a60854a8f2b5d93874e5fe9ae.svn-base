package com.ztesoft.mobile.systemMobMgr.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;

public class InsertButRightAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobButtonRightDAO().insert(param);
		}else if(type.equals("mod")){
			getMobButtonRightDAO().update(param);
		}else if(type.equals("del")){
			getMobButtonRightDAO().delete(param);
			Map mappram = new HashMap();
			mappram.put("privClassId", MapUtils.getString(param, "buttonId"));
			mappram.put("privType", "2");           //1为菜单
			getMobPrivDAO().delete2(mappram);          //删除与其相关的权限
		}

		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	private MobButtonRightDAO getMobButtonRightDAO() {
		String daoName = MobButtonRightDAOImpl.class.getName();
		return (MobButtonRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
