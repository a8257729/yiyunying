package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import com.zterc.uos.UOSException;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface EtlJdbcRuleDAO extends BaseDAO {
	public void insert(Map paramMap) throws DataAccessException, UOSException;

	public void update(Map paramMap) throws DataAccessException, UOSException;

	public void delete(Map paramMap) throws DataAccessException, UOSException;

	public Map queryAllEtlRule(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

}
