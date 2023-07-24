package com.ztesoft.mobile.v2.dao.oaas;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface StaffDAO extends BaseDAO{
	public Map selByUserName(Map paramMap)throws DataAccessException;
	public Map selByJobId(Map paramMap)throws DataAccessException;
	public Map selByOrgId(Map paramMap)throws DataAccessException; 
}
