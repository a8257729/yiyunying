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

public class StaffPrivManager implements SessionBean {

	private static final long serialVersionUID = 880331849742115054L;
	
	/**
     * 查询2个职位不具有的权限，权限范围是全体权限
     * @param jobId int 职位编号
     * @throws OAASException
     * @return String 职位权限数组
     */
    public static String findPrivsJobNotHoldJson(int jobId, int defaultJobId) throws OAASException {
        try {
        	
            Privilege[] privs = JobPrivilegeDAOFactory.getDAO().findPrivsJobNotHold(jobId, defaultJobId);
            
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
     * 查询一个职位有，而另外2个职位没有的权限
     * @param jobId1 int 职位编号1
     * @param jobId2 int 职位编号2
     * @param defaultJobId int 职位编号3
     * @param boolean isAll 所有权限还是仅仅能授出的权限
     * @throws OAASException
     * @return 权限对象Xml字符串
     */
    public static String findPrivsJobNotHoldJson(int jobId1, int jobId2,
                                            int defaultJobId,
                                            boolean isAll) throws
        OAASException {

        JobPrivilegeDAO jobPrivDAO = JobPrivilegeDAOFactory.getDAO();
        Privilege[] privs = null;
        try {
            privs = jobPrivDAO.findPrivsJobNotHoldnew(jobId1, jobId2,
                defaultJobId, isAll);
            
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
     * 返回两个职位有，而另一个职位没有的权限
     * @param jobId1 int 职位编号1
     * @param jobId2 int 职位编号2
     * @param jobId3 int 职位编号3
     * @param boolean isAll 所有权限还是仅仅能授出的权限
     * @throws OAASException
     * @return 权限对象Xml字符串
     */
    public static String findPrivsJobNotHoldJson(int jobId1, int jobId2, int jobId3,
                                         int defaultJobId,
                                         boolean isAll) throws
        OAASException {
        JobPrivilegeDAO jobPrivDAO = JobPrivilegeDAOFactory.getDAO();
        Privilege[] privs = null;
        try {
            privs = jobPrivDAO.findPrivsJobNotHold(jobId1, jobId2, jobId3,defaultJobId,isAll);
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
     * @return String 权限XML字符串
     */
    public static String findPrivsByJob(int jobId, boolean isAll) throws
        OAASException {
        try {
            JobPrivilege[] jobPrivs = JobPrivilegeDAOFactory.getDAO().findPrivsByJob(jobId, isAll);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getJobPrivSelJson(privClassMap, jobPrivs,"人员特有权限");
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
