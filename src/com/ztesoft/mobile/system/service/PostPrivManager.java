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
     * ����ְλ��ѯ����Ȩ��
     * @param jobId int ְλ���
     * @throws OAASException
     * @return String ְλȨ������
     */
    public static String findPrivsPostGrantJson(int postId) throws OAASException {
    	try {
    		Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findPrivsPostNotHold(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"������Ȩ��");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * ��ѯְλ������Ȩ��(����Ȩ��)
     * @param jobId int ְλ���
     * @param isAll boolean ��ȫ�����ǽ����ڳ�����
     * @throws SQLException
     * @return String Ȩ��json�ַ���
     */
    public static String findPrivsByPostJson(int postId) throws
        OAASException {
        try {
        	Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findPrivsByPost(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"����Ȩ��");
        }
        catch (SQLException ex) {
            OAASError error = new OAASError(OAASError.JOB_PRIV_FIND_ERROR);
            throw new OAASException(error, ex);
        }
    }
    
    /**
     * ��ѯְλ�Ľ�ɫ��Ȩ��
     * @param jobId int ְλ���
     * @throws SQLException
     * @return String Ȩ��XML�ַ���
     * @author liangli 2007.01.23
     */
    public static String findAllRolePrivsByPostJson(int postId) throws OAASException {
        try {
        	Privilege[] privs = PostPrivilegeDAOFactory.getDAO().findRolePrivsByPost(postId);
            Map privClassMap = PrivilegeClassDAOFactory.getDAO().findPrivClassesMap();
            return StaffPrivJsonUtil.getPrivSelJson(privClassMap, privs,"��ɫȨ��");
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
