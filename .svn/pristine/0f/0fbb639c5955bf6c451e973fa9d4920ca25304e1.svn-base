package com.ztesoft.eoms.common.dao.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.eoms.common.dao.ibatis.BaseIbatisDAOImpl;
import com.ztesoft.eoms.common.db.DbOper;
import com.ztesoft.eoms.common.db.DbOperDecorate;
import com.ztesoft.eoms.exception.DataAccessException;
import com.ztesoft.eoms.exception.RequiredException;

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
public class BaseJDBCDAOImpl extends BaseIbatisDAOImpl {
	// 粒度提示：PAGLOCK、NOLOCK、ROWLOCK、TABLOCK 或 TABLOCKX。

	// 隔离级别提示：HOLDLOCK、NOLOCK、READCOMMITTED、REPEATABLEREAD 或 SERIALIZABLE。
	// NOLOCK、READUNCOMMITTED 和 READPAST 表提示不能用于将进行删除、插入或更新操作的表。

	public static final int LOCKFLAG_NOLOCK = 0;

	public static final int LOCKFLAG_ROWLOCK = 1;

	public static final int LOCKFLAG_PAGLOCK = 2;

	public static final int LOCKFLAG_TABLOCK = 3;

	protected BaseJDBCDAOImpl() {

	}

	/**
	 * 根据传入tableName,代插入的数据对象,提供插入操作,供继承类调用
	 * 
	 * @param tableName
	 * @param param
	 * @return
	 * @throws SQLException
	 * @since Version 2.0
	 */
	protected int dynamicInsertByMap(Map param, String tableName,
			String patternStr) throws DataAccessException {
		Connection conn = null;
		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicInsertMap(param, tableName, patternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " Insert_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);

		}

	}

	/**
	 * 根据传入tableName,代插入的数据对象,提供插入操作,供继承类调用
	 * 
	 * @param paramMapList
	 *            List
	 * @param tableName
	 *            String
	 * @param patternStr
	 *            String
	 * @return int[]
	 * @throws Exception
	 */
	protected int[] dynamicInsertBatchByMap(List paramMapList,
			String tableName, String patternStr) throws DataAccessException {
		Connection conn = null;
		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicInsertMapBatch(paramMapList, tableName,
					patternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " InsertBatch_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}

	}

	/**
	 * 更新
	 * 
	 * @param param
	 * @param tableName
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	protected int dynamicUpdateByMap(Map param, String tableName,
			String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;
		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicUpdateMap(param, tableName, updatePatternStr,
					wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " Update_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);

		}

	}

	/**
	 * 
	 * @param UpdateSqlStr
	 * @return
	 * @throws DataAccessException
	 */
	protected int dynamicUpdateBySql(String UpdateSqlStr)
			throws DataAccessException {
		if (UpdateSqlStr == null || UpdateSqlStr.trim().equals("")) {
			throw new DataAccessException("UpdateSqlStr can't be empty!");
		}
		Connection conn = null;
		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicUpdateBySql(UpdateSqlStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(" Update_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);

		}

	}

	/**
	 * 
	 * @param paramMapList
	 * @param tableName
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	protected int[] dynamicUpdateBatchByMap(List paramMapList,
			String tableName, String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;

		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicUpdateMapBatch(paramMapList, tableName,
					updatePatternStr, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " UpdateBatch_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}

	}

	/**
	 * 批量更新，此更新方法支持显式提示lock的粒度，由于当前故障模块会出现死锁情况，提供此方法暂时支持减少锁的粒度
	 * 
	 * @param paramMapList
	 * @param tableName
	 * @param lockFlag
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	protected int[] dynamicUpdateBatchByMap(List paramMapList,
			String tableName, int lockFlag, String updatePatternStr,
			String wherePatternStr) throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;

		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicUpdateMapBatch(paramMapList, tableName,
					lockFlag, updatePatternStr, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " UpdateBatch_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}

	}

	/**
	 * 更新，此更新方法支持显式提示lock的粒度，由于当前故障模块会出现死锁情况，提供此方法暂时支持减少锁的粒度
	 * 
	 * @param param
	 * @param tableName
	 * @param lockFlag
	 * @param updatePatternStr
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 * @since 2007.12.06
	 */
	protected int dynamicUpdateByMap(Map param, String tableName, int lockFlag,
			String updatePatternStr, String wherePatternStr)
			throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;
		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicUpdateMap(param, tableName, lockFlag,
					updatePatternStr, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " Update_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);

		}

	}

	/**
	 * 删除方法
	 * 
	 * @param param
	 * @param tableName
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 */
	protected int dynamicDeleteByMap(Map param, String tableName,
			String wherePatternStr) throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;

		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicDelMap(param, tableName, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " Delete_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}
	}

	/**
	 * 删除方法，主要提是支持显式声明锁粒度语法
	 * 
	 * @param param
	 * @param tableName
	 * @param lockFlag
	 * @param wherePatternStr
	 * @return
	 * @throws DataAccessException
	 * @since 2007.12.06
	 * 
	 */
	protected int dynamicDeleteByMap(Map param, String tableName, int lockFlag,
			String wherePatternStr) throws DataAccessException {
		if (wherePatternStr == null || wherePatternStr.trim().equals("")) {
			throw new DataAccessException("wherePatternStr can't be empty!");
		}
		Connection conn = null;

		try {
			conn = getConnection();
			DbOperDecorate dbOper = new DbOperDecorate(conn);
			return dbOper.dynamicDelMap(param, tableName, lockFlag,
					wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(tableName + " Delete_exception "
					+ ex.getMessage());
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}
	}

	protected Map dynamicQueryObjectByMap(String sqlStr, Map param,
			String wherePatternStr) throws DataAccessException {
		Connection conn = null;
		try {
			conn = getConnection();
			DbOper dbOper = new DbOper(conn);
			return dbOper.dynamicSelForMap(sqlStr, param, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage(), ex);
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}

	}

	protected List dynamicQueryListByMap(String sqlStr, Map param,
			String wherePatternStr) throws DataAccessException {
		Connection conn = null;
		try {
			conn = getConnection();
			DbOper dbOper = new DbOper(conn);
			return dbOper.dynamicSelForMapList(sqlStr, param, wherePatternStr);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage(), ex);
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}

	}

	protected Map populateQueryByMap(StringBuffer sqlStr, int startIndex,
			int stepSize) throws DataAccessException {
		Connection conn = null;
		DbOper dbOper = null;
		try {
			conn = getConnection();
			dbOper = new DbOper(conn);
			return dbOper.populateQueryByMap(sqlStr, startIndex, stepSize);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage(), ex);
		} finally {
			if (dbOper != null) {
				dbOper.destroy();
				dbOper=null;
			}
			DbOper.cleanUp(null, null, null, conn);
		}
	}

	/**
	 * 通用分页反射方法 author:dawn 定义得分页VO需具有 totalSize,pageSize,pageIndex,totalRecords
	 * 这些字段并且有相应得get和 set方法。还应具有分页结果数据存储得vo数组
	 * 
	 * @param sqlStr
	 *            StringBuffer 需要查询得原始sql语句
	 * @param startIndex
	 *            int 开始页数
	 * @param stepSize
	 *            int 步长
	 * @param voPageClsPath
	 *            String 分页vo得class路径
	 * @param fieldName
	 *            String 分页结果数组得字段名称
	 * @param databaseTag
	 *            int 数据库类型
	 * @return Object 分页VO.
	 * @throws RequiredException
	 *             如果没有Order by 子句将抛出错误
	 * @throws SQLException
	 *             例子： PlanDomainDataPage.java
	 * 
	 * 
	 * private int totalSize = 0; private int pageSize = 0; private int
	 * pageIndex = 0; private int totalRecords = 0; private PlanDomain[]
	 * planDomainArr = null;
	 * 
	 * sql语句写法: 1.对每个查找得column需要用as
	 * 与其分页结果存储得对象里面得字段对应。比如planDomain这个返回得对象里面有domainId，对应得select查询
	 * 得column是DOMAIN_ID,则写法是DOMAIN_ID as domainId。 2.需要加入order
	 * by。表明以什么为标量对查询结果进行分页 例子：
	 * 
	 * select DOMAIN_ID as domainId,DOMAIN_NAME as domainName,ORG_ID as
	 * orgId,COMMENT as comment,state as state,STATE_DATE as
	 * stateDate,CREATE_DATE as createDate from PLAN_DOMAIN order by domainId
	 * 
	 * 调用： populateQuery("select DOMAIN_ID as domainId,DOMAIN_NAME as
	 * domainName,ORG_ID as orgId,COMMENT as comment,state as state,STATE_DATE
	 * as stateDate,CREATE_DATE as createDate from PLAN_DOMAIN order by
	 * domainId",
	 * 1,2,"com.ztesoft.eoms.planmanager.vo.PlanDomainDataPage","planDomainArr");
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/**
	 * 这个是通用的分页查询方法，不分开sybase和sqlserver，统一用存储过程实现
	 */
	protected Map paginateQueryByMap(StringBuffer sqlStr, int startIndex,
			int stepSize, int totalRecords) throws DataAccessException {
		Connection conn = null;
		try {
			conn = getConnection();
			DbOper dbOper = new DbOper(conn);
			return dbOper.paginateQueryByMap(sqlStr, startIndex, stepSize,
					totalRecords);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new DataAccessException(ex.getMessage(), ex);
		} finally {
			DbOper.cleanUp(null, null, null, conn);
		}
	}

	protected Object populateQuery(StringBuffer sqlStr, int startIndex,
			int stepSize, String voPageClsPath, String fieldName)
			throws RequiredException, SQLException {
		DbOper dbOper = null;
		try {
			dbOper = new DbOper(getConnection());
			return dbOper.populateQuery(sqlStr, startIndex, stepSize,
					voPageClsPath, fieldName);
		} finally {
			dbOper.close();
		}

	}
	 public Integer getSeqId(String seqName) throws SQLException {
		    if (seqName == null || seqName.equals("")) {
		    	return null;
		    }
		    int rtValue = 0;
		    String sql = "select " + seqName + ".NEXTVAL as ID from dual";
		    Connection conn = null;
		    PreparedStatement ps = null;
		    ResultSet rs = null;
		    try {
	            conn = getConnection();
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();
	            while (rs.next()) {
	            	rtValue = rs.getInt("ID");
	            }
	            return new Integer(rtValue);
		    } finally {
		    	DbOper.cleanUp(rs, null, ps, conn);
		    }
	    }
	public void cleanUp(Connection conn, CallableStatement cs,
			PreparedStatement ps, ResultSet rs) {
		DbOper.cleanUp(rs, cs, ps, conn);
	}

}
