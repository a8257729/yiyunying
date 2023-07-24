package com.ztesoft.mobile.outsystem.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.SysRegionDAO;
import com.ztesoft.mobile.outsystem.dao.SysRegionDAOImpl;

public class QryAllRegionAction extends AbstractAction{
	
	public String execute() throws Exception {
       /**
        * 查询所有系统域
        */
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		List list = getSysRegionDAO().selAllRegions(paramMap);
		DedicatedActionContext.setResult(list);
		return SUCCESS;	
	}
	private SysRegionDAO getSysRegionDAO() {
		String daoName = SysRegionDAOImpl.class.getName();
		return (SysRegionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
