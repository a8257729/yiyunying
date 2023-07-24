package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class MobCompntAttributeDAOImpl extends BaseDAOImpl implements MobCompntAttributeDAO {
	private static final String TABLE_NAME = "MOBILE_COMPONENT_ATTRIBUTE";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "ATTR_ID:attrId,TYPE_NAME:typeName,ATTR_CODE:attrCode,ATTR_NAME:attrName";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("attrId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ATTR_ID:attrId,TYPE_NAME:typeName,ATTR_CODE:attrCode,ATTR_NAME:attrName";
		String wherePatternStr = "ATTR_ID:attrId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ATTR_ID:attrId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   ATTR_ID AS attrId,  TYPE_NAME AS typeName,  ATTR_CODE AS attrCode,  ATTR_NAME AS attrName FROM MOBILE_COMPONENT_ATTRIBUTE ");
		if (!MapUtils.getString(paramMap, "typeName").equals("0")) {
			sqlStr.append(" WHERE TYPE_NAME ='"+MapUtils.getString(paramMap, "typeName")+"'");
		}
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   ATTR_ID AS attrId,  TYPE_NAME AS typeName,  ATTR_CODE AS attrCode,  ATTR_NAME AS attrName FROM MOBILE_COMPONENT_ATTRIBUTE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
}
