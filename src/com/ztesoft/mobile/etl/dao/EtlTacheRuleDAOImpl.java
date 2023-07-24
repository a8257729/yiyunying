package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class EtlTacheRuleDAOImpl extends BaseDAOImpl implements EtlTacheRuleDAO {

	private static final String TABLE_NAME = "SQ_ETL_TACHE_RULE";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "RULE_ID:ruleId,TACHE_ID:tacheId,RULE_NAME:ruleName,PARTY_TYPE:partyType,PARTY_ID:partyId,PARTY_NAME:partyName";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("ruleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "RULE_NAME:ruleName,PARTY_TYPE:partyType,PARTY_ID:partyId,PARTY_NAME:partyName";
		String wherePatternStr = "RULE_ID:ruleId";
		
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "RULE_ID:ruleId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}


	/**
	 * 根据父级节点查询任务目录
	 */
	public List selByParentId(long parentId) throws DataAccessException {
		StringBuffer sBuff = new StringBuffer();
		sBuff.append("SELECT ID                      AS id,");
		sBuff.append("       TACHE_CATALOG_NAME      AS text,");
		sBuff.append("       PARENT_TACHE_CATALOG_ID AS parentId");
		sBuff.append("  FROM UOS_TACHE_CATALOG");
		sBuff.append(" WHERE STATE = '10A'");
		sBuff.append(" AND PARENT_TACHE_CATALOG_ID =  ").append(parentId);
		System.out.println("selByParentId" + sBuff.toString());

		return dynamicQueryListByMap(sBuff.toString(), null, null);

	}

	public Map queryAllEtlTacheRule(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select t.rule_id    as ruleId,");
		sqlStr.append("       t.tache_id   as tacheId,");
		sqlStr.append("       t.rule_name  as ruleName,");
		sqlStr.append("       t.party_type as partyType,");
		sqlStr.append("       t.party_id   as partyId,");
		sqlStr.append("       t.party_name as partyName");
		sqlStr.append("  from SQ_ETL_TACHE_RULE t");
		sqlStr.append(" where t.tache_id = ").append(
				MapUtils.getLong(paramMap, "tacheId"));
		System.out.println("queryAllEtlTacheRule:" + sqlStr.toString());
		return populateQueryByMap(sqlStr, startIndex, stepSize);

	}

	public Map queryAllEtlTache(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select t.id as tacheId,");
		sqlStr.append("       t.tache_catalog_id as tacheCatalogId,");
		sqlStr.append("       t.tache_name as tacheName,");
		sqlStr.append("       t.tache_code as tacheCode,");
		sqlStr.append("       t.current_edition as edition,");
		sqlStr.append("       t.state as state,");
		sqlStr
				.append("		  to_char(t.state_date, 'yyyy-mm-dd hh24:mi:ss') as stateDate");
		sqlStr.append("  from uos_tache t ");
		sqlStr.append(" where t.state = '10A' ");

		if (paramMap != null) {
			if (paramMap.containsKey("tacheCatalogId")) {
				long tacheCatalogId = MapUtils.getLong(paramMap,
						"tacheCatalogId");
				if (tacheCatalogId != 0) {
					sqlStr.append("  and t.tache_catalog_id = ").append(
							tacheCatalogId);
				}
			}

			if (paramMap.containsKey("tacheName")) {
				String tacheName = MapUtils.getString(paramMap, "tacheName");
				if (tacheName != null && !tacheName.trim().equals("")) {
					sqlStr.append("  and t.tache_name LIKE '%").append(
							tacheName).append("%'");
				}
			}

			if (paramMap.containsKey("tacheCode")) {
				String tacheCode = MapUtils.getString(paramMap, "tacheCode");
				if (tacheCode != null && !tacheCode.trim().equals("")) {
					sqlStr.append("  and t.tache_code LIKE '%").append(
							tacheCode).append("%'");
				}
			}
		}

		sqlStr.append(" order by t.state_date desc ");
		System.out.println("queryAllEtlTache:" + sqlStr.toString());
		return populateQueryByMap(sqlStr, startIndex, stepSize);

	}
}
