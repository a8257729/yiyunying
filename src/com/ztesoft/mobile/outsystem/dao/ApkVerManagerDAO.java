package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface ApkVerManagerDAO extends BaseDAO {
	public Long insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public Map selAll(Map paramMap)throws DataAccessException;
	public Map selByApkCode(Map paramMap)throws DataAccessException;
	public List selApvkVerByCode(Map paramMap)throws DataAccessException;
	public void addDownlaodTimes(Map paramMap)throws DataAccessException;
}
