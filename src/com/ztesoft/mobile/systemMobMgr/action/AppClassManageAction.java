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

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;

public class AppClassManageAction implements BaseAction{
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		String moduleId = request.getParameter("moduleId");		
		String apkIds = request.getParameter("apkIds")==null? "": (String) request.getParameter("apkIds");
		String apkCode = request.getParameter("apkCode")==null? "": (String) request.getParameter("apkCode");
		String apkType = request.getParameter("apkType")==null? "": (String) request.getParameter("apkType");
		String funcStatus = request.getParameter("funcStatus")==null? "": (String) request.getParameter("funcStatus");
		String osType = request.getParameter("osType")==null? "": (String) request.getParameter("osType");
		String flag = request.getParameter("flag");
		int limit = request.getParameter("limit")==null?200:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("moduleId", moduleId);
			param.put("apkCode", apkCode);
			param.put("apkIds", apkIds);	
			param.put("funcStatus",funcStatus);	
			
			param.put("apkType", apkType);	
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			param.put("osType", osType);
			

			Map paginResultMap = new HashMap();
			if (flag != null && flag.equals("1")){
			  paginResultMap = getAppRegMgrDAO().selByName(param);
			}else if (flag != null && flag.equals("2")){
			  paginResultMap = getAppRegMgrDAO().selApkInfoByconn(param);
			}else if (flag != null && flag.equals("3")){
			  paginResultMap = getAppRegMgrDAO().selByApkCode(param);
			}else if (flag != null && flag.equals("4")){
			  paginResultMap = getAppRegMgrDAO().selByOsType(param);
			}else if (flag != null && flag.equals("5")){
			  paginResultMap = getAppRegMgrDAO().selClassByOsType(param);
			}

			List list = (List) paginResultMap.get("resultList");
			List resultList = new ArrayList();

			
			if (list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Map _map = (Map) iterator.next();
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

	private AppRegMgrDAO getAppRegMgrDAO() {
        String daoName = AppRegMgrDAOImpl.class.getName();
        return (AppRegMgrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
