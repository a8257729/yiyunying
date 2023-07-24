package com.ztesoft.mobile.v2.service.common;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.v2.core.Result;

/**
 * User: heisonyee
 * Date: 13-2-25
 * Time: ����4:37
 */
public interface MobileParamService {

    /** ���ݲ��������ȡ�����б� */
    public Result getParam(String gcode);

    /** ���ݲ������࣬���������ȡ���� */
    public Result getParam(String gcode, Integer mcode);
    
    /** */
    public Result getParamForApp(Integer osType);
    
    public Map getParamMap(String gcode, Integer mcode) throws Exception;
    
    public List getParamMapList(String gcode) throws Exception;

}
