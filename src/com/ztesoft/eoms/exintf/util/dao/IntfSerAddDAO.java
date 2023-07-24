package com.ztesoft.eoms.exintf.util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zterc.uos.helpers.AbstractDAOImpl;
import com.ztesoft.mobile.common.db.DbOper;

public class IntfSerAddDAO extends AbstractDAOImpl {
	
	private static IntfSerAddDAO _IntfSerAddDAO = new IntfSerAddDAO();
	
	private IntfSerAddDAO(){
		
	}
	public synchronized static IntfSerAddDAO getInstance(){
		if(_IntfSerAddDAO==null){
			_IntfSerAddDAO = new IntfSerAddDAO();
		}
		return _IntfSerAddDAO;
	}
	
	/**
	 * 获取外系统的服务接入地址
	 * @param sysNam 系统名称
	 * @return serAdd 系统服务接入地址
	 * @throws SQLException
	 */
	public String getSerAdd(String sysName) throws SQLException {
		String serAdd = null;
		final String sql = "select SYS_NAME,SERVICE_ADDRESS,COMMENTS from INTERFACE_SERVICE_ADDRESS where SYS_NAME=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			//conn.setAutoCommit(true);
			ps = conn.prepareStatement(sql);
			DbOper.setPrepStatementParam(1, ps, sysName);
			rs = ps.executeQuery();
			while (rs.next()) {
				serAdd = rs.getString("SERVICE_ADDRESS");
			}
		} finally {
			cleanUp(conn, null, ps, null);
		}
		return serAdd;
	}
}
