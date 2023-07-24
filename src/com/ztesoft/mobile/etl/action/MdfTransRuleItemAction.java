package com.ztesoft.mobile.etl.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.TransItemDAO;
import com.ztesoft.mobile.etl.dao.TransItemDAOImpl;

public class MdfTransRuleItemAction extends AbstractAction {
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		System.out.println("MdfTransRuleAction paramMap:" + paramMap);

		long transOperItemId = MapUtils.getLong(paramMap, "transOperItemId");

		getDAO().update(paramMap);

		DedicatedActionContext.setResult(transOperItemId);
		return SUCCESS;
	}

	private TransItemDAO getDAO() {
		String daoName = TransItemDAOImpl.class.getName();
		return (TransItemDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
