package com.ztesoft.mobile.systemMobMgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.exception.OAASException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class MobStaffJObRightDAOImpl extends BaseDAOImpl implements MobStaffJObRightDAO {
	private static final String TABLE_NAME = "MOBILE_STAFF_JOB_RIGHT";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "JOB_PRIV_ID:jobPrivId,ROLE_ID:roleId,PRIV_CODE:privCode,JOB_ID:jobId,UPDATE_DATE:updateDate,CAN_GRANT:canGrant";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("jobPrivId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "JOB_PRIV_ID:jobPrivId,ROLE_ID:roleId,PRIV_CODE:privCode,JOB_ID:jobId,UPDATE_DATE:updateDate,CAN_GRANT:canGrant";
		String wherePatternStr = "JOB_PRIV_ID:jobPrivId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "JOB_PRIV_ID:jobPrivId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void deleteByJobId(Map paramMap) throws DataAccessException {
		String wherePatternStr = "JOB_ID:jobId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   JOB_PRIV_ID AS jobPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  JOB_ID AS jobId,  UPDATE_DATE AS updateDate,  CAN_GRANT AS canGrant FROM MOBILE_STAFF_JOB_RIGHT WHERE JOB_PRIV_ID=?";
		String wherePatternStr = "JOB_PRIV_ID:jobPrivId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   JOB_PRIV_ID AS jobPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  JOB_ID AS jobId,  UPDATE_DATE AS updateDate,  CAN_GRANT AS canGrant FROM MOBILE_STAFF_JOB_RIGHT";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public List selByJobId(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   JOB_PRIV_ID AS jobPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  JOB_ID AS jobId,  UPDATE_DATE AS updateDate,  CAN_GRANT AS canGrant FROM MOBILE_STAFF_JOB_RIGHT WHERE JOB_ID = ? ";
		String wherePatternStr = "JOB_ID:jobId";
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List getPrivCode(String strpriv) throws DataAccessException{
		final String sqlStr ="SELECT  PRIV_CODE AS privCode FROM MOBILE_PRIV where PRIV_CLASS_ID in ("+strpriv+") ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}

	public void updateJobPrivs(String[] roleIds, int jobId,String[] grantPrivs, String[] selectedPrivs)
	throws OAASException {

		System.out.println("jobId:" + jobId);
		System.out.println("selectedPrivs:" + selectedPrivs);

		removeJobPrivs(jobId);
		addJobPrivsBath(roleIds, jobId, grantPrivs, selectedPrivs);
	}

	/**
	 * 批量解除权限 staffId：人员Id jobId：职位Id orgId：组织Id
	 * 
	 * @throws SQLException
	 */
	public void removeJobPrivs(int jobId) {
		String sql = "DELETE FROM MOBILE_STAFF_JOB_RIGHT R WHERE  R.JOB_ID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, jobId);

			psmt.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp(conn, null, psmt, null);
		}
	}

	/**
	 * 批量增加权限 staffId：人员Id jobId：职位Id orgId：组织Id selectedPrivs:权限列表
	 * 
	 * @throws SQLException
	 */
	public void addJobPrivsBath(String[] roleIds, int jobId, String[] grantPrivs, String[] selectedPrivs) {
		//String sql = "INSERT INTO MOBILE_STAFF_JOB_RIGHT(JOB_PRIV_ID,ROLE_ID,PRIV_CODE,JOB_ID,UPDATE_DATE,CAN_GRANT) VALUES(SEQ_MOBILE_STAFF_JOB_RIGHT.NEXTVAL,-1,?,?,?,1)";
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO MOBILE_STAFF_JOB_RIGHT(JOB_PRIV_ID,ROLE_ID,PRIV_CODE,JOB_ID,UPDATE_DATE,CAN_GRANT) VALUES(SEQ_MOBILE_STAFF_JOB_RIGHT.NEXTVAL,?,?,?,?,1)");
		Connection conn = null;
		PreparedStatement psmt = null;
		System.out.println("sql-------->>>  "+sql.toString());

		
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql.toString());
			if(roleIds.length <= 1 && roleIds[0].equals("-1")){
				for (int i = 0; i < selectedPrivs.length; i++) {
					System.out.println("selectedPrivs[i]="+selectedPrivs[i]);
					psmt.setInt(1, -1);
					psmt.setString(2, selectedPrivs[i]);
					psmt.setInt(3, jobId);
					psmt.setDate(4, new java.sql.Date(new Date().getTime()));
					psmt.addBatch();
				}
			}else if(selectedPrivs.length <= 1 && selectedPrivs[0].equals("-1")){
				for (int i = 0; i < roleIds.length; i++) {
					psmt.setInt(1, Integer.parseInt(roleIds[i]));
					psmt.setString(2, "-1");
					psmt.setInt(3, jobId);
					psmt.setDate(4, new java.sql.Date(new Date().getTime()));
					psmt.addBatch();
				}
			}
			
			psmt.executeBatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp(conn, null, psmt, null);
		}
	}
}

