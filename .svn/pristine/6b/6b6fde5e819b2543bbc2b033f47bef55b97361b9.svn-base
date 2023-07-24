package com.ztesoft.eoms.oaas.role.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.ztesoft.eoms.common.dao.BaseDAOImpl;
import com.ztesoft.eoms.oaas.role.dao.ConfRoleDAO;

public class ConfRoleDAOImpl extends BaseDAOImpl implements ConfRoleDAO {

	/**
	 * 批量解除职位和角色的关联
	 * 
	 * @param jobs
	 *            int[] 职位编号数组
	 * @param roles
	 *            int[] 角色编号数组
	 * @throws SQLException
	 */
	public void removeRolesBatch(int roleId) throws SQLException {
		if (roleId == 0) {
			return;
		}
		String sql = "DELETE FROM UOS_JOB_PRIV WHERE ROLE_ID=? ";
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = getConnection();
			psmt = conn.prepareStatement(sql);

			psmt.setInt(1, roleId);
			psmt.addBatch();

			psmt.executeBatch();
		} finally {
			cleanUp(conn, null, psmt, null);
		}
	}
}
