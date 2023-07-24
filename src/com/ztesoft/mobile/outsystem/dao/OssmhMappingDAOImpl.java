package com.ztesoft.mobile.outsystem.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl; 
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.JsonUtil;
public class OssmhMappingDAOImpl extends BaseDAOImpl implements OssmhMappingDAO {
private static final String TABLE_NAME = "OSSMH_MAPPING";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "OSSMH_MAPPING_ID:ossmhMappingId,STAFF_ID:staffId,STAFF_NAME:staffName,JOB_ID:jobId,JOB_NAME:jobName,USERNAME:username,ORG_ID:orgId,ORG_NAME:orgName,ORG_PATH_NAME:orgPathName,STAFF_MAP_BASE_ID:staffMapBaseId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("ossmhMappingId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "OSSMH_MAPPING_ID:ossmhMappingId,STAFF_ID:staffId,STAFF_NAME:staffName,JOB_ID:jobId,JOB_NAME:jobName,STAFF_MAP_BASE_ID:staffMapBaseId";
		String wherePatternStr = "OSSMH_MAPPING_ID:ossmhMappingId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "STAFF_ID:staffId,&&:JOB_ID:jobId,&&:ORG_ID:orgId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   OSSMH_MAPPING_ID AS ossmhMappingId,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  JOB_ID AS jobId,  JOB_NAME AS jobName,  STAFF_MAP_BASE_ID AS staffMapBaseId FROM OSSMH_MAPPING WHERE OSSMH_MAPPING_ID=?";
		String wherePatternStr = "OSSMH_MAPPING_ID:ossmhMappingId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   OSSMH_MAPPING_ID AS ossmhMappingId,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  JOB_ID AS jobId,  JOB_NAME AS jobName,  STAFF_MAP_BASE_ID AS staffMapBaseId FROM OSSMH_MAPPING";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAllSystems(Map paramMap) throws DataAccessException {
		String sqlStr ="select sys_id as sysId,sys_code as sysCode,sys_name as sysName,sys_icon as sysIcon,sys_app_address as sysAppAddress from OUTER_SYSTEM where state = 1 ";
		
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	//根据用户名和角色名查询所有的系统映射用户信息
	public List selThirdUserByStaffJobId(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("select c.sys_id as sysId,");
		sqlBuf.append(" c.sys_code as sysCode, ");
		sqlBuf.append(" c.sys_name as sysName, ");
		sqlBuf.append(" c.SYS_IP_ADDRESS as sysIpAddress, ");
		sqlBuf.append(" c.sys_port as sysPort, ");
		sqlBuf.append(" d.sso_type_id as ssoTypeId, ");
		sqlBuf.append(" b.STAFF_ID as thirdStaffId,b.USERNAME as thirdUsername, b.staff_name as thirdStaffName, ");
		sqlBuf.append(" b.ROLE_ID as thirdRoleId , b.ROLE_NAME as thirdRoleName, ");
		sqlBuf.append(" b.org_path_name as thirdOrgPathName,b.staff_map_base_id as staffMapBaseId ");
		sqlBuf.append(" from OSSMH_MAPPING a ");
		sqlBuf.append(" join STAFF_MAP_BASE b on a.STAFF_MAP_BASE_ID = b.STAFF_MAP_BASE_ID ");
		sqlBuf.append(" join OUTER_SYSTEM c on b.sys_id = c.sys_id ");
		sqlBuf.append(" join SSO_TYPE d on d.SSO_TYPE_ID = C.SSO_TYPE_ID ");
		sqlBuf.append(" where c.STATE = 1 ");
		if(paramMap.containsKey("staffId")&&paramMap.get("staffId")!=null){
			sqlBuf.append(" and a.staff_id = ").append(paramMap.get("staffId"));
		}
		if(paramMap.containsKey("jobId")&&paramMap.get("jobId")!=null){
			sqlBuf.append(" and a.job_id = ").append(paramMap.get("jobId"));
		}
		if(paramMap.containsKey("sysIpAddress")&&paramMap.get("sysIpAddress")!=null){
			sqlBuf.append(" and c.SYS_IP_ADDRESS = ").append(paramMap.get("sysIpAddress"));
		}	
		
		return dynamicQueryListByMap(sqlBuf.toString(), paramMap, null);
	}
	public Map selThirdUserByStaffJobIdPaging(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("select c.sys_id as sysId,");
		sqlBuf.append(" c.sys_code as sysCode, ");
		sqlBuf.append(" c.sys_name as sysName, ");
		sqlBuf.append(" c.SYS_IP_ADDRESS as sysIpAddress, ");
		sqlBuf.append(" c.sys_port as sysPort, ");
		sqlBuf.append(" b.STAFF_NAME as thirdStaffName , ");
		sqlBuf.append(" b.STAFF_ID as thirdStaffId,b.USERNAME as thirdUsername, ");
		sqlBuf.append(" b.ROLE_ID as thirdRoleId , b.ROLE_NAME as thirdRoleName, ");
		sqlBuf.append(" b.org_path_name as thirdOrgPathName,b.staff_map_base_id as staffMapBaseId ");
		sqlBuf.append(" from OSSMH_MAPPING a ");
		sqlBuf.append(" join STAFF_MAP_BASE b on a.STAFF_MAP_BASE_ID = b.STAFF_MAP_BASE_ID ");
		sqlBuf.append(" join OUTER_SYSTEM c on b.sys_id = c.sys_id ");
		sqlBuf.append(" where c.STATE = 1 ");
		if(paramMap.containsKey("staffId")&&paramMap.get("staffId")!=null){
			sqlBuf.append(" and a.staff_id = ").append(paramMap.get("staffId"));
		}
		if(paramMap.containsKey("jobId")&&paramMap.get("jobId")!=null){
			sqlBuf.append(" and a.job_id = ").append(paramMap.get("jobId"));
		}
			
		
		return populateQueryByMap(sqlBuf, Integer.parseInt(paramMap.get("start").toString()), Integer.parseInt(paramMap.get("limit").toString()));
	}
	public Map selThirdUser(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("select b.staff_map_base_id as staffMapBaseId,");
		sqlBuf.append(" c.sys_id as sysId,c.sys_code as sysCode,c.sys_name as sysName, ");
		sqlBuf.append(" c.SYS_IP_ADDRESS as sysIpAddress,c.sys_port as sysPort,b.STAFF_NAME as staffName ,");
		sqlBuf.append(" b.STAFF_ID as staffId,b.USERNAME as username,b.ROLE_ID as roleId , b.ROLE_NAME as roleName, ");
		sqlBuf.append(" b.org_path_name as orgPathName ");
		sqlBuf.append(" from STAFF_MAP_BASE b  join OUTER_SYSTEM c on b.sys_id = c.sys_id ");
		sqlBuf.append(" where c.STATE = 1 ");
		
		if(paramMap.containsKey("sysId")&&paramMap.get("sysId")!=null&&!paramMap.get("sysId").toString().equals("")){
			sqlBuf.append(" and c.sys_id = ").append(paramMap.get("sysId"));
		}
		if(paramMap.containsKey("staffName")&&paramMap.get("staffName")!=null&&!paramMap.get("staffName").toString().equals("")){
			sqlBuf.append(" and b.staff_name like '%").append(paramMap.get("staffName")).append("%'");
		}
		if(paramMap.containsKey("userName")&&paramMap.get("userName")!=null&&!paramMap.get("userName").toString().equals("")){
			sqlBuf.append(" and b.username like '%").append(paramMap.get("userName")).append("%'");
		}
		System.out.println(sqlBuf);
		return populateQueryByMap(sqlBuf, Integer.parseInt(paramMap.get("start").toString()), Integer.parseInt(paramMap.get("limit").toString()));
	}
	//根据系统ID查询工单链接
	public static void main(String args[]){
		OssmhMappingDAOImpl ossmhMappingDAOImpl = new OssmhMappingDAOImpl();
		Map paramMap = new HashMap();
		paramMap.put("start", new Integer(0));
		paramMap.put("limit", new Integer(5));
	//\\	paramMap.put("staffName", new Long(34357));
		paramMap.put("userName", "e");
		try {
			//System.out.println(ossmhMappingDAOImpl.selAllSystems(paramMap));
			System.out.println(JsonUtil.getBasetJsonData(ossmhMappingDAOImpl.selAllSystems(paramMap)));
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
