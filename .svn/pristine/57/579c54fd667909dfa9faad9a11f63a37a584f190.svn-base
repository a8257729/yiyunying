package com.ztesoft.android.client.action.interf;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.ZipUtil;

//TODO 重构
public class SelInterfAction implements AndrBaseAction {
	
	//TODO 移出去！
	private static final String WS_URL = "http://10.45.48.154:18049/IOMPROJ/services/PFServicesForEBizPort?wsdl";
	private static final String WS_NAMESPACE = "";
	private static final String WS_METHOD_OPERATION_NAME = "pfServicesForEBiz";

	public Object doAction(HttpServletRequest request,HttpServletResponse response)  throws ServletException, IOException {
		
			/*JSONObject jsonData = new JSONObject();	
			Map params = new HashMap();
			Map requestMap = new HashMap();
			Map dataMap = new HashMap();
			Map paramsMap = new HashMap();
			requestMap.put("Data", dataMap);
			paramsMap.put("UseName", "admin");
			paramsMap.put("Comments", "我是测试");
			paramsMap.put("WorkOrderID", "444");
			String finishTimeStr = "2012-02-12 13:12:21";
			paramsMap.put("FinishTime", finishTimeStr);
			dataMap.put("QueryMethod", "finishWorkOrderForEBiz");	//接口方法
			dataMap.put("Params",paramsMap);
			jsonData.put("WSRequest", requestMap);
			jsonData.put("actionName", "SelInterfAction");
			jsonData.put("wsName", "finishWorkOrderForEBiz");	//回单
			params.put("params", jsonData.toString());	
			*/
			
			
		//调用接口服务
		EBizToIomWebservice service = new EBizToIomWebservice();
			
		request.setCharacterEncoding("UTF-8");	//处理乱码用
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		System.out.println("调用【SelInterfAction】，传入参数：" + jsonPara);
		String resultCode = null;
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		System.out.println("调用【SelInterfAction】，生成的JSON数据：" + jsonObject.toString());
		
		//StringBuilder responseXmlStr = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		String responseXmlStr = "";
		String newstr = "";
		JSONObject jsonObj = new JSONObject();
		String jsondata = null; 
		String xmlStr = null;
		try{
			//String wsRequestStr = jsonObject.getString("WSRequest");
			//wsRequestStr = URLDecoder.decode(wsRequestStr, "UTF-8");
			String methodStr = jsonObject.getString("QueryMethod");
			//String paramsStr = jsonObject.getString("Params");
			jsonObject.remove("QueryMethod");
			jsonObject.remove("actionName");
			//JSONObject paramsObject = JSONObject.fromObject(paramsStr);
			System.out.println("调用【SelInterfAction】，生成的Params的数据：" + jsonObject.toString());
			
			Map map = new HashMap();
			map.put("Params", jsonObject);
			map.put("QueryMethod", methodStr);
			
			JSONObject newObj = JSONObject.fromObject(map);
			
			XMLSerializer serl = new XMLSerializer();
			serl.setTypeHintsEnabled(false);
			serl.setObjectName("Data");
			//(1)弱智做法：String requestXmlStr = serl.write(newObj, "GBK").replace("encoding=\"GBK\"", "encoding=\"UTF-8\"");
			//别扭的处理编码问题，无语
			String requestXmlStr = new String(serl.write(newObj, "UTF-8").getBytes("GBK"), "UTF-8");	
			
			String[] paraName = {"infType","requestxml"};
			//
			System.out.println("调用【SelInterfAction】，XML字符串: " + requestXmlStr);
			
			if("finishWorkOrderForEBiz".equals(methodStr)) {	//服务开通回单接口
				
				/*StringBuilder responseXmlStrx = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
					responseXmlStrx.append("<Data>")
									.append("<WorkOrderID>TEST001</WorkOrderID>")
										.append("<Return>")
											.append("<Result>000</Result>")
											.append("<ErrorDesc>回单成功</ErrorDesc>")
										.append("</Return>")
									.append("</Data>");
				responseXmlStr = responseXmlStrx.toString(); */
				
				try{
					responseXmlStr = service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE, WS_METHOD_OPERATION_NAME, paraName,  new String[]{methodStr, requestXmlStr});
				}catch(Exception ex){
					//获取接口调用异常
					System.out.println("接口调用异常：" + ex.getMessage());
					throw new Exception("WebService接口调用异常");
				}
				newstr = ZipUtil.compress(this.formatResponseJSONForFinish(responseXmlStr.toString()).toString());  
			
			} else if("queryWorkOrderForEBiz".equals(methodStr)) {		//代办工单查询接口
				//测试数据
				/*StringBuilder responseXmlStrx = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
				responseXmlStrx.append("<Data>");
				responseXmlStrx.append("<QueryMethod>testMethod</QueryMethod>");
				responseXmlStrx.append("<Return>");
				responseXmlStrx.append("<ContentList>")
								.append("<Content>")
										.append("<Public>")
											.append("<WorkOrderID>").append("1113344").append("</WorkOrderID>")
											.append("<OrderCode>").append("ORDCODE").append("</OrderCode >")
											.append("<ServiceName>").append("SOS").append("</ServiceName>")
											.append("<AccNbr>").append("XT001").append("</AccNbr>")
											.append("<Address>").append("黄埔大道").append("</Address>")
											.append("<CustName>").append("李嘉诚").append("</CustName>")
											.append("<CustLinkPerson>").append("李超人").append("</CustLinkPerson>")
											.append("<CustLinkPhone>").append("18022394265").append("</CustLinkPhone>")
											.append("<SlaTime>").append("2012-01-01 12:13:43").append("</SlaTime>")
											.append("<TacheName>").append("node3").append("</TacheName>")
											.append("<CreateDate>").append("2012-11-21 12:13:43").append("</CreateDate>")
											.append("<WorkOrderType/>")
										.append("</Public>")
									.append("</Content>") 
									.append("<Content>")
										.append("<Public>")
											.append("<WorkOrderID>").append("1113345").append("</WorkOrderID>")
											.append("<OrderCode>").append("ORDCODE").append("</OrderCode >")
											.append("<ServiceName>").append("SOS").append("</ServiceName>")
											.append("<AccNbr>").append("XT001").append("</AccNbr>")
											.append("<Address>").append("广州大道").append("</Address>")
											.append("<CustName>").append("张飞机").append("</CustName>")
											.append("<CustLinkPerson>").append("张大炮").append("</CustLinkPerson>")
											.append("<CustLinkPhone>").append("18022394265").append("</CustLinkPhone>")
											.append("<SlaTime>").append("2012-01-01 12:13:43").append("</SlaTime>")
											.append("<TacheName>").append("node3").append("</TacheName>")
											.append("<CreateDate>").append("2012-11-21 12:13:43").append("</CreateDate>")
											.append("<WorkOrderType/>")
										.append("</Public>")
									.append("</Content>") 
									.append("</ContentList>")
								.append("</Return>")
							.append("</Data>"); 

				responseXmlStr = responseXmlStrx.toString(); */
				try{
					responseXmlStr = service.callWebService(ComInfData.iomServiceUrl, WS_NAMESPACE, WS_METHOD_OPERATION_NAME, paraName,  new String[]{methodStr, requestXmlStr});
				}catch(Exception ex){
					//获取接口调用异常
					System.out.println("接口调用异常：" + ex.getMessage());
					throw new Exception("WebService接口调用异常");
				}
				newstr = ZipUtil.compress(this.formatResponseJSONForQuery(responseXmlStr.toString()).toString());
			} /*else if(InterfConstants.Method.WS_METHOD_QRECDMARATEFOREBIZ.equals(methodStr)) {
				//调用指定的接口服务
				InterfService iserv = new CDMAQueryInterfService();
				responseXmlStr = iserv.doService(methodStr, jsonObject.toString());
				newstr = ZipUtil.compress(responseXmlStr);
			} else if(InterfConstants.Method.WS_METHOD_QRYMOMTINFOFOREBIZ.equals(methodStr)) {
				//调用指定的接口服务
				InterfService iserv = new MOMTQueryInterfService();
				responseXmlStr = iserv.doService(methodStr, jsonObject.toString());
				newstr = ZipUtil.compress(responseXmlStr);
			} else if(InterfConstants.Method.WS_METHOD_DXGETMOATTRIBUTE.equals(methodStr)) {
				//调用指定的接口服务
				InterfService iserv = new BlacklistQueryInterfService();
				responseXmlStr = iserv.doService(methodStr, jsonObject.toString());
				newstr = ZipUtil.compress(responseXmlStr);
			}*/
			System.out.println("接口返回XML报文："+ responseXmlStr);
		
		}catch(Exception ex){
			//ex.printStackTrace();
			newstr = "{\"result\": \"001\"}";
		}finally{
		    response.setContentType("text/plain;charset=ISO-8859-1");
		    response.getWriter().write(newstr);
		}
		return null;
	}
	
	private JSONObject formatResponseJSONForQuery(String xmlStr) {
		
		JSONObject responseJSON = new JSONObject();
		//JSONObject response = new JSONObject();
		XMLSerializer serl = new XMLSerializer();
		
		responseJSON.put("total", "0");
		try {
			// 将返回的XML格式的数据转换成JSON格式
			JSONObject rawResponseObj = JSONObject.fromObject(serl.read(
					xmlStr.toString()).toString());
			JSONObject returnData = rawResponseObj.getJSONObject("Return");
			JSONArray contentData = returnData.optJSONArray("ContentList");

			if (null == contentData) {
				JSONObject contentObj = returnData.optJSONObject("ContentList");
				if(null != contentObj) {
					JSONArray arr = new JSONArray();
					
					JSONObject bean = contentObj.getJSONObject("Content").getJSONObject("Public");
					JSONObject newObj = new JSONObject();
					newObj.put("name1",
							"定单编码: "
									+ bean.optString("OrderCode", "")
											.replace("[]", "")); // 定单编码
					newObj.put("displayType", "2");
					newObj.put("name2",
							"联系电话: "
									+ bean.optString("CustLinkPhone", "")
											.replace("[]", "")+"\n"+
							"客户名称: "
									+ bean.optString("CustName", "")
											.replace("[]", ""));
					newObj.put("name3", "");
					newObj.put("name4",
							"预约时间: "
									+ bean.optString("SlaTime", "")
											.replace("[]", ""));
					newObj.put("name5",
							"工单类型: "
									+ bean.optString("WorkOrderType", "")
											.replace("[]", ""));
					newObj.put("id", bean.optString("WorkOrderID", "")
											.replace("[]", ""));
					
					newObj.put("name6",
							"装机地址: "
									+ bean.optString("Address", "")
											.replace("[]", ""));
					if(bean.has("TroubleOrderStatus")){
					newObj.put("name7",
							"工单类别: "+ bean.optString("TroubleOrderStatus", "").replace("[]", ""));
					}else {
						newObj.put("name7","工单类别: 装,移单");
					}
					// newObj.put("name6", "");

					arr.add(newObj);
					
					JSONObject listDataObj = new JSONObject();
					listDataObj.put("listdata", arr);
					
					responseJSON.put("body", listDataObj.toString());
					responseJSON.put("total", "" + arr.size());
				}
			} else {

				if (null != contentData && 0 != contentData.size()) {
					//
					JSONArray arr = new JSONArray();
					for (int i = 0; i < contentData.size(); i++) {
						JSONObject bean = contentData.getJSONObject(i)
								.getJSONObject("Public");
						JSONObject newObj = new JSONObject();
						newObj.put("name1",
								"定单编码: "
										+ bean.optString("OrderCode", "")
												.replace("[]", "")); // 定单编码
						newObj.put("displayType", "2");
						newObj.put("name2",
								"联系电话: "
										+ bean.optString("CustLinkPhone", "")
												.replace("[]", "")+"\n"+
								"客户名称: "
										+ bean.optString("CustName", "")
												.replace("[]", ""));
						newObj.put("name3", "");
						newObj.put("name4",
								"预约时间: "
										+ bean.optString("SlaTime", "")
												.replace("[]", ""));
						newObj.put("name5",
								"工单类型: "
										+ bean.optString("WorkOrderType", "")
												.replace("[]", ""));
						newObj.put("id", bean.optString("WorkOrderID", "")
												.replace("[]", ""));
						
						newObj.put("name6",
								"装机地址: "
										+ bean.optString("Address", "")
												.replace("[]", ""));
						// newObj.put("name6", "");

						newObj.put("id", bean.optString("WorkOrderID", "")
								.replace("[]", ""));
						arr.add(newObj);
						// System.out.println("xx=000000000000222-------" +
						// bean);
					}
					JSONObject listDataObj = new JSONObject();
					listDataObj.put("listdata", arr);

					responseJSON.put("body", listDataObj.toString());
					responseJSON.put("total", "" + arr.size());
				} else {
					JSONArray arr = new JSONArray();

					// JSONObject newObj = new JSONObject();
					// newObj.put("name1", ""); //工单ID
					// newObj.put("name2", "");
					// newObj.put("name3", "");
					// newObj.put("name4", "");
					// newObj.put("name5", "");
					// newObj.put("name6", "");
					// newObj.put("name7", "工单类型: " +
					// bean.getString("WorkOrderType"));
					// newObj.put("id", "-111");
					// arr.add(newObj);

					JSONObject listDataObj = new JSONObject();
					listDataObj.put("listdata", arr);

					responseJSON.put("body", listDataObj.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseJSON.put("result", "001");
		}

		//写死的数据
		responseJSON.put("buttons", JSONObject.fromObject("{\"buttondata\":[]}"));
		responseJSON.put("formName", "代办工单查询");
		responseJSON.put("nowpage", "pagelist");
		responseJSON.put("nowpage", "pagelist");
		responseJSON.put("keys", "[\"name1\",\"name2\",\"name3\",\"name4\",\"name5\",\"name6\"]");
		
		
		//responseJSON.put("response", response);
		responseJSON.put("result", "000");
		
		System.out.println("Response的JSON报文是：" + responseJSON.toString());
		
		return responseJSON;
	}

	
	private JSONObject formatResponseJSONForFinish(String xmlStr) {
		
		JSONObject responseJSON = new JSONObject();
		XMLSerializer serl = new XMLSerializer();
		try{
			//将返回的XML格式的数据转换成JSON格式
			JSONObject rawResponseObj = JSONObject.fromObject(serl.read(xmlStr.toString()).toString());
			JSONObject returnObj = rawResponseObj.getJSONObject("Return");
			responseJSON.put("result", returnObj.optString("Result","001"));
			responseJSON.put("errorDesc", returnObj.optString("ErrorDesc","回单失败"));
			
		}catch(Exception e){
			e.printStackTrace();
			responseJSON.put("result", "001");
		}
		
		System.out.println("Response的JSON报文是：" + responseJSON.toString());
		
		return responseJSON;
	}	
}
