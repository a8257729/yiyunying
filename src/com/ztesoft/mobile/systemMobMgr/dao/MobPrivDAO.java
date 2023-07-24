package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.systemMobMgr.bean.Privilege;
public interface MobPrivDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selByName(Map paramMap) throws DataAccessException;
	public void delete2(Map paramMap) throws DataAccessException;
	public Privilege[] selAllPriv(int jobId, int defaultJobId) throws DataAccessException;
}

