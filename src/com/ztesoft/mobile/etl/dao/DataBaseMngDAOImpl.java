package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : ¥À¥¶◊¢ Õ“Ú¬“¬Î…æµÙ
 * @author FL
 */
public class DataBaseMngDAOImpl extends BaseDAOImpl implements DataBaseMngDAO {
	
	public Map getDataBaseList(Map paramMap, int startIndex, int stepSize) throws DataAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.ds_id AS dsId,");
		sb.append("d.ds_name AS dsName,");
		sb.append("d.ds_type AS dsType,");
		sb.append("d.state AS state,");
		sb.append("d.db_driver AS dbDriver,");
		sb.append("d.db_url AS dbUrl,");
		sb.append("d.user_name AS userName,");
		sb.append("d.password AS password,");
		sb.append("d.init_num AS initNum,");
		sb.append("d.inc_num AS incNum,");
		sb.append("d.max_num AS maxNum,");
		sb.append("d.ftp_ip AS ftpIp,");
		sb.append("d.ser_cop AS serCop,");
		sb.append("to_char(d.create_date,'YYYY-MM-DD HH24:mi:ss') AS createDate,");
		sb.append("to_char(d.state_date,'YYYY-MM-DD HH24:mi:ss') AS stateDate,");
		sb.append("d.memo AS memo,");
		sb.append("d.oper_man_id AS operManId,");
		sb.append("d.oper_man_name AS operManName,");
		sb.append("d.db_type AS dbType,");
		sb.append("t.db_tpye_name AS dbTypeName,");
		sb.append("d.dblink_name AS dblinkName from SQ_DATA_SOURCE d ");
		sb.append("	left join SQ_ETL_BD_TYPE t on d.db_type = t.db_type ");
		sb.append(" where 1=1 ");
		sb.append("and d.state <> '10P'");
		if (MapUtils.getString(paramMap, "dsType") != null && !"".equals(MapUtils.getString(paramMap, "dsType"))) {
			sb.append(" and d.ds_type = '").append(MapUtils.getString(paramMap, "dsType")).append("'");
		}

		if (MapUtils.getString(paramMap, "dsName") != null
				&& !"".equals(MapUtils.getString(paramMap, "dsName"))) {
			sb.append(" and d.ds_name like '%").append(MapUtils.getString(paramMap, "dsName")).append("%'");
		}

		System.out.println("getShareData sql:" + sb.toString());
		
		return populateQueryByMap(sb, startIndex, stepSize);
	}

	public List getDataListForRule(String dsType) throws DataAccessException {
		// TODO Auto-generated method stub
		String sqlStr = "select ds_id,ds_name from SQ_DATA_SOURCE  where state='10A' ";
		if(dsType!=null&&!dsType.trim().equals("")){
			sqlStr+=" and ds_type='"+dsType+"'";
		}
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}
	
	public List queryDataBaseTypes() throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select t.db_type      as value,");
		sqlStr.append("       t.db_tpye_name as text,");
		sqlStr.append("       t.url          as url,");
		sqlStr.append("       t.driver_name  as driver");
		sqlStr.append("  from SQ_ETL_BD_TYPE t");
		
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr.toString(), null, wherePatternStr);
	}
}
