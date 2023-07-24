package com.ztesoft.eoms.oaas.staff.dao;

import java.util.Map;

import com.ztesoft.eoms.common.dao.BaseDAO;
import com.ztesoft.eoms.exception.DataAccessException;

public interface StaffPermissionsDAO extends BaseDAO {
	public void setPermissions(Map paramMap)throws DataAccessException;

}
