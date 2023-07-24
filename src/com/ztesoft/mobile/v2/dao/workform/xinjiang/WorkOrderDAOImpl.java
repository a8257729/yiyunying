package com.ztesoft.mobile.v2.dao.workform.xinjiang;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.internal.OracleTypes;

import com.ztesoft.eoms.common.db.DbOper;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class WorkOrderDAOImpl extends BaseDAOImpl implements WorkOrderDAO {

	public List selOrgList(Long areaId, Long orgId) throws DataAccessException {
        StringBuffer sb = new StringBuffer();
        sb.append("select distinct area_id as AREA_ID,org_id AS ORG_ID,org_name AS ORG_NAME,");
        sb.append(" decode(connect_by_isleaf, 1, 'Y', 0, ' N') AS IS_LEAF,");
        sb.append(" decode(connect_by_isleaf, 1, 'child', 0, 'parent') AS NODE_TYPE");
        sb.append(" from uos_org a WHERE ");
        //sb.append( "(a.area_id='" + areaId + "' and a.org_id!='" + orgId + "') or ");
        sb.append(" (a.parent_id='" + orgId + "'"+" OR a.org_id='" + orgId + "')");
        sb.append(" connect by prior a.org_id=a.parent_id");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
	}
	
	public List selOrgList(Long areaId, Long orgId,Boolean isRoot) throws DataAccessException {
        StringBuffer sb = new StringBuffer();
        if (isRoot.equals(true))
        {
        	 sb.append("select area_id as AREA_ID,org_id AS ORG_ID,org_name AS ORG_NAME,");
        	 sb.append("'N' AS IS_LEAF,");
        	 sb.append("'parent' AS NODE_TYPE");
        	 sb.append(" from uos_org a WHERE ");
        	 sb.append(" a.org_id='" + orgId + "'");
        	 System.out.println(sb.toString());
        	 return dynamicQueryListByMap(sb.toString(), null, null);
        }
        else {
	        return this.selOrgList(areaId, orgId);
        }
       
       
	}

	public List selJobList(Long areaId, Long orgId) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
        sb.append("select JOB_ID AS JOB_ID, JOB_NAME AS JOB_NAME, 'child' AS NODE_TYPE");
        sb.append(" from uos_job a WHERE ");
        sb.append(" a.org_id='" + orgId + "'");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
	}

	public List selStaffList(Long areaId, Long jobId)
			throws DataAccessException {
		StringBuffer sb = new StringBuffer();
        sb.append("select a.staff_id as staff_id, a.staff_name as staff_name, a.username as username, 'child' as node_type from uos_staff a,");
        sb.append(" uos_job_staff b, uos_job c, uos_org d");
        sb.append(" where a.staff_id=b.staff_id and b.job_id=c.job_id ");
        sb.append(" and c.org_id=d.org_id and a.state=1 ");
        sb.append(" and c.job_id='" + jobId + "'");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
	}
	
	public List selStaffList(Long orgId)
			throws DataAccessException {
		StringBuffer sb = new StringBuffer();
        sb.append("select a.staff_id as staff_id, a.staff_name as staff_name, a.username as username, 'child' as node_type from VW_STAFF_QUERY a");
        sb.append(" where  a.org_id='" + orgId + "'");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
	}

	public Map<String, Object> checkWorkOrderReply(String wkOrderId,
			String hguSN, String stbMac) throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;  
		CallableStatement callStmt = null; 
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_Qry_IsReturn(?,?,?,?,?) }");
			callStmt.setString(1, wkOrderId);
			callStmt.setString(2, hguSN==null?"":hguSN.trim());
			callStmt.setString(3, stbMac==null?"":stbMac.trim());
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callStmt.execute();
			
			String outFlag = callStmt.getString(4);
			String outDesc = callStmt.getString(5);
			resultMap.put("out_flag", outFlag);
			resultMap.put("outDesc", outDesc);
		} catch (SQLException e) {
			resultMap.put("out_flag", "-1");
			resultMap.put("outDesc", "调用f_Qry_IsReturn失败");
			e.printStackTrace();
		}finally {
			DbOper.cleanUp(null, callStmt, null, conn);
		}

		return resultMap;
	}

	public Map<String, Object> checkFaultWorkOrderReply(String yhym, String workOrderId) throws DataAccessException {

		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call F_QRY_ISRETURN_COPY(?,?,?,?) }");
			callStmt.setString(1, workOrderId==null?"":workOrderId.trim());
			callStmt.setString(2, yhym==null?"":yhym.trim());
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.execute();
			String outFlag = callStmt.getString(3);
			String outDesc = callStmt.getString(4);

			resultMap.put("out_flag", outFlag);
			resultMap.put("outDesc", outDesc);
		} catch (SQLException e) {
			resultMap.put("out_flag", "-1");
			resultMap.put("outDesc", "调用f_Qry_IsReturn_copy失败");
			e.printStackTrace();
		}finally {
			DbOper.cleanUp(null, callStmt, null, conn);
		}
		return resultMap;
	}
}
