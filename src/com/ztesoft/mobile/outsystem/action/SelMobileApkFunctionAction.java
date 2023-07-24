package com.ztesoft.mobile.outsystem.action;

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
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAO;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAOImpl;

public class SelMobileApkFunctionAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//获取参数 
		String apkCode = request.getParameter("apkCode")==null?"":request.getParameter("apkCode");
		String verId = request.getParameter("verId")==null?"":request.getParameter("verId");
		int limit = request.getParameter("limit")==null?50:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("apkCode", apkCode);
			param.put("verId", verId);
			param.put("pageIndex", start);
			param.put("pageSize", limit);
			//System.out.println("=====[debug]======param: "+param.toString());
			Map paginResultMap = getMobileApkFunctionDAO().selById(param);

			//列表数据
			List resultList = (List) paginResultMap.get("resultList");
//			List resultList = new ArrayList();
// 
//			if (list != null && list.size() > 0) {
//				Iterator iterator = list.iterator();
//				while (iterator.hasNext()) {
//					Map _map = (Map) iterator.next();
//					_map.put("createDate", format(MapUtils.getString(_map, "createDate"),"-"));
//
//					resultList.add(_map);
//				}
//			}
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
	
	private MobileApkFunctionDAO getMobileApkFunctionDAO() {
		String daoName = MobileApkFunctionDAOImpl.class.getName();
		return (MobileApkFunctionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
