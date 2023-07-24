package com.ztesoft.eoms.im.action;


import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImChatLogDAO;
import com.ztesoft.eoms.im.dao.ImChatLogDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;
import com.ztesoft.iom.orderdesign.helpers.DateHelper;
import com.ztesoft.mobile.common.helper.ParamUtils;

public class QryChatLogPagingAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		ParamUtils paramUtils = new ParamUtils();
		Map paramMap = paramUtils.getMapByRequest(request);
		System.out.println("paramMap: "+paramMap);
		//处理起始条数
		int limit = Integer.parseInt((String)request.getParameter("limit")); 
		int start = Integer.parseInt((String)request.getParameter("start"))/limit+1;
		paramMap.put("start", new Integer(start));
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map retMap =getImChatLogDAO().selChatLog(paramMap);
			//转时间格式
			if(retMap.get("resultList")!=null){
				Iterator it = ((List)retMap.get("resultList")).iterator();
				while(it.hasNext()){
					Map map = (Map)it.next();
					map.put("msgDate", DateHelper.parse((Date)map.get("msgDate")));
				}
			}
			
			if(Integer.parseInt(retMap.get("totalSize").toString())!=0){
				jsonStr = JsonUtil.getExtGridJsonData((List)retMap.get("resultList"),Integer.parseInt(retMap.get("totalRecords").toString()));
			}else{
				jsonStr = "{totalCount:0,Body:[]}";
				
			}
			response.getWriter().write(jsonStr);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private ImChatLogDAO getImChatLogDAO() {
        String daoName = ImChatLogDAOImpl.class.getName();
        return (ImChatLogDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
