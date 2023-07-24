package com.ztesoft.mobile.pn.dao;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class PushMessageQueueDAOImpl extends BaseDAOImpl implements
		PushMessageQueueDAO {
	private static final String TABLE_NAME = "PUSH_MESSAGE_QUEUE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PUSH_MESSAGE_QUEUE_ID:@@SEQ,PUSH_MESSAGE_ID:pushMessageId,RECEIVER_ID:receiverId,RECEIVER_NAME:receiverName,RECEIVER_TOKENS:receiverTokens,MESSAGE_QUEUE_TIME:messageQueueTime,SENDER_ID:senderId,SENDER_NAME:senderName,MESSAGE_QUEUE_STATUS:messageQueueStatus,REMARKS:remarks,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PUSH_MESSAGE_QUEUE_ID:pushMessageQueueId,PUSH_MESSAGE_ID:pushMessageId,RECEIVER_ID:receiverId,RECEIVER_NAME:receiverName,RECEIVER_TOKENS:receiverTokens,MESSAGE_QUEUE_TIME:messageQueueTime,SENDER_ID:senderId,SENDER_NAME:senderName,MESSAGE_QUEUE_STATUS:messageQueueStatus,REMARKS:remarks,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "PUSH_MESSAGE_QUEUE_ID:pushMessageQueueId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PUSH_MESSAGE_QUEUE_ID:pushMessageQueueId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   PUSH_MESSAGE_QUEUE_ID AS pushMessageQueueId,  PUSH_MESSAGE_ID AS pushMessageId,  RECEIVER_ID AS receiverId,  RECEIVER_NAME AS receiverName,  RECEIVER_TOKENS AS receiverTokens,  to_char(MESSAGE_QUEUE_TIME, 'YYYY-MM-DD HH24:MI:SS') AS messageQueueTime, SENDER_ID AS senderId,  SENDER_NAME AS senderName,  MESSAGE_QUEUE_STATUS AS messageQueueStatus, REMARKS AS remarks, STATE AS state, STATE_DATE AS stateDate FROM PUSH_MESSAGE_QUEUE WHERE PUSH_MESSAGE_QUEUE_ID=?";
		String wherePatternStr = "PUSH_MESSAGE_QUEUE_ID:pushMessageQueueId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap) {
		 StringBuffer sqlStr = new StringBuffer(" SELECT   Q.PUSH_MESSAGE_QUEUE_ID AS pushMessageQueueId,  Q.PUSH_MESSAGE_ID AS pushMessageId,  Q.RECEIVER_ID AS receiverId,  Q.RECEIVER_NAME AS receiverName,  Q.RECEIVER_TOKENS AS receiverTokens,  to_char(MESSAGE_QUEUE_TIME, 'YYYY-MM-DD HH24:MI:SS') AS messageQueueTime, Q.SENDER_ID AS senderId,  Q.SENDER_NAME AS senderName, Q.MESSAGE_QUEUE_STATUS AS messageQueueStatus, REMARKS AS remarks, STATE AS state, STATE_DATE AS stateDate  FROM PUSH_MESSAGE_QUEUE Q WHERE STATE = 1 ");

		 Long pushMessageId = MapUtils.getLong(paramMap, "pushMessageId");
		 if(null != pushMessageId) {
			 sqlStr.append(" AND Q.PUSH_MESSAGE_ID  = " + pushMessageId);
		 }

		System.out.println(TABLE_NAME +"打印的SQL是：" + sqlStr);
		return sqlStr;
	}

	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, null);
	}

	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getIntValue(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getIntValue(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}

    public Map selPushMessageQueryByPagin(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = new StringBuffer(" select M.MESSAGE_TITLE   AS messageTitle, ");
        sqlStr.append(" M.MESSAGE_CONTENT AS msssageContent, ");
        sqlStr.append(" M.MESSAGE_TYPE    AS messageType, ");
        sqlStr.append(" Q.RECEIVER_ID     AS receiverId, ");
        sqlStr.append(" Q.RECEIVER_NAME   AS receiverName, ");
        sqlStr.append(" Q.SENDER_ID       AS senderId, ");
        sqlStr.append(" Q.SENDER_NAME     AS senderName, ");
        sqlStr.append(" to_char(Q.MESSAGE_QUEUE_TIME,'yyyy-mm-dd hh24:mi:ss') AS msssageQueueTime ");
        sqlStr.append(" from PUSH_MESSAGE_QUEUE Q left join PUSH_MESSAGE M ");
        sqlStr.append(" on Q.PUSH_MESSAGE_ID = M.PUSH_MESSAGE_ID WHERE 1=1 ");

        String beginDate = MapUtils.getString(paramMap, "beginDate");
        if(StringUtils.isNotBlank(beginDate))  {
            sqlStr.append(" AND Q.MESSAGE_QUEUE_TIME  >= TO_DATE('" + beginDate + "', 'yyyy-mm-dd hh24:mi:ss') ");
        }

        String endDate = MapUtils.getString(paramMap, "endDate");
        if(StringUtils.isNotBlank(endDate))  {
            sqlStr.append(" AND Q.MESSAGE_QUEUE_TIME  <= TO_DATE('" + endDate + "', 'yyyy-mm-dd hh24:mi:ss') ");
        }

        String senderName = MapUtils.getString(paramMap, "senderName");
        if(StringUtils.isNotBlank(senderName))  {
            sqlStr.append(" AND Q.SENDER_NAME like '%" + senderName + "%' ");
        }

        String receiverName = MapUtils.getString(paramMap, "receiverName");
        if(StringUtils.isNotBlank(receiverName))  {
            sqlStr.append(" AND Q.RECEIVER_NAME like '%" + receiverName + "%' ");
        }

       Integer messageType = MapUtils.getInteger(paramMap, "messageType");
        if(messageType != null)  {
            sqlStr.append(" AND M.MESSAGE_TYPE =" + messageType);
        }
        //
        System.out.println("selPushMessageQueryByPagin打印SQL是：" + sqlStr.toString());

        int pageIndex = MapUtils.getIntValue(paramMap, "pageIndex", 1);
        int pageSize = MapUtils.getIntValue(paramMap, "pageSize", 10);
        return this.populateQueryByMap(sqlStr, pageIndex, pageSize);

    }
}
