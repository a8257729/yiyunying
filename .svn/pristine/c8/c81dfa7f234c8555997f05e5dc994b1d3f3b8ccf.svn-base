package com.ztesoft.mobile.v2.controller.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.core.StatefulController;
import com.ztesoft.mobile.v2.entity.common.MobileParam;
import com.ztesoft.mobile.v2.service.common.MobileParamService;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午4:41
 */
@Controller("paramController")
public class ParamController extends StatefulController {

    private static final Logger logger = Logger.getLogger(ParamController.class);

    private MobileParamService mobileParamService;

    private MobileParamService getMobileParamService() {
        return mobileParamService;
    }

    @Autowired(required = false)
    public void setMobileParamService(MobileParamService mobileParamService) {
        this.mobileParamService = mobileParamService;
    }

    /** 用于客户端 */
    @RequestMapping(value = {"/client/param/one"})
    public @ResponseBody Result getParamOne(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call getParamOne");
        }
        
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String gcode = json.optString(MobileParam.GCODE_NODE);
        Integer mcode = json.optInt(MobileParam.MCODE_NODE);
    	Result result = getMobileParamService().getParam(gcode, mcode);
        return result;
    }

    /** 用于客户端 */
    @RequestMapping(value = {"/client/param/list"})
    public @ResponseBody Result getParamList(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call getParamList");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String gcode = json.optString(MobileParam.GCODE_NODE);
    	Result result = getMobileParamService().getParam(gcode);
        return result;
    }

    
    /** 用于Web页面 */
    @RequestMapping(value = {"/param/{gcode}/{mcode}"})
    @FilterTag
    public @ResponseBody Map getParamMap(@PathVariable("gcode") String gcode, @PathVariable("mcode") Integer mcode, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call getParamMap" + gcode + ", " + mcode);
        }
    	
    	Map resultMap = getMobileParamService().getParamMap(gcode, mcode);
        return resultMap;
    }
    
    /** 用于Web页面 */
    @RequestMapping(value = {"/param/list/{gcode}"})
    @FilterTag
    public @ResponseBody List getParamMapList(@PathVariable("gcode") String gcode, 
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call getParamMapList, Query: " + gcode);
        }
    	
    	List resultList = getMobileParamService().getParamMapList(gcode);
        return resultList;
    }
}
