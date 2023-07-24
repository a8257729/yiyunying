package com.ztesoft.mobile.v2.dao.oaas;

import java.util.Map;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;

import org.apache.commons.collections.MapUtils;



public class StaffDAOImpl  extends BaseDAOImpl implements StaffDAO{
	public Map selByUserName(Map paramMap)throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.STAFF_ID AS staffId,");
    	sqlBuf.append(" A.STAFF_NAME AS staffName,"); 
    	sqlBuf.append(" A.USERNAME AS userName,");
    	sqlBuf.append(" A.PASSWORD AS password,");
    	sqlBuf.append(" A.EMAIL AS email,");
    	sqlBuf.append(" A.HOME_TEL AS homeTel,");
    	sqlBuf.append(" A.OFFICE_TEL AS officeTel,");
    	sqlBuf.append(" A.MOBILE_TEL AS mobile,");
    	sqlBuf.append(" A.ADDRESS1 AS address1,");
    	sqlBuf.append(" A.ADDRESS2 AS address2,");
    	sqlBuf.append(" A.VALID_COMM_MODE AS validCommMode,");
    	sqlBuf.append(" A.EFFECT_DATE AS effectDate,");
    	sqlBuf.append(" A.EXPIRE_DATE AS expireDate,");
    	sqlBuf.append(" A.COMMENTS AS comments,");
    	sqlBuf.append(" A.CONSTRUCT_ABILITY AS constructAbility,");
    	sqlBuf.append(" A.LOGON_NUMBER AS logonNumber,");
    	sqlBuf.append(" A.LDAP_ID AS ldapId,");
    	sqlBuf.append(" A.NATION_ID AS nationId,");
    	sqlBuf.append(" A.CERT_NO AS certNo");
    	sqlBuf.append(" FROM UOS_STAFF A WHERE 1=1 AND rownum =1  ");
    	
    	if (paramMap.get("userName") != null && !paramMap.get("userName").equals("")) {
    		sqlBuf.append(" AND A.USERNAME ='").append(paramMap.get("userName")).append("'");
		}
    	System.out.println("selByUserName="+sqlBuf.toString());
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);
	}
	public Map selByJobId(Map paramMap)throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT count(*) as icount FROM UOS_JOB WHERE STATE = '1'  ");
		if (paramMap.get("jobId") != null && !paramMap.get("jobId").equals("")) {
    		sqlBuf.append(" AND JOB_ID = ").append(paramMap.get("jobId"));
		}
		System.out.println("selByJobId="+sqlBuf.toString());
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);
	}
	public Map selByOrgId(Map paramMap)throws DataAccessException{
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT count(*) as orgcount FROM UOS_ORG WHERE STATE = '1'  ");
		if (paramMap.get("orgId") != null && !paramMap.get("orgId").equals("")) {
    		sqlBuf.append(" AND ORG_ID = ").append(paramMap.get("orgId"));
		}
		System.out.println("selByOrgId="+sqlBuf.toString());
		return dynamicQueryObjectByMap(sqlBuf.toString(), paramMap, null);
	}
}
