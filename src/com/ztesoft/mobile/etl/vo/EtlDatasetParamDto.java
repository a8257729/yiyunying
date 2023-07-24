package com.ztesoft.mobile.etl.vo;

import java.io.Serializable;

public class EtlDatasetParamDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9034792113472404067L;
	
	private Long paramId;
	private Long etlRuleId;
	private String paramCode;
	private String querySql;
	private Long operManId;
	private String operManName;
	
	public Long getParamId() {
		return paramId;
	}
	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}
	public Long getEtlRuleId() {
		return etlRuleId;
	}
	public void setEtlRuleId(Long etlRuleId) {
		this.etlRuleId = etlRuleId;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public Long getOperManId() {
		return operManId;
	}
	public void setOperManId(Long operManId) {
		this.operManId = operManId;
	}
	public String getOperManName() {
		return operManName;
	}
	public void setOperManName(String operManName) {
		this.operManName = operManName;
	}
}
