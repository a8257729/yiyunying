package com.ztesoft.mobile.v2.dao.common;
import com.zterc.uos.exception.RequiredException;
import com.zterc.uos.exception.SystemException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import oracle.jdbc.internal.OracleTypes;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;


public class MobileCommonDAOImpl extends BaseDAOImpl implements MobileCommonDAO {

	private static final Logger logger = Logger.getLogger(MobileCommonDAOImpl.class);

	public String getPasswordByUsername(String username) throws DataAccessException
	{
		String sql = "SELECT e.password as password FROM UOS_STAFF e WHERE  e.state='1' and e.username = '" +username + "'";
		Map map = dynamicQueryObjectByMap(sql, null, null);
		String password = MapUtils.getString(map, "password", null);
		return password;
	}

	public String getPasswordByStaffId(Long staffId) throws DataAccessException {
		String sql = "SELECT e.PASSWORD as password FROM UOS_STAFF e WHERE  e.state='1' and e.STAFF_ID = " + staffId;
		Map map = dynamicQueryObjectByMap(sql, null, null);
		String password = MapUtils.getString(map, "password", null);
		return password;
	}

	public void updatePasswordByStaffId(Long staffId, String newPasswd) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("staffId", staffId);
		paramMap.put("password", newPasswd);
		//
		String updatePatternStr = "PASSWORD:password";
		String wherePatternStr = "STAFF_ID:staffId";
		dynamicUpdateByMap(paramMap, "UOS_STAFF", updatePatternStr, wherePatternStr);
	}

	public String getPasswordByMobile(String mobile) throws DataAccessException
	{

		String sql = "SELECT e.password as password FROM UOS_STAFF e WHERE e.state='1' AND e.ADDRESS2='" + mobile + "'";
		Map map = dynamicQueryObjectByMap(sql, null, null);
		String password = MapUtils.getString(map, "password", null);
		return password;
	}

	private StringBuffer buildSqlForStaff(Map paramMap) {
		StringBuffer sql = new StringBuffer(" SELECT S.STAFF_ID as staffId, ");
		sql.append(" S.STAFF_NAME as staffName, ");
		sql.append(" S.USERNAME as username, ");
		sql.append(" S.PASSWORD as password, ");
		sql.append(" S.MOBILE_TEL as mobile, ");
		sql.append(" S.HOME_TEL as tel, ");
		sql.append(" S.EMAIL as email, ");
		sql.append(" S.ADDRESS2 as imsi, ");
		sql.append(" S.connect_limit AS connectLimit ");
		sql.append(" FROM UOS_STAFF S WHERE S.state='1' ");

		String username = MapUtils.getString(paramMap, "username", null);
		if(StringUtils.isNotBlank(username)) {
			sql.append(" AND S.USERNAME = '" + username + "' ");
		}

		String mobile = MapUtils.getString(paramMap, "mobile", null);
		if(StringUtils.isNotBlank(mobile)) {
			sql.append(" AND S.MOBILE_TEL = '" + mobile + "' ");
		}

		String imsi = MapUtils.getString(paramMap, "imsi", null);
		if(StringUtils.isNotBlank(imsi)) {
			sql.append(" AND S.ADDRESS2 = '" + imsi + "' ");
		}

		Long staffId = MapUtils.getLong(paramMap, "staffId");
		if(null != staffId) {
			sql.append(" AND S.STAFF_ID = " + staffId);
		}
		//Debug Info
		if(logger.isDebugEnabled()) {
			logger.debug("buildSqlForStaff打印SQL是：" + sql.toString());
		}
		return sql;
	}

	public Map<String, Object> getStaffByUsername(String username) throws DataAccessException {
		Map paramMap = new HashMap();
		paramMap.put("username", username);
		String where = null;
		StringBuffer sql = new StringBuffer(" SELECT S.STAFF_ID as staffId, ");
		sql.append(" S.STAFF_NAME as staffName, ");
		sql.append(" S.USERNAME as username, ");
		sql.append(" S.PASSWORD as password, ");
		sql.append(" S.MOBILE_TEL as mobile, ");
		sql.append(" S.HOME_TEL as tel, ");
		sql.append(" S.EMAIL as email, ");
		sql.append(" S.ADDRESS2 as imsi, ");
		sql.append(" S.connect_limit AS connectLimit ");
		sql.append(" FROM UOS_STAFF S WHERE S.state='1' ");
		if(StringUtil.isNull(username)==false&&"null".equals(username)==false) {
			sql.append(" AND S.USERNAME =  ?");
			where = "USERNAME:username";
		}
		return dynamicQueryObjectByMap(sql.toString(), paramMap, where);
	}

	public Map<String, Object> getStaffByMobile(String mobile) throws DataAccessException {
		Map paramMap = new HashMap();
		paramMap.put("mobile", mobile);
		String where = null;
		StringBuffer sql = new StringBuffer(" SELECT S.STAFF_ID as staffId, ");
		sql.append(" S.STAFF_NAME as staffName, ");
		sql.append(" S.USERNAME as username, ");
		sql.append(" S.PASSWORD as password, ");
		sql.append(" S.MOBILE_TEL as mobile, ");
		sql.append(" S.HOME_TEL as tel, ");
		sql.append(" S.EMAIL as email, ");
		sql.append(" S.ADDRESS2 as imsi, ");
		sql.append(" S.connect_limit AS connectLimit ");
		sql.append(" FROM UOS_STAFF S WHERE S.state='1' ");
		if (StringUtil.isNull(mobile) == false && "null".equals(mobile) == false) {
			sql.append(" AND S.MOBILE_TEL =  ?");
			where = "MOBILE_TEL:mobile";
		}
		return dynamicQueryObjectByMap(sql.toString(), paramMap, where);
	}

	public List<StaffControls> getStaffControlsList() throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" select f.staff_id AS staffId, ");
		sqlStr.append(" f.staff_name AS stffName, ");
		sqlStr.append(" f.username AS username, ");
		sqlStr.append(" f.ADDRESS2 as imsi, ");
		sqlStr.append(" f.connect_limit AS connectLimit ");
		sqlStr.append(" from uos_staff f where f.state = '1' ");

		List<Map> list =  this.dynamicQueryListByMap(sqlStr.toString(), null, null);

		if(null != list) {
			List<StaffControls> resultList = new ArrayList<StaffControls>();
			for(int i=0; i<list.size(); i++) {

				Map<String, Object> map = list.get(i);
				Long vStaffId = MapUtils.getLong(map, StaffControls.STAFF_ID_NODE);
				String vStaffName = MapUtils.getString(map, StaffControls.STAFF_NAME_NODE);
				String vUsername = MapUtils.getString(map, StaffControls.USERNAME_NODE);
				String vImsi = MapUtils.getString(map, StaffControls.IMSI_NODE);
				Integer vConnectLimit = MapUtils.getInteger(map, StaffControls.CONNECT_LIMIT_NODE);

				StaffControls item = new StaffControls();
				item.setStaffId(vStaffId);
				item.setStaffName(vStaffName);
				item.setUsername(vUsername);
				item.setConnectLimit(vConnectLimit);
				item.setImsi(vImsi);

				resultList.add(item);
			}
			return resultList;
		} else {
			return Collections.EMPTY_LIST;
		}

	}

	public StaffControls getStaffControls(Long staffId) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" select f.staff_id AS staffId, ");
		sqlStr.append(" f.staff_name AS stffName, ");
		sqlStr.append(" f.username AS username, ");
		sqlStr.append(" f.ADDRESS2 as imsi, ");
		sqlStr.append(" f.CONNECT_STATE as connectState, ");
		sqlStr.append(" f.USER_CONN_PRIV as userConnPriv, ");
		sqlStr.append(" f.USER_FLOW_LIMIT as userFlowLimit, ");
		sqlStr.append(" f.connect_limit AS connectLimit ");
		sqlStr.append(" from uos_staff f where f.state = '1' ");

		if(null != staffId) {
			sqlStr.append(" AND f.staff_id = " + staffId);
		}

		Map map =  this.dynamicQueryObjectByMap(sqlStr.toString(), null, null);

		if(null == map || 0 == map.size()) return null;

		Long vStaffId = MapUtils.getLong(map, StaffControls.STAFF_ID_NODE);
		String vStaffName = MapUtils.getString(map, StaffControls.STAFF_NAME_NODE);
		String vUsername = MapUtils.getString(map, StaffControls.USERNAME_NODE);
		String vImsi = MapUtils.getString(map, StaffControls.IMSI_NODE);
		Integer vConnectLimit = MapUtils.getInteger(map, StaffControls.CONNECT_LIMIT_NODE);
		Integer vConnectState = MapUtils.getInteger(map, StaffControls.CONNECT_STATE_NODE);
		String vUserConnPriv = MapUtils.getString(map, StaffControls.USER_CONN_PRIV_NODE);
		Long vUserFlowLimit = MapUtils.getLong(map, StaffControls.USER_FLOW_LIMIT_NODE);

		StaffControls item = new StaffControls();
		item.setStaffId(vStaffId);
		item.setStaffName(vStaffName);
		item.setUsername(vUsername);
		item.setConnectLimit(vConnectLimit);
		item.setConnectState(vConnectState);
		item.setUserConnPriv(vUserConnPriv);
		item.setUserFlowLimit(vUserFlowLimit);
		item.setImsi(vImsi);

		return item;

	}


	private StringBuffer buildSqlForJobList(Map paramMap) {
		StringBuffer sql = new StringBuffer("SELECT b.job_id as jobId, ");
		//sql.append("SELECT d.staff_id as staffId, ");
		//sql.append("d.staff_name as staffName,");
		//sql.append("d.username as username,");
		//sql.append("b.job_id as jobId, ");
		sql.append(" b.job_name as jobName, ");
		sql.append(" c.org_id as orgId, ");
		sql.append(" c.org_name as orgName, ");
		sql.append(" c.area_id as areaId, ");
		sql.append(" a.is_normal as basic ");
		sql.append(" FROM UOS_JOB_STAFF a JOIN UOS_JOB b  on a.JOB_ID = b.job_id ");
		sql.append(" JOIN UOS_ORG c on b.org_id = c.org_id JOIN UOS_STAFF d on a.staff_id = d.staff_id ");
		sql.append(" WHERE a.state = 1 AND b.state = 1 AND c.state = 1 AND d.state = 1 ");

		Long staffId = MapUtils.getLong(paramMap, "staffId", new Long(-1));
		if(null != staffId) {
			sql.append(" AND a.staff_id = " + staffId );
		}

		int basic = MapUtils.getIntValue(paramMap, "basic", -1);
		if(1 == basic || 0 == basic) {
			sql.append(" AND a.is_normal = '" + basic + "' ");
		}
		//Debug Info
		if(logger.isDebugEnabled()) {
			logger.debug("getJobList的SQL是：" + sql);
		}
		return sql;
	}

	public List<Map> getJobList(Long staffId, int basic) throws DataAccessException {
		Map paramMap = new HashMap();
		paramMap.put("staffId", staffId);
		paramMap.put("basic", basic);
		StringBuffer sql = this.buildSqlForJobList(paramMap);
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);

		return list;
	}

	public Map getDefaultJob(Long staffId) throws DataAccessException {
		List<Map> list = this.getJobList(staffId, 0);

		Map map = Collections.EMPTY_MAP;
		//理论上来说，应该有且仅有1条i记录，否则就是数据问题了
		if(null != list && 0 != list.size()) {
			map = list.get(0);
		}
		return map;
	}

	public List<Map> getNormalJobList(Long staffId) throws DataAccessException {
		List<Map> list = getJobList(staffId, 1);
		return list;
	}
	public void updateUosStaffYxwx(Map paramMap) throws DataAccessException{
		String updatePatternStr = "USERNAME:username";
		String wherePatternStr = "STAFF_YXWX_ID:yxwxId";
		dynamicUpdateByMap(paramMap, "UOS_STAFF_YXWX", updatePatternStr, wherePatternStr);
	}
	public void insertUosStaffYxwx(Map paramMap) throws DataAccessException{
		String updatePatternStr = "OPEN_ID:openid,YXWX_TYPE:type";
		String wherePatternStr = "USERNAME:username";
		String patternStr = "STAFF_YXWX_ID:@@SEQ,OPEN_ID:openid,USERNAME:username,YXWX_TYPE:type";
		dynamicInsertByMap(paramMap, "UOS_STAFF_YXWX", patternStr);
	}
	public Map<String, Object>  getUosStaffYxwx(Map paramMap) throws DataAccessException
	{
		return null;
	}
	public Map<String, Object> getStaffByOpenId(String openId,String type) throws DataAccessException {
		// Map paramMap = new HashMap();
		//paramMap.put("openId", openId);
		StringBuffer sql = new StringBuffer(" SELECT S.STAFF_YXWX_ID as yxwxId, ");
		sql.append(" V.STAFF_ID as staffId,");
		sql.append(" V.STAFF_NAME as staffName,");
		sql.append(" V.ORG_ID as orgId,");
		sql.append(" V.ORG_PATH_CODE as orgPathCode,");
		sql.append(" S.USERNAME as username");
		sql.append(" FROM VM_STAFF_ORG V,UOS_STAFF_YXWX S WHERE ");
		sql.append("  V.username=S.username and OPEN_ID='"+openId +"' and YXWX_TYPE='" +type +"'");

		return dynamicQueryObjectByMap(sql.toString(), null, null);
	}
	public List<Map> selOrgTree(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = this.buildSqlForOrgTree(paramMap);
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		//计算层数
		int i=list.size();
		if(i>0){
			for(int k=0;k<i;k++){
				Map newObj=list.get(k);
				String pathCode=MapUtils.getString(newObj, "pathCode");
				String partyType=MapUtils.getString(newObj, "partyType");
				int level=countLevel(pathCode);
				if("STA".equals(partyType)){//人员的话 层数要加1
					level++;
				}
				newObj.put("level", level+"");
			}
		}
		return list;
	}

	public List<Map> selAllOrgTree(Map paramMap) throws DataAccessException {
		StringBuffer sql = this.buildSqlForAllOrgTree(paramMap);
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		return list;
	}
	public List<Map> selAuthOrgTree(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = this.buildSqlForAuthOrgTree(paramMap);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;//
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		//计算层数
		int i=list.size();
		int maxLevel=0;
		if(i>0){
			for(int k=0;k<i;k++){
				Map newObj=list.get(k);
				String pathCode=MapUtils.getString(newObj, "pathCode");
				String partyType=MapUtils.getString(newObj, "partyType");
				int level=countLevel(pathCode);
				if("STA".equals(partyType)){//人员的话 层数要加1
					level++;
				}
				if(level>maxLevel){
					maxLevel=level;
				}
				newObj.put("level", level+"");
			}
			//如果是有下层组织，上层组织展开默认为true
			for(int k=0;k<i;k++){
				Map newObj=list.get(k);
				String level=MapUtils.getString(newObj, "level");
				int le=Integer.parseInt(level);
				String partyType=MapUtils.getString(newObj, "partyType");
				//如果是有下层组织，上层组织展开默认为true
				if("ORG".equals(partyType)){
					if(le<maxLevel){
						newObj.put("expanded", true);
					}
				}

			}
		}
		return list;
	}

	public List<Map> qryFaultReasonTree(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sql = this.buildSqlForFaultReason(paramMap);
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		//计算层数
		int i=list.size();
		if(i>0){
			for(int k=0;k<i;k++){
				Map newObj=list.get(k);
				String pathCode=MapUtils.getString(newObj, "pathCode");
				String faultKind=MapUtils.getString(newObj, "faultKind");
				int level=countLevel(pathCode);
				newObj.put("level", level);
			}
		}
		return list;
	}

	private static int countLevel(String code){
		if(code!=null&&!"".equals(code)){
			int le=0;
			String[] arr=code.split("\\.");
			//如果树是以0.*.**开头，则认为level是1
			for(int i=0;i<arr.length;i++){
				if(!"0".equals(arr[i])){
					le++;
				}
			}
			return le;
		}else{
			return 0;
		}

	}

	private StringBuffer buildSqlForOrgTree(Map paramMap) {
		StringBuffer sql = new StringBuffer("" );
		String orgId=MapUtils.getString(paramMap, "OrgId");
		if(paramMap!=null&&"true".equals(MapUtils.getString(paramMap, "IsFirst"))){
			//第一次查询父节点
			if("0".equals(orgId)){
				sql.append("SELECT A.ORG_ID  AS id,A.ORG_NAME AS name,'ORG' AS partyType," +
						"A.ORG_PATH_CODE AS pathCode,CASE WHEN A.PARENT_ID=0 THEN 'false'  end AS mhasParent," +
						"  'true' AS mhasChild,A.Parent_Id AS parent,'false' as expanded" +
						"  FROM UOS_ORG A,UOS_AREA B" +
						"  WHERE (A.PARENT_ID=0 OR A.PARENT_ID IS NULL) " +
						" AND A.STATE='1' AND B.STATE='10A' AND A.AREA_ID=B.AREA_ID"
				);
			}else{
				sql.append("SELECT A.ORG_ID  AS id,A.ORG_NAME AS name,'ORG' AS partyType," +
						"A.ORG_PATH_CODE AS pathCode,CASE WHEN A.PARENT_ID=0 THEN 'flase' ELSE 'true' end AS mhasParent," +
						"  'true' AS mhasChild,A.Parent_Id AS parent,'false' as expanded" +
						"  FROM UOS_ORG A,UOS_AREA B" +
						"  WHERE A.ORG_ID="+orgId);

				sql.append("  AND A.STATE='1' AND B.STATE='10A' AND A.AREA_ID=B.AREA_ID"
				);
			}
		}else{
			//查询下面的组织
			sql.append("(SELECT A.ORG_ID  AS id,A.ORG_NAME AS name,'ORG' AS partyType," +
					"A.ORG_PATH_CODE AS pathCode,CASE WHEN A.PARENT_ID=0 THEN 'false' ELSE 'true' end AS mhasParent," +
					" 'true' AS mhasChild,A.Parent_Id AS parent,'false' as expanded" +
					" FROM UOS_ORG A,UOS_AREA B WHERE A.AREA_ID=B.AREA_ID AND A.PARENT_ID=" +orgId+
					" AND A.STATE='1' " +
					" AND B.STATE='10A' ");
			sql.append(" UNION ALL ");
			//查询职位下的人
			sql.append("SELECT A.Staff_ID AS id,A.staff_NAME AS name,'STA' AS partyType,D.Org_Path_Code AS pathCode," +
					" 'true' AS mhasParent,'false' AS mhasChild,D.ORG_ID AS parent,'false' as expanded" +
					" FROM UOS_STAFF A,UOS_JOB_STAFF B,UOS_JOB C,UOS_ORG D" +
					" WHERE A.STAFF_ID = B.STAFF_ID AND B.JOB_ID= C.JOB_ID AND C.ORG_ID=D.ORG_ID" +
					" AND B.JOB_ID IN " +
					"( SELECT A.JOB_ID  FROM UOS_JOB A,UOS_ORG C,UOS_AREA D " +
					" WHERE A.ORG_ID= "+orgId+" AND C.AREA_ID=D.AREA_ID AND A.JOB_NAME NOT LIKE 'JOB_%' " +
					"AND A.STATE='1' AND C.ORG_ID="+orgId+" )" +
					" AND B.STATE='1' AND D.STATE='1' AND C.STATE='1'");
			sql.append(" )ORDER BY partyType,pathCode ASC ");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("getOrgList的SQL是：" + sql);
		}
		return sql;
	}

	private StringBuffer buildSqlForAllOrgTree(Map paramMap) {
		StringBuffer sql = new StringBuffer("" );
		//String orgId=MapUtils.getString(paramMap, "orgId");
		String orgPathCode=MapUtils.getString(paramMap, "orgPathCode");
		//查询下面的组织
		sql.append("(SELECT A.ORG_ID  AS id,A.ORG_NAME AS name,'ORG' AS partyType," +
				"A.ORG_PATH_CODE AS pathCode,CASE WHEN A.PARENT_ID=0 THEN 'false' ELSE 'true' end AS mhasParent," +
				" 'true' AS mhasChild,A.Parent_Id AS parent,'false' as expanded" +
				" FROM UOS_ORG A,UOS_AREA B WHERE A.AREA_ID=B.AREA_ID " +
				"AND A.ORG_PATH_CODE like '" +orgPathCode+ "%'" +
				" AND A.STATE='1' " +
				" AND B.STATE='10A' ");
		sql.append(" UNION ALL ");
		//查询职位下的人
		sql.append("SELECT A.Staff_ID AS id,A.staff_NAME AS name,'STA' AS partyType,D.Org_Path_Code AS pathCode," +
				" 'true' AS mhasParent,'false' AS mhasChild,D.ORG_ID AS parent,'false' as expanded" +
				" FROM UOS_STAFF A,UOS_JOB_STAFF B,UOS_JOB C,UOS_ORG D" +
				" WHERE A.STAFF_ID = B.STAFF_ID AND B.JOB_ID= C.JOB_ID AND C.ORG_ID=D.ORG_ID" +
				" AND B.JOB_ID IN " +
				"( SELECT A.JOB_ID  FROM UOS_JOB A,UOS_ORG C,UOS_AREA D " +
				" WHERE A.ORG_ID= C.ORG_ID AND C.AREA_ID=D.AREA_ID AND A.JOB_NAME NOT LIKE 'JOB_%' " +
				"AND A.STATE='1' AND C.ORG_PATH_CODE like '"+orgPathCode+"%' )" +
				" AND B.STATE='1' AND D.STATE='1' AND C.STATE='1'");
		sql.append(" ) ORDER BY partyType,pathCode ASC ");

		if(logger.isDebugEnabled()) {
			logger.debug("getAllOrgList的SQL是：" + sql);
		}
		return sql;
	}
	private StringBuffer buildSqlForAuthOrgTree(Map paramMap) {
		StringBuffer sql = new StringBuffer("" );
		String orgId=MapUtils.getString(paramMap, "OrgId");
		String parentOrgId=MapUtils.getString(paramMap, "ParentOrgId");
		if(paramMap!=null&&"true".equals(MapUtils.getString(paramMap, "IsFirst"))){
			//第一次查询父节点

//			sql.append("SELECT UO.ORG_ID  AS id,UO.ORG_NAME AS name,'ORG' AS partyType," +
//					"UO.ORG_PATH_CODE AS pathCode," +
//					" CASE WHEN UO.PARENT_ID=0 THEN 'false' ELSE 'true' end AS mhasParent," +
//					" 'true' AS mhasChild,UO.Parent_Id AS parent,'false' AS expanded,'0' AS allowed " +
//					"  FROM UOS_ORG UO " +
//					" WHERE UO.ORG_PATH_CODE IN " +
//					"(SELECT  B.ORG_PATH_CODE AS ORG_PATH_CODE" +
//					"	FROM FAULT_DISP_PARTY_PERFORMER A   " +
//					" JOIN UOS_ORG B ON A.party_org_id = B.ORG_ID AND  B.STATE ='1'  " +
//					"	WHERE   A.ORG_ID = "+parentOrgId+"  AND A.OPER_TYPE=1  ) ORDER BY partyType,pathCode ASC "
//			);
			sql.append("SELECT A.ORG_ID  AS id,A.ORG_NAME AS name,'ORG' AS partyType," +
					"A.ORG_PATH_CODE AS pathCode,CASE WHEN A.PARENT_ID=0 THEN 'flase' ELSE 'true' end AS mhasParent," +
					"  'true' AS mhasChild,A.Parent_Id AS parent,'false' as expanded," +
					"  CASE WHEN C.PERFORMER_ID IS NOT NULL THEN '1' ELSE '0' END AS allowed " +
					" FROM UOS_ORG A" +
					" LEFT JOIN FAULT_DISP_PARTY_PERFORMER C ON C.PARTY_ID = A.ORG_ID AND C.PARTY_TYPE = 'ORG' AND c.org_id="+parentOrgId+
					" WHERE A.ORG_ID="+parentOrgId);
			sql.append("  AND A.STATE='1' ");

		}else{

			//查询职位下的人
			sql.append("(SELECT US.Staff_ID AS id,US.staff_NAME AS name,'STA' AS partyType," +
					"UO.Org_Path_Code AS pathCode,'true' AS mhasParent,'false' AS mhasChild," +
					"UO.ORG_ID AS parent,'false' AS expanded, '1' AS allowed FROM UOS_STAFF US" +
					" JOIN UOS_JOB_STAFF UJS ON UJS.STAFF_ID = US.STAFF_ID  AND UJS.STATE = '1' AND UJS.IS_NORMAL = '1'" +
					" JOIN UOS_JOB UJ ON UJ.JOB_ID = UJS.JOB_ID AND UJ.STATE = '1'" +
					" JOIN UOS_ORG UO ON UO.ORG_ID = UJ.ORG_ID AND UO.STATE = '1'" +
					" WHERE US.STATE = '1' AND UJ.ORG_ID ="+orgId+"" +
					" AND EXISTS (SELECT A.PERFORMER_ID AS PERFORMERID" +
					" FROM FAULT_DISP_PARTY_PERFORMER A" +
					" JOIN UOS_STAFF C ON A.PARTY_ID = C.STAFF_ID" +
					" AND A.PARTY_TYPE = 'STA' AND C.STATE = '1' WHERE A.ORG_ID ="+parentOrgId+" AND C.STAFF_ID = US.STAFF_ID ) ");

			sql.append("  UNION ALL ");

			//查询下面的组织
			sql.append(" SELECT UO.ORG_ID AS id,UO.ORG_NAME AS name,'ORG' AS partyType," +
					"UO.ORG_PATH_CODE AS pathCode," +
					" CASE WHEN UO.PARENT_ID=0 THEN 'false' ELSE 'true' end AS mhasParent," +
					" 'true' AS mhasChild,UO.Parent_Id AS parent,'false' AS expanded," +
					" CASE WHEN (SELECT COUNT(*) FROM FAULT_DISP_PARTY_PERFORMER A" +
					" JOIN UOS_ORG B ON A.PARTY_ID = B.ORG_ID AND A.PARTY_TYPE = 'ORG' AND B.STATE = '1' " +
					"WHERE A.ORG_ID = "+parentOrgId+" AND A.PARTY_ID = UO.ORG_ID) > 0 THEN '1' ELSE '0' END AS allowed" +
					" FROM UOS_ORG UO WHERE UO.STATE = '1' AND UO.PARENT_ID ="+orgId+"" +
					"  AND EXISTS (SELECT A.PERFORMER_ID AS PERFORMERID FROM FAULT_DISP_PARTY_PERFORMER A" +
					" JOIN UOS_ORG B ON A.PARTY_ORG_ID = B.ORG_ID AND B.STATE = '1'" +
					" WHERE A.ORG_ID ="+parentOrgId+" AND B.ORG_PATH_CODE LIKE UO.ORG_PATH_CODE || '%' )");

			sql.append(" )ORDER BY partyType,pathCode ASC ");
		}
		if(logger.isDebugEnabled()) {
			logger.debug("getOrgList的SQL是：" + sql);
		}
		return sql;
	}


	private StringBuffer buildSqlForFaultReason(Map paramMap) {
		StringBuffer sql = new StringBuffer("" );
		sql.append("SELECT t.recover_reason_id AS id,t.recover_reason AS name,'false' AS fold," +
				"parent_id AS parent,t.fault_kind_id AS faultKind," +
				"t.path_code AS pathCode, " +
				"CASE WHEN parent_id='0' OR parent_id IS NULL THEN 'false' ELSE 'true' END AS mhasParent," +
				"has_child AS mhasChild from fault_recover_reason t " +
				" WHERE t.state=1 ");
		if(MapUtils.getString(paramMap, "FaultKind")!=null&&!"".equals(MapUtils.getString(paramMap, "FaultKind"))){
			sql.append(" AND t.fault_kind_id in ("+MapUtils.getString(paramMap, "FaultKind")+")");
		}
		sql.append(" ORDER BY pathCode ASC");
		if(logger.isDebugEnabled()) {
			logger.debug("getFaultReason的SQL是：" + sql);
		}
		return sql;
	}

	public Map<String, Object> getMakeDataByWkOrderId(String wkOrderId)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_Qry_Makedata_By_Orderid(?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			callStmt.setString(1, wkOrderId);

			//callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.registerOutParameter(2, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(11, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(12, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(13, OracleTypes.VARCHAR);

			callStmt.execute();
			//int returnNum = callStmt.getInt(1);
			String orderCode = callStmt.getString(2);//工单编号
			String teleNumber = callStmt.getString(3);//电话号码
			String hguSn = callStmt.getString(4);//HGU（光猫）SN
			String stbMac = callStmt.getString(5);//STB(机顶盒)MAC
			String eqpId = callStmt.getString(6);//设备ID
			String eqpName = callStmt.getString(7);//设备名
			String portId = callStmt.getString(8);//端口ID
			String portName = callStmt.getString(9);//端口名
			String ponId = callStmt.getString(10);//PON口ID
			String ponName = callStmt.getString(11);//PON口名
			String makingFlag = callStmt.getString(12);//是否正在数据制作 0：没有 1：正在制作
			String lastMakingDate = callStmt.getString(13);//最近一次数据制作开始时间

			resultMap.put("returnNum", "1");
			resultMap.put("flag_desc", "查询成功");
			resultMap.put("orderCode", orderCode);
			resultMap.put("teleNumber", teleNumber);
			resultMap.put("hguSn", hguSn);
			resultMap.put("stbMac", stbMac);
			resultMap.put("eqpId", eqpId);
			resultMap.put("eqpName", eqpName);
			resultMap.put("portId", portId);
			resultMap.put("portName", portName);
			resultMap.put("ponId", ponId);
			resultMap.put("ponName", ponName);
			resultMap.put("makingFlag", makingFlag);
			resultMap.put("lastMakingDate", lastMakingDate);
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "根据ORDERID:"+wkOrderId+"查询数据制作界面信息失败[f_Qry_Makedata_By_Orderid]!");
			resultMap.put("returnNum",  "-1");
			resultMap.put("makingFlag",  "0");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public Map<String, Object> getMakeProgressByWkOrderId(String wkOrderId,String type)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			if ("sm".equals(type)) {
				// 扫码装机
				callStmt = conn.prepareCall("{call f_qry_making_progress_sm(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			} else {
				callStmt = conn.prepareCall("{call f_Qry_Making_Progress(?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
			}
			callStmt.setString(1, wkOrderId);

			//callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.registerOutParameter(2, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(11, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(12, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(13, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(14, OracleTypes.VARCHAR);

			callStmt.execute();
			//int returnNum = callStmt.getInt(1);
			String startDate = callStmt.getString(2);//数据制作开始时间
			String endDate = callStmt.getString(3);//数据制作结束时间
			String endingFlag = callStmt.getString(4);//数据制作流程是否完成 0：未完成 1：已完成
			String progress1 = callStmt.getString(5);//数据制作进度具体描述1
			String progress2 = callStmt.getString(6);//数据制作进度具体描述2
			String progress3 = callStmt.getString(7);//数据制作进度具体描述3
			String progress4 = callStmt.getString(8);//数据制作进度具体描述4
			String progress5 = callStmt.getString(9);//数据制作进度具体描述5
			String progress6 = callStmt.getString(10);//数据制作进度具体描述6
			String progress7 = callStmt.getString(11);//数据制作进度具体描述7
			String progress8 = callStmt.getString(12);//数据制作进度具体描述8
			String progress9 = callStmt.getString(13);//数据制作进度具体描述9
			String progress10 = callStmt.getString(14);//数据制作进度具体描述10

			resultMap.put("returnNum", "1");
			resultMap.put("flag_desc", "查询成功");
			resultMap.put("startDate", startDate);
			resultMap.put("endDate", endDate);
			resultMap.put("endingFlag", endingFlag);
			resultMap.put("progress1", progress1);
			resultMap.put("progress2", progress2);
			resultMap.put("progress3", progress3);
			resultMap.put("progress4", progress4);
			resultMap.put("progress5", progress5);
			resultMap.put("progress6", progress6);
			resultMap.put("progress7", progress7);
			resultMap.put("progress8", progress8);
			resultMap.put("progress9", progress9);
			resultMap.put("progress10", progress10);
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "根据ORDERID:"+wkOrderId+"查询数据制作进度信息失败[f_Qry_Making_Progress]!");
			resultMap.put("returnNum",  "-1");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public Map<String, Object> addMakeDataInfo(Map paramMap)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_Make_Data_new(?,?,?,?,?,?,?,?,?,?) }");

			callStmt.setString(1, String.valueOf(paramMap.get("wk_order_id")));
			callStmt.setString(2, String.valueOf(paramMap.get("hgu_sn")));
			callStmt.setString(3, String.valueOf(paramMap.get("stb_mac")));
			callStmt.setString(4, String.valueOf(paramMap.get("eqp_id")));
			callStmt.setString(5, String.valueOf(paramMap.get("port_id")));
			callStmt.setString(6, String.valueOf(paramMap.get("pon_id")));
			callStmt.setString(7, String.valueOf(paramMap.get("staff_id")));
			callStmt.setString(8, String.valueOf(paramMap.get("staff_name")));

			//callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);

			callStmt.execute();
			//int returnNum = callStmt.getInt(1);
			String makeDataFlag = callStmt.getString(9);
			String makeDataDesc = callStmt.getString(10);

			resultMap.put("flag_desc",  makeDataDesc);
			resultMap.put("returnNum",  "1");
			resultMap.put("makeDataFlag",  makeDataFlag);
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "添加数据制作信息失败[f_Make_Data]");
			resultMap.put("returnNum",  "-1");
			resultMap.put("makeDataFlag",  "0");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public Map<String, Object> commitTerminalInfo(Map paramMap)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_commit_terminal(?,?,?,?,?,?,?)}");

			callStmt.setString(1, String.valueOf(paramMap.get("wk_order_id")));
			callStmt.setString(2, String.valueOf(paramMap.get("hgu_sn")));
			callStmt.setString(3, String.valueOf(paramMap.get("stb_mac")));
			callStmt.setString(4, String.valueOf(paramMap.get("staff_id")));
			callStmt.setString(5, String.valueOf(paramMap.get("staff_name")));

			//callStmt.registerOutParameter(1, Types.INTEGER);
			callStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);

			callStmt.execute();
			//int returnNum = callStmt.getInt(1);
			String makeDataFlag = callStmt.getString(6);
			String makeDataDesc = callStmt.getString(7);

			resultMap.put("flag_desc",  makeDataDesc);
			resultMap.put("returnNum",  "1");
			resultMap.put("makeDataFlag",  makeDataFlag);
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "提交终端数据失败[f_commit_terminal]");
			resultMap.put("returnNum",  "-1");
			resultMap.put("makeDataFlag",  "0");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public List<Map> qryOltByName(String oltName,String wkOrderId) throws DataAccessException {
		StringBuffer sql = new StringBuffer();
		sql.append("select eqp_id,eqp_name from V_APP_OLT a where p_view_workorderid.set_workorderid('"+wkOrderId+"')='"+wkOrderId+"' and a.eqp_name like '%").append(oltName).append("%' order by a.eqp_name");
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		return list;
	}

	public List<Map> qryOltPonPortById(String oltId) throws DataAccessException {
		StringBuffer sql = new StringBuffer();
		//sql.append("select port_id,port_name,svlan,cvlan,super_res_id from(");
		sql.append("select port_id,port_name,svlan,cvlan,super_res_id from V_APP_PON a where a.super_res_id = '").append(oltId).append("' order by a.port_name");
		//sql.append(") where rownum <=50");
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		return list;
	}

	public Map<String, Object> getTerminalByWkOrderId(String wkOrderId)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_Qry_Terminal(?,?,?,?,?) }");

			callStmt.setString(1, wkOrderId);
			callStmt.registerOutParameter(2, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);

			callStmt.execute();
			//int returnNum = callStmt.getInt(1);
			String hgu_sn = callStmt.getString(2);
			String stb_mac = callStmt.getString(3);
			String bk1 = callStmt.getString(4)==null?"":callStmt.getString(4);
			String bk2 = callStmt.getString(5)==null?"":callStmt.getString(5);
			resultMap.put("hgn_sn",  hgu_sn==null?"":hgu_sn);
			resultMap.put("stb_mac",  stb_mac==null?"":stb_mac);
			resultMap.put("bk1",  bk1);
			resultMap.put("bk2",  bk2);
			resultMap.put("returnNum",  "1");
			resultMap.put("flag_desc",  "查询终端数据成功");
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "查询终端数据失败[f_Qry_Terminal]");
			resultMap.put("returnNum",  "-1");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}
	//正式环境不需要ClobToString 转换
	public String getReportInfo(String retrieveDate, String staffId,
								String reportType) throws DataAccessException {

		String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','"+reportType+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null, null);
		String report_str= "";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;
	}
	/**
	 * 报表查询test
	 * @throws DataAccessException
	 * */
	public String getReportTestInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select hnlt_gk.PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','801') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}
	/**
	 * 报表查询Company
	 * @throws DataAccessException
	 * */
	public String getReportCompanyInfo(String retrieveDate,String areaName) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_khzb_company_QUOTA('"+retrieveDate+"','"+areaName+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}
	/**
	 * 报表查询CompanyStaff
	 * @throws DataAccessException
	 * */
	public String getReportCompanyStaffInfo(String retrieveDate,String areaName,String company) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_khzb_company_staff_QUOTA('"+retrieveDate+"','"+areaName+"','"+company+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}
	/**
	 * 报表查询StaffDay
	 * @throws DataAccessException
	 * */
	public String getReportStaffDayInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select hnlt_gk.PKG_KGZB_QUERY.F_khzb_staff_QUOTA('"+retrieveDate+"','"+staffId+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}
	/**
	 * 报表查询OrderAlarm
	 * @throws DataAccessException
	 * */
	public String getOrderAlarmInfo(String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select hnlt_gk.PKG_ORDER_ALARM.F_ORDER_ALARM_QUOTA('"+staffId+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}

	/**
	 * 报表查询OrderDetail
	 * @throws DataAccessException
	 * */
	public String getOrderDetailInfo(String accNbr,String areaNbr) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select hnlt_gk.app_work_order_query('"+accNbr+"','"+areaNbr+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}
	/**
	 * 报表查询companyLogin
	 * @throws DataAccessException
	 * */
	public String getReportCompanyLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','701') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}

	/**
	 * 报表查询companyDwLogin
	 * @throws DataAccessException
	 * */
	public String getReportCompanyDwLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','702') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}

	/**
	 * 报表查询companyDetsil
	 * @throws DataAccessException
	 * */
	public String getReportCompanyDetailLoginInfo(String retrieveDate,String areaName) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_login_on_company_QUOTA('"+retrieveDate+"','"+areaName+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}

	/**
	 * 报表查询companyDwLogin
	 * @throws DataAccessException
	 * */
	public String getReportStaffLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_login_list_QUOTA('"+retrieveDate+"','"+staffId+"') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;

	}


	/**
	 * 报表查询ManagerLogin
	 * @throws DataAccessException
	 * */
	public String getReportManagerLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
		//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
		String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','703') as report_str from dual";
		Map map = dynamicQueryObjectByMap(sql, null,null);
		System.out.println(sql);
		String report_str="";
		if(null != map){
			if(map.containsKey("report_str") && null!=map.get("report_str")){
				report_str = map.get("report_str").toString();
			}else{
				report_str="[]";
			}

		}else{
			report_str="[]";
		}
		return report_str;


	}

	public Map<String, Object> getProduceOrderIdByOrderId(String orderId) {
		StringBuffer sql = new StringBuffer("select t.cust_order_code as produceOrderId");
		sql.append(" from  om_order_key_info t where t.id= " + orderId);

		Map<String, Object> map = null;
		try {
			map = dynamicQueryObjectByMap(sql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("getProduceOrderIdByOrderId 失败，"+ e.getMessage());
		}
		return map;
	}

	public Map<String, Object> getRegionInfoByOrderId(String orderId) {
		StringBuffer sql = new StringBuffer("select oo.order_code as orderCode, ua.acronym as eparchyCode,ua.area_id as countyCode,ua.area_name as countyName,ps.pro_id as serviceType \n");
		sql.append(" from   om_order oo, uos_area ua,pm_service_with_product ps \n");
		sql.append(" where oo.area_id=ua.area_id and oo.service_id=ps.id \n");
		sql.append(" and oo.id=" + orderId);

		Map<String, Object> map = null;
		try {
			map = dynamicQueryObjectByMap(sql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("getRegionInfoByOrderId 失败，"+ e.getMessage());
		}
		return map;
	}

	public Map<String, Object> getStaffInfoByStaffId(String staffId) {
		StringBuffer sql = new StringBuffer("select t.mobile_tel as mobileTel,t.username as userName \n");
		sql.append(" from  uos_staff t where t.staff_id= " + staffId);

		Map<String, Object> map = null;
		try {
			map = dynamicQueryObjectByMap(sql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("getRegionInfoByOrderId 失败，"+ e.getMessage());
		}
		return map;
	}

	public void insertIntfLog(String orderCode,String operName,String returnDesc) throws DataAccessException {
		Connection conn = null;
		PreparedStatement pst = null;
		int dbloop = 1;
		try {
			String sql = "INSERT INTO INTERFACE_LOG(ORDER_CODE,OPER_NAME,STATE_TIME,RETURN_DESC)  VALUES (?,?,sysdate,?)";
			logger.debug("insertIntfLog sql===========>: " + sql);
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(dbloop++, orderCode);
			pst.setString(dbloop++, operName);
			pst.setString(dbloop++, returnDesc);
			pst.execute();
		}catch(Exception e){
			logger.error("insertIntfLog error:" + e.getMessage());
		}finally {
			cleanUp(conn, null, pst, null);
		}

	}

	public boolean validateModifyOrder(String orderId){
		boolean result = false;
		StringBuffer sql = new StringBuffer("select t.attr_value as attrValue from  om_so_attr t \n");
		sql.append(" where t.service_order_id='" + orderId + "' \n");
		sql.append(" and t.attr_code='orignalSoNbr'");

		Map<String, Object> map = null;
		try {
			map = dynamicQueryObjectByMap(sql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("validateModifyOrder 失败，"+ e.getMessage());
		}
		if(map == null ||  map.get("attrValue") == null){
			result = true;
		}

		return result;
	}

	public Map<String, Object> getInstallDataByQrCode(String qrCode, String wkOrderId) {
		// qrCode = "6901285991219";
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();

			logger.info("<<<qrCode>>>:" + qrCode);
			logger.info("<<<wkOrderId>>>:" + wkOrderId);
			// if (StringUtil.isNull(qrCode)) {
			String resultQrCode = "";
			// 先根据wkOrderId查询设备二维码
			StringBuffer queryQrCodeSql = new StringBuffer();
			queryQrCodeSql.append(" Select t.hgu_sn as qrCode,res_port_id as portId,p.digcode as digCode \n");
			queryQrCodeSql.append(" From Uos_Datamake t left join pub_two_dimension_code p on t.res_port_id = p.res_id where t.wk_order_id = '" + wkOrderId + "' and t.oper_type =2\n");
			queryQrCodeSql.append(" order by t.create_date desc");
			Map<String, Object> map = null;
			try {
				map = dynamicQueryObjectByMap(queryQrCodeSql.toString(), null, null);
			} catch (DataAccessException e) {
				logger.error("wkOrderId:" + wkOrderId + "查询设备二维码失败，" + e.getMessage());
			}
			String portId = "";
			String digCode = "";
			if (map != null && map.size() > 0) {
				resultQrCode = (String) map.get("qrCode");
				portId = (String) map.get("portId");
				digCode = (String) map.get("digCode");

			}
			// }

			logger.info("<<<resultQrCode>>>:" + resultQrCode);

			callStmt = conn.prepareCall("{call Sf_Get_eqpinfo_by_code2_bk2(?,?,?,?,?,?,?,?,?,?,?) }");
			if(StringUtil.isNull(qrCode)){
				//为空表示第一次扫码装机进入
				callStmt.setString(1, resultQrCode);
			}else{
				//装机完成后查询展示
				callStmt.setString(1, qrCode);
			}

			callStmt.registerOutParameter(2, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(3, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(4, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(5, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(6, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(7, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(8, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(11, OracleTypes.VARCHAR);
			callStmt.execute();
			String splitterName = callStmt.getString(2);// 分光器名称
			String eqpId = callStmt.getString(3);
			String oltIP = callStmt.getString(4);// oltIp
			String oltName = callStmt.getString(5);// olt名称
			String pon = callStmt.getString(6);// pon口
			String ponId = callStmt.getString(7);// pon口id

			String address = callStmt.getString(8);//安放地址
			String standName = callStmt.getString(9);//绑定标准地址
			String resId = callStmt.getString(10);//resId
			String remark = callStmt.getString(11);//备注
			resultMap.put("splitterName", splitterName);
			resultMap.put("oltIP", oltIP);
			resultMap.put("oltName", oltName);
			resultMap.put("pon", pon);
			resultMap.put("eqpId", eqpId);
			resultMap.put("ponId", ponId);
			resultMap.put("qrCode", resultQrCode);
			resultMap.put("portId", portId);
			resultMap.put("digCode", digCode);
			resultMap.put("address",address);
			resultMap.put("standName",standName);
			resultMap.put("resId",resId);
			resultMap.put("remark",remark);
			// 根据二维码查询分光器端口列表
			StringBuffer sbf = new StringBuffer();
			sbf.append(
					" select c.position as portName , SF_GET_DESC_CHINA(c.OPR_STATE_ID) as state ,port_id as portId\n");
			sbf.append(" from rme_eqp a, pub_two_dimension_code b, rme_port c           \n");
			sbf.append(" where a.delete_state = '0'                                     \n");
			sbf.append("  and a.res_type_id = '2530'                                    \n");
			sbf.append("  and a.eqp_id = b.res_id                                       \n");
			sbf.append("  and b.delete_state = '0'                                      \n");
			sbf.append("  and b.res_type_id  in( '2530','160001')                       \n");
			sbf.append("  and c.delete_state = '0'                                      \n");
			sbf.append("  and c.super_res_id = a.eqp_id                                 \n");
			if(StringUtil.isNull(qrCode)){
				sbf.append("  and b.digcode ='" + resultQrCode + "' ");
			}else{
				sbf.append("  and b.digcode ='" + qrCode + "' ");
			}
			sbf.append(" order by portName");

			List ponPortList = null;
			try {
				ponPortList = dynamicQueryListByMap(sbf.toString(), null, null);
			} catch (DataAccessException e) {
				logger.error("查询分光器端口信息失败，qrCode：" + qrCode + "。" + e.getMessage());
			}

			//查询订单预占的端口
			String yzPortId = "";
			StringBuilder sb = new StringBuilder();
			sb.append(" select r.res_id as resId                                   \n");
			sb.append("    from Asn_Link_Route r, srv_instance i,wo_work_order a   \n");
			sb.append("   where r.Delete_State = '0'                               \n");
			sb.append("     And r.Link_Id = i.Route_Id                             \n");
			sb.append("     and i.Dis_Seq = to_char(a.base_order_id)               \n");
			sb.append("     And i.Srv_Id <> ('1001')                               \n");
			sb.append("     And i.Delete_State = '0'                               \n");
			sb.append("     and a.id= '" + wkOrderId + "'                           ");
			try {
				map = dynamicQueryObjectByMap(sb.toString(), null, null);
			} catch (DataAccessException e) {
				logger.error("wkOrderId:" + wkOrderId + ",查询订单预占的端口失败，" + e.getMessage());
			}
			if (null != map) {
				yzPortId = (String) map.get("resId");
				logger.info("wkOrderId:" + wkOrderId + ",预占端口为：" + yzPortId);
				// 如果是该定单之前预占的端口，前台状态要改成未占用，可供其选择，然后提交后进行覆盖
				if (ponPortList != null && ponPortList.size() > 0 && !StringUtil.isNull(yzPortId)) {
					for (Object portInfo : ponPortList) {
						if (((Map) portInfo).get("portId").equals(yzPortId)) {
							((Map) portInfo).put("state", "空闲");
						}
					}
				}
			}
			resultMap.put("ponPortList", ponPortList);
		} catch (SQLException e) {
			resultMap.put("flag_desc", "根据qrCode:" + qrCode + "查询资源信息失败!");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}


	public Map<String, Object> addMakeDataSMInfo(Map paramMap)
			throws DataAccessException {
		Map<String, Object> resultMap =  new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement callStmt = null;
		try {
			conn = getConnection();
			callStmt = conn.prepareCall("{call f_Make_Data_SM(?,?,?,?,?,?,?,?,?,?) }");

			callStmt.setString(1, String.valueOf(paramMap.get("wk_order_id")));
			callStmt.setString(2, String.valueOf(paramMap.get("hgu_sn")));
			callStmt.setString(3, String.valueOf(paramMap.get("stb_mac")));
			callStmt.setString(4, String.valueOf(paramMap.get("eqp_id")));
			callStmt.setString(5, String.valueOf(paramMap.get("port_id")));
			callStmt.setString(6, String.valueOf(paramMap.get("pon_id")));
			callStmt.setString(7, String.valueOf(paramMap.get("staff_id")));
			callStmt.setString(8, String.valueOf(paramMap.get("staff_name")));

			callStmt.registerOutParameter(9, OracleTypes.VARCHAR);
			callStmt.registerOutParameter(10, OracleTypes.VARCHAR);

			callStmt.execute();
			String makeDataFlag = callStmt.getString(9);
			String makeDataDesc = callStmt.getString(10);

			resultMap.put("flag_desc",  makeDataDesc);
			resultMap.put("returnNum",  "1");
			resultMap.put("makeDataFlag",  makeDataFlag);


			String resultQrCode = "";
			String wkOrderId = (String) paramMap.get("wk_order_id");
			StringBuffer queryQrCodeSql = new StringBuffer();
			queryQrCodeSql.append(" Select t.hgu_sn as qrCode \n");
			queryQrCodeSql.append(" From Uos_Datamake t where t.wk_order_id = '" + wkOrderId + "' \n");
			queryQrCodeSql.append(" order by t.create_date desc");
			Map<String, Object> map = null;
			try {
				map = dynamicQueryObjectByMap(queryQrCodeSql.toString(), null, null);
			} catch (DataAccessException e) {
				logger.error("wkOrderId:" + wkOrderId + "查询设备二维码失败，" + e.getMessage());
			}
			if (map != null && map.size() > 0) {
				resultQrCode = (String) map.get("qrCode");
			}
			resultMap.put("resultQrCode", resultQrCode);
		} catch (SQLException e) {
			resultMap.put("flag_desc",  "添加数据制作信息失败[f_Make_Data_SM]");
			resultMap.put("returnNum",  "-1");
			resultMap.put("makeDataFlag",  "0");
			e.printStackTrace();
		} finally {
			cleanUp(conn, null, callStmt, null);
		}
		return resultMap;
	}

	public boolean validateSmInstallOrder(String orderId){
		boolean result = false;
		StringBuffer smOrderSql = new StringBuffer("select f_get_qcflag_by_orderid(" + orderId +") as returnVal from  dual \n");
		Map<String, Object> smOrderMap = null;
		try {
			smOrderMap = dynamicQueryObjectByMap(smOrderSql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("检验是否扫码单失败，" + e.getMessage());
		}

		if (smOrderMap == null || "1".equals(smOrderMap.get("returnVal"))) {
			result = true;
		}
		return result;
	}

	public Map<String, Object> pxCodeBind(String portId,String pxCode,String staffId) {
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pstDelete = null;
		int dbloop = 1;
		Map<String, Object> resultMap = new HashMap<String,Object>();
		try {
			conn = getConnection();
			//先删除原来的记录，根据端口id
			String deleteSql = "delete from pub_two_dimension_code where res_id = '" + portId + "'";
			logger.info("pxCodeBind deletesql===========>: " + deleteSql);
			pstDelete = conn.prepareStatement(deleteSql);
			pstDelete.execute();

			StringBuilder sbf = new StringBuilder();
			sbf.append("INSERT INTO pub_two_dimension_code ptdc( \n");
			sbf.append(" ptdc.id,                            \n");
			sbf.append(" ptdc.digcode,                       \n");
			sbf.append(" ptdc.res_type_id,                   \n");
			sbf.append(" ptdc.res_id,                        \n");
			sbf.append(" ptdc.code_type,                     \n");
			sbf.append(" ptdc.create_date,                   \n");
			sbf.append(" ptdc.modify_date,                   \n");
			sbf.append(" ptdc.usage,                         \n");
			sbf.append(" ptdc.state,                         \n");
			sbf.append(" ptdc.is_print,                      \n");
			sbf.append(" ptdc.create_op                      \n");
			sbf.append(" ) VALUES (                          \n");
			sbf.append(" SEQ_PUB_TWO_DIMENSION_CODE.NEXTVAL, \n");
			sbf.append("?, \n");
			sbf.append(" '6024',      \n");
			sbf.append("?, \n");
			sbf.append(" '1260002',    \n");
			sbf.append(" sysdate,                            \n");
			sbf.append(" sysdate,                            \n");
			sbf.append(" '0',                                \n");
			sbf.append(" '2',                                \n");
			sbf.append(" '1',                                \n");
			sbf.append("?         \n");
			sbf.append(" )                                   \n");

			String sql = sbf.toString();
			logger.info("pxCodeBind insertsql===========>: " + sql);
			pst = conn.prepareStatement(sql);
			pst.setString(dbloop++, pxCode);
			pst.setString(dbloop++, portId);
			pst.setString(dbloop++, staffId);
			pst.execute();
			resultMap.put("resultCode", "1");
		}catch(Exception e){
			resultMap.put("resultCode", "0");
			resultMap.put("resultMsg", "插入数据失败!");
			logger.error("pxCodeBind error:" + e.getMessage());
		}finally {
			cleanUp(conn, null, pst, null);
		}
		return resultMap;

	}

	public boolean validatePxCode(String pxCode){
		boolean result = false;
		StringBuffer smOrderSql = new StringBuffer("select digcode as digCode from pub_two_dimension_code t where t.digcode = '" + pxCode +"'");
		Map<String, Object> smOrderMap = null;
		try {
			smOrderMap = dynamicQueryObjectByMap(smOrderSql.toString(), null, null);
		} catch (DataAccessException e) {
			logger.error("校验皮线二维码失败，validatePxCode：" + e.getMessage());
		}

		if (smOrderMap == null || smOrderMap.get("digCode") == null) {
			result = true;
		}else{
			logger.error(pxCode + ",该皮线二维码已经存在！");
		}
		return result;
	}

	public List<Map> qryHandTree(Map paramMap) throws DataAccessException {
		StringBuffer sql = this.buildSqlForHand(paramMap);
		List<Map> list = dynamicQueryListByMap(sql.toString(), null, null);
		//�������
//		int i=list.size();
//		if(i>0){
//			for(int k=0;k<i;k++){
//				Map newObj=list.get(k);
//				String pathCode=MapUtils.getString(newObj, "pathCode");
//				String faultKind=MapUtils.getString(newObj, "faultKind");
//				int level=countLevel(pathCode);
//				newObj.put("level", level);
//			}
//		}
		return list;
	}

	private StringBuffer buildSqlForHand(Map paramMap) {
		StringBuffer sql = new StringBuffer("" );
		sql.append("SELECT t.id AS id,t.return_name AS name,'false' AS fold,\n" +
				"            parent_id AS parent,\n" +
				"            t.pathname AS pathname,t.level_id as level_id,CASE WHEN parent_id='0' OR parent_id IS NULL THEN 'false' ELSE 'true' END AS mhasParent,\n" +
				"            t.mhasChild AS mhasChild from om_sa_inhand_type t ");
		return sql;
	}

	public Map qryStaffIdArea(String staffId) throws Exception  {
		String sql = "SELECT DISTINCT UA.acronym, D.STAFF_ID \n" +
				"                FROM UOS_ORG A, UOS_JOB B, UOS_JOB_STAFF C, UOS_STAFF D, UOS_AREA UA \n" +
				"                WHERE A.ORG_ID = B.ORG_ID \n" +
				"                AND B.JOB_ID = C.JOB_ID \n" +
				"                AND A.AREA_ID = UA.AREA_ID \n" +
				"                AND C.IS_NORMAL = '1' \n" +
				"                AND C.STATE = '1' \n" +
				"                AND A.STATE = 1 \n" +
				"                AND B.STATE = 1 \n" +
				"                AND D.STATE = 1 \n" +
				"                AND C.STAFF_ID = D.STAFF_ID \n" +
				"                AND D.STAFF_ID = "+staffId;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		Map resultMap = new HashMap();
		while (rs.next()) {
			resultMap.put("acronym",rs.getString("acronym"));
		}

		if(conn!=null)
		{
			conn.close();
		}
		if(ps!=null)
		{
			ps.close();

		}

		return resultMap;
	}

	public String qryOrderId(String workOrderId)throws Exception  {
		String sql = " select base_order_id from wo_work_order  where id="+workOrderId;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		Map resultMap = new HashMap();
		while (rs.next()) {

			String orderId = rs.getString("base_order_id");
			return orderId;
		}

		if(conn!=null)
		{
			conn.close();
		}
		if(ps!=null)
		{
			ps.close();

		}

		return "";
	}



	public Map<String,String> qryFaultOrderExt(String workOrderId)throws Exception  {
		String sql = "SELECT FA1.PARAM_NAME fault_type, \n" +
				"FA2.PARAM_NAME cust_typle,\n" +
				"FA3.PARAM_NAME fault_order_type ,\n" +
				"FA4.PARAM_NAME cust_def \n" +
				"FROM hnlt_gk.ACCEPT_FAULT_ORDER_INFO T \n" +
				"LEFT JOIN hnlt_gk.fault_order_info_config FA1 ON (FA1.STYPE = 'COMPAINT_PHENOMENON' AND FA1.PARAM_CODE = T.COMPAINT_PHENOMENON) \n" +
				"LEFT JOIN hnlt_gk.fault_order_info_config FA2 ON (FA2.STYPE = 'IMPORT_TANCE' AND FA2.PARAM_CODE = T.IMPORT_TANCE) \n" +
				"LEFT JOIN hnlt_gk.fault_order_info_config FA3 ON (FA3.STYPE = 'NET_WORK_FIELD' AND FA3.PARAM_CODE = T.NET_WORK_FIELD) \n" +
				"LEFT JOIN hnlt_gk.fault_order_info_config FA4 ON (FA4.STYPE = 'CONTACT_LEVE' AND FA4.PARAM_CODE = T.Contact_Leve) \n" +
				"WHERE T.CS_SHEET_ID = (SELECT OO.ORDER_CODE FROM hnlt_gk.WO_WORK_ORDER T \n" +
				"LEFT JOIN hnlt_gk.OM_ORDER OO ON OO.ID = T.BASE_ORDER_ID\n" +
				"WHERE T.ID = '"+workOrderId+"')\n" +
				" and rownum = 1";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = getConnection();
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		Map<String,String> resultMap = new HashMap<String,String>();
		while (rs.next()) {

			String fault_type = rs.getString("fault_type")==null?"":rs.getString("fault_type");
			String cust_typle = rs.getString("cust_typle")==null?"":rs.getString("cust_typle");
			String fault_order_type = rs.getString("fault_order_type")==null?"":rs.getString("fault_order_type");
			String cust_def = rs.getString("cust_def")==null?"":rs.getString("cust_def");
			resultMap.put("fault_type",fault_type);
			resultMap.put("cust_typle",cust_typle);
			resultMap.put("fault_order_type",fault_order_type);
			resultMap.put("cust_def",cust_def);
			return resultMap;
		}

		if(conn!=null)
		{
			conn.close();
		}
		if(ps!=null)
		{
			ps.close();

		}

		return resultMap;
	}

	public  void saveHandType(Map map)throws RequiredException, SystemException, SQLException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("insert into OM_HAND_TYPE(ID,order_id,staff_id,acc_nbr,inhand_type_id,inhand_pathname) ");
		sqlStr.append(" values(OM_HAND_TYPE_SEQ.nextval,?,?,?,?,?)");
		System.out.println(sqlStr.toString());
		int count=1;
		conn = getConnection();
		ps = conn.prepareStatement(sqlStr.toString());


		ps.setLong(1,Long.valueOf(MapUtils.getString(map,"orderId")));
		ps.setLong(2,Long.valueOf(MapUtils.getString(map,"staffId")));
		ps.setString(3,MapUtils.getString(map,"accNbr"));
		ps.setString(4,MapUtils.getString(map,"handId"));
		ps.setString(5,MapUtils.getString(map,"handPathName"));
		ps.execute();

		if(conn!=null)
		{
			conn.close();
		}
		if(ps!=null)
		{
			ps.close();
		}

	}



	//测试环境部分

//	public String getReportInfo(String retrieveDate, String staffId,
//			String reportType) throws DataAccessException {
//
//		String sql = "select hnlt_gk.PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','"+reportType+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null, null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//	}
//
//	private static String ClobToString(Object strClobObj) throws SQLException, IOException {
//		Clob strClob = null;
//		if (strClobObj != null && strClobObj instanceof Clob ){
//			strClob = (Clob)strClobObj;
//		}else{
//			return "";
//		}
//
//		if( strClob.length() < 1){
//			return "";
//		}
//
//		String reString = "";
//		Reader is = strClob.getCharacterStream();// 得到流
//		BufferedReader br = new BufferedReader(is);
//		String s = br.readLine();
//		StringBuffer sb = new StringBuffer();
//		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
//			sb.append(s);
//			s = br.readLine();
//		}
//		reString = sb.toString();
//		return reString;
//	}
//	 /**
//     * 报表查询test
//	 * @throws DataAccessException
//     * */
//    public String getReportTestInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select hnlt_gk.PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','801') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//    /**
//     * 报表查询Company
//	 * @throws DataAccessException
//     * */
//    public String getReportCompanyInfo(String retrieveDate,String areaName) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_khzb_company_QUOTA('"+retrieveDate+"','"+areaName+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//    }
//
//    /**
//     * 报表查询CompanyStaff
//	 * @throws DataAccessException
//     * */
//    public String getReportCompanyStaffInfo(String retrieveDate,String areaName,String company) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_khzb_company_staff_QUOTA('"+retrieveDate+"','"+areaName+"','"+company+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//
//    /**
//     * 报表查询StaffDay
//	 * @throws DataAccessException
//     * */
//    public String getReportStaffDayInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select hnlt_gk.PKG_KGZB_QUERY.F_khzb_staff_QUOTA('"+retrieveDate+"','"+staffId+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询OrderAlarm
//	 * @throws DataAccessException
//     * */
//    public String getOrderAlarmInfo(String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select hnlt_gk.PKG_ORDER_ALARM.F_ORDER_ALARM_QUOTA('"+staffId+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询OrderDetail
//	 * @throws DataAccessException
//     * */
//    public String getOrderDetailInfo(String accNbr,String areaNbr) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select hnlt_gk.app_work_order_query('"+accNbr+"','"+areaNbr+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询companyLogin
//	 * @throws DataAccessException
//     * */
//    public String getReportCompanyLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','701') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询companyDwLogin
//	 * @throws DataAccessException
//     * */
//    public String getReportCompanyDwLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','702') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询companyDetsil
//	 * @throws DataAccessException
//     * */
//    public String getReportCompanyDetailLoginInfo(String retrieveDate,String areaName) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_login_on_company_QUOTA('"+retrieveDate+"','"+areaName+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//    /**
//     * 报表查询companyDwLogin
//	 * @throws DataAccessException
//     * */
//    public String getReportStaffLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_login_list_QUOTA('"+retrieveDate+"','"+staffId+"') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//
//
//    }
//
//
//    /**
//     * 报表查询ManagerLogin
//	 * @throws DataAccessException
//     * */
//    public String getReportManagerLoginInfo(String retrieveDate,String staffId) throws DataAccessException{
//    	//String report_str  = "[{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"},{\"test1\":\"岳麓区\",\"test2\":\"1001\",\"test3\":\"装机预约\",\"test4\":\"75\",\"test5\":\"69.66\",\"test6\":\"69.66\",\"test7\":\"69.66\",\"test8\":\"69.66\",\"test9\":\"22\"}]";
//    	String sql = "select PKG_KGZB_QUERY.F_BASE_INTF('"+staffId+"','"+retrieveDate+"','703') as report_str from dual";
//		Map map = dynamicQueryObjectByMap(sql, null,null);
//		System.out.println(sql);
//        String report_str="";
//		try {
//			report_str = ClobToString(map.get("report_str"));
//			System.out.println("ClobToString=="+report_str);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        return report_str;
//		
//    	
//    }

}
