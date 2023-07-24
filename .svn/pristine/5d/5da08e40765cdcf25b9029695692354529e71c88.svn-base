package com.ztesoft.mobile.etl.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAO;
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAOImpl;
import com.ztesoft.mobile.etl.util.DataBaseTime;

public class InsertEtlRuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" InsertEtlCleanRuleAction paramMap: "+param);
		Long id = null;
		DataBaseTime dataBaseTime = new DataBaseTime();
		Date now = dataBaseTime.getDataBaseDate();
		try{
			
			String oper=(String)param.get("oper");
			param.put("stateDate", now);
			
			if(oper!=null&&oper.equalsIgnoreCase("add")){
				param.put("createDate", now);
				getExtEtlRuleDAO().insert(param);
			}else{				
				getExtEtlRuleDAO().update(param);
			}
			DedicatedActionContext.setResult(SUCCESS);
		}catch(Exception ex){
			DedicatedActionContext.setResult(ERROR);
			ex.printStackTrace();
		}
	
		
		return SUCCESS;
	}
	
	public ExtEtlRuleDAO getExtEtlRuleDAO(){
		String daoName = ExtEtlRuleDAOImpl.class.getName();
		
		return (ExtEtlRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
