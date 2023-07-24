package com.ztesoft.mobile.etl.vo;


public class DSDataDto {
	
	private String[] fieldName;
	private String errorMessage;
	private String dataXml;
	
	public String[] getFieldName() {
		return fieldName;
	}
	public void setFieldName(String[] fieldName) {
		this.fieldName = fieldName;
	}
	public String getDataXml() {
		return dataXml;
	}
	public void setDataXml(String dataXml) {
		this.dataXml = dataXml;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
