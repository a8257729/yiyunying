package com.ztesoft.mobile.v2.service.assistant;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.AAAInterface.AAAInterface;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;

/**
 * 装维助手模块服务
 *
 */
@Service("assistantService")
public class AssistantServiceImpl extends BaseService implements AssistantService {
	
	private static final Logger logger = Logger.getLogger(AssistantServiceImpl.class);

	public Result queryAAAUserInlineStatus(String userName){
		Result result = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultMap = AAAInterface.getAAAInterfaceService().queryAAAUserInlineStatus(userName);
		String status = "";
		if(resultMap != null && !StringUtil.isNull((String) resultMap.get("retCode"))){
			if("0".equals(resultMap.get("retCode"))){
				status = "在线";
			}else{
				status = "不在线";
			}
		}else{
			logger.error("查询3A状态失败，userName:" + userName);
			result = Result.buildFailure();
			return result;
		}
		Map<String,Object> returnMap = new HashMap<String,Object>();
		returnMap.put("status", status);
		returnMap.put("serStarttime", (String) resultMap.get("serStarttime"));
		returnMap.put("nasportid", (String) resultMap.get("nasportid"));
		resultData.put("data_info", returnMap);
		result = Result.buildSuccess();
		result.setResultData(resultData);
		return result;
	}


	
}
