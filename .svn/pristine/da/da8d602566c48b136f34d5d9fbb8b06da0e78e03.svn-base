package com.ztesoft.mobile.systemMonitor.dao;

import java.util.Map;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.collections.MapUtils;

public class MobileExceptionRecordDAOImpl extends BaseDAOImpl implements MobileExceptionRecordDAO {
	private static final String TABLE_NAME = "MOBILE_EXCEPTION_RECORD";
	
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ID:id,EXCEPTION_TYPE:exceptionType,EXCEPTION_TYPE_NAME:exceptionTypeName,EXCEPTION_CONTENT:exceptionContent,SYS_CODE:sysCode,SYS_NAME:sysName,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STATE:state,CREATE_TIME:createTime";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("id", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ID:id,EXCEPTION_TYPE:exceptionType,EXCEPTION_TYPE_NAME:exceptionTypeName,EXCEPTION_CONTENT:exceptionContent,SYS_CODE:sysCode,SYS_NAME:sysName,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STATE:state,CREATE_TIME:createTime";
		String wherePatternStr = "ID:id";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ID:id";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  EXCEPTION_TYPE AS exceptionType,  EXCEPTION_TYPE_NAME AS exceptionTypeName,  EXCEPTION_CONTENT AS exceptionContent,  SYS_CODE AS sysCode,  SYS_NAME AS sysName,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STATE AS state,  CREATE_TIME AS createTime FROM MOBILE_EXCEPTION_RECORD WHERE ID=?";
		String wherePatternStr = "ID:id";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  EXCEPTION_TYPE AS exceptionType,  EXCEPTION_TYPE_NAME AS exceptionTypeName,  EXCEPTION_CONTENT AS exceptionContent,  SYS_CODE AS sysCode,  SYS_NAME AS sysName,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STATE AS state,  CREATE_TIME AS createTime FROM MOBILE_EXCEPTION_RECORD";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}
	public Map selByConn(Map paramMap)throws DataAccessException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT  A.ID AS id,  A.EXCEPTION_TYPE AS exceptionType,  A.EXCEPTION_TYPE_NAME AS exceptionTypeName,  A.EXCEPTION_CONTENT AS exceptionContent,  A.SYS_CODE AS sysCode,  A.SYS_NAME AS sysName,  A.SERVICE_ID AS serviceId,  A.SERVICE_NAME AS serviceName,  A.STATE AS state,  A.CREATE_TIME AS createTime,B.HANDLE_SUGGESTION_NAME AS handleSuggestionName FROM MOBILE_EXCEPTION_RECORD A ");
		
		sqlStr.append(" LEFT JOIN MOBILE_EXCEPTION_HANDLE B ON  A.EXCEPTION_TYPE = B.EXCEPTION_TYPE  WHERE  A.STATE = 1  ");
		
		if (MapUtils.getString(paramMap, "sysCode") != null && !MapUtils.getString(paramMap, "sysCode").equals("") ) {
			sqlStr.append(" AND SYS_CODE ='").append(MapUtils.getString(paramMap, "sysCode")).append("'");
		}
		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID =").append(MapUtils.getInteger(paramMap, "serviceId"));
		}
		
		sqlStr.append(" ORDER BY CREATE_TIME DESC ");
		//System.out.println("selByConn="+sqlStr.toString());

		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
		
	}
}
