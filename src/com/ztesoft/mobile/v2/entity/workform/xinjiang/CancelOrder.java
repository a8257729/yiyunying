package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: forest
 * Date: 13-4-13
 */
public class CancelOrder extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2214285892428623654L;
	public static final String CANCEL_REASON_TYPE_NODE ="cancelReasonType";
    public static final String CANCEL_REASON_NODE ="cancelReason";
    public static final String CANCEL_REASON_ID_NODE ="cancelReasonId";//退单原因id
    public static final String CANCEL_REASON_DESC_NODE ="cancelReasonDesc";//退单原因描述
    public static final String USER_NAME_NODE = "userName";
    public static final String STAFF_ID_NODE = "staffId";
    public static final String WORKORDER_ID_NODE = "workOrderID";
    public static final String WORKORDER_CODE_NODE = "workOrderCode";
    public static final String EXECUTOR_ID_NODE = "executorId";//执行人
    public static final String CANCEL_STAFF_ID ="cancelStaffId";//退单人
    public static final String HANDLE_RESULT = "handleResult";//处理结果 
    private String reason;
    private String staffId;
    private String userName;

    public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
