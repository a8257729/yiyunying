package com.ztesoft.mobile.system.dao;

import java.util.Map;
import java.util.List;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;


public interface OuterSysLoginLogDAO extends BaseDAO {
	public void insert(Map paramMap) throws DataAccessException;

	public void update(Map paramMap) throws DataAccessException;

	public void delete(Map paramMap) throws DataAccessException;

	public List selAll(Map paramMap) throws DataAccessException;

	public Map selById(Map paramMap) throws DataAccessException;
	/**
	 * 根据会话ID，访问系统ID 判断是否已存在记录
	 * */
	public int selLogCount(Map paramMap) throws DataAccessException;
	/**
	 * 更新访问外系统记录的退出时间，即门户的退出时间
	 * */
	public void updateLogoutDate(Map paramMap) throws DataAccessException;
}
