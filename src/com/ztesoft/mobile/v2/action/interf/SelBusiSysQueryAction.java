package com.ztesoft.mobile.v2.action.interf;

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
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileBusiSysDAOImpl;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 业务系统查询
 * @author heisonyee
 *
 */
public class SelBusiSysQueryAction implements BaseAction {

	public Object doAction(HttpServletRequest request,
			HttpServletResponse response) {

		String optype = request.getParameter("optype");
		String pagin = request.getParameter("pagin");

        System.out.println("调用【SelBusiSysQueryAction】, 参数是：optype: " + optype);

		String jsonStr = "{totalCount:0,Body:[]}";

		try{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			Map paramMap = new HashMap();
/*			paramMap.put("pageIndex", start);
			paramMap.put("pageSize", limit);
			paramMap.put("receiverName", receiverName);
            paramMap.put("senderName", senderName);
            paramMap.put("beginDate", beginDate);
            paramMap.put("endDate", endDate);
            paramMap.put("messageType", messageType);*/

			Map resultMap = new HashMap();
			if("ALL".equalsIgnoreCase(optype)) {
				if("N".equalsIgnoreCase(pagin)) {
					List list = this.getMobileBusiSysDAO().selAll(paramMap);
					if(null != list && !list.isEmpty()) {
						resultMap.put("resultList", list);
						resultMap.put("totalSize", list.size());
						resultMap.put("totalRecords", list.size());
					}
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

	private MobileBusiSysDAO getMobileBusiSysDAO() {
		String daoName = MobileBusiSysDAOImpl.class.getName();
		return (MobileBusiSysDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
}
