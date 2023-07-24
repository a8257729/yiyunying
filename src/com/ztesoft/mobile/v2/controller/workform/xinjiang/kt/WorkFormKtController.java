package com.ztesoft.mobile.v2.controller.workform.xinjiang.kt;

import com.google.gson.Gson;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.CancelOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.ReplyOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.service.common.MobileCommonService;
import com.ztesoft.mobile.v2.service.workform.xinjiang.kt.WorkOrderKtService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller("xjWorkFormKtController")
public class WorkFormKtController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(WorkFormKtController.class);

	private WorkOrderKtService workOrderService;
	
	private MobileCommonService mobileCommonService;
	
	private CommonService commonService;

	private CommonService getCommonService() {
		return commonService;
	}

	@Autowired(required = false)
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}


	public WorkOrderKtService getWorkOrderService() {
		return workOrderService;
	}

	@Autowired(required = false)
	public void setWorkOrderService(WorkOrderKtService workOrderService) {
		this.workOrderService = workOrderService;
	}
	
    private MobileCommonService getMobileCommonService() {
		return mobileCommonService;
	}

    @Autowired(required = false)
	public void setMobileCommonService(MobileCommonService mobileCommonService) {
		this.mobileCommonService = mobileCommonService;
	}

	/**
	 * 开通工单监控 -- 查询
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/barrier/query" })    
	public @ResponseBody
	Result queryBarrierList(@RequestBody
	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryBarrierList method ");
		}
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		// String username = json.optString(StaffInfo.USERNAME_NODE);
		// Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		// Integer pageIndex = json.optInt("pageIndex");
		// Integer pageSize = json.optInt("pageSize");
		Result result = getWorkOrderService().workOrderContorlQuery(json);
		return result;
	}

	/**
	 * 开通工单监控 -- 详情
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/barrier/detail" })
	public @ResponseBody
	Result queryBarrierDetail(@RequestBody
	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryBarrierDetail method ");
		}
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		//Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		//Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
		Result result = getWorkOrderService().workOrderContorlDtlie(json);
		return result;
	}
	


	/**开通工单监控退单原因查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/jiankong/reason/query" })
	public @ResponseBody
	Result cancelOrderktReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call cancelOrderktJkReason method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().cancelOrderktJkReason(json);
		return result;
	}

	/**
	 * 开通工单监控退单
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/return" })
	public @ResponseBody
	Result returnWorkOrder(@RequestBody
	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call getStaffList method ");
		}
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
	//	Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
	//	Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
		Result result = getWorkOrderService().cancelOrderktJk(json);
		return result;
	}
//	private WorkOrderKtService workOrderService;
//
//	public WorkOrderKtService getWorkOrderService() {
//		return workOrderService;
//	}
//
//	@Autowired(required = false)
//	public void setWorkOrderService(WorkOrderKtService workOrderService) {
//		this.workOrderService = workOrderService;
//	}

	/**
	 * 退单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/cancelorder/submit" })
	public @ResponseBody
	Result cancelOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call cancelOrder method ");
		}
		
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().cancelOrder(json);
		return result;
	}
	/**
	* 退单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/backorder/submit" })
	public @ResponseBody
	Result backOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call backOrder method ");
		}
		
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().backOrder(json);
		return result;
	}
	
	/**
	 * 退单web端
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/backorderinfo/submit" })
	public @ResponseBody
	Result webToBackOrder(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call backOrder method ");
		}
		String content = "";
		String executorId = "";
		String remark = "";
		String 	orderCode = "";
		String orderType="";
		String staffId = "";
		String userName = "";
        String workOrderID = "";
        if(!data.isEmpty())
        {
        	content = (String)data.get("content");
        	executorId =(String) data.get("executorId");
        	remark = (String)data.get("remark");
        	orderCode =(String) data.get("workOrderCode");
        	orderType = (String)data.get("orderType");
        	staffId =(String) data.get("staffId");
        	userName = (String)data.get("userName");
        	workOrderID =(String) data.get("workOrderID");
        }
		JSONObject json = new JSONObject();
		json.put(CancelOrder.WORKORDER_ID_NODE, workOrderID);
		json.put(CancelOrder.WORKORDER_CODE_NODE, orderCode);
		json.put(CancelOrder.EXECUTOR_ID_NODE, executorId);
		json.put(CancelOrder.USER_NAME_NODE, userName);
		json.put(CancelOrder.STAFF_ID_NODE, staffId);
		json.put("content", content);
		json.put("orderType", orderType);
		json.put("remark", remark);
		Result result = this.getWorkOrderService().backOrder(json);
		return result;
	}
	
	
	/**
	 * 获取 开通工单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/detail/resource" })
	public @ResponseBody
	Result getResourceInfoList(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call backOrder method ");
		}
		
        String workOrderID = "";
        if(!data.isEmpty())
        {
        	
        	workOrderID =(String) data.get("workOrderID");
        }
		
		Result result = this.getWorkOrderService().getResourceInfoList(workOrderID);
		return result;
	}
	
	/**
	 * 退单原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/callBack/reason" })
	public @ResponseBody
	Result getCallBackReason(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call backOrder method ");
		}
		
		Result result = this.getWorkOrderService().getCallBackReason();
		return result;
	}
	
	/**
	 * ??????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/cancelorder/reason/query" })
	public @ResponseBody
	Result cancelOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call cancelOrderReason method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().cancelOrderReason(json);
		return result;
	}
	
	/**
	 * 待装
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/waitorder/submit" })
	public @ResponseBody
	Result waitOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call waitOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().waitOrder(json);
		return result;
	}
	/**
	 * 待装原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/waitorder/reason/query" })
	public @ResponseBody
	Result waitOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call waitOrderReason method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().waitOrderReason(json);
		return result;
	}
	
	/**
	 *  指派原因
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/appoint/reason/query" })
	public @ResponseBody
	Result appointOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call appointOrderReason method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().appointOrderReason(json);
		return result;
	}

	/**
	 * 原因查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/workorder/reason/query" })
	public @ResponseBody
	Result workOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call workOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().workOrderReason(json);
		return result;
	}
	private HuNanDAO getWorkOrderDAO() {
		String daoName = HuNanDAOImpl.class.getName();
		return (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	@ResponseBody
	@RequestMapping(value = "/client/xj/kt/workorder/reason/btn_yySA", method = RequestMethod.POST)
	public String queryBtnYySA(@RequestBody Map<String, Object> data) {
		// 小区pon口用户查询
		logger.info("getPonInfo param:"+data);
		JSONObject  js = new JSONObject();
		String result="000";
		try {
			Map resultMap = getWorkOrderDAO().queryBtnYySA((String) data.get("staffId"));

			if("0".equals((String)resultMap.get("count1")))
			{
				//查出来为0，所以预约按钮展示
				js.put("result","000");

			}
			else
			{
				js.put("result","001");
			}
		} catch (Exception e) {
			e.printStackTrace();
			js.put("result","000");
		}


		return js.toString();
	}
	/**
	 *故障签单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/faultorder/sign" })
	public @ResponseBody
	Result signFaultOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call acceptFaultOrder method ");
		}

		throw new RuntimeException("δ???");
	}

	/**
	 * ????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/order/accept" })
	public @ResponseBody
	Result acceptKtOrder(@RequestBody RequestEntity requestEntity,
			HttpServletRequest request, HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call acceptKtOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().acceptKtOrder(json);
		return result;
	}

	/**
	 * ????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/replyorder/submit" })
	public @ResponseBody
	Result replyOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call replyOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().replyOrder(json);
		return result;
	}
	
	/**
	 * ????????
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/web/replyorder/submit" })
	public @ResponseBody
	Result webReplyOrder(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call replyOrder method ");
		}
		String workOrderID = data.get("workOrderID") == null ? "" : (String)data.get("workOrderID");
		String builderId = data.get("builderId") == null ? "" : (String)data.get("builderId");
		String executeTime = data.get("executeTime") == null ? "" : (String)data.get("executeTime");
		String hgu_sn = data.get("hgu_sn") == null ? "" : (String)data.get("hgu_sn");
		String overTimeReasonDesc = data.get("overTimeReasonDesc") == null ? "" : (String)data.get("overTimeReasonDesc");
		String overTimeReasonId = data.get("overTimeReasonId") == null ? "" : (String)data.get("overTimeReasonId");
		String stb_mac = data.get("stb_mac") == null ? "" : (String)data.get("stb_mac");
		String trackHelpId = data.get("trackHelpId") == null ? "" : (String)data.get("trackHelpId");
		String handleResult = data.get("handleResult") == null ? "" : (String)data.get("handleResult");
		//智能终端
		String intelgtTerminal = data.get("intelgtTerminal") == null ? "" : (String)data.get("intelgtTerminal");

		String handPathName = data.get("handPathName") == null ? "" : (String)data.get("handPathName");
		String handId = data.get("handId") == null ? "" : (String)data.get("handId");
		String accNbr = data.get("accNbr") == null ? "" : (String)data.get("accNbr");
		String staffArea = data.get("staffArea") == null ? "" : (String)data.get("staffArea");

		JSONObject json = new JSONObject();
		json.put(ReplyOrder.HANDLE_RESULT_NODE, handleResult);
		json.put(ReplyOrder.EXECUTE_TIME_NODE, executeTime);
		json.put(ReplyOrder.BUILDER_ID_NODE, builderId);
		json.put(WorkOrderKt.WORK_ORDER_ID_NODE, workOrderID);
		json.put(ReplyOrder.TRACK_HELP_ID_NODE, trackHelpId);
		json.put(ReplyOrder.OVER_TIME_REASON_ID_NODE, overTimeReasonId);
		json.put(ReplyOrder.OVER_TIME_REASON_DESC_NODE, overTimeReasonDesc);
		json.put("hgu_sn", hgu_sn);
		json.put("stb_mac", stb_mac);
		json.put("intelgtTerminal",intelgtTerminal);

		json.put("handPathName", handPathName);
		json.put("handId", handId);
		json.put("accNbr",accNbr);
		json.put("staffArea",staffArea);

		Result result = this.getWorkOrderService().replyOrder(json);

		System.out.println("the saveIntelligentTerminalInfo :"+intelgtTerminal);
		if(Constants.OptCode.OPT_SUCCESS.equals(result.getResultCode())){
			//保存智能终端信息
			//if(StringUtils.isNotBlank(intelgtTerminal)){
			if(!"".equals(intelgtTerminal)){
				saveIntelligentTerminalInfo(intelgtTerminal,workOrderID,builderId);
			}
		}

		return result;
	}

	/**
	 * 保存智能终端信息
	 * @param qrCode
	 * @param workOrderId
	 * @param staffId
	 */
	private void saveIntelligentTerminalInfo(String qrCode,String workOrderId,String staffId){
		String qrCode2 = "";
		String qrCode1="";
		String qrCode3="";
		String qrCode4="";
		String qrCode5="";

		String qrCode6="";
		String qrCode7="";
		String qrCode8="";
		String qrCode9="";
		String qrCode10="";

		String qrCode11="";
		String qrCode12="";
		String qrCode13="";
		String qrCode14="";
		String qrCode15="";

		String qrCode16="";
		String qrCode17="";
		String qrCode18="";
		String qrCode19="";
		String qrCode20="";

		if(qrCode.split(",").length>1)
		{
			int i = 0;
			qrCode1 = qrCode.split(",")[i];
			if((i+1)<(qrCode.split(",").length))
			{
				qrCode2 = qrCode.split(",")[1];
			}
			if((i+2)<(qrCode.split(",").length))
			{
				qrCode3 = qrCode.split(",")[2];
			}
			if((i+3)<(qrCode.split(",").length))
			{
				qrCode4 = qrCode.split(",")[3];
			}
			if((i+4)<(qrCode.split(",").length))
			{
				qrCode5 = qrCode.split(",")[4];
			}
			if((i+5)<(qrCode.split(",").length))
			{
				qrCode6 = qrCode.split(",")[5];
			}
			if((i+6)<(qrCode.split(",").length))
			{
				qrCode7 = qrCode.split(",")[6];
			}
			if((i+7)<(qrCode.split(",").length))
			{
				qrCode8 = qrCode.split(",")[7];
			}
			if((i+8)<(qrCode.split(",").length))
			{
				qrCode9 = qrCode.split(",")[8];
			}
			if((i+9)<(qrCode.split(",").length))
			{
				qrCode10 = qrCode.split(",")[9];
			}

			if((i+10)<(qrCode.split(",").length))
			{
				qrCode11 = qrCode.split(",")[10];
			}
			if((i+11)<(qrCode.split(",").length))
			{
				qrCode12 = qrCode.split(",")[11];
			}
			if((i+12)<(qrCode.split(",").length))
			{
				qrCode13 = qrCode.split(",")[12];
			}
			if((i+13)<(qrCode.split(",").length))
			{
				qrCode14 = qrCode.split(",")[13];
			}
			if((i+14)<(qrCode.split(",").length))
			{
				qrCode15 = qrCode.split(",")[14];
			}

			if((i+15)<(qrCode.split(",").length))
			{
				qrCode16 = qrCode.split(",")[15];
			}
			if((i+16)<(qrCode.split(",").length))
			{
				qrCode17 = qrCode.split(",")[16];
			}
			if((i+17)<(qrCode.split(",").length))
			{
				qrCode18 = qrCode.split(",")[17];
			}
			if((i+18)<(qrCode.split(",").length))
			{
				qrCode19 = qrCode.split(",")[18];
			}
			if((i+19)<(qrCode.split(",").length))
			{
				qrCode20 = qrCode.split(",")[19];
			}

		}
		else
		{
			qrCode1 = qrCode.split(",")[0];
		}
		//先删后增
		String deleteSql = "delete from intelligent_terminal_sn_inf where work_order_id = ?";
		Map deleteparamMap = new HashMap();
		logger.info("保存智能终端信息，workOrderId:" + workOrderId + ",staffId:" + staffId + ",qrCode:" + qrCode);
		deleteparamMap.put("workOrderId",workOrderId);
		Result deleteResult = getCommonService().commonInsertObjectBySql(deleteSql, deleteparamMap);

		if(Constants.OptCode.OPT_SUCCESS.equals(deleteResult.getResultCode())){
			String sql = "insert into intelligent_terminal_sn_inf(work_order_id,sn,sn2,sn3,sn4,sn5,sn6,sn7,sn8,sn9,sn10,sn11,sn12,sn13,sn14,sn15,sn16,sn17,sn18,sn19,sn20,staff_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			Map paramMap = new LinkedHashMap();
			paramMap.put("workOrderId",new BigDecimal(workOrderId));
			paramMap.put("qrCode1",qrCode1);
			paramMap.put("qrCode2",qrCode2);
			paramMap.put("qrCode3",qrCode3);
			paramMap.put("qrCode4",qrCode4);
			paramMap.put("qrCode5",qrCode5);

			paramMap.put("qrCode6",qrCode6);
			paramMap.put("qrCode7",qrCode7);
			paramMap.put("qrCode8",qrCode8);
			paramMap.put("qrCode9",qrCode9);
			paramMap.put("qrCode10",qrCode10);

			paramMap.put("qrCode11",qrCode11);
			paramMap.put("qrCode12",qrCode12);
			paramMap.put("qrCode13",qrCode13);
			paramMap.put("qrCode14",qrCode14);
			paramMap.put("qrCode15",qrCode15);

			paramMap.put("qrCode16",qrCode16);
			paramMap.put("qrCode17",qrCode17);
			paramMap.put("qrCode18",qrCode18);
			paramMap.put("qrCode19",qrCode19);
			paramMap.put("qrCode20",qrCode20);
			paramMap.put("staffId",new BigDecimal(staffId));
			Result result = getCommonService().commonInsertObjectBySql(sql, paramMap);
		}

		
	}



	/**
	 * ????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/delayorder/submit" })
	public @ResponseBody
	Result delayOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call delayOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().delayOrder(json);
		return result;
	}
	
	/**
	 * 锟斤拷装原锟斤拷
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/delayorder/reason/query" })
	public @ResponseBody
	Result delayOrderReason(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call delayOrderReason method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().delayOrderReason(json);
		return result;
	}
	
	/**
	 * ?????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/faultorder/load" })
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
	 * ???????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/appointorder/submit" })
	public @ResponseBody
	Result appointOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call appointOrder method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().appointOrder(json);
		return result;
	}
	
	/**
	 * ???????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/appointorder/web/submit" })
	public @ResponseBody
	Result webToAppointOrder(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call appointOrder method ");
		}
		String workOrderId = data.get("WorkOrderID")== null ? "" :(String) data.get("WorkOrderID");
		String assignReason = data.get("AssignReason")== null ? "" :(String) data.get("AssignReason");
		String comment = data.get("Comment")== null ? "" :(String) data.get("Comment");
		String desc = data.get("Desc")== null ? "" :(String) data.get("Desc");
		String handleStaffId = data.get("HandleStaffId")== null ? "" :(String) data.get("HandleStaffId");
		String isSendNotice = data.get("IsSendNotice")== null ? "" :(String) data.get("IsSendNotice");
		String trackStaffId = data.get("TrackStaffId")== null ? "" :(String) data.get("TrackStaffId");
		String orderClass = data.get("OrderClass")== null ? "" :(String) data.get("OrderClass");
		String appointBeginDate = data.get("AppointBeginDate")== null ? "" :(String) data.get("AppointBeginDate");
		String appointEndDate = data.get("AppointEndDate")== null ? "" :(String) data.get("AppointEndDate");
		String timeSeqId = data.get("TimeSeqId")== null ? "" :(String) data.get("TimeSeqId");

		JSONObject json = new JSONObject();
		json.put("WorkOrderID", workOrderId);
		json.put("AssignReason", assignReason);
		json.put("Comment", comment);
		json.put("Desc", desc);
		json.put("HandleStaffId", handleStaffId);
		json.put("IsSendNotice", isSendNotice);
		json.put("TrackStaffId", trackStaffId);
		json.put("OrderClass", orderClass);
		json.put("AppointBeginDate", appointBeginDate);
		json.put("AppointEndDate", appointEndDate);
		json.put("TimeSeqId", timeSeqId);
		Result result = this.getWorkOrderService().appointOrder(json);
		return result;
	}
	
	
	/**
     * ???????б?
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/org/query"})      
    public @ResponseBody Result getOrgList(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call getOrgList method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());

        Long orgId = json.optLong(JobInfo.ORG_ID_NODE);
        Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
        Result result = this.workOrderService.selOrgList(areaId, orgId);
        return result;
    }
    
    /**
     * ????λ?б?
     * @param requestEntity
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/job/query"})
    public @ResponseBody Result getJobList(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call getJobList method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());

        Long orgId = json.optLong(JobInfo.ORG_ID_NODE);
        Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
        Result result = this.workOrderService.selJobList(areaId,orgId);
        return result;
    }
    /**
     * ???????б?
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/staff/query"})
    public @ResponseBody Result getStaffList(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call getStaffList method ???????б?");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
       
        int queryType = json.optInt(JobInfo.STAFF_QUERY_TYPE);
        Result result = new Result();
        if (queryType == 0)
        {
        	Long orgId = json.optLong(JobInfo.ORG_ID_NODE);
            result = this.workOrderService.selStaffList(orgId);
         } else {
        	 Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
             Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
            result =  this.workOrderService.selStaffList(areaId,jobId);
        }
        return result;
    }
    /**
     * ???????б?
     * @param request
     * @param response
     * @return
     */
    /*@RequestMapping(value = {"/client/xj/staff/query"})
    public @ResponseBody Result getStaffList(@RequestBody RequestEntity requestEntity, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        if(logger.isDebugEnabled()) {
        	logger.debug(" Call getStaffList method ");
        }
        JSONObject json = JSONObject.fromObject(requestEntity.getParams());
        Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
        Long areaId = json.optLong(JobInfo.AREA_ID_NODE);
        Result result = this.workOrderService.selStaffList(areaId,jobId);
        return result;
    }*/

    
    /**
	 * ???????
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/signorder/submit" })
	public @ResponseBody
	Result signOrder(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call signOrder method ");
		}
		System.out.println("Call WorkFormKtController signOrder method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("WorkFormKtController signOrder json"+json.toString());
		Result result = this.getWorkOrderService().signOrder(json);
		return result;
	}
	
	/**
	 * ???????
	 * JSweb????????
	 * @param request
	 * @param response`
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/signorder/submit/position" })
	public @ResponseBody
	Result signOrderJS(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call signOrder method ");
		}
		System.out.println("Call WorkFormKtController signOrder method");
		//JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		//System.out.println("WorkFormKtController signOrder json"+json.toString());
		JSONObject json = new JSONObject();
		
		json.put("checkCode",(String)data.get("checkCode"));
		json.put("latitude",(String)data.get("latitude"));
		json.put("longitude",(String)data.get("longitude"));
		json.put("orgId",(String)data.get("orgId"));
		json.put("orgName",(String)data.get("orgName"));
		json.put("staffId",(String)data.get("staffId"));
		json.put("staffName",(String)data.get("staffName"));
	//	json.put("username",(String)data.get("username"));
		json.put("workOrderId",(String)data.get("workOrderId"));
		Result result = this.getWorkOrderService().signOrder(json);
		return result;
	}
	
	/**
	 *???????????????
	 * 
	 * @param request
	 * @param response`
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/insert/real/position" })
	public @ResponseBody
	Result insertMobileStaffPosition(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call signOrder method ");
		}
		System.out.println("Call WorkFormKtController signOrder method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("WorkFormKtController signOrder json"+json.toString());
//		JSONObject json = new JSONObject();
//		
//	
//		json.put("smx",(String)data.get("smx"));
//		json.put("smy",(String)data.get("smy"));
//		json.put("staffId",(String)data.get("staffId"));
	
		Result result = this.getWorkOrderService().insertMobileStaffPosition(json);
		return result;
	}
	

	/**
	 * ?????????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/makedata/query" })
	public @ResponseBody
	Result queryMakingDataInfo(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryMakingDataInfo method ");
		}
		System.out.println("Call queryMakingDataInfo method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("queryMakingDataInfo json"+json.toString());
		String wkOrderId = json.optString("wkOrder_id");
		Result result = this.getMobileCommonService().getMakingDataByWkOrder(wkOrderId);
		return result;
	}
	
	
	/**
	 * web???????????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/web/kt/makedata/query" })
	public @ResponseBody
	Result webQueryMakingDataInfo(@RequestBody Map<String,Object> data,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryMakingDataInfo method ");
		}
		System.out.println("Call queryMakingDataInfo method");
		
		
		String wkOrderId =data.get("workOrderId") ==  null ? "" : (String)data.get("workOrderId");
		Result result = this.getMobileCommonService().getMakingDataByWkOrder(wkOrderId);
		return result;
	}
   
	/**
	 * ???????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/addmakedata/submit" })
	public @ResponseBody
	Result makeData(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call makeData method ");
		}
		System.out.println("Call makeData method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("makeData json"+json.toString());
		String wkOrderId = json.optString("wkOrder_id");
		String hguSn = json.optString("hgu_sn");
		String stbMac = json.optString("stb_mac");
		String eqpId = json.optString("eqp_id");
		String portId = json.optString("port_id");
		String ponId = json.optString("pon_id");//????PON??ID
		String staffId = json.optString("staff_id");
		String staffName = json.optString("staff_name");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("wk_order_id", wkOrderId);
		param.put("hgu_sn", hguSn);
		param.put("stb_mac", stbMac);
		param.put("eqp_id", eqpId);
		param.put("port_id", portId);
		param.put("pon_id", ponId);
		param.put("staff_id", staffId);
		param.put("staff_name", staffName);
		Result result = this.getMobileCommonService().addMakeDataProgress(param);
		return result;
	}
	
	/**
	 * web????????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/web/kt/addmakedata/submit" })
	public @ResponseBody
	Result webMakeData(@RequestBody Map<String,Object> data) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call makeData method ");
		}
		System.out.println("Call makeData method");
		
		Result result = this.getMobileCommonService().addMakeDataProgress(data);
		return result;
	}
	
	
	/**
	 * web????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/web/kt/terminal/submit" })
	public @ResponseBody
	Result webAddTerminal(@RequestBody Map<String,Object> data) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call makeData method ");
		}
		
		Result result = this.getMobileCommonService().commitTerminalInfo(data);

//		String mac = (String) data.get("stb_mac");
//		String orgId = (String) data.get("orgId");
//		String areano = (String) data.get("areano");
//		String account = (String) data.get("account");
//		String staffId = (String) data.get("staff_id");
		//调用iptv绑定接口，进行绑定
//		boolean bindResult = bindIptv(mac,areano,account);
//		logger.info("account:" + account + ",mac:" + mac + ",iptv绑定结果：" + bindResult);
		return result;
	}
	
	
	/**
	 * ????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/terminal/submit" })
	public @ResponseBody
	Result addTerminal(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call makeData method ");
		}
		System.out.println("Call makeData method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("makeData json"+json.toString());
		String wkOrderId = json.optString("wkOrder_id");
		String hguSn = json.optString("hgu_sn");
		String stbMac = json.optString("stb_mac");
		String staffId = json.optString("staff_id");
		String staffName = json.optString("staff_name");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("wk_order_id", wkOrderId);
		param.put("hgu_sn", hguSn);
		param.put("stb_mac", stbMac);
		param.put("staff_id", staffId);
		param.put("staff_name", staffName);
		Result result = this.getMobileCommonService().commitTerminalInfo(param);
		return result;
	}

	/**
	 * 绑定Iptv机顶盒和账号
	 * @param mac
	 * @param cityId
	 * @param servaccount
	 */
	private boolean bindIptv(String mac,String cityId,String servaccount){
		boolean bindResult = false;
		IptvInterface iptvInterface  = new IptvInterface();
		Map inMap = new HashMap();
		inMap.put("mac", mac);
		inMap.put("cityId", cityId);
		inMap.put("servaccount", servaccount);
		try {
			Map bindMap = iptvInterface.bandIPTVSTB(inMap);
			String resultCode = (String) bindMap.get("resultCode");
			if("000".equals(resultCode)) {
				bindResult = true;
			}

			if(bindMap.size()>0){
				Gson gson = new Gson();
				String msg = gson.toJson(bindMap);
				logger.info("iptv绑定结果：" + msg);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bindResult;

	}


	/**
	 * ??????????OLT???
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/makedata/query/olt" })
	public @ResponseBody
	Result queryAppOLTInfo(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryAppOLTInfo method ");
		}
		System.out.println("Call queryAppOLTInfo method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("queryAppOLTInfo json"+json.toString());
		String oltName = json.optString("olt_name");
		String wk_order_id = json.optString("wk_order_id");
		Result result = this.getMobileCommonService().getOLTInfoByName(oltName,wk_order_id);
		return result;
	}
	
	/**
	 * ??????????PON?????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/makedata/query/pon" })
	public @ResponseBody
	Result queryAppOLTPonInfo(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryAppOLTPonInfo method ");
		}
		System.out.println("Call queryAppOLTPonInfo method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("queryAppOLTPonInfo json"+json.toString());
		String oltId = json.optString("olt_id");
		Result result = this.getMobileCommonService().getOLTPonInfo(oltId);
		return result;
	}
	
	/**
	 * ?????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/kt/terminal/query" })
	public @ResponseBody
	Result queryTerminalInfo(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryTerminalInfo method ");
		}
		System.out.println("Call queryTerminalInfo method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		System.out.println("queryTerminalInfo json"+json.toString());
		String orderId = json.optString("WorkOrderID");
		Result result = this.getMobileCommonService().queryTerminalInfo(orderId);
		return result;
	}
	
	/**
	 * ?????????
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = { "/client/xj/web/kt/terminal/query" })
	public @ResponseBody
	Result webQueryTerminalInfo(@RequestBody Map<String,Object> data) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call queryTerminalInfo method ");
		}
		System.out.println("Call queryTerminalInfo method");
		JSONObject json = new JSONObject();
		System.out.println("queryTerminalInfo json"+json.toString());
		String orderId = (String)data.get("workOrderId");
		Result result = this.getMobileCommonService().queryTerminalInfo(orderId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/query" })
	public @ResponseBody
	Result reportCommonQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		String reportType = json.optString("Flg");
		Result result = this.getMobileCommonService()
				.reportCommonQuery(beginDate,staffId,reportType);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/querytest" })
	public @ResponseBody
	Result reportCommonTestQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonTestQuery(beginDate,staffId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/queryCompany" })
	public @ResponseBody
	Result reportCommonCompanyQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String areaName = json.optString("areaName");
		Result result = this.getMobileCommonService()
				.reportCommonCompanyQuery(beginDate,areaName);
		return result;
	}
	
	
	@RequestMapping(value = { "/client/xj/kt/report/queryCompanyStaff" })
	public @ResponseBody
	Result reportCommonCompanyStaffQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String areaName = json.optString("areaName");
		String company = json.optString("company");
		Result result = this.getMobileCommonService()
				.reportCommonCompanyStaffQuery(beginDate,areaName,company);
		return result;
	}
	
	
	@RequestMapping(value = { "/client/xj/kt/report/queryStaffDay" })
	public @ResponseBody
	Result reportCommonStaffDayQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonStaffDayQuery(beginDate,staffId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/orderAlarm" })
	public @ResponseBody
	Result reportCommonOrderAlarmQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
//		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonOrderAlarmQuery(staffId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/orderDetail" })
	public @ResponseBody
	Result reportCommonOrderDetailQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String accNbr = json.optString("accNbr");
		String areaNbr = json.optString("areaNbr");
		Result result = this.getMobileCommonService()
				.reportCommonOrderDetailQuery(accNbr,areaNbr);
		return result;
	}
	///MOBILE/api/client/xj/kt/report/companyLogin
	
	@RequestMapping(value = { "/client/xj/kt/report/companyLogin" })
	public @ResponseBody
	Result reportCommonCompanyLoginQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonCompanyLoginQuery(beginDate,staffId);
		return result;
	}
	
	
	@RequestMapping(value = { "/client/xj/kt/report/companyDwLogin" })
	public @ResponseBody
	Result reportCommonCompanyDwLoginQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonCompanyDwLoginQuery(beginDate,staffId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/companyDetail" })
	public @ResponseBody
	Result reportCommonCompanyDetailLoginQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String areaName = json.optString("areaName");
		Result result = this.getMobileCommonService()
				.reportCommonCompanyDetailLoginQuery(beginDate,areaName);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/staffLogin" })
	public @ResponseBody
	Result reportCommonStaffLoginQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonStaffLoginQuery(beginDate,staffId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/kt/report/managerLogin" })
	public @ResponseBody
	Result reportCommonManagerLoginQuery(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call reportCommonQuery method ");
		}
//		System.out.println("Call reportCommonQuery method");
		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//		System.out.println("reportCommonQuery json" + json.toString());
		String beginDate = json.optString("BeginDate");
		String staffId = json.optString("StaffId");
		Result result = this.getMobileCommonService()
				.reportCommonManagerLoginQuery(beginDate,staffId);
		return result;
	}
	
	/**
	 * 改单
	 * @param data
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/kt/web/modifyorder/submit" })
	public @ResponseBody
	Result modifyOrder(@RequestBody Map<String,Object> data) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call modifyOrder method ");
		}
		String staffId = data.get("staffId") == null ? "" : (String)data.get("staffId");
		String orderId = data.get("orderId") == null ? "" : (String)data.get("orderId");
		String oprateName = data.get("oprateName") == null ? "" : (String)data.get("oprateName");
		String serviceNum = data.get("serviceNum") == null ? "" : (String)data.get("serviceNum");
		String contactPhone = data.get("contactPhone") == null ? "" : (String)data.get("contactPhone");
		String addressInfo = data.get("addressInfo") == null ? "" : (String)data.get("addressInfo");
		String remark = data.get("remark") == null ? "" : (String)data.get("remark");
		
		Result result = null;
		//改单前校验是否之前改过，如果改过则不允许继续改,false校验失败
		boolean validateResult = this.getMobileCommonService().validateModifyOrder(orderId);
		if(!validateResult){
			result = new Result();
			result.setResultCode(0);
			result.setResultMsg("该单已经修改过，不能再次修改。");
			return result;
		}
		
		//查询工单相关信息
		logger.info("<<<<<>>>>> orderId:" + orderId);
		Result orderResult = this.getMobileCommonService().getModifyOrderInfoByOrderId(orderId,staffId);
		
		Map<Object, Object> resultData = orderResult.getResultData();
		JSONObject json = new JSONObject();
		json.put("produceOrderId", resultData.get("produceOrderId"));
		json.put("eparchyCode", resultData.get("eparchyCode"));
		json.put("countyCode", resultData.get("countyCode"));
		json.put("countyName", resultData.get("countyName"));
		json.put("operateId", resultData.get("userName"));
		json.put("oprateName", oprateName);
		json.put("serviceNum", serviceNum);
		json.put("contactPhone", contactPhone);
		json.put("serviceType", resultData.get("serviceType"));
		json.put("addressInfo", addressInfo);
		json.put("remark", remark);
		JSONObject root = new JSONObject();
		root.put("root", json);
		//调用改单通知接口
		result = this.getWorkOrderService().modifyOrder(root);
		String orderCode = (String) resultData.get("orderCode");
		
		//插入接口日志
		String msg = result.getResultMsg();
		logger.info("<<<改单接口返回msg>>>:" + msg);
		this.getMobileCommonService().insertIntfLogInfo("改单通知", orderCode, msg);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/web/kt/qrcodeInstall/query" })
	public @ResponseBody
	Result webQueryQrcodeInstallDataInfo(@RequestBody Map<String,Object> data,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call webQueryQrcodeInstallDataInfo method ");
		}
		String qrCode =data.get("qrCode") ==  null ? "" : (String)data.get("qrCode");
		String workOrderId = data.get("workOrderId") ==  null ? "" : (String)data.get("workOrderId");
		logger.info("qrCode:" + qrCode);
		Result result = this.getMobileCommonService().getInstallDataByQrCode(qrCode,workOrderId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/web/kt/qrcodeInstall/submit" })
	public @ResponseBody
	Result qrCodeInstallSubmit(@RequestBody Map<String,Object> data) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call qrCodeInstallSubmit method ");
		}
		Result result = null;
		String orderId = (String) data.get("orderId");
		//boolean validateSmInstallOrder = this.getMobileCommonService().validateSmInstallOrder(orderId);
		/*if(!validateSmInstallOrder){
			result = new Result();
			result.setResultCode(0);
			result.setResultMsg("非已清查单，不能进行扫码装机。");
			return result;
		}*/
		String sqlStr = "select count(1) as flagCount from  om_order t where t.id='" + orderId + "' and t.service_id in (600209,600207,600270,600271)";
		Map paramMap = null;
		String wherePatternStr = "";
		Result validateResult = this.getCommonService().commonQueryObjectBySql(sqlStr, paramMap, wherePatternStr);
		Map<String,Object> resultMap = (Map<String, Object>) validateResult.getResultData().get("data_info");
		logger.info("resultMap:" + resultMap);
		int flagCount = ((BigDecimal) resultMap.get("flagCount")).intValue();
		boolean validateSmInstallOrder = flagCount>0?true:false;
		if(!validateSmInstallOrder){
			result = new Result();
			result.setResultCode(0);
			result.setResultMsg("非FTTH装移机单，不能进行扫码装机！");
			return result;
		}
		
		logger.info("扫码装机数据提交：" + data.toString());
		result = this.getMobileCommonService().addMakeDataSMProgress(data);
		return result;
	}
	
	/**
	 * 扫码装机进度查询
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/xj/web/kt/smMakedata/query" })
	public @ResponseBody
	Result webQuerySmMakingDataInfo(@RequestBody Map<String,Object> data,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call webQuerySmMakingDataInfo method ");
		}
		logger.info("Call webQuerySmMakingDataInfo method");
		
		
		String wkOrderId =data.get("workOrderId") ==  null ? "" : (String)data.get("workOrderId");
		Result result = this.getMobileCommonService().getSmMakingDataByWkOrder(wkOrderId);
		return result;
	}
	
	@RequestMapping(value = { "/client/xj/web/kt/qrcodeInstall/pxCodeBind" })
	public @ResponseBody
	Result pxCodeBind(@RequestBody Map<String,Object> data,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call pxCodeBind method ");
		}
		logger.info("Call pxCodeBind method");
		Result result = null;
		String portId = data.get("portId") ==  null ? "" : (String)data.get("portId");
		String pxCode = data.get("pxCode") ==  null ? "" : (String)data.get("pxCode");
		String staffId = data.get("staffId") ==  null ? "" : (String)data.get("staffId");
		
		//校验皮线二维码是否存在
		boolean valideResult = this.getMobileCommonService().validatePxCode(pxCode);
		if(!valideResult){
			result = Result.buildFailure();
			result.setResultMsg("皮线二维码重复！");
			return result;
		}
		
		result = this.getMobileCommonService().pxCodeBind(portId,pxCode,staffId);
		return result;
	}
}
