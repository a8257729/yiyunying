package com.ztesoft.eoms.oaas.privilege.manager;

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
import com.ztesoft.eoms.oaas.privilege.util.StaffPrivJsonUtil;

public class OrgTmpPrivManager implements SessionBean {
	
	private static final long serialVersionUID = 8275053926006400396L;
	
	/**
     * 查询出职位模板所有的权限
     * @param postId int 职位模板编号
     * @throws OAASException
     * @return String 权限树
     */
    public static String findPrivsByPostJson(int postId) throws OAASException {
        try {
            Privilege[] privs = PostPrivilegeDAOFactory.getDAO().
                findPrivsByPost(postId);
            Map privClassMap = null;
            if (privs.length > 0) {
                privClassMap = PrivilegeClassDAOFactory.getDAO().
                    findPrivClassesMap();
            }
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"职位模板权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.POST_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * 查询职位模板所具有的角色
     * @param postId int 职位模板编号
     * @throws OAASException
     * @return String 权限树
     */
    public static String findAllPrivsByPostXml(int postId) throws OAASException {
        try {
            Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findAllPrivsByPost(postId);
            Map privClassMap = null;
            if (privs.length > 0) {
                privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            }
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"所有权限");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.POST_PRIV_FIND_ERROR);
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
