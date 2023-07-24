package com.ztesoft.mobile.etl.action;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.SchMonitorDAO;
import com.ztesoft.mobile.etl.dao.SchMonitorDAOImpl;

public class QryTransFieldLogAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("QryTransFieldLogAction paramMap:" + paramMap);
		Map log = getSchMonitorDAO().qryFieldLog(paramMap);
		System.out.println("QryTransFieldLogAction: " + log);

		DedicatedActionContext.setResult(log);

		return SUCCESS;
	}

	public SchMonitorDAO getSchMonitorDAO() {
		String daoName = SchMonitorDAOImpl.class.getName();
		return (SchMonitorDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
