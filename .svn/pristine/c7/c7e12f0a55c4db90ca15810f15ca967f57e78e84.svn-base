package com.ztesoft.mobile.systemMonitor.dao;

import java.sql.SQLException;
import java.util.Map;
import java.util.List;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileFuncCallRecordDAO extends BaseDAO{	
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selByConn(Map paramMap) throws DataAccessException;
	public Map qryAvgTimeConsuming(Map paramMap) throws SQLException ;
	public Map qryserverAskRank10(Map paramMap) throws SQLException ;
	public Map qryInterAskRank10(Map paramMap) throws SQLException;
	public Map qryServerRespondRank10(Map paramMap) throws SQLException;
	public Map qrySuccessRate(Map paramMap) throws SQLException;
	public Map qrySuccessRateCount10(Map paramMap) throws SQLException;
	public Map qryTimeConsuming(Map paramMap) throws SQLException;
	public Map qryFlow(Map paramMap) throws SQLException;
	public Map qryAvgFlow(Map paramMap) throws SQLException;
	public Map qryAskFlow10(Map paramMap) throws SQLException;
	public Map qryRespondFlow10(Map paramMap) throws SQLException;
	public Map qryTotalFlow(Map paramMap) throws SQLException;
}
