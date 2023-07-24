package com.ztesoft.eoms.im.action;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class InsertFriendQuickAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		
		Map staffIdMap = getImStaffGroupRealDAO().selStaffByUserName(paramMap);
		if(staffIdMap==null||!staffIdMap.containsKey("staffId")||staffIdMap.get("staffId")==null){//查询无此用户
			DedicatedActionContext.setResult(0);
		}else{
			paramMap.put("imFriStaffId", staffIdMap.get("staffId"));
			getImStaffGroupRealDAO().insert(paramMap);
			DedicatedActionContext.setResult(SUCCESS);
		}
		
		
		
		
		return SUCCESS;
	}
	private ImStaffGroupRealDAO getImStaffGroupRealDAO() {
		String daoName = ImStaffGroupRealDAOImpl.class.getName();
		return (ImStaffGroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
