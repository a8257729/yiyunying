package com.ztesoft.mobile.etl.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.etl.dao.ScheduleMngDAO;
import com.ztesoft.mobile.etl.dao.ScheduleMngDAOImpl;

public class QryScheCatalogAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);					//获取查询参数
		
		System.out.println("QryScheCatalogAction paramMap: "+paramMap);
		String parnetId = request.getParameter("node");
		
		paramMap.put("parnetId", parnetId);
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			List cataList = getScheduleMngDAO().getScheCatalog(paramMap); 
			if(cataList != null && cataList.size()>0){
				List treeCataList = new ArrayList();
				Map tm = null;
				for(int i=0;i<cataList.size();i++){
					Map m = (Map)cataList.get(i);
					tm = m;		//自定义属性
					tm.put("id", m.get("scheCatalogId"));
					tm.put("text", m.get("scheCatalogName"));
					tm.put("singleClickExpand", Boolean.TRUE);		//点击展开
					if("Y".equals(m.get("isLeaf"))){
						tm.put("leaf",Boolean.TRUE);
						tm.put("cls","file");
					}else{
						tm.put("leaf",Boolean.FALSE);
						tm.put("cls","folder");
					}
					treeCataList.add(tm);
				}
				jsonStr = JsonUtil.getJsonByList(treeCataList);
				
			}else{
				jsonStr = "{totalCount:0,Body:[]}";	
			}
			
			System.out.println("QryScheCatalogAction =====================jsonStr: "+jsonStr);
			
			response.getWriter().write(jsonStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ScheduleMngDAO getScheduleMngDAO() {
		String daoName = ScheduleMngDAOImpl.class.getName();
		return (ScheduleMngDAOImpl) BaseDAOFactory.getImplDAO(daoName);
	}

}
