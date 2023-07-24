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
public class MobRolePrivDAOImpl extends BaseDAOImpl implements MobRolePrivDAO {
private static final String TABLE_NAME = "MOBILE_ROLE_PRIV";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ROLE_PRIV_ID:rolePrivId,ROLE_ID:roleId,PRIV_CODE:privCode";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("rolePrivId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ROLE_PRIV_ID:rolePrivId,ROLE_ID:roleId,PRIV_CODE:privCode";
		String wherePatternStr = "ROLE_PRIV_ID:rolePrivId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ROLE_PRIV_ID:rolePrivId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ROLE_PRIV_ID AS rolePrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode FROM MOBILE_ROLE_PRIV WHERE ROLE_PRIV_ID=?";
		String wherePatternStr = "ROLE_PRIV_ID:rolePrivId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ROLE_PRIV_ID AS rolePrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode FROM MOBILE_ROLE_PRIV";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List getPrivCode(String strpriv) throws DataAccessException{
		final String sqlStr ="SELECT  PRIV_CODE AS privCode FROM MOBILE_PRIV where PRIV_CLASS_ID in ("+strpriv+") ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}

	public void updateRolePrivs(int roleId, String[] selectedPrivs)
	throws OAASException {

		System.out.println("roleId:" + roleId);
		System.out.println("selectedPrivs:" + selectedPrivs);

		removeJobPrivs(roleId);
		addJobPrivsBath(roleId,selectedPrivs);
	}

	/**
	 * 批量解除权限 staffId：人员Id jobId：职位Id orgId：组织Id
	 * 
	 * @throws SQLException
	 */
	public void removeJobPrivs(int jobId) {
		System.out.println("roleId111----"+jobId);
		String sql = "DELETE FROM MOBILE_ROLE_PRIV R WHERE  R.ROLE_ID = ? ";
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
	public void addJobPrivsBath(int roleId,String[] selectedPrivs) {
		String sql = "INSERT INTO MOBILE_ROLE_PRIV(ROLE_PRIV_ID,ROLE_ID,PRIV_CODE) VALUES(SEQ_MOBILE_ROLE_PRIV.NEXTVAL,?,?)";
		Connection conn = null;
		PreparedStatement psmt = null;
		System.out.println("sql-------->>>  "+sql.toString());
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);
			for (int i = 0; i < selectedPrivs.length; i++) {
				psmt.setInt(1, roleId);
				psmt.setString(2, selectedPrivs[i]);
				psmt.addBatch();
			}
			psmt.executeBatch();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			cleanUp(conn, null, psmt, null);
		}
	}
}
