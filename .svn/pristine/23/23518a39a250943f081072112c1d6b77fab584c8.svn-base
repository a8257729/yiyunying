package com.ztesoft.mobile.v2.AAAInterface;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ztesoft.mobile.v2.broadBandWs.AAAServiceSoap11BindingImplServiceLocator;
import com.ztesoft.mobile.v2.broadBandWs.Online_info;
import com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionBean;
import com.ztesoft.mobile.v2.broadBandWs.QueryUserSessionResponseBean;
import com.ztesoft.mobile.v2.service.assistant.AssistantServiceImpl;

public class AAAInterface {
	private static final Logger logger = Logger.getLogger(AAAInterface.class);
	
	
	private static AAAInterface aaaInterfaceService = new AAAInterface();
	
	private AAAInterface(){
	}

	public static AAAInterface getAAAInterfaceService() {
		return aaaInterfaceService;
	}
	public static void main(String[] args) {
		AAAServiceSoap11BindingImplServiceLocator service = new AAAServiceSoap11BindingImplServiceLocator();
		QueryUserSessionBean queryUserSessionBean = new QueryUserSessionBean();
		queryUserSessionBean.setVersion("1");;
		queryUserSessionBean.setServtype("1");
		queryUserSessionBean.setReqtype("117");
		Date date = new Date();
		String seq = String.valueOf(date.getTime());
		queryUserSessionBean.setSequence(seq);//
		queryUserSessionBean.setPriority("1");
		queryUserSessionBean.setContinued("1");
		queryUserSessionBean.setUser_name("073108922484");//宽带账号
		String dateStr = DateUtil.formatDate(date, "yyyyMMddHHmmss");
		queryUserSessionBean.setReqtime(dateStr);
		try {
			QueryUserSessionResponseBean response = service.getAAAServiceHttpSoap11Endpoint().queryUserSession(queryUserSessionBean);
			System.out.println(response.getRet_code());//返回结果
			System.out.println(response.getRet_msg());
			response.getOnline_infos();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
	}
	
	public Map<String,Object> queryAAAUserInlineStatus(String userName){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		AAAServiceSoap11BindingImplServiceLocator service = new AAAServiceSoap11BindingImplServiceLocator();
		QueryUserSessionBean queryUserSessionBean = new QueryUserSessionBean();
		queryUserSessionBean.setVersion("1");;
		queryUserSessionBean.setServtype("1");
		queryUserSessionBean.setReqtype("117");
		Date date = new Date();
		String seq = String.valueOf(date.getTime());
		queryUserSessionBean.setSequence(seq);//
		queryUserSessionBean.setPriority("1");
		queryUserSessionBean.setContinued("1");
		queryUserSessionBean.setUser_name(userName);//宽带账号
		String dateStr = DateUtil.formatDate(date, "yyyyMMddHHmmss");
		queryUserSessionBean.setReqtime(dateStr);
		try {
			QueryUserSessionResponseBean response = service.getAAAServiceHttpSoap11Endpoint().queryUserSession(queryUserSessionBean);
			resultMap.put("retCode", response.getRet_code());
			resultMap.put("retMsg", response.getRet_msg());
			Online_info[] online_infos = response.getOnline_infos();
			if(online_infos != null && online_infos.length > 0){
				Online_info onlineInfo = online_infos[0];
				resultMap.put("serStarttime", onlineInfo.getSerstarttime());
				resultMap.put("nasportid", onlineInfo.getNasportid());
			}

			logger.info(response.getRet_code());//返回结果
			logger.info(response.getRet_msg());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
		return resultMap;
	}

}
