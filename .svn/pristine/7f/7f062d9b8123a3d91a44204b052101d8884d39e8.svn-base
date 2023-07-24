package com.ztesoft.eoms.exintf.util.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zterc.uos.helpers.AbstractDAOImpl;
import com.ztesoft.mobile.common.db.DbOper;

public class UosConfigDAO extends AbstractDAOImpl{
	private static UosConfigDAO _UosConfigDAO = new UosConfigDAO();
	
	private UosConfigDAO(){
		
	}
	public synchronized static UosConfigDAO getInstance(){
		if(_UosConfigDAO==null){
			_UosConfigDAO = new UosConfigDAO();
		}
		return _UosConfigDAO;
	}
	
	/**
	 * 获取UOS_CONFIG表所配置的值
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public String getValue(String name) throws SQLException {
		String value = "";
		final String sql = "select NAME,VALUE,COMMENTS from uos_config where NAME=?";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(true);
			
			ps = conn.prepareStatement(sql);
			DbOper.setPrepStatementParam(1, ps, name);
			rs = ps.executeQuery();
			while (rs.next()) {
				value = rs.getString("VALUE");
			}
		} finally {
			cleanUp(conn, null, ps, null);
		}
		cleanUp(conn, null, ps, null);
		return value;
	}
	/**
	 * 更新UOS_CONFIG表配置的值
	 * @param name
	 * @param newValue
	 * @return
	 * @throws SQLException
	 */
	public int updateValue(String name,String newValue) throws SQLException{
		int ret = -1;
		final String sql = "update uos_config set value=? where name=?";
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			DbOper.setPrepStatementParam(1, ps, newValue);
			DbOper.setPrepStatementParam(2, ps, name);
			ret = ps.executeUpdate();
		}finally {
			cleanUp(conn, null, ps, null);
		}
		return ret;
	}
	
}
