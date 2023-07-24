package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface TransConfigDAO extends BaseDAO {

	public void insert(Map paramMap) throws DataAccessException;

	public void update(Map paramMap) throws DataAccessException;

	public void deleteByUpdate(Map paramMap) throws DataAccessException;

	public void delete(Map paramMap) throws DataAccessException;

	public Map selById(Map paramMap) throws DataAccessException;

	public Map selRulesAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public List selCatalogTree(long parentId) throws DataAccessException;

	/**
	 * 查询结果�?

	 * 
	 * @param paramMap
	 *            页面参数
	 * @param startIndex
	 *            翻页起始索引
	 * @param stepSize
	 *            页纪录数
	 */
	public Map selSysTabAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

}
