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
//		"<ErrorDesc>失败信息描述</ErrorDesc>" +
//		"<ContentList>" + 
//		"<Content>" + 
//		"<Public>" + 
//		"<WorkOrderID>123</WorkOrderID>" + 
//		"<OrderCode>定单编码</OrderCode>" + 
//		"<ServiceName>业务名称</ServiceName>" + 
//		"<AccNbr>业务号码</AccNbr>" + 
//		"<CustName>客户名称</CustName>" + 
//		"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
//		"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
//		"<TacheName>环节名称</TacheName>" + 
//		"<TacheCode>环节编码</TacheCode>" + 
//		"<Address>装机地址</Address>" + 
//		"<SlaTime>预约时间</SlaTime>" + 
//		"<WorkOrderType>工单类型</WorkOrderType>" + 
//		"<CreateDate>工单派发时间</CreateDate>" + 
//		"</Public>" + 
//		"</Content>" +
//		"<Content>" + 
//		"<Public>" + 
//		"<WorkOrderID>123</WorkOrderID>" + 
//		"<OrderCode>定单编码</OrderCode>" + 
//		"<ServiceName>业务名称</ServiceName>" + 
//		"<AccNbr>业务号码</AccNbr>" + 
//		"<CustName>客户名称</CustName>" + 
//		"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
//		"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
//		"<TacheName>环节名称</TacheName>" + 
//		"<TacheCode>环节编码</TacheCode>" + 
//		"<Address>装机地址</Address>" + 
//		"<SlaTime>预约时间</SlaTime>" + 
//		"<WorkOrderType>工单类型</WorkOrderType>" + 
//		"<CreateDate>工单派发时间</CreateDate>" + 
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
		"<WorkOrderID>工单ID</WorkOrderID>" + 
		"<Return>" + 
		"<Result>000</Result>" + 
		"<ErrorDesc>失败信息描述</ErrorDesc>" + 
		"</Return>" + 
		"<ReasonList>" + 
		"<Reason>" + 
		"<ErrorCode>异常原因编码</ErrorCode>" + 
		"<ErrorName>异常原因编码</ErrorName>" + 
		"<TargetTache>退单目标环节名称</TargetTache>" + 
		"</Reason>" + 
		"<Reason>" + 
		"<ErrorCode>异常原因编码1</ErrorCode>" + 
		"<ErrorName>异常原因编码1</ErrorName>" + 
		"<TargetTache>退单目标环节名称1</TargetTache>" + 
		"</Reason>" + 
		"</ReasonList>" + 
		"</Data>";
result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
"<Data>" +
"<QueryMethod>queryWorkOrderForEBiz</QueryMethod>" + 
"<Return>" + 
"<Result>000</Result>" + 
"<ErrorDesc>失败信息描述</ErrorDesc>" +
"<ContentList>" + 
"<Content>" + 
"<Public>" + 
"<WorkOrderID>123</WorkOrderID>" + 
"<OrderCode>定单编码</OrderCode>" + 
"<ServiceName>业务名称</ServiceName>" + 
"<AccNbr>业务号码</AccNbr>" + 
"<CustName>客户名称</CustName>" + 
"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
"<TacheName>环节名称</TacheName>" + 
"<TacheCode>环节编码</TacheCode>" + 
"<Address>装机地址</Address>" + 
"<SlaTime>预约时间</SlaTime>" + 
"<WorkOrderType>工单类型</WorkOrderType>" + 
"<CreateDate>工单派发时间</CreateDate>" + 
"</Public>" + 
"</Content>" +
"<Content>" + 
"<Public>" + 
"<WorkOrderID>123</WorkOrderID>" + 
"<OrderCode>定单编码</OrderCode>" + 
"<ServiceName>业务名称</ServiceName>" + 
"<AccNbr>业务号码</AccNbr>" + 
"<CustName>客户名称</CustName>" + 
"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
"<TacheName>环节名称</TacheName>" + 
"<TacheCode>环节编码</TacheCode>" + 
"<Address>装机地址</Address>" + 
"<SlaTime>预约时间</SlaTime>" + 
"<WorkOrderType>工单类型</WorkOrderType>" + 
"<CreateDate>工单派发时间</CreateDate>" + 
"</Public>" + 
"</Content>" +
"</ContentList>" + 
"</Return>" + 
"</Data>";

result="<?xml version=\"1.0\" encoding=\"GBK\"?>" +
"<Data>" +
"<QueryMethod>方法名称</QueryMethod>" + 
"<Return>" + 
"<Result>000</Result>" + 
"<ErrorDesc>失败信息描述</ErrorDesc>" + 
"<Content>" + 
"<Public>" + 
"<WorkOrderID>待办工单ID</WorkOrderID>" + 
"<OrderCode>定单编码</OrderCode>" + 
"<ServiceName>业务名称</ServiceName>" + 
"<AccNbr>业务号码</AccNbr>" + 
"<CustName>客户名称</CustName>" + 
"<CustLinkPerson>客户联系人名称</CustLinkPerson>" + 
"<CustLinkPhone>客户联系人电话</CustLinkPhone>" + 
"<TacheName>环节名称</TacheName>" + 
"<TacheCode>环节编码</TacheCode>" + 
"<Address>装机地址</Address>" + 
"<SlaTime>预约时间</SlaTime>" + 
"<CreateDate>工单派发时间</CreateDate>" + 
"<WorkOrderType>工单类型</WorkOrderType>" + 
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
