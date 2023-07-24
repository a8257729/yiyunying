package com.ztesoft.mobile.v2.controller.workform.xinjiang.zy;


import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.RequestEntity;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.workform.xinjiang.zy.WorkOrderZyService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author Linping
 *
 */
@Controller("xjWorkOrderZyController")
public class WorkOrderZyController extends WebConfigController {
	
	private static final Logger logger = Logger.getLogger(WorkOrderZyController.class);

    private WorkOrderZyService workOrderService;

    private WorkOrderZyService getWorkOrderService() {
        return workOrderService;
    }
    
    @Autowired(required = false)
    public void setWorkOrderService(WorkOrderZyService workOrderService) {
        this.workOrderService = workOrderService;
    }
    
    private static String WORK_ORDER_LIST_URL = "";
    private static String WORK_ORDER_DETIAL_URL = "";
    private static String WORK_ORDER_ACCEPT_URL = "";
    private static String WORK_ORDER_DEPART_URL = "";
    
 
    /**
     * 资源预判--主界面查询
     * 
     * */
    @RequestMapping(value = {"/client/xj/zy/resources/private/query"})
    public @ResponseBody Result queryResourcesList(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Throwable {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	System.out.println("WorkOrderZyController.queryResourcesList.json -->"+json);
    	
        Result result = getWorkOrderService().queryResourcesList(json);
        return result;
    }
    
    
	/**
	 * 设备管理 -- 查询条件初始化查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/zy/device/query/param/init" })
	public @ResponseBody Result queryDeviceManagementInitParams(@RequestBody RequestEntity requestEntity,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call devicemanegement params init method ");
		}

		JSONObject json = JSONObject.fromObject(requestEntity.getParams());
		Result result = this.getWorkOrderService().queryDeviceManagementInitParams(json);
		return result;
	}
	
	
	//----------------------------------------------------------------
	/**
	 * 临时  ---设备管理 -- 查询条件初始化查询
	 * @param map
	 * @return
	 */
	@RequestMapping(value = { "/client/xj/zy/device/query/param/init1" })
	public @ResponseBody Result queryDeviceManagementInitParams(@RequestBody Map<String,Object> map) {
		if (logger.isDebugEnabled()) {
			logger.debug(" Call devicemanegement params init method ");
		}
		JSONObject  json = new JSONObject();
		String staffId = (String)map.get("staffId");
		json.put("staffId",staffId!=null?staffId:"");
		Result result = this.getWorkOrderService().queryDeviceManagementInitParams(json);
		return result;
	}
	
	//----------------------------------------------------------------
	
	
	/**
	 * 设备管理 -- 设备列表查询
	 * 
	 * @param request 
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/client/xj/zy/device/query/list"})
    public @ResponseBody Result queryDeviceListByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	String org_id = json.optString("org_id");
    	String res_type_id = json.optString("res_type_id");
    	String eqp_name = json.optString("eqp_name");
    	Integer pageIndex = json.optInt("pageIndex");
    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().queryDeviceListByPage(org_id, res_type_id,eqp_name, pageSize, pageIndex);
        return result;
    }
    
   
    /***********************************************
     * 临时设备列表查询
     * @param map
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value = {"/client/xj/zy/device/query/list1"})
    public @ResponseBody Result queryDeviceListByPage(@RequestBody Map<String,Object> map) throws Exception {
    	
    	
    	String org_id = (String)map.get("org_id");
    	String res_type_id = (String)map.get("res_type_id");
    	String eqp_name = (String)map.get("eqp_name");
    	Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
    	Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
        Result result = getWorkOrderService().queryDeviceListByPage(org_id, res_type_id,eqp_name, pageSize, pageIndex);
        return result;
    }
    
    
    
   
	/**
	 * 设备管理 -- 设备端口列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/client/xj/zy/device/query/port/list"})
    public @ResponseBody Result queryDevicePortListByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	String eqp_id = json.optString("eqp_id");
    	String port_name = json.optString("port_name");
    	Integer pageIndex = json.optInt("pageIndex");
    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().queryDevicePortListByPage(eqp_id,port_name,pageSize, pageIndex);
        return result;
    }
    
 
    
    
    /***************************************************
     * 临时新增设备端口列表查询
     * @param map
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value = {"/client/xj/zy/device/query/port/list1"})
    public @ResponseBody Result queryDevicePortListByPage(@RequestBody Map<String,Object> map) throws Exception {
    	
    	String eqp_id = (String)map.get("eqp_id");
    	String port_name = (String)map.get("port_name");
    	Integer pageIndex = Integer.parseInt((String)map.get("pageIndex"));
    	Integer pageSize = Integer.parseInt((String)map.get("pageSize"));
    	Result result = getWorkOrderService().queryDevicePortListByPage(eqp_id,port_name,pageSize, pageIndex);
        return result;
    }
    
    
    
    
    
    /**
     * 设备管理 -- 设备端口状态配置菜单查询
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/query/operation/list"})
    public @ResponseBody Result queryDeviceOperationList(@RequestBody RequestEntity requestEntity,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	
    	Result result = getWorkOrderService().queryDeviceOperationList(json);
    	return result;
    }
    /**
     * 设备管理 -- 设备端口状态配置操作 
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/query/operation/execute"})
    public @ResponseBody Result executeDeviceOperation(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	System.out.println("WorkOrderZyController.executeDeviceOperation.json -->"+json);
    	Result result = getWorkOrderService().executeDeviceOperation(json);
    	return result;
    }
    
    
	/**
	 * 设备管理 -- 迁入号码列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value = {"/client/xj/zy/device/query/rollin/tele/list"})
    public @ResponseBody Result queryTeleRollInListByPage(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	String org_id = json.optString("org_id");
    	String tele_nbr = json.optString("tele_nbr");
    	Integer pageIndex = json.optInt("pageIndex");
    	Integer pageSize = json.optInt("pageSize");
        Result result = getWorkOrderService().queryTeleRollInListByPage(org_id,tele_nbr,pageSize, pageIndex);
        return result;
    }
    
    /**
     * 设备管理 -- 设备端口状态配置操作 
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/query/operation/rollin/execute"})
    public @ResponseBody Result executeDeviceRollInOperation(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	Result result = getWorkOrderService().executeDeviceRollInOperation(json);
    	return result;
    }
    
    
    /**
     * 
     * 帐号查询-- 查询宽带帐号状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/query/broadband"})
    public @ResponseBody Result queryResourcesBroadbandOperation(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	System.out.println("WorkOrderZyController.queryResourcesBroadbandOperation.json -->"+json);
    	Result result = getWorkOrderService().queryResourcesBroadbandOperation(json);
    	return result;
    }
    
  //--------------------------
    /**
     * WEB端
     * 帐号查询-- 查询宽带帐号状态
     * @param map
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/query/broadband1"})
    public @ResponseBody Result queryResourcesBroadbandOperation(@RequestBody  Map<String,Object> map) throws Exception {
    	//account
    	String account = (String)map.get("account");
    	JSONObject json = new JSONObject();
    	json.put("account", account);
    	System.out.println("WorkOrderZyController.queryResourcesBroadbandOperation.json -->"+json);
    	Result result = getWorkOrderService().queryResourcesBroadbandOperation(json);
    	return result;
    }

    //通过业务号码查询信息
    @RequestMapping(value = {"/client/xj/zy/device/query/broadband2"})
    public @ResponseBody Result queryChangeMachine(@RequestBody  Map<String,Object> map) throws Exception {
    	//account
    	String account = (String)map.get("account");
    	JSONObject json = new JSONObject();
    	json.put("account", account);
    	System.out.println("WorkOrderZyController.queryChangeMachine.json -->"+json);
    	Result result = getWorkOrderService().queryChangeMachine(json);
    	return result;
    }

    //通过业务号码查询obd名
	@RequestMapping(value = {"/client/xj/zy/device/query/obd"})
	@ResponseBody
	public Object querySelObd(@RequestBody Map<String, Object> data, ModelMap model,
										HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("调用方法：querySelObd");
		String account = data.get("account").toString();
		Map<String, String> result = getWorkOrderService().querySelObd(account);
		return result;
	}

    /**
     * 
     * 帐号查询-- 查询宽带帐号状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/add/loginlog"})
    public @ResponseBody Result addLoginlogOperation(@RequestBody RequestEntity requestEntity,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject json = JSONObject.fromObject(requestEntity.getParams());
    	System.out.println("WorkOrderZyController.queryResourcesBroadbandOperation.json -->"+json);
    	Result result = getWorkOrderService().addLoginlogOperation(json);
    	return result;
    }
    
    
    
    /**
     * 
     * 帐号查询-- 查询宽带帐号状态
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/client/xj/zy/device/reset/broadband"})
		public @ResponseBody Result resetResourcesBroadbandOperation(@RequestBody Map<String,Object> map,
    	HttpServletRequest request, HttpServletResponse response) throws Exception {
    	JSONObject json = JSONObject.fromObject(map);
    	System.out.println("WorkOrderZyController.resetResourcesBroadbandOperation.json -->"+json);
    	Result result = getWorkOrderService().resetResourcesBroadbandOperation(json);
    	return result;
    }
}
