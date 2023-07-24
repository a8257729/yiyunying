package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.OrgTmpDAO;
import com.ztesoft.mobile.system.dao.OrgTmpDAOImpl;



public class OrgTmpRoleGridAction implements BaseAction {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		int postId = Integer.parseInt((String)request.getParameter("postId"));
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map result = getOrgTmpDAO().findRolesByPost(postId);
			int totalSize = Integer.parseInt(result.get("totalSize")+"");
			
			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(result.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData((List)result.get("resultList"),totalRecords);
			}
			System.out.println("jsonStr:"+jsonStr);
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

	private OrgTmpDAO getOrgTmpDAO() {
        String daoName = OrgTmpDAOImpl.class.getName();
        return (OrgTmpDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
