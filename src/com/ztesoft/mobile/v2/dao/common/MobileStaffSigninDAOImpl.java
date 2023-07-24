package com.ztesoft.mobile.v2.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileStaffSigninDAOImpl extends BaseDAOImpl implements
		MobileStaffSigninDAO {

	private static final String TABLE_NAME = "MOBILE_STAFF_SIGNIN";

	public Long insert(Map paramMap) throws DataAccessException {
		String patternStr = "STAFF_SIGNIN_ID:staffSigninId,STAFF_ID:staffId,STAFF_NAME:staffName,USERNAME:username,SIGNIN_STATUS:signinStatus,SIGNIN_TIME:signinTime,LONGITUDE:longitude,LATITUDE:latitude,SIGNIN_TYPE:signinType,SIGNIN_ADDR:signinAddr,CROODS_TYPE:croodsType";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("staffSigninId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		//返回ID
		return MapUtils.getLong(paramMap, "staffSigninId");
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STAFF_SIGNIN_ID:staffSigninId,STAFF_ID:staffId,STAFF_NAME:staffName,USERNAME:username,SIGNIN_STATUS:signinStatus,SIGNIN_TIME:signinTime,LONGITUDE:longitude,LATITUDE:latitude,SIGNIN_TYPE:signinType,SIGNIN_ADDR:signinAddr,CROODS_TYPE:croodsType";
		String wherePatternStr = "STAFF_SIGNIN_ID:staffSigninId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "STAFF_SIGNIN_ID:staffSigninId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   STAFF_SIGNIN_ID AS staffSigninId, STAFF_ID AS staffId,STAFF_NAME AS staffName,USERNAME AS username,SIGNIN_STATUS AS signinStatus,SIGNIN_TIME AS signinTime,LONGITUDE AS longitude,LATITUDE AS latitude,SIGNIN_TYPE AS signinType,SIGNIN_ADDR AS signinAddr,CROODS_TYPE AS croodsType WHERE STAFF_SIGNIN_ID=?";
		String wherePatternStr = "STAFF_SIGNIN_ID:staffSigninId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public Map selByStaffId(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   STAFF_SIGNIN_ID AS staffSigninId, STAFF_ID AS staffId,STAFF_NAME AS staffName,USERNAME AS username,SIGNIN_STATUS AS signinStatus,SIGNIN_TIME AS signinTime,LONGITUDE AS longitude,LATITUDE AS latitude,SIGNIN_TYPE AS signinType,SIGNIN_ADDR AS signinAddr,CROODS_TYPE AS croodsType WHERE STAFF_ID=?";
		String wherePatternStr = "STAFF_ID:staffId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   STAFF_SIGNIN_ID AS staffSigninId, STAFF_ID AS staffId,STAFF_NAME AS staffName,USERNAME AS username,SIGNIN_STATUS AS signinStatus,SIGNIN_TIME AS signinTime,LONGITUDE AS longitude,LATITUDE AS latitude,SIGNIN_TYPE AS signinType,SIGNIN_ADDR AS signinAddr,CROODS_TYPE AS croodsType  FROM MOBILE_STAFF_SIGNIN";
		String wherePatternStr = null;
		
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public Map selAllPage(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   STAFF_SIGNIN_ID AS staffSigninId, STAFF_ID AS staffId,STAFF_NAME AS staffName,USERNAME AS username,SIGNIN_STATUS AS signinStatus,SIGNIN_TIME AS signinTime,LONGITUDE AS longitude,LATITUDE AS latitude,SIGNIN_TYPE AS signinType,SIGNIN_ADDR AS signinAddr,CROODS_TYPE AS croodsType  FROM MOBILE_STAFF_SIGNIN";
		String wherePatternStr = null;
		
		return populateQueryByMap(createSql(paramMap), ((Integer)paramMap.get("pageIndex")).intValue(), ((Integer)paramMap.get("pageSize")).intValue());
	}
	
	
//得到数据记录的sql
	private StringBuffer createSql(Map paramMap) {
		
		StringBuffer sqlStr = new StringBuffer(
				" SELECT   STAFF_SIGNIN_ID AS staffSigninId, STAFF_ID AS staffId,STAFF_NAME AS staffName,USERNAME AS username,SIGNIN_STATUS AS signinStatus,SIGNIN_TIME AS signinTime,LONGITUDE AS longitude,LATITUDE AS latitude,SIGNIN_TYPE AS signinType,SIGNIN_ADDR AS signinAddr,CROODS_TYPE AS croodsType  FROM MOBILE_STAFF_SIGNIN where 1=1 ");
		
		
		String beginDate = MapUtils.getString(paramMap, "beginDate");
		if(StringUtils.isNotBlank(beginDate)) {
			sqlStr.append(" and SIGNIN_TIME >= to_date('" + beginDate + "', 'yyyy-MM-dd HH24:mi:ss') ");
		}
		
		String endDate = MapUtils.getString(paramMap, "endDate");
		if(StringUtils.isNotBlank(endDate)) {
			sqlStr.append(" and SIGNIN_TIME <= to_date('" + endDate + "', 'yyyy-MM-dd HH24:mi:ss') ");
		}
		
		String staffName = MapUtils.getString(paramMap, "staffName");
		if(StringUtils.isNotBlank(staffName)) {
			sqlStr.append(" and STAFF_NAME like '%" + staffName + "%' ");
		}
		
		Integer signinType = MapUtils.getInteger(paramMap, "signinType");
		if (signinType != null) {
			sqlStr.append(" and SIGNIN_TYPE = " + signinType);
		}
		sqlStr.append(" order by SIGNIN_TIME desc ");
		
		System.out.println(sqlStr.toString());
		
		return sqlStr;
	}
	


	public String getTableName() {
		return TABLE_NAME;
	}

}
