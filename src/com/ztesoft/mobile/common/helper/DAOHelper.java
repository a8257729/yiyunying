package com.ztesoft.mobile.common.helper;

import com.zterc.uos.UOSException;
import com.zterc.uos.model.SequenceDTO;
import com.ztesoft.mobile.common.db.ConnectionAdapters;
import com.ztesoft.mobile.common.db.DbOper;
import com.ztesoft.mobile.common.db.DbOperSupportFactory;
import org.apache.commons.collections.MapUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * Title: EomsProj
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author dawn
 * @version 1.0
 */
public class DAOHelper {

	private DAOHelper() {
	}

	private static String dataBaseType = null;

	private static String connectionType = null;

	private static String jndiDataSourceName = null;

	private static String isSeqRandomDebug = null;

	static {
		if (!ValidateHelper.validateNotEmpty(dataBaseType)) {
			dataBaseType = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database");

		}
		if (!ValidateHelper.validateNotEmpty(connectionType)) {
			connectionType = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.databse.connectType");
		}
		if (!ValidateHelper.validateNotEmpty(jndiDataSourceName)) {
			jndiDataSourceName = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.database.jndi.datasourceName");
		}

		if (!ValidateHelper.validateNotEmpty(isSeqRandomDebug)) {
			isSeqRandomDebug = ConfigMgr.getInstance().getPropertyAsString(
					"common", "com.ztesoft.eoms.randomsequence");
		}

	}

	/**
	 * 获取
	 * 
	 * @param num
	 *            int
	 * @return String
	 */
	public static String getRandom(int num) {

		return NumberHelper.getSimplyRandom(num);

	}

	/**
	 * 从db中获得primayKey
	 * 
	 * @param tableName
	 *            String
	 * @param num
	 *            int
	 * @return Long
	 * @throws UOSException
	 */
	public static Long getPKFromDB(String tableName, int num)
			throws UOSException {
		Long retId = null;
//		long retId = com.zterc.uos.client.SequenceGenerator.getSequenceValue(
//				tableName, num);
//		return (!EqualsHelper.equals(retId, -1)) ? new Long(retId) : null;
		try {
			retId = getPKFromSeq(tableName, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retId;
	}

	/**
	 * 从内存中获得primaryKey,默认增加值为1
	 * 
	 * @param tablename
	 *            String
	 * @param num
	 *            int
	 * @return Long
	 * @throws UOSException
	 */
	public static Long getPKFromMem(String tablename, int num)
			throws UOSException, SQLException {
		// 支持oralce方式，从sequence方式
		// return getPKFromMem(tablename, num, 1);
		return getPKFromSeq(tablename, 1);
	}

	/**
	 * 从sequence序列里面获取ID ,主要是提高给oracle使用
	 * 
	 * @param tablename
	 * @param addNum
	 * @return
	 * @throws Exception
	 * @since 2008.5.22
	 */
	private static Long getPKFromSeq(String tablename, int addNum)
			throws UOSException, SQLException {
		Long retVal = null;
		Connection con = getDbConnection();
		DbOper oper = new DbOper(con);
		Map param = new HashMap();
		try {
			for (int i = 0; i < addNum; i++) {
				DbOperSupportFactory.getFactory().getDbOperSupport()
						.preparedInsert(param, tablename, null, oper);
			}
			retVal = MapUtils.getLong(param, "@@SEQ");
			return retVal;
		} finally {
			if (oper != null) {
				oper = null;
			}
			if (con != null) {
				con = null;
			}
			if (param != null) {
				param = null;
			}
		}
	}

	/**
	 * 从内存中获得primaryKey,增加值指定
	 * 
	 * @param tablename
	 *            String
	 * @param num
	 *            int
	 * @param addNum
	 *            int
	 * @return Long
	 * @throws UOSException
	 */

	public static Long getPKFromMem(String tablename, int num, int addNum)
			throws UOSException,SQLException {
		// long retVal = getNewSequenceValueFormMemory(tablename, addNum);
		//	
		// return (!EqualsHelper.equals(retVal, -1)) ? new Long(retVal) : null;
		//	
		return getPKFromSeq(tablename, addNum);
	}

	/**
	 * 私有方法。实际获得内存中primaryKey
	 * 
	 * @param tableName
	 *            String
	 * @return long
	 * @throws UOSException
	 */
	

	/**
	 * 私有方法，实际获得db中primaryKey
	 * 
	 * @param sequence
	 *            SequenceDTO
	 * @return long
	 * @throws UOSException
	 */
	private synchronized static long getNewSequenceValue(SequenceDTO sequence)
			throws UOSException {
		return com.zterc.uos.client.SequenceGenerator.generate(sequence);
	}

	/**
	 * 私有重载方法。获得db中的primayKey
	 * 
	 * @param sequence
	 *            SequenceDTO
	 * @return String
	 * @throws UOSException
	 */
	public synchronized static String getNewSequenceId(SequenceDTO sequence)
			throws UOSException {
		return String.valueOf(getNewSequenceValue(sequence));
	}

	/**
	 * 获取数据库类型
	 * 
	 * @return String
	 */

	public static String getDatabaseType() {
		return dataBaseType;
	}

	/**
	 * 获取是否链接类型
	 * 
	 * @return String
	 */
	public static String getConnectionType() {
		return connectionType;
	}

	/**
	 * 获取JNDI的dataSource名称
	 * 
	 * @return String
	 */
	public static String getJNDIDataSourceName() {
		return jndiDataSourceName;
	}

	private static String getRandomSeqDebug() {
		return isSeqRandomDebug;
	}

	public static boolean isDebug() {
		return isRandomSeqDebug();
	}

	public static boolean isRandomSeqDebug() {
		return EqualsHelper.equals(getRandomSeqDebug(), "true");
	}

	private static Connection getDbConnection() throws UOSException,
			SQLException {
		ConnectionAdapters adapters = new ConnectionAdapters();
		Connection conn = adapters.newConnection();
		adapters = null;
		return conn;

	}
}
