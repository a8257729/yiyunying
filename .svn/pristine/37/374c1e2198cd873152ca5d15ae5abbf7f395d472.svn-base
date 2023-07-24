package com.ztesoft.mobile.v2.action.rest;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAOImpl;
import com.ztesoft.mobile.v2.dao.rest.MobileRestServiceDAO;
import com.ztesoft.mobile.v2.dao.rest.MobileRestServiceDAOImpl;

public class SelRestServiceAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {

		try {
			Map paramMap = new HashMap();
		    
		Staff u=	(Staff) request
		.getSession().getAttribute("staff");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String difualBeginDate = sdf.format(new Date()) + " 00:00:00";
		String difualEndDate = sdf.format(new Date()) + " 23:59:59";
		String beginDate=request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String servStaus=request.getParameter("servStaus");
		String servName=request.getParameter("servName");
		String servAddr = request.getParameter("servAddr");
//		if(beginDate == null || beginDate.equals("")){
//			beginDate=difualBeginDate;
//		}
//		if(endDate == null || endDate.equals("")){
//			endDate=difualEndDate;
//		}
		
		paramMap.put("servName",servName);
		paramMap.put("servAddr", servAddr);
//		paramMap.put("beginDate", beginDate.replace("T", " "));
//		paramMap.put("endDate", endDate.replace("T", " "));
		String actionType = request.getParameter("actionType") == null ? "0"
				: request.getParameter("actionType");
		String pagin = request.getParameter("pagin");
		int limit = request.getParameter("pageSize") == null ? 15 : Integer
				.parseInt((String) request.getParameter("pageSize"));
		int start = request.getParameter("start") == null ? 1 : Integer
				.parseInt((String) request.getParameter("start"))
				/ limit + 1;
		paramMap.put("pageIndex", start);
		paramMap.put("pageSize", limit);
		paramMap.put("servStaus", servStaus);
		Map resultMap = new HashMap();
		 resultMap = getMobileRestServiceDAO().selPageMap(paramMap);
		List list = (List) resultMap.get("resultList");
	if(list!=null && list.size()>0){
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			map.put("stateDate", MapUtils.getString(map, "stateDate"));
			map.put("updateTime", MapUtils.getString(map, "updateTime"));
			map.put("buildTime", MapUtils.getString(map, "buildTime"));
		}
	}
		String jsonStr = "{totalCount:0,Body:[]}";

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	
		int totalSize = MapUtils.getIntValue(resultMap, "totalSize", 0);
		if (null != list && !list.isEmpty()) {
			resultMap.put("resultList", list);
			resultMap.put("totalSize",totalSize);
			resultMap.put("totalRecords",resultMap.get("totalRecords"));

		}
	
		if (totalSize != 0) {
			int totalRecords = Integer.parseInt(MapUtils.getString(
					resultMap, "totalRecords"));
			jsonStr = JsonUtil.getExtGridJsonData((List) MapUtils
					.getObject(resultMap, "resultList"), totalRecords);
		}

		response.getWriter().write(jsonStr);

	} catch (Exception e) {
		e.printStackTrace();
		
	}
		
		return null;
	}

	private MobileRestServiceDAO getMobileRestServiceDAO() {
		String daoName = MobileRestServiceDAOImpl.class.getName();
		return (MobileRestServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	
}
