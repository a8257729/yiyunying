package com.ztesoft.mobile.v2.entity.interf;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: heisonyee
 * Date: 13-3-13
 * Time: ÉÏÎç10:27
 */
public class MobileRestService extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1294112362245559147L;

	public static final String TABLE_NAME = "MOBILE_REST_SERVICE";

    public static final String REST_SERVICE_NODE = "restService";
    public static final String REST_SERVICE_LIST_NODE = "restServiceList";
    
    public static final String REST_SERVICE_ID_NODE = "restServiceId";
    public static final String SERV_NAME_NODE = "servName";
    public static final String SERV_ADDR_NODE = "servAddr";
    public static final String SERV_STATUS_NODE ="servStatus";
    public static final String SERV_TYPE_NODE ="servType";

    private Long restServiceId;
    
    private String servName;
    
    private String servAddr;
    
    private Integer servStatus;

    private Integer servType;
    
	public Long getRestServiceId() {
		return restServiceId;
	}

	public void setRestServiceId(Long restServiceId) {
		this.restServiceId = restServiceId;
	}

	public String getServName() {
		return servName;
	}

	public void setServName(String servName) {
		this.servName = servName;
	}

	public String getServAddr() {
		return servAddr;
	}

	public void setServAddr(String servAddr) {
		this.servAddr = servAddr;
	}

	public Integer getServStatus() {
		return servStatus;
	}

	public void setServStatus(Integer servStatus) {
		this.servStatus = servStatus;
	}

	public Integer getServType() {
		return servType;
	}

	public void setServType(Integer servType) {
		this.servType = servType;
	}
    
}
