package com.ztesoft.mobile.v2.action.app;

import java.io.IOException;
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
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAOImpl;


public class QryMobileFrameAppAction implements BaseAction{
	
	public static String QRY_MOBILE_PARAM="qryMobileFrameAppAction";
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
	    Integer osType = 1;
		if(request.getParameter("osType")!=null &&!request.getParameter("osType").equals("") ){
	              osType=Integer.parseInt(request.getParameter("osType"));
	    }
		Integer isRelease = null;
		   if(request.getParameter("isRelease")!=null && !request.getParameter("isRelease").equals("")){
			   isRelease=Integer.parseInt(request.getParameter("isRelease"));
		   }
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			param.put("osType", osType);
			param.put("isRelease", isRelease);
			
			Map paginResultMap = new HashMap();
			
			paginResultMap = getMobileFrameAppDAO().selByPage(param);
			if (paginResultMap != null){
				int totalSize = 0;		
				//如果使用分页的方法就取totalSize，否则显示查询的数据数
				totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");	
				if (totalSize == 0){
					jsonStr = "{totalCount:0,Body:[]}";
				} else if (paginResultMap.get("resultList") != null){
					List list = (List) paginResultMap.get("resultList");
					List resultList = new ArrayList();
					
					if (list != null && list.size() > 0) {
						Iterator iterator = list.iterator();
						while (iterator.hasNext()) {
							Map _map = (Map) iterator.next();
							_map.put("buildTime", format(MapUtils.getString(_map, "buildTime"),"/"));
							_map.put("updateTime", format(MapUtils.getString(_map, "updateTime"),"/"));
	
							resultList.add(_map);
						}
					}
					int totalRecords = 0;
						totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");	
						jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
				}
				response.getWriter().write(jsonStr);
			}
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
			result = DateTime.substring(0, 19).replaceAll("/", format);
		}
		return result;
	}
	
	private MobileFrameAppDAO getMobileFrameAppDAO() {
		String daoName = MobileFrameAppDAOImpl.class.getName();
        return (MobileFrameAppDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
