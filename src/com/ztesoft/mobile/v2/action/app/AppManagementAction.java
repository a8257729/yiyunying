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
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class AppManagementAction implements BaseAction{
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		String parentId = request.getParameter("parentId")==null? "": (String) request.getParameter("parentId");
		String appId = request.getParameter("appId")==null? "": (String) request.getParameter("appId");
		String gcode = request.getParameter("gcode")==null? "": (String) request.getParameter("gcode");
		String appClass = request.getParameter("appClass")==null? "": (String) request.getParameter("appClass");
		
		String osType = request.getParameter("osType")==null? "": (String) request.getParameter("osType");
		String flag = request.getParameter("flag");
		int limit = request.getParameter("limit")==null?200:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();

			param.put("appId", appId);	
			param.put("parentId", parentId);				
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			param.put("osType", osType);
			param.put("gcode", gcode);
			param.put("appClass",appClass);
			
			//System.out.println("param------------="+param);
			//System.out.println(osType+"  osType" +" flag "+flag);
			Map paginResultMap = new HashMap();
			if (flag != null && flag.equals("1")){
			  paginResultMap = getMobileAppDAO().selByConn(param);
			}else if (flag != null && flag.equals("2")){
			 // paginResultMap = getMobileAppFunctionDAO().selByConn(param);
			}else if (flag != null && flag.equals("3")){
			
			  paginResultMap = getMobileMenuDAO().selClassByOsType(param);
			}else if (flag != null && flag.equals("4")){
			  paginResultMap = getMobileParamDAO().selForParamByConn(param);
			}else if (flag != null && flag.equals("5")){
			  paginResultMap = getMobileAppHisDAO().selByConn(param);
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
			int totalSize=0;
			if(paginResultMap.get("totalSize")!=null && !paginResultMap.get("totalSize").equals("")){
				totalSize=Integer.parseInt(paginResultMap.get("totalSize").toString());
			}

			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
			}
             System.out.println("appmaangement  =="+jsonStr);
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private MobileAppDAO getMobileAppDAO() {
        String daoName = MobileAppDAOImpl.class.getName();
        return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileAppFuncDAO getMobileAppFunctionDAO() {
        String daoName = MobileAppFuncDAOImpl.class.getName();
        return (MobileAppFuncDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileMenuDAO getMobileMenuDAO() {
        String daoName = MobileMenuDAOImpl.class.getName();
        return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileAppHisDAO getMobileAppHisDAO() {
        String daoName = MobileAppHisDAOImpl.class.getName();
        return (MobileAppHisDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
