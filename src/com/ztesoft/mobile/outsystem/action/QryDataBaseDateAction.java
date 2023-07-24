package com.ztesoft.mobile.outsystem.action;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.mobile.common.helper.DataBaseHelper;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;

public class QryDataBaseDateAction extends AbstractAction {

	//获取数据库当前的时间信息供页面使用
	public String execute() throws Exception {
		try {
			Map resultMap = new HashMap();
			DataBaseHelper helper = new DataBaseHelper();
			int currentYear = helper.getDataBaseCurrentYear();
			int currentMonth = helper.getDataBaseCurrentMonth();
			resultMap.put("currentYear", currentYear);
			resultMap.put("currentMonth", currentMonth);
			
			System.out.println(" QryDataBaseDateAction resultMap: " + resultMap);
			DedicatedActionContext.setResult(resultMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return SUCCESS;
	}
}
