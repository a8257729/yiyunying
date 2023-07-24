package com.ztesoft.eoms.im.action;

import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class UpdateBigGroupAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		
		
		getImStaffBiggroupDAO().update(paramMap);
		
		List groupStaffList = (List)paramMap.get("groupStaffList");
		if(groupStaffList!=null){
			getImStaffBiggroupRealDAO().deleteByGroupId(paramMap);
			for(int i=0;i<groupStaffList.size();i++){
				Map map = (Map)groupStaffList.get(i);
				map.put("imStaffBiggroupId", paramMap.get("imStaffBiggroupId"));
				map.put("imBgStaffId", map.get("staffId"));
				getImStaffBiggroupRealDAO().insert(map);
			}
		
		}
		
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}
	private ImStaffBiggroupDAO getImStaffBiggroupDAO() {
		String daoName = ImStaffBiggroupDAOImpl.class.getName();
		return (ImStaffBiggroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private ImStaffBiggroupRealDAO getImStaffBiggroupRealDAO() {
		String daoName = ImStaffBiggroupRealDAOImpl.class.getName();
		return (ImStaffBiggroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
