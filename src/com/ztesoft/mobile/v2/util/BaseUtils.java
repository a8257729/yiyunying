package com.ztesoft.mobile.v2.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseUtils {
	
	/** ��ȡ�ļ���׺�� */
	public static String getFileSuffix(String fileName) {
    	if(StringUtils.isBlank(fileName)) return "";
    	
    	String suffix = null;
    	int lastIdx = fileName.lastIndexOf('.');
    	if(-1 != lastIdx) {
    		suffix = fileName.substring(lastIdx + 1);
    	}
    	return suffix;
    }
    
	/** ��ȡ��������׺�����ļ��� */
	public static String getFileNameWithoutSuffix(String fileName) {
    	if(StringUtils.isBlank(fileName)) return "";
    	
    	String nFileName = null;
    	int lastIdx = fileName.lastIndexOf('.');
    	if(-1 != lastIdx) {
    		nFileName = fileName.substring(0,lastIdx);
    	} else {
    		nFileName = fileName;
    	}
    	return nFileName;
    }
	
	public static String getDateString(long millis) {
		Date date = new Date(millis);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
}
