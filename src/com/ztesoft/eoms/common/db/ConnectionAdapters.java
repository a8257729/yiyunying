package com.ztesoft.eoms.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ztesoft.eoms.common.configure.ConfigMgr;
import com.ztesoft.eoms.common.helper.Base64It;
import com.ztesoft.eoms.common.helper.DAOHelper;
import com.ztesoft.eoms.common.helper.NamingHelper;
import com.ztesoft.eoms.common.helper.ValidateHelper;
import com.ztesoft.eoms.exception.DataAccessException;

/**
 * 对数据库连接的适配器时类 调用newConnection()得到一个数据库连接
 * 如果是命令行启动则得到一个DriverManager.connection连接
 * 如果是web服务器环境则得到一个DataSource.connection连接
 * <p>
 * Title: 电子运维项目
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 *
 * <p>
 * Company: ZTESoft
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
public class ConnectionAdapters {
	private static final Logger _log = Logger.getLogger(ConnectionAdapters.class);
	public ConnectionAdapters() {
	}

	/**
	 * 服务器DataSource.connection连接
	 *
	 * @return Connection
	 */
	private Connection getConnectionForServer() throws SQLException {
		return GetJNDIConn.getConn();
	}

	private Connection getConnectionForClient() throws SQLException {
		return GetJDBCConn.getConn();
	}

	/**
	 * 外部获得一个数据库连接 用完以后请调用cleanUp方法关闭数据库连接
	 *
	 * @return Connection
	 */
	public Connection newConnection() throws SQLException {
		String connectType = ConfigMgr.getInstance().getPropertyAsString(
				"common", "com.ztesoft.eoms.databse.connectType");
		Connection conn = null;
		
		if (connectType.toUpperCase().equals("JNDI")) {
			
			conn = getConnectionForServer();
			
		} else if (connectType.toUpperCase().equals("JDBC")) {
			conn = getConnectionForClient();

		}
		return conn;
	}

	public static void resetThreadLocal() {
		GetJDBCConn.resetThreadLocal(null);
		GetJNDIConn.resetThreadLocal(null);
	}

	public static void closeConnection() throws SQLException {
		try {
		} finally {
			GetJDBCConn.closeConnection();
			GetJNDIConn.closeConnection();
		}
	}

}

class GetJDBCConn {
	/* 缓存线程共享Connection */
	// private static final ThreadLocal clientConn = new ThreadLocal();
	public static Connection getConn() throws SQLException {
		// Connection conn = (Connection) clientConn.get();
		Connection conn = null;
		// if (!ValidateHelper.validateNotNull(conn) || conn.isClosed()) {
		conn = getConnection();
		// clientConn.set(conn);
		// }
		return conn;
	}

	public static void resetThreadLocal(Object obj) {
		// clientConn.set(obj);

	}

	protected static void closeConnection() throws SQLException {
		// Connection conn = (Connection) clientConn.get();
		// if (ValidateHelper.validateNotNull(conn)) {
		// DbOper.cleanUp(null, null, null, conn);
		// }

	}

	/**
	 * 命令行DataSource.connection连接 在classes目录下配置oradb.properties文件 文件内容:
	 * oracle.url="数据库连接" oracle.user="用户名" oracle.password="密码" 示例： oracle.url =
	 * "jdbc:oracle:thin:@10.238.75.65:1521:eoms"; oracle.user = "eproj";
	 * oracle.password= "eproj";
	 *
	 * @return Connection
	 */
	private static Connection getConnection() throws SQLException {
		/*
		 * String databaseUrl = "jdbc:oracle:thin:@10.238.75.65:1521:eoms";
		 * String password = "eproj"; String user = "eproj";
		 */

		Connection conn = null;
		try {

			String databaseUrl = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database.jdbc.db.url");
			String driverName = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database.jdbc.db.driver");
			String _user = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database.jdbc.db.user");
			String _password = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database.jdbc.db.pwd");
			Class.forName(driverName);
  		    _user = new String(Base64It.decode(_user.getBytes()));
			_password = new String(Base64It.decode(_password.getBytes()));
 
			conn = DriverManager.getConnection(databaseUrl, _user, _password);

		} catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException(
					"JDBC connect attributes can not be used", e);
		}
		return conn;

	}

        public static void main(String[] args){
       ConnectionAdapters connectionAdapters = new ConnectionAdapters();
       Connection conn = null;
                  Connection conn1 = null;
                 try{
                 conn = connectionAdapters.newConnection();
                 conn1 = connectionAdapters.newConnection();

                 }catch(SQLException s){
                   s.printStackTrace();

                 }finally{
                         try{
                                 conn.close();
                                 conn1.close();

                         }catch(SQLException s){
                                 s.printStackTrace();
                         }
                 }

   }


}

/**
 *
 * <p>
 * Title: 电子运维项目
 * </p>
 *
 * <p>
 * Description:
 * </p>
 * 负责取得服务器数据库连接的内部类 extends <code>AbstractDAOImpl</code> protected connection
 * getConnection()方法。 进行public 重载。方便外部DBManager访问
 *
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 *
 * <p>
 * Company: ZTESoft
 * </p>
 *
 * @author dawn
 * @version 1.0
 */
class GetJNDIConn {
	private static final Logger _log = Logger.getLogger(GetJNDIConn.class);
	/* 缓存线程共享Connection */
	// private static final ThreadLocal serverConn = new ThreadLocal();
	/** 缓存数据源的HashMap */
	private static Map dsMap = Collections.synchronizedMap(new HashMap());

	private static String dataSourceUrl = DAOHelper.getJNDIDataSourceName();

	/**
	 * Insert the method's description here.
	 *
	 * @param dsName
	 *            the name of DataSource
	 * @return javax.sql.DataSource
	 * @throws NamingException
	 *             The exception description.
	 */
	private static DataSource getDataSource(String dsName)
			throws NamingException {
		
		// See if we already have the DataSource
		DataSource ds = (DataSource) dsMap.get(dsName);

		if (!ValidateHelper.validateNotNull(ds)) {
			ds = (DataSource) NamingHelper.singleton().getInitialContext()
					.lookup(dsName);
			dsMap.put(dsName, ds);
		}
		
				
		return ds;
	}

	public static void resetThreadLocal(Object obj) {
		// serverConn.set(obj);

	}

	protected static void closeConnection() throws SQLException {
		// Connection conn = (Connection) serverConn.get();
		// if (ValidateHelper.validateNotNull(conn)) {
		// DbOper.cleanUp(null, null, null, conn);
		// }

	}

	public static Connection getConn() throws SQLException {
		// Connection conn = (Connection) serverConn.get();
		// if (!ValidateHelper.validateNotNull(conn) || conn.isClosed()) {
		// 去掉数据源缓存,适应iom整合的需要
		Connection conn = getConnection();
		// serverConn.set(conn);
		// }
		return conn;

	}

	public static Connection getConnection() throws SQLException {
		try {
			return getDataSource(dataSourceUrl).getConnection();
		} catch (NamingException ne) {
			throw new DataAccessException("lookup Datasource_JNDI:" + dataSourceUrl
					+ ";error:" + ne.getMessage(),ne);

		}
	}

}
