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
	 * 调用handle之前的处理方法
	 * @param reqObj
	 * @throws Exception
	 */
	//public void beforeHandle(RequestObject reqObj) throws Exception;

	/**
	 * 调用handle之后的处理方法
	 * @param reqObj
	 * @throws Exception
	 */
	//public void afterHandle(RequestObject reqObj, ResponseObject repObj) throws Exception;
	
	/**
	 * 最后的处理操作
	 * @param reqObj
	 * @param repObj
	 * @throws Exception
	 */
	//public void finalHandle(RequestObject reqObj, ResponseObject repObj) throws Exception;
}
