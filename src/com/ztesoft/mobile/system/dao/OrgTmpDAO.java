package com.ztesoft.mobile.system.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface OrgTmpDAO extends BaseDAO {
	public Map getSubOrgsById(int orgId)throws Exception ;
	public Map getJobTmpGridByOrgTmp(int orgTmpId) throws DataAccessException ;
	public Map findRolesByPost(int postId) throws DataAccessException ;
	public Map findJobTmpLevelList() throws Exception ;
}
