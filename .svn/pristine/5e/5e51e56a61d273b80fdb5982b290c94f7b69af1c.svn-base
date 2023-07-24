package com.ztesoft.mobile.outsystem.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceMappingDAO;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceMappingDAOImpl;


public class MobileInterfaceMappingManageAction extends AbstractAction{
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			param.put("stateDate", new Date());
			param.put("state", "1");
			getMobileInterfaceMappingDAO().insert(param);
		}else if(type.equals("mod")){
			param.put("stateDate", new Date());
			getMobileInterfaceMappingDAO().update(param);
		}else if(type.equals("del")){
			getMobileInterfaceMappingDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}

	private MobileInterfaceMappingDAO getMobileInterfaceMappingDAO() {
		String daoName = MobileInterfaceMappingDAOImpl.class.getName();
		return (MobileInterfaceMappingDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
