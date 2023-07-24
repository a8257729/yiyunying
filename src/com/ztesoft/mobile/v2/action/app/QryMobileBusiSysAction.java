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
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileStaffMappingDAOImpl;

public class QryMobileBusiSysAction implements BaseAction{
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		String flag = request.getParameter("flag");
		int limit = request.getParameter("limit")==null?200:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
	
			param.put("pageIndex", start);
			param.put("pageSize", limit);


			Map paginResultMap = new HashMap();
			if (flag != null && flag.equals("1")){
			  paginResultMap = getMobileBusiSysDAO().selByConn(param);
			}

			List list = (List) paginResultMap.get("resultList");
			List resultList = new ArrayList();

			
			if (list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Map _map = (Map) iterator.next();
					if (flag != null && (flag.equals("1")||flag.equals("5"))){
						_map.put("buildTime", MapUtils.getString(_map, "buildTime"));
						_map.put("updateTime", MapUtils.getString(_map, "updateTime"));
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

			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private MobileBusiSysDAO getMobileBusiSysDAO() {
        String daoName = MobileBusiSysDAOImpl.class.getName();
        return (MobileBusiSysDAO) BaseDAOFactory.getImplDAO(daoName);
    }

}
