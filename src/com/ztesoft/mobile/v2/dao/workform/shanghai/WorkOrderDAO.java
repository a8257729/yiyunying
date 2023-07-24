package com.ztesoft.mobile.v2.dao.workform.shanghai;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface WorkOrderDAO extends BaseDAO {
	
	public List selAll(Map paramMap) throws DataAccessException; 

    public Map selByPage(Map paramMap) throws DataAccessException;
    
    public List getCancelOrderReason() throws DataAccessException; 
    
}
