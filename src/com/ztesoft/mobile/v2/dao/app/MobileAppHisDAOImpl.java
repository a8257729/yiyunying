package com.ztesoft.mobile.v2.dao.app;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class MobileAppHisDAOImpl extends BaseDAOImpl implements MobileAppHisDAO {
	private static final String TABLE_NAME = "MOBILE_APP_HIS";
	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "APP_HIS_ID:appHisId,APP_ID:appId,APP_NAME:appName,ICON_URI:iconUri,VERSION_CODE:versionCode,VERSION_NAME:versionName,APP_TYPE:appType,APP_SIZE:appSize,APP_KEY_WORD:appKeyWord,APP_STATUS:appStatus,OS_TYPE:osType,APP_INTRO:appIntro,APP_SNAPSHOT:appSnapshot,DOWNLOAD_URL:downloadUrl,DOWNLOAD_COUNT:downloadCount,QRCODE_URL:qrcodeUrl,APP_PUBLISHER:appPublisher,APP_CLASS:appClass,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,MEMO:memo,STATE:state,STATE_DATE:stateDate,BUILD_TIME:buildTime";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("appHisId", nextId);
		paramMap.put("versionCode", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APP_HIS_ID:appHisId,APP_ID:appId,APP_NAME:appName,ICON_URI:iconUri,VERSION_CODE:versionCode,VERSION_NAME:versionName,APP_TYPE:appType,APP_SIZE:appSize,APP_KEY_WORD:appKeyWord,APP_STATUS:appStatus,OS_TYPE:osType,APP_INTRO:appIntro,APP_SNAPSHOT:appSnapshot,DOWNLOAD_URL:downloadUrl,DOWNLOAD_COUNT:downloadCount,QRCODE_URL:qrcodeUrl,APP_PUBLISHER:appPublisher,APP_CLASS:appClass,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,MEMO:memo,STATE:state,STATE_DATE:stateDate,BUILD_TIME:buildTime";
		String wherePatternStr = "APP_HIS_ID:appHisId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
//修改app表的下载次数
	public void updateDownCount (Map paramMap) throws DataAccessException{
		
		String updatePatternStr = "APP_ID:appId,DOWNLOAD_COUNT:downloadCount";
		String wherePatternStr = "APP_ID:appId";
		dynamicUpdateByMap(paramMap, "MOBILE_APP", updatePatternStr, wherePatternStr);
	}
	
	
	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APP_ID:appId";
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
	}
	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APP_HIS_ID AS appHisId,  APP_ID AS appId,  APP_NAME AS appName,  ICON_URI AS iconUri,  VERSION_CODE AS versionCode,  VERSION_NAME AS versionName,  APP_TYPE AS appType,  APP_SIZE AS appSize,  APP_KEY_WORD AS appKeyWord,  APP_STATUS AS appStatus,  OS_TYPE AS osType,  APP_INTRO AS appIntro,  APP_SNAPSHOT AS appSnapshot,  DOWNLOAD_URL AS downloadUrl,  DOWNLOAD_COUNT AS downloadCount,  QRCODE_URL AS qrcodeUrl,  APP_PUBLISHER AS appPublisher,  APP_CLASS AS appClass,  UPDATE_LOG AS updateLog,  UPDATE_TIME AS updateTime,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate,  BUILD_TIME AS buildTime FROM MOBILE_APP_HIS WHERE APP_HIS_ID=?";
		String wherePatternStr = "APP_HIS_ID:appHisId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}
	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr ="SELECT   APP_HIS_ID AS appHisId,  APP_ID AS appId,  APP_NAME AS appName,  ICON_URI AS iconUri,  VERSION_CODE AS versionCode,  VERSION_NAME AS versionName,  APP_TYPE AS appType,  APP_SIZE AS appSize,  APP_KEY_WORD AS appKeyWord,  APP_STATUS AS appStatus,  OS_TYPE AS osType,  APP_INTRO AS appIntro,  APP_SNAPSHOT AS appSnapshot,  DOWNLOAD_URL AS downloadUrl,  DOWNLOAD_COUNT AS downloadCount,  QRCODE_URL AS qrcodeUrl,  APP_PUBLISHER AS appPublisher,  APP_CLASS AS appClass,  UPDATE_LOG AS updateLog,  UPDATE_TIME AS updateTime,  MEMO AS memo,  STATE AS state,  STATE_DATE AS stateDate,  BUILD_TIME AS buildTime FROM MOBILE_APP_HIS";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}
	public String getTableName() {
		return TABLE_NAME;
	}

	public Map selByConn(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT   A.APP_ID AS appId,  A.APP_NAME AS appName,  A.ICON_URI AS iconUri,  A.VERSION_CODE AS versionCode,  A.VERSION_NAME AS versionName,  A.APP_TYPE AS appType,  A.APP_SIZE AS appSize,  A.APP_KEY_WORD AS appKeyWord,  A.APP_STATUS AS appStatus,  A.OS_TYPE AS osType,  A.APP_INTRO AS appIntro,  A.APP_SNAPSHOT AS appSnapshot,  A.DOWNLOAD_URL AS downloadUrl,  A.DOWNLOAD_COUNT AS downloadCount,  A.QRCODE_URL AS qrcodeUrl,  A.APP_PUBLISHER AS appPublisher,  A.APP_CLASS AS appClass,  A.UPDATE_LOG AS updateLog,  A.UPDATE_TIME AS updateTime,  A.MEMO AS memo,  A.STATE AS state,  A.STATE_DATE AS stateDate,  A.BUILD_TIME AS buildTime ,B.STAFF_NAME AS staffName,C.MNAME AS appStatusName ");
		sqlBuf.append(" FROM MOBILE_APP_HIS A LEFT JOIN  UOS_STAFF B ON A.APP_PUBLISHER = B.STAFF_ID     ");
		sqlBuf.append(" JOIN MOBILE_PARAM C ON A.APP_STATUS = C.MCODE AND C.GCODE = 'APP_STATUS'   ");
		if (MapUtils.getString(paramMap, "appHisId") != null && !MapUtils.getString(paramMap, "appHisId").equals("")) {
			sqlBuf.append(" AND A.APP_HIS_ID =").append(MapUtils.getLong(paramMap, "appHisId"));			
		}
		if (MapUtils.getString(paramMap, "appId") != null && !MapUtils.getString(paramMap, "appId").equals("")) {
			sqlBuf.append(" AND A.APP_ID =").append(MapUtils.getLong(paramMap, "appId"));			
		}
		
		sqlBuf.append(" ORDER BY A.APP_HIS_ID ASC ");
		
		System.out.println("selByConn="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
	public Map selMaxIdByConn(Map paramMap) throws DataAccessException{
		final String sqlStr ="SELECT MAX(APP_HIS_ID) AS appHisId FROM MOBILE_APP_HIS WHERE APP_ID=?";
		String wherePatternStr = "APP_ID:appId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
		
	}

	public Map getAppFunMap(Map paramMap) throws DataAccessException{
		 StringBuffer sqlStr=new StringBuffer("SELECT a.APP_ID         AS appId,"+
      " a.APP_NAME       AS appName,"+
     "  a.ICON_URI       AS iconUri,"+
     "  a.VERSION_CODE   AS versionCode,"+
    "   a.VERSION_NAME   AS versionName,"+
     "  a.APP_TYPE       AS appType,"+
     "  trans_param('SERVER_SYS', a.APP_Class) as appClassName,"+
    "   a.APP_SIZE       AS appSize,"+
     "  a.APP_KEY_WORD   AS appKeyWord,"+
   "    a.APP_STATUS     AS appStatus,"+
    "   a.OS_TYPE        AS osType,"+
    "     trans_param('APP_OS_TYPE', a.OS_TYPE )          as osName,"+
    "    trans_param('APP_STATUS', a.APP_STATUS)                 as appStatusName,"+
    "    mm.BUSI_SYS_ID as busiSysId,"+
     "      mm.SYS_PROVIDER     as appTypeName,"+
    "   a.APP_INTRO      AS appIntro,"+
    "   a.APP_SNAPSHOT   AS appSnapshot,"+
      " a.DOWNLOAD_URL   AS downloadUrl,"+
     "  a.DOWNLOAD_COUNT AS downloadCount,"+
     "  a.QRCODE_URL     AS qrcodeUrl,"+
     "  a.APP_PUBLISHER  AS appPublisher,"+
     "  a.APP_CLASS      AS appClass,"+
     "  a.UPDATE_LOG     AS updateLog,"+
     "  a.UPDATE_TIME    AS updateTime,"+
    "   a.MEMO           AS memo,"+
    "   a.STATE          AS state,"+
    "   a.STATE_DATE     AS stateDate,"+
    "   a.BUILD_TIME     AS buildTime"+
  " FROM MOBILE_APP a  left join mobile_busi_sys mm on a.BUSI_SYS_ID = mm.busi_sys_id where 1=1");
		  if(paramMap.get("appName")!=null && !paramMap.get("appName").equals("") &&!paramMap.get("appName").equals("null") ){
			  sqlStr.append("and a.app_name like '%"+paramMap.get("appName")+"%'");
	  
		  }
		  if(paramMap.get("osType")!=null && !paramMap.get("osType").equals("") &&!paramMap.get("osType").equals("null") ){
			  sqlStr.append(" and a.OS_TYPE  = "+paramMap.get("osType"));
			  
		  }
		  if(paramMap.get("appType")!=null && !paramMap.get("appType").equals("") &&!paramMap.get("appType").equals("null") ){
			  sqlStr.append(" and a.APP_TYPE  = "+paramMap.get("appType"));
			  
		  }
		  if(paramMap.get("beginDate")!=null && !paramMap.get("beginDate").equals("")) {
			  sqlStr.append(" and a.UPDATE_TIME >= to_date('" + paramMap.get("beginDate") + "','yyyy-MM-dd HH24:mi:ss') ");
		  }
		  
		  if (paramMap.get("endDate")!=null && !paramMap.get("endDate").equals("")) {
			  sqlStr.append("and  a.UPDATE_TIME <= to_date('" + paramMap.get("endDate") + "','yyyy-MM-dd HH24:mi:ss')  ");
		  }
		  if(paramMap.get("appClass")!=null && !paramMap.get("appClass").equals("") &&!paramMap.get("appClass").equals("null") ){
			  sqlStr.append(" and a.APP_CLASS  = "+paramMap.get("appClass"));
		  }
		  System.out.println(sqlStr.toString());
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}

	public void deleteFun(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APP_ID:appId";
		dynamicDeleteByMap(paramMap, "MOBILE_APP_FUNC", wherePatternStr);
	}
}

