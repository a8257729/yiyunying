package com.ztesoft.mobile.v2.dao.common;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileWebConfigDAOImpl extends BaseDAOImpl implements MobileWebConfigDAO {

    private static final Logger logger = Logger.getLogger(MobileWebConfigDAOImpl.class);

    private static final String TABLE_NAME = "MOBILE_WEB_CONFIG";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "WEB_CONFIG_ID:@@SEQ,CONFIG_CODE:configCode,CONFIG_VALUE:configValue,CONFIG_TIME:configTime,MEMO:memo";
        //Long nextId = getPKFromMem(TABLE_NAME, 9);
        //paramMap.put("webConfigId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "WEB_CONFIG_ID:webConfigId,CONFIG_CODE:configCode,CONFIG_VALUE:configValue,CONFIG_TIME:configTime,MEMO:memo";
        String wherePatternStr = "WEB_CONFIG_ID:webConfigId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "WEB_CONFIG_ID:webConfigId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = this.buildSql(paramMap);
        sqlStr.append(" AND WEB_CONFIG_ID = ? ");
        String wherePatternStr = "WEB_CONFIG_ID:webConfigId";
        return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = this.buildSql(paramMap);
        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }

    public String getConfigValue(String configCode) throws DataAccessException {
        final String sqlStr = "SELECT CONFIG_VALUE AS configValue FROM MOBILE_WEB_CONFIG WHERE CONFIG_CODE = '" + configCode + "' ";
        
        if(logger.isDebugEnabled()) {
            logger.debug("getConfigValue打印的SQL是：" + sqlStr.toString());
        }
        
        Map param = dynamicQueryObjectByMap(sqlStr, null, null);
        return MapUtils.getString(param, "configValue", null);
    }

    private StringBuffer buildSql(Map paramMap) {
        StringBuffer sqlStr = new StringBuffer(" SELECT   WEB_CONFIG_ID AS webConfigId, ");
        sqlStr.append(" CONFIG_CODE AS configCode, ");
        sqlStr.append(" CONFIG_VALUE AS configValue, ");
        sqlStr.append(" CONFIG_TIME AS configTime, ");
        sqlStr.append(" MEMO AS memo ");
        sqlStr.append(" FROM MOBILE_WEB_CONFIG WHERE 1=1 ");

        if(logger.isDebugEnabled()) {
            logger.debug("buildSql打印的SQL是：" + sqlStr.toString());
        }

        return sqlStr;
    }

    public String getTableName() {
        return TABLE_NAME;
    }

	public Map getConfigFTPPath(String configCode) throws DataAccessException {
		String sqlStr = "select ftp_addr as UP_IP_ADDR,ftp_user as UP_FTP_USER,ftp_password as UP_FTP_PASSWORD,ftp_port as UP_PORT,ftp_path as UP_PATH from FTP_CONFIG_INFO where ftp_keyword ='"+configCode+"'";
		if(logger.isDebugEnabled()) {
            logger.debug("getConfigFTPPath打印的SQL是：" + sqlStr);
        }
		return dynamicQueryObjectByMap(sqlStr, null, null);
	}
}