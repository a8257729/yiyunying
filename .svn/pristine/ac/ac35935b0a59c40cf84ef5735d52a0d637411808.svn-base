package com.ztesoft.mobile.etl.dao;

import org.apache.log4j.Logger;

import com.zterc.uos.helpers.DAOSysException;
import com.ztesoft.iom.common.IOMDAOUtils;

public class EtlRuleDAOFactory {

	private static final Logger _logger = Logger
			.getLogger(EtlRuleDAOFactory.class);

	public static final String EtlRuleDAO_CLASS = "com.ztesoft.sqm.etl.dao.EtlRuleDAO";

	public static EtlRuleDAO getDAO() throws DAOSysException {
		EtlRuleDAO dao = null;
		try {
			_logger.debug("className="
					+ (EtlRuleDAO_CLASS + IOMDAOUtils.getDbType()));
			
			dao = (EtlRuleDAO) Class.forName(
					EtlRuleDAO_CLASS + IOMDAOUtils.getDbType()).newInstance();
		} catch (Exception ex) {
			throw new DAOSysException("EtlRuleDAOFactory.getDAO: "
					+ "Exception while getting DAO type : \n" + ex.getMessage());
		}
		return dao;
	}
}
