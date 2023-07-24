package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface MobileAppDAO extends BaseDAO {

    public Map insert(Map paramMap) throws DataAccessException;

    public void update(Map paramMap) throws DataAccessException;

    public void delete(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap) throws DataAccessException;

    public List selAll(Map paramMap) throws DataAccessException;
    
    public Map selByConn(Map paramMap) throws DataAccessException;
    
    public Map selVisionCodeByConn(Map paramMap) throws DataAccessException;

    public void updateAppStatus(Map paramMap) throws DataAccessException;
    
    /** 获取有做集成的APP */
    public List getFuncAppList(Integer osType) throws DataAccessException;
    
    public Map getAppById(Long appId) throws DataAccessException;

    
    public void updateDownloadCount(Long appId)  throws DataAccessException;
    
    public List getAppOrderCount(Map paramMap) throws DataAccessException;
}

