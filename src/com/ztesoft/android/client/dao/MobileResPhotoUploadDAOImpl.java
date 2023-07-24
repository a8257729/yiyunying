package com.ztesoft.android.client.dao;

import java.util.List;
import java.util.Map;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileResPhotoUploadDAOImpl extends BaseDAOImpl implements MobileResPhotoUploadDAO {
	
	private static final String TABLE_NAME = "UPLOAD_PHOTO";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "PHOTO_ID:photoId,PHOTO_PATH:photoPath,PHOTO_NAME:photoName,USERNAME:username";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("photoId", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "PHOTO_ID:photoId,PHOTO_PATH:photoPath,PHOTO_NAME:photoName,USERNAME:username";
		String wherePatternStr = "PHOTO_ID:photoId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "PHOTO_ID:photoId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PHOTO_ID AS photoId,  PHOTO_PATH AS photoPath,  PHOTO_NAME AS photoName,  USERNAME AS username  FROM UPLOAD_PHOTO WHERE PHOTO_ID=?";
		String wherePatternStr = "PHOTO_ID:photoId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   PHOTO_ID AS photoId,  PHOTO_PATH AS photoPath,  PHOTO_NAME AS photoName,  USERNAME AS username  FROM UPLOAD_PHOTO";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selByName(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}

