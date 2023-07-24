package com.ztesoft.mobile.v2.controller.speed;

import com.ztesoft.mobile.v2.controller.common.CommonBaseController;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.service.app.MobileDataInstallService;
import com.ztesoft.mobile.v2.service.app.MobileDataInstallServiceImpl;
import com.ztesoft.mobile.v2.service.resource.ResourceService;
import com.ztesoft.mobile.v2.service.resource.ResourceServiceImpl;
import com.ztesoft.mobile.v2.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
public class SpeedController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(SpeedController.class);
    private MobileDataInstallService dataInstallService = new MobileDataInstallServiceImpl();

    @RequestMapping(value = {"/client/speed/queryBroadband1"})
    @ResponseBody
    public Object queryBroadband(@RequestBody Map<String, Object> data, ModelMap model,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryBroadband");
        String account = (String) data.get("account");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.101.15:5090/bdes-oi/thirdPartyAction/userBindNumberInfo.action?dNumber="+account;
        Map param = new HashMap();
        Map resMap = restTemplate.postForObject(url, param, Map.class);
        List<Map<String,Object>> list = (List)resMap.get("data");
        
        if(list!=null){
            if(list.size()>0){
                Map<String, Object> map = list.get(0);
                Set<String> keySet = map.keySet();
                Iterator<String> it = keySet.iterator();
                while(it.hasNext()) {
                    System.out.println("speed"+" "+map.get("speed"));
                    Map<String,String> speed = new HashMap();
                    speed.put("speed", (String) map.get("speed"));
                    logger.info("调用方法：queryBroadband 返回结果:"+speed+",参数:"+account);
                    return speed;
                }
            }
        }
        logger.info("调用方法：queryBroadband 返回结果:"+null+",参数:"+account);
        return null;
    }


    @RequestMapping(value = {"/client/dataInstall/queryDataInstallByOrg"})
    @ResponseBody
    public Object queryDataInstallByOrg(@RequestBody Map<String, Object> data, ModelMap model,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String org_id = data.get("org_id").toString();
        Map<String, Object> result = new HashMap<String, Object>();
        String falg = dataInstallService.queryDataInstallByOrg(org_id);
        result.put("result",falg);
        return result;
    }


    @RequestMapping(value = {"/client/bss/project/queryBSSProject"})
    @ResponseBody
    public Object queryBSSProject(@RequestBody Map<String, Object> data, ModelMap model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String work_order_id = data.get("work_order_id").toString();
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryBSSProject(work_order_id);

        return result;
    }
    @RequestMapping(value = {"/client/query/obd/queryPixianCode"})
    @ResponseBody
    public Object queryPixianCode(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        Map<String, Object>  param = (Map<String, Object>) data.get("param");
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryPixianCode(param.get("workOrderId").toString());

        return result;
    }

    @RequestMapping(value = {"/client/query/project/queryPowerModel"})
    @ResponseBody
    public Object queryPowerModel(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String base_order_id = data.get("base_order_id").toString();
        String hgu_sn = data.get("hgu_sn").toString();
        String staffId = data.get("staffId").toString();
        String STAFF_NAME = data.get("STAFF_NAME").toString();
        String res = data.get("res").toString();
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryPowerModel(base_order_id,hgu_sn,staffId,STAFF_NAME,res);

        return result;
    }

    @RequestMapping(value = {"/client/query/project/queryPowerModel1"})
    @ResponseBody
    public Object queryPowerModel1(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String order_id = data.get("order_id").toString();
        String hgu_sn = data.get("hgu_sn").toString();
        String staffId = data.get("staffId").toString();
        String STAFF_NAME = data.get("STAFF_NAME").toString();
        String oldSn = data.get("STAFF_NAME").toString();
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryPowerModel1(order_id,hgu_sn,staffId,STAFF_NAME,oldSn);
        System.out.println("result=-=-=-======================================================>>>>>>"+result);
        return result;
    }

    @RequestMapping(value = {"/client/query/obd/queryOBDInfo"})
    @ResponseBody
    public Result queryOBDInfo(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String qrCode = data.get("qrCode").toString();
        String workOrderId = data.get("workOrderId").toString();

        Result result = dataInstallService.queryOBDInfo(qrCode,workOrderId);

        return result;
    }

    @RequestMapping(value = {"/client/execute/dataInstall/processDataInstall"})
    @ResponseBody
    public Result processDataInstall(@RequestBody Map<String, Object> data, ModelMap model,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：processDataInstall");


        Result result = dataInstallService.processDataInstall(data);

        return result;
    }

    @RequestMapping(value = {"/client/execute/dataInstall/processDataInstall1"})
    @ResponseBody
    public Result processDataInstall1(@RequestBody Map<String, Object> data, ModelMap model,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：processDataInstall1");

        Result result = dataInstallService.processDataInstall1(data);
        return result;
    }

    @RequestMapping(value = {"/client/dataInstall/query/querymakeProcessData"})
    @ResponseBody
    public Object querymakeProcessData(@RequestBody Map<String, Object> data, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String wk_order_id = data.get("wk_order_id").toString();
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.querymakeProcessData(wk_order_id);

        return result;
    }

    @RequestMapping(value = {"/client/zwt/query/speedInfo"})
    @ResponseBody
    public Object queryZWTSpeedInfo(@RequestBody Map<String, Object> data, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryZWTSpeedInfo");
        Map<String, Object> param = (Map<String, Object>) data.get("param");
       String accNbr = "";
        String kdaccountAc = param.get("kdaccountAc").toString();
        String requestOrderId = param.get("orderId").toString();
        if("".equals(kdaccountAc))
        {
            Map<String,String> resultMap =  dataInstallService.queryOrderTypeById(requestOrderId);
            accNbr = resultMap.get("accNbrCode");
        }
        else
        {
            accNbr=kdaccountAc;
        }

       List<Map>  resultList = new ArrayList<Map>();
        Map<String, Object>  orderMap = dataInstallService.queryZWTSpeedInfo(accNbr);
        String orderId = orderMap.get("orderId").toString();
        resultList = dataInstallService.querySpotSpeedInfo(orderId);
        return resultList;
    }



    /**
     * 微信公众号用户信息查询
     */
    @RequestMapping(value = { "/client/yh/click/count" })
    public @ResponseBody void addClickYHCount(@RequestParam String callBack, HttpServletRequest request, HttpServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.info(" Call queryUserHouseInfoByCode method ");
        }
        try{
            logger.info(" Call queryUserHouseInfoByCode method ");
            String qrcode = request.getParameter("qrcode");
           String type = request.getParameter("type");
            Result result = dataInstallService.addClickYHCount(qrcode,type);
            JSONObject jsonResult = JSONObject.fromObject(result);
            response.getWriter().print(callBack+"("+jsonResult.toString()+")");
            response.flushBuffer();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 微信公众号用户信息查询
     */
    @RequestMapping(value = { "/client/yh/click/countNew" },produces = "application/json;charset=UTF-8")
    public @ResponseBody String addClickYHCounts(@RequestBody String params, HttpServletRequest request, HttpServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.info(" Call queryUserHouseInfoByCode method ");
        }
        try{
            logger.info(" Call queryUserHouseInfoByCode method ");
            JSONObject jsonObject = JSONObject.fromObject(params);
            String qrcode = jsonObject.getString("qrcode");
            String type = jsonObject.getString("type");
            Result result = dataInstallService.addClickYHCount(qrcode,type);
            JSONObject jsonResult = JSONObject.fromObject(result);
            String res = jsonResult.toString();
            response.setContentLength(res.getBytes("UTF-8").length);
            return res;
        }catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }



    @ResponseBody
    @RequestMapping(value = "/client/zwt/query/speed", method = RequestMethod.POST,produces = "application/text;charset=UTF-8")
    public String queryZWTSpeed(@RequestBody String jsonParam) {
        // 	小区基本信息查询
        logger.info("zwt query speed param:"+jsonParam);
        JSONObject js =  JSONObject.fromObject(jsonParam);
       String orderId = js.getString("orderId");
        String staffId = js.getString("staffId");
        String kdaccountAc = js.getString("kdaccountAc");
        String accNbr = js.getString("userBroadcast");
        Map<String,String> resultMap =  dataInstallService.queryOrderTypeById(orderId);
        String phone =  dataInstallService.queryStaffTel(staffId);
        js.put("orderScene",resultMap.get("orderType"));
        js.put("cityCode",resultMap.get("cityCode"));
        js.put("districtCode",resultMap.get("districtCode"));
        js.put("phone",phone);
        //组网是没有宽带账号的所以为空
        if("".equals(kdaccountAc))
        {
            String userBroadcast = resultMap.get("accNbrCode");
            js.put("userBroadcast",userBroadcast);
        }
        else
        {
            js.put("userBroadcast",kdaccountAc);
        }
        // 将获取的json数据封装一层，然后在给返回

System.out.println("json param :"+js.toString());
        String result =  HttpUtil.sendPostBoc("http://192.168.101.175:11231/orders/powerSharing", js.toString());
        System.out.println("queryZWTSpeed result :"+result);
        JSONObject retJson =  JSONObject.fromObject(result);
        JSONObject bss_body = retJson.getJSONObject("UNI_BSS_BODY");
        JSONObject rspJson = bss_body.getJSONObject("VALET_PLACE_ORDER_INFORMATION_RSP");
        String message = rspJson.getString("message");
        System.out.println("queryZWTSpeed message :"+message);
        return message;
    }

    @RequestMapping(value = {"/client/query/detail/lightState"})
    @ResponseBody
    public Object queryLightState(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryLightState");
        String order_id = data.get("order_id").toString();

        Map<String, Object> result = new HashMap<String, Object>();
        result = dataInstallService.queryLightState(order_id);

        System.out.println("result=-=-=-======================================================>>>>>>"+result);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/client/interface/commonPost", method = RequestMethod.POST)
    public String commonPost(@RequestParam Map<String,Object> request) {
        String jsonParam = (String)request.get("param");
        String url = (String)request.get("url");

        // 入参json
        logger.info("commonPost param:"+jsonParam);
        // 入参url
        logger.info("commonPost url:"+url);

        // 将获取的json数据封装一层，然后在给返回
        //  String result="{\"code\":\"200\",\"msg\":\"\",\"data\":{\"ponInfoEntityList\":[{\"communityName\":\"小区名称1\",\"oltIp\":\"10.1.5.46\",\"ponCode\":\"NA-1-1\",\"terminalSituation\":\"端业匹配\",\"weakLightSituation\":\"弱光\",\"userAccount\":\"073125484541\",\"userName\":\"王五\",\"userAddress\":\"香樟路\"},{\"communityName\":\"小区名称2\",\"oltIp\":\"10.1.5.46\",\"ponCode\":\"NA-1-2\",\"terminalSituation\":\"端业匹配\",\"weakLightSituation\":\"弱光\",\"userAccount\":\"073125484551\",\"userName\":\"李四\",\"userAddress\":\"香樟路\"}]}}";
        String result =  HttpUtil.sendPostBoc(url, jsonParam);


        return result;
    }


    @RequestMapping(value = {"/client/change/machine/submitPonChangeMachine"})
    @ResponseBody
    public Object submitPonChangeMachine(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitPonChangeMachine");

        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.submitPonChangeMachine(data);

        return result;
    }

    @RequestMapping(value = {"/client/change/machine/queryPonChangeMachineData"})
    @ResponseBody
    public Object queryPonChangeMachineData(@RequestBody Map<String, Object> data, ModelMap model,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitPonChangeMachine");

        String wk_order_id = data.get("wk_order_id")==null?"":(String)data.get("wk_order_id");
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryPonChangeMachineData(wk_order_id);

        return result;
    }

    /**
     * 固网回单
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/query/net/order/info"})
    @ResponseBody
    public Object queryNetOrderInfo(@RequestBody Map<String, Object> data, ModelMap model,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String orderId = data.get("wk_order_id")==null?"":(String)data.get("wk_order_id");
        String staff_name = data.get("staff_name")==null?"":(String)data.get("staff_name");
        String workOrderCode = data.get("workOrderCode")==null?"":(String)data.get("workOrderCode");
        String check = data.get("check")==null?"":(String)data.get("check");
        String mainSheetFlowNo = data.get("mainSheetFlowNo")==null?"":(String)data.get("mainSheetFlowNo");
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryNetOrderInfo(orderId);
        result.put("accpetRes",check);
        result.put("recycler",staff_name);
        result.put("acceptor",staff_name);
        result.put("applyId",mainSheetFlowNo);
        result.put("busiId",workOrderCode);
        result.put("transId",workOrderCode);
        result.put("idNum","");
        return result;
    }


    /**
     * 终端查询
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/query/terminalstore"})
    @ResponseBody
    public Object queryTerminalStoreByStaffId(@RequestBody Map<String, Object> data, ModelMap model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryZWTSpeedInfo");
        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");


        List<Map>  resultList = new ArrayList<Map>();

        resultList = dataInstallService.queryTerminalStoreByStaffId(staffId,oper_type);
        return resultList;
    }


    /**
     * 终端领用入库
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/save/saveTerminalStore"})
    @ResponseBody
    public Object saveTerminalStore(@RequestBody Map<String, Object> data, ModelMap model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        String zwName = data.get("zwName")==null?"":(String)data.get("zwName");
        String zwNumber = data.get("zwNumber")==null?"":(String)data.get("zwNumber");
        String werks = data.get("werks")==null?"":(String)data.get("werks");
        String newTerminalId = data.get("newTerminalId")==null?"":(String)data.get("newTerminalId");
        String channelCode = data.get("channelCode")==null?"":(String)data.get("channelCode");
        String channelName = data.get("channelName")==null?"":(String)data.get("channelName");
        Map<String, String> param = new HashMap<String, String>();
        param.put("staffId",staffId);
        param.put("zwName",zwName);
        param.put("zwNumber",zwNumber);
        param.put("werks",werks);
        param.put("newTerminalId",newTerminalId);
        param.put("channelCode",channelCode);
        param.put("channelName",channelName);
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.insertTerminalStore(param);

        return result;
    }


    /**
     * 终端派单
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/update/sendTerimalToStaff"})
    @ResponseBody
    public Object sendTerimalToStaff(@RequestBody Map<String, Object> data, ModelMap model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        String accept_staff = data.get("accept_staff")==null?"":(String)data.get("accept_staff");
        String terminal_state = data.get("terminal_state")==null?"":(String)data.get("terminal_state");
        String terimal_sn = data.get("terimal_sn")==null?"":(String)data.get("terimal_sn");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");
        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> result = new HashMap<String, String>();
        param.put("staffId",staffId);
        param.put("accept_staff",accept_staff);
        param.put("terminal_state",terminal_state);
        param.put("terimal_sn",terimal_sn);
        param.put("oper_type",oper_type);


        result = dataInstallService.sendTerimalToStaff(param);

        return result;
    }

    /**
     * 终端派单接单
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/update/acceptTerimalToStaff"})
    @ResponseBody
    public Object acceptTerimalToStaff(@RequestBody Map<String, Object> data, ModelMap model,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
      //  String accept_staff = data.get("accept_staff")==null?"":(String)data.get("accept_staff");
        String terminal_state = data.get("terminal_state")==null?"":(String)data.get("terminal_state");
        String terimal_sn = data.get("terimal_sn")==null?"":(String)data.get("terimal_sn");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");

        String zwName = data.get("zwName")==null?"":(String)data.get("zwName");
        String zwNumber = data.get("zwNumber")==null?"":(String)data.get("zwNumber");
        String werks = data.get("werks")==null?"":(String)data.get("werks");
        String newTerminalId = data.get("newTerminalId")==null?"":(String)data.get("newTerminalId");
        String channelCode = data.get("channelCode")==null?"":(String)data.get("channelCode");
        String channelName = data.get("channelName")==null?"":(String)data.get("channelName");

        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> result = new HashMap<String, String>();
        param.put("staffId",staffId);
      //  param.put("accept_staff",accept_staff);
        param.put("terminal_state",terminal_state);
        param.put("terimal_sn",terimal_sn);
        param.put("oper_type",oper_type);

        param.put("zwName",zwName);
        param.put("zwNumber",zwNumber);
        param.put("werks",werks);
        param.put("newTerminalId",newTerminalId);
        param.put("channelCode",channelCode);
        param.put("channelName",channelName);


        result = dataInstallService.acceptTerimalToStaff(param);

        return result;
    }

    /**
     * 终端派单回退
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/update/backTerimalToStaff"})
    @ResponseBody
    public Object backTerimalToStaff(@RequestBody Map<String, Object> data, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        String modify_staff = data.get("modify_staff")==null?"":(String)data.get("modify_staff");
        String terminal_state = data.get("terminal_state")==null?"":(String)data.get("terminal_state");
        String terimal_sn = data.get("terimal_sn")==null?"":(String)data.get("terimal_sn");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");
        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> result = new HashMap<String, String>();
        param.put("staffId",staffId);
        param.put("modify_staff",modify_staff);
        param.put("terminal_state",terminal_state);
        param.put("terimal_sn",terimal_sn);
        param.put("oper_type",oper_type);


        result = dataInstallService.backTerimalToStaff(param);

        return result;
    }


    /**
     * 终端派单退单
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/update/backToDigtal"})
    @ResponseBody
    public Object backToDigtal(@RequestBody Map<String, Object> data, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        //  String accept_staff = data.get("accept_staff")==null?"":(String)data.get("accept_staff");
        String terminal_state = data.get("terminal_state")==null?"":(String)data.get("terminal_state");
        String terimal_sn = data.get("terimal_sn")==null?"":(String)data.get("terimal_sn");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");

        String zwName = data.get("zwName")==null?"":(String)data.get("zwName");
        String zwNumber = data.get("zwNumber")==null?"":(String)data.get("zwNumber");
        String werks = data.get("werks")==null?"":(String)data.get("werks");
        String newTerminalId = data.get("newTerminalId")==null?"":(String)data.get("newTerminalId");
        String channelCode = data.get("channelCode")==null?"":(String)data.get("channelCode");
        String channelName = data.get("channelName")==null?"":(String)data.get("channelName");

        String terminalState =  data.get("terminalState")==null?"":(String)data.get("terminalState");
        Map<String, String> param = new HashMap<String, String>();
        Map<String, String> result = new HashMap<String, String>();
        param.put("staffId",staffId);
        //  param.put("accept_staff",accept_staff);
        param.put("terminal_state",terminal_state);
        param.put("terimal_sn",terimal_sn);
        param.put("oper_type",oper_type);

        param.put("zwName",zwName);
        param.put("zwNumber",zwNumber);
        param.put("werks",werks);
        param.put("newTerminalId",newTerminalId);
        param.put("channelCode",channelCode);
        param.put("channelName",channelName);
        param.put("terminalState",terminalState);


        result = dataInstallService.backToDigtal(param);

        return result;
    }

    /**
     * 终端查询智家工程师
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/query/queryStaff"})
    @ResponseBody
    public Object queryStaff(@RequestBody Map<String, Object> data, ModelMap model,
                               HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String phoneNumber = data.get("phoneNumber")==null?"":(String)data.get("phoneNumber");



        Map<String,String> result = dataInstallService.queryStaff(phoneNumber);

        return result;
    }

    /**
     * 终端操作记录查询
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/query/queryTerminalOperByStaffId"})
    @ResponseBody
    public Object queryTerminalOperByStaffId(@RequestBody Map<String, Object> data, ModelMap model,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryZWTSpeedInfo");
        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");
        String oper_type = data.get("oper_type")==null?"":(String)data.get("oper_type");


        List<Map>  resultList = new ArrayList<Map>();

        resultList = dataInstallService.queryTerminalOperByStaffId(staffId,oper_type);
        return resultList;
    }


    /**
     * 终端查询智家工程师电话
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/query/queryStaffMobile"})
    @ResponseBody
    public Object queryStaffMobile(@RequestBody Map<String, Object> data, ModelMap model,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");



        Map<String,String> result = dataInstallService.queryStaffMobile(staffId);

        return result;
    }


    /**
     * 校验旧光猫合法性 旧光猫必须是出库状态且归属于当前staff人员
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/digtal/power/checkHguSn"})
    @ResponseBody
    public Object checkHguSn(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：checkHguSn");

        String staffId = data.get("staffId")==null?"":(String)data.get("staffId");



        Map<String,String> result = dataInstallService.queryStaffMobile(staffId);

        return result;
    }

    @RequestMapping(value = {"/client/dataInstall/query/queryChangeMachineOrder"})
    @ResponseBody
    public Object queryChangeMachineOrder(@RequestBody Map<String, Object> data, ModelMap model,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");
        String staffId = data.get("staffId").toString();
        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.queryChangeMachineOrder(staffId);

        return result;
    }

    @RequestMapping(value = {"/client/dataInstall/change/sendChangePowerModel"})
    @ResponseBody
    public Object sendChangePowerModel(@RequestBody Map<String, Object> data, ModelMap model,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryDataInstallByOrg");

        Map<String, String> result = new HashMap<String, String>();
        result = dataInstallService.sendChangePowerModel(data);

        return result;
    }

    /**
     * 终端查询智家工程师
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/terimal/query/queryTerimalBySn"})
    @ResponseBody
    public Object queryTerimalBySn(@RequestBody Map<String, Object> data, ModelMap model,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：submitNetOrderReplay");

        String sn = data.get("sn")==null?"":(String)data.get("sn");



        Map<String,String> result = dataInstallService.queryTerimalBySn(sn);

        return result;
    }
}
