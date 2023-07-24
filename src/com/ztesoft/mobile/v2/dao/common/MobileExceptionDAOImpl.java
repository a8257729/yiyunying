package com.ztesoft.mobile.v2.dao.common;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileExceptionDAOImpl extends BaseDAOImpl implements MobileExceptionDAO {
	private static final String TABLE_NAME = "MOBILE_EXCEPTION_LOG";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "EXCEPTION_LOG_ID:exceptionLogId,REST_SERVICE_ID:restServiceId,EXCEPTION_NAME:exceptionName,EXCEPTION_TIME:exceptionTime,EXCEPTION_MSG:exceptionMsg";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("exceptionLogId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		
		//ИљОн
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "EXCEPTION_LOG_ID:exceptionLogId,REST_SERVICE_ID:restServiceId,EXCEPTION_NAME:exceptionName,EXCEPTION_TIME:exceptionTime,EXCEPTION_MSG:exceptionMsg";
		String wherePatternStr = "EXCEPTION_LOG_ID:exceptionLogId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "EXCEPTION_LOG_ID:exceptionLogId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	
}
