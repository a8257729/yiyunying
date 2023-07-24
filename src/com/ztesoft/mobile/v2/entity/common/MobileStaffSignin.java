package com.ztesoft.mobile.v2.entity.common;

import java.util.Date;

import com.ztesoft.mobile.v2.core.Entity;

public class MobileStaffSignin extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2023424563230301113L;

	public static final String TABLE_NAME = "MOBILE_STAFF_SIGNIN";
	
	public static final String STAFF_SIGNIN_ID_NODE = "staffSigninId";
	public static final String STAFF_ID_NODE = "staffId";
	public static final String STAFF_NAME_NODE = "staffName";
	public static final String USERNAME_NODE = "username";
	public static final String SIGNIN_STATUS_NODE = "signinStatus";
	public static final String SIGNIN_TIME_NODE = "signinTime";
	public static final String LONGITUDE_NODE = "longitude";
	public static final String LATITUDE_NODE = "latitude";
	public static final String SIGNIN_TYPE_NODE = "signinType";
	public static final String SIGNIN_ADDR_NODE = "signinAddr";
	public static final String CROODS_TYPE_NODE = "croodsType";
	public static final String WORDORDER_ID_NODE = "wordOrderId";
	public static final String ORDER_ID_NODE = "orderId";	

	private Long staffSigninId;
	private Long staffId;
	private String staffName;
	private String username;
	private Integer signinStatus;
	private Date signinTime;
	private Float longitude;
	private Float latitude;
	private Integer signinType;
	private String signinAddr;
	private Integer croodsType;
	private Long wordOrderId;
	private Long orderId;
	
	
	public Long getWordOrderId() {
		return wordOrderId;
	}
	public void setWordOrderId(Long wordOrderId) {
		this.wordOrderId = wordOrderId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getStaffSigninId() {
		return staffSigninId;
	}
	public void setStaffSigninId(Long staffSigninId) {
		this.staffSigninId = staffSigninId;
	}
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getUsername() {
		return username;
	}
	public void setUserame(String username) {
		this.username = username;
	}
	public Integer getSigninStatus() {
		return signinStatus;
	}
	public void setSigninStatus(Integer signinStatus) {
		this.signinStatus = signinStatus;
	}
	public Date getSigninTime() {
		return signinTime;
	}
	public void setSigninTime(Date signinTime) {
		this.signinTime = signinTime;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Integer getSigninType() {
		return signinType;
	}
	public void setSigninType(Integer signinType) {
		this.signinType = signinType;
	}
	public String getSigninAddr() {
		return signinAddr;
	}
	public void setSigninAddr(String signinAddr) {
		this.signinAddr = signinAddr;
	}
	public Integer getCroodsType() {
		return croodsType;
	}
	public void setCroodsType(Integer croodsType) {
		this.croodsType = croodsType;
	}
}
