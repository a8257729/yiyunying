package com.ztesoft.mobile.v2.controller.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.v2.annotation.FilterTag;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.MobileStaffSignin;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.common.MobileStaffSigninService;
import com.ztesoft.mobile.v2.util.XMLParseUtils;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午1:46
 */
@Controller("staffController")
public class StaffController extends BaseController {

    private static final Logger logger = Logger.getLogger(StaffController.class);

/*    private MobileParamService mobileParamService;

    private MobileParamService getMobileParamService() {
        return mobileParamService;
    }

    @Autowired(required = false)
    public void setMobileParamService(MobileParamService mobileParamService) {
        this.mobileParamService = mobileParamService;
    }*/

    private MobileCommonService mobileCommonService;
    
    private MobileStaffSigninService mobileStaffSigninService;

    private MobileCommonService getMobileCommonService() {
        return mobileCommonService;
    }

    @Autowired(required = false)
    public void setMobileCommonService(MobileCommonService mobileCommonService) {
        this.mobileCommonService = mobileCommonService;
    }

    private MobileStaffSigninService getMobileStaffSigninService() {
        return mobileStaffSigninService;
    }
    
    @Autowired(required = false)
    public void setMobileStaffSigninService(MobileStaffSigninService mobileStaffSigninService) {
        this.mobileStaffSigninService = mobileStaffSigninService;
    }

    /**
     * 用户登录
     * @param staffInfo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/authenicate"}, method = {RequestMethod.POST})
    @FilterTag
    public String authenicate(ModelMap modelMap, @RequestParam(value="username", required=false) String username,
            @RequestParam(value = "password",  required=false) String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
            logger.debug(" Call login method. ");
        }

        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()) {     //

            return "redirect:../";

        } else {    //未验证
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            currentUser.login(token);

            return "redirect:../";
        }
    }
    /**
     * 用户登录
     * @param staffInfo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/login"})
    //不跳过REST服务拦截的处理
    @FilterTag(skipRestServ=false)
    public @ResponseBody Result login(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call login method. ");
        }
        StaffInfo staffInfo = requestEntity.parse(StaffInfo.class);
        Result result = getMobileCommonService().login(staffInfo);
        return result;
    }

    /**
     * 易信微信用户登录
     * @param openId/username
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/yxwx/staff/login"})
    //不跳过REST服务拦截的处理
    @FilterTag(skipRestServ=false)
    public @ResponseBody Result yxwxlogin(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call login method. ");
        }
        StaffInfo staffInfo = requestEntity.parse(StaffInfo.class);
        Result result = getMobileCommonService().login(staffInfo);
        return result;
    }
    /**
     * 用户注销
     * @param staffInfo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/logout"})
    public @ResponseBody Result logout(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call logout method. ");
        }
        StaffInfo staffInfo = requestEntity.parse(StaffInfo.class);
    	Result result = getMobileCommonService().logout(staffInfo);
        return result;
    }

    /**
     * 获取默认职位API
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/job/default"})
    public @ResponseBody Result getDefaultJob(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call logout method. ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
    	Result result = getMobileCommonService().selDefaultJob(staffId);
        return result;
    }
    
    /**
     * 通过openid获取StaffId及默认职位API
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/yxwx/staff/query"})
    public @ResponseBody Result getStaffIByOpenId(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isInfoEnabled()) {
        	logger.info(" Call getStaffIByOpenId method. ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	Result result = getMobileCommonService().getStaffByOpenId(json);
        return result;
    }
    /**
     * 获取非默认职位API
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/job/list/normal"})
    public @ResponseBody Result getNormalJobList(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call logout method. ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
    	Result result = getMobileCommonService().selNormalJobList(staffId);
        return result;
    }
    
    /**
     * 获取默认职位和正常职位列表信息API
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/job/list/all"})
    public @ResponseBody Result getJobList(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call logout method. ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
    	Result result = getMobileCommonService().selJobList(staffId);
        return result;
    }
    
    /**
     * 用户退出
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/staff/exit"})
    public @ResponseBody Result exit(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call logout method. ");
        }
        StaffInfo staffInfo = requestEntity.parse(StaffInfo.class);
    	Result result = getMobileCommonService().logout(staffInfo);
        return result;
    }
    

    /**
     * 用户签到
     * @param requestEntity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/staff/signin"})
    public @ResponseBody Result staffSignin(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	MobileStaffSignin staffSignin =  requestEntity.parse(MobileStaffSignin.class);
        Result result = getMobileStaffSigninService().addStaffSignin(staffSignin);
        return result;
    }
   
    @RequestMapping(value = {"/client/staff/signin/list"})
    public @ResponseBody Result getStaffSigninList(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	MobileStaffSignin staffSignin =  requestEntity.parse(MobileStaffSignin.class);
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long staffId = json.getLong(StaffInfo.STAFF_ID_NODE);
        Map<String, Long> param = new HashMap();
        param.put("STAFF_ID", staffId);
    	List list = getMobileStaffSigninService().getMobileStaffSigninService(param);
        
        Map resultMap = new HashMap();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		resultData.put("sigininList", list);
      
		Result result = Result.buildSuccess();
		result.setResultData(resultData);
        return result;
    }
    
    /**
     * 签到
     * @param requestEntity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/staff/passwd/update"})
    public @ResponseBody Result resetPasswd(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json =  JSONObject.fromObject(requestEntity.getParams());
    	Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
    	String oldPasswd = json.optString(StaffInfo.OLD_PASSWD_NODE);
    	String newPasswd = json.optString(StaffInfo.NEW_PASSWD_NODE);
        Result result = getMobileCommonService().updatePassword(staffId, oldPasswd, newPasswd);
        return result;
    }
}
