package com.ztesoft.mobile.pn.dao;

import java.util.Map;
import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobilePnUserDAO extends BaseDAO {
	
	public void insert(Map paramMap) throws Exception;

	//public void insertBatch(List paramMapList) throws Exception;

	public void update(Map paramMap) throws Exception;
	
	public void updateOnline(Map paramMap) throws Exception;

	//public void updateBatch(List paramMapList) throws Exception;

	public void delete(Map paramMap) throws Exception;

	public Map selById(Map paramMap) throws Exception;

	public List selAll(Map paramMap) throws Exception;
	
	public Map selByPagin(Map paramMap) throws Exception;
	
	public Map getOneByCons(Map paramMap) throws Exception;
	
	public List selAllOnline(Map paramMap) throws Exception;
}