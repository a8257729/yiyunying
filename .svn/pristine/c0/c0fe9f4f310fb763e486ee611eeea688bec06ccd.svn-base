package com.ztesoft.mobile.ws.action;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.ws.dao.WsXmlManageDAO;
import com.ztesoft.mobile.ws.dao.WsXmlManageDAOImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelWsXmlManageAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

        Map paramMap = new HashMap();
        paramMap.put("pageIndex", start);
        paramMap.put("pageSize", limit);

        String mappingIdStr = request.getParameter("mappingId");
        if(StringUtils.isNotBlank(mappingIdStr)) {
            Long mappingId = Long.valueOf(mappingIdStr);
            paramMap.put("mappingId", mappingId);
        }

		System.out.println("调用【SelWsXmlManageAction】, 参数是：" + limit +"," + start + "," + optype + ", " +  pagin);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map resultMap = new HashMap();

			if("ALL".equals(optype)) {

				if("Y".equalsIgnoreCase(pagin)) {
					resultMap = this.getWsXmlManageDAO().selByPagin(paramMap);
				} else if("N".equalsIgnoreCase(pagin)) {
					List list = this.getWsXmlManageDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
				}

			} else if("ONE".equalsIgnoreCase(optype)) {
				Map dataMap = this.getWsXmlManageDAO().selById(paramMap);
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

    private WsXmlManageDAO getWsXmlManageDAO() {
        String daoName = WsXmlManageDAOImpl.class.getName();
        return (WsXmlManageDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
