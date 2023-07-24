package com.ztesoft.mobile.systemMobMgr.action;


import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAO;
import com.ztesoft.mobile.outsystem.dao.ApkVerManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAO;
import com.ztesoft.mobile.outsystem.dao.MobileInterfaceManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.MobileOtherSysManagerDAO;
import com.ztesoft.mobile.outsystem.dao.MobileOtherSysManagerDAOImpl;
import com.ztesoft.mobile.outsystem.dao.MobilePhoneVersionDAO;
import com.ztesoft.mobile.outsystem.dao.MobilePhoneVersionDAOImpl;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAO;
import com.ztesoft.mobile.outsystem.dao.OtherApkManagerDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.AppRegMgrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAO;
import com.ztesoft.mobile.systemMobMgr.dao.FiledInfoDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAO;
import com.ztesoft.mobile.systemMobMgr.dao.JsonCreateDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobPrivDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobileSearchTabDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobileSearchTabDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MobileStatTransferDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobileStatTransferDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieFildNodeDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieFildNodeDAOImpl;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MoblieStatDisplayDAOImpl;

public class SelMobMuneAction extends AbstractAction {
	@Override
    public String execute() throws Exception {
        //获取参数
        Map paramMap = DedicatedActionContext.getParams();
        Map param = MapUtils.getMap(paramMap, "parameter_1");
        String type = MapUtils.getString(param, "type");

        if(type.equals("selIsExist")){      	
        	List list= getMobMunePrDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selDisplayInedx")){      	
        	Map _map= getMobMunePrDAO().selByDisplayInedx(param);
        	DedicatedActionContext.setResult(_map);
        }else if(type.equals("selMeuIsExit")){
        	List list= getJsonCreateDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selStatIsExit")){
        	List list = getStatDisplayDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        } else if(type.equals("selTransferIsExit")){
        	List list = getMobileStatTransferDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }
        else if(type.equals("selPrivIsExist")){   //权限代码是否存在
        	List list= getMobPrivDAO().selByName(param); 
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selButIsExit")){      //按钮是否存在
        	List list= getMobButtonRightDAO().selByName(param);  
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selFiledIsExit")){      //字段是否存在
        	List list= getFiledInfoDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selNodeIsExit")){      //节点是否存在
        	List list= getMoblieFildNodeDAO().selByName(param);  
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selSearchTabIsExit")){
        	List list = getMobileSearchTabDAO().selByName(param); 
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selInterfaceManagerIsExit")){
        	List list = getMobInterfaceMngDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selOtherApkIsExit")){
        	List list = getOtherApkManagerDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selAddressIsExit")){
        	List list = getSysAddressDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selPhoneVersionIsExit")){
        	List list = getMobPhoneVersionDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selApkIsVerExit")){//APK版本是否已存在 add by li.guoyang UR85875
        	List list =getApkVerManagerDAO().selApvkVerByCode(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selApkInfo")){
        	List List = getAppRegMgrDAO().selApkInfoByMuneId(param);
        	DedicatedActionContext.setResult(List);
        }else if(type.equals("selBySysCode")){
        	
        	List List = getOtherApkManagerDAO().selInfoBySysCode(param);
        	DedicatedActionContext.setResult(List);
        }
        return SUCCESS;

    }
	
	private MobilePhoneVersionDAO getMobPhoneVersionDAO() {
		String daoName = MobilePhoneVersionDAOImpl.class.getName();
		return (MobilePhoneVersionDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileOtherSysManagerDAO getSysAddressDAO() {
		String daoName = MobileOtherSysManagerDAOImpl.class.getName();
		return (MobileOtherSysManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private OtherApkManagerDAO getOtherApkManagerDAO() {
		String daoName = OtherApkManagerDAOImpl.class.getName();
		return (OtherApkManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileInterfaceManagerDAO getMobInterfaceMngDAO() {
		String daoName = MobileInterfaceManagerDAOImpl.class.getName();
		return  (MobileInterfaceManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileSearchTabDAO getMobileSearchTabDAO() {
		String daoName = MobileSearchTabDAOImpl.class.getName();
		return (MobileSearchTabDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MoblieStatDisplayDAO getStatDisplayDAO() {
		String daoName = MoblieStatDisplayDAOImpl.class.getName();
        return (MoblieStatDisplayDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobMunePrDAO getMobMunePrDAO() {
        String daoName = MobMunePrDAOImpl.class.getName();
        return (MobMunePrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private JsonCreateDAO getJsonCreateDAO() {
		String daoName = JsonCreateDAOImpl.class.getName();
		return (JsonCreateDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobPrivDAO getMobPrivDAO() {
		String daoName = MobPrivDAOImpl.class.getName();
		return (MobPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobButtonRightDAO getMobButtonRightDAO() {
		String daoName = MobButtonRightDAOImpl.class.getName();
		return (MobButtonRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private FiledInfoDAO getFiledInfoDAO() {
		String daoName = FiledInfoDAOImpl.class.getName();
		return (FiledInfoDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MoblieFildNodeDAO getMoblieFildNodeDAO() {
		String daoName = MoblieFildNodeDAOImpl.class.getName();
		return (MoblieFildNodeDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private MobileStatTransferDAO getMobileStatTransferDAO() {
		String daoName = MobileStatTransferDAOImpl.class.getName();
		return (MobileStatTransferDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	//add by li.guoyang UR85875
	private ApkVerManagerDAO getApkVerManagerDAO() {
		String daoName = ApkVerManagerDAOImpl.class.getName();
		return (ApkVerManagerDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	private AppRegMgrDAO getAppRegMgrDAO() {
        String daoName = AppRegMgrDAOImpl.class.getName();
        return (AppRegMgrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
