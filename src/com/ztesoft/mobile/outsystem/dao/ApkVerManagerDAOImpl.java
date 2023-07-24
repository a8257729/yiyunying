package com.ztesoft.mobile.outsystem.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.common.helper.DataBaseHelper;

public class ApkVerManagerDAOImpl  extends BaseDAOImpl  implements ApkVerManagerDAO {

	private static final String APK_VER_MANGER_TBL = "MOBILE_APK_VER";
	public void delete(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
	}
	
	private static final String insertStr="VER_ID:verId,APK_CODE:apkCode,APK_VER_NO:apkVersionNo,DOWNLOAD_TIME:downloadTime,APK_VER_PKG:apkPackage,APK_VER_URL:apkUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments";
	public Long insert(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		Long nextId = getPKFromMem(APK_VER_MANGER_TBL, 9);
		paramMap.put("verId", nextId);
		dynamicInsertByMap(paramMap, APK_VER_MANGER_TBL, insertStr);
		return nextId;
	}

	
	
	private static final String updateStr = "APK_VER_PKG:apkPackage,APK_VER_URL:apkUrl,FORCE_UPDATE:forceUpdate,COMMENTS:comments,STATE_DATE:stateDate,APK_VER_STATE:apkVerState";
	private static final String upWhereStr = "APK_CODE:apkCode,&&:APK_VER_NO:apkVersionNo";//"&&:"表示AND,"||:"表示OR
	public void update(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		DataBaseHelper dataBaseHelper=new DataBaseHelper();
		try {
			paramMap.put("stateDate",dataBaseHelper.getDataBaseDate());
			dynamicUpdateByMap(paramMap, APK_VER_MANGER_TBL, updateStr, upWhereStr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Map selAll(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	//private static final String selApkVerbyCodeStr="SELECT VER_ID as verId,APK_CODE as apkCode,APK_VER_NO as apkVerNo,APK_VER_STATE as apkVerState,CREATE_DATE as createDate,DOWNLOAD_TIME as downloadTime,APK_VER_PKG as apkVerPkg,APK_VER_URL as apkVerUrl,FORCE_UPDATE as forceUpdate,COMMENTS as comments,STATE_DATE as stateDate FROM MOBILE_APK_VER";
	//private static final String selApkVerbyCodeWhereStr = "APK_CODE:apkCode";
	public Map selByApkCode(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT VER_ID as verId,APK_CODE as apkCode,APK_VER_NO as apkVerNo,APK_VER_STATE as apkVerState,CREATE_DATE as createDate,DOWNLOAD_TIME as downloadTime,APK_VER_PKG as apkVerPkg,APK_VER_URL as apkVerUrl,FORCE_UPDATE as forceUpdate,COMMENTS as comments,STATE_DATE as stateDate FROM MOBILE_APK_VER");
		if (!"0".equals(MapUtils.getString(paramMap, "sysCode"))) {
			sqlStr.append(" WHERE APK_CODE ='"+MapUtils.getString(paramMap, "apkCode")+"' ORDER BY VER_ID DESC");
		}
		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());	
	
	}
	
	private static final String selApkVerStr ="SELECT VER_ID as verId,APK_CODE as apkCode,APK_VER_NO as apkVerNo,APK_VER_STATE as apkVer_state,CREATE_DATE as createDate,DOWNLOAD_TIME as downloadTime,APK_VER_PKG as apkVerPkg,APK_VER_URL as apkVerUrl,FORCE_UPDATE as forceUpadte,COMMENTS as Comments,STATE_DATE as stateDate  FROM MOBILE_APK_VER   WHERE APK_CODE=?  AND APK_VER_NO=?" ;
	private static final String selApkVerWhereStr = "APK_CODE:apkCode,APK_VER_NO:apkVersionNo";
	public List selApvkVerByCode(Map paramMap) throws DataAccessException {
		return dynamicQueryListByMap(selApkVerStr, paramMap, selApkVerWhereStr);
	}
	
	//根据版本号和APK编码更新下载次数。li.guoyang
	public void addDownlaodTimes(Map paramMap) throws DataAccessException {
		// TODO Auto-generated method stub
		DataBaseHelper dataBaseHelper=new DataBaseHelper();
		try {
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("UPDATE MOBILE_APK_VER A SET A.DOWNLOAD_TIME=A.DOWNLOAD_TIME+1 ");
			if(!"".equals(MapUtils.getString(paramMap, "apkCode"))&&!"".equals(MapUtils.getString(paramMap, "apkVersionNo"))){
				sqlStr.append(" WHERE A.APK_CODE='")
				.append(MapUtils.getString(paramMap, "apkCode"))
				.append("' AND A.APK_VER_NO='")
				.append(MapUtils.getString(paramMap, "apkVersionNo"))
				.append("'");
				dynamicUpdateBySql(sqlStr.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
