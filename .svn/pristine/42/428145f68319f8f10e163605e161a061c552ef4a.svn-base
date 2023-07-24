package com.ztesoft.mobile.common.action;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.common.dao.FaultReportDAO;
import com.ztesoft.mobile.common.dao.FaultReportDAOImpl;
import com.ztesoft.mobile.common.xwork.AbstractAction;

public class FaultReportSumAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		// 获取参数
		  Map rootMap = DedicatedActionContext.getParams();
		  Map paramMap = (Map)  rootMap.get("parameter_1");
		 paramMap.put("width_prc","100%"); //故障单及时率
			paramMap.put("startIndex","1");
			paramMap.put("reportCode","faultPromptRate");
			paramMap.put("endTime","2013-05-30 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");
			paramMap.put("report_id","3");  
		/*   	paramMap.put("width_prc","100%");
		  	paramMap.put("startIndex","1"); //装拆机及时率faultBokReport
			paramMap.put("reportCode","orderTimelyRateList");
			paramMap.put("endTime","2013-05-30 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");
			paramMap.put("report_id","3");*/
			
	/* 	 paramMap.put("startIndex","1"); //装拆机及时率faultBokReport
			paramMap.put("reportCode","faultBokReport");
			paramMap.put("endTime","2013-05-10 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");*/

        System.out.println("new chenlin ........." + paramMap.toString()); 
        if(paramMap.containsKey("showModel")&&MapUtils.getString(paramMap, "showModel").equals("1")) {
        	Map reportMap =  getFaultReportDAO().selReportInfo(paramMap);//获得报表定义
        	List titleList =  getFaultReportDAO().selReportTitle(paramMap);//获得表头
        	List paramList = getFaultReportDAO().selReportParam(paramMap);//获得查询参数
        	List fieldList = getFaultReportDAO().selReportField(paramMap);//获得展现字段
        	String reportSql = MapUtils.getString(reportMap,"reportSql");  
	        paramMap.put("paramList",paramList);
	        paramMap.put("fieldList",fieldList);
	        paramMap.put("titleList", titleList);
	        if(!paramMap.containsKey("reportSql")||MapUtils.getString(paramMap,"reportSql").equals(""))
	            paramMap.put("reportSql",reportSql); 
	        Map retMap   =  getFaultReportDAO().selReportData(paramMap); //获得数据
	        retMap.put("titleList", titleList);
	        
        	DedicatedActionContext.setResult(retMap);
        }else{
	        System.out.println("new chenlin paramMap........" + paramMap.toString());
	        List titleList =  getFaultReportDAO().selReportTitle(paramMap);//获得表头
	       System.out.println(" new chenlin titleList........" + titleList.toString());
	 
	        List paramList = getFaultReportDAO().selReportParam(paramMap);//获得查询参数
	        System.out.println("new chenlin paramList........" + paramList.toString());  
	
	        List fieldList = getFaultReportDAO().selReportField(paramMap);//获得展现字段
	        System.out.println("new chenlin fieldList........" + fieldList.toString());
	
	        Map reportMap =  getFaultReportDAO().selReportInfo(paramMap);//获得报表定义
	        System.out.println("new chenlin reportMap........" + reportMap.toString());
	
	        List formulaList =  getFaultReportDAO().selReportFormula(paramMap);//获得报表口径
	
	        String reportSql = MapUtils.getString(reportMap,"reportSql");    //
	
	
	        paramMap.put("paramList",paramList);
	        paramMap.put("fieldList",fieldList);
	        if(!paramMap.containsKey("reportSql")||MapUtils.getString(paramMap,"reportSql").equals(""))
	            paramMap.put("reportSql",reportSql);
	
	        Map retMap   =  getFaultReportDAO().selReportData(paramMap); //获得数据
	       
	        retMap.put("titleList",titleList);
	        retMap.put("formulaList",formulaList);
	        retMap.put("reportMap", reportMap);
	        
	  //      transData(retMap);
	        System.out.println("new chenlin retMap........" + retMap.toString());
	
	        DedicatedActionContext.setResult(retMap);
        }
        return SUCCESS;
	}
	private  FaultReportDAO getFaultReportDAO() {
		String daoName = FaultReportDAOImpl.class.getName();
		return (FaultReportDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private void transData(Map retMap){
		 List dataList = (List) retMap.get("dataList");
	        //int totalRecords = (Integer) retMap.get("totalRecords");
	        ArrayList rsList =  new ArrayList();
	        Map rsMap =  new HashMap();
	        for (int i = 0; i < dataList.size() -1; i++)
	        {
	        	rsList = (ArrayList) dataList.get(i);
	        	System.out.println("new chenlin rsList........" + rsList.toString());
	        	for(int j = 0; j < rsList.size() -1; j++ )
	        	{
	        		rsMap = (Map) rsList.get(j);
	        		System.out.println("new chenlin rsMap........" + rsMap.toString());
	        	}
	        }
	        
	        System.out.println("new chenlin dataList........" + dataList.toString());
	        //System.out.println("new chenlin totalRecords........" + totalRecords);
	}
	
	

}
