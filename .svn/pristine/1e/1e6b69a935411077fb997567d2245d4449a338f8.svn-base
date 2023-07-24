package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

public class QryMyGroupsAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Staff staff = (Staff)request.getSession().getAttribute("staff");
			Map staffMap = new HashMap();
			staffMap.put("staffId", new Long(staff.getStaffId()));
		
			List grouplist = getImStaffGroupDAO().selGroupByStaff(staffMap);
			response.getWriter().write(JsonUtil.getJsonByList(grouplist));
				response.getWriter().flush();
				response.getWriter().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	private ImStaffGroupDAO getImStaffGroupDAO() {
		String daoName = ImStaffGroupDAOImpl.class.getName();
		return (ImStaffGroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private ImStaffGroupRealDAO getImStaffGroupRealDAO() {
		String daoName = ImStaffGroupRealDAOImpl.class.getName();
		return (ImStaffGroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
