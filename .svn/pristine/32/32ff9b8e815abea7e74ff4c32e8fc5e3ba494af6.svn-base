package com.ztesoft.mobile.outsystem.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAO;
import com.ztesoft.mobile.outsystem.dao.OuterSystemDAOImpl;

public class QryOuterSystemForTreeAction implements BaseAction{

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		System.out.println("调用【QryOuterSystemForTreeAction】，输入参数是："+paramMap);
		
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			
			List list = getOutSystemDAO().queryOuterSystemForTree(paramMap);
			if(null != list && 0 != list.size()){
				jsonStr = JsonUtil.getJsonByList(list);
			}else{
				jsonStr = "{totalCount:0,Body:[]}";	
			}
			//System.out.println("QryOuterSystemForTreeAction jsonStr:----->: " + jsonStr);
			
			response.getWriter().write(jsonStr);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
				
		return null;
	}
	
	public OuterSystemDAO getOutSystemDAO(){
		String daoName = OuterSystemDAOImpl.class.getName();
		
		return (OuterSystemDAO) BaseDAOFactory.getImplDAO(daoName);
	}


}
