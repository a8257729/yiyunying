package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

public class MobileAppEvaluateDAOImpl extends BaseDAOImpl implements MobileAppEvaluateDAO {
	private static final String TABLE_NAME = "MOBILE_APP_EVALUATE";
	 
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "EVALUATE_ID:evaluateId,APP_ID:appId,EVALUATE_DETAIL:evaluateDetail,EVALUATE_STAFF_ID:evaluateStaffId,CREATE_TIME:createTime,STATE:state";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("evaluateId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "EVALUATE_ID:evaluateId,APP_ID:appId,EVALUATE_DETAIL:evaluateDetail,EVALUATE_STAFF_ID:evaluateStaffId,CREATE_TIME:createTime,STATE:state";
		String wherePatternStr = "EVALUATE_ID:evaluateId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "EVALUATE_ID:evaluateId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   EVALUATE_ID AS evaluateId,  APP_ID AS appId,  EVALUATE_DETAIL AS evaluateDetail,  EVALUATE_STAFF_ID AS evaluateStaffId,  CREATE_TIME AS createTime,  STATE AS state FROM MOBILE_APP_EVALUATE WHERE EVALUATE_ID=?";
		String wherePatternStr = "EVALUATE_ID:evaluateId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   EVALUATE_ID AS evaluateId,  APP_ID AS appId,  EVALUATE_DETAIL AS evaluateDetail,  EVALUATE_STAFF_ID AS evaluateStaffId,  CREATE_TIME AS createTime,  STATE AS state FROM MOBILE_APP_EVALUATE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}
	public Map selByConn(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT   EVALUATE_ID AS evaluateId,  APP_ID AS appId,  EVALUATE_DETAIL AS evaluateDetail,  EVALUATE_STAFF_ID AS evaluateStaffId,  CREATE_TIME AS createTime,  STATE AS state FROM MOBILE_APP_EVALUATE ");
		sqlBuf.append(" WHERE 1=1  ");
		if (MapUtils.getString(paramMap, "evaluateId") != null && !MapUtils.getString(paramMap, "evaluateId").equals("")) {
			sqlBuf.append(" AND EVALUATE_ID =").append(MapUtils.getLong(paramMap, "evaluateId"));			
		}
		
		sqlBuf.append(" ORDER BY EVALUATE_ID DESC ");
		
		System.out.println("selByConn="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
}
