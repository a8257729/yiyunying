package com.ztesoft.mobile.etl.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : Impl
 * @author FL
 */
public class SchMonitorDAOImpl extends BaseDAOImpl implements SchMonitorDAO {
	
	public Map getScheduleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SESI.SCH_INST_ID AS schInstId,");
		sb.append(" SESI.SCHEDULE_ID AS schedule,");
		sb.append(" TO_CHAR(SESI.CREATE_DATE,'YYYY-MM-DD HH24:mi:ss') AS createDate,");
		sb.append(" SESI.MEMO AS memo,");
		sb.append(" SESI.OPER_MAN AS operMan,");
		sb.append(" SESI.OPER_MAN_ID AS operNameId,");
		sb.append(" SESI.OPER_MAN_TYPE AS operManType,");
		sb.append(" SES.SCHEDULE_NAME AS scheduleName,");
		sb.append(" TO_CHAR(SESI.ETL_START_DATE,'YYYY-MM-DD HH24:mi:ss') AS etlStartDate,");
		sb.append(" TO_CHAR(SESI.ETL_NEXT_DATE,'YYYY-MM-DD HH24:mi:ss') AS etlNextDate,");
		sb.append(" SESI.STATE AS state,");
		sb.append(" TO_CHAR(SESI.STATE_DATE,'YYYY-MM-DD HH24:mi:ss') AS stateDate");
		sb.append(" FROM SQ_ETL_SCHEDULE_INST SESI");
		sb.append(" JOIN SQ_ETL_SCHEDULE SES ON SESI.SCHEDULE_ID = SES.SCHEDULE_ID  AND SES.STATE = '10A' ");
		sb.append(" WHERE 1=1 ");
		
		if (MapUtils.getString(paramMap, "state") != null && !"".equals(MapUtils.getString(paramMap, "state"))) {
			sb.append("AND SESI.STATE = '").append(MapUtils.getString(paramMap, "state")).append("'");
		} 

		if (MapUtils.getString(paramMap, "scheduleName") != null
				&& !"".equals(MapUtils.getString(paramMap, "scheduleName"))) {
			sb.append(" AND SES.SCHEDULE_NAME LIKE'%").append(MapUtils.getString(paramMap, "scheduleName")).append("%'");
		}

		sb.append(" ORDER BY SESI.STATE_DATE DESC");

		System.out.println("getScheduleList sql:" + sb.toString());
		
		return populateQueryByMap(sb, startIndex, stepSize);
	}
	
	public Map getRuleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SER.ETL_RULE_NAME AS etlRuleName,");
		sb.append(" SEI.STATE AS state,");
		sb.append(" SESS.SCH_SEQU AS schSequ,");
		sb.append(" SESI.SCH_INST_ID AS schInst,");
		sb.append(" SES.SCHEDULE_ID AS scheduleId,");
		sb.append(" SESS.SCH_SEQU_ID AS schSequId,");
		sb.append(" SER.ETL_RULE_ID AS etlRuleId,");
		sb.append(" SEI.ETL_INST_ID AS etlInstId,");
		sb.append(" SER.ETL_TYPE AS etlType,");
		sb.append(" SETY.ETL_TYPE_NAME AS etlTypeName");
		sb.append(" FROM SQ_ETL_SCHEDULE_INST SESI");
		sb.append(" JOIN SQ_ETL_SCHEDULE SES ON SESI.SCHEDULE_ID = SES.SCHEDULE_ID");
		sb.append(" JOIN SQ_ETL_SCHEDULE_SEQU SESS ON SES.SCHEDULE_ID = SESS.SCHEDULE_ID");
		sb.append(" JOIN SQ_ETL_RULE SER ON SESS.ETL_RULE_ID = SER.ETL_RULE_ID");
		sb.append(" JOIN SQ_ETL_INST SEI ON SER.ETL_RULE_ID = SEI.ETL_RULE_ID AND SEI.SCH_INST_ID = SESI.SCH_INST_ID");
		sb.append(" JOIN SQ_ETL_TYPE SETY ON SER.ETL_TYPE = SETY.ETL_TYPE");
		sb.append(" WHERE SESI.SCH_INST_ID = ");
		sb.append(MapUtils.getLong(paramMap, "schInstId"));
		sb.append(" ORDER BY SESS.SCH_SEQU ASC ");

		System.out.println("getRuleList sql:" + sb.toString());
		
		return populateQueryByMap(sb, startIndex, stepSize);
	}

	public Map qryInfAllRowsImplLog(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {
		// 抽取规则=000 整合规则=001 转换规则=002 文件抽取规则=003 汇总规则=004
		String etlType = MapUtils.getString(paramMap, "etlType");
		if ("000".equals(etlType)) {
			return qryExtractLogs(paramMap, startIndex, stepSize);
		} else if ("002".equals(etlType)) {
			return qryTransLogs(paramMap, startIndex, stepSize);
		}
		return null;
	}
	
	public Map qryFieldLog(Map paramMap) throws DataAccessException {

		String sqlStr = "SELECT LOG_DESC AS logDesc FROM SQ_FIELD_CLEAN_TRANS_LOG  WHERE ETL_LOG_ID = "
				+ MapUtils.getLong(paramMap, "etlLogId");
		System.out.println("qryFieldLog:" + sqlStr);
		List<Map> result = dynamicQueryListByMap(sqlStr, null, null);
		
		if (result == null || result.isEmpty()) {
			return null;
		}
		Map log = new HashMap();
		StringBuffer logDesc = new StringBuffer();
		for (Map row : result) {
			logDesc.append(row.get("logDesc"));
			logDesc.append("\n") ;
		}
		log.put("logDesc", logDesc.toString());
		return log;
	}

	private Map qryExtractLogs(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select lg.etl_log_id as etlLogId,");
		sb.append("       lg.etl_inst_id as etlInstId,");
		sb.append("       lg.sys_id as sysId,");
		sb.append("       ms.sys_name as sysName,");
		sb.append("       lg.source_table_name as sourceTableName,");
		sb.append("       lg.tables_id as tablesId,");
		sb.append("       mt.table_name as tableName,");
		sb.append("       to_char(lg.begin_time, 'yyyy-MM-dd HH24:MI:SS') as beginTime,");
		sb.append("       to_char(lg.end_time, 'yyyy-MM-dd HH24:MI:SS') as endTime,");
		sb.append("       lg.mustbe_num as mustbeNum,");
		sb.append("       lg.actual_num as actualNum,");
		sb.append("       lg.is_execption as isException,");
		sb.append("       lg.log_desc as logDesc,");
		sb.append("       lg.extract_type as extractType");
		sb.append("  from SQ_ETL_EXTRACT_LOG lg");
		sb.append("  left join METERDATA_SYSTEM ms on lg.sys_id = ms.sys_id");
		sb.append("                               and ms.state = '10A'");
		sb.append("  left join METERDATA_TABLES mt on lg.tables_id = mt.tables_id");
		sb.append("                               and mt.state = '10A'");
		//sb.append(" where lg.extract_type = 'ALL'");
		sb.append(" where lg.etl_inst_id =").append(MapUtils.getLong(paramMap, "etlInstId"));

		System.out.println("qryInfAllRowsImplLog sql:" + sb.toString());
		return populateQueryByMap(sb, startIndex, stepSize);
	}
	
	private Map qryTransLogs(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select lg.etl_log_id as etlLogId,");
		sb.append("       lg.etl_inst_id as etlInstId,");
		sb.append("       lg.source_tables_id as sourceTablesId,");
		sb.append("       ms.table_name       as sourceTablesName,");
		sb.append("       lg.target_tables_id as targetTablesId,");
		sb.append("       mt.table_name as targetTabelsName,");
		sb.append("       to_char(lg.begin_time, 'yyyy-MM-dd HH24:MI:SS') as beginTime,");
		sb.append("       to_char(lg.end_time, 'yyyy-MM-dd HH24:MI:SS') as endTime,");
		sb.append("       lg.clean_trans_total as cleanTransTotal,");
		sb.append("       lg.right_count as rightCount,");
		sb.append("       lg.error_count as errorCount,");
		sb.append("       lg.is_execption as isException,");
		sb.append("       lg.log_desc as logDesc");
		sb.append("  from SQ_ETL_TRANS_LOG lg");
		sb.append("  left join METERDATA_TABLES ms on lg.source_tables_id = ms.tables_id");
		sb.append("                               and ms.state = '10A'");
		sb.append("  left join METERDATA_TABLES mt on lg.target_tables_id = mt.tables_id");
		sb.append("                               and mt.state = '10A'");
		sb.append(" where lg.etl_inst_id =").append(MapUtils.getLong(paramMap, "etlInstId"));

		System.out.println("qryTransLogs sql:" + sb.toString());
		return populateQueryByMap(sb, startIndex, stepSize);
	}
//	public Map qryInfAllRowsImplLog(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
//		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT T.INF_ALL_ROWS_IMP_LOG_ID AS infAllRowsImpLogId,");
//		sb.append(" T.BATCH_NUM AS batchNum,");
//		sb.append( "T.TYPE AS type,");
//		sb.append(" T.SYSTEM_CODE AS systemCode,");
//		sb.append(" S.SYS_NAME AS sysName,");
//		sb.append(" T.DBLINK_NAME AS dblinkName,");
//		sb.append(" T.SOURCE_TABLE_NAME AS sourceTableName,");
//		sb.append(" T.DEST_TABLE_NAME AS destTableName,");
//		sb.append(" TO_CHAR(T.BEGIN_TIME, 'YYYY-MM-DD HH24:mi:ss') AS beginTime,");
//		sb.append(" TO_CHAR(T.END_TIME, 'YYYY-MM-DD HH24:mi:ss') AS endTime,");
//		sb.append(" T.MUSTBE_NUM AS mustbeNum,");
//		sb.append(" T.ACTUAL_NUM AS actualNum,");
//		sb.append(" T.IS_EXCEPTION AS isException,");
//		sb.append(" T.CRATE_TIME AS createTime,");
//		sb.append(" T.STATE AS state,");
//		sb.append(" T.EXCE_ID AS exceId,");
//		sb.append(" E.EXCE_DESC AS exceDesc,");
//		sb.append(" E.EXCE_ICON AS exceIcon,");
//		sb.append(" T.ETL_INST_ID AS etlInstId");
//		sb.append(" FROM INF_ALL_ROWS_IMP_LOG T");
//		sb.append(" LEFT JOIN INF_EXCE_TYPE E ON E.EXCE_ID = T.EXCE_ID");
//		sb.append(" LEFT JOIN OUTER_SYSTEM S ON S.SYS_CODE = T.SYSTEM_CODE");
//		sb.append(" WHERE S.STATE = 1");
//		sb.append(" AND T.ETL_INST_ID = ");
//		sb.append(MapUtils.getLong(paramMap, "schInstId"));
//		sb.append(" ORDER BY T.CRATE_TIME");
//
//		System.out.println("qryInfAllRowsImplLog sql:" + sb.toString());
//		return populateQueryByMap(sb, startIndex, stepSize);
//	}
	
	public Map qryCleanLogListAction(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT SQ_ETL_CLEAN_LOG_ID AS sqEtlCleanLogId,");
		sb.append(" ETL_INST_ID AS etlInstId,");
		sb.append(" CLEAN_TB_NAME AS cleanTbName,");
		sb.append(" CLEAN_CNT AS cleanCnt,");
		sb.append(" TO_CHAR(EXEC_BEGIN_TIME, 'YYYY-MM-DD HH24:mi:ss') AS execBeginTime,");
		sb.append(" TO_CHAR(EXEC_END_TIME, 'YYYY-MM-DD HH24:mi:ss') AS execEndTime,");
		sb.append(" BAK_TB_NAME AS bakTbName,");
		sb.append(" MEMO AS memo,");
		sb.append(" STATE AS state,");
		sb.append(" TO_CHAR(CREATE_TIME, 'YYYY-MM-DD HH24:mi:ss') AS createTime");
		sb.append(" FROM SQ_ETL_CLEAN_LOG");
		sb.append(" WHERE ETL_INST_ID = ");
		sb.append(MapUtils.getLong(paramMap, "schInstId"));

		System.out.println("qryCleanLogListAction sql:" + sb.toString());
		return populateQueryByMap(sb, startIndex, stepSize);
	}
}
