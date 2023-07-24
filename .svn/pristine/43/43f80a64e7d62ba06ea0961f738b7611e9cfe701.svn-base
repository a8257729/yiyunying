package com.ztesoft.mobile.systemMonitor.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileSessionRecordDAOImpl extends BaseDAOImpl implements MobileSessionRecordDAO {
	private static final String TABLE_NAME = "MOBILE_SESSION_RECORD";
	private static final String TABLE_NAME1 = "UOS_STAFF";
	private static final String TABLE_NAME2 = "MOBILE_USER_CONTROL";
	
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ID:id,SESSION_ID:sessionId,USERNAME:username,STAFF_ID:staffId,STAFF_NAME:staffName,CREATE_TIME:createTime,LAST_VISIT_TIME:lastVisitTime,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STATE:state,COMMENTS:comments";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("id", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ID:id,SESSION_ID:sessionId,USERNAME:username,STAFF_ID:staffId,STAFF_NAME:staffName,CREATE_TIME:createTime,LAST_VISIT_TIME:lastVisitTime,SERVICE_ID:serviceId,SERVICE_NAME:serviceName,STATE:state,COMMENTS:comments";
		String wherePatternStr = "ID:id";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void updateLastVisitTime(Map paramMap)throws DataAccessException{
		String updatePatternStr = "LAST_VISIT_TIME:lastVisitTime,SERVICE_ID:serviceId,SERVICE_NAME:serviceName";
		String wherePatternStr = "STAFF_ID:staffId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ID:id";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  SESSION_ID AS sessionId,  USERNAME AS username,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  CREATE_TIME AS createTime,  LAST_VISIT_TIME AS lastVisitTime,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STATE AS state,  COMMENTS AS comments FROM MOBILE_SESSION_RECORD WHERE STAFF_ID=?";
		String wherePatternStr = "STAFF_ID:staffId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  SESSION_ID AS sessionId,  USERNAME AS username,  STAFF_ID AS staffId,  STAFF_NAME AS staffName,  CREATE_TIME AS createTime,  LAST_VISIT_TIME AS lastVisitTime,  SERVICE_ID AS serviceId,  SERVICE_NAME AS serviceName,  STATE AS state,  COMMENTS AS comments FROM MOBILE_SESSION_RECORD";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public void updateConnectState(Map paramMap)throws DataAccessException{
		String updatePatternStr = "CONNECT_STATE:connectState";
		String wherePatternStr = "STAFF_ID:staffId";
		dynamicUpdateByMap(paramMap, TABLE_NAME1, updatePatternStr, wherePatternStr);
	}
	public void updateConnectLimit(Map paramMap)throws DataAccessException{
		
		String updatePatternStr = "CONNECT_LIMIT:connectLimit";
		String wherePatternStr = "USERNAME:username";
		dynamicUpdateByMap(paramMap, TABLE_NAME1, updatePatternStr, wherePatternStr);
	}
	public Map selByConn(Map paramMap) throws DataAccessException{
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT B.USERNAME as username,B.CONNECT_STATE as connectState,B.CONNECT_LIMIT as connectLimit,B.USER_CONN_PRIV as userConnPriv,B.USER_FLOW_LIMIT as userFlowLimit, ");
		sqlStr.append(" A.CREATE_TIME as createTime,A.LAST_VISIT_TIME as lastVisitTime,A.SESSION_ID as sessionId ");
		sqlStr.append(" FROM MOBILE_SESSION_RECORD A ,UOS_STAFF B ");
		sqlStr.append(" WHERE  A.STATE = 1  AND B.STATE =1 AND A.USERNAME = B.USERNAME ");
		
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND A.USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "connectState") != null && !MapUtils.getString(paramMap, "connectState").equals("") ) {
			sqlStr.append(" AND B.CONNECT_STATE =").append(MapUtils.getInteger(paramMap, "connectState"));
		}
		if (MapUtils.getString(paramMap, "connectLimit") != null && !MapUtils.getString(paramMap, "connectLimit").equals("") ) {
			sqlStr.append(" AND B.CONNECT_LIMIT =").append(MapUtils.getInteger(paramMap, "connectLimit"));
		}
		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND A.SERVICE_ID =").append(MapUtils.getLong(paramMap, "serviceId"));
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(A.CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(A.CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		sqlStr.append(" ORDER BY A.CREATE_TIME DESC ");
		//System.out.println("selByConn="+sqlStr.toString());

		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public Map selStaffInfo(Map paramMap) throws DataAccessException{
		final String sqlStr ="SELECT  STAFF_NAME AS staffName ,USERNAME AS username,CONNECT_STATE as connectState,CONNECT_LIMIT AS connectLimit,USER_CONN_PRIV as userConnPriv,USER_FLOW_LIMIT as userFlowLimit FROM  UOS_STAFF  WHERE STATE =1  AND STAFF_ID = ?";
		String wherePatternStr = "STAFF_ID:staffId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
		
	}
	
	public Map selStaffId(Map paramMap) throws DataAccessException{
		final String sqlStr ="SELECT  STAFF_ID as staffId  FROM  UOS_STAFF  WHERE STATE =1  AND USERNAME = ?";
		String wherePatternStr = "USERNAME:username";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
		
	}
	
	public Map selUserConnLimit(Map paramMap) throws DataAccessException{
		final String sqlStr ="SELECT USER_CONN_LIMIT as userConnLimit FROM  MOBILE_USER_CONTROL where rownum = 1  ";
		String wherePatternStr = null;
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public void updateUserConnPriv(Map paramMap)throws DataAccessException{
		String updatePatternStr = "USER_CONN_PRIV:userConnPriv";
		String wherePatternStr = "USERNAME:username";
		dynamicUpdateByMap(paramMap, TABLE_NAME1, updatePatternStr, wherePatternStr);
	}
	public void updateUserFlowLimit(Map paramMap)throws DataAccessException{
		String updatePatternStr = "USER_FLOW_LIMIT:userFlowLimit";
		String wherePatternStr = "USERNAME:username";
		dynamicUpdateByMap(paramMap, TABLE_NAME1, updatePatternStr, wherePatternStr);
		
	}
	
	public void updateUserConnLimit(Map paramMap)throws DataAccessException{
		String updatePatternStr = " UPDATE  MOBILE_USER_CONTROL SET USER_CONN_LIMIT =  " + MapUtils.getLong(paramMap, "userConnLimit");

		dynamicUpdateBySql(updatePatternStr);
	}
	
	public Map selOnline(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT COUNT(STAFF_ID) as icount   FROM UOS_STAFF WHERE CONNECT_STATE = 1 ";
		String wherePatternStr = null;

		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public Map qryTotalFlow(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT AA.SERVICE_ID AS serviceId,AA.USERNAME AS username,BB.SERVICE_NAME as serviceName , AA.TOTAL_IN_MESSAGE_SIZE as totalInMessageSize , ");
		sqlStr.append(" AA.TOTAL_OUT_MESSAGE_SIZE as totalOutMessageSize,BB.IN_MESSAGE_SIZE as inMessageSize,BB.OUT_MESSAGE_SIZE as outMessageSize FROM ");  		
		sqlStr.append(" (SELECT SERVICE_ID ,USERNAME, MAX(ID) AS ID, SUM(IN_MESSAGE_SIZE) AS TOTAL_IN_MESSAGE_SIZE,SUM (OUT_MESSAGE_SIZE) AS TOTAL_OUT_MESSAGE_SIZE "); 		
		sqlStr.append(" FROM  MOBILE_FUNC_CALL_RECORD WHERE STATE = 1 AND  IN_MESSAGE_SIZE IS NOT NULL AND OUT_MESSAGE_SIZE IS NOT NULL "); 

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "staffId") != null && !MapUtils.getString(paramMap, "staffId").equals("") ) {
			sqlStr.append(" AND STAFF_ID =").append(MapUtils.getString(paramMap, "staffId"));
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		if (MapUtils.getString(paramMap, "beginMonth") != null && !MapUtils.getString(paramMap, "beginMonth").equals("") ) {
            sqlStr.append("  AND to_char(CREATE_TIME,'YYYY-MM') = '").append(MapUtils.getString(paramMap, "beginMonth")).append("'");
        }

		
		sqlStr.append(" GROUP BY SERVICE_ID,USERNAME) AA ,MOBILE_FUNC_CALL_RECORD BB WHERE  AA.ID = BB.ID  ORDER BY AA.SERVICE_ID "); 
		
		//System.out.println("qryTotalFlow="+sqlStr.toString());
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, null);    	    	
		
	}
	
	public Map qryTotalFlowForMobile(Map paramMap) throws SQLException{
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT   AA.TOTAL_IN_MESSAGE_SIZE  as totalInMessageSize,   AA.TOTAL_OUT_MESSAGE_SIZE as totalOutMessageSize ");
		sqlStr.append(" FROM (SELECT SUM(IN_MESSAGE_SIZE) AS TOTAL_IN_MESSAGE_SIZE,   SUM(OUT_MESSAGE_SIZE) AS TOTAL_OUT_MESSAGE_SIZE ");  		
		sqlStr.append(" FROM MOBILE_FUNC_CALL_RECORD  WHERE STATE = 1      "); 		
		

		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND SERVICE_ID IN (").append(MapUtils.getLong(paramMap, "serviceId")).append(")");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "staffId") != null && !MapUtils.getString(paramMap, "staffId").equals("") ) {
			sqlStr.append(" AND STAFF_ID =").append(MapUtils.getString(paramMap, "staffId"));
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
        }
		if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
            sqlStr.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
        }
		
		if (MapUtils.getString(paramMap, "beginMonth") != null && !MapUtils.getString(paramMap, "beginMonth").equals("") ) {
            sqlStr.append("  AND to_char(CREATE_TIME,'YYYY-MM') = '").append(MapUtils.getString(paramMap, "beginMonth")).append("'");
        }

		
		sqlStr.append(" AND IN_MESSAGE_SIZE IS NOT NULL AND OUT_MESSAGE_SIZE IS NOT NULL ) AA "); 
		
		System.out.println("qryTotalFlowForMobile="+sqlStr.toString());
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, null);    	    
	}

	public Map getWkorderCodeBarFlag(Map paramMap) throws SQLException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select sf_save_barcode('"+MapUtils.getString(paramMap, "workOrderId")+"','"+MapUtils.getString(paramMap, "codeBar")+"') as bar_check_flag from dual");
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, null);
	}
}
