package com.ztesoft.mobile.v2.action.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.rest.MobileRestServiceDAO;
import com.ztesoft.mobile.v2.dao.rest.MobileRestServiceDAOImpl;

public class OptRestServiceAction extends AbstractAction {

	@Override
	public String execute() throws Exception {

		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		String menuType = MapUtils.getString(param, "menuType");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(param);
		if (type.equals("add")) {
			param.put("buildTime", new Date());
			getMobileRestServiceDAO().insert(param);
		} else if (type.equals("mod")) {
			if (param.get("buildTime").toString() != null
					&& !param.get("buildTime").toString().equals("")) {
			           	param.put("buildTime", sdf.parse(param.get("buildTime")
						.toString()));
			}
			param.put("updateTime", new Date());
			System.out.println(param);
			getMobileRestServiceDAO().update(param);
		} else if (type.equals("del")) {
			getMobileRestServiceDAO().delete(param);
		} else if (type.equals("flush")) {

		}
		return null;
	}

	private MobileRestServiceDAO getMobileRestServiceDAO() {
		String daoName = MobileRestServiceDAOImpl.class.getName();
		return (MobileRestServiceDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
