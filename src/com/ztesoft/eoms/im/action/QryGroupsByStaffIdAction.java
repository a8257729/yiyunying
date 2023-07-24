package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupDAOImpl;

import com.ztesoft.eoms.util.JsonUtil;

public class QryGroupsByStaffIdAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
	
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Staff staff = (Staff)request.getSession().getAttribute("staff");
			int staffId = staff.getStaffId();
			
			List list = getImStaffBiggroupDAO().selGroupsByStaffId(staffId);
			Iterator it = list.iterator();
			while(it.hasNext()){
				Map map = (Map)it.next();
				map.put("iconCls", "group_icon");
				map.put("leaf", "true");
				map.put("text", map.get("imStaffBiggroupName"));
				map.put("id", map.get("imStaffBiggroupId"));
				
			}
			
			response.getWriter().write(JsonUtil.getJsonByList(list));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	private ImStaffBiggroupDAO getImStaffBiggroupDAO() {
		String daoName = ImStaffBiggroupDAOImpl.class.getName();
		return (ImStaffBiggroupDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}

