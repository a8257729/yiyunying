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
		JsonName.put("WorkOrderID","�������");
		JsonName.put("OrderCode","��������");
		JsonName.put("ServiceName","ҵ������");
		JsonName.put("AccNbr","ҵ�����");
		JsonName.put("CustName","�ͻ�����");
		JsonName.put("CustLinkPerson","�ͻ���ϵ������");
		JsonName.put("CustLinkPhone","�ͻ���ϵ�˵绰");
		JsonName.put("TacheName","��������");
		//JsonName.put("TacheCode","���ڱ���");
		JsonName.put("Address","װ����ַ");
		JsonName.put("SlaTime","ԤԼʱ��");
		JsonName.put("CreateDate","�����ɷ�ʱ��");
		JsonName.put("WorkOrderType","��������");
		JsonName.put("Mdfv","ֱ��");
		JsonName.put("Mdfh","����");
		JsonName.put("CabinetName","����������");
		JsonName.put("Cabinet","������");
		JsonName.put("CabinetIn","���������򣨽��������ɶˣ�");
		JsonName.put("CabinetOut","��������򣨽��������߶ˣ�");
		JsonName.put("PCableP1","�����߶�");
		JsonName.put("Pcable1","���º�");
		JsonName.put("Scable1","�����߶�");
		JsonName.put("ScableP1","���º�");
		JsonName.put("Panel1","���ߺк�");
		JsonName.put("PanelP1","���ߺ���");
		JsonName.put("AccessType","���뷽ʽ���磺����Ľ��뷽ʽ����ͳ(ADSL)");
		JsonName.put("AdslEquipType","ADSL�豸");
		JsonName.put("ScaleDataOut","ADSL�˿�");
		JsonName.put("AreaSwitchName","LANС�����������");
		JsonName.put("SwitchEquip","LAN¥��������");
		JsonName.put("SwitchPort","LAN¥���������˿�");
		JsonName.put("OBD_NAME","�ֹ�������");
		JsonName.put("LOID","LOID");
		JsonName.put("ONU_No","ONU����");
		JsonName.put("ONU_NAME","ONU����");
		JsonName.put("ONU_IP","ONU��IP��ַ");
		JsonName.put("ONU_PORT_NO","ONU����ҵ��˿�");
		JsonName.put("VLAN_ID","����VLAN");
		JsonName.put("EID","����");
		JsonName.put("OLT_IP","OLT IP��ַ");
		JsonName.put("SVLAN","SVLAN");
		JsonName.put("POS_PORT","POS�˿�");
		JsonName.put("POS_ADDR","�ֹ�����ַ");
		JsonName.put("PON_PORT","PON����λ�˿�");
		JsonName.put("queryWorkOrderDetailForEBiz","SEARCH_WORK_DETAIL");

	}
	

	protected void processHandle(Map paramMap) throws Exception {
		String jsonPara = MapUtils.getString(paramMap, "params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		//System.out.println("�������!!:" + jsonPara);
        
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
		/*System.out.println("��������ʵʱ���²���");   
		System.out.println("ѹ������ַ���="+newstr);   
		System.out.println("ѹ����ĳ�="+newstr.length());  */
		     
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
			//������ѯ����
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("Data");
			Element queryMethodEl = root.addElement("QueryMethod");
			queryMethodEl.setText(queryWorkDetail);
			Element paramsEl=root.addElement("Params");
			Element workOrderEl = paramsEl.addElement("WorkOrderID");
			workOrderEl.setText(workOrderId);
			String requestXml=doc.asXML();
			System.out.println("�ƶ�ƽ̨���͹��������ѯ�������ģ�"+requestXml);
			
			paramMap.put("requestXml", requestXml);
			this.beforeCallWS(paramMap);		//���ýӿ�֮��
			
			//���Ͳ�ѯ����
			String[] paraValues=new String[]{queryWorkDetail,requestXml};
			String retXml=eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceFunc, paraName, paraValues);
			//������ѯ���ģ���XMLת����JSON
			System.out.println(retXml);
			result=parseXMLforWorkDetail(retXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		paramMap.put("responseXml", result);
		this.afterCallWS(paramMap);		//���ýӿ�
		
		System.out.println(result);
		return result;
	}
	//������ѯ���ģ���XMLת����JSON
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
				
				
				//����������Ϣ
				JSONObject baseInfo = content.optJSONObject("Public")!=null?content.optJSONObject("Public"):new JSONObject();
				
				JSONObject resource=content.optJSONObject("Resource");
				//����Դ��Ϣ
				JSONObject resinfo = resource.optJSONObject("Resinfo")!=null?resource.optJSONObject("Resinfo"):new JSONObject();
				//����Դ��Ϣ
				JSONObject oldresinfo=resource.optJSONObject("Oldresinfo")!=null?resource.optJSONObject("Oldresinfo"):new JSONObject();
				content=new JSONObject();
				//�ѽڵ�������ӳ�䣬���������
				content.put("isSuccess", "0");
				String paramValue = UosConfigDAO.getInstance().getValue("WORK_DETAIL_CONFIG");
				if(paramValue!=null&&!"".equals(paramValue)){//ʹ�ÿ�������ֶ�
					try{
						Map paramMap = new HashMap();
						paramMap.put("teachName", paramValue);
						List pageIdList = getJsonCreateDAO().selByName(paramMap);
						if (pageIdList==null||pageIdList.size()<1) {
							throw new Exception();
						} else {
							Map pageMap = (Map) pageIdList.get(0);
							// ��ѯ�������ֶε�������Ϣ
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
							content.put("����������Ϣ", tranJsonNameByConfig(baseInfo,jsonName));
							content.put("����Դ��Ϣ", tranJsonNameByConfig(resinfo,jsonName));
							content.put("����Դ��Ϣ", tranJsonNameByConfig(oldresinfo,jsonName));
						}
					}catch(Exception e){
						content=new JSONObject();
						content.put("isSuccess", "1");
						Element errorDesc=retEle.element("ErrorDesc");
						String errorDescStr= errorDesc.getText()!=null?errorDesc.getText():"";
						content.put("ErrorDesc", "δ���ù�������ҳ�棡");
						content.put("funcName", JsonName.get("queryWorkOrderDetailForEBiz"));
						result=content.toString();
					}
					
				}else{//�����ÿ�������ֶ�
					content.put("����������Ϣ", tranJsonName(baseInfo));
					content.put("����Դ��Ϣ", tranJsonName(resinfo));
					content.put("����Դ��Ϣ", tranJsonName(oldresinfo));
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
			/*result="{\"����Դ��Ϣ\":{\"����\":\"456\",\"ֱ��\":\"123\"},\"����Դ��Ϣ\":{\"����\":\"456\",\"ֱ��\":\"123\"},\"����������Ϣ\":{\"�ͻ���ϵ������\":\"sdf\",\"��������\":\"wer\",\"�ͻ���ϵ�˵绰\":\"1245423543\",\"ԤԼʱ��\":\"etetet\",\"�����ɷ�ʱ��\":\"1343\",\"���ڱ���\":\"43243\",\"ҵ�����\":\"134543\",\"��������\":\"576575\",\"ҵ������\":\"qwwqww\",\"���칤��ID\":\"123\"," +
					"\"��������\":\"a\",\"�ͻ�����\":\"vscdv\",\"װ����ַ\":\"xcvcxvcx\"}}";*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	//�ѽڵ�������ӳ�䣬���������
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
	
	//��ѯʩ���嵥�ӿ�
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
			System.out.println("�ƶ�ƽ̨����ʩ���嵥��ѯ�������ģ�"+requestXml);
			//���Ͳ�ѯ����
			String[] paraValues=new String[]{QRY_FINISH_WORK_LIST,requestXml};
			String retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceFunc, paraName, paraValues);
			System.out.println(retXml);
			//������ѯ���ģ���XMLת����JSON
			result=parseQryFinishWorkList(retXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	//����ʩ���嵥��ѯ���صı���
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
			if("000".equals(resultEle)){//��ѯ�ɹ�
				content=new JSONObject();
				Map paramMap = new HashMap();
				paramMap.put("teachName", FINISH_WORK_LIST_PAGE);
				List pageIdList = getJsonCreateDAO().selByName(paramMap);
				if (pageIdList.size() > 0) {
					Map pageMap = (Map) pageIdList.get(0);
					// ��ѯ�������ֶε�������Ϣ
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
					josn.put("ErrorCommens", "δ���ù����б�ҳ�棡");
					result = josn.toString();
				}
			}else{//��ѯʧ��
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
	// ����ҳ�����õ��ֶ�ȥ��������
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
					// ��ѯ���úõ��ֵ�ӳ��,���ݸ�ʽΪ:
					// �ֵ����1,�ֵ�ֵ1;�ֵ����2,�ֵ�ֵ2;�ֵ����3,�ֵ�ֵ3
					// ","ǰ�ı�ʾ����","��ı�ʾֵ��";"��Ϊ��������
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
		File file = new File("E:/��Ŀ/�����ĵ�/���Ա���/nmdk2iom.xml");
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
