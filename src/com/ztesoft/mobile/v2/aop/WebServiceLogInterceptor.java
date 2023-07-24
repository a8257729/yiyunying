package com.ztesoft.mobile.v2.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class WebServiceLogInterceptor implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		
		Object object = null;
		
		object = invocation.proceed();
		
		return object;
	}

}
