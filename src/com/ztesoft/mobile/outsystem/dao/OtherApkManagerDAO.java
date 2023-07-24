package com.ztesoft.mobile.outsystem.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface OtherApkManagerDAO extends BaseDAO {
	public Map insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public Map selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selByName(Map paramMap) throws DataAccessException;
	public Map selBySysCode(Map paramMap) throws DataAccessException;
	public List selInfoBySysCode(Map paramMap) throws DataAccessException;
	public void updateName(Map paramMap) throws DataAccessException;
}
