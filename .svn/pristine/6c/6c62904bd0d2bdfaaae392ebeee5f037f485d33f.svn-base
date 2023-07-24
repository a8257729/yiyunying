package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.zterc.uos.oaas.vo.Job;
import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.xwork.AbstractAction;
import com.ztesoft.mobile.common.xwork.DedicatedActionContext;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobMunePrDAOImpl;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAO;
import com.ztesoft.mobile.v2.dao.app.MobileAppDAOImpl;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuDAOImpl;

public class MobMuneTreeAction extends AbstractAction implements BaseAction {
	public Object doAction(HttpServletRequest request, 
			HttpServletResponse response) {
		
		int moduleId = Integer.parseInt(request.getParameter("node"));
		String type = request.getParameter("type");
		System.out.println("OrgTreeAction orgId :" + moduleId);
		Map result = new HashMap();
		 
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			if(type.equals("model")){                    //模型目录树
			   result = getMobMunePrDAO().getSubMenusById(moduleId);
			}else if(type.equals("index")){
				result = getMobMunePrDAO().getSubIndexById(moduleId);
			}else {
				result.put("resultList", null);
			}
			jsonStr = JsonUtil.getJsonByList((List)result.get("resultList"));
			
			System.out.println("jsonStr:"+jsonStr);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		Map paramMap = DedicatedActionContext.getParams();
		Map param = MapUtils.getMap(paramMap, "parameter_1");
		String type = MapUtils.getString(param, "type");
		int moduleId =MapUtils.getIntValue(param, "node");
        Integer osType=MapUtils.getIntValue(param, "osType");
		Long staffId=new Long(MapUtils.getIntValue(param, "staffId"));
		Long jobId =MapUtils.getLong(param, "jobId");
		Long specialJobId=MapUtils.getLong(param, "specialJobId");
		Long menuId=MapUtils.getLong(param, "node");
		Map result = new HashMap();
		List list=new ArrayList();
		if ("findMune".equals(type)) {
			String modelType = MapUtils.getString(param, "modelType");
			if (modelType.equals("model")) { // 模型目录树
				//result = getMobMunePrDAO().getSubMenusById(moduleId);//以前的应用列表
				list=getMobileMenuDAO().queryMenuCatalog(staffId,jobId,specialJobId,osType);         
			} else if (modelType.equals("index")) {
				//result = getMobMunePrDAO().getSubIndexById(moduleId);
				list=getMobileMenuDAO().querySubMenu(menuId,staffId,jobId,specialJobId,osType);  
			} else {
				result.put("resultList", null);
			}
			DedicatedActionContext.setResult(list);
		}else if ("rank".equals(type)) {
		//	result=getMobMunePrDAO().getMenuRank();
			list=getMobileAppDAO().getAppOrderCount(null);
			DedicatedActionContext.setResult(list);
		}
		return null;
	}
	
	
	private MobMunePrDAO getMobMunePrDAO() {
        String daoName = MobMunePrDAOImpl.class.getName();
        return (MobMunePrDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileMenuDAO getMobileMenuDAO() {
        String daoName = MobileMenuDAOImpl.class.getName();
        return (MobileMenuDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	private MobileAppDAO getMobileAppDAO() {
        String daoName = MobileAppDAOImpl.class.getName();
        return (MobileAppDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
