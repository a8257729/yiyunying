package com.ztesoft.mobile.system.dao;

import java.util.Map;

import com.zterc.uos.oaas.vo.Staff;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface StaffSelDAO extends BaseDAO {
    public Map getOrgStaff(String qryType,int orgId,String orgPathCode ,String staffName,String userName,String officeTel,String mobileTel,int start,int limit) throws DataAccessException;
    public Map getOrgStaffForPushMsg(int orgId, String orgPathCode, String staffName) throws DataAccessException;
    public Map getOrgForTree(String qryType,int orgId, String orgPathCode, String staffName,
			String userName, String officeTel, String mobileTel, int start,
			int limit) throws DataAccessException;
    public Map getOrgStaffForTree(String qryType,int orgId, String orgPathCode, String staffName,
			String userName, String officeTel, String mobileTel, int start,
			int limit) throws DataAccessException;
    public Map getNoJobStaff(String staffName, String userName, String officeTel, String mobileTel, int start, int limit) throws DataAccessException ;
    public Map getRolesByStaff(int staffId,int jobId)throws DataAccessException ;
    public Map getPrivilegeByStaff(int staffId,int specialJobId)throws Exception ;
    public Map findNationList() throws Exception;
    public Map getStaffsByJob(int jobId,int startIndex,int stepSize)throws DataAccessException ;
    public Staff selStaff(Map paramMap)throws DataAccessException;
    public int updataStaff(Map paramMap)throws DataAccessException;;
    public int selImsi(Map paramMap)throws DataAccessException;
}
