package com.ztesoft.eoms.im.action;

import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImChatLogDAO;
import com.ztesoft.eoms.im.dao.ImChatLogDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class QryUnReadByStaffAction extends AbstractAction{

	@Override
	
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		List list =  getImChatLogDAO().selUnReadMsg(paramMap);
		DedicatedActionContext.setResult(list);
		return SUCCESS;
	}
	private ImChatLogDAO getImChatLogDAO() {
		String daoName = ImChatLogDAOImpl.class.getName();
		return (ImChatLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
