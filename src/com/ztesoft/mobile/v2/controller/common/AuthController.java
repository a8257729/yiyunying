package com.ztesoft.mobile.v2.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.v2.annotation.FilterTag;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.Result;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller("authController")
public class AuthController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AuthController.class);

    @RequestMapping(value = {"/client/auth/test/get"})
    @FilterTag
    public @ResponseBody Result testGet(HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
            logger.debug("Call testGet method");
        }

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        resultData.put("content", "测试服务正常");

        return Result.buildSuccess(resultData);
    }

    @RequestMapping(value = {"/client/auth/index"})
    @FilterTag
    public @ResponseBody Result authIndex(HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
            logger.debug("Call authIndex method");
        }

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        resultData.put("content", "EBiz服务首页");

        return Result.buildSuccess(resultData);
    }

    @RequestMapping(value = {"/client/auth/authorized"})
    @FilterTag
    public @ResponseBody Result authorized(HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
            logger.debug("Call authorized method");
        }

        Map<Object, Object> resultData = new HashMap<Object, Object>();
        resultData.put("content", "服务授权成功");

        return Result.buildSuccess(resultData);
    }
	
    /** 用于客户端 */
    @RequestMapping(value = {"client/auth/unauthorized"})
    public @ResponseBody Result unauthorized(HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug("Call unauthorized method");
        }

        return Result.buildUnauthorized();
    }

}
