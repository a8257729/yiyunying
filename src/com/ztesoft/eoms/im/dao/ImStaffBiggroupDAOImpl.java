package com.ztesoft.eoms.im.dao;

import java.util.Map;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;

import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
public class ImStaffBiggroupDAOImpl extends BaseDAOImpl implements ImStaffBiggroupDAO {
private static final String TABLE_NAME = "IM_STAFF_BIGGROUP";
	public long insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId,IM_STAFF_BIGGROUP_NAME:imStaffBiggroupName,GROUP_NEWS:groupNews,STAFF_ID:staffId,CREATE_DATE:createDate,STATE:state";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imStaffBiggroupId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return nextId;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId,IM_STAFF_BIGGROUP_NAME:imStaffBiggroupName,GROUP_NEWS:groupNews";
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STATE:state";
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,  IM_STAFF_BIGGROUP_NAME AS imStaffBiggroupName,  GROUP_NEWS AS groupNews , STAFF_ID AS staffId,  CREATE_DATE AS createDate,  STATE AS state FROM IM_STAFF_BIGGROUP WHERE IM_STAFF_BIGGROUP_ID=?";
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,  IM_STAFF_BIGGROUP_NAME AS imStaffBiggroupName, GROUP_NEWS AS groupNews , STAFF_ID AS staffId,  CREATE_DATE AS createDate,  STATE AS state FROM IM_STAFF_BIGGROUP";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selGroupsByStaffId(int staffId) throws DataAccessException {
		StringBuffer sqlStr =new StringBuffer("select a.im_staff_biggroup_id as imStaffBiggroupId , ");
		
		
		sqlStr.append(" b.im_staff_biggroup_name as imStaffBiggroupName, ");
		sqlStr.append(" b.staff_id as staffId ");
		sqlStr.append(" from IM_STAFF_BIGGROUP_REAL a join IM_STAFF_BIGGROUP b on a.im_staff_biggroup_id = b.im_staff_biggroup_id ");
		sqlStr.append(" WHERE b.STATE = 1");
		sqlStr.append(" AND a.im_bg_staff_id = ").append(staffId);
		return dynamicQueryListByMap(sqlStr.toString(), null, null);
	}
	public Map selByGroupId(Map paramMap) throws DataAccessException {
		String sqlStr ="SELECT   A.IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,  A.IM_STAFF_BIGGROUP_NAME AS imStaffBiggroupName,  A.GROUP_NEWS AS groupNews , A.STAFF_ID AS staffId,  B.STAFF_NAME as staffName, A.CREATE_DATE AS createDate,  A.STATE AS state FROM IM_STAFF_BIGGROUP A JOIN UOS_STAFF B ON A.STAFF_ID = B.Staff_Id WHERE A.IM_STAFF_BIGGROUP_ID=?";
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	
public static void main(String[] args) {
	ImStaffBiggroupDAOImpl test = new ImStaffBiggroupDAOImpl();
	try {
		Map paramMap = new HashMap();
		paramMap.put("imStaffBiggroupId", 28);
		paramMap.put("state", 0);
//		paramMap.put("imStaffBiggroupName", "Ⱥtest");
//		paramMap.put("groupNews", "Ⱥtest");
//		paramMap.put("staffId", 1);
//		paramMap.put("createDate", new Date());
//		paramMap.put("state", 1);
		
		//test.selGroupsByStaffId(paramMap);
//		test.insert(paramMap);
//		test.delete(paramMap);
		System.out.println(test.selGroupsByStaffId(34357));
		//test.delete(paramMap);
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}

