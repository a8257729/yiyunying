package com.ztesoft.mobile.systemMobMgr.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobileStatTransferDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobileStatTransferDAOImpl;

public class InsertStatTransferAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobileStatTransferDAO().insert(param);
		}else if(type.equals("mod")){
			getMobileStatTransferDAO().update(param);
		}else if(type.equals("del")){
			getMobileStatTransferDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	private MobileStatTransferDAO getMobileStatTransferDAO() {
		String daoName = MobileStatTransferDAOImpl.class.getName();
		return (MobileStatTransferDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
