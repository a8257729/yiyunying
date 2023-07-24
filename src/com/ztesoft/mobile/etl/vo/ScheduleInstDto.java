package com.ztesoft.mobile.etl.vo;

import java.util.Date;

public class ScheduleInstDto {

	private Long schInstId;
	private Long scheduleId;
	private Date etlStartDate;
	private Date etlNextDate;
	private String state;
	private String stateText;
	private Date createDate;
	private Date stateDate;
	private String memo;
	private String operMan;
	private String operManId;
	private String operManType;
	private int execRate;
	private String sqEtlScheduleName;
	public String getSqEtlScheduleName() {
		return sqEtlScheduleName;
	}
	public void setSqEtlScheduleName(String sqEtlScheduleName) {
		this.sqEtlScheduleName = sqEtlScheduleName;
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
	public Date getEtlStartDate() {
		return etlStartDate;
	}
	public void setEtlStartDate(Date etlStartDate) {
		this.etlStartDate = etlStartDate;
	}
	public Date getEtlNextDate() {
		return etlNextDate;
	}
	public void setEtlNextDate(Date etlNextDate) {
		this.etlNextDate = etlNextDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getStateDate() {
		return stateDate;
	}
	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOperMan() {
		return operMan;
	}
	public void setOperMan(String operMan) {
		this.operMan = operMan;
	}
	public String getOperManId() {
		return operManId;
	}
	public void setOperManId(String operManId) {
		this.operManId = operManId;
	}
	public String getOperManType() {
		return operManType;
	}
	public void setOperManType(String operManType) {
		this.operManType = operManType;
	}
	public int getExecRate() {
		return execRate;
	}
	public void setExecRate(int execRate) {
		this.execRate = execRate;
	}
	public String getStateText() {
		return stateText;
	}
	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

}
