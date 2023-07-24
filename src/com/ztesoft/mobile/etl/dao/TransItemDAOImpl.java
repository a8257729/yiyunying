package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class TransItemDAOImpl extends BaseDAOImpl implements TransItemDAO {

	private static final String TABLE_NAME = "SQ_ETL_TRANS_OPER_ITEM";

	public long insert(Map paramMap) throws DataAccessException {
		String patternStr = "TRANS_OPER_ITEM_ID:transOperItemId,ETL_RULE_ID:etlRuleId,TARGET_COLUMNS_ID:targetColumnsId,SOURCE_COLUMNS_ID:sourceColumnsId,ITEM_DESC:itemDesc";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("transOperItemId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return nextId;
	}

	public void insertComp(Map paramMap) throws DataAccessException {
		String patternStr = "TRANS_OPER_ITEM_COMP_ID:transOperItemCompId,TRANS_OPER_ITEM_ID:transOperItemId,COMP_ID:compId";
		Long nextId = getPKFromMem("SQ_ETL_TRANS_OPER_COMP", 9);
		paramMap.put("transOperItemCompId", nextId);
		dynamicInsertByMap(paramMap, "SQ_ETL_TRANS_OPER_COMP", patternStr);

	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "TARGET_COLUMNS_ID:targetColumnsId,SOURCE_COLUMNS_ID:sourceColumnsId,ITEM_DESC:itemDesc";
		String wherePatternStr = "TRANS_OPER_ITEM_ID:transOperItemId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {

		String wherePatternStr = "TRANS_OPER_ITEM_ID:transOperItemId";
		dynamicDeleteByMap(paramMap, "SQ_ETL_TRANS_OPER_COMP", wherePatternStr);

		wherePatternStr = "TRANS_OPER_ITEM_ID:transOperItemId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);

	}

	public void deleteComp(Map paramMap) throws DataAccessException {

		String wherePatternStr = "TRANS_OPER_ITEM_COMP_ID:transOperItemCompId";
		dynamicDeleteByMap(paramMap, "SQ_ETL_TRANS_OPER_COMP", wherePatternStr);

	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer strBf = new StringBuffer();
		strBf.append("SELECT ti.TRANS_OPER_ITEM_ID AS transOperItemId,");
		strBf.append("       ti.ETL_RULE_ID        AS etlRuleId,");
		strBf.append("       ti.TARGET_COLUMNS_ID  AS targetColumnsId,");
		strBf.append("       ti.SOURCE_COLUMNS_ID  AS sourceColumnsId,");
		strBf.append("       ti.ITEM_DESC          AS itemDesc,");
		strBf.append("       mcs.column_name       AS sourceColumnsName,");
		strBf.append("       mct.column_name       AS targetColumnsName");
		strBf.append("  FROM SQ_ETL_TRANS_OPER_ITEM ti,");
		strBf.append("       METERDATA_COLUMNS      mcs,");
		strBf.append("       METERDATA_COLUMNS      mct");
		strBf.append(" WHERE ti.source_columns_id = mcs.columns_id");
		strBf.append("   and ti.target_columns_id = mct.columns_id");
		strBf.append("   and TRANS_OPER_ITEM_ID = ? ");

		String wherePatternStr = "TRANS_OPER_ITEM_ID:transOperItemId";
		return dynamicQueryObjectByMap(strBf.toString(), paramMap,
				wherePatternStr);
	}

	public Map selItemsCompAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {
		StringBuffer strBf = new StringBuffer();
		strBf
				.append("SELECT op.trans_oper_item_comp_id as transOperItemCompId,");
		strBf.append("       op.COMP_ID                 AS compId,");
		strBf.append("       cp.COMP_CLASS              AS compClass,");
		strBf.append("       cp.COMP_NAME               AS compName,");
		strBf.append("       cp.COMP_TYPE               AS compType,");
		strBf.append("       cp.COMP_EXPR               AS compExpr,");
		strBf.append("       cp.REMARK                  AS remark,");
		strBf.append("       cp.STATE                   AS state");
		strBf.append("  FROM METERDATA_COMP cp, SQ_ETL_TRANS_OPER_COMP op");
		strBf.append(" WHERE cp.comp_id = op.comp_id");
		strBf.append("   and cp.state = '10A'");
		strBf.append("   and op.trans_oper_item_id = ").append(
				MapUtils.getLong(paramMap, "transOperItemId"));

		System.out.println("selItemsCompAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);
	}

	public Map selCompsAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {
		StringBuffer strBf = new StringBuffer();
		strBf.append("SELECT COMP_ID    AS compId,");
		strBf.append("       COMP_CLASS AS compClass,");
		strBf.append("       COMP_NAME  AS compName,");
		strBf.append("       COMP_TYPE  AS compType,");
		strBf.append("       COMP_EXPR  AS compExpr,");
		strBf.append("       REMARK     AS remark,");
		strBf.append("       STATE      AS state");
		strBf.append("  FROM METERDATA_COMP");

		System.out.println("selCompsAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);
	}

	public Map selMeterDataColumnsByTab(Map paramMap, int startIndex,
			int stepSize) throws DataAccessException {
		StringBuffer strBf = new StringBuffer();
		strBf.append("select mc.columns_id  as columnId,");
		strBf.append("       mc.column_name as columnName,");
		strBf.append("       mc.column_code as columnCode,");
		strBf.append("       mc.data_type   as dataType,");
		strBf.append("       mc.remark      as remark");
		strBf.append("  from METERDATA_COLUMNS mc");
		strBf.append(" where mc.tables_id = ").append(
				MapUtils.getLong(paramMap, "tableId"));

		System.out.println("selMeterDataColumnsByTab :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);
	}

	public Map selItemsAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select ti.etl_rule_id        as etlRuleId,");
		strBf.append("       ti.trans_oper_item_id as transOperItemId,");
		strBf.append("       ti.item_desc          as itemDesc,");
		strBf.append("       ti.source_columns_id  as sourceColumnsId,");
		strBf.append("       ti.target_columns_id  as targetColumnsId,");
		strBf.append("       mcs.column_name       as sourceColumnsName,");
		strBf.append("       mct.column_name       as targetColumnsName,");
		strBf.append("       mcs.column_code       as sourceColumnsCode,");
		strBf.append("       mct.column_code       as targetColumnsCode");
		strBf.append("  from SQ_ETL_TRANS_OPER_ITEM ti,");
		strBf.append("       METERDATA_COLUMNS      mcs,");
		strBf.append("       METERDATA_COLUMNS      mct");
		strBf.append(" where ti.source_columns_id = mcs.columns_id");
		strBf.append("   and ti.target_columns_id = mct.columns_id");
		strBf.append("   and ti.etl_rule_id = ").append(
				MapUtils.getLong(paramMap, "etlRuleId"));

		System.out.println("selRulesAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}
}
