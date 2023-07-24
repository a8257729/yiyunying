package com.ztesoft.mobile.etl.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.zterc.uos.UOSException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class ExtEtlRuleDAOImpl extends BaseDAOImpl implements ExtEtlRuleDAO {

	private final static Logger logger = Logger.getLogger(ExtEtlRuleDAOImpl.class);
	private static final String TABLE_NAME = "SQ_ETL_RULE";

	private static final String TABLE_NAME_FETCH = "SQ_ETL_DATA_FETCH_RULE";

	public void insert(Map paramMap) throws DataAccessException, UOSException {

		// '抽取规则 ''''000''''
		paramMap.put("etlType", "000");
		// '10A有效 10P 无效';
		paramMap.put("state", "10A");

		paramMap.put("createDate", new Date());

		paramMap.put("stateDate", new Date());

		String patternStr = "ETL_RULE_ID:etlRuleId,ETL_RULE_NAME:etlRuleName,ETL_TYPE:etlType,STATE:state,CREATE_DATE:createDate,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,MEMO:memo,TACHE_ID:tacheId";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("etlRuleId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		//,PRIMARY_COLUMN_ID:primaryColumnId
		patternStr = "ETL_RULE_ID:etlRuleId,SOURCE_DS_ID:sourceDsId,SOURCE_TB_NAME:sourceTbName,TARGET_TABLES_ID:targetTablesId,FETCH_MODE:fetchMode,FETCH_SCRIPT:fetchScript";

		dynamicInsertByMap(paramMap, TABLE_NAME_FETCH, patternStr);

	}

	public void update(Map paramMap) throws DataAccessException {
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "ETL_RULE_NAME:etlRuleName,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName,TACHE_ID:tacheId";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
		//,PRIMARY_COLUMN_ID:primaryColumnId
		updatePatternStr = "SOURCE_DS_ID:sourceDsId,SOURCE_TB_NAME:sourceTbName,TARGET_TABLES_ID:targetTablesId,FETCH_MODE:fetchMode,FETCH_SCRIPT:fetchScript";

		dynamicUpdateByMap(paramMap, TABLE_NAME_FETCH, updatePatternStr,
				wherePatternStr);
	}

	public void deleteByUpdate(Map paramMap) throws DataAccessException {
		// '10A有效 10P 无效';
		paramMap.put("state", "10P");
		paramMap.put("stateDate", new Date());

		String updatePatternStr = "STATE:state,STATE_DATE:stateDate,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";

		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);

	}

	public void delete(Map paramMap) throws DataAccessException {
		String updatePatternStr = "STATE_DATE:stateDate,STATE:state,OPER_MAN_ID:operManId,OPER_MAN_NAME:operManName";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   ETL_RULE_ID AS etlRuleId,  ETL_RULE_NAME AS etlRuleName,  ETL_TYPE AS etlType,  SOURCE_DS_ID AS sourceDsId,  SOURCE_DATASET AS sourceDataset,  DATASET_TYPE AS datasetType,  TARGET_DS_ID AS targetDsId,  FILE_DIR AS fileDir,  FILE_NAME AS fileName,  FILE_SEPARATOR AS fileSeparator,  STATE AS state,  CREATE_DATE AS createDate,  STATE_DATE AS stateDate,  OPER_MAN_ID AS operManId,  OPER_MAN_NAME AS operManName,  MEMO AS memo,  IS_SUP_BREAK AS isSupBreak,  FILE_BAK_DIR AS fileBakDir,  SOURCE_TB_NAME AS sourceTbName,  TARGET_TB_NAME AS targetTbName,  PROC_NAME AS procName,  FETCH_TYPE AS fetchType,  FETCH_MODE AS fetchMode,  UP_COL_NAME AS upColName,  UP_COL_DATA_TYPE AS upColDataType FROM SQ_ETL_RULE WHERE ETL_RULE_ID=?";
		String wherePatternStr = "ETL_RULE_ID:etlRuleId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   ETL_RULE_ID AS etlRuleId,  ETL_RULE_NAME AS etlRuleName,  ETL_TYPE AS etlType,  SOURCE_DS_ID AS sourceDsId,  SOURCE_DATASET AS sourceDataset,  DATASET_TYPE AS datasetType,  TARGET_DS_ID AS targetDsId,  FILE_DIR AS fileDir,  FILE_NAME AS fileName,  FILE_SEPARATOR AS fileSeparator,  STATE AS state,  CREATE_DATE AS createDate,  STATE_DATE AS stateDate,  OPER_MAN_ID AS operManId,  OPER_MAN_NAME AS operManName,  MEMO AS memo,  IS_SUP_BREAK AS isSupBreak,  FILE_BAK_DIR AS fileBakDir,  SOURCE_TB_NAME AS sourceTbName,  TARGET_TB_NAME AS targetTbName,  PROC_NAME AS procName,  FETCH_TYPE AS fetchType,  FETCH_MODE AS fetchMode,  UP_COL_NAME AS upColName,  UP_COL_DATA_TYPE AS upColDataType FROM SQ_ETL_RULE";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public Map queryAllEtlRule(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {

		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT er.ETL_RULE_ID       AS etlRuleId,");
		sqlStr.append("       er.tache_id AS tacheId,");
        sqlStr.append("       ut.tache_name AS tacheName,");
		sqlStr.append("       er.ETL_RULE_NAME     AS etlRuleName,");
		sqlStr.append("       er.ETL_TYPE          AS etlType,");
		sqlStr.append("       er.STATE             AS state,");
		sqlStr.append("       to_char(er.CREATE_DATE,'yyyy-MM-dd HH24:MM:SS')       AS createDate,");
		sqlStr
				.append("       to_char(er.STATE_DATE,'yyyy-MM-dd HH24:MM:SS')        AS stateDate,");
		sqlStr.append("       er.OPER_MAN_ID       AS operManId,");
		sqlStr.append("       er.OPER_MAN_NAME     AS operManName,");
		sqlStr.append("       er.MEMO              AS memo,");
		sqlStr.append("       fr.SOURCE_DS_ID      AS sourceDsId,");
		sqlStr.append("       ds.ds_name           as sourceDsName,");
		sqlStr.append("       fr.SOURCE_TB_NAME    as sourceTbName,");
		sqlStr.append("       fr.TARGET_TABLES_ID  AS targetTablesId,");
		sqlStr.append("       mt.table_name        as targetTablesName,");
		sqlStr.append("       fr.FETCH_MODE        as fetchMode,");
		sqlStr.append("       fr.FETCH_SCRIPT        as fetchScript");
		//sqlStr.append("       fr.PRIMARY_COLUMN_ID as primaryColumnId,");
		//sqlStr.append("       mc.column_name       as primaryColumnName");
		sqlStr.append("  FROM SQ_ETL_RULE er");
		sqlStr
				.append("  join SQ_ETL_DATA_FETCH_RULE fr on er.etl_rule_id = fr.etl_rule_id");
		sqlStr.append("                                and er.state = '10A'");
		sqlStr
				.append("  left join SQ_DATA_SOURCE ds on fr.source_ds_id = ds.ds_id");
		sqlStr.append("                             and ds.state = '10A'");
		sqlStr
				.append("  left join METERDATA_TABLES mt on fr.target_tables_id = mt.tables_id");
		sqlStr.append("                               and mt.state = '10A'");
		sqlStr
				.append("  left join UOS_TACHE ut on er.tache_id = ut.id and ut.state='10A' ");
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

			if (paramMap.containsKey("targetTableName")) {
				String targetTableName = (String) MapUtils.getObject(paramMap,
						"targetTableName");
				if (targetTableName != null
						&& !targetTableName.trim().equals("")) {
					sqlStr.append(" AND mt.table_name LIKE '%").append(
							targetTableName).append("%'");
				}
			}
		}

		sqlStr.append(" ORDER BY  er.state_date DESC");

		logger.debug("sqlStr===>" + sqlStr.toString());

		return populateQueryByMap(sqlStr, startIndex, stepSize);

	}

	public static void main(String[] args) {
		ExtEtlRuleDAOImpl test = new ExtEtlRuleDAOImpl();
		try {
			Map paramMap = new HashMap();
			paramMap.put("etlRuleId", "1");
			paramMap.put("etlRuleName", "1");
			paramMap.put("etlType", "1");
			paramMap.put("sourceDsId", "1");
			paramMap.put("sourceDataset", "1");
			paramMap.put("datasetType", "1");
			paramMap.put("targetDsId", "1");
			paramMap.put("fileDir", "1");
			paramMap.put("fileName", "1");
			paramMap.put("fileSeparator", "1");
			paramMap.put("state", "1");
			paramMap.put("createDate", "1");
			paramMap.put("stateDate", "1");
			paramMap.put("operManId", "1");
			paramMap.put("operManName", "1");
			paramMap.put("memo", "1");
			paramMap.put("isSupBreak", "1");
			paramMap.put("fileBakDir", "1");
			paramMap.put("sourceTbName", "1");
			paramMap.put("targetTbName", "1");
			paramMap.put("procName", "1");
			paramMap.put("fetchType", "1");
			paramMap.put("fetchMode", "1");
			paramMap.put("upColName", "1");
			paramMap.put("upColDataType", "1");
			// test.insert(paramMap);
			// test.update(paramMap);
			// test.delete(paramMap);
			// System.out.println(test.selById(paramMap));
			// System.out.println(test.selAll(paramMap));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map qryUosTache(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT UT.ID AS tacheId,UT.TACHE_CATALOG_ID AS tacheCatalogId,");
		sqlStr.append("UT.TACHE_NAME AS tacheName,UT.TACHE_CODE AS tacheCode ");
		sqlStr.append(" FROM UOS_TACHE UT WHERE UT.STATE='10A'");
		
		if (paramMap.containsKey("tacheCatalogId")) {
			String tacheCatalogId = (String) MapUtils.getObject(paramMap,"tacheCatalogId");
			if (tacheCatalogId != null && !tacheCatalogId.trim().equals("")) {
				sqlStr.append(" AND UT.TACHE_CATALOG_ID = ").append(tacheCatalogId.trim());
			}
		}
	
		return super.populateQueryByMap(sqlStr, startIndex, stepSize);
	}

	public List qryUosTacheCata(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT TC.ID AS catalogId,TC.TACHE_CATALOG_NAME AS catalogName,");
		sqlStr.append("CASE WHEN (SELECT COUNT(*) FROM UOS_TACHE_CATALOG PTC WHERE PTC.STATE='10A' ")
			.append("AND PTC.PARENT_TACHE_CATALOG_ID = TC.ID)=0 THEN 'Y' ELSE 'N' END AS isLeaf ");
		sqlStr.append(" FROM UOS_TACHE_CATALOG TC WHERE TC.STATE = '10A' ");
		
        if (paramMap.containsKey("parnetId")) {
            if (MapUtils.getObject(paramMap, "parnetId") != null) {
            	sqlStr.append(super.getWhereSql(sqlStr.toString(), " TC.PARENT_TACHE_CATALOG_ID= " + MapUtils.getLong(paramMap, "parnetId")));
            }
        }else{
        	sqlStr.append(super.getWhereSql(sqlStr.toString(), " TC.PARENT_TACHE_CATALOG_ID IS NULL"));
        }
        
        sqlStr.append(" ORDER BY TC.PATH_CODE");
      
        System.out.println("=============qryUosTacheCata==================>    " +  sqlStr.toString() );
        
		return populateQueryByMapNoAs(sqlStr);
	}
}
