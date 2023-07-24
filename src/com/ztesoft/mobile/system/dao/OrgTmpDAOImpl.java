package com.ztesoft.mobile.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;


public class OrgTmpDAOImpl extends BaseDAOImpl implements OrgTmpDAO{
	
	/**
	 * 组织模板树查询方法
	 */
	public Map getSubOrgsById(int orgTmpId)throws Exception {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ORG_TMP_ID AS orgTmpId, A.ORG_TMP_NAME AS orgTmpName, A.PARENT_ID AS parentId, A.COMMENTS AS comments, A.STATE AS state,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT A1.ORG_TMP_ID FROM UOS_ORG_TMP A1 WHERE A1.PARENT_ID = A.ORG_TMP_ID AND A1.STATE = '1') THEN 0 ELSE 1 END) AS leaf");
		sqlBuf.append(" FROM UOS_ORG_TMP A WHERE STATE = '1'");
		
		if(orgTmpId == 0){
			sqlBuf.append(" AND (A.PARENT_ID = ");
			sqlBuf.append(orgTmpId);
			sqlBuf.append(" OR A.PARENT_ID IS NULL)");
		}else{
			sqlBuf.append(" AND A.PARENT_ID = ");
			sqlBuf.append(orgTmpId);
		}
		
		sqlBuf.append(" ORDER BY A.ORG_TMP_ID");

		System.out.println(sqlBuf.toString());

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
                result.put("id", rs.getInt("orgTmpId"));
                result.put("orgTmpId", rs.getInt("orgTmpId"));
                result.put("text",rs.getString("orgTmpName"));
                result.put("orgTmpName",rs.getString("orgTmpName"));
                result.put("comments",rs.getString("comments"));
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
	
	public Map getJobTmpGridByOrgTmp(int orgTmpId) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.POST_ID AS postId, A.ORG_TMP_ID AS orgTmpId,A.POST_LEVEL_ID AS postLevelId,B.POST_LEVEL_NAME AS postLevelName,");
		sqlBuf.append(" A.POST_NAME AS postName,A.MAX_STAFFS AS maxStaffs,A.COMMENTS AS comments FROM UOS_POST A, UOS_POST_LEVEL B");
		sqlBuf.append(" WHERE A.STATE = '1' AND A.POST_LEVEL_ID = B.POST_LEVEL_ID");
		sqlBuf.append(" AND A.ORG_TMP_ID = ");
		sqlBuf.append(orgTmpId);
		
		System.out.println("getJobTmpGridByOrgTmp sql:"+sqlBuf.toString());
		
		return (Map)populateQueryByMap(sqlBuf, 0, 0);
	}
	
	public Map findRolesByPost(int postId) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.ROLE_ID AS roleId, B.ROLE_NAME AS roleName,C.AREA_NAME AS areaName,B.COMMENTS AS comments");
		sqlBuf.append(" FROM UOS_POST_PRIV A, UOS_ROLE B,UOS_AREA C WHERE A.ROLE_ID = B.ROLE_ID AND B.AREA_ID = C.AREA_ID");
		sqlBuf.append(" AND C.STATE = '10A' AND A.POST_ID = ");
		sqlBuf.append(postId);
		sqlBuf.append(" ORDER BY B.ROLE_NAME");
		
		System.out.println("findRolesByPost sql:"+sqlBuf.toString());
		
		return (Map)populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 查询职位级别列表
	 * @return
	 * @throws Exception
	 */
	public Map findJobTmpLevelList() throws Exception{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT POST_LEVEL_ID, POST_LEVEL_NAME, POST_LEVEL_VALUE, COMMENTS FROM UOS_POST_LEVEL ");
		return (Map) populateQueryByMap(sqlBuf, 0, 0); 
	}
}
