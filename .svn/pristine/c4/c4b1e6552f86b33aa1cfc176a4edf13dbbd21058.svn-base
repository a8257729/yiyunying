package com.ztesoft.mobile.common.xwork.interceptor;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AbstractLifecycleInterceptor;
/**
 * 抽象的Action生命周期拦截器
 * @author Dawn
 *
 */
public abstract class AbstractActonInterceptor extends AbstractLifecycleInterceptor {
  //初始化方法
	public abstract void init();
	//拦截action之前
	protected abstract void before(ActionInvocation invocation)throws Exception;
//	拦截action之后
	protected abstract void after(ActionInvocation invocation,String result)throws Exception;
	//执行action时发生异常时
	protected abstract void handleException(Exception e)throws Exception;
	

}
