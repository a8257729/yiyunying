package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAO;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class QryOnLineStateByStaffIdAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		Map map = getImStaffOnlineDAO().selOnlineStateByStaffId(paramMap);
		DedicatedActionContext.setResult(map);
		return SUCCESS;
	}
	private ImStaffOnlineDAO getImStaffOnlineDAO() {
		String daoName = ImStaffOnlineDAOImpl.class.getName();
		return (ImStaffOnlineDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
