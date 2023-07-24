package com.ztesoft.mobile.systemMobMgr.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileFiledTypeDAOImpl extends BaseDAOImpl implements MobileFiledTypeDAO {
	private static final String TABLE_NAME = "MOBILE_FILED_TYPE";
		public void insert(Map paramMap) throws DataAccessException {
			String patternStr = "TYPE_ID:typeId,TYPE_NAME:typeName,TYPE_LABLE:typeLable";
			Long nextId = getPKFromMem(TABLE_NAME, 9);
			paramMap.put("typeId", nextId);
			dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		}
		public void update(Map paramMap) throws DataAccessException {
			String updatePatternStr = "TYPE_ID:typeId,TYPE_NAME:typeName,TYPE_LABLE:typeLable";
			String wherePatternStr = "TYPE_ID:typeId";
			dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
		}
		public void delete(Map paramMap) throws DataAccessException {
			String wherePatternStr = "TYPE_ID:typeId";
			dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
		}
		public Map selById(Map paramMap) throws DataAccessException {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT   TYPE_ID AS typeId,  TYPE_NAME AS typeName,  TYPE_LABLE AS typeLable FROM MOBILE_FILED_TYPE");
			System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
			return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
		}
		public List selAll(Map paramMap) throws DataAccessException {
			final String sqlStr ="SELECT   TYPE_ID AS typeId,  TYPE_NAME AS typeName,  TYPE_LABLE AS typeLable FROM MOBILE_FILED_TYPE";
			String wherePatternStr = null;
			return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
		}
	}
