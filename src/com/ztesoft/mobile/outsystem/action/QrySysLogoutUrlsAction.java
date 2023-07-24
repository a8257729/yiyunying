package com.ztesoft.mobile.outsystem.action;


import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAO;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAOImpl;


public class QrySysLogoutUrlsAction extends AbstractAction{
	
	public String execute() throws Exception {
      
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
	
		List list = getOuterSystemDAO().selAllLogoutUrl(null);
		
		
		DedicatedActionContext.setResult(list);
		return SUCCESS;	
	}
	private OuterSystemDAO getOuterSystemDAO() {
		String daoName = OuterSystemDAOImpl.class.getName();
		return (OuterSystemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
