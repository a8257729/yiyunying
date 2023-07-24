package com.ztesoft.mobile.v2.dao.app;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface MobileAppHisDAO extends BaseDAO{
	public void insert(Map paramMap)throws DataAccessException;
	public void update(Map paramMap)throws DataAccessException;
	public void delete(Map paramMap)throws DataAccessException;
	public Map selById(Map paramMap)throws DataAccessException;
	public List selAll(Map paramMap)throws DataAccessException;
	public Map selByConn(Map paramMap) throws DataAccessException;
	public Map selMaxIdByConn(Map paramMap) throws DataAccessException;
	//分页查询功能列表
	public Map getAppFunMap(Map paramMap) throws DataAccessException;
	//级联删除 通过appid 删除fun
	
	public void deleteFun(Map paramMap) throws DataAccessException;
	//修改app表的下载次数
	public void updateDownCount (Map paramMap) throws DataAccessException;
}

