package com.ztesoft.mobile.systemMonitor.dao;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileSessionRecordDAO extends BaseDAO{
	
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void updateLastVisitTime(Map paramMap)throws DataAccessException;
	public void updateConnectLimit(Map paramMap)throws DataAccessException;
	public void updateConnectState(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selByConn(Map paramMap) throws DataAccessException;
	public Map selStaffInfo(Map paramMap) throws DataAccessException;
	public Map selUserConnLimit(Map paramMap) throws DataAccessException;
	public void updateUserConnPriv(Map paramMap)throws DataAccessException;	
	public void updateUserFlowLimit(Map paramMap)throws DataAccessException;
	public Map selOnline(Map paramMap) throws DataAccessException ;
	public Map selStaffId(Map paramMap) throws DataAccessException;
	public Map qryTotalFlow(Map paramMap) throws SQLException;
	public void updateUserConnLimit(Map paramMap)throws DataAccessException;
	public Map qryTotalFlowForMobile(Map paramMap) throws SQLException;
	public Map getWkorderCodeBarFlag(Map paramMap) throws SQLException;
}
