package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : DAO
 * @author FL
 */
public interface SchMonitorDAO extends BaseDAO{
	public Map qryFieldLog(Map paramMap) throws DataAccessException;
	public Map getScheduleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException ;
	public Map getRuleList(Map paramMap, int startIndex, int stepSize) throws DataAccessException ;
	public Map qryInfAllRowsImplLog(Map paramMap, int startIndex, int stepSize) throws DataAccessException ;
	public Map qryCleanLogListAction(Map paramMap, int startIndex, int stepSize) throws DataAccessException ;
}

