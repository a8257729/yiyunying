package com.ztesoft.mobile.system.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.system.dao.RoleDAO;
import com.ztesoft.mobile.system.dao.RoleDAOImpl;



public class RoleConfSelAction implements BaseAction {
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
			System.out.println("methodName:"+methodName);
			if("1".equals(methodName.trim())){
				System.out.println("_jobId:"+_jobId);
				System.out.println("_defaultJobId:"+_defaultJobId);
				System.out.println("areaId:"+areaId);
				
				result = getRoleDAO().findAllRolesInArea(_jobId, _defaultJobId, areaId);
			}else if("2".equals(methodName.trim())){
				result = getRoleDAO().findRolesHoldOneJobInArea(jobId, _jobId, _defaultJobId, areaId);
			}else if("3".equals(methodName.trim())){
				result = getRoleDAO().findRolesHoldOneJobInArea(specialJobId, _jobId, _defaultJobId, areaId);
			}else{
				result = getRoleDAO().findRolesHoldTwoJobInArea(jobId, specialJobId, _jobId, _defaultJobId, areaId);
			}
			
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
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

	private RoleDAO getRoleDAO() {
        String daoName = RoleDAOImpl.class.getName();
        return (RoleDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
