package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileOtherSysManagerDAOImpl extends BaseDAOImpl implements MobileOtherSysManagerDAO {

	private static final String TABLE_NAME = "MOBILE_OTHERE_SYS_MANAGER";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "SYS_ADDRESS_ID:sysAddressId,SYSTEM_CODE:systemCode,INTERFACE_NAMESPACE:interfaceNamespace,INTERFACE_CODE:interfaceCode,METHOD_TYPE:methodType,METHOD_ADDRESS:methodAddress,INTERFACE_VERSION:interfaceVersion,APK_CODE:apkCode,INTERFACE_METHOD:interfaceMethod,DATA_TYPE:dataType";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("sysAddressId", nextId);
		paramMap.put("interfaceCode", "INTERFACE_"+nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SYS_ADDRESS_ID:sysAddressId,METHOD_TYPE:methodType,INTERFACE_NAMESPACE:interfaceNamespace,METHOD_ADDRESS:methodAddress,INTERFACE_VERSION:interfaceVersion,APK_CODE:apkCode,INTERFACE_METHOD:interfaceMethod,DATA_TYPE:dataType";
		String wherePatternStr = "SYS_ADDRESS_ID:sysAddressId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "SYS_ADDRESS_ID:sysAddressId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   SYS_ADDRESS_ID AS sysAddressId, SYSTEM_CODE AS systemCode, INTERFACE_NAMESPACE AS interfaceNamespace, INTERFACE_CODE AS interfaceCode,  METHOD_TYPE AS methodType,  METHOD_ADDRESS AS methodAddress,  INTERFACE_VERSION AS interfaceVersion,  APK_CODE AS apkCode, INTERFACE_METHOD AS interfaceMethod, DATA_TYPE AS dataType ");
		sqlStr.append(" FROM MOBILE_OTHERE_SYS_MANAGER ");
		if (!MapUtils.getString(paramMap, "systemCode").equals("0")) {
			sqlStr.append(" WHERE SYSTEM_CODE ='"+MapUtils.getString(paramMap, "systemCode")+"'");
		}		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SYS_ADDRESS_ID AS sysAddressId,  SYSTEM_CODE AS systemCode, INTERFACE_NAMESPACE AS interfaceNamespace, INTERFACE_CODE AS interfaceCode,  METHOD_TYPE AS methodType,  METHOD_ADDRESS AS methodAddress,  INTERFACE_VERSION AS interfaceVersion,  APK_CODE AS apkCode, INTERFACE_METHOD AS interfaceMethod, DATA_TYPE AS dataType  FROM MOBILE_OTHERE_SYS_MANAGER";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SYS_ADDRESS_ID AS sysAddressId FROM MOBILE_OTHERE_SYS_MANAGER " 
			+ "WHERE INTERFACE_CODE = '" + MapUtils.getString(paramMap, "interfaceCode") + "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}

}
