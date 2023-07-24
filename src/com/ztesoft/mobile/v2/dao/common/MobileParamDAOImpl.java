package com.ztesoft.mobile.v2.dao.common;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.Constants;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午4:12
 */
public class MobileParamDAOImpl extends BaseDAOImpl implements MobileParamDAO {

    private static final Logger logger = Logger.getLogger(MobileParamDAOImpl.class);
    private static final String TABLE_NAME = "MOBILE_PARAM";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "PARAM_ID:@@SEQ,GCODE:gcode,MCODE:mcode,MNAME:mname,TARGET:target,TYPE:type,MEMO:memo,UPDATE_TIME:updateTime,CREATE_TIME:createTime,STATE:state,STATE_DATE:stateDate";
        Long nextId = getPKFromMem(TABLE_NAME, 9);
        paramMap.put("paramId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }
    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "PARAM_ID:paramId,GCODE:gcode,MCODE:mcode,MNAME:mname,TARGET:target,TYPE:type,MEMO:memo,UPDATE_TIME:updateTime,STATE_DATE:stateDate";
        String wherePatternStr = "PARAM_ID:paramId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "PARAM_ID:paramId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = buildSql(paramMap);
        sqlStr.append(" AND PARAM_ID = ? ");
        String wherePatternStr = "PARAM_ID:paramId";
        return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
    }

    //TODO 未实现
    public Map selByPage(Map paramMap) throws DataAccessException {
        return null;
    }

    public List selAll(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = buildSql(paramMap);
        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }
    private StringBuffer buildSql(Map paramMap) {
        StringBuffer sqlStr = new StringBuffer(" SELECT PARAM_ID AS paramId, ");
        sqlStr.append(" GCODE AS gcode, ");
        sqlStr.append(" MCODE AS mcode, ");
        sqlStr.append(" MNAME AS mname, ");
        sqlStr.append(" TARGET AS target, ");
        sqlStr.append(" TYPE AS type, ");
        sqlStr.append(" MEMO AS memo, ");
        sqlStr.append(" UPDATE_TIME AS updateTime, ");
        sqlStr.append(" CREATE_TIME AS createTime, ");
        sqlStr.append(" STATE AS state, ");
        sqlStr.append(" STATE_DATE AS stateDate ");
        sqlStr.append(" FROM MOBILE_PARAM WHERE STATE = 1 ");
        return sqlStr;
    }

    public List selForParam(Map paramMap) throws DataAccessException {
        final StringBuffer sqlStr = new StringBuffer(" SELECT MCODE AS mcode, ");
        sqlStr.append(" GCODE AS gcode, ");
        sqlStr.append(" TARGET AS target, ");
        sqlStr.append(" MNAME AS mname ");
        sqlStr.append(" FROM MOBILE_PARAM WHERE STATE = 1");

        String gcode = MapUtils.getString(paramMap, "gcode");
        if(StringUtils.isNotBlank(gcode)) {
            sqlStr.append(" AND GCODE like '%" + gcode + "%' ");
        }
        String mname = MapUtils.getString(paramMap, "mname");
        if(StringUtils.isNotBlank(mname)) {
            sqlStr.append(" AND MNAME like '%" + mname + "%' ");
        }
        String target = MapUtils.getString(paramMap, "target");
        if(StringUtils.isNotBlank(target)) {
            sqlStr.append(" AND TARGET = " + target + "");
        }
        Integer mcode = MapUtils.getInteger(paramMap, "mcode");
        if(null != mcode){
            sqlStr.append(" AND MCODE = " + mcode + " ");
        }
        
        if(logger.isDebugEnabled()) {
        	logger.debug("buildSql: " + sqlStr.toString());
        }

        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }

    @Cacheable(value = Constants.CacheKey.T10MIN_CACHE, condition = "#gcode")
    public List getParam(String gcode)  throws DataAccessException {
        Map paramMap = new HashMap();
        paramMap.put("gcode", gcode);
        List list = this.selForParam(paramMap);
        return list;
    }

    @Cacheable(value=Constants.CacheKey.T10MIN_CACHE, key="#cacheKey")
    public Map getParam(String gcode, Integer mcode, String cacheKey) throws DataAccessException {
        Map paramMap = new HashMap();
        paramMap.put("gcode", gcode);
        paramMap.put("mcode", mcode);
        
        Map map =  Collections.EMPTY_MAP;
        List<Map> list  = this.selForParam(paramMap);
        if(null != list || 0 != list.size())  {
            map = list.get(0);
        }
        return map;
    }

    //@Cacheable(value=Constants.CacheKey.T10MIN_CACHE, key="#cacheKey")
    public String getMname(String gcode, Integer mcode, String cacheKey) throws DataAccessException {
        Map map = getParam(gcode, mcode, cacheKey);
        String mname = MapUtils.getString(map, "mname");
        return mname;
    }
    
    public Map selForParamByConn(Map paramMap) throws DataAccessException{
    	final StringBuffer sqlStr = new StringBuffer(" SELECT MCODE AS mcode, ");
        //sqlStr.append(" MCODE AS mcode, ");
        sqlStr.append(" MNAME AS mname ");
        sqlStr.append(" FROM MOBILE_PARAM WHERE STATE = 1");

        String gcode = MapUtils.getString(paramMap, "gcode");
        if(StringUtils.isNotBlank(gcode)) {
            sqlStr.append(" AND GCODE = '" + gcode + "' ");
        }

        Integer mcode = MapUtils.getInteger(paramMap, "mcode");
        if(null != mcode){
            sqlStr.append(" AND MCODE = " + mcode + " ");
        }

        return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
    }
    
    public List getParamForApp(Integer osType) throws DataAccessException {
        final StringBuffer sqlStr = new StringBuffer(" SELECT MCODE AS mcode, ");
        sqlStr.append(" GCODE AS gcode, ");
        //sqlStr.append(" TARGET AS target, ");
        sqlStr.append(" MNAME AS mname ");
        sqlStr.append(" FROM MOBILE_PARAM WHERE STATE = 1 ");
        
        if(Constants.AppOsType.ANDROID.equals(osType)) {
        	sqlStr.append(" AND (TARGET = 0 OR TARGET = 2) ");
        } else if(Constants.AppOsType.IOS.equals(osType)) {
        	sqlStr.append(" AND (TARGET = 0 OR TARGET = 3) ");
        }else if(Constants.AppOsType.IOS.equals(osType)) {
        	sqlStr.append(" AND (TARGET = 0 OR TARGET = 4) ");
        } else {
        	sqlStr.append(" AND TARGET = 0 ");
        }
        //
        if(logger.isDebugEnabled()) {
        	logger.debug("getParamForApp(Integer osType)打印的SQL是: " + sqlStr.toString());
        }
        
        return this.dynamicQueryListByMap(sqlStr.toString(), null, null);
    }
}
