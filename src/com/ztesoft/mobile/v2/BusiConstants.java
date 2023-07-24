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
     * ��ѯ���˹����ƻ�ʱ��������� 
     */
    public interface PlanTimeType { 
        /** ʱ����ࣺ����� */
        public static final Integer OUT_THREE_DAYS = 1;
        /** ʱ����ࣺ������ */
        public static final Integer IN_THREE_DAYS = 0;
    }
    
    /**
     * ���¹�����ר��
     */
    public interface ORDER_TYPE {
    	/** װ�� */
    	public static final Integer INSTALL = 1;
    	/** ��� */
    	public static final Integer DISASSEMBLE = 2;
    	/** �ƻ� */
    	public static final Integer REMOVE = 3;
    	/** Ͷ�� */
    	public static final Integer COMPLAINT = 4;
    }
}
