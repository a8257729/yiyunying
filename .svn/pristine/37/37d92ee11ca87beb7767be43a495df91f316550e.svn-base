package com.ztesoft.eoms.common.oaas;

import java.sql.SQLException;
import java.util.Map;

import com.zterc.uos.UOSException;
import com.zterc.uos.oaas.vo.PostLevel;
import com.zterc.uos.oaas.vo.Organization;
import com.zterc.uos.oaas.vo.Staff;

import java.util.List;

/**
 * <p>
 * Title: EomsProj
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 *
 * <p>
 * Company:
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
public interface OaasOper {

	/**
	 * 根据当前组织，查询当前组织及其下所有子组织
	 *
	 * @param orgId
	 * @return
	 * @throws UOSException
	 */
	public Organization[] findSimpleAllSubOrgs(Long orgId) throws UOSException;

	/**
	 * 构造一个部门以及下属部门的所有部门树 新方法
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */
	public String qryOrgsById(Long orgId) throws SQLException, UOSException;

	/**
	 * 构造部门以及下属部门的所有部门树
	 *
	 * @param orgIdArr
	 *            Long[]
	 * @return String
	 * @throws SQLException
	 * @throws UOSException
	 */
	public String qryOrgsByIds(Long[] orgIdArr) throws SQLException,
			UOSException;

	/**
	 * 构造一个查询二级部门以及下属部门的所有部门树 新方法
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */

	public String qrySecondOrgsById(Long orgId) throws UOSException;

	public String qryAllOrg() throws SQLException, UOSException;

	/**
	 * 查询某个组织下人员，不查询子树
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return Staff[]
	 */
	public StaffDto[] findStaffByOrgId(long orgId) throws UOSException;

	/**
	 * 根据id获得staff对象 (using)
	 *
	 * @param Id
	 *            long
	 * @throws OAASException
	 * @return StaffDto
	 */
	public StaffDto getStaffById(long Id) throws UOSException;

	/**
	 * 根据ID或者org对象
	 */
	public Organization getOrgById(long orgId) throws UOSException;

	public Organization getOrgByStaffId(int staffId) throws UOSException;

	public String getOrgNameByOrgId(long orgId) throws UOSException;

	public String findOrgByOrgTemp(int orgId, int orgTempId)
			throws SQLException, UOSException;

	public String findOrganization1Org(int OrgId) throws UOSException;

	/**
	 * 根据职位标识查询岗位级别
	 *
	 * @param jobId
	 *            Long
	 * @return PostLevel
	 * @throws SQLException
	 */
	public PostLevel qryPostLevelByJobId(Long jobId) throws SQLException;

	/**
	 * 根据ID查询父组织和父组织的所有子组织
	 *
	 * @param orgId
	 *            Long
	 * @return String
	 * @throws SQLException
	 * @throws UOSException
	 */
	public String qryParentAndSubOrgsById(Long orgId) throws SQLException,
			UOSException;

        public Map qryStaffTelMap(String staffIds) throws SQLException;

        public List qryStaffTelList(String staffIds) throws SQLException;
        
        public Staff getStaffByUserName(String userName) throws UOSException;        
}
