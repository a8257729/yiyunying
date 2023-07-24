package com.ztesoft.mobile.common.xwork.execution;

public class MonitorActionThreadLocal {
	
	private MonitorActionThreadLocal(){
		
	}
	
private static final ThreadLocal _local = new ThreadLocal();

  public static void putValue(Object value){
	  _local.set(value);
  }

  public static Object getValue(){
	  return _local.get();
  }
}
