package com.ztesoft.mobile.outsystem.dao;

import java.util.Map;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.collections.MapUtils;

public class OtherApkVersionInfoDAOImpl extends BaseDAOImpl implements OtherApkVersionInfoDAO {
	private static final String TABLE_NAME = "OTHER_APK_VERSION_INFO";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "APK_VERSION_INFO_ID:apkVersionInfoId,APK_ID:apkId,APK_NAME:apkName,APK_VERSION_NO:apkVersionNo,APK_URL:apkUrl,CREATE_DATE:createDate,APK_SIZE:apkSize,APK_CONTENT:apkContent,APK_STATE:apkState";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("apkVersionInfoId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APK_VERSION_INFO_ID:apkVersionInfoId,APK_ID:apkId,APK_NAME:apkName,APK_VERSION_NO:apkVersionNo,APK_URL:apkUrl,CREATE_DATE:createDate,APK_SIZE:apkSize,APK_CONTENT:apkContent,APK_STATE:apkState";
		String wherePatternStr = "APK_VERSION_INFO_ID:apkVersionInfoId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APK_VERSION_INFO_ID:apkVersionInfoId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APK_VERSION_INFO_ID AS apkVersionInfoId,  APK_ID AS apkId,  APK_NAME AS apkName,  APK_VERSION_NO AS apkVersionNo,  APK_URL AS apkUrl,  CREATE_DATE AS createDate,  APK_SIZE AS apkSize,  APK_CONTENT AS apkContent,  APK_STATE AS apkState FROM OTHER_APK_VERSION_INFO WHERE APK_VERSION_INFO_ID=?";
		String wherePatternStr = "APK_VERSION_INFO_ID:apkVersionInfoId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APK_VERSION_INFO_ID AS apkVersionInfoId,  APK_ID AS apkId,  APK_NAME AS apkName,  APK_VERSION_NO AS apkVersionNo,  APK_URL AS apkUrl,  CREATE_DATE AS createDate,  APK_SIZE AS apkSize,  APK_CONTENT AS apkContent,  APK_STATE AS apkState FROM OTHER_APK_VERSION_INFO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public Map selByApkId(Map paramMap)throws DataAccessException{

			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append(" SELECT   APK_VERSION_INFO_ID AS apkVersionInfoId,  APK_ID AS apkId,  APK_NAME AS apkName,  APK_VERSION_NO AS apkVersionNo,  APK_URL AS apkUrl,  CREATE_DATE AS createDate,  APK_SIZE AS apkSize,  APK_CONTENT AS apkContent,  APK_STATE AS apkState FROM OTHER_APK_VERSION_INFO WHERE 1=1 ");			
			if(MapUtils.getString(paramMap, "apkId")!= null && !MapUtils.getString(paramMap, "apkId").equals("")){
				sqlBuf.append(" AND APK_ID = ").append(MapUtils.getLong(paramMap, "apkId"));
				
			}						
			sqlBuf.append(" order by CREATE_DATE asc ");			
			//System.out.println("selByApkId="+sqlBuf.toString());
			
			return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
					
	}
	
	
	public String getTableName() {
		return TABLE_NAME;
	}
}
