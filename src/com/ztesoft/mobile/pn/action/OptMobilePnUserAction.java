package com.ztesoft.mobile.pn.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAO;
import com.ztesoft.mobile.pn.dao.MobilePnUserDAOImpl;

public class OptMobilePnUserAction extends AbstractAction {
	
	public String execute() throws Exception {
		// ��ȡǰ̨����
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("���á�OptMobilePnUserAction���������ǣ�" + paramMap);
	
		String optType = MapUtils.getString(paramMap,"optType");
	
		if("I".equals(optType)) {
			this.getMobilePnUserDAO().insert(paramMap);	//����
		} else if("U".equals(optType)) {
			this.getMobilePnUserDAO().update(paramMap);	//�޸�
		} else if("D".equals(optType)) {
			String pnUserIds = MapUtils.getString(paramMap, "pnUserIds");
			String[] idArr = pnUserIds.split(",");
			Map delMap = new HashMap();
		
			for(int i=0; i<idArr.length; i++) {
				delMap.put("pnUserId", Long.valueOf(idArr[i]));
				this.getMobilePnUserDAO().delete(delMap);	//ɾ��
			}
		
		} else {
			throw new RuntimeException("�������Ͳ���ȷoptType��" + optType);
		}
	
		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);
	
		return SUCCESS;
	}

	private MobilePnUserDAO getMobilePnUserDAO() {
		String daoName = MobilePnUserDAOImpl.class.getName();
		return (MobilePnUserDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
