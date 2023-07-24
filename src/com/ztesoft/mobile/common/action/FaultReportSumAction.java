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
		// ��ȡ����
		  Map rootMap = DedicatedActionContext.getParams();
		  Map paramMap = (Map)  rootMap.get("parameter_1");
		 paramMap.put("width_prc","100%"); //���ϵ���ʱ��
			paramMap.put("startIndex","1");
			paramMap.put("reportCode","faultPromptRate");
			paramMap.put("endTime","2013-05-30 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");
			paramMap.put("report_id","3");  
		/*   	paramMap.put("width_prc","100%");
		  	paramMap.put("startIndex","1"); //װ�����ʱ��faultBokReport
			paramMap.put("reportCode","orderTimelyRateList");
			paramMap.put("endTime","2013-05-30 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");
			paramMap.put("report_id","3");*/
			
	/* 	 paramMap.put("startIndex","1"); //װ�����ʱ��faultBokReport
			paramMap.put("reportCode","faultBokReport");
			paramMap.put("endTime","2013-05-10 23:59:59");
			paramMap.put("funName","oper.queryData");
			paramMap.put("beginTime","2013-05-01 00:00:00");
			paramMap.put("stepSize","20");*/

        System.out.println("new chenlin ........." + paramMap.toString()); 
        if(paramMap.containsKey("showModel")&&MapUtils.getString(paramMap, "showModel").equals("1")) {
        	Map reportMap =  getFaultReportDAO().selReportInfo(paramMap);//��ñ�����
        	List titleList =  getFaultReportDAO().selReportTitle(paramMap);//��ñ�ͷ
        	List paramList = getFaultReportDAO().selReportParam(paramMap);//��ò�ѯ����
        	List fieldList = getFaultReportDAO().selReportField(paramMap);//���չ���ֶ�
        	String reportSql = MapUtils.getString(reportMap,"reportSql");  
	        paramMap.put("paramList",paramList);
	        paramMap.put("fieldList",fieldList);
	        paramMap.put("titleList", titleList);
	        if(!paramMap.containsKey("reportSql")||MapUtils.getString(paramMap,"reportSql").equals(""))
	            paramMap.put("reportSql",reportSql); 
	        Map retMap   =  getFaultReportDAO().selReportData(paramMap); //�������
	        retMap.put("titleList", titleList);
	        
        	DedicatedActionContext.setResult(retMap);
        }else{
	        System.out.println("new chenlin paramMap........" + paramMap.toString());
	        List titleList =  getFaultReportDAO().selReportTitle(paramMap);//��ñ�ͷ
	       System.out.println(" new chenlin titleList........" + titleList.toString());
	 
	        List paramList = getFaultReportDAO().selReportParam(paramMap);//��ò�ѯ����
	        System.out.println("new chenlin paramList........" + paramList.toString());  
	
	        List fieldList = getFaultReportDAO().selReportField(paramMap);//���չ���ֶ�
	        System.out.println("new chenlin fieldList........" + fieldList.toString());
	
	        Map reportMap =  getFaultReportDAO().selReportInfo(paramMap);//��ñ�����
	        System.out.println("new chenlin reportMap........" + reportMap.toString());
	
	        List formulaList =  getFaultReportDAO().selReportFormula(paramMap);//��ñ���ھ�
	
	        String reportSql = MapUtils.getString(reportMap,"reportSql");    //
	
	
	        paramMap.put("paramList",paramList);
	        paramMap.put("fieldList",fieldList);
	        if(!paramMap.containsKey("reportSql")||MapUtils.getString(paramMap,"reportSql").equals(""))
	            paramMap.put("reportSql",reportSql);
	
	        Map retMap   =  getFaultReportDAO().selReportData(paramMap); //�������
	       
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
