/**
 *
 */
package com.ztesoft.eoms.common.db;

import com.ztesoft.eoms.common.helper.DAOHelper;

/**
 * @author dawn
 *
 */
public class DbOperSupportFactory {

	private DbOperSupportFactory() {

		if (DAOHelper.getDatabaseType().toUpperCase().equals("MSSQL")) {

			dbOperSupport = new DbOperSupportForMSSQL();
		}
                else if(DAOHelper.getDatabaseType().toUpperCase().equals("SYBASE")){
                       dbOperSupport = new DbOperSupportForSYBASE();
                }
                else if(DAOHelper.getDatabaseType().toUpperCase().equals("ORACLE")){
                       dbOperSupport = new DbOperSupportForORACLE();
                }


	}

	private  DbOperSupport dbOperSupport;

	private static DbOperSupportFactory _factory = new DbOperSupportFactory();

	public DbOperSupport getDbOperSupport() {
		return dbOperSupport;

	}

	public static DbOperSupportFactory getFactory() {

		return _factory;

	}
}
