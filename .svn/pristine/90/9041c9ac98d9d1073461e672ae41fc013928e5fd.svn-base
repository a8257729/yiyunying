package com.ztesoft.mobile.systemMonitor.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMonitor.dao.MobileExceptionRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileExceptionRecordDAOImpl;


public class QryExceptionMonitorAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		//获取参数

		Long serviceId = null;
		if (request.getParameter("serviceId")!= null && !request.getParameter("serviceId").equals("")){
		  serviceId = new Long((String)request.getParameter("serviceId")); 
		}
		int flag = request.getParameter("flag")==null? -1:Integer.parseInt((String)request.getParameter("flag")); 
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String sysCode  = request.getParameter("sysCode")==null? "":(String)request.getParameter("sysCode");
		String beginDate  = request.getParameter("beginDate")==null? "":(String)request.getParameter("beginDate"); 
		String endDate  = request.getParameter("endDate")==null? "":(String)request.getParameter("endDate"); 
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			
			if (serviceId != null && !serviceId.equals("")){
			  param.put("serviceId", serviceId);
			}
			if (sysCode != null && !sysCode.equals("")){
				  param.put("sysCode", sysCode);
			}
			if (beginDate != null && !beginDate.equals("")){
			  param.put("beginDate", beginDate.substring(0, 10));
			}
			if (endDate != null && !endDate.equals("")){
			  param.put("endDate", endDate.substring(0, 10));
			}
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			

			Map paginResultMap = new HashMap();
			
			if ( flag == 1){			
		      paginResultMap = getMobileExceptionRecordDAO().selByConn(param);
			}

			if (paginResultMap != null && paginResultMap.get("resultList") != null){
				//列表数据
				List list = (List) paginResultMap.get("resultList");
				List resultList = new ArrayList();
	
				if (list != null && list.size() > 0) {
					Iterator iterator = list.iterator();
					while (iterator.hasNext()) {
						Map _map = (Map) iterator.next();
						_map.put("createTime",MapUtils.getString(_map, "createTime"));
												
						resultList.add(_map);
					}
				}
				
				
				int totalSize = 0;		
				//如果使用分页的方法就取totalSize，否则显示查询的数据数
				if (flag == 1 ){
					totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");			
						
				}else {
					totalSize = list.size();	
				}
				int totalRecords = 0;
				if(totalSize == 0){
					jsonStr = "{totalCount:0,Body:[]}";
				}else{
					if (flag == 1 ){
						totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");	
				      
					}else{
						totalRecords = list.size();				
					}
					jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
				}
				
			}
			//System.out.println("flag = "+ flag);
			//System.out.println("jsonStr = "+ jsonStr);
			response.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	 
	private MobileExceptionRecordDAO getMobileExceptionRecordDAO() {
		String daoName = MobileExceptionRecordDAOImpl.class.getName();
		return (MobileExceptionRecordDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
