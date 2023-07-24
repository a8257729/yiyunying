package com.ztesoft.mobile.v2.dao.menu;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileMenuConfigDAOImpl extends BaseDAOImpl implements MobileMenuConfigDAO {

    private static final Logger logger = Logger.getLogger(MobileMenuConfigDAOImpl.class);

    private static final String TABLE_NAME = "MOBILE_MENU_CONFIG";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "STATE:state,STATE_DATE:stateDate,CONFIG_CODE:configCode,MENU_CONFIG_ID:menuConfigId,MENU_ID:menuId,MENU_TYPE:menuType,MENU_URI:menuUri,IS_MAIN:isMain,BUILD_TIME:buildTime,UPDATE_TIME:updateTime";
        Long nextId = getPKFromMem(TABLE_NAME, 9);
        paramMap.put("menuConfigId", nextId);
        paramMap.put("configCode", "FUNC_"+nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "STATE:state,STATE_DATE:stateDate,CONFIG_CODE:configCode,MENU_CONFIG_ID:menuConfigId,MENU_ID:menuId,MENU_TYPE:menuType,MENU_URI:menuUri,IS_MAIN:isMain,BUILD_TIME:buildTime,UPDATE_TIME:updateTime";
        String wherePatternStr = "MENU_CONFIG_ID:menuConfigId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "MENU_CONFIG_ID:menuConfigId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   STATE AS state,  STATE_DATE AS stateDate,  CONFIG_CODE AS configCode,  MENU_CONFIG_ID AS menuConfigId,  MENU_ID AS menuId,  MENU_TYPE AS menuType,  MENU_URI AS menuUri,  IS_MAIN AS isMain,  BUILD_TIME AS buildTime,  UPDATE_TIME AS updateTime FROM MOBILE_MENU_CONFIG WHERE MENU_CONFIG_ID=?";
        String wherePatternStr = "MENU_CONFIG_ID:menuConfigId";
        return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   STATE AS state,  STATE_DATE AS stateDate,  CONFIG_CODE AS configCode,  MENU_CONFIG_ID AS menuConfigId,  MENU_ID AS menuId,  MENU_TYPE AS menuType,  MENU_URI AS menuUri,  IS_MAIN AS isMain,  BUILD_TIME AS buildTime,  UPDATE_TIME AS updateTime FROM MOBILE_MENU_CONFIG";
        String wherePatternStr = null;
        return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selMenuConfigByCons(Long staffId, Long jobId, Long defaultJobId, Integer osType) throws DataAccessException {
        /*
          select c.menu_config_id as menuConfigId,
               c.menu_id        as menuId,
               c.menu_type      as menuType,
               c.menu_uri       as menuUri,
               c.config_code    as configCode
          from mobile_staff_job_right b,
               uos_job_staff          a,
               mobile_menu            m,
               mobile_menu_config     c
          where a.job_id = b.job_id
               and m.priv_code = b.priv_code
               and m.parent_id = 0
               and m.state = 1
               and c.state = 1
               and a.state = '1'
               and c.menu_id = m.menu_id
         */
        StringBuffer sqlStr = new StringBuffer(" select c.menu_config_id as menuConfigId, ");
        sqlStr.append(" c.menu_id        as menuId, ");
        sqlStr.append(" c.menu_type      as menuType, ");
        sqlStr.append(" c.menu_uri       as menuUri, ");
        sqlStr.append(" c.is_main        as isMain, ");
        sqlStr.append(" c.config_code    as configCode ");
        sqlStr.append(" from mobile_staff_job_right b, ");
        sqlStr.append(" uos_job_staff          a, ");
        sqlStr.append(" mobile_menu            m, ");
        sqlStr.append(" mobile_menu_config     c ");
        sqlStr.append(" where a.job_id = b.job_id ");
        sqlStr.append(" and m.priv_code = b.priv_code ");
        //sqlStr.append(" and m.parent_id = 0 ");
        sqlStr.append(" and m.state = 1 ");
        sqlStr.append(" and c.state = 1");
        sqlStr.append(" and a.state = '1' ");
        sqlStr.append(" and c.menu_id = m.menu_id ");
        sqlStr.append(" and a.staff_id = " + staffId);
        //sqlStr.append(" and m.os_type = " + osType);
        sqlStr.append(" and (a.job_id = " + jobId + " or a.job_id = " + defaultJobId + ") ");
        
        //
        if(logger.isDebugEnabled()) {
            logger.debug("selMenuConfigByCons打印的SQL是：" + sqlStr.toString());
        }

        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }

   public String getTableName() {
        return TABLE_NAME;
    }
}