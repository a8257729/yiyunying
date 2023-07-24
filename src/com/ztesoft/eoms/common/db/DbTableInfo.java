package com.ztesoft.eoms.common.db;

public class DbTableInfo {
	
	
	public String tableName;

	public Long tableObjId;

	public String tableType;

	public DbColumnInfo[] columnInfo;

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableObjId(Long tableObjId) {
		this.tableObjId = tableObjId;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getTableName() {
		return this.tableName;
	}

	public Long getTableObjId() {
		return this.tableObjId;
	}

	public String getTableType() {
		return this.tableType;
	}

	public void setColumnInfo(DbColumnInfo[] columnInfo) {
		this.columnInfo = columnInfo;
	}

	public DbColumnInfo[] getColumnInfo() {
		return this.columnInfo;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer(100);
		buffer
				.append("/******************Print Table Begin****************************//n");
		buffer.append("TABLENAME = ").append(tableName).append("\n");
		buffer.append("TABLEOBJID= ").append(tableObjId).append("\n");
		buffer.append("TableType=").append(tableType).append("\n");
		buffer.append("ColumnInfo Length=").append(columnInfo.length).append(
				"\n");
		for (int i = 0; i < columnInfo.length; i++) {

			buffer.append(columnInfo[i]);

		}
		buffer
				.append("/******************Print Table End *****************************//n");

		return buffer.toString();

	}

}
