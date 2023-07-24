package com.ztesoft.eoms.oaas.staff.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.oaas.staff.dao.StaffPermissionsDAO;

public class StaffPermissionsDAOImpl extends BaseDAOImpl implements
		StaffPermissionsDAO {

	public void setPermissions(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		System.out.println("CHENLIN: in setPermissions!!!" );
		String callStr = "{call ADD_STAFF_PRIV( ?)}";
		 Connection conn = null;
         CallableStatement proc = null; 
         try {
             conn = getConnection();
             proc = conn.prepareCall(callStr);
             //System.out.println("CHENLIN :111111111111111111 "+MapUtils.getString(paramMap, "userName"));
             proc.setString(1,MapUtils.getString(paramMap, "userName"));
             
             //System.out.println("CHENLIN: callStr = " + callStr);
             proc.execute();  
         } catch (Exception ex) {
             ex.printStackTrace();
             /** @todo Handle this exception */
         } finally {
             cleanUp(conn, proc, null, null);
         }
	}

}
