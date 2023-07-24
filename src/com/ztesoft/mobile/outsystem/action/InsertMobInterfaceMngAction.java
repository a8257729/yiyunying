package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAO;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAOImpl;

public class InsertMobInterfaceMngAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobInterfaceMngDAO().insert(param);
		}else if(type.equals("mod")){
			getMobInterfaceMngDAO().update(param);
		}else if(type.equals("del")){
			getMobInterfaceMngDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}

	private MobileInterfaceManagerDAO getMobInterfaceMngDAO() {
		String daoName = MobileInterfaceManagerDAOImpl.class.getName();
		return  (MobileInterfaceManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
