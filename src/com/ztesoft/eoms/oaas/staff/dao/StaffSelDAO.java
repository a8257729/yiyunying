package com.ztesoft.eoms.oaas.staff.dao;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;

public interface StaffSelDAO extends BaseDAO {
    public Map getOrgStaff(String qryType,int orgId,String orgPathCode ,String staffName,String userName,String officeTel,String mobileTel,int start,int limit) throws DataAccessException;
    public Map getNoJobStaff(String staffName, String userName, String officeTel, String mobileTel, int start, int limit) throws DataAccessException ;
    public Map getRolesByStaff(int staffId,int jobId)throws DataAccessException ;
    public Map getPrivilegeByStaff(int staffId,int specialJobId)throws Exception ;
    public Map findNationList() throws Exception;
    public Map getStaffsByJob(int jobId,int startIndex,int stepSize)throws DataAccessException ;
    public Map selByUserName(Map paramMap)throws DataAccessException;
	public Map selByJobId(Map paramMap)throws DataAccessException;
	public Map selByOrgId(Map paramMap)throws DataAccessException; 
}
