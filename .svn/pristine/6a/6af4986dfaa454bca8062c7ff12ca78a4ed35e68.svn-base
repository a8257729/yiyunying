package com.ztesoft.mobile.outsystem.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.helper.DateHelper;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAO;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkAuditInfoDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkAuditInfoDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAOImpl;


public class ApkAuditManageAction extends AbstractAction{
	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");

		Long auditStaffId =  MapUtils.getLong(param, "auditStaffId");
		Date auditDate =  (Date) MapUtils.getObject(param, "auditDate");
		//System.out.println("auditDate="+auditDate);
		String isPass =  MapUtils.getString(param, "isPass");
		String auditComment =  MapUtils.getString(param, "auditComment");
		List paramList = (List) MapUtils.getObject(param, "valueArray");
        if (paramList != null && paramList.size() >0){
        	for ( int i = 0; i < paramList.size(); i++){
				Map _map = (Map) paramList.get(i);
				_map.put("createDate", new Date());
				_map.put("auditStaffId", auditStaffId);
				_map.put("auditDate",auditDate);
				_map.put("isPass", isPass);
				_map.put("auditComment", auditComment);
				
				Map stateMap = new HashMap();
				stateMap.put("funId", MapUtils.getLong(_map, "funId"));
				stateMap.put("funcRegStaffId", MapUtils.getLong(_map, "funcRegStaffId"));
				stateMap.put("muneId", MapUtils.getLong(_map, "muneId"));
				
				//funcStatus  1 已注册  2 审核不通过  3 审核通过  4 已更新
				if (isPass != null && isPass.equals("0")){
				   stateMap.put("funcStatus", "2");
				}else {
				   stateMap.put("funcStatus", "3");		
				   stateMap.put("isPass", "1");		
				}
				getOtherApkAuditInfoDAO().insert(_map);
				getMobileApkFunctionDAO().updateMenuId(stateMap);
				getAppRegMgrDAO().updateIsPass(stateMap);
            }  
        }
		
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
	private OtherApkAuditInfoDAO getOtherApkAuditInfoDAO() {
		String daoName = OtherApkAuditInfoDAOImpl.class.getName();
		return (OtherApkAuditInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileApkFunctionDAO getMobileApkFunctionDAO() {
		String daoName = MobileApkFunctionDAOImpl.class.getName();
		return (MobileApkFunctionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private AppRegMgrDAO getAppRegMgrDAO() {
		String daoName = AppRegMgrDAOImpl.class.getName();
		return (AppRegMgrDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
