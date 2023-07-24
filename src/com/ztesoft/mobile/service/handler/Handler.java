package com.ztesoft.mobile.service.handler;

import java.util.Map;

import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;

public interface Handler {

	public Map getResultMap();
	
	/**
	 * 
	 * @param reqObj
	 * @param repObj
	 * @throws Exception
	 */
	public void handle(Map paramMap) throws Exception;
	
	/**
	 * ����handle֮ǰ�Ĵ�����
	 * @param reqObj
	 * @throws Exception
	 */
	//public void beforeHandle(RequestObject reqObj) throws Exception;

	/**
	 * ����handle֮��Ĵ�����
	 * @param reqObj
	 * @throws Exception
	 */
	//public void afterHandle(RequestObject reqObj, ResponseObject repObj) throws Exception;
	
	/**
	 * ���Ĵ������
	 * @param reqObj
	 * @param repObj
	 * @throws Exception
	 */
	//public void finalHandle(RequestObject reqObj, ResponseObject repObj) throws Exception;
}
