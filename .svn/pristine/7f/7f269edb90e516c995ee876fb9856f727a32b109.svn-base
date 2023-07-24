package com.ztesoft.mobile.etl.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAO;
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAOImpl;


public class EtlRuleManagerAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		System.out.println(" EtlRuleManagerAction paramMap: "+param);
		param.put("stateDate", new Date());
		param.put("state", "10P");
		String flag = "failure";
		
		try{
			String mothed= MapUtils.getString(param, "mothed");
			if(mothed!=null&&mothed.equalsIgnoreCase("delete")){				
				delete(param);
			}
			
			flag = "success";
		}catch(Exception ex){
			ex.printStackTrace();
		}
		DedicatedActionContext.setResult(flag);	
		return SUCCESS;
	}
	public void delete(Map param) throws Exception{
		//getEtlRuleDAO().delete(param);
		getExtEtlRuleDAO().deleteByUpdate(param);
		//
	}
	public ExtEtlRuleDAO getExtEtlRuleDAO(){
		String daoName = ExtEtlRuleDAOImpl.class.getName();
		
		return (ExtEtlRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
