package com.ztesoft.mobile.pn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class PushMessageClassDAOImpl extends BaseDAOImpl implements PushMessageClassDAO {
	//
	private static final String TABLE_NAME = "PUSH_MESSAGE_CLASS";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PUSH_MESSAGE_CLASS_ID:@@SEQ,CLASS_NAME:className,CREATE_TIME:createTime,STAFF_ID:staffId,STAFF_NAME:staffName,SOURCE:source,MEMO:memo,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PUSH_MESSAGE_CLASS_ID:pushMessageClassId,CLASS_NAME:className,CREATE_TIME:createTime,STAFF_ID:staffId,STAFF_NAME:staffName,SOURCE:source,MEMO:memo,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "PUSH_MESSAGE_CLASS_ID:pushMessageClassId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PUSH_MESSAGE_CLASS_ID:pushMessageClassId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "select T.PUSH_MESSAGE_CLASS_ID as pushMessageClassId, T.CLASS_NAME as className, T.to_char(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') as createTime, T.STAFF_ID as staffId, T.STAFF_NAME AS staffName, T.SOURCE as source, T.MEMO AS memo, T.STATE AS state, T.STATE_DATE AS stateDate from push_message_class T ";
		String wherePatternStr = "PUSH_MESSAGE_CLASS_ID:pushMessageClassId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap) {
		final StringBuffer sqlStr = new StringBuffer("select T.PUSH_MESSAGE_CLASS_ID as pushMessageClassId, T.CLASS_NAME as className, to_char(T.CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS') as createTime, T.STAFF_ID as staffId, T.STAFF_NAME AS staffName, T.SOURCE as source, T.MEMO AS memo, T.STATE AS state, T.STATE_DATE AS stateDate from push_message_class T WHERE STATE=1 ");
		
		String className =	MapUtils.getString(paramMap, "className"); 
		if(StringUtils.isNotBlank(className)) {
			//
			sqlStr.append(" AND T.CLASS_NAME = '" + className + "' ");
		}
		
		String source = MapUtils.getString(paramMap, "source");
		if(StringUtils.isNotBlank(source)) {
			//
			sqlStr.append(" AND T.SOURCE = '" + source + "' ");
		}

		System.out.println(TABLE_NAME +"´òÓ¡µÄSQLÊÇ£º" + sqlStr);
		return sqlStr;
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}

	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

	public static void main(String[] args)  throws DataAccessException{
		PushMessageClassDAOImpl test = new PushMessageClassDAOImpl();
		Map paramMap = new HashMap();
		paramMap.put("pushMessageId", "1");
		paramMap.put("messageTitle", "1");
		paramMap.put("messageContent", "1");
	}
}
