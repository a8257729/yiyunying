package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: forest
 * Date: 13-4-13
 */
public class MobileCancelOrder extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2214285892428623654L;
	
    public static final String REASON_NODE ="reason";
    public static final String CONTACT_NODE = "contact";
    public static final String USER_NAME_NODE = "userName";
    public static final String STAFF_ID_NODE = "staffId";

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
