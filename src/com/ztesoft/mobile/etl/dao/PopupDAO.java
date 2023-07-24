package com.ztesoft.mobile.etl.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface PopupDAO extends BaseDAO {
	public Map selOrgAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public Map selJobAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;

	public Map selStaffAsPage(Map paramMap, int startIndex, int stepSize)
			throws DataAccessException;
}
