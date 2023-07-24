package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAO;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;

import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;

public class QryOnlineStaffsAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Session [] sessions = SessionManager.getInstance().getSessions();
			List list = new ArrayList();		
			for(int i =0;i<sessions.length;i++){
				Staff staff = (Staff)sessions[i].getHttpSession().getAttribute("staff");			
				//查询人员状态
				Long imStaffOnlineId = (Long)sessions[i].getHttpSession().getAttribute("imStaffOnlineId");
				Map qryMap = new HashMap();
				qryMap.put("imStaffOnlineId", imStaffOnlineId);

				Map onlineMap =  getImStaffOnlineDAO().selById(qryMap);
				Map map = new HashMap();
				
				map.put("id", staff.getStaffId());
				map.put("text", staff.getStaffName());
				map.put("userName", staff.getUserName());
				map.put("leaf", "true");

                int stateId = 0;
                if (null!=onlineMap)
				  stateId = (new Integer(onlineMap.get("stateId").toString())).intValue();
				map.put("stateId", stateId);

				if(stateId==1){//在线
					map.put("iconCls", "online_icon");
					list.add(map);
				}else if(stateId == 2){//隐身不加入在线列表
					map.put("iconCls", "outline_icon");
				}else if(stateId == 3){//繁忙
					map.put("iconCls", "busy_icon");
					list.add(map);
				}else if(stateId == 0){//离线
					map.put("iconCls", "outline_icon");
				}
				
			}

			//在线列表进行排序 在线人员排前面
			Collections.sort(list, new OnLineStaffComparator());
			//输出数据
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
	private ImStaffOnlineDAO getImStaffOnlineDAO() {
		String daoName = ImStaffOnlineDAOImpl.class.getName();
		return (ImStaffOnlineDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	class OnLineStaffComparator implements Comparator{

		public int compare(Object o1, Object o2) {
			Map map1 = (Map)o1;
			Map map2 = (Map)o2;
			if(((Integer)map1.get("stateId")).intValue()<((Integer)map2.get("stateId")).intValue()){
				return -1;
			}else if(((Integer)map1.get("stateId")).intValue()==((Integer)map2.get("stateId")).intValue()){
				return 0;
			}else{
				return 1;
			}
			
		}
		
		
	}
}

