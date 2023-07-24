package com.ztesoft.eoms.oaas.privilege.manager;

import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.service.privclassmanager.dao.PrivilegeClassDAOFactory;
import com.zterc.uos.oaas.service.roleprivmanager.dao.RolePrivilegeDAOFactory;
import com.zterc.uos.oaas.vo.Privilege;
import com.ztesoft.eoms.oaas.privilege.util.StaffPrivJsonUtil;

public class RolePrivManager implements SessionBean {

	private static final long serialVersionUID = -6017722282284139637L;

	// 查询一个角色所有的权限
	public static String findPrivsByRoleJson(int roleId) throws OAASException {
		try {
			Privilege[] privs = RolePrivilegeDAOFactory.getDAO()
					.findPrivsByRole(roleId);
			Map privClassMap = PrivilegeClassDAOFactory.getDAO()
					.findPrivClassesMap();
			return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"角色所有权限");
		} catch (SQLException ex) {
			OAASError error = new OAASError(OAASError.ROLE_FIND_ERROR);
			throw new OAASException(error, ex);
		}
	}

	// 查询在所有的权限中，角色所不具有的权限
	public static String findPrivsRoleNotHoldJson(int roleId) throws OAASException {
		try {
			Privilege[] privs = RolePrivilegeDAOFactory.getDAO()
					.findPrivsRoleNotHold(roleId);
			Map privClassMap = PrivilegeClassDAOFactory.getDAO()
					.findPrivClassesMap();
			return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"角色可配置权限");
		} catch (SQLException ex) {
			OAASError error = new OAASError(OAASError.ROLE_FIND_ERROR);
			throw new OAASException(error, ex);
		}
	}

	// 查询在所有的权限中，角色所不具有的权限
	public static String findPrivsRoleNotHoldJson(int jobId, int roleId) throws OAASException {
		try {
			Privilege[] privs = RolePrivilegeDAOFactory.getDAO()
					.findPrivsRoleNotHold(jobId, roleId, false);
			Map privClassMap = PrivilegeClassDAOFactory.getDAO()
					.findPrivClassesMap();
			return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"角色可配置权限");
		} catch (SQLException ex) {
			OAASError error = new OAASError(OAASError.ROLE_FIND_ERROR);
			throw new OAASException(error, ex);
		}

	}

	public static String findPrivsRoleNotHoldJson(int jobId1, int jobId2, int roleId) throws OAASException {
		try {
			Privilege[] privs = RolePrivilegeDAOFactory.getDAO().findPrivsRoleNotHold(jobId1, jobId2, roleId, false);
			Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
			return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"角色可配置权限");
		} catch (SQLException ex) {
			OAASError error = new OAASError(OAASError.ROLE_FIND_ERROR);
			throw new OAASException(error, ex);
		}
	}

	SessionContext sessionContext;

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}
}
