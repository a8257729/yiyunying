package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MobileDataInstallDAO extends BaseDAO {
    public Map<String,String> queryDataInstallByOrg(String org_id) throws SQLException;

    public Map<String,String> querySelOrgByAccount(String account) throws SQLException;

    public Map<String,String> queryBSSProject(String work_order_id) throws SQLException;

    public Map<Object,Object> queryOBDInfo(String qrCode, String wkOrderId) throws SQLException;

    public Map<String,String> querymakeProcessData(String work_order_id) throws SQLException;
    public Map<Object,Object> processDataInstall( Map<String,Object> paramMap) throws SQLException;
    public Map<Object,Object> processDataInstall1( Map<String,Object> paramMap) throws SQLException;

    public Map<String,String> queryPowerModel(String base_order_id,String hgu_sn,String staffId,String staffName,String res) throws SQLException;


    public Map<String,String> queryPowerModel1(String order_id,String hgu_sn,String staffId,String staffName) throws SQLException;

    public Map<String, String> queryPixianCode(String wk_order_id) throws SQLException;

    public void insertYHCount(String qrcode,String type) throws SQLException;

    public Map<String,String> queryOrderTypeById(String orderId) throws SQLException;

    public String queryStaffTel(String staffId) throws SQLException;

    public Map<String,Object> queryZWTSpeedInfo(String work_order_id) throws SQLException;

    public List<Map> querySpotSpeedInfo(String work_order_id) throws SQLException;

    public Map<String, Object> queryLightState(String orderId) throws SQLException;

    public Map<String, String> submitPonChangeMachine(Map<String,Object> paramMap) throws SQLException;

    public Map<String,String> queryPonChangeMachineData(String wk_order_id) throws SQLException;

    public  Map<String, String> queryNetOrderInfo(String orderId);


    /**
     * 终端库根据staffId查询
     * @param staffId
     * @return
     */
    public List<Map> queryTerminalStoreByStaffId(String staffId,String oper_type);


    /**
     * 领用入库
     * @param map
     * @throws SQLException
     */
    public void insertTerminalStore(Map<String,String> map) throws SQLException;

    /**
     * 记录转派动作日志
     * @param map
     * @throws SQLException
     */
    public void insertSendLog(Map<String,String> map) throws SQLException;

    /**
     * 更新终端转派数据
     * @param map
     * @throws SQLException
     */
    public void updateSendTerimalData(Map<String,String> map) throws SQLException;
    /**
     * 更新终端转派回退数据
     * @param map
     * @throws SQLException
     */
    public void updateBackTerimalData(Map<String,String> map) throws SQLException;

    /**
     * 更新终端转派接收数据
     * @param map
     * @throws SQLException
     */
    public void updateAcceptTerimalData(Map<String,String> map) throws SQLException;

    public void updateRetTerimalData(Map<String,String> map) throws SQLException;


    public  Map<String, String> queryStaff(String phoneNumber);


    public Map<String,String> queryTerimalBySn(String sn) throws SQLException;


    /**
     * 查询终端操作记录
     * @param staffId
     * @param oper_type
     * @return
     */
    public List<Map> queryTerminalOperByStaffId(String staffId,String oper_type);


    public  Map<String, String> queryStaffMobile(String staffId);
    public String getWareHouseStaff(String staffId,String orderId) throws DataAccessException, SQLException;
    public Map<String,String> queryIsSap(String wk_order_id) throws SQLException;

    public   Map<String, String> queryChangeMachineOrder(String staffId);

    public void insertChangeMachineOrder(Map<String,Object> paramMap) throws SQLException;
    public void delChangeMachineOrder(String staffId) throws SQLException;
    public void insertChangeMachineOldSn(Map<String,Object> paramMap) throws SQLException;






}
