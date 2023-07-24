package com.ztesoft.mobile.system.action;

import java.util.Map;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.iom.funcmanager.dao.module.ModuleDAO;
import com.ztesoft.iom.funcmanager.dao.module.ModuleDAOFactory;
public class QueryMenuByModuleAction extends AbstractAction {

	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get("parameter_1");
		
		//取到操作参数
			try{
				int staffId=0;
				Long jobId =null;
				Long specialJobId =null;
				long moduleId =0;
				if(paramMap.containsKey("staffId")){
					staffId  = new Integer(paramMap.get("staffId").toString()).intValue();
				}
				if(paramMap.containsKey("jobId")){
					jobId  = new Long(paramMap.get("jobId").toString());
				}	
				if(paramMap.containsKey("specialJobId")){
					specialJobId  = new Long(paramMap.get("specialJobId").toString());
				}
				if(paramMap.containsKey("moduleId")){
					moduleId  = new Integer(paramMap.get("moduleId").toString()).longValue();
				}
				System.out.println("staffId: "+staffId);
				System.out.println("jobId: "+jobId);
				System.out.println("specialJobId: "+specialJobId);
				System.out.println("moduleId: "+moduleId);
				ModuleDAO moduledao = ModuleDAOFactory.getDAO();
				Map retMap = moduledao.getMenus(staffId, jobId, specialJobId, moduleId);
				System.out.println("menu map："+ retMap);
				DedicatedActionContext.setResult(retMap);
			}catch(Exception e){
				e.printStackTrace();
			}
	
		
		return SUCCESS;
	}


}
