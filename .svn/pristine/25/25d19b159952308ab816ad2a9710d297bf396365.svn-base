package com.ztesoft.mobile.system.dao;

import java.sql.SQLException;
import java.util.Map;

import com.zterc.uos.oaas.vo.Job;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface JobSelDAO extends BaseDAO {
	public Map getJobGridByOrg(int orgId) throws DataAccessException ;
    public Map getJobByOrg(int orgId) throws DataAccessException;
    
    public Map getOrgByPathCode(String orgPathCode) throws Exception;
    public Job[] getJobByPathCode(String orgPathCode) throws Exception;
    
    public Job[] findByStaff(int staffId) throws SQLException ;
}
