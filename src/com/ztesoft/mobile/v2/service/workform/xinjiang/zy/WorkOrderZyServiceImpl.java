package com.ztesoft.mobile.v2.service.workform.xinjiang.zy;



import com.ztesoft.android.common.ComInfData;
import com.ztesoft.android.service.EBizToIomWebservice;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.exception.XMLDocException;
import com.ztesoft.mobile.common.xwork.execution.Dom4jUtils;
import com.ztesoft.mobile.v2.core.BaseService;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAO;
import com.ztesoft.mobile.v2.dao.app.MobileDataInstallDAOImpl;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAO;
import com.ztesoft.mobile.v2.dao.workform.hunan.HuNanDAOImpl;
import com.ztesoft.mobile.v2.service.workform.XmlUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.bouncycastle.util.Strings;
import org.dom4j.Document;
import org.dom4j.Node;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service("workOrderXjZyService")
public class WorkOrderZyServiceImpl extends BaseService implements
		WorkOrderZyService {

	private MobileDataInstallDAO mbDAO = new MobileDataInstallDAOImpl();
	public static final String WS_ZY_NAMESPACE = "";
	public static final String WS_ZY_METHOD_OPERATION_NAME = "pfServicesForEBiz";
	private static final String[] PARAM_ZY_NAME = { "infType", "requestxml" };
	/** *  �ʺŲ�ѯ */
	private static final String XJ_ZY_RESOURCES_BROADBAND_QUERY = "queryAccountState";
	/**
	 * �����λ
	 */
	private static final String XJ_ZY_RESER_BROADBAND_QUERY = "resetAccount";
	private static final boolean debug = false;
	
	private static final Logger logger = Logger
			.getLogger(WorkOrderZyServiceImpl.class);
	
	// ���ýӿڷ���
	private static final EBizToIomWebservice service = new EBizToIomWebservice();
	
	private String callWsService(String method, String reqXml) throws Exception {

		return service.callWebService(ComInfData.iomServiceUrl, WS_ZY_NAMESPACE,
	WS_ZY_METHOD_OPERATION_NAME, PARAM_ZY_NAME, new String[] { method,
			reqXml });
}

	private String callWsServiceToIom(String method, String reqXml) throws Exception {

		return service.callWebService("IOM_SERVICE_URL_18", WS_ZY_NAMESPACE,
				WS_ZY_METHOD_OPERATION_NAME, PARAM_ZY_NAME, new String[] { method,
						reqXml });
	}
	
	
	/**
	 * ��ȡ����Դ
	 */
	private HuNanDAO getWorkOrderDAO() {
		String daoName = HuNanDAOImpl.class.getName();
		return (HuNanDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	/***
	 * ��ԴԤ��--��ѯ
	 * */
	public Result queryResourcesList(JSONObject json) throws Throwable {
		System.out.println("json"+json);
		Result result = new Result();
		try{
			String search_address = json.getString("search_address");
			String pageIndex = json.getString("pageIndex");
			String pageSize = json.getString("pageSize");
			String staffId = json.getString("staffId");
			Map resultMap = getWorkOrderDAO().queryResourcesList(staffId, search_address,Integer.parseInt(pageIndex),Integer.parseInt(pageSize));
		    result.setResultData(resultMap);
		    result.setResultCode(1);
		    return result;
		}catch(Exception e){
			e.printStackTrace();
		    result.setResultCode(-1);
		    return result;
		}
	    
	}



	public Result queryDeviceManagementInitParams(JSONObject json) {
        System.out.println("json"+json);

		String staffId = json.getString("staffId");
		
		List staffGirdList =null;
		List deviceTypeList =null;
		try {
			staffGirdList = getWorkOrderDAO().queryStaffGirdList(staffId);
			deviceTypeList = getWorkOrderDAO().queryDeviceTypeList(staffId);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Map<Object, Object> result_data_node = new HashMap<Object,Object>();
	    result_data_node.put("STAFF_GIRD_LIST", staffGirdList);
	    result_data_node.put("DEVICE_TYPE_LIST", deviceTypeList);
	    
	    Result result = new Result();
	    result.setResultData(result_data_node);
	    result.setResultCode(1000);
	    
		return result;
	}
	
	public Result queryDeviceListByPage(String org_id,String res_type_id,String eqp_name,Integer pageSize, Integer pageIndex){
		 Map<Object, Object> result_data_node = new HashMap<Object,Object>();
		try {
			result_data_node = getWorkOrderDAO().queryDeviceListByPage(org_id, res_type_id,eqp_name, pageSize, pageIndex);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Result result = new Result();
	    result.setResultData(result_data_node);
	    result.setResultCode(1000);
	    
		return result;
		
	}
	
	/** �豸���� -- �豸�˿��б��ѯ   */
	public Result queryDevicePortListByPage(String eqp_id,String port_name,Integer pageSize, Integer pageIndex){
		 Map<Object, Object> result_data_node = new HashMap<Object,Object>();
		try {
			result_data_node = getWorkOrderDAO().queryDevicePortListByPage(eqp_id, port_name, pageSize, pageIndex);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Result result = new Result();
	    result.setResultData(result_data_node);
	    result.setResultCode(1000);
	    
		return result;
		
	}
	/**
	 * �豸���� -- �豸�˿�״̬���ò˵���ѯ
	 * @throws DataAccessException 
	 * 
	 * */
	public Result queryDeviceOperationList(JSONObject json) throws DataAccessException {
		Result result = new Result();
		try{
			String state_id = json.optString("state_id");
	    	List list = getWorkOrderDAO().queryDeviceOperationList(state_id);
	    	
	    	Map<Object, Object> result_data_node = new HashMap<Object,Object>();
	    	result_data_node.put("list_data", list);
	    	
		    result.setResultData(result_data_node);
		    result.setResultCode(1);
		    return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}
	}
	/**
	 * �豸���� -- �豸�˿�״̬���ò���
	 * 
	 * */
	public Result executeDeviceOperation(JSONObject json) {
		Result result = new Result();
		try{
			String staff_id = json.optString("staff_id");
			String port_id = json.optString("port_id");
			String oper_id = json.optString("oper_id");
			
			Map<Object, Object> result_data_node = getWorkOrderDAO().executeDeviceOperation(staff_id,port_id,oper_id);
			
//			Map<Object, Object> result_data_node = new HashMap<Object,Object>();
//			result_data_node.put("list_data", list);
			
			result.setResultData(result_data_node);
			result.setResultCode(1);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}
	}

	public Result queryTeleRollInListByPage(String eqp_id, String tele_nbr,
			Integer pageSize, Integer pageIndex) {
		Map<Object, Object> result_data_node = new HashMap<Object,Object>();
		try {
			result_data_node = getWorkOrderDAO().queryTeleRollInListByPage(eqp_id, tele_nbr, pageSize, pageIndex);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Result result = new Result();
	    result.setResultData(result_data_node);
	    result.setResultCode(1000);
	    
		return result;
	}
	
	public Result executeDeviceRollInOperation(JSONObject json) {
		Result result = new Result();
		try{
			String staff_id = json.optString("staff_id");
			String port_id = json.optString("port_id");
			String disseq = json.optString("disseq");
			
			Map<Object, Object> result_data_node = getWorkOrderDAO().executeDeviceRollInOperation(staff_id,port_id,disseq);
			

			result.setResultData(result_data_node);
			result.setResultCode(1);
			return result;
		}catch(Exception e){
			e.printStackTrace();
			result.setResultCode(-1);
			return result;
		}
	}
	
	
	public Result queryResourcesBroadbandOperation(JSONObject json) {
			String account = json.getString("account");
			if (logger.isDebugEnabled()) {
				logger.debug("�˺�ID: " + account);
			}

			Result result = null;
			try {
				String reqXml = getResourcesBroadbandBzRequest(account);
				String respXml="";
				if (debug){
					  respXml = XmlUtil.getRemotelXmlData(XJ_ZY_RESOURCES_BROADBAND_QUERY);
				} else {
					  respXml = callWsServiceToIom(
						XJ_ZY_RESOURCES_BROADBAND_QUERY, reqXml);
			    }
				System.out.println("���ͱ���:"+reqXml.toString());
				System.out.println("���ر���: + " + respXml);
				Map resultMap = getWorkOrderDAO().queryMaxUnBildTime(account);
//
				result = parseResourcesBroadbandZyResponse(respXml);
				if(resultMap!=null && !resultMap.isEmpty())
				{

					result.getResultData().put("unbind_time",resultMap.get("unbind_time"));
				}

			} catch (Exception e) {
				if (logger.isDebugEnabled()) {
					logger.debug(e.getMessage());
				}
				//
				result = Result.buildInvokeInterError();
			}

			return result;
		}

	public Result queryChangeMachine(JSONObject json) {
		logger.info("���÷�����queryBroadband");
		String account = (String) json.get("account");
		System.out.println(account);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://192.168.101.15:5090/bdes-oi/thirdPartyAction/userBindNumberInfo.action?dNumber="+account;
		Map param = new HashMap();
		Result result = null;
		Map resMap = restTemplate.postForObject(url, param, Map.class);
		List<Map<String,Object>> list = (List)resMap.get("data");
		if(list!=null){
			if(list.size()>0){
				Map<String, Object> map = list.get(0);
				Set<String> keySet = map.keySet();
				Iterator<String> it = keySet.iterator();
				while(it.hasNext()) {
					Map<Object,Object> resultData = new HashMap();
					resultData.put("customer_name",(String) map.get("customer_name"));
					resultData.put("address",(String) map.get("address"));
					logger.info("���÷�����queryBroadband ���ؽ��:"+resultData+",����:"+account);
					System.out.println(resultData);
					result=Result.buildSuccess(resultData);
					return result;
				}
			}
		}
		logger.info("���÷�����queryBroadband ���ؽ��:"+null+",����:"+account);
		return null;
	}

//	public Result queryChangeMachine(JSONObject json) {
//		String account = json.getString("account");
//		if (logger.isDebugEnabled()) {
//			logger.debug("ҵ�����:" + account);
//		}
//		String urlStr ="http://192.168.101.15:5090/bdes-oi/thirdPartyAction/userBindNumberInfo.action?dNumber="+account;
//		String postStr="";
//		OutputStreamWriter out = null;
//		BufferedReader in = null;
//		String result = "";
//		try
//		{
////			result = HttpUtil.post(urlStr+account,"");
////			result = HttpUtil.post(urlStr+account,"");
////			System.out.println(result);
//			URL realUrl = new URL(urlStr);
//			URLConnection conn = realUrl.openConnection();
//			conn.setRequestProperty("accept", "*/*");
//			conn.setRequestProperty("connection", "Keep-Alive");
//			conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//			conn.setDoOutput(true);
//			conn.setRequestProperty("Content-Length","0");
//			conn.setDoInput(true);
//			logger.debug("ҵ�����ѭ��1:" + account);
//			out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
//			logger.debug("ҵ�����ѭ��1:" + account);
//			out.write(postStr);
//			out.flush();
//			in = new BufferedReader(new InputStreamReader(
//					conn.getInputStream(), "utf-8"));
//			String line;
//			logger.debug("ҵ�����ѭ��1:" + account);
//			while ((line = in.readLine()) != null) {
//				result = result + line;
//			}
//			logger.debug("ҵ�����ѭ��2:" + account);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (out != null) {
//					out.close();
//				}
//				if (in != null) {
//					in.close();
//				}
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
//		}
//		Result result1 = new Result();
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		System.out.println(jsonObject);
//		if (jsonObject.get("data").equals(null)){
//			result1 = Result.buildWSXmlParsedError();
//		}else {
//			JSONArray ja = jsonObject.getJSONArray("data");
//			JSONObject object = new JSONObject();
//			for (int i = 0; i < ja.size(); i++) {
//				object = ja.getJSONObject(i);
//				System.out.println(object.toString());
//			}
//			Map<Object, Object> result_data_node = new HashMap<Object,Object>();
//			result_data_node.put("customer_name", object);
//			result1.setResultData(result_data_node);
//			result1.setResultCode(1000);
//			result1 = Result.buildSuccess(result_data_node);
//		}
//		return result1;
//	}


	public Map<String,String> querySelObd(String account) {
		String result = "";
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			 map = mbDAO.querySelOrgByAccount(account);
//			String json = JSON.toJSONString(map);
//			JSONObject aliBodyObj = JSONObject.fromObject(map);
//			JSONObject object = JSONObject.fromObject(map);

		}catch (Exception e)
		{
			logger.info("queryDataInstallByOrg :"+e.getMessage());
		}
		return map;
	}


	public Result queryNew(JSONObject json) {
		return null;
	}

	/**
	 *
	 *
	 * @param
	 * @return
	 */
	private String getResourcesBroadbandBzRequest(String account) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	
		sb.append("<Data>").append("<QueryMethod>").append(
				XJ_ZY_RESOURCES_BROADBAND_QUERY).append(
				"</QueryMethod>")
				.append("<Params>").append("<Account>"+account+"</Account>")
				.append("</Params>").append("</Data>")
				.append("");
	
		if (logger.isDebugEnabled()) {
			logger.debug("����ʺŲ�ѯ������: " + sb.toString());
		}
	
		return sb.toString();
	}
	
	
	//��Ӧ����
	private Result parseResourcesBroadbandZyResponse(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("����ʺŲ�ѯ��Ӧ����: " + respXml);
		}
		
		Result result = null;
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");
 
			String rootPath = "/Data/";
			 
			Node nn1=doc.selectSingleNode(rootPath+ "State");
			Node nn2=doc.selectSingleNode(rootPath+ "IsOnline");
			Node nn3=doc.selectSingleNode(rootPath+ "Rate");
			Node nn4=doc.selectSingleNode(rootPath+ "IsBand");
			Node nn5=doc.selectSingleNode(rootPath+ "LastFail");
			Node nn6=doc.selectSingleNode(rootPath+ "NasIp");
			Node nn7=doc.selectSingleNode(rootPath+ "NasPortId");
			
//			//int size = rtEleList.size();
			Map<Object, Object> resultData = new HashMap<Object, Object>();
			resultData.put("State", nn1!=null?nn1.getText():"");
			resultData.put("IsOnline",nn2!=null?nn2.getText():"");
			resultData.put("Rate", nn3!=null?nn3.getText():"");
			resultData.put("IsBand", nn4!=null?nn4.getText():"");			
			resultData.put("LastFail",nn5!=null?nn5.getText():"");
			resultData.put("NasIp",nn6!=null?nn6.getText():"");
			resultData.put("NasPortId",nn7!=null?nn7.getText():"");
			result = Result.buildSuccess(resultData);
		 
		}catch (XMLDocException e) {
			e.printStackTrace();

			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}

			result = Result.buildWSXmlParsedError();
		} 
		return result;
	}


	public Result addLoginlogOperation(JSONObject json) {
		// TODO Auto-generated method stub
		Result result = new Result();
		try {
			Map<Object, Object> result_data_node = getWorkOrderDAO().executeSaveLoginlogOperation(json);
			if("1000".equals(result_data_node.get("resultCode"))){
				result = Result.buildSuccess(result_data_node);
			}else{
				result = Result.buildParameterError();
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = Result.buildWSXmlParsedError();
			return result;
		}
	}
	
	
	
	public Result resetResourcesBroadbandOperation(JSONObject json) {
		String user_name = json.getString("username");
		if (logger.isDebugEnabled()) {
			logger.debug("�˺�ID: " + user_name);
		}

		Result result = null;
		try {
			String reqXml = getResetBroadbandBzRequest(user_name);
			String respXml="";
			if (debug){
				  respXml = XmlUtil.getRemotelXmlData(XJ_ZY_RESER_BROADBAND_QUERY);
			} else {
				  respXml = callWsService(
						  XJ_ZY_RESER_BROADBAND_QUERY, reqXml);
		    }
			System.out.println("���ͱ���:"+reqXml.toString());
			System.out.println("���ر���: + " + respXml);
			
			result = parseResetBroadbandZyResponse(respXml);
			
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			//
			result = Result.buildInvokeInterError();
		}

		return result;
	}

	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	private String getResetBroadbandBzRequest(String username) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	
		sb.append("<Data>").append("<QueryMethod>").append(
				"resetAccount").append(
				"</QueryMethod>")
				.append("<Params>").append("<Account>"+username+"</Account>")
				.append("</Params>").append("</Data>")
				.append("");
	
		if (logger.isDebugEnabled()) {
			logger.debug("�����ѯ������: " + sb.toString());
		}
		return sb.toString();
	}
	
	
	//��Ӧ����
	private Result parseResetBroadbandZyResponse(String respXml) {
		// TODO Auto-generated method stub
		if (logger.isDebugEnabled()) {
			logger.debug("����ʺŲ�ѯ��Ӧ����: " + respXml);
		}
		
		Result result = null;
		try {
			Document doc = Dom4jUtils.fromXML(respXml, "UTF-8");
	
			String rootPath = "/Data/";
			 
			Node nn1=doc.selectSingleNode(rootPath+ "State");
			Node nn2=doc.selectSingleNode(rootPath+ "Desc");
			
			Map<Object, Object> resultData = new HashMap<Object, Object>();
	//		//int size = rtEleList.size();
			resultData.put("State", nn1!=null?nn1.getText():"");
			resultData.put("Desc",nn2!=null?nn2.getText():"");
			result = Result.buildSuccess(resultData);
		 
		}catch (XMLDocException e) {
			e.printStackTrace();
	
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
	
			result = Result.buildWSXmlParsedError();
		} 
		return result;
	}
	
	
}
