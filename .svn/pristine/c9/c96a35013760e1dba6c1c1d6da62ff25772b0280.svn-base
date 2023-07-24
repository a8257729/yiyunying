package com.ztesoft.mobile.outsystem.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAO;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAOImpl;

public class QueryOuterSystemToShortAction  extends AbstractAction{

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		
		List list = getOssmhMappingDAO().selAllSystems(param);
 
		DedicatedActionContext.setResult(list);	
		
		return null;
	}
	
	private OssmhMappingDAO getOssmhMappingDAO() {
        String daoName = OssmhMappingDAOImpl.class.getName();
        return (OssmhMappingDAO) BaseDAOFactory.getImplDAO(daoName);
    }

}
