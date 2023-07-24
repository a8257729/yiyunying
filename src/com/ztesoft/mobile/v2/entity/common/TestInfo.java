package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: heisonyee
 * Date: 13-2-28
 * Time: ÏÂÎç1:05
 */
public class TestInfo extends Entity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5407007156803406431L;
	
	private String code;
    private String msg;

    public TestInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public TestInfo() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
