package com.ztesoft.mobile.etl.dao;

import org.apache.log4j.Logger;

import com.zterc.uos.helpers.DAOSysException;
import com.ztesoft.iom.common.IOMDAOUtils;

public class ScheduleSequDAOFactory {

	private static final Logger _logger = Logger.getLogger(ScheduleSequDAOFactory.class );
	public static final String DataSourceDAO_DAO_CLASS = "com.ztesoft.mobile.etl.dao.ScheduleSequDAO";
	public static ScheduleSequDAO getDAO() throws DAOSysException {
		ScheduleSequDAO dao = null;
        try {
            _logger.debug( "className=" + (DataSourceDAO_DAO_CLASS + IOMDAOUtils.getDbType()) );
            dao = (ScheduleSequDAO) Class.forName(DataSourceDAO_DAO_CLASS + IOMDAOUtils.getDbType()).newInstance();
        }
        catch (Exception ex) {
            throw new DAOSysException(
                "DataSourceDAOFactory.getDAO:  Exception while getting DAO type :"+  ex.getMessage());
        }
        return dao;
    }
}
