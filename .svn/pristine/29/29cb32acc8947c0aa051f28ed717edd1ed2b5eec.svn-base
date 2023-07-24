package com.ztesoft.mobile.common.db;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.zterc.uos.helpers.DAOSysException;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.exception.RequiredException;
import com.ztesoft.mobile.common.helper.ClobHelper;
import com.ztesoft.mobile.common.helper.DateHelper;
import com.ztesoft.mobile.common.helper.NumberHelper;
import com.ztesoft.mobile.common.helper.ObjectHelper;
import com.ztesoft.mobile.common.helper.SQLHelper;
import com.ztesoft.mobile.common.helper.StringHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;

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
public class DbOper {

	private static final Logger logger = Logger.getLogger(DbOper.class);

	// 用于Sybase的分页存储过�?

	public static final String PAGI_PROC = "P_PAGINATE";

	// 新版的sybase分页存储过程，支持union分页
	public static final String PAGI_PROC_V2 = "OUT_PAGINATEVIEW";

	public DbOper(Connection conn) throws SQLException {
		if (conn == null) {
			throw new SQLException("connection not be initialize");
		}

		this.conn = conn;

	}

	private Connection conn = null;

	private PreparedStatement ps = null;

	private CallableStatement cs = null;

	public Connection getDbConnection() {

		return conn;
	}

	/**
	 * 清理这个对象的内�?
	 * 
	 * 
	 */
	public void destroy() {
		try {
			if (this.conn != null) {
				this.conn.close();
				this.conn = null;
			}
			if (this.cs != null) {
				this.cs.close();
				this.cs = null;
			}
			if (this.ps != null) {
				this.ps.close();
				this.ps = null;
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error(e.getMessage());
			}
		} finally {
		}
	}

	/**
	 * 返回resultset里记录数�? 内部调用
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return int
	 */
	public int resultSetCount(ResultSet rs) throws SQLException {
		return getResultCount(rs);
	}

	/**
	 * 返回resultset里记录数�? 支持外部调用
	 * 
	 * @param rs
	 *            ResultSet
	 * @throws SQLException
	 * @return int
	 */
	public static int getResultCount(ResultSet rs) throws SQLException {
		// �?验有效�??

		if (!ValidateHelper.validateNotNull(rs)) {
			throw new SQLException("resultset not be initialize");
		}
		if (rs.isAfterLast()) {
			// 如果rs从while(rs.next)出来的话。只�?前移�?位就�?

			rs.previous();
		} else {
			// 否则移动到集合末�?

			rs.last();
		}
		// 得到数量
		int count = rs.getRow();
		rs.beforeFirst();
		return count;
	}

	/**
	 * 
	 * 动�?�增加函�?
	 * 
	 * 
	 * @param obj
	 *            Object �?要插入包含�?�的dto对象
	 * @param tableName
	 *            String �?要插入的表名
	 * @param insertPatternStr
	 *            String 插入模式字符�? "表字段名1:对象中属性名�?1,表字段名2:对象中属性名�?2" example
	 *            :"ID:id,STAFF_NAME:staffName"
	 * @return int
	 * @throws SQLException
	 */
	public int dynamicInsert(Object obj, String tableName, String patternStr)
			throws DataAccessException {
		ValidateHelper.notNull(obj);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(patternStr);

		String sql = "";

		String[] patternStrArr = StringHelper.split(patternStr, ",");
		if (ValidateHelper.validateNotEmpty(patternStrArr)) {
			sql = SQLGenerator.generateSql(patternStrArr, null, tableName,
					SQLGenerator.INSERT_TAG);

			try {
				long beginTime = System.currentTimeMillis();
				ps = conn.prepareStatement(sql);
				setParValue(patternStrArr, ps, obj);
				// return ps.executeUpdate();
				int result = ps.executeUpdate();
				if (System.currentTimeMillis() - beginTime >= 2000) {// 如果单条语句执行超过2秒的，打印其语句
					System.out.println("dynamicInsert_sql:("
							+ (System.currentTimeMillis() - beginTime) + ")"
							+ sql);
				}
				return result;

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("dynamicInsert_exception:" + sql);
				throw new DataAccessException(e.getMessage());
			} finally {

				DbOper.cleanUp(null, null, ps, null);
			}

		} else {
			return -1;
		}
	}

	/**
	 * 动�?�增加函�?
	 * 
	 * 
	 * @param param
	 * @param tableName
	 * @param patternStr
	 *            String 插入模式字符�? "表字段名1:对象中属性名�?1,&&:表字段名2:对象中属性名�?2" example
	 *            :"ID:id,&&STAFF_NAME:staffName,||STAFF_AGE:staffAge"
	 * 
	 *            传入的参数param里面的格式必须是
	 *            key=id,value=100;key=staffName,value=eoms_test;.....
	 * @return
	 * @throws Exception
	 * @since Version 2.0
	 * 
	 *        <p>
	 *        注意,传入的时间不要是Date类型的字�?,而要求是字符格式�?: yyyy-mm-dd hh:MM:ss
	 *        </p>
	 */
	public int dynamicInsertMap(Map param, String tableName,
			String[] patternStrArr, String sql) throws DataAccessException {
		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			DbOperSupportFactory.getFactory().getDbOperSupport().setPrepValue(
					patternStrArr, ps, param, tableName);
			// return ps.executeUpdate();
			int tmp = ps.executeUpdate();
			if (System.currentTimeMillis() - beginTime >= 2000) {
				System.out.println("dynamicInsertMap_sql:("
						+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			}
			return tmp;
		} catch (SQLException se) {
			System.out.println("dynamicInsertMap_exception:" + sql);
			throw new DataAccessException("dynamicInsertMap insert failure ",
					se);
		} finally {
			DbOper.cleanUp(null, null, ps, null);
		}
	}

	public int[] dynamicInsertMapBatch(List paramMapList, String tableName,
			String[] patternStrArr, String sql) throws DataAccessException {

		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			Map param = null;
			for (int i = 0; i < paramMapList.size(); i++) {
				param = (Map) paramMapList.get(i);
				DbOperSupportFactory.getFactory().getDbOperSupport()
						.setPrepValue(patternStrArr, ps, param, tableName);
				ps.addBatch();
			}
			// return ps.executeBatch();
			int tmp[] = ps.executeBatch();
			if (System.currentTimeMillis() - beginTime >= 2000) {
				System.out.println("dynamicInsertMapBatch_sql:("
						+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			}
			return tmp;
		} catch (SQLException se) {
			System.out.println("dynamicInsertMapBatch_exception:" + sql);
			throw new DataAccessException("dynamicInsertMap insert failure ",
					se);
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	/**
	 * 动�?�修改函�?
	 * 
	 * 
	 * @param obj
	 *            Object �?要修改的包含值的dto对象
	 * @param tableName
	 *            String �?要修改的表名�?
	 * 
	 * @param updatePatternStr
	 *            String �?要修改的update字符�?
	 * 
	 * @param wherePatternStr
	 *            String �?要修改的条件字符�? "ID:id,AND:ID:id,OR:ID:id"
	 * @return int
	 * @throws SQLException
	 */
	public int dynamicUpdate(Object obj, String tableName,
			String updatePatternStr, String wherePatternStr)
			throws SQLException {
		ValidateHelper.notNull(obj);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(updatePatternStr);

		String[] updatePatternStrArr = StringHelper
				.split(updatePatternStr, ",");
		String[] wherePatternStrArr = ValidateHelper
				.validateNotEmpty(wherePatternStr) ? StringHelper.split(
				wherePatternStr, ",") : null;

		if (ValidateHelper.validateNotEmpty(updatePatternStrArr)) {
			String sql = SQLGenerator.generateSql(updatePatternStrArr,
					wherePatternStrArr, tableName, SQLGenerator.UPDATE_TAG);
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			try {
				String[] patternStrArr = null;
				if (ValidateHelper.validateNotEmpty(wherePatternStrArr)) {
					patternStrArr = new String[updatePatternStrArr.length
							+ wherePatternStrArr.length];

					System.arraycopy(updatePatternStrArr, 0, patternStrArr, 0,
							updatePatternStrArr.length);

					System.arraycopy(wherePatternStrArr, 0, patternStrArr,
							updatePatternStrArr.length,
							wherePatternStrArr.length);

				} else {
					patternStrArr = updatePatternStrArr;
				}
				setParValue(patternStrArr, ps, obj);
				// return ps.executeUpdate();
				int tmp = ps.executeUpdate();
				// if (System.currentTimeMillis() - beginTime >= 2000) {
				System.out.println("dynamicUpdate_sql:("
						+ (System.currentTimeMillis() - beginTime) + ")" + sql);
				// }
				return tmp;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("dynamicUpdate_exception:" + sql);
				throw new SQLException(e.getMessage());
			} finally {

				DbOper.cleanUp(null, null, ps, null);
			}

		} else {
			throw new SQLException("SQL UPDATE COLUMN IS NULL");
		}

	}

	/**
	 * @param param
	 *            Map 修改的�??: 格式 key=id,value=100;key=staffName,value=eoms_test
	 * @param tableName
	 *            String �?要修改的表名�?
	 * 
	 * @param updatePatternStr
	 *            String �?要修改的update字符�?
	 * 
	 *            example:
	 *            "ID:id,STAFF_NAME:staffName,STAFF_ID:staffId,ORG_ID:orgId,ORG_NAME:orgName"
	 * @param wherePatternStr
	 *            String �?要修改的条件字符�? "ID:id,AND:ID:id,OR:ID:id"
	 * @author Administrator
	 * @since Version 2.0
	 */
	public int dynamicUpdateMap(Map param, String tableName,
			String[] updatePatternStrArr, String[] wherePatternStrArr,
			String sql) throws DataAccessException {

		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			String[] patternStrArr = null;
			if (ValidateHelper.validateNotEmpty(wherePatternStrArr)) {

				patternStrArr = new String[updatePatternStrArr.length
						+ wherePatternStrArr.length];

				System.arraycopy(updatePatternStrArr, 0, patternStrArr, 0,
						updatePatternStrArr.length);

				System.arraycopy(wherePatternStrArr, 0, patternStrArr,
						updatePatternStrArr.length, wherePatternStrArr.length);
			} else {
				patternStrArr = updatePatternStrArr;
			}
			DbOperSupportFactory.getFactory().getDbOperSupport().setPrepValue(
					patternStrArr, ps, param, tableName);
			// return ps.executeUpdate();
			int tmp = ps.executeUpdate();
			// if (System.currentTimeMillis() - beginTime >= 2000) {
			System.out.println("dynamicUpdateMap_sql:("
					+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			// }
			return tmp;
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("dynamicUpdateMap_exception:" + sql);
			throw new DataAccessException("dynamicInsertMap update failure ",
					se);
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	/**
	 * 
	 * @param param
	 * @param tableName
	 * @param updatePatternStrArr
	 * @param wherePatternStrArr
	 * @param sql
	 * @return
	 * @throws DataAccessException
	 */
	public int dynamicUpdateBySql(String sql) throws DataAccessException {

		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			// return ps.executeUpdate();
			int tmp = ps.executeUpdate();
			// if (System.currentTimeMillis() - beginTime >= 2000) {
			System.out.println("dynamicUpdateBySql_sql:("
					+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			// }
			return tmp;
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("dynamicUpdateBySql_exception:" + sql);
			throw new DataAccessException("dynamicUpdateBySql update failure ",
					se);
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	public int[] dynamicUpdateMapBatch(List paramMapList, String tableName,
			String[] updatePatternStrArr, String[] wherePatternStrArr,
			String sql) throws DataAccessException {

		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			String[] patternStrArr = null;
			if (ValidateHelper.validateNotEmpty(wherePatternStrArr)) {

				patternStrArr = new String[updatePatternStrArr.length
						+ wherePatternStrArr.length];

				System.arraycopy(updatePatternStrArr, 0, patternStrArr, 0,
						updatePatternStrArr.length);

				System.arraycopy(wherePatternStrArr, 0, patternStrArr,
						updatePatternStrArr.length, wherePatternStrArr.length);
			} else {
				patternStrArr = updatePatternStrArr;
			}

			Map param = null;
			for (int i = 0; i < paramMapList.size(); i++) {
				param = (Map) paramMapList.get(i);
				DbOperSupportFactory.getFactory().getDbOperSupport()
						.setPrepValue(patternStrArr, ps, param, tableName);
				ps.addBatch();
			}
			// return ps.executeBatch();
			int[] tmp = ps.executeBatch();
			// if (System.currentTimeMillis() - beginTime >= 2000) {
			System.out.println("dynamicUpdateMapBatch_sql:("
					+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			// }
			return tmp;
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("dynamicUpdateMapBatch_exception:" + sql);
			throw new DataAccessException("dynamicUpdateMap update failure ",
					se);
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	/**
	 * 
	 * 动�?�删除函�?
	 * 
	 * 
	 * @param obj
	 *            Object �?要包含的值对�?
	 * 
	 * @param tableName
	 *            String 表名�?
	 * 
	 * @param patternStr
	 *            String where模式字符�?
	 * 
	 * @return int
	 * @throws SQLException
	 */
	public int dynamicDel(Object obj, String tableName, String wherePatternStr)
			throws SQLException {
		ValidateHelper.hasText(tableName);

		String[] wherePatternStrArr = (ValidateHelper
				.validateNotEmpty(wherePatternStr)) ? StringHelper.split(
				wherePatternStr, ",") : null;

		String sql = SQLGenerator.generateSql(null, wherePatternStrArr,
				tableName, SQLGenerator.DEL_TAG);
		long beginTime = System.currentTimeMillis();
		ps = conn.prepareStatement(sql);
		try {
			setParValue(wherePatternStrArr, ps, obj);
			// return ps.executeUpdate();
			int tmp = ps.executeUpdate();
			if (System.currentTimeMillis() - beginTime >= 2000) {
				System.out.println("dynamicDel_time_too_long:("
						+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			}
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("dynamicDel_exception:" + sql);
			throw new SQLException(e.getMessage());
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	/**
	 * 动�?�删除函�?
	 * 
	 * 
	 * @param param
	 * @param tableName
	 * @param wherePatternStr
	 *            条件格式:ID:id,AND:STAFF_NAME:staffName,OR:STAFF_NAME:staffName
	 * @return
	 * @throws SQLException
	 * @since Version 2.0
	 */
	public int dynamicDelMap(Map param, String tableName,
			String[] wherePatternStrArr, String sql) throws DataAccessException {

		try {
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sql);
			DbOperSupportFactory.getFactory().getDbOperSupport().setPrepValue(
					wherePatternStrArr, ps, param, tableName);
			// return ps.executeUpdate();
			int tmp = ps.executeUpdate();
			if (System.currentTimeMillis() - beginTime >= 2000) {
				System.out.println("dynamicDelMap_time_too_long:("
						+ (System.currentTimeMillis() - beginTime) + ")" + sql);
			}
			return tmp;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("dynamicDelMap_exception:" + sql);
			throw new DataAccessException(e.getMessage());
		} finally {

			DbOper.cleanUp(null, null, ps, null);
		}

	}

	/**
	 * 
	 * @param sqlStr
	 *            StringBuffer
	 * @param param
	 *            Map
	 * @param wherePatternStr
	 *            String
	 * @return Map
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public Map dynamicSelForMap(String sqlStr, Map param, String wherePatternStr)
			throws SQLException, RequiredException {

		List list = dynamicSelForMapList(sqlStr, param, wherePatternStr);

		return (ValidateHelper.validateNotEmpty(list)) ? ((Map) list.iterator()
				.next()) : null;
	}

	/**
	 * 
	 * @param sqlStr
	 *            StringBuffer
	 * @param param
	 *            Map
	 * @param wherePatternStr
	 *            String
	 * @return List
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public List dynamicSelForMapList(String sqlStr, Map param,
			String wherePatternStr) throws SQLException, RequiredException {

		ValidateHelper.hasText(sqlStr);

		String sqlTemp = sqlStr.toUpperCase();

		if (sqlTemp.indexOf(" UNION ") != -1) {
			sqlTemp = StringHelper.substr(sqlTemp, 0, sqlTemp
					.indexOf(" UNION "));
		}

		if (sqlTemp.indexOf(SQL_SELECT_TAG) == -1
				|| sqlTemp.indexOf(SQL_FROM_TAG) == -1) {
			throw new RequiredException("'SELECT OR FROM' requried!");
		}
		// 取得查询结果语句
		String qryColSQL = "";
		if (sqlTemp.indexOf(" " + SQL_DISTINCT_TAG + " ") != -1) {
			qryColSQL = StringHelper.substr(sqlStr, sqlTemp
					.indexOf(SQL_DISTINCT_TAG)
					+ SQL_DISTINCT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		} else {
			qryColSQL = StringHelper.substr(sqlStr, sqlTemp
					.indexOf(SQL_SELECT_TAG)
					+ SQL_SELECT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		}

		sqlTemp = null;

		// 取得对象属�?�名�?
		
		String[] propNoAs = propNameForQryColStr(qryColSQL);
		logger.info("============="+qryColSQL);
		/* 得到动�?�数�? */
		List list = null;
		if (ValidateHelper.validateNotEmpty(propNoAs)) {
			/** 声明查询变量 */
			ResultSet rs = null;
			try {
				/* 查询 */
				 System.out.println("sqlStr="+sqlStr);
				long beginTime = System.currentTimeMillis();
				ps = conn.prepareStatement(sqlStr);

				if (param != null
						&& ValidateHelper.validateNotEmpty(wherePatternStr)) {
					String[] patternStrArr = StringHelper.split(wherePatternStr
							.trim(), ",");
					try {
						setParValue(patternStrArr, ps, param);
					} catch (Exception e) {
						throw new SQLException(e.getMessage());
					}

				}
				rs = ps.executeQuery();
				if (System.currentTimeMillis() - beginTime > 2000) {
					System.out.println("dynamicSelForMapList_time_too_long:"
							+ sqlStr);
				}
				if (rs != null) {
					list = new LinkedList();
					Map tempMap = null;
					while (rs.next()) {
						tempMap = new HashMap();
						for (int i = 0; i < propNoAs.length; i++) {
							if (!tempMap.containsKey(propNoAs[i])) {
								// tempMap.put(propNoAs[i],
								// rs.getObject(propNoAs[i]));
								saveValueToMap(rs, propNoAs[i], tempMap);
							}
						}
						list.add(tempMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("dynamicSelForMapList_exception:" + sqlStr);
				throw new DAOSysException(
						"populateQuery method called failed error detail="
								+ e.getMessage());
			}

			finally {

				cleanUp(rs, null, ps, null);
			}
		}
		return list;
	}

	public List populateQueryByMapNoAs(StringBuffer sqlStr)
			throws RequiredException, SQLException {
		int databaseTag = SQLHelper.CURRENT_DATABASE_TAG;
		if (!ValidateHelper.validateNotNull(sqlStr)) {
			throw new RequiredException("'SQL ' requried!");
		}

		/** 声明查询变量 */
		ResultSet rs = null;
		List list = null;
		try {
			System.out.println("populateQueryByMap_qryColSQL:" + sqlStr);

			/* 查询 */
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sqlStr.toString());
			rs = ps.executeQuery();
			if (System.currentTimeMillis() - beginTime > 3000) {
				System.out
						.println("populateQueryByMap_time_too_long:" + sqlStr);
			}

			/* 得到动�?�数�? */
			if (rs != null) {
				list = new LinkedList();
				Map tempMap = null;

				ResultSetMetaData md = rs.getMetaData();
				int index = md.getColumnCount();
				String[] propNoAs = new String[index];
				int in = 0;
				for (int i = 1; i <= index; i++) {
					String column = md.getColumnName(i);
					propNoAs[in++] = column;
				}
				while (rs.next()) {
					tempMap = new HashMap();
					for (int i = 0; i < propNoAs.length; i++) {
						if (!tempMap.containsKey(propNoAs[i])) {
							// tempMap.put(propNoAs[i],
							// rs.getObject(propNoAs[i]));
							saveValueToMap(rs, propNoAs[i], tempMap);
						}
					}
					list.add(tempMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOSysException(
					"populateQuery method called failed error detail="
							+ e.getMessage());
		} finally {
			cleanUp(rs, cs, ps, null);
		}
		// }
		return list;
	}

	/**
	 * 使用Map进行参数输出的分页查�? 返回的Map的key包括:totalSize�?
	 * 
	 * pageIndex、pageSize、totalRecords、resultList
	 * 
	 * @param sqlStr
	 *            StringBuffer
	 * @param startIndex
	 *            int
	 * @param stepSize
	 *            int
	 * @param databaseTag
	 *            int
	 * @return Map
	 * @throws RequiredException
	 * @throws SQLException
	 */
	public Map populateQueryByMapNoAs(StringBuffer sqlStr, int startIndex,
			int stepSize) throws RequiredException, SQLException {
		int databaseTag = SQLHelper.CURRENT_DATABASE_TAG;
		if (!ValidateHelper.validateNotNull(sqlStr)) {
			throw new RequiredException("'SQL ' requried!");
		}

		String sqlTemp = sqlStr.toString().toUpperCase();

		if (!ValidateHelper.validateNotEmpty(sqlTemp)) {
			throw new RequiredException("'SQL ' is Empty");
		}

		if (sqlTemp.indexOf(SQL_SELECT_TAG) == -1
				|| sqlTemp.indexOf(SQL_FROM_TAG) == -1) {
			throw new RequiredException("'SELECT OR FROM' requried!");
		}
		// 取得查询结果语句
		String qryColSQL = "";
		if (sqlTemp.indexOf(" " + SQL_DISTINCT_TAG + " ") != -1) {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_DISTINCT_TAG)
					+ SQL_DISTINCT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		} else {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_SELECT_TAG)
					+ SQL_SELECT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		}
		sqlTemp = null;

		// 返回的Map
		Map paginMap = new HashMap();

		// 取得分页对象属�?�名�?

		// String[] propNoAs = propNameForQryColStr(qryColSQL);

		// if (ValidateHelper.validateNotEmpty(propNoAs)) {
		/** 声明查询变量 */
		ResultSet rs = null;
		try {

			// 得到�?要查询记录的总数
			int totalRecords = calculateSQLCount(sqlStr.toString());

			int totalSize = -1;
			if (startIndex <= 0) {
				startIndex = 1;
			}
			if (stepSize <= 0) {
				// 如果stepSize�?-1，默认所有记录�??

				stepSize = totalRecords;
			}
			/* 如果totalRecords�?0则表示没有记录被查询 */
			if (totalRecords == 0) {
				totalSize = 0;
				// 赋totalSize变量
				paginMap.put("totalSize", new Integer(totalSize));
				return paginMap;
			} else {
				/* 如果大于0则表示数据不能被完整分配成N个分页，�?要N+1�? */
				if (totalRecords % stepSize > 0) {
					totalSize = totalRecords / stepSize + 1;
				} else {
					totalSize = totalRecords / stepSize;
				}
				// 赋totalSize变量
				paginMap.put("totalSize", new Integer(totalSize));
			}

			int currentSize = 0;

			if (startIndex >= totalSize) {
				startIndex = totalSize;
				currentSize = totalRecords - (startIndex - 1) * stepSize;
			} else {
				currentSize = stepSize;
			}
			// 赋pageIndex变量
			paginMap.put("pageIndex", new Integer(startIndex));
			// 赋pageSize变量
			paginMap.put("pageSize", new Integer(stepSize));
			// 赋totalRecord变量
			paginMap.put("totalRecords", new Integer(totalRecords));

			/** **针对Sybase数据库，调用存储过程查询*** */
			if (databaseTag == SQLHelper.SYBASE_TAG) {
				// 如果是Sybase，用存储过程分页
				conn.setAutoCommit(true);
				cs = conn.prepareCall("exec " + DbOper.PAGI_PROC + "(?,?,?)");
				cs.setInt(1, startIndex);
				cs.setInt(2, stepSize);
				cs.setString(3, sqlStr.toString());
				rs = cs.executeQuery();
			} else {
				// 得到份页SQL
				String sqlPageStr = SQLHelper.populateQuerySQLWithKey(sqlStr
						.toString(), (startIndex - 1) * stepSize, currentSize,
						databaseTag, null);

				/* 查询 */
				long beginTime = System.currentTimeMillis();
				ps = conn.prepareStatement(sqlPageStr);
				rs = ps.executeQuery();
				if (System.currentTimeMillis() - beginTime > 3000) {
					System.out.println("populateQueryByMap_time_too_long:"
							+ sqlPageStr);
				}

			}

			/* 得到动�?�数�? */
			List list = null;
			if (rs != null) {
				list = new LinkedList();
				Map tempMap = null;

				ResultSetMetaData md = rs.getMetaData();
				int index = md.getColumnCount();
				String[] propNoAs = new String[index];
				int in = 0;
				for (int i = 1; i <= index; i++) {
					String column = md.getColumnName(i);
					propNoAs[in++] = column;
				}
				while (rs.next()) {
					tempMap = new HashMap();
					for (int i = 0; i < propNoAs.length; i++) {
						if (!tempMap.containsKey(propNoAs[i])) {
							// tempMap.put(propNoAs[i],
							// rs.getObject(propNoAs[i]));
							saveValueToMap(rs, propNoAs[i], tempMap);
						}
					}
					list.add(tempMap);
				}
			}
			// put查询结果至map
			paginMap.put("resultList", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOSysException(
					"populateQuery method called failed error detail="
							+ e.getMessage());
		}

		finally {

			cleanUp(rs, cs, ps, null);
		}
		// }
		return paginMap;
	}

	/**
	 * 动�?�查询单对象方法(只支持一�?,不支持对象包�?,后续可以支持对象包含)
	 * 
	 * @param sqlStr
	 *            StringBuffer sql语句
	 * @param instanClsPath
	 *            String �?要返回的实例的dto对象的class路径
	 * @return Object
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public Object dynamicSelForObj(StringBuffer sqlStr, String instanClsPath,
			Object obj, String wherePatternStr) throws SQLException,
			RequiredException {

		List list = dynamicSelForList(sqlStr, instanClsPath, obj,
				wherePatternStr);

		return (ValidateHelper.validateNotEmpty(list)) ? (list.iterator()
				.next()) : null;

	}

	/**
	 * 
	 * 动�?�查询复合对象方�?(只支持一�?,不支持对象包�?.后续可以支持对象包含)
	 * 
	 * @param sqlStr
	 *            StringBuffer sql语句
	 * @param instanClsPath
	 *            String �?要返回的实例的dto对象的class路径
	 * @return List
	 * @throws SQLException
	 * @throws RequiredException
	 */
	public List dynamicSelForList(StringBuffer sqlStr, String instanClsPath,
			Object obj, String wherePatternStr) throws SQLException,
			RequiredException {
		ValidateHelper.hasText(sqlStr.toString());
		ValidateHelper.hasText(instanClsPath);

		String sqlTemp = sqlStr.toString().toUpperCase();

		if (sqlTemp.indexOf(SQL_SELECT_TAG) == -1
				|| sqlTemp.indexOf(SQL_FROM_TAG) == -1) {
			throw new RequiredException("'SELECT OR FROM' requried!");
		}
		// 取得查询结果语句
		String qryColSQL = "";
		if (sqlTemp.indexOf(" " + SQL_DISTINCT_TAG + " ") != -1) {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_DISTINCT_TAG)
					+ SQL_DISTINCT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		} else {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_SELECT_TAG)
					+ SQL_SELECT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		}
		sqlTemp = null;

		// 取得对象属�?�名�?

		String[] propNoAs = propNameForQryColStr(qryColSQL);

		/* 得到动�?�数�? */
		List list = null;
		if (ValidateHelper.validateNotEmpty(propNoAs)) {
			/** 声明查询变量 */
			ResultSet rs = null;
			try {
				/* 查询 */
				long beginTime = System.currentTimeMillis();
				ps = conn.prepareStatement(sqlStr.toString());

				if (ValidateHelper.validateNotNull(obj)
						&& ValidateHelper.validateNotEmpty(wherePatternStr)) {
					String[] patternStrArr = StringHelper.split(
							wherePatternStr, ",");
					try {
						setParValue(patternStrArr, ps, obj);
					} catch (Exception e) {
						e.printStackTrace();
						throw new SQLException(e.getMessage());
					}

				}
				rs = ps.executeQuery();
				if (System.currentTimeMillis() - beginTime > 2000) {
					System.out.println("dynamicSelForList_time_too_long:"
							+ sqlStr);
				}
				if (ValidateHelper.validateNotNull(rs)) {
					list = new LinkedList();
					/* save 数据库�?�value到对象中 */
					while (rs.next()) {
						Object instance = ObjectHelper
								.newInstance(instanClsPath);
						ObjectHelper helper = new ObjectHelper(instance);

						for (int i = 0; i < propNoAs.length; i++) {
							saveValue(helper, rs.getObject(propNoAs[i]),
									propNoAs[i]);
						}
						list.add(instance);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("dynamicSelForList_exception:" + sqlStr);
				throw new DAOSysException(
						"populateQuery method called failed error detail="
								+ e.getMessage());
			}

			finally {

				cleanUp(rs, null, ps, null);
			}
		}
		return list;
	}

	/**
	 * 实例化PreparedStatement
	 * 
	 * @param patternStrArr
	 *            String[]
	 * @param ps
	 *            PreparedStatement
	 * @param obj
	 *            Object
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	private void setParValue(String[] patternStrArr, PreparedStatement ps,
			Object obj) throws SQLException, IllegalAccessException,
			IllegalArgumentException {
		ObjectHelper objHelper = new ObjectHelper(obj);
		for (int i = 0; i < patternStrArr.length; i++) {
			String propName = patternStrArr[i].substring(patternStrArr[i]
					.lastIndexOf(":") + 1);

			Field field = objHelper.getField(propName);

			Object fValue = field.get(obj);

			Class fType = field.getType();

			String fTypeStr = StringHelper.toString(fType);

			if (logger.isDebugEnabled()) {
				logger.debug("propName=" + propName);
				logger.debug("fValue=" + fValue);
				logger.debug("fType=" + fType);
				logger.debug("fTypeStr=" + fTypeStr);
			}

			if (fType.isInstance(new Long(0)) || fTypeStr.equals("long")) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.longValueOf(fValue));
			} else if (fType.isInstance(new Integer(0))
					|| fTypeStr.equals("int")) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.integerValueOf(fValue));
			} else if (fType.isInstance(new String(""))) {
				DbOper.setPrepStatementParam(i + 1, ps, StringHelper
						.toString(fValue));
			} else if (fType.isInstance(new Date())) {
				DbOper.setPrepStatementParam(i + 1, ps, (Date) fValue);
			} else if (fType.isInstance(new Double(0.0))
					|| fTypeStr.equals("double")) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.doubleValueOf(fValue));
			} else if (fType.isInstance(new Float(0.0))
					|| fTypeStr.equals("float")) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.floatValueOf(fValue));
			}
		}
	}

	/**
	 * 传入参数是MAP
	 * 
	 * @param patternStrArr
	 *            String[]
	 * @param ps
	 *            PreparedStatement
	 * @param param
	 *            Map
	 * @throws SQLException
	 */
	private void setParValue(String[] patternStrArr, PreparedStatement ps,
			Map param) throws SQLException {

		String propName = null;
		Object fValue = null;
		Class fType = null;

		for (int i = 0; i < patternStrArr.length; i++) {
			propName = patternStrArr[i].trim().substring(
					patternStrArr[i].trim().lastIndexOf(":") + 1);

			fValue = MapUtils.getObject(param, propName);
			fType = fValue.getClass();

			if (fType.isInstance(new Long(0))) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.longValueOf(fValue));
			} else if (fType.isInstance(new Integer(0))) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.integerValueOf(fValue));
			} else if (fType.isInstance(new String(""))) {
				DbOper.setPrepStatementParam(i + 1, ps, StringHelper
						.toString(fValue));
			} else if (fType.isInstance(new Date())) {
				DbOper.setPrepStatementParam(i + 1, ps, (Date) fValue);
			} else if (fType.isInstance(new Double(0.0))) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.doubleValueOf(fValue));
			} else if (fType.isInstance(new Float(0.0))) {
				DbOper.setPrepStatementParam(i + 1, ps, NumberHelper
						.floatValueOf(fValue));
			}
		}
	}

	/**
	 * 内部调用静�?�查�?
	 * 
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return ResultSet
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		// 有效性检�?

		if (!ValidateHelper.validateNotNull(sql)) {
			throw new SQLException("SQL sentence not be initialize");
		}

		// 如果sql是空字符串则返回null.
		if (!ValidateHelper.validateNotEmpty(sql)) {
			return null;
		}
		// 否则返回resultset
		long beginTime = System.currentTimeMillis();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (System.currentTimeMillis() - beginTime > 2000) {
			System.out.println("executeQuery_time_too_long:" + sql);
		}
		DbOper.cleanUp(null, null, ps, null);
		return rs;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		if (conn == null || conn.isClosed()) {
			throw new SQLException("Connection not be initialize");
		}
		ps = conn.prepareStatement(sql);
		return ps;
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持Long
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			Long val) throws SQLException {

		if (ValidateHelper.validateNotNull(val)) {
			ps.setLong(loop, NumberHelper.parseLong(val));
		} else {
			ps.setNull(loop, java.sql.Types.NUMERIC);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持Integer
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			Integer val) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setInt(loop, NumberHelper.parseInt(val));
		} else {
			ps.setNull(loop, java.sql.Types.NUMERIC);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持String
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			String val) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setString(loop, val);
		} else {
			ps.setNull(loop, java.sql.Types.VARCHAR);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持CLOB
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			InputStreamReader val, byte[] b) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setCharacterStream(loop, val, b.length);
		} else {
			ps.setNull(loop, java.sql.Types.VARCHAR);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持date
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			Date val) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setTimestamp(loop, DateHelper.dateToTimeStamp(val));
		} else {
			ps.setNull(loop, java.sql.Types.DATE);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持float
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			Float val) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setFloat(loop, NumberHelper.parseFloat(val));
		} else {
			ps.setNull(loop, java.sql.Types.NUMERIC);
		}
	}

	/**
	 * 重载prepardStatement赋�?�函�? 支持float
	 * 
	 * @param loop
	 *            int
	 * @param ps
	 *            PreparedStatement
	 * @param val
	 *            Long
	 * @throws SQLException
	 */
	public static void setPrepStatementParam(int loop, PreparedStatement ps,
			Double val) throws SQLException {
		if (ValidateHelper.validateNotNull(val)) {
			ps.setDouble(loop, NumberHelper.parseDouble(val));
		} else {
			ps.setNull(loop, java.sql.Types.NUMERIC);
		}
	}

	/**
	 * 内部调用静�?�修�?
	 * 
	 * 
	 * @param conn
	 *            Connection
	 * @param sql
	 *            String
	 * @throws SQLException
	 * @return int
	 */
	public int executeUpdate(String sql) throws SQLException {
		// 有效性检�?

		if (sql == null) {
			throw new SQLException("SQL sentence not be initialize");
		}
		// 如果sql是空则返�?-1,表示插入失败
		if (sql.length() == 0) {
			return -1;
		}
		// 否则返回更新过记�?

		PreparedStatement ps = conn.prepareStatement(sql);
		int update = ps.executeUpdate();
		DbOper.cleanUp(null, null, ps, null);
		return update;

	}

	public void close() throws DataAccessException {
		DbOper.cleanUp(null, null, ps, conn);
	}

	/**
	 * 关闭数据库连�?
	 * 
	 * 
	 * @param rs
	 *            ResultSet
	 * @param cs
	 *            CallableStatement
	 * @param ps
	 *            PreparedStatement
	 * @param conn
	 *            Connection
	 */
	public static void cleanUp(ResultSet rs, CallableStatement cs,
			PreparedStatement ps, Connection conn) {
		try {

		}

		finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (cs != null) {
					cs.close();
					cs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}

				if (conn != null) {

					if (!conn.isClosed()) {
						conn.close();
					}
					conn = null;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

	}

	/* 分页函数中常�? */
	private static final String TOTAL_SIZE_METHOD_NAME = "setTotalSize";

	private static final String PAGE_INDEX_METHOD_NAME = "setPageIndex";

	private static final String PAGE_SIZE_METHOD_NAME = "setPageSize";

	private static final String TOTAL_RECORDS_METHOD_NAME = "setTotalRecords";

	private static final String SQL_SELECT_TAG = "SELECT";

	private static final String SQL_FROM_TAG = " FROM ";

	private static final String SQL_DISTINCT_TAG = "DISTINCT";

	private static final String SQL_AS_LOWER_TAG = " as ";

	private static final String SQL_AS_UPPER_TAG = " AS ";

	/**
	 * 通用分页反射方法 author:dawn 定义得分页VO�?具有
	 * totalSize,pageSize,pageIndex,totalRecords 这些字段并且有相应得get�?
	 * set方法。还应具有分页结果数据存储得vo数组
	 * 
	 * @param sqlStr
	 *            StringBuffer �?要查询得原始sql语句
	 * @param startIndex
	 *            int �?始页�?
	 * 
	 * @param stepSize
	 *            int 步长
	 * @param voPageClsPath
	 *            String 分页vo得class路径
	 * @param fieldName
	 *            String 分页结果数组得字段名�?
	 * 
	 * @param databaseTag
	 *            int 数据库类�?
	 * 
	 * @return Object 分页VO.
	 * @throws RequiredException
	 *             如果没有Order by 子句将抛出错�?
	 * 
	 * @throws SQLException
	 *             例子�? PlanDomainDataPage.java
	 * 
	 * 
	 *             private int totalSize = 0; private int pageSize = 0; private
	 *             int pageIndex = 0; private int totalRecords = 0; private
	 *             PlanDomain[] planDomainArr = null;
	 * 
	 *             sql语句写法: 1.对每个查找得column�?要用as
	 *             与其分页结果存储得对象里面得字段对应。比如planDomain这个返回得对象里面有domainId，对应得select查询
	 *             得column是DOMAIN_ID,则写法是DOMAIN_ID as domainId�? 2.�?要加入order
	 *             by。表明以�?么为标量对查询结果进行分�? 例子�?
	 * 
	 * 
	 *             select DOMAIN_ID as domainId,DOMAIN_NAME as domainName,ORG_ID
	 *             as orgId,COMMENT as comment,state as state,STATE_DATE as
	 *             stateDate,CREATE_DATE as createDate from PLAN_DOMAIN order by
	 *             domainId
	 * 
	 *             调用�? populateQuery("select DOMAIN_ID as domainId,DOMAIN_NAME
	 *             as domainName,ORG_ID as orgId,COMMENT as comment,state as
	 *             state,STATE_DATE as stateDate,CREATE_DATE as createDate from
	 *             PLAN_DOMAIN order by domainId",
	 *             1,2,"com.ztesoft.eoms.planmanager.vo.PlanDomainDataPage"
	 *             ,"planDomainArr");
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public Object populateQuery(StringBuffer sqlStr, int startIndex,
			int stepSize, String voPageClsPath, String fieldName)
			throws RequiredException, SQLException {
		int databaseTag = SQLHelper.CURRENT_DATABASE_TAG;
		if (!ValidateHelper.validateNotNull(sqlStr)) {
			throw new RequiredException("'SQL ' requried!");
		}

		String sqlTemp = sqlStr.toString().toUpperCase();

		if (!ValidateHelper.validateNotEmpty(sqlTemp)) {
			throw new RequiredException("'SQL ' is Empty");
		}

		if (sqlTemp.indexOf(SQL_SELECT_TAG) == -1
				|| sqlTemp.indexOf(SQL_FROM_TAG) == -1) {
			throw new RequiredException("'SELECT OR FROM' requried!");
		}
		// 取得查询结果语句
		String qryColSQL = "";
		if (sqlTemp.indexOf(" " + SQL_DISTINCT_TAG + " ") != -1) {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_DISTINCT_TAG)
					+ SQL_DISTINCT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		} else {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_SELECT_TAG)
					+ SQL_SELECT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		}
		sqlTemp = null;
		// 取得分页对象属�?�名�?

		String[] propNoAs = propNameForQryColStr(qryColSQL);
		// 实例化分页类
		Object voPage = null;

		if (ValidateHelper.validateNotEmpty(propNoAs)) {
			/** 声明查询变量 */
			ResultSet rs = null;
			try {

				// 初始化分页类
				voPage = ObjectHelper.newInstance(voPageClsPath);
				ObjectHelper objHelper = new ObjectHelper(voPage);
				// 得到�?要查询记录的总数
				int totalRecords = calculateSQLCount(sqlStr.toString());
				int totalSize = -1;
				if (startIndex <= 0) {
					startIndex = 1;
				}
				if (stepSize <= 0) {
					// 如果stepSize�?-1，默认所有记录�??

					stepSize = totalRecords;
				}
				/* 如果totalRecords�?0则表示没有记录被查询 */
				if (totalRecords == 0) {
					totalSize = 0;
					// 赋totalSize变量
					objHelper.callMethod(TOTAL_SIZE_METHOD_NAME,
							new Class[] { int.class },
							new Object[] { new Integer(totalSize) });
					return voPage;

				} else {
					/* 如果大于0则表示数据不能被完整分配成N个分页，�?要N+1�? */
					if (totalRecords % stepSize > 0) {
						totalSize = totalRecords / stepSize + 1;
					} else {
						totalSize = totalRecords / stepSize;
					}
					// 赋totalSize变量
					objHelper.callMethod(TOTAL_SIZE_METHOD_NAME,
							new Class[] { int.class },
							new Object[] { new Integer(totalSize) });

				}

				int currentSize = 0;

				if (startIndex >= totalSize) {
					startIndex = totalSize;
					currentSize = totalRecords - (startIndex - 1) * stepSize;
				} else {
					currentSize = stepSize;
				}
				// 赋pageIndex变量
				objHelper.callMethod(this.PAGE_INDEX_METHOD_NAME,
						new Class[] { int.class }, new Object[] { new Integer(
								startIndex) });
				// 赋pageSize变量
				objHelper.callMethod(PAGE_SIZE_METHOD_NAME,
						new Class[] { int.class }, new Object[] { new Integer(
								stepSize) });
				// 赋totalRecord变量
				objHelper.callMethod(TOTAL_RECORDS_METHOD_NAME,
						new Class[] { int.class }, new Object[] { new Integer(
								totalRecords) });
				// 得到�?要存储分页对象数组中单个对象类路�?

				String instanClsPath = findFieldCls(fieldName, objHelper);
				/** 进行存储分页对象实例�? */
				if (ValidateHelper.validateNotEmpty(instanClsPath)) {
					/* 查询 */
					if (databaseTag == SQLHelper.SYBASE_TAG) {
						// 如果是Sybase，用存储过程分页
						conn.setAutoCommit(true);
						cs = conn.prepareCall("exec " + DbOper.PAGI_PROC
								+ "(?,?,?)");
						cs.setInt(1, startIndex);
						cs.setInt(2, stepSize);
						cs.setString(3, sqlStr.toString());
						rs = cs.executeQuery();
					} else {
						// 得到份页SQL
						String sqlPageStr = SQLHelper.populateQuerySQLWithKey(
								sqlStr.toString(), (startIndex - 1) * stepSize,
								currentSize, databaseTag, null);
						long beginTime = System.currentTimeMillis();
						ps = conn.prepareStatement(sqlPageStr);
						rs = ps.executeQuery();
						if (System.currentTimeMillis() - beginTime > 2000) {
							System.out.println("populateQuery_time_too_long:"
									+ sqlPageStr);
						}
					}

					/* 得到动�?�数�? */
					List list = null;
					if (rs != null) {
						list = new LinkedList();
						/* save 数据库�?�value到对象中 */
						while (rs.next()) {
							Object instance = ObjectHelper
									.newInstance(instanClsPath);
							ObjectHelper helper = new ObjectHelper(instance);

							for (int i = 0; i < propNoAs.length; i++) {
								saveValue(helper, rs.getObject(propNoAs[i]),
										propNoAs[i]);
							}
							list.add(instance);
						}
					}
					// 赋对象叔�?

					Object objArr = null;
					if (list != null && list.size() != 0) {
						// 创建对象数组，只有这样创建的数据才能被Method的invoke方法调用
						Object[] objectArr = (Object[]) list
								.toArray(new Object[list.size()]);

						objArr = ObjectHelper.toArray(objectArr, Class
								.forName(instanClsPath));
					}
					// 得到对象数组调用方法
					String mtdName = patchSetFuncName(fieldName);
					Field f = objHelper.getField(fieldName);
					Method mtd = objHelper.getMethod(mtdName, new Class[] { f
							.getType() });
					// 进行分页对象赋�??

					mtd.invoke(voPage, new Object[] { objArr });
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOSysException(
						"populateQuery method called failed error detail="
								+ e.getMessage());
			}

			finally {

				cleanUp(rs, cs, ps, null);
			}
		}
		return voPage;
	}

	/**
	 * 使用Map进行参数输出的分页查�? 返回的Map的key包括:totalSize�?
	 * 
	 * pageIndex、pageSize、totalRecords、resultList
	 * 
	 * @param sqlStr
	 *            StringBuffer
	 * @param startIndex
	 *            int
	 * @param stepSize
	 *            int
	 * @param databaseTag
	 *            int
	 * @return Map
	 * @throws RequiredException
	 * @throws SQLException
	 */
	public Map populateQueryByMap(StringBuffer sqlStr, int startIndex,
			int stepSize) throws RequiredException, SQLException {
		int databaseTag = SQLHelper.CURRENT_DATABASE_TAG;
		if (!ValidateHelper.validateNotNull(sqlStr)) {
			throw new RequiredException("'SQL ' requried!");
		}

		String sqlTemp = sqlStr.toString().toUpperCase();

		if (!ValidateHelper.validateNotEmpty(sqlTemp)) {
			throw new RequiredException("'SQL ' is Empty");
		}

		if (sqlTemp.indexOf(SQL_SELECT_TAG) == -1
				|| sqlTemp.indexOf(SQL_FROM_TAG) == -1) {
			throw new RequiredException("'SELECT OR FROM' requried!");
		}
		// 取得查询结果语句
		String qryColSQL = "";
		if (sqlTemp.indexOf(" " + SQL_DISTINCT_TAG + " ") != -1) {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_DISTINCT_TAG)
					+ SQL_DISTINCT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		} else {
			qryColSQL = StringHelper.substr(sqlStr.toString(), sqlTemp
					.indexOf(SQL_SELECT_TAG)
					+ SQL_SELECT_TAG.length(), sqlTemp.indexOf(SQL_FROM_TAG));

		}
		sqlTemp = null;

		// 返回的Map
		Map paginMap = new HashMap();

		// 取得分页对象属�?�名�?

		String[] propNoAs = propNameForQryColStr(qryColSQL);

		if (ValidateHelper.validateNotEmpty(propNoAs)) {
			/** 声明查询变量 */
			ResultSet rs = null;
			try {

				// 得到�?要查询记录的总数
				int totalRecords = calculateSQLCount(sqlStr.toString());

				int totalSize = -1;
				if (startIndex <= 0) {
					startIndex = 1;
				}
				if (stepSize <= 0) {
					// 如果stepSize�?-1，默认所有记录�??

					stepSize = totalRecords;
				}
				/* 如果totalRecords�?0则表示没有记录被查询 */
				if (totalRecords == 0) {
					totalSize = 0;
					// 赋totalSize变量
					paginMap.put("totalSize", new Integer(totalSize));
					return paginMap;
				} else {
					/* 如果大于0则表示数据不能被完整分配成N个分页，�?要N+1�? */
					if (totalRecords % stepSize > 0) {
						totalSize = totalRecords / stepSize + 1;
					} else {
						totalSize = totalRecords / stepSize;
					}
					// 赋totalSize变量
					paginMap.put("totalSize", new Integer(totalSize));
				}

				int currentSize = 0;

				if (startIndex >= totalSize) {
					startIndex = totalSize;
					currentSize = totalRecords - (startIndex - 1) * stepSize;
				} else {
					currentSize = stepSize;
				}
				// 赋pageIndex变量
				paginMap.put("pageIndex", new Integer(startIndex));
				// 赋pageSize变量
				paginMap.put("pageSize", new Integer(stepSize));
				// 赋totalRecord变量
				paginMap.put("totalRecords", new Integer(totalRecords));

				/** **针对Sybase数据库，调用存储过程查询*** */
				if (databaseTag == SQLHelper.SYBASE_TAG) {
					// 如果是Sybase，用存储过程分页
					conn.setAutoCommit(true);
					cs = conn.prepareCall("exec " + DbOper.PAGI_PROC
							+ "(?,?,?)");
					cs.setInt(1, startIndex);
					cs.setInt(2, stepSize);
					cs.setString(3, sqlStr.toString());
					rs = cs.executeQuery();
				} else {
					// 得到份页SQL
					String sqlPageStr = SQLHelper.populateQuerySQLWithKey(
							sqlStr.toString(), (startIndex - 1) * stepSize,
							currentSize, databaseTag, null);

					/* 查询 */
					long beginTime = System.currentTimeMillis();
					ps = conn.prepareStatement(sqlPageStr);
					rs = ps.executeQuery();
					if (System.currentTimeMillis() - beginTime > 3000) {
						System.out.println("populateQueryByMap_time_too_long:"
								+ sqlPageStr);
					}

				}

				/* 得到动�?�数�? */
				List list = null;
				if (rs != null) {
					list = new LinkedList();
					Map tempMap = null;
					while (rs.next()) {
						tempMap = new HashMap();
						for (int i = 0; i < propNoAs.length; i++) {
							if (!tempMap.containsKey(propNoAs[i])) {
								// tempMap.put(propNoAs[i],
								// rs.getObject(propNoAs[i]));
								saveValueToMap(rs, propNoAs[i], tempMap);
							}
						}
						list.add(tempMap);
					}
				}
				// put查询结果至map
				paginMap.put("resultList", list);
			} catch (Exception e) {
				e.printStackTrace();
				throw new DAOSysException(
						"populateQuery method called failed error detail="
								+ e.getMessage());
			}

			finally {

				cleanUp(rs, cs, ps, null);
			}
		}
		return paginMap;
	}

	/**
	 * count�?�? sql语句结果得数�?
	 * 
	 * 
	 * @param sql
	 *            String
	 * @return int
	 * @throws SQLException
	 * 
	 */

	private int calculateSQLCount(String sql) throws SQLException {

		return SQLHelper.getCount(sql, conn);
	}

	/**
	 * 赋�?��?�对象中
	 * 
	 * @param helper
	 *            ObjectHelper
	 * @param val
	 *            Object
	 * @param fldName
	 *            String
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void saveValue(ObjectHelper helper, Object val, String fldName)
			throws InvocationTargetException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException, SecurityException {
		// 得到字段set方法名称
		String setMtdName = patchSetFuncName(fldName);
		// 得到字段在类中字段对�?

		Field fld = helper.getField(fldName);
		if (fld == null) {
			throw new NoSuchMethodException(setMtdName + " not exists");

		}
		// 得到字段类型
		Class type = fld.getType();
		// 得到赋�?�方�?

		Method mtd = helper.getMethod(setMtdName, new Class[] { type });
		// 得到赋予值得对象
		Object instance = helper.getObject();

		/**
		 * 匹配字段类型，从resultSet中取出相应类型得�?
		 */
		// if column is Integer
		if (fld.getType().isInstance(new Integer(0))
				|| fld.getType().toString().equals("int")) {
			mtd.invoke(instance, new Object[] { NumberHelper
					.integerValueOf(val) });

		} else

		// if column type is Long
		if (type.isInstance(new Long(0))
				|| fld.getType().toString().equals("long")) {

			mtd
					.invoke(instance, new Object[] { NumberHelper
							.longValueOf(val) });

		} else

		// if column type is double
		if (type.isInstance(new Double(0.0))
				|| fld.getType().toString().equals("double")) {
			mtd.invoke(instance,
					new Object[] { NumberHelper.doubleValueOf(val) });

		} else

		// if column type is java.util.Date
		if (type.isInstance(new java.util.Date())) {
			mtd.invoke(instance, new Object[] { DateHelper.dateValueOf(val) });
		} else

		// if column type is String
		if (type.isInstance(new String(""))) {
			mtd.invoke(instance, new Object[] { StringHelper.toString(val) });
		} else

		// if column type is Float
		if (type.isInstance(new Float(0.0))
				|| fld.getType().toString().equals("float")) {
			mtd.invoke(instance,
					new Object[] { NumberHelper.floatValueOf(val) });
		}
	}

	private void saveValueToMap(ResultSet pRs, String pColAlias, Map pMap)
			throws Exception {
		Object _value = pRs.getObject(pColAlias);
		// 如果是日期：直接用getObject(...)只获取到年月日，获取不到时分�?(Weblogic+Oracle的情况下)
		if (_value instanceof java.util.Date) {
			_value = DateHelper.timestampToDate(pRs.getTimestamp(pColAlias));
		}
		
		// 如果数据是clob类型的要转成string************03年5月3日************************** 新加类型
		if (_value != null
				&& (_value.getClass().getName().equals("oracle.sql.CLOB") || _value
						.getClass().getName().equals(
								"weblogic.jdbc.wrapper.Clob_oracle_sql_CLOB"))) {
			_value = ClobHelper.clobToString(_value);
		}
		///**********************************************************************
		pMap.put(pColAlias, _value);
	}

	/**
	 * 从qryColSQL中抽取属性名�?
	 * 
	 * 
	 * @param fromStr
	 *            String
	 * @return String[]
	 */
	private String[] propNameForQryColStr(String fromStr) {
		// 例子：select a.id as id,a.name as name
		// 1.得到<array><string>a.id as id<string><string>a.name as
		// name</string></array>
		fromStr = elimitQuota(fromStr);
		String[] colNameHasAS = StringHelper.split(fromStr, ",");

		String[] colNameNoAS = null;
		// 2.从a.id as id得到id,a.name 得到name
		if (ValidateHelper.validateNotEmpty(colNameHasAS)) {

			colNameNoAS = new String[colNameHasAS.length];

			for (int i = 0; i < colNameHasAS.length; i++) {
				colNameHasAS[i] = colNameHasAS[i].trim();
				colNameNoAS[i] = new String();
				// 匹配大小�?" as "，截出属性�??

				if (colNameHasAS[i].indexOf(SQL_AS_LOWER_TAG) != -1) {
					// 如果是小�?" as "
					colNameNoAS[i] = StringHelper.substr(colNameHasAS[i],
							SQL_AS_LOWER_TAG);
				} else if (colNameHasAS[i].indexOf(SQL_AS_UPPER_TAG) != -1) {
					// 如果是大�?" AS "
					colNameNoAS[i] = StringHelper.substr(colNameHasAS[i],
							SQL_AS_UPPER_TAG);
				} else {
					// 用列名做属�?��??

					colNameNoAS[i] = colNameHasAS[i];
				}

			}
		}
		colNameHasAS = null;

		return colNameNoAS;
	}

	/**
	 * 得到字段对应的class名称
	 * 
	 * @param fieldName
	 *            String
	 * @param objHelper
	 *            ObjectHelper
	 * @return String
	 */
	private String findFieldCls(String fieldName, ObjectHelper objHelper)
			throws NoSuchFieldException {
		Field field = objHelper.getField(fieldName);
		String fieldInstClass;
		if (!ValidateHelper.validateNotNull(field)) {
			throw new NoSuchFieldException("NOT EXIST FIELD");
		} else {
			String typeName = field.getType().getName();
			if (typeName.indexOf("[L") != -1 && typeName.indexOf(";") != -1) {
				fieldInstClass = StringHelper.substr(typeName, "[L", ";");
			} else {
				fieldInstClass = typeName;
			}
			typeName = null;
		}

		return fieldInstClass;

	}

	/**
	 * 得到字段的set方法�?
	 * 
	 * 
	 * @param colName
	 *            String
	 * @return String
	 */
	private String patchSetFuncName(String colName) {
		String temp = colName;
		char firChar = temp.charAt(0);
		temp = temp.substring(1);
		firChar = Character.toUpperCase(firChar);
		temp = "set" + firChar + temp;
		return temp;
	}

	/**
	 * 使用Map进行参数输出的分页查�? 返回的Map的key包括:totalSize�?
	 * 
	 * pageIndex、pageSize、totalRecords、resultList
	 * 
	 * @param sqlStr
	 *            StringBuffer
	 * @param startIndex
	 *            int
	 * @param stepSize
	 *            int
	 * @param databaseTag
	 *            int
	 * @return Map
	 * @throws RequiredException
	 * @throws SQLException
	 * @version v2.0
	 */
	public Map paginateQueryByMap(StringBuffer sqlStr, int startIndex,
			int stepSize, int totalRecords) throws RequiredException,
			SQLException {

		Map paginMap = new HashMap();
		ResultSet rs = null;
		try {
			// 得到�?要查询记录的总数
			if (totalRecords <= 0) {
				totalRecords = calculateSQLCount(sqlStr.toString());
			}
			// System.out.println("totalRecords="+totalRecords);
			// 取得分页对象属�?�名�?

			String[] propNoAs = colNameForQryStr(sqlStr.toString());

			// 赋pageIndex变量
			paginMap.put("pageIndex", new Integer(startIndex));
			// 赋pageSize变量
			paginMap.put("pageSize", new Integer(stepSize));
			// String sql = sqlStr.toString().replaceAll("'","''");
			// System.out.println("pageSql="+sql);
			conn.setAutoCommit(true);
			cs = conn.prepareCall("exec " + DbOper.PAGI_PROC_V2 + "(?,?,?,?)");
			cs.setInt(1, startIndex);
			cs.setInt(2, stepSize);
			cs.setInt(3, totalRecords);
			cs.setString(4, sqlStr.toString());
			cs.executeQuery();
			rs = cs.getResultSet();

			paginMap.put("totalRecords", new Integer(totalRecords));
			/* 得到动�?�数�? */
			List list = null;
			if (rs != null) {
				list = new LinkedList();
				Map tempMap = null;
				while (rs.next()) {
					tempMap = new HashMap();
					for (int i = 0; i < propNoAs.length; i++) {
						if (!tempMap.containsKey(propNoAs[i])) {
							saveValueToMap(rs, propNoAs[i], tempMap);
						}
					}
					list.add(tempMap);
				}
			}
			// put查询结果至map
			paginMap.put("resultList", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOSysException(
					"paginateQueryByMapV2 method called failed error detail="
							+ e.getMessage());
		} finally {
			cleanUp(rs, cs, ps, null);
		}

		return paginMap;
	}

	/**
	 * 从qryColSQL中抽取属性名�?
	 * 
	 * 
	 * @param fromStr
	 *            String
	 * @return String[]
	 */
	private String[] colNameForQryStr(String sqlStr) {
		// 例子：select a.id as id,a.name as name
		// 1.得到<array><string>a.id as id<string><string>a.name as
		// name</string></array>
		if (sqlStr == null || sqlStr.trim().equals("")) {
			return null;
		}
		StringBuffer sqltmp = new StringBuffer(sqlStr);
		String fromStr = sqlStr.substring(
				sqltmp.toString().toLowerCase().indexOf("select") + 6,
				sqltmp.toString().toLowerCase().indexOf(" from ")).trim();

		String[] colNameHasAS = StringHelper.split(fromStr, ",");

		String[] colNameNoAS = null;
		// 2.从a.id as id得到id,a.name 得到name
		if (ValidateHelper.validateNotEmpty(colNameHasAS)) {

			colNameNoAS = new String[colNameHasAS.length];

			for (int i = 0; i < colNameHasAS.length; i++) {
				colNameHasAS[i] = colNameHasAS[i].trim();
				colNameNoAS[i] = new String();
				// 匹配大小�?" as "，截出属性�??

				if (colNameHasAS[i].indexOf(SQL_AS_LOWER_TAG) != -1) {
					// 如果是小�?" as "
					colNameNoAS[i] = StringHelper.substr(colNameHasAS[i],
							SQL_AS_LOWER_TAG);
				} else if (colNameHasAS[i].indexOf(SQL_AS_UPPER_TAG) != -1) {
					// 如果是大�?" AS "
					colNameNoAS[i] = StringHelper.substr(colNameHasAS[i],
							SQL_AS_UPPER_TAG);
				} else {
					// 用列名做属�?��??

					colNameNoAS[i] = colNameHasAS[i];
				}

			}
		}
		colNameHasAS = null;

		return colNameNoAS;
	}

	/**
	 * 把一段有括号的字符串去除括号段，拼接返回 如：a.PLAN_TASKORDER_ID
	 * planTaskorderId,TO_CHAR(END_DATE,'MM') endDate 返回: a.PLAN_TASKORDER_ID
	 * planTaskorderId,TO_CHAR endDate,...
	 * 
	 * @param str
	 * @return
	 */
	private String elimitQuota(String str) {
		if (str == null || str.trim().equals("")) {
			return str;
		}
		StringBuffer tempStr = new StringBuffer();

		if (str.indexOf("(") > 0) {
			String[] leftEl = StringHelper.split(str, "(");
			if (leftEl != null && leftEl.length > 0) {
				for (int t = 0; t < leftEl.length; t++) {
					if (leftEl[t].indexOf(")") > 0) {
						tempStr.append(StringHelper.substr(leftEl[t], ")"));
					} else {
						tempStr.append(leftEl[t]);
					}
				}
			}
		} else {
			tempStr.append(str.trim());
		}
		return tempStr.toString();
	}

	// ---------添加普通查询------------------------------------------------//

	public List dynamicSelForMapListByNew(String sqlStr) throws SQLException,
			RequiredException {

		/* 得到动�?�数�? */
		List list = null;

		/** 声明查询变量 */
		ResultSet rs = null;
		try {
			/* 查询 */
			// System.out.println("sqlStr="+sqlStr);
			long beginTime = System.currentTimeMillis();
			ps = conn.prepareStatement(sqlStr);

			rs = ps.executeQuery();
			if (System.currentTimeMillis() - beginTime > 2000) {
				System.out.println("dynamicSelForMapList_time_too_long:"
						+ sqlStr);
			}
			if (rs != null) {
				list = new LinkedList();
				Map tempMap = null;
				while (rs.next()) {
					tempMap = new HashMap();
					ResultSetMetaData lineInfo = rs.getMetaData();
					int columnCount = lineInfo.getColumnCount();
					for (int i = 1; i <= columnCount; i++) {
						tempMap.put(lineInfo.getColumnName(i).toLowerCase(), rs
								.getObject(i) == null ? "-1" : rs.getString(i));
					}
					list.add(tempMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("dynamicSelForMapList_exception:" + sqlStr);
			throw new DAOSysException(
					"populateQuery method called failed error detail="
							+ e.getMessage());
		}

		finally {

			cleanUp(rs, null, ps, null);
		}

		return list;
	}
}
