package com.ztesoft.eoms.im.dao;

import java.util.Map;
import java.util.List;
import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;
public interface ImStaffOnlineDAO extends BaseDAO{
	public Long insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public Map selOnlineStateByStaffId(Map paramMap) throws DataAccessException ;
}
