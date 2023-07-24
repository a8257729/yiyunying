package com.ztesoft.mobile.v2.action.interf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAOImpl;

/**
 * ����ҵ��ϵͳ��Ϣ
 * @author heisonyee
 *
 */
public class OptBusiSysAction extends AbstractAction {

	public String execute() throws Exception {
		// ��ȡǰ̨����
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("���á�OptBusiSysAction���������ǣ�" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equals(optType)) {

			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			paramMap.put("buildTime", new Date());
			paramMap.put("updateTime", new Date());
			this.getMobileBusiSysDAO().insert(paramMap);	//����

		} else if("U".equals(optType)) {

			paramMap.put("updateTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getMobileBusiSysDAO().update(paramMap);	//�޸�

		} else if("D".equals(optType)) {

			String pushMessageIds = MapUtils.getString(paramMap, "busiSysIds");
			String[] idArr = pushMessageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<idArr.length; i++) {
				delMap.put("busiSysId", Long.valueOf(idArr[i]));
				this.getMobileBusiSysDAO().delete(delMap);	//ɾ��
			}

		} else {
			throw new RuntimeException("�������Ͳ���ȷoptType��" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private MobileBusiSysDAO getMobileBusiSysDAO() {
		String daoName = MobileBusiSysDAOImpl.class.getName();
		return (MobileBusiSysDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
