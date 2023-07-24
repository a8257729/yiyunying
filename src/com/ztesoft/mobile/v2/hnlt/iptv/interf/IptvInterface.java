package com.ztesoft.mobile.v2.hnlt.iptv.interf;

import com.ztesoft.mobile.v2.hnlt.iptv.servlet.BindServlet;
import com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterface;
import com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsInterfaceProxy;
import com.ztesoft.mobile.v2.hnlt.iptv.ws.RmsProxy;
import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IptvInterface {
	private static final Logger logger = Logger.getLogger(IptvInterface.class);

	// 通过账号查用户信息
	public Map queryAccount(String account) {
		Map outMap = new HashMap();
		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();
		try {
			String ret = rmsInf.queryAccount(account);
			//System.out.println(ret);
			JSONObject obj = JSONObject.fromObject(ret);
			outMap.put("result", obj.get("result"));
			outMap.put("description", obj.get("description"));

			JSONObject data = obj.getJSONObject("userdata");

			if(data.get("loginAccount") instanceof JSONNull) {
				outMap.put("loginAccount", "");
			}else {
				outMap.put("loginAccount", data.get("loginAccount"));
			}

			
			if(data.get("status") instanceof JSONNull) {
				outMap.put("status", "");
			}else {
				outMap.put("status", data.get("status"));
			}
			
			if(data.get("phoneNum") instanceof JSONNull) {
				outMap.put("phoneNum", "");
			}else {
				outMap.put("phoneNum", data.get("phoneNum"));
			}
			
			if(data.get("termtype") instanceof JSONNull) {
				outMap.put("termtype", "");
			}else {
				outMap.put("termtype", data.get("termtype"));
			}
			
			if(data.get("mac") instanceof JSONNull) {
				outMap.put("mac", "");
			}else {
				outMap.put("mac", data.get("mac"));
			}
			
			if(data.get("areano") instanceof JSONNull) {
				outMap.put("areano", "");
			}else {
				outMap.put("areano", data.get("areano"));
			}
			
			if(data.get("platformid") instanceof JSONNull) {
				outMap.put("platformid", "");
			}else {
				outMap.put("platformid", data.get("platformid"));
			}
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}

	// 通过MAC查用户信息
	public Map queryMAC(String mac) {
		Map outMap = new HashMap();
		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();
		try {
			String ret = rmsInf.queryMAC(mac);
			JSONObject obj = JSONObject.fromObject(ret);
			//System.out.println(ret);

			outMap.put("result", obj.get("result"));
			outMap.put("description", obj.get("description"));

			JSONObject data = obj.getJSONObject("userdata");
			
			if(data.get("username") instanceof JSONNull) {
				outMap.put("username", "");
			}else {
				outMap.put("username", data.get("username"));
			}

			if(data.get("phoneNum") instanceof JSONNull) {
				outMap.put("phoneNum", "");
			}else {
				outMap.put("phoneNum", data.get("phoneNum"));
			}

			if(data.get("platformType") instanceof JSONNull) {
				outMap.put("platformType", "");
			}else {
				outMap.put("platformType", data.get("platformType"));
			}

			if(data.get("termtype") instanceof JSONNull) {
				outMap.put("termtype", "");
			}else {
				outMap.put("termtype", data.get("termtype"));
			}

			if(data.get("groupid") instanceof JSONNull) {
				outMap.put("groupid", "");
			}else {
				outMap.put("groupid", data.get("groupid"));
			}

			if(data.get("areano") instanceof JSONNull) {
				outMap.put("areano", "");
			}else {
				outMap.put("areano", data.get("areano"));
			}

			if(data.get("password") instanceof JSONNull) {
				outMap.put("password", "");
			}else {
				outMap.put("password", data.get("password"));
			}

			
			outMap.put("mac", mac);
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}

	// 绑定IPTV账号和MAC
	public Map bandIPTVSTB(Map inMap) throws Exception {
		// map.put("cityId", "0013111");
		// map.put("servaccount", "97003201");
		// map.put("mac", "FFFFFFFFAAAA");
		Map outMap = new HashMap();
		outMap.put("resultCode", "002");
		outMap.put("resultDesc", "失败");

		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();
		try {
			String inXml = "<root><cityId>" + inMap.get("cityId") + "</cityId><servaccount>" + inMap.get("servaccount")
					+ "</servaccount><mac>" + inMap.get("mac") + "</mac><grid>" + inMap.get("grid") +
					"</grid><opertor>" + inMap.get("opertor") + "</opertor></root>";
			String ret = rmsInf.bandIPTVSTB(inXml);
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON j = xmlSerializer.read(ret);
			JSONObject obj = JSONObject.fromObject(j.toString());
			outMap.put("resultCode", obj.get("resultCode"));
			outMap.put("resultDesc", obj.get("resultDes"));
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}

	// 解绑IPTV账号和MAC
	public Map unbandIPTVSTB(Map inMap) throws Exception {
		// map.put("cityId", "0013111");
		// map.put("servaccount", "97003201");
		// map.put("mac", "FFFFFFFFAAAA");
		Map outMap = new HashMap();
		outMap.put("resultCode", "002");
		outMap.put("resultDesc", "失败");

		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();
		try {
			String inXml = "<root><cityId>" + inMap.get("cityId") + "</cityId><servaccount>" + inMap.get("servaccount")
					+ "</servaccount><mac>" + inMap.get("mac") + "</mac><grid>" + inMap.get("grid") +
					"</grid><opertor>" + inMap.get("opertor") + "</opertor></root>";
			String ret = rmsInf.unbandIPTVSTB(inXml);
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON j = xmlSerializer.read(ret);
			//System.out.println(j.toString());
			JSONObject obj = JSONObject.fromObject(j.toString());
			outMap.put("resultCode", obj.get("resultCode"));
			outMap.put("resultDesc", obj.get("resultDes"));
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}
	
	
	//根据业务账号修改用户高清类型
	public Map<String,Object> modifyHDTypeByAccount(Map<String,Object> map) throws RemoteException{
		Map<String,Object> resultMap = new HashMap<String,Object>();
		RmsProxy proxy = new RmsProxy();
		String ret = proxy.modifyHDTypeByAccountService(map);
		JSONObject jsonstringObj = JSONObject.fromObject(ret);
		resultMap.put("result",jsonstringObj.get("result"));
		resultMap.put("description",jsonstringObj.get("description"));
		return resultMap;
	}
	
	
	//根据业务账号修复机
		public Map<String,Object> resetByAccount(Map<String,Object> map) throws RemoteException{
			Map<String,Object> resultMap = new HashMap<String,Object>();
			RmsProxy proxy = new RmsProxy();
			String ret = proxy.resetByAccount(map);
			JSONObject jsonstringObj = JSONObject.fromObject(ret);
			resultMap.put("result",jsonstringObj.get("result"));
			resultMap.put("description",jsonstringObj.get("description"));
			return resultMap;
		}
	
	

	/**
	 * 故障诊断--客服接口
	 * @param map
	 * @return
	 * @throws RemoteException
	 * @author 李军
	 * 2018-09-23
	 */
	public List<Object> queryDiagnose(Map<String,Object> map) throws RemoteException{
		
		List<Object> list = new ArrayList<Object>();
		RmsProxy proxy = new RmsProxy();
		String ret = proxy.queryDiagnoseService(map);
		JSONObject obj = JSONObject.fromObject(ret);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("code", obj.get("code"));
		resultMap.put("msg", obj.get("msg"));
		resultMap.put("testData", obj.get("testData"));
		resultMap.put("testItemData", obj.get("testItemData"));
		System.out.println(ret);
		String msg = "查询成功！";
		list.add(msg);
		list.add(resultMap);
		return list;
	}
	
	
	/**
	 * 童锁
	 * @param map
	 * @return
	 * @throws RemoteException
	 * 2019-08-15
	 * @author 李军
	 */
		public Map<String,Object> ChdPass(Map<String,Object> map) throws RemoteException{
			
			RmsProxy proxy = new RmsProxy();
			String ret = proxy.chdPassService(map);
			JSONObject obj = JSONObject.fromObject(ret);
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("returnInfo", obj.get("returnInfo"));
			return resultMap;
		}


	//更换机顶盒
	public Map changeIPTVSTB(Map inMap) throws Exception {
		Map outMap = new HashMap();
		outMap.put("resultCode", "002");
		outMap.put("resultDesc", "失败");

		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();
		try {
			String inXml = "<root><cityId>" + inMap.get("cityId") + "</cityId><servaccount>" + inMap.get("servaccount")
					+ "</servaccount><mac>" + inMap.get("mac") + "</mac><oldMac>" + inMap.get("oldMac") +
					"</oldMac><grid>" + inMap.get("grid") +
					"</grid><opertor>" + inMap.get("opertor") + "</opertor></root>";
			logger.info("更换机顶盒请求：\n"+ inXml);
			String ret = rmsInf.changeIPTVSTB(inXml);
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON j = xmlSerializer.read(ret);
			JSONObject obj = JSONObject.fromObject(j.toString());
			outMap.put("resultCode", obj.get("resultCode"));
			outMap.put("resultDesc", obj.get("resultDes"));
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}


	/**
	 * 更换机顶盒绑定申请
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	public Map changeIPTVStbApply(Map inMap) throws Exception {
		Map outMap = new HashMap();
		outMap.put("resultCode", "002");
		outMap.put("resultDesc", "失败");

		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();

		String cmdId = (String) inMap.get("cmdId");
		try {
			String inXml = "<root><cmdId>" + cmdId + "</cmdId><address>" + inMap.get("address") + "</address><servaccount>" + inMap.get("servaccount")
					+ "</servaccount><mac>" + inMap.get("mac") + "</mac><grid>" + inMap.get("grid") +
					"</grid><opertor>" + inMap.get("opertor") + "</opertor></root>";
			logger.info("更换机顶盒申请请求：\n"+ inXml);
			String ret = rmsInf.changeIPTVStbApply(inXml);
			XMLSerializer xmlSerializer = new XMLSerializer();
			JSON j = xmlSerializer.read(ret);
			JSONObject obj = JSONObject.fromObject(j.toString());
			outMap.put("resultCode", obj.get("resultCode"));
			outMap.put("resultDesc", obj.get("resultDes"));
		} catch (RemoteException e) {
			logger.error(e.toString(),e);
		}

		return outMap;
	}

	/**
	 * 查询更换机顶盒绑定申请工单执行结果
	 * @param cmdId
	 * @return
	 */
	public Map queryStbBindChgRecord(String cmdId) throws RemoteException {
		Map outMap = new HashMap();
		RmsInterfaceProxy proxy = new RmsInterfaceProxy();
		RmsInterface rmsInf = proxy.getRmsInterface();

		String ret = rmsInf.queryStbBindChgRecord(cmdId);
		JSONObject obj = JSONObject.fromObject(ret);

		outMap.put("result", obj.get("result"));
		outMap.put("description", obj.get("description"));
		outMap.put("status", obj.get("status"));
		return outMap;

	}
	
	
	
	//测试用例，可删除
	 public static void main(String[] args) throws Exception {
		IptvInterface iptvInf = new IptvInterface();
//		Map map = iptvInf.queryAccount("97003201");
//		System.out.println(map);
//		map = iptvInf.queryMAC("FFFFFFFFAAAF");
//		System.out.println(map);
//
//		map = new HashMap();
//		map.put("cityId", "0013111");
//		map.put("servaccount", "97003201");
//		map.put("mac", "FFFFFFFFAAAF");
//
//
//		//System.out.println(iptvInf.unbandIPTVSTB(map));
//		System.out.println(iptvInf.bandIPTVSTB(map));
//		 Map map = iptvInf.queryStbBindChgRecord("123568");
//		 System.out.println(map.toString());

		 Map paramMap = new HashMap<String,String>();
		 paramMap.put("address", "测试地址");
		 paramMap.put("mac", "F44C70F8DDA3");
		 paramMap.put("servaccount", "073400692171A@tv");
		 paramMap.put("opertor", "");
		 paramMap.put("grid", "");
		 Map map = iptvInf.changeIPTVStbApply(paramMap);
		 System.out.println(map.toString());
	 }

}
