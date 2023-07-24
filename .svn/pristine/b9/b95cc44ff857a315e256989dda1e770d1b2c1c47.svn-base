package com.ztesoft.mobile.v2.dao.interf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileStaffMappingDAOImpl extends BaseDAOImpl implements
		MobileStaffMappingDAO {
	
	private static final Logger logger = Logger.getLogger(MobileStaffMappingDAOImpl.class);
	
	private static final String TABLE_NAME = "MOBILE_STAFF_MAPPING";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "STAFF_MAPPING_ID:@@SEQ,BUSI_SYS_ID:busiSysId,STAFF_ID:staffId,STAFF_NAME:staffName,MAPPING_USER_NAME:mappingUserName,MAPPING_STAFF_ID:mappingStaffId,MAPPING_STAFF_NAME:mappingStaffName,MAPPING_JOB_ID:mappingJobId,MAPPING_JOB_NAME:mappingJobName,MAPPING_ROLE_ID:mappingRoleId,MAPPING_ROLE_NAME:mappingRoleName,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("staffMappingId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STAFF_MAPPING_ID:staffMappingId,BUSI_SYS_ID:busiSysId,MAPPING_USER_NAME:mappingUserName,MAPPING_STAFF_ID:mappingStaffId,MAPPING_STAFF_NAME:mappingStaffName,MAPPING_JOB_ID:mappingJobId,MAPPING_JOB_NAME:mappingJobName,MAPPING_ROLE_ID:mappingRoleId,MAPPING_ROLE_NAME:mappingRoleName,UPDATE_TIME:updateTime,STATE_DATE:stateDate";
		String wherePatternStr = "STAFF_MAPPING_ID:staffMappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "STAFF_MAPPING_ID:staffMappingId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		return dynamicQueryObjectByMap(sqlStr.toString(), null, null);
	}
	
	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	private StringBuffer buildSql(Map paramMap) throws DataAccessException {
		
		StringBuffer sqlStr = new StringBuffer("SELECT   STAFF_MAPPING_ID AS staffMappingId, ");
		sqlStr.append(" BUSI_SYS_ID AS busiSysId, ");
		sqlStr.append(" STAFF_ID AS staffId, ");
		sqlStr.append(" STAFF_NAME AS staffName, ");
		sqlStr.append(" MAPPING_USER_NAME AS mappingUsername, ");
		sqlStr.append(" MAPPING_STAFF_ID AS mappingStaffId, ");
		sqlStr.append(" MAPPING_STAFF_NAME AS mappingStaffName, ");
		sqlStr.append(" MAPPING_JOB_ID AS mappingJobId, ");
		sqlStr.append(" MAPPING_JOB_NAME AS mappingJobName, ");
		sqlStr.append(" MAPPING_ROLE_ID AS mappingRoleId, ");
		sqlStr.append(" MAPPING_ROLE_NAME AS mappingRoleName ");
		//sqlStr.append(" BUILD_TIME AS buildTime,  UPDATE_TIME AS updateTime, ");
		//sqlStr.append(" STATE AS state,  STATE_DATE AS stateDate ");
		sqlStr.append(" FROM MOBILE_STAFF_MAPPING WHERE STATE = 1 ");
		
		Long staffId = MapUtils.getLong(paramMap, "staffId");
		if(null != staffId) {
			sqlStr.append(" AND STAFF_ID = " + staffId);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("buildSql´òÓ¡µÄSQLÊÇ£º" + sqlStr.toString());
		}

		return sqlStr;
	}
	
	public List getMappingInfoByStaffId(Long staffId) throws DataAccessException {
		Map paramMap = new HashMap();
		paramMap.put("staffId", staffId);
		
		return selAll(paramMap);
	}
	
	 public Map selByConn(Map paramMap) throws DataAccessException{
		    StringBuffer sqlStr = new StringBuffer("SELECT   A.STAFF_MAPPING_ID AS staffMappingId, ");
			sqlStr.append(" A.BUSI_SYS_ID AS busiSysId, ");
			sqlStr.append(" A.STAFF_ID AS staffId, ");
			sqlStr.append(" A.STAFF_NAME AS staffName,A.MAPPING_USER_NAME AS mappingUserName, ");
			sqlStr.append(" A.MAPPING_STAFF_ID AS mappingStaffId, ");
			sqlStr.append(" A.MAPPING_STAFF_NAME AS mappingStaffName, ");
			sqlStr.append(" A.MAPPING_JOB_ID AS mappingJobId, ");
			sqlStr.append(" A.MAPPING_JOB_NAME AS mappingJobName, ");
			sqlStr.append(" A.MAPPING_ROLE_ID AS mappingRoleId, ");
			sqlStr.append(" A.MAPPING_ROLE_NAME AS mappingRoleName,B.SYS_PROVIDER as sysProvider ");
			//sqlStr.append(" BUILD_TIME AS buildTime,  UPDATE_TIME AS updateTime, ");
			//sqlStr.append(" STATE AS state,  STATE_DATE AS stateDate ");
			sqlStr.append(" FROM MOBILE_STAFF_MAPPING A, MOBILE_BUSI_SYS B WHERE A.BUSI_SYS_ID = B.BUSI_SYS_ID AND A.STATE = 1 ");
			
			Long staffId = MapUtils.getLong(paramMap, "staffId");
			if(null != staffId) {
				sqlStr.append(" AND STAFF_ID = " + staffId);
			}
			
			System.out.println("selByConn="+sqlStr.toString());
			
			return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
			
		}

}
