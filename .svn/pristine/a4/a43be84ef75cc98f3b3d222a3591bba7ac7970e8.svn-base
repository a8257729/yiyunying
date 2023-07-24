package com.ztesoft.mobile.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.vo.Job;
import com.zterc.uos.oaas.vo.Organization;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class JobSelDAOImpl extends BaseDAOImpl implements JobSelDAO {
	
	public Map getJobGridByOrg(int orgId) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.JOB_ID AS jobId,");
		sqlBuf.append(" A.JOB_NAME AS jobName,");
		sqlBuf.append(" A.POST_ID AS postId,");
		sqlBuf.append(" B.POST_NAME AS postName,");
		sqlBuf.append(" A.COMMENTS AS comments,");
		sqlBuf.append(" C.ORG_PATH_NAME AS orgPathCode,");
		sqlBuf.append(" C.AREA_ID AS areaId,");
		sqlBuf.append(" D.AREA_NAME AS areaName");
		sqlBuf.append(" FROM UOS_JOB A, UOS_POST B, UOS_ORG C, UOS_AREA D");
		sqlBuf.append(" WHERE A.POST_ID = B.POST_ID");
		sqlBuf.append(" AND C.AREA_ID = D.AREA_ID");
		sqlBuf.append(" AND A.JOB_NAME NOT LIKE 'JOB_%'");
		sqlBuf.append(" AND A.STATE = '1'");
		sqlBuf.append(" AND B.STATE = '1'");
		sqlBuf.append(" AND C.STATE = '1'");
		sqlBuf.append(" AND D.STATE = '10A'");
		sqlBuf.append(" AND C.ORG_ID = ");
		sqlBuf.append(orgId);
		sqlBuf.append(" AND A.ORG_ID = ");
		sqlBuf.append(orgId);
		
		return (Map)populateQueryByMap(sqlBuf, 0, 0);
	}

	public Map getJobByOrg(int orgId) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT J.JOB_ID AS jobId,J.JOB_NAME AS jobName FROM UOS_JOB J WHERE J.POST_ID IS NOT NULL AND J.STATE = '1' AND J.ORG_ID = ");
		sqlBuf.append(orgId);
		
		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 根据 orgPathCode 查询其下所有职位所属组织
	 * @param orgPathCode
	 * @return
	 * @throws DataAccessException
	 */
	public Map getOrgByPathCode(String orgPathCode) throws Exception{
		Map orgs = new HashMap();
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT DISTINCT O.ORG_ID AS orgId,O.ORG_NAME AS orgName,O.ORG_PATH_CODE AS orgPathCode,O.PARENT_ID AS parentId");
		sqlBuf.append(" FROM UOS_JOB J, UOS_ORG O");
		sqlBuf.append(" WHERE J.ORG_ID = O.ORG_ID");
		sqlBuf.append(" AND POST_ID IS NOT NULL");
		
		if(!"-1".equals(orgPathCode)){
			sqlBuf.append(" AND O.ORG_PATH_CODE LIKE '");
			sqlBuf.append( orgPathCode);
			sqlBuf.append("%'");
		}
		
		sqlBuf.append(" ORDER BY O.ORG_PATH_CODE");
		
		System.out.println("getOrgByPathCode sql:" + sqlBuf.toString());
		
		Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
            	Organization org = new Organization();
                org.setOrgId(rs.getInt("orgId"));
                org.setOrgName(rs.getString("orgName"));
                org.setParentId(rs.getInt("parentId"));
                
                orgs.put(rs.getInt("orgId"),org);
            }
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
		
		return orgs;
	}
	
	/**
	 * 根据 orgPathCode 查询其下所有职位
	 * @param orgPathCode
	 * @return
	 * @throws DataAccessException
	 */
	public Job[] getJobByPathCode(String orgPathCode) throws Exception{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT J.JOB_ID AS jobId,");
		sqlBuf.append(" J.JOB_NAME AS jobName,");
		sqlBuf.append(" J.ORG_ID AS orgId");
		sqlBuf.append(" FROM UOS_JOB J, UOS_ORG O");
		sqlBuf.append(" WHERE J.ORG_ID = O.ORG_ID");
		sqlBuf.append(" AND POST_ID IS NOT NULL");
		
		if(!"-1".equals(orgPathCode)){
			sqlBuf.append(" AND O.ORG_PATH_CODE LIKE '");
			sqlBuf.append( orgPathCode);
			sqlBuf.append("%'");
		}
		
		sqlBuf.append(" ORDER BY O.ORG_PATH_CODE");
		
		System.out.println("getJobByPathCode sql :"+sqlBuf.toString());
		
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList orgs = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            rs = psmt.executeQuery();
            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("jobId"));
                job.setJobName(rs.getString("jobName"));
                job.setOrgId(rs.getInt("orgId"));
                orgs.add(job);
            }
            return (Job[]) orgs.toArray(new Job[orgs.size()]);
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
	}
	
	/**
     * 查询员工的职位
     * @param staffId int 员工编号
     * @param isAll boolean 是否是全部职位，如果为true是全部职位，为false不包含默认职位
     * @throws OAASException
     * @return Job[] 职位
     */
    public Job[] findByStaff(int staffId) throws SQLException {
        String sql = null;
        sql = "SELECT B.JOB_ID,B.JOB_NAME,B.ORG_ID,B.POST_ID,C.ORG_PATH_NAME,A.ISBASIC FROM UOS_JOB_STAFF A,UOS_JOB B,UOS_ORG C WHERE A.STAFF_ID=? AND A.JOB_ID=B.JOB_ID AND B.ORG_ID=C.ORG_ID AND A.STATE='1' AND A.IS_NORMAL='1'";
        
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        ArrayList jobs = new ArrayList();
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setInt(1, staffId);
            rs = psmt.executeQuery();
            while (rs.next()) {
                Job job = new Job();
                job.setJobId(rs.getInt("JOB_ID"));
                job.setJobName(rs.getString("JOB_NAME"));
                job.setOrgId(rs.getInt("ORG_ID"));
                job.setIsBasic(rs.getString("ISBASIC"));
                jobs.add(job);
                
                System.out.println("人员特有职位是否默认职位："+job.getIsBasic()+"%%%%");
            }
            return (Job[]) jobs.toArray(new Job[jobs.size()]);
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }
    }
	

}
