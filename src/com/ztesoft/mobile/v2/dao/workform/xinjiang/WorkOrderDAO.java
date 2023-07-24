package com.ztesoft.mobile.v2.dao.workform.xinjiang;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface WorkOrderDAO extends BaseDAO {
	
	public List selOrgList(Long areaId, Long orgId) throws DataAccessException; 
	
	public List selOrgList(Long areaId, Long orgId,Boolean isRoot) throws DataAccessException; 

	public List selJobList(Long areaId, Long orgId) throws DataAccessException; 
	
	public List selStaffList(Long areaId, Long jobId) throws DataAccessException; 
	
	public List selStaffList(Long orgId) throws DataAccessException; 
	
	public Map<String,Object> checkWorkOrderReply(String wkOrderId,String hguSN,String stbMac) throws DataAccessException;

	public Map<String,Object> checkFaultWorkOrderReply(String yhym,String workOrderId) throws DataAccessException;
}
