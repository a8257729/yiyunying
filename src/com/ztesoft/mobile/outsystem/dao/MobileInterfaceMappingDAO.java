package com.ztesoft.mobile.outsystem.dao;

import java.util.Map;
import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileInterfaceMappingDAO extends BaseDAO {
	
	public void insert(Map paramMap) throws DataAccessException;

	public void update(Map paramMap) throws DataAccessException;

	public void delete(Map paramMap) throws DataAccessException;

	public Map selById(Map paramMap) throws DataAccessException;

	public List selAll(Map paramMap) throws DataAccessException;
	
	/**
	 * ��ҳ��ѯ
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map selByPagin(Map paramMap)  throws DataAccessException;
	
}