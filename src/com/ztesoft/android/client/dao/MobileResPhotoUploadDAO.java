package com.ztesoft.android.client.dao;

import java.util.Map;
import java.util.List;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileResPhotoUploadDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selByName(Map paramMap) throws DataAccessException;
}
