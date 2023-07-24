package com.ztesoft.mobile.pn.action;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAO;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAOImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelPushMessageQueueAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");
		String pushMessageIdStr = request.getParameter("pushMessageId");
		Long pushMessageId = (StringUtils.isBlank(pushMessageIdStr) ? null : Long.valueOf(pushMessageIdStr));
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

		System.out.println("���á�SelPushMessageQueueAction��, �����ǣ�limit: " + limit + ", start: " + start);

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
					resultMap = this.getPushMessageQueueDAO().selByPagin(paramMap);
				} else if("N".equals(pagin)) {
					List list = this.getPushMessageQueueDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
				}

			} else if("ONE".equals(optype)) {
				Map dataMap = this.getPushMessageQueueDAO().selById(paramMap);
                if(null != dataMap && !dataMap.isEmpty()) {
					List list = new ArrayList();
					list.add(dataMap);

					resultMap.put("resultList", list);
					resultMap.put("totalSize", list.size());
					resultMap.put("totalRecords", list.size());
				}
			} else {
				throw new Exception("optType�������Ͳ���ȷ��" + optype);
			}

			int totalSize =MapUtils.getIntValue(resultMap, "totalSize", 0);

			if(totalSize != 0){
				int totalRecords = Integer.parseInt(MapUtils.getString(resultMap, "totalRecords"));
				jsonStr = JsonUtil.getExtGridJsonData((List)MapUtils.getObject(resultMap, "resultList"),totalRecords);
			}

			System.out.println("���jsonStr: " + jsonStr);

			response.getWriter().write(jsonStr);

		}catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private PushMessageQueueDAO getPushMessageQueueDAO() {
		String daoName = PushMessageQueueDAOImpl.class.getName();
		return (PushMessageQueueDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
