package com.ztesoft.mobile.v2.controller.newFunction;


import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.hnlt.iptv.interf.IptvInterface;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.service.common.CommonServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ���Ļ�����Mac
 */
@Controller("changeIptvStbController")
public class ChangeIptvStbController extends WebConfigController {

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    private static final Logger logger = Logger.getLogger(ChangeIptvStbController.class);

    @RequestMapping(value = {"/client/changeIptvStbController/changeIptvStb"})
    public @ResponseBody
    Result changeIptvStb(@RequestBody Map<String, Object> data, ModelMap model,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mac = (String) data.get("mac");
        String oldMac = (String) data.get("oldMac");
        String cityId = (String) data.get("areano");
        String servaccount = (String) data.get("account");
        String orgId = (String) data.get("orgId");
        String userName = (String) data.get("userName");
        Result result = Result.buildSuccess();

        IptvInterface iptvInterface = new IptvInterface();
        Map inMap = new HashMap();
        inMap.put("mac", mac);
        inMap.put("oldMac", oldMac);
        inMap.put("cityId", cityId);
        inMap.put("servaccount", servaccount);

        //�������id�Ͳ���Աid
        inMap.put("grid", orgId);
        inMap.put("opertor", userName);

        try {
            Map UnbindMap = iptvInterface.changeIPTVSTB(inMap);
            logger.info("���������нӿڷ��أ�" + UnbindMap.toString());
            result.setResultData(UnbindMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }


    /**
     * ���������а�����
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/changeIptvStbController/changeIPTVStbApply"})
    public @ResponseBody
    Result changeIPTVStbApply(@RequestBody Map<String, Object> data, ModelMap model,
                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        String mac = (String) data.get("mac");
        String address = (String) data.get("address");
        String orgId = (String) data.get("orgId");
        String userName = (String) data.get("userName");
        String workOrderId = (String) data.get("workOrderId");
        //�ȸ��ݹ���id��ѯiptv�˺�
        String servaccount = getIptvAccountByWorkOrderId(workOrderId);
        logger.info("���������а�����iptv�˺�Ϊ��" + servaccount);

        Result result = Result.buildSuccess();

        //��У��ö���ͬһ��mac�Ƿ��Ѿ��й�����
        String checkResult = checkExistIPTVStbApply(servaccount,mac,workOrderId);
        if (!StringUtil.isNull(checkResult)) {
            Map resultMap = new HashMap();
            resultMap.put("resultCode", "002");
            resultMap.put("resultDesc", checkResult);
            result.setResultData(resultMap);
            return result;
        }

        IptvInterface iptvInterface = new IptvInterface();
        Map inMap = new HashMap();
        inMap.put("mac", mac);
        inMap.put("address", address);
        inMap.put("servaccount", servaccount);

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNowStr = sdf.format(d);
        String strTime = String.valueOf(System.nanoTime());
        strTime = strTime.substring(strTime.length() - 4);
        String cmdId = "FromGK-" + dateNowStr + strTime;
        inMap.put("cmdId", cmdId);

        //�������id�Ͳ���Աid
        inMap.put("grid", orgId);
        inMap.put("opertor", userName);

        try {
            Map resultMap = iptvInterface.changeIPTVStbApply(inMap);
            String resultCode = (String) resultMap.get("resultCode");
            String resultDesc = (String) resultMap.get("resultDesc");
            logger.info("���°󶨻���������ӿڷ��أ�" + resultMap.toString());
            result.setResultData(resultMap);

            //��¼�������־
            recordApplyLog(mac, servaccount, userName, workOrderId, cmdId,resultCode,resultDesc);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * У���Ƿ�ù���ͬһ��IPTV�˺��Ѿ��й���ͬmac�󶨵������¼
     * @return
     */
    private String checkExistIPTVStbApply(String servAccount,String mac,String workOrderId){
        String sql = "select 1 as flag from iptv_bind_apply_log t where t.iptv_account = ? \n" +
                     " and t.mac = ? and  t.work_order_id = ? and t.result_code = '000' ";
        Map map = new LinkedHashMap();
        map.put("servAccount",servAccount);
        map.put("mac", mac);
        map.put("workOrderId",workOrderId);
        String paramStr = "servAccount:servAccount,mac:mac,workOrderId:workOrderId";
        Result result = getCommonService().commonQueryObjectBySql(sql, map, paramStr);
        if(result.getResultData().get("data_info") != null ){
            Map data_info = (Map) result.getResultData().get("data_info");
            if(data_info.get("flag") == null){
               return "";
           }else{
                return "�ù����Ѿ��ύ����ͬmac�İ����룬�����ظ��ύ���룬��ȴ����������";
            }
        }
        return "";
    }

    /**
     * ���ݹ���id��ѯiptv�˺�
     * @param workOrderId
     * @return
     */
    private String getIptvAccountByWorkOrderId(String workOrderId){
        String iptvAccount = "";
        StringBuilder sql = new StringBuilder();
        sql.append(" select osa.attr_value as iptvAccount                                                   ");
        sql.append("   from wo_work_order oo                                                                ");
        sql.append("   LEFT JOIN OM_SO_ATTR OSA                                                             ");
        sql.append("     ON (OSA.SERVICE_ORDER_ID = OO.Base_Order_Id AND OSA.ATTR_CODE = 'iptv_loginname')  ");
        sql.append("  where oo.id = ?                                                                       ");
        Map map = new LinkedHashMap();
        map.put("workOrderId",workOrderId);
        String paramStr = "workOrderId:workOrderId";
        Result result = getCommonService().commonQueryObjectBySql(sql.toString(), map, paramStr);
        if(result.getResultData().get("data_info") != null ){
            Map data_info = (Map) result.getResultData().get("data_info");
            iptvAccount = (String) data_info.get("iptvAccount");
        }
        return iptvAccount;
    }

    public static void main(String[] args) {
        String result = new ChangeIptvStbController().checkExistIPTVStbApply("", "", "");
        System.out.print(result);
    }

    /**
     * ��¼iptv�󶨱���������־
     * @param mac
     * @param servaccount
     * @param userName
     * @param workOrderId
     * @param cmdId
     */
    private void recordApplyLog(String mac, String servaccount, String userName, String workOrderId, String cmdId,String resultCode,String resultDesc) {
        String sql = "insert into iptv_bind_apply_log(work_order_id,cmd_id,mac,iptv_account,operator_user_name,apply_date,result_code,result_desc) values(?,?,?,?,?,sysdate,?,?) ";
        Map map = new LinkedHashMap();
        map.put("workOrderId",workOrderId);
        map.put("cmdId", cmdId);
        map.put("mac", mac);
        map.put("servaccount",servaccount);
        map.put("userName",userName);
        map.put("resultCode", resultCode);
        map.put("resultDesc", resultDesc);
        getCommonService().commonInsertObjectBySql(sql,map);
    }


    /**
     * ��ѯ���������а����빤��ִ�н��
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/changeIptvStbController/queryStbBindChgRecord"})
    public @ResponseBody
    Result queryStbBindChgRecord(@RequestBody Map<String, Object> data, ModelMap model,
                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        //String cmdId = (String) data.get("cmdId");
        String cmdId = "";
        String workOrderId = (String) data.get("workOrderId");
        String mac = (String) data.get("mac");

        //�ȸ���workOrderId��mac��ѯ�����cmdId
        String sql = "select t.cmd_id as cmdId from iptv_bind_apply_log t where t.work_order_id = ? and t.mac = ? and t.result_code = '000' ";
        Map paramMap = new LinkedHashMap();
        paramMap.put("workOrderId",workOrderId);
        paramMap.put("mac", mac);
        String whereStr = "workOrderId:workOrderId,mac:mac";
        Result queryResult = getCommonService().commonQueryObjectBySql(sql, paramMap, whereStr);
        if(queryResult.getResultData().get("data_info") != null){
            Map resultMap = (Map) queryResult.getResultData().get("data_info");
            cmdId = (String) resultMap.get("cmdId");
        }

        Result result = Result.buildSuccess();
        if(StringUtil.isNull(cmdId)){
             Map resultDataMap = new HashMap();
             resultDataMap.put("result", 1);
             resultDataMap.put("description", "û�в�ѯ���ù�����MAC:" + mac + " ����󶨵ļ�¼��");
             result.setResultData(resultDataMap);
             return result;
        }

        IptvInterface iptvInterface = new IptvInterface();
        try {
            logger.info("��ѯ����˽��cmdId:" + cmdId);
            Map resultMap = iptvInterface.queryStbBindChgRecord(cmdId);
            logger.info("��ѯ���������а����빤��ִ�н���ӿڷ��أ�" + resultMap.toString());
            if(resultMap.get("status") == null || ("null").equals(resultMap.get("status").toString()) ){
                resultMap.put("status", "");
            }
            result.setResultData(resultMap);

            System.out.println("====================="+resultMap);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }



}

