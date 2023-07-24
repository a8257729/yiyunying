package com.ztesoft.mobile.v2.dao.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobilePhotoRecordDAOImpl extends BaseDAOImpl implements
		MobilePhotoRecordDAO {
	private static final String TABLE_NAME = "MOBILE_PHOTO_RECORD";

	public Map insert(Map paramMap) throws DataAccessException {
		String patternStr = "PHOTO_RECORD_ID:photoRecordId,PHOTO_NAME:photoName,BATCH_NO:batchNo,FILE_PATH:filePath,PHOTO_OWNER:photoOwner,UPLOAD_TIME:uploadTime,WORK_ORDER_ID:workOrderId,RESOURCE_ID:resourceId,FB_ID:fbId,RESOURCE_NAME:resourceName";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("photoRecordId", nextId);
		int i = dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return (i>0? paramMap : Collections.EMPTY_MAP);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PHOTO_RECORD_ID:photoRecordId,PHOTO_NAME:photoName,BATCH_NO:batchNo,FILE_PATH:filePath,PHOTO_OWNER:photoOwner,UPLOAD_TIME:uploadTime";
		String wherePatternStr = "PHOTO_RECORD_ID:photoRecordId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PHOTO_RECORD_ID:photoRecordId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   PHOTO_RECORD_ID AS photoRecordId,  PHOTO_NAME AS photoName,  BATCH_NO AS batchNo,  PHOTO_OWNER AS photoOwner,  UPLOAD_TIME AS uploadTime,WORK_ORDER_ID AS workOrderId FROM MOBILE_PHOTO_RECORD WHERE PHOTO_RECORD_ID=?";
		String wherePatternStr = "PHOTO_RECORD_ID:photoRecordId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT   PHOTO_RECORD_ID AS photoRecordId,  PHOTO_NAME AS photoName,  BATCH_NO AS batchNo,  PHOTO_OWNER AS photoOwner,  UPLOAD_TIME AS uploadTime,WORK_ORDER_ID AS workOrderId  FROM MOBILE_PHOTO_RECORD";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public Map selByWorkOrderId(Map paramMap) throws DataAccessException{
		final String sqlStr = "SELECT   PHOTO_RECORD_ID AS photoRecordId,  PHOTO_NAME AS photoName,  BATCH_NO AS batchNo,  PHOTO_OWNER AS photoOwner,  UPLOAD_TIME AS uploadTime,WORK_ORDER_ID AS workOrderId FROM MOBILE_PHOTO_RECORD WHERE WORK_ORDER_ID=?";
		String wherePatternStr = "WORK_ORDER_ID:workOrderId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public Map insertWkOrderPhotoRecord(Map paramMap)
			throws DataAccessException {
		String patternStr = "PHOTO_RECORD_ID:photoRecordId,IMAGE_NAME:photoName,IMAGE_URL:filePath,UPLOAD_STAFF:photoOwner,UPLOAD_TIME:uploadTime,WORK_ORDER_ID:workOrderId,EQP_ID:resourceId,PORT_ID:resourceName,CODE_BAR:fbId";
		Long nextId = getPKFromMem("WK_ORDER_PHOTO_RECORD", 9);
		paramMap.put("photoRecordId", nextId);
		int i = dynamicInsertByMap(paramMap, "WK_ORDER_PHOTO_RECORD", patternStr);
		return (i>0? paramMap : Collections.EMPTY_MAP);
	}
}
