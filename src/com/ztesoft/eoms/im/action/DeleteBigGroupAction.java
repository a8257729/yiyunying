package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;


public class DeleteBigGroupAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		paramMap.put("state",0);
		getImStaffBiggroupDAO().delete(paramMap);
		
		
		
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImStaffBiggroupDAO getImStaffBiggroupDAO() {
		String daoName = ImStaffBiggroupDAOImpl.class.getName();
		return (ImStaffBiggroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	
}
