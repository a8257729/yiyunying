package com.ztesoft.mobile.etl.action;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.TransConfigDAO;
import com.ztesoft.mobile.etl.dao.TransConfigDAOImpl;

public class QryTransRuleAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("QryTransRuleAction paramMap:" + paramMap);
		Map transRule = getDao().selById(paramMap);
		System.out.println("ViewNotepadAction: " + transRule);

		DedicatedActionContext.setResult(transRule);

		return SUCCESS;
	}

	private TransConfigDAO getDao() {
		String daoName = TransConfigDAOImpl.class.getName();
		return (TransConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}