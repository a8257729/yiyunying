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
	 * ���ݻỰID������ϵͳID �ж��Ƿ��Ѵ��ڼ�¼
	 * */
	public int selLogCount(Map paramMap) throws DataAccessException;
	/**
	 * ���·�����ϵͳ��¼���˳�ʱ�䣬���Ż����˳�ʱ��
	 * */
	public void updateLogoutDate(Map paramMap) throws DataAccessException;
}
