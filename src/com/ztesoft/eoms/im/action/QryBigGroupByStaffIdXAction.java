package com.ztesoft.eoms.im.action;

import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;


public class QryBigGroupByStaffIdXAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		int staffId = Integer.parseInt(paramMap.get("staffId").toString());
		List list = getImStaffBiggroupDAO().selGroupsByStaffId(staffId);
		DedicatedActionContext.setResult(list);
		return SUCCESS;
	}
	private ImStaffBiggroupDAO getImStaffBiggroupDAO() {
		String daoName = ImStaffBiggroupDAOImpl.class.getName();
		return (ImStaffBiggroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
