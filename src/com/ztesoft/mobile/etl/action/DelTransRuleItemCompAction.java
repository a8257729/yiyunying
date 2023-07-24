package com.ztesoft.mobile.etl.action;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.TransItemDAO;
import com.ztesoft.mobile.etl.dao.TransItemDAOImpl;

public class DelTransRuleItemCompAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("DelTransRuleAction paramMap:" + paramMap);

		getDAO().deleteComp(paramMap);

		DedicatedActionContext.setResult("success");
		return SUCCESS;
	}

	private TransItemDAO getDAO() {
		String daoName = TransItemDAOImpl.class.getName();
		return (TransItemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
