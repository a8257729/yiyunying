package com.ztesoft.mobile.common.helper;

import java.util.LinkedList;

/**
 * <p>Title: EomsProj</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author dawn
 * @version 1.0
 */
public class StringHelper {
    private StringHelper() {
    }

    /**
     * 将对象转换成String
     * @param val Object
     * @return String
     */
    public static final String toString(Object val) {
        if (val == null) {
            return "";
        }
        return val.toString();
    }

    /**
     * 将对象数组转换成String
     * @param objs Object[]
     * @return String
     */
    public static final String toString(Object[] objs) {
        if (objs != null && objs.length > 0) {
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < objs.length; i++) {
                buff.append("\nItem[").append(i).append("]\n").append(objs[i]);
            }
            return buff.toString();
        } else {
            return "";
        }
    }

    /**
     * 将int值转换成String
     * @param val int
     * @return String
     */
    public static final String toString(int val) {
        return Integer.toString(val);
    }

    /**
     * 将float值转换成String
     * @param val float
     * @return String
     */
    public static final String toString(float val) {
        return Float.toString(val);
    }

    /**
     * 将double值转换成String
     * @param val double
     * @return String
     */
    public static final String toString(double val) {
        return Double.toString(val);
    }

    /**
     * 将long值转换成String
     * @param val long
     * @return String
     */
    public static final String toString(long val) {
        return Long.toString(val);
    }

    /**
     * 将short值转换成String
     * @param val short
     * @return String
     */
    public static final String toString(short val) {
        return Short.toString(val);
    }

    /**
     * 将boolean值转换成String
     * @param val boolean
     * @return String
     */
    public static final String toString(boolean val) {
        return Boolean.toString(val);
    }

    /**
     * 替换null.
     * @param str String
     * @return String
     */

    public final static String replaceNull(String str) {

        return (str != null) ? str : "";

    }

    /**
     *  split操作。
     * @param line String
     * @param separator String
     * @return String[]
     */
    public static final String[] split(String line, String separator) {
        LinkedList list = new LinkedList();
        if (line != null) {
            int start = 0;
            int end = 0;
            int separatorLen = separator.length();
            while ((end = line.indexOf(separator, start)) >= 0) {
                list.add(line.substring(start, end));
                start = end + separatorLen;
            }
            if (start < line.length()) {
                list.add(line.substring(start, line.length()));
            }
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    /**
     * 转换对象到string.
     * @param obj Object
     * @return String
     */
    public static final String stringValueOf(Object obj) {
        String str = null;
        if (ValidateHelper.validateNotNull(obj)) {
            str = String.valueOf(obj);
        }
        return str;
    }

    public static String substr(String source, int beginIndex, int endIndex) {

        return (ValidateHelper.validateNotEmpty(source)) ?
                source.substring(beginIndex, endIndex)
                : "";

    }

    public static String substr(String source, String begin, String end) {
        return (ValidateHelper.validateNotEmpty(source)) ?
                substr(source,
                       source.indexOf(begin) + begin.length(),
                       source.indexOf(end))
                : "";

    }

    public static String substr(String source, String begin) {
        return (ValidateHelper.validateNotEmpty(source)) ?
                source.substring(source.indexOf(begin) + begin.length())
                : "";

    }
    
    public static boolean isNull(Object obj){
    	
    	if(obj == null)	return true;
    	
    	if(obj instanceof String)	return "".equals((String.valueOf(obj)).trim());
    	
    	return false;
    }
    
}
