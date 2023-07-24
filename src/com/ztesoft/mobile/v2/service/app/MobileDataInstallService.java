package com.ztesoft.mobile.v2.service.app;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MobileDataInstallService {
    public String queryDataInstallByOrg(String org_id);
    public  Map<String, String> queryBSSProject(String work_order_id);
    public Result queryOBDInfo(String qrCode, String wkOrderId);

    public  Map<String, String> querymakeProcessData(String work_order_id);
    public  Map<String, String> queryPixianCode(String work_order_id);
    public Result processDataInstall( Map<String, Object> paramMap);

    public Result processDataInstall1( Map<String, Object> paramMap);

    public  Map<String, String> queryPowerModel(String base_order_id,String hgu_sn,String staffId,String staffName,String res);


    public  Result addClickYHCount(String qrcode,String type);

    public Map<String,String> queryOrderTypeById(String orderId);
    public String queryStaffTel(String staffId);

    public  Map<String, Object> queryZWTSpeedInfo(String work_order_id);
    public List<Map> querySpotSpeedInfo(String orderId);
    public  Map<String, Object> queryLightState(String order_id);
    public Map<String,String> submitPonChangeMachine(Map<String, Object> paramMap);
    public  Map<String, String> queryPonChangeMachineData(String wk_order_id);
    public  Map<String, String> queryNetOrderInfo(String orderId);

    public List<Map>  queryTerminalStoreByStaffId(String staffId,String oper_type);

    public Map<String,String>  insertTerminalStore(Map<String,String> map);


    /**
     * 记录转派动作日志
     * @param map
     * @throws SQLException
     */
    public Map<String,String> sendTerimalToStaff(Map<String,String> map) throws SQLException;
    public  Map<String, String> acceptTerimalToStaff(Map<String,String> map);

    public  Map<String, String> backTerimalToStaff(Map<String,String> map);
    public  Map<String, String> backToDigtal(Map<String,String> map);
    public  Map<String, String> queryStaff(String phoneNumber);

    public List<Map> queryTerminalOperByStaffId(String staffId,String oper_type);

    public  Map<String, String> queryStaffMobile(String staffId);
    public Map<String, String> queryPowerModel1(String order_id, String hgu_sn, String staffId, String staffName,String oldSn);
    public Map<String,String> sendChangePowerModel(Map<String,Object> data);
    public  Map<String, String> queryChangeMachineOrder(String staffId);


    public  Map<String, String> queryTerimalBySn(String sn);
}
