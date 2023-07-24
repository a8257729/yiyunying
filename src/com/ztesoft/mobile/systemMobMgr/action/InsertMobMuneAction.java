package com.ztesoft.mobile.systemMobMgr.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;

public class InsertMobMuneAction extends AbstractAction{

	public String execute() throws Exception {

		//��ȡ����
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //ģ��Ŀ¼,��,ɾ,��
		if(type.equals("add")){
			getMobMunePrDAO().insert(param);
		}else if(type.equals("mod")){
			getMobMunePrDAO().update(param);
		}else if(type.equals("del")){
			getMobMunePrDAO().delete(param);
			
			Map mappram = new HashMap();
			mappram.put("privClassId", MapUtils.getString(param, "muneId"));
			mappram.put("privType", "1");           //1Ϊ�˵�
			getMobPrivDAO().delete2(mappram);          //ɾ��������ص�Ȩ��
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	private MobMunePrDAO getMobMunePrDAO() {
        String daoName = MobMunePrDAOImpl.class.getName();
        return (MobMunePrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
