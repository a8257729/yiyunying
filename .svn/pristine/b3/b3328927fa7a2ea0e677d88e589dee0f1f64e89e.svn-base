package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : ÂÒÂëÒÆ³ý×¢ÊÍImpl
 * @author FL
 */
public class ScheduleMngDAOImpl extends BaseDAOImpl implements ScheduleMngDAO {
	
	public Map getScheduleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select s.schedule_id AS scheduleId,");
		sb.append(" s.schedule_name AS scheduleName,");
		sb.append(" up.packagedefineid AS packageDefineId,");
		sb.append(" upk.name AS processDefinName,");
		sb.append(" sc.sche_catalog_id AS scheCatalogId,");
		sb.append(" sc.sche_catalog_name AS scheCatalogName,");
		sb.append(" to_char(s.exec_start_date,'YYYY-MM-DD HH24:mi:ss') AS execStartDate,");
		sb.append(" s.exec_rate AS execRate,");
		sb.append(" s.state AS state,");
		sb.append(" to_char(s.create_date,'YYYY-MM-DD HH24:mi:ss') AS createDate,");
		sb.append(" to_char(s.state_date,'YYYY-MM-DD HH24:mi:ss') AS stateDate,");
		sb.append(" s.oper_man_id AS operManId,");
		sb.append(" s.oper_man_name AS operManName,");
		sb.append(" s.memo AS memo from SQ_ETL_SCHEDULE s ");
		sb.append(" join uos_processdefine up on s.packagedefineid = up.packagedefineid");
		sb.append(" join uos_package upk on up.packageid = upk.packageid");
		sb.append(" join SQ_ETL_SCHEDULE_CATALOG sc on s.sche_catalog_id = sc.sche_catalog_id");
		sb.append(" where up.state = '10A' and upk.state='10A' and sc.state='10A'  ");
		
		if (MapUtils.getString(paramMap, "state") != null && !"".equals(MapUtils.getString(paramMap, "state"))) {
			sb.append(" and s.state = '").append(MapUtils.getString(paramMap, "state")).append("'");
		}else{
			sb.append(" and s.state <> '10P'");
		}

		if (MapUtils.getString(paramMap, "scheduleName") != null
				&& !"".equals(MapUtils.getString(paramMap, "scheduleName"))) {
			sb.append(" and s.schedule_name like '%").append(MapUtils.getString(paramMap, "scheduleName")).append("%'");
		}

		if (MapUtils.getString(paramMap, "scheCataId") != null
				&& !"".equals(MapUtils.getString(paramMap, "scheCataId"))) {
			sb.append(" and s.sche_catalog_id = '").append(MapUtils.getString(paramMap, "scheCataId")).append("'");
		}
		
		sb.append(" order by s.state_date desc") ;

		System.out.println("getScheduleList sql:" + sb.toString());
		
		return populateQueryByMap(sb, startIndex, stepSize);
	}
	
	public Map getScheduleRules(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.SCH_SEQU_ID AS schSequId,");
		sb.append(" a.SCHEDULE_ID AS schedule,");
		sb.append(" a.ETL_RULE_ID AS etlRule,");
		sb.append(" a.SCH_SEQU AS schSequ,");
		sb.append(" b.ETL_RULE_NAME AS etlRuleName,");
		sb.append(" b.ETL_TYPE AS etlType,");
		//sb.append(" b.SOURCE_DS_ID AS sourceDsId,");
		//sb.append(" b.TARGET_DS_ID AS targetDsId,");
		sb.append(" a.STATE AS state,");
		sb.append(" to_char(a.create_date,'YYYY-MM-DD HH24:mi:ss') AS createDate,");
		sb.append(" a.OPER_MAN_NAME AS operManName,");
		sb.append(" c.ETL_TYPE_NAME AS etlTypeName");
		//sb.append(" d.ds_name AS sourceDsName,");
		//sb.append(" e.ds_name AS targetDsName");
		sb.append(" from SQ_ETL_SCHEDULE_SEQU a");
		sb.append(" join SQ_ETL_RULE b on a.etl_rule_id = b.etl_rule_id");
		sb.append(" join SQ_ETL_TYPE c on b.etl_type = c.etl_type");
		//sb.append(" left join SQ_DATA_SOURCE d on b.source_ds_id = d.ds_id and d.state = '10A'");
		//sb.append(" left join SQ_DATA_SOURCE e on b.target_ds_id = e.ds_id and e.state = '10A'");
		sb.append(" where a.STATE = '10A' and b.STATE = '10A'");
		sb.append(" and SCHEDULE_ID = ");
		sb.append(MapUtils.getLong(paramMap, "scheduleId"));
		sb.append(" order by a.SCH_SEQU asc");
		
		System.out.println("getScheduleRules sql:" + sb.toString());
		return populateQueryByMap(sb, startIndex, stepSize);
	}
	
	public Map getOutScheduleRules(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT T.ETL_RULE_ID AS etlRuleId,"); 
		sb.append(" T.ETL_RULE_NAME AS etlRuleName,");
		sb.append(" T.ETL_TYPE AS etlType,");
		//sb.append(" D1.DS_NAME AS sourceDsName,");
		//sb.append(" D2.DS_NAME AS targetDsName,");
		sb.append(" Y.ETL_TYPE_NAME AS etlTypeName");
		sb.append(" FROM SQ_ETL_RULE T");
		//sb.append(" LEFT JOIN SQ_DATA_SOURCE D1 ON T.SOURCE_DS_ID = D1.DS_ID");
		//sb.append(" LEFT JOIN SQ_DATA_SOURCE D2 ON T.TARGET_DS_ID = D2.DS_ID");
		sb.append(" LEFT JOIN SQ_ETL_TYPE Y ON Y.ETL_TYPE = T.ETL_TYPE");
		//sb.append(" LEFT JOIN SQ_ETL_DATASET_TYPE S ON S.DATASET_TYPE_ID = T.DATASET_TYPE");
		//sb.append(" LEFT JOIN SQ_DATA_SOURCE_TYPE R ON R.DS_TYPE = D1.DS_TYPE");
		sb.append(" WHERE T.STATE = '10A'");
		sb.append(" AND NOT EXISTS (SELECT 1 FROM SQ_ETL_SCHEDULE_SEQU SC WHERE SC.ETL_RULE_ID = T.ETL_RULE_ID AND SC.STATE = '10A')");
		
		if (MapUtils.getString(paramMap, "etlRuleName") != null
				&& !"".equals(MapUtils.getString(paramMap, "etlRuleName"))) {
			sb.append(" AND T.ETL_RULE_NAME LIKE '%").append(MapUtils.getString(paramMap, "etlRuleName")).append("%'");
		}
		
		sb.append(" ORDER BY T.STATE, T.STATE_DATE DESC ");
		System.out.println("getOutScheduleRules sql:" + sb.toString());
		return populateQueryByMap(sb, startIndex, stepSize);
	}

	public Map getProcessDefin(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(); 
		sqlStr.append("SELECT UPD.PACKAGEDEFINEID AS packageDefineid, UPK.NAME AS name, UPD.VERSION AS version,");
		sqlStr.append("UPC.CATALOGNAME AS catalogName,UPD.EDITUSER AS editUser,UPD.DESCRIPTION AS description ");
		sqlStr.append("FROM UOS_PROCESSDEFINE UPD ");
		sqlStr.append("JOIN UOS_PACKAGE UPK ON UPD.PACKAGEID = UPK.PACKAGEID ");
		sqlStr.append("JOIN UOS_PACKAGECATALOG UPC ON UPK.CATALOGID = UPC.CATALOGID ");
		sqlStr.append("WHERE UPD.STATE = '10A' AND UPK.STATE='10A' AND UPC.STATE='10A'");		
		return populateQueryByMap(sqlStr, startIndex, stepSize);
	}

	public List getScheCatalog(Map paramMap)
			throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT T.SCHE_CATALOG_ID AS scheCatalogId,T.SCHE_CATALOG_NAME AS scheCatalogName,");
		sqlStr.append("T.IS_LEAF AS isLeaf FROM SQ_ETL_SCHEDULE_CATALOG T");
		
        if (paramMap.containsKey("parnetId")) {
            if (MapUtils.getObject(paramMap, "parnetId") != null) {
            	sqlStr.append(super.getWhereSql(sqlStr.toString(), " T.PARENT_ID= " + MapUtils.getLong(paramMap, "parnetId")));
            }
        }
        
        sqlStr.append(" ORDER BY T.PATH_CODE");
        
		return dynamicQueryListByMap(sqlStr.toString(),null,null);
	}
	
}
