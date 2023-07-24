package com.ztesoft.eoms.im.dao;

import java.util.Map;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;

import java.util.Date;
import java.util.List;
import java.util.HashMap;

public class ImStaffOnlineDAOImpl extends BaseDAOImpl implements ImStaffOnlineDAO {
private static final String TABLE_NAME = "IM_STAFF_ONLINE";
	public Long insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_STAFF_ONLINE_ID:imStaffOnlineId,STAFF_ID:staffId,STATE_ID:stateId,ONLINE_DATE:onlineDate,OUTLINE_DATE:outlineDate";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imStaffOnlineId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return nextId;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STATE_ID:stateId,OUTLINE_DATE:outlineDate";
		String wherePatternStr = "IM_STAFF_ONLINE_ID:imStaffOnlineId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_STAFF_ONLINE_ID:imStaffOnlineId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_ONLINE_ID AS imStaffOnlineId,  STAFF_ID AS staffId,  STATE_ID AS stateId,  ONLINE_DATE AS onlineDate,  OUTLINE_DATE AS outlineDate FROM IM_STAFF_ONLINE WHERE IM_STAFF_ONLINE_ID=? and STATE_ID <> 0";
		String wherePatternStr = "IM_STAFF_ONLINE_ID:imStaffOnlineId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_ONLINE_ID AS imStaffOnlineId,  STAFF_ID AS staffId,  STATE_ID AS stateId,  ONLINE_DATE AS onlineDate,  OUTLINE_DATE AS outlineDate FROM IM_STAFF_ONLINE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selOnlineStateByStaffId(Map paramMap) throws DataAccessException {
		String sqlStr ="select IM_STAFF_ONLINE_ID AS imStaffOnlineId,  STAFF_ID AS staffId,  STATE_ID AS stateId,  ONLINE_DATE AS onlineDate,  OUTLINE_DATE AS outlineDate from (select * from IM_STAFF_ONLINE where staff_id = ? order by 1 desc ) where rownum<2";
		String wherePatternStr = "STAFF_ID:staffId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
public static void main(String[] args) {
	ImStaffOnlineDAOImpl test = new ImStaffOnlineDAOImpl();
	try {
		  Map paramMap = new HashMap();
		paramMap.put("imStaffOnlineId", "1");
		paramMap.put("staffId", new Integer(38102));
		paramMap.put("stateId", "1");
		paramMap.put("onlineDate", new Date());
		paramMap.put("outlineDate", new Date());
	System.out.println( test.selOnlineStateByStaffId(paramMap));	
//		test.update(paramMap);
//		test.delete(paramMap);
//		System.out.println(test.selById(paramMap));
//		System.out.println(test.selAll(paramMap));
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}

