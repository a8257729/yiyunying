package com.ztesoft.mobile.v2.service.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.common.CommonDAO;
import com.ztesoft.mobile.v2.dao.common.CommonDAOImpl;

/**
 *公共模块服务
 *
 */
@Service("baseCommonService")
public class CommonServiceImpl extends BaseService implements CommonService {
	
	private static final Logger logger = Logger.getLogger(CommonServiceImpl.class);

	 private CommonDAO getCommonDAO() {
	        String daoName = CommonDAOImpl.class.getName();
	        return (CommonDAO) BaseDAOFactory.getImplDAO(daoName);
	    }

	
	public Result commonQueryObjectBySql(String sqlStr,Map paramMap,String wherePatternStr){
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = getCommonDAO().commonQueryObjectBySql(sqlStr,paramMap,wherePatternStr);
		
		resultData.put("data_info", resultMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}

	public Result commonQueryListBySql(String sql, Map<String, Object> paramMap, String wherePatternStr) {
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = getCommonDAO().commonQueryListBySql(sql,paramMap,wherePatternStr);
		
		resultData.put("data_info", resultMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}
	
	public Result commonQueryObjectByProcedure(String procedureName, List paramList, int inParamLength,String[] outParam) {
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = getCommonDAO().commonQueryObjectByProcedure(procedureName,paramList,inParamLength,outParam);
		
		resultData.put("data_info", resultMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}
	
	
	public Result commonInsertObjectBySql(String sqlStr,Map paramMap){
		boolean operResult = getCommonDAO().commonInsertObjectBySql(sqlStr,paramMap);
		Result result = null;
		if(operResult){
			result = Result.buildSuccess();
		}else{
			result = Result.buildFailure();
		}
		return result;
	}


	public int commonQueryTotalBySql(String sqlStr,Map paramMap,String wherePatternStr) {

		return  getCommonDAO().commonQueryTotalBySql(sqlStr, paramMap,wherePatternStr);
	}
}
