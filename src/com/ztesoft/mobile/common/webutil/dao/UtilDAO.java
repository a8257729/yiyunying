package com.ztesoft.mobile.common.webutil.dao;

import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface UtilDAO extends BaseDAO {

	 public List getTextValue(String tablename,String textfieldname,String valuefiledname)throws DataAccessException;
}
