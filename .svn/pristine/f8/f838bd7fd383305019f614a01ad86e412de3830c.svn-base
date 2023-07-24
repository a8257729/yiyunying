package com.ztesoft.mobile.v2.dao.interf;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;


public class MobileServInterfRelaDAOImpl extends BaseDAOImpl implements
		MobileServInterfRelaDAO {
	
	private static final String TABLE_NAME = "MOBILE_SERV_INTERF_RELA";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "SERV_INTERF_RELA_ID:@@SEQ,INTERFACE_ID:interfaceId,REST_SERVICE_ID:restServiceId,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("restServInterfRelaId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SERV_INTERF_RELA_ID:servInterfRelaId,INTERFACE_ID:interfaceId,REST_SERVICE_ID:restServiceId,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "SERV_INTERF_RELA_ID:servInterfRelaId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "SERV_INTERF_RELA_ID:servInterfRelaId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = buildSql(paramMap);
		sqlStr.append(" AND SERV_INTERF_RELA_ID = ? ");
		String wherePatternStr = "SERV_INTERF_RELA_ID:servInterfRelaId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = buildSql(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public StringBuffer buildSql(Map paramMap) {
		
		StringBuffer sqlStr = new StringBuffer(" SELECT SERV_INTERF_RELA_ID AS servInterfRelaId, ");
		sqlStr.append(" INTERFACE_ID AS interfaceId, ");
		sqlStr.append(" REST_SERVICE_ID AS restServiceId, ");
		sqlStr.append(" BUILD_TIME AS buildTime, ");
		sqlStr.append(" UPDATE_TIME AS updateTime ");
		sqlStr.append(" FROM MOBILE_REST_SERV_INTERF_RELA STATE = 1 ");
		
		Long interfaceId = MapUtils.getLong(paramMap, "interfaceId");
		if(null != interfaceId) {
			sqlStr.append(" AND INTERFACE_ID = " + interfaceId);
		}
		
		Long restServiceId = MapUtils.getLong(paramMap, "restServiceId");
		if(null != restServiceId) {
			sqlStr.append(" AND REST_SERVICE_ID = " + restServiceId);
		}
		
		return sqlStr;
	}
}
