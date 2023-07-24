package com.ztesoft.eoms.im.action;

import java.util.Date;
import java.util.HashMap;
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

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.ext.paging.BaseAction;
import com.ztesoft.eoms.common.helper.TransactionHelper;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAO;
import com.ztesoft.eoms.im.dao.ImStaffOnlineDAOImpl;


public class LogoutIMAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		UserTransaction userTransaction;
		try {
			userTransaction = TransactionHelper.getTransaction();
			userTransaction.begin();
			Map removeMap = new HashMap();
			Long imStaffOnlineId = (Long)request.getSession().getAttribute("imStaffOnlineId");
			removeMap.put("imStaffOnlineId", imStaffOnlineId);
			removeMap.put("stateId", new Integer(0));
			removeMap.put("outlineDate", new Date());
			System.out.println("removeMap: "+removeMap);
			
			getImStaffOnlineDAO().update(removeMap);
			userTransaction.commit();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
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
	
}
