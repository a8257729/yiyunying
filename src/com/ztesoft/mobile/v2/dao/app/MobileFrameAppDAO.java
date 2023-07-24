package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 7:08
 */
public interface MobileFrameAppDAO extends BaseDAO {

    public void insert(Map paramMap)throws DataAccessException;
    
    public Map getSmsContentId(Map paramMap)  throws DataAccessException;
    
    public void insertSmsStaff(Map paramMap) throws DataAccessException;
    
    public void insertSmsContent(Map paramMap) throws DataAccessException;

    public void update(Map paramMap)throws DataAccessException;

    public void delete(Map paramMap)throws DataAccessException;

    public List selAll(Map paramMap)throws DataAccessException;
    
    public Map selByPage(Map paramMap) throws DataAccessException;

    public Map selById(Map paramMap)throws DataAccessException;
    
    public List selVersionIsExist(String versionName)throws DataAccessException;
    
    public List selVersionIsExist2(Map paramMap) throws DataAccessException;

    public Map getCurrentVersion(Map paramMap) throws DataAccessException;
    
    public Map getCurrentVersion(Integer osType) throws DataAccessException;
    
    public Map getLatestVersion(Map paramMap) throws DataAccessException;
    
    public Map getFrameAppById(Long appId) throws DataAccessException;
    
    public void incDownloadCount(Map paramMap) throws DataAccessException;

	public void updateIsRelease(Map param)throws DataAccessException;
	
	public void updateLatestIsRelease(Map param) throws DataAccessException;

	public void updateDownloadCount(Long frameAppId)  throws DataAccessException;
}
