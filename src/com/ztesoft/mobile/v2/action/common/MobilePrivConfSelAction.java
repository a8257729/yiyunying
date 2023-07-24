package com.ztesoft.mobile.v2.action.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuPrivDAO;
import com.ztesoft.mobile.v2.dao.menu.MobileMenuPrivDAOImpl;

public class MobilePrivConfSelAction implements BaseAction{
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		int jobId = request.getParameter("jobId")==null? -1:Integer.parseInt(request.getParameter("jobId"));
		int specialJobId = request.getParameter("specialJobId")==null? -1:Integer.parseInt(request.getParameter("specialJobId"));
		int _jobId = request.getParameter("_jobId")==null? -1:Integer.parseInt(request.getParameter("_jobId"));
		int _defaultJobId = request.getParameter("_defaultJobId")==null? -1:Integer.parseInt(request.getParameter("_defaultJobId"));
		String staffId = request.getParameter("staffId");
		String methodName = request.getParameter("methodName");
		String jsonStr = "";

		//response.setContentType("text/html;charset=UTF-8");
		System.out.println("MobilePrivConfSelAction excute........");
		int moduleId = Integer.parseInt(request.getParameter("node"));
		String type = request.getParameter("type");
		System.out.println("OrgTreeAction orgId :" + moduleId+"---type="+type);
		System.out.println("methodName="+methodName+"--moduleId="+moduleId+"--jobId:"+jobId +"--specialJobId:"+specialJobId+"--_jobId:"+_jobId+"--_defaultJobId:"+_defaultJobId);
		Map result = new HashMap();
		Map butresult = new HashMap();

		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			if(type.equals("allpriv")){                   //可选择权限
				if(methodName.equals("1")){
					result = getMobMunePrDAO().getAllSubPrivData(moduleId, -1, -1, _jobId,  _defaultJobId, staffId);   //菜单
					//butresult = getMobMunePrDAO().getAllButPrivData(moduleId, -1, -1, _jobId,  _defaultJobId, staffId); //按钮
				}else if(methodName.equals("2")){
					result = getMobMunePrDAO().getAllSubPrivData(moduleId, jobId, -1, _jobId,  _defaultJobId, staffId);
					//butresult = getMobMunePrDAO().getAllButPrivData(moduleId, jobId, -1, _jobId,  _defaultJobId, staffId);
				}else if(methodName.equals("3")){
					result = getMobMunePrDAO().getAllSubPrivData(moduleId, -1, specialJobId, _jobId,  _defaultJobId, staffId);
					//butresult = getMobMunePrDAO().getAllButPrivData(moduleId, -1, specialJobId, _jobId,  _defaultJobId, staffId);
				}else {
					result = getMobMunePrDAO().getAllSubPrivData(moduleId, jobId, specialJobId, _jobId,  _defaultJobId, staffId);
					//butresult = getMobMunePrDAO().getAllButPrivData(moduleId, jobId, specialJobId, _jobId,  _defaultJobId, staffId);
				}				
			}
			if(type.equals("haspriv")) {                                    //已有权限
				result = getMobMunePrDAO().getAllHasPrivData(_jobId, _defaultJobId);
				//butresult = getMobMunePrDAO().getHasButPrivData(_jobId, _defaultJobId);

			}

			List dlist = (List)result.get("resultList");
			//List blist = (List)butresult.get("resultList");
			List nlist = new ArrayList();
			if(dlist.size()>0){
				Map mapFunc = new HashMap();
				mapFunc.put("id", -10);
				mapFunc.put("text", "菜单");
				mapFunc.put("leaf", false);
				mapFunc.put("children", (List)result.get("resultList"));
				nlist.add(mapFunc);
			}
//			if(blist.size()>0){
//				Map mapFunc = new HashMap();
//				mapFunc.put("id", -11);
//				mapFunc.put("text", "操作环节");
//				mapFunc.put("leaf", false);
//				mapFunc.put("children", (List)butresult.get("resultList"));
//				nlist.add(mapFunc);
//			}
			jsonStr = JsonUtil.getJsonByList(nlist);
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

	private MobileMenuPrivDAO getMobMunePrDAO() {
		String daoName = MobileMenuPrivDAOImpl.class.getName();
		return (MobileMenuPrivDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
