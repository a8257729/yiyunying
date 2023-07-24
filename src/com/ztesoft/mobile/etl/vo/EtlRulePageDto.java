package com.ztesoft.mobile.etl.vo;

import java.io.Serializable;

public class EtlRulePageDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1839570098352723752L;
	
	private int totalSize = 0; 		//��ҳ��
	private int pageSize = 15; 		//ÿҳ��¼��,Ĭ��Ϊ15
	private int pageIndex = 0; 		//��ǰҳ [1, totalSize]
	private int totalRecords = 0; 	//�ܼ�¼��
	
	private EtlRuleDto[] etlRuleDto;

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public EtlRuleDto[] getEtlRuleDto() {
		return etlRuleDto;
	}

	public void setEtlRuleDto(EtlRuleDto[] etlRuleDto) {
		this.etlRuleDto = etlRuleDto;
	}
	
	

}
