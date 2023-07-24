package com.ztesoft.mobile.etl.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class SqEtlCleanRuleDAOImpl extends BaseDAOImpl implements SqEtlCleanRuleDAO {
private static final String TABLE_NAME = "SQ_ETL_CLEAN_RULE";
	public Long insert(Map paramMap) throws DataAccessException {
		String patternStr = "SQ_ETL_CLEAN_RULE_ID:sqEtlCleanRuleId,ETL_RULE_ID:etlRuleId,ETL_CLEAN_COMP_ID:etlCleanCompId,SQ_ETL_CLEAN_RULE_NAME:sqEtlCleanRuleName,CLEAN_COL_NAME:cleanColName,CREATE_DATE:createDate,STATE_DATE:stateDate,STATE:state,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("sqEtlCleanRuleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return nextId;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "ETL_CLEAN_COMP_ID:etlCleanCompId,SQ_ETL_CLEAN_RULE_NAME:sqEtlCleanRuleName,CLEAN_COL_NAME:cleanColName,STATE_DATE:stateDate,STATE:state,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo";
		String wherePatternStr = "SQ_ETL_CLEAN_RULE_ID:sqEtlCleanRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STATE_DATE:stateDate,STATE:state,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName";
		String wherePatternStr = "SQ_ETL_CLEAN_RULE_ID:sqEtlCleanRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SQ_ETL_CLEAN_RULE_ID AS sqEtlCleanRuleId,  ETL_RULE_ID AS etlRuleId,  ETL_CLEAN_COMP_ID AS etlCleanCompId,  SQ_ETL_CLEAN_RULE_NAME AS sqEtlCleanRuleName,  CLEAN_TB_NAME AS cleanTbName,  CLEAN_COL_NAME AS cleanColName,  CREATE_DATE AS createDate,  STATE_DATE AS stateDate,  STATE AS state,  OPER_MAN_ID AS operManId,  OPER_MAN_NAME AS operManName,  MEMO AS memo FROM SQ_ETL_CLEAN_RULE WHERE SQ_ETL_CLEAN_RULE_ID=?";
		String wherePatternStr = "SQ_ETL_CLEAN_RULE_ID:sqEtlCleanRuleId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   SQ_ETL_CLEAN_RULE_ID AS sqEtlCleanRuleId,  ETL_RULE_ID AS etlRuleId,  ETL_CLEAN_COMP_ID AS etlCleanCompId,  SQ_ETL_CLEAN_RULE_NAME AS sqEtlCleanRuleName,  CLEAN_TB_NAME AS cleanTbName,  CLEAN_COL_NAME AS cleanColName,  CREATE_DATE AS createDate,  STATE_DATE AS stateDate,  STATE AS state,  OPER_MAN_ID AS operManId,  OPER_MAN_NAME AS operManName,  MEMO AS memo FROM SQ_ETL_CLEAN_RULE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public Map selPagingByMap(Map paramMap) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer("SELECT   A.SQ_ETL_CLEAN_RULE_ID AS sqEtlCleanRuleId,");
		sqlBuf.append(" A.ETL_RULE_ID AS etlRuleId,A.ETL_CLEAN_COMP_ID AS etlCleanCompId,");
		sqlBuf.append(" A.SQ_ETL_CLEAN_RULE_NAME AS sqEtlCleanRuleName,");
		sqlBuf.append(" A.CLEAN_COL_NAME AS cleanColName,");
		sqlBuf.append(" A.CREATE_DATE AS createDate,");
		sqlBuf.append(" A.STATE_DATE AS stateDate,");
		sqlBuf.append(" A.STATE AS state,");
		sqlBuf.append(" A.OPER_MAN_ID AS operManId,");
		sqlBuf.append(" A.OPER_MAN_NAME AS operManName,");
		sqlBuf.append(" A.MEMO AS memo,");
		sqlBuf.append(" B.CLEAN_COMP_NAME as cleanCompName");
		sqlBuf.append(" FROM SQ_ETL_CLEAN_RULE A");
		sqlBuf.append(" JOIN SQ_ETL_CLEAN_COMP B ON A.ETL_CLEAN_COMP_ID = b.sq_etl_clean_comp_id");
		sqlBuf.append(" Where A.state = '10A' AND B.State = '10A'");

		if(paramMap.containsKey("etlRuleId")){
        	if(MapUtils.getObject(paramMap, "etlRuleId") != null &&
             	   !MapUtils.getString(paramMap, "etlRuleId").trim().equals("")){
        		sqlBuf.append(" and A.ETL_RULE_ID  = ")
     		    		.append(MapUtils.getString(paramMap, "etlRuleId"));
             }
        }
		sqlBuf.append(" order by STATE_DATE DESC ");
		return populateQueryByMap(sqlBuf, MapUtils.getIntValue(paramMap,
		"pageIndex"), MapUtils.getIntValue(paramMap, "pageSize"));
	}
	
public static void main(String[] args) {
	SqEtlCleanRuleDAOImpl test = new SqEtlCleanRuleDAOImpl();
	try {
		  Map paramMap = new HashMap();
		paramMap.put("sqEtlCleanRuleId", "1");
		paramMap.put("etlRuleId", "1");
		paramMap.put("etlCleanCompId", "1");
		paramMap.put("sqEtlCleanRuleName", "1");
		paramMap.put("cleanTbName", "1");
		paramMap.put("cleanColName", "1");
		paramMap.put("createDate", "1");
		paramMap.put("stateDate", "1");
		paramMap.put("state", "1");
		paramMap.put("operManId", "1");
		paramMap.put("operManName", "1");
		paramMap.put("memo", "1");
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
