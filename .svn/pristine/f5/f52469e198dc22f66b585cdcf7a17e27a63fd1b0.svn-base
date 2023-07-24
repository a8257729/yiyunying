package com.ztesoft.mobile.v2.service.workform.xinjiang.zy;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;

import java.util.Map;

/**
 * 资源管理模块
 * 
 * @author linping
 * 
 */
public interface WorkOrderZyService {

	public static final String WS_NAMESPACE = "";

	public static final String WS_METHOD_OPERATION_NAME = "pfServicesForEBiz";

	public static final String ORDER_TYPE_NODE = "orderType";
	public static final String WORKORDER_ID_NODE = "workOrderId";
	 

	/** 资源预判查询  
	 * @throws Throwable */
	public Result queryResourcesList(JSONObject json) throws Throwable;
	
	
	/** 设备管理 -- 查询条件初始化查询   */
	public Result queryDeviceManagementInitParams(JSONObject json);
	
	/** 设备管理 -- 设备列表查询   */
	public Result queryDeviceListByPage(String org_id,String res_type_id,String eqp_name,Integer pageSize, Integer pageIndex);
	
	/** 设备管理 -- 设备端口列表查询   */
	public Result queryDevicePortListByPage(String eqp_id,String port_name,Integer pageSize, Integer pageIndex);
	
	/** 设备管理 -- 设备端口状态配置菜单查询   
	 * @throws DataAccessException */
	public Result queryDeviceOperationList(JSONObject json) throws DataAccessException;
	
	/** 设备管理 -- 设备端口状态配置操作 */
	public Result executeDeviceOperation(JSONObject json);
	
	
	/** 设备管理 -- 迁入号码列表查询   */
	public Result queryTeleRollInListByPage(String org_id,String tele_nbr,Integer pageSize, Integer pageIndex);
	
	/** 设备管理 -- 设备端口迁入操作 */
	public Result executeDeviceRollInOperation(JSONObject json);
	
	/** 帐号查询 -- 查询宽带帐号状态 */
	public Result queryResourcesBroadbandOperation(JSONObject json);

	/** 业务号码查询 -- 查询光猫换机 老光猫详情 */
	public Result queryChangeMachine(JSONObject json);

	/** 业务号码查询 -- 查询obd名  */
	public Map<String,String> querySelObd(String account);

	/** 添加登录日志*/
	public Result addLoginlogOperation(JSONObject json);
	
	
	/** 帐号复位 --  */
	public Result resetResourcesBroadbandOperation(JSONObject json);
}
