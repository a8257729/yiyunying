package com.ztesoft.mobile.v2.dao.workform.hubei;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class WorkOrderDAOImpl extends BaseDAOImpl implements WorkOrderDAO {

	public List selRecoverReason() throws DataAccessException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT RECOVER_REASON_ID as recoverReasonId,RECOVER_REASON as recoverReason ");
        sb.append("FROM FAULT_RECOVER_REASON ");
        sb.append("WHERE STATE = 1 ");
        sb.append("ORDER BY RECOVER_REASON");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
	}

	public List selOrgList(Long areaId, Long orgId) throws DataAccessException {
        StringBuffer sb = new StringBuffer();
        sb.append("select distinct area_id as AREA_ID,org_id AS ORG_ID,org_name AS ORG_NAME,");
        sb.append(" decode(connect_by_isleaf, 1, 'Y', 0, ' N') AS IS_LEAF,");
        sb.append(" decode(connect_by_isleaf, 1, 'child', 0, 'parent') AS NODE_TYPE");
        sb.append(" from uos_org a WHERE ");
        //sb.append( "(a.area_id='" + areaId + "' and a.org_id!='" + orgId + "') or ");
        sb.append(" a.parent_id='" + orgId + "'");
        sb.append(" connect by prior a.org_id=a.parent_id");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
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
}
