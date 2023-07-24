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
	// ������ʾ��PAGLOCK��NOLOCK��ROWLOCK��TABLOCK �� TABLOCKX��

	// ���뼶����ʾ��HOLDLOCK��NOLOCK��READCOMMITTED��REPEATABLEREAD �� SERIALIZABLE��
	// NOLOCK��READUNCOMMITTED �� READPAST ����ʾ�������ڽ�����ɾ�����������²����ı�

	public static final int LOCKFLAG_NOLOCK = 0;

	public static final int LOCKFLAG_ROWLOCK = 1;

	public static final int LOCKFLAG_PAGLOCK = 2;

	public static final int LOCKFLAG_TABLOCK = 3;

	protected BaseJDBCDAOImpl() {

	}

	/**
	 * ���ݴ���tableName,����������ݶ���,�ṩ�������,���̳������
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
	 * ���ݴ���tableName,����������ݶ���,�ṩ�������,���̳������
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
	 * ����
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
	 * �������£��˸��·���֧����ʽ��ʾlock�����ȣ����ڵ�ǰ����ģ����������������ṩ�˷�����ʱ֧�ּ�����������
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
	 * ���£��˸��·���֧����ʽ��ʾlock�����ȣ����ڵ�ǰ����ģ����������������ṩ�˷�����ʱ֧�ּ�����������
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
	 * ɾ������
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
	 * ɾ����������Ҫ����֧����ʽ�����������﷨
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
	 * ͨ�÷�ҳ���䷽�� author:dawn ����÷�ҳVO����� totalSize,pageSize,pageIndex,totalRecords
	 * ��Щ�ֶβ�������Ӧ��get�� set��������Ӧ���з�ҳ������ݴ洢��vo����
	 * 
	 * @param sqlStr
	 *            StringBuffer ��Ҫ��ѯ��ԭʼsql���
	 * @param startIndex
	 *            int ��ʼҳ��
	 * @param stepSize
	 *            int ����
	 * @param voPageClsPath
	 *            String ��ҳvo��class·��
	 * @param fieldName
	 *            String ��ҳ���������ֶ�����
	 * @param databaseTag
	 *            int ���ݿ�����
	 * @return Object ��ҳVO.
	 * @throws RequiredException
	 *             ���û��Order by �Ӿ佫�׳�����
	 * @throws SQLException
	 *             ���ӣ� PlanDomainDataPage.java
	 * 
	 * 
	 * private int totalSize = 0; private int pageSize = 0; private int
	 * pageIndex = 0; private int totalRecords = 0; private PlanDomain[]
	 * planDomainArr = null;
	 * 
	 * sql���д��: 1.��ÿ�����ҵ�column��Ҫ��as
	 * �����ҳ����洢�ö���������ֶζ�Ӧ������planDomain������صö���������domainId����Ӧ��select��ѯ
	 * ��column��DOMAIN_ID,��д����DOMAIN_ID as domainId�� 2.��Ҫ����order
	 * by��������ʲôΪ�����Բ�ѯ������з�ҳ ���ӣ�
	 * 
	 * select DOMAIN_ID as domainId,DOMAIN_NAME as domainName,ORG_ID as
	 * orgId,COMMENT as comment,state as state,STATE_DATE as
	 * stateDate,CREATE_DATE as createDate from PLAN_DOMAIN order by domainId
	 * 
	 * ���ã� populateQuery("select DOMAIN_ID as domainId,DOMAIN_NAME as
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
	 * �����ͨ�õķ�ҳ��ѯ���������ֿ�sybase��sqlserver��ͳһ�ô洢����ʵ��
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
