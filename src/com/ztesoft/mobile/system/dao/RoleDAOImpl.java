package com.ztesoft.mobile.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zterc.uos.util.LongUtils;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class RoleDAOImpl extends BaseDAOImpl implements RoleDAO {
	
	/**
	 * 查询区域列表
	 */
	public Map getAreas()throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.AREA_ID AS id,");
		sqlBuf.append(" A.AREA_NAME AS text,");
		sqlBuf.append(" 1 AS leaf,");
		sqlBuf.append(" A.PARENT_ID AS parentId,");
		sqlBuf.append(" A.PATH_CODE AS pathCode,");
		sqlBuf.append(" A.PATH_NAME AS pathName,");
		sqlBuf.append(" A.ACRONYM AS acronym,");
		sqlBuf.append(" A.CODE AS code,");
		sqlBuf.append(" A.AREA_CODE AS areaCode,");
		sqlBuf.append(" A.GRADE AS grade,");
		sqlBuf.append(" A.COMMENTS AS comments,");
		sqlBuf.append(" B.GRADE_NAME AS gradeName");
		sqlBuf.append(" FROM UOS_AREA A, UOS_AREA_GRADE B");
		sqlBuf.append(" WHERE A.STATE = '10A'");
		sqlBuf.append(" AND A.GRADE = B.GRADE");
		
		sqlBuf.append(" ORDER BY A.GRADE");
		System.out.println(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 查询区域列表
	 */
	public Map getSubAreaTreeById(int areaId) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.AREA_ID AS id,");
		sqlBuf.append(" A.AREA_NAME AS text,");
		sqlBuf.append(" 1 AS leaf,");
		sqlBuf.append(" A.PARENT_ID AS parentId,");
		sqlBuf.append(" A.PATH_CODE AS pathCode,");
		sqlBuf.append(" A.PATH_NAME AS pathName,");
		sqlBuf.append(" A.ACRONYM AS acronym,");
		sqlBuf.append(" A.CODE AS code,");
		sqlBuf.append(" A.AREA_CODE AS areaCode,");
		sqlBuf.append(" A.GRADE AS grade,");
		sqlBuf.append(" A.COMMENTS AS comments,");
		sqlBuf.append(" B.GRADE_NAME AS gradeName");
		sqlBuf.append(" FROM UOS_AREA A, UOS_AREA_GRADE B");
		sqlBuf.append(" WHERE A.STATE = '10A'");
		sqlBuf.append(" AND A.GRADE = B.GRADE");
		sqlBuf.append(" AND A.PARENT_ID = ");
		sqlBuf.append(areaId);
		sqlBuf.append(" ORDER BY A.GRADE");
		System.out.println(sqlBuf.toString());

		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 管理员查询角色列表,对应方法1
	 */
	public Map findAllRolesInArea(int _jobId,int _defaultJobId,int areaId) throws Exception{
		StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.ROLE_ID,A.ROLE_NAME,A.AREA_ID FROM UOS_ROLE A ");
        sql.append(" WHERE NOT EXISTS (SELECT * FROM UOS_JOB_PRIV ");
        sql.append(" WHERE (JOB_ID=? OR JOB_ID=?) AND ROLE_ID=A.ROLE_ID) ");
        sql.append(" AND A.AREA_ID= ? ORDER BY A.ROLE_NAME ");
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql.toString());
            psmt.setInt(1, _jobId);
            psmt.setInt(2, _defaultJobId);
            psmt.setLong(3, areaId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("ROLE_ID"));
                result.put("text",rs.getString("ROLE_NAME"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("AREA_ID")));
                result.put("leaf", 1);
                result.put("checked",false);
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
     * 只有默认职位或只有被赋予职位
     */
    public Map findRolesHoldOneJobInArea(int jobId1, int jobId2,int specialJobId, int areaId) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT A.ROLE_ID,B.AREA_ID,B.ROLE_NAME FROM UOS_JOB_PRIV A,UOS_ROLE B ");
        sb.append(" WHERE A.JOB_ID=? AND A.ROLE_ID=B.ROLE_ID ");
        sb.append(" AND NOT EXISTS (SELECT * FROM UOS_JOB_PRIV WHERE (JOB_ID=? OR JOB_ID=?) ");
        sb.append(" AND ROLE_ID=A.ROLE_ID) AND B.AREA_ID = ? ORDER BY B.ROLE_NAME ");
        String sql = sb.toString();

        System.out.println(sb);
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, jobId1);
            psmt.setInt(2, jobId2);
            psmt.setInt(3, specialJobId);
            psmt.setInt(4, areaId);

            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("ROLE_ID"));
                result.put("text",rs.getString("ROLE_NAME"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("AREA_ID")));
                result.put("leaf", 1);
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
     * 既有默认职位，也有被赋予的职位，对应方法4
     */
    public Map findRolesHoldTwoJobInArea(int jobId1, int jobId2,
                                            int jobId3, int specialJobId,
                                            int areaId) throws Exception {

    	StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT A.ROLE_ID,B.AREA_ID,B.ROLE_NAME FROM UOS_JOB_PRIV A,UOS_ROLE B ");
        sb.append(" WHERE (A.JOB_ID=? OR A.JOB_ID=?) AND A.ROLE_ID=B.ROLE_ID ");
        sb.append(" AND NOT EXISTS (SELECT * FROM UOS_JOB_PRIV WHERE (JOB_ID=? OR JOB_ID=?) ");
        sb.append(" AND ROLE_ID=A.ROLE_ID) AND B.AREA_ID = ? ORDER BY B.ROLE_NAME ");

        String sql = sb.toString();

        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, jobId1);
            psmt.setInt(2, jobId2);
            psmt.setInt(3, jobId3);
            psmt.setInt(4, specialJobId);
            psmt.setInt(5, areaId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("id", rs.getInt("ROLE_ID"));
                result.put("text",rs.getString("ROLE_NAME"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("AREA_ID")));
                result.put("leaf", 1);
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
     * 查询一个职位的现有角色
     */
    public Map findRolesByJob(int jobId) throws SQLException {
        String sql = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT A.ROLE_ID, B.ROLE_NAME, A.CAN_GRANT,B.AREA_ID,UA.AREA_NAME ");
        sb.append(" FROM UOS_JOB_PRIV A, UOS_ROLE B ");
        sb.append(" LEFT JOIN UOS_AREA UA ON UA.AREA_ID=B.AREA_ID ");
        sb.append(" WHERE A.JOB_ID = ? ");
        sb.append(" AND A.ROLE_ID = B.ROLE_ID ");
        sb.append(" ORDER BY B.ROLE_NAME ");
        sql = sb.toString();
        System.out.println("findRolesByJobSql:"+sql);
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        Map map = new HashMap();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, jobId);
            rs = psmt.executeQuery();
            
            while (rs.next()) {
            	Map result = new HashMap();
            	boolean checked ;
            	if(1 == rs.getInt("CAN_GRANT")){
            		checked = true ;
            	}else {
            		checked = false ;
            	}
            	result.put("id", rs.getInt("ROLE_ID"));
                result.put("text",rs.getString("ROLE_NAME"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("AREA_ID")));
                result.put("leaf", 1);
                result.put("checked", checked);
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
     * 查询一个职位模板的现有角色
     */
    public Map findRolesByPost(int postId) throws SQLException {
        String sql = null;
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT A.ROLE_ID AS roleId, B.ROLE_NAME AS roleName ");
        sb.append(" FROM UOS_POST_PRIV A, UOS_ROLE B");
        sb.append(" WHERE A.ROLE_ID = B.ROLE_ID");
        sb.append(" AND A.POST_ID = ?");
        sql = sb.toString();
        System.out.println("findRolesByPostSql:"+sql);
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList resultList = new ArrayList();
        Map map = new HashMap();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, postId);
            rs = psmt.executeQuery();
            
            while (rs.next()) {
            	Map result = new HashMap();
            	result.put("id", rs.getInt("roleId"));
                result.put("text",rs.getString("roleName"));
                result.put("leaf", 1);
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
    
    public Map getRoleGridByJob(int jobId ) throws DataAccessException {
    	StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("SELECT A.ROLE_ID AS roleId,B.ROLE_NAME AS roleName,A.CAN_GRANT AS canGrant,B.AREA_ID AS areaId,UA.AREA_NAME AS areaName ");
    	sqlBuf.append(" FROM UOS_JOB_PRIV A, UOS_ROLE B ");
    	sqlBuf.append(" LEFT JOIN UOS_AREA UA ON UA.AREA_ID=B.AREA_ID ");
    	sqlBuf.append(" WHERE A.JOB_ID =  ");
    	sqlBuf.append(jobId);
    	sqlBuf.append(" AND A.ROLE_ID = B.ROLE_ID ");
    	sqlBuf.append(" ORDER BY B.ROLE_NAME ");
        
        return (Map)populateQueryByMap(sqlBuf, 0, 0);
    }
    
    /**
     * 根据地域查询角色
     * @param areaId
     * @return
     * @throws DataAccessException
     */
    public Map getRoleGridByArea(int areaId ) throws DataAccessException {
    	StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("SELECT UR.ROLE_ID AS roleId,");
    	sqlBuf.append(" UR.ROLE_NAME AS roleName,");
    	sqlBuf.append(" UR.COMMENTS AS comments,");
    	sqlBuf.append(" UR.AREA_ID AS areaId,");
    	sqlBuf.append(" UA.AREA_NAME AS areaName");
    	sqlBuf.append(" FROM UOS_ROLE UR, UOS_AREA UA");
    	sqlBuf.append(" WHERE UR.AREA_ID = UA.AREA_ID");
    	sqlBuf.append(" AND UR.AREA_ID = ");
    	sqlBuf.append(areaId);
        
        return (Map)populateQueryByMap(sqlBuf, 0, 0);
    }
    
    /**
     * 组织管理配置角色页面可配置角色树查询1
     * @param _jobId
     * @param areaId
     * @return
     * @throws Exception
     */
	public Map getAllRolesInArea(int _jobId,int areaId) throws Exception{
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ROLE_ID AS roleId, A.AREA_ID AS areaId, A.ROLE_NAME AS roleName FROM UOS_ROLE A ");
        sql.append(" WHERE NOT EXISTS (SELECT * FROM UOS_JOB_PRIV WHERE JOB_ID = ");
        sql.append(_jobId);
        sql.append(" AND ROLE_ID = A.ROLE_ID)");
        sql.append(" AND A.AREA_ID = ");
        sql.append(areaId);
        sql.append(" ORDER BY A.ROLE_NAME ");
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("roleId"));
                result.put("text",rs.getString("roleName"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("areaId")));
                result.put("leaf", 1);
                result.put("checked",false);
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
	 * 组织管理配置角色页面可配置角色树查询2
	 * @param jobId1
	 * @param jobId2
	 * @param areaId
	 * @return
	 * @throws Exception
	 */
	public Map getAllRolesInArea(int jobId1,int jobId2,int areaId) throws Exception{
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ROLE_ID AS roleId, A.AREA_ID AS areaId, A.ROLE_NAME AS roleName FROM UOS_JOB_PRIV A, UOS_ROLE B");
        sql.append(" WHERE A.JOB_ID = ");
        sql.append(jobId1);
        sql.append(" AND A.ROLE_ID = B.ROLE_ID");
        sql.append(" AND A.CAN_GRANT = '1' ");
        sql.append(" AND NOT EXISTS (SELECT * FROM UOS_JOB_PRIV WHERE JOB_ID = ");
        sql.append(jobId2);
        sql.append(" AND ROLE_ID = A.ROLE_ID)");
        sql.append(" AND A.AREA_ID = ");
        sql.append(areaId);
        sql.append(" ORDER BY A.ROLE_NAME ");
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("roleId"));
                result.put("text",rs.getString("roleName"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("areaId")));
                result.put("leaf", 1);
                result.put("checked",false);
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
     * 组织管理配置角色页面可配置角色树查询3
     * @param _jobId
     * @param areaId
     * @return
     * @throws Exception
     */
	public Map getAllRolesInArea(int jobId1,int jobId2,int jobId3,int areaId) throws Exception{
		StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ROLE_ID AS roleId, B.AREA_ID AS areaId, B.ROLE_NAME AS roleName");
        sql.append(" FROM UOS_JOB_PRIV A, UOS_ROLE B");
        sql.append(" WHERE A.ROLE_ID = B.ROLE_ID AND A.CAN_GRANT = '1'");
        sql.append(" AND A.JOB_ID = ");
        sql.append(jobId1);
        sql.append(" AND NOT EXISTS (SELECT * FROM UOS_JOB_PRIV WHERE (JOB_ID = ");
        sql.append(jobId2);
        sql.append(" OR JOB_ID = ");
        sql.append(jobId3);
        sql.append(") AND ROLE_ID = A.ROLE_ID)");
        sql.append(" AND B.AREA_ID = ");
        sql.append(areaId);
        sql.append(" ORDER BY B.ROLE_NAME ");
        
        System.out.println("getAllRolesInArea sql:"+sql.toString());
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("roleId"));
                result.put("text",rs.getString("roleName"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("areaId")));
                result.put("leaf", 1);
                result.put("checked",false);
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
     * 组织模板管理配置角色页面可配置角色树查询
     * @param _jobId
     * @param areaId
     * @return
     * @throws Exception
     */
	public Map getPostRolesInArea(int postId,int areaId) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.ROLE_ID, A.ROLE_NAME, A.AREA_ID, UA.AREA_NAME ");
		sql.append(" FROM UOS_ROLE A ");
		sql.append(" LEFT JOIN UOS_AREA UA ON UA.AREA_ID=A.AREA_ID ");
		sql.append(" WHERE NOT EXISTS (SELECT * FROM UOS_POST_PRIV ");
		sql.append(" WHERE ROLE_ID = A.ROLE_ID AND POST_ID = ? ) ");
		sql.append(" AND A.AREA_ID= ? ");
		sql.append(" ORDER BY ROLE_NAME ");
        
		System.out.println("getPostRolesInArea sql:"+sql.toString());
		
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Map map = new HashMap();
        ArrayList resultList = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql.toString());
            psmt.setInt(1, postId);
            psmt.setInt(2,areaId);
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Map result = new HashMap();
                result.put("id", rs.getInt("ROLE_ID"));
                result.put("text",rs.getString("ROLE_NAME"));
                result.put("AREA_ID",LongUtils.valueOf(rs.getObject("AREA_ID")));
                result.put("AREA_NAME",rs.getString("AREA_NAME"));
                result.put("leaf", 1);
                resultList.add(result);
            }
            map.put("resultList", resultList);
            return map;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
}
