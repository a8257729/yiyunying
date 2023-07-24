package com.ztesoft.mobile.v2.action.app;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileFrameAppDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class SelMobileFrameAppAction extends AbstractAction {
    public String execute() throws Exception {

        Map paramMap = DedicatedActionContext.getParams();
        Map param = MapUtils.getMap(paramMap, "parameter_1");
        String type = MapUtils.getString(param, "type");

        if(type.equals("selVersionIsExit")){
        	List list =getMobileFrameAppDAO().selVersionIsExist2(param);
        	DedicatedActionContext.setResult(list);
        } else if (type.equals("selCurrentVersion")) { 
        	Map map = getMobileFrameAppDAO().getLatestVersion(param);
        	DedicatedActionContext.setResult(map);
        }
        
        return SUCCESS;

    }
    
    private MobileFrameAppDAO getMobileFrameAppDAO() {
    	String daoName = MobileFrameAppDAOImpl.class.getName();
        return (MobileFrameAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
}
