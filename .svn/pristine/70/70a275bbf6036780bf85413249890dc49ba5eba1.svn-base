package com.ztesoft.mobile.systemMobMgr.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobRolePrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobRolePrivDAOImpl;

public class InsertRolePrivAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");

		String strpriv = MapUtils.getString(param, "strpriv");
		ArrayList priv = (ArrayList)param.get("priv");
		int roleId = MapUtils.getIntValue(param, "roleId");

	
		if(strpriv.equals("")){
			getMobRolePrivDAO().removeJobPrivs(roleId);
		}else {
			List list = getMobRolePrivDAO().getPrivCode(strpriv);  

			String[] privs = new String[list.size()];
			for(int i=0 ;i< list.size();i++){
				Map map = (Map)list.get(i);
				privs[i] = map.get("privCode")+"";
			}
		
			if(type.equals("rolePrivAdd")){ 
				getMobRolePrivDAO().updateRolePrivs(roleId,privs);
			}else{
			}
		}

		return SUCCESS;

	}
	

	private MobRolePrivDAO getMobRolePrivDAO() {
		String daoName = MobRolePrivDAOImpl.class.getName();
		return (MobRolePrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
