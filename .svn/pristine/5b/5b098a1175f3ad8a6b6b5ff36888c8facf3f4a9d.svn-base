package com.ztesoft.mobile.v2.action.app;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppFuncDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuConfigDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class SelAppFunctionAction implements BaseAction {

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

	private MobileMenuConfigDAO getMobileMenuConfigDAO() {
		String daoName = MobileMenuConfigDAOImpl.class.getName();
		return (MobileMenuConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map paramMap = new HashMap();
			String osType = request.getParameter("osType");
			String flag = request.getParameter("flag");
			String menuId = request.getParameter("menuId");
			String jsonStr = "{totalCount:0,Body:[]}";
			String selfun = request.getParameter("selfun");
			String seleAppType=request.getParameter("seleAppType");
			if(selfun!=null && selfun.equals("selfun")){//用于查询app下的fun类路径
			Map	map =new HashMap();
			 map.put("appId", request.getParameter("appId"));
				List resMap=  getMobileAppFunctionDAO().selFunClass(map);
			//	JSONArray json = JSONArray.fromObject(resMap);

				jsonStr = JsonUtil.getExtGridJsonData(resMap);
		    	System.out.println("jsonFun  = "+jsonStr.toString());
		    	response.getWriter().write(jsonStr.toString());
		    	response.getWriter().flush();
			   return null;
			}
			 if(seleAppType!=null && seleAppType.equals("select")){ //查询应用类型
				 
				 Map	map =new HashMap();
				 map.put("appClass", request.getParameter("appClass"));
				 System.out.println(map);
					List resMap=  getMobileAppFunctionDAO().selClassForType(map);
				//	JSONArray json = JSONArray.fromObject(resMap);

					jsonStr = JsonUtil.getExtGridJsonData(resMap);
			    	System.out.println("jsonFun  = "+jsonStr.toString());
			    	response.getWriter().write(jsonStr.toString());
			    	response.getWriter().flush();
				   return null;
			 }                                     
			
			
			int limit = request.getParameter("pageSize") == null ? 10 : Integer
					.parseInt((String) request.getParameter("pageSize"));
			int start = request.getParameter("start") == null ? 1 : Integer
					.parseInt((String) request.getParameter("start"))
					/ limit + 1;
			String parentId= request.getParameter("menuId");
			System.out.println("parentId  "+parentId);
			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);
			paramMap.put("osType", osType);
			paramMap.put("parentId", parentId);
			paramMap.put("menuId", menuId);
			paramMap.put("flag", flag);
            paramMap.put("appClass", "appClass");
			
			Map resultMap = getMobileAppFunctionDAO().pageSel(paramMap);
			List list = (List) resultMap.get("resultList");
			int totalSize = MapUtils.getIntValue(resultMap, "totalSize", 0);
			if(list!=null && list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					map.put("buildTime", MapUtils.getString(map, "buildTime"));
				}
			}
			if (null != list && !list.isEmpty()) {
				resultMap.put("resultList", list);
				resultMap.put("totalSize", totalSize);
				resultMap.put("totalRecords", resultMap.get("totalRecords"));
			}
	//		System.out.println(resultMap.get("totalRecords"));
			if (totalSize != 0) {
				int totalRecords = Integer.parseInt(MapUtils.getString(
						resultMap, "totalRecords"));
				jsonStr = JsonUtil.getExtGridJsonData((List) MapUtils
						.getObject(resultMap, "resultList"), totalRecords);
			}

			response.getWriter().write(jsonStr);
			//System.out.println(jsonStr+ ((List)resultMap.get("resultList")).size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
