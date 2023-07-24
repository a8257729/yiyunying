package com.ztesoft.mobile.v2.core;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.service.interf.MobileRestServService;

/**
 * User: heisonyee
 * Date: 13-2-5
 * Time: 下午3:01
 */
//@Controller("baseController")
public abstract class BaseController {
	
    private static final Logger logger = Logger.getLogger(BaseController.class);
    
    public static final boolean DEBUG = true;
    
    private MobileRestServService mobileRestService;
    

	public MobileRestServService getMobileRestService() {
		return mobileRestService;
	}

	@Autowired(required = false)
	public void setMobileRestService(MobileRestServService mobileRestService) {
		this.mobileRestService = mobileRestService;
	}

	@ExceptionHandler
    public void handleException(Exception ex, HttpServletRequest request , HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
            logger.error("BaseController捕获异常: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        
        //针对不同的Exception做不同的响应处理
        boolean logged = false;
        JSONObject resultJson = null;
        if(ex instanceof DataAccessException) {
            resultJson = JSONObject.fromObject(Result.buildDataAccessError());
            logged = true;
        } else if(ex instanceof IOException) {
        	logged = true;
        } else if(ex instanceof JsonParseException) {
        	logged = true;
        	resultJson = JSONObject.fromObject(Result.buildJsonParseError()); 
        } else if(ex instanceof JsonMappingException) {
        	logged = true;
        	resultJson = JSONObject.fromObject(Result.buildJsonMappingError()); 
        } else if(ex instanceof BusinessException){
        	//业务异常
        	logged = true;
        } else {
        	//写不写log呢??
        	logged = true;
        	resultJson = JSONObject.fromObject(Result.buildUnknownError());
        }
        
        try {
			response.getWriter().write(resultJson.toString());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(logged) {
			Long rId = -1L;
			String exName = ex.getClass().getName();
			String exMsg = ex.getMessage();
			try {
				this.getMobileRestService().writeExceptionLog(rId, exName, exMsg);
			} catch (Exception e) {
				//Print Stack but DO nothing.
				e.printStackTrace();
			}
		}
    }

	
	private String toJSONString(HttpServletRequest request) {
		String str = null;
		try {
			InputStream in = request.getInputStream();
			str = IOUtils.toString(in, "UTF-8");
			IOUtils.closeQuietly(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
