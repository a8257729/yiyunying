package com.ztesoft.mobile.v2.action.app;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class SelAppMenuAction extends AbstractAction {
    public String execute() throws Exception {

        Map paramMap = DedicatedActionContext.getParams();
        Map param = MapUtils.getMap(paramMap, "parameter_1");
        String type = MapUtils.getString(param, "type");

        if(type.equals("selIsExist")){      	
        	List list= getMobileMenuDAO().selByName(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selDisplayInedx")){      	
        	Map  map= getMobileMenuDAO().selByDisplayInedx(param);
        	DedicatedActionContext.setResult(map);
        }else if(type.equals("selParam")){      	
        	List list= getMobileParamDAO().selForParam(param);
        	DedicatedActionContext.setResult(list);
        }else if(type.equals("selAppInfo")){      	
        	Map  map= getMobileAppDAO().selById(param);
        	DedicatedActionContext.setResult(map);
        }else if(type.equals("selMaxIndex")){
        	Map map=getMobileMenuDAO().selByMenuIndex(param);
        	
        	System.out.println(map +"  "+param.get("parentId"));
        	DedicatedActionContext.setResult(map);
        }else if(type.equals("selMenuAppClass")){
        	
        	List list= getMobileMenuDAO().getMenuAppClass(param);
        	System.out.println("list ="+list);
        	DedicatedActionContext.setResult(list);
        }
        
        return SUCCESS;

    }
	
    private MobileMenuDAO getMobileMenuDAO() {
        String daoName = MobileMenuDAOImpl.class.getName();
        return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }
    private MobileAppDAO getMobileAppDAO() {
        String daoName = MobileAppDAOImpl.class.getName();
        return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
