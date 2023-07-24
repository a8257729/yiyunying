package com.ztesoft.mobile.common.dao;


import org.apache.log4j.Logger;

import com.ztesoft.mobile.common.dao.jdbc.BaseJDBCDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.DAOHelper;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class BaseDAOImpl extends BaseJDBCDAOImpl{
	public static final Logger logger = Logger.getLogger(BaseDAOImpl.class);
	/**
	 * ×éºÏWHEREÓï¾ä
	 * @param qrySql
	 * @param whereSql
	 * @return
	 */
	protected String getWhereSql(String qrySql,String whereSql){
		
		if(qrySql == null || "".equals(qrySql))	return "";
		
		if(qrySql.toUpperCase().indexOf("WHERE") > -1){
			return " AND " + whereSql;
		}
		
		return " WHERE " + whereSql;
	}
	public static Long getPKFromMem(String tableName, int num, int addNum) throws
    DataAccessException {
		try {
		    return DAOHelper.getPKFromMem(tableName, addNum);
		} catch (Exception e) {
		    throw new DataAccessException(
		            "Exception: get sequence from Mem for " + tableName);
		}
	}
	 public static Long getPKFromMem(String tableName, int num) throws
	     DataAccessException {
	 return getPKFromMem(tableName, num, 1);
	}
}
