package com.ztesoft.mobile.v2.controller.common;

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

import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.annotation.FilterTag;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobileFeedback;
import com.ztesoft.mobile.v2.entity.common.MobileNotice;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
/**
 * User: heisonyee
 * Date: 13-2-5
 * Time: 下午3:00
 */
@Controller("commonController")
public class CommonController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(CommonController.class);

    private MobileCommonService mobileCommonService;

    private MobileCommonService getMobileCommonService() {
        return mobileCommonService;
    }

    @Autowired(required = false)
    public void setMobileCommonService(MobileCommonService mobileCommonService) {
        this.mobileCommonService = mobileCommonService;
    }

    /**
     * 检查版本信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/common/version/check/android"})
    public @ResponseBody Result checkAndroidAppVersion(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call checkAppVersion method ");
        }
    	
    	Result result = getMobileCommonService().getFrameAppCurrentVersion(Constants.AppOsType.ANDROID);
        return result;
    }

    /**
     * 意见反馈
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/common/feedback/submit"})
    public @ResponseBody Result feedback(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call feedback method ");
        }
    	MobileFeedback feedback = requestEntity.parse(MobileFeedback.class);
    	Result result = getMobileCommonService().addFackback(feedback);
        return result;
    }

    /**
     * 分页查询公告消息
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @RequestMapping(value = {"/client/common/notice/page/{pageSize}/{pageIndex}/{latestNoticeTimestamp}"})
    public @ResponseBody Result selNoticeByPage(@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize,
    	@PathVariable("latestNoticeTimestamp") long latestNoticeTimestamp,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
        Result result = getMobileCommonService().selNoticeByPage(latestNoticeTimestamp, pageIndex, pageSize);
        return result;
    }
    
    /**
     * 根据最近公告时间，非分页查询公告消息
     * @param latestNoticeTimestamp
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/common/notice/list"})
    public @ResponseBody Result selNotice(@RequestBody RequestEntity requestEntity, 
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call selNotice method ");
        }
        
        //latestNoticeTime 的格式为：20110502131315，yyyyddmmhh24miss
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        String startNoticeTime = json.optString(MobileNotice.START_NOTICE_TIME_NODE);
        String endNoticeTime = json.optString(MobileNotice.END_NOTICE_TIME_NODE);
    	Result result = getMobileCommonService().selNotice(startNoticeTime, endNoticeTime);
        return result;
    }
    
    /** 应用初始化 */
    @RequestMapping(value = {"/client/common/app/init/android"})
    @FilterTag(skipRestServ=false)
    public @ResponseBody Result appInit(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call appInit method ");
        }
        
    	Result result = getMobileCommonService().appInit(Constants.AppOsType.ANDROID);
        return result;
    }

    /** 应用同步API */
    @RequestMapping(value = {"/client/common/app/sync/android"})
    public @ResponseBody Result appSync(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call appSync method ");
        }
        
    	Result result = getMobileCommonService().appInit(Constants.AppOsType.ANDROID);
        return result;
    }
    
    /** 账号绑定API */
    @RequestMapping(value = {"/client/common/acc/binding"})
    public @ResponseBody Result accBinding(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call accBinding method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Result result = getMobileCommonService().accBinding(json);
        return result;
    }
    
}
