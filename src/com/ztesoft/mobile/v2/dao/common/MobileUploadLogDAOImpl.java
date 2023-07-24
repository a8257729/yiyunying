package com.ztesoft.mobile.v2.dao.common;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public class MobileUploadLogDAOImpl extends BaseDAOImpl implements MobileUploadLogDAO {
    private static final String TABLE_NAME = "MOBILE_UPLOAD_LOG";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "UPLOAD_LOG_ID:@@SEQ,FILE_NAME:fileName,FILE_PATH:filePath,FILE_TYPE:fileType,UPLOAD_TIME:uploadTime,UPLOAD_STAFF_ID:uploadStaffId,UPLOAD_USERNAME:uploadUsername,UPLOAD_STAFF_NAME:uploadStaffName";
        //Long nextId = getPKFromMem(TABLE_NAME, 9);
        //paramMap.put("uploadLogId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "UPLOAD_LOG_ID:uploadLogId,FILE_NAME:fileName,FILE_PATH:filePath,FILE_TYPE:fileType,UPLOAD_TIME:uploadTime,UPLOAD_STAFF_ID:uploadStaffId,UPLOAD_USERNAME:uploadUsername,UPLOAD_STAFF_NAME:uploadStaffName";
        String wherePatternStr = "UPLOAD_LOG_ID:uploadLogId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "UPLOAD_LOG_ID:uploadLogId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   UPLOAD_LOG_ID AS uploadLogId,  FILE_NAME AS fileName,  FILE_PATH AS filePath,  FILE_TYPE AS fileType,  UPLOAD_TIME AS uploadTime,  UPLOAD_STAFF_ID AS uploadStaffId,  UPLOAD_USERNAME AS uploadUsername,  UPLOAD_STAFF_NAME AS uploadStaffName FROM MOBILE_UPLOAD_LOG WHERE UPLOAD_LOG_ID=?";
        String wherePatternStr = "UPLOAD_LOG_ID:uploadLogId";
        return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   UPLOAD_LOG_ID AS uploadLogId,  FILE_NAME AS fileName,  FILE_PATH AS filePath,  FILE_TYPE AS fileType,  UPLOAD_TIME AS uploadTime,  UPLOAD_STAFF_ID AS uploadStaffId,  UPLOAD_USERNAME AS uploadUsername,  UPLOAD_STAFF_NAME AS uploadStaffName FROM MOBILE_UPLOAD_LOG";
        String wherePatternStr = null;
        return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}