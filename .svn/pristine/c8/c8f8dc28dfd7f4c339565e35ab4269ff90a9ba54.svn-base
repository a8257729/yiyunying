package com.ztesoft.eoms.im.dao;

import java.util.Map;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
public class ImStaffGroupRealDAOImpl extends BaseDAOImpl implements ImStaffGroupRealDAO {
private static final String TABLE_NAME = "IM_STAFF_GROUP_REAL";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId,IM_STAFF_GROUP_ID:imStaffGroupId,IM_FRI_STAFF_ID:imFriStaffId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("imStaffGroupRealId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId,IM_STAFF_GROUP_ID:imStaffGroupId,IM_FRI_STAFF_ID:imFriStaffId";
		String wherePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   IM_STAFF_GROUP_REAL_ID AS imStaffGroupRealId,  IM_STAFF_GROUP_ID AS imStaffGroupId,  IM_FRI_STAFF_ID AS imFriStaffId FROM IM_STAFF_GROUP_REAL WHERE IM_STAFF_GROUP_REAL_ID=?";
		String wherePatternStr = "IM_STAFF_GROUP_REAL_ID:imStaffGroupRealId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selStaffsByGroupId(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr =new StringBuffer("SELECT   IM_STAFF_GROUP_REAL_ID AS imStaffGroupRealId,");
		sqlStr.append(" IM_STAFF_GROUP_ID AS imStaffGroupId, ");
		sqlStr.append(" IM_FRI_STAFF_ID AS imFriStaffId, ");
		sqlStr.append(" b.staff_name AS staffName,");
		sqlStr.append(" b.username as userName");
		sqlStr.append(" FROM IM_STAFF_GROUP_REAL a join uos_staff b on a.im_fri_staff_id = b.staff_id");
		sqlStr.append(" where b.state = '1' ");
		if(paramMap.containsKey("imStaffGroupId")&&paramMap.get("imStaffGroupId")!=null){
			sqlStr.append(" and a.im_staff_group_id = ").append(paramMap.get("imStaffGroupId"));
		}else{
			return new ArrayList();
		}
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, null);
	}
	//移动好友到分组
	public void moveFriToGroup(Map paramMap)throws DataAccessException{
		String updatePatternStr = "IM_STAFF_GROUP_ID:toImStaffGroupId";
		String wherePatternStr = "IM_FRI_STAFF_ID:imFriStaffId,&&:IM_STAFF_GROUP_ID:fromImStaffGroupId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	//删除好友
	public void delFriFromGroup(Map paramMap)throws DataAccessException{
		String wherePatternStr = "IM_FRI_STAFF_ID:imFriStaffId,&&:IM_STAFF_GROUP_ID:fromImStaffGroupId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	//根据用户名查询好友ID 添加好友(1)
	public Map selStaffByUserName(Map paramMap) throws DataAccessException {
		String sqlStr ="select STAFF_ID as staffId from uos_staff where state = '1' and USERNAME = ?";
		String wherePatternStr = "USERNAME:userName";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	//查询好友是否存在
	public Map selStaff(Map paramMap) throws DataAccessException {
		String sqlStr ="select a.im_staff_group_id as imStaffGroupId,a.im_fri_staff_id as imFriStaffId from IM_STAFF_GROUP_REAL a join uos_staff b on a.im_fri_staff_id = b.staff_id join im_staff_group c on a.im_staff_group_id = c.im_staff_group_id where b.state = '1' and c.staff_id = ? and b.username = ?";
		String wherePatternStr = "STAFF_ID:staffId,USERNAME:userName";
		
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	
	
public static void main(String[] args) {
	ImStaffGroupRealDAOImpl test = new ImStaffGroupRealDAOImpl();
	try {
		  Map paramMap = new HashMap();
		//paramMap.put("imStaffGroupRealId", "1");
		paramMap.put("imStaffGroupId", new Long(601));
		//paramMap.put("imFriStaffId", new Long(38101));
		//paramMap.put("imFriStaffId", new Long(34357));
//		test.insert(paramMap);
//		test.update(paramMap);
//		test.delete(paramMap);
		System.out.println(test.selStaffsByGroupId(paramMap));
//		System.out.println(test.selAll(paramMap));
	
	} catch (DataAccessException e) {
		e.printStackTrace();
		}
	}
}

