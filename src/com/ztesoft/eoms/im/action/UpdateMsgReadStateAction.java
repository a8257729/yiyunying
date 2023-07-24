package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImChatLogDAO;
import com.ztesoft.eoms.im.dao.ImChatLogDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;


public class UpdateMsgReadStateAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		getImChatLogDAO().updateMsgReadState(paramMap);
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImChatLogDAO getImChatLogDAO() {
		String daoName = ImChatLogDAOImpl.class.getName();
		return (ImChatLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
