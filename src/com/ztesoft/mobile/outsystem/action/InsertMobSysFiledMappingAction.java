package com.ztesoft.mobile.outsystem.action;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.android.service.CommonXmlToJson;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.MobSysFiledMappingDAO;
import com.ztesoft.mobile.outsystem.dao.MobSysFiledMappingDAOImpl;

public class InsertMobSysFiledMappingAction extends AbstractAction{

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			getMobSysFiledMappingDAO().insert(param);
		}else if(type.equals("mod")){
			getMobSysFiledMappingDAO().update(param);
		}else if(type.equals("del")){
			getMobSysFiledMappingDAO().delete(param);
		}else if(type.equals("updateTrsfRs")){
			//////////////////////////////////////////////////////////////////////////////////////
			//updateTrsfRs(param)至少需要传入filedMappingId和transferResult,transferBefore
			///////////////////////////////////////////////////////////////////////////////////////
			getMobSysFiledMappingDAO().updateTrsfRs(param);
		}else if(type.equals("debug")){
			///////////////////////////////xml转换json
			String[] resultArr = CommonXmlToJson.XmlToJson(param);
			param.put("transferResult", resultArr[0]);
			param.put("transferBefore", resultArr[1]);
			param.put("result", "success");
		}else if(type.equals("sel")){
			param.put("pageIndex", 1);
			param.put("pageSize", 20);
			param = getMobSysFiledMappingDAO().selById(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}
	
	private MobSysFiledMappingDAO getMobSysFiledMappingDAO() {
		String daoName = MobSysFiledMappingDAOImpl.class.getName();
		return (MobSysFiledMappingDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
