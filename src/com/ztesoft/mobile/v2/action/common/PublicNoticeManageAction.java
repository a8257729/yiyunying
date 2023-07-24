package com.ztesoft.mobile.v2.action.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.message.dao.MobileMessageDAO;
import com.ztesoft.mobile.message.dao.MobileMessageDAOImpl;
import com.ztesoft.mobile.pn.xmpp.push.NotificationManager;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.dao.common.MobileNoticeDAO;
import com.ztesoft.mobile.v2.dao.common.MobileNoticeDAOImpl;


public class PublicNoticeManageAction extends AbstractAction{
	public String execute() throws Exception {
		// ��ȡǰ̨����
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equals(optType)) {

			//paramMap.put("createTime", new Date());
			paramMap.put("state", new  Integer(1));
			getMobileNoticeDAO().insert(paramMap);	//����
			
			//����
			System.out.println("�������ͽӿڣ����͹���");
			String content = MapUtils.getString(paramMap,"content");
			String title = MapUtils.getString(paramMap,"title");
			NotificationManager notiManager = new NotificationManager();
			notiManager.sendBroadcast(Constants.NOTI_API_KEY, title, content, title);

		} else if("U".equals(optType)) {

			getMobileNoticeDAO().update(paramMap);	//�޸�

		} else if("D".equals(optType)) {

			getMobileNoticeDAO().delete(paramMap);

		} else {
			throw new RuntimeException("�������Ͳ���ȷoptType��" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private MobileNoticeDAO getMobileNoticeDAO() {
		String daoName = MobileNoticeDAOImpl.class.getName();
		return (MobileNoticeDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
