package com.ztesoft.mobile.v2.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

/** �������������� */
public class FlowControlsInterceptor extends BaseRestServInterceptor implements
		MethodInterceptor {

	private static final Logger logger = Logger
			.getLogger(StaffControlsInterceptor.class);

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

	/** �Ƿ����������� */
	private boolean enabled;
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("FlowControlsInterceptorִ�з�������֪ͨ");
		}
		
		Object result = null;
		
		if(!enabled) {
			
			if(logger.isDebugEnabled()) {
				logger.debug("δ����FlowControlsInterceptor������");
			}
			
			return result = invocation.proceed();
		}
		
		Method method = invocation.getMethod();

		RequestMapping mapping = AnnotationUtils.findAnnotation(method,
				RequestMapping.class);
		if (null != mapping) {
			Long staffId = new Long(-1L);
			if (invocation.getArguments().length > 0) {
				if (invocation.getArguments()[0] instanceof RequestEntity) {
					RequestEntity requestEntity = (RequestEntity) invocation
							.getArguments()[0];
					if (null != requestEntity.getAppToken()) {
						staffId = Long.valueOf(requestEntity.getAppToken());
					}
				}
			}

			// ������-1�����
			if (-1L == staffId) {
				return result = invocation.proceed();
			}

			StaffControls ctrl = this.getMobileRestService().getStaffControls(
					staffId);

			FilterTag filterTag = AnnotationUtils.findAnnotation(method,
					FilterTag.class);
			if (null != filterTag) {
				if (filterTag.skipFlowControls()) {		//�Ƿ�������������������
					if (logger.isDebugEnabled()) {
						logger.debug("����ִ����������������");
					}
					return result = invocation.proceed();
				}
			}

			if (null != ctrl) {
				if (isOverFlow(ctrl)) {
					if (logger.isDebugEnabled()) {
						logger.debug("STAFF_IDΪ: " + staffId + "���û�����������ֵ");
					}
					result = Result.buildFlowOverloadError();
				} else {
				/*	if (logger.isDebugEnabled()) {
						logger.debug("");
					}
					result = Result.buildLimitAccessNotConfigError();*/
					
					if (logger.isDebugEnabled()) {
						logger.debug("�������û�����δ������ֵ");
					}
					
					result = invocation.proceed();
				}
			} else {
				// ������ΪNULL��
				result = invocation.proceed();
			}
		} else {
			result = invocation.proceed();
		}

		return result;
	}

	/** ƴ��URLs�ַ��� */
	protected String urlStrings(String[] urls) {
		if (null == urls || 0 == urls.length)
			return "";

		String str = null;
		if (urls.length > 1) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < urls.length; i++) {
				sb.append(urls[i]).append(",");
			}
			str = sb.substring(0, sb.length() - 1);
		} else {
			str = urls[0];
		}
		return str;
	}

	/** ����Ƿ񳬳����� */
	protected boolean isOverFlow(StaffControls ctrl) {
		try {
			List retList = this.getMobileCommonService().getStaffFlowCount(
					ctrl.getStaffId());

			if (retList == null || retList.size() < 1)
				return false;

			Map rtMap = (Map) retList.get(0);
			Long sumInSize = MapUtils.getLong(rtMap, "sumInSize", 0L);
			Long sumOutSize = MapUtils.getLong(rtMap, "sumOutSize", 0L);
			//ʵ�����ĵ�����ֵ
			long costTotal = sumInSize.longValue() + sumOutSize.longValue();
			//�趨����������ֵ
			long ctrlTotal = ctrl.getUserFlowLimit().longValue() * 1024 * 1024;
			
			if(logger.isDebugEnabled()) {
				logger.debug("�����ĵ�������ֵ: " + costTotal + "(B)");
				logger.debug("�趨����������ֵ: " + ctrlTotal + "(B)");
			}
			
			if (costTotal > ctrlTotal)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
