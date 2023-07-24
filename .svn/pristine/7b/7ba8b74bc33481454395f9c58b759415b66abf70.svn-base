package com.ztesoft.mobile.v2.action.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.pn.xmpp.push.NotificationManager;
import com.ztesoft.mobile.system.dao.StaffSelDAO;
import com.ztesoft.mobile.system.dao.StaffSelDAOImpl;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAOImpl;

public class MobileFrameAppAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		System.out.println(param);
		param.put("name", "易运营智能终端");
		param.put("iconUrl", "ebiz_logo.png");
		param.put("isRelease", 1);
		param.put("state", 1);
		param.put("stateDate", new Date());
		param.put("updateTime", new Date());
		if(type.equals("add")){
			param.put("optType", Constants.OptType.INSERT);
			param.put("downloadCount", new Long(0));
			param.put("buildTime", new Date());
			//TODO 以后有用
			param.put("qrcodeUrl", "");
			Map map=new HashMap();
			  map.put("isRelease", 0);
			getMobileFrameAppDAO().updateIsRelease(map);
			getMobileFrameAppDAO().insert(param);
			
			//推送消息
			int notiFlag = MapUtils.getIntValue(param, "notiFlag");
			String downloadUrl = Constants.HOST_IP + MapUtils.getString(param, "downloadUrl");
			if(1 == notiFlag) {	//推送
				System.out.println("调用推送接口，推送应用更新");
				String msg = MapUtils.getString(param, "updateLog");
				NotificationManager notiManager = new NotificationManager();
				notiManager.sendBroadcast(Constants.NOTI_API_KEY, "应用更新推送", msg, downloadUrl);
			}
			
		} else if (type.equals("mod")) {
			param.put("optType", Constants.OptType.UPDATE);
			getMobileFrameAppDAO().update(param);
			
			//推送消息
			int notiFlag = MapUtils.getIntValue(param, "notiFlag");
			String downloadUrl = Constants.HOST_IP + "/MOBILE/api/server/downloads/appFrame/" + MapUtils.getString(param, "frameAppId");
			if(1 == notiFlag) {	//推送
				System.out.println("调用推送接口，推送应用更新");
				String msg = MapUtils.getString(param, "updateLog");
				NotificationManager notiManager = new NotificationManager();
				notiManager.sendBroadcast(Constants.NOTI_API_KEY, "应用更新推送", msg, downloadUrl);
			}
			
		} else if (type.equals("del")) {
			getMobileFrameAppDAO().delete(param);
			paramMap.put("isRelease", 0);
			Map map = getMobileFrameAppDAO().getLatestVersion(paramMap);
			map.put("isRelease", 1);
			getMobileFrameAppDAO().updateLatestIsRelease(map);
		} else if (type.equals("incDownCount")) {
			Integer downloadCount = MapUtils.getInteger(param, "downloadCount");
			downloadCount=downloadCount+1;
			param.put("downloadCount", downloadCount);
			getMobileFrameAppDAO().incDownloadCount(param);
		} else if (type.equals("pushMsg")) {		// 短信推送
			String msgContent = MapUtils.getString(param, "msgContent");
			List<Map> nodesArr = (List) MapUtils.getObject(param, "nodesArr");
			List<Map> staffList = new ArrayList<Map>();
			System.out.println("nodesArr==>" + nodesArr);
			for (int i = 0; i < nodesArr.size(); i++) {
				int orgId = MapUtils.getIntValue(nodesArr.get(i), "id");
				String orgPathCode = MapUtils.getString(nodesArr.get(i), "pathCode");
				Map map = getStaffSelDAO().getOrgStaffForPushMsg(orgId, orgPathCode, null);
				List tmpList = (List) map.get("resultList");
				if (tmpList != null && tmpList.size() != 0) {
					staffList.addAll(tmpList);
				}
			}
			System.out.println("staffList==>" + staffList);
			
			Map map = getMobileFrameAppDAO().getSmsContentId(param);
			map.put("msgContent", msgContent);
			
			getMobileFrameAppDAO().insertSmsContent(map);
			for (int i = 0; i < staffList.size(); i++) {
				map.put("name", MapUtils.getString(staffList.get(i), "name"));
				map.put("mobileTel", MapUtils.getString(staffList.get(i), "mobileTel"));
				getMobileFrameAppDAO().insertSmsStaff(map);
			}
			
		} else if (type.equals("pushMsgForStaff")) {
			String msgContent = MapUtils.getString(param, "msgContent");
			List<Map> staffList = (List) MapUtils.getObject(param, "staffArr");
			System.out.println("staffList==>" + staffList);
			
			Map map = getMobileFrameAppDAO().getSmsContentId(param);
			map.put("msgContent", msgContent);
			
			getMobileFrameAppDAO().insertSmsContent(map);
//			List nameList = new ArrayList();  
//			List telList = new ArrayList(); 
			for (int i = 0; i < staffList.size(); i++) {
//				nameList.add(MapUtils.getString(staffList.get(i), "name"));
//				telList.add(MapUtils.getString(staffList.get(i), "mobileTel"));
				map.put("name", MapUtils.getString(staffList.get(i), "name"));
				map.put("mobileTel", MapUtils.getString(staffList.get(i), "mobileTel"));
				getMobileFrameAppDAO().insertSmsStaff(map);
			}
//			map.put("nameList", nameList);
//			map.put("telList", telList);
			
		}
		return SUCCESS;
	}
	
	 private MobileFrameAppDAO getMobileFrameAppDAO() {
	    	String daoName = MobileFrameAppDAOImpl.class.getName();
	        return (MobileFrameAppDAO) BaseDAOFactory.getImplDAO(daoName);
	 }
	 
	 
	 private StaffSelDAO getStaffSelDAO() {
	    	String daoName = StaffSelDAOImpl.class.getName();
	        return (StaffSelDAO) BaseDAOFactory.getImplDAO(daoName);
	 }
}
