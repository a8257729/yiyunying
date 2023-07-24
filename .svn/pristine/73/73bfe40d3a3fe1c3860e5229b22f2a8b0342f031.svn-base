package com.ztesoft.mobile.pn.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.pn.dao.PushMessageDAO;
import com.ztesoft.mobile.pn.dao.PushMessageDAOImpl;

public class OptPushMessageAction extends AbstractAction {

	public String execute() throws Exception {
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptPushMessageAction】，参数是：" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equals(optType)) {

			paramMap.put("messageTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageDAO().insert(paramMap);	//新增

		} else if("U".equals(optType)) {

			paramMap.put("messageTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageDAO().update(paramMap);	//修改

		} else if("D".equals(optType)) {

			String pushMessageIds = MapUtils.getString(paramMap, "pushMessageIds");
			String[] idArr = pushMessageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<idArr.length; i++) {
				delMap.put("pushMessageId", Long.valueOf(idArr[i]));
				this.getPushMessageDAO().delete(delMap);	//删除
			}

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private PushMessageDAO getPushMessageDAO() {
		String daoName = PushMessageDAOImpl.class.getName();
		return (PushMessageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
