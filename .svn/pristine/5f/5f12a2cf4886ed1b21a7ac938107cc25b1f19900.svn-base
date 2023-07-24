package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.exception.OAASException;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
public interface MobRolePrivDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List getPrivCode(String strpriv) throws DataAccessException;
	public void updateRolePrivs(int roleId, String[] selectedPrivs) throws OAASException;
	public void removeJobPrivs(int jobId);
	
}
