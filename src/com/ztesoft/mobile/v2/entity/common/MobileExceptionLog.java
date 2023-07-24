package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

import java.util.Date;

/**
 * User: heisonyee
 * Date: 13-3-13
 * Time: ÉÏÎç10:27
 */
public class MobileExceptionLog extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2745667526441999323L;

	public static final String TABLE_NAME = "MOBILE_EXCEPTION_LOG";

    public static final String EXCEPTION_LOG_NODE = "exceptionLog";
    public static final String EXCEPTION_NODE = "exception";
    public static final String EXCEPTION_LOG_ID_NODE = "exceptionLogId";
    public static final String REST_SERVICE_ID_NODE = "restServiceId";
    public static final String EXCEPTION_NAME_NODE = "exceptionName";
    public static final String EXCEPTION_MSG_NODE = "exceptionMsg";
    public static final String EXCEPTION_TIME_NODE ="exceptionTime";

    private Long exceptionLogId;
    private String exceptionName;
    private String exceptionMsg;
    private Date exceptionTime;
    
    public MobileExceptionLog(String exceptionName, String exceptionMsg,
			Date exceptionTime) {
		super();
		this.exceptionName = exceptionName;
		this.exceptionMsg = exceptionMsg;
		this.exceptionTime = exceptionTime;
	}

    public MobileExceptionLog() {
		super();
	}

	public Long getExceptionLogId() {
		return exceptionLogId;
	}

	public void setExceptionLogId(Long exceptionLogId) {
		this.exceptionLogId = exceptionLogId;
	}

	public String getExceptionName() {
		return exceptionName;
	}

	public void setExceptionName(String exceptionName) {
		this.exceptionName = exceptionName;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

	public Date getExceptionTime() {
		return exceptionTime;
	}

	public void setExceptionTime(Date exceptionTime) {
		this.exceptionTime = exceptionTime;
	}
    
}
