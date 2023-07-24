package com.ztesoft.mobile.v2.dao.common;


import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.List;
import java.util.Map;

public class MobileDownloadLogDAOImpl extends BaseDAOImpl implements MobileDownloadLogDAO {
    private static final String TABLE_NAME = "MOBILE_DOWNLOAD_LOG";

    public void insert(Map paramMap) throws DataAccessException {
        String patternStr = "DOWNLOAD_LOG_ID:@@SEQ,FILE_NAME:fileName,FILE_PATH:filePath,FILE_TYPE:fileType,DOWNLOAD_TIME:downloadTime,DOWNLOAD_STAFF_ID:downloadStaffId,DOWNLOAD_USERNAME:downloadUsername,DOWNLOAD_STAFF_NAME:downloadStaffName,DOWNLOAD_OBJ_ID:downloadObjId,DOWNLOAD_OBJ_TYPE:downloadObjType";
        //Long nextId = getPKFromMem(TABLE_NAME, 9);
        //paramMap.put("DOWNLOADLogId", nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "DOWNLOAD_LOG_ID:downloadLogId,FILE_NAME:fileName,FILE_PATH:filePath,FILE_TYPE:fileType,DOWNLOAD_TIME:downloadTime,DOWNLOAD_STAFF_ID:downloadStaffId,DOWNLOAD_USERNAME:downloadUsername,DOWNLOAD_STAFF_NAME:downloadStaffName";
        String wherePatternStr = "DOWNLOAD_LOG_ID:downloadLogId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "DOWNLOAD_LOG_ID:downloadLogId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   DOWNLOAD_LOG_ID AS downloadLogId,  FILE_NAME AS fileName,  FILE_PATH AS filePath,  FILE_TYPE AS fileType,  DOWNLOAD_TIME AS downloadTime,  DOWNLOAD_STAFF_ID AS DOWNLOADStaffId,  DOWNLOAD_USERNAME AS downloadUsername,  DOWNLOAD_STAFF_NAME AS downloadStaffName FROM MOBILE_DOWNLOAD_LOG WHERE DOWNLOAD_LOG_ID=?";
        String wherePatternStr = "DOWNLOAD_LOG_ID:downloadLogId";
        return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        final String sqlStr = "SELECT   DOWNLOAD_LOG_ID AS downloadLogId,  FILE_NAME AS fileName,  FILE_PATH AS filePath,  FILE_TYPE AS fileType,  DOWNLOAD_TIME AS downloadTime,  DOWNLOAD_STAFF_ID AS downloadStaffId,  DOWNLOAD_USERNAME AS downloadUsername,  DOWNLOAD_STAFF_NAME AS downloadStaffName FROM MOBILE_DOWNLOAD_LOG";
        String wherePatternStr = null;
        return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
    }
    
    public List selAppDownloadStat(Map paramMap) throws DataAccessException {
    	StringBuilder str = new StringBuilder();
    	str.append("SELECT fileName, appId, versionName, downloadCount from (");
    	str.append("select a.file_name as fileName, b.frame_app_id as appId, b.version_name as versionName, b.download_count as downloadCount from mobile_download_log a, mobile_frame_app b where a.download_obj_type='2' and a.download_obj_id = b.frame_app_id ");
    	str.append("union select a.file_name as fileName, b.app_id as appId, b.version_name as versionName, b.download_count as downloadCount from mobile_download_log a, mobile_app b where a.download_obj_type='1' and a.download_obj_id = b.app_id ");
    	str.append("order by downloadCount desc )");
//        final String sqlStr = "select a.file_name as fileName, b.frame_app_id as appId, b.version_name as versionName, b.download_count as downloadCount from mobile_download_log a, mobile_frame_app b where a.download_obj_type='2' and a.download_obj_id = b.frame_app_id ";
        String wherePatternStr = null;
        return dynamicQueryListByMap(str.toString(), paramMap, wherePatternStr);
    }

    public List selAppDownloadLatestMonthStat(Map paramMap) throws DataAccessException {
    	StringBuilder str = new StringBuilder();
    	str.append("SELECT TO_CHAR(T.DOWNLOAD_TIME,'YYYY-MM-DD') AS downloadTime,COUNT(*) AS downloadCount, T.DOWNLOAD_OBJ_TYPE AS downloadObjType from mobile_download_log T ");
    	str.append("WHERE T.DOWNLOAD_OBJ_TYPE IS NOT NULL AND T.DOWNLOAD_TIME > add_months(sysdate,-1) GROUP BY TO_CHAR(T.DOWNLOAD_TIME,'YYYY-MM-DD'), T.DOWNLOAD_OBJ_TYPE ORDER BY downloadTime");
        String wherePatternStr = null;
        return dynamicQueryListByMap(str.toString(), paramMap, wherePatternStr);
    }
    
    public String getTableName() {
        return TABLE_NAME;
    }
    
    public List selAppDownloadPerMonthStat(Map paramMap) throws DataAccessException {
    	StringBuilder str = new StringBuilder();
    	str.append("SELECT TO_CHAR(T.DOWNLOAD_TIME,'YYYY-MM') AS downloadTime,COUNT(*) AS downloadCount, T.DOWNLOAD_OBJ_TYPE AS downloadObjType from mobile_download_log T ");
    	str.append("WHERE T.DOWNLOAD_OBJ_TYPE IS NOT NULL GROUP BY TO_CHAR(T.DOWNLOAD_TIME,'YYYY-MM'), T.DOWNLOAD_OBJ_TYPE ORDER BY downloadTime");
        String wherePatternStr = null;
        return dynamicQueryListByMap(str.toString(), paramMap, wherePatternStr);
    }
    
}