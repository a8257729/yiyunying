package com.ztesoft.mobile.etl.vo;

import java.io.Serializable;
public class SchedulePageDto implements Serializable{

	 private int totalSize = 0; //总页数
	    private int pageSize = 0; //每页记录数
	    private int pageIndex = 0; //当前页 [1, totalSize]
	    private int totalRecords = 0; //总记录数

	    private ScheduleDto[] scheduleDto;
	    
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

		public ScheduleDto[] getScheduleDto() {
			return scheduleDto;
		}

		public void setScheduleDto(ScheduleDto[] scheduleDto) {
			this.scheduleDto = scheduleDto;
		}

		public String getDataSourceXML() {
			return dataSourceXML;
		}

		public void setDataSourceXML(String dataSourceXML) {
			this.dataSourceXML = dataSourceXML;
		}
}
