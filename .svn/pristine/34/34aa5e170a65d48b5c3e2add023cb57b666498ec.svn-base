package com.ztesoft.mobile.systemMonitor.action;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.extservice.BaseAction;
import com.ztesoft.mobile.common.helper.JsonUtil;
import com.ztesoft.mobile.common.helper.ValidateHelper;
import com.ztesoft.mobile.systemMonitor.dao.MobileFuncCallRecordDAO;
import com.ztesoft.mobile.systemMonitor.dao.MobileFuncCallRecordDAOImpl;

public class QrySystemMonitorTimeAndCountAction implements BaseAction
{

	public static final Logger logger = Logger.getLogger(QrySystemMonitorTimeAndCountAction.class);

	public Object doAction(HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		// 获取参数
		// Long staffId = new Long(request.getParameter("staffId"));
		// String userName = request.getParameter("userName");

		/*
		 * Long serviceId = null; if (request.getParameter("serviceId")!= null
		 * && !request.getParameter("serviceId").equals("")){ serviceId = new
		 * Long((String)request.getParameter("serviceId")); }
		 */
		int flag = request.getParameter("flag") == null ? -1 : Integer.parseInt((String) request.getParameter("flag"));
		int limit = request.getParameter("limit") == null ? 20 : Integer.parseInt((String) request.getParameter("limit"));
		int start = request.getParameter("start") == null ? 1 : Integer.parseInt((String) request.getParameter("start")) / limit + 1;
		String staffName = request.getParameter("staffName") == null ? "" : (String) request.getParameter("staffName");
		String username = request.getParameter("username") == null ? "" : (String) request.getParameter("username");
		String beginDate = request.getParameter("beginDate") == null ? "" : (String) request.getParameter("beginDate");
		String endDate = request.getParameter("endDate") == null ? "" : (String) request.getParameter("endDate");
		String serviceId = request.getParameter("serviceId") == null ? "" : (String) request.getParameter("serviceId");
		String jsonStr = "";
		try
		{
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			Map param = new HashMap();
			// param.put("staffId", staffId);
			param.put("username", username);
			param.put("serviceId", serviceId);
			param.put("staffName", staffName);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			if (beginDate != null && !beginDate.equals(""))
//			{
//				// logger.debug("=========beginDate: "+beginDate.substring(0,
//				// 10));
//				try
//				{
//					Date date = sdf.parse(beginDate.substring(0, 10) + " 00:00:00");
//					param.put("beginDate", date.getTime());
//				} catch (ParseException e)
//				{
//					e.printStackTrace();
//				}
//			}
//			if (endDate != null && !endDate.equals(""))
//			{
//				try
//				{
//					Date date = sdf.parse(endDate.substring(0, 10) + "23:59:59");
//					param.put("endDate", date.getTime());
//				} catch (ParseException e)
//				{
//					e.printStackTrace();
//				}
//			}
			
			try
			 {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
				if (beginDate != null && !beginDate.equals("")){
					  param.put("beginDate", dateFormatter.parse(beginDate).getTime());
					}
					if (endDate != null && !endDate.equals("")){
					  param.put("endDate", dateFormatter.parse(endDate).getTime());
				}
			} catch (ParseException e)
			{
				e.printStackTrace();
			}
				
				
			param.put("pageIndex", start);
			param.put("pageSize", limit);

			logger.debug("=========map: " + param);

			Map paginResultMap = new HashMap();

			if (flag == 1)
			{
				paginResultMap = getMobileFuncCallRecordDAO().selByConn(param);
			} else if (flag == 2)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryAvgTimeConsuming(param);
			} else if (flag == 3)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryserverAskRank10(param);
			} else if (flag == 4)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryInterAskRank10(param);
			} else if (flag == 5)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryServerRespondRank10(param);
			} else if (flag == 6)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qrySuccessRateCount10(param);
			} else if (flag == 7)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryTimeConsuming(param);
			} else if (flag == 8)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryAvgFlow(param);
			} else if (flag == 9)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryFlow(param);
			} else if (flag == 10)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryAskFlow10(param);
			} else if (flag == 11)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryRespondFlow10(param);
			} else if (flag == 12)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qryTotalFlow(param);
			} else if (flag == 13)
			{
				paginResultMap = getMobileFuncCallRecordDAO().qrySuccessRate(param);
			}

			//logger.debug("===========paginResultMap: " + paginResultMap);

			if (paginResultMap != null && paginResultMap.get("resultList") != null)
			{
				// 列表数据
				List list = (List) paginResultMap.get("resultList");
				List resultList = new ArrayList();

				if (list != null && list.size() > 0)
				{
					Iterator iterator = list.iterator();
					while (iterator.hasNext())
					{
						Map _map = (Map) iterator.next();
						if (flag == 7)
						{
							Long totalTimeconsuming = new Long(0);
							if (MapUtils.getLong(_map, "firstTimeconsuming") != null && MapUtils.getLong(_map, "firstTimeconsuming").longValue() > 0)
							{
								totalTimeconsuming = totalTimeconsuming + MapUtils.getLong(_map, "firstTimeconsuming");
							}
							if (MapUtils.getLong(_map, "secondTimeconsuming") != null && MapUtils.getLong(_map, "secondTimeconsuming").longValue() > 0)
							{
								totalTimeconsuming = totalTimeconsuming + MapUtils.getLong(_map, "secondTimeconsuming");
							}
							if (MapUtils.getLong(_map, "thirdTimeconsuming") != null && MapUtils.getLong(_map, "thirdTimeconsuming").longValue() > 0)
							{
								totalTimeconsuming = totalTimeconsuming + MapUtils.getLong(_map, "thirdTimeconsuming");
							}
							_map.put("totalTimeconsuming", totalTimeconsuming);
							_map.put("createTime", MapUtils.getString(_map, "createTime"));

							// _map.put("totalTimeconsuming",
							// MapUtils.getLong(_map,
							// "firstTimeconsuming")+MapUtils.getLong(_map,"secondTimeconsuming")+MapUtils.getLong(_map,"thirdTimeconsuming"));

						} else if (flag == 2)
						{
							_map.put("avgServerAskTime", MapUtils.getLong(_map, "avgServerAskTime"));
							_map.put("avgBusiAnswerTime", MapUtils.getLong(_map, "avgBusiAnswerTime"));
							_map.put("avgServerAnswerTime", MapUtils.getLong(_map, "avgServerAnswerTime"));
						} else if (flag == 8)
						{
							_map.put("avgInMessageSize", MapUtils.getLong(_map, "avgInMessageSize"));
							_map.put("avgOutMessageSize", MapUtils.getLong(_map, "avgOutMessageSize"));
						} else if (flag == 9)
						{
							Date inTimestamp = new Date(MapUtils.getLongValue(_map, "inTimestamp"));
							Date outTimestamp = new Date(MapUtils.getLongValue(_map, "outTimestamp"));
							_map.put("inTimestamp", sdf.format(inTimestamp));
							_map.put("outTimestamp", sdf.format(outTimestamp));
						} else if (flag == 12)
						{
							Long totalFlow = new Long(0);
							if (MapUtils.getLong(_map, "totalInMessageSize") != null && MapUtils.getLong(_map, "totalInMessageSize").longValue() > 0)
							{
								totalFlow = totalFlow + MapUtils.getLong(_map, "totalInMessageSize");
							}
							if (MapUtils.getLong(_map, "totalOutMessageSize") != null && MapUtils.getLong(_map, "totalOutMessageSize").longValue() > 0)
							{
								totalFlow = totalFlow + MapUtils.getLong(_map, "totalOutMessageSize");
							}
							_map.put("totalFlow", totalFlow);
						}
						resultList.add(_map);
					}
				}

				int totalSize = 0;
				// 如果使用分页的方法就取totalSize，否则显示查询的数据数
				if (flag == 1 || flag == 2 || flag == 7 || flag == 8 || flag == 9 || flag == 12 || flag == 13)
				{
					totalSize = Integer.parseInt(paginResultMap.get("totalSize") + "");

				} else
				{
					totalSize = list.size();
				}
				int totalRecords = 0;
				if (totalSize == 0)
				{
					jsonStr = "{totalCount:0,Body:[]}";
				} else
				{
					if (flag == 1 || flag == 2 || flag == 7 || flag == 8 || flag == 9 || flag == 12 || flag == 13)
					{
						totalRecords = Integer.parseInt(paginResultMap.get("totalRecords") + "");

					} else
					{
						totalRecords = list.size();
					}
					jsonStr = JsonUtil.getExtGridJsonData(resultList, totalRecords);
				}

			}
			//logger.debug("======jsonStr: " + jsonStr);
			// System.out.println("flag = "+ flag);
			// System.out.println("jsonStr = "+ jsonStr);
			response.getWriter().write(jsonStr);

		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (DataAccessException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static Date timestampToDate(Timestamp timeStamp)
	{
		return (ValidateHelper.validateNotNull(timeStamp)) ? (Date) timeStamp : null;
	}

	private MobileFuncCallRecordDAO getMobileFuncCallRecordDAO()
	{
		String daoName = MobileFuncCallRecordDAOImpl.class.getName();
		return (MobileFuncCallRecordDAO) BaseDAOFactory.getImplDAO(daoName);
	}
}
