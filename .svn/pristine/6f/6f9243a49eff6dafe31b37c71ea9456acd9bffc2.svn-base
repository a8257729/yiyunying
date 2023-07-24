package com.ztesoft.mobile.service.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.outsystem.dao.RestServiceDAO;
import com.ztesoft.mobile.outsystem.dao.RestServiceDAOImpl;


public class HandlerFactory {

	//
	private Handler handler;
	
	public HandlerFactory() {
		//Do nothing here
	}
	
	public Handler createErrorHandler() {
		return this.handler = new RequestErrorHandler();
	}
	
	public Handler createHandler(Map paramMap) {
		//
		Map map = new HashMap();
		map.put("restServicePath", MapUtils.getString(paramMap, "path"));
		map.put("restServiceCode", MapUtils.getString(paramMap, "code"));
		
		Map rsMap = Collections.EMPTY_MAP;
		
		//获取REST服务信息
		rsMap = this.getRestService(map);
		
		System.out.println("REST Service is: " + rsMap);
		
		//若没有记录，则表示服务不存在，返回错误编码
		if(MapUtils.isEmpty(rsMap)) return (this.handler = new RequestErrorHandler());
		
		paramMap.put("restServiceId", MapUtils.getLongValue(rsMap, "restServiceId", -1L));
		paramMap.put("restServiceName", MapUtils.getString(rsMap, "restServiceName"));
		
		Integer restServiceType = MapUtils.getIntValue(rsMap, "restServiceType", -1);
		if(-1 != restServiceType) {
			//0：为了兼容原客户端的老代码而特别留下的状态，实现方式也是调用具体的实现类的；1：接口；2：具体实现类；3：透传实现
			if(0 == restServiceType || 2 == restServiceType) {
				String className = MapUtils.getString(rsMap, "restServiceClass", null);
				this.handler = initHandlerByClassName(className);
			} else if(1 == restServiceType) {	//走接口
				this.handler = new InterfaceHandler();
			} else if(3 == restServiceType){   //透传
				this.handler = new DirectInterfaceHandler();
			} else {
				this.handler = new RequestErrorHandler();
			}
		} else {
			this.handler = new RequestErrorHandler();
		}
		return this.handler;
	}
	
	private Handler initHandlerByClassName(String className) {
		Handler hdl = new RequestErrorHandler();
		if(null != className) {
			try {
				//实例化
				hdl = (Handler) Class.forName(className).newInstance();
			}catch(Exception ex) {
				//异常则
				System.out.println("Handler创建实例失败！" + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return hdl;
	}
	
	private RestServiceDAO getRestServiceDAO() {
		String daoName = RestServiceDAOImpl.class.getName();
		 return (RestServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private Map getRestService(Map paramMap) {
		Map dataMap = Collections.EMPTY_MAP;
		try {
			List list = this.getRestServiceDAO().selAll(paramMap);
			if(null != list && 0 != list.size()) {
				dataMap	= (Map) list.get(0);
			}
		} catch(Exception e) {
			System.out.println("[ERROR] 获取REST服务异常");
			e.printStackTrace();
		} 
		return dataMap;
	}
}
