package com.ztesoft.mobile.outsystem.dao;

import java.util.Map;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class OtherApkAuditInfoDAOImpl extends BaseDAOImpl implements OtherApkAuditInfoDAO {
	
	private static final String TABLE_NAME = "OTHER_APK_AUDIT_INFO";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "APK_AUDIT_INFO_ID:apkAuditInfoId,APK_ID:apkId,APK_NAME:apkName,CREATE_DATE:createDate,AUDIT_STAFF_ID:auditStaffId,AUDIT_DATE:auditDate,IS_PASS:isPass,AUDIT_COMMENT:auditComment";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("apkAuditInfoId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APK_AUDIT_INFO_ID:apkAuditInfoId,APK_ID:apkId,APK_NAME:apkName,CREATE_DATE:createDate,AUDIT_STAFF_ID:auditStaffId,AUDIT_DATE:auditDate,IS_PASS:isPass,AUDIT_COMMENT:auditComment";
		String wherePatternStr = "APK_AUDIT_INFO_ID:apkAuditInfoId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APK_AUDIT_INFO_ID:apkAuditInfoId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APK_AUDIT_INFO_ID AS apkAuditInfoId,  APK_ID AS apkId,  APK_NAME AS apkName,  CREATE_DATE AS createDate,  AUDIT_STAFF_ID AS auditStaffId,  AUDIT_DATE AS auditDate,  IS_PASS AS isPass,  AUDIT_COMMENT AS auditComment FROM OTHER_APK_AUDIT_INFO WHERE APK_AUDIT_INFO_ID=?";
		String wherePatternStr = "APK_AUDIT_INFO_ID:apkAuditInfoId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APK_AUDIT_INFO_ID AS apkAuditInfoId,  APK_ID AS apkId,  APK_NAME AS apkName,  CREATE_DATE AS createDate,  AUDIT_STAFF_ID AS auditStaffId,  AUDIT_DATE AS auditDate,  IS_PASS AS isPass,  AUDIT_COMMENT AS auditComment FROM OTHER_APK_AUDIT_INFO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}
}
