package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import com.zterc.uos.oaas.exception.OAASException;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;
public interface MobStaffJObRightDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public void deleteByJobId(Map paramMap) throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selByJobId(Map paramMap) throws DataAccessException;
	public void updateJobPrivs(String[] roleIds, int jobId,String[] grantPrivs, String[] selectedPrivs) throws OAASException;
	public List getPrivCode(String strpriv) throws DataAccessException;
	public void removeJobPrivs(int jobId);
}
