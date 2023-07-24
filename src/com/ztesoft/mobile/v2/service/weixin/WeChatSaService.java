package com.ztesoft.mobile.v2.service.weixin;

import com.ztesoft.mobile.v2.core.Result;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 工单处理模块
 * 
 * @author linping
 * 
 */
public interface WeChatSaService {

	public static final String WS_NAMESPACE = "";

	public static final String WS_METHOD_OPERATION_NAME = "newWorkSheet";

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
	
	/** 根据二维码查询用户信息 */
	public JSONObject queryUserHouseInfoByCode(JSONObject json);
	public Result newFaultWorkSheet(Map<String,Object> data);
	public Result queryInstallationReport(JSONObject json);
	public Result checkUserName(JSONObject json);
	public Result getPhoneNum(JSONObject json);
}
