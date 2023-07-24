/**
 *
 */
package com.ztesoft.mobile.common.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.ztesoft.mobile.common.dao.jdbc.BaseJDBCDAOImpl;
import com.ztesoft.mobile.common.helper.StringHelper;
import com.ztesoft.mobile.common.helper.ValidateHelper;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * @author dawn
 *
 */
public class DbOperDecorate {

	private DbOper dbOper;

	public DbOperDecorate(DbOper dbOper) throws SQLException {

		this.dbOper = dbOper;
	}

	public DbOperDecorate(Connection conn) throws SQLException {
		this.dbOper = new DbOper(conn);

	}

	public int dynamicInsertMap(Map param, String tableName, String patternStr)
			throws DataAccessException {

		ValidateHelper.notNull(param);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(patternStr);
		int _return = -1;
		String[] patternStrArr = StringHelper.split(patternStr, ",");

		if (patternStr.indexOf("@@SEQ") != -1) {
			String target = finderSEQColumn(patternStrArr);
			if (!target.endsWith(":@@SEQ")) {
				throw new DataAccessException(
						"FORMAT FAILED SEQUENCE DEFINITION FAILED :"
								+ patternStr);
			}
			String keyName = target.substring(0, target.lastIndexOf(":"));

			DbOperSupportFactory.getFactory().getDbOperSupport()
					.preparedInsert(param, tableName, keyName, dbOper);
			patternStrArr = DbOperSupportFactory.getFactory()
					.getDbOperSupport()
					.replaceSequenceDefinition(patternStrArr);

			for (int i = 0; i < patternStrArr.length; i++) {
				// System.out.println(patternStrArr[i]);
			}

			String sql = SQLCache.getInsertSQL(tableName, patternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {

				if (ValidateHelper.validateNotEmpty(patternStrArr)) {
					sql = SQLGenerator.generateSql(patternStrArr, null,
							tableName, SQLGenerator.INSERT_TAG);
					SQLCache.putInsertSQL(tableName, patternStr, sql);
				}
			}

			_return = dbOper.dynamicInsertMap(param, tableName, patternStrArr,
					sql);
			DbOperSupportFactory.getFactory().getDbOperSupport().lastedInsert(
					param, tableName, keyName, dbOper);

		} else {
			String sql = SQLCache.getInsertSQL(tableName, patternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {

				if (ValidateHelper.validateNotEmpty(patternStrArr)) {
					sql = SQLGenerator.generateSql(patternStrArr, null,
							tableName, SQLGenerator.INSERT_TAG);
					SQLCache.putInsertSQL(tableName, patternStr, sql);
				}
			}

			_return = dbOper.dynamicInsertMap(param, tableName, patternStrArr,
					sql);
		}

		return _return;
	}

	
	/**
	 *
	 * @param param
	 * @param tableName
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	public int dynamicUpdateMap(Map param, String tableName,
			String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		ValidateHelper.notNull(param);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(updatePatternStr);

		String[] updatePatternStrArr = StringHelper
				.split(updatePatternStr, ",");
		String[] wherePatternStrArr = ValidateHelper
				.validateNotEmpty(wherePatternStr) ? StringHelper.split(
				wherePatternStr, ",") : null;

		if (ValidateHelper.validateNotEmpty(updatePatternStrArr)) {
			String sql = SQLCache.getUpdateSQL(tableName, updatePatternStr,
					wherePatternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {
				sql = SQLGenerator.generateSql(updatePatternStrArr,
						wherePatternStrArr, tableName, SQLGenerator.UPDATE_TAG);
				SQLCache.putUpdateSQL(tableName, updatePatternStr,
						wherePatternStr, sql);
			}
			return dbOper.dynamicUpdateMap(param, tableName,
					updatePatternStrArr, wherePatternStrArr, sql);
		} else {
			throw new DataAccessException(
					"dynamicUpdateMap update failure SQL UPDATE COLUMN IS Invalid");
		}

	}

	/**
	 *
	 * @param UpdateSqlStr
	 * @return
	 * @throws DataAccessException
	 */
	public int dynamicUpdateBySql(String UpdateSqlStr)
			throws DataAccessException {
		return dbOper.dynamicUpdateBySql(UpdateSqlStr);

	}

	public int[] dynamicUpdateMapBatch(List paramMapList, String tableName,
			String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		ValidateHelper.notEmpty(paramMapList);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(updatePatternStr);

		String[] updatePatternStrArr = StringHelper
				.split(updatePatternStr, ",");
		String[] wherePatternStrArr = ValidateHelper
				.validateNotEmpty(wherePatternStr) ? StringHelper.split(
				wherePatternStr, ",") : null;

		if (ValidateHelper.validateNotEmpty(updatePatternStrArr)) {

			String sql = SQLCache.getUpdateSQL(tableName, updatePatternStr,
					wherePatternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {
				sql = SQLGenerator.generateSql(updatePatternStrArr,
						wherePatternStrArr, tableName, SQLGenerator.UPDATE_TAG);
				SQLCache.putUpdateSQL(tableName, updatePatternStr,
						wherePatternStr, sql);
			}
			return dbOper.dynamicUpdateMapBatch(paramMapList, tableName,
					updatePatternStrArr, wherePatternStrArr, sql);
		} else {
			throw new DataAccessException(
					"dynamicUpdateMap update failure SQL UPDATE COLUMN IS Invalid");
		}
	}

	/**
	 * 批量更新，支持声明式的锁粒度编码
	 *
	 * @param paramMapList
	 * @param tableName
	 * @param lockFlag
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	public int[] dynamicUpdateMapBatch(List paramMapList, String tableName,
			int lockFlag, String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		ValidateHelper.notEmpty(paramMapList);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(updatePatternStr);

		String[] updatePatternStrArr = StringHelper
				.split(updatePatternStr, ",");
		String[] wherePatternStrArr = ValidateHelper
				.validateNotEmpty(wherePatternStr) ? StringHelper.split(
				wherePatternStr, ",") : null;

		if (ValidateHelper.validateNotEmpty(updatePatternStrArr)) {

			String sql = SQLCache.getUpdateSQL(tableName, updatePatternStr,
					wherePatternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {
				sql = SQLGenerator.generateSql(updatePatternStrArr,
						wherePatternStrArr, tableName, SQLGenerator.UPDATE_TAG);
				SQLCache.putUpdateSQL(tableName, updatePatternStr,
						wherePatternStr, sql);
			}
			// 改编sql语句，把lockFlag标签属性加上
			StringBuffer slqWtihFlag = new StringBuffer();
                        slqWtihFlag.append(sql.substring(0, sql.indexOf(tableName.trim())+ tableName.length()));
			if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_ROWLOCK) {
				slqWtihFlag.append(" WITH (ROWLOCK) ");
			} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_PAGLOCK) {
				slqWtihFlag.append(" WITH (PAGLOCK) ");
			} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_TABLOCK) {
				slqWtihFlag.append(" WITH (TABLOCK) ");
			}
			slqWtihFlag.append(sql.substring(sql.indexOf(tableName)
					+ tableName.length()));

			return dbOper.dynamicUpdateMapBatch(paramMapList, tableName,
					updatePatternStrArr, wherePatternStrArr, slqWtihFlag
							.toString());
		} else {
			throw new DataAccessException(
					"dynamicUpdateMap update failure SQL UPDATE COLUMN IS Invalid");
		}
	}

	public int dynamicDelMap(Map param, String tableName, String wherePatternStr)
			throws DataAccessException {

		ValidateHelper.hasText(tableName);

		String[] wherePatternStrArr = (ValidateHelper
				.validateNotEmpty(wherePatternStr)) ? StringHelper.split(
				wherePatternStr, ",") : null;
		String sql = SQLCache.getDeleteSQL(tableName, wherePatternStr);
		if (!ValidateHelper.validateNotEmpty(sql)) {

			sql = SQLGenerator.generateSql(null, wherePatternStrArr, tableName,
					SQLGenerator.DEL_TAG);
			SQLCache.putDeleteSQL(tableName, wherePatternStr, sql);
		}

		return dbOper.dynamicDelMap(param, tableName, wherePatternStrArr, sql);
	}

	/**
	 * 删除，此方法支持显式提示lock的粒度，由于当前故障模块会出现死锁情况，提供此方法暂时支持减少锁的粒度
	 *
	 * @param param
	 * @param tableName
	 * @param lockFlag
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	public int dynamicDelMap(Map param, String tableName, int lockFlag,
			String wherePatternStr) throws DataAccessException {

		ValidateHelper.hasText(tableName);

		String[] wherePatternStrArr = (ValidateHelper
				.validateNotEmpty(wherePatternStr)) ? StringHelper.split(
				wherePatternStr, ",") : null;
		String sql = SQLCache.getDeleteSQL(tableName, wherePatternStr);
		if (!ValidateHelper.validateNotEmpty(sql)) {

			sql = SQLGenerator.generateSql(null, wherePatternStrArr, tableName,
					SQLGenerator.DEL_TAG);
			SQLCache.putDeleteSQL(tableName, wherePatternStr, sql);
		}
		// 改编sql语句，把lockFlag标签属性加上
		StringBuffer slqWtihFlag = new StringBuffer();
                slqWtihFlag.append(sql.substring(0, sql.indexOf(tableName.trim())+ tableName.length()));
		if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_ROWLOCK) {
			slqWtihFlag.append(" WITH (ROWLOCK) ");
		} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_PAGLOCK) {
			slqWtihFlag.append(" WITH (PAGLOCK) ");
		} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_TABLOCK) {
			slqWtihFlag.append(" WITH (TABLOCK) ");
		}
		slqWtihFlag.append(sql.substring(sql.indexOf(tableName)
				+ tableName.length()));

		return dbOper.dynamicDelMap(param, tableName, wherePatternStrArr,
				slqWtihFlag.toString());
	}

	private String finderSEQColumn(String[] patternStrArr) {
		for (int i = 0; i < patternStrArr.length; i++) {
			if (patternStrArr[i].indexOf("@@SEQ") != -1) {

				return patternStrArr[i];
			}

		}
		return null;

	}

	/**
	 * 更新，此更新方法支持显式提示lock的粒度，由于当前故障模块会出现死锁情况，提供此方法暂时支持减少锁的粒度
	 *
	 * @param param
	 * @param tableName
	 * @param lockFlag
	 *            声明锁的粒度
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	public int dynamicUpdateMap(Map param, String tableName, int lockFlag,
			String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		ValidateHelper.notNull(param);
		ValidateHelper.hasText(tableName);
		ValidateHelper.hasText(updatePatternStr);

		String[] updatePatternStrArr = StringHelper
				.split(updatePatternStr, ",");
		String[] wherePatternStrArr = ValidateHelper
				.validateNotEmpty(wherePatternStr) ? StringHelper.split(
				wherePatternStr, ",") : null;

		if (ValidateHelper.validateNotEmpty(updatePatternStrArr)) {
			String sql = SQLCache.getUpdateSQL(tableName, updatePatternStr,
					wherePatternStr);
			if (!ValidateHelper.validateNotEmpty(sql)) {
				sql = SQLGenerator.generateSql(updatePatternStrArr,
						wherePatternStrArr, tableName, SQLGenerator.UPDATE_TAG);
				SQLCache.putUpdateSQL(tableName, updatePatternStr,
						wherePatternStr, sql);
			}
			// 改编sql语句，把lockFlag标签属性加上
			StringBuffer slqWtihFlag = new StringBuffer();
			slqWtihFlag.append(sql.substring(0, sql.indexOf(tableName.trim())+ tableName.length()));
			if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_ROWLOCK) {
				slqWtihFlag.append(" WITH (ROWLOCK) ");
			} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_PAGLOCK) {
				slqWtihFlag.append(" WITH (PAGLOCK) ");
			} else if (lockFlag == BaseJDBCDAOImpl.LOCKFLAG_TABLOCK) {
				slqWtihFlag.append(" WITH (TABLOCK) ");
			}
			slqWtihFlag.append(sql.substring(sql.indexOf(tableName)
					+ tableName.length()));
			return dbOper.dynamicUpdateMap(param, tableName,
					updatePatternStrArr, wherePatternStrArr, slqWtihFlag
							.toString());
		} else {
			throw new DataAccessException(
					"dynamicUpdateMap update failure SQL UPDATE COLUMN IS Invalid");
		}

	}
}
