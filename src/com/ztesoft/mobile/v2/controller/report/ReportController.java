package com.ztesoft.mobile.v2.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.entity.interf.MobileRequestData;
import com.ztesoft.mobile.v2.service.interf.MobileFaultReportService;

@Controller("ReportController")
public class ReportController {
	private MobileFaultReportService mobileFaultReportService;
	public MobileFaultReportService getMobileFaultReportService(){
		return mobileFaultReportService;
	}
	@Autowired(required = false)
	public void setMobileFaultReportService(
			MobileFaultReportService mobileFaultReportService) {
		this.mobileFaultReportService = mobileFaultReportService;
	}
	@RequestMapping(value = {"/busi/stat/hubei/stat"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getReportList(@RequestBody  MobileRequestData mobileRequestData,HttpServletRequest request, HttpServletResponse response) throws Exception{
		System.out.println("Begin chenlin is " + mobileRequestData.getType());
		String type = mobileRequestData.getType();
		List<Map> resultList=  (List<Map>) this.getMobileFaultReportService().getReportList(type);		
		Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
		System.out.println("chenlin 001 resultList is " + resultList);
		
		if(null != resultList && 0!= resultList.size()&&"orderTimelyRateList".equals(type)) {
			List<Object> areaNameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	
    		List<Object> dataList2 = new ArrayList<Object>(resultList.size());	
    		List<Object> dataList3 = new ArrayList<Object>(resultList.size());
    		for(int i=0; i<resultList.size()- 1; i++) {
    			Map m = resultList.get(i);
    			
    				String areaName = MapUtils.getString(m, "AREA_NAME");
    				int sum = Integer.parseInt(MapUtils.getString(m, "SUM"));
    				int timeNum = Integer.parseInt(MapUtils.getString(m, "TIME"));
    				//String rate = MapUtils.getString(m, "RATE");
    				Double rate;
    				if(sum == 0 &&timeNum ==0 ){
    					rate  = 100.0;
    				}
    				else
    					rate = Math.round((((float)timeNum)/sum)*1000)/10.0;
    			

    				System.out.println("¹ÊÕÏµ¥£ºareaName£º " + areaName);
    				System.out.println("¹ÊÕÏµ¥£ºsum£º " + sum);
    				System.out.println("¹ÊÕÏµ¥£ºtimeNum£º " + timeNum);
    				System.out.println("¹ÊÕÏµ¥£ºrate£º " + rate);
    				areaNameList.add(areaName);
    				dataList1.add(sum);
    				dataList2.add(timeNum);
    				dataList3.add(rate);
    			
    		}
    		resultMap.put("AREA_NAME",areaNameList);
    		resultMap.put("SUM",dataList1);
    		resultMap.put("TIME",dataList2);
    		resultMap.put("RATE",dataList3);
		}
		else if(null != resultList && 0!= resultList.size()&& "faultPromptRate".equals(type)) {
			List<Object> areaNameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	
    		List<Object> dataList2 = new ArrayList<Object>(resultList.size());	
    		List<Object> dataList3 = new ArrayList<Object>(resultList.size());
    		for(int i=0; i<resultList.size()- 1; i++) {
    			Map m = resultList.get(i);
    			
    				String areaName = MapUtils.getString(m, "FAULTAREANAME");
    				int sum = Integer.parseInt(MapUtils.getString(m, "ORDERAMOUNT"));
    				int timeNum = sum - Integer.parseInt(MapUtils.getString(m, "ALARMNUM"));
    				Double rate;
    				if(sum == 0 && timeNum == 0 ){
    					rate  = 100.0;
    				}
    				else
    					rate = Math.round((((float)timeNum)/sum)*1000)/10.0;
    				System.out.println("¹ÊÕÏµ¥£ºareaName£º " + areaName);
    				System.out.println("¹ÊÕÏµ¥£ºsum£º " + sum);
    				System.out.println("¹ÊÕÏµ¥£ºtimeNum£º " + timeNum);
    				System.out.println("¹ÊÕÏµ¥£ºrate£º " + rate);
    				areaNameList.add(areaName);
    				dataList1.add(sum);
    				dataList2.add(timeNum);
    				dataList3.add(rate);
    			
    		}
    		resultMap.put("AREA_NAME",areaNameList);
    		resultMap.put("SUM",dataList1);
    		resultMap.put("TIME",dataList2);
    		resultMap.put("RATE",dataList3);
			
		}
		else if(null != resultList && 0!= resultList.size()&& "faultBokReport".equals(type)) {
			List<Object> areaNameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataSum = new ArrayList<Object>(resultList.size());	
    		List<Object> dataBokNum = new ArrayList<Object>(resultList.size());  	
    		List<Object> dataOnTimeNum = new ArrayList<Object>(resultList.size());    		
    		List<Object> dataBokRate = new ArrayList<Object>(resultList.size());     		
    		List<Object> dataOnTimeRate = new ArrayList<Object>(resultList.size());  
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String areaName = MapUtils.getString(m, "AREANAME");
    			int sum = Integer.parseInt(MapUtils.getString(m, "ORDERAMOUNT"));
    			int bokNum = Integer.parseInt(MapUtils.getString(m, "BOKNUM"));
    			int onTimeNum = Integer.parseInt(MapUtils.getString(m, "ONTIMENUM"));
    			Double bokRate ; 
    			Double onTimeRate;
    			if(sum == 0 && bokNum == 0)
    				bokRate = 100.0;
    			else
    				bokRate = Math.round((((float)bokNum)/sum)*1000)/10.0;
    			if(bokNum == 0 && onTimeNum == 0 )
    				onTimeRate = 100.0;
    			else
    				onTimeRate = Math.round((((float)onTimeNum)/bokNum)*1000)/10.0; 
    			
    			areaNameList.add(areaName);	
    			dataSum.add(sum);
    			dataBokNum.add(bokNum);
    			dataOnTimeNum.add(onTimeNum);
    			dataBokRate.add(bokRate);
    			dataOnTimeRate.add(onTimeRate);
    			
    			}
    		resultMap.put("AREA_NAME",areaNameList);
    		resultMap.put("SUM",dataSum);
    		resultMap.put("BOKNUM",dataBokNum);
    		resultMap.put("ONTIMENUM",dataBokNum);
    		resultMap.put("BOKRATE",dataBokRate);
    		resultMap.put("ONTIMERATE",dataOnTimeRate);
		}		
		
		
		System.out.println("END Here " + resultMap);
		return resultMap;
		
	}

}
