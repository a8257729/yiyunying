package com.ztesoft.mobile.systemMobMgr.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAOImpl;

public class InsertStatDisplayAction extends AbstractAction {

	@Override
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMoblieStatDisplayDAO().insert(param);
		}else if(type.equals("mod")){
			getMoblieStatDisplayDAO().update(param);
		}else if(type.equals("del")){
			getMoblieStatDisplayDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	 private MoblieStatDisplayDAO getMoblieStatDisplayDAO() {
	        String daoName = MoblieStatDisplayDAOImpl.class.getName();
	        return (MoblieStatDisplayDAO) BaseDAOFactory.getImplDAO(daoName);
	    }
	
}
