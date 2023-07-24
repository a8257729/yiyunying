package com.ztesoft.eoms.im.dao;

import java.util.Map;
import java.util.List;
import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;
public interface ImStaffGroupRealDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selStaffsByGroupId(Map paramMap) throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public void moveFriToGroup(Map paramMap)throws DataAccessException;
	public void delFriFromGroup(Map paramMap)throws DataAccessException;
	public Map selStaffByUserName(Map paramMap) throws DataAccessException;
	public Map selStaff(Map paramMap) throws DataAccessException;
}

