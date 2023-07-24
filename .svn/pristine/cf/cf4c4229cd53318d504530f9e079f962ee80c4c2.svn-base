package com.ztesoft.mobile.etl.dao;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class EtlJdbcRuleDAOImpl extends BaseDAOImpl implements EtlJdbcRuleDAO {

	private final static Logger logger = Logger.getLogger(EtlRuleDAOImpl.class);
	private static final String TABLE_NAME = "SQ_ETL_RULE";

	private static final String TABLE_NAME_EXT = "SQ_ETL_JDBC_FETCH_RULE";

	public void insert(Map paramMap) throws DataAccessException, UOSException {

		// '抽取规则 ''''000''''
		paramMap.put("etlType", "005");
		// '10A有效 10P 无效';
		paramMap.put("state", "10A");

		paramMap.put("createDate", new Date());

		paramMap.put("stateDate", new Date());

		String patternStr = "ETL_RULE_ID:etlRuleId,ETL_RULE_NAME:etlRuleName,ETL_TYPE:etlType,STATE:state,CREATE_DATE:createDate,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("etlRuleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);

		patternStr = "ETL_RULE_ID:etlRuleId,SOURCE_DS_ID:sourceDsId,SOURCE_TB_NAME:sourceTbName,TARGET_DS_ID:targetDsId,TARGET_TB_ID:targetTbId,FETCH_MODE:fetchMode,FETCH_SCRIPT:fetchScript";

		dynamicInsertByMap(paramMap, TABLE_NAME_EXT, patternStr);

	}

	public void update(Map paramMap) throws DataAccessException {
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "ETL_RULE_NAME:etlRuleName,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);

		updatePatternStr = "SOURCE_DS_ID:sourceDsId,SOURCE_TB_NAME:sourceTbName,TARGET_DS_ID:targetDsId,TARGET_TB_ID:targetTbId,FETCH_MODE:fetchMode,FETCH_SCRIPT:fetchScript";

		dynamicUpdateByMap(paramMap, TABLE_NAME_EXT, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		// '10A有效 10P 无效';
		paramMap.put("state", "10P");
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "STATE:state,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";

		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);

	}

	public Map queryAllEtlRule(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT er.ETL_RULE_ID       AS etlRuleId,");
		sqlStr.append("       er.ETL_RULE_NAME     AS etlRuleName,");
		sqlStr.append("       er.ETL_TYPE          AS etlType,");
		sqlStr.append("       er.STATE             AS state,");
		sqlStr
				.append("       to_char(er.CREATE_DATE,'yyyy-MM-dd HH24:MM:SS')       AS createDate,");
		sqlStr
				.append("       to_char(er.STATE_DATE,'yyyy-MM-dd HH24:MM:SS')        AS stateDate,");
		sqlStr.append("       er.OPER_MAN_ID       AS operManId,");
		sqlStr.append("       er.OPER_MAN_NAME     AS operManName,");
		sqlStr.append("       er.MEMO              AS memo,");
		sqlStr.append("		  fr.SOURCE_DS_ID      as sourceDsId,");
		sqlStr.append("		  sds.DS_NAME          as sourceDsName,");
		sqlStr.append("		  fr.SOURCE_TB_NAME    as sourceTbName,");
		sqlStr.append("		  fr.TARGET_DS_ID      as targetDsId,");
		sqlStr.append("		  tds.DS_NAME          as targetDsName,");
		sqlStr.append("		  fr.target_tb_id      as targetTbId,");
		sqlStr.append("		  mt.table_name        as targetTbName,");
		sqlStr.append("		  mt.table_code        as targetTbCode,");
		sqlStr.append("       fr.FETCH_MODE        as fetchMode,");
		sqlStr
				.append("       decode(fr.fetch_mode,1,'全量',2,'增量','') as fetchModeText,");
		sqlStr.append("       fr.FETCH_SCRIPT      as fetchScript");
		sqlStr.append("  FROM SQ_ETL_RULE er");
		sqlStr
				.append("  join SQ_ETL_JDBC_FETCH_RULE fr on er.etl_rule_id = fr.etl_rule_id");
		sqlStr.append("                                and er.state = '10A'");
		sqlStr
				.append("  left join SQ_DATA_SOURCE sds on fr.source_ds_id = sds.ds_id");
		sqlStr.append("                             and sds.state = '10A'");
		sqlStr
				.append("  left join SQ_DATA_SOURCE tds on fr.target_ds_id = tds.ds_id");
		sqlStr.append("                             and tds.state = '10A'");
		sqlStr
				.append("  left join meterdata_tables mt on fr.target_tb_id = mt.tables_id");
		sqlStr.append("                             and mt.state = '10A'");
		sqlStr.append(" where er.state = '10A' ");

		if (paramMap != null) {
			if (paramMap.containsKey("etlType")) {
				String etlType = (String) MapUtils.getObject(paramMap,
						"etlType");
				if (etlType != null && !etlType.trim().equals("")) {
					sqlStr.append(" AND er.etl_type = '").append(etlType)
							.append("'");
				}
			}

			if (paramMap.containsKey("etlRuleName")) {
				String servicesName = (String) MapUtils.getObject(paramMap,
						"etlRuleName");
				if (servicesName != null && !servicesName.trim().equals("")) {
					sqlStr.append(" AND er.etl_rule_name LIKE '%").append(
							servicesName).append("%'");
				}
			}

			if (paramMap.containsKey("sourceDsId")) {
				String sourceDsId = (String) MapUtils.getObject(paramMap,
						"sourceDsId");
				if (sourceDsId != null && !sourceDsId.trim().equals("")) {
					sqlStr.append(" and fr.source_ds_id = " + sourceDsId);
				}
			}

			if (paramMap.containsKey("sourceTableName")) {
				String sourceTableName = (String) MapUtils.getObject(paramMap,
						"sourceTableName");
				if (sourceTableName != null
						&& !sourceTableName.trim().equals("")) {
					sqlStr.append(" AND fr.source_tb_name LIKE '%").append(
							sourceTableName).append("%'");
				}
			}

			if (paramMap.containsKey("targetDsId")) {
				String targetDsId = (String) MapUtils.getObject(paramMap,
						"targetDsId");
				if (targetDsId != null && !targetDsId.trim().equals("")) {
					sqlStr.append(" and fr.target_ds_id = " + targetDsId);
				}
			}

			if (paramMap.containsKey("targetTableName")) {
				String targetTableName = (String) MapUtils.getObject(paramMap,
						"targetTableName");
				if (targetTableName != null
						&& !targetTableName.trim().equals("")) {
					sqlStr.append(" AND mt.table_code LIKE '%").append(
							targetTableName).append("%'");
				}
			}
		}

		sqlStr.append(" ORDER BY  er.state_date DESC");

		logger.debug("sqlStr===>" + sqlStr.toString());

		return populateQueryByMap(sqlStr, startIndex, stepSize);

	}

}
