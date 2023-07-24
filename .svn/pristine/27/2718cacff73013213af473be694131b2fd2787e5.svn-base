package com.ztesoft.mobile.v2.dao.common;


import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MobileWebConfigDAO extends BaseDAO {

    public void insert(Map paramMap) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;

    public String getConfigValue(String configCode) throws DataAccessException;
    
    public Map getConfigFTPPath(String configCode) throws DataAccessException;

}