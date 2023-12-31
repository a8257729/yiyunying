package com.ztesoft.mobile.v2.service.app;

import com.ztesoft.mobile.v2.controller.sn.SnPublicService;
import com.ztesoft.mobile.v2.controller.sn.impl.SnPublicServiceImpl;
import com.ztesoft.mobile.v2.controller.speed.SpeedController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAO;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAOImpl;
import com.ztesoft.mobile.v2.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class MobileDataInstallServiceImpl implements MobileDataInstallService {
    private static final Logger logger = Logger.getLogger(MobileDataInstallServiceImpl.class);
    private MobileDataInstallDAO mbDAO = new MobileDataInstallDAOImpl();
    public String queryDataInstallByOrg(String org_id)
    {
        String result = "";
        try
        {
            Map<String, String> map =   mbDAO.queryDataInstallByOrg(org_id);
            result =  map.get("flag");
        }catch (Exception e)
        {
            logger.info("queryDataInstallByOrg :"+e.getMessage());
        }


        return result;
    }

    public  Map<String, String> queryBSSProject(String work_order_id)
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
             map =   mbDAO.queryBSSProject(work_order_id);

        }catch (Exception e)
        {
            logger.info("queryDataInstallByOrg :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> queryPowerModel(String base_order_id,String hgu_sn,String staffId,String staffName,String res)
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            map =   mbDAO.queryPowerModel(base_order_id,hgu_sn,staffId,staffName,res);

        }catch (Exception e)
        {
            logger.info("queryDataInstallByOrg :"+e.getMessage());
        }


        return map;
    }

    public Map<String, String> queryPowerModel1(String order_id, String hgu_sn, String staffId, String staffName,String oldSn) {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            map =   mbDAO.queryPowerModel1(order_id,hgu_sn,staffId,staffName);
            Map<String, String> isSapMap = mbDAO.queryIsSap(oldSn);
            map.put("sap_flag",isSapMap.get("isSapMap"));
        }catch (Exception e)
        {
            logger.info("queryDataInstallByOrg :"+e.getMessage());
        }

        return map;
    }

    public  Map<String, String> querymakeProcessData(String work_order_id)
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            map =   mbDAO.querymakeProcessData(work_order_id);

        }catch (Exception e)
        {
            logger.info("querymakeProcessData :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> queryPixianCode(String work_order_id)
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            map =   mbDAO.queryPixianCode(work_order_id);

        }catch (Exception e)
        {
            logger.info("querymakeProcessData :"+e.getMessage());
        }


        return map;
    }

    public Result queryOBDInfo(String qrCode, String wkOrderId)
    {
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        Map<Object, Object>   resultMap =new HashMap<Object, Object>();
        Result result = null;
        try
        {
            resultMap =   mbDAO.queryOBDInfo(qrCode,wkOrderId);

        }catch (Exception e)
        {
            logger.info("queryDataInstallByOrg :"+e.getMessage());
        }


        resultData.put("data_info", resultMap);
        result = Result.buildSuccess();
        result.setResultData(resultData);


        return result;
    }

    public Result processDataInstall( Map<String, Object> paramMap)
    {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        Result result = null;
        try
        {
            resultMap =   mbDAO.processDataInstall(paramMap);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryDataInstallByOrg :"+e.getMessage());
            result = Result.buildFailure();
            return result;
        }

        resultData.put("data_info", resultMap);
        result = Result.buildSuccess();
        result.setResultData(resultData);


        return result;
    }

    //新逻辑
//    public Result processDataInstall1(Map<String, Object> paramMap) {
//        Map<Object, Object> resultMap = new HashMap<Object, Object>();
//        Map<Object, Object> resultData = new HashMap<Object, Object>();
//        String staff_id = paramMap.get("staff_id").toString();
//        String aaa_flag = paramMap.get("aaa_flag").toString();
//
//        Result result = null;
//        try
//        {
//            if("true".equals(aaa_flag))
//            {
//                //先删除记录uos_change_machine_order记录
//                mbDAO.delChangeMachineOrder(staff_id);
//                //记录新的订单信息
//                mbDAO.insertChangeMachineOrder(paramMap);
//            }
//            resultMap =   mbDAO.processDataInstall1(paramMap);
//
//        }catch (Exception e)
//        {
//            e.printStackTrace();
//            logger.info("queryDataInstallByOrg :"+e.getMessage());
//            result = Result.buildFailure();
//            return result;
//        }
//
//        resultData.put("data_info", resultMap);
//        result = Result.buildSuccess();
//        result.setResultData(resultData);
//
//
//        return result;
//    }

    //旧逻辑
    public Result processDataInstall1(Map<String, Object> paramMap) {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        Result result = null;
        try
        {
            resultMap =   mbDAO.processDataInstall1(paramMap);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryDataInstallByOrg :"+e.getMessage());
            result = Result.buildFailure();
            return result;
        }

        resultData.put("data_info", resultMap);
        result = Result.buildSuccess();
        result.setResultData(resultData);


        return result;
    }

    public Result addClickYHCount(String qrcode,String type) {
        Map<Object, Object> resultMap = new HashMap<Object, Object>();
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        Result result = null;
        try
        {
              mbDAO.insertYHCount(qrcode,type);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryDataInstallByOrg :"+e.getMessage());
            result = Result.buildFailure();
            return result;
        }


        result = Result.buildSuccess();
        result.setResultData(resultData);


        return result;
    }

    public Map<String,String> queryOrderTypeById(String orderId) {
        Map<String, String> resultMap =  new HashMap<String,String>();
        try
        {
            resultMap =  mbDAO.queryOrderTypeById(orderId);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return resultMap;

    }

    public String queryStaffTel(String staffId) {
        String orderType = "";
        try
        {
            orderType =  mbDAO.queryStaffTel(staffId);
        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return orderType;

    }

    public  Map<String, Object> queryZWTSpeedInfo(String work_order_id)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            map =   mbDAO.queryZWTSpeedInfo(work_order_id);

        }catch (Exception e)
        {
            logger.info("queryZWTSpeedInfo :"+e.getMessage());
        }


        return map;
    }

    public List<Map> querySpotSpeedInfo(String orderId)
    {
        List<Map>  list = new ArrayList<Map>();
        try
        {
            list =   mbDAO.querySpotSpeedInfo(orderId);

        }catch (Exception e)
        {
            logger.info("queryZWTSpeedInfo :"+e.getMessage());
        }


        return list;
    }

    public  Map<String, Object> queryLightState(String orderId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            map =   mbDAO.queryLightState(orderId);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryZWTSpeedInfo :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> submitPonChangeMachine(Map<String, Object> paramMap)
    {
        Map<String, String> map = new HashMap<String, String>();
        SnPublicService snPublicService = new SnPublicServiceImpl();

        try
        {
            Map resultMap = snPublicService.selBySystem1();
            paramMap.put("wk_order_id",(String)resultMap.get("flag"));
            map =   mbDAO.submitPonChangeMachine(paramMap);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("submitPonChangeMachine :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> queryPonChangeMachineData(String wk_order_id)
    {
        Map<String, String> map = new HashMap<String, String>();


        try
        {

            map =   mbDAO.queryPonChangeMachineData(wk_order_id);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryZWTSpeedInfo :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> queryNetOrderInfo(String orderId)
    {
        Map<String, String> map = new HashMap<String, String>();


        try
        {

            map =   mbDAO.queryNetOrderInfo(orderId);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryZWTSpeedInfo :"+e.getMessage());
        }


        return map;
    }

    public List<Map>  queryTerminalStoreByStaffId(String staffId,String oper_type)
    {

        List<Map> list = new ArrayList<Map>();
        try
        {

            list =   mbDAO.queryTerminalStoreByStaffId(staffId,oper_type);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryTerminalStoreByStaffId :"+e.getMessage());
        }

        return list;
    }

    public Map<String,String>  insertTerminalStore(Map<String,String> map)
    {

        List<Map> list = new ArrayList<Map>();
        Map<String,String> resultMap = new HashMap<String, String>();
        //staffID
        String staffId = map.get("staffId");
        //staffName
        String zwName = map.get("zwName");
        //智家手机号
        String zwNumber = map.get("zwNumber");
        //区县接口查询
        String werks = map.get("werks");

        //领用SN
        String newTerminalId = map.get("newTerminalId");
        //渠道编码；易运营四级库网络渠道系统编码 从查询接口获取
        String channelCode = map.get("channelCode");
        //渠道名称；四级库网络渠道系统名称 从查询接口获取
        String channelName = map.get("channelName");

        JSONObject  request = new JSONObject();
        request.put("broadbandUserId","");
        request.put("channelCode",channelCode);
        request.put("channelName",channelName);
        request.put("fixedNumUserId","");
        request.put("ipTvUserID","");
        request.put("isSelfDevice","0");
        request.put("newTerminalId",newTerminalId);
        request.put("oldFlag","0");
        request.put("oldPropertyCode","");
        request.put("oldSerialNumber","");
        request.put("oldTerminalId","");
        request.put("para1","67");
        request.put("para2","87");
        request.put("propertyCode","");
        request.put("recoverySource","03");
        request.put("serialNumber",getSerialNumber());
        request.put("staffId",staffId);
        request.put("terminalState","04");
        request.put("tradeType","04");
        request.put("useMode","02");
        request.put("useNumber","1");
        request.put("werks",werks);
        request.put("zdaik","");
        request.put("zwId",staffId);
        request.put("zwName",zwName);
        request.put("zwNumber",zwNumber);
        request.put("zzwzh","");
        //领用
        String url = "http://192.168.101.175:8001/digtal/sendDigtalRequest";
        String result =  HttpUtil.sendPostBoc(url, request.toString());
        JSONObject jsResult =    JSONObject.fromObject(result);
        String code = jsResult.getString("code");
        String message = jsResult.getString("message");



        map.put("STAFF_ID",staffId);

        map.put("HGU_SN",newTerminalId);
        map.put("HGU_STATUS","1");
        map.put("HGU_MAC",newTerminalId);
        map.put("HGU_SYSTEM","");
        map.put("HGU_LAN","");
        map.put("HGU_EDITION","");
        map.put("GMPONTYPE","");
        map.put("GM32CODE","");
        map.put("OPER_STAFF",staffId);
        map.put("MODIFY_STAFF",staffId);

        resultMap.put("code",code);
        resultMap.put("message",message);
        if (!"200".equals(code))
        {

            return resultMap;
        }
        try
        {
            String zdBrand = jsResult.getString("zdBrand");
            String zdType = jsResult.getString("zdType");
            String zdStyle = jsResult.getString("zdStyle");
            map.put("HGU_BRAND",zdBrand);
            map.put("HGU_MODEL",zdType);
            map.put("HGU_TYPE",zdStyle);
            mbDAO.insertTerminalStore(map);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryTerminalStoreByStaffId :"+e.getMessage());
        }

        return resultMap;
    }

    private String getSerialNumber()
    {
        String serialNumber="";

        String str="";
        Date date = new Date();

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        str = sdf.format(date);

        String dig = "";
        Random random = new Random();
        for(int i=0;i<9;i++)
        {
            int randDig = random.nextInt(10);
            dig = dig+String.valueOf(randDig);
        }
        serialNumber=str+dig;
        return serialNumber;
    }


    public  Map<String, String> sendTerimalToStaff(Map<String,String> map)
    {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code","200");

        try
        {
            //更新终端库状态
            mbDAO.updateSendTerimalData(map);
            //记录转派日志
              mbDAO.insertSendLog(map);

        }catch (Exception e)
        {
            e.printStackTrace();
            resultMap.put("code","100");
            logger.info("sendTerimalToStaff :"+e.getMessage());
        }


        return resultMap;
    }

    public  Map<String, String> acceptTerimalToStaff(Map<String,String> map)
    {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code","200");
        //知会数字能力平台
        if(sendToDigtal(map))
        {
            resultMap.put("code","100");
            return resultMap;
        }

        try
        {
            map.put("ware_house_status","2");
            map.put("accept_staff",map.get("staffId"));
            //更新终端库状态
            mbDAO.updateAcceptTerimalData(map);
            //记录转派日志
            mbDAO.insertSendLog(map);

        }catch (Exception e)
        {
            e.printStackTrace();
            resultMap.put("code","100");
            logger.info("sendTerimalToStaff :"+e.getMessage());
        }


        return resultMap;
    }

    public boolean  sendToDigtal(Map<String,String> map) {

        List<Map> list = new ArrayList<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        //staffID
        String staffId = map.get("staffId");
        //staffName
        String zwName = map.get("zwName");
        //智家手机号
        String zwNumber = map.get("zwNumber");
        //区县接口查询
        String werks = map.get("werks");

        //领用SN
        String newTerminalId = map.get("newTerminalId");
        //渠道编码；易运营四级库网络渠道系统编码 从查询接口获取
        String channelCode = map.get("channelCode");
        //渠道名称；四级库网络渠道系统名称 从查询接口获取
        String channelName = map.get("channelName");

        JSONObject request = new JSONObject();
        request.put("broadbandUserId", "");
        request.put("channelCode", channelCode);
        request.put("channelName", channelName);
        request.put("fixedNumUserId", "");
        request.put("ipTvUserID", "");
        request.put("isSelfDevice", "0");
        request.put("newTerminalId", newTerminalId);
        request.put("oldFlag", "0");
        request.put("oldPropertyCode", "");
        request.put("oldSerialNumber", "");
        request.put("oldTerminalId", "");
        request.put("para1", "67");
        request.put("para2", "87");
        request.put("propertyCode", "");
        request.put("recoverySource", "03");
        request.put("serialNumber", getSerialNumber());
        request.put("staffId", staffId);
        request.put("terminalState", "04");
        request.put("tradeType", "06");
        request.put("useMode", "02");
        request.put("useNumber", "1");
        request.put("werks", werks);
        request.put("zdaik", "");
        request.put("zwId", staffId);
        request.put("zwName", zwName);
        request.put("zwNumber", zwNumber);
        request.put("zzwzh", "");
        //领用
        String url = "http://192.168.101.175:8001/digtal/sendDigtalRequest";
        String result = HttpUtil.sendPostBoc(url, request.toString());
        JSONObject jsResult = JSONObject.fromObject(result);
        String code = jsResult.getString("code");
        String message = jsResult.getString("message");
        resultMap.put("code", code);
        resultMap.put("message", message);
        if (!"200".equals(code)) {
            return true;
        }

        return false;
    }

    public boolean  backTerimalToDigtal(Map<String,String> map) {

        List<Map> list = new ArrayList<Map>();
        Map<String, String> resultMap = new HashMap<String, String>();
        //staffID
        String staffId = map.get("staffId");
        //staffName
        String zwName = map.get("zwName");
        //智家手机号
        String zwNumber = map.get("zwNumber");
        //区县接口查询
        String werks = map.get("werks");

        //区县接口查询
        String terminalState = map.get("terminalState");



        //领用SN
        String newTerminalId = map.get("newTerminalId");
        //渠道编码；易运营四级库网络渠道系统编码 从查询接口获取
        String channelCode = map.get("channelCode");
        //渠道名称；四级库网络渠道系统名称 从查询接口获取
        String channelName = map.get("channelName");

        JSONObject request = new JSONObject();
        request.put("broadbandUserId", "");
        request.put("channelCode", channelCode);
        request.put("channelName", channelName);
        request.put("fixedNumUserId", "");
        request.put("ipTvUserID", "");
        request.put("isSelfDevice", "0");
        request.put("newTerminalId", newTerminalId);
        request.put("oldFlag", "0");
        request.put("oldPropertyCode", "");
        request.put("oldSerialNumber", "");
        request.put("oldTerminalId", "");
        request.put("para1", "67");
        request.put("para2", "87");
        request.put("propertyCode", "");
        request.put("recoverySource", "02");
        request.put("serialNumber", getSerialNumber());
        request.put("staffId", staffId);
        request.put("terminalState",terminalState);
        request.put("tradeType", "05");
        request.put("useMode", "02");
        request.put("useNumber", "1");
        request.put("werks", werks);
        request.put("zdaik", "");
        request.put("zwId", staffId);
        request.put("zwName", zwName);
        request.put("zwNumber", zwNumber);
        request.put("zzwzh", "");
        //领用
        String url = "http://192.168.101.175:8001/digtal/sendDigtalRequest";
        String result = HttpUtil.sendPostBoc(url, request.toString());
        JSONObject jsResult = JSONObject.fromObject(result);
        String code = jsResult.getString("code");
        String message = jsResult.getString("message");
        resultMap.put("code", code);
        resultMap.put("message", message);
        if (!"200".equals(code)) {
            return true;
        }

        return false;
    }

    public  Map<String, String> backTerimalToStaff(Map<String,String> map)
    {
        Map<String, String> resultMap = new HashMap<String, String>();


        try
        {
            //更新终端库状态
            mbDAO.updateBackTerimalData(map);
            //记录转派日志
            mbDAO.insertSendLog(map);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("backTerimalToStaff :"+e.getMessage());
        }

        resultMap.put("code","200");
        return resultMap;
    }


    public  Map<String, String> backToDigtal(Map<String,String> map)
    {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code","200");
        //知会数字能力平台
        if(backTerimalToDigtal(map))
        {
            resultMap.put("code","100");
            return resultMap;
        }

        try
        {
            map.put("ware_house_status","1");
            //更新终端库状态
            mbDAO.updateRetTerimalData(map);


        }catch (Exception e)
        {
            e.printStackTrace();
            resultMap.put("code","100");
            logger.info("backToDigtal :"+e.getMessage());
        }


        return resultMap;
    }


    public  Map<String, String> queryStaff(String phoneNumber)
    {
        Map<String,String> result  = new HashMap<String, String>();

        result= mbDAO.queryStaff(phoneNumber);
        return result;
    }

    public List<Map> queryTerminalOperByStaffId(String staffId,String oper_type)
    {
        List<Map> list = new ArrayList<Map>();
        try
        {

            list =   mbDAO.queryTerminalOperByStaffId(staffId,oper_type);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryTerminalOperByStaffId :"+e.getMessage());
        }

        return list;
    }

    public  Map<String, String> queryStaffMobile(String staffId)
    {

        Map<String,String> result = new HashMap<String, String>();


        try
        {
            result =  mbDAO.queryStaffMobile(staffId);

           String yyy = mbDAO.getWareHouseStaff(staffId,"0");
            result.put("yyy_status",yyy);
        }catch (Exception e)
        {
            e.printStackTrace();
        }



        return result;
    }

    public  Map<String, String> changePowerModel(Map<String,Object> data)
    {
        Map<String,String> resultMap = new HashMap<String,String>();

        try
        {
          resultMap = sendChangePowerModel(data);
          String code = resultMap.get("code");
            if("".equals(code))
            {
                //去掉换机订单信息
               // delChangePowerModel();
                Map<String, String> param = new HashMap<String, String>();

                param.put("staffId",(String)data.get("staffId"));
                //  param.put("accept_staff",accept_staff);
                param.put("terminal_state","3.5");
                param.put("terminalState","07");
                param.put("terimal_sn",(String)data.get("sn_old"));
               // param.put("oper_type",oper_type);

                param.put("zwName",(String)data.get("zwName"));
                param.put("zwNumber",(String)data.get("zwNumber"));
                param.put("werks",(String)data.get("werks"));
                param.put("newTerminalId",(String)data.get("sn_old"));
                param.put("channelCode",(String)data.get("channelCode"));
                param.put("channelName",(String)data.get("channelName"));

                //发送待检退回命令
                backTerimalToDigtal(param);

                param.put("ware_house_status","1");
                //更新终端库状态
                mbDAO.updateRetTerimalData(param);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            resultMap.put("code","111");
        }

        return resultMap;
    }

    public Map<String,String> sendChangePowerModel(Map<String,Object> data)
    {
        String code="";
        Map<String, String> resultMap = new HashMap<String, String>();
        //staffID
        String staffId =(String) data.get("staffId");
        //staffName
        String zwName = (String)data.get("zwName");
        //智家手机号
        String zwNumber = (String)data.get("zwNumber");
        //区县接口查询
        String werks =(String) data.get("werks");

        //区县接口查询
        //String terminalState =(String) data.get("terminalState");

        String broadbandUserId =(String) data.get("broadbandUserId");

        //1是sap  0不是sap
        String sap_flag =(String) data.get("sap_flag");



        //领用SN
        String newTerminalId =(String) data.get("newTerminalId");
        //渠道编码；易运营四级库网络渠道系统编码 从查询接口获取
        String channelCode = (String)data.get("channelCode");
        //渠道名称；四级库网络渠道系统名称 从查询接口获取
        String channelName =(String) data.get("channelName");

        String oldTerminalId = (String)data.get("oldTerminalId");

        JSONObject request = new JSONObject();
        request.put("broadbandUserId", broadbandUserId);
        request.put("channelCode", channelCode);
        request.put("channelName", channelName);
        request.put("fixedNumUserId", "");
        request.put("ipTvUserID", "");
        request.put("isSelfDevice", "1");
        request.put("newTerminalId", newTerminalId);
        request.put("oldFlag", "0");
        request.put("oldPropertyCode", "");
        request.put("oldSerialNumber", "");
        request.put("oldTerminalId", oldTerminalId);
        request.put("para1", "67");
        request.put("para2", "87");
        request.put("propertyCode", "");
        request.put("recoverySource", "01");
        request.put("serialNumber", getSerialNumber());
        request.put("staffId", staffId);
        request.put("terminalState","04");
        request.put("tradeType", "02");
        request.put("useMode", "01");
        request.put("useNumber", "1");
        request.put("werks", werks);
        request.put("zdaik", "");
        request.put("zwId", staffId);
        request.put("zwName", zwName);
        request.put("zwNumber", zwNumber);
        request.put("zzwzh", "");
        //领用
        String url = "http://192.168.101.175:8001/digtal/sendDigtalRequest";
        String result = HttpUtil.sendPostBoc(url, request.toString());
        JSONObject jsResult = JSONObject.fromObject(result);
         code = jsResult.getString("code");
        String message = jsResult.getString("message");
        resultMap.put("code", code);
        resultMap.put("message", message);

        //换机成功需要删除光猫换机订单
        if("200".equals(code))
        {
            try
            {
                if("0".equals(sap_flag))
                {
                    //将旧串码保存到下沉库
                    mbDAO.insertChangeMachineOldSn(data);
                }
                mbDAO.delChangeMachineOrder(staffId);
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }

        return resultMap;
    }


    public  Map<String, String> queryChangeMachineOrder(String staffId)
    {
        Map<String, String> map = new HashMap<String, String>();


        try
        {

            map =   mbDAO.queryChangeMachineOrder(staffId);

        }catch (Exception e)
        {
            e.printStackTrace();
            logger.info("queryChangeMachineOrder :"+e.getMessage());
        }


        return map;
    }

    public  Map<String, String> queryTerimalBySn(String sn)
    {
        Map<String,String> result  = new HashMap<String, String>();

        try{
            result= mbDAO.queryTerimalBySn(sn);
        }catch (Exception e)
        {
            e.printStackTrace();

        }
        return result;
    }

}
