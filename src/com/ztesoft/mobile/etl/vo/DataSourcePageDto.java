package com.ztesoft.mobile.etl.vo;

import java.io.Serializable;


public class DataSourcePageDto implements Serializable{
	  private int totalSize = 0; //��ҳ��
	    private int pageSize = 0; //ÿҳ��¼��
	    private int pageIndex = 0; //��ǰҳ [1, totalSize]
	    private int totalRecords = 0; //�ܼ�¼��

	    private DataSourceDto[] dataSourceDto;
	    
	    private String dataSourceXML;

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

		public DataSourceDto[] getDataSourceDto() {
			return dataSourceDto;
		}

		public void setDataSourceDto(DataSourceDto[] dataSourceDto) {
			this.dataSourceDto = dataSourceDto;
		}

		public String getDataSourceXML() {
			return dataSourceXML;
		}

		public void setDataSourceXML(String dataSourceXML) {
			this.dataSourceXML = dataSourceXML;
		}
	    
	    
}
