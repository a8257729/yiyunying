package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
public class IndexDAOImpl extends BaseDAOImpl implements IndexDAO {
private static final String TABLE_NAME = "PROD_MODEL_INDICATOR";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PROD_MODEL_INDICATOR_ID:prodModelIndicatorId,PRODUCT_MODEL_ID:productModelId,INDICATOR_ID:indicatorId,STANDARD_VALUE:standardValue,EARLY_WARN_VALUE:earlyWarnValue,ALARM_VALUE:alarmValue,COMP_TYPE:compType,IS_TOP:isTop";
		Long nextId = getPKFromMem(TABLE_NAME, 2);
		paramMap.put("prodModelIndicatorId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PROD_MODEL_INDICATOR_ID:prodModelIndicatorId,STANDARD_VALUE:standardValue,EARLY_WARN_VALUE:earlyWarnValue,ALARM_VALUE:alarmValue,COMP_TYPE:compType,IS_TOP:isTop";
		String wherePatternStr = "PROD_MODEL_INDICATOR_ID:prodModelIndicatorId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PROD_MODEL_INDICATOR_ID:prodModelIndicatorId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
					sqlStr.append("SELECT   a.PROD_MODEL_INDICATOR_ID AS prodModelIndicatorId,  a.PRODUCT_MODEL_ID AS productModelId, ");
					sqlStr.append(" a.INDICATOR_ID AS indicatorId,  a.STANDARD_VALUE AS standardValue,  a.EARLY_WARN_VALUE AS earlyWarnValue, ");
					sqlStr.append(" a.ALARM_VALUE AS alarmValue,  case when a.COMP_TYPE = 1 then '大于标准值' else '小于标准值' end AS compTypeName, a.COMP_TYPE as compType, ");
					sqlStr.append(" a.EXE_SEQ as exeSeq,b.indicator_name as indicatorName FROM PROD_MODEL_INDICATOR a join INDICATOR b ON a.indicator_id = b.indicator_id WHERE 1=1 ");
					if (!MapUtils.getString(paramMap, "productModelId").equals("")) {
						sqlStr.append(" AND a.PRODUCT_MODEL_ID ="+MapUtils.getString(paramMap, "productModelId"));
					}
					System.out.println("dd  "+sqlStr.toString());
		return populateQueryByMap(sqlStr,1,100);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PROD_MODEL_INDICATOR_ID AS prodModelIndicatorId,  PRODUCT_MODEL_ID AS productModelId,  INDICATOR_ID AS indicatorId,  STANDARD_VALUE AS standardValue,  EARLY_WARN_VALUE AS earlyWarnValue,  ALARM_VALUE AS alarmValue,  COMP_TYPE AS compType,,EXE_SEQ as exeSeq FROM PROD_MODEL_INDICATOR";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	
	public List selByName(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT PROD_MODEL_INDICATOR_ID AS prodModelIndicatorId from PROD_MODEL_INDICATOR " +
							"WHERE INDICATOR_ID="+MapUtils.getString(paramMap, "indicatorId")+" and PRODUCT_MODEL_ID="+MapUtils.getString(paramMap, "productModelId");
		return dynamicQueryListByMap(sqlStr, paramMap, null);
	}
	public Map selListByModelId(Map paramMap)throws Exception{
		StringBuffer sqlBuf = new StringBuffer(" select a.PROD_MODEL_INDICATOR_ID as prodModelIndicatorId ,a.indicator_id as indicatorId,b.indicator_name as indicatorName,");
		sqlBuf.append(" b.indicator_code as indicatorCode,b.indicator_type as indicatorType,");
		sqlBuf.append(" a.is_top as isTop,case when a.is_top = 1 then '是' else '否' end as isTopName,");
		sqlBuf.append(" a.standard_value as standardValue,a.early_warn_value as earlyWarnValue,");
		sqlBuf.append(" a.comp_type as compType,case when a.comp_type = 1 then '大于标准值' else '小于标准值' end as compTypeName,");
		sqlBuf.append(" a.alarm_value as alarmValue");
		sqlBuf.append(" from PROD_MODEL_INDICATOR a join INDICATOR b on a.indicator_id = b.indicator_id ");
		sqlBuf.append(" join PRODUCT_MODEL c on a.product_model_id = c.product_model_id ");
		sqlBuf.append(" where b.state = '10A' and c.state = '10A' ");
		sqlBuf.append(" and a.product_model_id = ").append(MapUtils.getIntValue(paramMap, "productModelId"));
					
		return populateQueryByMap(sqlBuf, Integer.parseInt(paramMap.get("start").toString()), Integer.parseInt(paramMap.get("limit").toString()));
	}
}
