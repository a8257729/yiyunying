package com.ztesoft.mobile.message.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.message.dao.MobileMessageDAO;
import com.ztesoft.mobile.message.dao.MobileMessageDAOImpl;

public class SelMobileMessageAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");
		
		String orgId = request.getParameter("orgId") ==null? "":(String)request.getParameter("orgId");;
		String beginDate = request.getParameter("beginDate")==null? "":(String)request.getParameter("beginDate");;
		String endDate = request.getParameter("endDate")==null? "":(String)request.getParameter("endDate");;
		String staffId = request.getParameter("staffId")==null? "":(String)request.getParameter("staffId");;
		String publicType = request.getParameter("publicType")==null? "":(String)request.getParameter("publicType");;
		
		
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

		System.out.println("调用【SelMobileMessageAction】, 参数是：" + limit +"," + start + "," + optype + ", " +  pagin);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/json;charset=UTF-8");  
			response.setHeader("Cache-Control","no-cache");  

			Map paramMap = new HashMap();
			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);
			paramMap.put("publicType", publicType);
			
			if (orgId != null && !orgId.equals("")){
				paramMap.put("orgId", orgId);
			}
			if (staffId != null && !staffId.equals("")){
				paramMap.put("staffId", staffId);
			}			
			if (beginDate != null && !beginDate.equals("")){
				paramMap.put("beginDate", beginDate.substring(0, 10));
			}
			if (endDate != null && !endDate.equals("")){
				paramMap.put("endDate", endDate.substring(0, 10));
			}

			Map resultMap = new HashMap();

			if("ALL".equals(optype)) {

				if("Y".equals(pagin)) {
					resultMap = this.getMobileMessageDAO().selByConn(paramMap);
					List list = (List)MapUtils.getObject(resultMap, "resultList");
					List resultList = new ArrayList();
					
					if (list != null && list.size() > 0) {
						Iterator iterator = list.iterator();
						while (iterator.hasNext()) {
							Map _map = (Map) iterator.next();
							_map.put("createTime",MapUtils.getString(_map, "createTime"));
	                        							
							resultList.add(_map);
						}
					}
					resultMap.put("resultList", resultList);
				} else if("N".equals(pagin)) {
					List list = this.getMobileMessageDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
				}

			} else if("ONE".equals(optype)) {
				Map dataMap = this.getMobileMessageDAO().selById(paramMap);
				if(null != dataMap && !dataMap.isEmpty()) {
					List list = new ArrayList();
					list.add(dataMap);

					resultMap.put("resultList", list);
					resultMap.put("totalSize", list.size());
					resultMap.put("totalRecords", list.size());
				}
			} else {
				throw new Exception("optType操作类型不正确：" + optype);
			}

			int totalSize =MapUtils.getIntValue(resultMap, "totalSize", 0);

			if(totalSize != 0){
				Integer totalRecords = Integer.parseInt(MapUtils.getString(resultMap, "totalRecords"));
				
				Map<String, Object> jsonMap = new HashMap<String, Object>();
				jsonMap.put("totalCount", totalRecords);
				jsonMap.put("Body", (List)MapUtils.getObject(resultMap, "resultList"));
				jsonStr = JSONObject.fromObject(jsonMap).toString();
			}

			System.out.println("输出jsonStr: " + jsonStr);

			response.getWriter().write(jsonStr);
			response.getWriter().flush();
			response.getWriter().close();

		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private MobileMessageDAO getMobileMessageDAO() {
		String daoName = MobileMessageDAOImpl.class.getName();
		return (MobileMessageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
