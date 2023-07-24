package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class PopupDAOImpl extends BaseDAOImpl implements PopupDAO {

	public Map selOrgAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select o.org_id        as orgId,");
		strBf.append("       o.org_code      as orgCode,");
		strBf.append("       o.org_name      as orgName,");
		strBf.append("       o.org_path_name as orgPath,");
		strBf.append("       t.org_tmp_name  as orgTmpName");
		strBf.append("  from uos_org o, uos_org_tmp t");
		strBf.append(" where o.org_tmp_id = t.org_tmp_id");
		strBf.append("   and o.state = 1");
		strBf.append("   and t.state = 1");

		if (MapUtils.getString(paramMap, "catalogId") != null
				&& !MapUtils.getString(paramMap, "catalogId").equals("")) {
			strBf.append("   and o.parent_id =  ").append(
					MapUtils.getLong(paramMap, "catalogId"));
		}

		if (MapUtils.getString(paramMap, "orgName") != null
				&& !MapUtils.getString(paramMap, "orgName").equals("")) {
			strBf.append(" and o.org_name like '%").append(
					MapUtils.getString(paramMap, "orgName")).append("%'");
		}
		if (MapUtils.getString(paramMap, "orgCode") != null
				&& !MapUtils.getString(paramMap, "orgCode").equals("")) {
			strBf.append(" and o.org_code like '%").append(
					MapUtils.getString(paramMap, "orgCode")).append("%'");
		}

		strBf.append("   order by o.org_id");

		System.out.println("selJobAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}

	public Map selJobAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select j.job_id        as jobId,");
		strBf.append("       j.job_name      as jobName,");
		strBf.append("       o.org_code      as orgCode,");
		strBf.append("       o.org_path_name as orgPath");
		strBf.append("  from uos_job j, uos_org o");
		strBf.append(" where j.org_id = o.org_id");
		strBf.append("   and o.state = 1");
		strBf.append("   and j.state = 1");

		if (MapUtils.getString(paramMap, "catalogId") != null
				&& !MapUtils.getString(paramMap, "catalogId").equals("")) {
			strBf.append("   and o.org_id =  ").append(
					MapUtils.getLong(paramMap, "catalogId"));
		}

		if (MapUtils.getString(paramMap, "jobName") != null
				&& !MapUtils.getString(paramMap, "jobName").equals("")) {
			strBf.append(" and j.job_name like '%").append(
					MapUtils.getString(paramMap, "jobName")).append("%'");
		}
		strBf.append("   order by o.org_id");

		System.out.println("selJobAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}

	public Map selStaffAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select s.staff_id      as staffId,");
		strBf.append("       s.staff_name    as staffName,");
		strBf.append("       s.username      as userName,");
		strBf.append("       s.staff_code    as staffCode,");
		strBf.append("       j.job_name      as jobName,");
		strBf.append("       o.org_path_name as orgPath,");
		strBf.append("       s.office_tel    as officeTel,");
		strBf.append("       s.mobile_tel    as mobileTel");
		strBf
				.append("  from uos_staff s, uos_job_staff js, uos_job j, uos_org o");
		strBf.append(" where s.staff_id = js.staff_id");
		strBf.append("   and js.job_id = j.job_id");
		strBf.append("   and j.org_id = o.org_id");
		strBf.append("   and s.state = 1");
		strBf.append("   and js.state = 1");
		strBf.append("   and o.state = 1");
		strBf.append("   and j.state = 1");

		if (MapUtils.getString(paramMap, "catalogId") != null
				&& !MapUtils.getString(paramMap, "catalogId").equals("")) {
			strBf.append("   and o.org_id =  ").append(
					MapUtils.getLong(paramMap, "catalogId"));
		}

		if (MapUtils.getString(paramMap, "staffName") != null
				&& !MapUtils.getString(paramMap, "staffName").equals("")) {
			strBf.append(" and s.staff_name like '%").append(
					MapUtils.getString(paramMap, "staffName")).append("%'");
		}
		if (MapUtils.getString(paramMap, "userName") != null
				&& !MapUtils.getString(paramMap, "userName").equals("")) {
			strBf.append(" and s.username like '%").append(
					MapUtils.getString(paramMap, "userName")).append("%'");
		}
		strBf.append("   order by o.org_id");

		System.out.println("selStaffAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}
}
