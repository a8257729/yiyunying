package com.ztesoft.mobile.v2.controller.workform.xinjiang.dz;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.service.workform.xinjiang.kt.WorkOrderKtService;

/**
 * 待装工单处理类
 * @author Dell
 *
 */
@Controller("WorkOrderPendingController")
public class WorkorderPendingController extends WebConfigController {
	
	private static final Logger logger = Logger.getLogger(WorkorderPendingController.class);

    private WorkOrderKtService workOrderService;

    private WorkOrderKtService getWorkOrderService() {
        return workOrderService;
    }
    
    @Autowired(required = false)
    public void setWorkOrderService(WorkOrderKtService workOrderService) {
        this.workOrderService = workOrderService;
    }
    
    
   
    
    //待装工单查询--------------------------------------------------------------------
    @RequestMapping(value = {"/client/xj/dz/private/page"})
    public @ResponseBody Result selPrivateWorkOrderByPage(
    		@RequestBody Map<String,Object> map) throws Exception {
    	String username = (String)map.get("username");
    	logger.info("待装工单查询,userName:" + username);
    	Long jobId = Long.valueOf((String)map.get("jobId"));
    	Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
    	Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
    	String accNbr = (String)map.get("accNbr");
        String bokState = (String)map.get("bokState");
        String bokTime = (String)map.get("bokTime");
        String createDate = (String)map.get("createDate");
        Result result = getWorkOrderService().selPendingWorkOrderByPageCondition(username, jobId, pageSize, pageIndex, accNbr, bokState, bokTime, createDate);
        return result;
    }
    
    //所需参数查询--------------------------------------------------------------------
    @RequestMapping(value = {"/client/xj/dz/private/params"})
    public @ResponseBody Result selParamsByStaffId(
    		@RequestBody Map<String,Object> map) throws Exception {
    	
    	String staff_id = (String)map.get("staff_id");
        Result result = getWorkOrderService().selParamsByStaffId(staff_id);
        return result;
    }
    
    
    /**
     * 移出待装工单
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/xj/dz/removeToKt"})
    public @ResponseBody Result dzRemoveToKtOrder(
    		@RequestBody Map<String,Object> map) throws Exception {
    	String orderId = (String)map.get("orderId");
    	logger.info("移出待装工单,orderId:" + orderId);
    	
        Result result = Result.buildSuccess();
        return result;
    }
 
}
