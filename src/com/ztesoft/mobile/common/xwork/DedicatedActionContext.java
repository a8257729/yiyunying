package com.ztesoft.mobile.common.xwork;

import java.util.Map;

import com.opensymphony.xwork.ActionContext;
import java.util.HashMap;
/**
 * ActionContextר�ŵ�ʵ����
 * @author Dawn
 *
 */
public class DedicatedActionContext {
	
/**
 * ���ô������
 * @param params
 */

  public static void setParams(Map params) {

    ActionContext.getContext().setParameters(params);
  }
  /**
   * ��ȡ�������
   * @return
   */
  public static Map getParams() {

    return ActionContext.getContext().getParameters();

  }
  /**
   * ���ô������
   * @param result Object
   */
  public static void setResult(Object result) {
    Map _resultMap = new HashMap();
    _resultMap.put("applicationResult", result);
    ActionContext.getContext().setApplication(_resultMap);
  }
  /**
   * ���ô������
   * @param result int
   */
  public static void setResult(int result) {
    Object _result = (Object) new Integer(result);
    setResult(_result);
  }
  /**
   * ���ô������
   * @param result long
   */
  public static void setResult(long result) {
    Object _result = (Object) new Long(result);
    setResult(_result);
  }
  /**
   * ���ô������
   * @param result float
   */
  public static void setResult(float result) {
    Object _result = (Object) new Float(result);
    setResult(_result);
  }
 /**
  * ���ô������
  * @param result double
  */
  public static void setResult(double result) {
    Object _result = (Object) new Double(result);
    setResult(_result);
  }
 /**
  *��ȡ�������
  * @return
  */
  public static Object getResult() {
    Map _resultMap = ActionContext.getContext().getApplication();
    if(_resultMap!=null){
      return _resultMap.get("applicationResult");
    }
    else{
      return null;
    }
  }
  /**
   * ��ȡSession����
   * @return
   */
  public static Map getSession() {

    return (Map) ActionContext.getContext().getSession();
  }
/**
 * ����Session����
 * @param session
 */
  public static void setSession(Map session) {
    ActionContext.getContext().setSession(session);
  }
/**
 * ��ȡActionContext��洢������
 * @param key
 * @return
 */
  public static Map get(String key) {
    return (Map) ActionContext.getContext().get(key);
  }
/**
 * ����ActionContext�洢������
 * @param key
 * @param map
 */
  public static void set(String key, Map map) {
    ActionContext.getContext().put(key, map);
  }

}
