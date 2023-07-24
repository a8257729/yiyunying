package com.ztesoft.mobile.v2.service.common;

import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-3-18
 * Time: обнГ2:27
 */
public interface MobileWebConfigService {

    public String getConfigValue(String configCode);
    
    public Map getConfigFTPPath(String configCode);

}
