package com.ztesoft.mobile.etl.action;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.TransConfigDAO;
import com.ztesoft.mobile.etl.dao.TransConfigDAOImpl;

public class AddTransRuleAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("AddTransRuleAction paramMap:" + paramMap);

		getDAO().insert(paramMap);

		DedicatedActionContext.setResult("success");
		return SUCCESS;
	}

	private TransConfigDAO getDAO() {
		String daoName = TransConfigDAOImpl.class.getName();
		return (TransConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
