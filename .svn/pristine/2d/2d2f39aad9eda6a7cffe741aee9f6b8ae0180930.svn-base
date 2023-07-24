package com.ztesoft.mobile.v2.controller.common;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.service.common.CommonService;
import com.ztesoft.mobile.v2.util.HttpUtil;
import com.ztesoft.mobile.v2.util.JSONUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 公共处理类，通过配置的sql或者存储过程，以及传参等信息，直接返回需要查询的结果
 *
 * @author Dell
 */
@Controller("delayInstallController")
public class DelayInstallController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(DelayInstallController.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }



    /**
     * 缓装特有
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/common/commonQueryObjectByProcedure1"})
    public @ResponseBody
    Result commonQueryObjectByProcedure1(@RequestBody Map<String, Object> data, ModelMap model,
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
        Map<String, Object> resultMap = (HashMap<String, Object>) result.getResultData().get("data_info");
        String result_code = ((BigDecimal)resultMap.get("result")).toString();

        //缓装提交成功才执行
        if("0".equals(result_code))
        {
            delayDispmsgProc(param,paramMap);
        }
        else
        {
            result.setResultCode(1001);
            result.setResultMsg("操作失败");
        }

//		logger.info("返回结果：" + result.getResultData().toString());
        return result;
    }

    /**
     * 缓装出入库 20220110
     * @param param
     * @return
     */
    private void delayDispmsgProc( Map<String, Object> param, Map<String, Object> paramMap )
    {
        String result_code = "";
        String method = (String)paramMap.get("method");
        BigDecimal intLength = new BigDecimal("3");
        String sqlParam="inf_delay_dispmsg_proc(?,?,?,?,?)";
        List<Object> list = new ArrayList<Object>();
        list.add(param.get("orderId"));
        list.add(param.get("workOrderId"));
        if("submitDelayOrderByProc".equals(method))
        {
            //缓装入库
            list.add("1");
        }
        else
        {
            //缓装出库
            list.add("0");
        }

        String[] outParamArray = new String[2];
        outParamArray[0]="resultCode";
        outParamArray[1]="resultDesc";

        //出参
        list.add("");
        list.add("");

        Result result = getCommonService().commonQueryObjectByProcedure(sqlParam, list, intLength.intValue(),
                outParamArray);
        Map<String, Object> resultMap = (HashMap<String, Object>) result.getResultData().get("data_info");
        result_code = (String)resultMap.get("resultCode");
        logger.info("返回结果：inf_delay_dispmsg_proc" + result_code);
//
//        return result_code;
    }




}
