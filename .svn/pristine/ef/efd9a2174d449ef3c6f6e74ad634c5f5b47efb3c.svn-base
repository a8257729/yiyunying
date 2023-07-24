package com.ztesoft.mobile.v2.controller.workform.xinjiang.bz;

import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.service.workform.xinjiang.bz.MyWorkOrderBzService;
import com.ztesoft.mobile.v2.service.workform.xinjiang.bz.MyWorkOrderBzServiceImpl;
import com.ztesoft.mobile.v2.service.workform.xinjiang.bz.WorkOrderBzService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Linping
 *
 */
@Controller("xjWorkOrderBzController")
public class WorkOrderBzController extends WebConfigController {
	
	private static final Logger logger = Logger.getLogger(WorkOrderBzController.class);

    private WorkOrderBzService workOrderService;
    private MyWorkOrderBzService myworkOrderService = new MyWorkOrderBzServiceImpl();
    private WorkOrderBzService getWorkOrderService() {
        return workOrderService;
    }
    
    @Autowired(required = false)
    public void setWorkOrderService(WorkOrderBzService workOrderService) {
        this.workOrderService = workOrderService;
    }
    
    private static String WORK_ORDER_LIST_URL = "";
    private static String WORK_ORDER_DETIAL_URL = "";
    private static String WORK_ORDER_ACCEPT_URL = "";
    private static String WORK_ORDER_DEPART_URL = "";
         
     
    @RequestMapping(value = {"/client/xj/bz/faultorder/private/page"})
    public @ResponseBody Result selPrivateWorkOrderByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	String username = json.optString(StaffInfo.USERNAME_NODE);
    	Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
    	Integer pageIndex = json.optInt("pageIndex");
    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().selSaPrivateWorkOrderByPage(username, jobId, pageSize, pageIndex);
        return result;
    }
    
    //临时故障工单(私有的)------------------------------------------
    @RequestMapping(value = {"/client/xj/bz/faultorder/private/page1"})
    public @ResponseBody Result selPrivateWorkOrderByPage(@RequestBody Map<String,Object> map) throws Exception {
    	
    	String username = (String)map.get("username");
        String orderType = (String)map.get("orderType");
    	Long jobId = Long.valueOf((String)map.get("jobId"));
    	Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
    	Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
    	if("sa".equals(orderType))
        {
            Result result = myworkOrderService.selSaPrivateWorkOrderByPage(username, jobId, pageSize, pageIndex);
            return result;
        }
      else
        {
            Result result = myworkOrderService.selZdPrivateWorkOrderByPage(username, jobId, pageSize, pageIndex);
            return result;
        }

    }


    @RequestMapping(value = {"/client/xj/bz/service/order/page1"})
    public @ResponseBody Result selPrivateServiceOrderByPage(@RequestBody Map<String,Object> map){
        String username = (String)map.get("username");
        Long jobId = Long.valueOf((String)map.get("jobId"));
        Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
        Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
        Result result = myworkOrderService.selZDPrivateWorkOrderByPage(username, jobId, pageSize, pageIndex);
        return result;
    }
   
    /**
     * 故障工单
     * @param requestEntity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/xj/bz/faultorder/public/page"})
    public @ResponseBody Result selPublicWorkOrderByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	String username = json.optString(StaffInfo.USERNAME_NODE);
    	Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
    	Integer pageIndex = json.optInt("pageIndex");
    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().selSaPublicWorkOrderByPage(username, jobId, pageSize, pageIndex);
        return result;
    }
    
    /**
     * 保障待办工单详情
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/bz/faultorder/private/detail"})
    public @ResponseBody Result selSaPrivateWorkOrderDetail(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) {
    	if(logger.isDebugEnabled()) {
        	logger.debug(" Call selSaPrivateWorkOrderDetail method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long workOrderId = json.optLong(WorkOrderBz.WORK_ORDER_ID_NODE);
    	Result result = getWorkOrderService().selSaPrivateWorkOrderDetail(workOrderId);
        return result;
    }
    
    
    /**
     * 保障待办工单详情
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/bz/web/faultorder/private/detail"})
    public @ResponseBody Result selWebSaPrivateWorkOrderDetail(@RequestBody Map<String,Object> map) {
    	if(logger.isDebugEnabled()) {
        	logger.debug(" Call selSaPrivateWorkOrderDetail method ");
        }
    	

        Long workOrderId = Long.valueOf((String)map.get("WorkOrderID"));
    	Result result = getWorkOrderService().selSaPrivateWorkOrderDetail(workOrderId);
        return result;
    }
    
    /**
     * 保障待办工单详情
     * @param requestEntity
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/bz/faultorder/public/detail"})
    public @ResponseBody Result selSaPublicWorkOrderDetail(@RequestBody RequestEntity requestEntity, HttpServletRequest request, HttpServletResponse response) {
    	if(logger.isDebugEnabled()) {
        	logger.debug(" Call selWorkOrderDetail method ");
        }
    	
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long workOrderId = json.optLong(WorkOrderBz.WORKORDER_ID_NODE);
    	Result result = getWorkOrderService().selSaPublicWorkOrderDetail(workOrderId);
        return result;
    }
}
