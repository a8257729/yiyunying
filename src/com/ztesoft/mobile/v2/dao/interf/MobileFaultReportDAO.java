package com.ztesoft.mobile.v2.dao.interf;


import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface MobileFaultReportDAO extends BaseDAO {
    public Map selReportInfo(Map paramMap) throws DataAccessException ;
    public List selReportTitle(Map paramMap)throws DataAccessException;
    public List selReportData(Map paramMap)throws DataAccessException;
    public List selReportParam(Map paramMap)throws DataAccessException;
    public List selReportField(Map paramMap)throws DataAccessException;
    public List selReportFormula(Map paramMap)throws DataAccessException;
    public Map selReportDimensionInfo(Map paramMap) throws DataAccessException ;
    public List selProduct() throws DataAccessException ;
}
