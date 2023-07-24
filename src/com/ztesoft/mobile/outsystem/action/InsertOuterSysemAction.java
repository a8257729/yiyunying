package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAO;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAOImpl;

public class InsertOuterSysemAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		Map map = getOutSystemDAO().selById(param);
		
		System.out.println(" InsertOuterSysemAction paramMap: "+paramMap);
		
		System.out.println(" InsertOuterSysemAction param: "+param);
		
		System.out.println(" InsertOuterSysemAction map: "+map);
		boolean flag = false;
		try{
			if(map!=null){
				getOutSystemDAO().update(param);
				System.out.println(" InsertOuterSysemAction update ");
			}else{
				getOutSystemDAO().insert(param);
				System.out.println(" InsertOuterSysemAction insert ");
			}
			flag = true;
			
		}catch(Exception ex){
			ex.printStackTrace();
			//throw new Exception(ex.getMessage());
		}
		
		if(flag){
			DedicatedActionContext.setResult(SUCCESS);		
		}else{
			DedicatedActionContext.setResult(ERROR);			
		}
		
		
		return SUCCESS;
	}
	
	public OuterSystemDAO getOutSystemDAO(){
		String daoName = OuterSystemDAOImpl.class.getName();
		
		return (OuterSystemDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
