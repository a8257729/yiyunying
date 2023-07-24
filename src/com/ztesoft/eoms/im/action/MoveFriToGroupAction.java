package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class MoveFriToGroupAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		getImStaffGroupRealDAO().moveFriToGroup(paramMap);
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImStaffGroupRealDAO getImStaffGroupRealDAO() {
		String daoName = ImStaffGroupRealDAOImpl.class.getName();
		return (ImStaffGroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
