package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobilePhoneVersionDAOImpl extends BaseDAOImpl implements
		MobilePhoneVersionDAO {

	private static final String TABLE_NAME = "MOBILE_PHONE_VERSION";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "VERSION_ID:versionId,VERSION_NO:versionNo,VERSION_URL:versionUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments,PIC_VERSION_NO:picVersionNo,PIC_VERSION_URL:picVersionUrl";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("versionId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "VERSION_ID:versionId,VERSION_URL:versionUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments,PIC_VERSION_NO:picVersionNo,PIC_VERSION_URL:picVersionUrl";
		String wherePatternStr = "VERSION_ID:versionId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "VERSION_ID:versionId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   VERSION_ID AS versionId,  VERSION_NO AS versionNo,  VERSION_URL AS versionUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  PUBLIC_DATE AS publicDate,  PIC_VERSION_NO AS picVersionNo,  PIC_VERSION_URL AS picVersionUrl FROM MOBILE_PHONE_VERSION WHERE VERSION_ID=?";
		String wherePatternStr = "VERSION_ID:versionId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   VERSION_ID AS versionId,  VERSION_NO AS versionNo,  VERSION_URL AS versionUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  PUBLIC_DATE AS publicDate,  PIC_VERSION_NO AS picVersionNo,  PIC_VERSION_URL AS picVersionUrl FROM MOBILE_PHONE_VERSION");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}


	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT VERSION_ID AS versionId FROM MOBILE_PHONE_VERSION "
			+ "WHERE VERSION_NO='"
			+ MapUtils.getString(paramMap, "versionNo")
			+ "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}

}
