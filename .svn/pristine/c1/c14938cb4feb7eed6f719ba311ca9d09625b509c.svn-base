package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import com.zterc.uos.UOSException;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
public interface ExtEtlRuleDAO extends BaseDAO{
	public void deleteByUpdate(Map paramMap) throws DataAccessException;
	public void insert(Map paramMap)throws DataAccessException,UOSException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public Map queryAllEtlRule(Map paramMap,int startIndex,int stepSize) throws DataAccessException;
	public List qryUosTacheCata(Map paramMap)throws DataAccessException;
	public Map qryUosTache(Map paramMap,int startIndex,int stepSize) throws DataAccessException;
}
