package com.ztesoft.mobile.systemMonitor.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileSessionRecordDAOImpl;

public class UpdateConnectLimitAction extends AbstractAction{
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");

		String flag = MapUtils.getString(param, "flag");
		if (flag.equals("1")){
			getMobileSessionRecordDAO().updateConnectLimit(param);
		}else if (flag.equals("2")){
			getMobileSessionRecordDAO().updateUserConnPriv(param);
		}else if (flag.equals("3")){
			getMobileSessionRecordDAO().updateUserFlowLimit(param);
		}else if (flag.equals("4")){
			getMobileSessionRecordDAO().updateUserConnLimit(param);
		}else if (flag.equals("5")){
			param = getMobileSessionRecordDAO().selUserConnLimit(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
	private MobileSessionRecordDAO getMobileSessionRecordDAO() {
		String daoName = MobileSessionRecordDAOImpl.class.getName();
		return (MobileSessionRecordDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
