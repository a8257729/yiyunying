package com.ztesoft.mobile.systemMobMgr.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobStaffJObRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobStaffJObRightDAOImpl;

public class InsertPrivAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");

		String strpriv = MapUtils.getString(param, "strpriv");
		ArrayList grantPriv = (ArrayList)param.get("grantPriv");
		ArrayList priv = (ArrayList)param.get("priv");
		int _defaultJobId = MapUtils.getIntValue(param, "_defaultJobId");
		ArrayList roleId = (ArrayList)param.get("roleIds");

		System.out.println("strpriv--->>  "+strpriv +"---type="+type+"_defaultJobId ="+_defaultJobId);
		if(strpriv.equals("")){
			getMobStaffJObRightDAO().removeJobPrivs(_defaultJobId);
		}else if(type.equals("rolePrivAdd")){
			String[] str = {"-1"};
			String[] roleIds = new String[roleId.size()];
			for(int i=0 ;i< roleId.size();i++){
				roleIds[i] = roleId.get(i).toString();
				System.out.println("roleIds: "+roleIds[i]);
			}
			getMobStaffJObRightDAO().updateJobPrivs(roleIds,_defaultJobId,str,str);
		}else {
			List list = getMobStaffJObRightDAO().getPrivCode(strpriv);  

			String[] privs = new String[list.size()];
			for(int i=0 ;i< list.size();i++){
				Map map = (Map)list.get(i);
				privs[i] = map.get("privCode")+"";
				System.out.println("privs: "+privs[i]);
			}
			String[] grantPrivs = new String[grantPriv.size()];
			for(int i=0 ;i< grantPriv.size();i++){
				grantPrivs[i] = grantPriv.get(i).toString();
				System.out.println("grantPrivs: "+grantPrivs[i]);
			}

			if(type.equals("staffPrivAdd")){ 
				String[] str = {"-1"};
				getMobStaffJObRightDAO().updateJobPrivs(str,_defaultJobId,grantPrivs,privs);
			}
		}

		return SUCCESS;

	}
	

	private MobStaffJObRightDAO getMobStaffJObRightDAO() {
		String daoName = MobStaffJObRightDAOImpl.class.getName();
		return (MobStaffJObRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
