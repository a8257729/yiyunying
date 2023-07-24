package com.ztesoft.eoms.exception;

import java.sql.SQLException;

public class DataAccessException extends SQLException {

	/**
	 * 
	 * @param msg
	 */
	public DataAccessException(String msg) {
		super(msg);
	}

	/**
	 * 
	 * @param msg
	 * @param ex
	 */
	public DataAccessException(String msg, Throwable ex) {

		this(msg);
		initCause(ex);
	}

}
