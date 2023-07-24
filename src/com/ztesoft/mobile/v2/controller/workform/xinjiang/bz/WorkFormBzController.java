package com.ztesoft.mobile.v2.controller.workform.xinjiang.bz;

import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.service.workform.xinjiang.bz.WorkOrderBzService;
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

@Controller("xjWorkformBzController")
public class WorkFormBzController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(WorkOrderBzController.class);

	private WorkOrderBzService workOrderService;

	public WorkOrderBzService getWorkOrderService() {
		return workOrderService;
	}

	@Autowired(required = false)
	public void setWorkOrderService(WorkOrderBzService workOrderService) {
		this.workOrderService = workOrderService;
	}

	/**
	 * 退单原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/cancelorder/reason/query" })
	public @ResponseBody
	Result cancelOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call cancelOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().cancelOrderReason(json);
		return result;
	}
	/**
	 * 退单原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/web/cancelorder/reason/query" })
	public @ResponseBody
	Result webCancelOrderReason(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call cancelOrder method ");
		}

		JSONObject json = new JSONObject();
		json.put("WorkOrderID", (String)data.get("WorkOrderID"));
		Result result = this.getWorkOrderService().cancelOrderReason(json);
		return result;
	}


	/**
	 * 故障签单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/faultorder/sign" })
	public @ResponseBody
	Result signFaultOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call acceptFaultOrder method ");
		}

		throw new RuntimeException("未实现");
	}


	/**
	 * 故障单接单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/faultorder/accept" })
	public @ResponseBody
	Result acceptFaultOrder(@RequestBody RequestEntity requestEntity,
			HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call acceptFaultOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().acceptFaultOrder(json);
		return result;
	}

	/**
	 * 故障单回单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/faultorder/reply" })
	public @ResponseBody
	Result replyFaultOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call replyFaultOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().replyFaultOrder(json);
		return result;
	}
	
	/**
	 * 故障单回单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/web/faultorder/reply" })
	public @ResponseBody
	Result webReplyFaultOrder(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call replyFaultOrder method ");
		}

		JSONObject json = new JSONObject();
		json.put("staffArea",(String) data.get("staffArea"));
		json.put("handPathName",(String) data.get("handPathName"));
		json.put("handId",(String) data.get("handId"));
		json.put("accNbr",(String) data.get("accNbr"));
		json.put("staffId",(String) data.get("staffId"));
		json.put("orderType",(String) data.get("orderType"));
		json.put("workOrderId",(String) data.get("workOrderId"));
		json.put("workOrderCode",(String) data.get("workOrderCode"));
		json.put("autoReturnVisit",(String) data.get("autoReturnVisit"));
		json.put("isVisit",(String) data.get("isVisit"));
		json.put("recoverTime",(String) data.get("recoverTime"));
		json.put("recoverReasonId",(String) data.get("recoverReasonId"));
		json.put("maintainStaffId",(String) data.get("maintainStaffId"));
		json.put("arriveTime",(String) data.get("arriveTime"));
		json.put("recoverConfirmStaff",(String) data.get("recoverConfirmStaff"));
		json.put("confirmTel",(String) data.get("confirmTel"));
		json.put("desc",(String) data.get("desc"));
		json.put("remark",(String) data.get("remark"));
		json.put("reliefRemark",(String) data.get("reliefRemark"));
		json.put("resChangeDes",(String) data.get("resChangeDes"));
		json.put("timeOutReasonId",(String) data.get("timeOutReasonId"));
		json.put("trackHelpId",Long.parseLong((String) data.get("trackHelpId")));
		json.put("yhym",(String) data.get("yhym"));
		Result result = this.getWorkOrderService().replyFaultOrder(json);
		return result;
	}
	
	
	/**
	 * 加载回单数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/bz/faultorder/load" })
	public @ResponseBody
	Result loadReplyOrderData(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call replyOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService()
				.loadReplyFaultOrderData(json);
		return result;
	}

   /**
    * 障碍工单监控 -- 查询 
    */
    @RequestMapping(value = {"/client/xj/bz/workorder/barrier/query"})
    public @ResponseBody Result queryBarrierList(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call getStaffList method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//    	String username = json.optString(StaffInfo.USERNAME_NODE);
//    	Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
//    	Integer pageIndex = json.optInt("pageIndex");
//    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().workOrderContorlQuery(json);
        return result;
    }
    /**
     * 障碍工单监控 -- 详情
     */
    @RequestMapping(value = {"/client/xj/bz/workorder/barrier/detail"})
    public @ResponseBody Result queryBarrierDetail(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug("障碍工单监控 -- 详情 method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
        Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
        Result result =getWorkOrderService().workOrderContorlDtlie(json);
        return result;
    }    
   

}
