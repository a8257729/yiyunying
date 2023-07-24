package com.ztesoft.mobile.etl.action;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.TransItemDAO;
import com.ztesoft.mobile.etl.dao.TransItemDAOImpl;

public class AddTransRuleItemAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("AddTransRuleItemAction paramMap:" + paramMap);

		long transOperItemId = getDAO().insert(paramMap);

		DedicatedActionContext.setResult(transOperItemId);
		return SUCCESS;
	}

	private TransItemDAO getDAO() {
		String daoName = TransItemDAOImpl.class.getName();
		return (TransItemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}