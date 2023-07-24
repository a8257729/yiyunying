package com.ztesoft.mobile.outsystem.action;

import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAO;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAO;
import com.ztesoft.mobile.outsystem.dao.MobileApkFunctionDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAOImpl;

public class InsertOtherApkManagerAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		//获取参数
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
        //模型目录,增,删,改
		if(type.equals("add")){
			
			getOtherApkManagerDAO().insert(param);
		}else if(type.equals("mod")){
			getOtherApkManagerDAO().update(param);
		}else if(type.equals("del")){
			getOtherApkManagerDAO().delete(param);
		}else if(type.equals("addApkVer")){//add by li.guoyang ur85875 增加APK版本信息
			param.put("downloadTime", 0L);
			String apkCode=param.get("apkCode").toString();
			Long verId=getApkVerManagerDAO().insert(param);
			param.put("verId", verId);
			//复制前一个版本中的功能列表
			getApkFunctionDAO().copyLastedFuncByApkCode(param);
		}else if(type.equals("modApkVer")){//add by li.guoyang ur85875 修改APK版本信息
			/*Date d = new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			System.out.print(sdf.parse(sdf.format(d)));
			System.out.print("@@@@@@@@@@@@@@@@@@2"+sdf.parse(sdf.format(d)));
			param.put("stateDate",sdf.parse(sdf.format(d)));*/
			getApkVerManagerDAO().update(param);
		}else if(type.equals("downloadApkVer")){//add by li.guoyang ur85875 记录下载次数
			 getApkVerManagerDAO().addDownlaodTimes(param);
		}
		DedicatedActionContext.setResult(param);

		return SUCCESS;
	}

	private OtherApkManagerDAO getOtherApkManagerDAO() {
		String daoName = OtherApkManagerDAOImpl.class.getName();
		return (OtherApkManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	//add by li.guoyang UR85875
	private ApkVerManagerDAO getApkVerManagerDAO() {
		String daoName = ApkVerManagerDAOImpl.class.getName();
		return (ApkVerManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	//add by li.guoyang UR85875
	private MobileApkFunctionDAO getApkFunctionDAO() {
		String daoName = MobileApkFunctionDAOImpl.class.getName();
		return (MobileApkFunctionDAO) BaseDAOFactory.getImplDAO(daoName);
	}

}
