package com.ztesoft.mobile.etl.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.etl.dao.CleanRuleConfigDAO;
import com.ztesoft.mobile.etl.dao.CleanRuleConfigDAOImpl;

/**
 * description :数据清理规则组件查询Action
 * @author FL
 *
 */
public class QryRoleComListAction implements BaseAction {

	public Object doAction(HttpServletRequest request,HttpServletResponse response) {
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		
		//处理起始条数
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		
		String jsonStr = "" ;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			Map retMap = getCleanRuleConfigDAO().getComListByRole(paramMap,start,limit);
			
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				List resList = (List)retMap.get("resultList");
				jsonStr = JsonUtil.getExtGridJsonData(resList,Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";
			}
			
			System.out.println("QryRoleComListAction jsonStr:----->: " + jsonStr);
			
			response.getWriter().write(jsonStr);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public CleanRuleConfigDAO getCleanRuleConfigDAO(){
		String daoName = CleanRuleConfigDAOImpl.class.getName();
		return (CleanRuleConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
