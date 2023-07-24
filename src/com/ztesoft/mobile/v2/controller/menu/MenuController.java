package com.ztesoft.mobile.v2.controller.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.service.common.MobileMenuService;

/**
 * User: heisonyee
 * Date: 13-3-12
 * Time: ÉÏÎç10:48
 */
@Controller("menuController")
public class MenuController extends BaseController {

    private MobileMenuService mobileMenuService;

    private MobileMenuService getMobileMenuService() {
        return mobileMenuService;
    }

    @Autowired(required = false)
    public void setMobileMenuService(MobileMenuService mobileMenuService) {
        this.mobileMenuService = mobileMenuService;
    }

    @RequestMapping(value = {"/client/menu/all"})
    public @ResponseBody Result getMenu(@RequestBody RequestEntity requestEntity, 
            HttpServletRequest request, HttpServletResponse response) {
    	
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
		Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		Long defaultJobId = json.optLong(JobInfo.DEFAULT_JOB_ID_NODE);
		Integer osType = json.optInt(StaffInfo.OS_TYPE_NODE);
		
        Result result = getMobileMenuService().queryMenu(staffId, jobId, defaultJobId, osType);
        return result;
    }
    
    @RequestMapping(value = {"/client/menu/catalog"})
    public @ResponseBody Result getMenuCatalog(@RequestBody RequestEntity requestEntity, 
            HttpServletRequest request, HttpServletResponse response) {
	
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
		Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		Long defaultJobId = json.optLong(JobInfo.DEFAULT_JOB_ID_NODE);
		Integer osType = json.optInt(StaffInfo.OS_TYPE_NODE);
    		
        Result result = getMobileMenuService().queryMenuCatalog(staffId, jobId, defaultJobId, osType);
        return result;
    }
    
    @RequestMapping(value = {"/client/menu/sub"})
    public @ResponseBody Result getSubMenu(@RequestBody RequestEntity requestEntity, 
            HttpServletRequest request, HttpServletResponse response) {
    	
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Long staffId = json.optLong(StaffInfo.STAFF_ID_NODE);
		Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		Long defaultJobId = json.optLong(JobInfo.DEFAULT_JOB_ID_NODE);
		Integer osType = json.optInt(StaffInfo.OS_TYPE_NODE);
		
        Result result = getMobileMenuService().querySubMenu(staffId, jobId, defaultJobId, osType);
        return result;
    }
}
