package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileSearchTabDAOImpl extends BaseDAOImpl implements MobileSearchTabDAO {

	private static final String TABLE_NAME = "MOBILE_SEARCH_TAB";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "TAB_ID:tabId,FORM_ID:formId,TAB_NAME:tabName,NEXT_FORM_ID:nextFormId,SEQ_ID:seqId,IS_SHOW:isShow,POSITION:position,SHOW_HEIGHT:showHeight,IS_BG:isBg";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("tabId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "TAB_ID:tabId,TAB_NAME:tabName,NEXT_FORM_ID:nextFormId,SEQ_ID:seqId,IS_SHOW:isShow,POSITION:position,SHOW_HEIGHT:showHeight,IS_BG:isBg";
		String wherePatternStr = "TAB_ID:tabId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "TAB_ID:tabId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   t.TAB_ID AS tabId,  t.FORM_ID AS formId,  t.TAB_NAME AS tabName,  t.NEXT_FORM_ID AS nextFormId,  c.FORM_NAME AS nextFormIdName,  t.SEQ_ID AS seqId,t.IS_SHOW as isShow,  t.POSITION AS position, SHOW_HEIGHT AS showHeight,  IS_BG AS isBg   ");
		sqlStr.append("FROM MOBILE_SEARCH_TAB t JOIN MOBILE_JSON_CREATE c ON t.NEXT_FORM_ID=c.FORM_ID");
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" WHERE t.FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}		
		sqlStr.append(" order by t.SEQ_ID asc ");
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   t.TAB_ID AS tabId,  t.FORM_ID AS formId,  t.TAB_NAME AS tabName,  t.NEXT_FORM_ID AS nextFormId,  c.FORM_NAME AS nextFormIdName,  t.SEQ_ID AS seqId,t.IS_SHOW as isShow,  t.POSITION AS position,  SHOW_HEIGHT AS showHeight,  IS_BG AS isBg  " +
				"FROM MOBILE_SEARCH_TAB t JOIN MOBILE_JSON_CREATE c ON t.NEXT_FORM_ID=c.FORM_ID";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT TAB_ID AS tabId FROM MOBILE_SEARCH_TAB "
				+ "WHERE TAB_NAME='"
				+ MapUtils.getString(paramMap, "tabName")
				+ "' AND FORM_ID = '"
				+ MapUtils.getString(paramMap, "formId")
				+ "'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}

}
