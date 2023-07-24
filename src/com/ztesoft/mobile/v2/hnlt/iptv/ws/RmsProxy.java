package com.ztesoft.mobile.v2.hnlt.iptv.ws;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.xml.rpc.ParameterMode;
import java.util.Map;


public class RmsProxy {

//	String endpoint = "http://gkweb1:9020/FLOWBUS_INTERFACE/services/RMSInterface?wsdl"; //正式环境
	String endpoint = "http://192.168.101.11:7022/FLOWBUS_INTERFACE/services/RMSInterface?wsdl";
	//String endpoint = "http://localhost:7001/WebRoot/services/rmsInterface?wsdl";  //测试环境
	/**
	 * 障碍查询
	 * @param map
	 * @return JsonString
	 * @author 李军
	 * 2018-9-23
	 */
	public String queryDiagnoseService(Map<String, Object> map) {
		String result = "{'result':'账号查询接口调用返回结果失败'}";
		try {
			
			Service service = new Service();
			Call call = (Call) service.createCall();
			
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName("queryDiagnose");
			call.addParameter("ukType", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("ukValue", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("flag", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);

			Object[] paramValues = { map.get("ukType"), map.get("ukValue"), map.get("flag") };
			result = (String) call.invoke(paramValues);
			System.out.println("result is " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
		/**
		 * 调用运营平台的童锁接口
		 * @param map
		 * @return JsonString
		 * @author 李军
		 * 2019-8-15
		 */
		public String chdPassService(Map<String, Object> map) {
			String result = "{'result':'童锁接口调用返回结果失败'}";
			try {
				
				Service service = new Service();
				Call call = (Call) service.createCall();
				
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName("ChdPass");
				call.addParameter("logicdevno", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("chd_pass", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("old_chd_pass", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("command", XMLType.XSD_STRING, ParameterMode.IN);
				call.setReturnType(XMLType.XSD_STRING);
				
				Object[] paramValues = { map.get("logicdevno"), map.get("chd_pass"), map.get("old_chd_pass"), map.get("command")};
				result = (String) call.invoke(paramValues);
				System.out.println("result is " + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	
		/**
		 * 修改高清标识
		 * @param map
		 * @return JsonString
		 * @author 李军
		 * 2019-8-22
		 */
		public String modifyHDTypeByAccountService(Map<String, Object> map) {
			String result = "{'result':'修改高清类型服务调用失败'}";
			try {
				
				Service service = new Service();
				Call call = (Call) service.createCall();
				
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName("modifyHDTypeByAccount");
				call.addParameter("loginAccount", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("epggroupnmb", XMLType.XSD_STRING, ParameterMode.IN);
				call.setReturnType(XMLType.XSD_STRING);
				
				Object[] paramValues = { map.get("loginAccount"), map.get("epggroupnmb")};
				result = (String) call.invoke(paramValues);
				System.out.println("调用修改高清类型" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
		
		/**
		 * 复机
		 * @param map
		 * @return JsonString
		 * @author 李军
		 * 2019-8-22resetByAccount
		 */
		public String resetByAccount(Map<String, Object> map) {
			String result = "{'result':'复机服务调用失败'}";
			try {
				
				Service service = new Service();
				Call call = (Call) service.createCall();
				
				call.setTargetEndpointAddress(endpoint);
				call.setOperationName("resetByAccount");
				call.addParameter("loginAccount", XMLType.XSD_STRING, ParameterMode.IN);
				call.addParameter("epggroupnmb", XMLType.XSD_STRING, ParameterMode.IN);
				call.setReturnType(XMLType.XSD_STRING);
				
				Object[] paramValues = { map.get("loginAccount"), map.get("epggroupnmb")};
				result = (String) call.invoke(paramValues);
				System.out.println("调用复机" + result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
		
}
