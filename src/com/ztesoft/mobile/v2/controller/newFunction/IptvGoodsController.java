package com.ztesoft.mobile.v2.controller.newFunction;

import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.util.ParamPropertyUtil;
import net.sf.json.JSONObject;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ParameterMode;
import java.util.HashMap;
import java.util.Map;


/**
 * IPTV订购商品功能处理类
 *
 * @author Dell
 */
@Controller("iptvGoodsController")
public class IptvGoodsController extends WebConfigController {

    private static final Logger logger = Logger.getLogger(IptvGoodsController.class);

    /**
     * 查询IPTV商品订购列表
     *
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/iptvGoodsController/queryIptvGoods"})
    public @ResponseBody
    Result queryIptvGoods(@RequestBody Map<String, Object> data, ModelMap model,
                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logicdevno = (String) data.get("logicdevno");
        logger.info("查询IPTV商品订购列表，logicdevno:" + logicdevno);
        Result result = null;
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            String endpoint = ParamPropertyUtil.getConfigWithDefault("iptvGoodsInterfaceAddress","http://192.168.101.11:7022/FLOWBUS_INTERFACE/services/RMSInterface");
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("queryIptvGoods");
            call.addParameter("logicdevno", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);

            Object[] paramValues = {logicdevno};
            String jsonResult = (String) call.invoke(paramValues);
            Map<Object,Object> resultMap = new JSONObject().fromObject(jsonResult);
            result = Result.buildSuccess();
            result.setResultData(resultMap);
            logger.info("result is " + result);
        } catch (Exception e) {
            logger.error("调用查询IPTV商品订购列表接口失败，失败原因：\n" + e.getMessage());
        }

        return result;
    }

    /**
     * 退订IPTV商品
     * @param data
     * @param model
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/client/iptvGoodsController/cancelIptvGoods"})
    public @ResponseBody
    Result cancelIptvGoods(@RequestBody Map<String, Object> data, ModelMap model,
                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logicdevno = (String) data.get("logicdevno");
        String staffId = (String) data.get("staffId");
        String skucode = (String) data.get("skucode");
        logger.info("取消IPTV商品订购，logicdevno:" + logicdevno);
        Result result = null;
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            String endpoint = ParamPropertyUtil.getConfigWithDefault("iptvGoodsInterfaceAddress","http://192.168.101.11:7022/FLOWBUS_INTERFACE/services/RMSInterface");
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("cancelIptvGoods");
            call.addParameter("logicdevno", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("skucode", XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter("staffId", XMLType.XSD_STRING, ParameterMode.IN);
            call.setReturnType(XMLType.XSD_STRING);

            Object[] paramValues = {logicdevno,skucode,staffId};
            String jsonResult = (String) call.invoke(paramValues);
            Map<Object,Object> resultMap = new JSONObject().fromObject(jsonResult);
            result = Result.buildSuccess();
            result.setResultData(resultMap);
            logger.info("result is " + result);
        } catch (Exception e) {
            logger.error("调用取消IPTV商品订购接口失败，失败原因：\n" + e.getMessage());
        }

        return result;
    }

	public static void main(String[] args) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("modifyPwd", "ZTEsoft123");
	}


}
