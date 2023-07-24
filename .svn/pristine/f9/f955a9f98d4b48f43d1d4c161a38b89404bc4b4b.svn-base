package com.ztesoft.mobile.systemMonitor.action;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAOImpl;

//提供连接监控的查询功能

public class QryConnectMonitorAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		//获取参数

		Long connectState = null;
		Long connectLimit = null;
		if (request.getParameter("connectState")!= null && !request.getParameter("connectState").equals("")){
			connectState = new Long((String)request.getParameter("connectState")); 
		}
		if (request.getParameter("connectLimit")!= null && !request.getParameter("connectLimit").equals("")){
			connectLimit = new Long((String)request.getParameter("connectLimit")); 
		}
		int flag = request.getParameter("flag")==null? -1:Integer.parseInt((String)request.getParameter("flag")); 
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String username  = request.getParameter("username")==null? "":(String)request.getParameter("username");
		String beginDate  = request.getParameter("beginDate")==null? "":(String)request.getParameter("beginDate"); 
		String endDate  = request.getParameter("endDate")==null? "":(String)request.getParameter("endDate"); 
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();

			param.put("username", username);
			if (connectLimit != null && !connectLimit.equals("")){
				  param.put("connectLimit", connectLimit);
			}
			if (connectState != null && !connectState.equals("")){
			  param.put("connectState", connectState);
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
		      paginResultMap = getMobileSessionRecordDAO().selByConn(param);
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
						_map.put("lastVisitTime",MapUtils.getString(_map, "lastVisitTime"));
                        if (MapUtils.getString(_map, "connectState")!= null && !MapUtils.getString(_map, "connectState").equals("")){
                        	String connectState1 = MapUtils.getString(_map, "connectState");
                        	if (connectState1.equals("0")){
                        		_map.put("connectState","离线");
                        	}else if (connectState1.equals("1")){
                        		_map.put("connectState","在线");
                        	}else if (connectState1.equals("2")){
                        		_map.put("connectState","休眠");
                        	}
                        }
                        if (MapUtils.getString(_map, "connectLimit")!= null && !MapUtils.getString(_map, "connectLimit").equals("")){
                        	String connectLimit1 = MapUtils.getString(_map, "connectLimit");
                        	if (connectLimit1.equals("1")){
                        		_map.put("connectLimit","限制接入");
                        	}else {
                        		_map.put("connectLimit","无限制");
                        	}
                        }else {
                        	_map.put("connectLimit","无限制");                     	
                        }
						
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

	 
	private MobileSessionRecordDAO getMobileSessionRecordDAO() {
		String daoName = MobileSessionRecordDAOImpl.class.getName();
		return (MobileSessionRecordDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
