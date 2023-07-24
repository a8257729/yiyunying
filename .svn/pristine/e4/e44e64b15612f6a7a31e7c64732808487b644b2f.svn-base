package com.ztesoft.mobile.pn.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.pn.xmpp.session.ClientSession;
import com.ztesoft.mobile.pn.xmpp.session.SessionManager;

public class MobilePnUserDAOImpl extends BaseDAOImpl implements MobilePnUserDAO {

	//private final Log logger = LogFactory.getLog(getClass());

	private static final String TABLE_NAME = "MOBILE_PN_USER";

	public void insert(Map paramMap) throws Exception {
		String patternStr = "PN_USER_ID:@@SEQ,PN_USER_NAME:pnUserName,PN_USER_PASSWORD:pnUserPassword,PN_CREATE_DATE:pnCreateDate,PN_UPDATE_DATE:pnUpdateDate,PN_PLATFORM_TYPE:pnPlatformType,PN_PHONE_NO:pnPhoneNo,PN_ONLINE:pnOnline,PN_STAFF_ID:pnStaffId,PN_STAFF_NAME:pnStaffName,PN_ACCOUNT:pnAccount,PN_IMSI:pnImsi";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("pnUserId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

/*	public void insertBatch(List paramMapList) throws Exception {
		String patternStr = "PN_USER_ID:pnUserId,PN_USER_NAME:pnUserName,PN_USER_PASSWORD:pnUserPassword,PN_CREATE_DATE:pnCreateDate,PN_UPDATE_DATE:pnUpdateDate,PN_PLATFORM_TYPE:pnPlatformType,PN_PHONE_NO:pnPhoneNo,PN_ONLINE:pnOnline";
		long nextId = getPKFromMem(TABLE_NAME, 9, paramMapList.size())
				.longValue();
		for (int i = 0; i < paramMapList.size(); i++) {
			((Map) paramMapList.get(i)).put("pnUserId", new Long(nextId));
			nextId--;
		}
		dynamicInsertBatchByMap(paramMapList, TABLE_NAME, patternStr);
	}*/

	public void update(Map paramMap) throws Exception {
		String updatePatternStr = "PN_USER_ID:pnUserId,PN_USER_NAME:pnUserName,PN_USER_PASSWORD:pnUserPassword,PN_CREATE_DATE:pnCreateDate,PN_UPDATE_DATE:pnUpdateDate,PN_PLATFORM_TYPE:pnPlatformType,PN_PHONE_NO:pnPhoneNo,PN_ONLINE:pnOnline,PN_STAFF_ID:pnStaffId,PN_STAFF_NAME:pnStaffName,PN_ACCOUNT:pnAccount,PN_IMSI:pnImsi";
		String wherePatternStr = "PN_USER_ID:pnUserId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}
	
	public void updateOnline(Map paramMap) throws Exception {
		String updatePatternStr = "PN_ONLINE:pnOnline";
		String wherePatternStr = "PN_USER_NAME:pnUserName";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}
/*	public void updateBatch(List paramMapList) throws Exception {
		String updatePatternStr = "PN_USER_ID:pnUserId,PN_USER_NAME:pnUserName,PN_USER_PASSWORD:pnUserPassword,PN_CREATE_DATE:pnCreateDate,PN_UPDATE_DATE:pnUpdateDate,PN_PLATFORM_TYPE:pnPlatformType,PN_PHONE_NO:pnPhoneNo,PN_ONLINE:pnOnline";
		String wherePatternStr = "PN_USER_ID:pnUserId";
		dynamicUpdateBatchByMap(paramMapList, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}*/

	public void delete(Map paramMap) throws Exception {
		String wherePatternStr = "PN_USER_ID:pnUserId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws Exception {
		final String sqlStr = "SELECT   PN_USER_ID AS pnUserId,  PN_USER_NAME AS pnUserName,  PN_USER_PASSWORD AS pnUserPassword,  PN_CREATE_DATE AS pnCreateDate,  PN_UPDATE_DATE AS pnUpdateDate,  PN_PLATFORM_TYPE AS pnPlatformType,  PN_PHONE_NO AS pnPhoneNo,  PN_ONLINE AS pnOnline, PN_STAFF_ID AS pnStaffId,PN_STAFF_NAME AS pnStaffName,PN_ACCOUNT AS pnAccount,PN_IMSI AS pnImsi FROM MOBILE_PN_USER A WHERE PN_USER_ID=?";
		String wherePatternStr = "PN_USER_ID:pnUserId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap){
		StringBuffer sqlStr = new StringBuffer(" SELECT  PN_USER_ID AS pnUserId,  PN_USER_NAME AS pnUserName,  PN_USER_PASSWORD AS pnUserPassword,  PN_CREATE_DATE AS pnCreateDate,  PN_UPDATE_DATE AS pnUpdateDate,  PN_PLATFORM_TYPE AS pnPlatformType,  PN_PHONE_NO AS pnPhoneNo,  PN_ONLINE AS pnOnline, PN_STAFF_ID AS pnStaffId,PN_STAFF_NAME AS pnStaffName,PN_ACCOUNT AS pnAccount,PN_IMSI AS pnImsi FROM MOBILE_PN_USER A WHERE 1=1");


		if(paramMap.containsKey("pnUserNameEquals")){
        	if(StringUtils.isNotBlank(MapUtils.getString(paramMap, "pnUserNameEquals"))){
        		sqlStr.append(" AND A.PN_USER_NAME ='").append(MapUtils.getString(paramMap, "pnUserNameEquals")+"'");
        	}
        }

		if(paramMap.containsKey("pnUserNameLike")){
        	if(StringUtils.isNotBlank(MapUtils.getString(paramMap, "pnUserNameLike"))){
        		sqlStr.append(" AND A.PN_USER_NAME LIKE '%").append(MapUtils.getString(paramMap, "pnUserNameLike")+"%'");
        	}
        }
		
		Long pnStaffId = MapUtils.getLong(paramMap, "pnStaffId", null);
		if(null != pnStaffId) {
			sqlStr.append(" AND A.PN_STAFF_ID = ").append(pnStaffId);
		}
		
		Integer pnOnline = MapUtils.getInteger(paramMap, "pnOnline");
		if(null != pnStaffId) {
			sqlStr.append(" AND A.PN_ONLINE = ").append(pnOnline);
		}
		
		String qOline = MapUtils.getString(paramMap, "Q_ONLINE", "N");
		if("Y".equals(qOline)) {
			String onlineIds = this.formatOnlineIds();
			if(StringUtils.isNotBlank(onlineIds)) {	//在线逻辑
				sqlStr.append(" AND PN_USER_NAME IN ( " + onlineIds + ") ");
			} 
		}

		sqlStr.append(" ORDER BY A.PN_CREATE_DATE DESC ");

		System.out.println("querySql: " + sqlStr.toString());

		return sqlStr;
	}

	public List selAll(Map paramMap) throws Exception {
		String sqlStr = this.getSqlStr(paramMap).toString();
		return dynamicQueryListByMap(sqlStr, null, null);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Map selByPagin(Map paramMap) throws Exception {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

	public Map getOneByCons(Map paramMap) throws Exception {
		Map rtMap = Collections.EMPTY_MAP;
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		rtMap = this.dynamicQueryObjectByMap(sqlStr.toString(), null, null);
		System.out.println("查询到的结果集：" + rtMap);
		return rtMap;
	}
	
	private String formatOnlineIds() {
		StringBuffer sb = new StringBuffer();
		SessionManager sessionManager = SessionManager.getInstance();
		
		if(null != sessionManager.getSessions() && 0 != sessionManager.getSessions().size()) {
			ClientSession[] clientSessions = new ClientSession[ sessionManager.getSessions().size()];
			//转为数组
			sessionManager.getSessions().toArray(clientSessions);
			sb.append("'" + clientSessions[0].getAddress().getNode() + "' ");
	        for(int i=1; i<clientSessions.length; i++) {
	        	sb.append(", '" + clientSessions[i]  + "' ");
	        }
		}
		return sb.toString();
	}
	
	public List selAllOnline(Map paramMap) throws Exception {
		List list = Collections.EMPTY_LIST;
		SessionManager sessionManager = SessionManager.getInstance();
		
		if(0 != sessionManager.getSessions().size()) {
			paramMap.put("Q_ONLINE", "Y");
			StringBuffer sqlStr = this.getSqlStr(paramMap);
			list = this.dynamicQueryListByMap(sqlStr.toString(), null, null);
		}
		return list;
	}
}