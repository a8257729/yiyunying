package com.ztesoft.mobile.v2.service.workform.xinjiang.bz;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;
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
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAO;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAOImpl;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.common.StaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimOrgInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimStaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.AcceptFaultOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderDetailBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.service.workform.XmlUtil;
import com.ztesoft.mobile.v2.service.workform.xinjiang.WebService;
import com.ztesoft.mobile.v2.util.DateTimeUtils;

@Service("workOrderXjBzService")
public class WorkOrderBzServiceImpl extends BaseService implements
		WorkOrderBzService {

	private static final boolean debug = false;

	private static final Logger logger = Logger
			.getLogger(WorkOrderBzServiceImpl.class);

	private static final String[] PARAM_NAME = { "infType", "requestxml" };
	/** 保障待办查询 */
	private static final String QUERY_SA_WORKORDER_INTERF = "querySaWorkOrderForEBiz";
	/** 保障私有代办查询 */
	private static final String QUERY_SA_PRIVATE_WORKORDER_INTERF = "querySaPrivateWorkOrderForEBiz";
	/** 保障共享代办查询 */
	private static final String QUERY_SA_PUBLIC_WORKORDER_INTERF = "querySaPublicWorkOrderForEBiz";
	/** 保障代办详情 */
	private static final String QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF = "querySaWorkOrderDetailForEBiz";
	/** 保障代办详情（公有） */
	private static final String QUERY_WORKORDER_DETAIL_INTERF = "queryWorkOrderDetailForEBiz";
	/** 退单列表 */
	private static final String CANCEL_WORKORDER_INTERF = "returnWorkOrderForEBiz";
	/** 退单原因列表 */
	private static final String CANCEL_WORKORDER_REASON_INTERF = "returnReasonListForEBiz";
	/** 回单 */
	private static final String FINISH_WORKORDER_INTERF = "submitFinishSaWorkOrder";
	/** 保障回单数据加载 */
	private static final String LOAD_REPLY_WORKORDER_DATA_INTERF = "loadFinishWorkOrderViewData";
	/** 保障接单 */
	private static final String ACCEPT_FAULT_WORKORDER_INTERF = "acceptFaultWorkOrderForEBiz";
	/** 保障代办详情 */
	private static final String QUERY_PNET_WORKORDER_DETAIL_INTERF = "queryWorkDetailForPnet";
	/** 保障签单 */
	private static final String SIGN_FAULT_WORKORDER_DETAIL_INTERF = "SignWorkOrderForEBiz";
	/** * 指派授权人员查询*/
	private static final String QUERY_DO_DESIGNNATE_FOR_EBIZ = "queryDoDesignateForEBiz";
	/** * 加派授权人员查询 */
	private static final String QUERY_ADD_DISPATHCH_FOR_EBIZ = "queryAddDispatchForEBiz";
	/** * 转派 授权人员查询*/
	private static final String QUERY_TRAN_DESTION_FOR_EBIZ= "queryTransDestionForEBiz";

	/** * 保障工单监控 -- 查询 */
	private static final String QUERYSAWORKORDERFORMONITOR = "querySaWorkOrderForMonitor";

	/***保障工单监控--详情*/
	private static final String QRYSAWORKORDERDETAILFOREBIZ = "qrySaWorkOrderDetailForEBiz";


	// 调用接口服务
	private static final EBizToIomWebservice service = new EBizToIomWebservice();
	private static final WebService ws = new WebService();

	private String callWsService(String method, String reqXml) throws Exception {
		return service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE,
				WS_METHOD_OPERATION_NAME, PARAM_NAME, new String[] { method,
						reqXml });
	}

	public Result selSaPrivateWorkOrderByPage(String username, Long jobId,
											  Integer pageSize, Integer pageIndex) {
		Result result = null;

		try {
			String reqXml = getSaPrivateWorkOrderRequest(username, jobId,
					pageSize, pageIndex);
			String respXml = "";
			//
			if (debug){
				respXml = XmlUtil.getRemotelXmlData(QUERY_SA_PRIVATE_WORKORDER_INTERF);
			} else {
				respXml = callWsService(QUERY_SA_PRIVATE_WORKORDER_INTERF,
						reqXml);
			}
			result = parseSaPrivateWorkOrderResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	public Result selSaPublicWorkOrderByPage(String username, Long jobId,
											 Integer pageSize, Integer pageIndex) {
		Result result = null;

		try {
			String reqXml = getSaPublicWorkOrderRequest(username, jobId,
					pageSize, pageIndex);
			String respXml = "";//callWsService(QUERY_SA_PUBLIC_WORKORDER_INTERF,
//					reqXml);
			//
			if (debug){
				respXml = XmlUtil.getRemotelXmlData(QUERY_SA_PUBLIC_WORKORDER_INTERF);
			} else {
				respXml = callWsService(QUERY_SA_PUBLIC_WORKORDER_INTERF,
						reqXml);
			}
			result = parseSaPublicWorkOrderResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	public Result selSaWorkOrderByPage(String username, Long jobId,
									   Integer pageSize, Integer pageIndex) {
		/*
		 * Result result = null;
		 *
		 * try { String reqXml = getSaWorkOrderRequest(username, jobId,
		 * pageSize, pageIndex); String respXml =
		 * callWsService(QUERY_SA_PRIVATE_WORKORDER_INTERF, reqXml); // result =
		 * parseWorkOrderResponse(respXml); } catch (Exception e) { if
		 * (logger.isDebugEnabled()) { logger.debug(e.getMessage()); } // result =
		 * Result.buildInvokeInterError(); }
		 *
		 * return result;
		 */
		throw new RuntimeException("未实现");
	}

	/** 拼装报文 */
	private String getLoadReplayOrderRequest(String username, String workOrderId) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				LOAD_REPLY_WORKORDER_DATA_INTERF).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<WorkOrderID>").append(
				workOrderId).append("</WorkOrderID>").append(
				"</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障加载回单数据的请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	/** 拼装私有工单查询报文 */
	private String getSaPrivateWorkOrderRequest(String username, Long jobId,
												Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PRIVATE_WORKORDER_INTERF).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr></ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("故障私有工单查询请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	/** 拼装共享工单详情报文 */
	private String getSaPublicWorkOrderRequest(String username, Long jobId,
											   Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PUBLIC_WORKORDER_INTERF).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr></ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障工单查询请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	/** 拼装故障工单监控详情报文 */
	private String getSaWorkOrderForMonitorRequest(String username, Long jobId,
												   Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PUBLIC_WORKORDER_INTERF).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr></ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障工单查询请求报文: " + sb.toString());
		}

		return sb.toString();
	}


	private String getWorkOrderDetailRequest(Long workOrderId) {

		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF).append(
				"</QueryMethod>").append("<SERIALNUMBER></SERIALNUMBER>")
				.append("<Params>").append("<WorkOrderID>").append(workOrderId)
				.append("</WorkOrderID>").append("</Params>").append("</Data>")
				.append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障工单详情请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	private Result parseLoadReplyOrderResponse(String retXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障加载回单数据的响应报文: " + retXml);
		}

		Result result = null;
		String rtFlag;

		try {
			SAXReader reader = new SAXReader();
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element Data = doc.getRootElement();
			Element Return = Data.element("Return");

			// 获取返回代码及描述信息
			rtFlag = Return.element("Result").getText();

			if ("000".equals(rtFlag)) { // 成功

				String recoverConfirmStaff;
				String confirmTel;
				String recoverTime;
				String alarmState;

				Map<Object, Object> resultData = new HashMap<Object, Object>();

				Element Content = Return.element("Content");
				Element RepairReasonListEle = Content
						.element("RepairReasonList");
				Element WorkOperListEle = Content.element("WorkOperList");
				Element TimeOutReasonListEle = Content
						.element("TimeOutReasonList");

				List timeOutReasonList = TimeOutReasonListEle
						.elements("TimeOutReason");
				List timeOutReasonList_ = new ArrayList();

				for (int i = 0; i < timeOutReasonList.size(); i++) {
					Element timeOutEle = (Element) timeOutReasonList.get(i);
					List timeOutColList = timeOutEle.elements();
					Map colMap = new HashMap();
					for (int j = 0; j < timeOutColList.size(); j++) {
						Element e = (Element) timeOutColList.get(j);
						colMap.put(e.getName(), e.getStringValue());

					}
					timeOutReasonList_.add(colMap);

				}

				List reasonList = RepairReasonListEle.elements("RepairReason");
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

				List workOperList = WorkOperListEle.elements("WorkOper");
				List workOperList_ = new ArrayList();

				for (int i = 0; i < workOperList.size(); i++) {
					Element reason = (Element) workOperList.get(i);
					List workOperColList = reason.elements();
					Map colMap = new HashMap();
					for (int j = 0; j < workOperColList.size(); j++) {
						Element e = (Element) workOperColList.get(j);
						colMap.put(e.getName(), e.getStringValue());

					}
					workOperList_.add(colMap);
				}

				recoverConfirmStaff = Content.element("RepairLinkMan")
						.getStringValue();
				confirmTel = Content.element("RepairLinkManTel")
						.getStringValue();
				recoverTime = Content.element("RepairTime").getStringValue();
				alarmState = Content.element("AlarmState").getStringValue();

				resultData.put("RECOVER_REASON_LIST", reasonList_);
				resultData.put("TIME_OUT_REASON_LIST", timeOutReasonList_);
				resultData.put("MAINTAIN_STAFF_LIST", workOperList_);
				resultData.put("RECOVER_CONFIRM_STAFF", recoverConfirmStaff);
				resultData.put("CONFIRM_TEL", confirmTel);
				resultData.put("RECOVER_TIME", recoverTime);
				resultData.put("ALARM_STATE", alarmState);

				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (Exception ex) {
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	private Result parseReplyOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障回单响应报文: " + respXml);
		}

		Result result = Result.buildSuccess();
//
//		try {
//			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");
//
//			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
//			String resultVal = resultNode.getText();
//
//			if ("000".equals(resultVal)) { // 成功
//
//				String rootPath = "/Data/Return/ContentList/Content/Public/";
//
//				// Map<Object, Object> resultData = new HashMap<Object,
//				// Object>();
//				// resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);
//
//				result = Result.buildSuccess();
//
//			} else { // 失败
//				Node errorDescNode = doc
//						.selectSingleNode("/Data/Return/ErrorDesc");
//				String msg = errorDescNode.getText();
//				result = Result.buildInterInfoError(msg);
//			}
//
//		} catch (XMLDocException e) {
//			e.printStackTrace();
//
//			if (logger.isDebugEnabled()) {
//				logger.debug(e.getMessage());
//			}
//
//			result = Result.buildWSXmlParsedError();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

		return result;
	}

	/** 解析故障接单返回报文 */
	private Result parseAcceptOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("故障接单响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功
				// String rootPath = "/Data/Return/ContentList/Content/Public/";
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

	private Result parseCancelOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障退单响应报文: " + respXml);
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
				// resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

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

	private Result parsePublicOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障操作响应报文: " + respXml);
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
				// resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

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

	//
	private Result parsePnetWorkOrderResponse(String respXml) {

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				//String rootPath = "/Data/Return/ContentList/Content/Public/";
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
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORKORDER_ID_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_CODE_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACC_NBR_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_TITLE_NODE);
//					Node nn8 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.ORDER_STATE_NAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_STATE_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FIRST_DEAL_TIME_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FAULT_PHENOM_COMMENT_NODE);

					Node nn16 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_NAME_NODE);
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_TEL_NODE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_CODE_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ISPAUSE_NODE);

					Map<String, Object> map_ = new HashMap<String, Object>();

					map_.put(WorkOrderBz.WORKORDER_ID_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderBz.WORK_ORDER_CODE_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderBz.ACC_NBR_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderBz.ORDER_TITLE_NODE,
							nn6.getText() == null ? "" : nn6.getText());

					map_.put(WorkOrderBz.WORK_ORDER_STATE_NODE, nn9
							.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderBz.FIRST_DEAL_TIME_NODE,
							nn12.getText() == null ? "" : nn12.getText());

					map_.put(WorkOrderBz.FAULT_PHENOM_COMMENT_NODE, nn15
							.getText() == null ? "" : nn15.getText());

					map_.put(WorkOrderBz.CONTACT_NAME_NODE,
							nn16.getText() == null ? "" : nn16.getText());
					map_.put(WorkOrderBz.CONTACT_TEL_NODE,
							nn17.getText() == null ? "" : nn17.getText());
					map_.put(WorkOrderBz.ORDER_CODE_NODE,
							nn18.getText() == null ? "" : nn18.getText());
					map_.put(WorkOrderKt.ISPAUSE_NODE,
							nn19.getText() == null ? "" : nn19.getText());

					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);
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
		}

		return result;
	}

	/** 解析共享工单查询报文   故障待办（公有） */
	private Result parseSaPublicWorkOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障共享工单查询响应报文: " + respXml);
		}

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
//					Node nn1 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.PARTY_TYPE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORKORDER_ID_NODE_2);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_CODE_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.PROF_TYPE_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACC_NBR_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_TITLE_NODE);
//					Node nn7 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.URGE_NUM_NODE);
//					Node nn8 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.ORDER_STATE_NAME_NODE);
//					Node nn9 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.WORK_ORDER_STATE_NAME_NODE);
//					Node nn10 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.DEAL_MAN_NAME_NODE);
//
//					Node nn11 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.REPEAT_NUM_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FIRST_DEAL_TIME_NODE);

//					Node nn13 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.LIMIT_VALUE_NODE);
//					Node nn14 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.LEFT_VALUE_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FAULT_PHENOM_COMMENT_NODE);

					Node nn16 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_NAME_NODE);  //联系人
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_TEL_NODE);//联系电话
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_CODE_NODE);//
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE); //故障现象

					Map<String, Object> map_ = new HashMap<String, Object>();
//					map_.put(WorkOrderBz.PARTY_TYPE_NODE,
//							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderBz.WORKORDER_ID_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderBz.WORK_ORDER_CODE_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderBz.PROF_TYPE_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderBz.ACC_NBR_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderBz.ORDER_TITLE_NODE,
							nn6.getText() == null ? "" : nn6.getText());
//					map_.put(WorkOrderBz.URGE_NUM_NODE,
//							nn7.getText() == null ? "" : nn7.getText());
//					map_.put(WorkOrderBz.ORDER_STATE_NAME_NODE,
//							nn8.getText() == null ? "" : nn8.getText());
//					map_.put(WorkOrderBz.WORK_ORDER_STATE_NAME_NODE, nn9
//							.getText() == null ? "" : nn9.getText());
//					map_.put(WorkOrderBz.DEAL_MAN_NAME_NODE,
//							nn10.getText() == null ? "" : nn10.getText());
//
//					map_.put(WorkOrderBz.REPEAT_NUM_NODE,
//							nn11.getText() == null ? "" : nn11.getText());

					String createDate="";
					if(nn12!=null&&nn12.getText().indexOf(".") > -1){
						createDate = nn12.getText().substring(0, nn12.getText().indexOf("."));
					}else
						createDate=nn12.getText() == null ? "" : nn12.getText();
					map_.put(WorkOrderBz.FIRST_DEAL_TIME_NODE,createDate);
//					map_.put(WorkOrderBz.LIMIT_VALUE_NODE,
//							nn13.getText() == null ? "" : nn13.getText());
//					map_.put(WorkOrderBz.LEFT_VALUE_NODE,
//							nn14.getText() == null ? "" : nn14.getText());
					map_.put(WorkOrderBz.FAULT_PHENOM_COMMENT_NODE, nn15
							.getText() == null ? "" : nn15.getText());

					map_.put(WorkOrderBz.CONTACT_NAME_NODE,
							nn16.getText() == null ? "" : nn16.getText());
					map_.put(WorkOrderBz.CONTACT_TEL_NODE,
							nn17.getText() == null ? "" : nn17.getText());
//					map_.put(WorkOrderBz.ORDER_CODE_NODE,
//							nn18.getText() == null ? "" : nn18.getText());
					map_.put(WorkOrderBz.ALERT_STATE,
							nn19.getText() == null ? "" : nn19.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderBz.TOTAL_COUNT_NODE,
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

	/** 解析私有工单查询报文  故障待办（私有） */
	private Result parseSaPrivateWorkOrderResponse(String respXml) {
		logger.error("保障私有工单查询响应报文: " + respXml);
		if (logger.isDebugEnabled()) {
			logger.debug("保障私有工单查询响应报文: " + respXml);
		}

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
//					Node nn1 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.PARTY_TYPE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORKORDER_ID_NODE_2);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_CODE_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.PROF_TYPE_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACC_NBR_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CUST_LINK_PERSON_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CUST_LINK_PHONE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ADDRESS_NODE);
//					Node nn9 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.FAULT_DESC_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.BOK_TIME_NODE);

					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FAULT_PHENOM_COMMENT_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_TITLE_NODE);

					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_STATE_NODE);
//					Node nn14 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.WORK_ORDER_STATE_NAME_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_STATE_NODE);
//					Node nn16 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.ORDER_STATE_NAME_NODE);

					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_CODE_NODE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FIRST_DEAL_TIME_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.BOOKSTATE_NODE);
					Node nn21 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CREATE_DATE_NODE);
					Node nn22 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACCEPT_DATE_NODE);



					Map<String, Object> map_ = new HashMap<String, Object>();
//					map_.put(WorkOrderBz.PARTY_TYPE_NODE,
//							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderBz.WORKORDER_ID_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderBz.WORK_ORDER_CODE_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderBz.PROF_TYPE_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderBz.ACC_NBR_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderBz.CUST_LINK_PERSON_NODE,
							nn6.getText() == null ? "" : nn6.getText());
					map_.put(WorkOrderBz.CUST_LINK_PHONE_NODE,
							nn7.getText() == null ? "" : nn7.getText());
					map_.put(WorkOrderBz.ADDRESS_NODE,
							nn8.getText() == null ? "" : nn8.getText());
//					map_.put(WorkOrderBz.FAULT_DESC_NODE,
//							nn9.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderBz.BOK_TIME_NODE,nn10.getText() == null ? "" : nn10.getText());

					map_.put(WorkOrderBz.FAULT_PHENOM_COMMENT_NODE, nn11
							.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderBz.ORDER_TITLE_NODE,
							nn12.getText() == null ? "" : nn12.getText());

//					map_.put(WorkOrderBz.WORK_ORDER_STATE_NODE,
//							nn13.getText() == null ? "" : nn13.getText());
//					map_.put(WorkOrderBz.WORK_ORDER_STATE_NAME_NODE, nn14
//							.getText() == null ? "" : nn14.getText());
//					map_.put(WorkOrderBz.ORDER_STATE_NODE,
//							nn15.getText() == null ? "" : nn15.getText());
//					map_.put(WorkOrderBz.ORDER_STATE_NAME_NODE,
//							nn16.getText() == null ? "" : nn16.getText());
//					map_.put(WorkOrderBz.ORDER_CODE_NODE,
//							nn17.getText() == null ? "":nn17.getText());
//					String createDate="";
//					if(nn18!=null&&nn18.getText().indexOf(".") > -1){
//						createDate = nn18.getText().substring(0, nn18.getText().indexOf("."));
//					}else
//						createDate=nn18.getText() == null ? "" : nn18.getText();
//					map_.put(WorkOrderBz.FIRST_DEAL_TIME_NODE, createDate);
//					map_.put(WorkOrderBz.ALERT_STATE,
//							nn19.getText() == null ? "" : nn19.getText());
					//				map_.put(WorkOrderKt.BOOKSTATE_NODE,
					//				nn20.getText() == null ? "" : nn20.getText());
					map_.put("beSpeakState",nn20.getText() == null ? "" : nn20.getText());
					map_.put(WorkOrderKt.CREATE_DATE_NODE,
							nn21 == null ? "" : nn21.getText());
					map_.put(WorkOrderKt.ACCEPT_DATE_NODE,
							nn22 == null ? "" : nn22.getText());

					rtList.add(map_);

				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderBz.TOTAL_COUNT_NODE,
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

	private Result parseWorkOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障工单查询响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				//String rootPath = "/Data/Return/ContentList/Content/Public/";
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
							+ WorkOrderBz.WORK_ORDER_ID_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_CODE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.SERVICE_NAME_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACC_NBR_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CUST_NAME_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CUST_LINK_PERSON_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CUST_LINK_PHONE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.TACHE_NAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.TACHE_CODE_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ADDRESS_NODE);

					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.SLATIME_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_TYPE_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CREATE_DATE_NODE);

					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderBz.WORK_ORDER_ID_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderBz.ORDER_CODE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderBz.SERVICE_NAME_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderBz.ACC_NBR_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderBz.CUST_NAME_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderBz.CUST_LINK_PERSON_NODE,
							nn6.getText() == null ? "" : nn6.getText());
					map_.put(WorkOrderBz.CUST_LINK_PHONE_NODE,
							nn7.getText() == null ? "" : nn7.getText());
					map_.put(WorkOrderBz.TACHE_NAME_NODE,
							nn8.getText() == null ? "" : nn8.getText());
					map_.put(WorkOrderBz.TACHE_CODE_NODE,
							nn9.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderBz.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

					map_.put(WorkOrderBz.SLATIME_NODE,
							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderBz.WORK_ORDER_TYPE_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					map_.put(WorkOrderBz.CREATE_DATE_NODE,
							nn13.getText() == null ? "" : nn13.getText());

					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

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
		}

		return result;
	}

	/** 解析共享工单详情报文 */
	private Result parseSaPublicWorkOrderDetailResponse(String respXml,Long workOrderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障公有工单详情响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_ACCEPT_TIME_NODE);
				Node nn2 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CUST_GRADE_NODE);
				Node nn3 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERV_GRADE_NODE);
				Node nn4 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_TITLE_NODE);
				Node nn5 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_USER_ADDR_NODE);
				Node nn6 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERVICE_NBR_NODE);
				Node nn7 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_PHENOM_NODE);
				Node nn8 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_FAULT_DESC_NODE);
				Node nn9 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_LIMIT_TIME_NODE);
				Node nn10 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SUB_TIME_NODE);
				Node nn11 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_USER_NAME_NODE);
				Node nn12 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_USER_TEL_NODE);
				Node nn13 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CONTACT_NAME_NODE);
				Node nn14 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CONTACT_TEL_NODE);
				Node nn15 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CODE_NODE);
				Node nn16 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_URGE_NUM_NODE);
				Node nn17 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_STATE_NAME_NODE);
				Node nn18 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_DEAL_MAN_NODE);
				Node nn19 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_ACCEPT_STAFF_NODE);

				WorkOrderDetailBz detail = new WorkOrderDetailBz();

				detail.setWorkOrderAcceptTime(nn1.getText());
				detail.setWorkOrderCustGrade(nn2.getText());
				detail.setWorkOrderServGrade(nn3.getText());
				detail.setWorkOrderTitle(nn4.getText());
				detail.setWorkOrderAddress(nn5.getText());
				detail.setWorkOrderServiceNbr(nn6.getText());
				detail.setWorkOrderPhenomenon(nn7.getText());
				detail.setWorkOrderDesc(nn8.getText());
				detail.setWorkOrderLimitTime(nn9.getText());
				detail.setWorkOrderSubscribeTime(nn10.getText());
				detail.setWorkOrderCustName(nn11.getText());
				detail.setWorkOrderCustTel(nn12.getText());
				detail.setWorkOrderCustLinkPerson(nn13.getText());
				detail.setWorkOrderCustLinkPhone(nn14.getText());
//				detail.setWorkOrderCode(nn15.getText());
				detail.setWorkOrderUrgeNum(nn16.getText());
				detail.setWorkOrderState(nn17.getText());
				detail.setWorkOrderDealMan(nn18.getText());
				detail.setWorkOrderAcceptStaff(nn19.getText());

				try
				{
					//故障单字段补充
					Map<String,String> orderExtMap =  getMobileCommonDAO().qryFaultOrderExt(workOrderId.toString());
					detail.setFaultOrderType(orderExtMap.get("fault_order_type"));
					detail.setFaultType(orderExtMap.get("fault_type"));
					detail.setCustDef(orderExtMap.get("cust_def"));
					detail.setCustTyple(orderExtMap.get("cust_typle"));
				}catch (Exception e)
				{
					e.printStackTrace();
					detail.setFaultOrderType("");
					detail.setFaultType("");
					detail.setCustDef("");
					detail.setCustTyple("");
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderDetailBz.WORK_ORDER_DETAIL_NODE, detail);

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



	/** 解析私有工单详情报文 */
	private Result parseSaPrivateWorkOrderDetailResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("保障私有工单详情响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_ACCEPT_TIME_NODE);
				Node nn2 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CUST_GRADE_NODE);
				Node nn3 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERV_GRADE_NODE);
				Node nn4 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_TITLE_NODE);
				Node nn5 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_USER_ADDR_NODE);
				Node nn6 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERVICE_NBR_NODE);
				Node nn7 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_PHENOM_NODE);
				Node nn8 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_FAULT_DESC_NODE);
				Node nn9 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_LIMIT_TIME_NODE);
				Node nn10 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SUB_TIME_NODE);
				Node nn11 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CONTACT_NAME_NODE);

				WorkOrderDetailBz detail = new WorkOrderDetailBz();

				detail.setWorkOrderAcceptTime(nn1.getText());
				detail.setWorkOrderCustGrade(nn2.getText());
				detail.setWorkOrderServGrade(nn3.getText());
				detail.setWorkOrderTitle(nn4.getText());
				detail.setWorkOrderAddress(nn5.getText());
				detail.setWorkOrderServiceNbr(nn6.getText());
				detail.setWorkOrderPhenomenon(nn7.getText());
				detail.setWorkOrderDesc(nn8.getText());
				detail.setWorkOrderLimitTime(nn9.getText());
				detail.setWorkOrderSubscribeTime(nn10.getText());
				detail.setWorkOrderCustName(nn11.getText());

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData
						.put(WorkOrderDetailBz.WORK_ORDER_DETAIL_NODE, detail);

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

	public Result selSaPublicWorkOrderDetail(Long workOrderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("工单ID: " + workOrderId);
		}

		Result result = null;
		try {
			String reqXml = getWorkOrderDetailRequest(workOrderId);
			String respXml = callWsService(QUERY_WORKORDER_DETAIL_INTERF,
					reqXml);
			//
			result = parseSaPublicWorkOrderDetailResponse(respXml,workOrderId);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	public Result selSaPrivateWorkOrderDetail(Long workOrderId) {
		if (logger.isDebugEnabled()) {
			logger.debug("工单ID: " + workOrderId);
		}

		Result result = null;
		try {
			String reqXml = getWorkOrderDetailRequest(workOrderId);
			String respXml="";
			if (debug){
				respXml = XmlUtil.getRemotelXmlData(QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF);
			} else {

				respXml = callWsService(
						QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF, reqXml);
			}
			//
			result = parseSaPublicWorkOrderDetailResponse(respXml,workOrderId);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}



	public Result cancelOrderReason(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderBz.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					CANCEL_WORKORDER_REASON_INTERF);

			String retXml = callWsService(CANCEL_WORKORDER_REASON_INTERF,
					reqXml);

			resultData = ws.getReasonResultData(retXml);

			result = Result.buildSuccess();
			result.setResultData(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}

	public Result loadReplyFaultOrderData(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.optString(WorkOrderBz.WORK_ORDER_ID_NODE);
			String username = json.optString(WorkOrderBz.USE_NAME_NODE);

			String reqXml = this.getLoadReplayOrderRequest(username,
					workOrderId);

			String retXml = callWsService(LOAD_REPLY_WORKORDER_DATA_INTERF,
					reqXml);

			result = this.parseLoadReplyOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}

	public Result cancelOrder(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.getString(WORKORDER_ID_NODE);
			String cancelReasonId = json.getString(CANCEL_REASON_ID_NODE);
			String cancelReason = json.getString(CANCEL_REASON_NODE);
			// String cancelStaff = json.getString(STAFF_ID_NODE);
			String cancelUserName = json.getString(USER_NAME_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<UseName>" + cancelUserName + "</UseName>");
			sbReqXml.append("<ErroReason>" + cancelReasonId + "</ErroReason>");
			sbReqXml.append("<Comments>" + cancelReason + "</Comments>");
			sbReqXml.append("<FinishTime>" + DateTimeUtils.now()
					+ "</FinishTime>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					CANCEL_WORKORDER_INTERF);
			String retXml = this.callWsService(CANCEL_WORKORDER_INTERF, reqXml);

			result = parseCancelOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}

	@Deprecated
	public Result workOrderViewData(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WORKORDER_ID_NODE);
			String userName = json.getString(USER_NAME_NODE);

			String qryMethod = "loadFinishWorkOrderViewData";
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<UseName>" + userName + "</UseName>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(), qryMethod);
			String retXml = ws.call(reqXml);

			resultData = ws.getWorkOrderViewResultData(retXml);

			result = Result.buildSuccess();
			result.setResultData(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}

	private MobileCommonDAO getMobileCommonDAO() {
		String daoName = MobileCommonDAOImpl.class.getName();
		return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	public Result replyFaultOrder(JSONObject json)  {
		Result result = null;
		String staffArea = json.optString("staffArea", "");
		String repairResonName = json.optString("repairResonName", "");


		//
		String workOrderId = json.optString(WORKORDER_ID_NODE, "");
		String orderType = json.optString(ORDER_TYPE_NODE, "");

		String workOrderCode = json.optString(WORKORDER_CODE_NODE, "");
		String isReturn = json.optString(AUTO_RETURN_VISIT_NODE, "");
		String isVisit = json.optString(IS_VISIT_NODE, "");
		String recoverTime = json.optString(RECOVER_TIME_NODE, "");
		String recoverReasonId = json.optString(RECOVER_REASON_ID_NODE, "");
		//String recoverReasonName = json.optString(RECOVER_REASON_NAME_NODE, "");
		String maintainStaffId = json.optString(MAINTAIN_STAFF_ID_NODE, "");
		String arriveTime = json.optString(ARRIVE_TIME_NODE, "");
		String recoverConfirmStaff = json.optString(RECOVER_CONFIRM_STAFF_NODE,
				"");
		String confirmTel = json.optString(CONFIRM_TEL_NODE, "");
		String desc = json.optString(DESC_NODE, "");
		String remark = json.optString(REMARK_NODE, "");
		String reliefRemark = json.optString(RELIEF_REMARK_NODE, "");
		String resChange = json.optString(RES_CHANGE_DESC_NODE, "");
		String timeOutReasonId = json.optString(TIME_OUT_REASON_ID_NODE, "");

		String yhym = json.optString(YHYM, "");

		Long trackHelpId = json.optLong(TRACK_HELP_ID_NODE, -1L);
		//String trackHelpName = json.optString(TRACK_HELP_NAME_NODE, "");
		//Long trackHelpOrgId = json.optLong(TRACK_HELP_ORG_ID_NODE, -1l);
		//String trackHelpProportion = json.optString(TRACK_HELP_PROPORTION_NODE,"");
		Map<String,Object> checkMap = null;
		try {
			checkMap = getWorkOrderDAO().checkFaultWorkOrderReply(yhym, workOrderId);

			if (checkMap !=null){
				String outFlag = MapUtils.getString(checkMap, "out_flag",null);
				if ("0".equals(outFlag)){
					//不能进行回单操作
					String outMsg = MapUtils.getString(checkMap, "outDesc",null);
					result = Result.buildWorkOrderError(outMsg);
					return result;
				}
			}
		} catch (DataAccessException throwables) {
			throwables.printStackTrace();
			result = Result.buildWorkOrderError("系统错误");
			return result;
		}
		//长沙需求，需要新增牵手分类
		if ("0731".equals(staffArea))
		{
			insertOmHandType(json);
		}


		try {
			// String isVisit = json.getString(IS_VISIT_NODE);
			// String autoReturnVist = json.getString(AUTO_RETURN_VISIT_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<RecoverTime>" + recoverTime + "</RecoverTime>");
			sbReqXml.append("<RecoverReasonId>" + recoverReasonId
					+ "</RecoverReasonId>");
			sbReqXml.append("<IsVisit>" + isVisit + "</IsVisit>");
			sbReqXml.append("<AutoReturnVisit>" + isReturn + "</AutoReturnVisit>");

			sbReqXml.append("<ConfirmManName>" + recoverConfirmStaff
					+ "</ConfirmManName>");
			sbReqXml
					.append("<ConfirmManTel>" + confirmTel + "</ConfirmManTel>");
			sbReqXml.append("<ArriveTime>" + arriveTime + "</ArriveTime>");
			sbReqXml.append("<Desc>" + desc + "</Desc>");
			sbReqXml.append("<Remark>" + remark + "</Remark>");
			sbReqXml.append("<ResChangeDes>" + resChange
					+ "</ResChangeDes>");
			sbReqXml.append("<ReliefRemark>" + reliefRemark
					+ "</ReliefRemark>");

			sbReqXml.append("<MaintainStaffId>" + maintainStaffId + "</MaintainStaffId>");
			sbReqXml.append("<OverTimeReasonId>" + timeOutReasonId
					+ "</OverTimeReasonId>");

			sbReqXml.append("<TrackStaffId>" + trackHelpId + "</TrackStaffId>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					FINISH_WORKORDER_INTERF);
			String retXml="";
			if (debug )
				retXml = XmlUtil.getRemotelXmlData(FINISH_WORKORDER_INTERF);
			else
				retXml = this.callWsService(FINISH_WORKORDER_INTERF, reqXml);

			result = this.parseReplyOrderResponse(retXml);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	private void insertOmHandType(JSONObject json) {

		Map paramMap = new HashMap();
		String handPathName = json.optString("handPathName", "");
		String handId = json.optString("handId", "");
		String accNbr = json.optString("accNbr", "");
		String staffId = json.optString("staffId", "");
		String workOrderId = json.optString(WORKORDER_ID_NODE, "");
		paramMap.put("handPathName",handPathName);
		paramMap.put("handId",handId);
		paramMap.put("accNbr",accNbr);
		paramMap.put("staffId",staffId);
		try{
			String orderId = getMobileCommonDAO().qryOrderId(workOrderId);;
			paramMap.put("orderId",orderId);
			getMobileCommonDAO().saveHandType(paramMap);;

		}catch(Exception e)
		{
			e.printStackTrace();
		}

	}


	public Result signFaultOrder(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.getString(WORKORDER_ID_NODE);
			String cancelReasonId = json.getString(CANCEL_REASON_ID_NODE);
			String cancelReason = json.getString(CANCEL_REASON_NODE);
			// String cancelStaff = json.getString(STAFF_ID_NODE);
			String cancelUserName = json.getString(USER_NAME_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<UseName>" + cancelUserName + "</UseName>");
			sbReqXml.append("<ErroReason>" + cancelReasonId + "</ErroReason>");
			sbReqXml.append("<Comments>" + cancelReason + "</Comments>");
			sbReqXml.append("<FinishTime>" + DateTimeUtils.now()
					+ "</FinishTime>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					CANCEL_WORKORDER_INTERF);
			String retXml = this.callWsService(CANCEL_WORKORDER_INTERF, reqXml);

			result = parseCancelOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}




	public Result acceptFaultOrder(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.optString(
					AcceptFaultOrder.WORKORDER_ID_NODE, "");
			// String staffId = json.optString(AcceptFaultOrder.STAFF_ID_NODE,
			// "");
			// String staffName =
			// json.optString(AcceptFaultOrder.STAFF_NAME_NODE, "");
			String username = "";

			if (debug) {
				username = "ck";
			} else {
				username = json.optString(AcceptFaultOrder.USERNAME_NODE, "");
			}

			String finishTime =DateTimeUtils.now();// json.optString(
			//AcceptFaultOrder.ACCEPT_TIME_NODE, "");
			String comments = json.optString(
					AcceptFaultOrder.ACCEPT_COMMENT_NODE, "");

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<Username>" + username + "</Username>");
			sbReqXml.append("<AcceptComment>" + comments + "</AcceptComment>");
			sbReqXml.append("<AcceptTime>" + finishTime + "</AcceptTime>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					ACCEPT_FAULT_WORKORDER_INTERF);

			if (logger.isDebugEnabled()) {
				logger.debug("故障接单请求报文: " + reqXml);
			}
			String retXml = "";
			if (debug){
				retXml = XmlUtil.getRemotelXmlData(ACCEPT_FAULT_WORKORDER_INTERF);
			} else {
				retXml = this.callWsService(ACCEPT_FAULT_WORKORDER_INTERF,
						reqXml);
			}

			result = parseAcceptOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}




	public Result selOrgList(Long areaId, Long orgId,Boolean isRoot) {
		if (logger.isDebugEnabled()) {
			logger.debug("参数AREA_ID, ORG_ID: " + areaId + "," + orgId);
		}

		Result result = null;

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {


			List<Map> orgList_ = getWorkOrderDAO().selOrgList(areaId, orgId, isRoot);

			List<SimOrgInfo> newOrgList_ = new ArrayList<SimOrgInfo>(orgList_
					.size());
			for (int i = 0; i < orgList_.size(); i++) {

				Map map = orgList_.get(i);
				Long vAreaId = MapUtils.getLong(map, "AREA_ID");
				Long vOrgId = MapUtils.getLong(map, "ORG_ID");
				String vOrgName = MapUtils.getString(map, "ORG_NAME");
				String vIsLeaf = MapUtils.getString(map, "IS_LEAF");
				String vNodeType = MapUtils.getString(map, "NODE_TYPE");

				SimOrgInfo item = new SimOrgInfo();
				item.setAreaId(vAreaId);
				item.setOrgId(vOrgId);
				item.setOrgName(vOrgName);
				item.setIsLeaf(vIsLeaf);
				item.setNodeType(vNodeType);

				newOrgList_.add(item);
			}

			resultData.put("ORG_LIST", newOrgList_);

			result = Result.buildSuccess(resultData);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}


	public Result selAuthStaffList(Long areaId, Long jobId, String type) {
		Result result = null;
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				type).append("</QueryMethod>")
				.append("<Params>").append("<AreaId>").append(areaId)
				.append("</AreaId>").append("<JobId>").append(jobId)
				.append("</JobId>")
				.append("</Params>").append("</Data>");

		if (logger.isDebugEnabled()) {
			logger.debug("指派授权人员查询请求报文: " + sb.toString());
		}
		try{
			/*
			String respXml = null;
			if(type.equalsIgnoreCase(QUERY_DO_DESIGNNATE_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff.xml");
				result=parseSelAuthStaffList(respXml);
			}else if(type.equalsIgnoreCase(QUERY_ADD_DISPATHCH_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff1.xml");
				result=parseSelAuthOrgList(respXml);
			}else if(type.equalsIgnoreCase(QUERY_TRAN_DESTION_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff2.xml");
				result=parseSelAuthOrgList(respXml);
			}
			*/
			String respXml = callWsService(type, sb.toString());
			result=parseSelAuthStaffList(respXml);

		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildInvokeInterError();
		}

		return result;
	}
	public Result selStaffList(Long orgId) {
		if (logger.isDebugEnabled()) {
			logger.debug("参数org_id: " + orgId);
		}

		Result result = null;

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {


			// 根据返回的Map列表进行赋值操作
			List<Map> staffList_  = getWorkOrderDAO()
					.selStaffList(orgId);

			List<SimStaffInfo> newStaffList_ = new ArrayList<SimStaffInfo>(
					staffList_.size());
			for (int i = 0; i < staffList_.size(); i++) {

				Map map = staffList_.get(i);
				Long staffId = MapUtils.getLong(map, "staff_id");
				String staffName = MapUtils.getString(map, "staff_name");
				String nodeType = MapUtils.getString(map, "node_type");

				SimStaffInfo item = new SimStaffInfo();
				item.setStaffId(staffId);
				item.setStaffName(staffName);
				item.setNodeType(nodeType);
				newStaffList_.add(item);
			}

			resultData.put("STAFF_LIST", newStaffList_);

			staffList_ = null;

			result = Result.buildSuccess(resultData);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}

	public Result selAuthStaffList(Long orgId, String type) {
		// TODO Auto-generated method stub
		Result result = null;
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				type).append("</QueryMethod>")
				.append("<Params>").append("<OrgId>").append(orgId)
				.append("</OrgId>")
				.append("</Params>").append("</Data>");

		if (logger.isDebugEnabled()) {
			logger.debug("指派授权人员查询请求报文: " + sb.toString());
		}
		try{
			/*
			String respXml = null;
			if(type.equalsIgnoreCase(QUERY_DO_DESIGNNATE_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff.xml");
				result=parseSelAuthStaffList(respXml);
			}else if(type.equalsIgnoreCase(QUERY_ADD_DISPATHCH_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff1.xml");
				result=parseSelAuthOrgList(respXml);
			}else if(type.equalsIgnoreCase(QUERY_TRAN_DESTION_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff2.xml");
				result=parseSelAuthOrgList(respXml);
			}
			*/
			String respXml = callWsService(type, sb.toString());
			result=parseSelAuthStaffList(respXml);

		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildInvokeInterError();
		}

		return result;


	}

	private Result parseSelAuthOrgList(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("加派授权组织查询响应报文: " + respXml);
		}
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/root/result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				List<Element> rtEleList = doc
						.selectNodes("/root/items/item");
				int size = rtEleList.size();
				List<SimOrgInfo> newStaffList_ = new ArrayList<SimOrgInfo>(size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					ele_.attributeValue("id");
					logger.info("id======"+ele_.attributeValue("id"));
					logger.info("text======"+ele_.getText());
					SimOrgInfo item = new SimOrgInfo();
					item.setOrgId(Long.parseLong(ele_.attributeValue("id")));
					item.setOrgName(ele_.getText());
					item.setNodeType(ele_.attributeValue("type"));
					newStaffList_.add(item);
				}
				resultData.put("ORG_LIST", newStaffList_);
				result = Result.buildSuccess(resultData);

				result = Result.buildSuccess(resultData);
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

	private Result parseSelAuthStaffList(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("指派授权人员查询响应报文: " + respXml);
		}
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/root/result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				List<Element> rtEleList = doc
						.selectNodes("/root/items/item");
				int size = rtEleList.size();
				List<SimStaffInfo> newStaffList_ = new ArrayList<SimStaffInfo>(size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					ele_.attributeValue("staffId");
					logger.info("staffId======"+ele_.attributeValue("staffId"));
					logger.info("text======"+ele_.getText());
					SimStaffInfo item = new SimStaffInfo();
					item.setStaffId(Long.parseLong(ele_.attributeValue("staffId")));
					item.setStaffName(ele_.getText());
					item.setNodeType(ele_.attributeValue("type"));
					newStaffList_.add(item);
				}
				resultData.put("STAFF_LIST", newStaffList_);
				result = Result.buildSuccess(resultData);

				result = Result.buildSuccess(resultData);
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

	private WorkOrderDAO getWorkOrderDAO() {
		String daoName = WorkOrderDAOImpl.class.getName();
		return (WorkOrderDAO) BaseDAOFactory.getImplDAO(daoName);
	}
//	/**
//	 * 调用指派接口
//	 */
//	public Result workOrderAppoint(JSONObject json) {
//		Result result ;
//		try {
//			String workOrderId = json.getString(WORKORDERID);
//			String UseName = json.getString(USENAME);
//			String DispComments = json.getString(DISPCOMMENTS);
//			String DispPartyType = json.getString(DISPPARTYTYPE);
//			String DispPartyId = json.getString(DISPPARTYID);
//			String DispPartyName = json.getString(DISPPARTYNAME);
//			String DispOrgId = json.getString(DISPORGID);
//			String OrderID = json.getString(ORDERID);
//			String OrderClass = json.getString(ORDERCLASS);
//			StringBuffer sbReqXml = new StringBuffer();
//			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
//			sbReqXml.append("<OrderClass>" + OrderClass + "</OrderClass>");
//			sbReqXml.append("<OrderID>" + OrderID + "</OrderID>");
//			sbReqXml.append("<UseName>" + UseName + "</UseName>");
//			sbReqXml.append("<DispComments>" + DispComments
//					+ "</DispComments>");
//			sbReqXml.append("<DispPartyType>" + DispPartyType
//					+ "</DispPartyType>");
//			sbReqXml
//					.append("<DispPartyId>" + DispPartyId + "</DispPartyId>");
//			sbReqXml.append("<DispPartyName>" + DispPartyName
//					+ "</DispPartyName>");
//			sbReqXml.append("<DispOrgId>" + DispOrgId + "</DispOrgId>");
//
//			String reqXml = ws.getReqestXml(sbReqXml.toString(),
//					DISPWORKORDERFOREBIZ);
//			String retXml = "";
//			if (debug) {
//				retXml = getRemotelXmlData("result.xml");
//			} else {
//				retXml = this.callWsService(DISPWORKORDERFOREBIZ, reqXml);
//			}
//			result = parseCancelOrderResponse(retXml);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = Result.buildServerError();
//		}
//		return result;
//	}

	public Result workOrderContorlDtlie(JSONObject json) {
		Long workOrderId = json.getLong(WORKORDERID);
		String OrderId = json.getString(ORDERID);
		if (logger.isDebugEnabled()) {
			logger.debug("工单ID: " + workOrderId);
		}

		Result result = null;
		try {
			String reqXml = getWorkOrderDetailBzRequest(OrderId);
			String respXml = callWsService(
					QRYSAWORKORDERDETAILFOREBIZ, reqXml);
			//
//				respXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//			"<Data><QueryMethod>qrySaWorkOrderDetailForEBiz</QueryMethod>"+
//
//			"<Return><Content><Public>"+
//			"<OrderTitle>模拟_22222</OrderTitle>"+
//			"<UserName>22222</UserName>"+
//			"<UserAddr>ddd</UserAddr>"+
//			"<ContactName>22222</ContactName>"+
//			"<ContactTel>22222</ContactTel>"+
//			"<FaultPhenomenaComment>所有台有雪花</FaultPhenomenaComment>"+
//			"<ServBrandName>gg</ServBrandName>"+
//			"<UserLevelName>ff</UserLevelName>"+
//			"<DateEndTime>dd</DateEndTime>"+
//			"<AcceptDate>dd</AcceptDate>"+
//			"<LimitDate>sss</LimitDate>"+
//			"<FaultDescribe>22222</FaultDescribe>"+
//			"<AccNbr>22222</AccNbr>"+
//			"</Public><Resource><Resinfo/></Resource></Content><Result>000</Result><ErrorDesc></ErrorDesc></Return></Data>";

			result = parseWorkOrderDetailBzResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}
	/**
	 * 保障工单监控 工单信息请求xml
	 * @param
	 * @return
	 */
	private String getWorkOrderDetailBzRequest(String OrderId) {

		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QRYSAWORKORDERDETAILFOREBIZ).append(
				"</QueryMethod>")
				.append("<Params>").append("<OrderId>").append(OrderId)
				.append("</OrderId>").append("</Params>").append("</Data>")
				.append("");

		if (logger.isDebugEnabled()) {
			logger.debug("障碍工单监控详情请求报文 orderid: " + sb.toString());
		}

		return sb.toString();
	}
	/**
	 * 解析障碍工单监控详情报文
	 * @param respXml
	 * @return
	 */
	private Result parseWorkOrderDetailBzResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("障碍工单监控详情响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_ACCEPT_TIME_NODE);
				Node nn2 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CUST_GRADE_NODE);
				Node nn3 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERV_GRADE_NODE);
				Node nn4 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_TITLE_NODE);
				Node nn5 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_USER_ADDR_NODE);
				Node nn6 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SERVICE_NBR_NODE);
				Node nn7 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_PHENOM_NODE);
				Node nn8 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_FAULT_DESC_NODE);
				Node nn9 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_LIMIT_TIME_NODE);
				Node nn10 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_SUB_TIME_NODE);
				Node nn11 = doc.selectSingleNode(rootPath
						+ WorkOrderDetailBz.WORK_ORDER_CONTACT_NAME_NODE);


				WorkOrderDetailBz detail = new WorkOrderDetailBz();

				detail.setWorkOrderAcceptTime(nn1.getText());
				detail.setWorkOrderCustGrade(nn2.getText());
				detail.setWorkOrderServGrade(nn3.getText());
				detail.setWorkOrderTitle(nn4.getText());
				detail.setWorkOrderAddress(nn5.getText());
				detail.setWorkOrderServiceNbr(nn6.getText());
				detail.setWorkOrderPhenomenon(nn7.getText());
				detail.setWorkOrderDesc(nn8.getText());
				detail.setWorkOrderLimitTime(nn9.getText());
				detail.setWorkOrderSubscribeTime(nn10.getText());
				detail.setWorkOrderCustName(nn11.getText());


				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData
						.put(WorkOrderDetailBz.WORK_ORDER_DETAIL_NODE, detail);

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
	 * 调用查询障碍工单列表接口
	 */
	public Result workOrderContorlQuery(JSONObject json) {
		Result result = null;
		String username = json.optString(StaffInfo.USERNAME_NODE);
		Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		Integer pageIndex = json.optInt("pageIndex");
		Integer pageSize = json.optInt("pageSize");
		if (debug) {
			username = "ck";
			jobId = 51296L;
		}

		try {
			String reqXml = getSaWorkOrderForMonitorRequest(username, jobId,
					pageSize, pageIndex);

			String respXml = "";

			if (debug) {
				respXml = XmlUtil.getRemotelXmlData(QUERYSAWORKORDERFORMONITOR);
			} else {
				respXml = callWsService(QUERYSAWORKORDERFORMONITOR,
						reqXml);
			}
			//
			result = parseMonitorWorkOrderResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	private Result parseMonitorWorkOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("障碍工单监控查询响应报文: " + respXml);
		}

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
//					Node nn1 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.PARTY_TYPE_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ORDER_TITLE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORKORDER_ID_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_CODE_NODE);
//					Node nn5 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz. PROF_TYPE_NODE);
					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACC_NBR_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_NAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.CONTACT_TEL_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ADDRESS_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.BOK_TIME_NODE);

					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.FAULT_PHENOM_COMMENT_NODE);
//					Node nn14 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.WORK_ORDER_STATE_NODE);
//					Node nn15 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.ORDER_STATE_NODE);

//					Node nn16 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.ORDER_STATE_NAME_NODE);
//					Node nn17 = ele_.selectSingleNode(rootPath
//							+ WorkOrderBz.WORK_ORDER_STATE_NAME_NODE);

					Node nn18 = ele_.selectSingleNode(rootPath
							+ "OrderID");
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.DEAL_MAN_NAME_NODE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ "IsPause");


//					Node nn21 = ele_.selectSingleNode(rootPath
//							+ "OrderStateName");
//					Node nn22 = ele_.selectSingleNode(rootPath
//							+ "DealManName");
//					Node nn23 = ele_.selectSingleNode(rootPath
//							+ "DealOrgName");
					Node nn24 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE);
					Node nn25 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ACCEPT_DATE);
					Node nn26 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.BOOKSTATE_NODE);

					Map<String, Object> map_ = new HashMap<String, Object>();
//					map_.put(WorkOrderBz.PARTY_TYPE_NODE,
//							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderBz.ORDER_TITLE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderBz.WORKORDER_ID_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderBz.WORK_ORDER_CODE_NODE,
							nn4.getText() == null ? "" : nn4.getText());
//					map_.put(WorkOrderBz.PROF_TYPE_NODE,
//							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderBz.ACC_NBR_NODE,
							nn6.getText() == null ? "" : nn6.getText());

					map_.put(WorkOrderBz.CONTACT_NAME_NODE,
							nn8.getText() == null ? "" : nn8.getText());
					map_.put(WorkOrderBz.CONTACT_TEL_NODE, nn9
							.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderBz.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

//					map_.put(WorkOrderBz.REPEAT_NUM_NODE,
//							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderBz.BOK_TIME_NODE,
							nn12.getText() == null ? "" : nn12.getText());

					map_.put(WorkOrderBz.FAULT_PHENOM_COMMENT_NODE,
							nn13.getText() == null ? "" : nn13.getText());
//					map_.put(WorkOrderBz.WORK_ORDER_STATE_NODE,
//							nn14.getText() == null ? "" : nn14.getText());
					map_.put("OrderID", nn18
							.getText() == null ? "" : nn18.getText());
					map_.put(WorkOrderBz.DEAL_MAN_NAME_NODE, nn19
							.getText() == null ? "" : nn19.getText());
					map_.put("IsPause",
							nn20.getText() == null ? "" : nn20.getText());

					map_.put(WorkOrderBz.ALERT_STATE,
							nn24.getText() == null ? "" : nn24.getText());
					map_.put(WorkOrderBz.ACCEPT_DATE,
							nn25.getText() == null ? "" : nn25.getText());
					map_.put(WorkOrderKt.BOOKSTATE_NODE,
							nn26.getText() == null ? "" : nn26.getText());


					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderBz.WORK_ORDER_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderBz.TOTAL_COUNT_NODE,
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



	public static void main(String[] args) {
		WorkOrderBzServiceImpl wobzs = new WorkOrderBzServiceImpl();
		String	respXml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
				"<Data><QueryMethod>qrySaWorkOrderDetailForEBiz</QueryMethod>"+

				"<Return><Content><Public>"+
				"<OrderTitle>模拟_22222</OrderTitle>"+
				"<UserName>22222</UserName>"+
				"<UserAddr/>"+
				"<ContactName>22222</ContactName>"+
				"<ContactTel>22222</ContactTel>"+
				"<FaultPhenomenaComment>所有台有雪花</FaultPhenomenaComment>"+
				"<ServBrandName/>"+
				"<UserLevelName/>"+
				"<DateEndTime/>"+
				"<AcceptDate/>"+
				"<LimitDate/>"+
				"<FaultDescribe>22222</FaultDescribe>"+
				"<AccNbr>22222</AccNbr>"+
				"</Public><Resource><Resinfo/></Resource></Content><Result>000</Result><ErrorDesc></ErrorDesc></Return></Data>";
		Result result = wobzs.parseWorkOrderDetailBzResponse(respXml);
		System.out.println(result);
	}

	public static String getRemotelXmlData(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			String url = "http://10.45.47.143:7003/MOBILE/xml/";
			URL theUrl = new URL(url + fileName);
			HttpURLConnection conn = (HttpURLConnection) theUrl
					.openConnection();
			conn.connect();
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, "UTF-8"));

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("remoteXmlData===\n" + sb.toString());
		return sb.toString();
	}

	public Result selAuthOrgList(Long areaId, Long orgId, Boolean isRoot,
								 String type) {
		// TODO Auto-generated method stub
		Result result = null;
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				type).append("</QueryMethod>")
				.append("<Params>").append("<AreaId>").append(areaId)
				.append("</AreaId>").append("<OrgId>").append(orgId)
				.append("</OrgId>").append("<IsRoot>").append(isRoot)
				.append("</IsRoot>")
				.append("</Params>").append("</Data>");

		if (logger.isDebugEnabled()) {
			logger.debug("加派授权组织查询请求报文: " + sb.toString());
		}
		try{
			/*
			String respXml = null;
            if(type.equalsIgnoreCase(QUERY_ADD_DISPATHCH_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff1.xml");
				
			}else if(type.equalsIgnoreCase(QUERY_TRAN_DESTION_FOR_EBIZ)){
				respXml=getRemotelXmlData("authStaff2.xml");
				
			}
			*/
			String respXml = callWsService(type, sb.toString());
			result=parseSelAuthOrgList(respXml);

		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildInvokeInterError();
		}
		return result;

	}

}
