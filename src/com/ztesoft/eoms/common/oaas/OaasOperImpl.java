package com.ztesoft.eoms.common.oaas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

import com.zterc.uos.UOSException;
import com.zterc.uos.client.ParameterManager;
import com.zterc.uos.helpers.AbstractDAOImpl;
import com.zterc.uos.oaas.exception.OAASError;
import com.zterc.uos.oaas.exception.OAASException;
import com.zterc.uos.oaas.service.orgmanager.OrganizationManager;
import com.zterc.uos.oaas.service.orgmanager.dao.OrganizationDAO;
import com.zterc.uos.oaas.service.orgmanager.dao.OrganizationDAOFactory;
import com.zterc.uos.oaas.service.staffmanager.StaffManager;
import com.zterc.uos.oaas.vo.Organization;
import com.zterc.uos.oaas.vo.PostLevel;
import com.zterc.uos.oaas.vo.Staff;
import com.zterc.uos.util.xml.XMLDom4jUtils;
import com.ztesoft.mobile.common.db.DbOper;
import com.ztesoft.mobile.common.helper.NumberHelper;
import com.ztesoft.mobile.common.helper.SQLHelper;
import com.ztesoft.mobile.common.helper.StringHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;

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
public class OaasOperImpl extends AbstractDAOImpl implements OaasOper {
	/**
	 * 组织显示顺序是否由客户指定,按照displayIndex排序 0：不是，1 是
	 */
	private static String ORG_DISPLAY_PARAMETER = "ORG_DISPLAY_BY_CUST";

	public OaasOperImpl() {
	}

	/**
	 * 查询组织 -- 最好不要使用这个的方法，应该统一调用OAAS包里提供的方法
	 *
	 * @param OrgId
	 *            int
	 * @throws UOSException
	 * @return String
	 */
	public String findOrganization1Org(int OrgId) throws UOSException {
		// 获取数据
		OrganizationManager orgMng = new OrganizationManager();
		Organization[] orgs = orgMng.findSimpleAllSubOrgs(OrgId);

		HashMap parentMap = new HashMap();
		DocumentFactory df = new DocumentFactory();
		Element rootElement = df.createElement(new QName("property"));
		Document document = df.createDocument(rootElement);
		Element columnsElement = XMLDom4jUtils.appendChild(rootElement,
				"columns");
		Element columnElement = XMLDom4jUtils.appendChild(columnsElement,
				"column");
		columnElement.addAttribute("width", "200");
		columnElement.addAttribute("display", "true");
		columnElement.addAttribute("displayText", "名称");
		columnElement.addAttribute("propertyName", "name");

		columnElement = XMLDom4jUtils.appendChild(columnsElement, "column");
		columnElement.addAttribute("width", "200");
		columnElement.addAttribute("display", "false");
		columnElement.addAttribute("displayText", "标示");
		columnElement.addAttribute("propertyName", "id");

		Element listElement = XMLDom4jUtils.appendChild(rootElement, "items");
		for (int i = 0; i < orgs.length; i++) {
			int orgId = orgs[i].getOrgId();
			int parentId = orgs[i].getParentId();
			String orgaNizationPathCode = orgs[i].getOrgPathCode();
			String pathCode = orgaNizationPathCode;
			if (orgaNizationPathCode.lastIndexOf(".") >= 0) {

				pathCode = orgaNizationPathCode.substring(0,
						orgaNizationPathCode.lastIndexOf("."));
			}
			Element orgElement = null;

			if (parentMap.containsKey(pathCode)) {
				Element parentEle = (Element) parentMap.get(pathCode);
				orgElement = XMLDom4jUtils.appendChild(parentEle, "item");
			} else {
				orgElement = XMLDom4jUtils.appendChild(listElement, "item");
			}
			orgElement.addAttribute("id", String.valueOf(orgId));
			orgElement.addAttribute("name", orgs[i].getOrgName());
			parentMap.put(orgaNizationPathCode, orgElement);
		}

		return document.asXML();
	}

	private static final Logger logger = Logger.getLogger(OaasOperImpl.class);

	public Staff[] findStaffsByOrganizationId(int organizaitonId)
			throws UOSException {
		StaffManager staffMng = new StaffManager();
		return staffMng.findAllByOrg(organizaitonId);
	}

	public Staff[] findStaffsByOrgId(int orgId) throws UOSException {
		StaffManager staffMng = new StaffManager();
		return staffMng.findDistinctStaffsByOrg(orgId);
	}

	/**
	 * 构造一个部门以及下属部门的所有部门树 新方法 -- 最好不要使用这个的方法，应该统一调用OAAS包里提供的方法
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */
	public String qryOrgsById(Long orgId) throws SQLException, UOSException {
		OrganizationDAO orgDAO = OrganizationDAOFactory.getDAO();
		ParameterManager parameterManager = new ParameterManager();
		Organization[] orgs = null;

		if (parameterManager.findParameter(ORG_DISPLAY_PARAMETER) != null) {
			int displayParameter = Long.valueOf(
					parameterManager.findParameter(ORG_DISPLAY_PARAMETER)
							.getValue()).intValue();
			if (displayParameter == 1) {
				orgs = orgDAO.findAllSubOrgsByDisplayIndex(orgId.intValue());
			} else {
				orgs = orgDAO.findAllSubOrgs(orgId.intValue());
			}
		} else {
			orgs = orgDAO.findAllSubOrgs(orgId.intValue());
		}
		return this.arrayToXml(orgs);

	}

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
			UOSException {
		OrganizationDAO orgDAO = OrganizationDAOFactory.getDAO();
		ParameterManager parameterManager = new ParameterManager();
		Organization[] orgs = null;
		Collection col = new ArrayList();

		if (orgIdArr != null && orgIdArr.length > 0) {
			int flag = 0;
			if (parameterManager.findParameter(ORG_DISPLAY_PARAMETER) != null) {
				int displayParameter = Long.valueOf(
						parameterManager.findParameter(ORG_DISPLAY_PARAMETER)
								.getValue()).intValue();
				if (displayParameter == 1) {
					flag = 1;
				} else {
					flag = 2;
				}
			}

			Organization[] tempOrgs = null;
			for (int i = 0; i < orgIdArr.length; i++) {
				switch (flag) {
				case 0:
					tempOrgs = orgDAO.findAllSubOrgs(orgIdArr[i].intValue());
					break;
				case 1:
					tempOrgs = orgDAO.findAllSubOrgsByDisplayIndex(orgIdArr[i]
							.intValue());
					break;
				case 2:
					tempOrgs = orgDAO.findAllSubOrgs(orgIdArr[i].intValue());
					break;
				}
				if (tempOrgs != null && tempOrgs.length > 0) {
					for (int j = 0; j < tempOrgs.length; j++) {
						col.add(tempOrgs[j]);
					}
				}

			}
		}
		orgs = (col.size() > 0) ? (Organization[]) col
				.toArray(new Organization[col.size()]) : null;

		return this.arrayToXml(orgs);
	}

	/**
	 * 构造一个查询二级部门以及下属部门的所有部门树 新方法
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return String
	 */

	public String qrySecondOrgsById(Long orgId) throws UOSException {
		if (ValidateHelper.validateNotNull(orgId)) {
			Organization org = getOrgById(orgId.longValue());
			if (org == null) {
				logger.error("#org is null,qry by orgId=" + orgId);
				return null;
			}
			String pathCode = org.getOrgPathCode();
			Organization[] orgs = null;
			String[] orgIds = pathCode.split("\\.");
			if (orgIds.length <= 2) {
				orgs = new OrganizationManager().findSimpleAllSubOrgs(orgId
						.intValue());
			} else {
				int secondOrgId = Integer.parseInt(orgIds[1]);
				orgs = new OrganizationManager()
						.findSimpleAllSubOrgs(secondOrgId);
			}
			return buildOrgXml(orgs);
		}
		logger.error("#orgId is null");
		return null;
	}

	/**
	 * 查找所有的子组织
	 *
	 * @param orgId
	 * @return
	 * @throws UOSException
	 */
	public Organization[] findSimpleAllSubOrgs(Long orgId) throws UOSException {
		logger.debug("#orgId:" + orgId);
		Organization[] orgs = null;
		if (ValidateHelper.validateNotNull(orgId)) {
			orgs = new OrganizationManager().findSimpleAllSubOrgs(orgId
					.intValue());
		}
		return orgs;
	}

	/**
	 * -- 最好不要使用这个的方法，应该统一调用OAAS包里提供的方法
	 *
	 * @return String
	 * @throws SQLException
	 * @throws UOSException
	 */
	public String qryAllOrg() throws SQLException, UOSException {
		OrganizationDAO orgDAO = OrganizationDAOFactory.getDAO();
		ParameterManager parameterManager = new ParameterManager();
		Organization[] orgs = null;

		if (parameterManager.findParameter(ORG_DISPLAY_PARAMETER) != null) {
			int displayParameter = Long.valueOf(
					parameterManager.findParameter(ORG_DISPLAY_PARAMETER)
							.getValue()).intValue();
			if (displayParameter == 1) {
				orgs = orgDAO.findOrgsByDisplayIndex();
			} else {
				orgs = orgDAO.findOrgs();
			}
		} else {
			orgs = orgDAO.findOrgs();
		}
		return this.arrayToXml(orgs);
	}

	/**
	 * 把组织转变为XML字符串
	 *
	 * @param orgs
	 *            Organization[] 组织数组
	 * @return String XML字符串
	 */
	public static String arrayToXml(Organization[] orgs) throws OAASException {
		try {
			HashMap parentMap = new HashMap();
			DocumentFactory df = new DocumentFactory();
			Element listElement = df.createElement(new QName("items"));
			Document document = df.createDocument(listElement);

			for (int i = 0; i < orgs.length; i++) {
				Integer parentId = new Integer(orgs[i].getParentId());
				Element orgElement = null;
				if (parentMap.containsKey(parentId)) {
					Element parentEle = (Element) parentMap.get(parentId);
					orgElement = XMLDom4jUtils.appendChild(parentEle, "item");
				} else {
					orgElement = XMLDom4jUtils.appendChild(listElement, "item");
				}

				// 给节点赋值
				orgElement.addAttribute("id", String
						.valueOf(orgs[i].getOrgId()));
				orgElement.addAttribute("name", orgs[i].getOrgName());
				orgElement.addAttribute("orgTmpId", String.valueOf(orgs[i]
						.getOrgTmpId()));
				// 地区Id
				orgElement.addAttribute("areaId", Integer.toString(orgs[i]
						.getAreaId()));
				// 地区名
				String areaName = orgs[i].getAreaName();
				orgElement.addAttribute("areaName", areaName == null ? ""
						: areaName);
				// 简称
				String acronym = orgs[i].getAcronym();
				orgElement.addAttribute("acronym", acronym == null ? ""
						: acronym);
				// 别名
				String alias = orgs[i].getAlias();
				orgElement.addAttribute("alias", alias == null ? "" : alias);

				// 备注
				String comments = orgs[i].getComments();
				orgElement.addAttribute("comments", comments == null ? ""
						: comments);
				// 组织路径
				String orgPathCode = orgs[i].getOrgPathCode();
				orgElement.addAttribute("orgPathCode", orgPathCode == null ? ""
						: orgPathCode);
				// 组织路径名称
				String orgPathName = orgs[i].getOrgPathName();
				orgElement.addAttribute("orgPathName", orgPathName == null ? ""
						: orgPathName);
				orgElement.addAttribute("parentId", String.valueOf(orgs[i]
						.getParentId()));
				String tel = orgs[i].getTel();
				orgElement.addAttribute("tel", tel == null ? "" : tel);
				String orgCode = orgs[i].getOrgCode();
				orgElement.addAttribute("orgCode", orgCode == null ? ""
						: orgCode);

				parentMap.put(new Integer(orgs[i].getOrgId()), orgElement);
			}

			return document.asXML();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			OAASError error = new OAASError(OAASError.XML_COMPOSITTE_ERROR);
			throw new OAASException(error, ex);
		}

	}

	private String buildOrgXml(Organization[] orgs) {
		if (ValidateHelper.validateNotEmpty(orgs)) {
			HashMap parentMap = new HashMap();
			DocumentFactory df = new DocumentFactory();
			Element listElement = df.createElement(new QName("items"));
			Document document = df.createDocument(listElement);
			for (int i = 0; i < orgs.length; i++) {
				int organizationId = orgs[i].getOrgId();
				String orgaNizationPathCode = orgs[i].getOrgPathCode();
				String pathCode = orgaNizationPathCode;
				if (orgaNizationPathCode.lastIndexOf(".") >= 0) {

					pathCode = orgaNizationPathCode.substring(0,
							orgaNizationPathCode.lastIndexOf("."));
				}
				Element orgElement = null;

				if (parentMap.containsKey(pathCode)) {
					Element parentEle = (Element) parentMap.get(pathCode);
					orgElement = XMLDom4jUtils.appendChild(parentEle, "item");
				} else {
					orgElement = XMLDom4jUtils.appendChild(listElement, "item");
				}

				orgElement.addAttribute("id", String.valueOf(organizationId));
				orgElement.addAttribute("name", orgs[i].getOrgName());
				orgElement.addAttribute("acronym", StringHelper
						.toString(orgs[i].getAcronym()));

				parentMap.put(orgaNizationPathCode, orgElement);

			}
			return document.asXML();
		} else {
			return "";
		}
	}

	/**
	 * 查询某个组织下人员，不查询子树
	 *
	 * @param orgId
	 *            long
	 * @throws UOSException
	 * @return Staff[]
	 */
	public StaffDto[] findStaffByOrgId(long orgId) throws UOSException {
		StaffManager staffMng = new StaffManager();
		Staff[] staffs = staffMng.findDistinctStaffsByOrg((int) orgId);
		StaffDto[] dtos = new StaffDto[staffs.length];
		for (int i = 0; i < staffs.length; i++) {
			dtos[i] = new StaffDto();
			dtos[i].setId(new Long(staffs[i].getStaffId()));
			dtos[i].setStaffId(new Long(staffs[i].getStaffId()));
			dtos[i].setStaffName(staffs[i].getStaffName());
			dtos[i].setUserName(staffs[i].getUserName());
			dtos[i].setMobileTele(staffs[i].getMobileTel());
                        if(SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.SQLSERVER_TAG)
                        {
                            dtos[i].setTelephone(staffs[i].getOfficeTel());
                        }
                        else
                        {
                            dtos[i].setTelephone(staffs[i].getPhsTel());
                        }
                        dtos[i].setEmail(staffs[i].getEmail());
		}

		return dtos;
	}

	/**
	 * 根据id获得staff对象 (using)
	 *
	 * @param Id
	 *            long
	 * @throws OAASException
	 * @return StaffDto
	 */
	public StaffDto getStaffById(long Id) throws UOSException {
		StaffManager staffMng = new StaffManager();
		Staff staff = staffMng.findByKey((int) Id);
		StaffDto staffDto = new StaffDto();
		if (staff != null) {
			staffDto.setId(new Long(Id));
			staffDto.setStaffId(new Long(staff.getStaffId()));
			staffDto.setStaffName(staff.getStaffName());
			staffDto.setUserName(staff.getUserName());
			staffDto.setJobId(new Long(staff.getJobId()));
			staffDto.setJobName(staff.getJobName());
			staffDto.setMobileTele(staff.getMobileTel());
                        staffDto.setEmail(staff.getEmail());
		}
		return staffDto;
	}

	/**
	 * 根据ID或者org对象
	 */
	public Organization getOrgById(long orgId) throws UOSException {
		return new OrganizationManager().findByKey((int) orgId);

	}

	public Organization getOrgByStaffId(int staffId) throws UOSException {
		OrganizationManager orgMng = new OrganizationManager();
		Organization[] orgs = orgMng.findByStaff((int) staffId);
		if (orgs.length > 0) {
			return orgs[0];
		}
		return null;
	}

	/**
	 * 根据组织Id取得组织名称
	 *
	 * @param orgId
	 *            long
	 * @throws SQLException
	 * @return String
	 */
	public String getOrgNameByOrgId(long orgId) throws UOSException {
		OrganizationManager orgMng = new OrganizationManager();
		return orgMng.findByKey((int) orgId).getOrgName();

	}

	public String findOrgByOrgTemp(int orgId, int orgTempId)
			throws SQLException, UOSException {
		Organization[] orgs = null;
		OrganizationManager orgMng = new OrganizationManager();
		Organization org = orgMng.findByKey(orgId);
		while (org != null) {
			if (org.getOrgTmpId() == orgTempId) {
				orgs = orgMng.findSimpleAllSubOrgs(org.getOrgId());
				break;
			}
			org = orgMng.findByKey(org.getParentId());
		}
		if ((orgs == null) || (orgs.length == 0)) {
			return "";
		}
		HashMap parentMap = new HashMap();
		DocumentFactory df = new DocumentFactory();
		Element rootElement = df.createElement(new QName("property"));
		Document document = df.createDocument(rootElement);
		Element listElement = XMLDom4jUtils.appendChild(rootElement, "items");
		for (int i = 0; i < orgs.length; i++) {
			int organizationId = orgs[i].getOrgId();
			String orgaNizationPathCode = orgs[i].getOrgPathCode();
			String pathCode = orgaNizationPathCode;
			if (orgaNizationPathCode.lastIndexOf(".") >= 0) {

				pathCode = orgaNizationPathCode.substring(0,
						orgaNizationPathCode.lastIndexOf("."));
			}
			Element orgElement = null;

			if (parentMap.containsKey(pathCode)) {
				Element parentEle = (Element) parentMap.get(pathCode);
				orgElement = XMLDom4jUtils.appendChild(parentEle, "item");
			} else {
				orgElement = XMLDom4jUtils.appendChild(listElement, "item");
			}
			orgElement.addAttribute("id", String.valueOf(organizationId));
			orgElement.addAttribute("name", orgs[i].getOrgName());
			parentMap.put(orgaNizationPathCode, orgElement);
		}
		return document.asXML();

	}

	/**
	 * 根据组织Id取得组织名称路径
	 *
	 * @param orgId
	 *            long
	 * @throws SQLException
	 * @return String
	 */
	public String getOrgPathNameByOrgId(long orgId) throws UOSException {
		OrganizationManager orgMng = new OrganizationManager();
		return orgMng.findByKey((int) orgId).getOrgPathName();

	}

	/**
	 * 根据职位标识查询岗位级别
	 *
	 * @param jobId
	 *            Long
	 * @return PostLevel
	 * @throws SQLException
	 */
	public PostLevel qryPostLevelByJobId(Long jobId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlStr = "SELECT A.POST_LEVEL_ID,A.POST_LEVEL_NAME,A.POST_LEVEL_VALUE,A.COMMENTS FROM UOS_POST_LEVEL A"
				+ " LEFT JOIN UOS_POST B ON B.POST_LEVEL_ID=A.POST_LEVEL_ID"
				+ " LEFT JOIN UOS_JOB C ON C.POST_ID=B.POST_ID"
				+ " WHERE C.JOB_ID=? AND C.STATE=1";
		PostLevel postLevel = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sqlStr);
			DbOper.setPrepStatementParam(1, ps, jobId);
			rs = ps.executeQuery();

			while (rs.next()) {
				postLevel = new PostLevel();
				postLevel.setPostLevelId(NumberHelper.integerValueOf(
						rs.getObject("POST_LEVEL_ID")).intValue());
				postLevel.setPostLevelName(rs.getString("POST_LEVEL_NAME"));
				if (rs.getObject("POST_LEVEL_VALUE") != null)
					postLevel.setPostLevelValue(NumberHelper.integerValueOf(
							rs.getObject("POST_LEVEL_VALUE")).intValue());
				postLevel.setComments(rs.getString("COMMENTS"));
			}

			return postLevel;
		} finally {
			cleanUp(conn, null, ps, rs);
		}

	}

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
			UOSException {
		OrganizationDAO orgDAO = OrganizationDAOFactory.getDAO();
		ParameterManager parameterManager = new ParameterManager();
		Organization[] orgs = null;
		int parentOrgId = 0;

		Organization parentOrg = orgDAO.findByChild(orgId.intValue());
		if (parentOrg != null && parentOrg.getOrgId() > 0) {
			parentOrgId = parentOrg.getOrgId();
		} else {
			parentOrgId = orgId.intValue();
		}

		if (parameterManager.findParameter(ORG_DISPLAY_PARAMETER) != null) {
			int displayParameter = Long.valueOf(
					parameterManager.findParameter(ORG_DISPLAY_PARAMETER)
							.getValue()).intValue();
			if (displayParameter == 1) {
				orgs = orgDAO.findAllSubOrgsByDisplayIndex(parentOrgId);
			} else {
				orgs = orgDAO.findAllSubOrgs(parentOrgId);
			}
		} else {
			orgs = orgDAO.findAllSubOrgs(parentOrgId);
		}
		return this.arrayToXml(orgs);

	}

	public Map qryStaffTelMap(String staffIds) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlStr = "SELECT STAFF_ID,MOBILE_TEL FROM UOS_STAFF WHERE STATE='1' AND STAFF_ID IN("
				+ staffIds + ")";
		Map map = null;
                String mobileTel = "";
                try {
                    conn = getConnection();
                    ps = conn.prepareStatement(sqlStr);
                    rs = ps.executeQuery();
                    map = new HashMap();
                    while (rs.next()) {
                        mobileTel = rs.getString("MOBILE_TEL");
                        if (null != mobileTel && !mobileTel.equals("")) {
                            map.put(NumberHelper.longValueOf(rs.getObject("STAFF_ID")), mobileTel);
                        }
                    }

                    return map;
                } finally {
                    cleanUp(conn, null, ps, rs);
                }

	}


        public List qryStaffTelList(String staffIds) throws SQLException {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            String sqlStr =
                    "SELECT STAFF_ID,MOBILE_TEL,PHS_TEL FROM UOS_STAFF WHERE STATE='1' AND STAFF_ID IN("
                    + staffIds + ")";
            Map map = null;
            List telList = null;

            try {
                conn = getConnection();
                ps = conn.prepareStatement(sqlStr);
                rs = ps.executeQuery();

                telList = new ArrayList();
                while (rs.next()) {
                    map = new HashMap();
                    String mobileTel = "";
                    String phsTel = "";
                    mobileTel = rs.getString("MOBILE_TEL");
                    phsTel = rs.getString("PHS_TEL");
                    if(SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.SQLSERVER_TAG)
                    {
                      if (null != mobileTel && mobileTel.trim().length()>0) {
                        map.put("mobileTel", mobileTel);
                        telList.add(map);
                      }
                    }
                    if(SQLHelper.CURRENT_DATABASE_TAG == SQLHelper.SYBASE_TAG)
                    {
                      if (null != phsTel && phsTel.trim().length()>0) {
                        map.put("phsTel", phsTel);
                        telList.add(map);
                      }
                    }
                }

                return telList;
            } finally {
                cleanUp(conn, null, ps, rs);
            }

        }
        public Staff getStaffByUserName(String userName) throws UOSException
        {
    		StaffManager staffMng = new StaffManager();
    		Staff staff = staffMng.findByUserName(userName);
    		return staff;
        }
}
