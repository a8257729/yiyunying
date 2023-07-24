package com.ztesoft.eoms.oaas.org.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseActionAdapter;
import com.ztesoft.eoms.oaas.role.dao.RoleDAO;
import com.ztesoft.eoms.oaas.role.impl.RoleDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class JobRolesGridAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		//获取参数
		//int jobId = Integer.parseInt((String)request.getParameter("jobId"));
		String jobId = request.getParameter("jobId");
		String jsonStr = "";
		try {
			if(jobId != null && !"".equals(jobId)){
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				Map result = getRoleDAO().getRoleGridByJob(Integer.parseInt(jobId)) ;
				int totalSize = Integer.parseInt(result.get("totalSize")+"");
				
				if(totalSize == 0){
					jsonStr = "{totalCount:0,Body:[]}";
				}else{
					int totalRecords = Integer.parseInt(result.get("totalRecords")+"");
					jsonStr = JsonUtil.getExtGridJsonData((List)result.get("resultList"),totalRecords);
				}	
			}
			logger.debug("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			logger.debug(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private RoleDAO getRoleDAO() {
        String daoName = RoleDAOImpl.class.getName();
        return (RoleDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
