package com.ztesoft.mobile.etl.vo;

import java.io.Serializable;

public class EtlDatasetTypeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6754627988853580654L;
	/**
	 * ���ݼ�����---SQL
	 */
	public final static String DSTYPE_SQL = "000";

	/**
	 * ���ݼ�����---�洢����
	 */
	public final static String DSTYPE_PROC = "001";
	
	private String datasetTypeId;
	private String datasetTypeName;
	
	public String getDatasetTypeId() {
		return datasetTypeId;
	}
	public void setDatasetTypeId(String datasetTypeId) {
		this.datasetTypeId = datasetTypeId;
	}
	public String getDatasetTypeName() {
		return datasetTypeName;
	}
	public void setDatasetTypeName(String datasetTypeName) {
		this.datasetTypeName = datasetTypeName;
	}
	
	
	
	
}
