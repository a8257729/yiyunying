package com.ztesoft.eoms.im.dao;

import java.util.Map;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
public class ImStaffBiggroupRealDAOImpl extends BaseDAOImpl implements ImStaffBiggroupRealDAO {
private static final String TABLE_NAME = "IM_STAFF_BIGGROUP_REAL";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId,IM_STAFF_BIGGROUP_ID:imStaffBiggroupId,IM_BG_STAFF_ID:imBgStaffId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imStaffGroupRealId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId,IM_STAFF_BIGGROUP_ID:imStaffBiggroupId,IM_BG_STAFF_ID:imBgStaffId";
		String wherePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void deleteByGroupId(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void deleteByGroupAndStaffId(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_STAFF_BIGGROUP_ID:imStaffBiggroupId,&&:IM_BG_STAFF_ID:imBgStaffId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_GROUP_REAL_ID AS imStaffGroupRealId,  IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,  IM_BG_STAFF_ID AS imBgStaffId FROM IM_STAFF_BIGGROUP_REAL WHERE IM_STAFF_GROUP_REAL_ID=?";
		String wherePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_GROUP_REAL_ID AS imStaffGroupRealId,  IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,  IM_BG_STAFF_ID AS imBgStaffId FROM IM_STAFF_BIGGROUP_REAL";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selGroupStaffsById(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("SELECT   IM_STAFF_GROUP_REAL_ID AS imStaffGroupRealId,");
		sqlBuf.append(" IM_STAFF_BIGGROUP_ID AS imStaffBiggroupId,");
		sqlBuf.append(" IM_BG_STAFF_ID AS staffId, ");
		sqlBuf.append(" b.staff_name as staffName, ");
		sqlBuf.append(" b.username as username ");
		sqlBuf.append(" FROM IM_STAFF_BIGGROUP_REAL A join uos_staff b on a.im_bg_staff_id = b.staff_id ");
		sqlBuf.append(" WHERE A.Im_Staff_Biggroup_Id = ").append(paramMap.get("imStaffBiggroupId"));
		
		
		return populateQueryByMap(sqlBuf, Integer.parseInt(paramMap.get("start").toString()), Integer.parseInt(paramMap.get("limit").toString()));
	}
	public List selStaffsByGroupId(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("SELECT B.STAFF_NAME AS staffName,");
		sqlBuf.append(" B.STAFF_ID AS staffId,");
		sqlBuf.append(" B.USERNAME AS userName ");
		sqlBuf.append(" FROM IM_STAFF_BIGGROUP_REAL A ");
		sqlBuf.append(" JOIN UOS_STAFF B ON A.IM_BG_STAFF_ID = B.STAFF_ID ");
		sqlBuf.append(" WHERE A.IM_STAFF_BIGGROUP_ID =  ").append(paramMap.get("imStaffBiggroupId"));
		sqlBuf.append(" AND B.STATE = '1' ");
		
		return dynamicQueryListByMap(sqlBuf.toString(), paramMap, null);
		
	}
public static void main(String[] args) {
	String s = "/group/141";
	String [] strs = s.split("/");
	for(int i=0;i<strs.length;i++){
		System.out.println(strs[2]);
	}
}
}

