package com.ztesoft.mobile.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileMessageDAOImpl extends BaseDAOImpl implements MobileMessageDAO {
	//
	private static final String TABLE_NAME = "MOBILE_MESSAGE";
	
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "MESSAGE_ID:@@SEQ,TITLE:title,CONTENT:content,CREATE_TIME:createTime,CREATE_STAFF_ID:createStaffId,PUBLIC_TYPE:publicType,PUBLIC_OBJECT:publicObject,STATE:state,PUBLIC_OBJECT_NAME:publicObjectName,CREATE_STAFF_NAME:createStaffName";
		//Long nextId = getPKFromMem(TABLE_NAME, 9);
		//paramMap.put("messageId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "MESSAGE_ID:messageId,TITLE:title,CONTENT:content,CREATE_STAFF_ID:createStaffId,PUBLIC_TYPE:publicType,PUBLIC_OBJECT:publicObject,PUBLIC_OBJECT_NAME:publicObjectName,CREATE_STAFF_NAME:createStaffName";
		String wherePatternStr = "MESSAGE_ID:messageId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "MESSAGE_ID:messageId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		sqlStr.append("WHERE M.MESSAGE_ID = ? ");
		String wherePatternStr = "MESSAGE_ID:messageId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}
	
	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}
	
	public List selAllNotice(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getNoticeSqlStr(paramMap);
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}
	
	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}
	
	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer(" SELECT M.MESSAGE_ID AS messageId, M.TITLE AS title, M.CONTENT AS content ,M.CREATE_TIME as createTime,M.CREATE_STAFF_ID as createStaffId,M.CREATE_STAFF_NAME as createStaffName,M.PUBLIC_TYPE as publicType,M.PUBLIC_OBJECT as publicObject,M.STATE as state,M.PUBLIC_OBJECT_NAME as publicObjectName FROM MOBILE_MESSAGE M ");
		//
		System.out.println(TABLE_NAME +"打印的SQL是：" + sqlStr);
		return sqlStr;
	}
	
	private StringBuffer getNoticeSqlStr(Map paramMap) {
		
		StringBuffer sqlStr = new StringBuffer(" SELECT M.MESSAGE_ID AS noticeId, M.TITLE AS title, M.CONTENT AS content ,M.CREATE_TIME as noticeTime,M.CREATE_STAFF_ID as objectId,M.CREATE_STAFF_NAME as objectName,M.PUBLIC_TYPE as objectType FROM MOBILE_MESSAGE M ");
		//
		System.out.println(TABLE_NAME +"打印的SQL是：" + sqlStr);
		return sqlStr;
		
		
	}

	public Map selByConn(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		//为1代表查发布类型为人员
		if(MapUtils.getString(paramMap, "publicType")!= null && MapUtils.getString(paramMap, "publicType").equals("1")){
			sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
			sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 1 AND STATE = 1 ");

			if(MapUtils.getString(paramMap, "staffId")!= null && !MapUtils.getString(paramMap, "staffId").equals("")){
				sqlBuf.append(" AND instr(PUBLIC_OBJECT,'");
				sqlBuf.append(MapUtils.getString(paramMap, "staffId"));
				sqlBuf.append("',1,1) >0");
			}
			if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
				sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
	        }
			if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
				sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
	        }
		}else if(MapUtils.getString(paramMap, "publicType")!= null && MapUtils.getString(paramMap, "publicType").equals("2")){
			//为2代表查发布类型为部门
			sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
			sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 2 AND STATE = 1 ");
			
			if(MapUtils.getString(paramMap, "orgId")!= null && !MapUtils.getString(paramMap, "orgId").equals("")){
				sqlBuf.append(" and instr(PUBLIC_OBJECT,'");
				sqlBuf.append(MapUtils.getString(paramMap, "orgId"));
				sqlBuf.append("',1,1) >0");
			}
			if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
				sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
	        }
			if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
				sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
	        }
		}else if(MapUtils.getString(paramMap, "publicType")!= null && MapUtils.getString(paramMap, "publicType").equals("")){
			//为空代表查发布类型为全部当人员ID不为空查指定人员，否则查所有人
			
			if(MapUtils.getString(paramMap, "staffId")!= null && !MapUtils.getString(paramMap, "staffId").equals("")){
				sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
				sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 1 AND STATE = 1 ");
			
				sqlBuf.append(" AND instr(PUBLIC_OBJECT,'");
				sqlBuf.append(MapUtils.getString(paramMap, "staffId"));
				sqlBuf.append("',1,1) >0");
				if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
		        }
				if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
		        }
			}else {
				sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
				sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 1 AND STATE = 1 ");
						
				if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
		        }
				if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
		        }
			}
			//为空代表查发布类型为全部，当部门ID不为空查指定部门，否则查所有记录
			sqlBuf.append(" union ");
			if(MapUtils.getString(paramMap, "orgId")!= null && !MapUtils.getString(paramMap, "orgId").equals("")){
				sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
				sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 2 AND STATE = 1 ");
						
				sqlBuf.append(" and instr(PUBLIC_OBJECT,'");
				sqlBuf.append(MapUtils.getString(paramMap, "orgId"));
				sqlBuf.append("',1,1) >0");
				if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
		        }
				if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
		        }
			}else {
				sqlBuf.append(" SELECT  MESSAGE_ID AS messageId,  TITLE AS title,  CONTENT AS content , CREATE_TIME as createTime, CREATE_STAFF_ID as createStaffId,CREATE_STAFF_NAME as createStaffName, PUBLIC_TYPE as publicType, PUBLIC_OBJECT as publicObject, STATE as state ,PUBLIC_OBJECT_NAME as publicObjectName");
				sqlBuf.append(" FROM MOBILE_MESSAGE  where PUBLIC_TYPE = 2 AND STATE = 1 ");
										
				if (MapUtils.getString(paramMap, "beginDate") != null && !MapUtils.getString(paramMap, "beginDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)>= TO_DATE('").append(MapUtils.getString(paramMap, "beginDate")).append("','YYYY-MM-DD')");
		        }
				if (MapUtils.getString(paramMap, "endDate") != null && !MapUtils.getString(paramMap, "endDate").equals("") ) {
					sqlBuf.append("  AND cast(CREATE_TIME as date)<= TO_DATE('").append(MapUtils.getString(paramMap, "endDate")).append("','YYYY-MM-DD')");
		        }
				
			}
			
		}
		
		
		
		
		System.out.println("selByConn"+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
}
