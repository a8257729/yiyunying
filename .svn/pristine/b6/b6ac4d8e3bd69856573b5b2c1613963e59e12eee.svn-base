package com.ztesoft.mobile.systemMobMgr.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAO;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAOImpl;

public class InsertFiledInfoAction extends AbstractAction{

	public String execute() throws Exception {

		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getFiledInfoDAO().insert(param);
		}else if(type.equals("mod")){
			getFiledInfoDAO().update(param);
		}else if(type.equals("del")){
			getFiledInfoDAO().delete(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;

	}
	
	 private FiledInfoDAO getFiledInfoDAO() {
	        String daoName = FiledInfoDAOImpl.class.getName();
	        return (FiledInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	    }
	
}
