package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAO;
import com.ztesoft.mobile.outsystem.dao.OssmhMappingDAOImpl;


public class InsertStaffMappingAction extends AbstractAction{
	
	public String execute() throws Exception {
       /**
        * �����ʺ�ӳ����Ϣ�������ṩ���������б����ݣ���ɾ�����п�����ݣ��������Ӳ���
        */
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		//ɾ��������������
		getOssmhMappingDAO().delete(param);
		if(!param.get("staffMapBaseIds").toString().equals("")){
			String [] staffMapBaseIds = param.get("staffMapBaseIds").toString().split(",");
			for(int i =0;i<staffMapBaseIds.length;i++){
				param.put("staffMapBaseId" , new Long(staffMapBaseIds[i]));
				getOssmhMappingDAO().insert(param);
			}
		}
		
		
		DedicatedActionContext.setResult(SUCCESS);
		return SUCCESS;	
	}
	private OssmhMappingDAO getOssmhMappingDAO() {
		String daoName = OssmhMappingDAOImpl.class.getName();
		return (OssmhMappingDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
