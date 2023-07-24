package com.ztesoft.mobile.v2.service.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;
import com.ztesoft.mobile.v2.entity.common.MobileParam;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: ÏÂÎç4:37
 */
@Service("mobileParamService")
public class MobileParamServiceImpl extends BaseService implements MobileParamService {
    
    private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    @Cacheable(value={Constants.CacheKey.T02MIN_CACHE}, key="#gcode")
    public Result getParam(String gcode) {
        if(StringUtils.isBlank(gcode))
            Result.buildParameterError();

        Result result = null;
        try {
        	//
        	List list = getMobileParamDAO().getParam(gcode);
            
            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileParam.PARAM_NODE, list);

            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (DataAccessException e) {
            result = Result.buildServerError();
            e.printStackTrace();
        }
        return result;
    }

    public Result getParam(String gcode, Integer mcode) {
        if(StringUtils.isBlank(gcode) || (null==mcode))
            return Result.buildParameterError();

        String cacheKey = gcode + mcode;
        Result result = null;
        try {
            Map map = getMobileParamDAO().getParam(gcode, mcode, cacheKey);

            Map<Object, Object> resultData = new HashMap<Object, Object>();
            resultData.put(MobileParam.PARAM_NODE, map);
            
            result = Result.buildSuccess();
            result.setResultData(resultData);
        } catch (DataAccessException e) {
           result = Result.buildServerError();
           //Print stack
           e.printStackTrace();
        }
        return result;
    }
    
    /** */
    public Result getParamForApp(Integer osType) {
    	 Result result = null;
    	try {
			List resultList = this.getMobileParamDAO().getParamForApp(osType);

			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put(MobileParam.PARAM_NODE, resultList);
			//
			result = Result.buildSuccess();
			result.setResultData(resultData);
		} catch (DataAccessException e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
    }
    
    public Map getParamMap(String gcode, Integer mcode) throws Exception {
        if(StringUtils.isBlank(gcode) || (null==mcode))
            return Collections.EMPTY_MAP;

        String cacheKey = gcode + mcode;
        Map resultMap = getMobileParamDAO().getParam(gcode, mcode, cacheKey);

        return resultMap;
    }
    
    public List getParamMapList(String gcode) throws Exception {
        if(StringUtils.isBlank(gcode))
        	return Collections.EMPTY_LIST;
        List resultList = getMobileParamDAO().getParam(gcode);
        return resultList;
    }

}
