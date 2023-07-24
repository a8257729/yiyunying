package com.ztesoft.mobile.pn.dao;

import java.util.Map;
import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface PushMessageClassDAO extends BaseDAO {

	public void insert(Map paramMap)throws DataAccessException;

	public void update(Map paramMap)throws DataAccessException;

	public void delete(Map paramMap)throws DataAccessException;

	/**
	 * 不分页查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List selAll(Map paramMap)throws DataAccessException;

	/**
	 * 根据PK查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map selById(Map paramMap)throws DataAccessException;

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map selByPagin(Map paramMap) throws DataAccessException;
}
