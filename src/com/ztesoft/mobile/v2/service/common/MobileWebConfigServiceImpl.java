package com.ztesoft.mobile.v2.service.common;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.dao.common.MobileWebConfigDAO;
import com.ztesoft.mobile.v2.dao.common.MobileWebConfigDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-3-18
 * Time: 下午2:27
 */
@Service("mobileWebConfigService")
public class MobileWebConfigServiceImpl implements MobileWebConfigService {
	
	private static final Logger logger = Logger.getLogger(MobileWebConfigServiceImpl.class);

    private MobileWebConfigDAO getMobileWebConfigDAO() {
        String daoName = MobileWebConfigDAOImpl.class.getName();
        return (MobileWebConfigDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    /* 启用缓存，提升性能 */
    @Cacheable(value = Constants.CacheKey.T15MIN_CACHE, key = "#configCode")
    public String getConfigValue(String configCode) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("获取" + configCode + "的缓存数据");
    	}
        String configValue = null;
        try {
            configValue = getMobileWebConfigDAO().getConfigValue(configCode);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return configValue;
    }
   
    /* 清理缓存 */
    @CacheEvict(value = Constants.CacheKey.T15MIN_CACHE, key="#configCode")
    public void flushConfigValue(String configCode) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("清理" + configCode + "的缓存数据");
    	}
    	//Do nothing
    }

	public Map getConfigFTPPath(String configCode) {
    	if(logger.isDebugEnabled()) {
    		logger.debug("获取" + configCode + "的缓存数据");
    	}
		Map ftpMap = null;
		try {
			ftpMap = getMobileWebConfigDAO().getConfigFTPPath(configCode);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return ftpMap;
	}
}
