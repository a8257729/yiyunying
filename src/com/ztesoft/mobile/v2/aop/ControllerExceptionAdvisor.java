package com.ztesoft.mobile.v2.aop;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.BusinessException;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

public class ControllerExceptionAdvisor extends BaseRestServInterceptor implements ThrowsAdvice {
	
	private static final Logger logger = Logger.getLogger(ControllerExceptionAdvisor.class);
	
	private MobileRestServService mobileRestService;
	
	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

	public void afterThrowing(Method method, Object[] args, Object target,
			Exception ex) throws Throwable {
		if(logger.isDebugEnabled()) {
			logger.debug("ControllerExceptionAdvisor��׽Controller�׳����쳣");
		}
		
		FilterTag skipLog = AnnotationUtils.findAnnotation(method, FilterTag.class);
		if(null != skipLog) {
			if(logger.isDebugEnabled()) {
				logger.debug("����ִ����־����");
			}
		}
		
		RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
		
		String inJsonStr = null;
		
		if(null != mapping) {
			//��ȡRequestMapping�ڵ�urlsֵ
			String[] urls = mapping.value();
			String urlStrings = urlStrings(urls);
			
			if(logger.isDebugEnabled()) {
				logger.debug("�׳��쳣�ķ���" + urlStrings);
			}
			
			if(args.length > 0) {
				if(args[0] instanceof RequestEntity) {
					RequestEntity requestEntity = (RequestEntity) args[0];
					inJsonStr = requestEntity.toString();
				}
			}
			
			MobileRestServLog servLog = new MobileRestServLog();
			servLog.setInTimestamp(System.currentTimeMillis());
			servLog.setInContent(inJsonStr);
			servLog.setInSize(Long.valueOf(inJsonStr==null? 0: inJsonStr.getBytes().length));
			
			List<MobileRestService> list = this.getMobileRestService().getRestServList();
			
			if(urls.length >0) {	//������˵��urls�ǲ���Ϊ�յ�
				
		        //��Բ�ͬ��Exception����ͬ����Ӧ����
		        boolean logged = false;
		        Long servId = -1L;
				
				MobileRestService serv = matchResServ(urls, list);
				if(null != serv) {
					servId = serv.getRestServiceId();
				} 

		        JSONObject resultJson = null;
		        if(ex instanceof DataAccessException) {
		            resultJson = JSONObject.fromObject(Result.buildDataAccessError());
		            logged = true;
		        } else if(ex instanceof IOException) {
		        	logged = true;
		        	resultJson = JSONObject.fromObject(Result.buildServerError());
		        } else if(ex instanceof JsonParseException) {
		        	logged = true;
		        	resultJson = JSONObject.fromObject(Result.buildJsonParseError()); 
		        } else if(ex instanceof JsonMappingException) {
		        	logged = true;
		        	resultJson = JSONObject.fromObject(Result.buildJsonMappingError()); 
		        } else if(ex instanceof BusinessException){
		        	//ҵ���쳣
		        	logged = true;
		        	resultJson = JSONObject.fromObject(Result.buildServerError());
		        } else {
		        	//д��дlog��??
		        	logged = true;
		        	resultJson = JSONObject.fromObject(Result.buildUnknownError());
		        }
		        
		        String outJsonStr = resultJson.toString();
				
				servLog.setRestServiceId(servId);
				servLog.setServLogType(Constants.ServLogType.ABNORMAL);
				servLog.setOutContent(outJsonStr);
				servLog.setOutSize(Long.valueOf(outJsonStr==null? 0: outJsonStr.getBytes().length));
				servLog.setOutTimestamp(System.currentTimeMillis());
				
				//д��־
				try {
					if(logged) {
						this.getMobileRestService().writeRestServLog(servLog);
					}
				} catch(Exception e) {
					e.printStackTrace();
					logger.debug("REST_SERV��־д��ʧ��");
				}
				
			} else {
				
			} 
			
		} else {
			
		}
		
	}
}
