package com.ztesoft.mobile.v2.controller.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.controller.common.ParamController;
import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.service.app.MobileFrameAppService;


/**
 * Time:2013/4/3
 * @author Leemon
 *
 */
@Controller("mobileFrameAppController")
public class FrameAppController extends BaseController {
	private static final Logger logger = Logger.getLogger(ParamController.class);
	
	private MobileFrameAppService mobileFrameAppService;

    private MobileFrameAppService getMobileFrameAppService() {
        return mobileFrameAppService;
    }
    
    @Autowired(required = false)
    public void setMobileFrameAppService(MobileFrameAppService mobileFrameAppService) {
        this.mobileFrameAppService = mobileFrameAppService;
    }
    
    @RequestMapping(value = {"/frame/app/exists/{versionName}"})
    public @ResponseBody Map versionExists(@PathVariable("versionName") String versionName, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call versionExists:" + versionName);
        }
    	
        Map reslutMap = getMobileFrameAppService().versionNameExists(versionName);
        
        return reslutMap;
    }
    
}
