package com.ztesoft.mobile.v2.service.workform.xinjiang.kt;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAO;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.helper.StringUtil;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.common.CommonDAO;
import com.ztesoft.mobile.v2.dao.common.CommonDAOImpl;
import com.ztesoft.mobile.v2.dao.workfloworder.hunan.WorkFlowDao;
import com.ztesoft.mobile.v2.dao.workfloworder.hunan.WorkFlowDaoImpl;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.dao.workform.hunan.MobileWorkOrderDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.MobileWorkOrderDAOImpl;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAO;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAOImpl;
import com.ztesoft.mobile.v2.entity.common.AuthRefresh;
import com.ztesoft.mobile.v2.entity.common.JobInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.AppointOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.CancelOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.DelayOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.ReplyOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimJobInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimOrgInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimStaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.WaitOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.WorkOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.AcceptFaultOrder;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.WorkOrderDetailBz;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderDetail;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.kt.WorkOrderKt;
import com.ztesoft.mobile.v2.service.workform.XmlUtil;
import com.ztesoft.mobile.v2.service.workform.xinjiang.WebService;
import com.ztesoft.mobile.v2.util.DateTimeUtils;
import com.ztesoft.mobile.v2.util.HttpUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service("workOrderXjKtService")
public class WorkOrderKtServiceImpl implements WorkOrderKtService {
	private static final Logger logger = Logger
			.getLogger(WorkOrderKtServiceImpl.class);
	private static final boolean debug = false;
	private static final String[] PARAM_NAME = { "infType", "requestxml" };

	private static final String SUBMIT_RETURN = "submitReturn";
	/** * 开通工单监控 -- 查询 */
	private static final String QUERYWORKORDERFORMONITOR = "queryWorkOrderForMonitor";
	/** * 开通工单监控 -- 详情 */
	private static final String QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF = "queryWorkOrderDetailForEBiz";

	/** * 开通工单监控 退单 */
	private static final String RETURNWORKORDERFOREBIZ = "returnWorkOrderForEBiz";
	/** * 开通工单监控 退单到调度 */
	private static final String BACKWORKORDERFOREBIZ = "backWorkOrderForEBiz";
	/** * 授权刷新 */
	private static final String AUTHREFRESHFOREBIZ="authRefreshForEBiz";
	// 调用接口服务
	private static final EBizToIomWebservice service = new EBizToIomWebservice();
	private static final WebService ws = new WebService();
	private  WorkFlowDao wfdao = new WorkFlowDaoImpl();

	private String callWsService(String method, String reqXml) throws Exception {

		return service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE,
				WS_METHOD_OPERATION_NAME, PARAM_NAME, new String[] { method,
						reqXml });
	}

	private CommonDAO getCommonDAO() {
		String daoName = CommonDAOImpl.class.getName();
		return (CommonDAO) BaseDAOFactory.getImplDAO(daoName);
	}

//	public Result workOrderAppoint(JSONObject json) {
//		Result result;
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
//			sbReqXml.append("<OrderID>" + OrderID + "</OrderID>");
//			sbReqXml.append("<OrderClass>" + OrderClass + "</OrderClass>");
//			sbReqXml.append("<UseName>" + UseName + "</UseName>");
//			sbReqXml
//					.append("<DispComments>" + DispComments + "</DispComments>");
//			sbReqXml.append("<DispPartyType>" + DispPartyType
//					+ "</DispPartyType>");
//			sbReqXml.append("<DispPartyId>" + DispPartyId + "</DispPartyId>");
//			sbReqXml.append("<DispPartyName>" + DispPartyName
//					+ "</DispPartyName>");
//			sbReqXml.append("<DispOrgId>" + DispOrgId + "</DispOrgId>");
//
//			String reqXml = ws.getReqestXml(sbReqXml.toString(),
//					DISPWORKORDERFOREBIZ);
//			String retXml = "";
//
//			retXml = this.callWsService(DISPWORKORDERFOREBIZ, reqXml);
//			result = parseCancelOrderResponse(retXml);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			result = Result.buildServerError();
//		}
//		return result;
//	}

	private Result parseCancelOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("退单响应报文: " + respXml);
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
	private Result parseBackOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("退单到调度响应报文: " + respXml);
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
	public Result workOrderContorlDtlie(JSONObject json) {
		Long workOrderId = json.getLong(WORKORDERID);
		String OrderId = json.getString(ORDERID);
		if (logger.isDebugEnabled()) {
			logger.debug("工单ID: " + workOrderId);
		}

		Result result = null;
		try {
			String reqXml = getWorkOrderDetailBzRequest(workOrderId,OrderId);
			String respXml="";
			if (debug){
				respXml = XmlUtil.getRemotelXmlData(QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF);
			} else {
				respXml = callWsService(
						QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF, reqXml);
			}
			System.out.println("传送报文:"+reqXml.toString());
			System.out.println("返回报文: + " + respXml);
			//
			result = parseWorkOrderDetailKtResponse(respXml);

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
	 * 解析开通工单监控详情报文
	 *
	 * @param respXml
	 * @return
	 */
	private Result parseWorkOrderDetailKtResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("开通工单监控详情响应报文: " + respXml);
		}

		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/Content/Public/";

				Node nn1 = doc.selectSingleNode(rootPath
						+ "WorkOrderID");
				Node nn2 = doc.selectSingleNode(rootPath
						+"OrderCode");
				Node nn3 = doc.selectSingleNode(rootPath
						+ "OrderID");
				Node nn4 = doc.selectSingleNode(rootPath
						+ "CustOrderCode");
				Node nn5 = doc.selectSingleNode(rootPath
						+ "ServiceName");
				Node nn6 = doc.selectSingleNode(rootPath
						+"AccNbr");
				Node nn7 = doc.selectSingleNode(rootPath
						+ "CustName");
				Node nn8 = doc.selectSingleNode(rootPath
						+ "CustLinkPerson");
				Node nn9 = doc.selectSingleNode(rootPath
						+ "CustLinkPhone");
				Node nn10 = doc.selectSingleNode(rootPath
						+ "AcceptDate");
				Node nn11 = doc.selectSingleNode(rootPath
						+ "TacheName");
				Node nn12 = doc.selectSingleNode(rootPath
						+ "TacheCode");
				Node nn13 = doc.selectSingleNode(rootPath
						+ "Address");

				Node nn14 = doc.selectSingleNode(rootPath
						+ "SlaTime");
				Node nn15 = doc.selectSingleNode(rootPath
						+ "AcceptDate");
				Node nn16 = doc.selectSingleNode(rootPath
						+ "CreateDate");
				Node nn17 = doc.selectSingleNode(rootPath
						+ "AcceptStaff");
				Node nn18 = doc.selectSingleNode(rootPath
						+ "CustAddress");
				Node nn19 = doc.selectSingleNode(rootPath
						+ "Comments");
				Node nn20 = doc.selectSingleNode(rootPath
						+ "ContactPhone");
				Node nn21 = doc.selectSingleNode(rootPath
						+ "BokTime");
				Map  map = new HashMap();
				try{
					map.put("WorkOrderID", nn1!=null?nn1.getText():"");
					map.put("OrderCode", nn2!=null?nn2.getText():"");
					map.put("OrderID", nn3!=null?nn3.getText():"");
					map.put("CustOrderCode", nn4!=null?nn4.getText():"");
					map.put("ServiceName", nn5!=null?nn5.getText():"");
					map.put("AccNbr", nn6!=null?nn6.getText():"");
					map.put("CustName", nn7!=null?nn7.getText():"");
					map.put("CustLinkPerson", nn8!=null?nn8.getText():"");
					map.put("CustLinkPhone", nn9!=null?nn9.getText():"");
					map.put("AcceptDate", nn10!=null?nn10.getText():"");
					map.put("TacheName", nn11!=null?nn11.getText():"");
					map.put("TacheCode", nn12!=null?nn12.getText():"");
					map.put("Address", nn13!=null?nn13.getText():"");
					map.put("SlaTime", nn14!=null?nn14.getText():"");
					map.put("AcceptDate", nn15!=null?nn15.getText():"");
					map.put("CreateDate", nn16!=null?nn16.getText():"");
					map.put("AcceptStaff", nn17!=null?nn17.getText():"");
					map.put("CustAddress", nn18!=null?nn18.getText():"");
					map.put("Comments", nn19!=null?nn19.getText():"");
					map.put("ContactPhone", nn20!=null?nn20.getText():"");
					map.put("BokTime", nn21!=null?nn21.getText():"");
				}catch (Exception e) {
					logger.error(e);
				}
//				WorkOrderDetailBz detail = new WorkOrderDetailBz();
//
//				detail.setWorkOrderAcceptTime(nn1.getText());
//				detail.setWorkOrderCustGrade(nn2.getText());
//				detail.setWorkOrderServGrade(nn3.getText());
//				detail.setWorkOrderTitle(nn4.getText());
//				detail.setWorkOrderAddress(nn5.getText());
//				detail.setWorkOrderServiceNbr(nn6.getText());
//				detail.setWorkOrderPhenomenon(nn7.getText());
//				detail.setWorkOrderDesc(nn8.getText());
//				detail.setWorkOrderLimitTime(nn9.getText());
//				detail.setWorkOrderSubscribeTime(nn10.getText());
//				detail.setWorkOrderCustName(nn11.getText());
//				detail.setCrmProdInstId(nn12.getText());
//				detail.setCustOrderCode(nn13.getText());
				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData
						.put(WorkOrderDetailBz.WORK_ORDER_DETAIL_NODE, map);
				result = Result.buildSuccess(resultData);

			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				result = Result.buildInterInfoError(msg);
			}

		} catch (XMLDocException e) {
			e.printStackTrace();
			logger.error(e);
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		}

		return result;
	}

	/**
	 * 开通工单监控 工单信息请求xml
	 *
	 * @param workOrderId
	 * @return
	 */
	private String getWorkOrderDetailBzRequest(Long workOrderId,String OrderId) {

		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PRIVATE_WORKORDER_DETAIL_INTERF).append(
				"</QueryMethod>")
				.append("<Params>").append("<OrderID>"+OrderId+"</OrderID>").append("<WorkOrderID>").append(workOrderId)
				.append("</WorkOrderID>").append("</Params>").append("</Data>")
				.append("");

		if (logger.isDebugEnabled()) {
			logger.debug("开通工单监控详情请求报文:33333 " + sb.toString());
		}

		return sb.toString();
	}

	public Result workOrderContorlQuery(JSONObject json) {
		Result result = null;
		//String username = json.optString(StaffInfo.USERNAME_NODE);
		Long jobId = json.optLong(JobInfo.JOB_ID_NODE);
		Integer pageIndex = json.optInt("pageIndex");
		Integer pageSize = json.optInt("pageSize");
		String staffId = json.getString("staffId");
		//String productNbr = json.getString("productNbr");

		try {
			String reqXml = "";
			StringBuilder sb = new StringBuilder(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<Data>").append("<QueryMethod>").append(
					QUERYWORKORDERFORMONITOR).append("</QueryMethod>")
					.append("<JobId>").append(jobId).append("</JobId>")
					.append("<StaffId>" + staffId + "</StaffId>").append(
					"<PageSize>").append(pageSize)
					.append("</PageSize>").append("<PageNum>")
					.append(pageIndex).append("</PageNum>").append("</Params>")
					.append("</Data>");
			String respXml = "";

			if (debug){
				respXml = XmlUtil.getRemotelXmlData(QUERYWORKORDERFORMONITOR);
			} else {
				respXml = callWsService(QUERYWORKORDERFORMONITOR, sb.toString());
			}
			System.out.println("传送报文:"+sb.toString());
			System.out.println("返回报文: + " + respXml);
			//

			result = parseWorkOrderKtResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	private Result parseWorkOrderKtResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("开通工单监控查询响应报文: " + respXml);
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
							+ WorkOrderKt.WORK_ORDER_ID_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ORDER_CODE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SERVICE_NAME_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACC_NBR_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_NAME_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ADDRESS_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_TYPE_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.BOK_TIME_NODE);
					Node nn14 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ISPAUSE_NODE);
					Node nn15 = ele_.selectSingleNode(rootPath
							+ "ExtState");
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CONTACT_PHONE_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.BOOKSTATE_NODE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACCEPT_DATE_NODE);
					Node nn21 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ISPAUSE_NODE);


					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderKt.WORK_ORDER_ID_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderKt.ORDER_CODE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderKt.SERVICE_NAME_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderKt.ACC_NBR_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderKt.CUST_NAME_NODE,
							nn5.getText() == null ? "" : nn5.getText());
					map_.put(WorkOrderKt.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

//					map_.put(WorkOrderKt.SLATIME_NODE,
//							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderKt.WORK_ORDER_TYPE_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					map_.put(WorkOrderBz.BOK_TIME_NODE,
							nn13.getText() == null ? "" : nn13.getText());
					map_.put(WorkOrderKt.ORDER_ID_NODE,
							nn14.getText() == null ? "" : nn14.getText());
					map_.put("ExtState",
							nn15.getText() == null ? "" : nn15.getText());
					map_.put(WorkOrderBz.ALERT_STATE,
							nn17.getText() == null ? "" : nn17.getText());
					map_.put(WorkOrderKt.CONTACT_PHONE_NODE,
							nn18.getText() == null ? "" : nn18.getText());
					map_.put(WorkOrderKt.BOOKSTATE_NODE,
							nn19.getText() == null ? "" : nn19.getText());
					map_.put(WorkOrderKt.ACCEPT_DATE_NODE,
							nn20.getText() == null ? "" : nn20.getText());
					map_.put(WorkOrderKt.ISPAUSE_NODE,
							nn21.getText() == null ? "" : nn21.getText());
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





	public static String getRemotelXmlData(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			String url = "http://10.45.47.172:18042/MOBILE/xml/";
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



//	public static void main(String args[]) {
//
//		WorkOrderKtServiceImpl impl = new WorkOrderKtServiceImpl();
//		String requestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><ReasonList><Reason><ErrorCode>RT015</ErrorCode><ErrorName>设备保障</ErrorName></Reason><Reason><ErrorCode>RT008</ErrorCode><ErrorName>用户要求待定</ErrorName></Reason><Reason><ErrorCode>RT013</ErrorCode><ErrorName>地址错误</ErrorName></Reason><Reason><ErrorCode>RT005</ErrorCode><ErrorName>要做工程预算</ErrorName></Reason><Reason><ErrorCode>RT016</ErrorCode><ErrorName>用户不能把室内线引出外面</ErrorName></Reason><Reason><ErrorCode>RT020</ErrorCode><ErrorName>返代理商跟进</ErrorName></Reason><Reason><ErrorCode>RT012</ErrorCode><ErrorName>用户电话无法接通</ErrorName></Reason><Reason><ErrorCode>RT003</ErrorCode><ErrorName>用户隔离有阻档物天线不能通过</ErrorName></Reason><Reason><ErrorCode>RT018</ErrorCode><ErrorName>派错单</ErrorName></Reason><Reason><ErrorCode>RT025</ErrorCode><ErrorName>出租屋或办公楼</ErrorName></Reason><Reason><ErrorCode>RT026</ErrorCode><ErrorName>上竹楼商铺没有施工条件</ErrorName></Reason><Reason><ErrorCode>RT021</ErrorCode><ErrorName>返网建跟进</ErrorName></Reason><Reason><ErrorCode>RT004</ErrorCode><ErrorName>小区未通电或提供不了电源</ErrorName></Reason><Reason><ErrorCode>RT010</ErrorCode><ErrorName>用户地址不符</ErrorName></Reason><Reason><ErrorCode>RT019</ErrorCode><ErrorName>返网维部处理</ErrorName></Reason><Reason><ErrorCode>RT009</ErrorCode><ErrorName>用户未装修或正在装修</ErrorName></Reason><Reason><ErrorCode>RT024</ErrorCode><ErrorName>没有施工条件</ErrorName></Reason><Reason><ErrorCode>RT002</ErrorCode><ErrorName>线路过长</ErrorName></Reason><Reason><ErrorCode>RT023</ErrorCode><ErrorName>片区规划中</ErrorName></Reason><Reason><ErrorCode>RT017</ErrorCode><ErrorName>重复派单</ErrorName></Reason><Reason><ErrorCode>RT006</ErrorCode><ErrorName>户主不在无法开通</ErrorName></Reason><Reason><ErrorCode>RT001</ErrorCode><ErrorName>小区工程未竣工或未验收</ErrorName></Reason><Reason><ErrorCode>RT014</ErrorCode><ErrorName>其他（施工失败）</ErrorName></Reason><Reason><ErrorCode>RT022</ErrorCode><ErrorName>线路没到</ErrorName></Reason><Reason><ErrorCode>RT011</ErrorCode><ErrorName>用户要找物业管理协商</ErrorName></Reason><Reason><ErrorCode>RT007</ErrorCode><ErrorName>用户电话不符</ErrorName></Reason></ReasonList><Return><Result>000</Result><ErrorDesc>查询成功</ErrorDesc></Return></Data>";
//		String xml="<Data><QueryMethod>qryFaultPauseOrUnpauseReason</QueryMethod><Return><Content><PauseReasonList><Reason><ReasonId>2</ReasonId><ReasonName>外部原因-天气影响</ReasonName></Reason><Reason><ReasonId>3</ReasonId><ReasonName>用户原因-联系不上</ReasonName></Reason><Reason><ReasonId>4</ReasonId><ReasonName>用户原因-另约/不在家</ReasonName></Reason><Reason><ReasonId>5</ReasonId><ReasonName>用户原因-已修复待观察</ReasonName></Reason><Reason><ReasonId>6</ReasonId><ReasonName>用户原因--不认同修复结果</ReasonName></Reason><Reason><ReasonId>7</ReasonId><ReasonName>材料供应-光电缆</ReasonName></Reason><Reason><ReasonId>8</ReasonId><ReasonName>材料供应-终端设备</ReasonName></Reason><Reason><ReasonId>9</ReasonId><ReasonName>设备电源故障</ReasonName></Reason><Reason><ReasonId>10</ReasonId><ReasonName>设备故障</ReasonName></Reason><Reason><ReasonId>11</ReasonId><ReasonName>光电缆（含被盗）</ReasonName></Reason><Reason><ReasonId>12</ReasonId><ReasonName>需协调区NOC处理</ReasonName></Reason><Reason><ReasonId>13</ReasonId><ReasonName>其他（需对挂起原因作具体描述）</ReasonName></Reason><Reason><ReasonId>1</ReasonId><ReasonName>外部原因--市政原因施工受阻</ReasonName></Reason></PauseReasonList></Content><Result>000</Result><ErrorDesc>查询成功</ErrorDesc></Return></Data>";
//		impl.preasTuiDanReason(requestXml);
//
//		try {
////			String xml = impl
////					.callWsService(QRYFAULTPAUSEORUNPAUSEREASON, sb.toString());
//			//	System.out.println(xml);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// 个JOB_ID=51296
//		// username=ck
//
//		/**
//		 * 55972 job_id staff_id=202541 username? hanqiuwan
//		 */
//
//		// WorkOrderKtServiceImpl impl = new WorkOrderKtServiceImpl();
//		// Result result1 = impl.selSaPrivateWorkOrderByPage("ck",
//		// new Long(51296), 10, 1);
//		// System.out.println("私有: " + result1);
//		//
//		// Result result2 = impl.selSaPublicWorkOrderByPage("ck", new
//		// Long(51296),
//		// 10, 1);
//		// System.out.println("公有: " + result2);
//		//
//		// Result result3 = impl.selSaPrivateWorkOrderDetail(1355142L);
//		// System.out.println("私有详情: " + result3);
//		/*
//		 * JSONObject json1 = new JSONObject();
//		 * json1.put(WorkOrderKt.WORK_ORDER_ID_NODE, "1323140"); Result rt1 =
//		 * impl.cancelOrderReason(json1); System.out.println(rt1);
//		 *
//		 * Long workOrderId = new Long(1323140); Result rt2 =
//		 * impl.selWorkOrderDetail(workOrderId);
//		 *
//		 *
//		 * System.out.println(rt2);
//		 */
//
//		/*
//		 * JSONObject json2 = new JSONObject(); json2.put("userName", "iom");
//		 * json2.put("cancelReasonId", "R0039"); json2.put("staffId", "73316");
//		 * json2.put("workOrderId", "1323153"); json2.put("cancelReason", "cc");
//		 *
//		 * Result rt2 = impl.cancelOrder(json2);
//		 *
//		 * System.out.println(rt2);
//		 */
//
//	}

	/** 保障待办查询 */
	private static final String QUERY_SA_WORKORDER_INTERF = "querySaWorkOrderForEBiz";
	/** 保障私有代办查询 */
	private static final String QUERY_SA_PRIVATE_WORKORDER_INTERF = "queryWorkOrderForEBiz";
	/** 开通代办查询 */
	private static final String QUERY_PUBLIC_WORKORDER_INTERF = "queryPublicWorkOrderForEBiz";

	/** 开通代办查询-适配湖南联通公客系统 */
	private static final String QUERY_WORKORDER_INTERF = "queryWorkOrderForEBiz";

	/**   故障待办（公有）*/
	private static final String QUERY_SA_PUBLIC_WORKORDER_FOR_EBIZ = "querySaPublicWorkOrderForEBiz";


	/** 保障代办详情（公有） */
	private static final String QUERY_WORKORDER_DETAIL_INTERF = "queryWorkOrderDetailForEBiz";
	/** 退单*/
	private static final String CANCEL_WORKORDER_INTERF = "returnWorkOrderForEBiz";
	/** 退单原因列表 */
	private static final String CANCEL_WORKORDER_REASON_INTERF = "returnReasonListForEBiz";
	/** 待装*/
	private static final String WAIT_WORKORDER_INTERF = "waitWorkOrderForEBiz";
	/** 待装原因列表 */
	private static final String WAIT_WORKORDER_REASON_INTERF = "waitOrderReasonListForEBiz";

	/** 缓装*/
	private static final String DELAY_WORKORDER_INTERF = "delayWorkOrderForEBiz";
	/** 缓装原因列表 */
	private static final String DELAY_WORKORDER_REASON_INTERF = "delayOrderReasonListForEBiz";

	/** 指派原因列表 */
	private static final String APPOINT_WORKORDER_REASON_INTERF = "appointOrderReasonListForEBiz";

	/** 改约原因列表 */
	private static final String CHANGE_APPOINT_WORKORDER_REASON_INTERF = "changeAppointOrderReasonListForEBiz";

	/** 原因列表 */
	private static final String WORKORDER_REASON_INTERF = "workOrderReasonListForEBiz";

	/** 回单 */
	private static final String FINISH_WORKORDER_INTERF = "finishWorkOrderForEBiz";

	/** 开通预约*/
	private static final String APPOINT_WORKORDER_INTERF = "appointWorkOrderForEBiz";

	/** 开通签到 */
	private static final String SIGN_WORKORDER_INTERF = "signWorkOrderForEBiz";

	/** 保障回单数据加载 */
	private static final String LOAD_REPLY_WORKORDER_DATA_INTERF = "loadFinishWorkOrderViewData";
	/** 保障接单 */
	private static final String ACCEPT_FAULT_WORKORDER_INTERF = "acceptFaultWorkOrderForEBiz";

	/** 开通接单 */
	private static final String ACCEPT_KT_WORKORDER_INTERF = "acceptWorkOrderForEBiz";

	/** 开通代办详情 */
	private static final String QUERY_KT_PRIVATE_WORKORDER_DETAIL_INTERF = "queryWorkOrderDetailForEBiz";

	/** 保障Pnet待办查询 */
	private static final String QUERY_PNET_WORKORDER_INTERF = "queryWorkOrderForPnet";
	/** 保障代办详情 */
	private static final String QUERY_PNET_WORKORDER_DETAIL_INTERF = "queryWorkDetailForPnet";
	/** 保障签单 */
	private static final String SIGN_FAULT_WORKORDER_DETAIL_INTERF = "SignWorkOrder";
	/** PNet申请挂起 */
	private static final String APPLY_PNET_PAUSE_INTERF = "applyPauseForPnet";

	/** 不知为何物，反正用于Pnet的 */
	private static final String PROV_NET_FAULT_INTERF = "ProvNetFault";

	public Result selPublicWorkOrderByPage(String username, Long jobId,
										   Integer pageSize, Integer pageIndex) {
		Result result = null;

		try {
			String reqXml = getPublicWorkOrderRequest(username, jobId,
					pageSize, pageIndex);
			String respXml = "";//
			if (debug){
//				respXml = XmlUtil.getRemotelXmlData(QUERY_PUBLIC_WORKORDER_INTERF);
				respXml = XmlUtil.getRemotelXmlData(QUERY_WORKORDER_INTERF);
			} else {
//			      respXml = callWsService(QUERY_PUBLIC_WORKORDER_INTERF,
//					reqXml);
				respXml = callWsService(QUERY_WORKORDER_INTERF,
						reqXml);
			}
			System.out.println("传送报文2:"+reqXml);
			System.out.println("返回报文: + " + respXml);
			//
			result = parsePublicKtWorkOrderResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	public Result selPublicWorkOrderByPageCondition(String username, Long jobId, Integer pageSize, Integer pageIndex, String accNbr, String bokState, String bokTime, String createDate)
	{
		Result result = null;
		try
		{

			String reqXml = getPublicWorkOrderRequest(username, jobId, pageSize, pageIndex, accNbr, bokState, bokTime, createDate);
			String respXml = "";




			respXml = callWsService("queryWorkOrderForEBiz",
					reqXml);

			System.out.println("传送报文2:" + reqXml);
			System.out.println("返回报文: + " + respXml);

			result = parsePublicKtWorkOrderResponse(respXml);
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			result = Result.buildInvokeInterError();
		}
		return result;
	}


	public Result selPublicWorkOrderByPageCondition1(String username, Long jobId, Integer pageSize, Integer pageIndex, String accNbr, String bokState, String bokTime, String createDate,String object)
	{
		Result result = null;
		try
		{

			String reqXml = getPublicWorkOrderRequest1(username, jobId, pageSize, pageIndex, accNbr, bokState, bokTime, createDate,object);
			String respXml = "";




			respXml = callWsService("queryWorkOrderForEBiz1",
					reqXml);

			System.out.println("传送报文2:" + reqXml);
			System.out.println("返回报文: + " + respXml);

			result = parsePublicKtWorkOrderResponse(respXml);
		}
		catch (Exception e)
		{
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	/*故障待办（公有）
	 * 殷慧明
	 */
	public Result selSaPublicWorkOrderByPage(String username, Long jobId,
											 Integer pageSize, Integer pageIndex) {
		Result result = null;

		if (debug) {
			username = "ck";
			jobId = 51296L;
		}

		try {
			String reqXml = getSaPublicWorkOrderRequest(username, jobId,
					pageSize, pageIndex);
			String respXml = "";//

			if (debug){
//			  respXml = XmlUtil.getRemotelXmlData(QUERY_PUBLIC_WORKORDER_INTERF);
				respXml = XmlUtil.getRemotelXmlData(QUERY_SA_PUBLIC_WORKORDER_FOR_EBIZ);
			} else {
//			   respXml = callWsService(QUERY_PUBLIC_WORKORDER_INTERF,
//					reqXml);
				respXml = callWsService(QUERY_SA_PUBLIC_WORKORDER_FOR_EBIZ,
						reqXml);
			}
			//
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
	private String getPublicWorkOrderRequest(String username, Long jobId,
											 Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PRIVATE_WORKORDER_INTERF).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr>").append("</ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("开通工单查询请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	private String getPublicWorkOrderRequest(String username, Long jobId, Integer pageSize, Integer pageIndex, String accNbr, String bokState, String bokTime, String createDate)
	{
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				"queryWorkOrderForEBiz").append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr>").append("</ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("<AccNbr>").append(accNbr).append("</AccNbr>")
				.append("<BokState>").append(bokState).append("</BokState>")
				.append("<BokTime>").append(bokTime).append("</BokTime>")
				.append("<CreateDate>").append(createDate).append("</CreateDate>")
				.append("</Params>").append("</Data>").append("");
		if (logger.isDebugEnabled()) {
			logger.debug("开通工单带条件查询请求报文: " + sb.toString());
		}
		return sb.toString();
	}

	private String getPublicWorkOrderRequest1(String username, Long jobId, Integer pageSize, Integer pageIndex, String accNbr, String bokState, String bokTime, String createDate,String object)
	{
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				"queryWorkOrderForEBiz1").append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr>").append("</ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("<AccNbr>").append(accNbr).append("</AccNbr>")
				.append("<BokState>").append(bokState).append("</BokState>")
				.append("<BokTime>").append(bokTime).append("</BokTime>")
				.append("<CreateDate>").append(createDate).append("</CreateDate>")
				.append("<Version>").append(object).append("</Version>")
				.append("</Params>").append("</Data>").append("");
		if (logger.isDebugEnabled()) {
			logger.debug("开通工单带条件查询请求报文: " + sb.toString());
		}
		return sb.toString();
	}

	/** 拼装共享工单详情报文 */
	private String getSaPublicWorkOrderRequest(String username, Long jobId,
											   Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

//		sb.append("<Data>").append("<QueryMethod>").append(
//				QUERY_PUBLIC_WORKORDER_INTERF).append("</QueryMethod>")
//				.append("<Params>").append("<UseName>").append(username)
//				.append("</UseName>").append("<JobId>").append(jobId).append(
//						"</JobId>").append("<ProductNbr></ProductNbr>").append(
//						"<PageSize>").append(pageSize).append("</PageSize>")
//				.append("<PageNum>").append(pageIndex).append("</PageNum>")
//				.append("</Params>").append("</Data>").append("");
		sb.append("<Data>").append("<QueryMethod>").append(
				QUERY_SA_PUBLIC_WORKORDER_FOR_EBIZ).append("</QueryMethod>")
				.append("<Params>").append("<UseName>").append(username)
				.append("</UseName>").append("<JobId>").append(jobId).append(
				"</JobId>").append("<ProductNbr></ProductNbr>").append(
				"<PageSize>").append(pageSize).append("</PageSize>")
				.append("<PageNum>").append(pageIndex).append("</PageNum>")
				.append("</Params>").append("</Data>").append("");

		if (logger.isDebugEnabled()) {
			logger.debug("开通工单查询请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	private String getPnetWorkOrderRequest(String username, Long staffId,
										   Long jobId, Integer pageSize, Integer pageIndex) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Params>").append("<QueryMethod>").append(
				QUERY_PNET_WORKORDER_INTERF).append("</QueryMethod>").append(
				"<UseName>").append(username).append("</UseName>").append(
				"<JobId>").append(jobId).append("</JobId>").append(
				"<dealAction>listNetFault</dealAction>").append(
				"<staffId>" + staffId + "</staffId>").append("<pageSize>")
				.append(pageSize).append("</pageSize>").append("<pageNum>")
				.append(pageIndex).append("</pageNum>").append("</Params>")
				.append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障Pnet工单查询请求报文: " + sb.toString());
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

	private Result parseReplyOrderResponse(String respXml,JSONObject json) {
		if (logger.isDebugEnabled()) {
			logger.debug("回单响应报文: " + respXml);
		}

		Result result = null;
		String staffArea = json.optString("staffArea", "");

		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				String rootPath = "/Data/Return/ContentList/Content/Public/";

				// Map<Object, Object> resultData = new HashMap<Object,
				// Object>();
				// resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

				result = Result.buildSuccess();
				//长沙需求，需要新增牵手分类
				if ("0731".equals(staffArea))
				{
					insertOmHandType(json);
				}

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

	private void insertOmHandType(JSONObject json) {

		Map paramMap = new HashMap();
		String handPathName = json.optString("handPathName", "");
		String handId = json.optString("handId", "");
		String accNbr = json.optString("accNbr", "");
		String staffId = json.optString("builderId", "");
		String workOrderId = json.optString("WorkOrderID", "");
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

	private MobileCommonDAO getMobileCommonDAO() {
		String daoName = MobileCommonDAOImpl.class.getName();
		return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
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


	/** 解析共享工单查询报文 */
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
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_ID_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ORDER_CODE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SERVICE_NAME_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACC_NBR_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_NAME_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_LINK_PERSON_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_LINK_PHONE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.TACHE_NAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.TACHE_CODE_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ADDRESS_NODE);

					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SLATIME_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_TYPE_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CREATE_DATE_NODE);
					Node nn14 = ele_.selectSingleNode(rootPath
							+"OrderId");
					Node nn15 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CREATE_DATE_NODE);
					Node nn16 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE);

					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderKt.WORK_ORDER_ID_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderKt.ORDER_CODE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderKt.SERVICE_NAME_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderKt.ACC_NBR_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderKt.CUST_NAME_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderKt.CUST_LINK_PERSON_NODE,
							nn6.getText() == null ? "" : nn6.getText());
					map_.put(WorkOrderKt.CUST_LINK_PHONE_NODE,
							nn7.getText() == null ? "" : nn7.getText());
					map_.put(WorkOrderKt.TACHE_NAME_NODE,
							nn8.getText() == null ? "" : nn8.getText());
					map_.put(WorkOrderKt.TACHE_CODE_NODE,
							nn9.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderKt.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

					map_.put(WorkOrderKt.SLATIME_NODE,
							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderKt.WORK_ORDER_TYPE_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					map_.put(WorkOrderKt.CREATE_DATE_NODE,
							nn13.getText() == null ? "" : nn13.getText());
					map_.put("OrderId",
							nn14.getText() == null ? "" : nn14.getText());
					map_.put(WorkOrderKt.CREATE_DATE_NODE,
							nn15.getText() == null ? "" : nn15.getText());
					map_.put(WorkOrderBz.ALERT_STATE,
							nn16.getText() == null ? "" : nn16.getText());
					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

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

	/*public static void main(String[] args) throws XMLDocException {


		System.out.println(resultNode.getText());

	}*/

	/** 解析开通待办工单查询报文 */
	private Result parsePublicKtWorkOrderResponse(String respXml) {
		Result result = null;


		try {
//			respXml = respXml.replace("&", "");

			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8"); //  这里错误吗？嗯
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
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(size);
				logger.debug("开通工单查询响应rtList: " + rtList+"  yhmsize:"+size);
				for (int i = 0; i < size; i++) {
					System.out.println("循环"+i+"次，解析的content内容:"+rtEleList.get(i).asXML());
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_ID_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ORDER_CODE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SERVICE_NAME_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACC_NBR_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_NAME_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ADDRESS_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_TYPE_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.BOK_TIME_NODE);
					Node nn14 = ele_.selectSingleNode(rootPath
							+ "OrderId");
					Node nn15 = ele_.selectSingleNode(rootPath
							+ "ExtState");
					Node nn17 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.ALERT_STATE);
					Node nn18 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CONTACT_PHONE_NODE);
					Node nn19 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.BOOKSTATE_NODE);
					Node nn20 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CREATE_DATE_NODE);
					Node nn21 = ele_.selectSingleNode(rootPath
							+ "ProdCode");
					Node nn22 = ele_.selectSingleNode(rootPath
							+ "IsFtth");
					Node nn23 = ele_.selectSingleNode(rootPath
							+ "PerformanceTime");
					Node nn24 = ele_.selectSingleNode(rootPath
							+ "IsFlowZlht");
					Node nn25 = ele_.selectSingleNode(rootPath
							+ "IpProperty");
					Node nn26 = ele_.selectSingleNode(rootPath
							+ WorkOrderBz.WORK_ORDER_CODE_NODE);
					Node nn27 = ele_.selectSingleNode(rootPath
							+ "LanId");
					Node nn28 = ele_.selectSingleNode(rootPath + "SmOrderFlag");//是否扫码装机单
					Node nn29 = ele_.selectSingleNode(rootPath + "Tache_name");//环节名称
					Node nn30 = ele_.selectSingleNode(rootPath + "terminal_info");//环节名称

					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderKt.WORK_ORDER_ID_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderKt.ORDER_CODE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderKt.SERVICE_NAME_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderKt.ACC_NBR_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderKt.CUST_NAME_NODE,
							nn5.getText() == null ? "" : nn5.getText());
					map_.put(WorkOrderKt.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

//					map_.put(WorkOrderKt.SLATIME_NODE,
//							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderKt.WORK_ORDER_TYPE_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					map_.put(WorkOrderBz.BOK_TIME_NODE,
							nn13.getText() == null ? "" : nn13.getText());
//					map_.put(WorkOrderKt.ORDER_ID_NODE,
//							nn14.getText() == null ? "" : nn14.getText());
					map_.put("orderId",nn14.getText() == null ? "" : nn14.getText());


//					map_.put("ExtState",
//							nn15.getText() == null ? "" : nn15.getText());
//					map_.put(WorkOrderBz.ALERT_STATE,
//							nn17.getText() == null ? "" : nn17.getText());
					map_.put(WorkOrderKt.CONTACT_PHONE_NODE,
							nn18 == null ? "" : nn18.getText());
					map_.put("beSpeakState",nn19.getText() == null ? "" : nn19.getText());
//					map_.put(WorkOrderKt.BOOKSTATE_NODE,
//							nn19.getText() == null ? "" : nn19.getText());
					String createDate="";
					if(nn20!=null&&nn20.getText().indexOf(".") > -1){
						createDate = nn20.getText().substring(0, nn20.getText().indexOf("."));
					}else
						createDate=nn20 == null ? "" : nn20.getText();
					map_.put(WorkOrderKt.CREATE_DATE_NODE,createDate);
					System.out.println("解析的时候CreateDate："+ createDate );
					map_.put("ProdCode",
							nn21==null||nn21.getText() == null ? "" : nn21.getText());
					map_.put("IsFtth",
							nn22==null||nn22.getText() == null ? "" : nn22.getText());
					map_.put("PerformanceTime",
							nn23==null||nn23.getText() == null ? "" : nn23.getText());
					map_.put("IsFlowZlht",
							nn24==null||nn24.getText() == null ? "" : nn24.getText());
					map_.put("IpProperty",
							nn25==null||nn25.getText() == null ? "" : nn25.getText());
					map_.put(WorkOrderBz.WORK_ORDER_CODE_NODE,
							nn26==null||nn26.getText() == null ? "" : nn26.getText());
					map_.put("LanId",
							nn27==null||nn27.getText() == null ? "" : nn27.getText());
					map_.put("SmOrderFlag", nn28==null||nn28.getText() == null ? "" : nn28.getText());
					map_.put("stateName", nn29==null||nn29.getText() == null ? "" : nn29.getText());
					map_.put("terminalInfo", nn30==null||nn30.getText() == null ? "" : nn30.getText());

					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

				Node countNode = doc.selectSingleNode("/Data/Return/TotalNum");
				resultData.put(WorkOrderKt.TOTAL_COUNT_NODE,
						null == countNode ? 0 : countNode.getText());
				Node pageNode = doc.selectSingleNode("/Data/Return/TotalPage");
				resultData.put(WorkOrder.TOTAL_PAGE_NODE,
						null == pageNode ? 0 : pageNode.getText());
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
							+ WorkOrderKt.WORK_ORDER_ID_NODE);
					Node nn2 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ORDER_CODE_NODE);
					Node nn3 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SERVICE_NAME_NODE);
					Node nn4 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ACC_NBR_NODE);
					Node nn5 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_NAME_NODE);

					Node nn6 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_LINK_PERSON_NODE);
					Node nn7 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CUST_LINK_PHONE_NODE);
					Node nn8 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.TACHE_NAME_NODE);
					Node nn9 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.TACHE_CODE_NODE);
					Node nn10 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.ADDRESS_NODE);

					Node nn11 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.SLATIME_NODE);
					Node nn12 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.WORK_ORDER_TYPE_NODE);
					Node nn13 = ele_.selectSingleNode(rootPath
							+ WorkOrderKt.CREATE_DATE_NODE);

					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put(WorkOrderKt.WORK_ORDER_ID_NODE,
							nn1.getText() == null ? "" : nn1.getText());
					map_.put(WorkOrderKt.ORDER_CODE_NODE,
							nn2.getText() == null ? "" : nn2.getText());
					map_.put(WorkOrderKt.SERVICE_NAME_NODE,
							nn3.getText() == null ? "" : nn3.getText());
					map_.put(WorkOrderKt.ACC_NBR_NODE,
							nn4.getText() == null ? "" : nn4.getText());
					map_.put(WorkOrderKt.CUST_NAME_NODE,
							nn5.getText() == null ? "" : nn5.getText());

					map_.put(WorkOrderKt.CUST_LINK_PERSON_NODE,
							nn6.getText() == null ? "" : nn6.getText());
					map_.put(WorkOrderKt.CUST_LINK_PHONE_NODE,
							nn7.getText() == null ? "" : nn7.getText());
					map_.put(WorkOrderKt.TACHE_NAME_NODE,
							nn8.getText() == null ? "" : nn8.getText());
					map_.put(WorkOrderKt.TACHE_CODE_NODE,
							nn9.getText() == null ? "" : nn9.getText());
					map_.put(WorkOrderKt.ADDRESS_NODE,
							nn10.getText() == null ? "" : nn10.getText());

					map_.put(WorkOrderKt.SLATIME_NODE,
							nn11.getText() == null ? "" : nn11.getText());
					map_.put(WorkOrderKt.WORK_ORDER_TYPE_NODE,
							nn12.getText() == null ? "" : nn12.getText());
					map_.put(WorkOrderKt.CREATE_DATE_NODE,
							nn13.getText() == null ? "" : nn13.getText());

					rtList.add(map_);
				}

				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

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
	private Result parseSaPublicWorkOrderDetailResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("开通公有工单详情响应报文: " + respXml);
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
				Node nn12 = doc.selectSingleNode(rootPath
						+ WorkOrderKt.CREATE_DATE_NODE);
				Node nn13 = doc.selectSingleNode(rootPath
						+ WorkOrderKt.WORK_ORDER_TYPE_NODE);

				Node nn14 = doc.selectSingleNode(rootPath
						+ WorkOrderKt.ACCEPT_DATE_NODE);

				Node nn15 = doc.selectSingleNode(rootPath
						+ WorkOrderKt.NEED_CS_NODE);

				WorkOrderDetail detail = new WorkOrderDetail();

				detail.setWorkOrderId(nn1.getText());
				detail.setWorkOrderCode(nn2.getText());
				detail.setWorkOrderServiceName(nn3.getText());
				detail.setWorkOrderServiceNbr(nn4.getText());
				detail.setWorkOrderCustName(nn5.getText());
				detail.setWorkOrderCustLinkPerson(nn6.getText());
				detail.setWorkOrderCustLinkPhone(nn7.getText());
				detail.setWorkOrderTacheName(nn8.getText());
				detail.setWorkOrderTacheCode(nn9.getText());
				detail.setWorkOrderAddress(nn10.getText());
				detail.setWorkOrderSubscribeTime(nn11.getText());
				detail.setWorkOrderAcceptTime(nn12.getText());
				detail.setWorkOrderType(nn13.getText());
				detail.setWorkOrderAcceptDate(nn14.getText());
				detail.setWorkOrderNeedCs(nn15.getText());

				Map<Object, Object> resultData = new HashMap<Object, Object>();

				resultData.put(WorkOrderDetail.WORK_ORDER_DETAIL_NODE, detail);
				//								
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
			String respXml = callWsService(
					QUERY_KT_PRIVATE_WORKORDER_DETAIL_INTERF, reqXml);
			//
			result = parseSaPublicWorkOrderDetailResponse(respXml);

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
			String respXml = callWsService(
					QUERY_KT_PRIVATE_WORKORDER_DETAIL_INTERF, reqXml);
			//
			result = parseSaPublicWorkOrderDetailResponse(respXml);

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
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					CANCEL_WORKORDER_REASON_INTERF);
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(CANCEL_WORKORDER_REASON_INTERF);
			else
				retXml = callWsService(CANCEL_WORKORDER_REASON_INTERF,
						reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			//判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功
				resultData = ws.getReasonResultData(retXml);//获取原因
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

	public Result waitOrderReason(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					WAIT_WORKORDER_REASON_INTERF);
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(WAIT_WORKORDER_REASON_INTERF);
			else
				retXml = callWsService(WAIT_WORKORDER_REASON_INTERF,
						reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			//判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功
				resultData = ws.getReasonResultData(retXml);//获取原因
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

	public Result delayOrderReason(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					DELAY_WORKORDER_REASON_INTERF);
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(DELAY_WORKORDER_REASON_INTERF);
			else
				retXml = callWsService(DELAY_WORKORDER_REASON_INTERF,
						reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			//判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功
				resultData = ws.getReasonResultData(retXml);//获取原因
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

	public Result appointOrderReason(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					APPOINT_WORKORDER_REASON_INTERF);
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(APPOINT_WORKORDER_REASON_INTERF);
			else
				retXml = callWsService(APPOINT_WORKORDER_REASON_INTERF,
						reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			//判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功
				resultData = ws.getReasonResultData(retXml);//获取原因
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


	public Result workOrderReason(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			StringBuffer sbReqXml = new StringBuffer();
			//sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");

			String reqXml = getWorkOrderOverReasonRequest(workOrderId);
			//ws.getReqestXml(sbReqXml.toString(),WORKORDER_REASON_INTERF);

			String retXml = "";
			if (debug)
				retXml  = XmlUtil.getRemotelXmlData(WORKORDER_REASON_INTERF);
			else
				retXml =callWsService(WORKORDER_REASON_INTERF,
						reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			//判断是否有原因返回
			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();
			if ("000".equals(resultVal)) { // 成功
				resultData = ws.getReasonResultData(retXml);//获取原因
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
	public Result loadReplyFaultOrderData(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.optString(WorkOrderKt.WORK_ORDER_ID_NODE);
			String username = json.optString(WorkOrderKt.USE_NAME_NODE);

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

	public Result replyOrder(JSONObject json) {
		Result result = null;

		// String orderType = json.getString(ORDER_TYPE_NODE);
		String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);

		// 施工单
		// if ("0".equals(orderType))
		// {
		try {
			//String finishTime = json.getString(ReplyOrder.FINISH_TIME_NODE);
			//String comments = json.getString(ReplyOrder.REMARK_NODE);
			//String userName = json.getString(StaffInfo.USERNAME_NODE);
			String resultHandle = json.getString(ReplyOrder.HANDLE_RESULT_NODE);
			String executeTime = json.getString(ReplyOrder.EXECUTE_TIME_NODE);
			String dealStaffId = json.getString(ReplyOrder.BUILDER_ID_NODE);
			String trackStaffId = json.getString(ReplyOrder.TRACK_HELP_ID_NODE);
			String overTimeReasonId =  json.getString(ReplyOrder.OVER_TIME_REASON_ID_NODE);
			String overTimeReasonDesc = json.getString(ReplyOrder.OVER_TIME_REASON_DESC_NODE);
			//String imageIds = json.getString("fileId");
			//String codeBar = json.getString("codeBar");
			String hguSn = json.getString("hgu_sn");//光猫SN
			String stbMac = json.getString("stb_mac");//机顶盒MAC
			Map<String,Object> checkMap = getWorkOrderDAO().checkWorkOrderReply(workOrderId, hguSn, stbMac);
			if (checkMap !=null){
				String outFlag = MapUtils.getString(checkMap, "out_flag",null);
				if ("0".equals(outFlag)){
					//不能进行回单操作
					String outMsg = MapUtils.getString(checkMap, "outDesc",null);
					result = Result.buildWorkOrderError(outMsg);
					return result;
				}
			}

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<DealStaffId>" + dealStaffId + "</DealStaffId>");
			sbReqXml.append("<TrackStaffId>" + trackStaffId + "</TrackStaffId>");
			//sbReqXml.append("<Comments>" + comments + "</Comments>");
			sbReqXml.append("<ResultHandle>" + resultHandle + "</ResultHandle>");
			sbReqXml.append("<ExecuteTime>" + executeTime + "</ExecuteTime>");
			sbReqXml.append("<OverTimeReasonId>" + overTimeReasonId + "</OverTimeReasonId>");
			sbReqXml.append("<OverTimeReasonDesc>" + overTimeReasonDesc + "</OverTimeReasonDesc>");
			//sbReqXml.append("<CodeBar>" + codeBar + "</CodeBar>");
			//sbReqXml.append("<ImageIds>" + imageIds + "</ImageIds>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					FINISH_WORKORDER_INTERF);
			String retXml = "";
			if (debug )
				retXml = XmlUtil.getRemotelXmlData(FINISH_WORKORDER_INTERF);
			else
				retXml =this.callWsService(FINISH_WORKORDER_INTERF, reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			result = parseReplyOrderResponse(retXml,json);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}

		// }
		return result;
	}

	//缓装
	public Result delayOrder(JSONObject json){
		Result result = null;
		try
		{
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			String reasonDesc = json.getString(DelayOrder.DELAY_REASON_DESC_NODE);
			String reasonCode = json.getString(DelayOrder.DELAY_REASON_ID_NODE);
			String execStaff = json.getString(DelayOrder.DELAY_STAFF_ID_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<ReasonCode>" + reasonCode + "</ReasonCode>");
			sbReqXml.append("<ReasonDesc>" + reasonDesc + "</ReasonDesc>");
			sbReqXml.append("<ExecStaffId>" + execStaff + "</ExecStaffId>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					DELAY_WORKORDER_INTERF);
			String retXml = "";
			if (debug )
				retXml = XmlUtil.getRemotelXmlData(DELAY_WORKORDER_INTERF);
			else
				retXml =this.callWsService(DELAY_WORKORDER_INTERF, reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			result = parseDelayOrderResponse(retXml);
		}catch(Exception e)
		{
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	//待装
	public Result waitOrder(JSONObject json){
		Result result = null;
		try
		{
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			String reasonCode =  json.getString(WaitOrder.WAIT_REASON_ID_NODE);
			String reasonDesc = json.getString(WaitOrder.WAIT_REASON_DESC_NODE);
			String execStaff = json.getString(WaitOrder.WAIT_STAFF_ID_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<ReasonCode>" + reasonCode + "</ReasonCode>");
			sbReqXml.append("<ReasonDesc>" + reasonDesc + "</ReasonDesc>");
			sbReqXml.append("<ExecStaffId>" + execStaff + "</ExecStaffId>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					WAIT_WORKORDER_INTERF);
			String retXml = "";
			if (debug )
				retXml = XmlUtil.getRemotelXmlData(WAIT_WORKORDER_INTERF);
			else
				retXml =this.callWsService(WAIT_WORKORDER_INTERF, reqXml);
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			result = parseWaitOrderResponse(retXml);
		}catch(Exception e)
		{
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}
		return result;
	}

	//预约    开通待办预约
	public Result appointOrder(JSONObject json){
		Result result = null;
		logger.debug("开通预约请求JSON: " + json.toString());
		if (logger.isDebugEnabled()) {
			logger.debug("DEBUG开通预约请求JSON: " + json.toString());
		}
		try
		{
			String workOrderId = json.getString(WorkOrderKt.WORK_ORDER_ID_NODE);
			String appointDate = json.getString(AppointOrder.APPOINT_DATE_NODE);
			String assignReason = json.getString(AppointOrder.ASSIGN_REASON_NODE);
			//String changeAppontReasonId = json.getString(AppointOrder.CHANGE_APPOINT_REASON_ID_NODE);
			String comment = json.getString(AppointOrder.COMMENT_NODE);
			String desc = json.getString(AppointOrder.DESC_NODE);
			String handleStaffId = json.getString(AppointOrder.HANDLE_STAFF_ID_NODE);
			String isSendNotice = json.getString(AppointOrder.IS_SEND_NOTICE_NODE);
			//殷慧明增加   ，增加传给公客接口
			String trackStaffId = json.getString("TrackStaffId");
			String OrderClass = json.getString("OrderClass");
			String AppointBeginDate  = json.getString("AppointBeginDate");
			String AppointEndDate = json.getString("AppointEndDate");
			String TimeSeqId  = json.getString("TimeSeqId");


			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<AppointDate>" + appointDate + "</AppointDate>");
			sbReqXml.append("<AssignReason>" + assignReason + "</AssignReason>");
			sbReqXml.append("<HandleStaffId>" + handleStaffId + "</HandleStaffId>");
			//sbReqXml.append("<ChangeAppontReasonId>" + changeAppontReasonId + "</ChangeAppontReasonId>");
			//殷慧明增加   ，增加传给公客接口
			sbReqXml.append("<TrackStaffId>"+trackStaffId+"</TrackStaffId>");
			sbReqXml.append("<OrderClass>"+OrderClass+"</OrderClass>");
			sbReqXml.append("<AppointBeginDate>"+AppointBeginDate+"</AppointBeginDate>");
			sbReqXml.append("<AppointEndDate>"+AppointEndDate+"</AppointEndDate>");
			sbReqXml.append("<TimeSeqId>"+TimeSeqId+"</TimeSeqId>");

			sbReqXml.append("<Comment>" + comment + "</Comment>");
			sbReqXml.append("<Desc>" + desc + "</Desc>");
			sbReqXml.append("<IsSendNotice>" + isSendNotice + "</IsSendNotice>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					APPOINT_WORKORDER_INTERF);
			String retXml = "";
			if (debug )
				retXml = XmlUtil.getRemotelXmlData(APPOINT_WORKORDER_INTERF);
			else
				retXml =this.callWsService(APPOINT_WORKORDER_INTERF, reqXml);
			System.out.println("传送报文 预约:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			result = parseAppointOrderResponse(retXml);
		}catch(Exception e)
		{
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}
		return result;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}



	public Result acceptKtOrder(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json.optString(
					AcceptFaultOrder.WORKORDER_ID_NODE, "");
			String username = "";

			if (debug) {
				username = "ck";
			} else {
				username = json.optString(AcceptFaultOrder.USERNAME_NODE, "");
			}

			String finishTime = json.optString(
					AcceptFaultOrder.FINISH_TIME_NODE, "");
			String comments = json.optString(
					AcceptFaultOrder.ACCEPT_COMMENT_NODE, "");

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<workOrderId>" + workOrderId + "</workOrderId>");
			sbReqXml.append("<userName>" + username + "</userName>");
//			sbReqXml.append("<Comments>" + comments + "</Comments>");
//			sbReqXml.append("<FinishTime>" + finishTime + "</FinishTime>");

			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					ACCEPT_KT_WORKORDER_INTERF);

			if (logger.isDebugEnabled()) {
				logger.debug("开通接单请求报文: " + reqXml);
			}

			String retXml = "";
			if (debug){
				retXml = XmlUtil.getRemotelXmlData(SUBMIT_RETURN);
			}else{
				retXml = this.callWsService(ACCEPT_KT_WORKORDER_INTERF,
						reqXml);
			}
			result = parseAcceptOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}

	public Result applyPauseForPnet(JSONObject json) {
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
					APPLY_PNET_PAUSE_INTERF);

			String retXml = this.callWsService(APPLY_PNET_PAUSE_INTERF, reqXml);

			result = parseCancelOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}

	public Result getOrgJobAndStaffResult(JSONObject json) {

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		Result result = null;
		try {

			// String interfaceName = "staffGroupQuery";
			// String staffId = json.getString(STAFF_ID_NODE);
			// String areaId = json.getString(JobInfo.AREA_ID_NODE);
			// StringBuffer sb = new StringBuffer();
			//	      
			// sb.append("<col colnum=\"1\" name=\"area_id\"
			// type=\"number\">"+areaId+"</col>");
			// sb.append("<col colnum=\"2\" name=\"staff_id\"
			// type=\"number\">"+staffId+"</col>");
			// String reqXml = sb.toString();
			//
			// // 拼装请求对象
			// String bodyXml = getRequestXml(interfaceName, reqXml,"");
			//			
			// HttpClientService hcs = new HttpClientService();
			//			
			// String retXml = "";
			// if(!debug) {
			// if(logger.isDebugEnabled()) {
			// logger.debug("启用接口调用");
			// }
			//				
			// String ServCode
			// =HttpClientService.getServCode(interfaceName,areaId);
			// String msgId = HttpClientService.getMsgId();
			// String headerXml = hcs.dealHeadXml(msgId, ServCode);
			// retXml = hcs.sendToOipBySys(headerXml, bodyXml, interfaceName);
			//			
			// } else {
			//				
			// }

			// String ServCode =HttpClientService.getServCode(interfaceName);
			// String msgId = HttpClientService.getMsgId();
			// String headerXml = hcs.dealHeadXml(msgId, ServCode);
			// String retXml = hcs.sendToOipBySys(headerXml, bodyXml,
			// interfaceName);*/

			// resultData = hcs.getResultData(retXml);
			/*
			 * //根据返回的Map列表进行赋值操作 List orgList_ =
			 * (ArrayList)resultData.get("resultList0"); List jobList_ =
			 * (ArrayList)resultData.get("resultList1"); List staffList_ =
			 * (ArrayList)resultData.get("resultList2");
			 *
			 * Map orgMap = new HashMap(); Map jobMap = new HashMap(); Map
			 * staffMap = new HashMap();
			 *
			 * List orgList = new ArrayList(); orgMap.put("ORG_ID","001");
			 * orgMap.put("ORG_NAME","安徽电信省公司"); orgMap.put("PATH", "安徽电信省公司");
			 * orgList.add(orgMap);
			 *
			 * orgMap = new HashMap(); orgMap.put("ORG_ID","002");
			 * orgMap.put("ORG_NAME","芜湖电信分公司"); orgMap.put("PATH",
			 * "安徽电信省公司/芜湖电信分公司"); orgList.add(orgMap);
			 *
			 * orgMap = new HashMap(); orgMap.put("ORG_ID","003");
			 * orgMap.put("ORG_NAME","宣城电信分公司"); orgMap.put("PATH",
			 * "安徽电信省公司/宣城电信分公司"); orgList.add(orgMap);
			 *
			 *
			 * List jobList = new ArrayList(); jobMap.put("JOB_ID","001");
			 * jobMap.put("JOB_NAME","普通员工"); jobMap.put("PATH",
			 * "安徽电信省公司/宣城电信分公司"); jobList.add(jobMap);
			 *
			 * jobMap = new HashMap(); jobMap.put("JOB_ID","002");
			 * jobMap.put("JOB_NAME","管理员"); jobMap.put("PATH",
			 * "安徽电信省公司/芜湖电信分公司"); jobList.add(jobMap);
			 *
			 *
			 *
			 * List staffList = new ArrayList(); staffMap.put("STAFF_ID","001");
			 * staffMap.put("STAFF_NAME","金向江"); staffMap.put("PATH",
			 * "安徽电信省公司"); staffList.add(staffMap);
			 *
			 * staffMap = new HashMap(); staffMap.put("STAFF_ID","002");
			 * staffMap.put("STAFF_NAME","王娟"); staffMap.put("PATH", "安徽电信省公司");
			 * staffList.add(staffMap);
			 *
			 * staffMap = new HashMap(); staffMap.put("STAFF_ID","003");
			 * staffMap.put("STAFF_NAME","刘胜联"); staffMap.put("PATH",
			 * "安徽电信省公司"); staffList.add(staffMap);
			 *
			 * staffMap = new HashMap(); staffMap.put("STAFF_ID","004");
			 * staffMap.put("STAFF_NAME","陈春霞"); staffMap.put("PATH",
			 * "安徽电信省公司"); staffList.add(staffMap);
			 * resultData.put("ORG_LIST",orgList);
			 * resultData.put("JOB_LIST",jobList);
			 * resultData.put("STAFF_LIST",staffList);
			 *
			 * result = hcs.getResult(resultData);
			 */

			result = Result.buildSuccess(resultData);
		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}

	public Result selOrgList(Long areaId, Long orgId) {
		if (logger.isDebugEnabled()) {
			logger.debug("参数AREA_ID, ORG_ID: " + areaId + "," + orgId);
		}

		Result result = null;

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {

			// String interfaceName = "orgGroupQuery";
			// StringBuffer sb = new StringBuffer();
			//
			// sb.append("<col colnum=\"1\" name=\"area_id\" type=\"number\">"
			// + (areaId !=null? areaId:"") + "</col>");
			// sb.append("<col colnum=\"2\" name=\"parent_id\" type=\"number\">"
			// + (orgId !=null? orgId:"") + "</col>");
			// String reqXml = sb.toString();
			//
			// // 拼装请求对象
			// String bodyXml = getRequestXml(interfaceName, reqXml, "");
			//
			// HttpClientService hcs = new HttpClientService();
			//
			// String retXml = "";
			// if (!debug) {
			// if (logger.isDebugEnabled()) {
			// logger.debug("启用接口调用");
			// }
			//
			// String ServCode =
			// HttpClientService.getServCode(interfaceName,areaId.toString());
			// String msgId = HttpClientService.getMsgId();
			// String headerXml = hcs.dealHeadXml(msgId, ServCode);
			// retXml = hcs.sendToOipBySys(headerXml, bodyXml, interfaceName);
			//
			// } else {
			//
			// }
			//			
			// resultData = hcs.getResultData(retXml);

			// 根据返回的Map列表进行赋值操作
			// List<Map> orgList_ =
			// (ArrayList<Map>)resultData.get("resultList0");
			List<Map> orgList_ = getWorkOrderDAO().selOrgList(areaId, orgId);

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

	public Result selJobList(Long areaId, Long orgId) {
		if (logger.isDebugEnabled()) {
			logger.debug("参数AREA_ID, ORG_ID: " + areaId + "," + orgId);
		}

		Result result = null;

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {

			// String interfaceName = "jobGroupQuery";
			// StringBuffer sb = new StringBuffer();
			//
			// sb.append("<col colnum=\"1\" name=\"org_id\" type=\"number\">"
			// + (null!=orgId? orgId:"") + "</col>");
			// String reqXml = sb.toString();
			//
			// // 拼装请求对象
			// String bodyXml = getRequestXml(interfaceName, reqXml, "");
			//
			// HttpClientService hcs = new HttpClientService();
			//
			// String retXml = "";
			// if (!debug) {
			// if (logger.isDebugEnabled()) {
			// logger.debug("启用接口调用");
			// }
			//
			// String ServCode =
			// HttpClientService.getServCode(interfaceName,areaId.toString());
			// String msgId = HttpClientService.getMsgId();
			// String headerXml = hcs.dealHeadXml(msgId, ServCode);
			// retXml = hcs.sendToOipBySys(headerXml, bodyXml, interfaceName);
			//
			// } else {
			//
			// }
			//			
			// resultData = hcs.getResultData(retXml);

			// 根据返回的Map列表进行赋值操作
			List<Map> jobList_ = getWorkOrderDAO().selJobList(areaId, orgId);

			List<SimJobInfo> newJobList_ = new ArrayList<SimJobInfo>(jobList_
					.size());
			for (int i = 0; i < jobList_.size(); i++) {

				Map map = jobList_.get(i);
				Long jobId = MapUtils.getLong(map, "JOB_ID");
				String jobName = MapUtils.getString(map, "JOB_NAME");
				String nodeType = MapUtils.getString(map, "NODE_TYPE");

				SimJobInfo item = new SimJobInfo();
				item.setJobId(jobId);
				item.setJobName(jobName);
				item.setNodeType(nodeType);
				newJobList_.add(item);
			}

			jobList_ = null;

			resultData.put("JOB_LIST", newJobList_);

			result = Result.buildSuccess(resultData);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}

	public Result selStaffList(Long areaId, Long jobId) {
		if (logger.isDebugEnabled()) {
			logger.debug("参数AREA_ID, job_Id: " + areaId + "," + jobId);
		}

		Result result = null;

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {

			// String interfaceName = "staffGroupQuery";
			// StringBuffer sb = new StringBuffer();
			//
			// sb.append("<col colnum=\"1\" name=\"job_id\" type=\"number\">"
			// + (jobId!= null? jobId:"") + "</col>");
			// String reqXml = sb.toString();
			//
			// // 拼装请求对象
			// String bodyXml = getRequestXml(interfaceName, reqXml, "");
			//
			// HttpClientService hcs = new HttpClientService();
			//
			// String retXml = "";
			// if (!debug) {
			// if (logger.isDebugEnabled()) {
			// logger.debug("启用接口调用");
			// }
			//
			// String ServCode =
			// HttpClientService.getServCode(interfaceName,areaId.toString());
			// String msgId = HttpClientService.getMsgId();
			// String headerXml = hcs.dealHeadXml(msgId, ServCode);
			// retXml = hcs.sendToOipBySys(headerXml, bodyXml, interfaceName);
			//
			// } else {
			//
			// }
			//			
			// resultData = hcs.getResultData(retXml);

			// 根据返回的Map列表进行赋值操作
			List<Map> staffList_ = getWorkOrderDAO()
					.selStaffList(areaId, jobId);

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
	private WorkOrderDAO getWorkOrderDAO() {
		String daoName = WorkOrderDAOImpl.class.getName();
		return (WorkOrderDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	public Result workOrderViewData(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	public Result cancelOrder(JSONObject json) {
		Result result = null;
		//String ErroReason = json.getString("ErroReason");
		//String FinishTime = json.getString("FinishTime");
		String WorkOrderID = json.getString(CancelOrder.WORKORDER_ID_NODE);
		//String Comments = json.getString("Comments");
		String cancelReasonId = json.getString(CancelOrder.CANCEL_REASON_ID_NODE);
		String cancelReasonDesc = json.getString(CancelOrder.CANCEL_REASON_DESC_NODE);
		//String cancelStaffId = json.getString(CancelOrder.CANCEL_STAFF_ID);
		String executorId = json.getString(CancelOrder.EXECUTOR_ID_NODE);
		//String handleResult = json.getString(CancelOrder.HANDLE_RESULT);
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Data>").append("<QueryMethod>").append(
				RETURNWORKORDERFOREBIZ).append("</QueryMethod>").append(
				"<Params>")
				.append("<WorkOrderID>"+WorkOrderID+"</WorkOrderID>")
				.append("<CancelReasonId>").append(cancelReasonId).append("</CancelReasonId>")
				.append("<CancelReasonDesc>").append(cancelReasonDesc).append("</CancelReasonDesc>")
				//.append("<CancelStaffId>").append(cancelStaffId).append("</CancelStaffId>")
				.append("<ExecutorId>").append(executorId).append("</ExecutorId>")
				//.append("<HandleResult>").append(handleResult).append("</HandleResult>")
				//"<ErroReason>").append(ErroReason).append("</ErroReason>")
				//.append("<FinishTime>"+FinishTime+"</FinishTime>")
				//.append("<Comments>"+Comments+"</Comments>")
				.append("</Params>").append("</Data>");

		String retXml = "";
		try {
			if (debug)
			{
				retXml = XmlUtil.getRemotelXmlData(SUBMIT_RETURN);
			} else {
				retXml = callWsService(RETURNWORKORDERFOREBIZ, sb
						.toString());
			}
			System.out.println("传送报文:"+sb.toString());
			System.out.println("返回报文: + " + retXml);
			result=	parseCancelOrderResponse(retXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}


	public Result backOrder(JSONObject json) {
		Result result = null;
		String WorkOrderID = json.getString(CancelOrder.WORKORDER_ID_NODE);
		String workOrderCode = json.getString(CancelOrder.WORKORDER_CODE_NODE);
		String executorId = json.getString(CancelOrder.EXECUTOR_ID_NODE);
		String userName = json.getString(CancelOrder.USER_NAME_NODE);
		String staffId  = json.getString(CancelOrder.STAFF_ID_NODE);
		String content = json.getString("content");
		String remark = json.getString("remark");
		String orderType = json.getString("orderType");
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Data>").append("<QueryMethod>").append(
				BACKWORKORDERFOREBIZ).append("</QueryMethod>").append(
				"<Params>")
				.append("<WorkOrderID>"+WorkOrderID+"</WorkOrderID>")
				.append("<userName>").append(userName).append("</userName>")
				.append("<staffId>").append(staffId).append("</staffId>")
				.append("<ExecutorId>").append(executorId).append("</ExecutorId>")
				.append("<workOrderCode>").append(workOrderCode).append("</workOrderCode>")
				.append("<content>").append(content).append("</content>")
				.append("<remark>").append(remark).append("</remark>")
				.append("<orderType>").append(orderType).append("</orderType>")
				.append("</Params>").append("</Data>");

		String retXml = "";
		try {
			if (debug)
			{
				retXml = XmlUtil.getRemotelXmlData(SUBMIT_RETURN);
			} else {
				retXml = callWsService(BACKWORKORDERFOREBIZ, sb
						.toString());
			}
			System.out.println("传送报文:"+sb.toString());
			System.out.println("返回报文: + " + retXml);
			result=	parseBackOrderResponse(retXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}


	public Result cancelOrderktJk(JSONObject json) {
		Result result = null;
//		String ErroReason = json.getString("ErroReason");
//		String FinishTime = json.getString("FinishTime");
//		String WorkOrderID = json.getString("WorkOrderID");
//		String Comments = json.getString("Comments");
		String ServiceName = json.getString("ServiceName");
		String InfType = json.getString("InfType");
		String InfSeq = json.getString("InfSeq");
		String ProduceNo = json.getString("ProduceNo");
		String ReasonCode = json.getString("ReasonCode");
		String ReasonDesc = json.getString("ReasonDesc");
		String OrgName = json.getString("OrgName");
		String StaffName = json.getString("StaffName");
		String OperTime = json.getString("OperTime");
		String Note1 = json.getString("Note1");
		String Note2 = json.getString("Note2");
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Data>").append("<QueryMethod>").append(
				RETURNWORKORDERFOREBIZ).append("</QueryMethod>").append(
				"<Params><ServiceName>").append(ServiceName).append("</ServiceName>")
				.append("<InfSeq>"+InfSeq+"</InfSeq>")
				.append("<InfType>"+InfType+"</InfType>")
				.append("<ProduceNo>"+ProduceNo+"</ProduceNo>")
				.append("<ReasonCode>"+ReasonCode+"</ReasonCode>")
				.append("<ReasonDesc>"+ReasonDesc+"</ReasonDesc>")
				.append("<OrgName>"+OrgName+"</OrgName>")
				.append("<StaffName>"+StaffName+"</StaffName>")
				.append("<OperTime>"+OperTime+"</OperTime>")
				.append("<Note1>"+Note1+"</Note1>")
				.append("<Note2>"+Note2+"</Note2>")

				.append("</Params>").append("</Data>");

		String responseXml = "";
		try {
			System.out.println(sb.toString());
			responseXml = callWsService(RETURNWORKORDERFOREBIZ, sb
					.toString());

			result=	preasTuiDanReason(responseXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	/**
	 * 退单原因返回报文
	 */
	public Result preasTuiDanReason(String responseXml) {
		Result result = null;

		try {
			Document doc = Dom4jUtils.fromXML(responseXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功
				String	rootPath  = "Reason/";
				List<Element> rtEleList = null;

				rtEleList = doc
						.selectNodes("/Data/ReasonList/Reason");

				int size = rtEleList.size();
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>(
						size);
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					Node nn1 = ele_.selectSingleNode("ErrorCode");
					Node nn2 = ele_.selectSingleNode("ErrorName");
					Map<String, Object> map_ = new HashMap<String, Object>();
					map_.put("ReasonId", nn1.getText() == null ? "" : nn1
							.getText());
					map_.put("ReasonName", nn2.getText() == null ? "" : nn2
							.getText());
					rtList.add(map_);
				}
				System.out.println(rtList);
				Map<Object, Object> resultData = new HashMap<Object, Object>();
				resultData.put("ReasonList", rtList);
				result = Result.buildSuccess(resultData);
				System.out.println(resultData);
			} else { // 失败
				Node errorDescNode = doc
						.selectSingleNode("/Data/Return/ErrorDesc");
				String msg = errorDescNode.getText();
				if (StringUtils.isBlank(msg)) {
					msg = "没有更多的记录";
				}
				result = Result.buildInterInfoError(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	public Result cancelOrderktJkReason(JSONObject json) {
		Result result = null;
		try {
			StringBuilder sbReqXml = new StringBuilder(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sbReqXml.append("<Root>").append("<QueryMethod>").append(
					"returnReasonsForEBiz").append("</QueryMethod>").append(
					"<Params></Params>").append("</Root>");

//			String reqXml = ws.getReqestXml(sbReqXml.toString(),
//					"returnReasonsForEBiz");

			String retXml = callWsService("returnReasonsForEBiz",
					sbReqXml.toString());

			result = preasTuiDanReason(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}

		return result;
	}
	//授权刷新
	public Result authRefresh(JSONObject json) {

		Result result = null;
		String respXml = "";
		try {
			String reqXml = getAuthRequest(json);
//				respXml=getRemotelXmlData("authorizeRefresh.xml"); 	//模拟测试报文	
			respXml = callWsService(AUTHREFRESHFOREBIZ, reqXml);
			result = parseAuthRefreshResponse(respXml);

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			result = Result.buildInvokeInterError();
		}

		return result;
	}


	private String getAuthRequest(JSONObject json) {
		// TODO Auto-generated method stub
		Long number=json.getLong(AuthRefresh.KEYNO);

		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Data>")
				.append("<QueryMethod>")
				.append(AUTHREFRESHFOREBIZ)
				.append("</QueryMethod>")
				.append("<Params>")
				.append("<BOSS_TO_OSS_SERVICE>")
				.append("<ServiceName>")
				.append("DOAUTHORIZE")
				.append("</ServiceName>")
				.append("</BOSS_TO_OSS_SERVICE>")
				.append("<BOSS_TO_OSS_ORDER>")
				.append("<KEYNO>"+number+"</KEYNO>")
				.append("<PERMARK>"+1+"</PERMARK>")
				.append("<city>"+"DG"+"</city>")
				.append("</BOSS_TO_OSS_ORDER>")
				.append("</Params>")
				.append("</Data");


		if (logger.isDebugEnabled()) {
			logger.debug("授权刷新请求报文: " + sb.toString());
		}

		return sb.toString();
	}

	//解析授权刷新响应报文
	private Result parseAuthRefreshResponse(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("授权刷新响应报文: " + respXml);
		}

		Result result = null;
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			String rootPath = "/Data/Return/Content/BOSS_TO_OSS_ORDER/";

			Node nn1=doc.selectSingleNode(rootPath+ AuthRefresh.KEYNO);
			Node nn2=doc.selectSingleNode(rootPath+ AuthRefresh.PERMARK);
			Node nn3=doc.selectSingleNode(rootPath+ AuthRefresh.dnums);
			Node nn4=doc.selectSingleNode(rootPath+ AuthRefresh.cnums);
			Node nn5=doc.selectSingleNode(rootPath+ AuthRefresh.DEAL_TIME);
			Node nn6=doc.selectSingleNode(rootPath+ AuthRefresh.Reason);
			Node nn7=doc.selectSingleNode(rootPath+ AuthRefresh.IS_SUCCESS);
			//int size = rtEleList.size();
			List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
			Map<String, Object> map_ = new HashMap<String, Object>();
			map_.put(AuthRefresh.KEYNO, nn1.getText());
			map_.put(AuthRefresh.PERMARK,nn2.getText());
			map_.put(AuthRefresh.dnums, nn3.getText());
			map_.put(AuthRefresh.cnums, nn4.getText());
			map_.put(AuthRefresh.DEAL_TIME,nn5.getText());
			map_.put(AuthRefresh.Reason, nn6.getText());
			map_.put(AuthRefresh.IS_SUCCESS,nn7.getText());
			rtList.add(map_);
			//System.out.println(rtList);
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put(AuthRefresh.ReturnData, rtList);
			result = Result.buildSuccess(resultData);
			System.out.println(resultData);

		}catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		}
		return result;
	}

	private Result parseDelayOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("缓装响应报文: " + respXml);
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
				// resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

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

	private Result parseWaitOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("待装响应报文: " + respXml);
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
				// resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

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

	private Result parseAppointOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("预约响应报文: " + respXml);
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
				// resultData.put(WorkOrderKt.WORK_ORDER_LIST_NODE, rtList);

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

	private String getWorkOrderOverReasonRequest(String workOrderId) {

		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		sb.append("<Data>").append("<QueryMethod>").append(
				WORKORDER_REASON_INTERF).append(
				"</QueryMethod>").append("<SERIALNUMBER></SERIALNUMBER>")
				.append("<Params>").append("<WorkOrderID>").append(workOrderId)
				.append("</WorkOrderID>").append("</Params>").append("</Data>")
				.append("");

		if (logger.isDebugEnabled()) {
			logger.debug("保障工单详情请求报文: " + sb.toString());
		}

		return sb.toString();
	}


	public Result signOrder(JSONObject json) {
		Result result = null;
		try {
			System.out.println(" Call WorkOrderKtServiceImpl-signOrder json  "+json.toString());
			String staffId = json.getString("staffId");
			String staffName = json.getString("staffName");
			String orgId = json.getString("orgId");
			String orgName = json.getString("orgName");
			String workOrderId =  json.getString("workOrderId");
			String longitude = json.getString("longitude");
			String latitude = json.getString("latitude");
			String checkCode = json.getString("checkCode");

			Map paramMap = new LinkedHashMap<String, String>();
			paramMap.put("workid", workOrderId);
			paramMap.put("staffid", staffId);
			paramMap.put("staffname", staffName);
			paramMap.put("orgid", orgId);
			paramMap.put("orgname", orgName);
			paramMap.put("longitude", longitude);
			paramMap.put("latitude", latitude);
			paramMap.put("checkCode", checkCode);

			System.out.println(" Call WorkOrderKtServiceImpl signOrder paramMap-- "+paramMap);
//			getMobileWorkOrderDAOImpl().insertArriveSignInfo(paramMap);
			//原来的保存方法查询序列号时连接池有连接泄露 modify by yangjihui 20200612
			String sql = "insert into ARRIVE_SIGN(sign_id,work_order_id,staff_id,staff_name,org_id,org_name,longitude,latitude,sign_date,check_code) values(SEQ_ARRIVE_SIGN.Nextval,?,?,?,?,?,?,?,sysdate,?)";
			getCommonDAO().commonInsertObjectBySql(sql,paramMap);
			result = Result.buildSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}

		return result;
	}


	/**
	 * web端
	 * 签到入库  表名ARRIVE_SIGN
	 * @param json
	 * @return
	 */
	public Result signOrderInsert(JSONObject json) {
		Result result = null;
		try {
			System.out.println(" Call WorkOrderKtServiceImpl-signOrder json  "+json.toString());
			String staffId = json.getString("staffId");
			String staffName = json.getString("staffName");
			String orgId = json.getString("orgId");
			String orgName = json.getString("orgName");
			String workOrderId =  json.getString("workOrderId");
			String longitude = json.getString("longitude");
			String latitude = json.getString("latitude");
			String checkCode = json.getString("checkCode");

			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("staffid", staffId);
			paramMap.put("staffname", staffName);
			paramMap.put("orgid", orgId);
			paramMap.put("orgname", orgName);
			paramMap.put("workid", workOrderId);
			paramMap.put("longitude", longitude);
			paramMap.put("latitude", latitude);
			paramMap.put("operateTime", new Date());
			paramMap.put("checkCode", checkCode);


			System.out.println(" Call WorkOrderKtServiceImpl signOrder paramMap-- "+paramMap);
			wfdao.insertArriveSign(paramMap);
			result = Result.buildSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	/**
	 *
	 * 签到入库  表名MOBILE_STAFF_POSITION
	 * @param json
	 * @return
	 */
	public Result insertMobileStaffPosition(JSONObject json) {
		Result result = null;
		try {

			String staffId = json.getString("staffId");
			String smy = json.getString("smy");
			String smx = json.getString("smx");


			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("staffid", staffId);
			paramMap.put("smy", smy);
			paramMap.put("smx", smx);



			System.out.println(" Call WorkOrderKtServiceImpl signOrder paramMap-- "+paramMap);
			wfdao.insertMobileStaffPosition(paramMap);
			result = Result.buildSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	/**
	 * 获取当前时间的
	 * @return
	 *//*
	private String getCurrentTime()
	{
		
	}*/


	private Result parseSignOrderResponse(String respXml) {
		if (logger.isDebugEnabled()) {
			logger.debug("签到响应报文: " + respXml);
		}
		Result result = null;
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");
			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功
				String rootPath = "/Data/Return/ContentList/Content/Public/";
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


	public MobileWorkOrderDAO getMobileWorkOrderDAOImpl() {
		String DAOName = MobileWorkOrderDAOImpl.class.getName();
		return (MobileWorkOrderDAO) BaseDAOFactory.getImplDAO(DAOName);
	}

	/**
	 * 开通工单抢单列表查询
	 *
	 * @param
	 * @param
	 * @return
	 */
	public Result selPublicWorkOrderForRobByPage(String staffId, String username, Long jobId,
												 Integer pageSize, Integer pageIndex){
		Map<Object, Object> result_data_node = new HashMap<Object,Object>();
		try {
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			result_data_node = dao.selPublicWorkOrderForRobByPage(staffId, username,jobId, pageSize, pageIndex);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Result result = new Result();
		result.setResultData(result_data_node);
		result.setResultCode(1000);

		return result;
	}

	public Result executeRodOrderOperation(JSONObject json){
		Result result = new Result();
		try{
			String workOrderID = json.optString("WorkOrderID");
			String staffId = json.optString("staffId");
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			Map<Object, Object> result_data_node = dao.executeRodOrderOperation(workOrderID,staffId);

			result.setResultData(result_data_node);
			result.setResultCode(1000);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}
	}

	public Result saveUserInfoForNotifyOrder(JSONObject json){
		Result result = new Result();
		try{
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			Map<Object, Object> result_data_node = dao.executeSaveUserInfoForNotifyOrder(json);

			result.setResultData(result_data_node);
			result.setResultCode(1000);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}
	}

	public Result selParamsByStaffId(String staff_id) {
		Result result = new Result();

		try {
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			Map<Object, Object> result_data_node;
			result_data_node = dao.selParams(staff_id);
			result.setResultData(result_data_node);
			result.setResultCode(1000);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}


	}
	public String selAllParamsByStaffId(String staff_id) {
		Result result = new Result();

		try {
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			List<Map<String, String>> maps = dao.selAllParams(staff_id);
			JSONObject js = new JSONObject();
			js.put("orgList",maps.toString());
			return js.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	public Result getCallBackReason()
	{
		Result result = new Result();
		try
		{
			List reasonList = wfdao.getCallBackReason();
			//List returnDataList = buildReturnData(reasonList);
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put(AuthRefresh.ReturnData, reasonList);
			result = Result.buildSuccess(resultData);
			System.out.println(resultData);
		}
		catch (DataAccessException e)
		{
			// TODO Auto-generated catch block
			result.setResultCode(-1);
			return result;
		}
		return result;
	}

	private List buildReturnData(List reasonList)
	{
		List returnDataList = new ArrayList();
		List reasons = new ArrayList();
		List reasonsType = new ArrayList();

		for(int i=0;i<reasonList.size();i++)
		{
			Map reasonMap =(Map)reasonList.get(i);
			if(((String)reasonMap.get("level_id")).equals("2"))
			{
				reasonsType.add(reasonMap);
			}
			if(((String)reasonMap.get("level_id")).equals("3"))
			{
				reasons.add(reasonMap);
			}
		}
		for(int z=0;z<reasonsType.size();z++)
		{
			Map<String,List> reasonTypeMap = new HashMap<String,List>();
			List reasonDatas = new ArrayList();
			List reasonTypeDatas = new ArrayList();
			reasonTypeDatas.add(reasonsType.get(z));
			String id =(String)((Map)reasonsType.get(z)).get("id");
			for(int j=0;j<reasons.size();j++)
			{
				String reasonId = (String)((Map)reasons.get(j)).get("parent_id");
				if(id.equals(reasonId))
				{
					reasonDatas.add(reasons.get(j));
				}
			}
			reasonTypeMap.put("key",reasonTypeDatas);
			reasonTypeMap.put("value",reasonDatas);
			returnDataList.add(reasonTypeMap);
		}
		return returnDataList;
	}

	public static void main(String[] args) {
		String a = "073108922484A@tv";
		a = a.substring(0,a.indexOf("A"));
		System.out.println(a);
	}

	public Result getResourceInfoList(String workOrderId)
	{
		Result result = new Result();
		try
		{
			List reasonList = wfdao.getResourceInfoList(workOrderId);
			//List returnDataList = buildReturnData(reasonList);
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put(AuthRefresh.ReturnData, reasonList);
			result = Result.buildSuccess(resultData);
			System.out.println(resultData);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			result.setResultCode(-1);
			return result;
		}
		return result;
	}


	//上图
	public int insertMap(String staffId,String orgId,String smx,String smy,String state) {
		Result result = new Result();
		int insertNum = 0;
		try {
			String daoName = HuNanDAOImpl.class.getName();
			HuNanDAO dao =  (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
			Map<Object, Object> result_data_node;
			insertNum = dao.insertMap(staffId,orgId,smx,smy,state);
			return insertNum;

		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(-1);
			return insertNum;
		}


	}

	/**
	 * 改单
	 * @param json
	 * @return
	 */
		/*public Result modifyOrder(JSONObject json) {
			Result result = null;

			try {
			    String produceOrderId = json.getString("produceOrderId");
			    String eparchyCode = json.getString("eparchyCode");
			    String countyCode = json.getString("countyCode");
			    String countyName = json.getString("countyName");
			    String operateId = json.getString("operateId");
				String oprateName = json.getString("oprateName");
				String serviceNum = json.getString("serviceNum");
				String contactPhone = json.getString("contactPhone");
				String serviceType = json.getString("serviceType");
				String addressInfo = json.getString("addressInfo");
				String remark = json.getString("remark");

				StringBuffer sbReqXml = new StringBuffer();
				sbReqXml.append("<produceOrderId>" + produceOrderId + "</produceOrderId>");
				sbReqXml.append("<eparchyCode>" + eparchyCode + "</eparchyCode>");
				sbReqXml.append("<countyCode>" + produceOrderId + "</countyCode>");
				sbReqXml.append("<countyName>" + produceOrderId + "</countyName>");
				sbReqXml.append("<operateId>" + operateId + "</operateId>");
				sbReqXml.append("<oprateName>" + oprateName + "</oprateName>");
				sbReqXml.append("<serviceNum>" + serviceNum + "</serviceNum>");
				sbReqXml.append("<contactPhone>" + contactPhone + "</contactPhone>");
				sbReqXml.append("<serviceType>" + serviceType + "</serviceType>");
				sbReqXml.append("<addressInfo>" + addressInfo + "</addressInfo>");
				sbReqXml.append("<remark>" + remark + "</remark>");

				String reqXml = ws.getReqestXml(sbReqXml.toString(),
						"changeOrder");
				String retXml = "";
				if (debug )
					retXml = XmlUtil.getRemotelXmlData(FINISH_WORKORDER_INTERF);
				else 
//				   retXml =this.callWsService(FINISH_WORKORDER_INTERF, reqXml);
				System.out.println("传送报文:"+reqXml);
				System.out.println("返回报文: + " + retXml);
				result = parseReplyOrderResponse(retXml);

			} catch (Exception e) {
				e.printStackTrace();
				result = Result.buildInvokeInterError();
			}

			// }
			return result;
		}
*/
	public Result modifyOrder(JSONObject json) {
		String jsonResult = HttpUtils.getInstance().call(ComInfData.crmServiceUrl, "kt_modifyorder_url", json.toString());
		logger.info("<<<改单接口返回：>>>:" + jsonResult);
		Result result = new Result();
		if(StringUtil.isNull(jsonResult)){
			logger.error("modifyOrder接口返回为空");
			result.setResultCode(0);
			result.setResultMsg("接口调用失败");
		}else{
			//JSONObject fromObject = JSONObject.fromObject(jsonResult);
			Map<String, Object> mapJson = JSONObject.fromObject(jsonResult);
			Map<String, Object> root = (Map<String, Object>) mapJson.get("root");
			if(root!= null && !StringUtil.isNull((String)root.get("respCode"))){
				result.setResultCode(Integer.valueOf((String)root.get("respCode")));
				result.setResultMsg((String)root.get("respDesc"));
			}else{
				logger.error("modifyOrder接口返回格式错误");
			}
		}
		return result;
	}


	/**
	 * 待装工单查询
	 * @param username
	 * @param jobId
	 * @param pageSize
	 * @param pageIndex
	 * @param accNbr
	 * @param bokState
	 * @param bokTime
	 * @param createDate
	 * @return
	 */
	public Result selPendingWorkOrderByPageCondition(String username, Long jobId, Integer pageSize, Integer pageIndex,
													 String accNbr, String bokState, String bokTime, String createDate) {
		Result result = null;
		try {
			// String reqXml = getPublicWorkOrderRequest(username, jobId,
			// pageSize, pageIndex, accNbr, bokState, bokTime, createDate);
			String respXml = "";

			List paramList = new ArrayList<String>();
			paramList.add(username);
			paramList.add(jobId);
			paramList.add("");
			paramList.add(pageSize);
			paramList.add(pageIndex);
			paramList.add(accNbr);
			paramList.add(bokState);
			paramList.add(bokTime);
			paramList.add(createDate);
			paramList.add(new BigDecimal(0));

			String[] outParamArray = { "Requestid" };
			Map<String, Object> resultMap = getCommonDAO().commonQueryObjectByProcedure("Inf_App_Pending_Order_Query(?,?,?,?,?,?,?,?,?,?)", paramList, 9,
					outParamArray);
			String requestId = resultMap.get("Requestid").toString();
			String sql = "select request_xml as requestXml from  inf_app_log where id=?";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("id", requestId);
			String wherePatternStr = "id:id";
			Map<String, Object> queryMap = new HashMap<String, Object>();
			resultMap = getCommonDAO().commonQueryObjectBySql(sql, paramMap, wherePatternStr);
			respXml = (String) resultMap.get("requestXml");
			result = parsePublicKtWorkOrderResponse(respXml);
//			Map<Object, Object> resultData = result.getResultData();
//			StringBuilder orderIds = new StringBuilder();
//			if(resultData != null){
//				List<Object> orderList = (List<Object>) resultData.get("WorkOrderList");
//			    if(orderList != null && orderList.size()> 0){
//			    	for (int i = 0; i < orderList.size(); i++) {
//						Object order = orderList.get(i);
//						JSONObject orderJson = JSONObject.fromObject(order);
//						String orderId = (String) orderJson.get("orderId");
//						orderIds.append(orderId).append(",");
//					}
//			    	String orderIdParam = orderIds.toString();
//			    	orderIdParam = orderIdParam.substring(0, orderIdParam.lastIndexOf(","));
//			    	String sql1 = "select order_id as orderId, to_char(delay_time,'yyyy-mm-dd hh24:mi:ss') as delayTime,delay_reason as delayReason from order_delay_inf where order_id in (" + orderIdParam + ")";
//					resultMap = getCommonDAO().commonQueryListBySql(sql1, null, "");
//					List<Object> pendList = (List<Object>) resultMap.get("dataList");
//					for (int i = 0; i < pendList.size(); i++) {
//						Object order = pendList.get(i);
//						JSONObject orderJson = JSONObject.fromObject(order);
//						String orderId = (String) orderJson.get("orderId");
//						for (int j = 0; j < orderList.size(); j++) {
//							Object pendOrder = orderList.get(j);
//							JSONObject pendOrderJson = JSONObject.fromObject(pendOrder);
//							String pendOrderId = (String) pendOrderJson.get("orderId");
//							if (orderId.equals(pendOrderId)) {
//								if (orderJson.get("delayTime") != null) {
//									String delayTime = (String) orderJson.get("delayTime");
//									pendOrderJson.put("delayTime", delayTime);
//								}
//								try{
//								if (orderJson.get("delayReason") != null) {
//									String delayReason = (String) orderJson.get("delayReason");
//									pendOrderJson.put("delayReason", delayReason);
//								}
//								}catch(Exception e){
//									logger.error(e.getMessage());
//								}
//							}
//						}
//					}
//					
//			    }
//			}

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			result = Result.buildInvokeInterError();
		}
		return result;
	}


}
