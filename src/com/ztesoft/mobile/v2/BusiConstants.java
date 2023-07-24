package com.ztesoft.mobile.v2;

/**
 * @author heisonyee
 *
 */
public abstract class BusiConstants extends BaseConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306103695941798487L;

	/**
     * 查询个人工单计划时间分类类型 
     */
    public interface PlanTimeType { 
        /** 时间分类：三天后 */
        public static final Integer OUT_THREE_DAYS = 1;
        /** 时间分类：三天内 */
        public static final Integer IN_THREE_DAYS = 0;
    }
    
    /**
     * 以下供湖北专用
     */
    public interface ORDER_TYPE {
    	/** 装机 */
    	public static final Integer INSTALL = 1;
    	/** 拆机 */
    	public static final Integer DISASSEMBLE = 2;
    	/** 移机 */
    	public static final Integer REMOVE = 3;
    	/** 投诉 */
    	public static final Integer COMPLAINT = 4;
    }
}
