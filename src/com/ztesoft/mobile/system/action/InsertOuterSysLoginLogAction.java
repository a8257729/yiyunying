package com.ztesoft.mobile.system.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.vo.Organization;
import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.system.dao.OuterSysLoginLogDAO;
import com.ztesoft.mobile.system.dao.OuterSysLoginLogDAOImpl;
import com.ztesoft.mobile.system.vo.LoginInfo;


public class InsertOuterSysLoginLogAction extends AbstractAction {
	@Override
	public String execute() throws Exception {
		Map paramMap = (Map) DedicatedActionContext.getParams().get(
				"parameter_1");
		//参数定义
		String menuName = null;//访问菜单名称
		String url = null;//访问的外系统地址
		String toSysIpAddress = null;//访问的外系统的IP地址和端口,如10.40.199.151:9080
		Long staffLoginLogId = null;//登陆日志表的ID，即访问外系统的SessionId
		Long outerSysId = null;//外系统的ID
		Map insertMap = null;//用于插入新日志记录使用的Map
		
		//初始参数
		url = String.valueOf(paramMap.get("url"));
		if (url.indexOf("http://") == -1) {
			//不合法的访问地址，直接返回，不做记录
			DedicatedActionContext.setResult(SUCCESS);
			return SUCCESS;
		}
		menuName = String.valueOf(paramMap.get("menuName"));
		if(url.indexOf("/", 7) == -1){
			toSysIpAddress = url.substring(7, url.length());
		}else{
			toSysIpAddress = url.substring(7, url.indexOf("/", 7));
		}
		LoginInfo loginInfo = (LoginInfo)DedicatedActionContext.getSession().get("loginInfo");
		staffLoginLogId = loginInfo.getStaffLoginLogId();
		Staff staff =  (Staff)DedicatedActionContext.getSession().get("staff");
	 	Organization org = (Organization)DedicatedActionContext.getSession().get("org");
		List systems = (List) DedicatedActionContext.getSession().get("systems");
		for (int i = 0; i < systems.size(); i++) {
			Map system = (Map) systems.get(i);
			String sysIpAddress = system.get("sysIpAddress")!=null ? system.get("sysIpAddress").toString() : null;
			System.out.println("sysIpAddress:"+sysIpAddress);
			if (toSysIpAddress.equalsIgnoreCase(sysIpAddress)) {
				outerSysId = Long.valueOf(system.get("sysId").toString());
				if(!isOuterSysLogExit(staffLoginLogId, outerSysId)){
					insertMap = new HashMap();
					insertMap.put("sessionId", staffLoginLogId);
					insertMap.put("staffId", staff.getStaffId());
					insertMap.put("staffName", staff.getStaffName());
					insertMap.put("orgId", new Long(org.getOrgId()));
					insertMap.put("orgName", org.getOrgName());
					insertMap.put("outerSysId", outerSysId);
					insertMap.put("outerSysName", system.get("sysName").toString());
					insertMap.put("loginDate", new Date());
					insertMap.put("menuName", menuName);
					insertMap.put("state", 1);
					getOuterSysLoginLogDAO().insert(insertMap);
				}
				break;
			}
		}
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;
	}

	private OuterSysLoginLogDAO getOuterSysLoginLogDAO() {
		String daoName = OuterSysLoginLogDAOImpl.class.getName();
		return (OuterSysLoginLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	/**
	 * 根据会话ID，访问系统ID 判断是否已存在记录，如不存在则新增记录，否则放弃、
	 * @throws DataAccessException 
	 * */
	private boolean isOuterSysLogExit(Long sessionId, Long outerSysId) throws DataAccessException{
		Map qryMap = new HashMap();
		int logCount = 0;
		qryMap.put("sessionId", sessionId);
		qryMap.put("outerSysId", outerSysId);
		logCount = getOuterSysLoginLogDAO().selLogCount(qryMap);
		
		return logCount > 0 ? true : false;
	}
}
