package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileOtherSysManagerDAO;
import com.ztesoft.mobile.outsystem.dao.MobileOtherSysManagerDAOImpl;

public class InsertMobileOtherSysManagerAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getSysAddressDAO().insert(param);
		}else if(type.equals("mod")){
			getSysAddressDAO().update(param);
		}else if(type.equals("del")){
			getSysAddressDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}

	private MobileOtherSysManagerDAO getSysAddressDAO() {
		String daoName = MobileOtherSysManagerDAOImpl.class.getName();
		return (MobileOtherSysManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
