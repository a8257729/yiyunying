package com.ztesoft.mobile.v2.controller.newFunction;

import com.ztesoft.mobile.v2.controller.common.WebConfigController;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.common.CommonService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 急速打卡处理类
 * @author Dell
 *
 */
@Controller("rapidlySignController")
public class RapidlySignontroller extends WebConfigController {

	private static final Logger logger = Logger.getLogger(RapidlySignontroller.class);

	private CommonService commonService;

	private CommonService getCommonService() {
		return commonService;
	}

	@Autowired(required = false)
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	

	
	/**
	 * 查询当前装维人员的所有开通单
	 * @param data
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/client/rapidlySign/getOrderListByStaffId"})
	public @ResponseBody Result getOrderListByStaffId(@RequestBody Map<String, Object> data, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String staffId = (String) data.get("staffId");
		Result result = null;
		//查询装维人员下面的所有开通单
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT oo.id as orderId,ooki.acc_nbr as serviceNum,ooki.addr_name as addrName,WWOI.id as workOrderId            \n");
		sb.append("             FROM WO_WORK_ORDER_ING WWOI                                                  \n");
		sb.append("             JOIN OM_ORDER_KEY_INFO OOKI                                                  \n");
		sb.append("               ON WWOI.BASE_ORDER_ID = OOKI.ID                                            \n");
		sb.append("             JOIN OM_ORDER OO                                                             \n");
		sb.append("               ON OOKI.ID = OO.ID                                                         \n");
		sb.append("             left join ARRIVE_SIGN arrsign                                                \n");
		sb.append("               on WWOI.id = arrsign.work_order_id                                         \n");
		sb.append("            WHERE WWOI.PARTY_TYPE = 'STA'                                                 \n");
		sb.append("              AND WWOI.PARTY_ID = ?                                                \n");
		sb.append("              AND (WWOI.WORKITEMID IS NOT NULL OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10R' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10S' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10C')                                           \n");
		sb.append("              AND WWOI.TACHE_CODE NOT IN ('KTJG2', 'KTJG')                                \n");
		sb.append("              AND WWOI.WORK_ORDER_STATE IN ('10I', '10D', '10G', '10L')                   \n");
		sb.append("              AND WWOI.ORDER_CLASS = '10S'                                                \n");
		sb.append("              and arrsign.sign_id is null                                                 \n");
		sb.append("           union                                                                          \n");
		sb.append("           SELECT oo.id as orderId,ooki.acc_nbr as serviceNum,ooki.addr_name as addrName,WWOI.id as workOrderId  \n");
		sb.append("             FROM WO_WORK_ORDER_ING WWOI                                                  \n");
		sb.append("             JOIN OM_ORDER_KEY_INFO OOKI                                                  \n");
		sb.append("               ON WWOI.BASE_ORDER_ID = OOKI.ID                                            \n");
		sb.append("             JOIN OM_ORDER OO                                                             \n");
		sb.append("               ON OOKI.ID = OO.ID                                                         \n");
		sb.append("             left join ARRIVE_SIGN arrsign                                                \n");
		sb.append("               on WWOI.id = arrsign.work_order_id                                         \n");
		sb.append("            WHERE WWOI.PARTY_TYPE = 'JOB'                                                 \n");
		sb.append("              AND WWOI.PARTY_ID IN                                                        \n");
		sb.append("                  (SELECT JS.JOB_ID                                                       \n");
		sb.append("                     FROM UOS_JOB_STAFF JS                                                \n");
		sb.append("                    WHERE JS.IS_NORMAL = '1'                                              \n");
		sb.append("                      and JS.STATE = '1'                                                  \n");
		sb.append("                      AND JS.STAFF_ID = ?)                                         \n");
		sb.append("              AND (WWOI.WORKITEMID IS NOT NULL OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10R' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10S' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10C')                                           \n");
		sb.append("              AND WWOI.TACHE_CODE NOT IN ('KTJG2', 'KTJG')                                \n");
		sb.append("              AND WWOI.WORK_ORDER_STATE IN ('10I', '10D', '10G', '10L')                   \n");
		sb.append("              AND WWOI.ORDER_CLASS = '10S'                                                \n");
		sb.append("              and arrsign.sign_id is null                                                 \n");
		sb.append("           union                                                                          \n");
		sb.append("           SELECT oo.id as orderId,ooki.acc_nbr as serviceNum,ooki.addr_name as addrName,WWOI.id as workOrderId  \n");
		sb.append("             FROM WO_WORK_ORDER_ING WWOI                                                  \n");
		sb.append("             JOIN OM_ORDER_KEY_INFO OOKI                                                  \n");
		sb.append("               ON WWOI.BASE_ORDER_ID = OOKI.ID                                            \n");
		sb.append("             JOIN OM_ORDER OO                                                             \n");
		sb.append("               ON OOKI.ID = OO.ID                                                         \n");
		sb.append("             left join ARRIVE_SIGN arrsign                                                \n");
		sb.append("               on WWOI.id = arrsign.work_order_id                                         \n");
		sb.append("            WHERE WWOI.PARTY_TYPE = 'ORG'                                                 \n");
		sb.append("              AND WWOI.PARTY_ID IN                                                        \n");
		sb.append("                  (SELECT J.ORG_ID                                                        \n");
		sb.append("                     FROM UOS_JOB_STAFF JS, UOS_JOB J                                     \n");
		sb.append("                    WHERE J.STATE = '1'                                                   \n");
		sb.append("                      AND J.JOB_ID = JS.JOB_ID                                            \n");
		sb.append("                      AND JS.IS_NORMAL = '1'                                              \n");
		sb.append("                      AND JS.STATE = '1'                                                  \n");
		sb.append("                      AND JS.STAFF_ID = ?)                                         \n");
		sb.append("              AND (WWOI.WORKITEMID IS NOT NULL OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10R' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10S' OR                                         \n");
		sb.append("                  WWOI.WORK_ORDER_TYPE = '10C')                                           \n");
		sb.append("              AND WWOI.TACHE_CODE NOT IN ('KTJG2', 'KTJG')                                \n");
		sb.append("              AND WWOI.WORK_ORDER_STATE IN ('10I', '10D', '10G', '10L')                   \n");
		sb.append("              AND WWOI.ORDER_CLASS = '10S'                                                \n");
		sb.append("              and arrsign.sign_id is null                                                 \n");
		String sql = sb.toString();
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		paramMap.put("staffId1", staffId);
		paramMap.put("staffId2", staffId);
		paramMap.put("staffId3", staffId);
		String wherePatternStr = "staffId1:staffId1,staffId2:staffId2,staffId3:staffId3";
		Result orderListResult = getCommonService().commonQueryListBySql(sql, paramMap, wherePatternStr);
		logger.info("返回结果：" + orderListResult.getResultData());
		List<Map<String,Object>> orderList = (List<Map<String, Object>>) ((Map<String,Object>)orderListResult.getResultData().get("data_info")).get("dataList");
		if(orderList != null && orderList.size() > 0){
			BigDecimal orderId;
			for (int i = 0; i < orderList.size(); i++) {
				Map<String, Object> orderMap =  orderList.get(i);
				orderId = (BigDecimal)orderMap.get("orderId");
				logger.info(staffId + "急速打卡查询资源点位置，orderId:" + orderId);

				//根据orderId 查WorkFormKtController询资源点的位置
				String[] outParamArray = new String[2];
				String sqlParam = "sf_get_eqpposi_by_order(?,?,?)";
				logger.info("调用的存储过程为：" + sqlParam);
				List<Object> list = new ArrayList<Object>();
				outParamArray[0] = "longitude";
				outParamArray[1] = "latitude";
				list.add(orderId);
				list.add("");
				list.add("");

				Result queryPointResult = getCommonService().commonQueryObjectByProcedure(sqlParam, list, 1,
						outParamArray);
				Map pointMap = (Map<String,Object>)queryPointResult.getResultData().get("data_info");
				logger.info("返回结果:" + queryPointResult.getResultData());
				if( pointMap.get("longitude") != null && pointMap.get("longitude") != ""){
					BigDecimal longitude = new BigDecimal((String)pointMap.get("longitude"));
					orderMap.put("longitude",longitude);
				}
				if( pointMap.get("latitude") != null && pointMap.get("latitude") != ""){
					BigDecimal latitude = new BigDecimal((String)pointMap.get("latitude"));
					orderMap.put("latitude",latitude);
				}
			}
			Map<String,Object> dataInfoMap = (Map<String, Object>) orderListResult.getResultData().get("data_info");
			dataInfoMap.put("dataList",orderList);
		}
		logger.info("返回前台结果：" + orderListResult.getResultData());
		return orderListResult;
	}



	

}
