package com.ztesoft.mobile.v2.service.workform.xinjiang;

import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAO;
import com.ztesoft.mobile.v2.dao.workform.xinjiang.WorkOrderDAOImpl;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.OrgStaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimOrgInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.SimStaffInfo;
import com.ztesoft.mobile.v2.entity.workform.xinjiang.bz.PnetReportOrder;
import com.ztesoft.mobile.v2.service.workform.XmlUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/***
 * 公用工单管控功能实现类
 * 
 ** */

@Service("workOrderXjPublicService")
public class WorkOrderPublicServiceImpl extends BaseService implements
		WorkOrderPublicService {

	private static final boolean debug = false;

	
	private static final Logger logger = Logger
			.getLogger(WorkOrderPublicServiceImpl.class);

	private static final String[] PARAM_NAME = { "infType", "requestxml" };

	/** * 工单反馈*/
	private static final String FEEDBACKWORKORDERFOREBIZ = "FeedBackWorkOrderForEBiz";
	
	/** * 组织树 */
	private static final String ORGTREEFOREBIZ = "staff_org_list";
	
	/** * 授权组织树 */
	private static final String AUTHORGTREEFOREBIZ = "auth_staff_org_list";
	/** * 预约时段 */
	private static final String APPOINT_SEQ_FOREBIZ = "appointTimeSeqForEBiz";
								
	// 调用接口服务
	private static final EBizToIomWebservice service = new EBizToIomWebservice();
	private static final WebService ws = new WebService();

	private String callWsService(String method, String reqXml) throws Exception {
		return service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE,
				WS_METHOD_OPERATION_NAME, PARAM_NAME, new String[] { method,
						reqXml });
	}

	private MobileCommonDAO getMobileCommonDAO() {
        String daoName = MobileCommonDAOImpl.class.getName();
        return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
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

	/**
	 * 调用反馈接口 
	 */
	public Result reportWorkOrder(JSONObject json) {
		Result result = null;
		try {
			String workOrderId = json
					.optString(PnetReportOrder.WORKORDER_ID_NODE);
			String orderId = json.optString(PnetReportOrder.ORDER_ID_NODE);
			String staffId = json.optString(PnetReportOrder.STAFF_ID_NODE);
			//String staffName = json.optString(PnetReportOrder.STAFF_NAME_NODE);
			String reportContent = json
					.optString(PnetReportOrder.REPORT_CONTENT_NODE);

			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<WorkOrderID>" + workOrderId + "</WorkOrderID>");
			sbReqXml.append("<orderId>" + orderId + "</orderId>");
			sbReqXml.append("<FeedBackComment>" + reportContent
					+ "</FeedBackComment>");
			sbReqXml.append("<StaffId>" + staffId + "</StaffId>");
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					FEEDBACKWORKORDERFOREBIZ);
			String retXml = "";
			if (debug) {
				retXml = XmlUtil.getRemotelXmlData(FEEDBACKWORKORDERFOREBIZ);
			} else {			
				retXml = this.callWsService(FEEDBACKWORKORDERFOREBIZ, reqXml);
			}
			System.out.println("传送报文:"+reqXml);
			System.out.println("返回报文: + " + retXml);
			result = parsePublicOrderResponse(retXml);

		} catch (Exception e) {
			e.printStackTrace();
			result = Result.buildServerError();
		}
		return result;
	}


	public static void main(String[] args) {
		WorkOrderPublicServiceImpl wobzs = new WorkOrderPublicServiceImpl();
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
//		Result result = wobzs.parseWorkOrderDetailBzResponse(respXml);
//		System.out.println(result);
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
	
	public Result qryOrgTree(JSONObject json) {
		// TODO Auto-generated method stub
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		String OrgId = json.getString("OrgId");
		String IsFirst = json.getString("IsFirst");
//		StringBuffer sbReqXml = new StringBuffer();
//		sbReqXml.append("<OrgId>" + OrgId + "</OrgId>");
		try{
			String retXml="";
//			String reqXml = ws.getReqestXml(sbReqXml.toString(),
//					ORGTREEFOREBIZ);
			if (false){
				if("1".equals(OrgId))
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ+"_next");
				else if("3".equals(OrgId))
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ+"_next2");
				else
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ);
				
				result=parseSelOrgTree(retXml);
			}else {
				//查询同步数据库
				Map<String, Object> paramMap = new HashMap<String, Object>();
		        paramMap.put("OrgId", OrgId);
		        paramMap.put("IsFirst", IsFirst);
		        List<Map> resultList = getMobileCommonDAO().selOrgTree(paramMap);
//				retXml = callWsService(ORGTREEFOREBIZ,reqXml);
		        resultData.put("Listdata", resultList);
				result = Result.buildSuccess(resultData);	
			}
	        //判断是否有返回
//			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");	
//			result=parseSelOrgTree(retXml);
			
			
		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		
			result = Result.buildInvokeInterError();
		}
		return result;	
		
	}

	public Result qryAllOrgTree(JSONObject json) {
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		//String orgId = json.getString("orgId");
		String orgPathCode = json.getString("orgPathCode");
		Map<String, Object> paramMap = new HashMap<String, Object>();
       // paramMap.put("orgId", orgId);
        paramMap.put("orgPathCode", orgPathCode);
		try{
		        List<Map> resultList = getMobileCommonDAO().selAllOrgTree(paramMap);

		        resultData.put("Listdata", resultList);
				result = Result.buildSuccess(resultData);	
		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		
			result = Result.buildInvokeInterError();
		}
		return result;	
		
	}
	public Result qryAuthOrgTree(JSONObject json) {
		// TODO Auto-generated method stub
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		String OrgId = json.getString("OrgId");
		String IsFirst = json.getString("IsFirst");
		String ParentOrgId = json.getString("ParentOrgId");
//		StringBuffer sbReqXml = new StringBuffer();
//		sbReqXml.append("<OrgId>" + OrgId + "</OrgId>");
		try{
			String retXml="";
//			String reqXml = ws.getReqestXml(sbReqXml.toString(),
//					ORGTREEFOREBIZ);
			if (false){
				if("1".equals(OrgId))
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ+"_next");
				else if("3".equals(OrgId))
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ+"_next2");
				else
					retXml  = XmlUtil.getRemotelXmlData(ORGTREEFOREBIZ);
				
				result=parseSelOrgTree(retXml);
			}else {
				//查询同步数据库
				Map<String, Object> paramMap = new HashMap<String, Object>();
		        paramMap.put("OrgId", OrgId);
		        paramMap.put("IsFirst", IsFirst);
		        paramMap.put("ParentOrgId", ParentOrgId);
		        List<Map> resultList = getMobileCommonDAO().selAuthOrgTree(paramMap);
//				retXml = callWsService(ORGTREEFOREBIZ,reqXml);
		        resultData.put("Listdata", resultList);
				result = Result.buildSuccess(resultData);	
			}
	        //判断是否有返回
//			Document doc = Dom4jUtils.fromXML(retXml, "UTF-8");	
//			result=parseSelOrgTree(retXml);
			
			
		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		
			result = Result.buildInvokeInterError();
		}
		return result;	
		
	}
	public Result qryFaultReasonTree(JSONObject json) {
		// TODO Auto-generated method stub
		logger.debug("===========================reason="+json.toString());
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		String JobId = json.optString("JobId");//预留参数
		String FaultKind = json.optString("FaultKind");//障碍类型 语音100001 宽带 100002 带宽200001
		try{
			String retXml="";
			
				//查询同步数据库
				Map<String, Object> paramMap = new HashMap<String, Object>();
		        paramMap.put("JobId", JobId);
		        //设置需要查询的故障原因
		        paramMap.put("FaultKind", JobId);
		        if (logger.isDebugEnabled()) {
					logger.debug("===========================FaultKind="+FaultKind);
				}
		        List<Map> resultList = getMobileCommonDAO().qryFaultReasonTree(paramMap);
//				retXml = callWsService(ORGTREEFOREBIZ,reqXml);
		        resultData.put("Listdata", resultList);
				result = Result.buildSuccess(resultData);	
			
			
			
		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		
			result = Result.buildInvokeInterError();
		}
		return result;	
		
	}

	public Result qryHandTree(JSONObject json) {
		// TODO Auto-generated method stub
		logger.debug("===========================reason="+json.toString());
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		String JobId = json.optString("JobId");//预留参数
		String FaultKind = json.optString("FaultKind");//障碍类型 语音100001 宽带 100002 带宽200001
		try{
			String retXml="";

			//查询同步数据库
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("JobId", JobId);
			//设置需要查询的故障原因
			paramMap.put("FaultKind", JobId);
			if (logger.isDebugEnabled()) {
				logger.debug("===========================FaultKind="+FaultKind);
			}
			List<Map> resultList = getMobileCommonDAO().qryHandTree(paramMap);
//				retXml = callWsService(ORGTREEFOREBIZ,reqXml);
			resultData.put("Listdata", resultList);
			result = Result.buildSuccess(resultData);



		}catch(Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildInvokeInterError();
		}
		return result;
	}

    public Result qryStaffIdArea(JSONObject json) {
        // TODO Auto-generated method stub
        logger.debug("===========================reason="+json.toString());
        Result result = null;
        Map<Object, Object> resultData = new HashMap<Object, Object>();
        String staffId = json.optString("staffId");//预留参数

        try{

            if (logger.isDebugEnabled()) {
                logger.debug("===========================staffId="+staffId);
            }
           Map resultMap = getMobileCommonDAO().qryStaffIdArea(staffId);
//				retXml = callWsService(ORGTREEFOREBIZ,reqXml);
            resultData.put("Listdata", resultMap);
            result = Result.buildSuccess(resultData);



        }catch(Exception e) {
        	e.printStackTrace();
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage());
            }

            result = Result.buildInvokeInterError();
        }
        return result;
    }

	public Result timeSeqQuery(JSONObject json) {
		Result result = null;
		try {
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			String BokDate = json.getString("BokDate");
			String StaffId = json.getString("StaffId");
			StringBuffer sbReqXml = new StringBuffer();
			sbReqXml.append("<StaffId>" + StaffId + "</StaffId>");
			sbReqXml.append("<BokDate>" + BokDate + "</BokDate>");
			String retXml="";
			String reqXml = ws.getReqestXml(sbReqXml.toString(),
					APPOINT_SEQ_FOREBIZ);
			if (false)
				retXml  = XmlUtil.getRemotelXmlData(APPOINT_SEQ_FOREBIZ);
			else 
				retXml = callWsService(APPOINT_SEQ_FOREBIZ,
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
	
	private Result parseSelOrgTree(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("组织查询响应报文: " + respXml);
		}
		Result result = null;
		Map<Object, Object> resultData = new HashMap<Object, Object>();
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");

			Node resultNode = doc.selectSingleNode("/Data/Return/Result");
			String resultVal = resultNode.getText();

			if ("000".equals(resultVal)) { // 成功

				List<Element> rtEleList = doc
						.selectNodes("/Data/Return/ContentList/Content");
				int size = rtEleList.size();				
				List<OrgStaffInfo> newStaffList_ = new ArrayList<OrgStaffInfo>(size);				
				for (int i = 0; i < size; i++) {
					Element ele_ = rtEleList.get(i);
					OrgStaffInfo item = new OrgStaffInfo();
					item.setId(Long.parseLong(ele_.selectSingleNode("id").getText()));					
					item.setName(ele_.selectSingleNode("name").getText());
					item.setPartyType(ele_.selectSingleNode("partyType").getText());
					item.setLevel(ele_.selectSingleNode("level").getText());
					item.setExpanded(ele_.selectSingleNode("expanded").getText());
					item.setMhasChild(ele_.selectSingleNode("mhasChild").getText());
					item.setMhasParent(ele_.selectSingleNode("mhasParent").getText());					
					item.setParent(ele_.selectSingleNode("parent").getText());					
					newStaffList_.add(item);
				}
					resultData.put("Listdata", newStaffList_);
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

	
	private String getSeriNo()
    {
    	Date currentTime = new Date();
    	DateFormat df= new SimpleDateFormat("yyyyMMddHHmmss"); 
    	return df.format(currentTime);
    }
	
}
