package com.ztesoft.mobile.system.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;


public class OuterSysLoginLogDAOImpl extends BaseDAOImpl implements
		OuterSysLoginLogDAO {
	private static final String TABLE_NAME = "OUTER_SYS_LOGIN_LOG";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "OUTER_SYS_LOGIN_LOG_ID:outerSysLoginLogId,SESSION_ID:sessionId,STAFF_ID:staffId,STAFF_NAME:staffName,ORG_ID:orgId,ORG_NAME:orgName,OUTER_SYS_ID:outerSysId,OUTER_SYS_NAME:outerSysName,LOGIN_DATE:loginDate,MENU_NAME:menuName,LOGOUT_DATE:logoutDate,STATE:state";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("outerSysLoginLogId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "OUTER_SYS_LOGIN_LOG_ID:outerSysLoginLogId,SESSION_ID:sessionId,STAFF_ID:staffId,STAFF_NAME:staffName,ORG_ID:orgId,ORG_NAME:orgName,OUTER_SYS_ID:outerSysId,OUTER_SYS_NAME:outerSysName,LOGIN_DATE:loginDate,MENU_NAME:menuName,LOGOUT_DATE:logoutDate,STATE:state";
		String wherePatternStr = "OUTER_SYS_LOGIN_LOG_ID:outerSysLoginLogId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "OUTER_SYS_LOGIN_LOG_ID:outerSysLoginLogId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   OUTER_SYS_LOGIN_LOG_ID AS outerSysLoginLogId,  SESSION_ID AS sessionId,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  ORG_ID AS orgId,  ORG_NAME AS orgName,  OUTER_SYS_ID AS outerSysId,  OUTER_SYS_NAME AS outerSysName,  LOGIN_DATE AS loginDate,  MENU_NAME AS menuName,  LOGOUT_DATE AS logoutDate,  STATE AS state FROM OUTER_SYS_LOGIN_LOG WHERE OUTER_SYS_LOGIN_LOG_ID=?";
		String wherePatternStr = "OUTER_SYS_LOGIN_LOG_ID:outerSysLoginLogId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   OUTER_SYS_LOGIN_LOG_ID AS outerSysLoginLogId,  SESSION_ID AS sessionId,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  ORG_ID AS orgId,  ORG_NAME AS orgName,  OUTER_SYS_ID AS outerSysId,  OUTER_SYS_NAME AS outerSysName,  LOGIN_DATE AS loginDate,  MENU_NAME AS menuName,  LOGOUT_DATE AS logoutDate,  STATE AS state FROM OUTER_SYS_LOGIN_LOG";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	/**
	 * 根据会话ID，访问系统ID 判断是否已存在记录
	 * */
	public int selLogCount(Map paramMap) throws DataAccessException {
		StringBuffer sbf = new StringBuffer();
		sbf.append("SELECT COUNT(*) AS logCount FROM OUTER_SYS_LOGIN_LOG ");
		sbf.append(" WHERE SESSION_ID=");
		sbf.append(paramMap.get("sessionId"));
		sbf.append(" AND OUTER_SYS_ID=");
		sbf.append(paramMap.get("outerSysId"));
		
		System.out.println("根据会话ID，访问系统ID 判断是否已存在记录:"+sbf.toString());
		Map resultMap = dynamicQueryObjectByMap(sbf.toString(), paramMap, null);
		return Integer.parseInt(resultMap.get("logCount").toString());
	}
	
	public void updateLogoutDate(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SESSION_ID:sessionId,LOGOUT_DATE:logoutDate,STATE:state";
		String wherePatternStr = "SESSION_ID:sessionId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public static void main(String[] args) throws DataAccessException {
		OuterSysLoginLogDAOImpl test = new OuterSysLoginLogDAOImpl();
		Map insertMap = new HashMap();
		insertMap.put("sessionId", 111);
		insertMap.put("staffId", 11);
		insertMap.put("staffName","dfdf");
		insertMap.put("orgId", new Long(12));
		insertMap.put("orgName", "dfdf");
		insertMap.put("outerSysId", 1);
		insertMap.put("outerSysName", "dfdaf");
		insertMap.put("loginDate", new Date());
		insertMap.put("menuName", "menuName");
		insertMap.put("state", 1);
		test.insert(insertMap);
		// test.update(paramMap);
		// test.delete(paramMap);
		// System.out.println(test.selById(paramMap));
	}
}
