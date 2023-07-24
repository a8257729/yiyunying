package com.ztesoft.android.service;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;


public class EBizToOtherWebservice {
	public String callWebService(String url, String operationNameSpqce,
			String operationName, String[] paraName, String[] paraValues)throws Exception{
		Service service = new Service();
		String result="";
		try {
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperation(operationName);
			if (!"".equals(operationNameSpqce) && operationNameSpqce != null) {
				call.setOperationName(new javax.xml.namespace.QName(operationNameSpqce,operationName));
			} else {
				call.setOperationName(operationName);
			}
			for(int i=0;i<paraName.length;i++){
				call.addParameter(paraName[i], XMLType.XSD_STRING,ParameterMode.IN);
			}
			call.setReturnType(XMLType.XSD_STRING);
			result = (String) call.invoke(paraValues);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			throw e;
		}

//		result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
//		"<Data>" +
//		"<QueryMethod>queryWorkOrderForEBiz</QueryMethod>" + 
//		"<Return>" + 
//		"<Result>000</Result>" + 
//		"<ErrorDesc>ʧ����Ϣ����</ErrorDesc>" +
//		"<ContentList>" + 
//		"<Content>" + 
//		"<Public>" + 
//		"<WorkOrderID>123</WorkOrderID>" + 
//		"<OrderCode>��������</OrderCode>" + 
//		"<ServiceName>ҵ������</ServiceName>" + 
//		"<AccNbr>ҵ�����</AccNbr>" + 
//		"<CustName>�ͻ�����</CustName>" + 
//		"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
//		"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
//		"<TacheName>��������</TacheName>" + 
//		"<TacheCode>���ڱ���</TacheCode>" + 
//		"<Address>װ����ַ</Address>" + 
//		"<SlaTime>ԤԼʱ��</SlaTime>" + 
//		"<WorkOrderType>��������</WorkOrderType>" + 
//		"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
//		"</Public>" + 
//		"</Content>" +
//		"<Content>" + 
//		"<Public>" + 
//		"<WorkOrderID>123</WorkOrderID>" + 
//		"<OrderCode>��������</OrderCode>" + 
//		"<ServiceName>ҵ������</ServiceName>" + 
//		"<AccNbr>ҵ�����</AccNbr>" + 
//		"<CustName>�ͻ�����</CustName>" + 
//		"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
//		"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
//		"<TacheName>��������</TacheName>" + 
//		"<TacheCode>���ڱ���</TacheCode>" + 
//		"<Address>װ����ַ</Address>" + 
//		"<SlaTime>ԤԼʱ��</SlaTime>" + 
//		"<WorkOrderType>��������</WorkOrderType>" + 
//		"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
//		"</Public>" + 
//		"</Content>" +
//		"</ContentList>" + 
//		"</Return>" + 
//		"</Data>";
		return result;

	}
	public static void main(String[] args) {
		EBizToOtherWebservice eBizToIomWebservice=new EBizToOtherWebservice();
		String url="http://ztesoftgz.vicp.net:18049/IOMPROJ/services/PFServicesForEBizPort?wsdl";
		//String requestxml = "<?xml version=\"1.0\" encoding=\"GBK\"?><Data><Params><WorkOrderID>2169684</WorkOrderID><SysParam/></Params><QueryMethod>queryWorkOrderDetailForEBiz</QueryMethod></Data>";
		String requestxml="<?xml version=\"1.0\" encoding=\"GBK\"?><Data><Params><JobId>4213</JobId><PageNum>1</PageNum><PageSize>6</PageSize><ProductNbr/><SysParam/><UseName>zhangkai</UseName></Params><QueryMethod>queryWorkOrderForEBiz</QueryMethod></Data>";

		String[]paraName={"infType","requestxml"};
		String[]paraValues={"queryWorkOrderForEBiz",requestxml};
		String a;
		try {
			a = eBizToIomWebservice.callWebService(url, null, "pfServicesForEBiz", paraName, paraValues);
			System.out.println(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		EBizToIomWebservice eBizToIomWebservice=new EBizToIomWebservice();
//		//String url="http://10.45.47.154:18049/IOMPROJ/services/PFServicesForEBizPort?wsdl";
//		String url="http://10.45.46.109:8080/DispatchWebService/services/DispatchWebService?wsdl";
//		String[]paraName={"infType","requestxml"};
//		String[]paraValues={"getFaultRequstData",""};
//		String a;
//		try {
//			a = eBizToIomWebservice.callWebService(url, "http://eoms.ztesoft.com", "getFaultRequstData", paraName, paraValues);
//			System.out.println(a);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
/*

result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
		"<Data>" +
		"<WorkOrderID>����ID</WorkOrderID>" + 
		"<Return>" + 
		"<Result>000</Result>" + 
		"<ErrorDesc>ʧ����Ϣ����</ErrorDesc>" + 
		"</Return>" + 
		"<ReasonList>" + 
		"<Reason>" + 
		"<ErrorCode>�쳣ԭ�����</ErrorCode>" + 
		"<ErrorName>�쳣ԭ�����</ErrorName>" + 
		"<TargetTache>�˵�Ŀ�껷������</TargetTache>" + 
		"</Reason>" + 
		"<Reason>" + 
		"<ErrorCode>�쳣ԭ�����1</ErrorCode>" + 
		"<ErrorName>�쳣ԭ�����1</ErrorName>" + 
		"<TargetTache>�˵�Ŀ�껷������1</TargetTache>" + 
		"</Reason>" + 
		"</ReasonList>" + 
		"</Data>";
result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
"<Data>" +
"<QueryMethod>queryWorkOrderForEBiz</QueryMethod>" + 
"<Return>" + 
"<Result>000</Result>" + 
"<ErrorDesc>ʧ����Ϣ����</ErrorDesc>" +
"<ContentList>" + 
"<Content>" + 
"<Public>" + 
"<WorkOrderID>123</WorkOrderID>" + 
"<OrderCode>��������</OrderCode>" + 
"<ServiceName>ҵ������</ServiceName>" + 
"<AccNbr>ҵ�����</AccNbr>" + 
"<CustName>�ͻ�����</CustName>" + 
"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
"<TacheName>��������</TacheName>" + 
"<TacheCode>���ڱ���</TacheCode>" + 
"<Address>װ����ַ</Address>" + 
"<SlaTime>ԤԼʱ��</SlaTime>" + 
"<WorkOrderType>��������</WorkOrderType>" + 
"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
"</Public>" + 
"</Content>" +
"<Content>" + 
"<Public>" + 
"<WorkOrderID>123</WorkOrderID>" + 
"<OrderCode>��������</OrderCode>" + 
"<ServiceName>ҵ������</ServiceName>" + 
"<AccNbr>ҵ�����</AccNbr>" + 
"<CustName>�ͻ�����</CustName>" + 
"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
"<TacheName>��������</TacheName>" + 
"<TacheCode>���ڱ���</TacheCode>" + 
"<Address>װ����ַ</Address>" + 
"<SlaTime>ԤԼʱ��</SlaTime>" + 
"<WorkOrderType>��������</WorkOrderType>" + 
"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
"</Public>" + 
"</Content>" +
"</ContentList>" + 
"</Return>" + 
"</Data>";

result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
"<Data>" +
"<QueryMethod>��������</QueryMethod>" + 
"<Return>" + 
"<Result>000</Result>" + 
"<ErrorDesc>ʧ����Ϣ����</ErrorDesc>" + 
"<Content>" + 
"<Public>" + 
"<WorkOrderID>���칤��ID</WorkOrderID>" + 
"<OrderCode>��������</OrderCode>" + 
"<ServiceName>ҵ������</ServiceName>" + 
"<AccNbr>ҵ�����</AccNbr>" + 
"<CustName>�ͻ�����</CustName>" + 
"<CustLinkPerson>�ͻ���ϵ������</CustLinkPerson>" + 
"<CustLinkPhone>�ͻ���ϵ�˵绰</CustLinkPhone>" + 
"<TacheName>��������</TacheName>" + 
"<TacheCode>���ڱ���</TacheCode>" + 
"<Address>װ����ַ</Address>" + 
"<SlaTime>ԤԼʱ��</SlaTime>" + 
"<CreateDate>�����ɷ�ʱ��</CreateDate>" + 
"<WorkOrderType>��������</WorkOrderType>" + 
"</Public>" + 
"<Resinfo>" + 
"<Mdfv>1</Mdfv>" + 
"</Resinfo>" + 
"<Oldresinfo>" + 
"<Mdfv>2</Mdfv>" + 
"</Oldresinfo>" + 
"</Content>" + 
"</Return>" + 
"</Data>";

*/
