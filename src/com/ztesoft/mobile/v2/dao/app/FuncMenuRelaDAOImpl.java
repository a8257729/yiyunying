package com.ztesoft.mobile.v2.dao.app;

import java.util.Map;
import com.ztesoft.mobile.common.dao.BaseDAO;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
public class FuncMenuRelaDAOImpl extends BaseDAOImpl implements FuncMenuRelaDAO {
private static final String TABLE_NAME = "MOBILE_FUNC_MENU_RELA";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "APP_FUNC_ID:appFuncId,MENU_CONFIG_ID:menuConfigId,BUILD_TIME:buildTime,STATE:state,STATE_DATE:stateDate,FUNC_MENU_RELA:funcMenuRela";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("funcMenuRela", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APP_FUNC_ID:appFuncId,MENU_CONFIG_ID:menuConfigId,BUILD_TIME:buildTime,STATE:state,STATE_DATE:stateDate,FUNC_MENU_RELA:funcMenuRela";
		String wherePatternStr = "FUNC_MENU_RELA:funcMenuRela";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "FUNC_MENU_RELA:funcMenuRela";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APP_FUNC_ID AS appFuncId,  MENU_CONFIG_ID AS menuConfigId,  BUILD_TIME AS buildTime,  STATE AS state,  STATE_DATE AS stateDate,  FUNC_MENU_RELA AS funcMenuRela FROM MOBILE_FUNC_MENU_RELA WHERE FUNC_MENU_RELA=?";
		String wherePatternStr = "FUNC_MENU_RELA:funcMenuRela";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APP_FUNC_ID AS appFuncId,  MENU_CONFIG_ID AS menuConfigId,  BUILD_TIME AS buildTime,  STATE AS state,  STATE_DATE AS stateDate,  FUNC_MENU_RELA AS funcMenuRela FROM MOBILE_FUNC_MENU_RELA";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public void deleteFuncMenuRela(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub 通过menuConfig删除关系表中对应的数据
		String wherePatternStr = "MENU_CONFIG_ID:menuConfigId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public void updateFuncMenuRela(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		String sql="update MOBILE_FUNC_MENU_RELA";
		//通过menuConfigId修改MOBILE_FUNC_MENU_RELA的appFuncId
		if(paramMap.get("appFuncId")!=null &&!paramMap.get("appFuncId").equals("")){
			sql=sql+" set app_func_id= "+paramMap.get("appFuncId") +"where MENU_CONFIG_ID="+paramMap.get("menuConfigId");
		} 
		
		dynamicUpdateBySql(sql);
	}
}
