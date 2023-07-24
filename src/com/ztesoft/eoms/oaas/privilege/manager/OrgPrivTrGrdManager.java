package com.ztesoft.eoms.oaas.privilege.manager;

import java.sql.SQLException;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.service.jobprivmanager.dao.JobPrivilegeDAOFactory;
import com.zterc.uos.oaas.service.privclassmanager.dao.PrivilegeClassDAOFactory;
import com.zterc.uos.oaas.vo.Privilege;
import com.ztesoft.eoms.oaas.privilege.util.StaffPrivJsonUtil;

public class OrgPrivTrGrdManager implements SessionBean {

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
