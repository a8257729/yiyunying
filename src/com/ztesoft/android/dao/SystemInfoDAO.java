package com.ztesoft.android.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface SystemInfoDAO extends BaseDAO{
	

	public void insert(JSONObject device);
	public List selAll(Map paramMap)throws DataAccessException;
	public void insertStaffInfo(JSONObject jsonData)throws DataAccessException;
	public void updateStaffInfo(String params) throws DataAccessException;
	public void updateStaffImsi(String staffId,String imsi);

}
