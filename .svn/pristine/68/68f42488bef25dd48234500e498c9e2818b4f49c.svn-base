package com.ztesoft.mobile.v2.action.interf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAOImpl;

/**
 * 操作业务系统信息
 * @author heisonyee
 *
 */
public class OptBusiSysAction extends AbstractAction {

	public String execute() throws Exception {
		// 获取前台参数
		Map tempMap = DedicatedActionContext.getParams();
		Map paramMap = (HashMap) tempMap.get("parameter_1");

		System.out.println("调用【OptBusiSysAction】，参数是：" + paramMap);

		String optType = MapUtils.getString(paramMap,"optype");

		if("I".equals(optType)) {

			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			paramMap.put("buildTime", new Date());
			paramMap.put("updateTime", new Date());
			this.getMobileBusiSysDAO().insert(paramMap);	//新增

		} else if("U".equals(optType)) {

			paramMap.put("updateTime", new Date());
			paramMap.put("state", new  Integer(1));
			paramMap.put("stateDate", new Date());
			this.getMobileBusiSysDAO().update(paramMap);	//修改

		} else if("D".equals(optType)) {

			String pushMessageIds = MapUtils.getString(paramMap, "busiSysIds");
			String[] idArr = pushMessageIds.split(",");
			Map delMap = new HashMap();

			for(int i=0; i<idArr.length; i++) {
				delMap.put("busiSysId", Long.valueOf(idArr[i]));
				this.getMobileBusiSysDAO().delete(delMap);	//删除
			}

		} else {
			throw new RuntimeException("操作类型不正确optType：" + optType);
		}

		Map rtMap = new HashMap();
		rtMap.put("flag", "Y");
		DedicatedActionContext.setResult(rtMap);

		return SUCCESS;
	}

	private MobileBusiSysDAO getMobileBusiSysDAO() {
		String daoName = MobileBusiSysDAOImpl.class.getName();
		return (MobileBusiSysDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
