package com.ztesoft.mobile.etl.dao;

import org.apache.log4j.Logger;

import com.zterc.uos.helpers.DAOSysException;
import com.ztesoft.iom.common.IOMDAOUtils;

public class DataSourceDAOFactory {
	private static final Logger _logger = Logger.getLogger(
			DataSourceDAOFactory.class );
	public static final String DataSourceDAO_DAO_CLASS =
        "com.ztesoft.mobile.etl.dao.DataSourceDAO";
	public static DataSourceDAO getDAO() throws DAOSysException {
		DataSourceDAO dao = null;
        try {
            _logger.debug( "className=" + (DataSourceDAO_DAO_CLASS + IOMDAOUtils.getDbType()) );
            dao = (DataSourceDAO) Class.forName(DataSourceDAO_DAO_CLASS + IOMDAOUtils.getDbType()).newInstance();
        }
        catch (Exception ex) {
            throw new DAOSysException(
                "DataSourceDAOFactory.getDAO: "
                + "Exception while getting DAO type : \n"
                + ex.getMessage());
        }
        return dao;
    }
}
