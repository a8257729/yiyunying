package com.ztesoft.android.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAO;

public interface BaseDataDAO extends BaseDAO{

	public Map getDataByMap(Map params);
	public List getDataByList(Map params);
	public String getDataByStr(Map params);
	public String getDataByStr(String teachName, String jsonParams,Map params);
}
