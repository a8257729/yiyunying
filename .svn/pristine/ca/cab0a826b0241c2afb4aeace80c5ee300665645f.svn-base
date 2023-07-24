package com.ztesoft.mobile.pn.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAO;
import com.ztesoft.mobile.pn.dao.PushMessageQueueDAOImpl;

public class SelPushMessageQueryAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

        /* 查询条件 */
        String messageTypeStr = request.getParameter("messageType");
        Integer messageType = null;
        if(StringUtils.isNotBlank(messageTypeStr)) {
            messageType =  Integer.valueOf(messageTypeStr);
        }

        String receiverName = request.getParameter("senderName")==null? "":(String)request.getParameter("receiverName");
        String senderName = request.getParameter("senderName")==null? "":(String)request.getParameter("senderName");

        String beginDate  = request.getParameter("beginDate");
        if(StringUtils.isNotBlank(beginDate)) {
            beginDate = beginDate.replace("T", " ");
        }

        String endDate  = request.getParameter("endDate");
        if(StringUtils.isNotBlank(endDate)) {
            endDate = endDate.replace("T", " ");
        }

        System.out.println("调用【SelPushMessageQueryAction】, 参数是：limit: " + limit + ", start: " + start);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map paramMap = new HashMap();
			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);
			paramMap.put("receiverName", receiverName);
            paramMap.put("senderName", senderName);
            paramMap.put("beginDate", beginDate);
            paramMap.put("endDate", endDate);
            paramMap.put("messageType", messageType);

			Map resultMap = new HashMap();

			if("ALL".equalsIgnoreCase(optype)) {
			    resultMap = this.getPushMessageQueueDAO().selPushMessageQueryByPagin(paramMap);
			} else {
				throw new Exception("optType操作类型不正确：" + optype);
			}

			int totalSize =MapUtils.getIntValue(resultMap, "totalSize", 0);

			if(totalSize != 0) {
				int totalRecords = Integer.parseInt(MapUtils.getString(resultMap, "totalRecords"));
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

	private PushMessageQueueDAO getPushMessageQueueDAO() {
		String daoName = PushMessageQueueDAOImpl.class.getName();
		return (PushMessageQueueDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
