package com.ztesoft.eoms.oaas.job.dao;

import java.sql.SQLException;
import java.util.Map;

import com.zterc.uos.oaas.vo.Job;
import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;

public interface JobSelDAO extends BaseDAO {
	public Map getJobGridByOrg(int orgId) throws DataAccessException ;
    public Map getJobByOrg(int orgId) throws DataAccessException;
    
    public Map getOrgByPathCode(String orgPathCode) throws Exception;
    public Job[] getJobByPathCode(String orgPathCode) throws Exception;
    
    public Job[] findByStaff(int staffId) throws SQLException ;
}
