package com.ztesoft.mobile.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBean;
import com.ztesoft.mobile.systemMobMgr.bean.TreeBeanStaff;
import com.ztesoft.mobile.systemMobMgr.util.BuildTreeUtil;


public class StaffSelDAOImpl extends BaseDAOImpl implements StaffSelDAO {

	/**
	 * 综合查询人员
	 */
	public Map getOrgStaff(String qryType,int orgId, String orgPathCode, String staffName,
			String userName, String officeTel, String mobileTel, int start,
			int limit) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT UOS_STAFF.STAFF_ID AS staffId,");
		sqlBuf.append(" UOS_STAFF.STAFF_NAME AS staffName,");
		sqlBuf.append(" UOS_STAFF.USERNAME AS userName,");
		sqlBuf.append(" UOS_STAFF.EMAIL AS email,");
		sqlBuf.append(" UOS_STAFF.OFFICE_TEL AS officeTel,");
		sqlBuf.append(" UOS_STAFF.MOBILE_TEL AS mobileTel,");
		sqlBuf.append(" UOS_STAFF.ADDRESS1 AS adderss1,");
		sqlBuf.append(" UOS_STAFF.VALID_COMM_MODE AS validCommMode,");
		sqlBuf.append(" UOS_STAFF.COMMENTS AS comments,");
		sqlBuf.append(" UOS_JOB_STAFF.JOB_ID AS jobId,");
		sqlBuf.append(" UOS_JOB_STAFF.IS_NORMAL AS isNormal,");
		sqlBuf.append(" UOS_JOB_STAFF.ISBASIC AS isbasic,");
		sqlBuf.append(" UOS_JOB.JOB_NAME AS jobName,");
		sqlBuf.append(" UOS_ORG.ORG_ID AS orgId,");
		sqlBuf.append(" UOS_ORG.ORG_NAME AS orgName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_NAME AS orgPathName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_CODE AS orgPathCode");
		sqlBuf.append(" FROM UOS_STAFF");
		sqlBuf.append(" INNER JOIN UOS_JOB_STAFF ON UOS_STAFF.STAFF_ID = UOS_JOB_STAFF.STAFF_ID");
		sqlBuf.append(" INNER JOIN UOS_JOB ON UOS_JOB_STAFF.JOB_ID = UOS_JOB.JOB_ID");
		sqlBuf.append(" INNER JOIN UOS_ORG ON UOS_JOB.ORG_ID = UOS_ORG.ORG_ID");
		sqlBuf.append(" WHERE UOS_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_ORG.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.STATE = 1");
		sqlBuf.append(" AND UOS_JOB_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.post_id IS NOT NULL");
		
		//加查询条件
		if("qrySelf".equals(qryType)){
			sqlBuf.append(" AND UOS_ORG.ORG_ID = ");
			sqlBuf.append(orgId);
		}
		if(!"".equals(orgPathCode) && orgPathCode != null && "qrySub".equals(qryType)){
			sqlBuf.append(" AND UOS_ORG.ORG_PATH_CODE LIKE '");
			sqlBuf.append(orgPathCode);
			sqlBuf.append("%'");
		}
		if(!"".equals(staffName) && staffName != null){
			sqlBuf.append(" AND UOS_STAFF.STAFF_NAME LIKE '%");
			sqlBuf.append(staffName);
			sqlBuf.append("%'");
		}
		if(!"".equals(officeTel) && officeTel != null){
			sqlBuf.append(" AND UOS_STAFF.OFFICE_TEL LIKE '%");
			sqlBuf.append(officeTel);
			sqlBuf.append("%'");
		}
		if(!"".equals(mobileTel) && mobileTel != null){
			sqlBuf.append(" AND UOS_STAFF.MOBILE_TEL LIKE '%");
			sqlBuf.append(mobileTel);
			sqlBuf.append("%'");
		}
		if(!"".equals(userName) && userName != null){
			sqlBuf.append(" AND UOS_STAFF.USERNAME LIKE '%");
			sqlBuf.append(userName);
			sqlBuf.append("%'");
		}
		
		System.out.println(sqlBuf.toString());
		return (Map) populateQueryByMap(sqlBuf, start, limit);
	}
	
	/**
	 * 综合查询人员（简化）
	 */
	public Map getOrgStaffForPushMsg(int orgId, String orgPathCode, String staffName) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT UOS_STAFF.STAFF_ID AS id,");
		sqlBuf.append(" UOS_STAFF.STAFF_NAME AS name,");
		sqlBuf.append(" UOS_STAFF.USERNAME AS userName,");
		sqlBuf.append(" UOS_STAFF.EMAIL AS email,");
		sqlBuf.append(" UOS_STAFF.OFFICE_TEL AS officeTel,");
		sqlBuf.append(" UOS_STAFF.MOBILE_TEL AS mobileTel,");
		sqlBuf.append(" UOS_JOB_STAFF.JOB_ID AS jobId,");
		sqlBuf.append(" UOS_ORG.ORG_ID AS orgId,");
		sqlBuf.append(" UOS_ORG.ORG_NAME AS orgName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_NAME AS pathName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_CODE AS orgPathCode");
		sqlBuf.append(" FROM UOS_STAFF");
		sqlBuf.append(" INNER JOIN UOS_JOB_STAFF ON UOS_STAFF.STAFF_ID = UOS_JOB_STAFF.STAFF_ID");
		sqlBuf.append(" INNER JOIN UOS_JOB ON UOS_JOB_STAFF.JOB_ID = UOS_JOB.JOB_ID");
		sqlBuf.append(" INNER JOIN UOS_ORG ON UOS_JOB.ORG_ID = UOS_ORG.ORG_ID");
		sqlBuf.append(" WHERE UOS_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_ORG.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.STATE = 1");
		sqlBuf.append(" AND UOS_JOB_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.post_id IS NOT NULL");
		sqlBuf.append(" AND UOS_STAFF.MOBILE_TEL IS NOT NULL");
		
		//加查询条件
		if(!"".equals(orgId)){
			sqlBuf.append(" AND UOS_ORG.ORG_ID = ");
			sqlBuf.append(orgId);
		}
		if(!"".equals(orgPathCode) && orgPathCode != null){
			sqlBuf.append(" AND UOS_ORG.ORG_PATH_CODE LIKE '");
			sqlBuf.append(orgPathCode);
			sqlBuf.append("%'");
		}
		if(!"".equals(staffName) && staffName != null){
			sqlBuf.append(" AND UOS_STAFF.STAFF_NAME LIKE '%");
			sqlBuf.append(staffName);
			sqlBuf.append("%'");
		}
		
		System.out.println(sqlBuf.toString());
		return (Map) populateQueryByMap(sqlBuf, 200, 0);
	}
	
	/**
	 * 综合查询组织（树状）
	 */
	public Map getOrgForTree(String qryType,int orgId, String orgPathCode, String staffName,
			String userName, String officeTel, String mobileTel, int start,
			int limit) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT distinct P.ORG_ID AS id,");
		sqlBuf.append(" P.ORG_NAME AS name,");
		sqlBuf.append(" P.ORG_PATH_CODE AS pathCode,");
		sqlBuf.append(" P.ORG_PATH_NAME AS pathName,");
		sqlBuf.append(" (CASE WHEN EXISTS (SELECT P1.ORG_ID FROM UOS_ORG P1 ");
		sqlBuf.append(" WHERE P1.STATE = 1 AND P1.PARENT_ID = P.ORG_ID) THEN 0 ELSE 1 END) AS leaf,");
		sqlBuf.append(" (CASE WHEN P.PARENT_ID IS NULL THEN 0 ELSE P.PARENT_ID END) AS parentId");
		sqlBuf.append(" FROM UOS_ORG P");
		sqlBuf.append(" WHERE P.STATE = 1");
		
		System.out.println(sqlBuf.toString());

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
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setPathCode(rs.getString("pathCode"));
//				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(false);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");

			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBean nebean = (TreeBean)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			map.put("resultList", newresultlist);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
		return map;
	}
	
	/**
	 * 综合查询人员（树状）
	 */
	public Map getOrgStaffForTree(String qryType,int orgId, String orgPathCode, String staffName,
			String userName, String officeTel, String mobileTel, int start,
			int limit) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();

		sqlBuf.append("SELECT UOS_STAFF.STAFF_ID AS id,");
		sqlBuf.append(" UOS_STAFF.STAFF_NAME AS name,");
		sqlBuf.append(" UOS_STAFF.USERNAME AS userName,");
		sqlBuf.append(" UOS_STAFF.EMAIL AS email,");
		sqlBuf.append(" UOS_STAFF.OFFICE_TEL AS officeTel,");
		sqlBuf.append(" UOS_STAFF.MOBILE_TEL AS mobileTel,");
		sqlBuf.append(" UOS_STAFF.ADDRESS1 AS adderss1,");
		sqlBuf.append(" UOS_STAFF.VALID_COMM_MODE AS validCommMode,");
		sqlBuf.append(" UOS_STAFF.COMMENTS AS comments,");
		sqlBuf.append(" UOS_JOB_STAFF.JOB_ID AS jobId,");
		sqlBuf.append(" UOS_JOB_STAFF.IS_NORMAL AS isNormal,");
		sqlBuf.append(" UOS_JOB_STAFF.ISBASIC AS isbasic,");
		sqlBuf.append(" UOS_JOB.JOB_NAME AS jobName,");
		sqlBuf.append(" UOS_ORG.ORG_ID AS orgId,");
		sqlBuf.append(" UOS_ORG.ORG_NAME AS orgName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_NAME AS pathName,");
		sqlBuf.append(" UOS_ORG.ORG_PATH_CODE AS orgPathCode,");
		sqlBuf.append(" '1'                         AS leaf,");
		sqlBuf.append(" '0'                         AS parentId");
		sqlBuf.append(" FROM UOS_STAFF");
		sqlBuf.append(" INNER JOIN UOS_JOB_STAFF ON UOS_STAFF.STAFF_ID = UOS_JOB_STAFF.STAFF_ID");
		sqlBuf.append(" INNER JOIN UOS_JOB ON UOS_JOB_STAFF.JOB_ID = UOS_JOB.JOB_ID");
		sqlBuf.append(" INNER JOIN UOS_ORG ON UOS_JOB.ORG_ID = UOS_ORG.ORG_ID");
		sqlBuf.append(" WHERE UOS_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_ORG.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.STATE = 1");
		sqlBuf.append(" AND UOS_JOB_STAFF.STATE = 1");
		sqlBuf.append(" AND UOS_JOB.post_id IS NOT NULL");
		
		//加查询条件
		if (!"qryAllStaff".equals(qryType)) {
			if(!"".equals(orgId)){
				sqlBuf.append(" AND UOS_ORG.ORG_ID = ");
				sqlBuf.append(orgId);
			}
			if(!"".equals(orgPathCode) && orgPathCode != null){
				sqlBuf.append(" AND UOS_ORG.ORG_PATH_CODE LIKE '");
				sqlBuf.append(orgPathCode);
				sqlBuf.append("%'");
			}
		}
		System.out.println(sqlBuf.toString());

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
				TreeBeanStaff bean = new TreeBeanStaff();
				bean.setId(rs.getString("id"));
				bean.setText(rs.getString("name"));
				bean.setParentId(rs.getString("parentId"));
				bean.setLeaf(rs.getInt("leaf")==1?true:false);
				bean.setMobileTel(rs.getString("mobileTel"));
//				bean.setPrivCode(rs.getString("privCode"));
				bean.setChecked(false);
				alllist.add(bean);				
				
			}
			
			resultList = BuildTreeUtil.buildTreeData(alllist, "getId", "getParentId");

			List newresultlist = new ArrayList();
			for(int k=0;k<resultList.size();k++){
				TreeBeanStaff nebean = (TreeBeanStaff)resultList.get(k);
				if(nebean.isLeaf()){	
					newresultlist.add(nebean);
				}else {
					if(nebean.getChildren()==null){  //删除没有子节点的树
						continue;
					}else {
						newresultlist.add(nebean);
					}
				}
			}
			map.put("resultList", newresultlist);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			cleanUp(conn, null, psmt, rs);
		}
		return map;
	}
	
	/**
	 * 综合查询无职位人员
	 */
	public Map getNoJobStaff(String staffName, String userName, String officeTel, 
			String mobileTel, int start, int limit) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		
		sqlBuf.append("SELECT A.STAFF_ID AS staffId,");
		sqlBuf.append(" A.STAFF_NAME AS staffName,");
		sqlBuf.append(" A.USERNAME AS userName,");
		sqlBuf.append(" A.EMAIL AS email,");
		sqlBuf.append(" A.OFFICE_TEL AS officeTel,");
		sqlBuf.append(" A.MOBILE_TEL AS mobileTel,");
		sqlBuf.append(" A.ADDRESS1 AS address1,");
		sqlBuf.append(" A.ADDRESS2 AS address2,");
		sqlBuf.append(" A.VALID_COMM_MODE AS validCommMode,");
		sqlBuf.append(" A.COMMENTS AS comments,");
		sqlBuf.append(" 0 AS jobId,");
		sqlBuf.append(" 0 AS orgId,");
		sqlBuf.append(" '' AS orgName,");
		sqlBuf.append(" '' AS orgPathName,");
		sqlBuf.append(" 2 AS isbasic");
		sqlBuf.append(" FROM UOS_STAFF A WHERE A.STATE = '1'");
		sqlBuf.append(" AND A.STAFF_ID NOT IN");
		sqlBuf.append(" (SELECT STAFF_ID FROM UOS_JOB_STAFF WHERE STATE = '1' AND IS_NORMAL = '1')");
		sqlBuf.append(" AND NOT EXISTS (SELECT 'a' FROM UOS_JOB_STAFF B WHERE B.STAFF_ID = A.STAFF_ID AND B.STATE = '1' AND B.IS_NORMAL = '1')");
		
		
		if(!"".equals(staffName) && staffName != null){
			sqlBuf.append(" AND A.STAFF_NAME LIKE '%");
			sqlBuf.append(staffName);
			sqlBuf.append("%'");
		}
		if(!"".equals(officeTel) && officeTel != null){
			sqlBuf.append(" AND A.OFFICE_TEL LIKE '%");
			sqlBuf.append(officeTel);
			sqlBuf.append("%'");
		}
		if(!"".equals(mobileTel) && mobileTel != null){
			sqlBuf.append(" AND A.MOBILE_TEL LIKE '%");
			sqlBuf.append(mobileTel);
			sqlBuf.append("%'");
		}
		if(!"".equals(userName) && userName != null){
			sqlBuf.append(" AND A.USERNAME LIKE '%");
			sqlBuf.append(userName);
			sqlBuf.append("%'");
		}
		
		System.out.println("getNoJobStaff sql :"+sqlBuf.toString());
		return (Map) populateQueryByMap(sqlBuf, start, limit);
	}
	
	/**
	 * 根据人员、职位查询角色
	 */
	public Map getRolesByStaff(int staffId,int jobId)throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT STAFF_ID  AS staffId,ROLE_ID AS roleId, ROLE_NAME AS roleName,CAN_GRANT AS canGrant, SOURCE AS source");
		sqlBuf.append(" FROM VW_STAFF_FULL_ROLE V");
		sqlBuf.append(" WHERE STAFF_ID = ");
		sqlBuf.append(staffId);
		sqlBuf.append(" AND (V.job_id = ");
		sqlBuf.append(jobId);
		sqlBuf.append(" OR V.source = 2)");
		
		System.out.println(sqlBuf.toString());
		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
	
	/**
	 * 根据人员、职位查询权限菜单
	 */
	public Map getPrivilegeByStaff(int staffId,int jobId)throws Exception {
		int specialJobId = findJobIdByName("JOB_" + staffId);
		return findPrivsByStaffFromView(jobId,specialJobId);
	}
	
	/**
	 * 查询权限菜单
	 * @param jobId
	 * @param specialJobId
	 * @return
	 * @throws Exception
	 */
	public Map findPrivsByStaffFromView(int jobId, int specialJobId) throws Exception{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT DISTINCT PRIV_CODE,PRIV_NAME,PRIV_CLASS_ID,SOURCE,CAN_GRANT ");
		sqlBuf.append(" FROM VW_STAFF_FULL_PRIV ");
		sqlBuf.append(" WHERE (JOB_ID = ");
		sqlBuf.append(jobId);
		sqlBuf.append(" OR JOB_ID= ");
		sqlBuf.append(specialJobId);
		sqlBuf.append(" ) ORDER BY PRIV_NAME");
        
		return (Map) populateQueryByMap(sqlBuf, 0, 0); 
	}
	
	/**
	 * 查询名族列表
	 * @return
	 * @throws Exception
	 */
	public Map findNationList() throws Exception{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT N.ID AS id,N.NATION_NAME AS nationName FROM UOS_NATION N ORDER BY N.ID ");
		return (Map) populateQueryByMap(sqlBuf, 0, 0); 
	}
	
	/**
     * 根据职位名查询职位编号
     * @param jobName String 职位名
     * @throws SQLException
     * @return int 职位编号
     */
    public int findJobIdByName(String jobName) throws SQLException {
        String sql = "SELECT JOB_ID FROM UOS_JOB WHERE JOB_NAME=? AND STATE='1'";
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, jobName);
            rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            else {
                return -1;
            }
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }

    }
    
    /**
     * 根据职位ID查询其下属人员
     */
    public Map getStaffsByJob(int jobId,int startIndex,int stepSize)throws DataAccessException {
    	StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("SELECT A.STAFF_ID AS staffId,");
    	sqlBuf.append(" A.STAFF_NAME AS staffName,");
    	sqlBuf.append(" A.USERNAME AS userName,");
    	sqlBuf.append(" A.PASSWORD AS password,");
    	sqlBuf.append(" A.EMAIL AS email,");
    	sqlBuf.append(" A.HOME_TEL AS homeTel,");
    	sqlBuf.append(" A.OFFICE_TEL AS officeTel,");
    	sqlBuf.append(" A.MOBILE_TEL AS mobile,");
    	sqlBuf.append(" A.ADDRESS1 AS address1,");
    	sqlBuf.append(" A.ADDRESS2 AS address2,");
    	sqlBuf.append(" A.VALID_COMM_MODE AS validCommMode,");
    	sqlBuf.append(" A.EFFECT_DATE AS effectDate,");
    	sqlBuf.append(" A.EXPIRE_DATE AS expireDate,");
    	sqlBuf.append(" A.UPDATE_DATE AS updateDate,");
    	sqlBuf.append(" A.COMMENTS AS comments,");
    	sqlBuf.append(" A.DEFAULT_SERVICE_ABILITY_ID AS defaultServiceAbility,");
    	sqlBuf.append(" A.SERVER_GRADE AS serverGrade,");
    	sqlBuf.append(" A.CONSTRUCT_ABILITY AS constructAbility,");
    	sqlBuf.append(" A.FAULT_COMPLET AS faultComplet,");
    	sqlBuf.append(" A.ISPARTER AS isparter,");
    	sqlBuf.append(" A.PHS_TEL AS phsTel,");
    	sqlBuf.append(" A.LOCKED_DATE AS lockedDate,");
    	sqlBuf.append(" A.HIS_PWD AS hisPwd,");
    	sqlBuf.append(" B.IS_NORMAL AS isNormal,");
    	sqlBuf.append(" B.ISBASIC AS isbasic,");
    	sqlBuf.append(" C.JOB_ID AS jobId,");
    	sqlBuf.append(" C.JOB_NAME AS jobName,");
    	sqlBuf.append(" D.ORG_ID AS orgId,");
    	sqlBuf.append(" D.ORG_NAME AS orgName,");
    	sqlBuf.append(" D.ORG_PATH_NAME AS orgPathName");
    	sqlBuf.append(" FROM UOS_STAFF A, UOS_JOB_STAFF B, UOS_JOB C, UOS_ORG D");
    	sqlBuf.append(" WHERE A.STAFF_ID = B.STAFF_ID");
    	sqlBuf.append(" AND B.JOB_ID = C.JOB_ID");
    	sqlBuf.append(" AND C.ORG_ID = D.ORG_ID");
    	sqlBuf.append(" AND B.STATE = '1'");
    	sqlBuf.append(" AND D.STATE = '1'");
    	sqlBuf.append(" AND C.STATE = '1'");
    	sqlBuf.append(" AND B.JOB_ID = ");
    	sqlBuf.append(jobId);
    	
    	System.out.println("getStaffsByJob sql:"+sqlBuf.toString());
        
        return (Map)populateQueryByMap(sqlBuf, startIndex, stepSize);
    }
    
    /**
     * 根据主键查询人员
     * @param staffId int 人员编号
     * @throws SQLException
     * @return Staff 人员对象
     */
    public Staff findStaffByKey(int staffId) throws SQLException {
    	StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("SELECT A.STAFF_ID AS staffId,");
    	sqlBuf.append(" A.STAFF_NAME AS staffName,");
    	sqlBuf.append(" A.USERNAME AS userName,");
    	sqlBuf.append(" A.PASSWORD AS password,");
    	sqlBuf.append(" A.EMAIL AS email,");
    	sqlBuf.append(" A.HOME_TEL AS homeTel,");
    	sqlBuf.append(" A.OFFICE_TEL AS officeTel,");
    	sqlBuf.append(" A.MOBILE_TEL AS mobile,");
    	sqlBuf.append(" A.ADDRESS1 AS address1,");
    	sqlBuf.append(" A.ADDRESS2 AS address2,");
    	sqlBuf.append(" A.VALID_COMM_MODE AS validCommMode,");
    	sqlBuf.append(" A.EFFECT_DATE AS effectDate,");
    	sqlBuf.append(" A.EXPIRE_DATE AS expireDate,");
    	sqlBuf.append(" A.COMMENTS AS comments,");
    	sqlBuf.append(" A.CONSTRUCT_ABILITY AS constructAbility,");
    	sqlBuf.append(" B.IS_NORMAL AS isNormal,");
    	sqlBuf.append(" B.ISBASIC AS isbasic,");
    	sqlBuf.append(" C.JOB_ID AS jobId,");
    	sqlBuf.append(" C.JOB_NAME AS jobName,");
    	sqlBuf.append(" D.ORG_ID AS orgId,");
    	sqlBuf.append(" D.ORG_NAME AS orgName,");
    	sqlBuf.append(" D.ORG_PATH_NAME AS orgPathName,");
    	sqlBuf.append(" A.LOGON_NUMBER AS logonNumber,");
    	sqlBuf.append(" A.NATION_ID AS nationId,");
    	sqlBuf.append(" A.CERT_NO AS certNo");
    	sqlBuf.append(" FROM UOS_STAFF A, UOS_JOB_STAFF B, UOS_JOB C, UOS_ORG D");
    	sqlBuf.append(" WHERE A.STAFF_ID = B.STAFF_ID");
    	sqlBuf.append(" AND B.JOB_ID = C.JOB_ID");
    	sqlBuf.append(" AND C.ORG_ID = D.ORG_ID");
    	sqlBuf.append(" AND B.STATE = '1'");
    	sqlBuf.append(" AND D.STATE = '1'");
    	sqlBuf.append(" AND C.STATE = '1'");
    	sqlBuf.append(" AND A.STAFF_ID = ?");
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Staff staff = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setInt(1, staffId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                
                staff.setStaffId(staffId);
                staff.setStaffName(rs.getString("staffName"));
                staff.setUserName(rs.getString("userName"));
                staff.setPassword(rs.getString("password"));
                staff.setEmail(rs.getString("email"));
                staff.setHomeTel(rs.getString("homeTel"));
                staff.setOfficeTel(rs.getString("officeTel"));
                staff.setMobileTel(rs.getString("mobile"));
                staff.setAddress1(rs.getString("address1"));
                staff.setAddress2(rs.getString("address2"));
                staff.setValidCommMode(rs.getInt("validCommMode")+"");
                staff.setEffectDate(rs.getDate("effectDate"));
                staff.setExpireDate(rs.getDate("expireDate"));
                staff.setComments(rs.getString("comments"));
                staff.setConstructAbility(rs.getLong("constructAbility"));
                staff.setIsBasic(rs.getInt("isbasic")+"");
                staff.setJobId(rs.getInt("jobId"));
                staff.setJobName(rs.getString("jobName"));
                staff.setOrgId(rs.getInt("orgId"));
                staff.setOrgName(rs.getString("orgName"));
                staff.setOrgPathName(rs.getString("orgPathName"));
                staff.setLogonNumber(rs.getInt("logonNumber"));
                staff.setNationId(rs.getLong("nationId"));
                staff.setCertNo(rs.getString("certNo"));
            }
            return staff;
        }
        finally {
            cleanUp(conn, null, psmt, rs);
        }

    }

	public Staff selStaff(Map paramMap) throws DataAccessException {
    	StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append(" select us.username as username, us.staff_name as staffName, us.mobile_tel as mobileTel, us.email as email, us.address1 as address1, ");
    	sqlBuf.append(" us.address2 as address2, us.agent as agent, us.password as password ");
    	sqlBuf.append(" from uos_staff us where ");
    	
    	String parameter = "";
    	if( !"".equals(paramMap.get("username")) ){
    		sqlBuf.append(" us.username=? ");
    		parameter = (String) paramMap.get("username");
    	}else if( !"".equals(paramMap.get("address2")) ){
    		sqlBuf.append(" us.address2=? ");
    		parameter = (String) paramMap.get("address2");
    	}    	
    	
    	System.out.println("=====sql :"+sqlBuf.toString());
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Staff staff = null;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setString(1, parameter);
            rs = psmt.executeQuery();
            if (rs.next()) {
                staff = new Staff();
                
//                staff.setStaffId(Integer.parseInt((String)paramMap.get("staffId")));
                staff.setStaffName(rs.getString("staffName"));
                staff.setUserName(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                staff.setEmail(rs.getString("email"));
//                staff.setHomeTel("");//rs.getString("homeTel"));
//                staff.setOfficeTel("");//rs.getString("officeTel"));
                staff.setMobileTel(rs.getString("mobileTel"));
                staff.setAddress1(rs.getString("address1"));
                staff.setAddress2(rs.getString("address2"));
                staff.setAgent(rs.getLong("agent"));
//                staff.setValidCommMode("");//rs.getInt("validCommMode")+"");
//                staff.setEffectDate(rs.getDate("effectDate"));
//                staff.setExpireDate(rs.getDate("expireDate"));
//                staff.setComments("");//rs.getString("comments"));
//                staff.setConstructAbility(rs.getLong("constructAbility"));
//                staff.setIsBasic(rs.getInt("isbasic")+"");
//                staff.setJobId(rs.getInt("jobId"));
//                staff.setJobName(rs.getString("jobName"));
//                staff.setOrgId(rs.getInt("orgId"));
//                staff.setOrgName(rs.getString("orgName"));
//                staff.setOrgPathName(rs.getString("orgPathName"));
//                staff.setLogonNumber(rs.getInt("logonNumber"));
//                staff.setNationId(rs.getLong("nationId"));
//                staff.setCertNo(rs.getString("certNo"));
            }
        }catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
        return staff;
	}

	public int updataStaff(Map paramMap) throws DataAccessException {
//		System.out.println("===========paramMap: "+paramMap.toString());
//		String updatePatternStr = "username:username,staff_name:staffName,mobile_tel:mobileTel,email:email,address1:address1";
//		String wherePatternStr = "username:username";
//		return dynamicUpdateByMap(paramMap, "uos_staff", updatePatternStr, wherePatternStr);
		StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("UPDATE uos_staff SET STAFF_NAME=?, MOBILE_TEL=?, EMAIL=?, ADDRESS1=?, ADDRESS2=?, AGENT=? WHERE USERNAME=? ");
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Staff staff = null;
        int ri = 0;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setString(1, (String) paramMap.get("staffName"));
            psmt.setString(2, (String) paramMap.get("mobileTel"));
            psmt.setString(3, (String) paramMap.get("email"));
            psmt.setString(4, (String) paramMap.get("address1"));
            psmt.setString(5, (String) paramMap.get("address2"));
            psmt.setInt(6, Integer.parseInt((String) paramMap.get("agent")));
            psmt.setString(7, (String) paramMap.get("username"));
            ri = psmt.executeUpdate();
        }catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
        return ri;
	}

	public int selImsi(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
    	sqlBuf.append("select us.username as username from uos_staff us where us.address2=? and us.username<>? ");
    	
        Connection conn = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Staff staff = null;
        int ri = 0;
        try {
            conn = getConnection();
            psmt = conn.prepareStatement(sqlBuf.toString());
            psmt.setString(1, (String) paramMap.get("address2"));
            psmt.setString(2, (String) paramMap.get("username"));
            rs = psmt.executeQuery();
            
            if (rs.next()) {
            	ri++;
            }
        }catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            cleanUp(conn, null, psmt, rs);
        }
        return ri;
	} 
		
}
