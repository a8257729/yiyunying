package com.ztesoft.mobile.v2.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileSyncQueueDAOImpl extends BaseDAOImpl implements
		MobileSyncQueueDAO {
	
	private static final String TABLE_NAME = "MOBILE_SYNC_QUEUE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "SYNC_QUEUE_ID:@@SEQ,SYNC_TARGET:syncTarget,SYNC_DATA:syncData,OPT_TYPE:optType,OPT_TIMESTAMP:optTimestamp";		
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("syncQueueId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "SYNC_QUEUE_ID:syncQueueId,SYNC_TARGET:syncTarget,SYNC_DATA:syncData,OPT_TYPE:optType,OPT_TIMESTAMP:optTimestamp";		
		String wherePatternStr = "SYNC_QUEUE_ID:syncQueueId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "SYNC_QUEUE_ID:syncQueueId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   SYNC_QUEUE_IDSYNC_TARGETSYNC_DATAOPT_TYPEOPT_TIMESTAMP AS syncQueueIdsyncTargetsyncDataoptTypeoptTimestamp FROM MOBILE_SYNC_QUEUE WHERE SYNC_QUEUE_ID=?";
		String wherePatternStr = "SYNC_QUEUE_ID:syncQueueId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   SYNC_QUEUE_IDSYNC_TARGETSYNC_DATAOPT_TYPEOPT_TIMESTAMP AS syncQueueIdsyncTargetsyncDataoptTypeoptTimestamp FROM MOBILE_SYNC_QUEUE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	private StringBuffer buildSql(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer(" SELECT SYNC_QUEUE_ID AS syncQueueId, ");
		sqlStr.append(" SYNC_TARGET AS syncTarget, ");
		sqlStr.append(" SYNC_DATA AS syncData, ");
		sqlStr.append(" OPT_TYPE AS optType, ");
		sqlStr.append(" OPT_TIMESTAMP AS optTimestamp ");
		sqlStr.append(" FROM MOBILE_SYNC_QUEUE WHERE 1=1 ");
		
		String optType = MapUtils.getString(paramMap, "optType");
		if(StringUtils.isNotBlank(optType)) {
			sqlStr.append(" AND OPT_TYPE = '" + optType + "' ");
		}
		
		Integer syncTarget = MapUtils.getInteger(paramMap, "syncTarget");
		if(null != syncTarget) {
			sqlStr.append(" AND SYNC_TARGET = " + syncTarget + " ");
		}
		
		//∆Ù º
		String sOptTimestamp = MapUtils.getString(paramMap, "sOptTimestamp");
		if(StringUtils.isNotBlank(optType)) {
			long lSOptTimestamp = Long.valueOf(sOptTimestamp);
			sqlStr.append(" AND OPT_TIMESTAMP => " + lSOptTimestamp);
		}
		
		//∆Ù º
		String eOptTimestamp = MapUtils.getString(paramMap, "eOptTimestamp");
		if(StringUtils.isNotBlank(optType)) {
			long lEOptTimestamp = Long.valueOf(eOptTimestamp);
			sqlStr.append(" AND OPT_TIMESTAMP <= " + lEOptTimestamp);
		}
		
		
		return sqlStr;
	}
}
