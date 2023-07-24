package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MoblieStatDisplayDAOImpl extends BaseDAOImpl implements MoblieStatDisplayDAO {

	private static final String TABLE_NAME = "MOBILE_STAT_DISPLAY";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "STAT_ID:statId,FORM_ID:formId,STAT_CYCLE:statCycle,STAT_RANGE:statRange,IS_MAIN_PAGE:isMainPage,STAT_TYPE:statType,STAT_CYCLE_DISPLAY_NAME:statCycleDisplayName,PRV_DISPLAY_NAME:prvDisplayName,CITY_DISPLAY_NAME:cityDisplayName,PRV_NEXT_FORM_ID:prvNextFormId,CITY_NEXT_FORM_ID:cityNextFormId,ROW_COUNT:rowCount,IS_SUM:isSum";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("statId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STAT_ID:statId,STAT_CYCLE:statCycle,STAT_RANGE:statRange,IS_MAIN_PAGE:isMainPage,STAT_TYPE:statType,STAT_CYCLE_DISPLAY_NAME:statCycleDisplayName,PRV_DISPLAY_NAME:prvDisplayName,CITY_DISPLAY_NAME:cityDisplayName,PRV_NEXT_FORM_ID:prvNextFormId,CITY_NEXT_FORM_ID:cityNextFormId,ROW_COUNT:rowCount,IS_SUM:isSum";
		String wherePatternStr = "STAT_ID:statId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "STAT_ID:statId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT  STAT_ID AS statId, FORM_ID AS formId, STAT_CYCLE AS statCycle, STAT_RANGE AS statRange, IS_MAIN_PAGE AS isMainPage, STAT_TYPE AS statType, STAT_CYCLE_DISPLAY_NAME AS statCycleDisplayName, PRV_DISPLAY_NAME AS prvDisplayName, CITY_DISPLAY_NAME AS cityDisplayName, PRV_NEXT_FORM_ID AS prvNextFormId, CITY_NEXT_FORM_ID AS cityNextFormId, ROW_COUNT AS rowCount, IS_SUM AS isSum");
		sqlStr.append(" FROM MOBILE_STAT_DISPLAY");
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" WHERE FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   STAT_ID AS statId,  FORM_ID AS formId,  STAT_CYCLE AS statCycle,  STAT_RANGE AS statRange,  IS_MAIN_PAGE AS isMainPage,  STAT_TYPE AS statType,  STAT_CYCLE_DISPLAY_NAME AS statCycleDisplayName,  PRV_DISPLAY_NAME AS prvDisplayName,  CITY_DISPLAY_NAME AS cityDisplayName,  PRV_NEXT_FORM_ID AS prvNextFormId,  CITY_NEXT_FORM_ID AS cityNextFormId,  ROW_COUNT AS rowCount,  IS_SUM AS isSum FROM MOBILE_STAT_DISPLAY";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}


	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT STAT_ID AS statId from MOBILE_STAT_DISPLAY " 
			+ "WHERE STAT_CYCLE='"+MapUtils.getString(paramMap, "statCycle")+"' AND FORM_ID = '"+MapUtils.getString(paramMap, "formId")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}

	

}
