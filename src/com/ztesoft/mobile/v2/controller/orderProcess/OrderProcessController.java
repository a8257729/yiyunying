package com.ztesoft.mobile.v2.controller.orderProcess;

import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 工单进度查询
 *
 * @author Dell
 */
@Controller("orderProcessController")
public class OrderProcessController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(OrderProcessController.class);

    private CommonService commonService;

    private CommonService getCommonService() {
        return commonService;
    }

    @Autowired(required = false)
    public void setCommonService(CommonService commonService) {
        this.commonService = commonService;
    }


    @RequestMapping(value = {"/client/orderProcess/queryOrderProcess"})
    public @ResponseBody
    Result queryOrderProcess(@RequestBody Map<String, Object> data, ModelMap model,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userName = (String) data.get("userName");

        List paramList = new LinkedList();
        paramList.add(userName);
        paramList.add("10S");
        paramList.add("");
        paramList.add("");
        String[] outParam = new String[1];
        outParam[0] = "resultXML";
        Result queryResult = getCommonService().commonQueryObjectByProcedure("proc_orderqueryplan_for_all(?,?,?,?)", paramList, 3, outParam);
        logger.info("queryResult:" + queryResult.getResultMsg());
        logger.info("返回结果：" + queryResult.getResultData());
        String xml = ((Map<String, String>) queryResult.getResultData().get("data_info")).get("resultXML");
        logger.info("xml:\n" + xml);
        Map<Object, Object> resultData = parseSynOrderResponse(xml);
        queryResult.setResultData(resultData);
        return queryResult;
    }

    /**
     * 解析工单查询报文
     */
    private Map<Object, Object> parseSynOrderResponse(String respXml) {
        logger.info("工单进度查询响应报文: " + respXml);

        Result result = null;
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rtList3 = new ArrayList<Map<String, Object>>();
        try {
            Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

            Map<String, Object> commonMap_ = new HashMap<String, Object>();
            Node staffNameNode = doc.selectSingleNode("/RESP/ORDER/STAFF_NAME");
            Node staffMobileNode = doc.selectSingleNode("/RESP/ORDER/MOBILE_TEL");
            //订单状态
            Node orderStateNode = doc.selectSingleNode("/RESP/ORDER/ORDER_STATE");
            Node orderIdNode = doc.selectSingleNode("/RESP/ORDER/ORDER_ID");

            String staffName = staffNameNode == null ? "" : staffNameNode.getText();
            String staffMobile = staffMobileNode == null ? "" : staffMobileNode.getText();
            String orderState = orderStateNode == null ? "" : orderStateNode.getText();
            String orderId = orderIdNode == null ? "" : orderIdNode.getText();
            commonMap_.put("STAFF_NAME", staffName);
            commonMap_.put("MOBILE_TEL", staffMobile);
            commonMap_.put("ORDER_STATE", orderState);
            commonMap_.put("ORDER_ID", orderId);

            rtList2.add(commonMap_);
            String rootPath = "";
            List<Node> rtEleList = doc.selectNodes("/RESP/ORDER/WO_WORK_ORDER_LIST/WORK_ORDER");

            int size = rtEleList.size();

            logger.debug("the rtEleList size :" + size);
            logger.debug("the  rtEleList is:" + rtEleList);
            for (int i = 0; i < size; i++) {
                Node ele = rtEleList.get(i);
                Node tacheNameNode = ele.selectSingleNode(rootPath + "TACHE_NAME");
                Node createDateNode = ele.selectSingleNode(rootPath + "CREATE_DATE");
                Node workOrderStateNode = ele.selectSingleNode(rootPath + "WORK_ORDER_STATE");
                Map<String, Object> map_ = new HashMap<String, Object>();
                map_.put("TACHE_NAME", tacheNameNode == null ? "" : tacheNameNode.getText());
                map_.put("CREATE_DATE", createDateNode == null ? "" : createDateNode.getText());
                map_.put("WORK_ORDER_STATE", workOrderStateNode == null ? "" : workOrderStateNode.getText());
                rtList3.add(map_);
            }

        } catch (XMLDocException e) {
            e.printStackTrace();
            return dataMap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return dataMap;
        }
        dataMap.put("common", rtList2);
        dataMap.put("datas", rtList3);
        return dataMap;
    }


}
