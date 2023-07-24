package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAO;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAOImpl;

public class InsertMobileApkFunctionAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		System.out.println("========"+param.toString());
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobileApkFunctionDAO().insert(param);
		}else if(type.equals("mod")){
			getMobileApkFunctionDAO().update(param);
		}else if(type.equals("del")){
			getMobileApkFunctionDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
	private MobileApkFunctionDAO getMobileApkFunctionDAO() {
		String daoName = MobileApkFunctionDAOImpl.class.getName();
		return (MobileApkFunctionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
