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
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptMobilePnUserAction】，参数是：" + paramMap);
	
		String optType = MapUtils.getString(paramMap,"optType");
	
		if("I".equals(optType)) {
			this.getMobilePnUserDAO().insert(paramMap);	//新增
		} else if("U".equals(optType)) {
			this.getMobilePnUserDAO().update(paramMap);	//修改
		} else if("D".equals(optType)) {
			String pnUserIds = MapUtils.getString(paramMap, "pnUserIds");
			String[] idArr = pnUserIds.split(",");
			Map delMap = new HashMap();
		
			for(int i=0; i<idArr.length; i++) {
				delMap.put("pnUserId", Long.valueOf(idArr[i]));
				this.getMobilePnUserDAO().delete(delMap);	//删除
			}
		
		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
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
