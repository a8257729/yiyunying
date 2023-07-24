package com.ztesoft.mobile.v2.controller.common;

import com.lowagie.text.J;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.util.HttpUtil;
import com.ztesoft.mobile.v2.util.JSONUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.jdbc.OracleTypes;
import oracle.sql.NUMBER;
import org.apache.log4j.Logger;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * 公共处理类，通过配置的sql或者存储过程，以及传参等信息，直接返回需要查询的结果
 *
 * @author Dell
 */
@Controller("commonBaseController")
public class CommonBaseController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(CommonBaseController.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * 根据sql查询单个对象的公共方法
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonQueryObjectBySql"})
    public @ResponseBody
    Result commonQueryObjectBySql(@RequestBody Map<String, Object> data, ModelMap model,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法：" + data.get("method"));

        // 先查询配置的查询sql
        String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", data.get("method"));
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
        Map<String, Object> param = new HashMap<String, Object>();
        param = (Map<String, Object>) data.get("param");
//		logger.info("查询参数：" + param.toString());
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        String paramCondition = (String) dataInfo.get("wherePatternStr");
        String sqlParam = (String) dataInfo.get("sql");

        Result result = getCommonService().commonQueryObjectBySql(sqlParam, param, paramCondition);
//		logger.info("返回结果：" + result.getResultData().toString());
        return result;
    }

    /**
     * 获取数据源
     */
    private HuNanDAO getWorkOrderDAO() {
        String daoName = HuNanDAOImpl.class.getName();
        return (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
    }

    /**
     * 根据sql查询列表
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonQueryListBySql"})
    public @ResponseBody
    Result commonQueryListBySql(@RequestBody Map<String, Object> data, ModelMap model,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法：" + data.get("method"));

        // 先查询配置的查询sql
        String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", data.get("method"));
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
        Map<String, Object> param = new HashMap<String, Object>();
        param = (Map<String, Object>) data.get("param");
//		logger.info("查询参数：" + param.toString());
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        String paramCondition = (String) dataInfo.get("wherePatternStr");
        String sqlParam = (String) dataInfo.get("sql");
        Result result = getCommonService().commonQueryListBySql(sqlParam, param, paramCondition);
//		logger.info("返回结果：" + result.getResultData().toString());
        return result;
    }

    /**
     * 根据存储过程查询单个对象
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonQueryObjectByProcedure"})
    public @ResponseBody
    Result commonQueryObjectByProcedure(@RequestBody Map<String, Object> data, ModelMap model,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法：" + data.get("method"));

        // 先查询配置的查询sql
        String sql = "select t.in_param_length as inLength,t.query_sql as sql,t.out_param as outParam,t.out_param_type as outParamType from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", data.get("method"));
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
        Map<String, Object> param = new HashMap<String, Object>();
        param = (Map<String, Object>) data.get("param");
//		logger.info("查询参数：" + param.toString());
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        String outParam = (String) dataInfo.get("outParam");
//		logger.info("outParam:" + outParam);
        String[] outParamArray = outParam.split(",");
        String outParamType = (String) dataInfo.get("outParamType");
        String sqlParam = (String) dataInfo.get("sql");
//		logger.info("调用的存储过程为：" + sqlParam);
        BigDecimal intLength = (BigDecimal) dataInfo.get("inLength");
        List<Object> list = new ArrayList<Object>();
        Set<String> keySet = param.keySet();
        // 入参值
        for (String key : keySet) {
            list.add(param.get(key));
        }
        // 出参
        if (StringUtil.isNull(outParamType)) {
            // 如果没有配置出参类型，默认全部为varchar
            for (int i = 0; i < outParamArray.length; i++) {
                list.add("");
            }
        } else {
            String[] outParamTypeArray = outParamType.split(",");
            logger.debug("出参的个数：" + outParamTypeArray.length);
            for (int i = 0; i < outParamTypeArray.length; i++) {
                if ("varchar".equals(outParamTypeArray[i].toLowerCase())) {
                    list.add("");
                } else if ("number".equals(outParamTypeArray[i].toLowerCase())) {
                    list.add(new BigDecimal(0));
                }
            }
        }
        Result result = getCommonService().commonQueryObjectByProcedure(sqlParam, list, intLength.intValue(),
                outParamArray);
//		logger.info("返回结果：" + result.getResultData().toString());
        return result;
    }

    @RequestMapping(value = {"/client/common/commonInsertObjectBySql"})
    public @ResponseBody
    Result commonInsertObjectBySql(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法：" + data.get("method"));

        // 先查询配置的查询sql
        String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", data.get("method"));
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
        Map<String, Object> param = new HashMap<String, Object>();
        param = (Map<String, Object>) data.get("param");
		/*if (param != null) {
			logger.info("查询参数：" + param.toString());
		}*/
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        // String paramCondition = (String) dataInfo.get("wherePatternStr");
        String sqlParam = (String) dataInfo.get("sql");
        Result result = getCommonService().commonInsertObjectBySql(sqlParam, param);
//		logger.info("返回结果：" + result.getResultMsg());
        return result;
    }

    public static void main(String[] args) {

    }

    @RequestMapping(value = "/client/testAjaxJsonp")
    public void testAjaxJsonp(@RequestParam String callback, HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.info("请求参数：" + request.getParameter("id"));
            Result result = Result.buildSuccess();
            JSONObject jsonResult = JSONObject.fromObject(result);
            response.getWriter().print(callback + "(" + jsonResult.toString() + ")");
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/client/testAjaxJson")
    public void testAjaxJson(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            Result result = Result.buildSuccess();
            JSONObject jsonResult = JSONObject.fromObject(result);
            response.getWriter().print(jsonResult.toString());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 微信跨域调用后台插入数据的公共方法
     *
     * @param callback 回调函数
     * @param request  注意前台传递的参数param如果是json对象要用JSON.stringify转化，且param中的属性要按sql中？对应的参数顺序
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonInsertObjectForWechat"})
    public void commonInsertObjectForWechat(@RequestParam String callback,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getParameter("method");
//		logger.info("调用方法：" + method);

        String paramStr = request.getParameter("param");
        Map param = JSONObject.fromObject(paramStr);

        // 先查询配置的查询sql
        String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", method);
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
//		if (param != null) {
//			logger.info("查询参数：" + param.toString());
//		}
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        // String paramCondition = (String) dataInfo.get("wherePatternStr");
        String sqlParam = (String) dataInfo.get("sql");
        Result result = getCommonService().commonInsertObjectBySql(sqlParam, param);
//		logger.info("返回结果：" + result.getResultMsg());
        JSONObject jsonResult = JSONObject.fromObject(result);
        response.getWriter().print(callback + "(" + jsonResult.toString() + ")");
        response.flushBuffer();
    }

    @RequestMapping(value = {"/client/Newcommon/commonQueryObjectBySqlForWechat"},produces = "application/json;charset=UTF-8")
    public @ResponseBody String newCommonQueryObjectBySqlForWechat(@RequestBody String params,HttpServletResponse response) throws Exception {
        try {
            JSONObject jsonObject = JSONObject.fromObject(params);

            String method = jsonObject.getString("method");
            // 先查询配置的查询sql
            String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("method", method);
            String wherePatternStr = "method:method";
            Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);
            // 再根据查询的sql查询前台需要的结果
            String paramStr = jsonObject.getString("param");
            Map param = JSONObject.fromObject(paramStr);
//		logger.info("查询参数：" + param.toString());
            HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
            String paramCondition = (String) dataInfo.get("wherePatternStr");
            String sqlParam = (String) dataInfo.get("sql");
            Result result = getCommonService().commonQueryObjectBySql(sqlParam, param, paramCondition);
//		logger.info("返回结果：" + result.getResultData().toString());
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

    /**
     * 微信调用后台查询数据公共方法
     *
     * @param
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonQueryObjectBySqlForWechat"})
    public void commonQueryObjectBySqlForWechat(@RequestParam String callback,
                                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        String method = request.getParameter("method");
//		logger.info("调用方法：" + method);

        // 先查询配置的查询sql
        String sql = "select t.sql_condition as wherePatternStr,t.query_sql as sql from sql_config_info t where t.method = ?";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("method", method);
        String wherePatternStr = "method:method";
        Result sqlResult = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        // 再根据查询的sql查询前台需要的结果
        String paramStr = request.getParameter("param");
        Map param = JSONObject.fromObject(paramStr);
//		logger.info("查询参数：" + param.toString());
        HashMap<String, Object> dataInfo = (HashMap<String, Object>) sqlResult.getResultData().get("data_info");
        String paramCondition = (String) dataInfo.get("wherePatternStr");
        String sqlParam = (String) dataInfo.get("sql");
        Result result = getCommonService().commonQueryObjectBySql(sqlParam, param, paramCondition);
//		logger.info("返回结果：" + result.getResultData().toString());
        JSONObject jsonResult = JSONObject.fromObject(result);
        response.getWriter().print(callback + "(" + jsonResult.toString() + ")");
        response.flushBuffer();
    }





    private JdbcTemplate jdbcTemplate = null;

    {
        Hdf hdf = new Hdf();
        jdbcTemplate = new JdbcTemplate(hdf.getDataSource());
    }

    @RequestMapping(value = {"/client/common/autoGuangshuai"})
    public Map autoGuangShuai(@RequestBody Map<String, Object> data, ModelMap model,
                              HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.info("调用方法:autoGuangShuai");

        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String orderId = (String) param1.get("orderId");
        String sql = "SELECT a.userName,a.acceptPower,a.standardValue FROM (select t.username as userName,t.accept_power as acceptPower,t.standard_value as standardValue" +
                "  from guangshuai_user_state t where t.order_id = ? order by t.insert_time desc) a where rownum = 1";


        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{orderId});
        if(maps.size()>0){
            Object acceptpower = maps.get(0).get("ACCEPTPOWER");
            maps.get(0).remove("ACCEPTPOWER");
            maps.get(0).put("acceptPower",acceptpower);
            logger.info("autoGuangShuai 返回结果:"+ JSONUtils.toJSONString(maps));
            return maps.get(0);
        }
        logger.info("autoGuangShuai 返回结果: null");
        return null;
    }

    @RequestMapping(value = {"/client/common/autoSpeed"})
    public Object autoSpeed(@RequestBody Map<String, Object> data, ModelMap model,
                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法:autoSpeed");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String orderId = (String) param1.get("orderId");
        String userName = null;

        String sql1 = "SELECT t.attr_value FROM om_so_attr T where t.service_order_id = ? and t.attr_code = 'itellin_loginname'";

        Map<String, Object> attr = jdbcTemplate.queryForMap(sql1, new Object[]{orderId});
        userName = (String)attr.get("attr_value");
        String sql = "select * from active_speed_result where user_name= ? and order_id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{userName,orderId});
        if (maps.size() > 0) {
            Map<String, Object> resultData = maps.get(0);
            String speedResult = (String) resultData.get("speed_result");
            if (speedResult.equals("0")) {
                Result result = this.commonQueryObjectBySql(data, model, request, response);
                //String url = "http://localhost:7001/MOBILE/api/client/common/commonQueryObjectBySql";
                //RestTemplate restTemplate = new RestTemplate();
                //Map map = restTemplate.postForObject(url, data, Map.class);
                logger.info("autoSpeed 返回结果:"+ JSONUtils.toJSONString(result));
                return result;
            }
            return null;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.101.175:11004/rms/speedMeasurementByLightCat1";
            Map param = new HashMap();
            param.put("dNumber", userName);
            Map<String, Object> resMap = restTemplate.postForObject(url, param, Map.class);
            String speed = (String) resMap.get("setParameterValuesByItemNameResponse");

            sql = "delete active_speed_result where user_name = ? and order_id = ?";
            int update = jdbcTemplate.update(sql, new Object[]{userName,orderId});
            logger.info("active_speed_result删除結果:" + update);
            //insert

            sql = "insert into active_speed_result(user_name,speed_result,order_id) values(?,?,?)";
            update = jdbcTemplate.update(sql, new Object[]{userName, speed,orderId});
            logger.info("active_speed_result插入結果:" + update);
            if (speed.equals("0")) {
                Result result = this.commonQueryObjectBySql(data, model, request, response);
                // url1 = "http://localhost:7001/MOBILE/api/client/common/commonQueryObjectBySql";
                //RestTemplate restTemplate1 = new RestTemplate();
                //Map map = restTemplate1.postForObject(url1, data, Map.class);
                logger.info("autoSpeed 返回结果:"+ JSONUtils.toJSONString(result));
                return result;
            }
            return null;
        }
    }

    @RequestMapping(value = {"/client/common/signed"})
    @ResponseBody
    public Result  signed(@RequestBody Map<String, Object> data, ModelMap model,
                          HttpServletRequest request, HttpServletResponse response) throws Exception{

        String sql = "select count(1) as count from ARRIVE_SIGN where work_order_id = ? ";
        Map<String, Object> paramMap = new HashMap<String, Object>();

        paramMap.put("work_order_id", data.get("WorkOrderID"));
        String wherePatternStr = "work_order_id:work_order_id";
        Result result = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);

        return result;
    }

    @RequestMapping(value = {"/client/common/autoCesuQuery"})
    @ResponseBody
    public Result autoCesuQuery(@RequestBody Map<String, Object> data, ModelMap model,
                                HttpServletRequest request, HttpServletResponse response) throws Exception{
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String sql = "select t.username as userName,to_char(t.createdate,'yyyy-mm-dd hh24:mi:ss') as test_date,t.netspeed as netspeed,t.maxnetspeed as maxnetspeed,decode(t.isreachmark,'1','达标','不达标') as isreachmark" +
                "  from usernet_cesu t" +
                " where t.username = ? order by t.createdate desc ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", param1.get("username"));
        String wherePatternStr = "username:username";
        Result result = getCommonService().commonQueryObjectBySql(sql, paramMap, wherePatternStr);
        return result;
    }

    @RequestMapping(value = {"/client/common/autoCesuObjectQuery"})
    @ResponseBody
    public Map autoCesuObjectQuery(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = (String)data.get("username");
        String sql = "select * from active_speed_result where user_name= ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{username});
        Map map = new HashMap();
        map.put("count",maps.size());
        return map;
    }

    @RequestMapping(value = {"/client/common/autoSpeedUsername"})
    public Object autoSpeedUsername(@RequestBody Map<String, Object> data, ModelMap model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String userName = (String) param1.get("username");

        String orderId = (String) param1.get("orderId");
        String sql = "select * from active_speed_result where user_name= ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{userName});

        if (maps.size() > 0) {
            Map<String, Object> resultData = maps.get(0);
            String speedResult = (String) resultData.get("speed_result");
            if (speedResult.equals("0")) {
                Result result = this.autoCesuQuery(data, model, request, response);
                // String url = "http://localhost:7001/MOBILE/api/client/common/autoCesuQuery";
                //RestTemplate restTemplate = new RestTemplate();
                //Map map = restTemplate.postForObject(url, data, Map.class);
                return result;
            }
            return null;
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://192.168.101.175:11004/rms/speedMeasurementByLightCat1";
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setConnectTimeout(2000);
            httpRequestFactory.setReadTimeout(2000);
            restTemplate.setRequestFactory(httpRequestFactory);

            Map param = new HashMap();
            param.put("dNumber", userName);
            Map<String, Object> resMap = restTemplate.postForObject(url, param, Map.class);
            String speed = (String) resMap.get("setParameterValuesByItemNameResponse");



            sql = "delete active_speed_result where user_name = ?";
            int update = jdbcTemplate.update(sql, new Object[]{userName});
            logger.info("active_speed_result删除結果:" + update);
            //insert

            sql = "insert into active_speed_result(user_name,speed_result,ORDER_ID) values(?,?,?)";

            update = jdbcTemplate.update(sql, new Object[]{userName, speed,orderId});
            logger.info("active_speed_result插入結果:" + update);
            if (speed.equals("0")) {
                Result result = this.autoCesuQuery(data, model, request, response);
                // String url = "http://localhost:7001/MOBILE/api/client/common/autoCesuQuery";
                //RestTemplate restTemplate = new RestTemplate();
                //Map map = restTemplate.postForObject(url, data, Map.class);
                return result;
            }
            return null;
        }
    }

    @RequestMapping(value = {"/client/common/autolightWane"})
    @ResponseBody
    public Map autolightWane(@RequestBody Map<String, Object> data, ModelMap model,
                             HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：autolightWane");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String userName = (String) param1.get("username");
        String staffId = (String) param1.get("staffId");
        String workOrderId = (String) param1.get("orderId");

        String sql1 ="insert into guangshuai_user_state(USERNAME,STAFFID,ACCEPT_POWER,STANDARD_VALUE,INSERT_TIME,IS_FROM_INTF,ORDER_ID) values(?,?,?,?,sysdate,?,?) ";
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.101.175:11004/rms/lstPortPerf";
        Map param = new HashMap();
        param.put("dNumber", userName);
        Map result = restTemplate.postForObject(url, param, Map.class);
        String onurxpower =(String) result.get("onurxpower");
        logger.info("======");

        logger.info(onurxpower);
        if(onurxpower!=null&&!onurxpower.equals("终端暂不支持该账号")){
            String sql2 = "SELECT t.base_order_id as ORDERID FROM wo_work_order T where t.id = ?";
            Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql2,new Object[]{workOrderId});
            BigDecimal orderId = (BigDecimal)stringObjectMap.get("ORDERID");
            String substring = onurxpower.substring(0, onurxpower.length() - 2);
            jdbcTemplate.update(sql1,new Object[]{userName,staffId,substring+"dB","-27dB",1,orderId});
        }

        logger.info("调用方法：autolightWane 返回:"+JSONUtils.toJSONString(result));
        return result;
    }



    @RequestMapping(value = {"/client/common/autoOpen"})
    @ResponseBody
    public Map autoOpen(@RequestBody Map<String, Object> data, ModelMap model,
                        HttpServletRequest request, HttpServletResponse response){
        logger.info("调用方法：autoOpen");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        final String orderId = (String) param1.get("orderId");
        String sql = "call f_auto_diagnose(?,?,?)";

        CallableStatementA callableStatementA = new CallableStatementA(orderId);
        Map<String, String> execute = jdbcTemplate.execute(sql, callableStatementA);
        logger.info("调用方法：autoOpen 返回结果:"+JSONUtils.toJSONString(execute)+",参数:"+orderId);
        return execute;
    }

    @RequestMapping(value = {"/client/common/queryOrgList"})
    @ResponseBody
    public Object queryOrgList(@RequestBody Map<String, Object> data, ModelMap model,
                               HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryOrgList");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String staffId = (String) param1.get("staffId");
        String sql = "SELECT distinct uo.org_id,uo.org_name FROM uos_staff T left join uos_job_staff ujs on ujs.staff_id = t.staff_id left join uos_job uj on uj.job_id = ujs.job_id left join uos_org uo on uo.org_id = uj.org_id where t.state = '1'  and ujs.state = '1'  and uj.state = '1'  and uo.state = '1'  and t.staff_id = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{staffId});
        return maps;
    }

    @RequestMapping(value = {"/client/common/queryOrgDetailInfo"})
    @ResponseBody
    public Object queryOrgDetailInfo(@RequestBody Map<String, Object> data, ModelMap model,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryOrgDetailInfo");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        final String in_date = (String) param1.get("in_date");
        String in_date_type = (String) param1.get("in_date_type");
        String staffId = (String) param1.get("staffId");
        String orgId = (String) param1.get("orgId");
        String sql = "{?=call F_WG_RANK_QUOTA_QS(?,?,?,?)}";
        CallableStatementOrgDetailInfo callableStatementOrgDetailInfo = new CallableStatementOrgDetailInfo(in_date,in_date_type,Integer.parseInt(orgId),Integer.parseInt(staffId));
        Map<String, String> execute = jdbcTemplate.execute(sql, callableStatementOrgDetailInfo);
        logger.info("调用方法：queryOrgDetailInfo 返回结果:"+JSONUtils.toJSONString(execute)+",参数:"+param1);
        return execute;
    }

    @RequestMapping(value = {"/client/common/queryProjectName"})
    @ResponseBody
    public Object queryProjectName(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryProjectName");
        String account = (String) data.get("account");
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://192.168.101.15:11003/bdAccount/queryProjectName";
        Map param = new HashMap();
        param.put("dNumber", account);
        Map resMap = restTemplate.postForObject(url, param, Map.class);
        Map body = (Map)resMap.get("body");
        Map newMap = new HashMap();
        if(body==null){
            newMap.put("projectName","");
            return newMap;
        }
        String projectName = (String)body.get("projectName");
        logger.info("调用方法：queryProjectName:"+projectName);
        if(projectName==null){
            newMap.put("projectName","");
        }else{
            newMap.put("projectName",projectName);
        }
        return newMap;
    }

    @RequestMapping(value = {"/client/common/queryOrgIdIsCs"})
    @ResponseBody
    public Object queryOrgIdIsCs(@RequestBody Map<String, Object> data, ModelMap model,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryOrgIdIsCs");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        final String orderId = (String) param1.get("orgId");
        String sql = "SELECT T.* FROM REPORT_WG_RANK_ORG T WHERE T.ORG_ID = ?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{orderId});
        logger.info("调用方法：queryOrgIdIsCs 返回结果:"+maps+",参数:"+orderId);
        return maps;
    }


    @RequestMapping(value = {"/client/common/queryFeedBackList"})
    @ResponseBody
    public Object queryFeedBackList(@RequestBody Map<String, Object> data, ModelMap model,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("调用方法：queryFeedBackList");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        String fieldEq = (String) param1.get("fieldEq");
        String sql = "select content from feed_back_list where fieldEq = ? ";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{fieldEq});
        logger.info("调用方法：queryFeedBackList 返回结果:"+maps+",参数:"+fieldEq);
        return maps;
    }


    @RequestMapping(value = {"/client/common/queryBroadband"})
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
                logger.info("调用方法：queryBroadband 返回结果:"+map+",参数:"+account);
                return map;
            }
        }
        logger.info("调用方法：queryBroadband 返回结果:"+null+",参数:"+account);
        return null;
    }

    @RequestMapping(value = {"/client/common/queryQrcodeInstallArea"})
    @ResponseBody
    public Object queryQrcodeInstallArea(@RequestBody Map<String, Object> data,ModelMap model,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception{
        String sql = " SELECT count(1) FROM om_order T ,uos_area ua, om_force_pon_area f_pon where ua.area_id = t.area_id and ua.acronym = f_pon.acronym and  t.id = ?";
        String orderId = (String) data.get("orderId");
        Integer integer = jdbcTemplate.queryForObject(sql, new Object[]{orderId}, Integer.class);
        return integer;
    }

    @RequestMapping(value = {"/client/common/iptvValidated"})
    @ResponseBody
    public Object iptvValidated(@RequestBody Map<String, Object> data,ModelMap model,
                                HttpServletRequest request, HttpServletResponse response){
        Map param = (Map)data.get("param");
        String account = (String) param.get("account");
        String orderId = (String) param.get("orderId");

        RestTemplate rest = new RestTemplate();
        Map resp = rest.getForObject("http://192.168.101.175:20812/gk/iptv/user2networkinfo?loginAccount=" + account, Map.class);
        Map map=(Map)resp.get("data");
        Object[] obj = new Object[10];
        obj[0] = orderId;

        obj[1] = resp.get("result");
        obj[2] =resp.get("description");
        if(map!=null){
            obj[3] =map.get("loginAccount");
            obj[4] =map.get("last1_cpe_mac");
            obj[5] =map.get("last1_loopback_ip");
            obj[6] =map.get("last1_ip_type");
            obj[7] =map.get("changetime");
            obj[8] =map.get("last2_ip_type");
            obj[9] =map.get("last2_ip_type_changetime");
        }else{
            obj[3] =account;
            obj[4] ="null";
            obj[5] ="null";
            obj[6] ="null";
            obj[7] ="null";
            obj[8] ="null";
            obj[9] ="null";

        }
        String sqlParam ="";
        sqlParam = "insert into inf_iptv_type_log(ORDER_ID,RESULT_STATE,REMARK,LOGINACCOUNT,LAST1_CPE_MAC,LAST1_LOOPBACK_IP,LAST1_IP_TYPE,CHANGETIME,LAST2_IP_TYPE,LAST2_IP_TYPE_CHG,INSERT_DATE) values(?,?,?,?,?,?,?,?,?,?,sysdate)";
        try {
            String sql = "delete inf_iptv_type_log where order_id = ?";
            jdbcTemplate.update(sql,new Object[]{obj[0]});
            int update = jdbcTemplate.update(sqlParam, obj);
            return update;
        }catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }





    @RequestMapping(value = {"/client/common/queryIsUpdatePon"})
    @ResponseBody
    public Object queryIsUpdatePon(@RequestBody Map<String, Object> data, ModelMap model,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception{
        logger.info("调用方法：queryIsUpdatePon");
        Map<String, Object> param1 = (Map<String, Object>) data.get("param");
        final String portId = (String) param1.get("portId");
        String obdId = (String) param1.get("obdId");

        String sql = "{?=call SF_if_updatepon_flag(?)}";
        CallableStatementQueryUpdatePon callableStatementOrgDetailInfo = new CallableStatementQueryUpdatePon(portId);
        Map<String, String> execute = jdbcTemplate.execute(sql, callableStatementOrgDetailInfo);
        logger.info("调用方法：queryIsUpdatePon 返回结果:"+JSONUtils.toJSONString(execute)+",参数:"+param1);
        return execute;
    }
    //FTTR的只有175通，需要调用175
    //一键验收
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/oneKeyAcceptance"}, method = RequestMethod.POST)
    public 	 String fttrOneKeyAcceptance(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn")==null?"":jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        //查看数据库中是否有主网关
        String mainGateWay =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getFttrYsMainInfo",param);
        JSONObject res = JSONObject.fromObject(mainGateWay);
        //存在主网关，再查询从网关
        if(StringUtils.hasText(res.getString("data"))){
            String subGateWay =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getFttrYsSubInfo",param);
            JSONObject resSub = JSONObject.fromObject(subGateWay);
            JSONArray subArray = resSub.getJSONArray("data");
            JSONObject data = new JSONObject();
            data.put("ap",subArray);
            data.put("gateWay",res.getJSONObject("data"));
            result.put("code","200");
            result.put("data",data);
            return result.toString();
        }else {
            //未查询到的情况
            if(!StringUtils.hasText(sn)){
                result.put("code","450");
                return result.toString();
            }
            String getoneKeyAcceptance =  HttpUtil.sendPost("http://192.168.101.175:8782/fttr/main/oneKeyAcceptance",jsonParam);
            JSONObject getKeyAcceptanceBackInfo = JSONObject.fromObject(getoneKeyAcceptance);
            //入库
            JSONObject backData = getKeyAcceptanceBackInfo.getJSONObject("data");
            JSONObject gateWay = backData.getJSONObject("gateWay");
            if(StringUtils.hasText(gateWay.toString())){
                gateWay.put("sn",sn);
                gateWay.put("orderId",orderId);
                gateWay.put("staffId",staffId);
                HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/insetOneKeyAcceptance",gateWay.toString());
            }
            JSONArray ap = backData.getJSONArray("ap");
            if(StringUtils.hasText(ap.toString())&&ap.size()!=0){
                JSONObject insertSub = new JSONObject();
                insertSub.put("sn",sn);
                insertSub.put("orderId",orderId);
                insertSub.put("staffId",staffId);
                insertSub.put("result",ap);
                HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/insetOneKeyAcceptanceSub",insertSub.toString());
            }
            return getoneKeyAcceptance;

        }

    }



    //校验提示
    @RequestMapping(value = {"/client/fttr/checkAcceptanceState"})
    @ResponseBody
    public String checkAcceptanceState(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        final String sn = jsonObject.getString("sn");
        final String orderId = jsonObject.getString("orderId");
        final String staffId = jsonObject.getString("staffId");
        

        String res = jdbcTemplate.execute(new CallableStatementCreator() {

            public CallableStatement createCallableStatement(Connection conn) throws SQLException {
                String sql="{?=call hnlt_gk.get_if_fttr_up_desc(?,?,?)}";
                CallableStatement prepareCall = conn.prepareCall(sql);

                prepareCall.registerOutParameter(1, OracleTypes.VARCHAR);
                prepareCall.setString(2, orderId);
                prepareCall.setString(3, sn);
                prepareCall.setString(4, staffId);
                return prepareCall;
            }
        }, new CallableStatementCallback<String>() {

            public String doInCallableStatement(CallableStatement call) throws SQLException, DataAccessException {
                call.execute();
                return call.getString(1);
            }
        });
        result.put("res",res);
        return result.toString();

    }


    //重新验收
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/resetCheckFttr"}, method = RequestMethod.POST)
    public 	String resetCheckFttr(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        //查询
        String getoneKeyAcceptance =  HttpUtil.sendPost("http://192.168.101.175:8782/fttr/main/oneKeyAcceptance",jsonParam);
        JSONObject getKeyAcceptanceBackInfo = JSONObject.fromObject(getoneKeyAcceptance);
        //入库
        JSONObject backData = getKeyAcceptanceBackInfo.getJSONObject("data");
        JSONObject gateWay = backData.getJSONObject("gateWay");
        if(StringUtils.hasText(gateWay.toString())){
            gateWay.put("sn",sn);
            gateWay.put("orderId",orderId);
            gateWay.put("staffId",staffId);
            HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/updateFttrYsMainInfo",gateWay.toString());
        }
        JSONArray ap = backData.getJSONArray("ap");
        if(StringUtils.hasText(ap.toString())&&ap.size()!=0){
            JSONObject insertSub = new JSONObject();
            insertSub.put("sn",sn);
            insertSub.put("orderId",orderId);
            insertSub.put("staffId",staffId);
            insertSub.put("result",ap);
            HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/deleteOneKeyAcceptanceSub",jsonParam);
            HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/insetOneKeyAcceptanceSub",insertSub.toString());
        }
        return getoneKeyAcceptance;

    }
    //测速id
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSpeeTaskIdInDb"}, method = RequestMethod.POST)
    public 	String getSpeeTaskIdInDb(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        //查询
        String getoneKeyAcceptance =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getSpeeTaskId",param
        );
        return getoneKeyAcceptance;

    }


    //获取sn
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSnByDb"}, method = RequestMethod.POST)
    public 	String getSnByDb(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "&orderId=" + orderId + "&staffId=" + staffId;
        //查询
        String getoneKeyAcceptance =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getSnByDb",param
        );
        return getoneKeyAcceptance;

    }

    //删除
//    @ResponseBody
//    @RequestMapping(value = {"/client/fttr/deleteAllInfo"}, method = RequestMethod.POST)
//    public 	String deleteAllInfo(@RequestBody  String jsonParam) throws Exception{
//        JSONObject result = new JSONObject();
//        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
//        String orderId = jsonObject.getString("orderId");
//        String staffId = jsonObject.getString("staffId");
//        String param = "&orderId=" + orderId + "&staffId=" + staffId;
//        //查询
//        System.out.println("111");
//        String getoneKeyAcceptance =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/deleteAllInfo",param
//        );
//        return getoneKeyAcceptance;
//
//    }

    //检查是否能够再次获取测速id
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getTaskPermission"}, method = RequestMethod.POST)
    public 	String getTaskPermission(@RequestBody  String jsonParam) throws Exception{
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        //查询
        String getoneKeyAcceptance =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getTaskPermission",param
        );
        return getoneKeyAcceptance;

    }


    //通过宽带账号获取SN
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSnByPppoe"}, method = RequestMethod.POST)
    public 	String getSnByPppoe(@RequestBody  String jsonParam) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String pppoe = jsonObject.getString("pppoe");
        String param = "pppoe=" + pppoe;
        //查询
        String getSnByPppoe =  HttpUtil.sendGet("http://192.168.101.175:8782/fttr/main/getSnByPppoe",param);
        return getSnByPppoe;


    }

    //获取SN
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSnByAlgorithm"}, method = RequestMethod.POST)
    public 	String getSnByPppoegetSnByAlgorithm(@RequestBody  String jsonParam) throws Exception{
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String orderId = jsonObject.getString("workOrderId");
        String staffId = jsonObject.getString("staffId");
        String param = "workOrderId=" + orderId + "&staffId=" + staffId;
        //查询
        String getSnByPppoe =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getSnByAlgorithm",param);
        return getSnByPppoe;
    }
    //测速id
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/saveTaskId"}, method = RequestMethod.POST)
    public 	String saveTaskId(@RequestBody  String jsonParam) throws Exception{
        //查询
        String saveTaskId =  HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/insertTaskId",jsonParam);
        return saveTaskId;

    }
    //测速id
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSpeedTestTaskId"}, method = RequestMethod.POST)
    public 	 String getFttrSpeedTestTaskResult(@RequestBody  String jsonParam){
        String result =  HttpUtil.sendPost("http://192.168.101.175:8782/fttr/main/getSpeedTestTaskId",jsonParam);
//        String result =  HttpUtil.sendPost("http://192.168.101.175:8082/fttr/main/getSpeedTestTaskResult",jsonParam);
        return result;
    }

    //fttr测速
    @ResponseBody
    @RequestMapping(value = {"/client/fttr/getSpeedTestTaskResult"}, method = RequestMethod.POST)
    public 	 String getSpeedTestTaskResult(@RequestBody  String jsonParam){
        String result =  HttpUtil.sendPost("http://192.168.101.175:8782/fttr/main/getSpeedTestTaskResult",jsonParam);
//        String result =  HttpUtil.sendPost("http://192.168.101.175:8082/fttr/main/getSpeedTestTaskResult",jsonParam);
        return result;
    }



    @RequestMapping  (value = "/client/fttr/getFttrYsSpeedInfo",method = RequestMethod.POST)
    public @ResponseBody
    String getFttrYsSpeedInfo(@RequestBody String jsonParam)throws Exception {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        String res =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getFttrYsSpeedInfo",param);
        JSONObject getSpeedInfo = JSONObject.fromObject(res);
        if(StringUtils.hasText(getSpeedInfo.getString("data"))) {
            return getSpeedInfo.toString();
        }
        else {return null;}
    }

    @RequestMapping  (value = "/client/fttr/getFttrYsSpeedFlag",method = RequestMethod.POST)
    public @ResponseBody
    String getFttrYsSpeedFlag(@RequestBody String jsonParam)throws Exception {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn")==null?"":jsonObject.getString("sn");
        String orderId = jsonObject.getString("orderId");
        String staffId = jsonObject.getString("staffId");
        String param = "sn=" + sn + "&orderId=" + orderId + "&staffId=" + staffId;
        String res =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/getFttrYsSpeedFlag",param);
        JSONObject getSpeedInfo = JSONObject.fromObject(res);
        if(StringUtils.hasText(getSpeedInfo.getString("data"))) {
            return getSpeedInfo.toString();
        }
        else {return null;}
    }

    @ResponseBody
    @RequestMapping(value = {"/client/fttr/deleteFttrSpeed"}, method = RequestMethod.POST)
    public  String deleteFttrSpeed(@RequestBody  String jsonParam){
        String result =  HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/deleteFttrSpeed",jsonParam);
//        String result =  HttpUtil.sendPost("http://192.168.101.175:8082/fttr/main/getSpeedTestTaskResult",jsonParam);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = {"/client/fttr/insertFttrSpeed"}, method = RequestMethod.POST)
    public  String insertFttrSpeed(@RequestBody  String jsonParam){
        String result =  HttpUtil.sendPost("http://192.168.101.175:8782/fttrInDb/insertFttrSpeed",jsonParam);
//        String result =  HttpUtil.sendPost("http://192.168.101.175:8082/fttr/main/getSpeedTestTaskResult",jsonParam);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = {"/client/fttr/snAddAlgorithm"}, method = RequestMethod.POST)
    public  String SnAddAlgorithm(@RequestBody  String jsonParam){
        JSONObject jsonObject = JSONObject.fromObject(jsonParam);
        String sn = jsonObject.getString("sn");
        String staffId = jsonObject.getString("staffId");
        String kdaccountAc = jsonObject.getString("kdaccountAc");
        String param = "sn=" + sn  + "&staffId=" + staffId + "&kdaccountAc=" + kdaccountAc;
        String result =  HttpUtil.sendGet("http://192.168.101.175:8782/fttrInDb/snAddAlgorithm",param);
//        String result =  HttpUtil.sendPost("http://192.168.101.175:8082/fttr/main/getSpeedTestTaskResult",jsonParam);
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/client/Boc/getXiaoQuInfo", method = RequestMethod.POST)
    public String getXiaoQuInfo(@RequestBody String jsonParam) {
        // 	小区基本信息查询
        logger.info("getVillageInfo param:"+jsonParam);

        // 将获取的json数据封装一层，然后在给返回
        // String result="{\"resCode\":\"1\",\"resResult\":\"调用成功\",\"data\":[{\"villageName\":\"小区名称1111111111111111111111111111111twqwtqwtqwtqwtqtwqwtqtw1\",\"stockCount\":\"100\",\"silenceCount\":\"10\",\"lessTenCount\":\"20\",\"greaterTenCount\":\"70\",\"singleBroadbandCount\":\"10\",\"weakLightChange\":\"1%\",\"faultChange\":\"1%\"},{\"villageName\":\"小区名称2\",\"stockCount\":\"200\",\"silenceCount\":\"10\",\"lessTenCount\":\"40\",\"greaterTenCount\":\"150\",\"singleBroadbandCount\":\"10\",\"weakLightChange\":\"-1%\",\"faultChange\":\"-1%\"}]}";
        String result =  HttpUtil.sendPostBoc("http://192.168.101.175:8085/Boc/getXiaoQuInfo", jsonParam);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/client/Boc/getStaffPath", method = RequestMethod.POST)
    public String getStaffPath(@RequestBody String jsonParam) {
        // 用户登网轨迹
        logger.info("getUserTrailInfo param:"+jsonParam);

        // 将获取的json数据封装一层，然后在给返回
        // String result="{\"resCode\":\"1\",\"resResult\":\"调用成功\",\"data\":{\"oltIp\":\"10.1.5.26\",\"oltName\":\"长沙香樟路OLT\",\"ponCode\":\"10\",\"ponType\":\"20\",\"obdCode\":\"70\",\"onuSnCode\":\"54874512555555666sn\",\"onuModel\":\"321\",\"onuFactory\":\"华为\",\"onuName\":\"华为\",\"lanType\":\"GE\",\"userName\":\"刘*\",\"userAddress\":\"香樟路\",\"terminalSituation\":\"\",\"lightWaneSituation\":\"\",\"threeDayDropLine\":\"3\",\"lastOnlineTime\":\"2021-12-21\",\"signContractTime\":\"2021-12-21\",\"bandwidth\":\"100M\",\"isSingleWidth\":\"否\",\"packageInformation\":\"\"}}";
        String result =  HttpUtil.sendPostBoc("http://192.168.101.175:8085/Boc/getStaffPath", jsonParam);
        logger.info("getUserTrailInfo:"+result);

        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/client/Boc/getPonInfo", method = RequestMethod.POST)
    public String getPonInfo(@RequestBody String jsonParam) {
        // 小区pon口用户查询
        logger.info("getPonInfo param:"+jsonParam);

        // 将获取的json数据封装一层，然后在给返回
        //  String result="{\"code\":\"200\",\"msg\":\"\",\"data\":{\"ponInfoEntityList\":[{\"communityName\":\"小区名称1\",\"oltIp\":\"10.1.5.46\",\"ponCode\":\"NA-1-1\",\"terminalSituation\":\"端业匹配\",\"weakLightSituation\":\"弱光\",\"userAccount\":\"073125484541\",\"userName\":\"王五\",\"userAddress\":\"香樟路\"},{\"communityName\":\"小区名称2\",\"oltIp\":\"10.1.5.46\",\"ponCode\":\"NA-1-2\",\"terminalSituation\":\"端业匹配\",\"weakLightSituation\":\"弱光\",\"userAccount\":\"073125484551\",\"userName\":\"李四\",\"userAddress\":\"香樟路\"}]}}";
        String result =  HttpUtil.sendPostBoc("http://192.168.101.175:8085/Boc/getPonInfo", jsonParam);


        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/client/common/btn_yy", method = RequestMethod.POST)
    public String queryBtnYy(@RequestBody Map<String, Object> data) {
        // 小区pon口用户查询
        logger.info("getPonInfo param:"+data);
        JSONObject  js = new JSONObject();
        String result="000";
        try {
            Map resultMap = getWorkOrderDAO().queryBtnYy((String) data.get("staffId"));
            if("0".equals((String)resultMap.get("count1")))
            {
                //查出来为0，所以预约按钮展示
                js.put("result","000");

            }
            else
            {
                js.put("result","001");
            }
        } catch (Exception e) {
            e.printStackTrace();
            js.put("result","000");
        }


        return js.toString();
    }



}
