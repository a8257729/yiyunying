package com.ztesoft.eoms.im.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import com.ztesoft.eoms.common.helper.DateHelper;
import com.ztesoft.eoms.common.helper.SQLHelper;

import java.util.Date;

import java.util.HashMap;

import oracle.jdbc.driver.OracleTypes;

public class ImChatLogDAOImpl extends BaseDAOImpl implements ImChatLogDAO {
private static final String TABLE_NAME = "IM_CHAT_LOG";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_CHAT_LOG_ID:imChatLogId,MSG_BELONG_STAFF_ID:msgBelongStaffId,MSG_DATE:msgDate,SENDER_STAFF_ID:senderStaffId,RECEIVE_STAFF_ID:receiveStaffId,MSG_CONTENT:msgContent,READ_STATE:readState,MSG_TYPE:msgType";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imChatLogId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IM_CHAT_LOG_ID:imChatLogId,MSG_BELONG_STAFF_ID:msgBelongStaffId,MSG_DATE:msgDate,SENDER_STAFF_ID:senderStaffId,RECEIVE_STAFF_ID:receiveStaffId,MSG_CONTENT:msgContent,READ_STATE:readState";
		String wherePatternStr = "IM_CHAT_LOG_ID:imChatLogId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void updateReadState(Map paramMap) throws DataAccessException {
		String updatePatternStr = "READ_STATE:readState";
		String wherePatternStr = "MSG_BELONG_STAFF_ID:msgBelongStaffId,&&:SENDER_STAFF_ID:senderStaffId,&&:READ_STATE:oldReadState";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void updateMsgReadState(Map paramMap) throws DataAccessException {
		StringBuffer sql = new StringBuffer("update IM_CHAT_LOG set read_state = 1 where ");
		sql.append("MSG_BELONG_STAFF_ID = ").append(paramMap.get("msgBelongStaffId"));
		sql.append(" AND IM_STAFF_BIGGROUP_ID = ").append(paramMap.get("senderStaffId"));
		sql.append(" AND READ_STATE = 0 ");
		
		dynamicUpdateBySql(sql.toString());
	}
	
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_CHAT_LOG_ID:imChatLogId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_CHAT_LOG_ID AS imChatLogId,  MSG_BELONG_STAFF_ID AS msgBelongStaffId,  MSG_DATE AS msgDate,  SENDER_STAFF_ID AS senderStaffId,  RECEIVE_STAFF_ID AS receiveStaffId,  MSG_CONTENT AS msgContent,  READ_STATE AS readState,MSG_TYPE as msgType FROM IM_CHAT_LOG WHERE IM_CHAT_LOG_ID=?";
		String wherePatternStr = "IM_CHAT_LOG_ID:imChatLogId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_CHAT_LOG_ID AS imChatLogId,  MSG_BELONG_STAFF_ID AS msgBelongStaffId,  MSG_DATE AS msgDate,  SENDER_STAFF_ID AS senderStaffId,  RECEIVE_STAFF_ID AS receiveStaffId,  MSG_CONTENT AS msgContent,  READ_STATE AS readState ,MSG_TYPE as msgType FROM IM_CHAT_LOG";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selUnReadMsg(Map paramMap)throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer("select a.im_chat_log_id as imChatLogId,");
		sqlStr.append(" a.msg_belong_staff_id as msgBelongStaffId, ");
		sqlStr.append(" a.msg_date as msgDate, ");
		sqlStr.append(" a.sender_staff_id as senderStaffId, ");
		sqlStr.append(" b.staff_name as senderStaffName, ");
		sqlStr.append(" a.receive_staff_id as receiveStaffId, ");
		sqlStr.append(" a.msg_content as msgContent, ");
		sqlStr.append(" a.msg_type as msgType, ");
		sqlStr.append(" a.IM_STAFF_BIGGROUP_ID as imStaffBiggroupId, ");
		sqlStr.append(" '' as imStaffBiggroupName ");
		sqlStr.append(" from IM_CHAT_LOG a join uos_staff b on a.sender_staff_id = b.staff_id  ");
		sqlStr.append(" where  a.read_state = 0  and a.msg_type = 1 ");
		if(paramMap.containsKey("receiveStaffId")&&paramMap.get("receiveStaffId")!=null){
			sqlStr.append(" and a.receive_staff_id = ").append(paramMap.get("receiveStaffId"));
		}
		if(paramMap.containsKey("msgBelongStaffId")&&paramMap.get("msgBelongStaffId")!=null){
			sqlStr.append(" and a.msg_belong_staff_id = ").append(paramMap.get("msgBelongStaffId"));
		}
		if(paramMap.containsKey("senderStaffId")&&paramMap.get("senderStaffId")!=null){
			sqlStr.append(" and  a.sender_staff_id  = ").append(paramMap.get("senderStaffId"));
		}
		sqlStr.append(" union all ");
		sqlStr.append(" select a.im_chat_log_id as imChatLogId,");
		sqlStr.append(" a.msg_belong_staff_id as msgBelongStaffId, ");
		sqlStr.append(" a.msg_date as msgDate, ");
		sqlStr.append(" a.sender_staff_id as senderStaffId, ");
		sqlStr.append(" c.staff_name as senderStaffName, ");
		
		sqlStr.append(" a.receive_staff_id as receiveStaffId, ");
		sqlStr.append(" a.msg_content as msgContent, ");
		sqlStr.append(" a.msg_type as msgType, ");
		sqlStr.append(" a.IM_STAFF_BIGGROUP_ID as imStaffBiggroupId, ");
		sqlStr.append(" b.IM_STAFF_BIGGROUP_NAME as imStaffBiggroupName ");
		sqlStr.append(" from IM_CHAT_LOG a join IM_STAFF_BIGGROUP b on a.IM_STAFF_BIGGROUP_ID = b.IM_STAFF_BIGGROUP_ID ");
		sqlStr.append(" join uos_staff c on a.sender_staff_id = c.staff_id ");
		sqlStr.append(" where  a.read_state = 0  and a.msg_type = 2 ");
		
		if(paramMap.containsKey("receiveStaffId")&&paramMap.get("receiveStaffId")!=null){
			sqlStr.append(" and a.receive_staff_id = ").append(paramMap.get("receiveStaffId"));
		}
		if(paramMap.containsKey("msgBelongStaffId")&&paramMap.get("msgBelongStaffId")!=null){
			sqlStr.append(" and a.msg_belong_staff_id = ").append(paramMap.get("msgBelongStaffId"));
		}
		if(paramMap.containsKey("senderStaffId")&&paramMap.get("senderStaffId")!=null){
			sqlStr.append(" and  a.IM_STAFF_BIGGROUP_ID  = ").append(paramMap.get("senderStaffId"));
		}
		
		sqlStr.append(" order by 3 asc ");
		
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, null);
	}
	//²éÑ¯ÁÄÌì¼ÇÂ¼
	public Map selChatLog(Map paramMap)throws DataAccessException{
		StringBuffer sqlStr = new StringBuffer("select a.im_chat_log_id as imChatLogId,");
		sqlStr.append(" a.msg_belong_staff_id as msgBelongStaffId, ");
		sqlStr.append(" a.sender_staff_id as senderStaffId, ");
		sqlStr.append(" a.receive_staff_id as receiveStaffId, ");
		sqlStr.append(" b.staff_name as senderStaffName, ");
		sqlStr.append(" c.staff_name as receiveStaffName, ");
		sqlStr.append(" a.msg_date as msgDate, ");
		sqlStr.append(" a.msg_content as msgContent ");
		sqlStr.append(" from im_chat_log a ");
		sqlStr.append(" join uos_staff b on a.sender_staff_id = b.staff_id ");
		sqlStr.append(" join uos_staff c on a.receive_staff_id= c.staff_id ");
		sqlStr.append(" where a.read_state = 1  ");
		if(paramMap.containsKey("msgBelongStaffId")&&paramMap.get("msgBelongStaffId")!=null){
			sqlStr.append(" and a.msg_belong_staff_id = ").append(paramMap.get("msgBelongStaffId"));
		}
		if(paramMap.containsKey("msgType")&&paramMap.get("msgType")!=null){
			sqlStr.append(" and a.msg_type = ").append(paramMap.get("msgType"));
		}
		if(paramMap.containsKey("imStaffBiggroupId")&&paramMap.get("imStaffBiggroupId")!=null){
			sqlStr.append(" and a.im_staff_biggroup_id = ").append(paramMap.get("imStaffBiggroupId"));
		}
		if(paramMap.containsKey("logStaff")&&paramMap.get("logStaff")!=null){
			sqlStr.append(" and (a.sender_staff_id = ").append(paramMap.get("logStaff")).append(" or a.receive_staff_id = ").append(paramMap.get("logStaff")).append(" )");
		}
		if(paramMap.containsKey("msgBeginDate")&&paramMap.get("msgBeginDate")!=null){
			sqlStr.append(" and a.msg_date >= ").append(SQLHelper.toDateSqlStr(DateHelper.parse(paramMap.get("msgBeginDate").toString()),0));
		}
		if(paramMap.containsKey("msgEndDate")&&paramMap.get("msgEndDate")!=null){
			sqlStr.append(" and a.msg_date <= ").append(SQLHelper.toDateSqlStr(DateHelper.parse(paramMap.get("msgEndDate").toString()),0));
		}
		if(paramMap.containsKey("msgContent")&&paramMap.get("msgContent")!=null){
			sqlStr.append("  and a.msg_content like '%").append(paramMap.get("msgContent")).append("%'");
		}
		sqlStr.append(" order by msg_date asc ");
		
		return populateQueryByMap(sqlStr, Integer.parseInt(paramMap.get("start").toString()), Integer.parseInt(paramMap.get("limit").toString()));
	}

	public void insertGroupLog(int senderId,int receiveGroupId,String message){
		Connection conn = null;
        CallableStatement proc = null;
        ResultSet rs = null;
        try {
        	conn = getConnection();
			proc = conn.prepareCall(
			        "{call IM_INSERT_GROUP_LOG(?,?,?)}",
			        ResultSet.TYPE_SCROLL_INSENSITIVE,
			        ResultSet.CONCUR_READ_ONLY);
				proc.setInt(1,senderId);
	            proc.setInt(2,receiveGroupId);
	            proc.setString(3,message);
	            proc.execute();
       	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
                       
            cleanUp(conn, proc, null, rs);
        }
	}
	
public static void main(String[] args) {
	ImChatLogDAOImpl test = new ImChatLogDAOImpl();
	try {
		Map paramMap = new HashMap();
		
		paramMap.put("msgBelongStaffId", new Integer(38102));
		
		paramMap.put("senderStaffId", new Integer(241));
		
		
		
		//test.insertGroupLog(1,141,"hello");
		
//		test.update(paramMap);
//		test.delete(paramMap);
		test.updateMsgReadState(paramMap);
//		System.out.println(test.selAll(paramMap));
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}



