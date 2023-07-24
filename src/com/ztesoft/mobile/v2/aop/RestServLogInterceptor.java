package com.ztesoft.mobile.v2.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;
import com.ztesoft.mobile.v2.util.JSONUtils;

/**
 * 
 * @author heisonyee
 *
 */
public class RestServLogInterceptor extends BaseRestServInterceptor implements MethodInterceptor {
	
	private static final Logger logger = Logger.getLogger(RestServLogInterceptor.class);

	private MobileRestServService mobileRestService;
	
	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
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
		if(logger.isDebugEnabled()) {
			logger.debug("RestServLogInterceptorִ�з�������֪ͨ");
		}
		
		Object result = null;
		
		if(!enabled) {
			
			if(logger.isDebugEnabled()) {
				logger.debug("δ����RestServiceInterceptor������");
			}
			
			return result = invocation.proceed();
		}
		
		String inJsonStr = null;
		Long staffId = -1L;		//NOTE���������ֱ�ӻ�ȡ��STAFF_ID�ģ������ҵ���߼��������Щ�߼�
		Long workOrderId = -1L;
	    String style = "";
		
		Method method = invocation.getMethod();
		
		FilterTag filterTag = AnnotationUtils.findAnnotation(method, FilterTag.class);
		if(null != filterTag) {
			if(filterTag.skipServLog()) {
				if(logger.isDebugEnabled()) {
					logger.debug("����ִ��REST��־��¼������");
				}
				return result = invocation.proceed();
			}
		}
		
		RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		
		if(null != mapping) {
			//��ȡRequestMapping�ڵ�urlsֵ
			String[] urls = mapping.value();
			
			if(invocation.getArguments().length > 0) {
				if(invocation.getArguments()[0] instanceof RequestEntity) {
					RequestEntity requestEntity = (RequestEntity) invocation.getArguments()[0];
					inJsonStr = requestEntity.toString();
					staffId = requestEntity.getAppToken() == null? -1L : Long.valueOf(requestEntity.getAppToken());
					
					JSONObject json = JSONObject.fromObject(requestEntity.getParams());
					if (json.containsKey(WorkOrder.WORK_ORDER_ID_NODE)) {
						workOrderId = json.getLong(WorkOrder.WORK_ORDER_ID_NODE);
					}
			        if (json.containsKey("style")) {
			        	style = json.getString("style");
			        }
				}
			}
			
			MobileRestServLog servLog = new MobileRestServLog();
			servLog.setInTimestamp(System.currentTimeMillis());
			servLog.setStaffId(staffId);
			servLog.setInContent(inJsonStr);
			servLog.setInSize(Long.valueOf(inJsonStr==null? 0: inJsonStr.getBytes().length));
			
			List<MobileRestService> list = this.getMobileRestService().getRestServList();

			result = invocation.proceed();
			
			if(urls.length >0) {	//������˵��urls�ǲ���Ϊ�յ�
				MobileRestService serv = matchResServ(urls, list);
				Long servId = -1L;
				if(null != serv) {
					servId = serv.getRestServiceId();
				} 
				
				String outJsonStr = null;
				try {
					outJsonStr = JSONUtils.toJSONString(result);
				} catch(Exception e) {
					e.printStackTrace();
				}
				
				servLog.setServLogType(Constants.ServLogType.NORMAL);
				servLog.setRestServiceId(servId);
				servLog.setOutContent(outJsonStr);
				servLog.setOutSize(Long.valueOf(outJsonStr==null? 0: outJsonStr.getBytes().length));
				servLog.setOutTimestamp(System.currentTimeMillis());
				
				Long resultCode = -1l;
				JSONObject  jasonObject = JSONObject.fromObject(outJsonStr);
				if (jasonObject.containsKey("resultCode")) {
					resultCode = jasonObject.getLong("resultCode");
				}
				
				servLog.setWorkOrderId(workOrderId);
				servLog.setStyle(style);
				servLog.setResultCode(resultCode);
				
				//д��־
				try {
					this.getMobileRestService().writeRestServLog(servLog);
				} catch(Exception ex) {
					ex.printStackTrace();
					logger.debug("REST_SERV��־д��ʧ��");
				}
				
			} else {
				//������urlsΪ�յ�������Ҳ�д��־
			}
			
		} else {
			result = invocation.proceed();
		}
		
		return result;
	}

}
