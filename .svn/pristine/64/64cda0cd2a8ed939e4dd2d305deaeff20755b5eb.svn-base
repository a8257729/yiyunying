package com.ztesoft.mobile.message.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.message.dao.MobileMessageDAO;
import com.ztesoft.mobile.message.dao.MobileMessageDAOImpl;


public class PublicMessageManageAction extends AbstractAction{
	public String execute() throws Exception {
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equals(optType)) {

			paramMap.put("createTime", new Date());
			paramMap.put("state", new  Integer(1));
			getMobileMessageDAO().insert(paramMap);	//新增

		} else if("U".equals(optType)) {

			getMobileMessageDAO().update(paramMap);	//修改

		} else if("D".equals(optType)) {

			getMobileMessageDAO().delete(paramMap);

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private MobileMessageDAO getMobileMessageDAO() {
		String daoName = MobileMessageDAOImpl.class.getName();
		return (MobileMessageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
