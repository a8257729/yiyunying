package com.ztesoft.eoms.im;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.ztesoft.eoms.common.dao.BaseDAOFactory;
import com.ztesoft.eoms.common.helper.TransactionHelper;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.im.dao.ImChatLogDAO;
import com.ztesoft.eoms.im.dao.ImChatLogDAOImpl;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAO;
import com.ztesoft.eoms.im.dao.ImStaffBiggroupRealDAOImpl;

import nl.justobjects.pushlet.core.Command;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.core.Subscriber;
import nl.justobjects.pushlet.core.Subscription;

public class MsgDispatchService {
	//��Ϣ����
	public void dispatchMsg(Command command,Session session){
		//ȡ����Ϣ�ؼ��ֶ�
		//Ŀ���û�
		String m_to = command.reqEvent.getField("p_to");
		//��Դ�û�
		String m_from = session.getId();
		//��Ϣ����
		String m_content =  command.reqEvent.getField("message");
		//�ж�Ŀ���û��Ƿ�����
		//Session to_session = SessionManager.getInstance().getSession(m_to);
		
			UserTransaction userTransaction1  = null;
			try {
				userTransaction1 = TransactionHelper.getTransaction();
				userTransaction1.begin();
				//��־�Ѷ���Ϣ
				Map fromMsgMap = new HashMap();
				fromMsgMap.put("msgBelongStaffId", m_from);
				fromMsgMap.put("senderStaffId", m_from);
				fromMsgMap.put("receiveStaffId", m_to);
				fromMsgMap.put("msgContent", m_content);
				fromMsgMap.put("msgDate", new Date());
				fromMsgMap.put("readState", new Integer(1));
				fromMsgMap.put("msgType", new Integer(1));
		
				getImChatLogDAO().insert(fromMsgMap);
				Map toMsgMap = new HashMap();
				toMsgMap.put("msgBelongStaffId", m_to);
				toMsgMap.put("senderStaffId", m_from);
				toMsgMap.put("receiveStaffId", m_to);
				toMsgMap.put("msgContent", m_content);
				toMsgMap.put("msgDate", new Date());
				toMsgMap.put("msgType", new Integer(1));
				
				//if(to_session==null){//������
					toMsgMap.put("readState", new Integer(0));
				//}else{
					//toMsgMap.put("readState", new Integer(1));
				//}
				getImChatLogDAO().insert(toMsgMap);
				userTransaction1.commit();
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicMixedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
		
	}
	
	/**
	 * //Ⱥ��Ϣ����
	 */
	public void dispatchGroupMsg(Command command,Session session,int groupId){
		Event event = command.reqEvent;
		//��Ϣ������
		int senderId = Integer.parseInt(session.getId());
		String message = command.reqEvent.getField("message");
		//���������¼
		getImChatLogDAO().insertGroupLog(senderId, groupId, message);
		//�ɷ�֪ͨ
		//��ѯ��Ⱥ���е�Ⱥ��Աid
		Map qryMap = new HashMap();
		qryMap.put("imStaffBiggroupId", groupId);
		List staffList = null;
		Event clonedEvent = null;
		Subscription subscription = null;
		Subscriber subscriber = null;
		try {
			staffList = getImStaffBiggroupRealDAO().selStaffsByGroupId(qryMap);
			for(int i=0;i<staffList.size();i++){
				Map map = (Map)staffList.get(i);
				Session imSession = SessionManager.getInstance().getSession(map.get("staffId").toString());
				if(imSession!=null){
					subscriber = imSession.getSubscriber();
					if ((subscription = subscriber.match(event)) != null) {
						clonedEvent = (Event) event.clone();
						clonedEvent.setField("p_sid", subscription.getId());
						clonedEvent.setField("p_groupId", groupId);
						if (subscription.getLabel() != null) {
							clonedEvent.setField("p_label", subscription.getLabel());
						}

						subscriber.onEvent(clonedEvent);
					}
				}
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
	}
	private ImChatLogDAO getImChatLogDAO() {
		String daoName = ImChatLogDAOImpl.class.getName();
		return (ImChatLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private ImStaffBiggroupRealDAO getImStaffBiggroupRealDAO() {
		String daoName = ImStaffBiggroupRealDAOImpl.class.getName();
		return (ImStaffBiggroupRealDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
