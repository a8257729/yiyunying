package com.ztesoft.mobile.etl.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class TransConfigDAOImpl extends BaseDAOImpl implements TransConfigDAO {

	private static final String TABLE_NAME_RULE = "SQ_ETL_RULE";

	private static final String TABLE_NAME_TRANS = "SQ_ETL_TRANS_RULE";

	public void insert(Map paramMap) throws DataAccessException {

		// 转换规则 ''002''
		paramMap.put("etlType", "002");
		// '10A有效 10P 无效';
		paramMap.put("state", "10A");

		paramMap.put("createDate", new Date());

		paramMap.put("stateDate", new Date());

		String patternStr = "ETL_RULE_ID:etlRuleId,ETL_RULE_NAME:etlRuleName,ETL_TYPE:etlType,STATE:state,CREATE_DATE:createDate,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo,TACHE_ID:tacheId";
		Long nextId = getPKFromMem(TABLE_NAME_RULE, 9);
		paramMap.put("etlRuleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME_RULE, patternStr);

		patternStr = "ETL_RULE_ID:etlRuleId,SOURCE_TB_ID:sourceTbId,TARGET_TB_ID:targetTbId";
		dynamicInsertByMap(paramMap, TABLE_NAME_TRANS, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {

		paramMap.put("stateDate", new Date());

		String updatePatternStr = "ETL_RULE_NAME:etlRuleName,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,TACHE_ID:tacheId";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME_RULE, updatePatternStr,
				wherePatternStr);

		updatePatternStr = "ETL_RULE_ID:etlRuleId,SOURCE_TB_ID:sourceTbId,TARGET_TB_ID:targetTbId";
		dynamicUpdateByMap(paramMap, TABLE_NAME_TRANS, updatePatternStr,
				wherePatternStr);
	}

	public void deleteByUpdate(Map paramMap) throws DataAccessException {
		// '10A有效 10P 无效';
		paramMap.put("state", "10P");
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "STATE:state,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";

		dynamicUpdateByMap(paramMap, TABLE_NAME_RULE, updatePatternStr,
				wherePatternStr);

	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicDeleteByMap(paramMap, TABLE_NAME_RULE, wherePatternStr);

		dynamicDeleteByMap(paramMap, TABLE_NAME_TRANS, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select ru.etl_rule_id as etlRuleId,");
		strBf.append("       ru.etl_rule_name as etlRuleName,");
		strBf.append("       ru.tache_id as tacheId,");
		strBf.append("       ut.tache_name as tacheName,");
		strBf.append("       tr.source_tb_id as sourceTbId,");
		strBf.append("       mts.table_name as sourceTableName,");
		strBf.append("       tr.target_tb_id as targetTbId,");
		strBf.append("       mtt.table_name as targetTableName");
		strBf.append("  from SQ_ETL_RULE       ru,");
		strBf.append("       SQ_ETL_TRANS_RULE tr,");
		strBf.append("       METERDATA_TABLES  mts,");
		strBf.append("       METERDATA_TABLES  mtt,");
		strBf.append(" 		 UOS_TACHE 		   ut");
		strBf.append(" where ru.etl_rule_id = tr.etl_rule_id");
		strBf.append("   and tr.source_tb_id = mts.tables_id");
		strBf.append("   and tr.target_tb_id = mtt.tables_id");
		strBf.append("   and ru.tache_id = ut.id(+)");
		strBf.append("   and ru.state = '10A'");
		strBf.append("   and mts.state = '10A'");
		strBf.append("   and mtt.state = '10A'");
		strBf.append("   and ru.ETL_RULE_ID = ? ");

		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		return dynamicQueryObjectByMap(strBf.toString(), paramMap,
				wherePatternStr);
	}

	public Map selRulesAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		// 转换规则名称
		String etlRuleName = MapUtils.getString(paramMap, "etlRuleName");
		// 源表�?

		String sourceTableName = MapUtils
				.getString(paramMap, "sourceTableName");
		// 目标表名
		String targetTableName = MapUtils
				.getString(paramMap, "targetTableName");

		StringBuffer strBf = new StringBuffer();
		strBf.append("select ru.etl_rule_id as etlRuleId,");
		strBf.append("       ru.etl_rule_name as etlRuleName,");
		strBf.append("       ru.tache_id as tacheId,");
		strBf.append("       ut.tache_name as tacheName,");
		strBf.append("       ru.state as state,");
		strBf
				.append("       to_char(ru.state_date, 'yyyy-MM-dd HH24:MI:SS') as stateDate,");
		strBf.append("       ru.oper_man_name as operManName,");
		strBf.append("       tr.source_tb_id as sourceTbId,");
		strBf.append("       mts.table_name as sourceTableName,");
		strBf.append("       tr.target_tb_id as targetTbId,");
		strBf.append("       mtt.table_name as targetTableName");
		strBf.append("  from SQ_ETL_RULE       ru,");
		strBf.append("       SQ_ETL_TRANS_RULE tr,");
		strBf.append("       METERDATA_TABLES  mts,");
		strBf.append("       METERDATA_TABLES  mtt,");
		strBf.append(" 		 UOS_TACHE 		   ut");
		strBf.append(" where ru.etl_rule_id = tr.etl_rule_id");
		strBf.append("   and tr.source_tb_id = mts.tables_id");
		strBf.append("   and tr.target_tb_id = mtt.tables_id");
		strBf.append("   and ru.tache_id = ut.id(+)");
		strBf.append("   and ru.state = '10A'");
		strBf.append("   and mts.state = '10A'");
		strBf.append("   and mtt.state = '10A'");
		strBf.append("   and ru.etl_type = '002'");
		if (etlRuleName != null && !"".equals(etlRuleName.trim())) {
			strBf.append("   and ru.etl_rule_name like '").append(etlRuleName)
					.append("%'");
		}
		if (sourceTableName != null && !"".equals(sourceTableName.trim())) {
			strBf.append("   and mts.table_name like '")
					.append(sourceTableName).append("%'");
		}
		if (targetTableName != null && !"".equals(targetTableName.trim())) {
			strBf.append("   and mtt.table_name like '")
					.append(targetTableName).append("%'");
		}
		
		strBf.append(" order by ru.state_date desc ");

		System.out.println("selRulesAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}

	public List selCatalogTree(long parentId) throws DataAccessException {
		StringBuffer sBuff = new StringBuffer();
		sBuff.append("select p.catalog_id   as id,");
		sBuff.append("       p.catalog_name as text,");
		sBuff.append("       p.is_leaf      as leaf");
		sBuff.append("  from METERDATA_CATALOGS p");
		sBuff.append(" where p.state = '10A'");
		sBuff.append(" AND p.parent_id =  ").append(parentId);
		System.out.println("selCatalogTree:" + sBuff.toString());
		return dynamicQueryListByMap(sBuff.toString(), null, null);

	}

	public Map selSysTabAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer strBf = new StringBuffer();
		strBf.append("select mt.tables_id  as tablesId,");
		strBf.append("       mt.catalog_id as catalogId,");
		strBf.append("       mt.sys_id     as sysId,");
		strBf.append("       mt.table_code as tableCode,");
		strBf.append("       mt.table_name as tableName,");
		strBf.append("       mt.remark     as remark,");
		strBf.append("       ms.sys_name   as sysName");
		strBf.append("  from METERDATA_TABLES mt, METERDATA_SYSTEM ms");
		strBf.append(" where mt.sys_id = ms.sys_id");
		strBf.append("   and mt.state = '10A'");
		strBf.append("   and ms.state = '10A'");
		strBf.append("   and mt.catalog_id =  ").append(
				MapUtils.getLong(paramMap, "catalogId"));
		if(MapUtils.getString(paramMap, "tableName")!=null&&!MapUtils.getString(paramMap, "tableName").equals("")){
			strBf.append(" and mt.table_name like '%").append(MapUtils.getString(paramMap, "tableName")).append("%'");
		}
		if(MapUtils.getString(paramMap, "tableCode")!=null&&!MapUtils.getString(paramMap, "tableCode").equals("")){
			strBf.append(" and mt.table_code like '%").append(MapUtils.getString(paramMap, "tableCode")).append("%'");
		}

		System.out.println("selSysTabAsPage :" + strBf.toString());
		return populateQueryByMap(strBf, startIndex, stepSize);

	}

}
