package com.ztesoft.mobile.etl.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.EtlTacheRuleDAO;
import com.ztesoft.mobile.etl.dao.EtlTacheRuleDAOImpl;

public class AddEtlTacheRuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" AddEtlTacheRuleAction paramMap: " + param);
		try {

			getDAO().insert(param);

			DedicatedActionContext.setResult(SUCCESS);
		} catch (Exception ex) {
			DedicatedActionContext.setResult(ERROR);
			ex.printStackTrace();
		}

		return SUCCESS;
	}

	private EtlTacheRuleDAO getDAO() {
		String daoName = EtlTacheRuleDAOImpl.class.getName();
		return (EtlTacheRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
