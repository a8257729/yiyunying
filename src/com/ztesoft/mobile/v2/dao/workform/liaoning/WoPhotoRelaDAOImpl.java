package com.ztesoft.mobile.v2.dao.workform.liaoning;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class WoPhotoRelaDAOImpl extends BaseDAOImpl implements WoPhotoRelaDAO {
	
	private static final String TABLE_NAME = "OSS_WO_PHOTO_RELA";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId,WORK_ORDER_ID:workOrderId,PHOTO_RECORD_ID:photoRecordId,CREATE_TIME:createTime";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("woPhotoRelaId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId,WORK_ORDER_ID:workOrderId,PHOTO_RECORD_ID:photoRecordId,CREATE_TIME:createTime";
		String wherePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void updateBatch(List paramMapList) throws DataAccessException {
		String updatePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId,WORK_ORDER_ID:workOrderId,PHOTO_RECORD_ID:photoRecordId,CREATE_TIME:createTime";
		String wherePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId";
		dynamicUpdateBatchByMap(paramMapList, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   WO_PHOTO_RELA_ID AS woPhotoRelaId,  WORK_ORDER_ID AS workOrderId,  PHOTO_RECORD_ID AS photoRecordId,  CREATE_TIME AS createTime FROM OSS_WO_PHOTO_RELA WHERE WO_PHOTO_RELA_ID=?";
		String wherePatternStr = "WO_PHOTO_RELA_ID:woPhotoRelaId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   WO_PHOTO_RELA_ID AS woPhotoRelaId,  WORK_ORDER_ID AS workOrderId,  PHOTO_RECORD_ID AS photoRecordId,  CREATE_TIME AS createTime FROM OSS_WO_PHOTO_RELA";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public String getTableName() {
		return TABLE_NAME;
	}
}