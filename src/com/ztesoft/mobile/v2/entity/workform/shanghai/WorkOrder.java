package com.ztesoft.mobile.v2.entity.workform.shanghai;

import com.ztesoft.mobile.v2.core.Entity;

public class WorkOrder extends Entity {

	public static final String TABLE_NAME = "UOS_WORK_ORDER";
	
	public static final String RECOVER_REASON_LIST_NODE = "recoverReasonList";
	
	public static final String WORK_ORDER_NODE = "workOrder";
	
	public static final String WORK_ORDER_LIST_NODE = "workOrderList";
	
	public static final String ORDER_ID_NODE = "orderId";
	
	public static final String ORDER_TITLE = "orderTitle";
	
	public static final String ORDER_CODE = "orderCode";
	
	public static final String WORK_ORDER_ID_NODE = "workOrderId";
	
	public static final String WORK_ORDER_CODE_NODE = "workOrderCode";
	
	public static final String TACHE_NAME_NODE = "tacheName";
	
	public static final String SERVICE_NAME_NODE = "serviceName";
	
	public static final String CREATE_DATE_NODE = "createDate";
	
	public static final String ACC_NBR_NODE = "accNbr";
	
	//Task:127163
	public static final String CUST_NAME_NODE = "custName";
	public static final String DEPORDER_DATE_NODE = "deporderDate";
	
	public static final String WORK_ORDER_STATE = "workOrderState";
	
	public static final String WORK_ORDER_STATE_NAME_NODE = "workOrderStateName";
	
	public static final String WORK_ORDER_TYPE_NAME_NODE = "workOrderTypeName";
	
	public static final String ORDER_TYPE_NODE = "orderTypeName";
	
	public static final String ORDER_PRIORITY_NAME_NODE = "orderPriorityName";
	
	public static final String LIMIT_DATE_NODE = "limitDate";
	
	public static final String ORDER_DETAIL_NODE = "orderDetail";
	
	public static final String BESP_DATE_NODE = "bespDate";
	
	public static final String PREBESP_DATE_NODE = "preBespDate";
	
	private Long orderId;			// 订单ID
	private String orderTitle;		// 订单标题
	private String orderCode;		// 订单编码
	private Long workOrderId; 		// 工单ID
	private String workOrderCode;	// 工单编码
	private String tacheName; 		// 环节名称
	private String serviceName; 	// 业务名称
	private String createDate;		// 派单时间
	private String accNbr;			// 业务号码
	private String custName;//客户名称 Task:127163
	private String deporderDate;//完工时间
	
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getDeporderDate() {
		return deporderDate;
	}

	public void setDeporderDate(String deporderDate) {
		this.deporderDate = deporderDate;
	}

	private String workOrderState;		// 工单状态
	private String workOrderStateName;	// 工单状态名称
	private String workOrderTypeName;	// 工单类型
	private String orderTypeName;	// 订单类型
	private String orderPriorityName;	// 订单级别
	private String limitDate;		// 订成期限
	private String orderDetail;		// 订单描述
	private String bespDate;		// 平台预约开始时间
	private String preBespDate;		// 精确预约开始时间
	
	private boolean isAccepted;		// 是否已签单
	
	private boolean isDeparted;		// 是否已出发
	
	private boolean isSignin;		// 是否已签到
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getWorkOrderId() {
		return workOrderId;
	}

	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}

	public String getWorkOrderCode() {
		return workOrderCode;
	}

	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}

	public String getTacheName() {
		return tacheName;
	}

	public void setTacheName(String tacheName) {
		this.tacheName = tacheName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public void setAccNbr(String accNbr) {
		this.accNbr = accNbr;
	}

	public String getWorkOrderStateName() {
		return workOrderStateName;
	}

	public void setWorkOrderStateName(String workOrderStateName) {
		this.workOrderStateName = workOrderStateName;
	}

	public String getWorkOrderTypeName() {
		return workOrderTypeName;
	}

	public void setWorkOrderTypeName(String workOrderTypeName) {
		this.workOrderTypeName = workOrderTypeName;
	}

	public String getOrderTypeName() {
		return orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public String getOrderPriorityName() {
		return orderPriorityName;
	}

	public void setOrderPriorityName(String orderPriorityName) {
		this.orderPriorityName = orderPriorityName;
	}

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}

	public String getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(String orderDetail) {
		this.orderDetail = orderDetail;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public String getWorkOrderState() {
		return workOrderState;
	}

	public void setWorkOrderState(String workOrderState) {
		this.workOrderState = workOrderState;
	}

	public boolean isDeparted() {
		return isDeparted;
	}

	public void setDeparted(boolean isDeparted) {
		this.isDeparted = isDeparted;
	}

	public boolean isSignin() {
		return isSignin;
	}

	public void setSignin(boolean isSignin) {
		this.isSignin = isSignin;
	}

	public String getBespDate() {
		return bespDate;
	}

	public void setBespDate(String bespDate) {
		this.bespDate = bespDate;
	}

	public String getPreBespDate() {
		return preBespDate;
	}

	public void setPreBespDate(String preBespDate) {
		this.preBespDate = preBespDate;
	}
}
