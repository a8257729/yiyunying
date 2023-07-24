package com.ztesoft.mobile.common.extservice;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseAction {
	public Object doAction(HttpServletRequest request, HttpServletResponse response) throws SQLException ;
}
 