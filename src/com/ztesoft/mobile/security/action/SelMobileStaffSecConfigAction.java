package com.ztesoft.mobile.security.action;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.security.dao.MobileStaffSecConfigDAO;
import com.ztesoft.mobile.security.dao.MobileStaffSecConfigDAOImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class SelMobileStaffSecConfigAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

        String username = request.getParameter("username");
        String staffName = request.getParameter("staffName");
        String mobile = request.getParameter("mobile");
        String imsiCode = request.getParameter("imsiCode");

        String bindingStatusStr = request.getParameter("bindingStatus");
        Integer bindingStatus = null;
        if(StringUtils.isNotBlank(bindingStatusStr)) {
            bindingStatus = Integer.valueOf(bindingStatusStr);
        }
        String securityTypeStr = request.getParameter("securityType");
        Integer securityType = null;
        if(StringUtils.isNotBlank(securityTypeStr)) {
            securityType = Integer.valueOf(securityTypeStr);
        }

        Map paramMap = new HashMap();
        paramMap.put("staffName", staffName);
        paramMap.put("username", username);
        paramMap.put("mobile", mobile);
        paramMap.put("securityType", securityType);
        paramMap.put("bindingStatus", bindingStatus);
        paramMap.put("imsiCode", imsiCode);
        //
        paramMap.put("pageIndex", start);
        paramMap.put("pageSize", limit);

		System.out.println("调用【SelMobileStaffSecConfigAction】, 参数是：" + limit +"," + start + "," + optype + ", " +  pagin);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map resultMap = Collections.EMPTY_MAP;
            //
            if("ALL".equals(optype)) {
				if("Y".equalsIgnoreCase(pagin)) {
					resultMap = this.getMobileStaffSecConfigDAO().selByPagin(paramMap);
				} else if("N".equalsIgnoreCase(pagin)) {
					List list = this.getMobileStaffSecConfigDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
				}
			} else if("ONE".equalsIgnoreCase(optype)) {
				Map dataMap = this.getMobileStaffSecConfigDAO().selById(paramMap);
				if(null != dataMap && !dataMap.isEmpty()) {
					List list = new ArrayList();
					list.add(dataMap);
                    //
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

    private MobileStaffSecConfigDAO getMobileStaffSecConfigDAO() {
        String daoName = MobileStaffSecConfigDAOImpl.class.getName();
        return (MobileStaffSecConfigDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
