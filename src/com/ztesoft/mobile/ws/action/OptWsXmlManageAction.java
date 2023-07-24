package com.ztesoft.mobile.ws.action;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.ws.dao.WsXmlManageDAO;
import com.ztesoft.mobile.ws.dao.WsXmlManageDAOImpl;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OptWsXmlManageAction extends AbstractAction {

	public String execute() throws Exception {
		// ��ȡǰ̨����
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("���á�OptWsXmlManageAction���������ǣ�" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equalsIgnoreCase(optType)) {

			paramMap.put("uploadTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getWsXmlManageDAO().insert(paramMap);	//����

		} else if("U".equalsIgnoreCase(optType)) {

			paramMap.put("uploadTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getWsXmlManageDAO().update(paramMap);	//�޸�

		} else if("D".equalsIgnoreCase(optType)) {
			String wsXmlManageIds = MapUtils.getString(paramMap, "wsXmlManageIds");
			String[] ids = wsXmlManageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<ids.length; i++) {
				delMap.put("wsXmlManageId", Long.valueOf(ids[i]));
				this.getWsXmlManageDAO().delete(delMap);	//ɾ��
			}

		} else {
			throw new RuntimeException("�������Ͳ���ȷoptType��" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private WsXmlManageDAO getWsXmlManageDAO() {
		String daoName = WsXmlManageDAOImpl.class.getName();
		return (WsXmlManageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
