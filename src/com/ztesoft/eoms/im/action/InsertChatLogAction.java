package com.ztesoft.eoms.im.action;

import java.util.Date;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImChatLogDAO;
import com.ztesoft.eoms.im.dao.ImChatLogDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;


public class InsertChatLogAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		paramMap.put("msgDate", new Date());
		getImChatLogDAO().insert(paramMap);
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImChatLogDAO getImChatLogDAO() {
		String daoName = ImChatLogDAOImpl.class.getName();
		return (ImChatLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
