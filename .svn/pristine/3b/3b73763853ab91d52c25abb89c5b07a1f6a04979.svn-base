package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class OuterSystemDAOImpl extends BaseDAOImpl implements OuterSystemDAO {
	
	private static final String TABLE_NAME = "OUTER_SYSTEM";
	
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "SYS_ID:sysId,SYS_CODE:sysCode,SYS_NAME:sysName,SYS_REGION_ID:sysRegionId,SYS_IP_ADDRESS:sysIpAddress,SYS_PORT:sysPort,SYS_APP_ADDRESS:sysAppAddress,SYS_ICON:sysIcon,COMPANY_ID:companyId,SYS_DESC:sysDesc,REMARK:remark,CREATE_DATE:createDate,STATE:state,SSO_TYPE_ID:ssoTypeId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("sysId", nextId);
		paramMap.put("state", "1");				//设为未删除状态
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SYS_ID:sysId,SYS_CODE:sysCode,SYS_NAME:sysName,SYS_REGION_ID:sysRegionId,SYS_IP_ADDRESS:sysIpAddress,SYS_PORT:sysPort,SYS_APP_ADDRESS:sysAppAddress,SYS_ICON:sysIcon,COMPANY_ID:companyId,SYS_DESC:sysDesc,REMARK:remark,CREATE_DATE:createDate,STATE:state,SSO_TYPE_ID:ssoTypeId";
		String wherePatternStr = "SYS_ID:sysId";
		paramMap.put("state", "1");				//设为未删除状态
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void delete(Map paramMap) throws DataAccessException {
		paramMap.put("state", "0");				//设为删除状态
		
		String sqlStr = "STATE:state";
		String wherePatternStr = "SYS_ID:sysId";    //逻辑删除
		
		dynamicUpdateByMap(paramMap, TABLE_NAME,sqlStr, wherePatternStr);
	}
	
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SYS_ID AS sysId,  SYS_CODE AS sysCode,  SYS_NAME AS sysName,  SYS_REGION_ID AS sysRegionId,  SYS_IP_ADDRESS AS sysIpAddress,  SYS_PORT AS sysPort,  SYS_APP_ADDRESS AS sysAppAddress,  SYS_ICON AS sysIcon,  COMPANY_ID AS companyId,  SYS_DESC AS sysDesc,  REMARK AS remark ,SSO_TYPE_ID as ssoTypeId FROM OUTER_SYSTEM WHERE SYS_ID=?";
		String wherePatternStr = "SYS_ID:sysId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SYS_ID AS sysId,  SYS_CODE AS sysCode,  SYS_NAME AS sysName,  SYS_REGION_ID AS sysRegionId,  SYS_IP_ADDRESS AS sysIpAddress,  SYS_PORT AS sysPort,  SYS_APP_ADDRESS AS sysAppAddress,  SYS_ICON AS sysIcon,  COMPANY_ID AS companyId,  SYS_DESC AS sysDesc,  REMARK AS remark ,SSO_TYPE_ID as ssoTypeId FROM OUTER_SYSTEM";
		String wherePatternStr = "STATE:state";
		paramMap.put("state", "1");		 //查询未删除的
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public List selAllSysOrgion(Map paramMap)throws DataAccessException{
		final String sqlStr ="SELECT SYS_REGION_ID AS sysRegionId,SYS_REGION_NAME AS sysRegionName,SYS_REGION_CODE AS sysRegionCode,SYS_REGION_DESC AS sysRegionDesc,REMARK AS remark  FROM SYS_REGION";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAllSSOType(Map paramMap)throws DataAccessException{
		final String sqlStr ="SELECT SSO_TYPE_ID AS ssoTypeId,SSO_TYPE_NAME AS ssoTypeName  FROM SSO_TYPE ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	//查询所有注销外系统的URL
	public List selAllLogoutUrl(Map paramMap)throws DataAccessException{
		String sqlStr ="select LOGOUT_URL as logoutUrl from OUTER_SYSTEM where state = 1";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	
	public Map queryOuterSystem(Map paramMap, int startIndex, int stepSize)throws DataAccessException {
		paramMap.put("forTree", "N");
		return this.populateQueryByMap(this.getSqlStr(paramMap), startIndex, stepSize);
	}
	
	public List queryOuterSystemForTree(Map paramMap)throws DataAccessException {
		paramMap.put("forTree", "Y");
		return this.dynamicQueryListByMap(this.getSqlStr(paramMap).toString(), null, null);
	}
	
	private StringBuffer getSqlStr(Map paramMap)throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		
		if("Y".equals(MapUtils.getString(paramMap, "forTree", null))) {	//用于树
			System.out.println("OuterSystemDAO树形查询----->");
			sqlStr.append(" SELECT SYS_ID AS id, SYS_NAME AS text, 1 as leaf, SYS_CODE AS sysCode, ");
			sqlStr.append(" SYS_NAME AS sysName, S.SYS_REGION_ID AS sysRegionId,S.SYS_REGION_NAME AS sysRegionName,SYS_IP_ADDRESS AS sysIpAddress," +
					" SYS_PORT AS sysPort,SYS_APP_ADDRESS AS sysAppAddress,SYS_ICON AS sysIcon," +
					" C.COMPANY_ID AS companyId,C.COMPANY_NAME AS companyName,SYS_DESC AS sysDesc,T.REMARK AS remark ,T.SSO_TYPE_ID as ssoTypeId,ST.SSO_TYPE_NAME as ssoTypeName ");

		} else {
			sqlStr.append(" SELECT SYS_ID AS sysId, SYS_NAME AS sysName, ");
			sqlStr.append(" SYS_CODE AS sysCode, S.SYS_REGION_ID AS sysRegionId,S.SYS_REGION_NAME AS sysRegionName,SYS_IP_ADDRESS AS sysIpAddress," +
					" SYS_PORT AS sysPort,SYS_APP_ADDRESS AS sysAppAddress,SYS_ICON AS sysIcon," +
					" C.COMPANY_ID AS companyId,C.COMPANY_NAME AS companyName,SYS_DESC AS sysDesc,T.REMARK AS remark ,T.SSO_TYPE_ID as ssoTypeId,ST.SSO_TYPE_NAME as ssoTypeName ");
		}
		sqlStr.append(" FROM OUTER_SYSTEM T,SYS_REGION S,SYS_COMPANY_INFO C,SSO_TYPE ST");
		sqlStr.append(" WHERE T.SYS_REGION_ID = S.SYS_REGION_ID AND C.COMPANY_ID = T.COMPANY_ID AND T.STATE=1 AND ST.SSO_TYPE_ID = T.SSO_TYPE_ID ");
		
		if(paramMap != null){
	        if(paramMap.containsKey("sysCode")){
	        	if(MapUtils.getObject(paramMap, "sysCode") != null &&
	             	   !MapUtils.getString(paramMap, "sysCode").trim().equals("")){
	        		sqlStr.append(" and T.SYS_CODE like '%")
	     		    		.append(MapUtils.getString(paramMap, "sysCode")).append("%'");
	             }
	        }

	        if(paramMap.containsKey("sysName")){
	        	if(MapUtils.getObject(paramMap, "sysName") != null &&
	             	   !MapUtils.getString(paramMap, "sysName").trim().equals("")){
	        		sqlStr.append(" and T.SYS_NAME like '%")
	     		    		.append(MapUtils.getString(paramMap, "sysName")).append("%'");
	             }
	        }
	        
	        if(paramMap.containsKey("sysRegionId")){
	        	if(MapUtils.getObject(paramMap, "sysRegionId") != null &&
	             	   !MapUtils.getString(paramMap, "sysRegionId").trim().equals("")){
	        		sqlStr.append(" and T.SYS_REGION_ID = '")
	     		    		.append(MapUtils.getString(paramMap, "sysRegionId")).append("'");
	             }
	        }			
		}
        
        sqlStr.append(" ORDER BY T.CREATE_DATE DESC");
        
        System.out.println("strSql: " + sqlStr.toString());
        
		return sqlStr;
	}	
	
	public List selSystemByRegionId(Map paramMap) throws DataAccessException {
		StringBuffer sbuf = new StringBuffer();
		sbuf.append("SELECT A.SYS_ID AS sysId,");
		sbuf.append("A.SYS_CODE AS sysCode,");
		sbuf.append("A.SYS_NAME AS sysName,");
		sbuf.append("A.SYS_REGION_ID AS sysRegionId,");
		sbuf.append("A.SYS_IP_ADDRESS AS sysIpAddress,");
		sbuf.append("A.SYS_PORT AS sysPort,");
		sbuf.append("A.SYS_APP_ADDRESS AS sysAppAddress,");
		sbuf.append("A.SYS_ICON AS sysIcon,");
		sbuf.append("A.COMPANY_ID AS companyId,");
		sbuf.append("A.SYS_DESC AS sysDesc,");
		sbuf.append("C.COMPANY_NAME AS companyName,");
		sbuf.append("C.MANAGER_NAME AS managerName,");
		sbuf.append("C.MANAGER_PHONE AS managerPhone,");
		sbuf.append("A.REMARK AS remark, ");
		sbuf.append("A.SSO_TYPE_ID AS ssoTypeId, ");
		sbuf.append("S.SSO_TYPE_NAME AS ssoTypeName ");
		sbuf.append("FROM OUTER_SYSTEM A JOIN SYS_REGION B ON A.SYS_REGION_ID = B.SYS_REGION_ID ");
		sbuf.append("JOIN SYS_COMPANY_INFO  C ON A.COMPANY_ID = C.COMPANY_ID ");
		sbuf.append("JOIN SSO_TYPE S ON S.SSO_TYPE_ID = A.SSO_TYPE_ID ");
		sbuf.append("WHERE A.STATE = 1 ");
		if(paramMap.containsKey("sysRegionId")&&MapUtils.getInteger(paramMap,"sysRegionId")!=null){
			sbuf.append(" AND A.SYS_REGION_ID = ").append(MapUtils.getInteger(paramMap,"sysRegionId"));
		}
		return dynamicQueryListByMap(sbuf.toString(), paramMap, null);
	}
	
	public static void main(String args[]){
		OuterSystemDAOImpl daoImpl = new OuterSystemDAOImpl();
		
		try {
			
			Map map = daoImpl.queryOuterSystem(null,1,5);
			
			System.out.println(map.get("totalSize"));
			
			System.out.println(map.get("totalRecords"));

			/*
			Map param = new HashMap();
			param.put("sysRegionId", new Long(1));
			System.out.println(daoImpl.selSystemByRegionId(param));
			
			Map param = new HashMap();
			param.put("sysCode", "1");
			param.put("sysDesc", "1");
			param.put("sysName", "1");
			param.put("companyId", "1");
			param.put("sysAppAddress", "1");
			param.put("sysRegionId", "5");
			param.put("sysIcon", "1");
			param.put("remark", "1");
			param.put("sysId", "");
			param.put("sysIpAddress", "1");
			
			daoImpl.insert(param);*/
			
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		
	}


	
	

	
}
