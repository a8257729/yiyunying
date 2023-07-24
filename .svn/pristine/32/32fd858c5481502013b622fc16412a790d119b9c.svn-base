package com.ztesoft.eoms.oaas.privilege.manager;

import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.service.jobprivmanager.dao.JobPrivilegeDAO;
import com.zterc.uos.oaas.service.jobprivmanager.dao.JobPrivilegeDAOFactory;
import com.zterc.uos.oaas.service.privclassmanager.dao.PrivilegeClassDAOFactory;
import com.zterc.uos.oaas.vo.JobPrivilege;
import com.zterc.uos.oaas.vo.Privilege;
import com.ztesoft.eoms.oaas.privilege.util.StaffPrivJsonUtil;

public class OrgPrivManager implements SessionBean {

	private static final long serialVersionUID = -1363116901576684613L;
	
	/**
     * 根据职位查询特有权限
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 职位权限数组
     */
    public static String findPrivsJobGrantJson(int jobId) throws OAASException {
    	try {
            Privilege[] privs = JobPrivilegeDAOFactory.getDAO().
                findPrivsJobNotHold(jobId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().
                findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"可配置权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * @param loginedJobId
     * @param configJobId
     * @return
     * @throws OAASException
     */
    public static String findPrivsJobGrantJson(int loginedJobId,int configJobId) throws OAASException {
    	JobPrivilegeDAO jobPrivDAO = JobPrivilegeDAOFactory.getDAO();
        Privilege[] privs = null;
        try {
            privs = jobPrivDAO.findPrivsJobNotHold(loginedJobId, configJobId,true);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().
                findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"可配置权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * @param loginedJobId
     * @param configJobId
     * @return
     * @throws OAASException
     */
    public static String findPrivsJobGrantJson(int jobId1, int jobId2,int defaultJobId) throws OAASException {
    	JobPrivilegeDAO jobPrivDAO = JobPrivilegeDAOFactory.getDAO();
        Privilege[] privs = null;
        try {
            privs = jobPrivDAO.findPrivsJobNotHoldnew(jobId1, jobId2, defaultJobId, false);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().
                findPrivClassesMap();
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
    public static String findPrivsByJob(int jobId) throws
        OAASException {
        try {
            JobPrivilege[] jobPrivs = JobPrivilegeDAOFactory.getDAO().findPrivsByJob(jobId, true);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().
                findPrivClassesMap();
            return StaffPrivJsonUtil.getJobPrivSelJson(privClassMap, jobPrivs,"已有权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * 查询职位的所有权限(所有权限)
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 权限json字符串
     */
    public static String findAllPrivsByJob(int jobId) throws OAASException {
        try {
        	JobPrivilege[] jobPrivs = JobPrivilegeDAOFactory.getDAO().findAllPrivsByJob(jobId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().
                findPrivClassesMap();
            return StaffPrivJsonUtil.getJobPrivSelJson(privClassMap, jobPrivs,"所有权限");
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
    public static String findAllRolePrivsByJob(int jobId) throws OAASException {
        try {
            JobPrivilege[] jobPrivs = JobPrivilegeDAOFactory.getDAO().findAllRolePrivsByJob(jobId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getJobPrivSelJson(privClassMap, jobPrivs,"角色权限");
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
