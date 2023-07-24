package com.ztesoft.mobile.v2.controller.assistant;

import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * quartz定时任务，每天晚上执行一次
 * 查询前一天iptv回单的订单iptv登陆认证结果，并更新到iptv订单竣工信息表中iptv_order_complete_inf
 */
public class QueryIptvLoginResultJob {
    private static final Logger logger = Logger.getLogger(QueryIptvLoginResultJob.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }


    public void sayHello() {
        logger.info(" Hello, 我是任务 1");
        System.out.println(new Date() + " -> Hello, 我是任务 1");
    }

    public void queryIPTVLastLoginInfo() {
        logger.info("《《《执行任务查询iptv登陆认证信息开始》》》" + new Date());
        //先查询前一天iptv订单回单信息
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.staff_id as staffId,t.iptv_account as iptvAccount                     \n");
        sb.append("  from iptv_order_complete_inf t                                                \n");
        sb.append("  where t.insert_time >= trunc(sysdate-1) and t.insert_time < trunc(sysdate)    \n");
        Map paramMap = new HashMap<String, String>();
        String wherePatternStr = "";
        Result sqlResult = getCommonService().commonQueryListBySql(sb.toString(), paramMap, wherePatternStr);
        Map<Object, Object> resultData = sqlResult.getResultData();
        Map<String, Object> resultMap = (Map<String, Object>) resultData.get("data_info");
        List<Map<String, String>> dataList = (List<Map<String, String>>) resultMap.get("dataList");
        if (dataList != null && dataList.size() > 0) {
            logger.info("iptv竣工订单数量为：" + dataList.size());
            AssistantController assistant = new AssistantController();
            String account = "";
            String staffId = "";
            for (Map<String, String> data : dataList) {
                account = data.get("iptvAccount");
                staffId = data.get("staffId");
                assistant.queryIPTVLastLoginInfo(account, staffId,getCommonService());
            }
        } else {
            logger.info("没有查询到iptv竣工订单信息");
        }
        logger.info("《《《执行任务查询iptv登陆认证信息结束》》》" + new Date());
    }

    public static void main(String[] args) {
        new QueryIptvLoginResultJob().queryIPTVLastLoginInfo();
    }
}
