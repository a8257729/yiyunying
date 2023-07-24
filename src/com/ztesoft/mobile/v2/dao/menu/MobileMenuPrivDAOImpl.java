package com.ztesoft.mobile.v2.dao.menu;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBean;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBeanNoCheck;
import com.ztesoft.mobile.systemMobMgr.util.BuildTreeUtil;

public class MobileMenuPrivDAOImpl extends BaseDAOImpl implements
		MobileMenuPrivDAO {

	// 权限选择
	public Map getAllParentPrivData(int moduleId, int jobId, int defaultJobId)
			throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" P.MENU_ICON_URI AS menuIconUri,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND  P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,P.PRIV_CODE as privCode,P.MENU_TYPE AS menuType  ");
		sqlBuf.append(" FROM MOBILE_MENU P WHERE P.OS_TYPE = 1 AND P.STATE = 1");
		if (moduleId == 0) {
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		} else {
			sqlBuf.append(" AND P.PARENT_ID = ").append(moduleId);
		}
		sqlBuf.append(" AND PRIV_CODE not in (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
				+ jobId + " or msjr.JOB_ID = " + defaultJobId + ")) ");

		System.out.println("getAllParentPrivData=" + sqlBuf.toString());

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Map map = new HashMap();
		ArrayList resultList = new ArrayList();
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sqlBuf.toString());
			rs = psmt.executeQuery();
			while (rs.next()) {
				Map result = new HashMap();
				result.put("id", rs.getInt("id"));
				result.put("text", rs.getString("name"));
				result.put("pathCode", rs.getString("pathCode"));
				result.put("pathName", rs.getString("pathName"));
				result.put("leaf", rs.getInt("leaf") == 1 ? true : false);
				result.put("parentId", rs.getInt("parentId"));
				result.put("menuIndex", rs.getString("menuIndex"));
				result.put("privCode", rs.getString("privCode"));
				resultList.add(result);
			}
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 职位权限选择
	public Map getAllSubPrivData(int moduleId, int jobId, int specialJobId,
			int _jobId, int _defaultJobId, String staffId) throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if (staffId.equals("0")) { // 超级管理员权限
			sqlBuf.append("SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MENU P WHERE P.OS_TYPE = 1 AND P.STATE = 1");

			sqlBuf.append(" AND PRIV_CODE NOT IN (select mm.PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MENU mm where mm.OS_TYPE = 1 AND mm.priv_code=msjr.priv_code and mm.is_leaf=1 and (msjr.JOB_ID = "
					+ _jobId + " or msjr.JOB_ID = " + _defaultJobId + ")) ");
		} else { // 人员可以分配给其它人的权限
			sqlBuf.append("SELECT distinct P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,P.PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MENU P ");
			sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MENU a, MOBILE_STAFF_JOB_RIGHT b where a.priv_code = b.priv_code and (b.JOB_ID = "
					+ jobId + " or b.JOB_ID = " + specialJobId + ")) V ");
			sqlBuf.append(" WHERE P.OS_TYPE = 1 AND P.STATE = 1");

			// sqlBuf.append(" AND (P.MENU_ID != 0)");
			sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.MENU_ID = V.parent_id)  ");
			// sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "+_jobId+" or msjr.JOB_ID = "+_defaultJobId+")) ");
			sqlBuf.append(" AND PRIV_CODE NOT IN (select mm.PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr,MOBILE_MENU mm where mm.OS_TYPE = 1 AND mm.priv_code=msjr.priv_code and mm.is_leaf=1 and (msjr.JOB_ID = "
					+ _jobId + " or msjr.JOB_ID = " + _defaultJobId + ")) ");
		}
		System.out.println("getAllSubPrivData=" + sqlBuf.toString());

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

	// 已有职位权限选择
	public Map getAllHasPrivData(int jobId, int defaultJobId)
			throws Exception {

		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT distinct P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,p.PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MENU P ,MOBILE_STAFF_JOB_RIGHT b ");
		sqlBuf.append(" WHERE P.OS_TYPE = 1 AND P.STATE = 1");
		sqlBuf.append(" and p.priv_code = b.priv_code and (b.JOB_ID = " + jobId
			    + " or b.JOB_ID = "
				+ defaultJobId + ") ");

		System.out.println("getAllHasPrivData" + sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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

	// 角色权限选择
	public Map getAllRolePrivData(int moduleId, int jobId, int specialJobId,
			int roleId, String staffId) throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if (staffId.equals("0")) { // 超级管理员权限
			sqlBuf.append("SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MENU P WHERE P.OS_TYPE = 1 AND P.STATE = 1");

			sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
					+ roleId + ")) ");
		} else { // 人员可以分配给其它人的权限
			sqlBuf.append("SELECT distinct P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
			sqlBuf.append(" FROM MOBILE_MENU P ");
			sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MENU a, MOBILE_ROLE_PRIV b where a.priv_code = b.priv_code and (b.JOB_ID = "
					+ jobId + " or b.JOB_ID = " + specialJobId + ")) V ");
			sqlBuf.append(" WHERE P.OS_TYPE = 1 AND P.STATE = 1");

			// sqlBuf.append(" AND (P.MENU_ID != 0)");
			sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.MENU_ID = V.parent_id)  ");
			sqlBuf.append(" AND PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
					+ roleId + ")) ");
		}
		System.out.println("getAllRolePrivData=" + sqlBuf.toString());

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
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf") == 1 ? true : false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(false);
				alllist.add(bean);

			}

			resultList = BuildTreeUtil.buildTreeData(alllist, "getId",
					"getParentId");

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBean nebean = (TreeBean) resultList.get(k);
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

			// System.out.println("getAllRolePrivData resultList--->  "+newresultlist.toString());
			map.put("resultList", newresultlist);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 已有角色权限选择
	public Map getAllHasRolePrivData(int roleId) throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT distinct P.MENU_ID AS id, P.MENU_NAME AS name, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.MENU_ID FROM MOBILE_MENU P1 WHERE P1.OS_TYPE = 1 AND P1.STATE = 1 AND P1.PARENT_ID = P.MENU_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" P.PARENT_ID AS parentId,P.MENU_INDEX AS menuIndex,P.IS_LEAF AS isLeaf,PRIV_CODE as privCode  ");
		sqlBuf.append(" FROM MOBILE_MENU P ");
		sqlBuf.append(" ,(select a.parent_id, a.path_code  FROM MOBILE_MENU a, MOBILE_ROLE_PRIV b where a.priv_code = b.priv_code and (b.ROLE_ID = "
				+ roleId + ")) V ");
		sqlBuf.append(" WHERE P.OS_TYPE = 1 AND P.STATE = 1");

		// sqlBuf.append(" AND (P.MENU_ID != 0)");
		sqlBuf.append("  AND (P.Path_Code  like ''||V.path_code||'%' or P.MENU_ID = V.parent_id)  ");

		System.out.println("getAllHasRolePrivData" + sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf") == 1 ? true : false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(true);
				alllist.add(bean);

			}

			resultList = BuildTreeUtil.buildTreeData(alllist, "getId",
					"getParentId");
			// System.out.println("getAllHasRolePrivData resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 可选择操作按钮权限选择
	public Map getAllButPrivData(int moduleId, int jobId, int specialJobId,
			int _jobId, int _defaultJobId, String staffId) throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if (staffId.equals("0")) { // 超级管理员权限
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ _jobId + " or msjr.JOB_ID = " + _defaultJobId + ")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ _jobId + " or msjr.JOB_ID = " + _defaultJobId + ")) ");
			sqlBuf.append(" ) where 1=1 ");
		} else {
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ jobId + " or msjr.JOB_ID = " + specialJobId + ")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ jobId + " or msjr.JOB_ID = " + specialJobId + ")) ");
			sqlBuf.append(" ) where 1=1 ");
			sqlBuf.append(" AND privCode NOT IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ _jobId + " or msjr.JOB_ID = " + _defaultJobId + ")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBeanNoCheck nebean = (TreeBeanNoCheck) resultList.get(k);
				if (nebean.isLeaf()) {
					newresultlist.add(nebean);
				} else {
					if (nebean.getChildren() == null) { // 删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					} else {
						newresultlist.add(nebean);
					}
				}
			}
			// System.out.println("alllist---->> "+alllist.toString());
			// System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 可选择操作按钮权限选择
	public Map getHasButPrivData(int jobId, int defaultJobId) throws Exception {
		// sqlBuf.append(" SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PARENT_ID AS parentId, P.PRIV_CODE as privCode,0 as leaf FROM MOBILE_MENU P WHERE P.STATE = 1 ");

		// sqlBuf.append(" union ");
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

		sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
				+ jobId + " or msjr.JOB_ID = " + defaultJobId + ")) ");
		sqlBuf.append(" union ");
		sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
				+ jobId + " or msjr.JOB_ID = " + defaultJobId + ")) ");
		sqlBuf.append(" ) where 1=1 ");

		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBeanNoCheck nebean = (TreeBeanNoCheck) resultList.get(k);
				if (nebean.isLeaf()) {
					newresultlist.add(nebean);
				} else {
					if (nebean.getChildren() == null) { // 删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					} else {
						newresultlist.add(nebean);
					}
				}
			}
			// System.out.println("alllist---->> "+alllist.toString());
			// System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 可选择角色操作按钮权限选择
	public Map getAllRoleButPrivData(int moduleId, int jobId, int specialJobId,
			int roleId, String staffId) throws Exception {

		StringBuffer sqlBuf = new StringBuffer();
		if (staffId.equals("0")) { // 超级管理员权限
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
					+ roleId + ")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
					+ roleId + ")) ");
			sqlBuf.append(" ) where 1=1 ");
		} else {
			sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

			sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ jobId + " or msjr.JOB_ID = " + specialJobId + ")) ");
			sqlBuf.append(" union ");
			sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
			sqlBuf.append(" AND P2.priv_code IN (select PRIV_CODE from MOBILE_STAFF_JOB_RIGHT msjr where 1=1 and (msjr.JOB_ID = "
					+ jobId + " or msjr.JOB_ID = " + specialJobId + ")) ");
			sqlBuf.append(" ) where 1=1 ");
			sqlBuf.append(" AND privCode NOT IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
					+ roleId + ")) ");
		}
		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBeanNoCheck nebean = (TreeBeanNoCheck) resultList.get(k);
				if (nebean.isLeaf()) {
					newresultlist.add(nebean);
				} else {
					if (nebean.getChildren() == null) { // 删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					} else {
						newresultlist.add(nebean);
					}
				}
			}
			// System.out.println("alllist---->> "+alllist.toString());
			// System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	// 可选择角色操作按钮权限选择
	public Map getHasRoleButPrivData(int roleId) throws Exception {
		// sqlBuf.append(" SELECT P.MENU_ID AS id, P.MENU_NAME AS name, P.PARENT_ID AS parentId, P.PRIV_CODE as privCode,0 as leaf FROM MOBILE_MENU P WHERE P.STATE = 1 ");

		// sqlBuf.append(" union ");
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT DISTINCT id,name,parentId,privCode,leaf from ( ");

		sqlBuf.append(" select distinct P3.FORM_ID as id,(P3.FORM_NAME||'【'||P.MENU_NAME||'】') as name,0 as parentId,null as privCode,0 as leaf  FROM MOBILE_MENU P ,MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P.MENU_ID=P3.MUNE_ID AND P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
				+ roleId + ")) ");
		sqlBuf.append(" union ");
		sqlBuf.append(" select distinct P2.BUTTON_ID as id,P2.Button_Name as name,P3.FORM_ID as parentId,P2.priv_code as privCode,1 as leaf from MOBILE_JSON_CREATE P3,MOBILE_BUTTON_RIGHT P2 where P3.FORM_ID = P2.FORM_ID ");
		sqlBuf.append(" AND P2.PRIV_CODE IN (select PRIV_CODE from MOBILE_ROLE_PRIV msjr where 1=1 and (msjr.ROLE_ID = "
				+ roleId + ")) ");
		sqlBuf.append(" ) where 1=1 ");

		System.out.println(sqlBuf.toString());

		List alllist = new ArrayList();
		List parentlist = new ArrayList();
		List sublist = new ArrayList();
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
				TreeBean bean = new TreeBean();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf") == 1 ? true : false);
				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(true);
				alllist.add(bean);

			}

			resultList = BuildTreeUtil.buildTreeData(alllist, "getId",
					"getParentId");

			List newresultlist = new ArrayList();
			for (int k = 0; k < resultList.size(); k++) {
				TreeBean nebean = (TreeBean) resultList.get(k);
				if (nebean.isLeaf()) {
					newresultlist.add(nebean);
				} else {
					if (nebean.getChildren() == null) { // 删除没有子节点的树
						nebean.setLeaf(true);
						newresultlist.add(nebean);
						continue;
					} else {
						newresultlist.add(nebean);
					}
				}
			}
			// System.out.println("alllist---->> "+alllist.toString());
			// System.out.println("resultList--->  "+resultList.toString());
			map.put("resultList", resultList);
			return map;
		} finally {
			cleanUp(conn, null, psmt, rs);
		}
	}

	/**
	 * 获得区域信息
	 */
	public int getAreaInfo(int areaId) throws Exception {
		int areaIdC3 = 0;
		Connection conn = null;
		CallableStatement casm = null;
		try {
			conn = getConnection();
			casm = conn.prepareCall("{call ah.getAreaIdC3(?,?)}");
			casm.setInt(1, areaId);
			casm.registerOutParameter(2, Types.NUMERIC);
			casm.execute();
			areaIdC3 = casm.getInt(2);
			return areaIdC3;
		} finally {
			cleanUp(conn, null, casm, null);
		}
	}
}
