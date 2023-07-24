package com.ztesoft.mobile.etl.vo;

public class ScheduleMonitorQryDto {
	private String etlRuleName;
	private Long schInstId;
	private Long scheduleId;
	private Long schSequId;
	private Long etlRuleId;
	private Long etlInstId;
	private String etlType;
	
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEtlRuleName() {
		return etlRuleName;
	}

	public void setEtlRuleName(String etlRuleName) {
		this.etlRuleName = etlRuleName;
	}

	public Long getSchInstId() {
		return schInstId;
	}

	public void setSchInstId(Long schInstId) {
		this.schInstId = schInstId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Long getSchSequId() {
		return schSequId;
	}

	public void setSchSequId(Long schSequId) {
		this.schSequId = schSequId;
	}

	public Long getEtlRuleId() {
		return etlRuleId;
	}

	public void setEtlRuleId(Long etlRuleId) {
		this.etlRuleId = etlRuleId;
	}

	public Long getEtlInstId() {
		return etlInstId;
	}

	public void setEtlInstId(Long etlInstId) {
		this.etlInstId = etlInstId;
	}

	public String getEtlType() {
		return etlType;
	}

	public void setEtlType(String etlType) {
		this.etlType = etlType;
	}
}
