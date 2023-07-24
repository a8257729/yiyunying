package com.ztesoft.eoms.common.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.io.*;

/**
 * <p>
 * Title: 电子运维项目
 * </p>
 * <p>
 * Descripttion 
 * <p>
 * Company: ZTESoft
 * <p>
 * Copyright: Copyright (c) 2008
 * <p>
 * @author hou.haiping
 * @version 1.0
 *
 */
public class PropertiesHelper {	
	private static final String urlStr = "/order.properties";
	private static   Properties properties = null;
	
	public PropertiesHelper() {	
		properties = new Properties();
	}
	
	private Properties parseProperties() {		
		InputStream ins = this.getClass().getResourceAsStream(urlStr);		
		try {
			PropertiesHelper.properties.load(ins);
			ins.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}		
		return null;
	}
	
	
	
	public static String getProperties(String propStr){		
		PropertiesHelper helper = new PropertiesHelper();
		helper.parseProperties();			
		return properties.getProperty(propStr);		
	}


	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer code = new StringBuffer(50);
		Long type  = new Long(621);
		System.out.println(PropertiesHelper.getProperties(type.toString()));
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");		
		System.out.println(code.append(PropertiesHelper.getProperties(type.toString())).append(dateFormat.format(now)));
				
	}

}
