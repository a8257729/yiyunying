package com.ztesoft.eoms.im.action;

import java.util.Date;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;


public class InsertBiggroupAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		paramMap.put("createDate", new Date());
		paramMap.put("state", new Integer(1));
		getImStaffBiggroupDAO().insert(paramMap);
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImStaffBiggroupDAO getImStaffBiggroupDAO() {
		String daoName = ImStaffBiggroupDAOImpl.class.getName();
		return (ImStaffBiggroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
