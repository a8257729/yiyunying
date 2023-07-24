package com.ztesoft.mobile.v2.service.workform.xinjiang;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.collections.MapUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.ztesoft.android.common.ComInfData;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.core.Result;

public class WebService {

	private boolean debug = true;
	
	public String getReqestXml(String reqXml, String QryMethod) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<Data>");
		sb.append("<QueryMethod>" + QryMethod + "</QueryMethod>");
		sb.append("<Params>");
		sb.append(reqXml);
		sb.append("</Params>");
		sb.append("</Data>");
		System.out.println("reqXml=============" + sb.toString());
		return sb.toString();
	}

	public String call(String[] paraName, String[] paraValues) throws Exception {
		Service service = new Service();
		String result = "";
		String url = "";
		String serviceCode = ComInfData.iomServiceUrl;
		String operationNameSpqce = "";
		String operationName = "pfServicesForEBiz";
		try {
			/*
			 * System.out.println(getClass().getProtectionDomain().getCodeSource(
			 * ) .getLocation().getPath());
			 */
			url = this.getServiceUrl(serviceCode);
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperation(operationName);
			if (!"".equals(operationNameSpqce) && operationNameSpqce != null) {
				call.setOperationName(new javax.xml.namespace.QName(
						operationNameSpqce, operationName));
			} else {
				call.setOperationName(operationName);
			}
			for (int i = 0; i < paraName.length; i++) {
				call.addParameter(paraName[i], XMLType.XSD_STRING,
						ParameterMode.IN);
			}

			call.setReturnType(XMLType.XSD_STRING);
			result = (String) call.invoke(paraValues);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}

		return result;

	}

	public String getServiceUrl(String serviceCode) {
		String url = "http://10.45.47.178:7001/IOMPROJ/services/PFServicesForEBizPort";// (String)
																						// CustomizedPropertyPlaceholderConfigurer.getContextProperty(serviceCode);
		return url;
	}

	/*
	 * 调用webservice接口
	 * 
	 * @param retXml
	 */
	public String call(String reqXml) {
		return "";
	}

	/**
	 * 
	 * @param resultData
	 * @return Result
	 */
	public Result getResult(Map<Object, Object> resultData) {
		Result result = null;
		String errCode = MapUtils.getString(resultData, "errCode");
		String errMsg = MapUtils.getString(resultData, "ErrorDesc");
		String desc = MapUtils.getString(resultData, "desc");
		String rst = MapUtils.getString(resultData, "Result");
		if ("0".equals(rst) || "2".equals(rst))
			result = Result.buildSuccess();
		else {
			result = new Result(Constants.OptCode.SERVER_INTERNAL_ERROR, errMsg);
		}

		result.setResultData(resultData);
		return result;
	}

	/**
	 * 解析回单页面相关数据
	 */
	public Map<Object, Object> getWorkOrderViewResultData(String retXml)
	{
		retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<Data>"
				+ "<QueryMethod>loadFinishWorkOrderViewData</QueryMethod>"
				+ "<Return>"
				+ "<Content>"
				+ "<RepairReasonList>"
				+ "<RepairReason><ReasonId>123</ReasonId><ReasonName>端口坏</ReasonName></RepairReason>"
				+ "<RepairReason><ReasonId>124</ReasonId><ReasonName>地址错误</ReasonName></RepairReason>"
				+ "</RepairReasonList>"
				+ "<WorkOperList>"
				+ "<WorkOper><StaffId>56012</StaffId><StaffName>中兴维护</StaffName><StaffTel>13800000</StaffTel></WorkOper>"
				+ "<WorkOper><StaffId>56013</StaffId><StaffName>济南管理员</StaffName><StaffTel>13700000</StaffTel></WorkOper>"
				+ "</WorkOperList>" + "<RepairLinkMan>张婕妤</RepairLinkMan>"
				+ "<RepairLinkManTel>1234</RepairLinkManTel>"
				+ "<RepairTime>2012-5-9 21:55:12</RepairTime>"
				+ "<AlarmState>2</AlarmState>" + "</Content>"
				+ "<Result>000</Result><ErrorDesc>查找成功</ErrorDesc>"
				+ "</Return>" + "</Data>";

		Map<Object, Object> resultData = new HashMap<Object, Object>();
		SAXReader reader = new SAXReader();
		String result;
		String errorDesc;
		String recoverConfirmStaff;
		String confirmTel;
		String recoverTime;
		String alarmState;

		try {
			StringReader sr = new StringReader(retXml);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element Data = doc.getRootElement();
			Element Return = Data.element("Return");

			// 获取返回代码及描述信息
			result = Return.element("Result").getText();
			errorDesc = Return.element("ErrorDesc").getText();

			Element Content = Return.element("Content");
			Element RepairReasonListEle = Content.element("RepairReasonList");
			Element WorkOperListEle = Content.element("WorkOperList");

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
			confirmTel = Content.element("RepairLinkManTel").getStringValue();
			recoverTime = Content.element("RepairTime").getStringValue();
			alarmState = Content.element("AlarmState").getStringValue();

			resultData.put("Result", result);
			resultData.put("ErrorDesc", errorDesc);
			resultData.put("RECOVER_REASON_LIST", reasonList_);
			resultData.put("MAINTAIN_STAFF_LIST", workOperList_);
			resultData.put("RECOVER_CONFIRM_STAFF", recoverConfirmStaff);
			resultData.put("CONFIRM_TEL", confirmTel);
			resultData.put("RECOVER_TIME", recoverTime);
			resultData.put("ALARM_STATE", alarmState);

			System.out.println("resultData===" + resultData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}

	/**
	 * 解析原因列表
	 * 
	 * @param retXml
	 * @return
	 */
	public Map<Object, Object> getReasonResultData(String retXml) {
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

			Element ReasonListEle = Data.element("ReasonList");

			List reasonList = ReasonListEle.elements("Reason");
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
			resultData.put("CANCEL_REASON_LIST", reasonList_);
			System.out.println("resultData===" + resultData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}

	public Map<Object, Object> getResultData(String retXml) {
		SAXReader reader = new SAXReader();
		Map<Object, Object> resultData = new HashMap<Object, Object>();
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

			resultData.put("Result", result);
			resultData.put("ErrorDesc", errorDesc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;

	}

	public static void main(String[] args) {
		WebService ws = new WebService();
		String url = "http://10.45.47.178:7001/IOMPROJ/services/PFServicesForEBizPort";
		String queryMethod = "returnReasonListForEBiz";
		String inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Data><QueryMethod>"
				+ queryMethod
				+ "</QueryMethod><Params><WorkOrderID>232734</WorkOrderID></Params></Data>";
		String a;
		try {
			String[] paraName = { "a", "b" };
			String[] paraValues = { inXml, "returnReasonListForEBiz" };
			// a = eBizToIomWebservice.call(paraName, paraValues);
			ws.getWorkOrderViewResultData("");
			// System.out.println(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
