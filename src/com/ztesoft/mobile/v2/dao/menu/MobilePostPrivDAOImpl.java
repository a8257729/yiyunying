package com.ztesoft.mobile.v2.dao.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.exception.OAASException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBeanNoCheck;
import com.ztesoft.mobile.systemMobMgr.util.BuildTreeUtil;

public class MobilePostPrivDAOImpl extends BaseDAOImpl implements MobilePostPrivDAO{

	private static final String TABLE_NAME = "MOBILE_POST_PRIV";
		public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "POST_PRIV_ID:postPrivId,ROLE_ID:roleId,PRIV_CODE:privCode,POST_ID:postId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("postPrivId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "POST_PRIV_ID:postPrivId,ROLE_ID:roleId,PRIV_CODE:privCode,POST_ID:postId";
		String wherePatternStr = "POST_PRIV_ID:postPrivId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "POST_PRIV_ID:postPrivId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   POST_PRIV_ID AS postPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  POST_ID AS postId FROM MOBILE_POST_PRIV WHERE POST_PRIV_ID=?";
		String wherePatternStr = "POST_PRIV_ID:postPrivId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   POST_PRIV_ID AS postPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  POST_ID AS postId FROM MOBILE_POST_PRIV";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selByPostId(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   POST_PRIV_ID AS postPrivId,  ROLE_ID AS roleId,  PRIV_CODE AS privCode,  POST_ID AS postId FROM MOBILE_POST_PRIV WHERE POST_ID = ? ";
		String wherePatternStr = "POST_ID:postId";
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public Map getAllPostPrivData(int postId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();		
		sqlBuf.append("SELECT distinct P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,p.PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MENU P ,MOBILE_POST_PRIV b ");
		sqlBuf.append(" WHERE P.OS_TYPE = 1 AND P.STATE = 1");
		sqlBuf.append(" and p.priv_code = b.priv_code and b.POST_ID = " + postId );
		
		System.out.println("getAllPostPrivData" + sqlBuf.toString());
		
		List alllist = new ArrayList();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf") == 1 ? true : false);
				bean.setPrivCode(rs.getString("privCode"));
				// bean.setChecked(true);
				alllist.add(bean);
		
			}
		
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId",
					"getParentId");
			// System.out.println("getAllHasPrivData resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	public Map getAllSubPostPrivData(int postId)throws Exception {

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MENU P WHERE P.OS_TYPE = 1 AND P.STATE = 1");
		sqlBuf.append(" AND PRIV_CODE NOT IN (select mm.PRIV_CODE from MOBILE_POST_PRIV msjr,MOBILE_MENU mm where mm.OS_TYPE = 1 AND mm.priv_code=msjr.priv_code and mm.is_leaf=1 and (msjr.POST_ID = "
					+ postId +")) ");
		 
		System.out.println("getAllSubPostPrivData=" + sqlBuf.toString());

		List alllist = new ArrayList();

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		List resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				TreeBeanNoCheck bean = new TreeBeanNoCheck();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf") == 1 ? true : false);
				bean.setPrivCode(rs.getString("privCode"));
				// bean.setChecked(false);
				alllist.add(bean);

			}

			resultList = BuildTreeUtil.buildTreeData(alllist, "getId",
					"getParentId");

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBeanNoCheck nebean = (TreeBeanNoCheck) resultList.get(k);
				if (nebean.isLeaf()) {
					newresultlist.add(nebean);
				} else {
					if (nebean.getChildren() == null) { // 删除没有子节点的树
						continue;
					} else {
						newresultlist.add(nebean);
					}
				}
			}

			// System.out.println("getAllSubPrivData resultList--->  "+newresultlist.toString());
			map.put("resultList", newresultlist);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}
	
	public List getPrivCode(String strpriv) throws DataAccessException{
		final String sqlStr ="SELECT  PRIV_CODE AS privCode FROM MOBILE_PRIV where PRIV_CLASS_ID in ("+strpriv+") ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}

	public void updateJobPrivs(String[] roleIds, int postId,String[] grantPrivs, String[] selectedPrivs)throws OAASException {

		System.out.println("postId:" + postId);
		removeJobPrivs(postId);
		addJobPrivsBath(roleIds, postId, grantPrivs, selectedPrivs);
	}

	/**
	 * 批量解除权限 staffId：人员Id jobId：职位Id orgId：组织Id
	 * 
	 * @throws SQLException
	 */
	public void removeJobPrivs(int postId) {
		String sql = "DELETE FROM MOBILE_POST_PRIV R WHERE  R.POST_ID = ? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, postId);

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
	public void addJobPrivsBath(String[] roleIds, int postId, String[] grantPrivs, String[] selectedPrivs) {
		//String sql = "INSERT INTO MOBILE_STAFF_JOB_RIGHT(JOB_PRIV_ID,ROLE_ID,PRIV_CODE,JOB_ID,UPDATE_DATE,CAN_GRANT) VALUES(SEQ_MOBILE_STAFF_JOB_RIGHT.NEXTVAL,-1,?,?,?,1)";
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO MOBILE_POST_PRIV(POST_PRIV_ID,ROLE_ID,PRIV_CODE,POST_ID) VALUES(SEQ_MOBILE_POST_PRIV.NEXTVAL,?,?,?)");
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
					psmt.setInt(3, postId);
					psmt.addBatch();
				}
			}else if(selectedPrivs.length <= 1 && selectedPrivs[0].equals("-1")){
				for (int i = 0; i < roleIds.length; i++) {
					psmt.setInt(1, Integer.parseInt(roleIds[i]));
					psmt.setString(2, "-1");
					psmt.setInt(3, postId);
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
