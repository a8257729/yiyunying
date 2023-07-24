package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobilePhoneVersionDAO;
import com.ztesoft.mobile.outsystem.dao.MobilePhoneVersionDAOImpl;

public class InsertMobilePhoneVersionAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobPhoneVersionDAO().insert(param);
		}else if(type.equals("mod")){
			getMobPhoneVersionDAO().update(param);
		}else if(type.equals("del")){
			getMobPhoneVersionDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}

	private MobilePhoneVersionDAO getMobPhoneVersionDAO() {
		String daoName = MobilePhoneVersionDAOImpl.class.getName();
		return (MobilePhoneVersionDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
