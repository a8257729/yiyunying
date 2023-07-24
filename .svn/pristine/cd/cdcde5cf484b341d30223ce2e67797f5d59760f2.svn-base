package com.ztesoft.mobile.v2.service.common;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.v2.core.Result;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: 下午4:37
 */
public interface MobileParamService {

    /** 根据参数分类获取参数列表 */
    public Result getParam(String gcode);

    /** 根据参数分类，参数编码获取参数 */
    public Result getParam(String gcode, Integer mcode);
    
    /** */
    public Result getParamForApp(Integer osType);
    
    public Map getParamMap(String gcode, Integer mcode) throws Exception;
    
    public List getParamMapList(String gcode) throws Exception;

}
