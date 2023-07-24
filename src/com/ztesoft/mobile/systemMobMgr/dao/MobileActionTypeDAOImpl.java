package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileActionTypeDAOImpl extends BaseDAOImpl implements MobileActionTypeDAO {

	private static final String TABLE_NAME = "MOBILE_ACTION_TYPE";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ACTION_ID:actionId,TYPE_NAME:typeName,ACTION_NAME:actionName,ACTION_CODE:actionCode,ACTION_FUNTION:actionFuntion";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("actionId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "TYPE_NAME:typeName,ACTION_NAME:actionName,ACTION_CODE:actionCode,ACTION_FUNTION:actionFuntion";
		String wherePatternStr = "ACTION_ID:actionId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ACTION_ID:actionId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   ACTION_ID AS actionId,  TYPE_NAME AS typeName,  ACTION_NAME AS actionName,  ACTION_CODE AS actionCode,  ACTION_FUNTION AS actionFuntion FROM MOBILE_ACTION_TYPE  ");
		if (!MapUtils.getString(paramMap, "typeName").equals("0")) {
			sqlStr.append(" WHERE TYPE_NAME ='"+MapUtils.getString(paramMap, "typeName")+"'");
		}
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ACTION_ID AS actionId,  TYPE_NAME AS typeName,  ACTION_NAME AS actionName,  ACTION_CODE AS actionCode,  ACTION_FUNTION AS actionFuntion FROM MOBILE_ACTION_TYPE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

}
