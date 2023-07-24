package com.ztesoft.mobile.etl.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * description : DAO
 * @author FL
 */
public interface DataBaseMngDAO extends BaseDAO{
	public Map getDataBaseList(Map paramMap, int startIndex, int stepSize) throws DataAccessException ;
	public List getDataListForRule(String dsType) throws DataAccessException ;
	public List queryDataBaseTypes() throws DataAccessException;
}

