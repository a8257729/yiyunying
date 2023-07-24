package com.ztesoft.eoms.im.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.common.helper.TransactionHelper;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAO;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAOImpl;
import com.ztesoft.eoms.util.JsonUtil;
import com.ztesoft.mobile.common.helper.ParamUtils;

public class QryStaffByGroupIdAction implements BaseAction{
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
			ParamUtils paramUtils = new ParamUtils();
			Map paramMap = paramUtils.getMapByRequest(request);
			System.out.println("paramMap: "+paramMap);
			List list = getImStaffBiggroupRealDAO().selStaffsByGroupId(paramMap);
			
			Iterator it = list.iterator();
			while(it.hasNext()){
				Map map = (Map)it.next();
				map.put("id",map.get("staffId"));
				map.put("text",map.get("staffName"));
				map.put("leaf",true);
				int stateId = 0;
				for(int k=0;k<sessions.length;k++){
					if(map.get("staffId").toString().trim().equals(sessions[k].getId().trim())){
						Long imStaffOnlineId = (Long)sessions[k].getHttpSession().getAttribute("imStaffOnlineId");
						Map qryMap = new HashMap();
						qryMap.put("imStaffOnlineId", imStaffOnlineId);
						Map onlineMap = getImStaffOnlineDAO().selById(qryMap);
						stateId = (new Integer(onlineMap.get("stateId").toString())).intValue();
						break;
					}
					
				}
				if(stateId==1){//在线
					map.put("iconCls", "online_icon");
					
				}else if(stateId == 2){//隐身不加入在线列表
					map.put("iconCls", "outline_icon");
					
				}else if(stateId == 3){//繁忙
					map.put("iconCls", "busy_icon");
					
				}else if(stateId == 0){//离线
					map.put("iconCls", "outline_icon");
					
				}
				retlist.add(map);
				
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
	private ImStaffBiggroupRealDAO getImStaffBiggroupRealDAO() {
		String daoName = ImStaffBiggroupRealDAOImpl.class.getName();
		return (ImStaffBiggroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private ImStaffOnlineDAO getImStaffOnlineDAO() {
		String daoName = ImStaffOnlineDAOImpl.class.getName();
		return (ImStaffOnlineDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	
}
