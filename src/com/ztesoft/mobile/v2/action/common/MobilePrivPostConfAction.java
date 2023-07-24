package com.ztesoft.mobile.v2.action.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.v2.dao.menu.MobilePostPrivDAO;
import com.ztesoft.mobile.v2.dao.menu.MobilePostPrivDAOImpl;

public class MobilePrivPostConfAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		int postId = request.getParameter("postId")==null? -1:Integer.parseInt(request.getParameter("postId"));
		String staffId = request.getParameter("staffId");
		String jsonStr = "";

		//response.setContentType("text/html;charset=UTF-8");
		//int moduleId = Integer.parseInt(request.getParameter("node"));
		String type = request.getParameter("type");
		System.out.println("MobilePrivPostConfAction moduleId :" +"---type="+type+"--postId="+postId);
	
		Map result = new HashMap();

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

            if (type.equals("haspriv")){
		      result = getMobilePostPrivDAO().getAllPostPrivData(postId);
            }else if (type.equals("allpriv")){
              result = getMobilePostPrivDAO().getAllSubPostPrivData(postId);
            }
			

			List dlist = (List)result.get("resultList");
			List nlist = new ArrayList();
			if(dlist.size()>0){
				Map mapFunc = new HashMap();
				mapFunc.put("id", -10);
				mapFunc.put("text", "²Ëµ¥");
				mapFunc.put("leaf", false);
				mapFunc.put("children", (List)result.get("resultList"));
				nlist.add(mapFunc);
			}

			jsonStr = JsonUtil.getJsonByList(nlist);
			System.out.println("MobilePrivPostConfAction jsonStr:"+jsonStr);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}

		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private MobilePostPrivDAO getMobilePostPrivDAO() {
		String daoName = MobilePostPrivDAOImpl.class.getName();
		return (MobilePostPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
