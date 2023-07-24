package com.ztesoft.mobile.service.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.dao.ServiceDAO;
import com.ztesoft.android.dao.ServiceDAOImpl;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;
import com.ztesoft.eoms.exintf.util.dao.UosConfigDAO;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.service.bo.RequestObject;
import com.ztesoft.mobile.service.bo.ResponseObject;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAO;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;

public class WorkInfoHandler  extends AbstractHandler {
	private static Logger logger = Logger.getLogger(WorkInfoHandler.class);
	//private static String iomServiceUrl="http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort";
	//private static String queryWorkDetail="finishWorkOrderForEBiz";
	private static String queryWorkDetail="queryWorkOrderDetailForEBiz";
	private static String iomServiceFunc="pfServicesForEBiz";
	private static String QRY_FINISH_WORK_LIST="qryFinishWorkList";
	private static String FINISH_WORK_LIST_PAGE="FINISH_WORK_LIST";
	private static HashMap JsonName=new HashMap();
	private static String[]paraName={"infType","requestxml"};
	
	static{
		JsonName.put("WorkOrderID","工单编号");
		JsonName.put("OrderCode","定单编码");
		JsonName.put("ServiceName","业务名称");
		JsonName.put("AccNbr","业务号码");
		JsonName.put("CustName","客户名称");
		JsonName.put("CustLinkPerson","客户联系人名称");
		JsonName.put("CustLinkPhone","客户联系人电话");
		JsonName.put("TacheName","环节名称");
		//JsonName.put("TacheCode","环节编码");
		JsonName.put("Address","装机地址");
		JsonName.put("SlaTime","预约时间");
		JsonName.put("CreateDate","工单派发时间");
		JsonName.put("WorkOrderType","工单类型");
		JsonName.put("Mdfv","直列");
		JsonName.put("Mdfh","横列");
		JsonName.put("CabinetName","交接箱名称");
		JsonName.put("Cabinet","交接箱");
		JsonName.put("CabinetIn","交接箱入序（交接箱主干端）");
		JsonName.put("CabinetOut","交接箱出序（交接箱配线端）");
		JsonName.put("PCableP1","主缆线对");
		JsonName.put("Pcable1","主缆号");
		JsonName.put("Scable1","配缆线对");
		JsonName.put("ScableP1","配缆号");
		JsonName.put("Panel1","分线盒号");
		JsonName.put("PanelP1","分线盒序");
		JsonName.put("AccessType","接入方式，如：宽带的接入方式：传统(ADSL)");
		JsonName.put("AdslEquipType","ADSL设备");
		JsonName.put("ScaleDataOut","ADSL端口");
		JsonName.put("AreaSwitchName","LAN小区交换机编号");
		JsonName.put("SwitchEquip","LAN楼道交换机");
		JsonName.put("SwitchPort","LAN楼道交换机端口");
		JsonName.put("OBD_NAME","分光器名称");
		JsonName.put("LOID","LOID");
		JsonName.put("ONU_No","ONU编码");
		JsonName.put("ONU_NAME","ONU名称");
		JsonName.put("ONU_IP","ONU的IP地址");
		JsonName.put("ONU_PORT_NO","ONU数据业务端口");
		JsonName.put("VLAN_ID","数据VLAN");
		JsonName.put("EID","域名");
		JsonName.put("OLT_IP","OLT IP地址");
		JsonName.put("SVLAN","SVLAN");
		JsonName.put("POS_PORT","POS端口");
		JsonName.put("POS_ADDR","分光器地址");
		JsonName.put("PON_PORT","PON卡槽位端口");
		JsonName.put("queryWorkOrderDetailForEBiz","SEARCH_WORK_DETAIL");

	}
	

	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		//System.out.println("传入参数!!:" + jsonPara);
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String funcName=jsonObject.getString("function");
		String result="";
		if("WorkOrderDetailQuery".equals(funcName)){
			paramMap.put("_jsonPara", jsonPara);
			result=queryWorkOrderDetail(paramMap);
		}else if(QRY_FINISH_WORK_LIST.equals(funcName)){
			result=qryFinishWorkList(jsonPara);
		}
		String newstr = ZipUtil.compress(result);   
		/*System.out.println("看看可以实时更新不？");   
		System.out.println("压缩后的字符串="+newstr);   
		System.out.println("压缩后的长="+newstr.length());  */
		     
		//response.setContentType("text/plain;charset=ISO-8859-1");
		//response.getWriter().write(newstr);
	    //ResponseObject respObj = new ResponseObject();
	    //respObj.setResponse(newstr);
		//return respObj;
		paramMap.put("response", newstr);
	}
	public String queryWorkOrderDetail(Map paramMap){
		String jsonPara = MapUtils.getString(paramMap, "_jsonPara");
		String result="";
		EBizToIomWebservice eBizToIomWebservice=new EBizToIomWebservice();
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonPara);
			JSONObject jsonData=jsonObject.optJSONObject("datas");
			String workOrderId=jsonData.getString("workOrderId");
			//创建查询报文
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("Data");
			Element queryMethodEl = root.addElement("QueryMethod");
			queryMethodEl.setText(queryWorkDetail);
			Element paramsEl=root.addElement("Params");
			Element workOrderEl = paramsEl.addElement("WorkOrderID");
			workOrderEl.setText(workOrderId);
			String requestXml=doc.asXML();
			System.out.println("移动平台发送工单详情查询的请求报文："+requestXml);
			
			paramMap.put("requestXml", requestXml);
			this.beforeCallWS(paramMap);		//调用接口之后
			
			//发送查询请求
			String[] paraValues=new String[]{queryWorkDetail,requestXml};
			String retXml=eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceFunc, paraName, paraValues);
			//解析查询报文，把XML转换成JSON
			System.out.println(retXml);
			result=parseXMLforWorkDetail(retXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		paramMap.put("responseXml", result);
		this.afterCallWS(paramMap);		//调用接口
		
		System.out.println(result);
		return result;
	}
	//解析查询报文，把XML转换成JSON
	public String parseXMLforWorkDetail(String xmlStr) throws Exception{
		String result=null;
		JSONObject content=null;
		SAXReader reader = new SAXReader();
		try {
			StringReader sr = new StringReader(xmlStr);   
			InputSource is = new InputSource(sr);   
			Document doc = reader.read(is);
			Element root=doc.getRootElement();
			Element retEle=root.element("Return");
			Element resultEle=retEle.element("Result");
			if("000".equals(resultEle.getText())){
				Element contentEle=retEle.element("Content");
				System.out.println(contentEle.asXML());
				
				result=JsonUtils.getJsonStrFromXML(contentEle.asXML());
				content =  new JSONObject().fromObject(result);
				
				
				//工单基本信息
				JSONObject baseInfo = content.optJSONObject("Public")!=null?content.optJSONObject("Public"):new JSONObject();
				
				JSONObject resource=content.optJSONObject("Resource");
				//新资源信息
				JSONObject resinfo = resource.optJSONObject("Resinfo")!=null?resource.optJSONObject("Resinfo"):new JSONObject();
				//旧资源信息
				JSONObject oldresinfo=resource.optJSONObject("Oldresinfo")!=null?resource.optJSONObject("Oldresinfo"):new JSONObject();
				content=new JSONObject();
				//把节点名根据映射，翻译成中文
				content.put("isSuccess", "0");
				String paramValue = UosConfigDAO.getInstance().getValue("WORK_DETAIL_CONFIG");
				if(paramValue!=null&&!"".equals(paramValue)){//使用库表配置字段
					try{
						Map paramMap = new HashMap();
						paramMap.put("teachName", paramValue);
						List pageIdList = getJsonCreateDAO().selByName(paramMap);
						if (pageIdList==null||pageIdList.size()<1) {
							throw new Exception();
						} else {
							Map pageMap = (Map) pageIdList.get(0);
							// 查询出解析字段的配置信息
							List<Map> filedList = getServiceDAO().getFiledByFormId(
									pageMap.get("formId").toString());
							HashMap jsonName=new HashMap();
							if(filedList==null||filedList.size()<1){
								throw new Exception();
							}
							for(int i=0;i<filedList.size();i++){
								Map filedMap = new HashMap();
								filedMap = filedList.get(i);
								String filedName = (String) filedMap.get("filedName");
								String filedLable = (String) filedMap.get("filedLable");
								jsonName.put(filedName, filedLable);
							}
							content.put("工单基本信息", tranJsonNameByConfig(baseInfo,jsonName));
							content.put("新资源信息", tranJsonNameByConfig(resinfo,jsonName));
							content.put("旧资源信息", tranJsonNameByConfig(oldresinfo,jsonName));
						}
					}catch(Exception e){
						content=new JSONObject();
						content.put("isSuccess", "1");
						Element errorDesc=retEle.element("ErrorDesc");
						String errorDescStr= errorDesc.getText()!=null?errorDesc.getText():"";
						content.put("ErrorDesc", "未配置工单详情页面！");
						content.put("funcName", JsonName.get("queryWorkOrderDetailForEBiz"));
						result=content.toString();
					}
					
				}else{//不适用库表配置字段
					content.put("工单基本信息", tranJsonName(baseInfo));
					content.put("新资源信息", tranJsonName(resinfo));
					content.put("旧资源信息", tranJsonName(oldresinfo));
				}
				content.put("funcName", JsonName.get("queryWorkOrderDetailForEBiz"));
				result=content.toString();
			}else{
				content=new JSONObject();
				content.put("isSuccess", "1");
				Element errorDesc=retEle.element("ErrorDesc");
				String errorDescStr= errorDesc.getText()!=null?errorDesc.getText():"";
				content.put("ErrorDesc", errorDescStr);
				content.put("funcName", JsonName.get("queryWorkOrderDetailForEBiz"));
				result=content.toString();
			}
			/*result="{\"旧资源信息\":{\"横列\":\"456\",\"直列\":\"123\"},\"新资源信息\":{\"横列\":\"456\",\"直列\":\"123\"},\"工单基本信息\":{\"客户联系人名称\":\"sdf\",\"定单编码\":\"wer\",\"客户联系人电话\":\"1245423543\",\"预约时间\":\"etetet\",\"工单派发时间\":\"1343\",\"环节编码\":\"43243\",\"业务号码\":\"134543\",\"环节名称\":\"576575\",\"业务名称\":\"qwwqww\",\"待办工单ID\":\"123\"," +
					"\"工单类型\":\"a\",\"客户名称\":\"vscdv\",\"装机地址\":\"xcvcxvcx\"}}";*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	//把节点名根据映射，翻译成中文
	public JSONObject tranJsonName(JSONObject json){
		if(json==null){
			return new JSONObject();
		}
		Iterator it=json.keys();
		JSONObject content =  new JSONObject();
		while(it.hasNext()){
			String textName = (String) it.next().toString();
			String textValue = json.getString(textName);
			if("[]".endsWith(textValue)||textValue==null){
				textValue="";
			}
			if(JsonName.get(textName)!=null){
				content.put(JsonName.get(textName), textValue);
			}
		}
		return content;
	}
	//
	public JSONObject tranJsonNameByConfig(JSONObject json,HashMap jsonName){
		if(json==null){
			return new JSONObject();
		}
		Iterator it=json.keys();
		JSONObject content =  new JSONObject();
		while(it.hasNext()){
			String textName = (String) it.next().toString();
			String textValue = json.getString(textName);
			if("[]".endsWith(textValue)||textValue==null){
				textValue="";
			}
			if(jsonName.get(textName)!=null){
				content.put(jsonName.get(textName), textValue);
			}
		}
		return content;
	}
	
	//查询施工清单接口
	public String qryFinishWorkList(String jsonPara) {
		String result = "";
		JSONObject jsonData = new JSONObject().fromObject(jsonPara);
		EBizToIomWebservice eBizToIomWebservice=new EBizToIomWebservice();
		
		try {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("Data");
			Element queryMethodEl = root.addElement("QueryMethod");
			queryMethodEl.setText(QRY_FINISH_WORK_LIST);
			Element paramsEl=root.addElement("Params");
			paramsEl.addElement("UserName").setText(
					getJsonObejctValue(jsonData, "UserName"));
			paramsEl.addElement("LoginPhoneNo").setText(
					getJsonObejctValue(jsonData, "LoginPhoneNo"));
			paramsEl.addElement("JobId").setText(
					getJsonObejctValue(jsonData, "JobId"));
			paramsEl.addElement("StaffId").setText(
					getJsonObejctValue(jsonData, "StaffId"));
			paramsEl.addElement("BeginTime").setText(
					getJsonObejctValue(jsonData, "BeginTime"));
			paramsEl.addElement("EndTime").setText(
					getJsonObejctValue(jsonData, "EndTime"));
			paramsEl.addElement("PageSize").setText(
					getJsonObejctValue(jsonData, "PageSize"));
			paramsEl.addElement("PageNum").setText(
					getJsonObejctValue(jsonData, "PageNum"));
			String requestXml=doc.asXML();
			System.out.println("移动平台发送施工清单查询的请求报文："+requestXml);
			//发送查询请求
			String[] paraValues=new String[]{QRY_FINISH_WORK_LIST,requestXml};
			String retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceFunc, paraName, paraValues);
			System.out.println(retXml);
			//解析查询报文，把XML转换成JSON
			result=parseQryFinishWorkList(retXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//解析施工清单查询返回的报文
	public String parseQryFinishWorkList(String retXml)throws Exception {
		String result = "";
		JSONObject content=null;
		SAXReader reader = new SAXReader();
		try {
			StringReader sr = new StringReader(retXml);   
			InputSource is = new InputSource(sr);   
			Document doc = reader.read(is);
			Element root=doc.getRootElement();
			Element retEle=root.element("Return");
			String resultEle=retEle.element("Result").getText();
			if("000".equals(resultEle)){//查询成功
				content=new JSONObject();
				Map paramMap = new HashMap();
				paramMap.put("teachName", FINISH_WORK_LIST_PAGE);
				List pageIdList = getJsonCreateDAO().selByName(paramMap);
				if (pageIdList.size() > 0) {
					Map pageMap = (Map) pageIdList.get(0);
					// 查询出解析字段的配置信息
					List<Map> filedList = getServiceDAO().getFiledByFormId(
							pageMap.get("formId").toString());
					JSONObject josn = new JSONObject();
					josn.put("Result", "000");
					String totalNum = retEle.element("TotalNum").getText();
					String totalPage = retEle.element("TotalPage").getText();
					josn.put("TotalNum", totalNum);
					josn.put("TotalPage", totalPage);
					List contentList = retEle.elements("Content");
					JSONArray resultList = new JSONArray();
					for (int k = 0; k < contentList.size(); k++) {
						Element publicEle = ((Element) contentList.get(k))
								.element("Public");
						String recordList = this.setJsonByPageConfig(publicEle,
								filedList);
						resultList.add(recordList);
					}
					josn.put("listDatas", resultList);
					result = josn.toString();
				} else {
					JSONObject josn = new JSONObject();
					josn.put("Result", "001");
					josn.put("ErrorCommens", "未配置工单列表页面！");
					result = josn.toString();
				}
			}else{//查询失败
				content=new JSONObject();
				content.put("isSuccess", "001");
				Element errorDesc=retEle.element("ErrorDesc");
				String errorDescStr= errorDesc.getText()!=null?errorDesc.getText():"";
				content.put("ErrorDesc", errorDescStr);
				result=content.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	// 根据页面配置的字段去解析报文
	public String setJsonByPageConfig(Element ele, List<Map> filedList)
			throws Exception {
		String result = "";
		try {
			Map filedMap = new HashMap();
			JSONArray recordList = new JSONArray();
			for (int i = 0; i < filedList.size(); i++) {
				JSONObject jsonFiled = new JSONObject();
				filedMap = filedList.get(i);
				String filedName = (String) filedMap.get("filedName");
				if (ele.element(filedName) != null) {
					String filedValue = ele.element(filedName).getText();
					String valDictionaries = (String) filedMap
							.get("selectData");
					// 查询配置好的字典映射,数据格式为:
					// 字典编码1,字典值1;字典编码2,字典值2;字典编码3,字典值3
					// ","前的表示键，","后的表示值。";"作为分组依据
					if (null != valDictionaries && !"".equals(valDictionaries)) {
						String[] valueItems = valDictionaries.split(";");
						Map valueMap = new HashMap();
						for (int j = 0; j < valueItems.length; j++) {
							String[] items = valueItems[j].split(",");
							if (items.length == 2
									&& filedValue.equals(items[0])) {
								// valueMap.put(items[0], items[1]);
								filedValue = items[1];
								break;
							}
						}
						/*
						 * if(valueMap.size()>0){
						 * filedValue=(String)valueMap.get(filedValue); }
						 */
					}

					jsonFiled.put("filedLable", filedMap.get("filedLable"));
					jsonFiled.put("filedValue", filedValue);
					jsonFiled.put("filedType", filedMap.get("filedType"));
					jsonFiled.put("isDisplay", filedMap.get("isDisplay"));
					jsonFiled.put("isPassValue", filedMap.get("isPassValue"));
					jsonFiled.put("filedCode", filedName);
					recordList.add(jsonFiled);
				}
			}
			result = recordList.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	public String getJsonObejctValue(JSONObject json, String obejectName) {
		String result = "";
		if (json.getString(obejectName) != null) {
			result = json.getString(obejectName);
		}
		return result;
	}
	private JsonCreateDAO getJsonCreateDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (JsonCreateDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private ServiceDAO getServiceDAO() {
		String daoName = ServiceDAOImpl.class.getName();
		return (ServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WorkInfoHandler workInfoAction=new WorkInfoHandler();
		
		Document nmdk2iomDoc = null;
		File file = new File("E:/项目/杭州文档/测试报文/nmdk2iom.xml");
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(file);
			SAXReader saxReader = new SAXReader();
//			doc = saxReader.read(file); 
			nmdk2iomDoc = saxReader.read(fileInputStream);
			fileInputStream.close();

			String nmdk2iomXML = nmdk2iomDoc.asXML();
			System.out.println(nmdk2iomXML);
			String result=workInfoAction.parseXMLforWorkDetail(nmdk2iomXML);
			System.out.println(result);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}


}
