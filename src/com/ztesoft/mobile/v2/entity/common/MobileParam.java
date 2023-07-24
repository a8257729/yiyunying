package com.ztesoft.mobile.v2.entity.common;

import com.ztesoft.mobile.v2.core.Entity;

/**
 * User: heisonyee
 * Date: 13-2-28
 * Time: ÏÂÎç1:02
 */
public class MobileParam extends Entity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6542235836940784764L;

	public static final String TABLE_NAME = "MOBILE_PARAM";
	/** param */
	public static final String PARAM_NODE = "param";
	/** paramList */
	public static final String PARAM_LIST_NODE = "paramList";
	
	/** paramId */
	public static final String PARAM_ID_NODE = "paramId";
	/** gcode */
	public static final String GCODE_NODE = "gcode";
	/** mcode */
	public static final String MCODE_NODE = "mcode";
	/** mname */
	public static final String MNAME_NODE = "mname";
	
	private Long paramId;
	
	private String gcode;
	
	private Integer mcode;
	
	private String mname;

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public String getGcode() {
		return gcode;
	}

	public void setGcode(String gcode) {
		this.gcode = gcode;
	}

	public Integer getMcode() {
		return mcode;
	}

	public void setMcode(Integer mcode) {
		this.mcode = mcode;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}
}
