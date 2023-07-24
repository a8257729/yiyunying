package com.ztesoft.eoms.oaas.role.dao;

import java.sql.SQLException;

import com.ztesoft.eoms.common.dao.BaseDAO;

public interface ConfRoleDAO extends BaseDAO {
	public void removeRolesBatch(int roleId) throws SQLException ;
}
