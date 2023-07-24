package com.ztesoft.mobile.v2.service.interf;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ztesoft.mobile.common.dao.BaseDAOFactory;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.Constants;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAO;
import com.ztesoft.mobile.v2.dao.common.MobileCommonDAOImpl;
import com.ztesoft.mobile.v2.dao.common.MobileExceptionDAO;
import com.ztesoft.mobile.v2.dao.common.MobileExceptionDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServLogDAOImpl;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServiceDAO;
import com.ztesoft.mobile.v2.dao.interf.MobileRestServiceDAOImpl;
import com.ztesoft.mobile.v2.entity.common.MobileExceptionLog;
import com.ztesoft.mobile.v2.entity.common.StaffControls;
import com.ztesoft.mobile.v2.entity.interf.MobileRestServLog;
import com.ztesoft.mobile.v2.entity.interf.MobileRestService;

@Service("mobileRestServService")
public class MobileRestServServiceImpl implements MobileRestServService {
	
	private MobileRestServiceDAO getMobileRestServiceDAO() {
        String daoName = MobileRestServiceDAOImpl.class.getName();
        return (MobileRestServiceDAO) BaseDAOFactory.getImplDAO(daoName);
    }
	
	private MobileExceptionDAO getMobileExceptionDAO() {
		String daoName = MobileExceptionDAOImpl.class.getName();
		return (MobileExceptionDAO) BaseDAOFactory.getImplDAO(daoName);
	}

	private MobileRestServLogDAO getMobileRestServLogDAO() {
		String daoName = MobileRestServLogDAOImpl.class.getName();
		return (MobileRestServLogDAO) BaseDAOFactory.getImplDAO(daoName);
	}
	
    private MobileCommonDAO getMobileCommonDAO() {
        String daoName = MobileCommonDAOImpl.class.getName();
        return (MobileCommonDAO) BaseDAOFactory.getImplDAO(daoName);
    }

	@Transactional(rollbackFor={Throwable.class})
	public void writeRestServLog(MobileRestServLog restServLog) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(MobileRestServLog.REST_SERVICE_ID_NODE, restServLog.getRestServiceId());
		paramMap.put(MobileRestServLog.STAFF_ID_NODE, restServLog.getStaffId());
		paramMap.put(MobileRestServLog.SERV_LOG_TYPE_NODE, restServLog.getServLogType());
		paramMap.put(MobileRestServLog.IN_TIMESTAMP_NODE, restServLog.getInTimestamp());
		paramMap.put(MobileRestServLog.OUT_TIMESTAMP_NODE, restServLog.getOutTimestamp());
		paramMap.put(MobileRestServLog.IN_CONTENT_NODE, restServLog.getInContent()==null?"":restServLog.getInContent());
		paramMap.put(MobileRestServLog.OUT_CONTENT_NODE, restServLog.getOutContent()==null?"":restServLog.getInContent());
		paramMap.put(MobileRestServLog.IN_SIZE_NODE, restServLog.getInSize());
		paramMap.put(MobileRestServLog.OUT_SIZE_NODE, restServLog.getOutSize());
		
		paramMap.put(MobileRestServLog.WORK_ORDER_ID_NODE, restServLog.getWorkOrderId());
		paramMap.put(MobileRestServLog.STYLE_NODE, restServLog.getStyle());
		paramMap.put(MobileRestServLog.RESULT_CODE_NODE, restServLog.getResultCode());

    	System.out.println("输出++++++"+restServLog.getInContent());
    	System.out.println("输出++++++"+restServLog.getOutContent());
    	
		this.getMobileRestServLogDAO().insert(paramMap);
	}
	
	@Transactional(rollbackFor={Throwable.class})
	public void writeExceptionLog(Long restServiceId, String excepName,
			String excepMsg) throws Exception {
		
		String tExcepMsg = null;
		if(StringUtils.isNotBlank(excepMsg)) {	//避免超过数据库4000个字符串的限制
			if(excepMsg.length() >= 4000) {
				tExcepMsg = excepMsg.substring(0, 3990);
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put(MobileExceptionLog.REST_SERVICE_ID_NODE, restServiceId);
		paramMap.put(MobileExceptionLog.EXCEPTION_NAME_NODE, excepName);
		paramMap.put(MobileExceptionLog.EXCEPTION_MSG_NODE, tExcepMsg);
		paramMap.put(MobileExceptionLog.EXCEPTION_TIME_NODE, new Date());
		
		this.getMobileExceptionDAO().insert(paramMap);
	}
	
	@Cacheable(value = {Constants.CacheKey.T03MIN_CACHE}, 
			key=Constants.CacheCode.NORMAL_REST_SERV_LIST)
	public List<MobileRestService> getNormalRestServList() throws Exception {
		return this.getRestServList(Constants.RestServStatus.NORMAL);
	}
	
	@Cacheable(value={Constants.CacheKey.T03MIN_CACHE}, 
			key=Constants.CacheCode.FORBIDDEN_REST_SERV_LIST)
	public List<MobileRestService> getForbiddenlRestServList() throws Exception {
		return this.getRestServList(Constants.RestServStatus.FORBIDDEN);
	}
	
	@Cacheable(value = {Constants.CacheKey.T03MIN_CACHE},
			key=Constants.CacheCode.REST_SERV_LIST)
	public List<MobileRestService> getRestServList() throws Exception {
		return getRestServList(null);
	}
	
	public StaffControls getStaffControls(Long staffId) throws Exception {
		if(null == staffId) return null;
		return this.getMobileCommonDAO().getStaffControls(staffId);
	}
	
	@Cacheable(value = {Constants.CacheKey.T03MIN_CACHE},
			key="#restServStatus + 'OF_REST_SERV_LIST'")
	public List<MobileRestService> getRestServList(Integer restServStatus) throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		if(null != restServStatus) {
			paramMap.put(MobileRestService.SERV_STATUS_NODE, restServStatus);
		}
		List<Map> list = (List<Map>) this.getMobileRestServiceDAO().selAll(paramMap);
		
		List<MobileRestService> resultList = new ArrayList<MobileRestService>();
		if(null != list && 0 != list.size()) {
			for(int i=0; i<list.size(); i++) {
				MobileRestService item = new MobileRestService();
				Map map = list.get(i);
				Long restServiceId = MapUtils.getLong(map, MobileRestService.REST_SERVICE_ID_NODE);
				String servName = MapUtils.getString(map, MobileRestService.SERV_NAME_NODE);
				String servAddr = MapUtils.getString(map, MobileRestService.SERV_ADDR_NODE);
				Integer servStatus = MapUtils.getInteger(map, MobileRestService.SERV_STATUS_NODE);
				
				item.setRestServiceId(restServiceId);
				item.setServName(servName);
				item.setServAddr(servAddr);
				item.setServStatus(servStatus);
				
				resultList.add(item);
			}
			
		}
		return resultList;
	}

	public List getRestServStatList() throws DataAccessException {
		List resultList = this.getMobileRestServLogDAO().getRestServStatList();
		return resultList;
	}
	
	public List getRestServFlowStatList() throws DataAccessException {
		List resultList = this.getMobileRestServLogDAO().getRestServFlowStatList();
		return resultList;
	}
	
	public List getStaffFlowStatList() throws DataAccessException {
		List resultList = this.getMobileRestServLogDAO().getStaffFlowStatList();
		return resultList;
	}
}
