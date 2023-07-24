package com.ztesoft.mobile.outsystem.dao;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileInterfaceMappingDAOImpl extends BaseDAOImpl implements
		MobileInterfaceMappingDAO {
	
	private static final String TABLE_NAME = "MOBILE_INTERFACE_MAPPING";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "INTERFACE_MAPPING_ID:@@SEQ,INTERFACE_MANAGER_ID:interfaceManagerId,MAPPING_CODE:mappingCode,MAPPING_NAME:mappingName,MAPPING_METHOD:mappingMethod,MAPPING_TYPE:mappingType,MAPPING_PARAMS:mappingParams,MEMO:memo,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "INTERFACE_MAPPING_ID:interfaceMappingId,INTERFACE_MANAGER_ID:interfaceManagerId,MAPPING_CODE:mappingCode,MAPPING_NAME:mappingName,MAPPING_METHOD:mappingMethod,MAPPING_TYPE:mappingType,MAPPING_PARAMS:mappingParams,MEMO:memo,STATE_DATE:stateDate";
		String wherePatternStr = "INTERFACE_MAPPING_ID:interfaceMappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "INTERFACE_MAPPING_ID:interfaceMappingId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer(" SELECT   INTERFACE_MAPPING_ID AS interfaceMappingId,  INTERFACE_MANAGER_ID AS interfaceManagerId,  MAPPING_CODE AS mappingCode,  MAPPING_NAME AS mappingName,  MAPPING_METHOD AS mappingMethod,  MAPPING_TYPE AS mappingType,  MAPPING_PARAMS AS mappingParams,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_INTERFACE_MAPPING M WHERE STATE=1 ");

		//TODO ²¹³äÌõ¼þ
		Long interfaceManagerId = MapUtils.getLong(paramMap, "interfaceManagerId", null);
		if(null != interfaceManagerId) {
			sqlStr.append(" AND M.INTERFACE_MANAGER_ID = " + interfaceManagerId);
		}
		//
		System.out.println("query Sql: "+ sqlStr);
		
		return sqlStr;
	}
	
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(new HashMap());
		sqlStr.append(" WHERE INTERFACE_MAPPING_ID=? ");
		String wherePatternStr = "INTERFACE_MAPPING_ID:interfaceMappingId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public Map selByPagin(Map paramMap)  throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}	
	
	public String getTableName() {
		return TABLE_NAME;
	}
}