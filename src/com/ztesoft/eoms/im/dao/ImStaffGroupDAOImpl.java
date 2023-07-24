package com.ztesoft.eoms.im.dao;

import java.util.Map;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
public class ImStaffGroupDAOImpl extends BaseDAOImpl implements ImStaffGroupDAO {
private static final String TABLE_NAME = "IM_STAFF_GROUP";
	public Map insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_STAFF_GROUP_ID:imStaffGroupId,IM_STAFF_GROUP_NAME:imStaffGroupName,STAFF_ID:staffId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imStaffGroupId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return paramMap;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IM_STAFF_GROUP_ID:imStaffGroupId,IM_STAFF_GROUP_NAME:imStaffGroupName,STAFF_ID:staffId";
		String wherePatternStr = "IM_STAFF_GROUP_ID:imStaffGroupId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_STAFF_GROUP_ID:imStaffGroupId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_GROUP_ID AS imStaffGroupId,  IM_STAFF_GROUP_NAME AS imStaffGroupName,  STAFF_ID AS staffId FROM IM_STAFF_GROUP WHERE IM_STAFF_GROUP_ID=?";
		String wherePatternStr = "IM_STAFF_GROUP_ID:imStaffGroupId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	//查询自己所有的好友分组
	public List selGroupByStaff(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer("SELECT   IM_STAFF_GROUP_ID AS imStaffGroupId,  IM_STAFF_GROUP_NAME AS imStaffGroupName,  STAFF_ID AS staffId FROM IM_STAFF_GROUP ");
		if(paramMap.containsKey("staffId")&&paramMap.get("staffId")!=null){
			sqlStr.append(" where STAFF_ID = ").append(paramMap.get("staffId"));
		}else{
			return new ArrayList();
		}
	
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, null);
	}
	
public static void main(String[] args) {
	ImStaffGroupDAOImpl test = new ImStaffGroupDAOImpl();
	try {
		  Map paramMap = new HashMap();
		//paramMap.put("imStaffGroupId", new Long(2));
		paramMap.put("imStaffGroupName", "我的好友");
		paramMap.put("staffId", new Long(4));
		test.insert(paramMap);
//		test.update(paramMap);
//		test.delete(paramMap);
//		System.out.println(test.selById(paramMap));
//		System.out.println(test.selAll(paramMap));
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}

