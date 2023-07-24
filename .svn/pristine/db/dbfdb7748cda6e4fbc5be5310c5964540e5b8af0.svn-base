package com.ztesoft.mobile.v2.controller.stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.dao.common.MobileDownloadLogDAO;
import com.ztesoft.mobile.v2.dao.common.MobileDownloadLogDAOImpl;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

import edu.emory.mathcs.backport.java.util.Collections;

@Controller("statController")
public class StatController {
	
	private MobileRestServService mobileRestServService;
	
	private MobileCommonService mobileCommonService;
	
	public MobileRestServService getMobileRestServService() {
		return mobileRestServService;
	}
	
    private MobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

    @Autowired(required = false)
	public void setMobileCommonService(MobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}
	
	@Autowired(required = false)
	public void setMobileRestServService(MobileRestServService mobileRestServService) {
		this.mobileRestServService = mobileRestServService;
	}

	/**
	 * 服务监控统计
	 * @param requestEntity
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/web/stat/shanghai/serv"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getRestServStatList(
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    		
    	List<Map> resultList=  (List<Map>) this.getMobileRestServService().getRestServStatList();
    	
    	Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
    	if(null != resultList && 0!= resultList.size()) {
    		List<Object> nameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	//调用次数
    		List<Object> dataList2 = new ArrayList<Object>(resultList.size());	//耗时统计
    		List<Object> dataList3 = new ArrayList<Object>(resultList.size());	//调用频度
    		
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String name = MapUtils.getString(m, "servName");
    			Long data1 = MapUtils.getLong(m, "invokeNum");
    			Long data2 = MapUtils.getLong(m, "costTime");
    			Double data3 = new Double(data1)/resultList.size();
    			
    			nameList.add(i, name);
    			dataList1.add(i, data1);
    			dataList2.add(i, data2);
    			dataList3.add(i, data3);
    		}
    		
    		resultMap.put("nameList", nameList);
    		resultMap.put("dataList1", dataList1);
    		resultMap.put("dataList2", dataList2);
    		resultMap.put("dataList3", dataList3);
    	}
    	
        return resultMap;
    }
    
    /**
	 * 下载次数统计
	 * @param requestEntity
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/web/stat/shanghai/serv/downloadcount"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getDownloadCountStatList(
    		HttpServletRequest request, HttpServletResponse response) throws Exception {


    	Map paramMap = new HashMap<String, String>();
    	List<Map> resultList=  (List<Map>) this.getMobileCommonService().getAppDownloadCount(paramMap);
    	
    	Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
    	if(null != resultList && 0!= resultList.size()) {
    		List<Object> nameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	//下载次数
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String name = MapUtils.getString(m, "fileName");
    			String versionName = MapUtils.getString(m, "versionName");
    			Long data1 = MapUtils.getLong(m, "downloadCount");
    			
    			nameList.add(i, name + "V" + versionName);
    			dataList1.add(i, data1);
    		}
    		
    		resultMap.put("nameList", nameList);
    		resultMap.put("dataList1", dataList1);
    	}
    	
        return resultMap;
    }
    
    /**
	 * 按日期下载次数统计
	 * @param requestEntity
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/web/stat/shanghai/serv/downloaddate"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getDownloadDateStatList(
    		HttpServletRequest request, HttpServletResponse response) throws Exception {


    	Map paramMap = new HashMap<String, String>();
    	List<Map> resultList=  (List<Map>) this.getMobileCommonService().getAppDownloadLatestMonthStat(paramMap);
    	
    	List<Map> monthResultList=  (List<Map>) this.getMobileCommonService().getAppDownloadPerMonthStat(paramMap);
    	
    	Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
    	if(null != resultList && 0!= resultList.size()) {
    		List<Object> nameList = new ArrayList<Object>();
    		List<Object> dataList1 = new ArrayList<Object>();	//APP统计
    		List<Object> dataList2 = new ArrayList<Object>();	//FRAME_APP统计
    		
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String name = MapUtils.getString(m, "downloadTime");
    			Long downloadObjType = MapUtils.getLong(m, "downloadObjType");
    			Long data1 = 0l;
    			Long data2 = 0l;
    			if (downloadObjType == 1) {
    				data1 = MapUtils.getLong(m, "downloadCount");
    				
    				if (i < resultList.size() - 1) {
    					String nextName = MapUtils.getString(resultList.get(i+1), "downloadTime");
    					// 下条记录时间相同
    					if (nextName.equals(name)) {
    						data2 = MapUtils.getLong(resultList.get(i+1), "downloadCount");
    						i++;
    					}
    				}
    				
    			} else if (downloadObjType == 2) {
    				data2 = MapUtils.getLong(m, "downloadCount");
    				
    				if (i < resultList.size() - 1) {
    					String nextName = MapUtils.getString(resultList.get(i+1), "downloadTime");
    					// 下条记录时间相同
    					if (nextName.equals(name)) {
    						data1 = MapUtils.getLong(resultList.get(i+1), "downloadCount");
    						i++;
    					}
    				}
    			}
    			
    			nameList.add(name);
    			dataList1.add(data1);
    			dataList2.add(data2);
    		}
    		
    		resultMap.put("nameList", nameList);
    		resultMap.put("dataList1", dataList1);
    		resultMap.put("dataList2", dataList2);
    	}
    	
    	// 按每月统计
    	if(null != monthResultList && 0!= monthResultList.size()) {
    		List<Object> monthNameList = new ArrayList<Object>();
    		List<Object> dataList3 = new ArrayList<Object>();	//APP统计
    		List<Object> dataList4 = new ArrayList<Object>();	//APP统计
    		
    		for(int i=0; i<monthResultList.size(); i++) {
    			Map m = monthResultList.get(i);
    			String name = MapUtils.getString(m, "downloadTime");
    			Long downloadObjType = MapUtils.getLong(m, "downloadObjType");
    			Long data3 = 0l;
    			Long data4 = 0l;
    			if (downloadObjType == 1) {
    				data3 = MapUtils.getLong(m, "downloadCount");
    				if (i < monthResultList.size() - 1) {
    					String nextName = MapUtils.getString(monthResultList.get(i+1), "downloadTime");
    					// 下条记录时间相同
    					if (nextName.equals(name)) {
    						data4 = MapUtils.getLong(monthResultList.get(i+1), "downloadCount");
    						i++;
    					}
    				}
    				
    			} else if (downloadObjType == 2) {
    				data4 = MapUtils.getLong(m, "downloadCount");
    				
    				if (i < monthResultList.size() - 1) {
    					String nextName = MapUtils.getString(monthResultList.get(i+1), "downloadTime");
    					// 下条记录时间相同
    					if (nextName.equals(name)) {
    						data3 = MapUtils.getLong(monthResultList.get(i+1), "downloadCount");
    						i++;
    					}
    				}
    			}
    			monthNameList.add(name);
    			dataList3.add(data3);
    			dataList4.add(data4);
    		}
    		
    		resultMap.put("monthNameList", monthNameList);
    		resultMap.put("dataList3", dataList3);
    		resultMap.put("dataList4", dataList4);
    	}
        return resultMap;
    }
    
    /**
	 * 各服务流量监控统计
	 * @param requestEntity
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/web/stat/shanghai/serv/flow"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getRestServiceFlowStatList(
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    		
    	List<Map> resultList=  (List<Map>) this.getMobileRestServService().getRestServFlowStatList();
    	
    	Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
    	if(null != resultList && 0!= resultList.size()) {
    		List<Object> nameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	//服务流量使用
    		
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String name = MapUtils.getString(m, "servName");
    			Long data1 = MapUtils.getLong(m, "totalFlow");
    			
    			nameList.add(i, name);
    			dataList1.add(i, data1);
    		}
    		
    		resultMap.put("nameList", nameList);
    		resultMap.put("dataList1", dataList1);
    	}
    	
        return resultMap;
    }
    
    /**
	 * 人员使用流量监控统计
	 * @param requestEntity
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/web/stat/shanghai/serv/flow/staff"})
    @FilterTag
    public @ResponseBody Map<String, List<Object>> getStaffFlowStatList(
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    		
    	List<Map> resultList=  (List<Map>) this.getMobileRestServService().getStaffFlowStatList();
    	
    	Map<String, List<Object>> resultMap = new HashMap<String, List<Object>>();
    	if(null != resultList && 0!= resultList.size()) {
    		List<Object> nameList = new ArrayList<Object>(resultList.size());
    		List<Object> dataList1 = new ArrayList<Object>(resultList.size());	//人员流量使用
    		
    		for(int i=0; i<resultList.size(); i++) {
    			Map m = resultList.get(i);
    			String name = MapUtils.getString(m, "staffName");
    			Long data1 = MapUtils.getLong(m, "totalFlow");
    			
    			nameList.add(i, name);
    			dataList1.add(i, data1);
    		}
    		
    		resultMap.put("nameList", nameList);
    		resultMap.put("dataList1", dataList1);
    	}
    	
        return resultMap;
    }
}
