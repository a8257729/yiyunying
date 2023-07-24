package com.ztesoft.mobile.etl.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.EtlTacheRuleDAO;
import com.ztesoft.mobile.etl.dao.EtlTacheRuleDAOImpl;

public class DelEtlTacheRuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" DelEtlTacheRuleAction paramMap: " + param);
		String flag = "failure";
		try {
			getDAO().delete(param);
			flag = "success";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DedicatedActionContext.setResult(flag);
		return SUCCESS;
	}

	private EtlTacheRuleDAO getDAO() {
		String daoName = EtlTacheRuleDAOImpl.class.getName();
		return (EtlTacheRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
