package com.ztesoft.android.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AndrBaseAction {
	public Object doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
 