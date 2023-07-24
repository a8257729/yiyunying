package com.ztesoft.mobile.v2.controller.workform.xinjiang.sa;


import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.workform.xinjiang.sa.WorkOrderSaService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
/***
 * 装移工单管控功能
 * 
 ** */
 
@Controller("xjWorkFormSaController")
public class WorkFormSaController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(WorkFormSaController.class);

	private WorkOrderSaService workOrderService;

	public WorkOrderSaService getWorkOrderService() {
		return workOrderService;
	}

	@Autowired(required = false)
	public void setWorkOrderService(WorkOrderSaService workOrderService) {
		this.workOrderService = workOrderService;
	}

	
	/**
	 * 装移工单 -- 查询反馈列表
	 */
	@RequestMapping(value = { "/client/xj/zy/feedback/query" })    
	public @ResponseBody
	Result queryFeedbackInfo(@RequestBody
	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryFeedbackInfo method ");
		}
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		// String username = json.optString(StaffInfo.USERNAME_NODE);
		// Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		// Integer pageIndex = json.optInt("pageIndex");
		// Integer pageSize = json.optInt("pageSize");
		Result result = getWorkOrderService().queryFeedbackInfo(json);
		return result;
	}
	
	
	/**
	 * 装移工单 -- 详情
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/detail" })    
	public @ResponseBody
	Result WorkOrderDetail(@RequestBody
	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call WorkOrderDetail method ");
		}
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		// String username = json.optString(StaffInfo.USERNAME_NODE);
		// Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		// Integer pageIndex = json.optInt("pageIndex");
		// Integer pageSize = json.optInt("pageSize");
		Result result = getWorkOrderService().workOrderDetail(json);
		return result;
	}
	
	/**
	 * 工单详情--web端
	 * @param Map
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/detail1" })
	public @ResponseBody
	Result WorkOrderDetail1(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) { 
			
			
			logger.debug(" Call WorkOrderDetail method ");
		}
//		System.out.println("the workOrderID is:"+workOderVO.toString());
//		System.out.println("the orderID is:"+orderID.toString());
		JSONObject js = new JSONObject();
		js.put("WorkOrderID",(String)data.get("workOrderID"));
		js.put("OrderID",(String)data.get("orderID"));
		
		//JSONObject json = JSONObject.fromObject(workOrderID);
		// String username = json.optString(StaffInfo.USERNAME_NODE);
		// Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		// Integer pageIndex = json.optInt("pageIndex");
		// Integer pageSize = json.optInt("pageSize");
		Result result = getWorkOrderService().workOrderDetail(js);
		return result;
	}
	
	
	
}
