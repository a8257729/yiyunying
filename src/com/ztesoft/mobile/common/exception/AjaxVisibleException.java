package com.ztesoft.mobile.common.exception;


public class AjaxVisibleException extends Exception {

    private static final long serialVersionUID = 4383422340752265394L;
    private String level;
    private String exceptionCode;
    private String exceptionString = "No exception message!";

    public AjaxVisibleException() {

    }

    public AjaxVisibleException(String level, String exceptionCode,
                                String exceptionString) {
        this.level = level;
        this.exceptionCode = exceptionCode;
        this.exceptionString = exceptionString;
    }

    public AjaxVisibleException(String level, String exceptionCode, Exception e) {
        this.level = level;
        this.exceptionCode = exceptionCode;
        this.exceptionString = e.getMessage();
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public void setExceptionString(String exceptionString) {
        this.exceptionString = exceptionString;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public final String getExceptionCode() {
        return exceptionCode;
    }

    public final String getExceptionString() {
        return exceptionString;
    }

    public final String getLevel() {
        return level;
    }

    public String toString() {
        return new StringBuffer("exceptionCode='").append(exceptionCode).append(
                "',exceptionString='").append(exceptionString)
                .append("'").toString();
    }
}
