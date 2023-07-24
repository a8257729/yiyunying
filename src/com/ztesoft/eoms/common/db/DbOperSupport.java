/**
 *
 */
package com.ztesoft.eoms.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.ztesoft.eoms.exception.DataAccessException;

/**
 * @author dawn
 */
public interface DbOperSupport {

	public DbTableInfo[] qryAllTableInfo() throws DataAccessException;

	public DbTableInfo qryTableInfo(String tableName)
			throws DataAccessException;

	public DbTableInfo qryTableColumnInfo(DbTableInfo tableInfo)
			throws DataAccessException;

	public void setPrepValue(String[] patternStrArr, PreparedStatement ps,
			Map param, String tableName) throws DataAccessException;

   public Long sequence(String tableName,Connection conn)throws DataAccessException;
   
   public void preparedInsert(Map param, String tableName,String keyName,DbOper dbOper)throws DataAccessException;
   
   public void lastedInsert(Map param,String tableName,String sequneceColumnName,DbOper dbOper)throws DataAccessException;
   
   public void preparedInsertBatch(List param,String tableName,String keyName,DbOper dbOper)throws DataAccessException;
   
   public void lastedInsetBatch(List param,String tableName,String keyName,DbOper dbOper)throws DataAccessException;
   
   
   public String[] replaceSequenceDefinition(String[] patternStrArr) throws DataAccessException;
}
