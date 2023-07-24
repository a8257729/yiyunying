package com.ztesoft.mobile.v2.action.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAO;
import com.ztesoft.mobile.v2.dao.common.MobileParamDAOImpl;


public class QryMobileParamAction implements BaseAction{
	
	public static String QRY_MOBILE_PARAM="qryMobileParam";
	public static String QRY_MOBILE_PARAM_BY_COND="qryMobileParamByCond";
	
	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String actionType = request.getParameter("actionType")==null?"0":request.getParameter("actionType");
		String gcode = request.getParameter("gcode")==null?"0":request.getParameter("gcode");
		String mname = request.getParameter("mname")==null?"0":request.getParameter("mname");
		String target = request.getParameter("target")==null?"0":request.getParameter("target");
		
		String pagin = request.getParameter("pagin");
		int limit = request.getParameter("limit")==null ? 10:Integer.parseInt((String)request.getParameter("limit"));
		int start = request.getParameter("start")==null ? 1:Integer.parseInt((String)request.getParameter("start"))/limit+1;

        Map paramMap = new HashMap();
        paramMap.put("pageIndex", start);
        paramMap.put("pageSize", limit);
        paramMap.put("gcode", gcode);
        paramMap.put("mname", mname);
        paramMap.put("target", target);

		System.out.println("调用【QryMobileParamAction】, 参数是：" + limit +"," + start + "," + actionType + ", " +  pagin);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map resultMap = new HashMap();

			if(QRY_MOBILE_PARAM.equals(actionType)) {

					List list = this.getMobileParamDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
    
			}else if(QRY_MOBILE_PARAM_BY_COND.equals(actionType)) {
				List list = this.getMobileParamDAO().selForParam(paramMap);
				if(null != list && !list.isEmpty()) {
					resultMap.put("resultList", list);
					resultMap.put("totalSize", list.size());
					resultMap.put("totalRecords", list.size());  
				}
			}else {
				throw new Exception("actionType操作类型不正确：" + actionType);
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

    private MobileParamDAO getMobileParamDAO() {
        String daoName = MobileParamDAOImpl.class.getName();
        return (MobileParamDAO) BaseDAOFactory.getImplDAO(daoName);
    }
}
