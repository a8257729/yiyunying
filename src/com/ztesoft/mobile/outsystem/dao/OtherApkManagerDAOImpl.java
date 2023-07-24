package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class OtherApkManagerDAOImpl extends BaseDAOImpl implements
		OtherApkManagerDAO {
 
	private static final String TABLE_NAME = "OTHER_APK_MANAGE";
	public Map insert(Map paramMap) throws DataAccessException {
		String patternStr = "APK_ID:apkId,APK_CODE:apkCode,APK_VERSION_NO:apkVersionNo,APK_PACKAGE:apkPackage,APK_URL:apkUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments,SYS_CODE:sysCode,OPEN_CLASS:openClass,APK_NAME:apkName,APK_SIZE:apkSize,APK_ICON:apkIcon,APK_CONTENT:apkContent,APK_TYPE:apkType";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("apkId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return paramMap;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APK_ID:apkId,APK_VERSION_NO:apkVersionNo,APK_PACKAGE:apkPackage,APK_URL:apkUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments,SYS_CODE:sysCode,OPEN_CLASS:openClass,APK_NAME:apkName,APK_SIZE:apkSize,APK_ICON:apkIcon,APK_CONTENT:apkContent";
		String wherePatternStr = "APK_ID:apkId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void updateName(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APK_NAME:apkName";
		String wherePatternStr = "APK_ID:apkId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APK_ID:apkId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   APK_ID AS apkId,  APK_CODE AS apkCode,  APK_VERSION_NO AS apkVersionNo,  APK_PACKAGE AS apkPackage,  APK_URL AS apkUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  CREATE_DATE AS createDate, SYS_CODE AS sysCode, OPEN_CLASS AS openClass,APK_SIZE AS apkSize,APK_ICON AS apkIcon,APK_CONTENT AS apkContent FROM OTHER_APK_MANAGE  ");
		if (!MapUtils.getString(paramMap, "sysCode").equals("0")) {
			sqlStr.append(" WHERE SYS_CODE ='"+MapUtils.getString(paramMap, "sysCode")+"'");
		}
	//	System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	public Map selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT APK_NAME AS apkName,   APK_ID AS apkId,  APK_CODE AS apkCode,  APK_VERSION_NO AS apkVersionNo,  APK_PACKAGE AS apkPackage,  APK_URL AS apkUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  CREATE_DATE AS createDate, SYS_CODE AS sysCode, OPEN_CLASS AS openClass,APK_SIZE AS apkSize,APK_ICON AS apkIcon,APK_CONTENT AS apkContent FROM OTHER_APK_MANAGE   ");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}

		public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT APK_ID AS apkId FROM OTHER_APK_MANAGE "
			+ "WHERE APK_CODE='"
			+ MapUtils.getString(paramMap, "apkCode")
			+ "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
		
	public List selInfoBySysCode(Map paramMap) throws DataAccessException {
			final String sqlStr = "SELECT APK_NAME AS apkName,   APK_ID AS apkId,  APK_CODE AS apkCode,  APK_VERSION_NO AS apkVersionNo,  APK_PACKAGE AS apkPackage,  APK_URL AS apkUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  CREATE_DATE AS createDate, SYS_CODE AS sysCode, OPEN_CLASS AS openClass,APK_SIZE AS apkSize,APK_ICON AS apkIcon,APK_CONTENT AS apkContent  FROM OTHER_APK_MANAGE "
				+ "WHERE SYS_CODE='"
				+ MapUtils.getString(paramMap, "sysCode")
				+ "'";
			return dynamicQueryListByMap(sqlStr, paramMap, null);
	}	
	
	public Map selBySysCode(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT APK_NAME AS apkName,   APK_ID AS apkId,  APK_CODE AS apkCode,  APK_VERSION_NO AS apkVersionNo,  APK_PACKAGE AS apkPackage,  APK_URL AS apkUrl,  FORCE_UPDATE AS forceUpdate,  COMMENTS AS comments,  CREATE_DATE AS createDate, SYS_CODE AS sysCode, OPEN_CLASS AS openClass,APK_SIZE AS apkSize,APK_ICON AS apkIcon,APK_CONTENT AS apkContent  FROM OTHER_APK_MANAGE "
			+ "WHERE SYS_CODE='"
			+ MapUtils.getString(paramMap, "sysCode")
			+ "'";
		return populateQueryByMap(new StringBuffer(sqlStr),((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}

}
