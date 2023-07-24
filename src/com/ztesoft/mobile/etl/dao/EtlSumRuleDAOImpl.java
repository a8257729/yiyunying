package com.ztesoft.mobile.etl.dao;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class EtlSumRuleDAOImpl extends BaseDAOImpl implements EtlSumRuleDAO {

	private final static Logger logger = Logger.getLogger(EtlRuleDAOImpl.class);
	private static final String TABLE_NAME = "SQ_ETL_RULE";

	private static final String TABLE_NAME_SUM = "SQ_ETL_SUM_RULE";

	public void insert(Map paramMap) throws DataAccessException, UOSException {

		// '抽取规则 ''''000''''
		paramMap.put("etlType", "004");
		// '10A有效 10P 无效';
		paramMap.put("state", "10A");

		paramMap.put("createDate", new Date());

		paramMap.put("stateDate", new Date());

		String patternStr = "ETL_RULE_ID:etlRuleId,ETL_RULE_NAME:etlRuleName,ETL_TYPE:etlType,STATE:state,CREATE_DATE:createDate,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo,TACHE_ID:tacheId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("etlRuleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		// PROC_NAME:procName
		patternStr = "ETL_RULE_ID:etlRuleId,PROC_NAME:procName";

		dynamicInsertByMap(paramMap, TABLE_NAME_SUM, patternStr);

	}

	public void update(Map paramMap) throws DataAccessException {
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "ETL_RULE_NAME:etlRuleName,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo,TACHE_ID:tacheId";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
		// PROC_NAME:procName
		updatePatternStr = "PROC_NAME:procName";

		dynamicUpdateByMap(paramMap, TABLE_NAME_SUM, updatePatternStr,
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
		sqlStr.append("select er.etl_rule_id as etlRuleId,");
		sqlStr.append("       er.etl_rule_name as etlRuleName,");
		sqlStr.append("       er.tache_id as tacheId,");
		sqlStr.append("       ut.tache_name as tacheName,");
		sqlStr.append("       er.etl_type as etlType,");
		sqlStr.append("       er.state as state,");
		sqlStr
				.append("       to_char(er.create_date, 'yyyy-MM-dd HH24:MI:SS') as createDate,");
		sqlStr
				.append("       to_char(er.state_date, 'yyyy-MM-dd HH24:MI:SS') as stateDate,");
		sqlStr.append("       er.oper_man_id as operManId,");
		sqlStr.append("       er.oper_man_name as operManName,");
		sqlStr.append("       er.memo as memo,");
		sqlStr.append("       sr.proc_name as procName");
		sqlStr.append("  from SQ_ETL_RULE er, SQ_ETL_SUM_RULE sr,UOS_TACHE UT");
		sqlStr.append(" where er.etl_rule_id = sr.etl_rule_id AND ER.TACHE_ID = UT.ID(+)");
		sqlStr.append("   and er.state = '10A'");

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

			if (paramMap.containsKey("procName")) {
				String procName = (String) MapUtils.getObject(paramMap,
						"procName");
				if (procName != null && !procName.trim().equals("")) {
					sqlStr.append(" AND sr.proc_name LIKE '%").append(procName)
							.append("%'");
				}
			}

		}

		sqlStr.append(" ORDER BY  er.state_date DESC");

		logger.debug("sqlStr===>" + sqlStr.toString());

		return populateQueryByMap(sqlStr, startIndex, stepSize);

	}

}
