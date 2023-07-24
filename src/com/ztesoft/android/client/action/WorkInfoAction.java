package com.ztesoft.android.client.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.ztesoft.android.common.AndrBaseAction;
import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.android.util.JsonUtils;
import com.ztesoft.android.util.ZipUtil;

public class WorkInfoAction implements AndrBaseAction{
	private static Logger logger = Logger.getLogger(WorkInfoAction.class);
	//private static String iomServiceUrl="http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort";
	//private static String queryWorkDetail="finishWorkOrderForEBiz";
	private static String queryWorkDetail="queryWorkOrderDetailForEBiz";
	private static String iomServiceFunc="pfServicesForEBiz";
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
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String jsonPara = request.getParameter("params");
		jsonPara = URLDecoder.decode(jsonPara, "UTF-8");
		//System.out.println("�������!!:" + jsonPara);
        
		JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
		String funcName=jsonObject.getString("function");
		String result="";
		if("WorkOrderDetailQuery".equals(funcName)){
			result=queryWorkOrderDetail(jsonPara);
		}
		String newstr = ZipUtil.compress(result);   
		/*System.out.println("��������ʵʱ���²���");   
		System.out.println("ѹ������ַ���="+newstr);   
		System.out.println("ѹ����ĳ�="+newstr.length());  */
		     
		response.setContentType("text/plain;charset=ISO-8859-1");
		response.getWriter().write(newstr);
		return null;
	}
	public String queryWorkOrderDetail(String jsonPara){
		String result="";
		EBizToIomWebservice eBizToIomWebservice=new EBizToIomWebservice();
		try {
			JSONObject jsonObject = new JSONObject().fromObject(jsonPara);
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
				content.put("����������Ϣ", tranJsonName(baseInfo));
				content.put("����Դ��Ϣ", tranJsonName(resinfo));
				content.put("����Դ��Ϣ", tranJsonName(oldresinfo));
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WorkInfoAction workInfoAction=new WorkInfoAction();
		
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
