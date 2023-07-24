package com.ztesoft.mobile.outsystem.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileInterfaceManagerDAOImpl extends BaseDAOImpl implements
MobileInterfaceManagerDAO {

	private static final String TABLE_NAME = "MOBILE_INTEFACE_MANAGER";
	private static final String TABLE_NAME2 = "REST_SERVICE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "MAPPING_ID:mappingId,SYS_ADDRESS_ID:sysAddressId,MAPPING_CODE:mappingCode,MAPPING_NAME:mappingName,MAPPING_METHOD:mappingMethod,INTEFACE_NAME:intefaceName,INTEFACE_METHOD:intefaceMethod,INTEFACE_PARAMS:intefaceParams,COMMENTS:comments";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("mappingId", nextId);
		paramMap.put("mappingCode", "SERVICE_CODE_"+nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		
		String patternStrRest = "REST_SERVICE_ID:restServiceId,REST_SERVICE_PATH:restServicePath,REST_SERVICE_NAME:restServiceName,REST_SERVICE_CLASS:restServiceClass,REST_SERVICE_PARAMS:restServiceParams,STATE:state,STATE_DATE:stateDate,REST_SERVICE_CODE:restServiceCode,STAFF_ID:staffId,MEMO:memo,REST_SERVICE_TYPE:restServiceType,SYS_ADDRESS_ID:sysAddressId";
		Map mapRest = new HashMap();
		//modify by guo.jinjun at 2012-05-08 10:19:53
		//添加了REST_SERVICE的序列..原先是用nextId的,即MOBILE_INTEFACE_MANAGER的序列
		Long nextId2 = getPKFromMem(TABLE_NAME2, 9);
		mapRest.put("restServiceId", nextId2);
		
		mapRest.put("restServiceCode", MapUtils.getString(paramMap, "mappingCode"));
		mapRest.put("restServiceName", MapUtils.getString(paramMap, "intefaceName")+"服务");
		mapRest.put("sysAddressId", MapUtils.getString(paramMap, "sysAddressId"));
		mapRest.put("restServiceType", MapUtils.getString(paramMap, "restServiceType"));
		if(MapUtils.getString(paramMap, "restServiceType").equals("2")){
			mapRest.put("restServicePath", "appserv");
			mapRest.put("restServiceClass", "com.ztesoft.mobile.service.handler.SelInterfHandler");
		} else if(MapUtils.getString(paramMap, "restServiceType").equals("3")){
			mapRest.put("restServicePath", "directserv");
			mapRest.put("restServiceClass", "com.ztesoft.mobile.service.handler.DirectInterfaceHandler");			
		} else {
			mapRest.put("restServicePath", "-1");
			mapRest.put("restServiceClass", "-1");
		}
		mapRest.put("restServiceParams", "");
		mapRest.put("stateDate", new Date());
		mapRest.put("state", "1");
		mapRest.put("staffId", "-1");
		mapRest.put("memo", "");
		System.out.println("===[debug]=insert=======: "+mapRest.toString());
		dynamicInsertByMap(mapRest, TABLE_NAME2, patternStrRest);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "MAPPING_ID:mappingId,MAPPING_NAME:mappingName,MAPPING_METHOD:mappingMethod,INTEFACE_NAME:intefaceName,INTEFACE_METHOD:intefaceMethod,INTEFACE_PARAMS:intefaceParams,COMMENTS:comments";
		String wherePatternStr = "MAPPING_ID:mappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
		
		String patternStrRest = "REST_SERVICE_PATH:restServicePath,REST_SERVICE_NAME:restServiceName,REST_SERVICE_CLASS:restServiceClass,REST_SERVICE_PARAMS:restServiceParams,STATE:state,STATE_DATE:stateDate,STAFF_ID:staffId,MEMO:memo,REST_SERVICE_TYPE:restServiceType,SYS_ADDRESS_ID:sysAddressId";
		Map mapRest = new HashMap();
//		mapRest.put("restServiceId", MapUtils.getString(paramMap, "mappingId"));
		mapRest.put("restServiceCode", MapUtils.getString(paramMap, "restServiceCode"));
		mapRest.put("restServiceName", MapUtils.getString(paramMap, "intefaceName")+"服务");
		mapRest.put("sysAddressId", MapUtils.getString(paramMap, "sysAddressId"));
		mapRest.put("restServiceType", MapUtils.getString(paramMap, "restServiceType"));
		if(MapUtils.getString(paramMap, "restServiceType").equals("2")){
			mapRest.put("restServicePath", "appserv");
			mapRest.put("restServiceClass", "com.ztesoft.mobile.service.handler.SelInterfHandler");
		} else if(MapUtils.getString(paramMap, "restServiceType").equals("3")){
			mapRest.put("restServicePath", "directserv");
			mapRest.put("restServiceClass", "com.ztesoft.mobile.service.handler.DirectInterfaceHandler");			
		} else {
			mapRest.put("restServicePath", "-1");
			mapRest.put("restServiceClass", "-1");
		}
		mapRest.put("restServiceParams", "");
		mapRest.put("stateDate", new Date());
		mapRest.put("state", "1");
		mapRest.put("staffId", "-1");
		mapRest.put("memo", "");
		System.out.println("===[debug]=update=======: "+mapRest.toString());
		dynamicUpdateByMap(mapRest, TABLE_NAME2, patternStrRest, "REST_SERVICE_CODE:restServiceCode");
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "MAPPING_ID:mappingId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   im.MAPPING_ID AS mappingId, im.SYS_ADDRESS_ID AS sysAddressId,  im.MAPPING_CODE AS mappingCode,  im.MAPPING_NAME AS mappingName,  im.MAPPING_METHOD AS mappingMethod,  im.INTEFACE_NAME AS intefaceName,  im.INTEFACE_METHOD AS intefaceMethod,  im.INTEFACE_PARAMS AS intefaceParams,  im.COMMENTS AS comments,  rs.REST_SERVICE_TYPE AS restServiceType ");
		sqlStr.append(" FROM MOBILE_INTEFACE_MANAGER im,REST_SERVICE rs");
		if (!MapUtils.getString(paramMap, "sysAddressId").equals("0")) {
			sqlStr.append(" WHERE im.SYS_ADDRESS_ID ="+MapUtils.getString(paramMap, "sysAddressId"));
		}		
		sqlStr.append(" AND im.MAPPING_CODE=rs.REST_SERVICE_CODE  order by im.MAPPING_ID asc ");
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   MAPPING_ID AS mappingId,  SYS_ADDRESS_ID AS sysAddressId,  MAPPING_CODE AS mappingCode,  MAPPING_NAME AS mappingName,  MAPPING_METHOD AS mappingMethod,  INTEFACE_NAME AS intefaceName,  INTEFACE_METHOD AS intefaceMethod,  INTEFACE_PARAMS AS intefaceParams,  COMMENTS AS comments FROM MOBILE_INTEFACE_MANAGER";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}


	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT MAPPING_ID AS mappingId FROM MOBILE_INTEFACE_MANAGER "
			+ "WHERE MAPPING_CODE='"
			+ MapUtils.getString(paramMap, "mappingCode")
			+ "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr =  new StringBuffer("SELECT   INTERFACE_MANAGER_ID AS interfaceManagerId,  SYS_ID AS sysId,  SYSTEM_CODE AS systemCode,  INTERFACE_TYPE AS interfaceType,  INTERFACE_NAME AS interfaceName,  INTERFACE_URL AS interfaceUrl,  INTERFACE_VERSION AS interfaceVersion,  INTERFACE_CODE AS interfaceCode,  INTERFACE_STATUS AS interfaceStatus,  INTERFACE_NAMESPACE AS interfaceNamespace,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate FROM MOBILE_INTERFACE_MANAGER I WHERE STATE=1 ");

		Long sysId = MapUtils.getLong(paramMap, "sysId", null);
		if(null != sysId) {
			sqlStr.append(" AND I.SYS_ID =" + sysId);
		}

		String systemCode = MapUtils.getString(paramMap, "systemCode");
		if(StringUtils.isNotBlank(systemCode)) {
			sqlStr.append(" AND I.SYSTEM_CODE ='" + systemCode +"' ");
		}		
		System.out.println("query Sql: " + sqlStr);
		return sqlStr;
	}
	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

	public Map selInterfaceInfo(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuffer = new StringBuffer(" SELECT I.MAPPING_CODE    AS mappingCode, ");
		sqlBuffer.append(" I.MAPPING_NAME    AS mappingName, I.INTEFACE_METHOD AS intefaceMethodParam, ")
		.append(" I.Inteface_Params AS interfaceParams, S.Method_Address as methodAddress, ")
		.append(" I.mapping_method as interfParamsName,I.mapping_name as interfMethodType, ")
		.append("  S.Interface_Namespace as interfaceNamespace,S.Interface_Version as interfaceVersion,")
		.append("  S.Data_Type as dataType,S.Interface_Method as interfaceMethod ")
		.append("  FROM MOBILE_INTEFACE_MANAGER I, MOBILE_OTHERE_SYS_MANAGER S ")
		.append("  WHERE I.sys_address_id = S.sys_address_id ");

		String mappingCode = MapUtils.getString(paramMap, "mappingCode", null);
		if(StringUtils.isNotBlank(mappingCode)) {
			sqlBuffer.append(" AND I.MAPPING_CODE =  '" + mappingCode + "' ");
		}

		return this.dynamicQueryObjectByMap(sqlBuffer.toString(), null, null);
	}

}
