package com.ztesoft.mobile.v2.dao.common;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileStaffShortcutDAOImpl extends BaseDAOImpl implements MobileStaffShortcutDAO {

    private static final Logger logger = Logger.getLogger(MobileStaffShortcutDAOImpl.class);

    private static final String TABLE_NAME = "MOBILE_STAFF_SHORTCUT";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "STAFF_SHORTCUT_ID:@@SEQ,STAFF_ID:staffId,MENU_ID:menuId,OS_TYPE:osType,OPT_TIME:optTime";
        //Long nextId = getPKFromMem(TABLE_NAME, 9);
        //paramMap.put("notificationId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "STAFF_SHORTCUT_ID:staffShortcutId,STAFF_ID:staffId,MENU_ID:menuId,OS_TYPE:osType,OPT_TIME:optTime";
        String wherePatternStr = "NOTIFICATION_ID:notificationId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "NOTIFICATION_ID:notificationId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   STAFF_SHORTCUT_ID AS staffShortcutId,  STAFF_ID AS staffId,  MENU_ID AS menuId,  OS_TYPE AS osType,  OPT_TIME AS optTime FROM MOBILE_STAFF_SHORTCUT WHERE NOTIFICATION_ID=?";
        String wherePatternStr = "NOTIFICATION_ID:notificationId";
        return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        final StringBuffer sqlStr = buildSql(paramMap);
        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }

    private StringBuffer buildSql(Map paramMap) {
        StringBuffer sqlStr = new StringBuffer(" SELECT   STAFF_SHORTCUT_ID AS staffShortcutId, ");
        sqlStr.append(" STAFF_ID AS staffId, ");
        sqlStr.append(" MENU_ID AS menuId, ");
        sqlStr.append(" OS_TYPE AS osType ");
        sqlStr.append(" OPT_TIME AS optTime ");
        sqlStr.append(" FROM MOBILE_STAFF_SHORTCUT WHERE 1=1 ");

        Long staffId = MapUtils.getLong(paramMap, "staffId");
        if(null != staffId) {
            sqlStr.append(" AND STAFF_ID = " + staffId);
        }

        Integer osType = MapUtils.getInteger(paramMap, "osType");
        if(null != osType) {
            sqlStr.append(" AND OS_TYPE = " + osType);
        }

        Long menuId = MapUtils.getLong(paramMap, "menuId");
        if(null != menuId) {
            sqlStr.append(" AND MENU_ID = " + menuId);
        }
        //
        if(logger.isDebugEnabled()) {
            logger.debug("buildSql¥Ú”°SQL£∫" + sqlStr.toString());
        }

        return sqlStr;

    }

    public List selStaffShortcut(Long staffId, Integer osType) throws DataAccessException {
        StringBuffer sqlStr = new StringBuffer(" SELECT STAFF_SHORTCUT_ID AS staffShortcutId, ");
        sqlStr.append(" STAFF_ID AS staffId, ");
        sqlStr.append(" MENU_ID AS menuId, ");
        sqlStr.append(" OS_TYPE AS osType ");
        sqlStr.append(" FROM MOBILE_STAFF_SHORTCUT WHERE 1=1 ");

        if(null != staffId) {
            sqlStr.append(" AND STAFF_ID = " + staffId);
        }

        if(null != osType) {
            sqlStr.append(" AND OS_TYPE = " + osType);
        }

        if(logger.isDebugEnabled()) {
            logger.debug("buildSql¥Ú”°SQL£∫" + sqlStr.toString());
        }
        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}