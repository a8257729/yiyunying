package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: heisonyee
 * Date: 13-2-25
 */
public class MobileFrameAppDAOImpl extends BaseDAOImpl implements MobileFrameAppDAO {

    private static final Logger logger = Logger.getLogger(MobileFrameAppDAOImpl.class);
    private static final String TABLE_NAME = "MOBILE_FRAME_APP";
    
    private static final String selAppVerStr ="SELECT FRAME_APP_ID AS frameAppId,NAME AS name,ICON_URL AS iconUrl,VERSION_CODE AS versionCode,VERSION_NAME AS versionName,FILE_PATH AS filePath,OS_TYPE AS osType,DOWNLOAD_URL AS downloadUrl,QRCODE_URL AS qrcodeUrl,DOWNLOAD_COUNT AS downloadCount,UPDATE_TIME AS updateTime,UPDATE_LOG AS updateLog,BUILD_TIME AS buildTime,MEMO AS memo,IS_RELEASE AS isRelease  FROM MOBILE_FRAME_APP   WHERE VERSION_NAME=? " ;
	private static final String selAppVerWhereStr = "VERSION_NAME:versionName";

    public void insert(Map paramMap) throws DataAccessException {
    	System.out.println(paramMap);
        String patternStr = "FRAME_APP_ID:frameAppId,NAME:name,ICON_URL:iconUrl,FILE_PATH:filePath,VERSION_CODE:versionCode,VERSION_NAME:versionName,OS_TYPE:osType,DOWNLOAD_URL:downloadUrl,QRCODE_URL:qrcodeUrl,DOWNLOAD_COUNT:downloadCount,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,BUILD_TIME:buildTime,IS_RELEASE:isRelease,MEMO:memo,OPT_TYPE:optType,STATE:state,STATE_DATE:stateDate";
        Long nextId = getPKFromMem(TABLE_NAME, 9);
        paramMap.put("frameAppId", nextId);
        System.out.println("next id ="+nextId);
          paramMap.put("downloadUrl", paramMap.get("downloadUrl").toString()+ nextId);
          System.out.println(nextId);
        dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
    }
    
    public Map getSmsContentId(Map paramMap)  throws DataAccessException {
    	String idSql = "select seq_sms_msg_content.nextval@IOMDB as id from dual";
    	Map map = dynamicQueryObjectByMap(idSql.toString(), paramMap, null);
    	return map;
    }
    
    public void insertSmsStaff(Map paramMap) throws DataAccessException {
    	System.out.println(paramMap);
        Connection conn = null;
		PreparedStatement psmt = null;
//        CallableStatement proc = null;
		ResultSet rs = null;
		try {
			String id = MapUtils.getString(paramMap, "id");
			String name = MapUtils.getString(paramMap, "name");
			String mobileTel = MapUtils.getString(paramMap, "mobileTel");
//			List nameList = (List) MapUtils.getObject(paramMap, "nameList");
//			List telList =  (List) MapUtils.getObject(paramMap, "telList");
			conn = getConnection();
			psmt = conn.prepareCall("{ call SEND_SMS_MSG(?,?,?) }");
			
//			ArrayDescriptor descriptor = ArrayDescriptor.createDescriptor("VARCHAR_ARRAY",   
//			conn);  
			
			//把list中的元素转换成自定义的类型  
//			ARRAY nameArray = new ARRAY(descriptor, conn, nameList.toArray()); 
//			ARRAY telArray = new ARRAY(descriptor, conn, telList.toArray()); 
			psmt.setString(1, id);
//			psmt.setArray(2, nameArray);
//			psmt.setArray(3, telArray);
			psmt.setString(2, name);
			psmt.setString(3, mobileTel);
			psmt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, psmt, rs);
			}
    }
    
    public void insertSmsContent(Map paramMap) throws DataAccessException {
        Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			String id = MapUtils.getString(paramMap, "id");
			String msgContent = MapUtils.getString(paramMap, "msgContent");
			conn = getConnection();
			psmt = conn.prepareCall("{ call PROC_INSERT_SMS_CONTENT(?,?) }");
			psmt.setString(1, id);
			psmt.setString(2, msgContent);
			psmt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cleanUp(conn, null, psmt, rs);
		}
    }

    public void update(Map paramMap) throws DataAccessException {
        String updatePatternStr = "FRAME_APP_ID:frameAppId,NAME:name,ICON_URL:iconUrl,FILE_PATH:filePath,VERSION_CODE:versionCode,VERSION_NAME:versionName,OS_TYPE:osType,DOWNLOAD_URL:downloadUrl,QRCODE_URL:qrcodeUrl,DOWNLOAD_COUNT:downloadCount,UPDATE_LOG:updateLog,UPDATE_TIME:updateTime,BUILD_TIME:buildTime,IS_RELEASE:isRelease,MEMO:memo,OPT_TYPE:optType,STATE:state,STATE_DATE:stateDate";
        String wherePatternStr = "FRAME_APP_ID:frameAppId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
    }

    public void delete(Map paramMap) throws DataAccessException {
        String wherePatternStr = "FRAME_APP_ID:frameAppId";
        dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr);
    }

    public Map selById(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = this.buildSql(paramMap);
        sqlStr.append(" AND FRAME_APP_ID = ? ");
        String wherePatternStr = "FRAME_APP_ID:frameAppId";
        return dynamicQueryObjectByMap(sqlStr.toString(), paramMap, wherePatternStr);
    }

    public List selAll(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = this.buildSql(paramMap);
        System.out.println(sqlStr);
        return dynamicQueryListByMap(sqlStr.toString(), null, null);
    }
    
    public Map selByPage(Map paramMap) throws DataAccessException {
    	System.out.println("paramMap==>" + paramMap);
    	StringBuffer sqlStr = this.buildSql(paramMap);
    	  sqlStr.append(" order by VERSION_CODE desc");
        System.out.println(sqlStr);
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
    }

    private StringBuffer buildSql(Map paramMap) {
        StringBuffer sqlStr =new StringBuffer(" SELECT FRAME_APP_ID AS frameAppId, ");
        sqlStr.append(" NAME AS name, ");
        sqlStr.append(" ICON_URL AS iconUrl, ");
        sqlStr.append(" VERSION_CODE AS versionCode, ");
        sqlStr.append(" VERSION_NAME AS versionName, ");
        sqlStr.append(" FILE_PATH AS filePath, ");
        sqlStr.append(" OS_TYPE AS osType, ");
        sqlStr.append(" DOWNLOAD_URL AS downloadUrl, ");
        sqlStr.append(" QRCODE_URL AS qrcodeUrl, ");
        sqlStr.append(" DOWNLOAD_COUNT AS downloadCount, ");
        sqlStr.append(" UPDATE_TIME AS updateTime, ");
        sqlStr.append(" UPDATE_LOG AS updateLog, ");
        sqlStr.append(" BUILD_TIME AS buildTime, ");
        sqlStr.append(" MEMO AS memo, ");
        sqlStr.append(" IS_RELEASE AS isRelease ");
        //sqlStr.append(" STATE AS state ");
        //sqlStr.append(" STATE_DATE AS stateDate ");
        sqlStr.append(" FROM MOBILE_FRAME_APP WHERE STATE=1 ");

        Integer osType = MapUtils.getInteger(paramMap, "osType", null);
        if(null != osType) {
            sqlStr.append(" AND OS_TYPE = " + osType);
        }

        Integer isRelease = MapUtils.getInteger(paramMap, "isRelease", null);
        if(null != isRelease && !paramMap.get("isRelease").equals("")) {
            sqlStr.append(" AND IS_RELEASE = " + isRelease);
        }
        return sqlStr;
    }

    public Map getCurrentVersion(Map paramMap) throws DataAccessException {
        StringBuffer sqlStr = new StringBuffer(" SELECT FRAME_APP_ID as appId, ");
        sqlStr.append(" NAME AS appName, ");
        sqlStr.append(" VERSION_CODE AS versionCode, ");
        sqlStr.append(" VERSION_NAME AS versionName, ");
        sqlStr.append(" FILE_PATH AS filePath, ");
        sqlStr.append(" DOWNLOAD_URL AS downloadUrl, ");
        sqlStr.append(" to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') AS updateTime, ");
        sqlStr.append(" OPT_TYPE AS optType, ");
        sqlStr.append(" UPDATE_LOG AS updateLog ");
        sqlStr.append(" FROM MOBILE_FRAME_APP WHERE STATE=1 AND IS_RELEASE = 1 ");

        Integer osType = MapUtils.getInteger(paramMap, "osType");
        if(null != osType) {
            sqlStr.append(" AND OS_TYPE = " + osType);
        }
        
        Long appId = MapUtils.getLong(paramMap, "appId");
        if(null != appId) {
        	sqlStr.append(" AND FRAME_APP_ID = " + appId);
        }
        
        sqlStr.append(" ORDER BY APPID DESC");
        //
        if(logger.isDebugEnabled()) {
            logger.debug("getCurrentVersion的SQL：" + sqlStr.toString());
        }
        
        return this.dynamicQueryObjectByMap(sqlStr.toString(), null, null);
    }

	public Map getCurrentVersion(Integer osType) throws DataAccessException {
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("osType", osType);
		Map resultMap =  getCurrentVersion(paramMap);
		return resultMap;
	}

	public Map getFrameAppById(Long appId) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appId", appId);
		Map resultMap =  getCurrentVersion(paramMap);
		return resultMap;
	}

	public List selVersionIsExist(String versionName) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("versionName", versionName);
		return dynamicQueryListByMap(selAppVerStr, paramMap, selAppVerWhereStr);
	}
	
	public List selVersionIsExist2(Map paramMap) throws DataAccessException {
		return dynamicQueryListByMap(selAppVerStr, paramMap, selAppVerWhereStr);
	}

	public Map getLatestVersion(Map paramMap) throws DataAccessException {
		
		StringBuffer sqlStr = this.buildSql(paramMap);
		sqlStr.append(" ORDER BY VERSION_CODE DESC ");
        System.out.println(sqlStr);
        //
        if(logger.isDebugEnabled()) {
            logger.debug("getCurrentVersion：" + sqlStr.toString());
        }
        
        return this.dynamicQueryObjectByMap(sqlStr.toString(), null, null);
	}

	public void incDownloadCount(Map paramMap) throws DataAccessException {
		String updatePatternStr = "DOWNLOAD_COUNT:downloadCount";
        String wherePatternStr = "FRAME_APP_ID:frameAppId";
        dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	//修改发布状态
	public void updateIsRelease(Map param) throws DataAccessException {
	      String updatePatternStr = "UPDATE MOBILE_FRAME_APP SET IS_RELEASE = "+ param.get("isRelease");
	        
	        dynamicUpdateBySql(updatePatternStr);
	}
	
	//修改最新版本为发布状态
	public void updateLatestIsRelease(Map param) throws DataAccessException {
		String updatePatternStr = "UPDATE MOBILE_FRAME_APP SET IS_RELEASE = "+ param.get("isRelease")  + "where frame_app_id = " + param.get("frameAppId");
		dynamicUpdateBySql(updatePatternStr);
	}
	
	public void updateDownloadCount(Long frameAppId)  throws DataAccessException {
/*		Map params = new HashMap();
		params.put("frameAppId", frameAppId);*/
		
		String sqlStr = "update mobile_frame_app p set p.download_count = p.download_count + 1 where frame_app_id = " + frameAppId;
		
        //
        if(logger.isDebugEnabled()) {
            logger.debug("updateDownloadCount的SQL：" + sqlStr);
        }
		
		//String wherePatternStr = "FRAME_APP_ID:frameAppId";
		this.dynamicUpdateBySql(sqlStr);
	}
}