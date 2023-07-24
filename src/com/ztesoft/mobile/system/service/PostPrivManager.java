package com.ztesoft.mobile.system.service;

import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.service.postprivmanager.dao.PostPrivilegeDAOFactory;
import com.zterc.uos.oaas.service.privclassmanager.dao.PrivilegeClassDAOFactory;
import com.zterc.uos.oaas.vo.Privilege;
import com.ztesoft.mobile.system.util.StaffPrivJsonUtil;


public class PostPrivManager implements SessionBean {

	private static final long serialVersionUID = -1363116901576684613L;
	
	/**
     * 根据职位查询特有权限
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 职位权限数组
     */
    public static String findPrivsPostGrantJson(int postId) throws OAASException {
    	try {
    		Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findPrivsPostNotHold(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"可配置权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * 查询职位的所有权限(特有权限)
     * @param jobId int 职位编号
     * @param isAll boolean 是全部还是仅可授出部分
     * @throws SQLException
     * @return String 权限json字符串
     */
    public static String findPrivsByPostJson(int postId) throws
        OAASException {
        try {
        	Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findPrivsByPost(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"已有权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * 查询职位的角色的权限
     * @param jobId int 职位编号
     * @throws SQLException
     * @return String 权限XML字符串
     * @author liangli 2007.01.23
     */
    public static String findAllRolePrivsByPostJson(int postId) throws OAASException {
        try {
        	Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findRolePrivsByPost(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"角色权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
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
