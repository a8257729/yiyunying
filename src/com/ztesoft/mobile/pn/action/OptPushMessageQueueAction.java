package com.ztesoft.mobile.pn.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.pn.Constants;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAO;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAOImpl;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAO;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAOImpl;
import com.ztesoft.mobile.pn.xmpp.push.NotificationManager;
import com.ztesoft.mobile.pn.xmpp.session.ClientSession;
import com.ztesoft.mobile.pn.xmpp.session.SessionManager;

public class OptPushMessageQueueAction extends AbstractAction {

	public String execute() throws Exception {
		// ��ȡǰ̨����
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		
		System.out.println("���á�OptPushMessageQueueAction���������ǣ�" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");
		String uri = MapUtils.getString(paramMap,"pushUrl");

		if("I".equals(optType)) {
			
			String receiverTokens = MapUtils.getString(paramMap, "receiverTokens", null);
			String pushTitle = MapUtils.getString(paramMap, "pushTitle", "");
			String pushContent = MapUtils.getString(paramMap, "pushContent", "");
			String pushUrl = MapUtils.getString(paramMap, "pushUrl", "");

			int messageQueueStatus = MapUtils.getIntValue(paramMap, "messageQueueStatus", -1);
			if(0 == messageQueueStatus) {	//�����ͣ�
				
				//�������״̬Ϊ�������͡�ʱ����������ͽӿ�
				if(null != receiverTokens) {
					System.out.println("�������ͽӿ�");
					NotificationManager notiManager = new NotificationManager();
					notiManager.sendNotifcationToUser(receiverTokens, pushTitle, pushContent, pushUrl);
				}
				paramMap.put("messageQueueTime", new Date());
			}
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageQueueDAO().insert(paramMap);	//����
			

		} else if("U".equals(optType)) {
			
			String receiverTokens = MapUtils.getString(paramMap, "receiverTokens", null);
			String pushTitle = MapUtils.getString(paramMap, "pushTitle", "");
			String pushContent = MapUtils.getString(paramMap, "pushContent", "");
			String pushUrl = MapUtils.getString(paramMap, "pushUrl", "");

			int messageQueueStatus = MapUtils.getIntValue(paramMap, "messageQueueStatus", -1);
			if(0 == messageQueueStatus) {	//�����ͣ�
				
				//�������״̬Ϊ�������͡�ʱ����������ͽӿ�
				if(null != receiverTokens) {
					System.out.println("�������ͽӿ�");
					NotificationManager notiManager = new NotificationManager();
					notiManager.sendNotifcationToUser(receiverTokens, pushTitle, pushContent, pushUrl);
				}
				paramMap.put("messageQueueTime", new Date());
			}

			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageQueueDAO().update(paramMap);	//�޸�

		} else if("D".equals(optType)) {

			String pushMessageQueueIds = MapUtils.getString(paramMap, "pushMessageQueueIds");
			String[] idArr = pushMessageQueueIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<idArr.length; i++) {
				delMap.put("pushMessageQueueId", Long.valueOf(idArr[i]));
				this.getPushMessageQueueDAO().delete(delMap);	//ɾ��
			}

		} else if("P".equals(optType)){	//����
		
			
			//String receiverTokens = MapUtils.getString(paramMap, "receiverTokens", null);
			Long receiverId = MapUtils.getLong(paramMap, "receiverId", null);
			String pushTitle = MapUtils.getString(paramMap, "pushTitle", "");
			String pushContent = MapUtils.getString(paramMap, "pushContent", "");
			
			Map qMap = new HashMap();
			qMap.put("pnStaffId", receiverId);
			qMap.put("pnOnline", new Integer(1));
			Map userMap = this.getMobilePnUserDAO().getOneByCons(qMap);
			
			
			if(null == userMap || 0 == userMap.size()) {
				rtMap.put("flag", "N");
				rtMap.put("reason", "�û�����������״̬��");
				
			} else {
				//��ȡ���µ�tokens
				String tokens =  MapUtils.getString(userMap, "pnUserName", null);
				
				//�ж����ڴ��У��û��Ƿ�����
				SessionManager sessionManager = SessionManager.getInstance();
				ClientSession clientSession = sessionManager.getSession(tokens);
				
				if(null != clientSession) {
					Map dataQueueMap = this.getPushMessageQueueDAO().selById(paramMap);
					
					//�������ͽӿ�
					NotificationManager notiManager = new  NotificationManager();
//					notiManager.sendNotifcationToUser(tokens, pushTitle, pushContent);
					// ��ָ���û�������Ϣ
					notiManager.sendNotifcationToUser(Constants.PN_APIKEY_VALUE, tokens, pushTitle, pushContent, uri);
					dataQueueMap.put("receiverTokens", tokens);		//ͳһҲҪ����tokens
					dataQueueMap.put("messageQueueTime", new Date());		//��������ʱ��
					dataQueueMap.put("messageQueueStatus", new Integer(0));	//����Ϊ��������"״̬
					//������Ϻ󣬸��¼�¼״̬
					this.getPushMessageQueueDAO().update(dataQueueMap);
					
					System.out.println("������Ϣ....");
					
				} else {
					//
					rtMap.put("flag", "N");
					rtMap.put("reason", "�û�����������״̬��");
				}
			}
			
		} else {
			throw new RuntimeException("�������Ͳ���ȷoptType��" + optType);
		}

		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}


	private PushMessageQueueDAO getPushMessageQueueDAO() {
		String daoName = PushMessageQueueDAOImpl.class.getName();
		return (PushMessageQueueDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
	private MobilePnUserDAO getMobilePnUserDAO() {
		String daoName = MobilePnUserDAOImpl.class.getName();
		return (MobilePnUserDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
