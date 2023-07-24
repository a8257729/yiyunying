package com.ztesoft.mobile.v2.web.interceptor;

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 改为用AOP方式实现
 * @author heisonyee
 *
 */
@Deprecated
public class RestServiceInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = Logger.getLogger(RestServiceInterceptor.class);
	
	private static final String APPLICATION_JSON_VALUE = "application/json";
	
	private static final String UTF8_ENCODING = "UTF-8";
	
	/** REST服务列表 */
	//protected static Hashtable<String, MobileRestService> restServUrlMapping = new Hashtable<String, MobileRestService>();
	
	//private ThreadLocal<MobileRestServLog> servLogThreadLocal = new ThreadLocal<MobileRestServLog>();
	
	private MobileRestServService mobileRestService;
	
	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug(" Call preHandle() method. ");
		}
		
		boolean flag = true;
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			RequestMapping mapping = method.getMethodAnnotation(RequestMapping.class);
			String[] urls = mapping.value();
			//Print logger
			String urlStrings = urlStrings(urls);
			if(logger.isDebugEnabled()) {
				logger.debug("访问的URLs: " + urlStrings);
			}
			List<MobileRestService> list = this.getMobileRestService().getRestServList();
			if(urls.length >0) {
				MobileRestService serv = null;
				for(int j=0; j<urls.length; j++) {
					MobileRestService item = this.matchRestServ(urls[j], list);
					if(null != item) {
						serv = item;
						break;
					} 
				}
				//判断serv是否为null
				if(null != serv) {
					if(Constants.RestServStatus.FORBIDDEN.equals(serv.getServStatus())) {
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "禁止访问。");
						}
						//
						Result result = Result.buildRestServForbiddenError();
						wrapResponseWithJsonType(response, result);
						//
						flag = false;
						
					} else if(Constants.RestServStatus.NORMAL.equals(serv.getServStatus())) {
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "正常访问。");
						}
						//TODO 应该做啥
						
					} else {	//提示状态设置不正确
						if(logger.isDebugEnabled()) {
							logger.debug(urlStrings + "设置错误。");
						}
						
						Result result = Result.buildRestServStatusError();
						wrapResponseWithJsonType(response, result);
						//
						flag = false;
						
					}
				} else {	//提示未配置REST服务
					Result result = Result.buildRestServConfigError();
					wrapResponseWithJsonType(response, result);
					//
					flag = false;
					
				}
				
			} else {
				//TODO Do what???
			}
			
		} else {
			//DO NOTHING HERE!!
		}
		
		//写调用日志 
		//this.getMobileRestService().writeRestServLog(-1L, Constants.ServOptType.REQUEST);
		
		return flag;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug(" Call afterCompletion() method. ");
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug(" Call preHandle() method. ");
		}
	}
	
	private MobileRestService matchRestServ(String url, List<MobileRestService> list) {
		
		MobileRestService serv = null;
		for(int i=0; i<list.size(); i++) {
			MobileRestService item = list.get(i);
			if(url.equalsIgnoreCase(item.getServAddr())) {
				serv = item;
				break;
			}
		}
		return serv;
	}
	
	/** 拼接URLs字符串 */
	private String urlStrings(String[] urls) {
		if(null == urls || 0 == urls.length) return null;
		
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
	
	private void wrapResponseWithJsonType(HttpServletResponse response, Result result) {
		
		JSONObject resultJson = JSONObject.fromObject(result);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(UTF8_ENCODING);
        response.setStatus(200);
        
        try {
			response.getWriter().write(resultJson.toString());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
