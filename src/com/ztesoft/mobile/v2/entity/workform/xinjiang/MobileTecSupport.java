package com.ztesoft.mobile.v2.entity.workform.xinjiang;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: forest
 * Date: 13-4-13
 */
public class MobileTecSupport extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2214285892428623654L;
	
    public static final String EXPERT_NODE ="expert";
    public static final String DESC_NODE = "desc";
    public static final String USER_NAME_NODE = "userName";
    public static final String STAFF_ID_NODE = "staffId";

    private String desc;
    private String expert;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getExpert() {
		return expert;
	}

	public void setExpert(String expert) {
		this.expert = expert;
	}



}
