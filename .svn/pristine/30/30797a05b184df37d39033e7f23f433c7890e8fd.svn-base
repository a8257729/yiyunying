package com.ztesoft.eoms.oaas.role.web;

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

public class RoleConfSelAction extends BaseActionAdapter {
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		int jobId = Integer.parseInt(request.getParameter("jobId"));
		int specialJobId = Integer.parseInt(request.getParameter("specialJobId"));
		int _jobId = Integer.parseInt(request.getParameter("_jobId"));
		int _defaultJobId = Integer.parseInt(request.getParameter("_defaultJobId"));
		int areaId = Integer.parseInt(request.getParameter("areaId"));
		String methodName = request.getParameter("methodName");
		Map result ;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			logger.debug("methodName:"+methodName);
			if("1".equals(methodName.trim())){
				logger.debug("_jobId:"+_jobId);
				logger.debug("_defaultJobId:"+_defaultJobId);
				logger.debug("areaId:"+areaId);
				
				result = getRoleDAO().findAllRolesInArea(_jobId, _defaultJobId, areaId);
			}else if("2".equals(methodName.trim())){
				result = getRoleDAO().findRolesHoldOneJobInArea(jobId, _jobId, _defaultJobId, areaId);
			}else if("3".equals(methodName.trim())){
				result = getRoleDAO().findRolesHoldOneJobInArea(specialJobId, _jobId, _defaultJobId, areaId);
			}else{
				result = getRoleDAO().findRolesHoldTwoJobInArea(jobId, specialJobId, _jobId, _defaultJobId, areaId);
			}
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
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
