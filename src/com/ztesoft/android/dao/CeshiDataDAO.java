package com.ztesoft.android.dao;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.exception.DataAccessException;

public interface CeshiDataDAO extends BaseDAO{
	

	public String getData(String params)throws DataAccessException;
	public void updateData(String params) throws DataAccessException;
	
}
