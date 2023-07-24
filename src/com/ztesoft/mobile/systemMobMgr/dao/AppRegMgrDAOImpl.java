package com.ztesoft.mobile.systemMobMgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

public class AppRegMgrDAOImpl extends BaseDAOImpl implements AppRegMgrDAO{
	private static final String TABLE_NAME = "MOBILE_MUNE";
	private static final String TABLE_NAME2 = "MOBILE_PRIV";
	private static final String TABLE_NAME3 = "MOBILE_OS_TYPE";
	
	
	public Map insert(Map paramMap) throws DataAccessException {
		//System.out.println("====[debug]=insert传入的map值=======: "+paramMap.toString());
		String patternStr = "MUNE_ID:muneId,PARENT_ID:parentId,MUNE_NAME:muneName,PATH_NAME:pathName,PATH_CODE:pathCode,ISBG:isbg,DISPLAY_INEDX:displayInedx,DISPLAY_TYPE:displayType,ICON_ADR:iconAdr,OTHER_SYS_CODE:otherSysCode,TO_PAGE:toPage,GET_METHOD:getMethod,STATE:state,IS_LEAF:isLeaf,PRIV_CODE:privCode,INTEFACE_URL:intefaceUrl,AXIS_TYPE:axisType,MUNE_TYPE:menuType,IS_PASS:isPass,APK_ID:apkId,SYS_CODE:sysCode,APK_ICON:apkIcon,OS_TYPE:osType";
		String patternStrpriv = "PRIV_CODE:privCode,PRIV_NAME:privName,PRIV_CLASS_ID:privClassId,PRIV_TYPE:privType,COMMENTS:comments,STATE:state";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		String pathCode = paramMap.get("pathCode").equals("null")?nextId+"":paramMap.get("pathCode")+"/"+nextId;

		paramMap.put("muneId", nextId);
		paramMap.put("privCode", "MUNE_"+nextId);//权限代码自动生成	modify by guo.jinjun at 2012-05-10 10:08:03
		paramMap.put("pathCode", pathCode.replace("null/", ""));
		paramMap.put("pathName", MapUtils.getString(paramMap, "pathName").replace("null/", ""));
		Map priv = new HashMap();
		priv.put("privCode", MapUtils.getString(paramMap, "privCode"));
		priv.put("privName", MapUtils.getString(paramMap, "muneName"));
		priv.put("privClassId", nextId);
		priv.put("privType", "1");
		priv.put("comments", MapUtils.getString(paramMap, "muneName"));
		priv.put("state", "10A");
	
		dynamicInsertByMap(priv, TABLE_NAME2, patternStrpriv);  //权限
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);       //菜单  
		return paramMap;
	}
	
	public void updateIsPass(Map paramMap) throws DataAccessException {
		String updatePatternStr = "IS_PASS:isPass";
		String wherePatternStr = "MUNE_ID:muneId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public void update(Map paramMap) throws DataAccessException {

		String updatePatternStr = "MUNE_ID:muneId,MUNE_NAME:muneName,ISBG:isbg,DISPLAY_INEDX:displayInedx,DISPLAY_TYPE:displayType,ICON_ADR:iconAdr,OTHER_SYS_CODE:otherSysCode,TO_PAGE:toPage,GET_METHOD:getMethod,INTEFACE_URL:intefaceUrl,AXIS_TYPE:axisType,MUNE_TYPE:menuType,SYS_CODE:sysCode,APK_ICON:apkIcon";
		String wherePatternStr = "MUNE_ID:muneId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr, wherePatternStr);
	}
	
	public Map selByName(Map paramMap) throws DataAccessException {

			StringBuffer sqlBuf = new StringBuffer();
			sqlBuf.append("SELECT P.Mune_Id AS muneId, P.Mune_Name AS muneName, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
			sqlBuf.append(" P.ISBG AS isbg,P.DISPLAY_TYPE AS displayType,P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,");
			sqlBuf.append(" P.PARENT_ID AS parentId,P.IS_LEAF AS isLeaf,P.PRIV_CODE as privCode,P.INTEFACE_URL AS intefaceUrl,P.MUNE_TYPE AS menuType,P.SYS_CODE AS sysCode,P.APK_ICON AS apkIcon,P.OS_TYPE as osType  ");
			sqlBuf.append(" FROM MOBILE_MUNE P WHERE P.STATE = '10A'");
	
			if(MapUtils.getString(paramMap, "moduleId")!= null && MapUtils.getString(paramMap, "moduleId").equals("0")){
				sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
			}else{
				sqlBuf.append(" AND P.PARENT_ID = ").append(MapUtils.getIntValue(paramMap, "moduleId"));
			}
			
			if(MapUtils.getString(paramMap, "osType")!= null && !MapUtils.getString(paramMap, "osType").equals("")){
				sqlBuf.append(" AND P.OS_TYPE = ").append(MapUtils.getLong(paramMap, "osType"));
			}
			sqlBuf.append(" order by P.DISPLAY_INEDX asc ");
			System.out.println("selByName"+sqlBuf.toString());
			
			return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	
	public Map selApkInfoByconn(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT P.Mune_Id AS muneId, P.Mune_Name AS muneName, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,P.OS_TYPE AS osType,");
		sqlBuf.append(" P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,P.PARENT_ID AS parentId,P.PRIV_CODE as privCode,P.SYS_CODE AS sysCode,P.APK_ICON AS apkIcon,");
		sqlBuf.append(" B.APK_NAME AS apkName,B.APK_ID AS apkId,B.APK_CODE AS apkCode,B.APK_VERSION_NO AS apkVersionNo, ");
		sqlBuf.append(" B.APK_PACKAGE AS apkPackage,  B.APK_URL AS apkUrl,  B.FORCE_UPDATE AS forceUpdate,  B.COMMENTS AS comments,  B.CREATE_DATE AS createDate, ");
		sqlBuf.append(" B.OPEN_CLASS AS openClass,B.APK_SIZE AS apkSize,B.APK_CONTENT AS apkContent,B.APK_TYPE as apkType,C.FUN_ID AS funId,C.FUNC_STATUS as funcStatus,C.FUNC_REG_STAFF_ID AS funcRegStaffId  ");
		sqlBuf.append(" FROM MOBILE_MUNE P  ");
		sqlBuf.append(" LEFT JOIN OTHER_APK_MANAGE B ON P.APK_ID = B.APK_ID ");
		sqlBuf.append(" LEFT JOIN MOBILE_APK_FUNCTION C ON C.Mune_Id = P.Mune_Id ");
		sqlBuf.append(" WHERE P.STATE ='10A'   ");

		if(MapUtils.getString(paramMap, "moduleId")!= null && MapUtils.getString(paramMap, "moduleId").equals("0")){
			sqlBuf.append(" AND (P.PARENT_ID = 0 OR P.PARENT_ID IS NULL)");
		}else if (MapUtils.getString(paramMap, "moduleId")!= null && !MapUtils.getString(paramMap, "moduleId").equals("")){
			sqlBuf.append(" AND P.PARENT_ID = ").append(MapUtils.getIntValue(paramMap, "moduleId"));
		}
				
		if(MapUtils.getString(paramMap, "apkIds")!= null && !MapUtils.getString(paramMap, "apkIds").equals("")){
			sqlBuf.append(" AND P.Mune_Id in ( ").append(MapUtils.getString(paramMap, "apkIds")).append(" ) ");
			
		}
		
		if(MapUtils.getString(paramMap, "funcStatus")!= null && !MapUtils.getString(paramMap, "funcStatus").equals("")){
			sqlBuf.append(" AND C.FUNC_STATUS  in ( ").append(MapUtils.getString(paramMap, "funcStatus")).append(" ) ");
			
		}

		if(MapUtils.getString(paramMap, "osType")!= null && !MapUtils.getString(paramMap, "osType").equals("")){
			sqlBuf.append(" AND P.OS_TYPE = ").append(MapUtils.getLong(paramMap, "osType"));
		}
		
		if(MapUtils.getString(paramMap, "apkType")!= null && !MapUtils.getString(paramMap, "apkType").equals("")){
			sqlBuf.append(" AND B.APK_TYPE = '").append(MapUtils.getString(paramMap, "apkType")).append("'");
			
		}
		
		sqlBuf.append(" order by P.Mune_Id desc ");
		
		System.out.println("selApkInfoByconn="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
	
	public List selApkInfoByMuneId(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT P.Mune_Id AS muneId, P.Mune_Name AS muneName, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,P.OS_TYPE AS osType,");
		sqlBuf.append(" P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,P.PARENT_ID AS parentId,P.PRIV_CODE as privCode,P.SYS_CODE AS sysCode,P.APK_ICON AS apkIcon,");
		sqlBuf.append(" B.APK_NAME AS apkName,B.APK_ID AS apkId,B.APK_CODE AS apkCode,B.APK_VERSION_NO AS apkVersionNo, ");
		sqlBuf.append(" B.APK_PACKAGE AS apkPackage,  B.APK_URL AS apkUrl,  B.FORCE_UPDATE AS forceUpdate,  B.COMMENTS AS comments,  B.CREATE_DATE AS createDate, ");
		sqlBuf.append(" B.OPEN_CLASS AS openClass,B.APK_SIZE AS apkSize,B.APK_CONTENT AS apkContent  ");
		sqlBuf.append(" FROM MOBILE_MUNE P  ");
		sqlBuf.append(" LEFT JOIN OTHER_APK_MANAGE B ON P.APK_ID = B.APK_ID ");
		sqlBuf.append(" WHERE P.STATE ='10A'   ");

		if(MapUtils.getString(paramMap, "muneId")!= null && !MapUtils.getString(paramMap, "muneId").equals("")){
			sqlBuf.append(" AND P.Mune_Id = ").append(MapUtils.getLong(paramMap, "muneId"));
			
		}
		
		if(MapUtils.getString(paramMap, "osType")!= null && !MapUtils.getString(paramMap, "osType").equals("")){
			sqlBuf.append(" AND P.OS_TYPE = ").append(MapUtils.getLong(paramMap, "osType"));
		}
		
		sqlBuf.append(" order by P.Mune_Id desc ");
		
	//	System.out.println("selApkInfoByMuneId="+sqlBuf.toString());
		
		return dynamicQueryListByMap(sqlBuf.toString(), paramMap, null);
		
	}
	
	public Map selByApkCode(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("SELECT   A.FUN_ID AS funId,  A.APK_CODE AS apkCode,  A.FUN_CODE AS funCode,  A.FUN_NAME AS funName,  A.FUN_CLASS AS funClass,A.VER_ID AS verId,A.FUNC_REG_STAFF_ID AS funcRegStaffId,A.FUNC_STATUS AS funcStatus,A.MUNE_ID AS muneId,B.MUNE_NAME AS muneName FROM MOBILE_APK_FUNCTION A  ");
		sqlStr.append(" left JOIN MOBILE_MUNE B ON A.MUNE_ID = B.MUNE_ID ");
		if (MapUtils.getString(paramMap, "apkCode") != null && !MapUtils.getString(paramMap, "apkCode").equals("")) {
			sqlStr.append(" WHERE A.APK_CODE ='"+MapUtils.getString(paramMap, "apkCode")+"'");			
		}
		
		if(MapUtils.getString(paramMap, "osType")!= null && !MapUtils.getString(paramMap, "osType").equals("")){
			sqlStr.append(" AND B.OS_TYPE = ").append(MapUtils.getLong(paramMap, "osType"));
		}

		System.out.println("-----sql----"+sqlStr.toString()+"---------------------------");
		return populateQueryByMap(sqlStr,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
	}
	
	
	public void osInsert(Map paramMap) throws DataAccessException{
		String patternStr = "ID:id,OS_TYPE:osType,OS_TYPE_NAME:osTypeName";
		Long nextId = getPKFromMem(TABLE_NAME3, 9);
		paramMap.put("id", nextId);
		paramMap.put("osType", nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME3, patternStr);
	}
	
	public void osUpdate(Map paramMap) throws DataAccessException{
		String updatePatternStr = "ID:id,OS_TYPE:osType,OS_TYPE_NAME:osTypeName";
		String wherePatternStr = "ID:id";
		dynamicUpdateByMap(paramMap, TABLE_NAME3, updatePatternStr, wherePatternStr);
	}
	
	public void osDelete(Map paramMap) throws DataAccessException{
		String wherePatternStr = "ID:id";
		dynamicDeleteByMap(paramMap, TABLE_NAME3, wherePatternStr);
	}
	
	public Map selByOsType(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT ID as id,OS_TYPE as osType,OS_TYPE_NAME as osTypeName ");
		
		sqlBuf.append(" FROM MOBILE_OS_TYPE   ");
		sqlBuf.append(" WHERE 1=1   ");
		
		sqlBuf.append(" order by Id asc ");
		
	//	System.out.println("selApkInfoByMuneId="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
	
	public Map selClassByOsType(Map paramMap) throws DataAccessException{
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(" SELECT A.ID as id,A.OS_TYPE as osType,A.OS_TYPE_NAME as osTypeName, ");
		sqlBuf.append(" P.Mune_Id AS muneId, P.Mune_Name AS muneName, P.PATH_CODE AS pathCode, P.PATH_NAME AS pathName,");
		sqlBuf.append(" P.DISPLAY_INEDX AS displayIndex,P.ICON_ADR AS iconAdr,P.PARENT_ID AS parentId,P.PRIV_CODE as privCode,P.SYS_CODE AS sysCode,P.APK_ICON AS apkIcon ");
		sqlBuf.append(" FROM MOBILE_OS_TYPE A,MOBILE_MUNE P   ");
		sqlBuf.append(" WHERE A.OS_TYPE = P.OS_TYPE AND P.PARENT_ID = 0  ");
		
		if (MapUtils.getString(paramMap, "osType") != null && !MapUtils.getString(paramMap, "osType").equals("")) {
			sqlBuf.append(" AND A.OS_TYPE ='"+MapUtils.getString(paramMap, "osType")+"'");			
		}
		
		sqlBuf.append(" order by P.Mune_Id desc ");
		
		System.out.println("selClassByOsType="+sqlBuf.toString());
		
		return populateQueryByMap(sqlBuf,((Integer) paramMap.get("pageIndex")).intValue(),((Integer) paramMap.get("pageSize")).intValue());
		
	}
	
}
