package com.ztesoft.mobile.v2.dao.common;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: ÏÂÎç4:10
 */
public interface MobileParamDAO extends BaseDAO {

    public void insert(Map paramMap) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public Map selByPage(Map paramMap) throws DataAccessException;

    public List selForParam(Map paramMap) throws DataAccessException;

    public List getParam(String gcode) throws DataAccessException;

    public Map getParam(String gcode, Integer mcode, String cacheKey) throws DataAccessException;

    public String getMname(String gcode, Integer mcode, String cacheKey) throws DataAccessException;
    
    public Map selForParamByConn(Map paramMap) throws DataAccessException;
    
    public List getParamForApp(Integer osType) throws DataAccessException;

}
