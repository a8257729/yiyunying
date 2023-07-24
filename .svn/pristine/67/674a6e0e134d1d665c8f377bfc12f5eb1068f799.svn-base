package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileApkFunctionDAOImpl extends BaseDAOImpl implements MobileApkFunctionDAO {
private static final String TABLE_NAME = "MOBILE_APK_FUNCTION";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "FUN_ID:funId,APK_CODE:apkCode,FUN_CODE:funCode,FUN_NAME:funName,FUN_CLASS:funClass,VER_ID:verId,FUNC_REG_STAFF_ID:funcRegStaffId,FUNC_STATUS:funcStatus,MUNE_ID:muneId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("funId", nextId);
		paramMap.put("funCode", "FUN_"+nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "FUN_ID:funId,FUN_NAME:funName,FUN_CLASS:funClass,FUNC_REG_STAFF_ID:funcRegStaffId,FUNC_STATUS:funcStatus,MUNE_ID:muneId";
		String wherePatternStr = "FUN_ID:funId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void updateMenuId(Map paramMap) throws DataAccessException {
		String updatePatternStr = "FUNC_REG_STAFF_ID:funcRegStaffId,FUNC_STATUS:funcStatus,MUNE_ID:muneId";
		String wherePatternStr = "FUN_ID:funId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void updateNull(Map paramMap) throws DataAccessException {
		String updatePatternStr = " UPDATE MOBILE_APK_FUNCTION SET MUNE_ID = NULL,FUNC_STATUS = NULL,FUNC_REG_STAFF_ID = NULL WHERE MUNE_ID = "+MapUtils.getLong(paramMap, "muneId");
		System.out.println("-----updateNull----"+updatePatternStr+"---------------------------");
		dynamicUpdateBySql(updatePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FUN_ID:funId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void deleteByApkCode(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APK_CODE:apkCode";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   FUN_ID AS funId,  APK_CODE AS apkCode,  FUN_CODE AS funCode,  FUN_NAME AS funName,  FUN_CLASS AS funClass,VER_ID AS verId FROM MOBILE_APK_FUNCTION ");
		if (MapUtils.getString(paramMap, "apkCode") != null && !MapUtils.getString(paramMap, "apkCode").equals("")) {
			sqlStr.append(" WHERE APK_CODE ='"+MapUtils.getString(paramMap, "apkCode")+"'");			
		}
		if (MapUtils.getString(paramMap, "verId") != null && !MapUtils.getString(paramMap, "verId").equals("")) {
			sqlStr.append(" AND VER_ID ='"+MapUtils.getString(paramMap, "verId")+"'");
		}
		//System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   FUN_ID AS funId,  APK_CODE AS apkCode,  FUN_CODE AS funCode,  FUN_NAME AS funName,  FUN_CLASS AS funClass FROM MOBILE_APK_FUNCTION";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	private static final String copyApkFuncWhereStr = "INSERT INTO MOBILE_APK_FUNCTION " +
			" SELECT SEQ_MOBILE_APK_FUNCTION.NEXTVAL,MAF.APK_CODE,MAF.FUN_CODE,FUN_NAME,FUN_CLASS,$VER_ID FROM MOBILE_APK_FUNCTION MAF where MAF.ver_id in" +
			" (SELECT MAX(A.VER_ID) FROM MOBILE_APK_VER A WHERE A.APK_CODE=$APK_CODE)" ;
	public void copyLastedFuncByApkCode(Map paramMap)throws DataAccessException{
		String apkCode=paramMap.get("apkCode").toString();
		String verId=paramMap.get("verId").toString();
		String sql=copyApkFuncWhereStr.replace("$VER_ID", verId).replace("$APK_CODE", "'"+apkCode+"'");
		dynamicUpdateBySql(sql);
		
	}
}
