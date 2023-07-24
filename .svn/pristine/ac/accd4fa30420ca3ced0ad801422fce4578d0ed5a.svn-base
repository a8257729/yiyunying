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
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAO;
import com.ztesoft.mobile.etl.dao.ExtEtlRuleDAOImpl;

public class QryUosTacheCataTreeAction implements BaseAction {


	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);					//由于乱码，这里有一句注释删了
		
		System.out.println("QryUosTacheCataTreeAction paramMap: "+paramMap);
		String parnetId = request.getParameter("node");
		
		if(!"-1".equals(parnetId)){
			paramMap.put("parnetId", parnetId);
		}
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			List cataList = getExtEtlRuleDAO().qryUosTacheCata(paramMap); 
			if(cataList != null && cataList.size()>0){
				List treeCataList = new ArrayList();
				Map tm = null;
				for(int i=0;i<cataList.size();i++){
					Map m = (Map)cataList.get(i);
					tm = m;		//由于乱码，这里有一句注释删了
					tm.put("id", m.get("CATALOGID"));
					tm.put("text", m.get("CATALOGNAME"));
					tm.put("singleClickExpand", Boolean.TRUE);		//由于乱码，这里有一句注释删了
					if("Y".equals(m.get("ISLEAF"))){
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
			
			System.out.println("QryUosTacheCataTreeAction =====================jsonStr: "+jsonStr);
			
			response.getWriter().write(jsonStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ExtEtlRuleDAO getExtEtlRuleDAO() {
		String daoName = ExtEtlRuleDAOImpl.class.getName();
		return (ExtEtlRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
