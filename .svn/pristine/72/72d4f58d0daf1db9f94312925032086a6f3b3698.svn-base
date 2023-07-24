package com.ztesoft.mobile.v2.dao.app;

import com.ztesoft.mobile.common.dao.BaseDAOImpl;
import com.ztesoft.mobile.common.exception.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MobileAppFuncDAOImpl extends BaseDAOImpl implements
		MobileAppFuncDAO {

	private static final String TABLE_NAME = "MOBILE_APP_FUNC";

	public void insert(Map paramMap) throws DataAccessException {
		String patternStr = "APP_FUNC_ID:appFuncId,APP_ID:appId,FUNC_CODE:funcCode,FUNC_NAME:funcName,ACCESS_PACKAGE:accessPackage,ACCESS_CLASS:accessClass,FUNC_CLASS:funcClass,FUNC_CONTENT:funcContent,VERSION_CODE:versionCode,VERSION_NAME:versionName,BUILD_TIME:buildTime,STAFF_ID:staffId,STATE:state,STATE_DATE:stateDate,ACTION_NAME:actionName";
		Long nextId = getPKFromMem(TABLE_NAME, 9);
		paramMap.put("appFuncId", nextId);
		paramMap.put("funcCode", "FUNC_"+nextId);
		dynamicInsertByMap(paramMap, TABLE_NAME, patternStr);
	}

	public void update(Map paramMap) throws DataAccessException {
		String updatePatternStr = "APP_FUNC_ID:appFuncId,APP_ID:appId,FUNC_CODE:funcCode,FUNC_NAME:funcName,FUNC_CONTENT:funcContent,ACCESS_PACKAGE:accessPackage,ACCESS_CLASS:accessClass,FUNC_CLASS:funcClass,BUILD_TIME:buildTime,VERSION_CODE:versionCode,VERSION_NAME:versionName,STAFF_ID:staffId,STATE:state,STATE_DATE:stateDate,ACTION_NAME:actionName";
		String wherePatternStr = "APP_FUNC_ID:appFuncId";
		dynamicUpdateByMap(paramMap, TABLE_NAME, updatePatternStr,
				wherePatternStr);
	}

	public void delete(Map paramMap) throws DataAccessException {
		String wherePatternStr = "APP_FUNC_ID:appFuncId";   
		dynamicDeleteByMap(paramMap, TABLE_NAME, wherePatternStr); //功能删除
		
		Map map = new HashMap();
		List menuConfigIdList = getFunIdForConfigId(paramMap); //这个MOBILE_FUNC_MENU_RELA 表的删除
		for (int j = 0; j < menuConfigIdList.size(); j++) {    
			map.put("menuConfigId",((Map) menuConfigIdList.get(j)).get("menuConfigId"));
			String wherePatternStr2 = "MENU_CONFIG_ID:menuConfigId";
			dynamicDeleteByMap(map, "MOBILE_MENU_CONFIG", wherePatternStr2); //menuconfig 表的删除

		}

	}

	public Map selById(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT APP_FUNC_ID AS appFuncId, APP_ID AS appId, FUNC_CODE AS funcCode, FUNC_NAME AS funcName, ACCESS_CLASS AS accessClass, FUNC_CONTENT AS funcContent, VERSION_CODE AS versionCode, VERSION_NAME AS versionName, BUILD_TIME AS buildTime,STAFF_ID AS staffId, STATE AS state, STATE_DATE AS stateDate ,ACTION_NAME as actionName FROM MOBILE_APP_FUNC WHERE APP_FUNC_ID = ?";
		String wherePatternStr = "APP_FUNC_ID:appFuncId";
		return dynamicQueryObjectByMap(sqlStr, paramMap, wherePatternStr);
	}

	public List selAll(Map paramMap) throws DataAccessException {
		final String sqlStr = "SELECT APP_FUNC_ID AS appFuncId, APP_ID AS appId, FUNC_CODE AS funcCode, FUNC_NAME AS funcName, ACCESS_CLASS AS accessClass, FUNC_CONTENT AS funcContent, VERSION_CODE AS versionCode, VERSION_NAME AS versionName, BUILD_TIME AS buildTime,STAFF_ID AS staffId, STATE AS state, STATE_DATE AS stateDate , mm.priv_code as privCode  ,ACTION_NAME as actionName FROM MOBILE_APP_FUNC WHERE STATE = 1 ";
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, paramMap, wherePatternStr);
	}

	public Map pageSel(Map paramMap) throws DataAccessException {
		final StringBuffer sqlStr = new StringBuffer(
				"select mm.menu_name as menuName, ");
		sqlStr
				.append(" mm.menu_id as menuId ,  mm.parent_id as parentId  ,  mm.menu_icon_uri as menuIconURL,");
		sqlStr
				.append("   mm.os_type as osType,  mm.path_code as pathCode, mm.path_name as pathName, mmc.menu_config_id as menuConfigId,");
		sqlStr
				.append("      mmc.menu_uri as menuUri,   mmc.build_time as buildTime,mm.menu_type as menuType, mm.priv_code as privCode,");
		sqlStr
				.append("   mp.mname as menuTypeName ,mm.menu_index as menuIndex ,mm.MENU_INTRO as menuInfo  from mobile_menu mm, mobile_menu_config mmc , mobile_param mp ");
		sqlStr
				.append("  where mm.menu_id=mmc.menu_id and mp.MCODE =mm.menu_type and mp.gcode='MENU_TYPE'");
		String wherePatternStr = " and mm.parent_id= "
				+ paramMap.get("parentId")+" order by mm.menu_index";
		sqlStr.append(wherePatternStr);
		System.out.println(sqlStr.toString());
		return populateQueryByMap(sqlStr, ((Integer) paramMap.get("pageIndex"))
				.intValue(), ((Integer) paramMap.get("pageSize")).intValue());
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public List selFunClass(Map paramMap) throws DataAccessException {
		final String sqlStr = "select  af.APP_FUNC_ID as appFuncId,app.app_id as appId,app.app_name as appName,af.func_code as funcCode,af.func_name as funcName,af.func_content as funcContent,af.access_class as accessClass,af.access_package as accessPackage,"
				+ "af.func_class as funcClass,af.version_code as versionCode   ,af.ACTION_NAME as actionName from "
				+ "MOBILE_APP app,mobile_app_func af where app.app_id=af.app_id and app.app_id= "
				+ paramMap.get("appId");
		String wherePatternStr = null;
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}

	// 分页查询功能列表
	public Map selFunPage(Map paramMap) throws DataAccessException {
		StringBuffer sqlStr = new StringBuffer(
				"select  maf.app_func_id as appFuncID,"
						+ "  maf.app_id      as appId,"
						+ " maf.func_code   as funcCode,"
						+ " maf.func_name   as funcName,"
						+ " maf.access_package as accessPackage,"
						+ " maf.access_class   as accessClass,"
						+ " maf.func_class     as funcClass,"
						+ " maf.func_content   as funcContent,"
						+ " maf.version_code   as versionCode,"
						+ " maf.version_name   as versionName ,"
						+ " maf.build_time     as buildTime,"
						+ " maf.state_date     as stateDate ," 
                        +" maf.ACTION_NAME as actionName "+
						" from MOBILE_APP_FUNC maf where 1=1 ");
		sqlStr.append("and maf.app_id = " + paramMap.get("appId"));
		System.out.println(sqlStr.toString());
		return populateQueryByMap(sqlStr, ((Integer) paramMap.get("pageIndex"))
				.intValue(), ((Integer) paramMap.get("pageSize")).intValue());
	}

	public List selClassForType(Map paramMap) throws DataAccessException {
		String sqlStr = "select busi_sys_id as mcode, sys_name as mname from MOBILE_BUSI_SYS a where 1=1 ";
		String wherePatternStr = " and a.sys_field_type= "
				+ paramMap.get("appClass");
		System.out.println(sqlStr + wherePatternStr);
		return dynamicQueryListByMap(sqlStr, null, wherePatternStr);
	}

	// 删除应用的时候，级联删除appFun 和 MOBILE_FUNC_MENU_RELA 还有MOBILE_MENU_CONFIG
	public void deleteAppFuncRelaConfig(Map paraMap) throws DataAccessException {
		List list = getFunId(paraMap);
		Map map = new HashMap();
//		for (int i = 0; i < list.size(); i++) {
//			map.put("appFuncId", list.get(i));
//			List menuConfigIdList = getFunIdForConfigId(map);
//			for (int j = 0; j < menuConfigIdList.size(); j++) {
//				map.put("menuConfigId",((Map) menuConfigIdList.get(j)).get("menuConfigId"));
//				String wherePatternStr = "MENU_CONFIG_ID:menuConfigId";
//				dynamicDeleteByMap(map, "MOBILE_MENU_CONFIG", wherePatternStr);
//
//			}
//		}
	}

	// 通过应用查询fun并删除func表
	private List getFunId(Map param) throws DataAccessException {
		List list = null;
		if (param.get("appId") != null && !param.get("appId").equals("")) {

			String sql = "select maf.APP_FUNC_ID as appFuncId from MOBILE_APP_FUNC maf where 1=1 and maf.APP_ID="
			+ new Long(param.get("appId").toString().trim());
		//	 sql = "select maf.APP_FUNC_ID as appFuncId from MOBILE_APP_FUNC maf";
          System.out.println("sql=  "+sql);
			list = dynamicQueryListByMap(sql, null, null);
			System.out.println("getFunId = " + list);
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) {// 删除func
				map.put("appFuncId", ((Map)list.get(i)).get("appFuncId"));
				delete(map);
			}
		}
		return list;
	}

	// 通过fun查询MOBILE_FUNC_MENU_RELA表中的menuConfigId
	private List getFunIdForConfigId(Map param) throws DataAccessException {
		List list = null;
		if (param.get("appFuncId") != null
				&& !param.get("appFuncId").equals("")) {

			String sql = "select b.MENU_CONFIG_ID as menuConfigId from MOBILE_FUNC_MENU_RELA b where b.app_func_id="
			+ new Long(param.get("appFuncId").toString());
			System.out.println(sql);
			list = dynamicQueryListByMap(sql, null, null);
			System.out.println("getFunIdForConfigId = " + list);
			Map map = new HashMap();
			for (int i = 0; i < list.size(); i++) { // 删除MOBILE_FUNC_MENU_RELA的数据
				map.put("appFuncId",param.get("appFuncId") );
				String wherePatternStr = "APP_FUNC_ID:appFuncId";
				dynamicDeleteByMap(map, "MOBILE_FUNC_MENU_RELA",
						wherePatternStr);
			}
		}
		return list;
	}

}
