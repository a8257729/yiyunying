package com.ztesoft.mobile.v2.core;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1202651235369600886L;
	
	private Integer errorCode;
	private String errorMessage;

	public BusinessException() {
		super();
	}

	public BusinessException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BusinessException(String arg0) {
		super(arg0);
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}
	
	public BusinessException(Integer errorCode, String errorMessage, Throwable arg0) {
		super(arg0);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	/** 封装异常信息 */
	public static BusinessException wrapExcepiton(Throwable throwable) {
		return new BusinessException(throwable);
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
