package com.ztesoft.eoms.common.helper;

import com.zterc.uos.client.ParameterManager;
import com.zterc.uos.model.ParameterDTO;

/**
 * 读取配置文件里的值
 * @author Xsh
 *
 */
public class UosConfigHelper {
	
    private static ParameterManager parameterManager = null;
    
    /**
     * 获取是否需要代理
     * @return
     */
    public static String getWeatherIsProxy(){
    	return getValueByKey("WEATHER_IS_PROXY");
    }
 
    public static String getWeatherProxyHost(){
    	return getValueByKey("WEATHER_PROXY_HOST");
    }
    
    public static String getWeatherProxyPort(){
    	return getValueByKey("WEATHER_PROXY_PORT");
    }
    
    public static String getValueByKey(String _key) {
        String _value = null;
        if (parameterManager == null) {
            parameterManager = new ParameterManager(false);
        }
        try {
            ParameterDTO paraDto = parameterManager.findParameter(_key);
            if (paraDto != null) {
                _value = paraDto.getValue();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return _value;
    }
}
