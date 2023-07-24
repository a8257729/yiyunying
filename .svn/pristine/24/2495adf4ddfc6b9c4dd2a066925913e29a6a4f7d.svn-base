package com.ztesoft.mobile.common.xwork;

import java.util.Map;

import com.opensymphony.xwork.ActionContext;
import java.util.HashMap;
/**
 * ActionContext专门的实现类
 * @author Dawn
 *
 */
public class DedicatedActionContext {
	
/**
 * 设置传入参数
 * @param params
 */

  public static void setParams(Map params) {

    ActionContext.getContext().setParameters(params);
  }
  /**
   * 获取传入参数
   * @return
   */
  public static Map getParams() {

    return ActionContext.getContext().getParameters();

  }
  /**
   * 设置传出结果
   * @param result Object
   */
  public static void setResult(Object result) {
    Map _resultMap = new HashMap();
    _resultMap.put("applicationResult", result);
    ActionContext.getContext().setApplication(_resultMap);
  }
  /**
   * 设置传出结果
   * @param result int
   */
  public static void setResult(int result) {
    Object _result = (Object) new Integer(result);
    setResult(_result);
  }
  /**
   * 设置传出结果
   * @param result long
   */
  public static void setResult(long result) {
    Object _result = (Object) new Long(result);
    setResult(_result);
  }
  /**
   * 设置传出结果
   * @param result float
   */
  public static void setResult(float result) {
    Object _result = (Object) new Float(result);
    setResult(_result);
  }
 /**
  * 设置传出结果
  * @param result double
  */
  public static void setResult(double result) {
    Object _result = (Object) new Double(result);
    setResult(_result);
  }
 /**
  *获取传出结果
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
   * 获取Session参数
   * @return
   */
  public static Map getSession() {

    return (Map) ActionContext.getContext().getSession();
  }
/**
 * 设置Session参数
 * @param session
 */
  public static void setSession(Map session) {
    ActionContext.getContext().setSession(session);
  }
/**
 * 获取ActionContext里存储的数据
 * @param key
 * @return
 */
  public static Map get(String key) {
    return (Map) ActionContext.getContext().get(key);
  }
/**
 * 设置ActionContext存储的数据
 * @param key
 * @param map
 */
  public static void set(String key, Map map) {
    ActionContext.getContext().put(key, map);
  }

}
