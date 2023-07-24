package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.MobileSearchTabDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobileSearchTabDAOImpl;

public class SelSearchTabAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//获取参数
		String moduleId = request.getParameter("formId");
		System.out.println("moduleId--SelSearchTabAction=====================================-->>>>>> "+moduleId);
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("formId", moduleId);

			param.put("pageIndex", start);
			param.put("pageSize", limit);


			System.out.println("=====================getMobileSearchTabDAO().selById===========================");
			Map paginResultMap = getMobileSearchTabDAO().selById(param);
			System.out.println("=====================selById==end==============================================");
			//列表数据
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
			System.out.println("====================="+jsonStr+"===========================");
			response.getWriter().write(jsonStr);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}


	private MobileSearchTabDAO getMobileSearchTabDAO() {
		String daoName = MobileSearchTabDAOImpl.class.getName();
		return (MobileSearchTabDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}