package com.ztesoft.mobile.v2.action.common;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;

public class InsertMobileParamAction  extends AbstractAction {
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String oper = MapUtils.getString(param, "oper");
		param.put("createTime", new Date());
		param.put("updateTime", new Date());
		param.put("stateDate", new Date());
		System.out.println("========"+param.toString());
        //参数,增,删,改
		if(oper.equals("add")){
			param.put("state", 1);
			getMobileParamDAO().insert(param);
		}else if(oper.equals("mod")){
			param.put("state", 1);
			getMobileParamDAO().update(param);
		}else if(oper.equals("del")){
			getMobileParamDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
    private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
