package com.ztesoft.mobile.v2.service.workform.xinjiang.kt;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.v2.core.Result;

public interface WorkOrderKtService {
	
	public static final String WS_NAMESPACE = "";
	public static final String WS_METHOD_OPERATION_NAME = "pfServicesForEBiz";
	
	/**
	 * 开通工单监控
	 */
	public static final String WORKORDERID = "WorkOrderID";
	public static final String USENAME = "UseName";
	public static final String ORDERID = "OrderID";
	
	public static final String ORDERCLASS = "OrderClass";
	/** 开通工单监控查询 */
	public Result workOrderContorlQuery(JSONObject json);

	/** 开通工单监控详情 */
	public Result workOrderContorlDtlie(JSONObject json);
		
	/**开通工单监控 -- 退单原因原因查询 */
	public Result cancelOrderktJkReason(JSONObject json);
	
	/**开通工单监控 -- 退单 */
	public Result cancelOrderktJk(JSONObject json);
	
	public static final String ORDER_TYPE_NODE = "orderType";
	public static final String WORKORDER_ID_NODE = "workOrderId";
	public static final String WORKORDER_CODE_NODE = "workOrderCode";
	public static final String HANDLE_STAFF_ID_NODE = "handleStaffId";
	public static final String FINISH_TIME_NODE = "finishTime";
	public static final String EQUIP_NO_NODE = "equipNo";
	public static final String HANDLE_PROCESS_NODE = "handleProcess";
	public static final String IS_VISIT_NODE = "isVisit";
	public static final String AUTO_RETURN_VISIT_NODE = "autoReturnVisit";
	public static final String STAFF_ID_NODE = "staffId";
	public static final String USER_NAME_NODE = "userName";
    public static final String RECOVER_TIME_NODE = "recoverTime";
    public static final String RECOVER_REASON_ID_NODE = "recoverReasonId";
    public static final String RECOVER_REASON_NAME_NODE = "recoverReasonName";
    public static final String MAINTAIN_STAFF_ID_NODE = "maintainStaffId";
    public static final String MAINTAIN_STAFF_NAME_NODE = "maintainStaffName";
    public static final String MAINTAIN_STAFF_TEL_NODE = "maintainStaffTel";
    public static final String RECOVER_CONFIRM_STAFF_NODE = "recoverConfirmStaff";
    public static final String CONFIRM_TEL_NODE = "confirmTel";
	public static final String DESC_NODE = "desc";
	public static final String REMARK_NODE = "remark";
	public static final String RELIEF_REMARK_NODE = "reliefRemark";
	public static final String PIC_USER_ID_NODE = "picUserId";
	public static final String PHOTO_RECORD_ID = "photoRecordId";
	public static final String CANCEL_REASON_ID_NODE ="cancelReasonId";
	public static final String TIME_OUT_REASON_ID_NODE ="timeOutReasonId";
	

	public static final String CANCEL_REASON_TYPE_NODE = "cancelReasonType";
	public static final String CANCEL_REASON_NODE = "cancelReason";
	
	public static final String TRACK_HELP_PROPORTION_NODE = "trackHelpProportion";
    public static final String TRACK_HELP_ORG_ID_NODE = "trackHelpOrgId";
    public static final String TRACK_HELP_ID_NODE = "trackHelpId";
    public static final String TRACK_HELP_NAME_NODE = "trackHelpName";
	
	
	/** 开通接单 */
	public Result acceptKtOrder(JSONObject json);
	
   
	/*初始化回单页面相关内容 
	 * 修复原因列表
	 * 维护人员列表
	 * 超时原因列表
	 * 维护人电话
	 * 修复确认人
	 * 修复确认人联系电话
	 * 修复时间 取数据库当前时间
	 */
	public Result workOrderViewData(JSONObject json);
	//回单
	public Result replyOrder(JSONObject json);
	
	//缓装
	public Result delayOrder(JSONObject json);
	
	//缓装原因类别
	public Result delayOrderReason(JSONObject json);
	
	//待装
	public Result waitOrder(JSONObject json);
	
	//待装原因类别
	public Result waitOrderReason(JSONObject json);
	
	//指派原因类别
	public Result appointOrderReason(JSONObject json);
	
			
	public Result loadReplyFaultOrderData(JSONObject json);
	
	//退单原因类别
	public Result cancelOrderReason(JSONObject json);
	
	//原因类别
	public Result workOrderReason(JSONObject json);
	//退单
	public Result cancelOrder(JSONObject json);

	//退单
	public Result backOrder(JSONObject json);
	//预约
	public Result appointOrder(JSONObject json);
	
	//退单原因
		public Result getCallBackReason();

	/**
	 * 开通工单查询
	 * 
	 * @param staffInfo
	 * @param workOrder
	 * @return
	 */
	public Result selPublicWorkOrderByPage(String username, Long jobId,
			Integer pageSize, Integer pageIndex);
	
	public  Result selPublicWorkOrderByPageCondition(String paramString1, Long paramLong, Integer paramInteger1, Integer paramInteger2, String paramString2, String paramString3, String paramString4, String paramString5);


	public  Result selPublicWorkOrderByPageCondition1(String paramString1, Long paramLong, Integer paramInteger1, Integer paramInteger2, String paramString2, String paramString3, String paramString4, String paramString5,String object);


	//查询组织、职位、员工列表
	public Result getOrgJobAndStaffResult(JSONObject json);
	
	/** 查询组织 */
	public Result selOrgList(Long areaId, Long orgId);
	
	/** 查询职位 */
	public Result selJobList(Long areaId,Long orgId);
	
	/** 查询人员 */
	public Result selStaffList(Long areaId,Long jobId);
	
	/** 查询人员 */
	public Result selStaffList(Long orgId);
	
    /** 授权刷新 */
	public Result authRefresh(JSONObject json);
	
	/** 签到 */
    public Result signOrder(JSONObject json);
    
    /** 签到  web端*/
    public Result signOrderInsert(JSONObject json);
    
    /** 入库操作员实时坐标*/
    public Result insertMobileStaffPosition(JSONObject json);
	
	/** 开通工单抢单列表查询 */
	public Result selPublicWorkOrderForRobByPage(String staffId, String username, Long jobId,
			Integer pageSize, Integer pageIndex);
	
	/** 开通工单抢单执行 */
	public Result executeRodOrderOperation(JSONObject json);
	
	/** 到单提醒 -- 用户token保存操作  */
	public Result saveUserInfoForNotifyOrder(JSONObject json);
	
	//所需参数查询
	public Result selParamsByStaffId(String staff_id);
	public String selAllParamsByStaffId(String staff_id);

	//开通工单详情资源信息查询
	public Result getResourceInfoList(String workOrderId);
	
	//上图
	public int insertMap(String staffId,String orgId,String smx,String smy,String state) throws Exception;

	//改单
	public Result modifyOrder(JSONObject json);

	/**
	 * 待装工单查询
	 * @param username
	 * @param jobId
	 * @param pageSize
	 * @param pageIndex
	 * @param accNbr
	 * @param bokState
	 * @param bokTime
	 * @param createDate
	 * @return
	 */
	public Result selPendingWorkOrderByPageCondition(String username, Long jobId, Integer pageSize, Integer pageIndex,
			String accNbr, String bokState, String bokTime, String createDate);
		
}
