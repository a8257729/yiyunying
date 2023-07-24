package com.ztesoft.mobile.v2.dao.app;

import java.util.Map;
import java.util.List;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface FuncMenuRelaDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	//删除菜单时级联删除菜单与功能直接的关系表；
	public void deleteFuncMenuRela(Map paramMap)throws DataAccessException;
	
	//通过菜单的配置表修改MOBILE_FUNC_MENU_RELA
	 public void updateFuncMenuRela(Map paramMap)throws DataAccessException;
}
