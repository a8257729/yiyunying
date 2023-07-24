package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class UpdateGroupAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		getImStaffGroupDAO().update(paramMap);
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImStaffGroupDAO getImStaffGroupDAO() {
		String daoName = ImStaffGroupDAOImpl.class.getName();
		return (ImStaffGroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
