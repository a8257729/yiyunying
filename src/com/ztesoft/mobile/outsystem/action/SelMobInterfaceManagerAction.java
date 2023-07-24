package com.ztesoft.mobile.outsystem.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAO;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAOImpl;

public class SelMobInterfaceManagerAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//获取参数
		Long sysId = Long.valueOf(request.getParameter("sysId"));
		int limit = request.getParameter("limit") == null ? 10: Integer.parseInt(request.getParameter("limit")); 
		int start = request.getParameter("start") == null ? 1 : Integer.parseInt(request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("sysId", sysId);
			param.put("pageIndex", start);
			param.put("pageSize", limit);

			Map resultMap = getMobInterfaceMngDAO().selByPagin(param);

			//列表数据
			List resultList = (List) MapUtils.getObject(resultMap, "resultList");
			int totalSize = MapUtils.getIntValue(resultMap, "totalSize", 0);

			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = MapUtils.getIntValue(resultMap, "totalRecords");
				jsonStr = JsonUtil.getExtGridJsonData(resultList, totalRecords);
			}

			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private MobileInterfaceManagerDAO getMobInterfaceMngDAO() {
		String daoName = MobileInterfaceManagerDAOImpl.class.getName();
		return  (MobileInterfaceManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
