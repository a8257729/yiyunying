package com.ztesoft.mobile.v2.dao.interf;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileBusiSysDAOImpl extends BaseDAOImpl implements
		MobileBusiSysDAO {
	private static final String TABLE_NAME = "MOBILE_BUSI_SYS";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "BUSI_SYS_ID:@@SEQ,SYS_PROVIDER:sysProvider,SYS_ADDR:sysAddr,SYS_NAME:sysName,SYS_FIELD_TYPE:sysFieldType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("busiSysId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "BUSI_SYS_ID:busiSysId,SYS_PROVIDER:sysProvider,SYS_ADDR:sysAddr,SYS_NAME:sysName,SYS_FIELD_TYPE:sysFieldType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "BUSI_SYS_ID:busiSysId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "BUSI_SYS_ID:busiSysId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   BUSI_SYS_ID AS busiSysId,  SYS_PROVIDER AS sysProvider,  SYS_ADDR AS sysAddr, SYS_NAME AS sysName, SYS_FIELD_TYPE AS sysFieldType,  to_char(BUILD_TIME,'yyyy-mm-dd hh24:mi:ss') AS buildTime,  to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') AS updateTime,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_BUSI_SYS WHERE BUSI_SYS_ID=?";
		String wherePatternStr = "BUSI_SYS_ID:busiSysId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   BUSI_SYS_ID AS busiSysId,  SYS_PROVIDER AS sysProvider,  SYS_ADDR AS sysAddr, SYS_NAME AS sysName, SYS_FIELD_TYPE AS sysFieldType,  to_char(BUILD_TIME,'yyyy-mm-dd hh24:mi:ss') AS buildTime,  to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') AS updateTime,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_BUSI_SYS WHERE STATE=1 ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selByConn(Map paramMap) throws DataAccessException{
	    StringBuffer sqlStr = new StringBuffer("SELECT   BUSI_SYS_ID AS busiSysId,  SYS_PROVIDER AS sysProvider,  SYS_ADDR AS sysAddr, SYS_NAME AS sysName, SYS_FIELD_TYPE AS sysFieldType,  to_char(BUILD_TIME,'yyyy-mm-dd hh24:mi:ss') AS buildTime,  to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') AS updateTime, STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_BUSI_SYS ");
		sqlStr.append(" WHERE STATE = 1 ");
				
		System.out.println("selByConn="+sqlStr.toString());
		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}

}
