package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class RestServiceDAOImpl extends BaseDAOImpl implements RestServiceDAO {
	private static final String TABLE_NAME = "REST_SERVICE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "REST_SERVICE_ID:@@SEQ,REST_SERVICE_PATH:restServicePath,REST_SERVICE_NAME:restServiceName,REST_SERVICE_CLASS:restServiceClass,REST_SERVICE_PARAMS:restServiceParams,STATE:state,STATE_DATE:stateDate,REST_SERVICE_CODE:restServiceCode,REST_SERVICE_TYPE:restServiceType,STAFF_ID:staffId,MEMO:memo";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("restServiceId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "REST_SERVICE_ID:restServiceId,REST_SERVICE_PATH:restServicePath,REST_SERVICE_NAME:restServiceName,REST_SERVICE_CLASS:restServiceClass,REST_SERVICE_PARAMS:restServiceParams,STATE:state,STATE_DATE:stateDate,REST_SERVICE_CODE:restServiceCode,REST_SERVICE_TYPE:restServiceType,STAFF_ID:staffId,MEMO:memo";
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer("SELECT   REST_SERVICE_ID AS restServiceId,  ");
			sqlStr.append(" REST_SERVICE_PATH AS restServicePath,  ");
			sqlStr.append(" REST_SERVICE_NAME AS restServiceName,  ");
			sqlStr.append(" REST_SERVICE_CLASS AS restServiceClass, ");
			sqlStr.append(" REST_SERVICE_PARAMS AS restServiceParams, ");
			sqlStr.append(" STATE AS state,  STATE_DATE AS stateDate,  ");
			sqlStr.append(" REST_SERVICE_TYPE AS restServiceType, ");
			sqlStr.append(" REST_SERVICE_CODE AS restServiceCode,  STAFF_ID AS staffId,  ");
			sqlStr.append(" MEMO AS memo FROM REST_SERVICE WHERE STATE=1 ");
			
			String restServiceClass = MapUtils.getString(paramMap, "restServiceClass");
			if(null != restServiceClass && !"".equals(restServiceClass)) {
				sqlStr.append(" AND REST_SERVICE_CLASS = '" + restServiceClass + "' ");
			}

			String restServiceCode = MapUtils.getString(paramMap, "restServiceCode");
			if(null != restServiceCode && !"".equals(restServiceCode)) {
				sqlStr.append(" AND REST_SERVICE_CODE = '" + restServiceCode + "' ");
			}
			
			return sqlStr;
	}


	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer("SELECT   REST_SERVICE_ID AS restServiceId,  ");
		sqlStr.append(" REST_SERVICE_PATH AS restServicePath,  ");
		sqlStr.append(" REST_SERVICE_NAME AS restServiceName,  ");
		sqlStr.append(" REST_SERVICE_CLASS AS restServiceClass, ");
		sqlStr.append(" REST_SERVICE_PARAMS AS restServiceParams, ");
		sqlStr.append(" STATE AS state,  STATE_DATE AS stateDate,  ");
		sqlStr.append(" REST_SERVICE_TYPE AS restServiceType, ");
		sqlStr.append(" REST_SERVICE_CODE AS restServiceCode,  STAFF_ID AS staffId,  ");
		sqlStr.append(" MEMO AS memo FROM REST_SERVICE WHERE REST_SERVICE_ID=? ");
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

/*	public Map selByCond(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryObjectByMap(sqlStr.toString(), null, null);
	}	*/
	
	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public Map selByPagin(Map paramMap) throws DataAccessException {
		final StringBuffer sqlStr = this.getSqlStr(paramMap);
		//int pageIndex = MapUtils.getInteger(paramMap, "pageIndex") == null ? 1 : MapUtils.getInteger(paramMap, "pageIndex").intValue();
		//int pageSize = MapUtils.getInteger(paramMap, "pageSize") == null ? 10 : MapUtils.getInteger(paramMap, "pageSize").intValue();
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}
	

}