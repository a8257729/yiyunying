package com.ztesoft.mobile.v2.util;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 参数配置获取工具类
 * 读取param.properties 文件中配置的属性参数
 * @author yangjihui
 *
 */
public class ParamPropertyUtil {
	private static Logger logger = LoggerFactory.getLogger(ParamPropertyUtil.class);
    public static PropertiesLoader 	GLOB_CONFIG = null;

    static {
        init();
    }

    private static void init() {
        //输入配置文件名，一个或多个
        GLOB_CONFIG = new PropertiesLoader("param.properties");
    }

    public static String getConfig(String key) {
        if (GLOB_CONFIG == null) {
            init();
        }
        String value = "";
        try{
            value = GLOB_CONFIG.getProperty(key);
        }catch(NoSuchElementException e){
        	logger.error("参数不存在：" +  key);
        }
        return value;
    }
    
    /**
     * 获取参数配置值，如果没有配置返回默认值
     * @param key 配置的参数值
     * @param defaultValue 如果没有配置默认返回值
     * @return
     */
    public static String getConfigWithDefault(String key,String defaultValue) {
        if (GLOB_CONFIG == null) {
            init();
        }
        String value = defaultValue;
        try{
            value = GLOB_CONFIG.getProperty(key);
        }catch(NoSuchElementException e){
        	logger.error("参数不存在：" +  key);
        }
        return value;
    }
    
    public static void main(String[] args) {
    	System.out.println(ParamPropertyUtil.getConfig("guangshuaiPowerKey"));
	}

}
