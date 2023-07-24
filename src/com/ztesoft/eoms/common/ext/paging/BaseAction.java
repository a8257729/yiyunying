package com.ztesoft.eoms.common.ext.paging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseAction {
	public Object doAction(HttpServletRequest request, HttpServletResponse response);
}
