package com.ztesoft.eoms.common.db;

public class DbColumnInfo {
	// public String

	public String columnName;

	public String columnType;

	public Integer isComputed;// 0--false;1--true

	public Long length;

	public String prec;

	public Integer scale;

	public Integer nullAble;// 0--false;1--true

	//此字段是否自动增长的字段?(1-true;0-false)
	public Integer isIdentity;// 0--false;1--true

	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnType() {
		return this.columnType;
	}

	public Integer getIsComputed() {
		return this.isComputed;
	}

	public Long getLength() {
		return this.length;
	}

	public String getPrec() {
		return this.prec;
	}

	public Integer getIsIdentity() {
		return isIdentity;
	}

	public void setIsIdentity(Integer isIdentity) {
		this.isIdentity = isIdentity;
	}

	public Integer getNullAble() {
		return nullAble;
	}

	public void setNullAble(Integer nullAble) {
		this.nullAble = nullAble;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public void setIsComputed(Integer isComputed) {
		this.isComputed = isComputed;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public void setPrec(String prec) {
		this.prec = prec;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer(100);
		buffer.append("Column").append(" Name ='").append(columnName).append("'");
		buffer.append(" Type='").append(columnType).append("'");
		buffer.append(" isComputed='").append(isComputed.intValue()==0?true:false).append("'");
		buffer.append(" nullAble='").append(nullAble.intValue()==0?true:false).append("'");
		buffer.append(" Length ='").append(length).append("'");
		buffer.append(" Prec='").append(prec).append("'");
		buffer.append(" Scale='").append(scale).append("'");
		buffer.append(" isIdentity='").append(isIdentity.intValue()==0?true:false).append("'");
		buffer.append("\n");
		return buffer.toString();
	}
}
