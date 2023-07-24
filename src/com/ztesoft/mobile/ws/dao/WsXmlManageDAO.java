package com.ztesoft.mobile.ws.dao;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public interface WsXmlManageDAO extends BaseDAO {

	public void insert(Map paramMap)throws DataAccessException;

	public void update(Map paramMap)throws DataAccessException;

	public void delete(Map paramMap)throws DataAccessException;

	/**
	 * 不分页查询
	 * @param paramMap
	 * @return
	 * @throws com.ztesoft.mobile.common.exception.DataAccessException
	 */
	public List selAll(Map paramMap)throws DataAccessException;

	/**
	 * 根据PK查询
	 * @param paramMap
	 * @return
	 * @throws com.ztesoft.mobile.common.exception.DataAccessException
	 */
	public Map selById(Map paramMap)throws DataAccessException;

	/**
	 * 分页查询
	 * @param paramMap
	 * @return
	 * @throws com.ztesoft.mobile.common.exception.DataAccessException
	 */
	public Map selByPagin(Map paramMap) throws DataAccessException;
}
