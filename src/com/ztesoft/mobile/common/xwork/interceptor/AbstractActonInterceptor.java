package com.ztesoft.mobile.common.xwork.interceptor;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AbstractLifecycleInterceptor;
/**
 * �����Action��������������
 * @author Dawn
 *
 */
public abstract class AbstractActonInterceptor extends AbstractLifecycleInterceptor {
  //��ʼ������
	public abstract void init();
	//����action֮ǰ
	protected abstract void before(ActionInvocation invocation)throws Exception;
//	����action֮��
	protected abstract void after(ActionInvocation invocation,String result)throws Exception;
	//ִ��actionʱ�����쳣ʱ
	protected abstract void handleException(Exception e)throws Exception;
	

}
