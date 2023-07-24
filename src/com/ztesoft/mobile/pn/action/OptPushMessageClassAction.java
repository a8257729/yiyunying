package com.ztesoft.mobile.pn.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.pn.dao.PushMessageClassDAO;
import com.ztesoft.mobile.pn.dao.PushMessageClassDAOImpl;
import com.ztesoft.mobile.pn.dao.PushMessageDAO;
import com.ztesoft.mobile.pn.dao.PushMessageDAOImpl;

public class OptPushMessageClassAction extends AbstractAction {

	public String execute() throws Exception {
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptPushMessageClassAction】，参数是：" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equalsIgnoreCase(optType)) {

			paramMap.put("createTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageClassDAO().insert(paramMap);	//新增

		} else if("U".equalsIgnoreCase(optType)) {

			paramMap.put("createTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getPushMessageClassDAO().update(paramMap);	//修改

		} else if("D".equalsIgnoreCase(optType)) {
			String pushMessageIds = MapUtils.getString(paramMap, "pushMessageClassIds");
			String[] ids = pushMessageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<ids.length; i++) {
				delMap.put("pushMessageClassId", Long.valueOf(ids[i]));
				this.getPushMessageClassDAO().delete(delMap);	//删除
			}

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private PushMessageClassDAO getPushMessageClassDAO() {
		String daoName = PushMessageClassDAOImpl.class.getName();
		return (PushMessageClassDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
