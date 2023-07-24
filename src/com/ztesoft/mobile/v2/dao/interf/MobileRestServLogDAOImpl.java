package com.ztesoft.mobile.v2.dao.interf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileRestServLogDAOImpl extends BaseDAOImpl implements
		MobileRestServLogDAO {
	
	private static final Logger logger = Logger.getLogger(MobileRestServLogDAOImpl.class);
	
	private static final String TABLE_NAME = "MOBILE_REST_SERV_LOG";

	public void insert(Map paramMap) throws DataAccessException {
//		String patternStr = "REST_SERV_LOG_ID:@@SEQ,REST_SERVICE_ID:restServiceId,STAFF_ID:staffId,SERV_LOG_TYPE:servLogType,IN_CONTENT:inContent,OUT_CONTENT:outContent,IN_TIMESTAMP:inTimestamp,OUT_TIMESTAMP:outTimestamp,IN_SIZE:inSize,OUT_SIZE:outSize";
		String patternStr = "REST_SERV_LOG_ID:@@SEQ,REST_SERVICE_ID:restServiceId,STAFF_ID:staffId,SERV_LOG_TYPE:servLogType,IN_CONTENT:inContent,OUT_CONTENT:outContent,IN_TIMESTAMP:inTimestamp,OUT_TIMESTAMP:outTimestamp,IN_SIZE:inSize,OUT_SIZE:outSize,WORK_ORDER_ID:workOrderId,STYLE:style,RESULT_CODE:resultCode";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("restServLogId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "REST_SERV_LOG_ID:@@SEQ,REST_SERVICE_ID:restServiceId,STAFF_ID:staffId,SERV_LOG_TYPE:servLogType,IN_CONTENT:inContent,OUT_CONTENT:outContent,IN_TIMESTAMP:inTimestamp,OUT_TIMESTAMP:outTimestamp,IN_SIZE:inSize,OUT_SIZE:outSize";		
		String wherePatternStr = "REST_SERV_LOG_ID:restServLogId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "REST_SERV_LOG_ID:restServLogId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(new HashMap()); 	
		sqlStr.append(" AND REST_SERV_LOG_ID = ? ");
		String wherePatternStr = "REST_SERV_LOG_ID:restServLogId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap); 
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}
	
	private StringBuffer buildSql(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer("SELECT REST_SERV_LOG_ID AS restServLogId, "); 
		sqlStr.append(" REST_SERVICE_ID AS restServiceId, ");
		sqlStr.append(" STAFF_ID AS staffId, ");
		sqlStr.append(" SERV_LOG_TYPE AS servLogType, ");
		sqlStr.append(" IN_CONTENT AS inContent, ");
		sqlStr.append(" IN_SIZE AS inSize, ");
		sqlStr.append(" IN_TIMESTAMP AS inTimestamp, ");
		sqlStr.append(" OUT_CONTENT AS outContent, ");
		sqlStr.append(" OUT_SIZE:outSize, ");
		sqlStr.append(" OUT_TIMESTAMP AS outTimestamp ");
		sqlStr.append(" FROM MOBILE_REST_SERV_LOG T WHERE 1=1 ");
		
		Long restServiceId = MapUtils.getLong(paramMap, "restServiceId");
		if(null != restServiceId) {
			sqlStr.append(" AND REST_SERVICE_ID = " + restServiceId);
		}
		
		Long staffId = MapUtils.getLong(paramMap, "staffId");
		if(null != staffId) {
			sqlStr.append(" AND STAFF_ID = " + staffId);
		}
		
		return sqlStr;
	}
	
	public List getStaffFlowCount(Long staffId) throws DataAccessException {
		
		StringBuffer sqlStr = new StringBuffer(" select staffId, sumInSize, sumOutSize ");
		sqlStr.append(" from ( select l.STAFF_ID AS staffId, ");
		sqlStr.append(" SUM(l.IN_SIZE) AS sumInSize, ");
		sqlStr.append(" SUM(l.OUT_SIZE) AS sumOutSize ");
		sqlStr.append(" from mobile_rest_serv_log l ");
		sqlStr.append(" WHERE 1=1 ");
		
		if(staffId != null) {
			sqlStr.append(" AND l.staff_id = " + staffId);
		}
		
		sqlStr.append("GROUP BY l.STAFF_ID)");
		
		if(logger.isDebugEnabled()) {
			logger.debug("getStaffFlowCount的打印SQL是：" + sqlStr.toString());
		}
		
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}
	
	/**
	 * 废弃，完全有问题！！！
	 */
	public List selByStaff(Map paramMap) throws DataAccessException
	{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select restServiceId, sumInSize, sumOutSize, staffId, username from "); 
		sqlStr.append(" ( select  mrsl.REST_SERVICE_ID  AS restServiceId, "); 
		sqlStr.append("        TRIM(to_char(SUM (mrsl.IN_SIZE),'99999999999999.99'))       AS sumInSize, "); 
		sqlStr.append("        TRIM(to_char(SUM (mrsl.OUT_SIZE),'99999999999999.99'))       AS sumOutSize,  "); 
		sqlStr.append("        mrsl.STAFF_ID         AS staffId, "); 
		sqlStr.append("        us.username           AS username "); 
		sqlStr.append("   from mobile_rest_serv_log mrsl, uos_staff us "); 
		sqlStr.append("  where mrsl.staff_id = us.staff_id "); 

		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND us.staff_name ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND us.username ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		
		Long vStaffId = MapUtils.getLong(paramMap, "staffId");
		if(vStaffId != null) {
			sqlStr.append(" AND mrsl.staff_id = " + vStaffId);
		}
		
		sqlStr.append(" GROUP BY mrsl.REST_SERVICE_ID, mrsl.STAFF_ID, us.username, mrs.serv_name ) "); 

		return dynamicQueryListByMap(sqlStr.toString(), paramMap, null);
	}

	/** 
	 * REST服务调用统计
	 * @return
	 * @throws DataAccessException
	 */
	public List getRestServStatList() throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" select servName, invokeNum, costTime from ( ");
		sqlStr.append(" select s.rest_service_id AS restServiceId, "); 
		sqlStr.append(" s.serv_name AS servName, ");
		sqlStr.append(" count(s.rest_service_id) invokeNum, ");
		sqlStr.append(" sum(l.out_timestamp - l.in_timestamp) as costTime ");
		sqlStr.append(" from mobile_rest_serv_log l, mobile_rest_service s ");
		sqlStr.append(" where (l.rest_service_id is not null and l.rest_service_id != -1) ");
		sqlStr.append(" and (l.staff_id is not null and l.staff_id != -1) ");
		sqlStr.append(" and l.rest_service_id = s.rest_service_id ");
		sqlStr.append(" group by s.serv_name, s.rest_service_id ");
		sqlStr.append(" order by s.rest_service_id asc ");
		sqlStr.append(" ) ");
		
		if(logger.isDebugEnabled()) {
			logger.debug("getRestServStatList打印SQL：" + sqlStr.toString());
		}
		
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public List getStaffFlowStatList() throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" select staffId, staffName, totalFlow ");
		sqlStr.append(" from (select a.staff_id as staffId, b.staff_name as staffName, "); 
		sqlStr.append(" sum(IN_SIZE + OUT_SIZE) as totalFlow");
		sqlStr.append(" from mobile_rest_serv_log a, UOS_STAFF b ");
		sqlStr.append(" where a.staff_id = b.staff_id and a.staff_id != -1 ");
		sqlStr.append(" group by a.staff_id, b.staff_name) ");
		
		if(logger.isDebugEnabled()) {
			logger.debug("getStaffFlowStatList打印SQL：" + sqlStr.toString());
		}
		
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public List getRestServFlowStatList() throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(" select restServiceId, servName, totalFlow from ( ");
		sqlStr.append(" select a.rest_service_id as restServiceId, "); 
		sqlStr.append(" b.serv_name as servName, sum(IN_SIZE + OUT_SIZE) as totalFlow ");
		sqlStr.append(" from mobile_rest_serv_log a, mobile_rest_service b ");
		sqlStr.append(" where a.rest_service_id = b.rest_service_id ");
		sqlStr.append(" group by a.rest_service_id, b.serv_name order by a.rest_service_id asc) ");
		
		if(logger.isDebugEnabled()) {
			logger.debug("getRestServFlowStatList打印SQL：" + sqlStr.toString());
		}
		
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}
}
