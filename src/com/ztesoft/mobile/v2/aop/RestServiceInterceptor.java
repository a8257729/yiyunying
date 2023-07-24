package com.ztesoft.mobile.v2.aop;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;
import com.ztesoft.mobile.v2.util.JSONUtils;

/** REST服务 */
public class RestServiceInterceptor extends BaseRestServInterceptor implements MethodInterceptor {
	
	private static final Logger logger = Logger.getLogger(RestServiceInterceptor.class);
	
	private MobileRestServService mobileRestService;
	
	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

	/** 是否开启该拦截器 */
	private boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if(logger.isDebugEnabled()) {
			logger.debug("RestServiceInterceptor执行方法环绕通知");
		}

		Object result = null;
		
		if(!enabled) {
			
			if(logger.isDebugEnabled()) {
				logger.debug("未启用RestServiceInterceptor拦截器");
			}
			
			return result = invocation.proceed();
		}
		
		String inJsonStr = null;
		
		Method method = invocation.getMethod();
		
		FilterTag filterTag = AnnotationUtils.findAnnotation(method, FilterTag.class);
		if(null != filterTag) {
			if(filterTag.skipRestServ()) {
				if(logger.isDebugEnabled()) {
					logger.debug("跳过执行REST服务过滤拦截器");
				}
				return result = invocation.proceed();
			}
		}
		
		RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		
		if(null != mapping) {
			//获取RequestMapping内的urls值
			String[] urls = mapping.value();
			String urlStrings = urlStrings(urls);
			
			if(invocation.getArguments().length > 0) {
				if(invocation.getArguments()[0] instanceof RequestEntity) {
					RequestEntity requestEntity = (RequestEntity) invocation.getArguments()[0];
					inJsonStr = requestEntity.toString();
				}
			}
			
			MobileRestServLog servLog = new MobileRestServLog();
			servLog.setInTimestamp(System.currentTimeMillis());
			servLog.setInContent(inJsonStr);
			servLog.setInSize(Long.valueOf(inJsonStr==null? 0: inJsonStr.getBytes().length));
			
			List<MobileRestService> list = this.getMobileRestService().getRestServList();

			Long servId = -1L;
			Integer servLogType = 0;
			boolean logFlag = true;
			if(urls.length >0) {	//正常来说，urls是不能为空的
				MobileRestService serv = matchResServ(urls, list);
				if(null != serv) {
					if(Constants.RestServStatus.FORBIDDEN.equals(serv.getServStatus())) {
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "禁止访问。");
						}
						//
						result = Result.buildRestServForbiddenError();
						//
						servLogType = Constants.ServLogType.ABNORMAL;
						
					} else if(Constants.RestServStatus.NORMAL.equals(serv.getServStatus())){
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "正常访问。");
						}
						//
						result = invocation.proceed();
						logFlag = false;
						
					} else {
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "设置错误。");
						}
						//
						result = Result.buildRestServStatusError();
						//
						servLogType = Constants.ServLogType.ABNORMAL;
					}
					servId = serv.getRestServiceId();
					
				} else {
					result = Result.buildRestServConfigError();
					//
					servLogType = Constants.ServLogType.ABNORMAL;
				}
				
				String outJsonStr = null;
				try {
					outJsonStr = JSONUtils.toJSONString(result);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				servLog.setRestServiceId(servId);
				servLog.setServLogType(servLogType);
				servLog.setOutContent(outJsonStr);
				servLog.setOutSize(Long.valueOf(outJsonStr==null? 0: outJsonStr.getBytes().length));
				servLog.setOutTimestamp(System.currentTimeMillis());
				
				//写日志
				try {
					if(logFlag) {
						this.getMobileRestService().writeRestServLog(servLog);
					}
				} catch(Exception ex) {
					ex.printStackTrace();
					logger.debug("REST_SERV日志写入失败");
				}
				
			} else {
				result = invocation.proceed();
			} 
			
		} else {
			result = invocation.proceed();
		}
		
		return result;
	}

}
