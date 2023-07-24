package com.ztesoft.mobile.systemMonitor.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileServerMonitorDAO extends BaseDAO {

	public Map selById(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selByConn(Map paramMap) throws DataAccessException;
	public Map selAvgRestService(Map paramMap) throws DataAccessException;
	public Map selAvgNumRestService(Map paramMap) throws DataAccessException;
	public Map selRestServiceByConn(Map paramMap) throws DataAccessException;
}
