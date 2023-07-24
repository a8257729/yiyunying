package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class MoblieFildNodeDAOImpl extends BaseDAOImpl implements MoblieFildNodeDAO {
private static final String TABLE_NAME = "MOBILE_FILED_NODE";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "NODE_ID:nodeId,FORM_ID:formId,NODE_NAME:nodeName,NODE_LABEL:nodeLabel,IS_LEAF:isLeaf,SEQ_ID:seqId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("nodeId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "NODE_ID:nodeId,NODE_NAME:nodeName,NODE_LABEL:nodeLabel,IS_LEAF:isLeaf,SEQ_ID:seqId";
		String wherePatternStr = "NODE_ID:nodeId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "NODE_ID:nodeId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   NODE_ID AS nodeId,  FORM_ID AS formId,  NODE_NAME AS nodeName,  NODE_LABEL AS nodeLabel,  IS_LEAF AS isLeaf,SEQ_ID as seqId FROM MOBILE_FILED_NODE WHERE 1=1 ");
		if (!MapUtils.getString(paramMap, "formId").equals("0")) {
			sqlStr.append(" AND FORM_ID ="+MapUtils.getString(paramMap, "formId"));
		}
	
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   NODE_ID AS nodeId,  FORM_ID AS formId,  NODE_NAME AS nodeName,  NODE_LABEL AS nodeLabel,  IS_LEAF AS isLeaf,SEQ_ID as seqId FROM MOBILE_FILED_NODE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT NODE_ID AS nodeId from MOBILE_FILED_NODE " +
							"WHERE NODE_NAME='"+MapUtils.getString(paramMap, "nodeName")+"' AND FORM_ID = '"+MapUtils.getString(paramMap, "formId")+"'";
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
}
