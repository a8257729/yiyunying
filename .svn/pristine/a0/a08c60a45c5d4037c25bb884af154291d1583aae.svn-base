package com.ztesoft.mobile.system.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface OrgDAO extends BaseDAO {
	public Map getSubOrgTmpTreeById(int parentId) throws DataAccessException ;
	public Map getTopOrgTmpTree() throws DataAccessException ;
	public Map getSubAreaTreeById(int parentId) throws DataAccessException ;
	public Map getTopAreaTree() throws DataAccessException ;
	public Map getAreaByKey(int areaId)throws DataAccessException ;
	public Map getTopOrg()throws Exception ;
	public Map getSubOrgsById(int orgId)throws Exception ;
	public Map getSubOrgsByIdCheck(int orgId)throws Exception;
	public Map getTopOrgCheck()throws Exception;
}
