package com.ztesoft.mobile.security.dao;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class MobileStaffSecConfigDAOImpl extends BaseDAOImpl implements MobileStaffSecConfigDAO {
	//
	private static final String TABLE_NAME = "MOBILE_STAFF_SEC_CONFIG";
	
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "MOBILE_STAFF_SEC_CONFIG_ID:@@SEQ,STAFF_ID:staffId,STAFF_NAME:staffName,SECURITY_TYPE:securityType,IMSI_CODE:imsiCode,USERNAME:username,MOBILE:mobile,BINDING_STATUS:bindingStatus,OPT_TIME:optTime,STATE:state,STATE_DATE:stateDate";
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	
	public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "MOBILE_STAFF_SEC_CONFIG_ID:mobileStaffSecConfigId,STAFF_ID:staffId,STAFF_NAME:staffName,SECURITY_TYPE:securityType,IMSI_CODE:imsiCode,USERNAME:username,MOBILE:mobile,BINDING_STATUS:bindingStatus,OPT_TIME:optTime,STATE:state,STATE_DATE:stateDate";
		String wherePatternStr = "MOBILE_STAFF_SEC_CONFIG_ID:mobileStaffSecConfigId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "MOBILE_STAFF_SEC_CONFIG_ID:mobileStaffSecConfigId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	
	public Map selById(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		sqlStr.append("AND T.MOBILE_STAFF_SEC_CONFIG_ID = ? ");
        String wherePatternStr = "MOBILE_STAFF_SEC_CONFIG_ID:mobileStaffSecConfigId";
		return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}
	
	public List selAll(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr.toString(), paramMap, wherePatternStr);
	}
	
	public Map selByPagin(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = this.getSqlStr(paramMap);
		int pageIndex = MapUtils.getInteger(paramMap, "pageIndex", 1);
		int pageSize = MapUtils.getInteger(paramMap, "pageSize", 10);
		return this.populateQueryByMap(sqlStr, pageIndex, pageSize);
	}
	
	private StringBuffer getSqlStr(Map paramMap) {
		StringBuffer sqlStr = new StringBuffer(" select T.MOBILE_STAFF_SEC_CONFIG_ID as mobileStaffSecConfigId,");
        sqlStr.append(" T.STAFF_ID as staffId, ");
        sqlStr.append(" T.STAFF_NAME as staffName, ");
        sqlStr.append(" T.SECURITY_TYPE as securityType, ");
        sqlStr.append(" T.IMSI_CODE as imsiCode, ");
        sqlStr.append(" T.USERNAME as username, ");
        sqlStr.append(" T.MOBILE as mobile, ");
        sqlStr.append(" T.BINDING_STATUS as bindingStatus, ");
        sqlStr.append(" to_date(T.OPT_TIME, 'yyyy-mm-dd hh24:mi:ss') as optTime ");
        //sqlStr.append(" T.STATE as state, ");
        //sqlStr.append(" T.STATE_DATE as stateDate ");
        sqlStr.append(" from MOBILE_STAFF_SEC_CONFIG T ");
        sqlStr.append(" WHERE STATE = 1 ");

		//
        String username = MapUtils.getString(paramMap, "username");
        if(StringUtils.isNotBlank(username))  {
            sqlStr.append(" AND T.USERNAME like '%" + username + "%' ");
        }

        String imsiCode = MapUtils.getString(paramMap, "imsiCode");
        if(StringUtils.isNotBlank(imsiCode))  {
            sqlStr.append(" AND T.IMSI_CODE like '%" + imsiCode + "%' ");
        }

        String mobile = MapUtils.getString(paramMap, "username");
        if(StringUtils.isNotBlank(mobile))  {
            sqlStr.append(" AND T.MOBILE like '%" + mobile + "%' ");
        }

        String staffName = MapUtils.getString(paramMap, "staffName");
        if(StringUtils.isNotBlank(staffName))  {
            sqlStr.append(" AND T.STAFF_NAME like '%" + staffName + "%' ");
        }

        Integer securityType = MapUtils.getInteger(paramMap, "securityType");
        if(null != securityType)  {
            sqlStr.append(" AND T.BINDING_STATUS = " + securityType + " ");
        }

        Integer bindingStatus = MapUtils.getInteger(paramMap, "bindingStatus");
        if(null != bindingStatus)  {
            sqlStr.append(" AND T.BINDING_STATUS = " + bindingStatus + " ");
        }

		System.out.println(TABLE_NAME +"´òÓ¡µÄSQLÊÇ£º" + sqlStr);
		return sqlStr;
	}

}
