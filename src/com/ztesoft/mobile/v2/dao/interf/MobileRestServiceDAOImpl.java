package com.ztesoft.mobile.v2.dao.interf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileRestServiceDAOImpl extends BaseDAOImpl implements
		MobileRestServiceDAO {
	
	private Logger logger = Logger.getLogger(MobileRestServiceDAOImpl.class);
	
	private static final String TABLE_NAME = "MOBILE_REST_SERVICE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "REST_SERVICE_ID:@@SEQ,SERV_NAME:servName,SERV_ADDR:servAddr,SERV_STATUS:servStatus,SERV_TYPE:servType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("restServiceId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "REST_SERVICE_ID:restServiceId,SERV_NAME:servName,SERV_ADDR:servAddr,SERV_STATUS:servStatus,SERV_TYPE:servType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(new HashMap());
		sqlStr.append(" AND REST_SERVICE_ID = ? ");
		String wherePatternStr = "REST_SERVICE_ID:restServiceId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	private StringBuffer buildSql(Map paramMap) {
		
		StringBuffer sqlStr = new StringBuffer(" SELECT REST_SERVICE_ID AS restServiceId, ");
		sqlStr.append(" SERV_NAME AS servName, ");
		sqlStr.append(" SERV_ADDR AS servAddr, ");
		sqlStr.append(" SERV_STATUS AS servStatus, ");
		sqlStr.append(" BUILD_TIME AS buildTime, ");
		sqlStr.append(" UPDATE_TIME AS updateTime ");
		sqlStr.append(" FROM MOBILE_REST_SERVICE T WHERE STATE = 1 ");
		
		Long restServiceId = MapUtils.getLong(paramMap, "restServiceId");
		if(null != restServiceId) {
			sqlStr.append(" AND T.REST_SERVICE_ID = " + restServiceId);
		}
		
		String servNameEquals = MapUtils.getString(paramMap, "servNameEquals");
		if(StringUtils.isNotBlank(servNameEquals)) {
			sqlStr.append(" AND T.SERV_NAME = '" + servNameEquals + "' ");
		}
		
		String servNameLike = MapUtils.getString(paramMap, "servNameLike");
		if(StringUtils.isNotBlank(servNameLike)) {
			sqlStr.append(" AND T.SERV_NAME = '%" + servNameLike + "%' ");
		}
		
		String servAddrEquals = MapUtils.getString(paramMap, "servAddrEquals");
		if(StringUtils.isNotBlank(servAddrEquals)) {
			sqlStr.append(" AND T.SERV_ADDR = '" + servAddrEquals + "' ");
		}
		
		String servAddrLike = MapUtils.getString(paramMap, "servAddrLike");
		if(StringUtils.isNotBlank(servAddrLike)) {
			sqlStr.append(" AND T.SERV_ADDR = '%" + servAddrLike + "%' ");
		}
		
		Integer servStaus = MapUtils.getInteger(paramMap, "servNameLike");
		if(null != servStaus) {
			sqlStr.append(" AND T.SERV_STAUS = " + servStaus);
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("buildSql´òÓ¡µÄSQL£º" + sqlStr.toString());
		}
		
		return sqlStr;
	}
}
