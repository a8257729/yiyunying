package com.ztesoft.mobile.v2.dao.rest;

import java.util.HashMap;
import java.util.Map;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;

import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
public class MobileRestServiceDAOImpl extends BaseDAOImpl implements MobileRestServiceDAO{
private static final String TABLE_NAME = "MOBILE_REST_SERVICE";
public void insert(Map paramMap) throws DataAccessException {
	String patternStr = "REST_SERVICE_ID:@@SEQ,SERV_NAME:servName,SERV_ADDR:servAddr,SERV_STATUS:servStatus,SERV_TYPE:servType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
	Long nextId = getPKFromMem(TABLE_NAME, 9);
	paramMap.put("restServiceId", nextId);
	dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
}

public void update(Map paramMap) throws DataAccessException {
	String updatePatternStr = "REST_SERVICE_ID:restServiceId,SERV_NAME:servName,SERV_ADDR:servAddr,SERV_STATUS:servStatus,SERV_TYPE:servType,BUILD_TIME:buildTime,UPDATE_TIME:updateTime,STATE:state,STATE_DATE:stateDate";
	String wherePatternStr = "REST_SERVICE_ID:restServiceId";
	dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
			wherePatternStr);
}

public void delete(Map paramMap) throws DataAccessException {
	String wherePatternStr = "REST_SERVICE_ID:restServiceId";
	dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
}

public Map selById(Map paramMap) throws DataAccessException {
	StringBuffer sqlStr = this.buildSql(new HashMap());
	sqlStr.append(" AND REST_SERVICE_ID = ? ");
	String wherePatternStr = "REST_SERVICE_ID:restServiceId";
	return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
}

public List selAll(Map paramMap) throws DataAccessException {
	StringBuffer sqlStr = this.buildSql(paramMap);
	return dynamicQueryListByMap(sqlStr.toString(), null, null);
}

public StringBuffer buildSql(Map paramMap) {
	StringBuffer sqlStr = new StringBuffer(" SELECT REST_SERVICE_ID AS restServiceId, ");
	sqlStr.append(" SERV_NAME AS servName, ");
	sqlStr.append(" SERV_ADDR AS servAddr, ");
	sqlStr.append(" SERV_STATUS AS servStatus, ");
	sqlStr.append(" BUILD_TIME AS buildTime, ");
	sqlStr.append(" UPDATE_TIME AS updateTime ");
	sqlStr.append(" FROM MOBILE_REST_SERVICE T WHERE STATE = 1 ");
	
	Long restServiceId = MapUtils.getLong(paramMap, "restServiceId");
	if(null != restServiceId) {
		sqlStr.append(" AND T.REST_SERVICE_ID = " + restServiceId);
	}
	
	String servNameEquals = MapUtils.getString(paramMap, "servNameEquals");
	if(StringUtils.isNotBlank(servNameEquals)) {
		sqlStr.append(" AND T.SERV_NAME = '" + servNameEquals + "' ");
	}
	
	String servNameLike = MapUtils.getString(paramMap, "servNameLike");
	if(StringUtils.isNotBlank(servNameLike)) {
		sqlStr.append(" AND T.SERV_NAME = '%" + servNameLike + "%' ");
	}
	
	String servAddrEquals = MapUtils.getString(paramMap, "servAddrEquals");
	if(StringUtils.isNotBlank(servAddrEquals)) {
		sqlStr.append(" AND T.SERV_ADDR = '" + servAddrEquals + "' ");
	}
	
	String servAddrLike = MapUtils.getString(paramMap, "servAddrLike");
	if(StringUtils.isNotBlank(servAddrLike)) {
		sqlStr.append(" AND T.SERV_ADDR = '%" + servAddrLike + "%' ");
	}
	
	Integer servStaus = MapUtils.getInteger(paramMap, "servNameLike");
	if(null != servStaus) {
		sqlStr.append(" AND T.SERV_STAUS = " + servStaus);
	}
	
	
	return sqlStr;
}
	public Map selPageMap(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr=new StringBuffer(" SELECT  mrs.REST_SERVICE_ID AS restServiceId,"+
       	      "  mrs.SERV_NAME       AS servName,"+
       	      "  mrs.SERV_ADDR       AS servAddr,"+
       	      "  mrs.SERV_STATUS      AS servStaus,"+
       	      " mp.mname        as servStausName,"+
       	      "  mrs.STATE           AS state,"+
       	      "  mrs.serv_type        as servType ,"+
       	      " mp2.mname            as servTypeName,"+
       	      "  mrs.STATE_DATE      AS stateDate, "+
       	   "  mrs.UPDATE_TIME      AS updateTime, "+
       	"  mrs.BUILD_TIME      AS buildTime "+
       	  " FROM MOBILE_REST_SERVICE mrs left join mobile_param mp on mrs.serv_status=mp.mcode " +
       	  "left join mobile_param mp2 on mrs.serv_type = mp2.mcode ");
		   sqlStr.append(" where mp.gcode='SERV_STATUS' and mp2.gcode='SERV_TYPE'");
		   if(paramMap.get("servName")!=null){
		   sqlStr.append(" and mrs.serv_name like '%"+paramMap.get("servName")+"%' ");
		   }
		   if(paramMap.get("servAddr")!=null){
		   sqlStr.append(" and mrs.serv_addr like '%"+paramMap.get("servAddr")+"%' ");
		   }
		   if(paramMap.get("servStaus")!=null && !paramMap.get("servStaus").equals("")){
		   sqlStr.append(" and mrs.serv_status="+paramMap.get("servStaus"));
		  }
		   if(paramMap.get("beginDate")!= null &&!paramMap.get("beginDate").equals("") && paramMap.get("endDate")!=null && !paramMap.get("endDate").equals("")){
			   sqlStr.append(" and  mrs.STATE_DATE  between to_date('"+paramMap.get("beginDate")+"','yyyy-MM-dd HH24:mi:ss')  and  to_date('" + paramMap.get("endDate")	+ "','yyyy-MM-dd HH24:mi:ss')  ");
		   }
		   
		   System.out.println(sqlStr.toString());
		return populateQueryByMap(sqlStr, (Integer)paramMap.get("pageIndex"), (Integer)paramMap.get("pageSize"));
	}

}
