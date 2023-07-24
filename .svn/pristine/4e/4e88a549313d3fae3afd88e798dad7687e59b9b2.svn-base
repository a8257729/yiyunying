package com.ztesoft.mobile.v2.service.interf;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.dao.interf.MobileFaultReportDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileFaultReportDAOImpl;

@Service("mobileFaultReportService")
public class MobileFaultReportServiceImpl implements MobileFaultReportService {

	public List getReportList(String type)throws DataAccessException {
		// TODO Auto-generated method stub
			Map paramMap = new  HashMap();
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
			String  dateNow=sdf.format(today);
			today.setDate(1);
			today.setHours(0);
			today.setMinutes(0);
			today.setSeconds(0);
			String  dateStart =sdf.format(today);
			paramMap.put("reportCode",type);
			paramMap.put("beginTime", dateStart);
			paramMap.put("endTime", dateNow);
//			paramMap.put("beginTime", "2013-05-01 00:00:00");
//			paramMap.put("endTime", "2013-05-31 23:59:59");
		 	System.out.println("new chenlin paramMap........" + paramMap.toString());
	        List titleList =  getmFaultReportDAO().selReportTitle(paramMap);//获得表头
	        //System.out.println(" new chenlin titleList........" + titleList.toString());
	 
	        List paramList = getmFaultReportDAO().selReportParam(paramMap);//获得查询参数
	        //System.out.println("new chenlin paramList........" + paramList.toString());  
	
	        List fieldList = getmFaultReportDAO().selReportField(paramMap);//获得展现字段
	        //System.out.println("new chenlin fieldList........" + fieldList.toString());
	
	        Map reportMap =  getmFaultReportDAO().selReportInfo(paramMap);//获得报表定义
	        //System.out.println("new chenlin reportMap........" + reportMap.toString());
	
	        List formulaList =  getmFaultReportDAO().selReportFormula(paramMap);//获得报表口径
	
	        String reportSql = MapUtils.getString(reportMap,"reportSql"); 	
	        System.out.println("chenlin reportSql =  " + reportSql);
	        paramMap.put("paramList",paramList);
	        paramMap.put("fieldList",fieldList);
	        if(!paramMap.containsKey("reportSql")||MapUtils.getString(paramMap,"reportSql").equals(""))
	            paramMap.put("reportSql",reportSql);
	        List retList = (List) getmFaultReportDAO().selReportData(paramMap);
	        
		return retList;
	}
	private MobileFaultReportDAO getmFaultReportDAO(){
		String daoName = MobileFaultReportDAOImpl.class.getName();
		return (MobileFaultReportDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
