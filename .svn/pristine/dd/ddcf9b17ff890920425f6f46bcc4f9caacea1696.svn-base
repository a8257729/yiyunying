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
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

/** REST服务 */
public class StaffControlsInterceptor extends BaseRestServInterceptor implements MethodInterceptor {
	
	private static final Logger logger = Logger.getLogger(StaffControlsInterceptor.class);
	
	private MobileRestServService mobileRestService;

	private MobileCommonService mobileCommonService;
	
	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

	public MobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

	@Autowired(required = false)
	public void setMobileCommonService(MobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
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
			logger.debug("StaffControlsInterceptor执行方法环绕通知");
		}

		Object result = null;
		
		if(!enabled) {
			
			if(logger.isDebugEnabled()) {
				logger.debug("未启用StaffControlsInterceptor拦截器");
			}
			
			return result = invocation.proceed();
		}
		
		Method method = invocation.getMethod();
		
		FilterTag filterTag = AnnotationUtils.findAnnotation(method, FilterTag.class);
		if(null != filterTag) {
			if(filterTag.skipStaffControls()) {
				if(logger.isDebugEnabled()) {
					logger.debug("跳过执行用户连接限制拦截器");
				}
				return result = invocation.proceed();
			}
		}
		
		RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		if(null != mapping) {
			Long staffId = new Long(-1L);
			if(invocation.getArguments().length > 0) {
				if(invocation.getArguments()[0] instanceof RequestEntity) {
					RequestEntity requestEntity = (RequestEntity) invocation.getArguments()[0];
					if(null != requestEntity.getAppToken()) {
						staffId = Long.valueOf(requestEntity.getAppToken());
					}
				}
			}
			
			//不处理-1的情况
			if(-1L == staffId) {
				return result = invocation.proceed();
			}
			
			// 获取RequestMapping内的urls值
			String[] urls = mapping.value();
			// String urlStrings = urlStrings(urls);
			List<MobileRestService> list = this.getMobileRestService()
					.getRestServList();
			
			MobileRestService serv = matchResServ(urls, list);
			
			StaffControls ctrl = this.getMobileRestService().getStaffControls(staffId);
			
			if (!getMobileCommonService().isConnectStaffExist(ctrl)) {
				getMobileCommonService().insertConnectStaff(ctrl, serv);
			}
			//更新用户状态
			getMobileCommonService().updateConnectState(ctrl, serv);
				
			if(null != ctrl) {
				if(Constants.StaffLimitType.CONNECT_LIMIT.equals(ctrl.getConnectLimit())) {
					if(logger.isDebugEnabled()) {
						logger.debug("STAFF_ID为: " + staffId + "的用户连接限制");
					}
					result = Result.buildLimitAccessError();
					
				} else if(Constants.StaffLimitType.CONNECT_NO_LIMIT.equals(ctrl.getConnectLimit())) {
					if(logger.isDebugEnabled()) {
						logger.debug("STAFF_ID为: " + staffId + "的用户连接正常");
					}
					result = invocation.proceed();
					
				} else {
					if(logger.isDebugEnabled()) {
						logger.debug("STAFF_ID为: " + staffId + "的用户未配置连接限制");
					}
					result = Result.buildLimitAccessNotConfigError();
				}
			} else {
				//不可能为NULL的
				result = invocation.proceed();
			}
		} else {
			result = invocation.proceed();
		}
				
		return result;
	}

	/** 拼接URLs字符串 */
	protected String urlStrings(String[] urls) {
		if(null == urls || 0 == urls.length) return "";
		
		String str = null;
		if(urls.length > 1) {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<urls.length; i++) {
				sb.append(urls[i]).append(",");
			}
			str = sb.substring(0, sb.length()-1);
		} else {
			str = urls[0];
		}
		return str;
	}
}
