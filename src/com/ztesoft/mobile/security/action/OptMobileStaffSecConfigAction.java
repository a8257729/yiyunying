package com.ztesoft.mobile.security.action;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.security.dao.MobileStaffSecConfigDAO;
import com.ztesoft.mobile.security.dao.MobileStaffSecConfigDAOImpl;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OptMobileStaffSecConfigAction extends AbstractAction {

	public String execute() throws Exception {
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptMobileStaffSecConfigAction】，参数是：" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equalsIgnoreCase(optType)) {

			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getMobileStaffSecConfigDAO().insert(paramMap);	//新增

		} else if("U".equalsIgnoreCase(optType)) {

			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getMobileStaffSecConfigDAO().update(paramMap);	//修改

		} else if("D".equalsIgnoreCase(optType)) {
			String mobileStaffSecConfigIds = MapUtils.getString(paramMap, "mobileStaffSecConfigIds");
			String[] ids = mobileStaffSecConfigIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<ids.length; i++) {
				delMap.put("mobileStaffSecConfigId", Long.valueOf(ids[i]));
				this.getMobileStaffSecConfigDAO().delete(delMap);	//删除
			}

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private MobileStaffSecConfigDAO getMobileStaffSecConfigDAO() {
		String daoName = MobileStaffSecConfigDAOImpl.class.getName();
		return (MobileStaffSecConfigDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
