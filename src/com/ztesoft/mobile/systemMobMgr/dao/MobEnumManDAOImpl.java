package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class MobEnumManDAOImpl extends BaseDAOImpl implements MobEnumManDAO {
private static final String TABLE_NAME = "MOBILE_ENUM_MANGER";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ID:id,ENUM_ID:enumId,ENUM_NAME:enumName,ENUM_IMAGE:enumImage,ENUM_TYPE:enumType,IS_SHOW:isShow,DISPLAY_INEDX:displayIndex";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("id", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ID:id,ENUM_ID:enumId,ENUM_NAME:enumName,ENUM_IMAGE:enumImage,ENUM_TYPE:enumType,IS_SHOW:isShow,DISPLAY_INEDX:displayIndex";
		String wherePatternStr = "ID:id";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ID:id";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ID AS id,  ENUM_ID AS enumId,  ENUM_NAME AS enumName,  ENUM_IMAGE AS enumImage,  ENUM_TYPE AS enumType,  IS_SHOW AS isShow,DISPLAY_INEDX as displayIndex FROM MOBILE_ENUM_MANGER WHERE ID=?";
		String wherePatternStr = "ID:id";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   ID AS id,  ENUM_ID AS enumId,  ENUM_NAME AS enumName,  ENUM_IMAGE AS enumImage,  ENUM_TYPE AS enumType,  IS_SHOW AS isShow,DISPLAY_INEDX as displayIndex FROM MOBILE_ENUM_MANGER order by DISPLAY_INEDX ");
		String wherePatternStr = null;
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	
}
