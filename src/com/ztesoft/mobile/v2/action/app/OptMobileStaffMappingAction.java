package com.ztesoft.mobile.v2.action.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAOImpl;

public class OptMobileStaffMappingAction extends AbstractAction{
	public String execute() throws Exception {

		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		param.put("state", "1");
		param.put("stateDate", new Date());
		param.put("buildTime", new Date());
       
		if (type.equals("add")) {
			getMobileStaffMappingDAO().insert(param);			
		} else if (type.equals("mod")) {
			getMobileStaffMappingDAO().update(param);
		} else if (type.equals("del")) {
			System.out.println("param="+param);
			getMobileStaffMappingDAO().delete(param);
		} 
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	private MobileStaffMappingDAO getMobileStaffMappingDAO() {
		String daoName = MobileStaffMappingDAOImpl.class.getName();
		return (MobileStaffMappingDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
