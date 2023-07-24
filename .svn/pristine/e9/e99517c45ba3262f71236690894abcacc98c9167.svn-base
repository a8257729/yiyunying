package com.ztesoft.mobile.systemMonitor.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ztesoft.mobile.systemMonitor.dao.MobileServerMonitorDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileServerMonitorDAOImpl;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAOImpl;

//提供服务监控的查询功能

public class QryServerMonitorAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		//获取参数

//		Long serviceId = null;
//		if (request.getParameter("serviceId")!= null && !request.getParameter("serviceId").equals("")){
//		  serviceId = new Long((String)request.getParameter("serviceId")); 
//		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		int flag = request.getParameter("flag")==null? -1:Integer.parseInt((String)request.getParameter("flag")); 
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String restServiceId = request.getParameter("restServiceId")==null?"":(String)request.getParameter("restServiceId"); 
		String username  = request.getParameter("username")==null? "":(String)request.getParameter("username");
		String inTimestamp  = request.getParameter("inTimestamp")==null? "":(String)request.getParameter("inTimestamp"); 
		String outTimestamp  = request.getParameter("outTimestamp")==null? "":(String)request.getParameter("outTimestamp"); 
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();

			if (inTimestamp != null && !inTimestamp.equals("")){
			  param.put("inTimestamp", dateFormatter.parse(inTimestamp).getTime());
			}
			if (outTimestamp != null && !outTimestamp.equals("")){
			  param.put("outTimestamp", dateFormatter.parse(outTimestamp).getTime());
			}
			if (username != null && !username.equals("")){
				  param.put("username", username);
				}
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			param.put("restServiceId", restServiceId);
			
			Map paginResultMap = new HashMap();
			
			if ( flag == 1){			
		      paginResultMap = getMobileServerMonitorDAO().selByConn(param);
			} else if (flag == 2) {
				paginResultMap = getMobileServerMonitorDAO().selRestServiceByConn(param);
			} else if (flag == 3) {
				paginResultMap = getMobileServerMonitorDAO().selAvgNumRestService(param);
			}

			if (paginResultMap != null && paginResultMap.get("resultList") != null){
				//列表数据
				List list = (List) paginResultMap.get("resultList");
				List resultList = new ArrayList();
	
				if (list != null && list.size() > 0) {
					Iterator iterator = list.iterator();
					while (iterator.hasNext()) {
						Map _map = (Map) iterator.next();
						if (flag ==1) {
							_map.put("consumeTime", MapUtils.getLong(_map, "outTimestamp") - MapUtils.getLong(_map, "inTimestamp"));
							_map.put("inTimestamp",formatter.format(new Date(MapUtils.getLong(_map, "inTimestamp"))));
							_map.put("outTimestamp",formatter.format(new Date(MapUtils.getLong(_map, "outTimestamp"))));
						} else if (flag == 3) {
							_map.put("totalConsumeTime", MapUtils.getLong(_map, "sumOutTimestamp") - MapUtils.getLong(_map, "sumInTimestamp"));
							_map.put("avgConsumeTime", MapUtils.getLong(_map, "totalConsumeTime")/MapUtils.getLong(_map, "countNum"));
						}
						
						resultList.add(_map);
					}
				}
				
				System.out.println(resultList);
				
				int totalSize = 0;		
				//如果使用分页的方法就取totalSize，否则显示查询的数据数
				if (flag == 1 || flag == 2 || flag == 3){
					totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");			
						
				}else {
					totalSize = list.size();	
				}
				int totalRecords = 0;
				if(totalSize == 0){
					jsonStr = "{totalCount:0,Body:[]}";
				}else{
					if (flag == 1 || flag == 3){
						totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");	
				      
					}else{
						totalRecords = list.size();				
					}
					jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
				}
				
			}
			//System.out.println("flag = "+ flag);
			System.out.println("jsonStr = "+ jsonStr);
			response.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private MobileServerMonitorDAO getMobileServerMonitorDAO() {
		String daoName = MobileServerMonitorDAOImpl.class.getName();
		return (MobileServerMonitorDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
