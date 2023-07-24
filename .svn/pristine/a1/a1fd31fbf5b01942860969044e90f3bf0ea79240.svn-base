package com.ztesoft.mobile.systemMonitor.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileServerMonitorDAOImpl extends BaseDAOImpl implements MobileServerMonitorDAO {

	public Map selById(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public List selAll(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map selByConn(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();

		sqlStr.append(" select a.rest_serv_log_id AS restServLogId, a.staff_id AS staffId, b.username AS userName, b.staff_name AS staffName, c.rest_service_id AS restServiceId, c.serv_name AS servName, ");  
		sqlStr.append(" a.in_size AS inSize, a.out_size AS outSize, a.in_timestamp AS inTimestamp, a.out_timestamp AS outTimestamp ");
		sqlStr.append(" from mobile_rest_serv_log a, uos_staff b, mobile_rest_service c "); 		
		sqlStr.append("	where b.state=1 and a.rest_service_id=c.rest_service_id and a.staff_id=b.staff_id "); 

		if (MapUtils.getString(paramMap, "restServiceId") != null && !MapUtils.getString(paramMap, "restServiceId").equals("") ) {
			sqlStr.append(" AND a.rest_service_id = '").append(MapUtils.getLong(paramMap, "restServiceId")).append("'");
		}
		if (MapUtils.getString(paramMap, "staffName") != null && !MapUtils.getString(paramMap, "staffName").equals("") ) {
			sqlStr.append(" AND b.STAFF_NAME ='").append(MapUtils.getString(paramMap, "staffName")).append("'");
		}
		if (MapUtils.getString(paramMap, "username") != null && !MapUtils.getString(paramMap, "username").equals("") ) {
			sqlStr.append(" AND b.USERNAME ='").append(MapUtils.getString(paramMap, "username")).append("'");
		}
		if (MapUtils.getString(paramMap, "inTimestamp") != null && !MapUtils.getString(paramMap, "inTimestamp").equals("") ) {
            sqlStr.append("  AND a.in_timestamp >=").append(MapUtils.getString(paramMap, "inTimestamp"));
        }
		if (MapUtils.getString(paramMap, "outTimestamp") != null && !MapUtils.getString(paramMap, "outTimestamp").equals("") ) {
            sqlStr.append("  AND a.out_timestamp <=").append(MapUtils.getString(paramMap, "outTimestamp"));
        }
		
		sqlStr.append(" order by a.rest_serv_log_id desc"); 

		System.out.println("selByConn="+sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	  
	}

	public Map selRestServiceByConn(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" SELECT REST_SERVICE_ID AS restServiceId, SERV_NAME AS servName ");
		sqlStr.append(" FROM MOBILE_REST_SERVICE WHERE STATE = 1  ");
		if (MapUtils.getString(paramMap, "serviceId") != null && !MapUtils.getString(paramMap, "serviceId").equals("") ) {
			sqlStr.append(" AND REST_SERVICE_ID =").append(MapUtils.getLong(paramMap, "serviceId"));
		}
		
		sqlStr.append(" order by REST_SERVICE_ID asc ");
		System.out.println("selRestServiceByConn="+sqlStr.toString());

		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}

	public Map selAvgRestService(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select a.rest_service_id AS restServiceId, c.serv_name AS servName, count(a.rest_service_id) AS callNum from   ");
		sqlStr.append("  mobile_rest_serv_log a, uos_staff b, mobile_rest_service c  ");
		sqlStr.append("  where b.state = 1  and a.rest_service_id = c.rest_service_id  ");
		sqlStr.append("  and a.staff_id = b.staff_id group by a.rest_service_id, c.serv_name  ");
		sqlStr.append(" order by restServiceId ");
		System.out.println("selAvgRestService="+sqlStr.toString());
		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}
	
	public Map selAvgNumRestService(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append(" select  a.rest_service_id AS restServiceId, b.serv_name AS servName, count(rest_serv_log_id) AS countNum, sum(out_timestamp) AS sumOutTimestamp, sum(in_timestamp) AS sumInTimestamp from  ");
		sqlStr.append(" mobile_rest_serv_log a, mobile_rest_service b ");
		sqlStr.append(" where a.rest_service_id=b.rest_service_id and b.state=1 ");
		sqlStr.append(" group by a.rest_service_id, b.serv_name");
		System.out.println("selAvgNumRestService="+sqlStr.toString());
		
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	}

}
