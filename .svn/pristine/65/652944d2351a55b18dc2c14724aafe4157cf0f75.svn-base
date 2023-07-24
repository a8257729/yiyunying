package com.ztesoft.android.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.ztesoft.eoms.exintf.util.InterfaceHelper;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.helper.TimeUtil;
import com.ztesoft.mobile.common.helper.ValidateHelper;

public class DoServiceDaoImpl extends BaseDAOImpl{
	private static final long serialVersionUID = -4397192926052141162L;
	private Logger logger = Logger.getLogger(DoServiceDaoImpl.class);
	private static final String DEFAULT_ENCODING = null;
	private InterfaceHelper intfHelper;	
 
	private String dateTime = TimeUtil.format(new Date(),"yyyy-MM-dd hh:mm:ss");

	public static String CommonDoService(String wsd_url,String method,String Json){
		System.out.println("wsd_url  "+wsd_url);
		System.out.println("method  "+method);
		String jsonData = "";
		try {
			Service service = new Service(); 
			Call call = (Call) service.createCall();
			//call.setTargetEndpointAddress(new java.net.URL(wsd_url));
			call.setTargetEndpointAddress(new java.net.URL(wsd_url));
			//call.setOperationName(method); 
			call.setOperationName(new QName("http://webservice.sys.resmaster.ztesoft.com/", "processBuiz"));   //axis2需要空间名,axis1不需要空间名

			jsonData = (String)call.invoke(new Object[] {Json}); 
			
			System.out.println(jsonData);
			
		} catch (ServiceException e) {
			jsonData = "returnCode:false";
			e.printStackTrace();
		} catch (MalformedURLException e) {
			jsonData = "returnCode:false";
			e.printStackTrace();
		} catch (RemoteException e) {
			jsonData = "returnCode:false";
			e.printStackTrace();
		}
	
		return jsonData;
	}


	private InterfaceHelper getInterfaceHelper() {
		if (!ValidateHelper.validateNotNull(intfHelper)) {
			intfHelper = new InterfaceHelper().getInstance();
		}
		return intfHelper;
	} 
	public String format(java.util.Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				java.text.DateFormat df = new java.text.SimpleDateFormat(format);
				result = df.format(date);
			}
		}
		catch (Exception e) {
		}
		return result;
	}


	public static void main(String args[]){
		DoServiceDaoImpl dao = new DoServiceDaoImpl();
		//try {
		String returnstr = DoServiceDaoImpl.CommonDoService("http://10.45.28.24:9080/iresource/service/Iresource?wsdl", "processBuiz", "{\"method\":\"deptManageService_getSupportNum\",\"content\":{\"CODE\":\" DT-POS-003777\", \"TYPE\":\" OBD\",\"SESSION_ID \":\"sseeeeee\",\"USER_ID \":\"100001\"}}");
		//String returnstr = DoServiceDaoImpl.CommonDoService("http://localhost:8080/DispatchWebService/services/DispatchWebService?wsdl", "getFaultRequstData", "232");
		System.out.println("returnstr  "+returnstr);
//		} catch (SQLException e) {
//		 TODO Auto-generated catch block
//		e.printStackTrace();
//		}
		
//		EBizToOtherWebservice eBizToIomWebservice=new EBizToOtherWebservice();
//		String url="http://10.45.28.24:9080/iresource/service/Iresource?wsdl";
//		String requestxml = "<?xml version=\"1.0\" encoding=\"GBK\"?><Data><Params><WorkOrderID>2169684</WorkOrderID><SysParam/></Params><QueryMethod>queryWorkOrderDetailForEBiz</QueryMethod></Data>";
//		String[]paraName={"infType","requestxml"};
//		String[]paraValues={"DeptManage_getSupportNum",requestxml};
//		String a;
//		try {
//			a = eBizToIomWebservice.callWebService(url, "http://webservice.sys.resmaster.ztesoft.com/", "processBuiz", paraName, paraValues);
//			System.out.println(a);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//		WSClient wsClient = new WSClient(url);
//		String s = wsClient.invoke("processBuiz", "{\"method\":\"DeptManage_getSupportNum\",content:{\"CODE\":\" DT-POS-003777\", \"TYPE\":\" OBD\",\"SESSION_ID \":\"sseeeeee\"\"USER_ID \":\"100001\"}}");
//		System.out.println("s "+s);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		  JaxWsDynamicClientFactory dynamicClient = JaxWsDynamicClientFactory.newInstance();
//		 
//			Client client = dynamicClient.createClient("http://10.45.28.24:9080/iresource/service/Iresource?wsdl");
//			try {
//				Object[] results = client.invoke("processBuiz", new Object[]{"a"});
//			}catch (Exception e) {
//				// TODO: handle exception
//			}

	}
}
