package com.ztesoft.mobile.system.dao;

import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface MenuDAO extends BaseDAO {
	public Map getSubMenusById(int moduleId)throws Exception ;
	public Map getMenuListByModule(int moduleId) throws DataAccessException ;
}
