package com.ztesoft.mobile.etl.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.EtlSumRuleDAO;
import com.ztesoft.mobile.etl.dao.EtlSumRuleDAOImpl;
import com.ztesoft.mobile.etl.util.DataBaseTime;

public class MdfEtlSumRuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" MdfEtlSumRuleAction paramMap: " + param);
		DataBaseTime dataBaseTime = new DataBaseTime();
		Date now = dataBaseTime.getDataBaseDate();
		param.put("stateDate", now);

		param.put("state", "10A");
		String flag = "failure";
		try {

			getEtlRuleDAO().update(param);
			flag = "success";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DedicatedActionContext.setResult(flag);
		return SUCCESS;
	}

	public EtlSumRuleDAO getEtlRuleDAO() {
		String daoName = EtlSumRuleDAOImpl.class.getName();

		return (EtlSumRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
