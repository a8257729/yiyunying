package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface AppRegMgrDAO extends BaseDAO{
	public Map selByName(Map paramMap) throws DataAccessException ;
	public void updateIsPass(Map paramMap) throws DataAccessException ;
	public void update(Map paramMap) throws DataAccessException;
	public Map selApkInfoByconn(Map paramMap) throws DataAccessException ;
	public List selApkInfoByMuneId(Map paramMap) throws DataAccessException;
	public Map insert(Map paramMap) throws DataAccessException;
	public Map selByApkCode(Map paramMap) throws DataAccessException;
	
	public void osInsert(Map paramMap) throws DataAccessException;
	public void osUpdate(Map paramMap) throws DataAccessException;
	public void osDelete(Map paramMap) throws DataAccessException;
	public Map selByOsType(Map paramMap) throws DataAccessException;
	public Map selClassByOsType(Map paramMap) throws DataAccessException;
}
