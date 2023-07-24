package com.ztesoft.mobile.v2.dao.common;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileNoticeDAOImpl extends BaseDAOImpl implements MobileNoticeDAO {

	private static final String TABLE_NAME = "MOBILE_NOTICE";

	private Logger logger = Logger.getLogger(MobileNoticeDAOImpl.class);

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "NOTICE_ID:@@SEQ,TITLE:title,CONTENT:content,SOURCE:source,STATUS:status,PRIORITY:priority,TYPE:type,NOTICE_TIME:noticeTime,OBJECT_ID:objectId,OBJECT_NAME:objectName,OBJECT_TYPE:objectType,STATE:state,STATE_DATE:stateDate";
		// Long nextId = getPKFromMem(TABLE_NAME, 9);
		// paramMap.put("notificationId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "NOTICE_ID:noticeId,TITLE:title,CONTENT:content,SOURCE:source,STATUS:status,PRIORITY:priority,TYPE:type,NOTICE_TIME:noticeTime,OBJECT_ID:objectId,OBJECT_NAME:objectName,OBJECT_TYPE:objectType,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "NOTICE_ID:noticeId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "NOTICE_ID:noticeId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.buildSql(paramMap);
		sqlStr.append("AND NOTICE_ID=? ");
		String wherePatternStr = "NOTICE_ID:noticeId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap,
				wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final StringBuffer sqlStr = this.buildSql(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	private StringBuffer buildSql(Map paramMap) {

		StringBuffer sqlSql = new StringBuffer(
				" SELECT NOTICE_ID AS noticeId, ");
		sqlSql.append(" TITLE AS title, ");
		sqlSql.append(" CONTENT AS content, ");
		sqlSql.append(" SOURCE AS source, ");
		sqlSql.append(" STATUS AS status, ");
		sqlSql.append(" PRIORITY AS priority, ");
		sqlSql.append(" TYPE AS type, ");
		sqlSql
				.append(" to_char(NOTICE_TIME,'yyyy-mm-dd hh24:mi:ss') AS noticeTime, ");
		sqlSql.append(" OBJECT_ID AS objectId, ");
		sqlSql.append(" OBJECT_NAME AS objectName, ");
		sqlSql.append(" OBJECT_TYPE AS objectType ");
		// sqlSql.append(" STATE AS state, ");
		// sqlSql.append(" STATE_DATE AS stateDate ");
		sqlSql.append(" FROM MOBILE_NOTICE N WHERE N.STATE = 1 ");

		String startNoticeTime = MapUtils
				.getString(paramMap, "startNoticeTime");
		if (StringUtils.isNotBlank(startNoticeTime)) {
			sqlSql.append(" AND N.NOTICE_TIME >= to_date('" + startNoticeTime
					+ "', 'yyyymmddhh24miss') ");
		}

		String endNoticeTime = MapUtils.getString(paramMap, "endNoticeTime");
		if (StringUtils.isNotBlank(endNoticeTime)) {
			sqlSql.append(" AND N.NOTICE_TIME <= to_date('" + endNoticeTime
					+ "', 'yyyymmddhh24miss') ");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buildSql打印的SQL是: " + sqlSql.toString());
		}

		return sqlSql;
	}

	public Map selByPage(Map paramMap) throws DataAccessException {
		int pageIndex = MapUtils.getIntValue(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getIntValue(paramMap, "pageSize", 10);

		StringBuffer sqlStr = this.buildSql(paramMap);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Map selByConn(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer(
				"  SELECT NOTICE_ID         AS noticeId,"+
                "  TITLE              AS title,"+
                "  CONTENT            AS content,"+
                "  SOURCE             as source,"+
               "   STATUS             as status,"+
                "  trans_param('NOTIFICATION_STATUS',STATUS) as notificationStatus,"+
                "  TYPE              as type,"+
                "   trans_param('NOTIFICATION_TYPE',TYPE) as noticeType,"+
                "  PRIORITY           as priority,"+
                " trans_param('NOTIFICATION_PRIORITY',PRIORITY) as notificationPriority,"+
                "  NOTICE_TIME        as noticeTime,"+
               // " CREATE_STAFF_ID    as createStaffId,"+
                "  OBJECT_ID  as objectId,"+
                "  OBJECT_NAME        as objectName,"+
                "  OBJECT_TYPE      as objectType,"+
                "  trans_param('OBJECT_TYPE',OBJECT_TYPE) as objectTypeName,"+
                "  STATE              as state,"+
                "  STATE_DATE as stateDate"+
            " FROM MOBILE_NOTICE where 1=1 ");
		//如果部门和人员都不等于空，用or 链接查询
		if(MapUtils.getString(paramMap, "staffId")!= null && !MapUtils.getString(paramMap, "staffId").equals("")
				&& MapUtils.getString(paramMap, "orgId")!= null && !MapUtils.getString(paramMap, "orgId").equals("")){
			sqlBuf.append(" AND instr(OBJECT_ID,'");
			sqlBuf.append(MapUtils.getString(paramMap, "staffId"));
			sqlBuf.append("',1,1) >0");
			sqlBuf.append(" or instr(OBJECT_ID,'");
			sqlBuf.append(MapUtils.getString(paramMap, "orgId"));
			sqlBuf.append("',1,1) >0");
			
		}else{
			if(MapUtils.getString(paramMap, "staffId")!= null && !MapUtils.getString(paramMap, "staffId").equals("")){
				sqlBuf.append(" AND instr(OBJECT_ID,'");
				sqlBuf.append(MapUtils.getString(paramMap, "staffId"));
				sqlBuf.append("',1,1) >0");
			}
			if(MapUtils.getString(paramMap, "orgId")!= null && !MapUtils.getString(paramMap, "orgId").equals("")){
				sqlBuf.append(" and instr(OBJECT_ID,'");
				sqlBuf.append(MapUtils.getString(paramMap, "orgId"));
				sqlBuf.append("',1,1) >0");
			}
		}
		if (MapUtils.getString(paramMap, "beginDate") != null
				&& !MapUtils.getString(paramMap, "beginDate").equals("")) {
			sqlBuf.append("  AND cast(NOTICE_TIME as date)>= TO_DATE('")
					.append(MapUtils.getString(paramMap, "beginDate")).append(
							"','YYYY-MM-DD')");
		}
		if (MapUtils.getString(paramMap, "endDate") != null
				&& !MapUtils.getString(paramMap, "endDate").equals("")) {
			sqlBuf.append("  AND cast(NOTICE_TIME as date)<= TO_DATE('")
					.append(MapUtils.getString(paramMap, "endDate")).append(
							"','YYYY-MM-DD')");
		}
		return populateQueryByMap(sqlBuf, MapUtils.getIntValue(paramMap,
				"pageIndex", 1), MapUtils.getIntValue(paramMap, "pageSize", 10));
	}
}