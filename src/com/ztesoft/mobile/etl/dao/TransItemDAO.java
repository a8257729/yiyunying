package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface TransItemDAO extends BaseDAO {
	public long insert(Map paramMap) throws DataAccessException;

	public void insertComp(Map paramMap) throws DataAccessException;

	public void update(Map paramMap) throws DataAccessException;

	public void delete(Map paramMap) throws DataAccessException;

	public void deleteComp(Map paramMap) throws DataAccessException;

	public Map selById(Map paramMap) throws DataAccessException;

	public Map selItemsAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public Map selItemsCompAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public Map selCompsAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public Map selMeterDataColumnsByTab(Map paramMap, int startIndex,
			int stepSize) throws DataAccessException;
}
