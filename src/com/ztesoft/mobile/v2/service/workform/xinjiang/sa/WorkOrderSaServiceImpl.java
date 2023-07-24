package com.ztesoft.mobile.v2.service.workform.xinjiang.sa;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.WorkOrderSa;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.service.workform.XmlUtil;
import com.ztesoft.mobile.v2.service.workform.xinjiang.WebService;
/***
 * 公用工单管控功能实现类
 * 
 ** */

@Service("workOrderXjSaService")
public class WorkOrderSaServiceImpl extends BaseService implements
		WorkOrderSaService {

	private static final boolean debug = false;

	
	private static final Logger logger = Logger
			.getLogger(WorkOrderSaServiceImpl.class);

	private static final String[] PARAM_NAME = { "infType", "requestxml" };	
	/** 装移工单反馈列表 */
	private static final String FEEDBACK_INFO_SA_WORKORDER_INTERF = "saFeedBackInfoForEBiz";
	
	/** 装移工单详情 */
	private static final String WORKORDER_FULL_DETAIL_INTERF = "workOrderFullDetailForEBiz";
								
	// 调用接口服务
	private static final EBizToIomWebservice service = new EBizToIomWebservice();
	private static final WebService ws = new WebService();

	private String callWsService(String method, String reqXml) throws Exception {
		return service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE,
				WS_METHOD_OPERATION_NAME, PARAM_NAME, new String[] { method,
						reqXml });
	}


	public Result queryFeedbackInfo(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderSa.ZY_WORKORDERID_NODE);
			String staffId = json.getString(STAFFID);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<StaffId>" + staffId + "</StaffId>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					FEEDBACK_INFO_SA_WORKORDER_INTERF);
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(FEEDBACK_INFO_SA_WORKORDER_INTERF);
			else 
				retXml = callWsService(FEEDBACK_INFO_SA_WORKORDER_INTERF,
					reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
            //判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

            Node resultNode = doc.selectSingleNode("/Data/Return/Result");
            String resultVal = resultNode.getText();
            if ("000".equals(resultVal)) { // 成功
            	resultData = getListResultData(retXml);//获取列表
                result = Result.buildSuccess();
                result.setResultData(resultData);

	        } else { // 失败
	                Node errorDescNode = doc
	                                .selectSingleNode("/Data/Return/ErrorDesc");
	                String msg = errorDescNode.getText();
	                result = Result.buildInterInfoError(msg);
	        }            
			
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	
/** 
 * 开通待办详情
 */
	public Result workOrderDetail(JSONObject json) {
		Result result = null;
		try {
			String orderId = json.getString( WorkOrderKt.ORDER_ID_NODE );
			String workOrderId = json.getString( WorkOrderKt.WORK_ORDER_ID_NODE );

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<OrderID>" + orderId + "</OrderID>");
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			
			logger.debug("1111111111111解析解析解析："+ sbReqXml.toString());

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					WORKORDER_FULL_DETAIL_INTERF);
			String retXml="";
			if (debug) {
				retXml = XmlUtil.getRemotelXmlData(WORKORDER_FULL_DETAIL_INTERF);
			} else {
				retXml = this.callWsService(WORKORDER_FULL_DETAIL_INTERF, reqXml);
			}
			System.out.println("传送报文:"+reqXml.toString());
			logger.info("返回报文yhm222："+ retXml );
			System.out.println("返回报文: + " + retXml);
			result = parseWorkOrderDetailResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 解析工单查询报文 */
	private Result parseSynOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("工单查询响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";
				String rootPath1 = "WorkOrderInfo/";
				String rootPath2 = "ResourceInfo/";
				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");

				if ("0".equals(totalNode.getStringValue())) { // 没有记录的情况
					Node errorDescNode = doc
							.selectSingleNode("/Data/Return/ErrorDesc");
					String msg = errorDescNode.getText();
					if (StringUtils.isBlank(msg)) {
						msg = "没有更多的记录";
					}
					return result = Result.buildInterInfoError(msg);
				}

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList1 = new ArrayList<Map<String, Object>>(
						size);//定单信息
				List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>(
						size);//工单信息
				List<Map<String, Object>> rtList3 = new ArrayList<Map<String, Object>>(
						size);//资源信息
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_CODE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_LEVEL_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_APPLIC_DATE_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_CODEX_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_LOGIC_NUM_NODE);
					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_PHY_NUM_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_APPOIN_DATE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUSTNAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUST_LEVEL_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUST_BRAND_NODE);
					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CONTACT_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CONTSCT_P_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ZJ_ADDR_NODE);
					Node nn14 = ele_.selectSingleNode(rootPath
							+WorkOrderSa.ZY_WORKORDERID_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_REPEAT_NODE);
					Node nn16 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_URGE_NODE);
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_TIME_COUNT_TYPE_NODE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_TIME_COUNT_NUM_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_USER_LEVEL_NODE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_NEED_FOCUS_NODE);
					Node nn21 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_PROD_CODE_NODE);
					Node nn22 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IP_PROPERTY_NODE);
					Node nn23 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_USER_IP_PROPERTY_NAME_NODE);
					Node nn24 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DEVICE_IP_PROPERTY_NAME_NODE);
					Node nn25 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_LAN_ID_NODE);
					Node nn27 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IOM_ORDER_CODE_NODE);	
					Node nn28 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_U_NODE);
					Node nn29 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_D_NODE);
					Node nn30 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_R_NODE);
					
					Map<String, Object> map_1 = new HashMap<String, Object>();
					map_1.put(WorkOrderSa.ZY_ORDER_CODE_NODE,
							nn1 == null ? "" : nn1.getText());
					map_1.put(WorkOrderSa.ZY_ORDER_LEVEL_NODE,
							nn2 == null ? "" : nn2.getText());
					map_1.put(WorkOrderSa.ZY_APPLIC_DATE_NODE,
							nn3 == null ? "" : nn3.getText());
					map_1.put(WorkOrderSa.ZY_ORDER_CODEX_NODE,
							nn4 == null ? "" : nn4.getText());
					map_1.put(WorkOrderSa.ZY_LOGIC_NUM_NODE,
							nn5 == null ? "" : nn5.getText());
					map_1.put(WorkOrderSa.ZY_PHY_NUM_NODE,
							nn6 == null ? "" : nn6.getText());
					map_1.put(WorkOrderSa.ZY_APPOIN_DATE_NODE,
							nn7 == null ? "" : nn7.getText());
					map_1.put(WorkOrderSa.ZY_CUSTNAME_NODE,
							nn8 == null ? "" : nn8.getText());
					map_1.put(WorkOrderSa.ZY_CUST_LEVEL_NODE,
							nn9 == null ? "" : nn9.getText());
					map_1.put(WorkOrderSa.ZY_CUST_BRAND_NODE,
							nn10 == null ? "" : nn10.getText());
					map_1.put(WorkOrderSa.ZY_CONTACT_NODE,
							nn11 == null ? "" : nn11.getText());
					map_1.put(WorkOrderSa.ZY_CONTSCT_P_NODE,
							nn12 == null ? "" : nn12.getText());
					map_1.put(WorkOrderSa.ZY_ZJ_ADDR_NODE,
							nn13 == null ? "" : nn13.getText());
					map_1.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nn14 == null ? "" : nn14.getText());
					map_1.put(WorkOrderSa.ZY_IS_REPEAT_NODE,
							nn15 == null ? "" : nn15.getText());
					map_1.put(WorkOrderSa.ZY_IS_URGE_NODE,
							nn16 == null ? "" : nn16.getText());
					map_1.put(WorkOrderSa.ZY_TIME_COUNT_TYPE_NODE,
							nn17 == null ? "" : nn17.getText());
					map_1.put(WorkOrderSa.ZY_TIME_COUNT_NUM_NODE,
							nn18 == null ? "" : nn18.getText());
					map_1.put(WorkOrderSa.ZY_USER_LEVEL_NODE,
							nn19 == null ? "" : nn19.getText());
					map_1.put(WorkOrderSa.ZY_IS_NEED_FOCUS_NODE,
							nn20 == null ? "" : nn20.getText());
					map_1.put(WorkOrderSa.ZY_PROD_CODE_NODE,
							nn21 == null ? "" : nn21.getText());
					map_1.put(WorkOrderSa.ZY_IP_PROPERTY_NODE,
							nn22 == null ? "" : nn22.getText());
					map_1.put(WorkOrderSa.ZY_USER_IP_PROPERTY_NAME_NODE,
							nn23 == null ? "" : nn23.getText());
					map_1.put(WorkOrderSa.ZY_DEVICE_IP_PROPERTY_NAME_NODE,
							nn24 == null ? "" : nn24.getText());
					map_1.put(WorkOrderSa.ZY_LAN_ID_NODE,
							nn25 == null ? "" : nn25.getText());
					map_1.put(WorkOrderSa.ZY_IOM_ORDER_CODE_NODE,
							nn27 == null ? "" : nn27.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_U_NODE,
							nn28 == null ? "" : nn28.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_D_NODE,
							nn29 == null ? "" : nn29.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_R_NODE,
							nn30 == null ? "" : nn30.getText());
					rtList1.add(map_1);
					
					Node nm1 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_STATU_NODE);
					Node nm2 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_STATU_NAME_NODE);
					Node nm3 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_CODE_NODE);
					Node nm4 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORK_TYPE_NODE);
					Node nm5 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_ORDER_ID_NODE);
					Node nm6 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_STATUS_NODE);
					Node nm7 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_CON_BOK_DATE_NODE);
					Node nm9 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_BUSIN_TYPE_NODE);
					Node nm10 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORKORDERID_NODE);
					
					Map<String, Object> map_2 = new HashMap<String, Object>();
					map_2.put(WorkOrderSa.ZY_WORDER_STATU_NODE,
							nm1 == null ? "" : nm1.getText());
					map_2.put(WorkOrderSa.ZY_WORDER_STATU_NAME_NODE,
							nm2 == null ? "" : nm2.getText());
					map_2.put(WorkOrderSa.ZY_WORDER_CODE_NODE,
							nm3 == null ? "" : nm3.getText());
					map_2.put(WorkOrderSa.ZY_WORK_TYPE_NODE,
							nm4 == null ? "" : nm4.getText());
					map_2.put(WorkOrderSa.ZY_ORDER_ID_NODE,
							nm5 == null ? "" : nm5.getText());
					map_2.put(WorkOrderSa.ZY_STATUS_NODE,
							nm6 == null ? "" : nm6.getText());
					map_2.put(WorkOrderSa.ZY_CON_BOK_DATE_NODE,
							nm7 == null ? "" : nm7.getText());
					map_2.put(WorkOrderSa.ZY_BUSIN_TYPE_NODE,
							nm9 == null ? "" : nm9.getText());
					map_2.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nm10 == null ? "" : nm10.getText());
					rtList2.add(map_2);
					
					Node nmm1 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_NATUR_NODE);
					Node nmm2 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ACCESSMODE_NODE);
					Node nmm3 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ACCESS_TYPE_NODE);
					Node nmm4 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_CHARGE_NODE);
					Node nmm5 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_ACCO_NODE);
					Node nmm6 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PASS_NODE);
					Node nmm7 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_RATE_NODE);
					Node nmm8 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_RELA_NUM_NODE);
					Node nmm9 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BUSIN_PASS_NODE);
					Node nmm10 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVCOD_NEW_NODE);
					Node nmm11 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVCOD_OLD_NODE);
					Node nmm12 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_PHYCODE_NEW_NODE);
					Node nmm13 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_PHYCODE_OLD_NODE);
					Node nmm14 = ele_.selectSingleNode(rootPath2
							+WorkOrderSa.ZY_SECTYPE_NEW_NODE);
					Node nmm15 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SECTYPE_OLD_NODE);
					Node nmm16 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ROW_NODE);
					Node nmm17 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ROW_OLD_NODE);
					Node nmm18 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINE_NODE);
					Node nmm19 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINE_OLD_NODE);
					Node nmm20 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_AU_ROW_NODE);
					Node nmm21 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_AU_ROW_OLD_NODE);
					Node nmm22 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DA_ROW_NODE);
					Node nmm23 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DA_ROW_OLD_NODE);
					Node nmm24 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PORT_NODE);
					Node nmm25 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PORT_OLD_NODE);
					Node nmm26 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_TERM_INF_NODE);
					Node nmm27 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SPLI_DW_PORT_NODE);
					Node nmm28 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_TERM_UP_PORT_NODE);
					Node nmm29 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LO_SN_NUM_NODE);
					Node nmm30 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_NODE);
					Node nmm31 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_OLD_NODE);
					Node nmm32 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ADSL_TEST_NODE);
					Node nmm33 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_JBOX_INF_NODE);
					Node nmm34 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_NEW_NODE);
					Node nmm35 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SIP_PASSWORD_NODE);
					Node nmm36 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IPTV_PASSWORD_NODE);
					Node nmm37 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_EXCH_NAME_NODE);
					Node nmm38 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_NET_ACCO_NODE);
					Node nmm39 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_RANGE_ADR_NODE);
					Node nmm40 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ODB_NAME_NODE);
					Node nmm41 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_OLT_SB_IP_ADR_NODE);
					Node nmm42 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IS_FTTH_NODE);
					Node nmm43 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVICE_CLASS_NODE);
					Node nmm44 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVICE_SOURCE_NODE);
					Node nmm45 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IS_FLOW_ZLHT_NODE);
					Node nmm46 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_WORKORDERID_NODE);
					Node nmm47 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BUSIN_TYPE_NODE);
					
					Map<String, Object> map_3 = new HashMap<String, Object>();
					map_3.put(WorkOrderSa.ZY_NATUR_NODE,
							nmm1 == null ? "" : nmm1.getText());
					map_3.put(WorkOrderSa.ZY_ACCESSMODE_NODE,
							nmm2 == null ? "" : nmm2.getText());
					map_3.put(WorkOrderSa.ZY_ACCESS_TYPE_NODE,
							nmm3 == null ? "" : nmm3.getText());
					map_3.put(WorkOrderSa.ZY_CHARGE_NODE,
							nmm4 == null ? "" : nmm4.getText());
					map_3.put(WorkOrderSa.ZY_BROA_ACCO_NODE,
							nmm5 == null ? "" : nmm5.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PASS_NODE,
							nmm6 == null ? "" : nmm6.getText());
					map_3.put(WorkOrderSa.ZY_BROA_RATE_NODE,
							nmm7 == null ? "" : nmm7.getText());
					map_3.put(WorkOrderSa.ZY_RELA_NUM_NODE,
							nmm8 == null ? "" : nmm8.getText());
					map_3.put(WorkOrderSa.ZY_BUSIN_PASS_NODE,
							nmm9 == null ? "" : nmm9.getText());
					map_3.put(WorkOrderSa.ZY_DEVCOD_NEW_NODE,
							nmm10 == null ? "" : nmm10.getText());
					map_3.put(WorkOrderSa.ZY_DEVCOD_OLD_NODE,
							nmm11 == null ? "" : nmm11.getText());
					map_3.put(WorkOrderSa.ZY_PHYCODE_NEW_NODE,
							nmm12 == null ? "" : nmm12.getText());
					map_3.put(WorkOrderSa.ZY_PHYCODE_OLD_NODE,
							nmm13 == null ? "" : nmm13.getText());
					map_3.put(WorkOrderSa.ZY_SECTYPE_NEW_NODE,
							nmm14 == null ? "" : nmm14.getText());
					map_3.put(WorkOrderSa.ZY_SECTYPE_OLD_NODE,
							nmm15 == null ? "" : nmm15.getText());
					map_3.put(WorkOrderSa.ZY_ROW_NODE,
							nmm16 == null ? "" : nmm16.getText());
					map_3.put(WorkOrderSa.ZY_ROW_OLD_NODE,
							nmm17 == null ? "" : nmm17.getText());
					map_3.put(WorkOrderSa.ZY_LINE_NODE,
							nmm18 == null ? "" : nmm18.getText());
					map_3.put(WorkOrderSa.ZY_LINE_OLD_NODE,
							nmm19 == null ? "" : nmm19.getText());
					map_3.put(WorkOrderSa.ZY_AU_ROW_NODE,
							nmm20 == null ? "" : nmm20.getText());
					map_3.put(WorkOrderSa.ZY_AU_ROW_OLD_NODE,
							nmm21 == null ? "" : nmm21.getText());
					map_3.put(WorkOrderSa.ZY_DA_ROW_NODE,
							nmm22 == null ? "" : nmm22.getText());
					map_3.put(WorkOrderSa.ZY_DA_ROW_OLD_NODE,
							nmm23 == null ? "" : nmm23.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PORT_NODE,
							nmm24 == null ? "" : nmm24.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PORT_OLD_NODE,
							nmm25 == null ? "" : nmm25.getText());
					map_3.put(WorkOrderSa.ZY_TERM_INF_NODE,
							nmm26 == null ? "" : nmm26.getText());
					map_3.put(WorkOrderSa.ZY_SPLI_DW_PORT_NODE,
							nmm27 == null ? "" : nmm27.getText());
					map_3.put(WorkOrderSa.ZY_TERM_UP_PORT_NODE,
							nmm28 == null ? "" : nmm28.getText());
					map_3.put(WorkOrderSa.ZY_LO_SN_NUM_NODE,
							nmm29 == null ? "" : nmm29.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_NODE,
							nmm30 == null ? "" : nmm30.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_OLD_NODE,
							nmm31 == null ? "" : nmm31.getText());
					map_3.put(WorkOrderSa.ZY_ADSL_TEST_NODE,
							nmm32 == null ? "" : nmm32.getText());
					map_3.put(WorkOrderSa.ZY_JBOX_INF_NODE,
							nmm33 == null ? "" : nmm33.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_NEW_NODE,
							nmm34 == null ? "" : nmm34.getText());
					map_3.put(WorkOrderSa.ZY_SIP_PASSWORD_NODE,
							nmm35 == null ? "" : nmm35.getText());
					map_3.put(WorkOrderSa.ZY_IPTV_PASSWORD_NODE,
							nmm36 == null ? "" : nmm36.getText());
					map_3.put(WorkOrderSa.ZY_EXCH_NAME_NODE,
							nmm37 == null ? "" : nmm37.getText());
					map_3.put(WorkOrderSa.ZY_NET_ACCO_NODE,
							nmm38 == null ? "" : nmm38.getText());
					map_3.put(WorkOrderSa.ZY_RANGE_ADR_NODE,
							nmm39 == null ? "" : nmm39.getText());
					map_3.put(WorkOrderSa.ZY_ODB_NAME_NODE,
							nmm40 == null ? "" : nmm40.getText());
					map_3.put(WorkOrderSa.ZY_OLT_SB_IP_ADR_NODE,
							nmm41 == null ? "" : nmm41.getText());
					map_3.put(WorkOrderSa.ZY_IS_FTTH_NODE,
							nmm42 == null ? "" : nmm42.getText());
					map_3.put(WorkOrderSa.ZY_DEVICE_CLASS_NODE,
							nmm43 == null ? "" : nmm43.getText());
					map_3.put(WorkOrderSa.ZY_DEVICE_SOURCE_NODE,
							nmm44 == null ? "" : nmm44.getText());
					map_3.put(WorkOrderSa.ZY_IS_FLOW_ZLHT_NODE,
							nmm45 == null ? "" : nmm45.getText());
					map_3.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nmm46 == null ? "" : nmm46.getText());
					map_3.put(WorkOrderSa.ZY_BUSIN_TYPE_NODE,
							nmm47 == null ? "" : nmm47.getText());
					rtList3.add(map_3);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderSa.ORDER_LIST_NODE, rtList1);
				resultData.put(WorkOrderSa.WORK_ORDER_LIST_NODE, rtList2);				
				resultData.put(WorkOrderSa.RES_LIST_NODE, rtList3);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());
				Node pageNode = doc.selectSingleNode("/Data/Return/TotalPage");
				resultData.put(WorkOrder.TOTAL_PAGE_NODE,
						null == pageNode ? 0 : pageNode.getText());
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	
	private Result parsePublicOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("公用功能操作结果响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/ContentList/Content/Public/";

				// Map<Object, Object> resultData = new HashMap<Object,
				// Object>();
				// resultData.put(WorkOrderSa.WORK_ORDER_LIST_NODE, rtList);

				result = Result.buildSuccess();

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}

	private Result parseValidDeviceResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("验证设备操作结果响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			Node ValidNum = doc.selectSingleNode("/Data/Return/ValidNum");
			String validNum = ValidNum.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/ContentList/Content/Public/";

				 Map<Object, Object> resultData = new HashMap<Object,
				 Object>();
				 resultData.put("ValidNum", validNum);
				
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}
	private Result parseGetDeviceCodeResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("获取设备串码操作结果响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			Node DeviceCode = doc.selectSingleNode("/Data/Return/DeviceCode");
			String deviceCode = DeviceCode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/ContentList/Content/Public/";

				 Map<Object, Object> resultData = new HashMap<Object,
				 Object>();
				 resultData.put("DeviceCode", deviceCode);
				
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return result;
	}	
	
	
	/**
	 * 解析列表
	 * 
	 * @param retXml
	 * @return
	 */
	public Map<Object, Object> getListResultData(String retXml) {
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		SAXReader reader = new SAXReader();
		String result;
		String errorDesc;
		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element Data = doc.getRootElement();
			Element Return = Data.element("Return");

			// 获取返回代码及描述信息
			result = Return.element("Result").getText();
			errorDesc = Return.element("ErrorDesc").getText();

			Element ReasonListEle = Data.element("ReturnList");

			List reasonList = ReasonListEle.elements("List");
			List reasonList_ = new ArrayList();

			for (int i = 0; i < reasonList.size(); i++) {
				Element reason = (Element) reasonList.get(i);
				List reasonColList = reason.elements();
				Map colMap = new HashMap();
				for (int j = 0; j < reasonColList.size(); j++) {
					Element e = (Element) reasonColList.get(j);
					colMap.put(e.getName(), e.getStringValue());

				}
				reasonList_.add(colMap);

			}

			resultData.put("Result", result);
			resultData.put("ErrorDesc", errorDesc);
			resultData.put("RETURN_LIST", reasonList_);
			System.out.println("resultData===" + resultData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}
	
	/**
	 * 解析端口详情报文
	 * 
	 * @param respXml
	 * @return
	 */
	private Result parsePortDetailResponse(String respXml) {
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTPRODID);
				Node nn2 = doc.selectSingleNode(rootPath
						+WorkOrderSa.ZY_DK_CUSTDESEQ);
				Node nn3 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTORDERCODE);
				Node nn4 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTORDERID);
				Node nn5 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTPRODTYPE);
				Node nn6 = doc.selectSingleNode(rootPath
						+WorkOrderSa.ZY_DK_CUSTOPRSTATE);
				Node nn7 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTNAME);
				Node nn8 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTDIRENAME);
				Node nn9 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTADDRNAME);
				Node nn10 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_SPCCOMNAME);
				Node nn11 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTEQPNAME);
				Node nn12 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTPORTNAME);
				Node nn13 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTMOALAIS);				
				Node nn14 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTRACKNAMEH);
				Node nn15 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTHTERMNO);
				
			   Map  map = new HashMap();
               map.put(WorkOrderSa.ZY_DK_CUSTPRODID, nn1.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTDESEQ, nn2.getText());
               map.put( WorkOrderSa.ZY_DK_CUSTORDERCODE, nn3.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTORDERID, nn4.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTPRODTYPE, nn5.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTOPRSTATE, nn6.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTNAME, nn7.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTDIRENAME, nn8.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTADDRNAME, nn9.getText());
               map.put(WorkOrderSa.ZY_DK_SPCCOMNAME, nn10.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTEQPNAME, nn11.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTPORTNAME, nn12.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTMOALAIS, nn13.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTRACKNAMEH, nn14.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTHTERMNO, nn15.getText());
				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData
						.put(WorkOrderSa.PORT_LIST_NODE, map);
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		}

		return result;
	}
	
	/**
	 * 解析修改端口信息报文
	 * 
	 * @param respXml
	 * @return
	 */
	private Result parsePortInfoResponse(String respXml) {
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTDIREID);
				Node nn2 = doc.selectSingleNode(rootPath
						+WorkOrderSa.ZY_DK_CUSTEQPID);
				Node nn3 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTEQPNO);
				Node nn4 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTRACKNAMEZID);
				Node nn5 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTSTERMNOID);
				Node nn6 = doc.selectSingleNode(rootPath
						+WorkOrderSa.ZY_DK_CUSTPORTLINK);
				Node nn7 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTPORTNO);
				Node nn8 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_OLDACCESSID);
				Node nn9 = doc.selectSingleNode(rootPath
						+ WorkOrderSa.ZY_DK_CUSTDIRENAME);
				
			   Map  map = new HashMap();
               map.put(WorkOrderSa.ZY_DK_CUSTDIREID, nn1.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTEQPID, nn2.getText());
               map.put( WorkOrderSa.ZY_DK_CUSTEQPNO, nn3.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTRACKNAMEZID, nn4.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTSTERMNOID, nn5.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTPORTLINK, nn6.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTPORTNO, nn7.getText());
               map.put(WorkOrderSa.ZY_DK_OLDACCESSID, nn8.getText());
               map.put(WorkOrderSa.ZY_DK_CUSTDIRENAME, nn9.getText());
				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData
						.put(WorkOrderSa.PORT_LIST_NODE, map);
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		}

		return result;
	}
	
	/** 解析端口列表查询报文 */
	private Result parsePortListResponse(String respXml) {
		
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";

				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");

				if ("0".equals(totalNode.getStringValue())) { // 没有记录的情况
					Node errorDescNode = doc
							.selectSingleNode("/Data/Return/ErrorDesc");
					String msg = errorDescNode.getText();
					if (StringUtils.isBlank(msg)) {
						msg = "没有更多的记录";
					}
					return result = Result.buildInterInfoError(msg);
				}

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTNO);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTNAME);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTHORIZNO);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTMOALAIS);		
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPRESTYPEID);	
					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTMDFID);	
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTMDFTYPEID);	
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTHORIZID);	
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTHORIZTYPEID);	
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTPORTTYPE);	
					
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTNO,
							nn1 == null ? "" : nn1.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTNAME,
							nn2 == null ? "" : nn2.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTHORIZNO,
							nn3 == null ? "" : nn3.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTMOALAIS,
							nn4 == null ? "" : nn4.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPRESTYPEID,
							nn5 == null ? "" : nn5.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTMDFID,
							nn6 == null ? "" : nn6.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTMDFTYPEID,
							nn7 == null ? "" : nn7.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTHORIZID,
							nn8 == null ? "" : nn8.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTHORIZTYPEID,
							nn9.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTPORTTYPE,
							nn10.getText() == null ? "" : nn10.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderSa.PORT_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());

				result = Result.buildSuccess(resultData);

			} else { // 失败queryWorkOrderForEBiz
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	
	/** 解析交换机列表查询报文 */
	private Result parseSwitcherListResponse(String respXml) {
		
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";

				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");

				if ("0".equals(totalNode.getStringValue())) { // 没有记录的情况
					Node errorDescNode = doc
							.selectSingleNode("/Data/Return/ErrorDesc");
					String msg = errorDescNode.getText();
					if (StringUtils.isBlank(msg)) {
						msg = "没有更多的记录";
					}
					return result = Result.buildInterInfoError(msg);
				}

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPNO);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPNAME);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPID);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPRESTYPEID);		
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_CUSTEQPRESTYPENAME);	
					
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPNO,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPNAME,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPID,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPRESTYPEID,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderSa.ZY_DK_CUSTEQPRESTYPENAME,
							nn5.getText() == null ? "" : nn5.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderSa.PORT_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());

				result = Result.buildSuccess(resultData);

			} else { // 失败queryWorkOrderForEBiz
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	
	/** 解析交换机列表查询报文 */
	private Result parseDirectionListResponse(String respXml) {
		
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";

				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");

				if ("0".equals(totalNode.getStringValue())) { // 没有记录的情况
					Node errorDescNode = doc
							.selectSingleNode("/Data/Return/ErrorDesc");
					String msg = errorDescNode.getText();
					if (StringUtils.isBlank(msg)) {
						msg = "没有更多的记录";
					}
					return result = Result.buildInterInfoError(msg);
				}

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_DIREID);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DK_DIRENAME);
					
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderSa.ZY_DK_DIREID,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderSa.ZY_DK_DIRENAME,
							nn2.getText() == null ? "" : nn2.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderSa.PORT_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());

				result = Result.buildSuccess(resultData);

			} else { // 失败queryWorkOrderForEBiz
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	/** 解析材料列表查询报文 */
	private Result parseMaterialListResponse(String respXml) {
		
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";

				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");

				if ("0".equals(totalNode.getStringValue())) { // 没有记录的情况
					Node errorDescNode = doc
							.selectSingleNode("/Data/Return/ErrorDesc");
					String msg = errorDescNode.getText();
					if (StringUtils.isBlank(msg)) {
						msg = "没有更多的记录";
					}
					return result = Result.buildInterInfoError(msg);
				}

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_MATERIALID);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_MATERIALNAME);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_MATERIALCLASS);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_SPEC);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_UNIT);
					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_PRICE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_COMPOSEFLAG);
					
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderSa.ZY_MATERIALID,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderSa.ZY_MATERIALNAME,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderSa.ZY_MATERIALCLASS,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderSa.ZY_SPEC,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderSa.ZY_UNIT,
							nn5.getText() == null ? "" : nn5.getText());
					map_.put(WorkOrderSa.ZY_PRICE,
							nn6.getText() == null ? "" : nn6.getText());
					map_.put(WorkOrderSa.ZY_COMPOSEFLAG,
							nn7.getText() == null ? "" : nn7.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put("RETURN_LIST", rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());

				result = Result.buildSuccess(resultData);

			} else { // 失败queryWorkOrderForEBiz
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}	
	/** 解析流程信息查询报文 */
	private Result parseFlowInfoListResponse(String respXml) {
		
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/Content");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_CODE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_TITLE_NODE);
					
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderSa.ZY_ORDER_CODE_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderBz.ORDER_TITLE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					
					
					List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>(
							size);
					List<Element> rtEleList2 = ele_.selectNodes(rootPath
							+"FlowInfo");
					int size2 = rtEleList2.size();
					logger.error("size2="+size2);
					for (int j = 0; j < size2; j++) {
						Element ele_2 = rtEleList2.get(j);
						Node nm1 = ele_2.selectSingleNode(WorkOrderSa.ZY_TACHENAME);
						logger.error("nm1="+nm1);
						Node nm2 = ele_2.selectSingleNode(WorkOrderSa.ZY_SYSTEMNAME);
						Node nm3 = ele_2.selectSingleNode( WorkOrderSa.ZY_BEGINDATE);
						Node nm4 = ele_2.selectSingleNode(WorkOrderSa.ZY_FINISHDATE);
						Node nm5 = ele_2.selectSingleNode(WorkOrderSa.ZY_TOTALTIME);
						
						Map<String, Object> map_2 = new HashMap<String, Object>();
						map_2.put(WorkOrderSa.ZY_TACHENAME,
								nm1.getText() == null ? "" : nm1.getText());
						map_2.put(WorkOrderSa.ZY_SYSTEMNAME,
								nm2.getText() == null ? "" : nm2.getText());
						map_2.put(WorkOrderSa.ZY_BEGINDATE,
								nm3.getText() == null ? "" : nm3.getText());
						map_2.put(WorkOrderSa.ZY_FINISHDATE,
								nm4.getText() == null ? "" : nm4.getText());
						map_2.put(WorkOrderSa.ZY_TOTALTIME,
								nm5.getText() == null ? "" : nm5.getText());
						rtList2.add(map_2);
					}
					
					Map<String, Object> resultData = new HashMap<String, Object>();
					resultData.put("FLOW_LIST", rtList2);	
					map_.putAll(resultData);
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put("RETURN_LIST", rtList);			

				result = Result.buildSuccess(resultData);

			} else { // 失败queryWorkOrderForEBiz
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	
	/** 解析工单查询报文   解析开通待办详情返回报文 */
	private Result parseWorkOrderDetailResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("工单查询响应报文:" + respXml);
		}
		Result result = null;
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				// String rootPath = "/Data/Return/ContentList/Content/Public/";
				String rootPath = "Public/";
				String rootPath1 = "WorkOrderInfo/";
				String rootPath2 = "ResourceInfo/";
				Node totalNode = doc.selectSingleNode("/Data/Return/TotalNum");			

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");

				int size = rtEleList.size();
				
				List<Map<String, Object>> rtList1 = new ArrayList<Map<String, Object>>(
						size);//定单信息
				List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>(
						size);//工单信息
				List<Map<String, Object>> rtList3 = new ArrayList<Map<String, Object>>(
						size);//资源信息
				for (int i = 0; i < size; i++) {
					System.out.println("循环次数："+i);
					Element ele_ = rtEleList.get(i);
					System.out.println("ele_.asXML()"+ele_.asXML());
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_CODE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_LEVEL_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_APPLIC_DATE_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ORDER_CODEX_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_LOGIC_NUM_NODE);
					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_PHY_NUM_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_APPOIN_DATE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUSTNAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUST_LEVEL_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CUST_BRAND_NODE);
					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CONTACT_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_CONTSCT_P_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ZJ_ADDR_NODE);
					Node nn14 = ele_.selectSingleNode(rootPath
							+WorkOrderSa.ZY_WORKORDERID_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_REPEAT_NODE);
					Node nn16 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_URGE_NODE);
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_TIME_COUNT_TYPE_NODE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_TIME_COUNT_NUM_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_USER_LEVEL_NODE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IS_NEED_FOCUS_NODE);
					Node nn21 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_PROD_CODE_NODE);
					Node nn22 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IP_PROPERTY_NODE);
					Node nn23 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_USER_IP_PROPERTY_NAME_NODE);
					Node nn24 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_DEVICE_IP_PROPERTY_NAME_NODE);
					Node nn25 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_LAN_ID_NODE);
					Node nn27 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_IOM_ORDER_CODE_NODE);	
					Node nn28 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_U_NODE);
					Node nn29 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_D_NODE);
					Node nn30 = ele_.selectSingleNode(rootPath
							+ WorkOrderSa.ZY_ACCEP_R_NODE);
					
					Map<String, Object> map_1 = new HashMap<String, Object>();
					map_1.put(WorkOrderSa.ZY_ORDER_CODE_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_1.put(WorkOrderSa.ZY_ORDER_LEVEL_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_1.put(WorkOrderSa.ZY_APPLIC_DATE_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_1.put(WorkOrderSa.ZY_ORDER_CODEX_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_1.put(WorkOrderSa.ZY_LOGIC_NUM_NODE,
							nn5.getText() == null ? "" : nn5.getText());
					map_1.put(WorkOrderSa.ZY_PHY_NUM_NODE,
							nn6.getText() == null ? "" : nn6.getText());
					map_1.put(WorkOrderSa.ZY_APPOIN_DATE_NODE,
							nn7.getText() == null ? "" : nn7.getText());
					map_1.put(WorkOrderSa.ZY_CUSTNAME_NODE,
							nn8.getText() == null ? "" : nn8.getText());
					map_1.put(WorkOrderSa.ZY_CUST_LEVEL_NODE,
							nn9.getText() == null ? "" : nn9.getText());
					map_1.put(WorkOrderSa.ZY_CUST_BRAND_NODE,
							nn10.getText() == null ? "" : nn10.getText());
					map_1.put(WorkOrderSa.ZY_CONTACT_NODE,
							nn11.getText() == null ? "" : nn11.getText());
					map_1.put(WorkOrderSa.ZY_CONTSCT_P_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					System.out.println("2222解析的时候返回的联系人电话："+ (nn12.getText() == null ? "" : nn12.getText()) );
					map_1.put(WorkOrderSa.ZY_ZJ_ADDR_NODE,
							nn13.getText() == null ? "" : nn13.getText());
					map_1.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nn14.getText() == null ? "" : nn14.getText());
					map_1.put(WorkOrderSa.ZY_IS_REPEAT_NODE,
							nn15.getText() == null ? "" : nn15.getText());
					map_1.put(WorkOrderSa.ZY_IS_URGE_NODE,
							nn16.getText() == null ? "" : nn16.getText());
					map_1.put(WorkOrderSa.ZY_TIME_COUNT_TYPE_NODE,
							nn17.getText() == null ? "" : nn17.getText());
					map_1.put(WorkOrderSa.ZY_TIME_COUNT_NUM_NODE,
							nn18.getText() == null ? "" : nn18.getText());
					map_1.put(WorkOrderSa.ZY_USER_LEVEL_NODE,
							nn19.getText() == null ? "" : nn19.getText());
					map_1.put(WorkOrderSa.ZY_IS_NEED_FOCUS_NODE,
							nn20.getText() == null ? "" : nn20.getText());
					map_1.put(WorkOrderSa.ZY_PROD_CODE_NODE,
							nn21.getText() == null ? "" : nn21.getText());
					map_1.put(WorkOrderSa.ZY_IP_PROPERTY_NODE,
							nn22.getText() == null ? "" : nn22.getText());
					map_1.put(WorkOrderSa.ZY_USER_IP_PROPERTY_NAME_NODE,
							nn23.getText() == null ? "" : nn23.getText());
					map_1.put(WorkOrderSa.ZY_DEVICE_IP_PROPERTY_NAME_NODE,
							nn24.getText() == null ? "" : nn24.getText());
					map_1.put(WorkOrderSa.ZY_LAN_ID_NODE,
							nn25.getText() == null ? "" : nn25.getText());
					map_1.put(WorkOrderSa.ZY_IOM_ORDER_CODE_NODE,
							nn27.getText() == null ? "" : nn27.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_U_NODE,
							nn28.getText() == null ? "" : nn28.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_D_NODE,
							nn29.getText() == null ? "" : nn29.getText());
					map_1.put(WorkOrderSa.ZY_ACCEP_R_NODE,
							nn30.getText() == null ? "" : nn30.getText());
					rtList1.add(map_1);
					
					Node nm1 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_STATU_NODE);
					Node nm2 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_STATU_NAME_NODE);
					Node nm3 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORDER_CODE_NODE);
					Node nm4 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORK_TYPE_NODE);
					Node nm5 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_ORDER_ID_NODE);
					Node nm6 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_STATUS_NODE);
					Node nm7 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_CON_BOK_DATE_NODE);
					Node nm9 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_BUSIN_TYPE_NODE);
					Node nm10 = ele_.selectSingleNode(rootPath1
							+ WorkOrderSa.ZY_WORKORDERID_NODE);
					
					Map<String, Object> map_2 = new HashMap<String, Object>();
					map_2.put(WorkOrderSa.ZY_WORDER_STATU_NODE,
							nm1.getText() == null ? "" : nm1.getText());
					map_2.put(WorkOrderSa.ZY_WORDER_STATU_NAME_NODE,
							nm2.getText() == null ? "" : nm2.getText());
					map_2.put(WorkOrderSa.ZY_WORDER_CODE_NODE,
							nm3.getText() == null ? "" : nm3.getText());
					map_2.put(WorkOrderSa.ZY_WORK_TYPE_NODE,
							nm4.getText() == null ? "" : nm4.getText());
					map_2.put(WorkOrderSa.ZY_ORDER_ID_NODE,
							nm5.getText() == null ? "" : nm5.getText());
					map_2.put(WorkOrderSa.ZY_STATUS_NODE,
							nm6.getText() == null ? "" : nm6.getText());
					map_2.put(WorkOrderSa.ZY_CON_BOK_DATE_NODE,
							nm7.getText() == null ? "" : nm7.getText());
					map_2.put(WorkOrderSa.ZY_BUSIN_TYPE_NODE,
							nm9.getText() == null ? "" : nm9.getText());
					System.out.println("nm10.getText():"+nm10);
					map_2.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nm10.getText() == null ? "" : nm10.getText());    
					rtList2.add(map_2);
					
					Node nmm1 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_NATUR_NODE);
					Node nmm2 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ACCESSMODE_NODE);
					Node nmm3 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ACCESS_TYPE_NODE);
					Node nmm4 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_CHARGE_NODE);
					Node nmm5 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_ACCO_NODE);
					Node nmm6 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PASS_NODE);
					Node nmm7 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_RATE_NODE);
					Node nmm8 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_RELA_NUM_NODE);
					Node nmm9 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BUSIN_PASS_NODE);
					Node nmm10 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVCOD_NEW_NODE);
					Node nmm11 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVCOD_OLD_NODE);
					Node nmm12 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_PHYCODE_NEW_NODE);
					Node nmm13 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_PHYCODE_OLD_NODE);
					Node nmm14 = ele_.selectSingleNode(rootPath2
							+WorkOrderSa.ZY_SECTYPE_NEW_NODE);
					Node nmm15 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SECTYPE_OLD_NODE);
					Node nmm16 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ROW_NODE);
					Node nmm17 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ROW_OLD_NODE);
					Node nmm18 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINE_NODE);
					Node nmm19 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINE_OLD_NODE);
					Node nmm20 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_AU_ROW_NODE);
					Node nmm21 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_AU_ROW_OLD_NODE);
					Node nmm22 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DA_ROW_NODE);
					Node nmm23 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DA_ROW_OLD_NODE);
					Node nmm24 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PORT_NODE);
					Node nmm25 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BROA_PORT_OLD_NODE);
					Node nmm26 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_TERM_INF_NODE);
					Node nmm27 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SPLI_DW_PORT_NODE);
					Node nmm28 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_TERM_UP_PORT_NODE);
					Node nmm29 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LO_SN_NUM_NODE);
					Node nmm30 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_NODE);
					Node nmm31 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_OLD_NODE);
					Node nmm32 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ADSL_TEST_NODE);
					Node nmm33 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_JBOX_INF_NODE);
					Node nmm34 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_LINK_INF_NEW_NODE);
					Node nmm35 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_SIP_PASSWORD_NODE);
					Node nmm36 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IPTV_PASSWORD_NODE);
					Node nmm37 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_EXCH_NAME_NODE);
					Node nmm38 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_NET_ACCO_NODE);
					Node nmm39 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_RANGE_ADR_NODE);
					Node nmm40 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_ODB_NAME_NODE);
					Node nmm41 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_OLT_SB_IP_ADR_NODE);
					Node nmm42 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IS_FTTH_NODE);
					Node nmm43 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVICE_CLASS_NODE);
					Node nmm44 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_DEVICE_SOURCE_NODE);
					Node nmm45 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_IS_FLOW_ZLHT_NODE);
					Node nmm46 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_WORKORDERID_NODE);
					Node nmm47 = ele_.selectSingleNode(rootPath2
							+ WorkOrderSa.ZY_BUSIN_TYPE_NODE);
					
					Map<String, Object> map_3 = new HashMap<String, Object>();
					map_3.put(WorkOrderSa.ZY_NATUR_NODE,
							nmm1 == null ? "" : nmm1.getText());
					map_3.put(WorkOrderSa.ZY_ACCESSMODE_NODE,
							nmm2 == null ? "" : nmm2.getText());
					map_3.put(WorkOrderSa.ZY_ACCESS_TYPE_NODE,
							nmm3  == null ? "" : nmm3.getText());
					map_3.put(WorkOrderSa.ZY_CHARGE_NODE,
							nmm4  == null ? "" : nmm4.getText());
					map_3.put(WorkOrderSa.ZY_BROA_ACCO_NODE,
							nmm5  == null ? "" : nmm5.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PASS_NODE,
							nmm6  == null ? "" : nmm6.getText());
					map_3.put(WorkOrderSa.ZY_BROA_RATE_NODE,
							nmm7 == null ? "" : nmm7.getText());
					map_3.put(WorkOrderSa.ZY_RELA_NUM_NODE,
							nmm8  == null ? "" : nmm8.getText());
					map_3.put(WorkOrderSa.ZY_BUSIN_PASS_NODE,
							nmm9 == null ? "" : nmm9.getText());
					map_3.put(WorkOrderSa.ZY_DEVCOD_NEW_NODE,
							nmm10 == null ? "" : nmm10.getText());
					map_3.put(WorkOrderSa.ZY_DEVCOD_OLD_NODE,
							nmm11 == null ? "" : nmm11.getText());
					map_3.put(WorkOrderSa.ZY_PHYCODE_NEW_NODE,
							nmm12  == null ? "" : nmm12.getText());
					map_3.put(WorkOrderSa.ZY_PHYCODE_OLD_NODE,
							nmm13  == null ? "" : nmm13.getText());
					map_3.put(WorkOrderSa.ZY_SECTYPE_NEW_NODE,
							nmm14  == null ? "" : nmm14.getText());
					map_3.put(WorkOrderSa.ZY_SECTYPE_OLD_NODE,
							nmm15  == null ? "" : nmm15.getText());
					map_3.put(WorkOrderSa.ZY_ROW_NODE,
							nmm16  == null ? "" : nmm16.getText());
					map_3.put(WorkOrderSa.ZY_ROW_OLD_NODE,
							nmm17  == null ? "" : nmm17.getText());
					map_3.put(WorkOrderSa.ZY_LINE_NODE,
							nmm18 == null ? "" : nmm18.getText());
					map_3.put(WorkOrderSa.ZY_LINE_OLD_NODE,
							nmm19 == null ? "" : nmm19.getText());
					map_3.put(WorkOrderSa.ZY_AU_ROW_NODE,
							nmm20 == null ? "" : nmm20.getText());
					map_3.put(WorkOrderSa.ZY_AU_ROW_OLD_NODE,
							nmm21  == null ? "" : nmm21.getText());
					map_3.put(WorkOrderSa.ZY_DA_ROW_NODE,
							nmm22 == null ? "" : nmm22.getText());
					map_3.put(WorkOrderSa.ZY_DA_ROW_OLD_NODE,
							nmm23  == null ? "" : nmm23.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PORT_NODE,
							nmm24  == null ? "" : nmm24.getText());
					map_3.put(WorkOrderSa.ZY_BROA_PORT_OLD_NODE,
							nmm25 == null ? "" : nmm25.getText());
					map_3.put(WorkOrderSa.ZY_TERM_INF_NODE,
							nmm26 == null ? "" : nmm26.getText());
					map_3.put(WorkOrderSa.ZY_SPLI_DW_PORT_NODE,
							nmm27 == null ? "" : nmm27.getText());
					map_3.put(WorkOrderSa.ZY_TERM_UP_PORT_NODE,
							nmm28  == null ? "" : nmm28.getText());
					map_3.put(WorkOrderSa.ZY_LO_SN_NUM_NODE,
							nmm29  == null ? "" : nmm29.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_NODE,
							nmm30  == null ? "" : nmm30.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_OLD_NODE,
							nmm31== null ? "" : nmm31.getText());
					map_3.put(WorkOrderSa.ZY_ADSL_TEST_NODE,
							nmm32  == null ? "" : nmm32.getText());
					map_3.put(WorkOrderSa.ZY_JBOX_INF_NODE,
							nmm33 == null ? "" : nmm33.getText());
					map_3.put(WorkOrderSa.ZY_LINK_INF_NEW_NODE,
							nmm34  == null ? "" : nmm34.getText());
					map_3.put(WorkOrderSa.ZY_SIP_PASSWORD_NODE,
							nmm35 == null ? "" : nmm35.getText());
					map_3.put(WorkOrderSa.ZY_IPTV_PASSWORD_NODE,
							nmm36  == null ? "" : nmm36.getText());
					map_3.put(WorkOrderSa.ZY_EXCH_NAME_NODE,
							nmm37  == null ? "" : nmm37.getText());
					map_3.put(WorkOrderSa.ZY_NET_ACCO_NODE,
							nmm38 == null ? "" : nmm38.getText());
					map_3.put(WorkOrderSa.ZY_RANGE_ADR_NODE,
							nmm39  == null ? "" : nmm39.getText());
					map_3.put(WorkOrderSa.ZY_ODB_NAME_NODE,
							nmm40 == null ? "" : nmm40.getText());
					map_3.put(WorkOrderSa.ZY_OLT_SB_IP_ADR_NODE,
							nmm41 == null ? "" : nmm41.getText());
					map_3.put(WorkOrderSa.ZY_IS_FTTH_NODE,
							nmm42  == null ? "" : nmm42.getText());
					map_3.put(WorkOrderSa.ZY_DEVICE_CLASS_NODE,
							nmm43  == null ? "" : nmm43.getText());
					map_3.put(WorkOrderSa.ZY_DEVICE_SOURCE_NODE,
							nmm44  == null ? "" : nmm44.getText());
					map_3.put(WorkOrderSa.ZY_IS_FLOW_ZLHT_NODE,
							nmm45  == null ? "" : nmm45.getText());
					map_3.put(WorkOrderSa.ZY_WORKORDERID_NODE,
							nmm46  == null ? "" : nmm46.getText());
					map_3.put(WorkOrderSa.ZY_BUSIN_TYPE_NODE,
							nmm47 == null ? "" : nmm47.getText());
					rtList3.add(map_3);
				}

				
//				List<Map<String, Object>> rtList1 = new ArrayList<Map<String, Object>>(
//						size);//定单信息
//				List<Map<String, Object>> rtList2 = new ArrayList<Map<String, Object>>(
//						size);//工单信息
//				List<Map<String, Object>> rtList3 = new ArrayList<Map<String, Object>>(
//						size);//资源信息
				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderSa.ORDER_LIST_NODE, rtList1);
				resultData.put(WorkOrderSa.WORK_ORDER_LIST_NODE, rtList2);				
				resultData.put(WorkOrderSa.RES_LIST_NODE, rtList3);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());

				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} catch (Exception ex) {
			ex.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}
}
