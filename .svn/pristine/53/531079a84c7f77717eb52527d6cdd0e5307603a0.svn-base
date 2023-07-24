package com.ztesoft.mobile.pn.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.pn.dao.PushMessageDAO;
import com.ztesoft.mobile.pn.dao.PushMessageDAOImpl;

public class SelPushMessageAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

		System.out.println("调用【SelPushMessageAction】, 参数是：" + limit +"," + start + "," + optype + ", " +  pagin);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map paramMap = new HashMap();
			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);

			Map resultMap = new HashMap();

			if("ALL".equals(optype)) {

				if("Y".equals(pagin)) {
					resultMap = this.getPushMessageDAO().selByPagin(paramMap);
				} else if("N".equals(pagin)) {
					List list = this.getPushMessageDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
				}

			} else if("ONE".equals(optype)) {
				Map dataMap = this.getPushMessageDAO().selById(paramMap);
				if(null != dataMap && !dataMap.isEmpty()) {
					List list = new ArrayList();
					list.add(dataMap);

					resultMap.put("resultList", list);
					resultMap.put("totalSize", list.size());
					resultMap.put("totalRecords", list.size());
				}
			} else {
				throw new Exception("optType操作类型不正确：" + optype);
			}

			int totalSize =MapUtils.getIntValue(resultMap, "totalSize", 0);

			if(totalSize != 0){
				int totalRecords = Integer.parseInt(MapUtils.getString(resultMap, "totalRecords"));
				//System.out.println("xxx----> " + totalRecords);
				jsonStr = JsonUtil.getExtGridJsonData((List)MapUtils.getObject(resultMap, "resultList"),totalRecords);
			}

			System.out.println("输出jsonStr: " + jsonStr);

			response.getWriter().write(jsonStr);

		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private PushMessageDAO getPushMessageDAO() {
		String daoName = PushMessageDAOImpl.class.getName();
		return (PushMessageDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
