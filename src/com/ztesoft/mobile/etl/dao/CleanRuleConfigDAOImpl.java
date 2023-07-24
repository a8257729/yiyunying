package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : 清理规则配置 Impl
 * @author FL
 *
 */
public class CleanRuleConfigDAOImpl extends BaseDAOImpl implements CleanRuleConfigDAO {
	
	public Map getCleanRoleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT T.ETL_RULE_ID AS etlRuleId,");
		sb.append(" T.ETL_RULE_NAME AS etlRuleName,");
		sb.append(" T.STATE AS state,");
		sb.append(" T.TARGET_TB_NAME AS targetTbName,");
		sb.append(" T.OPER_MAN_NAME AS operManName,");
		sb.append(" TO_CHAR(T.STATE_DATE,'YYYY-MM-DD HH24:mi:ss') AS stateDate,");
		sb.append(" D1.DS_NAME AS sourceDsName,");
		sb.append(" D2.DS_NAME AS targetName,");
		sb.append(" Y.ETL_TYPE_NAME AS etlTypeName,");
		sb.append(" S.DATASET_TYPE_NAME AS datasetTypeName,");
		sb.append(" R.DS_TYPE AS dsType,");
		sb.append(" R.DS_TPYE_NAME AS dsTypeName");
		sb.append(" FROM SQ_ETL_RULE T");
		sb.append(" LEFT JOIN SQ_DATA_SOURCE D1 ON T.SOURCE_DS_ID = D1.DS_ID");
		sb.append(" LEFT JOIN SQ_DATA_SOURCE D2 ON T.TARGET_DS_ID = D2.DS_ID");
		sb.append(" LEFT JOIN SQ_ETL_TYPE Y ON Y.ETL_TYPE = T.ETL_TYPE");
		sb.append(" LEFT JOIN SQ_ETL_DATASET_TYPE S ON S.DATASET_TYPE_ID = T.DATASET_TYPE");
		sb.append(" LEFT JOIN SQ_DATA_SOURCE_TYPE R ON R.DS_TYPE = D1.DS_TYPE");
		sb.append(" WHERE T.ETL_TYPE = '001'");
		
		if (MapUtils.getString(paramMap, "state") != null && !"".equals(MapUtils.getString(paramMap, "state"))) {
			sb.append("AND T.STATE = '").append(MapUtils.getString(paramMap, "state")).append("'");
		} 

		if (MapUtils.getString(paramMap, "etlRuleName") != null
				&& !"".equals(MapUtils.getString(paramMap, "etlRuleName"))) {
			sb.append(" AND T.ETL_RULE_NAME LIKE '%").append(MapUtils.getString(paramMap, "etlRuleName")).append("%'");
		}

		sb.append(" ORDER BY T.STATE, T.STATE_DATE DESC");
		
		System.out.println("getCleanRoleList sql:" + sb.toString());
		
		return populateQueryByMap(sb, startIndex, stepSize);
	}
	
	public Map getComListByRole(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT A.SQ_ETL_CLEAN_RULE_ID AS sqEtlCleanRuleId,");
		sqlBuf.append(" A.ETL_RULE_ID AS etlRuleId,A.ETL_CLEAN_COMP_ID AS etlCleanCompId,");
		sqlBuf.append(" A.SQ_ETL_CLEAN_RULE_NAME AS sqEtlCleanRuleName,");
		sqlBuf.append(" A.CLEAN_COL_NAME AS cleanColName,");
		sqlBuf.append(" TO_CHAR(A.CREATE_DATE,'YYYY-MM-DD HH24:mi:ss') AS createDate,");
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
		
		System.out.println("getComListByRole sql:" + sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf, startIndex, stepSize);
	}
	
	/**
	 * 查询清洗规则类型
	 * @return
	 * @throws Exception
	 */
	public Map qryRuleTypeList() throws DataAccessException {
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT SQ_ETL_CLEAN_COMP_ID AS sqEtlCleanCompId,CLEAN_COMP_NAME AS cleanCompName,CLEAN_COMP_EXPR AS cleanCompExpr,EXEC_TYPE AS execType,MEMO AS memo,STATE AS state");
		sqlBuf.append(" FROM SQ_ETL_CLEAN_COMP where state  = '10A'");
		return (Map) populateQueryByMap(sqlBuf, 0, 0);
	}
}