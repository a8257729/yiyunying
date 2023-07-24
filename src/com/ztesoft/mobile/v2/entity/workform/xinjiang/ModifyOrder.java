package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

public class ModifyOrder extends Entity {
	private static final long serialVersionUID = -2777110025530022458L;
	private String produceOrderId;
	private String oprateName;
	private String serviceNum;
	private String contactPhone;
	private String serviceType;
	private String addressInfo;
	private String remark;
	
	
	public String getProduceOrderId() {
		return produceOrderId;
	}
	public void setProduceOrderId(String produceOrderId) {
		this.produceOrderId = produceOrderId;
	}
	public String getOprateName() {
		return oprateName;
	}
	public void setOprateName(String oprateName) {
		this.oprateName = oprateName;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getAddressInfo() {
		return addressInfo;
	}
	public void setAddressInfo(String addressInfo) {
		this.addressInfo = addressInfo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
