package com.ztesoft.android.client.action.sqm;

import net.sf.json.JSONObject;
import com.ztesoft.android.service.EBizToIomWebservice;
import org.apache.log4j.Logger;
import com.ztesoft.android.common.ComInfData;

public class FixedNetworkSearchAction {
	
	  private static Logger logger = Logger.getLogger(FixedNetworkSearchAction.class);
	  //private static String iomServiceUrl = "http://10.45.46.9:7001/IOMPROJ/services/PFServicesForEBizPort?wsdl";
	  private static String iomServiceOperName = "pfServicesForEBiz";

	  public String queryPortInfo(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> �����ѯ�˿���Ϣ������" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");
			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><SERVCODE>" + funcName + "</SERVCODE><MDN>" + MDN + "</MDN></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>0</RESULT_CODE>"+
            "<RESULT_DESC>����ʧ��</RESULT_DESC><RESULT_SET><RESULT_SET_TYPE><ACTION_TYPE>IP</ACTION_TYPE>"+
            "<STATE>123.178.240.9</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE><ACTION_TYPE>PORT</ACTION_TYPE>"+
            "<STATE>1748088793</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE><ACTION_TYPE>VLAN</ACTION_TYPE>"+
            "<STATE>eth--3, 0, 8:795.2009--0, 0, 0, 0, 0, 0</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE>"+
            "<ACTION_TYPE>PORTSTATE</ACTION_TYPE><STATE>0</STATE></RESULT_SET_TYPE></RESULT_SET></ROOT>";

            try {
			    System.out.println("�ƶ�ƽ̨���Ͳ�ѯ�˿���Ϣ�������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }

	  public String queryCustomerInfo(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> �����ѯ�˺���Ϣ������" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");
			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><SERVCODE>" + funcName + "</SERVCODE><MDN>" + MDN + "</MDN></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>0</RESULT_CODE>"+
               "<RESULT_DESC>����ʧ��</RESULT_DESC><RESULT_SET><RESULT_SET_TYPE><ACTION_TYPE>IP</ACTION_TYPE>"+
               "<STATE>123.178.240.9</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE><ACTION_TYPE>PORT</ACTION_TYPE>"+
               "<STATE>eth--3,0,8:795.2009--0,0,0,0,0,0</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE><ACTION_TYPE>STATUS</ACTION_TYPE>"+
               "<STATE>0</STATE></RESULT_SET_TYPE><RESULT_SET_TYPE>"+
               "<ACTION_TYPE>SPEED</ACTION_TYPE><STATE>4M@1</STATE></RESULT_SET_TYPE>"+
               "<RESULT_SET_TYPE><ACTION_TYPE>VNETFLAG</ACTION_TYPE><STATE>1</STATE></RESULT_SET_TYPE>"+
               "<RESULT_SET_TYPE><ACTION_TYPE>MAXNUM</ACTION_TYPE><STATE>2</STATE></RESULT_SET_TYPE>"+
               "</RESULT_SET></ROOT>";

	        try {
			    System.out.println("�ƶ�ƽ̨���Ͳ�ѯ�˿���Ϣ�������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }

	  public String modAccPwd(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> �����޸��˺����뷽����" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");
			String custId = "";
			String areaId = jsonObject.getString("areaId");
			String prodId = jsonObject.getString("prodId");
			String saleProdId = jsonObject.getString("saleProdId");
			String newPwd = jsonObject.getString("newPwd");
			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><AREA_CODE>" + areaId + "</AREA_CODE><PRODUCT_ID>" + prodId + "</PRODUCT_ID>" + 
			"<SALE_PROD_ID>" + saleProdId + "</SALE_PROD_ID><CUST_ID>" + custId + "</CUST_ID><MDN>" + MDN + "</MDN>" + 
			"<PWD>" + newPwd + "</PWD><RESULT_CODE/><RESULT_DESC/></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>1</RESULT_CODE>"+
               "<RESULT_DESC>fuck and shit</RESULT_DESC></ROOT>";

	        try {
			    System.out.println("�ƶ�ƽ̨���͸��˺�����������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }

	  public String setAccState(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> ����ͣ����������" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");
			String custId = "";
			String areaId = jsonObject.getString("areaId");
			String prodId = jsonObject.getString("prodId");
			String saleProdId = jsonObject.getString("saleProdId");
			String accState = jsonObject.getString("accState");
			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><AREA_CODE>" + areaId + "</AREA_CODE><PRODUCT_ID>" + prodId + "</PRODUCT_ID>" + 
			"<SALE_PROD_ID>" + saleProdId + "</SALE_PROD_ID><CUST_ID>" + custId + "</CUST_ID><MDN>" + MDN + "</MDN>" + 
			"<STATUS>" + accState + "</STATUS><RESULT_CODE/><RESULT_DESC/></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>0</RESULT_CODE>"+
               "<RESULT_DESC>double fuck </RESULT_DESC></ROOT>";

	        try {
			    System.out.println("�ƶ�ƽ̨����ͣ�����������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }

	  public String getAccOnLineLog(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> �����ѯ�����û�������" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");

			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><SERVCODE>" + funcName + "</SERVCODE><MDN>" + MDN + "</MDN></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>0</RESULT_CODE>"+
               "<RESULT_DESC>double fuck </RESULT_DESC>" + 
               "<CONTENT><USERIP>127.0.0.1</USERIP><ONLINETIME>12312314</ONLINETIME><LOGINTIME>2012-3-5 12:00:00</LOGINTIME>" + 
               "<INFLOWBYTE>34234</INFLOWBYTE><OUTFLOWBYTE>11</OUTFLOWBYTE><INFLOWPAKGE>33</INFLOWPAKGE><OUTFLOWPAKGE>777</OUTFLOWPAKGE></CONTENT>" +
               "<CONTENT><USERIP>127.0.0.2</USERIP><ONLINETIME>12312314</ONLINETIME><LOGINTIME>2012-3-6 12:00:00</LOGINTIME>" + 
               "<INFLOWBYTE>567567</INFLOWBYTE><OUTFLOWBYTE>2222</OUTFLOWBYTE><INFLOWPAKGE>555</INFLOWPAKGE><OUTFLOWPAKGE>7867</OUTFLOWPAKGE></CONTENT>" +
               "<CONTENT><USERIP>127.0.0.3</USERIP><ONLINETIME>12312314</ONLINETIME><LOGINTIME>2012-3-7 12:00:00</LOGINTIME>" + 
               "<INFLOWBYTE>567567</INFLOWBYTE><OUTFLOWBYTE>2222</OUTFLOWBYTE><INFLOWPAKGE>555</INFLOWPAKGE><OUTFLOWPAKGE>7867</OUTFLOWPAKGE></CONTENT>" +
               "<CONTENT><USERIP>127.0.0.4</USERIP><ONLINETIME>12312314</ONLINETIME><LOGINTIME>2012-3-8 12:00:00</LOGINTIME>" + 
               "<INFLOWBYTE>567567</INFLOWBYTE><OUTFLOWBYTE>2222</OUTFLOWBYTE><INFLOWPAKGE>555</INFLOWPAKGE><OUTFLOWPAKGE>7867</OUTFLOWPAKGE></CONTENT>" +
               "</ROOT>";

	        try {
			    System.out.println("�ƶ�ƽ̨���Ͳ�ѯ�����û��������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }

	  public String delOnlineInfo(JSONObject jsonObject) {
		    System.out.println(">>>>>>>>>>>>>>>>>>>>>>> �����޳������û�������" );
		    EBizToIomWebservice eBizToIomWebservice = new EBizToIomWebservice();
		    String retXml="";
		    String inXml = "";
			String funcName = jsonObject.getString("function");
			String MDN = jsonObject.getString("MDN");

			inXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Root><SERVCODE>" + funcName + "</SERVCODE><SEQ_AD></SEQ_AD>" + 
			"<ACC_NBR>" + MDN + "</ACC_NBR><NODE_TYPE></NODE_TYPE><RESULT_CODE/><RESULT_DESC/></Root>";

			retXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><RESULT_CODE>0</RESULT_CODE>"+
               "<RESULT_DESC>fuck and shit</RESULT_DESC></ROOT>";

	        try {
			    System.out.println("�ƶ�ƽ̨�����޳������û��������ģ�" + inXml);
			    String[] paraName = { "a", "b" };
			    String[] paraValues = { inXml, funcName };
				//retXml = eBizToIomWebservice.callWebService(ComInfData.iomServiceUrl, null, iomServiceOperName, paraName, paraValues);
			    System.out.println("��ͨ���صı��ģ�" + retXml);
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    return retXml; 
	  }
}
