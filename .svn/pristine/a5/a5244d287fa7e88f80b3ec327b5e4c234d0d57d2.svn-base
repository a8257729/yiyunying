package com.ztesoft.mobile.common.helper;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author  dawn
 * @version 1.0
 */
public class NumberHelper {
    private NumberHelper() {
    }

    /**
     * 转换Object到Integer
     * @param obj Object
     * @return Integer
     */
    public static Integer integerValueOf(Object obj) {

        return (ValidateHelper.validateNotNull(obj)) ?
                new Integer(StringHelper.toString(obj)) : null;

    }

    public static int parseInt(Integer i) {
        return (ValidateHelper.validateNotNull(i)) ? i.intValue() :
                Integer.MAX_VALUE;

    }

    /**
     * 转换object到Long
     * @param obj Object
     * @return Long
     */
    public static Long longValueOf(Object obj) {
        return (ValidateHelper.validateNotNull(obj)) ?
                new Long(String.valueOf(obj)) : null;

    }

    public static long parseLong(Long l) {
        return (ValidateHelper.validateNotNull(l)) ? l.longValue() :
                Long.MAX_VALUE;

    }


    /**
     * 转换object到Double
     * @param obj Object
     * @return Double
     */
    public static Double doubleValueOf(Object obj) {
        return (ValidateHelper.validateNotNull(obj)) ?
                new Double(String.valueOf(obj)) : null;

    }

    public static double parseDouble(Double d) {
        return (ValidateHelper.validateNotNull(d)) ? d.doubleValue() :
                Double.MAX_VALUE;

    }


    /**
     * 转换object到float
     * @param obj Object
     * @return Float
     */

    public static Float floatValueOf(Object obj) {
        return (ValidateHelper.validateNotNull(obj)) ?
                new Float(String.valueOf(obj)) : null;

    }

    public static float parseFloat(Float f) {
        return (ValidateHelper.validateNotNull(f)) ? f.floatValue() :
                Float.MAX_VALUE;
    }

    /**
     * 获取简单计算的随机数
     * @param num int
     * @return String
     */
    public static String getSimplyRandom(int num) {

        double d = Math.random();

        int value = (int) (d * Math.pow(10, num));

        return StringHelper.toString(value);

    }
    public static boolean isNumeric(String str){ 
    	  for (int i = str.length();--i>=0;){   
    	   if (!Character.isDigit(str.charAt(i))){ 
    	    return false; 
    	   } 
    	  } 
    	  return true; 
    }

}
