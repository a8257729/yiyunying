package com.ztesoft.mobile.systemMobMgr.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAO;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAO;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;

public class InsertJsonCreateAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getJsonCreateDAO().insert(param);
		}else if(type.equals("mod")){
			getJsonCreateDAO().update(param);
		}else if(type.equals("del")){
			getJsonCreateDAO().delete(param);
			getFiledInfoDAO().delete2(param);
			getMobButtonRightDAO().delete2(param);  //删除相关的按钮
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	private JsonCreateDAO getJsonCreateDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (JsonCreateDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobButtonRightDAO getMobButtonRightDAO() {
		String daoName = MobButtonRightDAOImpl.class.getName();
		return (MobButtonRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private FiledInfoDAO getFiledInfoDAO() {
		String daoName = FiledInfoDAOImpl.class.getName();
		return (FiledInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
