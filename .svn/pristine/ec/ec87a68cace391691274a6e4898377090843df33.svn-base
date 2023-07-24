package com.ztesoft.mobile.pn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class PushMessageDAOImpl extends BaseDAOImpl implements PushMessageDAO {
	private static final String TABLE_NAME = "PUSH_MESSAGE";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PUSH_MESSAGE_ID:@@SEQ,MESSAGE_TITLE:messageTitle,MESSAGE_CONTENT:messageContent,MESSAGE_URL:messageUrl,MESSAGE_TIME:messageTime,MESSAGE_STATUS:messageStatus,MESSAGE_STAFF_ID:messageStaffId,MESSAGE_STAFF_NAME:messageStaffName,MESSAGE_CLASSES:messageClasses,MESSAGE_RESEND:messageResend,MESSAGE_RESEND_NUMS:messageResendNums,MESSAGE_RESEND_INTERVAL:messageResendInterval,MESSAGE_SYNC:messageSync,MESSAGE_TYPE:messageType,MESSAGE_START_TIME:messageStartTime,MESSAGE_END_TIME:messageEndTime,MESSAGE_SEND_NUMS:messageSendNums,MESSAGE_SUCCESS_NUMS:messageSuccessNums,MESSAGE_FAILURE_NUMS:messageFailureNums,MESSAGE_TOTAL_NUMS:messageTotalNums,REMARKS:remarks,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PUSH_MESSAGE_ID:pushMessageId,MESSAGE_TITLE:messageTitle,MESSAGE_CONTENT:messageContent,MESSAGE_URL:messageUrl,MESSAGE_TIME:messageTime,MESSAGE_STATUS:messageStatus,MESSAGE_STAFF_ID:messageStaffId,MESSAGE_STAFF_NAME:messageStaffName,MESSAGE_CLASSES:messageClasses,MESSAGE_RESEND:messageResend,MESSAGE_RESEND_NUMS:messageResendNums,MESSAGE_RESEND_INTERVAL:messageResendInterval,MESSAGE_SYNC:messageSync,MESSAGE_TYPE:messageType,MESSAGE_START_TIME:messageStartTime,MESSAGE_END_TIME:messageEndTime,MESSAGE_SEND_NUMS:messageSendNums,MESSAGE_SUCCESS_NUMS:messageSuccessNums,MESSAGE_FAILURE_NUMS:messageFailureNums,MESSAGE_TOTAL_NUMS:messageTotalNums,REMARKS:remarks,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "PUSH_MESSAGE_ID:pushMessageId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PUSH_MESSAGE_ID:pushMessageId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   PUSH_MESSAGE_ID AS pushMessageId,  MESSAGE_TITLE AS messageTitle,  MESSAGE_CONTENT AS messageContent,   MESSAGE_URL AS messageUrl,  to_char(MESSAGE_TIME, 'YYYY-MM-DD HH24:MI:SS')  AS messageTime,  MESSAGE_STATUS AS messageStatus,  MESSAGE_STAFF_ID AS messageStaffId,  MESSAGE_STAFF_NAME AS messageStaffName,  MESSAGE_CLASSES AS messageClasses,  MESSAGE_RESEND AS messageResend,  MESSAGE_RESEND_NUMS AS messageResendNums,  MESSAGE_RESEND_INTERVAL AS messageResendInterval,  MESSAGE_SYNC AS messageSync,  MESSAGE_TYPE AS messageType,  MESSAGE_START_TIME AS messageStartTime,  MESSAGE_END_TIME AS messageEndTime,  MESSAGE_SEND_NUMS AS messageSendNums,  MESSAGE_SUCCESS_NUMS AS messageSuccessNums,  MESSAGE_FAILURE_NUMS AS messageFailureNums,  MESSAGE_TOTAL_NUMS AS messageTotalNums,  REMARKS AS remarks,  STATE AS state,  STATE_DATE AS stateDate FROM PUSH_MESSAGE M WHERE PUSH_MESSAGE_ID=?";
		String wherePatternStr = "PUSH_MESSAGE_ID:pushMessageId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer("SELECT PUSH_MESSAGE_ID AS pushMessageId,  MESSAGE_TITLE AS messageTitle,  MESSAGE_CONTENT AS messageContent,   MESSAGE_URL AS messageUrl,   to_char(MESSAGE_TIME, 'YYYY-MM-DD HH24:MI:SS')  AS messageTime,  MESSAGE_STATUS AS messageStatus,  MESSAGE_STAFF_ID AS messageStaffId,  MESSAGE_STAFF_NAME AS messageStaffName,  MESSAGE_CLASSES AS messageClasses,  MESSAGE_RESEND AS messageResend,  MESSAGE_RESEND_NUMS AS messageResendNums,  MESSAGE_RESEND_INTERVAL AS messageResendInterval,  MESSAGE_SYNC AS messageSync,  MESSAGE_TYPE AS messageType,  MESSAGE_START_TIME AS messageStartTime,  MESSAGE_END_TIME AS messageEndTime,  MESSAGE_SEND_NUMS AS messageSendNums,  MESSAGE_SUCCESS_NUMS AS messageSuccessNums,  MESSAGE_FAILURE_NUMS AS messageFailureNums,  MESSAGE_TOTAL_NUMS AS messageTotalNums,  REMARKS AS remarks,  STATE AS state,  STATE_DATE AS stateDate FROM PUSH_MESSAGE M ");
		//TODO 补充条件
		System.out.println(TABLE_NAME +"打印的SQL是：" + sqlStr);
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
		PushMessageDAOImpl test = new PushMessageDAOImpl();
		Map paramMap = new HashMap();
		paramMap.put("pushMessageId", "1");
		paramMap.put("messageTitle", "1");
		paramMap.put("messageContent", "1");
		paramMap.put("messageTime", "1");
		paramMap.put("messageStatus", "1");
		paramMap.put("messageStaffId", "1");
		paramMap.put("messageStaffName", "1");
		paramMap.put("messageClasses", "1");
		paramMap.put("messageResend", "1");
		paramMap.put("messageResendNums", "1");
		paramMap.put("messageResendInterval", "1");
		paramMap.put("messageSync", "1");
		paramMap.put("messageType", "1");
		paramMap.put("messageStartTime", "1");
		paramMap.put("messageEndTime", "1");
		paramMap.put("messageSendNums", "1");
		paramMap.put("messageSuccessNums", "1");
		paramMap.put("messageFailureNums", "1");
		paramMap.put("messageTotalNums", "1");
		paramMap.put("remarks", "1");
		paramMap.put("state", "1");
		paramMap.put("stateDate", "1");
		// test.insert(paramMap);
		// test.update(paramMap);
		// test.delete(paramMap);
		// System.out.println(test.selById(paramMap));
		// System.out.println(test.selAll(paramMap));
	}
}
