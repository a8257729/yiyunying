package com.ztesoft.eoms.oaas.role.dao;

import java.sql.SQLException;
import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;

public interface RoleDAO extends BaseDAO {
	public Map getAreas()throws DataAccessException ;
    public Map getSubAreaTreeById(int areaId) throws DataAccessException;
    public Map findAllRolesInArea(int _jobId,int _defaultJobId,int areaId) throws Exception;
    public Map findRolesHoldOneJobInArea(int jobId,int _jobId,int _defaultJobId,int areaId) throws Exception;
    public Map findRolesHoldTwoJobInArea(int jobId,int specialJobId,int _jobId,int _defaultJobId,int areaId) throws Exception;
    public Map findRolesByJob(int jobId) throws SQLException ;
    public Map findRolesByPost(int postId) throws SQLException;
    public Map getRoleGridByJob(int jobId) throws DataAccessException ;
    
    public Map getAllRolesInArea(int _jobId,int areaId) throws Exception ;
    public Map getAllRolesInArea(int jobId1,int jobId2,int areaId) throws Exception;
    public Map getAllRolesInArea(int jobId1,int jobId2,int jobId3,int areaId) throws Exception ;
    
    public Map getPostRolesInArea(int postId,int areaId) throws Exception ;
    
    public Map getRoleGridByArea(int areaId ) throws DataAccessException ;
}
