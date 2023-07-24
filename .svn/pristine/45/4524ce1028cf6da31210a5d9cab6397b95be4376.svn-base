package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileStatTransferDAOImpl extends BaseDAOImpl implements MobileStatTransferDAO {

	private static final String TABLE_NAME = "MOBILE_STAT_TRANSFER";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "STAT_ID:statId,FORM_ID:formId,FILED_ID:nextFiledId,NEXT_FORM_ID:nextFormId,OPERATE_TYPE:operateType,OPERATE_NAME:operateName";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("statId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STAT_ID:statId,FORM_ID:formId,FILED_ID:nextFiledId,NEXT_FORM_ID:nextFormId,OPERATE_TYPE:operateType,OPERATE_NAME:operateName";
		String wherePatternStr = "STAT_ID:statId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "STAT_ID:statId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT t.STAT_ID AS statId, t.FORM_ID AS formId, t.FILED_ID AS nextFiledId, t.NEXT_FORM_ID AS nextFormId, t.OPERATE_TYPE AS operateType, t.OPERATE_NAME AS operateName, a.FORM_NAME AS nextFormIdName, b.FILED_NAME AS nextFiledIdName");
		sqlStr.append(" FROM MOBILE_STAT_TRANSFER t JOIN MOBILE_JSON_CREATE a ON t.NEXT_FORM_ID=a.FORM_ID JOIN MOBILE_FIELD_INFO b ON t.FILED_ID=b.FILED_ID");
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" WHERE t.FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   STAT_ID AS statId,  FORM_ID AS formId,  FILED_ID AS nextFiledId,  NEXT_FORM_ID AS nextFormId,  OPERATE_TYPE AS operateType,  OPERATE_NAME AS operateName FROM MOBILE_STAT_TRANSFER";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT STAT_ID AS statId from MOBILE_STAT_TRANSFER " 
			+ "WHERE OPERATE_NAME='"+MapUtils.getString(paramMap, "operateName")+"' AND FORM_ID = '"+MapUtils.getString(paramMap, "formId")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
}

