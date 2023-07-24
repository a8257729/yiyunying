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

public class QryAllEtlRuleAction  implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		//System.out.println("QryAllServicesDefineAction paramMap: "+paramMap);
		//处理起始条数
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			Map retMap = getExtEtlRuleDAO().queryAllEtlRule(paramMap,start,limit);
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				List resList = (List)retMap.get("resultList");
				List defList = new ArrayList();
				for(int i=0;i<resList.size();i++){
					Map defMap = (Map) resList.get(i);	
					String fetchType=String.valueOf(defMap.get("fetchType"));
					if(fetchType!=null&&fetchType.equalsIgnoreCase("1")){
						defMap.put("fetchTypeText", "表抽取");
						}else {
							defMap.put("fetchTypeText", "存储过程");
					}		
					String fetchMode=String.valueOf(defMap.get("fetchMode"));
					if(fetchMode!=null&&fetchMode.equalsIgnoreCase("1")){
						defMap.put("fetchModeText", "全量");
						}else {
							defMap.put("fetchModeText", "增量");
					}		
					defMap.put("stateText", getStateText((String)defMap.get("state")));
					defList.add(defMap);
				}
				jsonStr = JsonUtil.getExtGridJsonData(defList,Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";	
			}
			
			System.out.println("QryAllServicesDefineAction jsonStr:----->: " + jsonStr);
			
			response.getWriter().write(jsonStr);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	
	private String getStateText(String state){
		
		if("10A".equals(state)) 	return "有效";
		
		if("10P".equals(state)) 	return "无效";
		
		return "";
	}
	
	
	
	
	
	public ExtEtlRuleDAO getExtEtlRuleDAO(){
		String daoName = ExtEtlRuleDAOImpl.class.getName();
		
		return (ExtEtlRuleDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}