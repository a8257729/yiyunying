package com.ztesoft.mobile.etl.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.SqEtlCleanRuleDAO;
import com.ztesoft.mobile.etl.dao.SqEtlCleanRuleDAOImpl;


public class DeleteEtlCleanRuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" deleteEtlCleanRuleAction paramMap: "+param);
		param.put("stateDate", new Date());
		param.put("state", "10F");
		String flag = "failure";
		try{
			getSqEtlCleanRuleDAO().delete(param);
			flag = "success";
		}catch(Exception ex){
			ex.printStackTrace();
		}
		DedicatedActionContext.setResult(flag);	
		return SUCCESS;
	}
	
	public SqEtlCleanRuleDAO getSqEtlCleanRuleDAO(){
		String daoName = SqEtlCleanRuleDAOImpl.class.getName();
		
		return (SqEtlCleanRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
