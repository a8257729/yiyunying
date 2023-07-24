package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.common.helper.TransactionHelper;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffGroupRealDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAO;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAOImpl;

import com.ztesoft.eoms.util.JsonUtil;

import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;

public class QryOnlineStaffGroupAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserTransaction userTransaction1  = null;
		try {
			request.setCharacterEncoding("UTF-8");
			userTransaction1 = TransactionHelper.getTransaction();
			userTransaction1.begin();
			response.setContentType("text/html;charset=UTF-8");
			Session [] sessions = SessionManager.getInstance().getSessions();
			List retlist = new ArrayList();//数据拼装
			//查询个人分组
			Staff staff = (Staff)request.getSession().getAttribute("staff");
			Map staffMap = new HashMap();
			staffMap.put("staffId", new Long(staff.getStaffId()));
			List grouplist = null;//好友分组列表
			grouplist = getImStaffGroupDAO().selGroupByStaff(staffMap);
			
				if(grouplist.size()==0){//如果自己没有建立分组则插入默认分组
					Map insertMap = new HashMap();
					insertMap.put("imStaffGroupName", "我的好友");
					insertMap.put("staffId", new Long(staff.getStaffId()));
					Map groupMap = getImStaffGroupDAO().insert(insertMap);
					grouplist.add(groupMap);
					Map jsonGroupMap = new HashMap();
					jsonGroupMap.put("id", "group_"+groupMap.get("imStaffGroupId").toString());
					jsonGroupMap.put("text", groupMap.get("imStaffGroupName"));
					jsonGroupMap.put("iconCls", "group_icon");
					jsonGroupMap.put("leaf", false);
					jsonGroupMap.put("children", new ArrayList());
					retlist.add(jsonGroupMap);
				}else{
						
					//根据好友分组查询好友列表
					for(int i=0;i<grouplist.size();i++){
						Map groupMap = (Map)grouplist.get(i);
						Map jsonGroupMap = new HashMap();
						jsonGroupMap.put("id", "group_"+groupMap.get("imStaffGroupId").toString());
						jsonGroupMap.put("text", groupMap.get("imStaffGroupName"));
						jsonGroupMap.put("iconCls", "group_icon");
						jsonGroupMap.put("leaf", false);
						jsonGroupMap.put("children", new ArrayList());
						
						List staffList = null;
						try {
							staffList = getImStaffGroupRealDAO().selStaffsByGroupId(groupMap);
						} catch (DataAccessException e) {

							e.printStackTrace();
						}
						//判断好友的在线状态
						for(int j=0;j<staffList.size();j++){
							Map staffGroupMap = (Map)staffList.get(j);
							Map jsonStaffMap = new HashMap();
							jsonStaffMap.put("id",staffGroupMap.get("imFriStaffId").toString());
							jsonStaffMap.put("text", staffGroupMap.get("staffName"));
							jsonStaffMap.put("leaf", true);
							int stateId = 0;
							for(int k=0;k<sessions.length;k++){
								if(staffGroupMap.get("imFriStaffId").toString().trim().equals(sessions[k].getId().trim())){
									Long imStaffOnlineId = (Long)sessions[k].getHttpSession().getAttribute("imStaffOnlineId");
									Map qryMap = new HashMap();
									qryMap.put("imStaffOnlineId", imStaffOnlineId);
									Map onlineMap = getImStaffOnlineDAO().selById(qryMap);
									if (null!=onlineMap)
									  stateId = (new Integer(onlineMap.get("stateId").toString())).intValue();
									break;
								}
								
							}
							if(stateId==1){//在线
								jsonStaffMap.put("iconCls", "online_icon");
								
							}else if(stateId == 2){//隐身不加入在线列表
								jsonStaffMap.put("iconCls", "outline_icon");
								
							}else if(stateId == 3){//繁忙
								jsonStaffMap.put("iconCls", "busy_icon");
								
							}else if(stateId == 0){//离线
								jsonStaffMap.put("iconCls", "outline_icon");
								
							}
							((List)jsonGroupMap.get("children")).add(jsonStaffMap);
							
							
						}
						retlist.add(jsonGroupMap);
						//将在线的好友排到前面
						
						
					}
					
				}

				userTransaction1.commit();
				response.getWriter().write(JsonUtil.getJsonByList(retlist));
				response.getWriter().flush();
				response.getWriter().close();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicMixedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private ImStaffOnlineDAO getImStaffOnlineDAO() {
		String daoName = ImStaffOnlineDAOImpl.class.getName();
		return (ImStaffOnlineDAO) BaseDAOFactory.getImplDAO(daoName);
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
