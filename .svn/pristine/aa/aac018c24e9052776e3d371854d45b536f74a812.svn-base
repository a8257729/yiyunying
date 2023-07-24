package com.ztesoft.eoms.im.action;

import java.io.IOException;
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
public class UpdateOnlineStateAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		
		UserTransaction userTransaction1  = null;
		try {
			request.setCharacterEncoding("UTF-8");
			userTransaction1 = TransactionHelper.getTransaction();
			userTransaction1.begin();
			response.setContentType("text/html;charset=UTF-8");
			Long imStaffOnlineId = (Long)request.getSession().getAttribute("imStaffOnlineId");
			Map paramMap = new HashMap();
			paramMap.put("imStaffOnlineId", imStaffOnlineId);
			paramMap.put("stateId", request.getParameter("stateId"));
			paramMap.put("outlineDate",new Date());
			getImStaffOnlineDAO().update(paramMap);
			userTransaction1.commit();

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
		} catch (DataAccessException e) {
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
