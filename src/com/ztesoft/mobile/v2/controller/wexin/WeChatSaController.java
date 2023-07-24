package com.ztesoft.mobile.v2.controller.wexin;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.v2.dao.weixin.WeChatDAOImpl;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztesoft.mobile.v2.core.BaseController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.shanghai.WorkOrder;
import com.ztesoft.mobile.v2.service.weixin.WeChatSaService;
import com.ztesoft.mobile.v2.service.weixin.WeChatSaServiceImpl;
import com.ztesoft.mobile.v2.service.workform.xinjiang.sa.WorkOrderSaService;
import com.ztesoft.mobile.v2.util.XmlDataUtil;
/***
 * 装移工单管控功能
 * 
 ** */
 
@Controller("weChatUserInfoController")
public class WeChatSaController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(WeChatSaController.class);

	//private WeChatSaService weChatService = new WeChatSaServiceImpl();
	
	private WeChatSaService weChatService;

	public WeChatSaService getWorkOrderService() {
		return weChatService;
	}

	@Autowired(required = false)
	public void setWorkOrderService(WeChatSaService weChatService) {
		this.weChatService = weChatService;
	}


	/**
	 * 装机报告
	 */
	@RequestMapping(value = { "/client/wechat/userinfo/report" })
	public @ResponseBody void queryReport(@RequestParam String callBack,HttpServletRequest request,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call queryReport method ");
		}
		try{
			String serviceNum = request.getParameter("serviceNum");
			logger.info(" SERVICE_NUM params :"+serviceNum);
			JSONObject json = new JSONObject();
			json.put("serviceNum",serviceNum);
			Result result = weChatService.queryInstallationReport(json);
			JSONObject jsonResult = JSONObject.fromObject(result);
			response.getWriter().print(callBack+"("+jsonResult.toString()+")");
			response.flushBuffer();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * 微信公众号用户信息查询
	 */
	@RequestMapping(value = { "/client/wechat/userinfo/reportNew"},produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryReportNew(@RequestBody String params,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call queryReportNew method ");
		}
		try{
			JSONObject jsonObject = JSONObject.fromObject(params);
			Result result = weChatService.queryInstallationReport(jsonObject);
			JSONObject jsonResult = JSONObject.fromObject(result);
			String res = jsonResult.toString();
			response.setContentLength(res.getBytes("UTF-8").length);
			return res;
		}catch(Exception e)
		{
			e.printStackTrace();
			return  null;
		}

	}
	/**
	 * 微信公众号用户信息查询
	 */
	@RequestMapping(value = { "/client/wechat/userinfo/query" })    
	public @ResponseBody void queryUserHouseInfoByCode(@RequestParam String callBack,HttpServletRequest request,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call queryUserHouseInfoByCode method ");
		}
		try{
		logger.info(" Call queryUserHouseInfoByCode method ");
		String qrcode = request.getParameter("qrcode");
		JSONObject json = new JSONObject();
		json.put("qrcode", qrcode);
		JSONObject jsonResult = weChatService.queryUserHouseInfoByCode(json);
		response.getWriter().print(callBack+"("+jsonResult.toString()+")");
		response.flushBuffer();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	/**
	 * 微信公众号用户信息查询
	 */
	@RequestMapping(value = { "/client/wechat/userinfo/queryNew"},produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryUserHouseInfoByCodeNew(@RequestBody String params,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call queryUserHouseInfoByCode method ");
		}
		try{
			logger.info(" Call queryUserHouseInfoByCode method ");
			JSONObject jsonObject = JSONObject.fromObject(params);
			JSONObject jsonResult = weChatService.queryUserHouseInfoByCode(jsonObject);
			String res = jsonResult.toString();
			response.setContentLength(res.getBytes("UTF-8").length);
			return res;
		}catch(Exception e)
		{
			e.printStackTrace();
			return  null;
		}

	}



	@RequestMapping(value = {"/client/yhym/username/getPhone"})
	public @ResponseBody void getPhoneNum(@RequestParam String callBack,HttpServletRequest request,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call getPhoneNum method ");
		}
		try{
			logger.info(" Call getPhoneNum method ");
			String qrcode = request.getParameter("qrcode");
			JSONObject json = new JSONObject();
			json.put("qrcode", qrcode);
			Result result = weChatService.getPhoneNum(json);
			JSONObject jsonResult = JSONObject.fromObject(result);
			response.getWriter().print(callBack+"("+jsonResult.toString()+")");
			response.flushBuffer();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//新一户一码查询手机号
	@RequestMapping(value = {"/client/newYhym/username/getPhone"},produces = "application/json;charset=UTF-8")
	public @ResponseBody String getPhoneNumNew(@RequestBody String params,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call NewgetPhoneNum method ");
		}
		try{
			logger.info(" Call NewgetPhoneNum method ");
			JSONObject jsonObject = JSONObject.fromObject(params);
			String qrcode = jsonObject.getString("qrcode");
			JSONObject json = new JSONObject();
			json.put("qrcode", qrcode);
			Result result = weChatService.getPhoneNum(json);

			JSONObject jsonResult = JSONObject.fromObject(result);
			String res = jsonResult.toString();
			response.setContentLength(res.getBytes("UTF-8").length);
			return res;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 微信公众号用户信息查询
	 */
	@RequestMapping(value = { "/client/yhym/username/check" })
	public @ResponseBody void checkUserName(@RequestParam String callBack,HttpServletRequest request,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call checkUserName method ");
		}
		try{
			logger.info(" Call checkUserName method ");
			String qrcode = request.getParameter("qrcode");
			String userName = request.getParameter("userName");
			JSONObject json = new JSONObject();
			json.put("qrcode", qrcode);
			json.put("userName", userName);
			Result result = weChatService.checkUserName(json);
			JSONObject jsonResult = JSONObject.fromObject(result);
			response.getWriter().print(callBack+"("+jsonResult.toString()+")");
			response.flushBuffer();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 微信公众号用户信息查询
	 */
	@RequestMapping(value = { "/client/newYhym/username/check" },produces = "application/json;charset=UTF-8")
	public @ResponseBody String checkUserNameNew(@RequestBody String params,HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.info(" Call NewcheckUserName method ");
		}
		try{
			logger.info(" Call NewcheckUserName method ");
			JSONObject jsonObject = JSONObject.fromObject(params);
			String phoneLastNum = jsonObject.getString("phoneLastNum");
			String qrcode = jsonObject.getString("qrcode");
			JSONObject json = new JSONObject();
			json.put("qrcode", qrcode);
			json.put("userName", phoneLastNum);
			Result result = weChatService.checkUserName(json);
			JSONObject jsonResult = JSONObject.fromObject(result);
			String res = jsonResult.toString();
			response.setContentLength(res.getBytes("UTF-8").length);
			return res;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 *  微信公众号报障
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/client/wechat/fault/report" })
	public void newFaultWorkSheet(@RequestParam String callBack,HttpServletRequest request,HttpServletResponse response) {
		try{
		if (logger.isDebugEnabled()) { 
			logger.debug(" Call WorkOrderDetail method ");
		}
		Map<String,Object> data = new HashMap<String,Object>();
		String areaField = request.getParameter("areaField");
		String service_num = request.getParameter("service_num");
		String cust_name = request.getParameter("cust_name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String faultType = request.getParameter("faultType");
		String diagnoseResult = request.getParameter("result");//诊断结果
		
		Result result = new Result();
		data.put("areaField",areaField);
		data.put("service_num",service_num);
		data.put("cust_name",cust_name);
		data.put("address",address);
		data.put("phone",phone);
		data.put("faultType",faultType);
		data.put("result", diagnoseResult);
		if(null == data)
		{
			result=Result.buildServerError();
		}
		else
		{
			 result = weChatService.newFaultWorkSheet(data);
		}
		JSONObject jsonResult = JSONObject.fromObject(result);
		response.getWriter().print(callBack+"("+jsonResult.toString()+")");
		response.flushBuffer();

		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}

	/**
	 *  微信公众号报障
	 * @param
	 * @return
	 */
	@RequestMapping(value = { "/client/wechat/fault/reportNew" },produces = "application/json;charset=UTF-8")
	public String newFaultWorkSheet2(@RequestParam String params,HttpServletRequest request,HttpServletResponse response) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug(" Call WorkOrderDetail method ");
			}
			JSONObject jsonObject = JSONObject.fromObject(params);

			String areaField = jsonObject.getString("areaField");
			String service_num = jsonObject.getString("service_num");
			String cust_name = jsonObject.getString("cust_name");
			String address = jsonObject.getString("address");
			String phone = jsonObject.getString("phone");
			String faultType = jsonObject.getString("faultType");
			String diagnoseResult = jsonObject.getString("result");//诊断结果
			Map<String, Object> data = new HashMap<String, Object>();
			Result result = new Result();
			data.put("areaField", areaField);
			data.put("service_num", service_num);
			data.put("cust_name", cust_name);
			data.put("address", address);
			data.put("phone", phone);
			data.put("faultType", faultType);
			data.put("result", diagnoseResult);
			if (null == data) {
				result = Result.buildServerError();
			} else {
				result = weChatService.newFaultWorkSheet(data);
			}
			JSONObject jsonResult = JSONObject.fromObject(result);
			String res = jsonResult.toString();
			response.setContentLength(res.getBytes("UTF-8").length);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



//	/**
//	 * 装移工单 -- 详情
//	 */
//	@RequestMapping(value = { "/client/xj/kt/workorder/detail" })    
//	public @ResponseBody
//	Result WorkOrderDetail(@RequestBody
//	RequestEntity requestEntity, ModelMap model, HttpServletRequest request,
//			HttpServletResponse response) {
//		if (logger.isDebugEnabled()) {
//			logger.debug(" Call WorkOrderDetail method ");
//		}
//		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
//	
//		Result result = getWorkOrderService().workOrderDetail(json);
//		return result;
//	}
//	
//	/**
//	 * 工单详情--web端
//	 * @param Map
//	 * @return
//	 */
//	@RequestMapping(value = { "/client/xj/kt/workorder/detail1" })
//	public @ResponseBody
//	Result WorkOrderDetail1(@RequestBody Map<String,Object> data) {
//		if (logger.isDebugEnabled()) { 
//			
//			
//			logger.debug(" Call WorkOrderDetail method ");
//		}
////		System.out.println("the workOrderID is:"+workOderVO.toString());
////		System.out.println("the orderID is:"+orderID.toString());
//		JSONObject js = new JSONObject();
//		js.put("WorkOrderID",(String)data.get("workOrderID"));
//		js.put("OrderID",(String)data.get("orderID"));
//		
//		//JSONObject json = JSONObject.fromObject(workOrderID);
//		// String username = json.optString(StaffInfo.USERNAME_NODE);
//		// Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
//		// Integer pageIndex = json.optInt("pageIndex");
//		// Integer pageSize = json.optInt("pageSize");
//		Result result = getWorkOrderService().workOrderDetail(js);
//		return result;
//	}
	
	
	
}
