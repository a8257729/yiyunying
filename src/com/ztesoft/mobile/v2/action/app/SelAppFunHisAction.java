package com.ztesoft.mobile.v2.action.app;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppHisDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class SelAppFunHisAction implements BaseAction {

	

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		try {
			 Map paramMap = DedicatedActionContext.getParams();
		         paramMap = MapUtils.getMap(paramMap, "parameter_1");
		         if(paramMap==null){
		        	 paramMap=new HashMap();
		         }
			String osType = request.getParameter("osType");
			String appType = request.getParameter("appType");
			String flag = request.getParameter("flag");
			String menuId = request.getParameter("menuId");
			String jsonStr = "{totalCount:0,Body:[]}";
			String selfun = request.getParameter("selfun");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate=request.getParameter("beginDate");
			String endDate = request.getParameter("endDate");
			if(beginDate != null && !beginDate.equals("")){
				beginDate=beginDate.replace("T", " ");
			}
			if(endDate != null && !endDate.equals("")){
				endDate=endDate.replace("T", " ");
			}
			paramMap.put("beginDate", beginDate);
			paramMap.put("endDate", endDate);
			int limit = request.getParameter("pageSize") == null ? 10 : Integer
					.parseInt((String) request.getParameter("pageSize"));
			int start = request.getParameter("start") == null ? 1 : Integer
					.parseInt((String) request.getParameter("start"))
					/ limit + 1;
			if(osType==null || osType.equals("")){
				osType="1";
			}
			String parentId= request.getParameter("menuId");
			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);
			paramMap.put("osType", osType);
			paramMap.put("appType", appType);
			paramMap.put("flag", flag);
            paramMap.put("appClass", request.getParameter("appClass"));
            paramMap.put("appName", request.getParameter("appname"));
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			Map resultMap  =new HashMap();
			System.out.println(selfun+"      selfun  ");
			
		
			if(selfun==null || selfun.equals("")){
				System.out.println("param ="+paramMap);
				resultMap= getMobileAppHisDAO().getAppFunMap(paramMap);//查询应用表
			}else{
				paramMap.put("appId", request.getParameter("appId"));
				resultMap=getMobileAppFunctionDAO().selFunPage(paramMap);//查询功能表
				
			}
			List list = (List) resultMap.get("resultList");
		//	System.out.println("list  "+list.size());
			int totalSize = MapUtils.getIntValue(resultMap, "totalSize", 0);
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					map.put("updateTime", MapUtils.getString(map, "updateTime"));
					map.put("buildTime", MapUtils.getString(map, "buildTime"));
					map.put("stateDate", MapUtils.getString(map, "stateDate"));
				}
			}
		//	System.out.println(null != list && !list.isEmpty());
			if (null != list && !list.isEmpty()) {
				resultMap.put("resultList", list);
				resultMap.put("totalSize", totalSize);
				resultMap.put("totalRecords", resultMap.get("totalRecords"));
			}
	//		 System.out.println(totalSize);
			if (totalSize != 0) {
				int totalRecords = Integer.parseInt(MapUtils.getString(
						resultMap, "totalRecords"));
				jsonStr = JsonUtil.getExtGridJsonData((List) MapUtils
						.getObject(resultMap, "resultList"), totalRecords);
			}

			response.getWriter().write(jsonStr);
	//		System.out.println(jsonStr+ ((List)resultMap.get("resultList")).size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private MobileAppFuncDAO getMobileAppFunctionDAO() {
		String daoName = MobileAppFuncDAOImpl.class.getName();
		return (MobileAppFuncDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileMenuDAO getMobileMenuDAO() {
		String daoName = MobileMenuDAOImpl.class.getName();
		return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileAppDAO getMobileAppDAO() {
		String daoName = MobileAppDAOImpl.class.getName();
		return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileMenuConfigDAO getMobileMenuConfigDAO() {
		String daoName = MobileMenuConfigDAOImpl.class.getName();
		return (MobileMenuConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileAppHisDAO getMobileAppHisDAO() {
		String daoName = MobileAppHisDAOImpl.class.getName();
		return (MobileAppHisDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
