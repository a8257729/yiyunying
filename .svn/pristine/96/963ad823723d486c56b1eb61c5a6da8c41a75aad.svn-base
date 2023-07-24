package com.ztesoft.mobile.v2.service.workform.xinjiang.sa;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.v2.core.Result;

/**
 * 工单处理模块
 * 
 * @author linping
 * 
 */
public interface WorkOrderSaService {

	public static final String WS_NAMESPACE = "";

	public static final String WS_METHOD_OPERATION_NAME = "pfServicesForEBiz";

	/**
	 * 装移工单
	 */
	public static final String STAFFID = "StaffId";
	public static final String ROWNUM = "RowNum";
	public static final String IDS = "Ids";
	public static final String CUSTTELENO = "CustTeleno";
	public static final String CUSTREGINCODE = "CustreginCode";
	public static final String CUSTDESEQ = "CustDeseq";
	public static final String CUSTPRODID = "CustPortID";	
	public static final String ACTIONTYPE = "ActionType";	
	
	/** 装移工单 -- 查询反馈列表 */
	public Result queryFeedbackInfo(JSONObject json);	
	/** 装移工单 -- 详情 */
	public Result workOrderDetail(JSONObject json);	
}
