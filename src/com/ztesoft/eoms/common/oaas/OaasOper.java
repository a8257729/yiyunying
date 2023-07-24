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
	 * ���ݵ�ǰ��֯����ѯ��ǰ��֯��������������֯
	 *
	 * @param orgId
	 * @return
	 * @throws UOSException
	 */
	public Organization[] findSimpleAllSubOrgs(Long orgId) throws UOSException;

	/**
	 * ����һ�������Լ��������ŵ����в����� �·���
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */
	public String qryOrgsById(Long orgId) throws SQLException, UOSException;

	/**
	 * ���첿���Լ��������ŵ����в�����
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
	 * ����һ����ѯ���������Լ��������ŵ����в����� �·���
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */

	public String qrySecondOrgsById(Long orgId) throws UOSException;

	public String qryAllOrg() throws SQLException, UOSException;

	/**
	 * ��ѯĳ����֯����Ա������ѯ����
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return Staff[]
	 */
	public StaffDto[] findStaffByOrgId(long orgId) throws UOSException;

	/**
	 * ����id���staff���� (using)
	 *
	 * @param Id
	 *            long
	 * @throws OAASException
	 * @return StaffDto
	 */
	public StaffDto getStaffById(long Id) throws UOSException;

	/**
	 * ����ID����org����
	 */
	public Organization getOrgById(long orgId) throws UOSException;

	public Organization getOrgByStaffId(int staffId) throws UOSException;

	public String getOrgNameByOrgId(long orgId) throws UOSException;

	public String findOrgByOrgTemp(int orgId, int orgTempId)
			throws SQLException, UOSException;

	public String findOrganization1Org(int OrgId) throws UOSException;

	/**
	 * ����ְλ��ʶ��ѯ��λ����
	 *
	 * @param jobId
	 *            Long
	 * @return PostLevel
	 * @throws SQLException
	 */
	public PostLevel qryPostLevelByJobId(Long jobId) throws SQLException;

	/**
	 * ����ID��ѯ����֯�͸���֯����������֯
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
