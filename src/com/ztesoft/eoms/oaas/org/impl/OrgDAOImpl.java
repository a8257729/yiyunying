package com.ztesoft.eoms.oaas.org.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.oaas.org.dao.OrgDAO;

public class OrgDAOImpl extends BaseDAOImpl implements OrgDAO{
	
	public Map getTopOrg()throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ORG_ID AS orgId,");
		sqlBuf.append(" A.ORG_NAME AS orgName,");
		sqlBuf.append(" A.AREA_ID AS areaId,");
		sqlBuf.append(" A.ORG_PATH_CODE orgPathCode,");
		sqlBuf.append(" A.PARENT_ID parentId,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT A1.ORG_ID FROM UOS_ORG A1 WHERE A1.PARENT_ID = A.ORG_ID AND A1.STATE = '1') THEN 0 ELSE 1 END) AS leaf");
		sqlBuf.append(" FROM UOS_ORG A");
		sqlBuf.append(" WHERE (A.PARENT_ID = 0 OR A.PARENT_ID IS NULL)");
		sqlBuf.append(" AND A.STATE = '1' ORDER BY LEAF , A.ORG_PATH_CODE");

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
                result.put("id", rs.getInt("orgId"));
                result.put("orgId", rs.getInt("orgId"));
                result.put("text",rs.getString("orgName"));
                result.put("areaId", rs.getInt("areaId"));
                result.put("orgPathCode",rs.getString("orgPathCode"));
                result.put("parentId", rs.getInt("parentId"));
                result.put("leaf", rs.getInt("leaf"));
                
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
	public Map getTopOrgCheck()throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ORG_ID AS orgId,");
		sqlBuf.append(" A.ORG_NAME AS orgName,");
		sqlBuf.append(" A.AREA_ID AS areaId,");
		sqlBuf.append(" A.ORG_PATH_CODE orgPathCode,");
		sqlBuf.append(" A.PARENT_ID parentId,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT A1.ORG_ID FROM UOS_ORG A1 WHERE A1.PARENT_ID = A.ORG_ID AND A1.STATE = '1') THEN 0 ELSE 1 END) AS leaf");
		sqlBuf.append(" FROM UOS_ORG A");
		sqlBuf.append(" WHERE (A.PARENT_ID = 0 OR A.PARENT_ID IS NULL)");
		sqlBuf.append(" AND A.STATE = '1' ORDER BY LEAF , A.ORG_PATH_CODE");

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
                result.put("id", rs.getInt("orgId"));
                result.put("orgId", rs.getInt("orgId"));
                result.put("text",rs.getString("orgName"));
                result.put("areaId", rs.getInt("areaId"));
                result.put("orgPathCode",rs.getString("orgPathCode"));
                result.put("parentId", rs.getInt("parentId"));
                result.put("leaf", rs.getInt("leaf"));
                result.put("checked", false);
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
	public Map getSubOrgsByIdCheck(int orgId)throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ORG_ID AS orgId,");
		sqlBuf.append(" A.ORG_NAME AS orgName,");
		sqlBuf.append(" A.AREA_ID AS areaId,");
		sqlBuf.append(" A.ORG_PATH_CODE orgPathCode,");
		sqlBuf.append(" A.PARENT_ID parentId,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT A1.ORG_ID FROM UOS_ORG A1 WHERE A1.PARENT_ID = A.ORG_ID AND A1.STATE = '1') THEN 0 ELSE 1 END) AS leaf");
		sqlBuf.append(" FROM UOS_ORG A");
		sqlBuf.append(" WHERE A.STATE = '1'");
		sqlBuf.append("  AND A.PARENT_ID  = ? ORDER BY LEAF , A.ORG_PATH_CODE");

		//logger.debug(sqlBuf.toString());

		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setInt(1, orgId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("orgId"));
                result.put("orgId", rs.getInt("orgId"));
                result.put("text",rs.getString("orgName"));
                result.put("areaId", rs.getInt("areaId"));
                result.put("orgPathCode",rs.getString("orgPathCode"));
                result.put("parentId", rs.getInt("parentId"));
                result.put("leaf", rs.getInt("leaf"));
                result.put("checked", false);
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
	public Map getSubOrgsById(int orgId)throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ORG_ID AS orgId,");
		sqlBuf.append(" A.ORG_NAME AS orgName,");
		sqlBuf.append(" A.AREA_ID AS areaId,");
		sqlBuf.append(" A.ORG_PATH_CODE orgPathCode,");
		sqlBuf.append(" A.PARENT_ID parentId,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT A1.ORG_ID FROM UOS_ORG A1 WHERE A1.PARENT_ID = A.ORG_ID AND A1.STATE = '1') THEN 0 ELSE 1 END) AS leaf");
		sqlBuf.append(" FROM UOS_ORG A");
		sqlBuf.append(" WHERE A.STATE = '1'");
		sqlBuf.append("  AND A.PARENT_ID  = ? ORDER BY LEAF , A.ORG_PATH_CODE");

		//logger.debug(sqlBuf.toString());

		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setInt(1, orgId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("orgId"));
                result.put("orgId", rs.getInt("orgId"));
                result.put("text",rs.getString("orgName"));
                result.put("areaId", rs.getInt("areaId"));
                result.put("orgPathCode",rs.getString("orgPathCode"));
                result.put("parentId", rs.getInt("parentId"));
                result.put("leaf", rs.getInt("leaf"));
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}

	/**
	 * 根据父级组织模板查询组织模板列表
	 */
	public Map getSubOrgTmpTreeById(int parentId) throws DataAccessException {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT U.ORG_TMP_ID AS orgTmpId, U.ORG_TMP_NAME AS orgTmpName, U.PARENT_ID AS parentId");
		sqlBuf.append(" FROM UOS_ORG_TMP U");
		sqlBuf.append(" WHERE U.STATE = 1");
		sqlBuf.append(" AND U.PARENT_ID = ");
		sqlBuf.append(parentId);

		//logger.debug(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 查询顶级组织模板列表
	 */
	public Map getTopOrgTmpTree() throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT U.ORG_TMP_ID AS orgTmpId, U.ORG_TMP_NAME AS orgTmpName, U.PARENT_ID AS parentId");
		sqlBuf.append(" FROM UOS_ORG_TMP U");
		sqlBuf.append(" WHERE U.STATE = 1");
		sqlBuf.append(" AND (U.PARENT_ID IS NULL OR U.PARENT_ID = 0)");

		//logger.debug(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 根据父级区域查询下属区域列表
	 */
	public Map getSubAreaTreeById(int parentId) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.AREA_ID AS areaId,");
		sqlBuf.append(" A.AREA_NAME AS areaName,");
		sqlBuf.append(" A.PARENT_ID AS parentId,");
		sqlBuf.append(" A.PATH_CODE AS pathCode,");
		sqlBuf.append(" A.PATH_NAME AS pathName,");
		sqlBuf.append(" A.ACRONYM AS acronym,");
		sqlBuf.append(" A.CODE AS code,");
		sqlBuf.append(" A.AREA_CODE AS areaCode,");
		sqlBuf.append(" A.GRADE AS grade");
		sqlBuf.append(" FROM UOS_AREA A ");
		sqlBuf.append(" WHERE A.STATE = '10A' ");
		sqlBuf.append(" AND (A.PARENT_ID = ");
		sqlBuf.append(parentId);
		sqlBuf.append(" OR A.AREA_ID = ");
		sqlBuf.append(parentId);
		sqlBuf.append(") ORDER BY A.PATH_CODE");

		//logger.debug(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 查询顶级区域列表
	 */
	public Map getTopAreaTree() throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.AREA_ID AS areaId,");
		sqlBuf.append(" A.AREA_NAME AS areaName,");
		sqlBuf.append(" A.PARENT_ID AS parentId,");
		sqlBuf.append(" A.PATH_CODE AS pathCode,");
		sqlBuf.append(" A.PATH_NAME AS pathName,");
		sqlBuf.append(" A.ACRONYM AS acronym,");
		sqlBuf.append(" A.CODE AS code,");
		sqlBuf.append(" A.AREA_CODE AS areaCode,");
		sqlBuf.append(" A.GRADE AS grade");
		sqlBuf.append(" FROM UOS_AREA A ");
		sqlBuf.append(" WHERE A.STATE = '10A' ");
		sqlBuf.append(" AND (A.PARENT_ID = 0 OR A.PARENT_ID IS NULL) ORDER BY A.PATH_CODE");

		//logger.debug(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	public Map getAreaByKey(int areaId)throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.AREA_ID AS areaId,");
		sqlBuf.append(" A.AREA_NAME AS areaName,");
		sqlBuf.append(" A.PARENT_ID AS parentId,");
		sqlBuf.append(" A.PATH_CODE AS pathCode,");
		sqlBuf.append(" A.PATH_NAME AS pathName,");
		sqlBuf.append(" A.ACRONYM AS acronym,");
		sqlBuf.append(" A.CODE AS code,");
		sqlBuf.append(" A.AREA_CODE AS areaCode,");
		sqlBuf.append(" A.GRADE AS grade");
		sqlBuf.append(" FROM UOS_AREA A ");
		sqlBuf.append(" WHERE A.STATE = '10A' ");
		sqlBuf.append(" AND A.AREA_ID = ");
		sqlBuf.append(areaId);
		sqlBuf.append(" ORDER BY A.PATH_CODE");

		//logger.debug(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}

}
