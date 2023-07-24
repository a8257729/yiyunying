package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
public interface OuterSystemDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selAllSysOrgion(Map paramMap)throws DataAccessException;
	public List selSystemByRegionId(Map paramMap) throws DataAccessException;
	public List selAllSSOType(Map paramMap)throws DataAccessException;
	/**
	 * 分页查询外系统
	 * @param paramMap
	 * @param startIndex
	 * @param stepSize
	 * @return
	 * @throws DataAccessException
	 */
	public Map queryOuterSystem(Map paramMap,int startIndex,int stepSize) throws DataAccessException;
	public List selAllLogoutUrl(Map paramMap)throws DataAccessException;
	public List queryOuterSystemForTree(Map paramMap)throws DataAccessException;
	
}

