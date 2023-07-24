package com.ztesoft.mobile.v2.dao.workform.shanghai;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class WorkOrderDAOImpl extends BaseDAOImpl implements WorkOrderDAO {

	public List selAll(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map selByPage(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null; 
	}

    public List getCancelOrderReason() throws DataAccessException
    {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT RECOVER_REASON_ID as recoverReasonId,RECOVER_REASON as recoverReason ");
        sb.append("FROM FAULT_RECOVER_REASON ");
        sb.append("WHERE STATE = 1 ");
        sb.append("ORDER BY RECOVER_REASON");
        System.out.println(sb.toString());
        return dynamicQueryListByMap(sb.toString(), null, null);
    }
    
}
