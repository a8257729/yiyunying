package com.ztesoft.mobile.v2.dao.interf;

import java.util.Map;
import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileRestServLogDAO extends BaseDAO {
	
	public void insert(Map paramMap) throws DataAccessException;

	public void update(Map paramMap) throws DataAccessException;

	public void delete(Map paramMap) throws DataAccessException;

	public List selAll(Map paramMap) throws DataAccessException;

	public Map selById(Map paramMap) throws DataAccessException;
	
	public List selByStaff(Map paramMap) throws DataAccessException;
	
	public List getRestServStatList() throws DataAccessException;
	
	public List getStaffFlowCount(Long staffId) throws DataAccessException;
	
	public List getStaffFlowStatList() throws DataAccessException;
	
	public List getRestServFlowStatList() throws DataAccessException;
}
