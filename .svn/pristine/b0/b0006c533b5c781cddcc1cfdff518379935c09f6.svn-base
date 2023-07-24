package com.ztesoft.mobile.systemMobMgr.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAO;
import com.ztesoft.mobile.systemMobMgr.dao.MobButtonRightDAOImpl;

public class SelButtonRightAction implements BaseAction{
	

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {
		//获取参数
		String moduleId = request.getParameter("formId");
		int limit = request.getParameter("limit")==null?20:Integer.parseInt((String)request.getParameter("limit")); 
		int start = request.getParameter("start")==null?1:Integer.parseInt((String)request.getParameter("start"))/limit+1;
		String jsonStr = "";
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			param.put("formId", moduleId);

			param.put("pageIndex", start);
			param.put("pageSize", limit);

			Map paginResultMap = getMobButtonRightDAO().selById(param);

			//列表数据
			List list = (List) paginResultMap.get("resultList");
			List resultList = new ArrayList();

			if (list != null && list.size() > 0) {
				Iterator iterator = list.iterator();
				while (iterator.hasNext()) {
					Map _map = (Map) iterator.next();
					_map.put("isShowName", MapUtils.getString(_map, "isShow").equals("1")?"是":"否");
					_map.put("isBottomName", MapUtils.getString(_map, "isBottom").equals("1")?"是":"否");
					_map.put("orientationName", MapUtils.getString(_map, "orientation").equals("1")?"垂直":"水平");
					
					String buttonType = MapUtils.getString(_map, "buttonType");
					if(buttonType.equals("1")){
						_map.put("buttonTypeName","查询");
					}else if(buttonType.equals("2")){
						_map.put("buttonTypeName","查看");
					}else if(buttonType.equals("3")){
						_map.put("buttonTypeName","增加");
					}else if(buttonType.equals("4")){
						_map.put("buttonTypeName","修改");
					}else if(buttonType.equals("5")){
						_map.put("buttonTypeName","删除");
					}else if(buttonType.equals("6")){
						_map.put("buttonTypeName","验证");
					}else if(buttonType.equals("7")){
						_map.put("buttonTypeName","提交");
					}
					resultList.add(_map);
				}
			}

			int totalSize = Integer.parseInt(paginResultMap.get("totalSize")+"");

			if(totalSize == 0){
				jsonStr = "{totalCount:0,Body:[]}";
			}else{
				int totalRecords = Integer.parseInt(paginResultMap.get("totalRecords")+"");
				jsonStr = JsonUtil.getExtGridJsonData(resultList,totalRecords);
			}

			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String format(String DateTime, String format) {
		String result = "";
		if(DateTime !=null && DateTime.length()>0){
			result = DateTime.substring(0, 19).replaceAll("-", format);
		}
		return result;
	}
	private MobButtonRightDAO getMobButtonRightDAO() {
		String daoName = MobButtonRightDAOImpl.class.getName();
		return (MobButtonRightDAO) BaseDAOFactory.getImplDAO(daoName);
	}


}
