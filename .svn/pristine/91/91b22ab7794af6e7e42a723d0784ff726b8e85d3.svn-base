package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

public class MobileAppDAOImpl extends BaseDAOImpl implements MobileAppDAO {
	
	private static final Logger logger = Logger.getLogger(MobileAppDAOImpl.class);
	
    private static final String TABLE_NAME = "MOBILE_APP";
    
    public Map insert(Map paramMap) throws DataAccessException {
    	String patternStr = "APP_ID:appId,APP_NAME:appName,ICON_URI:iconUri,VERSION_CODE:versionCode,VERSION_NAME:versionName,APP_TYPE:appType,APP_SIZE:appSize,APP_KEY_WORD:appKeyWord,APP_STATUS:appStatus,OS_TYPE:osType,APP_INTRO:appIntro,APP_SNAPSHOT:appSnapshot,DOWNLOAD_URL:downloadUrl,DOWNLOAD_COUNT:downloadCount,QRCODE_URL:qrcodeUrl,APP_PUBLISHER:appPublisher,APP_CLASS:appClass,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,MEMO:memo,STATE:state,STATE_DATE:stateDate,BUILD_TIME:buildTime,BUSI_SYS_ID:busiSysId,APP_FILE_PATH:appFilePath";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("appId", nextId);
		paramMap.put("downloadUrl",paramMap.get("downloadUrl").toString()+nextId );
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
		return paramMap;
	}
	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APP_NAME:appName,ICON_URI:iconUri,VERSION_CODE:versionCode,VERSION_NAME:versionName,APP_SIZE:appSize,APP_KEY_WORD:appKeyWord,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,APP_INTRO:appIntro,APP_SNAPSHOT:appSnapshot,DOWNLOAD_URL:downloadUrl,QRCODE_URL:qrcodeUrl,BUSI_SYS_ID:busiSysId,APP_FILE_PATH:appFilePath";	
	    //����޸�ʱ��û���ϴ��µ��ļ��Ͳ��޸�����ֶ� 
		System.out.println(paramMap);
		if(paramMap.get("appFilePath")==null || paramMap.get("appFilePath").equals("")){
	       updatePatternStr = "APP_NAME:appName,ICON_URI:iconUri,VERSION_CODE:versionCode,VERSION_NAME:versionName,APP_SIZE:appSize,APP_KEY_WORD:appKeyWord,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,APP_INTRO:appIntro,APP_SNAPSHOT:appSnapshot,DOWNLOAD_URL:downloadUrl,QRCODE_URL:qrcodeUrl,BUSI_SYS_ID:busiSysId";	
	 	 
	      }
		String wherePatternStr = "APP_ID:appId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APP_ID:appId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT APP_ID AS appId,  APP_NAME AS appName,  ICON_URI AS iconUri,  VERSION_CODE AS versionCode,  VERSION_NAME AS versionName,  APP_TYPE AS appType,  APP_SIZE AS appSize,  APP_KEY_WORD AS appKeyWord,  APP_STATUS AS appStatus,  OS_TYPE AS osType,  APP_INTRO AS appIntro,  APP_SNAPSHOT AS appSnapshot,  DOWNLOAD_URL AS downloadUrl,  DOWNLOAD_COUNT AS downloadCount,  QRCODE_URL AS qrcodeUrl,  APP_PUBLISHER AS appPublisher,  APP_CLASS AS appClass,  UPDATE_LOG AS updateLog,  A.UPDATE_TIME AS updateTime,  A.MEMO AS memo,  A.STATE AS state,  A.STATE_DATE AS stateDate,  A.BUILD_TIME AS buildTime,A.BUSI_SYS_ID AS busiSysId,APP_FILE_PATH as appFilePath FROM MOBILE_APP A WHERE APP_ID=? ";
		String wherePatternStr = "APP_ID:appId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT APP_ID AS appId,  APP_NAME AS appName,  ICON_URI AS iconUri,  VERSION_CODE AS versionCode,  VERSION_NAME AS versionName,  APP_TYPE AS appType,  APP_SIZE AS appSize,  APP_KEY_WORD AS appKeyWord,  APP_STATUS AS appStatus,  OS_TYPE AS osType,  APP_INTRO AS appIntro,  APP_SNAPSHOT AS appSnapshot,  DOWNLOAD_URL AS downloadUrl,  DOWNLOAD_COUNT AS downloadCount,  QRCODE_URL AS qrcodeUrl,  APP_PUBLISHER AS appPublisher,  APP_CLASS AS appClass,  UPDATE_LOG AS updateLog,  UPDATE_TIME AS updateTime,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate,  BUILD_TIME AS buildTime,BUSI_SYS_ID AS busiSysId,APP_FILE_PATH as appFilePath FROM MOBILE_APP ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
   
    public String getTableName() {
        return TABLE_NAME;
    }
    
    public Map selByConn(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT   A.APP_ID AS appId,  A.APP_NAME AS appName,  A.ICON_URI AS iconUri,  A.VERSION_CODE AS versionCode,  A.VERSION_NAME AS versionName,  A.APP_TYPE AS appType,  A.APP_SIZE AS appSize,  A.APP_KEY_WORD AS appKeyWord,  A.APP_STATUS AS appStatus,  A.OS_TYPE AS osType,  A.APP_INTRO AS appIntro,  A.APP_SNAPSHOT AS appSnapshot,  A.DOWNLOAD_URL AS downloadUrl,  A.DOWNLOAD_COUNT AS downloadCount,  A.QRCODE_URL AS qrcodeUrl,  A.APP_PUBLISHER AS appPublisher,  A.APP_CLASS AS appClass,  A.UPDATE_LOG AS updateLog,  A.UPDATE_TIME AS updateTime,  A.MEMO AS memo,  A.STATE AS state,  A.STATE_DATE AS stateDate,  A.BUILD_TIME AS buildTime,A.BUSI_SYS_ID AS busiSysId,A.APP_FILE_PATH as appFilePath ,B.STAFF_NAME AS staffName,C.MNAME AS appStatusName ");
		sqlBuf.append(" FROM MOBILE_APP A LEFT JOIN  UOS_STAFF B ON A.APP_PUBLISHER = B.STAFF_ID     ");
		sqlBuf.append(" JOIN MOBILE_PARAM C ON A.APP_STATUS = C.MCODE AND C.GCODE = 'APP_STATUS'   ");
		sqlBuf.append(" WHERE 1=1   ");
		if (MapUtils.getString(paramMap, "appId") != null && !MapUtils.getString(paramMap, "appId").equals("")) {
			sqlBuf.append(" AND A.APP_ID =").append(MapUtils.getLong(paramMap, "appId"));			
		}
		
		if (MapUtils.getString(paramMap, "osType") != null && !MapUtils.getString(paramMap, "osType").equals("")) {
			sqlBuf.append(" AND A.OS_TYPE  =").append(MapUtils.getLong(paramMap, "osType"));			
		}
		
		if (MapUtils.getString(paramMap, "appClass") != null && !MapUtils.getString(paramMap, "appClass").equals("")) {
			sqlBuf.append(" AND A.APP_CLASS =").append(MapUtils.getLong(paramMap, "appClass"));			
		}
		
		sqlBuf.append(" ORDER BY A.APP_ID DESC ");
		
		System.out.println("selByConn="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
    
    public Map selVisionCodeByConn(Map paramMap) throws DataAccessException{
    	final String sqlStr = " SELECT VERSION_CODE +1 as icount FROM MOBILE_APP WHERE APP_ID=? ";
		String wherePatternStr = "APP_ID:appId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
    	
    }
    
    public void updateAppStatus(Map paramMap) throws DataAccessException{
    	String updatePatternStr = "APP_STATUS:appStatus";	
		String wherePatternStr = "APP_ID:appId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    	
    }
    
    public List getFuncAppList(Integer osType) throws DataAccessException {
    	StringBuffer sqlStr = new StringBuffer(" select distinct a.app_id as appId, ");
    	sqlStr.append(" a.app_name as appName, ");
    	sqlStr.append(" a.version_code as versionCode, ");
    	sqlStr.append(" a.version_name as versionName, ");
    	sqlStr.append(" a.download_url as downloadUrl, ");
    	sqlStr.append(" c.access_package as accessPackage, ");
    	sqlStr.append(" a.busi_sys_id as busiSysId ");
    	sqlStr.append(" from mobile_app a, mobile_app_func c ");
    	sqlStr.append(" where a.app_id = c.app_id ");
    	sqlStr.append(" and a.state = 1 ");
    	sqlStr.append(" and c.state = 1 ");
    	
    	if(null != osType) {
    		sqlStr.append(" and a.os_type = " + osType);
    	}
    	
    	return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }
    
    public Map getAppById(Long appId) throws DataAccessException {
        StringBuffer sqlStr = new StringBuffer(" SELECT APP_ID as appId, ");
        sqlStr.append(" APP_NAME AS appName, ");
        sqlStr.append(" VERSION_CODE AS versionCode, ");
        sqlStr.append(" VERSION_NAME AS versionName, ");
        sqlStr.append(" APP_FILE_PATH AS filePath, ");
        sqlStr.append(" DOWNLOAD_URL AS downloadUrl, ");
        sqlStr.append(" to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') AS updateTime, ");
        sqlStr.append(" UPDATE_LOG AS updateLog ");
        sqlStr.append(" FROM MOBILE_APP WHERE STATE=1 ");
        
        if(null != appId) {
        	sqlStr.append(" AND APP_ID = " + appId);
        }
        
        //
        if(logger.isDebugEnabled()) {
            logger.debug("getCurrentVersion��SQL��" + sqlStr.toString());
        }
        
        return this.dynamicQueryObjectByMap(sqlStr.toString(), null, null);
    }
	
	public void updateDownloadCount(Long appId) throws DataAccessException {
		/*
		 * Map params = new HashMap(); params.put("frameAppId", frameAppId);
		 */

		String sqlStr = "update mobile_app p set p.download_count = p.download_count + 1 where app_id = "
				+ appId;
		// String wherePatternStr = "FRAME_APP_ID:frameAppId";
		this.dynamicUpdateBySql(sqlStr);
	}
	public List getAppOrderCount(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT APP_ID AS appId,  APP_NAME AS appName,  ICON_URI AS iconUri,  VERSION_CODE AS versionCode,  VERSION_NAME AS versionName,  APP_TYPE AS appType,  APP_SIZE AS appSize,  APP_KEY_WORD AS appKeyWord,  APP_STATUS AS appStatus,  OS_TYPE AS osType,  APP_INTRO AS appIntro,  APP_SNAPSHOT AS appSnapshot,  DOWNLOAD_URL AS downloadUrl,  DOWNLOAD_COUNT AS downloadCount,  QRCODE_URL AS qrcodeUrl,  APP_PUBLISHER AS appPublisher,  APP_CLASS AS appClass,  UPDATE_LOG AS updateLog,  UPDATE_TIME AS updateTime,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate,  BUILD_TIME AS buildTime,BUSI_SYS_ID AS busiSysId,APP_FILE_PATH as appFilePath FROM MOBILE_APP order by DOWNLOAD_COUNT  desc";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
    
}