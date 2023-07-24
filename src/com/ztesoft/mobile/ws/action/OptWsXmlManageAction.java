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
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptWsXmlManageAction】，参数是：" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equalsIgnoreCase(optType)) {

			paramMap.put("uploadTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getWsXmlManageDAO().insert(paramMap);	//新增

		} else if("U".equalsIgnoreCase(optType)) {

			paramMap.put("uploadTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getWsXmlManageDAO().update(paramMap);	//修改

		} else if("D".equalsIgnoreCase(optType)) {
			String wsXmlManageIds = MapUtils.getString(paramMap, "wsXmlManageIds");
			String[] ids = wsXmlManageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<ids.length; i++) {
				delMap.put("wsXmlManageId", Long.valueOf(ids[i]));
				this.getWsXmlManageDAO().delete(delMap);	//删除
			}

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
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
