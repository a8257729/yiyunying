package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

//import utils.system;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAO;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAOImpl;


public class SelStatDisplayAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//获取参数
		String moduleId = request.getParameter("formId");
		System.out.println("moduleId--SelStatDisplayAction-->>>>>> "+moduleId);
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("formId", moduleId);

			param.put("pageIndex", start);
			param.put("pageSize", limit);

			Map paginResultMap = getMoblieStatDisplayDAO().selById(param);

			//列表数据
			List list = (List) paginResultMap.get("resultList");
			List resultList = new ArrayList();

			if (list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Map _map = (Map) iterator.next();
					
					_map.put("isMainPageName", MapUtils.getString(_map, "isMainPage").equals("1")?"否":"是");
					_map.put("isSumName", MapUtils.getString(_map, "isSum").equals("1")?"显示":"不显示");
					_map.put("statTypeName", MapUtils.getString(_map, "statType").equals("1")?"总和/平均数等":"明细");
					
					String mapStr = MapUtils.getString(_map, "statCycle");
					if(mapStr!=null && mapStr!=""){
						String[] temp = mapStr.split("-");
						for(int i=0; i<temp.length; i++){
							if(temp[i].equals("1")){
								temp[i] = "月报";
							} else if(temp[i].equals("2")){
								temp[i] = "周报";
							} else if(temp[i].equals("3")){
								temp[i] = "日报";
							} else {
								temp[i] = "其他(" + MapUtils.getString(_map, "statCycleDisplayName") + ")";
							}
						}
						mapStr = temp[0];
						for(int i=1; i<temp.length; i++){
							mapStr += "-" + temp[i];
						}
						_map.put("statCycleName", mapStr);
					}
					
					mapStr = MapUtils.getString(_map, "statRange");
					if(mapStr!=null && mapStr!=""){
						String[] temp = mapStr.split("-");
						for(int i=0; i<temp.length; i++){
							if(temp[i].equals("1")){
								temp[i] = MapUtils.getString(_map, "prvDisplayName");
							} else if(temp[i].equals("2")){
								temp[i] = MapUtils.getString(_map, "cityDisplayName");
							}
						}
						mapStr = temp[0];
						for(int i=1; i<temp.length; i++){
							mapStr += "-" + temp[i];
						}
						_map.put("statRangeName", mapStr);
					}		
					
					//获取省级/市级对应环节名称
					List tempList = getNextFormIdName(_map);
					if (tempList != null && tempList.size() > 0) {
						Iterator iterator1 = tempList.iterator();
						while (iterator1.hasNext()) {
							Map tempMap = (Map) iterator1.next();
							_map.put("prvNextFormIdName", MapUtils.getString(tempMap, "prvNextFormIdName"));
							_map.put("cityNextFormIdName", MapUtils.getString(tempMap, "cityNextFormIdName"));
						}
					}
					resultList.add(_map);
				}
			}

			int totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");

			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
			}
			System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqq"+jsonStr);
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String format(String DateTime, String format) {
		String result = "";
		if(DateTime !=null && DateTime.length()>0){
			result = DateTime.substring(0, 19).replaceAll("-", format);
		}
		return result;
	}
	private MoblieStatDisplayDAO getMoblieStatDisplayDAO() {
		String daoName = MoblieStatDisplayDAOImpl.class.getName();
		return (MoblieStatDisplayDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private JsonCreateDAO getJsonCreateDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (JsonCreateDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private List getNextFormIdName(Map _map) throws DataAccessException{
		StringBuffer sqlStr1 = new StringBuffer();
		boolean prvNextFormIdName = false;
		boolean cityNextFormIdName = false;
		
		
		sqlStr1.append("SELECT ");
		if (MapUtils.getString(_map, "prvNextFormId")!=null && !MapUtils.getString(_map, "prvNextFormId").equals("0")) {
			sqlStr1.append(" b.FORM_NAME as prvNextFormIdName");
			prvNextFormIdName = true;
		} else {
			sqlStr1.append(" '' as prvNextFormIdName");
		}
		if (MapUtils.getString(_map, "cityNextFormId")!=null && !MapUtils.getString(_map, "cityNextFormId").equals("0")) {
			sqlStr1.append(", c.FORM_NAME as cityNextFormIdName");
			cityNextFormIdName = true;
		} else {
			sqlStr1.append(", '' as cityNextFormIdName");
		}		
		sqlStr1.append(" FROM MOBILE_JSON_CREATE b, MOBILE_JSON_CREATE c WHERE ROWNUM=1");
		if(prvNextFormIdName){
			sqlStr1.append(" AND b.FORM_ID="+MapUtils.getString(_map, "prvNextFormId"));
		}
		if(cityNextFormIdName){
			sqlStr1.append(" AND c.FORM_ID="+MapUtils.getString(_map, "cityNextFormId"));
		}
			
		return getJsonCreateDAO().selName(sqlStr1.toString());
	}
}
