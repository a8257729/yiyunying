package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface MobLoginLogDAO extends BaseDAO{
	
	public Map selMobileLog(Map paramMap,int start,int limit) throws DataAccessException;
	
}
